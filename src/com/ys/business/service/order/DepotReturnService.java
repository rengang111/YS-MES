package com.ys.business.service.order;

/**
 * 仓库退货
 */
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.DepotReturnModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchaseStockInDao;
import com.ys.business.db.dao.B_PurchaseStockInDetailDao;
import com.ys.business.db.data.B_InspectionReturnData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class DepotReturnService extends CommonService {
	
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private DepotReturnModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public DepotReturnService(){
		
	}

	public DepotReturnService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			DepotReturnModel reqModel,
			UserInfo userInfo){
		
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}


	public HashMap<String, Object> search(
			String data,String formId) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];

		dataModel.setQueryName("getDepotReturnList");
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);
		
		if ( iEnd > dataModel.getYsViewData().size()){
			iEnd = dataModel.getYsViewData().size();
		}		
		
		modelMap.put("sEcho", sEcho); 
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		modelMap.put("data", dataModel.getYsViewData());
		modelMap.put("keyword1",key1);
		modelMap.put("keyword2",key2);
		return modelMap;
	}
	
	
	
	public void createDepotReturnInit() {

		
	
	}
	
	
	
	public void doDelete() throws Exception{
		
		ts = new BaseTransaction();
		
		try{
			ts.begin();

			String receiptId = request.getParameter("receiptId");	
				
			String contractId = deletePurchaseStockin(receiptId);
		
			List<B_PurchaseStockInDetailData> list = getPurchaseStockInDetail(receiptId);
			
			if(list !=null){
				for(B_PurchaseStockInDetailData detail:list){

					//更新库存
					updateMaterial(
							"入库退货删除处理",
							detail.getMaterialid(),
							String.valueOf(stringToFloat(detail.getQuantity()) * (-1)),//退货数是负数，还原处理，所以 * -1
							"0");
					
					//删除明细
					deletePurchaseStockInDetail(detail);					

					//更新合同的有效入库，有点冗余，优先考虑性能
					if(notEmpty(contractId)){

						//***********************//
						//** 更新合同的入库数量 **//
						//***********************//
						updateContractStorage(contractId,detail.getMaterialid());
						
						//确认合同状态:是否全部入库
						boolean flag = checkPurchaseOrderStatus(contractId);		
						if(flag){			
							////更新合同状态
							updateContractStatus(contractId,
									Constants.STOCKIN_STS_Y,//已入库
									Constants.CONTRACT_STS_3);//入库完毕			
						}else{
							updateContractStatus(contractId,
									Constants.STOCKIN_STS_N,//未入库
									Constants.CONTRACT_STS_2);//入库中				
						}
						//String quantity = String.valueOf(stringToFloat(detail.getQuantity()) * (-1));
						//updateContract("入库退货更新处理",
						//		detail.getMaterialid(),
						//		quantity,
						//		contractId,
						//		-1);
					}
				}
			}
				
			
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
		}
		ts.commit();
		
		
	}
	
	@SuppressWarnings("unchecked")
	private List<B_PurchaseStockInDetailData> getPurchaseStockInDetail(
			String receiptId) throws Exception{
		List<B_PurchaseStockInDetailData> list = null;
		
		String where = " receiptId='" + receiptId + "' AND deleteFlag='0' ";

		list = new B_PurchaseStockInDetailDao().Find(where);
				
		return list;
	}

	@SuppressWarnings("rawtypes")
	private String deletePurchaseStockin(String receiptId) throws Exception{
		String contractId = "";
		String where = " receiptId='" + receiptId +"' AND deleteFlag='0' ";
		List list = new B_PurchaseStockInDao().Find(where);
		
		if(list == null || ("").equals(list))
			return contractId;
		
		//更新DB
		B_PurchaseStockInData data = (B_PurchaseStockInData) list.get(0);
		contractId = data.getContractid();
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"DepotReturnDelete",userInfo);
		copyProperties(data,commData);
		
		new B_PurchaseStockInDao().Store(data);
		
		return contractId;
		
	}
	
	private void deletePurchaseStockInDetail(
			B_PurchaseStockInDetailData detail) throws Exception{
		
		//删除DB
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"DepotReturnDelete",userInfo);
		copyProperties(detail,commData);
		
		new B_PurchaseStockInDetailDao().Store(detail);
	}
	
	public void update() throws Exception {
		
		ts = new BaseTransaction();
		String receiptid = "";
		
		try{
			ts.begin();
			
			String oldQty = request.getParameter("oldQuantity");
			
			B_PurchaseStockInData reqData = reqModel.getStockin();
			B_PurchaseStockInDetailData detail = 
					reqModel.getDetail();
			
			//更新处理
			updatePurchaseStockIn(reqData);
			
			//入库记录明细
			receiptid = reqData.getReceiptid();	
			float freqQty = stringToFloat(detail.getQuantity());//更新后
			//float dbQty = freqQty * (-1) ;
			detail.setReceiptid(receiptid);
			detail.setQuantity(floatToString(freqQty));//便于财务计算
			updatePurchaseStockInDetail(detail);

			//更新库存
			String materialId = detail.getMaterialid();
			float foldQty = stringToFloat(oldQty);//更新前
			String newQty = floatToString( foldQty - freqQty );//前后差值
			updateMaterial("入库退货更新处理",materialId,newQty,"0");
			
			//更新合同的有效入库，有点冗余，优先考虑性能
			String contractId = reqData.getContractid();

			//***********************//
			//** 更新合同的入库数量 **//
			//***********************//
			updateContractStorageAndArrival(contractId,materialId);
			
			//确认合同状态:是否全部入库
			boolean flag = checkPurchaseOrderStatus(contractId);		
			if(flag){			
				////更新合同状态
				updateContractStatus(contractId,
						Constants.STOCKIN_STS_Y,//已入库
						Constants.CONTRACT_STS_3);//入库完毕			
			}else{
				updateContractStatus(contractId,
						Constants.STOCKIN_STS_N,//未入库
						Constants.CONTRACT_STS_2);//入库中				
			}
			
			//updateContract("入库退货更新处理",materialId,newQty,contractId,0);
			
			ts.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
		}
		
		getDepotReturnDetail(receiptid);
		
	}
	
	public HashMap<String, Object> getContractDetail(String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null){			
			iEnd = iStart + Integer.parseInt(length);		
		}	
		String key1 = getJsonData(data, "keyword1").trim().toUpperCase();
		String key2 = getJsonData(data, "keyword2").trim().toUpperCase();
				
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = new BaseQuery(request, dataModel);

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getContractList");
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		String having = "1=1";
		sql = sql.replace("#", having);
		baseQuery.getYsQueryData(sql,having,iStart, iEnd);	
		
		if ( iEnd > dataModel.getYsViewData().size()){
			iEnd = dataModel.getYsViewData().size();
		}		
		
		modelMap.put("sEcho", sEcho);		
		modelMap.put("recordsTotal", dataModel.getRecordCount());		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		modelMap.put("data", dataModel.getYsViewData());
		modelMap.put("keyword1",key1);	
		modelMap.put("keyword2",key2);
		
		return modelMap;
	}
	
	public void insertAndView() throws Exception{
		
		ts = new BaseTransaction();
		String receiptid = "";
		
		try{
			ts.begin();

			B_PurchaseStockInData reqData = reqModel.getStockin();
			B_PurchaseStockInDetailData detail = 
					reqModel.getDetail();
			//取得入库申请编号
			reqData = getStorageRecordId(reqData);			
			receiptid = reqData.getReceiptid();
			String contractId = reqData.getContractid();
			String materialId = detail.getMaterialid();
			
			//取得原合同入库信息
			B_PurchaseStockInData dbData = getStockinData(contractId);
			
			String stokinType = dbData.getStockintype();
			reqData.setStockintype(stokinType);
			reqData.setArrivelid(dbData.getArrivelid());
			insertPurchaseStockIn(reqData);
			
			//入库记录明细
			String oldReceiptId = dbData.getReceiptid();
			B_PurchaseStockInDetailData dbDetail = getStockinDetail(oldReceiptId);
			String price = dbDetail.getPrice();
			if(isNullOrEmpty(price))
				price = getContractPrice(contractId,materialId);//重新从合同取得单价
			
			detail.setReceiptid(receiptid);
			detail.setPackaging(dbDetail.getPackaging());
			detail.setDepotid(dbDetail.getDepotid());
			detail.setPrice(price);

			String quantity = detail.getQuantity();//
			
			detail.setQuantity(quantity);
			insertPurchaseStockInDetail(detail);

			//更新库存
			updateMaterial("入库退货更新处理",materialId,quantity,"0");
			
			//更新合同有效收货
			if(notEmpty(contractId)){
				//***********************//
				//** 更新合同的入库数量 **//
				//***********************//
				updateContractStorage(contractId,materialId);
				//updateContract("入库退货更新处理",materialId,quantity,contractId,1);
				
				//确认合同状态:是否全部入库
				boolean flag = checkPurchaseOrderStatus(contractId);		
				if(flag){			
					////更新合同状态
					updateContractStatus(contractId,
							Constants.STOCKIN_STS_Y,//已入库
							Constants.CONTRACT_STS_3);//入库完毕			
				}else{
					updateContractStatus(contractId,
							Constants.STOCKIN_STS_N,//未入库
							Constants.CONTRACT_STS_2);//入库中				
				}
			}
			
			ts.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
		}
		
		getDepotReturnDetail(receiptid);
		
	}

	@SuppressWarnings("unchecked")
	private String getContractPrice(String contractId,String materialId) throws Exception{
		String price = "";
		String where = " contractId='"+ contractId + "' AND materialId ='"+materialId+"' AND deleteFlag='0' ";
		
		List<B_PurchaseOrderDetailData> list = new B_PurchaseOrderDetailDao().Find(where);
		
		if(list == null || list.size() == 0)
			return price;
		
		price = list.get(0).getPrice();
		
		return price;
	}
	
	
	@SuppressWarnings("unchecked")
	private B_PurchaseStockInData getStockinData(String contractId) throws Exception{
		
		B_PurchaseStockInData stockin = new B_PurchaseStockInData();
		String where = " contractId='"+ contractId + "' AND deleteFlag='0' ";
		
		List<B_PurchaseStockInData> list = new B_PurchaseStockInDao().Find(where);
		
		if(list == null || list.size() == 0)
			return stockin;
		
		stockin = list.get(0);
		
		return stockin;
	}
	
	@SuppressWarnings("unchecked")
	private B_PurchaseStockInDetailData getStockinDetail(String receiptId) throws Exception{
		
		B_PurchaseStockInDetailData stockin = new B_PurchaseStockInDetailData();
		String where = " receiptId='"+ receiptId + "' AND deleteFlag='0' ";
		
		List<B_PurchaseStockInDetailData> list = new B_PurchaseStockInDetailDao().Find(where);
		
		if(list == null || list.size() == 0)
			return stockin;
		
		stockin = list.get(0);
		
		return stockin;
	}
	
	@SuppressWarnings("unchecked")
	private void updatePurchaseStockIn(
			B_PurchaseStockInData stock) throws Exception {
		
		String where = "receiptId ='"+ stock.getReceiptid() + "' AND deleteFlag='0' ";
		
		List<B_PurchaseStockInData> list = 
				(List<B_PurchaseStockInData>)new B_PurchaseStockInDao().Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}
		B_PurchaseStockInData data = list.get(0);
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"入库取消更新",userInfo);
		copyProperties(data,commData);
		data.setRemarks(replaceTextArea(stock.getRemarks()));;
		
		new B_PurchaseStockInDao().Store(data);	
	}
	
	@SuppressWarnings("unchecked")
	private void updatePurchaseStockInDetail(
			B_PurchaseStockInDetailData stock) throws Exception {
		
		String where = "receiptId ='"+ stock.getReceiptid() + "' AND deleteFlag='0' ";
		
		List<B_PurchaseStockInDetailData> list = 
				(List<B_PurchaseStockInDetailData>)new B_PurchaseStockInDetailDao().Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}
		B_PurchaseStockInDetailData data = list.get(0);
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"入库取消更新",userInfo);
		copyProperties(data,commData);
		data.setQuantity(stock.getQuantity());
		
		new B_PurchaseStockInDetailDao().Store(data);	
	}
	
	private void insertPurchaseStockInDetail(
			B_PurchaseStockInDetailData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"新增入库取消",userInfo);
		copyProperties(stock,commData);

		String guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		
		new B_PurchaseStockInDetailDao().Create(stock);	
	}
	
	
	private void insertPurchaseStockIn(
			B_PurchaseStockInData stock) throws Exception {

		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"新增入库取消",userInfo);
		copyProperties(stock,commData);
		stock.setKeepuser(userInfo.getUserId());//默认为登陆者
		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		stock.setReverse("1");//冲账处理
		stock.setCheckindate(CalendarUtil.fmtDate());
		stock.setRemarks(replaceTextArea(stock.getRemarks()));
		
		new B_PurchaseStockInDao().Create(stock);
	}

	public B_PurchaseStockInData getStorageRecordId(
			B_PurchaseStockInData data) throws Exception {

		String parentId = BusinessService.getshortYearcode()+
				BusinessConstants.SHORTNAME_RK;
		dataModel.setQueryName("getMAXStorageRecordId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");	
		
		String inspectionId = 
				BusinessService.getStorageRecordId(
						parentId,
						code,
						false);	
		
		data.setReceiptid(inspectionId);
		data.setParentid(parentId);
		data.setSubid(code);			
		
		return data;
		
	}
	public void edit() throws Exception{
		
		String depotId = request.getParameter("inspectionReturnId");				

		getDepotReturnDetail(depotId);
		
	}
	
	public void getDepotReturnDetail(String receiptId) throws Exception {
		
		dataModel.setQueryName("getDepotReturnList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("receiptId", receiptId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		model.addAttribute("depot",dataModel.getYsViewData().get(0));
	}
	

	public B_InspectionReturnData getReceiveInspectionId(B_InspectionReturnData data) throws Exception {
	
		String parentid = data.getContractid();
		dataModel.setQueryName("getMAXInspectionReturnId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
		
		String inspectionId = 
				BusinessService.getInspectionReturnId(parentid,code,false);
		
		data.setInspectionreturnid(inspectionId);
		data.setParentid(parentid);
		data.setSubid(code);
		
		return data;		
	}

	
	//更新当前库存:减少“当前库存”
	@SuppressWarnings("unchecked")
	private void updateMaterial(
			String action,
			String materialId,
			String newQuantity,
			String oldQuantity) throws Exception{
	
		B_MaterialData data = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();
		
		String where = "materialId ='"+ materialId + "' AND deleteFlag='0' ";
		
		List<B_MaterialData> list = 
				(List<B_MaterialData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		insertStorageHistory(data,action,newQuantity);//保留更新前的数据
		
		//当前库存数量
		float iQuantity = stringToFloat(data.getQuantityonhand());
		//退货的更新处理，库存=库存 - 本次退货 + 修改前的退货数（还原）
		float iNewQuantiy = iQuantity - stringToFloat(newQuantity) + stringToFloat(oldQuantity);		
		//待入库
		float waitstockin = stringToFloat(data.getWaitstockin());
		//待出库
		float istockout = stringToFloat(data.getWaitstockout());		
		//float iNewStockOut = istockout - reqQuantity;
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float availabeltopromise = iNewQuantiy + waitstockin - istockout;
		
		data.setQuantityonhand(String.valueOf(iNewQuantiy));
		//data.setWaitstockout(String.valueOf(iNewStockOut));
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"DepotReturnUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}	
	
	//更新有效收货
	@SuppressWarnings("unchecked")
	private void updateContract2(
			String action,
			String materialId,
			String newQuantity,
			String contractId,
			int count) throws Exception{
	
		B_PurchaseOrderDetailData data = new B_PurchaseOrderDetailData();
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();
		
		String where = "contractId ='"+ contractId + "' "+
				" AND materialId ='"+ materialId + "' AND deleteFlag='0' ";
		
		List<B_PurchaseOrderDetailData> list = 
				(List<B_PurchaseOrderDetailData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		float webQty = stringToFloat(newQuantity);

		float istorage = stringToFloat(data.getContractstorage());//入库数
		float iaccumtd = stringToFloat(data.getAccumulated());//收货数
		float ireturns = stringToFloat(data.getReturngoods());//退货数
		float ireturnN = stringToFloat(data.getReturnnumber());//退货次数
		
		//有效库存减少
		float iNewStorage = istorage - webQty;
		float iNewAccumtd = iaccumtd - webQty;
		float iNewReturns = ireturns + webQty;//退货数增加
		float iNewReturnN = ireturnN + count;//累计
				
		data.setAccumulated(String.valueOf(iNewAccumtd));
		data.setContractstorage(String.valueOf(iNewStorage));
		data.setReturngoods(String.valueOf(iNewReturns));
		data.setReturnnumber(String.valueOf(iNewReturnN));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				action,userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}	
		
	public void getDepotRentunDeital() throws Exception{
		
		String depotId = request.getParameter("inspectionReturnId");				

		getDepotReturnDetail(depotId);
		
	}
	
	public HashMap<String, Object> getDepotReturnByContract() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String contractId = request.getParameter("contractId");				

		dataModel.setQueryName("getDepotReturnList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("contractId", contractId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> CansolDepotReturnByStockinId() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String stockinId = request.getParameter("stockinId");
		//String materialId = request.getParameter("materialId");								

		String where =" receiptId='" + stockinId 
			//	+"’AND materialId='" + materialId 
				+"' AND deleteFlag='0'";
		
		List<B_PurchaseStockInData> list = new B_PurchaseStockInDao().Find(where);
		
		if( list.size() > 0 ){
			B_PurchaseStockInData data = list.get(0);
			data.setReversevalid("1");//取消扣款
			//更新DB
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"取消扣款",userInfo);
			copyProperties(data,commData);
			
			new B_PurchaseStockInDao().Store(data);
			
		}
		

		modelMap.put("returnCode", "SUCCESS");
		
		return modelMap;
	}
	
}
