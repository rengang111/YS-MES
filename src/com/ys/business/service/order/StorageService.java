package com.ys.business.service.order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.ys.business.action.model.order.StorageModel;
import com.ys.business.db.dao.B_BeginningInventoryHistoryDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchaseStockInDao;
import com.ys.business.db.dao.B_PurchaseStockInDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_BeginningInventoryHistoryData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.ExcelUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class StorageService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;	
	
	private B_PurchaseStockInDao dao;
	private B_PurchaseStockInDetailDao detaildao;
	private StorageModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;

	public StorageService(){
		
	}

	public StorageService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			StorageModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_PurchaseStockInDao();
		this.detaildao = new B_PurchaseStockInDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.response = response;
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
	
	public HashMap<String, Object> doSearch(
			String data,String formId,String makeType) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(formId,data,session);
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
		
		dataModel.setQueryName("getMaterialCheckInList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		
		//包装,或者是料件入库
		if(("G").equals(makeType)){//包装
			userDefinedSearchCase.put("makeTypeG", "G");
			userDefinedSearchCase.put("makeTypeL", "");
		}else{//料件
			userDefinedSearchCase.put("makeTypeG", "");
			userDefinedSearchCase.put("makeTypeL", "G");			
		}

		String status = request.getParameter("status");	
		if( notEmpty(key1) || notEmpty(key2)){
			status = "";//关键字查询时,不考虑其状态;
		}
		userDefinedSearchCase.put("status", status);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);

		String where = "1=1";
		if(("020").equals(status)){			
			where = "REPLACE(stockinQty,',','') <= 0";//未入库
		}else if(("030").equals(status)){			
			where = "REPLACE(stockinQty,',','') > 0";//已入库		
		}
		sql = sql.replace("#", where);
		baseQuery.getYsQueryData(sql,where,iStart, iEnd);	 
				
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

	public HashMap<String, Object> beginningInventorySearch(
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

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		
		String searchType = request.getParameter("searchType");
		if(("1").equals(searchType)){
			//库存为负数
			dataModel.setQueryName("materialinventory_search");	
		}else if(("2").equals(searchType)){
			//库存 ≠ 总到货－总领料
			dataModel.setQueryName("materialinventory_search2");	
		}else{
			//正常库存
			dataModel.setQueryName("materialinventoryForNormal_search");	
		}	
		baseQuery = new BaseQuery(request, dataModel);
		
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		//String sql = getSortKeyFormWeb(data,baseQuery);
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

	public HashMap<String, Object> doSearchYszz(
			String data,String formId,String makeType) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(formId,data,session);
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
		
		dataModel.setQueryName("getMaterialCheckInListYszz");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2)){
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
	
	public HashMap<String, Object> doMaterialStockinSearch(
			String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_MATERIALSTOCKIN,data,session);
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

	
	public HashMap<String, Object> doFinanceSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_FINANCESTOCKIN,data,session);
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
		
		dataModel.setQueryName("getPurchaseStockInById");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);

		String approvalY = getJsonData(data, "approvalStatusY");
		String approvalN = getJsonData(data, "approvalStatusN");
		String makeTypeL = getJsonData(data, "makeTypeL");
		String makeTypeG = getJsonData(data, "makeTypeG");
		String makeTypeI = getJsonData(data, "makeTypeI");
		
		//查询全部
		if(notEmpty(approvalY) && notEmpty(approvalN)){
			userDefinedSearchCase.put("approvalStatusY", "");
			userDefinedSearchCase.put("approvalStatusN", "");			
		}else{
			userDefinedSearchCase.put("approvalStatusY", approvalY);
			userDefinedSearchCase.put("approvalStatusN", approvalN);	
			
		}
		
		//查询全部
		if(notEmpty(makeTypeL) && notEmpty(makeTypeG) && notEmpty(makeTypeI)){
			userDefinedSearchCase.put("makeTypeL_NOT", "");
			userDefinedSearchCase.put("makeTypeL_IN", "");
			userDefinedSearchCase.put("makeTypeG", "");
			userDefinedSearchCase.put("makeTypeI", "");
		}else if(notEmpty(makeTypeL) && notEmpty(makeTypeG)){
			userDefinedSearchCase.put("makeTypeL_NOT", "I");
			userDefinedSearchCase.put("makeTypeL_IN", "");
			userDefinedSearchCase.put("makeTypeG", "");
			userDefinedSearchCase.put("makeTypeI", "");
		}else if(notEmpty(makeTypeL) && notEmpty(makeTypeI)){
			userDefinedSearchCase.put("makeTypeL_NOT", "G");
			userDefinedSearchCase.put("makeTypeL_IN", "");
			userDefinedSearchCase.put("makeTypeG", "");
			userDefinedSearchCase.put("makeTypeI", "");
		}else if(notEmpty(makeTypeG) && notEmpty(makeTypeI)){
			userDefinedSearchCase.put("makeTypeL_IN", "G,I");
			userDefinedSearchCase.put("makeTypeL_NOT", "");
			userDefinedSearchCase.put("makeTypeG", "");
			userDefinedSearchCase.put("makeTypeI", "");
			
		}else{	
			if(notEmpty(makeTypeL)){
				userDefinedSearchCase.put("makeTypeL_NOT", "G,I");
				userDefinedSearchCase.put("makeTypeG", "");
				userDefinedSearchCase.put("makeTypeI", "");				
			}else{
				userDefinedSearchCase.put("makeTypeL_NOT", "");
				userDefinedSearchCase.put("makeTypeL_IN", "");
				userDefinedSearchCase.put("makeTypeG", makeTypeG);			
				userDefinedSearchCase.put("makeTypeI", makeTypeI);					
			}		
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
		ArrayList<HashMap<String, String>> hashmap = 
				baseQuery.getYsFullData(sql);	
		ArrayList<Float> list = setStockinCountData(hashmap);
		modelMap.put("baozhuang",list.get(0));	
		modelMap.put("zhuangpei",list.get(1));	
		modelMap.put("zongji",list.get(2));	
		return modelMap;		

	}
	public HashMap<String, Object> doOrderSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_PRODUCTSTORAGE,data,session);
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

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");	
		
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
	
	public void materialStockinAddInit() throws Exception {

		model.addAttribute("packagingList",
				util.getListOption(DicUtil.DIC_PACKAGING, ""));
	
	}
	
	public String addInit() throws Exception {
		String viewFlag="查看";
		String contractId = request.getParameter("contractId");
		String arrivalId = request.getParameter("arrivalId");
		//String receiptId = request.getParameter("receiptId");
		
		boolean flag = checkStockInDataById(contractId);
		//确认是否已经入库
		if(flag){
			viewFlag = "新增";//有未入库的数据
			model.addAttribute("arrivalId",arrivalId);
		}else{
			model.addAttribute("addFlag","false");//没有需要入库的数据
			
		}
		
		//合同信息
		getContractDetail(contractId);
		//取得该到货编号下的物料信息
		getReceivInspectionById(arrivalId);

		model.addAttribute("packagingList",util.getListOption(DicUtil.DIC_PACKAGING, ""));
	
		return viewFlag;
	
	}
	
	public void receiptListPrint() throws Exception {

		String contractIds = request.getParameter("contractIds");
		
		//B_PurchaseStockInData  stock = checkStockInExsit(contractId);
				
		//合同信息列表
		getContractList(contractIds);
	
	}

	public void addProductInit() throws Exception {
		
		String YSId = request.getParameter("YSId");		
				
		//取得订单信息
		getOrderDetail(YSId);

		model.addAttribute("packagingList",util.getListOption(DicUtil.DIC_PACKAGING, ""));
				
	}
	
	public void edit() throws Exception {
		String contractId = request.getParameter("contractId");
		String receiptId = request.getParameter("receiptId");
		
		getContractDetail(contractId);//合同信息
		getArrivaRecord(contractId,receiptId);//入库明细

		model.addAttribute("packagingList",util.getListOption(DicUtil.DIC_PACKAGING, ""));
		model.addAttribute("receiptId",receiptId);//已入库
	
	}

	
	public void editProduct() throws Exception {
		String YSId = request.getParameter("YSId");
		String receiptId = request.getParameter("receiptId");
		
		//取得订单信息
		getOrderDetail(YSId);
		getArrivaRecord("",receiptId);//入库明细

		model.addAttribute("packagingList",util.getListOption(DicUtil.DIC_PACKAGING, ""));
		model.addAttribute("receiptId",receiptId);//已入库
	
	}

	public void printReceipt() throws Exception {
		String contractId = request.getParameter("contractId");
		String receiptId = request.getParameter("receiptId");
		
		//取得订单信息
		getContractDetail(contractId);//合同信息
		getArrivaRecord(contractId,receiptId);//入库明细	
	}


	public void showHistory() throws Exception {
		String contractId = request.getParameter("contractId");
		String arrivalId = request.getParameter("arrivalId");
		model.addAttribute("arrivalId",arrivalId);//已入库
		
		getContractDetail(contractId);//合同信息
	}
	
	public void printProductReceipt() throws Exception {
		String YSId = request.getParameter("YSId");
		
		//取得订单信息
		getOrderDetail(YSId);	
	}
	
	public HashMap<String, Object> getStockInDetail() throws Exception {

		String contractId = request.getParameter("contractId");
		
		return getArrivaRecord(contractId,"");
	}

	public HashMap<String, Object> getProductStockInDetail() {

		String YSId = request.getParameter("YSId");
		String materialId = request.getParameter("materialId");
		return getOrderAndStockDetail(YSId,materialId);
	}
	public void insertAndReturn() throws Exception {

		String contractId = insertStorage();

		getContractDetail(contractId);
		
		boolean flag = checkStockInDataById(contractId);
		//确认是否已经入库
		if(!flag){
			model.addAttribute("addFlag","false");//没有需要入库的数据
		}
			
		model.addAttribute("receiptId",reqModel.getStock().getReceiptid());//返回到页面
	}
	
	public void insertProductAndReturn() throws Exception {

		String YSId = insertStorageProduct();

		getOrderDetail(YSId);
		model.addAttribute("receiptId",reqModel.getStock().getReceiptid());//返回到页面
	}
	
	
	public void updateAndReturn() throws Exception {

		String contractId = updateStorage();

		getContractDetail(contractId);
		boolean flag = checkStockInDataById(contractId);
		//确认是否已经入库
		if(!flag){
			model.addAttribute("addFlag","false");//没有需要入库的数据
		}
		
		model.addAttribute("receiptId",reqModel.getStock().getReceiptid());//返回到页面
	}
	

	public void updateProductAndReturn() throws Exception {

		String YSId = updateStorageProduct();

		getOrderDetail(YSId);
		model.addAttribute("receiptId",reqModel.getStock().getReceiptid());//返回到页面
	}
	
	private String updateStorage(){
		String contractId = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_PurchaseStockInData reqData = reqModel.getStock();
			List<B_PurchaseStockInDetailData> reqDataList = 
					reqModel.getStockList();

			//取得入库申请编号
			String receiptid = reqData.getReceiptid();
			contractId = reqData.getContractid();
			
			//料件入库记录
			updatePurchaseStockIn(reqData);
			
			//先删除已经存在的入库明细
			deletePurchaseStockInDetail(receiptid);
			
			//采购入库记录明细
			for(B_PurchaseStockInDetailData data:reqDataList ){
				String quantity = data.getQuantity();
				if(isNullOrEmpty(quantity) || quantity.equals("0"))
					continue;
				
				data.setReceiptid(receiptid);
				insertPurchaseStockInDetail(data);						
			}
			
			//确认合同状态:是否全部入库
			boolean flag = checkPurchaseOrderDetailStatus(reqData.getYsid(),contractId);
			if(flag){			
				////更新合同状态
				updateContractStatus(contractId,Constants.CONTRACT_STS_3);//入库完毕			
			}
			
			
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
		
		return contractId;
	}
	
	private String insertStorage(){
		String contractId = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_PurchaseStockInData reqData = reqModel.getStock();
			List<B_PurchaseStockInDetailData> reqDataList = 
					reqModel.getStockList();

			//取得入库申请编号
			reqData = getStorageRecordId(reqData);			
			String receiptid = reqData.getReceiptid();
			contractId = reqData.getContractid();
			
			//料件入库记录
			reqData.setStockintype(Constants.STOCKINTYPE_2);
			insertPurchaseStockIn(reqData);
			
			//采购入库记录明细
			for(B_PurchaseStockInDetailData data:reqDataList ){
				String quantity = data.getQuantity();
				String materialid= data.getMaterialid();
				if(quantity == null || quantity.equals("") || quantity.equals("0"))
					continue;
				
				data.setReceiptid(receiptid);
				insertPurchaseStockInDetail(data);
									
				updateMaterial(materialid,quantity,data.getPrice());//更新库存
				
				//更新合同的累计入库数量,收货状态
				updateContractStorage(reqData.getContractid(),materialid,quantity);					
						
			}			

			//确认合同状态:是否全部入库
			boolean flag = checkPurchaseOrderDetailStatus(reqData.getYsid(),contractId);
			if(flag){			
				////更新合同状态
				updateContractStatus(contractId,Constants.CONTRACT_STS_3);//入库完毕			
			}
			
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
		
		return contractId;
	}
	

	private String insertStorageProduct(){
		String ysid = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_PurchaseStockInData reqData = reqModel.getStock();
			List<B_PurchaseStockInDetailData> reqDataList = 
					reqModel.getStockList();

			//取得入库申请编号
			reqData = getStorageRecordId(reqData);
			String receiptid = reqData.getReceiptid();
			ysid = reqData.getYsid();
			
			//成品入库记录
			reqData.setStockintype(Constants.STOCKINTYPE_3);
			insertPurchaseStockIn(reqData);
			
			//采购入库记录明细
			for(B_PurchaseStockInDetailData data:reqDataList ){
				String quantity = data.getQuantity();
				String materialid= data.getMaterialid();
				if(quantity == null || quantity.equals("") || quantity.equals("0"))
					continue;
				
				data.setReceiptid(receiptid);
				insertPurchaseStockInDetail(data);
							
				updateProductStock(materialid,quantity,"0");//更新库存
				
				//更新订单的累计完成数量,累计件数
				updateOrderDetail(data,ysid,"0","0");					
							
			}			

			
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
		
		return ysid;
	}
	

	private String updateStorageProduct(){
		String ysid = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_PurchaseStockInData reqData = reqModel.getStock();
			List<B_PurchaseStockInDetailData> reqDataList = 
					reqModel.getStockList();

			//取得入库申请编号
			String receiptid = reqData.getReceiptid();
			ysid = reqData.getYsid();
			
			//采购入库记录
			updatePurchaseStockIn(reqData);
			
			
			//采购入库记录明细
			for(B_PurchaseStockInDetailData data:reqDataList ){
				String quantity = data.getQuantity();
				String materialid= data.getMaterialid();
				if(quantity == null || quantity.equals("") || quantity.equals("0"))
					continue;
				
				deletePurchaseStockInDetail(receiptid);

				data.setReceiptid(receiptid);
				insertPurchaseStockInDetail(data);

				String oldQuantity = reqModel.getOldQuantity();
				String oldNumber = reqModel.getOldPackagNumber();
				updateProductStock(materialid,quantity,oldQuantity);//更新库存
				
				//更新订单的累计完成数量,累计件数
				updateOrderDetail(data,ysid,oldQuantity,oldNumber);					
							
			}			

			
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
		
		return ysid;
	}
	
	//更新当前库存:成品入库时，减少“待入库”，增加“当前库存”
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
		
		//当前库存数量
		float iQuantity = stringToFloat(data.getQuantityonhand());
		float ireqQuantity = stringToFloat(reqQuantity);	
		float ireqPrice = stringToFloat(reqPrice);
		float iNewQuantiy = iQuantity + ireqQuantity;		
		
		//待入库数量
		float istockin = stringToFloat(data.getWaitstockin());		
		float iNewStockIn = istockin - ireqQuantity;
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float waitstockout = stringToFloat(data.getWaitstockout());//待出库	
		float availabeltopromise = iNewQuantiy + iNewStockIn - waitstockout;		
		
		//计算移动平均单价
		float fMAPrice = stringToFloat(data.getMaprice());//移动平均单价
		
		if(fMAPrice <= 0)
			fMAPrice = ireqPrice;//采用当前合同的单价
		
		float fnewPrice = 
				(iQuantity * fMAPrice + ireqQuantity * ireqPrice) / iNewQuantiy;	
		
		data.setQuantityonhand(floatToString(iNewQuantiy));
		data.setWaitstockin(floatToString(iNewStockIn));
		data.setAvailabeltopromise(floatToString(availabeltopromise));
		data.setMaprice(floatToString(fnewPrice));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
		return data;
		
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
	
	
	
	//更新合同的累计入库数量
	@SuppressWarnings("unchecked")
	private void updateContractStorage(
			String contractId,
			String materialId,
			String quantity) throws Exception{
		
		String where = "contractId ='"+ contractId + 
				"' AND materialId ='"+ materialId + 
				"' AND deleteFlag='0' ";
		
		List<B_PurchaseOrderDetailData> list = 
				(List<B_PurchaseOrderDetailData>) 
				new B_PurchaseOrderDetailDao().Find(where);
		
		if(list ==null || list.size() == 0)
			return ;		
		B_PurchaseOrderDetailData data = list.get(0);
		//String ysid = data.getYsid();
		String SContrat = list.get(0).getQuantity();//合同数量
		String SQuantyDB = list.get(0).getContractstorage();//累计入库
		float iContrat = stringToFloat(SContrat);//合同数量
		float iQuantity = stringToFloat(SQuantyDB);//累计入库
		float inewQuantity = stringToFloat(quantity);//本次入库
		
		float iNew = iQuantity + inewQuantity;
		if(iNew >= iContrat){
			data.setStatus(Constants.CONTRACT_STS_3);//入库完毕		
		}else{
			data.setStatus(Constants.CONTRACT_STS_2);//收货中			
		}
		
		//更新DB
		data.setContractstorage(String.valueOf(iNew));
		updateContractDetailStatus(data);		
		
	}
		
	@SuppressWarnings("unchecked")
	private boolean  checkPurchaseOrderDetailStatus(
			String ysid,String contractId) throws Exception{
		String where = "YSId = '" + ysid  +"'"
				+ " AND contractId = '" + contractId  +"' "
				+ " AND status <> '" + Constants.CONTRACT_STS_3  +"' "
				+ " AND deleteFlag = '0' ";
		List<B_PurchaseOrderDetailData> list  = new B_PurchaseOrderDetailDao().Find(where);
		if(list ==null || list.size() == 0){
			return true;	
		}else{
			return false;	
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private B_PurchaseStockInData  checkStockInExsit(
			String contractId) throws Exception{

		String where =  " contractId = '" + contractId  +"' "
				+ " AND deleteFlag = '0' ";
		List<B_PurchaseStockInData> list  = new B_PurchaseStockInDao().Find(where);
		if(list ==null || list.size() == 0){
			return null;	
		}else{
			return list.get(0);	
		}		
	}
	
	@SuppressWarnings("unchecked")
	private void updateOrderDetail(
			B_PurchaseStockInDetailData stock,
			String ysid,
			String oldQuantity,
			String oldNumber) throws Exception{
		String where = "YSId = '" + ysid  +"' AND deleteFlag = '0' ";
		List<B_OrderDetailData> list  = new B_OrderDetailDao().Find(where);
		if(list ==null || list.size() == 0)
			return ;	
		
		//更新DB
		B_OrderDetailData data = list.get(0);
	
		float orderQuan = stringToFloat(data.getTotalquantity());//生产数量
		float quantity = stringToFloat(data.getCompletedquantity());//已完成数量
		int number = stringToInteger(data.getCompletednumber());//已完成件数
		
		float thisQuan = stringToFloat(stock.getQuantity());//本次入库数
		int thisNum = stringToInteger(stock.getPackagnumber());//本次入库件数
		
		float oldQuan = stringToFloat(oldQuantity);//本次入库数
		int oldNum = stringToInteger(oldNumber);//本次入库件数
		
		float totalQuan = quantity+thisQuan - oldQuan;//累计完成数量
		int totalNum = number+thisNum - oldNum;//累计完成件数
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
	
		//if(orderQuan == totalQuan){
			data.setStoragedate(CalendarUtil.fmtYmdDate());
			data.setStatus(Constants.ORDER_STS_4);//已入库
		//}else{
			//data.setStatus(Constants.ORDER_STS_41);//入库中			
		//}
		
		data.setCompletedquantity(String.valueOf(totalQuan));
		data.setCompletednumber(String.valueOf(totalNum));
		data.setStoragedate(CalendarUtil.fmtYmdDate());//入库时间
		new B_OrderDetailDao().Store(data);
	}
	
	private void insertPurchaseStockIn(
			B_PurchaseStockInData stock) throws Exception {

		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseStockInInsert",userInfo);
		copyProperties(stock,commData);
		stock.setKeepuser(userInfo.getUserId());//默认为登陆者
		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		stock.setRemarks(replaceTextArea(stock.getRemarks()));
		
		dao.Create(stock);
	}
	
	private void updatePurchaseStockIn(
			B_PurchaseStockInData stock) throws Exception {

		//删除旧数据,防止数据重复
		B_PurchaseStockInData db = new B_PurchaseStockInDao(stock).beanData;
		
		if(db == null || ("").equals(db)){
			stock.setStockintype(Constants.STOCKINTYPE_2);
			insertPurchaseStockIn(stock);
		}

		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(db,commData);
		db.setPackagnumber(stock.getPackagnumber());
				
		dao.Store(db);
	}
	
	private void insertPurchaseStockInDetail(
			B_PurchaseStockInDetailData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseStockInDetailInsert",userInfo);
		copyProperties(stock,commData);

		String guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		
		detaildao.Create(stock);	
	}
	
	
	@SuppressWarnings("unchecked")
	private void deletePurchaseStockInDetail(
			String receiptid) throws Exception {
		
		//删除处理
		String astr_Where = "receiptid='"+ receiptid +"'";
		List<B_PurchaseStockInDetailData> list = detaildao.Find(astr_Where);

		for(B_PurchaseStockInDetailData stock:list){

			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"PurchaseStockInDetailInsert",userInfo);
			copyProperties(stock,commData);
			
			detaildao.Store(stock);
		}	
	}
	
	
	private HashMap<String, Object> getArrivaRecord(
			String contractId,String receiptId) throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		dataModel.setQueryName("getPurchaseStockInById");
		userDefinedSearchCase.put("contractId", contractId);
		userDefinedSearchCase.put("receiptId", receiptId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() > 0 ){
			modelMap.put("data", dataModel.getYsViewData());
			model.addAttribute("head",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());			
		}	
		return modelMap;
	}
	

	private void getStockinDetail(
			String receiptId) throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		dataModel.setQueryName("materialStockInList");
		userDefinedSearchCase.put("receiptId", receiptId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() > 0 ){
			model.addAttribute("storage", dataModel.getYsViewData().get(0));		
		}	
		
	}
	public void getStockinDetailByMaterialId() throws Exception{

		String materialId = request.getParameter("materialId");
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		dataModel.setQueryName("materialStockInList");
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() > 0 ){
			model.addAttribute("material", dataModel.getYsViewData().get(0));
			model.addAttribute("stockinList", dataModel.getYsViewData());
		}
		
	}
	
	private boolean checkStockInDataById(
			String contractId) throws Exception{
		
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		dataModel.setQueryName("stockInListBymaterialId");
		userDefinedSearchCase.put("contractId", contractId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() > 0){
			return true;
		}else{
			return false;
		}
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
	
	public void doDelete(String recordId) throws Exception{
		
		B_ArrivalData data = new B_ArrivalData();	
															
		try {
			
			ts = new BaseTransaction();										
			ts.begin();									
			
			String removeData[] = recordId.split(",");									
			for (String key:removeData) {									
												
				data.setRecordid(key);							
				dao.Remove(data);	
				
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
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
	
	public void getContractDetail(String contractId) throws Exception {
		
		dataModel.setQueryName("getContractById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contractId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));	
		}		
	}	
	
	public void getMaterialDetail(String recordid) throws Exception {
		
		B_MaterialData mate = new B_MaterialData();
		mate.setRecordid(recordid);
		
		mate = new B_MaterialDao(mate).beanData;
		
		if(!(mate == null))
			mate.setUnit(DicUtil.getCodeValue("计量单位" + mate.getUnit()));
		
		reqModel.setMaterial(mate);
	}	
	
	public void getContractList(String contractIds) throws Exception {
		
		dataModel.setQueryName("getContractListAndGroupContractId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractIds", contractIds);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));
			model.addAttribute("contractList",dataModel.getYsViewData());
		}		
	}
	
	private void getReceivInspectionById(String arrivalId) throws Exception {
		
		dataModel.setQueryName("receiveInspectionToStockinById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("arrivalId", arrivalId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("head",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());			
		}		
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
	
	public HashMap<String, Object> uploadPhotoAndReload(
			MultipartFile[] headPhotoFile,
			String folderName,String fileList,String fileCount) throws Exception {

		B_PurchaseStockInData reqDt = reqModel.getStock();
		String YSId = reqDt.getYsid();
		String supplierId = reqDt.getSupplierid();
		String contractId = reqDt.getContractid();
		
		String viewPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_GODOWNENTRYVIEW)
				+"/"+supplierId+"/"+contractId;	

		String savePath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_GODOWNENTRYFILE)
				+"/"+supplierId+"/"+contractId;
		
		String webPath = BusinessConstants.PATH_GODOWNENTRYVIEW
				+supplierId+"/"+contractId+"/";	
		
		String photoName  = YSId + "-" + supplierId+ "-" + contractId + "-" + CalendarUtil.timeStempDate(); 
		
		uploadPhoto(headPhotoFile,photoName,viewPath,savePath,webPath);		

		ArrayList<String> list = getFiles(savePath,webPath);
		modelMap.put(fileList, list);
		modelMap.put(fileCount, list.size());
		
		return modelMap;
	}
	
	
	
	private void getPhoto(
			String supplierId,String contractId,
			String folderName,String fileList,String fileCount) {

		
		String savePath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_GODOWNENTRYFILE)
				+"/"+supplierId+"/"+contractId;	
		String viewPath = BusinessConstants.PATH_GODOWNENTRYVIEW
				+supplierId+"/"+contractId+"/";	
		
		try {

			ArrayList<String> list = getFiles(savePath,viewPath);
			modelMap.put(fileList, list);
			modelMap.put(fileCount, list.size());
							
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}
	}
	
	
	
	public HashMap<String, Object> getProductPhoto() throws Exception {
		
		String supplierId = request.getParameter("supplierId");
		String contractId = request.getParameter("contractId");
		
		getPhoto(supplierId,contractId,"product","productFileList","productFileCount");
		
		return modelMap;
	}
	

	public HashMap<String, Object> deletePhotoAndReload(
			String folderName,String fileList,String fileCount) throws Exception {

		String path = request.getParameter("path");
		
		deletePhoto(path);

		B_PurchaseStockInData reqDt = reqModel.getStock();
		String supplierId = reqDt.getSupplierid();
		String contractId = reqDt.getContractid();
		
		getPhoto(supplierId,contractId,folderName,fileList,fileCount);
		
		return modelMap;
	}
	
	public void stockinDownloadExcelForfinance() throws Exception{
		
		//设置响应头，控制浏览器下载该文件
				
		//baseBom数据取得
		List<Map<Integer, Object>>  datalist = getStockinList();		
		
		String fileName = CalendarUtil.timeStempDate()+".xls";
		String dest = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)
				+"/"+File.separator+fileName;
       
		String tempFilePath = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_EXCELTEMPLATE)+File.separator+"stockin.xls";
        File file = new File(dest);
       
        OutputStream out = new FileOutputStream(file);         
        ExcelUtil excel = new ExcelUtil(response);


        //读取模板
        Workbook wbModule = excel.getTempWorkbook(tempFilePath);
        //数据填充的sheet
        int sheetNo=0;
        //title
        Map<String, Object> dataMap = new HashMap<String, Object>();
        wbModule = excel.writeData(wbModule, dataMap, sheetNo);        
        //detail
        //必须为列表头部所有位置集合,输出 数据单元格样式和头部单元格样式保持一致
		String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "stockinForFinanceExcel");
		String[] heads = title.split(",",-1);
        excel.writeDateList(wbModule,heads,datalist,sheetNo);
         
        //写到输出流并移除资源
        excel.writeAndClose(tempFilePath, out);
        System.out.println("导出成功");
        out.flush();
        out.close();
        
      //***********************Excel下载************************//
        excel.downloadFile(dest,fileName);
	}


	public List<Map<Integer, Object>> getStockinList() throws Exception {
		
		dataModel.setQueryName("getPurchaseStockInById");
		baseQuery = new BaseQuery(request, dataModel);

		String key1 = request.getParameter("key1");
		String key2 = request.getParameter("key2");
		String approvalY = request.getParameter("approvalStatusY");
		String approvalN = request.getParameter("approvalStatusN");
		String makeTypeL = request.getParameter("makeTypeL");
		String makeTypeG = request.getParameter("makeTypeG");

		//查询全部
		if(notEmpty(approvalY) && notEmpty(approvalN)){
			userDefinedSearchCase.put("approvalStatusY", "");
			userDefinedSearchCase.put("approvalStatusN", "");			
		}else{
			userDefinedSearchCase.put("approvalStatusY", approvalY);
			userDefinedSearchCase.put("approvalStatusN", approvalN);
			
		}		
		//查询全部
		if(notEmpty(makeTypeL) && notEmpty(makeTypeG)){
			userDefinedSearchCase.put("makeTypeL", "");
			userDefinedSearchCase.put("makeTypeG", "");
		}else{

			if(notEmpty(makeTypeL)){
				userDefinedSearchCase.put("makeTypeL", makeTypeL);
				userDefinedSearchCase.put("makeTypeG", "");				
			}else{
				userDefinedSearchCase.put("makeTypeL", "");
				userDefinedSearchCase.put("makeTypeG", makeTypeG);	
			}		
		}

		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		List<Map<Integer, Object>> listMap = new ArrayList<Map<Integer, Object>>();
		ArrayList<HashMap<String, String>>  hashMap = baseQuery.getYsFullData();	
		
		for(int i=0;i<hashMap.size();i++){
			String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "stockinForFinanceTitle");
			String[] titles = title.split(",",-1);
			Map<Integer, Object> excel = new HashMap<Integer, Object>();
			for(int j=0;j<titles.length;j++){
				excel.put(j,hashMap.get(i).get(titles[j]));		
			}
			listMap.add(excel);
		}
		
		return  listMap;

	}

	
	private ArrayList<Float> setStockinCountData(ArrayList<HashMap<String, String>> hashmap){
		
		float baozhuang = 0;
		float zhuangpei = 0;
		float zongji = 0;
		
		for(HashMap<String, String> map:hashmap){
			String materialId = map.get("materialId");
			String taxTotal = map.get("taxTotal");

			if(isNullOrEmpty(materialId))
				continue;
			
			zongji += stringToFloat(taxTotal);
			if(("G").equals(materialId.substring(0, 1))){
				
				baozhuang += stringToFloat(taxTotal);				
			}else{
				zhuangpei += stringToFloat(taxTotal);				
			}
		}
		ArrayList<Float> list = new ArrayList<Float>();
		list.add(baozhuang);
		list.add(zhuangpei);
		list.add(zongji);
		
		return list;
	}
	
	public void materialStockinDetailView() throws Exception {

		String receiptId = request.getParameter("receiptId");

		getStockinDetail(receiptId);
	}
	
	public void insertMaterialAndReturn() throws Exception {

		String receiptId = insertStorageMaterial();

		getStockinDetail(receiptId);
	}
	
	private String insertStorageMaterial(){
		String ysid = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_PurchaseStockInData reqData = reqModel.getStock();
			B_PurchaseStockInDetailData detail = reqModel.getStockDetail();

			//取得入库申请编号
			reqData = getStorageRecordId(reqData);
			String receiptid = reqData.getReceiptid();
			ysid = reqData.getYsid();
			
			//直接入库记录
			reqData.setStockintype(Constants.STOCKINTYPE_1);
			insertPurchaseStockIn(reqData);
			
			//入库记录明细			
			detail.setReceiptid(receiptid);
			insertPurchaseStockInDetail(detail);
			
			//更新库存			
			updateProductStock(
					detail.getMaterialid(),
					detail.getQuantity(),
					"0");			
			
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
		
		return ysid;
	}
	
	public void showStockIn() throws Exception {

		String contractId = request.getParameter("contractId");
		
		//合同信息
		getContractDetail(contractId);
	
	}
	

	public void setBeginningInventory() throws Exception {

		String recordId = request.getParameter("recordId");		
		//合同信息
		getMaterialDetail(recordId);
	
	}
	

	public void beginningInventoryAdd() throws Exception {

		ts = new BaseTransaction();		
		
		try {
			ts.begin();

			
			//更新物料信息
			B_MaterialData mate = updateMaterialBeginningInventory();
			
			//保存当前数据
			insertBeginningInventoryHisotry(mate);
			
			
			ts.commit();			
		
		}
		catch(Exception e) {
			e.printStackTrace();		
			ts.rollback();			
		}		
	
	}
	

	public void setQuantityOnHand() throws Exception {

		String recordId = request.getParameter("recordId");		
		//合同信息
		getMaterialDetail(recordId);
	
	}
	

	public void quantityOnHandAdd() throws Exception {

		ts = new BaseTransaction();		
		
		try {
			ts.begin();

			
			//更新物料信息
			B_MaterialData mate = updateMaterialQuantityOnHand();
			
			//保存当前数据
			//insertBeginningInventoryHisotry(mate);
			
			
			ts.commit();			
		
		}
		catch(Exception e) {
			e.printStackTrace();		
			ts.rollback();			
		}		
	
	}

	public void confirmQuantityOnHand() throws Exception {
	
		B_MaterialData mate = new B_MaterialData();
		
		String id = request.getParameter("recordId");
		mate.setRecordid(id);
		
		mate = new B_MaterialDao(mate).beanData;	
		
		if(mate ==null){
			return ;
		}	
		mate.setQuantityeditflag("1");//库存修改已确认
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"confirmQuantityOnHand",userInfo);
		copyProperties(mate,commData);
		new B_MaterialDao().Store(mate);
	
	}
	
	//更新
	private B_MaterialData updateMaterialBeginningInventory() throws Exception{
	
		B_MaterialData mate = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();
		
		B_MaterialData reqMeterial = reqModel.getMaterial();	
		
		mate = new B_MaterialDao(reqMeterial).beanData;	
		
		if(mate ==null){
			return null;
		}
		B_MaterialData rtnVal = new B_MaterialData();
		rtnVal.setQuantityonhand(mate.getQuantityonhand());
		rtnVal.setMaprice(mate.getMaprice());
		

		float iQuantity = stringToFloat(reqMeterial.getBeginninginventory());
		//待入库数量
		float istockin = stringToFloat(mate.getWaitstockin());		
		//float iNewStockIn = istockin - iQuantity;
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float iwaitstockout = stringToFloat(mate.getWaitstockout());//待出库	
		float availabeltopromise = iQuantity + istockin - iwaitstockout;		
		
		mate.setQuantityonhand(reqMeterial.getBeginninginventory());
		mate.setAvailabeltopromise(floatToString(availabeltopromise));
		mate.setMaprice(reqMeterial.getBeginningprice());
		mate.setBeginninginventory(reqMeterial.getBeginninginventory());
		mate.setBeginningprice(reqMeterial.getBeginningprice());		
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"updateMaterialBeginningInventory",userInfo);
		copyProperties(mate,commData);
		
		dao.Store(mate);
		
		return rtnVal;
		
	}
	
	//更新
	private void insertBeginningInventoryHisotry(
			B_MaterialData material) throws Exception{
	
		B_BeginningInventoryHistoryData inventory = new B_BeginningInventoryHistoryData();
		B_BeginningInventoryHistoryDao dao = new B_BeginningInventoryHistoryDao();

		B_MaterialData reqMeterial = reqModel.getMaterial();
		//
		inventory.setMaterialid(reqMeterial.getMaterialid());
		inventory.setBeginninginventory(reqMeterial.getBeginninginventory());
		inventory.setBeginningprice(reqMeterial.getBeginningprice());
		inventory.setOrigininventory(material.getQuantityonhand());
		inventory.setOriginprice(material.getMaprice());
		
		//新增DB
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"BeginningInventoryInsert",userInfo);
		copyProperties(inventory,commData);
		String guid = BaseDAO.getGuId();
		inventory.setRecordid(guid);
		
		dao.Create(inventory);
		
		
	}
	
	//更新
	private B_MaterialData updateMaterialQuantityOnHand() throws Exception{
	
		B_MaterialData mate = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();
		
		B_MaterialData reqMeterial = reqModel.getMaterial();	
		
		mate = new B_MaterialDao(reqMeterial).beanData;	
		
		if(mate ==null){
			return null;
		}
		B_MaterialData rtnVal = new B_MaterialData();
		rtnVal.setQuantityonhand(reqMeterial.getQuantityonhand());

		float iQuantity = stringToFloat(reqMeterial.getQuantityonhand());
		
		float istockin = stringToFloat(mate.getWaitstockin());	//待入库
		float istockout = stringToFloat(mate.getWaitstockout());//待出库	
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float availabeltopromise = iQuantity + istockin - istockout;		
		
		mate.setQuantityonhand(reqMeterial.getQuantityonhand());
		mate.setAvailabeltopromise(floatToString(availabeltopromise));	
		mate.setQuantityeditflag(reqMeterial.getQuantityeditflag());//库存修改标识
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"updateMaterialQuantityOnHand",userInfo);
		copyProperties(mate,commData);
		
		dao.Store(mate);
		
		return rtnVal;
		
	}
}
