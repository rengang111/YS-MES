package com.ys.business.service.order;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.ys.business.action.model.order.ArrivalModel;
import com.ys.business.action.model.order.StockOutModel;
import com.ys.business.action.model.order.StorageModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_InspectionProcessDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchaseStockInDao;
import com.ys.business.db.dao.B_PurchaseStockInDetailDao;
import com.ys.business.db.dao.B_StockOutDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_InspectionProcessData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_ProductDesignData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;
import com.ys.business.db.data.B_ReceiveInspectionData;
import com.ys.business.db.data.B_StockOutDetailData;
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
public class StockOutService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_StockOutDetailDao dao;
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
		
		this.dao = new B_StockOutDetailDao();
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
		String YSId = request.getParameter("YSId");
		String arrivalId = request.getParameter("arrivalId");
		String receiptId = request.getParameter("receiptId");
		
		//确认是否已经出库
		if(receiptId ==null || receiptId.equals("")){
			viewFlag = false;
			
		}else{
			model.addAttribute("receiptId",receiptId);//已出库
		}
		
		//合同信息
		getOrderDetail(YSId);
		//取得该到货编号下的物料信息
		//getStockOutDetailById(YSId);

		//model.addAttribute("packagingList",util.getListOption(DicUtil.DIC_PACKAGING, ""));
	
		return viewFlag;
	
	}
	

	public void edit() throws Exception {
		B_StockOutDetailData reqData = reqModel.getStockOut();
		
		
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
		getArrivaRecord(receiptId);//入库明细

		model.addAttribute("packagingList",util.getListOption(DicUtil.DIC_PACKAGING, ""));
		model.addAttribute("receiptId",receiptId);//已入库
	
	}

	public void printReceipt() throws Exception {
		String contractId = request.getParameter("contractId");
		String receiptId = request.getParameter("receiptId");
		
		//取得订单信息
		//getContractDetail(contractId);//合同信息
		getArrivaRecord(receiptId);//入库明细	
	}

	public void printProductReceipt() throws Exception {
		String YSId = request.getParameter("YSId");
		
		//取得订单信息
		getOrderDetail(YSId);	
	}
	
	public HashMap<String, Object> getStockInDetail() throws Exception {

		String receiptId = request.getParameter("receiptId");
		return getArrivaRecord(receiptId);
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
		/*
		try {
			ts.begin();
			
			B_PurchaseStockInData reqData = reqModel.getStockOut();


			//取得入库申请编号
			String receiptid = reqData.getReceiptid();
			YSId = reqData.getYsid();
			
			//采购出库记录
			updatePurchaseStockIn(reqData);
			

			
			//确认合同状态:是否全部入库
			boolean flag = checkPurchaseOrderStatus(reqData.getYsid(),contractId);
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
		*/
		return YSId;
	}
	
	private String insertStorage(){
		String YSId = "";
		ts = new BaseTransaction();		
		/*
		try {
			ts.begin();
			
			B_StockOutDetailData reqData = reqModel.getStockOut();

			//取得入库申请编号
			reqData = getStorageRecordId(reqData);			
			String receiptid = reqData.getStockoutid();
	
			//采购入库记录
			insertPurchaseStockIn(reqData);
			
			updateMaterial(materialid,quantity);//更新库存


			//确认合同状态:是否全部出库
			boolean flag = checkPurchaseOrderStatus(reqData.getYsid(),contractId);
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
		*/
		return YSId;
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
	
	//
	@SuppressWarnings("unchecked")
	private void updateInspectionProcess(
			String contractId,
			String materialId) throws Exception{
	
		
		String where = "contractId ='"+ contractId + 
				"' AND materialId ='"+ materialId + 
				"' AND deleteFlag='0' ";
		
		List<B_PurchaseOrderDetailData> list = 
				(List<B_PurchaseOrderDetailData>)
				new B_PurchaseOrderDetailDao().Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		B_PurchaseOrderDetailData data = list.get(0);
		
		//data.setStatus(Constants.ARRIVERECORD_4);//入库完毕
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		
		new B_PurchaseOrderDetailDao().Store(data);
		
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
		String ysid = data.getYsid();
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
	private boolean updatePurchaseOrderStatus(
			String ysid,String contractId,float quantity) throws Exception{
		
		boolean rtnFlag = false;
		String where = "YSId = '" + ysid  +"' AND deleteFlag = '0' ";
		List<B_PurchaseOrderData> list  = new B_PurchaseOrderDao().Find(where);
		if(list ==null || list.size() == 0)
			return rtnFlag;	

		B_PurchaseOrderData data = list.get(0);
		float orderTotal = stringToFloat(data.getOrderquantity());//合同总数
		float storageTotal = stringToFloat(data.getStoragequantity());//入库总数
		
		float newStorage = storageTotal + quantity;
		
		if(newStorage == orderTotal){
			//全部入库,更新状态
			data.setStatus(Constants.CONTRACT_STS_3);//完结
			rtnFlag = true;
		}else{
			data.setStatus(Constants.CONTRACT_STS_2);//执行中
			rtnFlag = false;
		}
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		data.setStoragequantity(String.valueOf(newStorage));
		
		dao.Store(data);
		
		return rtnFlag;
	}
	
	@SuppressWarnings("unchecked")
	private boolean  checkPurchaseOrderStatus(
			String ysid,String contractId) throws Exception{
		String where = "YSId = '" + ysid  +"'"
				+ " AND contractId = '" + contractId  +"' "
				+ " AND status <> '" + Constants.CONTRACT_STS_3  +"' "
				+ " AND deleteFlag = '0' ";
		List<B_PurchaseOrderData> list  = new B_PurchaseOrderDao().Find(where);
		if(list ==null || list.size() == 0){
			return true;	
		}else{
			return false;	
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

		//删除旧数据,防止数据重复
		String receiptId = stock.getReceiptid();
		String where = " receiptId = '"+receiptId+"'";
		try {
			dao.RemoveByWhere(where);
		} catch (Exception e) {
			// nothing
		}		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseStockInInsert",userInfo);
		copyProperties(stock,commData);
		stock.setKeepuser(userInfo.getUserId());//默认为登陆者
		String guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		
		dao.Create(stock);
	}
	
	private void updatePurchaseStockIn(
			B_PurchaseStockInData stock) throws Exception {

		//删除旧数据,防止数据重复
		B_PurchaseStockInData db = new B_PurchaseStockInDao(stock).beanData;
		
		if(db == null || ("").equals(db))
			return;

		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(db,commData);
		db.setPackagnumber(stock.getPackagnumber());
				
		dao.Store(db);
	}
	

	
	private HashMap<String, Object> getArrivaRecord(String receiptId) throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		dataModel.setQueryName("getPurchaseStockInById");
		userDefinedSearchCase.put("receiptId", receiptId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("head",dataModel.getYsViewData().get(0));
		model.addAttribute("material",dataModel.getYsViewData());		
	
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
	

	
	public B_PurchaseStockInData getStorageRecordId(
			B_PurchaseStockInData data) throws Exception {
	
		dataModel.setQueryName("getMAXStorageRecordId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", data.getYsid());
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");	
		
		String inspectionId = 
				BusinessService.getStorageRecordId(
						data.getYsid(),
						code,
						false);	
		
		data.setReceiptid(inspectionId);
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
	

}
