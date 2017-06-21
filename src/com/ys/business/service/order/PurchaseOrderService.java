package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.PurchaseOrderModel;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDao;
import com.ys.business.db.data.B_MaterialRequirmentData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchasePlanData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.business.service.order.CommonService;

@Service
public class PurchaseOrderService extends CommonService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_PurchaseOrderDao orderDao;
	private B_PurchaseOrderDetailDao detailDao;
	private PurchaseOrderModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	HttpSession session;

	public PurchaseOrderService(){
		
	}

	public PurchaseOrderService(Model model,
			HttpServletRequest request,
			HttpSession session,
			PurchaseOrderModel reqModel,
			UserInfo userInfo){
		
		this.orderDao = new B_PurchaseOrderDao();
		this.detailDao = new B_PurchaseOrderDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.session = session;
		this.userInfo = userInfo;
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		this.dataModel.setQueryFileName("/business/order/purchasequerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	
	public PurchaseOrderService(Model model,
			HttpServletRequest request,
			HttpSession session,
			UserInfo userInfo){
		
		this.orderDao = new B_PurchaseOrderDao();
		this.detailDao = new B_PurchaseOrderDetailDao();
		this.model = model;
		this.request = request;
		this.session = session;
		this.userInfo = userInfo;
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		this.dataModel.setQueryFileName("/business/order/purchasequerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}

	public HashMap<String, Object> getContractList(String data) throws Exception {
		
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
	
		dataModel.setQueryName("getContractList");
		
		baseQuery = new BaseQuery(request, dataModel);		
		
		String[] keyArr = getSearchKey(Constants.FORM_CONTRACT,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
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
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getContractList2(String data) throws Exception {
		
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
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();
	
		dataModel.setQueryName("getContractList");
		
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
		
		model.addAttribute("contract",dataModel.getYsViewData());
		return modelMap;
	}

	public void getZZOrderDetail( 
			String YSId) throws Exception {

		dataModel.setQueryName("getRequirementListByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
			
		model.addAttribute("order",dataModel.getYsViewData().get(0));		
		model.addAttribute("detail", dataModel.getYsViewData());
		
	}

	public  ArrayList<HashMap<String, String>> getRequirementBySupplierId( 
			String YSId,String supplierId) throws Exception {

		dataModel.setQueryName("getRequirementBySupplierId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("supplierId", supplierId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
				
		return baseQuery.getYsQueryData(0, 0);
		
				
	}
	
	public ArrayList<HashMap<String, String>> getSupplierList(String YSId) throws Exception {
		
		ArrayList<HashMap<String, String>> supplierList = null;
		
		dataModel.setQueryName("getContractSupplierList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		supplierList = baseQuery.getYsQueryData(0, 0);
				
		return supplierList;	
		
	}
	
	@SuppressWarnings("unchecked")
	public List<B_PurchaseOrderData> getSupplierContrct(String YSId) throws Exception {
			
		String astr_Where = "YSId = '"+YSId+"'";
		return (List<B_PurchaseOrderData>)orderDao.Find(astr_Where);
	}
	
	
	/**
	 * 采购合同明细做成
	 * @param YSId
	 * @param supplierId
	 * @param contractId
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void insertPurchaseOrderDetail(
			String YSId,
			String supplierId,
			String contractId) throws Exception{
		
		List<B_PurchasePlanData> dbList = new ArrayList<B_PurchasePlanData>();
		B_PurchasePlanDao plan = new B_PurchasePlanDao();
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();

		String where = " YSId= '" + YSId +"'AND supplierId= '" + supplierId +"' AND deleteFlag='0' ";
		

		dbList = (List<B_PurchasePlanData>)plan.Find(where);
	
		if(dbList != null && dbList.size() >0){
			
			for(B_PurchasePlanData dt:dbList){
				
				float quantity = stringToFloat(dt.getQuantity());
				//采购数量为零的物料不生成采购合同
				if (quantity == 0)
					continue;
				
				B_PurchaseOrderDetailData d = new B_PurchaseOrderDetailData();

				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"PurchaseOrderInsert",userInfo);
				copyProperties(d,commData);
				
				guid = BaseDAO.getGuId();
				d.setRecordid(guid);
				
				d.setYsid(YSId);
				d.setContractid(contractId);
				d.setMaterialid(dt.getMaterialid());
				d.setQuantity(dt.getQuantity());
				d.setPrice(dt.getPrice());					
				d.setTotalprice(dt.getTotalprice());
				
				dao.Create(d);					
			}
		}		
		
	}
	

	/**
	 * 取得合同详情
	 */
	public void getContractDetailList(String contractId) throws Exception {

		if(null == contractId || ("").equals(contractId))
			return;
	
		dataModel.setQueryName("getContractDetailList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	 

		model.addAttribute("contract",dataModel.getYsViewData().get(0));		
		model.addAttribute("detail", dataModel.getYsViewData());
		
	}	
	
	/**
	 * 1.生成采购合同(一条数据)
	 * 2.合同明细(N条数据)
	 */
	public void insert(String YSId) throws Exception {

		ts = new BaseTransaction();

		try {
			ts.begin();

			String materialId = request.getParameter("materialId");
			System.out.println("end purchaseOrderCreate:"+YSId+"时间:"+CalendarUtil.getSystemDate());

			//以采购方案里的供应商为单位集计
			ArrayList<HashMap<String, String>> supplierList = getSupplierList(YSId);
					
			if(supplierList == null || supplierList.size() <= 0)
				return;
				
			//取得合同里的供应商
			//List<B_PurchaseOrderData> supplierContrct = getSupplierContrct(YSId);

			//删除既存合同信息
			deleteContract(YSId);
			
			//删除既存合同明细
			deleteContractDetail(YSId);
			
			
			for(int i=0;i<supplierList.size();i++){
				
				String supplierId = supplierList.get(i).get("supplierId");
				String shortName  = supplierList.get(i).get("shortName");
				String total      = supplierList.get(i).get("total");
				
				if(supplierId == null || supplierId.equals("")){
					continue;
				}
				float totalf = stringToFloat(total); 
				if(totalf == 0){//采购数量为零的供应商不计入合同
					continue;
				}
				//取得供应商的合同流水号
				//父编号:年份+供应商简称
				String parentId = "";
				if(null != YSId && YSId.length() > 3 ){
					parentId = YSId.substring(0,2);//耀升编号前两位是年份
				}
				parentId = parentId + shortName;
				
				String subId = getContractCode(parentId);

				//3位流水号格式化	
				//采购合同编号:16YS081-WL002
				String contractId = BusinessService.getContractCode(YSId, shortName, subId);
				
				//新增采购合同
				insertPurchaseOrder(
						YSId,materialId,supplierId,contractId,parentId,subId,String.valueOf(totalf));

				//新增合同明细
				insertPurchaseOrderDetail(YSId,supplierId,contractId);
				
			}
			
			ts.commit();
			System.out.println("end purchaseOrderCreate:"+YSId+"时间:"+CalendarUtil.getSystemDate());
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
	}	

	/*
	 * 更新处理
	 */
	private void updatePurchaseOrder(
			B_PurchaseOrderData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseOrderUpdate",userInfo);
		copyProperties(data,commData);
		
		orderDao.Store(data);	
	}
	
	private void insertPurchaseOrder(
			String YSId,
			String materialId,
			String supplierId,
			String contractId,
			String parentId,
			String subId,
			String total) throws Exception{
		
		B_PurchaseOrderData data = new B_PurchaseOrderData();
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseOrderInsert",userInfo);
		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		
		data.setRecordid(guid);
		data.setYsid(YSId);
		data.setMaterialid(materialId);
		data.setContractid(contractId);
		data.setParentid(parentId);
		data.setSubid(String.valueOf(Integer.parseInt(subId)+1));
		data.setSupplierid(supplierId);
		data.setTotal(total);
		data.setPurchasedate(CalendarUtil.fmtYmdDate());
		data.setDeliverydate(CalendarUtil.dateAddToString(data.getPurchasedate(),20));
		
		orderDao.Create(data);	
	}	
	
	/*
	 * 订单详情插入处理
	 */
	private void insertOrderDetail(
			B_MaterialRequirmentData mData,
			String contractId) throws Exception{
		
		B_PurchaseOrderDetailData data = new B_PurchaseOrderDetailData();
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseOrderInsert",userInfo);

		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setContractid(contractId);
		data.setMaterialid(mData.getMaterialid());
		data.setQuantity(mData.getQuantity());
		data.setPrice(mData.getPrice());
		data.setTotalprice(mData.getTotalprice());
		
		detailDao.Create(data);	
	}	
	
	/*
	 * 1.订单基本信息更新处理(1条数据)
	 * 2.订单详情 新增/更新/删除 处理(N条数据)
	 */
	public void update() throws Exception  {

		ts = new BaseTransaction();
				
		try {
			
			ts.begin();	
			
			//合同基本信息
			B_PurchaseOrderData orderData = reqModel.getContract();
			
			updateOrder(orderData);
			
			//合同详情 更新 处理			
			List<B_PurchaseOrderDetailData> newDetailList = reqModel.getDetailList();
						
			//页面数据的recordId与DB匹配			
			for(B_PurchaseOrderDetailData data:newDetailList ){
				
				//更新处理
				updateOrderDetail(data);						
					
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * 订单基本信息更新处理
	 */
	private void updateOrder(B_PurchaseOrderData order) 
			throws Exception{

		
		orderDao = new B_PurchaseOrderDao(order);
		
		//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseOrderUpdate",userInfo);
		copyProperties(orderDao.beanData,commData);
		
		//获取页面数据
		copyProperties(orderDao.beanData,order);
		
		orderDao.Store(orderDao.beanData);
	
	}

	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail(
			B_PurchaseOrderDetailData data) throws Exception{
			
		detailDao = new B_PurchaseOrderDetailDao(data);

		//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseOrderDetailUpdate",userInfo);
		copyProperties(detailDao.beanData,commData);
		
		//获取页面数据
		copyProperties(detailDao.beanData,data);
		
		detailDao.Store(detailDao.beanData);
	}
	
	/*
	 * 审批处理:常规订单是以耀升编号为单位更新,
	 * 库存订单存在多个相同的耀升编号,需要逐条更新
	 */
	public void updateDetailToApprove(String PIId) throws Exception  {

		ts = new BaseTransaction();
		
		try {
			
			ts.begin();

			//根据画面的PiId取得DB中更新前的订单详情 
			List<B_OrderDetailData> detailList = null;

			//库存订单的PI编号,订单编号,耀升编号相同
			String where = " PIId = '"+PIId +"'" + " AND deleteFlag = '0' ";
			//detailList = getOrderDetailByPIId(where);
						
			for(B_OrderDetailData dbData:detailList ){				
					
				//更新处理//处理共通信息
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"ZZOrderDetailUpdate",userInfo);

				copyProperties(dbData,commData);
				//更新审批状态:下一步是审批
				dbData.setStatus(Constants.ORDER_STS_1);
				
				detailDao.Store(dbData);
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
		
	}

	/**
	 * 合同明细删除处理
	 */
	private void deleteContractDetail(String YSId) {
		
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();

		String astr_Where = " YSId = '" + YSId +"'";	
		try {
			dao.RemoveByWhere(astr_Where);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
	}
	
	/**
	 * 合同删除处理
	 */
	private void deleteContract(String YSId) {
		
		B_PurchaseOrderDao dao = new B_PurchaseOrderDao();

		String astr_Where = " YSId = '" + YSId +"'";	
		try {
			dao.RemoveByWhere(astr_Where);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
	}
	/*
	 * 订单详情删除处理
	 */
	private void updateOrderDetail(List<B_OrderDetailData> oldDetailList) 
			throws Exception{
		
		for(B_OrderDetailData detail:oldDetailList){
			
			if(null != detail){
				
				//处理共通信息
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"ZZOrderDetailDelete",userInfo);

				copyProperties(detail,commData);
				
				detailDao.Store(detail);
			}
		}
	}		
	
	private String getContractCode(String parentId) throws Exception {

		dataModel.setQueryName("getContractCode");
		
		baseQuery = new BaseQuery(request, dataModel);
	
		
		userDefinedSearchCase.put("parentId", parentId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		String code =dataModel.getYsViewData().get(0).get("MaxSubId");
		
		return code;
		
	}	

	//生成采购合同--自制品
	public void creatPurchaseOrderZZ() throws Exception {
		
		String YSId = request.getParameter("YSId");
		//String supplierId = request.getParameter("supplierId");
		
		insert(YSId);
		
		//ArrayList<ArrayList<String>> supplierList = null; 
		
		//生成采购合同		
		//if(null == supplierId){

		//	supplierId = insert(YSId);
			
		//}else{
		//	getSupplierList(YSId);//设置供应商下拉框
		//}
			
		//取得本次采购合同在该供应商购买的材料list
		//String suppShortName = getRequirementBySupplierId(YSId,supplierId);
		
		//getContractCode(YSId,suppShortName);
	}
	
	//生成采购合同--订单采购
	public void creatPurchaseOrder() throws Exception {
		
		String YSId = request.getParameter("YSId");
		//String supplierId = request.getParameter("supplierId");
		
		insert(YSId);
		
	}
	//生成采购合同--自制品
	public HashMap<String, Object> getContractList() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String YSId = request.getParameter("YSId");
	
		dataModel.setQueryName("getContractList");
		
		baseQuery = new BaseQuery(request, dataModel);
	
		userDefinedSearchCase.put("YSId", YSId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	
	public HashMap<String, Object> getContractByYSId() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String YSId = request.getParameter("YSId");
	
		dataModel.setQueryName("getContractByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
	
		userDefinedSearchCase.put("YSId", YSId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	//
	public HashMap<String, Object> getContractDetail() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String contractId = request.getParameter("contractId");
	
		dataModel.setQueryName("getContractDetail");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	//
	public HashMap<String, Object> getContractId() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String contractId = request.getParameter("contractId").trim().toUpperCase();
	
		dataModel.setQueryName("getContractId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	public HashMap<String, Object> getContractByMaterialId() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String contractId = request.getParameter("contractId");
		String materialId = request.getParameter("materialId");
	
		if(!(materialId == null || ("").equals(materialId))){
			materialId = materialId.toUpperCase();
		}
		dataModel.setQueryName("getContractDetail");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		userDefinedSearchCase.put("materialId", materialId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	public void updateAndView() throws Exception {
		
		update();

		String contractId = reqModel.getContract().getContractid();
		
		//getContractBySupplierId(contractId);
		
	}
	
	public void approveAndView() throws Exception {
		
		String YSId = request.getParameter("YSId");
		
		updateDetailToApprove(YSId);
		
		//getZZOrderDetail(YSId);	
		
	}
	
}
