package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.ys.business.action.model.order.StorageModel;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchaseStockInDao;
import com.ys.business.db.dao.B_PurchaseStockInDetailDao;
import com.ys.business.db.data.B_ArrivalData;
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
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class StorageService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_PurchaseStockInDao dao;
	private B_PurchaseStockInDetailDao detaildao;
	private StorageModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public StorageService(){
		
	}

	public StorageService(Model model,
			HttpServletRequest request,
			HttpSession session,
			StorageModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_PurchaseStockInDao();
		this.detaildao = new B_PurchaseStockInDetailDao();
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
	
	public HashMap<String, Object> doSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_MATERIALSTORAGE,data,session);
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
	
	public boolean addInit() throws Exception {
		boolean viewFlag=true;
		String contractId = request.getParameter("contractId");
		String arrivalId = request.getParameter("arrivalId");
		//String receiptId = request.getParameter("receiptId");
		
		boolean flag = checkStockInDataById(contractId);
		//确认是否已经入库
		if(flag){
			viewFlag = false;//有未入库的数据
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
	
	public void showStockIn() throws Exception {

		String contractId = request.getParameter("contractId");
		String arrivalId = request.getParameter("arrivalId");
		
		B_PurchaseStockInData  stock = checkStockInExsit(contractId);
		
		if(stock !=null)
			model.addAttribute("receiptId",stock.getReceiptid());//已入库
		
		//合同信息
		getContractDetail(contractId);
		//取得该到货编号下的物料信息
		getReceivInspectionById(arrivalId);

		model.addAttribute("packagingList",util.getListOption(DicUtil.DIC_PACKAGING, ""));
	
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
			
			//采购入库记录
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
			
			//采购入库记录
			insertPurchaseStockIn(reqData);
			
			//采购入库记录明细
			for(B_PurchaseStockInDetailData data:reqDataList ){
				String quantity = data.getQuantity();
				String materialid= data.getMaterialid();
				if(quantity == null || quantity.equals("") || quantity.equals("0"))
					continue;
				
				data.setReceiptid(receiptid);
				insertPurchaseStockInDetail(data);
									
				updateMaterial(materialid,quantity);//更新库存
				
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
			
			//采购入库记录
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
	private void updateMaterial(
			String materialId,
			String reqQuantity) throws Exception{
	
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
		float iNewQuantiy = iQuantity + ireqQuantity;		
		
		//待入库数量
		float istockin = stringToFloat(data.getWaitstockin());		
		float iNewStockIn = istockin - ireqQuantity;
		
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
	
		if(orderQuan == totalQuan){
			data.setStoragedate(CalendarUtil.fmtYmdDate());
			data.setStatus(Constants.ORDER_STS_4);//已入库
		}else{
			data.setStatus(Constants.ORDER_STS_3);//待交货			
		}
		
		data.setCompletedquantity(String.valueOf(totalQuan));
		data.setCompletednumber(String.valueOf(totalNum));
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
		
		dao.Create(stock);
	}
	
	private void updatePurchaseStockIn(
			B_PurchaseStockInData stock) throws Exception {

		//删除旧数据,防止数据重复
		B_PurchaseStockInData db = new B_PurchaseStockInDao(stock).beanData;
		
		if(db == null || ("").equals(db))
			insertPurchaseStockIn(stock);

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
	
	private boolean checkStockInDataById(
			String contractId) throws Exception{
		
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		dataModel.setQueryName("checkStockInDataById");
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
			//model.addAttribute("material",dataModel.getYsViewData());			
		}		
	}	
	

	private void getReceivInspectionById(String arrivalId) throws Exception {
		
		dataModel.setQueryName("getMaterialCheckInList");		
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
	


}
