package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.ys.business.action.model.order.StockOutModel;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_RequisitionDao;
import com.ys.business.db.dao.B_StockOutDao;
import com.ys.business.db.dao.B_StockOutDetailDao;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_RequisitionData;
import com.ys.business.db.data.B_StockOutData;
import com.ys.business.db.data.B_StockOutDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class StockOutService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_StockOutDao dao;
	private StockOutModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public StockOutService(){
		
	}

	public StockOutService(Model model,
			HttpServletRequest request,
			HttpSession session,
			StockOutModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_StockOutDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	
	public HashMap<String, Object> doSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_MATERIALSTOCKOUT,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryName("requisitionList");//领料单一览
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2)){
			userDefinedSearchCase.put("requisitionSts", "");
		}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		baseQuery.getYsQueryData(sql,iStart, iEnd);	 
				
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
	
	public void addInit() throws Exception {

		String YSId = request.getParameter("YSId");
		String requisitionId = request.getParameter("requisitionId");
				
		B_StockOutData stock = new B_StockOutData();
		stock.setYsid(YSId);
		stock.setRequisitionid(requisitionId);
		reqModel.setStockout(stock);

		//订单详情
		getOrderDetail(YSId);
	
	}
	

	public void edit() throws Exception {
	//	B_StockOutDetailData reqData = reqModel.getStockOut();
		
		
	//	getContractDetail(contractId);//合同信息
	//	getArrivaRecord(receiptId);//入库明细

	//	model.addAttribute("packagingList",util.getListOption(DicUtil.DIC_PACKAGING, ""));
	//	model.addAttribute("receiptId",receiptId);//已入库
	
	}

	
	public void editProduct() throws Exception {
		String YSId = request.getParameter("YSId");
		String receiptId = request.getParameter("receiptId");
		
		//取得订单信息
		getOrderDetail(YSId);
		//getArrivaRecord(receiptId);//入库明细

		model.addAttribute("packagingList",util.getListOption(DicUtil.DIC_PACKAGING, ""));
		model.addAttribute("receiptId",receiptId);//已入库
	
	}

	public void printReceipt() throws Exception {
		String YSId = request.getParameter("YSId");
		String stockOutId = request.getParameter("stockOutId");
		
		//取得订单信息
		getOrderDetail(YSId);
		//getArrivaRecord(receiptId);//入库明细	
	}

	public void printProductReceipt() throws Exception {
		String YSId = request.getParameter("YSId");
		
		//取得订单信息
		getOrderDetail(YSId);	
	}
	
	
	public HashMap<String, Object> getProductStockInDetail() {

		String YSId = request.getParameter("YSId");
		String materialId = request.getParameter("materialId");
		return getOrderAndStockDetail(YSId,materialId);
	}
	public void insertAndReturn() throws Exception {

		String YSId = insertStorage();

		getOrderDetail(YSId);

	}

	public void updateAndReturn() throws Exception {

		String YSId = updateStorage();

		getOrderDetail(YSId);

	}
	

	
	private String updateStorage(){
		String YSId = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			//B_StockOutData reqData = reqModel.getStockOut();


			//取得出库单编号
			//String receiptid = reqData.getReceiptid();
			//YSId = reqData.getYsid();
			
			//采购出库记录
			//updatePurchaseStockIn(reqData);
			

			
			//确认合同状态:是否全部入库
		//	boolean flag = checkPurchaseOrderStatus(reqData.getYsid(),contractId);
			//if(flag){			
				////更新合同状态
				//updateContractStatus(contractId,Constants.CONTRACT_STS_3);//入库完毕			
		//	}
			
			
			ts.commit();			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			try {
				ts.rollback();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		return YSId;
	}
	
	private String insertStorage(){
		String YSId = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_StockOutData reqData = reqModel.getStockout();
			List<B_StockOutDetailData> reqDataList = reqModel.getStockList();

			//取得出库单编号
			YSId= reqData.getYsid();
			reqData = getStorageRecordId(reqData);			
			String id = reqData.getStockoutid();
			String requisitionId = reqData.getRequisitionid();
	
			//出库记录
			insertStockOut(reqData);
			

			for(B_StockOutDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				
				if(quantity <= 0)
					continue;
				
				data.setStockoutid(id);
				insertStockOutDetail(data);								
				
				//更新库存
				updateMaterial(data.getMaterialid(),quantity);//更新库存
			
			}
			//确认合同状态:是否全部出库
			//boolean flag = checkPurchaseOrderStatus(reqData.getYsid(),contractId);
			
			updateRequisition(requisitionId);
			
			ts.commit();			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			try {
				ts.rollback();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		return YSId;
	}
	


	
	//更新当前库存:出库时，减少“当前库存”，减少“待出库“
	@SuppressWarnings("unchecked")
	private void updateMaterial(
			String materialId,
			float reqQuantity) throws Exception{
	
		B_MaterialData data = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();
		
		String where = "materialId ='"+ materialId + "' AND deleteFlag='0' ";
		
		List<B_MaterialData> list = 
				(List<B_MaterialData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		//当前库存数量
		float iQuantity = stringToFloat(data.getQuantityonhand());		
		float iNewQuantiy = iQuantity - reqQuantity;		
		
		//待入库数量
		float istockin = stringToFloat(data.getWaitstockin());		
		float iNewStockIn = istockin - reqQuantity;
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float waitstockout = stringToFloat(data.getWaitstockout());//待出库	
		float availabeltopromise = iNewQuantiy + iNewStockIn - waitstockout;
		
		data.setQuantityonhand(String.valueOf(iNewQuantiy));
		data.setWaitstockin(String.valueOf(iNewStockIn));
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}
	
	@SuppressWarnings("unchecked")
	private void updateProductStock(
			String materialId,
			String reqQuantity,
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
		
		//当前库存数量
		float iQuantity = stringToFloat(data.getQuantityonhand());
		float ireqQuantity = stringToFloat(reqQuantity);				
		float oldQuan = stringToFloat(oldQuantity);
		float iNewQuantiy = iQuantity + ireqQuantity - oldQuan;
		
				
		data.setQuantityonhand(String.valueOf(iNewQuantiy));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}
	

	@SuppressWarnings("unchecked")
	private void updateRequisition(
			String id) throws Exception{

		String where = "requisitionId = '" + id  +"' AND deleteFlag = '0' ";
		List<B_RequisitionData> list  = new B_RequisitionDao().Find(where);
		if(list ==null || list.size() == 0)
			return ;	

		B_RequisitionData data = list.get(0);		
		//更新状态
		data.setRequisitionsts(Constants.STOCKOUT_3);//已出库		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"StockOutInsert",userInfo);
		copyProperties(data,commData);
		
		new B_RequisitionDao().Store(data);
		
	}

	
	private void insertStockOut(
			B_StockOutData stock) throws Exception {

		//删除旧数据,防止数据重复
		String receiptId = stock.getStockoutid();
		String where = " receiptId = '"+receiptId+"'";
		try {
			dao.RemoveByWhere(where);
		} catch (Exception e) {
			// nothing
		}		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"StockOutInsert",userInfo);
		copyProperties(stock,commData);
		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		stock.setKeepuser(userInfo.getUserId());//默认为登陆者
		
		dao.Create(stock);
	}
	
	private void insertStockOutDetail(
			B_StockOutDetailData stock) throws Exception {

		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(stock,commData);
		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
				
		new B_StockOutDetailDao().Create(stock);
	}
	

	
	public HashMap<String, Object> getRequisitionDetail() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String requisitionId = request.getParameter("requisitionId");
		
		dataModel.setQueryName("requisitionDetailById");
		userDefinedSearchCase.put("requisitionId", requisitionId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());	
	
		return modelMap;
	}
	
	private HashMap<String, Object> getOrderAndStockDetail(
			String YSId,String materialId){

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		try {
			dataModel.setQueryName("getPurchaseStockInById");
			userDefinedSearchCase.put("YSId", YSId);
			userDefinedSearchCase.put("materialId", materialId);
			baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsFullData();

			modelMap.put("data", dataModel.getYsViewData());
			model.addAttribute("head",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());		
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return modelMap;
	}
	

	
	public B_StockOutData getStorageRecordId(
			B_StockOutData data) throws Exception {
	
		String parentId = BusinessService.getshortYearcode()+
				BusinessConstants.SHORTNAME_CK;
		dataModel.setQueryName("getMAXStockOutId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");	
		
		String id = BusinessService.getStockOutId(
						parentId,
						code,
						false);	
		
		data.setStockoutid(id);
		data.setParentid(parentId);
		data.setSubid(code);			
		
		return data;
		
	}
	

	public void getOrderDetail(String YSId) throws Exception {

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("orderDetail",dataModel.getYsViewData());			
		}		
	}
	
	public HashMap<String, Object> getStockoutHistory(
			String YSId) throws Exception {
		
		dataModel.setQueryName("stockoutListById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	
	
	public HashMap<String, Object> getStockoutDetail() throws Exception {
		String stockOutId = request.getParameter("stockOutId");
		dataModel.setQueryName("stockoutdetail");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("stockOutId", stockOutId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}

}
