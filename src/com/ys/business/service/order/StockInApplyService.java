package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
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
import com.ys.business.action.model.order.StockInApplyModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_PurchaseStockInDao;
import com.ys.business.db.dao.B_PurchaseStockInDetailDao;
import com.ys.business.db.dao.B_StockInApplyDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;
import com.ys.business.db.data.B_StockInApplyData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class StockInApplyService extends CommonService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	
	

	private StockInApplyModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public StockInApplyService(){
		
	}

	public StockInApplyService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			StockInApplyModel reqModel,
			UserInfo userInfo){
		
		this.model = model;
		this.reqModel = reqModel;
		this.dataModel = new BaseModel();
		this.request = request;
		this.response = response;
		this.session = session;
		this.userInfo = userInfo;
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		
	}

	
	public HashMap<String, Object> doSearch(String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_STOCKINAPPLY,data,session);
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
		
		dataModel.setQueryName("stockInApplyList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if((key1 !=null && !("").equals(key1)) || 
				(key2 !=null && !("").equals(key2))){
			userDefinedSearchCase.put("status", "");
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


	public HashMap<String, Object> doDirectSearch(String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_STOCKINAPPLY,data,session);
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
		
		dataModel.setQueryName("materialStockInList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if((key1 !=null && !("").equals(key1)) || 
				(key2 !=null && !("").equals(key2))){
			userDefinedSearchCase.put("status", "");
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

	
	public String getArriveId() throws Exception {

		String key = CalendarUtil.fmtYmdDate();
		dataModel.setQueryName("getMAXArrivalId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("arriveDate", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
		return BusinessService.getArriveRecordId(code);			
	}

	//
	public HashMap<String, Object> getBaseBomDetail(
			String bomId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBaseBomDetailByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("material",dataModel.getYsViewData().get(0));
			model.addAttribute("materialDetail",dataModel.getYsViewData());
			//quotData.setRecordid(dataModel.getYsViewData().get(0).get("recordId"));	
			
		}	
				
		return HashMap;
	}
	
	//
	public HashMap<String, Object> getQuotationDetail(
			String bomId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getQuotationDetail");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("material",dataModel.getYsViewData().get(0));
			model.addAttribute("materialDetail",dataModel.getYsViewData());
			//quotData.setRecordid(dataModel.getYsViewData().get(0).get("recordId"));	
			
		}	
				
		return HashMap;
	}
	

	private String insert() throws Exception  {

		String applyId = "";
		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_ArrivalData reqData = reqModel.getStockinApply();
			String remarks = reqData.getRemarks();
			
			applyId = reqData.getArrivalid();
			if( isNullOrEmpty(applyId) ){
				applyId = getArriveId();//申请编号
			}else{
				deleteArrivalById(applyId);
			}			
			
			List<B_ArrivalData> reqDataList = reqModel.getApplyDetailList();
			
			for(B_ArrivalData data:reqDataList ){
				
				String materId = data.getMaterialid();
				String quantity = data.getQuantity();
				//过滤空行或者被删除的数据
				if(notEmpty(materId) && notEmpty(quantity)){					
					data.setArrivalid(applyId);
					data.setRemarks(remarks);
					data.setStockintype(Constants.STOCKINTYPE_1);//入库类别：直接入库
					insertApplyDetail(data);
				}
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			e.printStackTrace();
		}
		
		return applyId;
		
	}	


	private String insertStockin() throws Exception  {

		String receiptid = "";
		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_PurchaseStockInData reqData = reqModel.getStockin();

			List<B_PurchaseStockInDetailData> reqDataList = reqModel.getDetailList();
			//取得入库申请编号
			reqData = getStorageRecordId(reqData);			
			receiptid = reqData.getReceiptid();
			
			//料件入库记录
			//取得入库类别
			reqData.setStockintype( Constants.STOCKINTYPE_11);
			insertPurchaseStockIn(reqData);
			
			//采购入库记录明细
			for(B_PurchaseStockInDetailData data:reqDataList ){
				String quantity = data.getQuantity();
				String materialid= data.getMaterialid();
				if(isNullOrEmpty(quantity) || quantity.equals("0") || isNullOrEmpty(materialid))
					continue;
				
				data.setReceiptid(receiptid);
				insertPurchaseStockInDetail(data);
									
				updateMaterial(materialid,quantity,data.getPrice());//更新库存
						
			}	
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			e.printStackTrace();
		}
		
		return receiptid;
		
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
	
	//更新当前库存:直接入库时，增加“当前库存”
	@SuppressWarnings("unchecked")
	private B_MaterialData updateMaterial(
			String materialId,
			String reqQuantity,String reqPrice) throws Exception{
	
		B_MaterialData data = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();
		
		String where = "materialId ='"+ materialId + "' AND deleteFlag='0' ";
		
		List<B_MaterialData> list = 
				(List<B_MaterialData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return null;
		}

		data = list.get(0);
		
		insertStorageHistory(data,"直接入库",reqQuantity);//保留更新前的数据
		
		//当前库存数量
		float iQuantity = stringToFloat(data.getQuantityonhand());
		float ireqQuantity = stringToFloat(reqQuantity);	
		float iNewQuantiy = iQuantity + ireqQuantity;		
		
		//待入库数量
		float istockin = stringToFloat(data.getWaitstockin());		
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float waitstockout = stringToFloat(data.getWaitstockout());//待出库	
		float availabeltopromise = iNewQuantiy + istockin - waitstockout;		
		
		
		data.setQuantityonhand(floatToString(iNewQuantiy));
		data.setAvailabeltopromise(floatToString(availabeltopromise));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
		return data;
		
	}
	

	private void insertPurchaseStockIn(
			B_PurchaseStockInData stock) throws Exception {

		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"直接入库",userInfo);
		copyProperties(stock,commData);
		stock.setKeepuser(userInfo.getUserId());//默认为登陆者
		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		stock.setRemarks(replaceTextArea(stock.getRemarks()));
		
		new B_PurchaseStockInDao().Create(stock);
	}
	
	private void insertPurchaseStockInDetail(
			B_PurchaseStockInDetailData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"直接入库",userInfo);
		copyProperties(stock,commData);

		String guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		
		new B_PurchaseStockInDetailDao().Create(stock);	
	}
	//删除待报检的记录
	@SuppressWarnings("unchecked")
	private void deleteArrivalById(String arrivalId) throws Exception {

		String where = " arrivalId = '"+arrivalId+
				"' AND deleteFlag='0' ";
		
		List<B_ArrivalData> list = new B_ArrivalDao().Find(where);
		if(list.size() > 0){
			
			for(B_ArrivalData data:list){

				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"ArrivalDelete",userInfo);
				copyProperties(data,commData);
				new B_ArrivalDao().Store(data);				
				
			}
		}		
	}
	
	public void getProductDetail(String productId) throws Exception {		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", productId);
		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getProductList");
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();	 
		
		model.addAttribute("product", dataModel.getYsViewData().get(0));

	}
	
	
	private void insertApplyDetail(
			B_ArrivalData apply) throws Exception{
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"StockInApplyDetailInsert",userInfo);
		copyProperties(apply,commData);

		guid = BaseDAO.getGuId();
		apply.setRecordid(guid);
		apply.setStatus(Constants.ARRIVAL_STS_1);//待报检
		apply.setArrivaltype(Constants.ARRIVAL_TYPE_2);//直接入库
		apply.setUserid(userInfo.getUserId());
		apply.setArrivedate(CalendarUtil.fmtYmdDate());
		apply.setRemarks(replaceTextArea(apply.getRemarks()));//换行符转换
		
		new B_ArrivalDao().Create(apply);

	}	

	private void updateStockInApply(B_ArrivalData apply) 
			throws Exception{
		
		//取得更新前数据
		B_StockInApplyData db = null;
		try{
			db = new B_StockInApplyDao(apply).beanData;
		}catch(Exception e){
			//
		}
		if(db == null){
			getArriveId();
		}else{
			//处理共通信息
			copyProperties(db,apply);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"StockInApplyUpdate",userInfo);
			copyProperties(db,commData);
			db.setRequestuserid(userInfo.getUserId());
			db.setStockinstatus("020");//待入库
			db.setRemarks(replaceTextArea(db.getRemarks()));
			
			new B_StockInApplyDao().Store(db);
		}
		
	}
	
	public void getStockInApplyDetail(String applyId) throws Exception {

		dataModel.setQueryName("getArrivaListByArrivalId");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("arrivalId", applyId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	
			
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("apply",dataModel.getYsViewData().get(0));//
			model.addAttribute("applyDetail",dataModel.getYsViewData());			
		}		
	}
	

	public void getStockInDirectDetail(String receiptId) throws Exception {

		dataModel.setQueryName("materialStockInList");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("receiptId", receiptId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	
			
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("stockin",dataModel.getYsViewData().get(0));//
			model.addAttribute("DirectDetail",dataModel.getYsViewData());			
		}		
	}
	
	public HashMap<String, Object> showBaseBomDetail() throws Exception {

		String materialId = request.getParameter("materialId");
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		return getBaseBomDetail(bomId);	
	}
	
	public void stockinApplyAddInit(){
		//nothing
	}
	
	public void stockinDirectAddInit(){
		//nothing
	}
	
	public void stockInApplyInsert() throws Exception{
		
		String applyId = insert();
		getStockInApplyDetail(applyId);		
	}
	
	public void stockInDirectInsert() throws Exception{
		
		String receiptid = insertStockin();
		getStockInDirectDetail(receiptid);		
	}
	
	
	public void showStockinApply() throws Exception{
		
		String applyId = request.getParameter("applyId");
		
		getStockInApplyDetail(applyId);		
	}
	
	public void showStockinDirect() throws Exception{
		
		String receiptId = request.getParameter("receiptId");
		
		getStockInDirectDetail(receiptId);		
	}

	public void stockInApplyEdit() throws Exception{
		
		String applyId = request.getParameter("applyId");
		
		getStockInApplyDetail(applyId);		
	}
	
	public void stockInApplyUpdate() throws Exception{
		
		String applyId = insert();
		
		getStockInApplyDetail(applyId);		
	}
}

