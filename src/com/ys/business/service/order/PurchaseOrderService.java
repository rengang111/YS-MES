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
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.dao.B_WorkshopReturnDao;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.db.data.B_WorkshopReturnData;
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

	public HashMap<String, Object> getContractList(
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
	
		dataModel.setQueryName("getContractList");
		
		baseQuery = new BaseQuery(request, dataModel);		
		
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
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
	
	
	
	public void getWorkshopReturnList(
			String YSId,String contractId) throws Exception {
				
		dataModel.setQueryName("getWorkshopReturnList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("contractId", contractId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();					
		model.addAttribute("workshop",dataModel.getYsViewData());
	}
	
	public void getWorkshopReturnList(String workshopReturnId) throws Exception {
				
		dataModel.setQueryName("getWorkshopReturnList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("workshopReturnId", workshopReturnId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();					
		model.addAttribute("workshop",dataModel.getYsViewData().get(0));
		model.addAttribute("workshopDetail",dataModel.getYsViewData());
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
		
		List<B_PurchasePlanDetailData> dbList = new ArrayList<B_PurchasePlanDetailData>();
		B_PurchasePlanDetailDao plan = new B_PurchasePlanDetailDao();
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();

		String where = " YSId= '" + YSId +"'AND supplierId= '" + supplierId +"' AND deleteFlag='0' ";	

		dbList = (List<B_PurchasePlanDetailData>)plan.Find(where);
	
		if(dbList != null && dbList.size() >0){
			
			for(B_PurchasePlanDetailData dt:dbList){
				
				float quantity = stringToFloat(dt.getPurchasequantity());
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
				d.setQuantity(dt.getPurchasequantity());
				d.setPrice(dt.getPrice());					
				d.setTotalprice(dt.getTotalprice());
				d.setVersion(1);//默认为1
				
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
	 * 
	 */
	public void getMaterialDetailList(String supplierId,
			String materialId) throws Exception {

		//if(null == supplierId || ("").equals(supplierId))
		//	return;
	
		dataModel.setQueryName("getMaterialForPurchase");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", supplierId);
		userDefinedSearchCase.put("contractId", materialId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	 

		if(dataModel.getRecordCount() > 0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));		
			model.addAttribute("detail", dataModel.getYsViewData());
		}
		
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

			//以采购方案里的供应商为单位集计
			ArrayList<HashMap<String, String>> supplierList = getSupplierList(YSId);
					
			if(supplierList == null || supplierList.size() <= 0)
				return;
				
			//取得合同里的供应商
			//List<B_PurchaseOrderData> supplierContrct = getSupplierContrct(YSId);

			//逻辑删除既存合同信息
			deleteContract(YSId);
			
			//删除既存合同明细
			deleteContractDetail(YSId);
			
			
			for(int i=0;i<supplierList.size();i++){
				
				String supplierId = supplierList.get(i).get("supplierId");
				String shortName  = supplierList.get(i).get("shortName");
				String total      = supplierList.get(i).get("total");
				String purchaseType = supplierList.get(i).get("purchaseType");
				
				if(supplierId == null || supplierId.equals("")){
					continue;
				}
				float totalf = stringToFloat(total); 
				if(totalf == 0){//采购数量为零的供应商不计入合同
					continue;
				}
				//取得供应商的合同流水号
				//父编号:年份+供应商简称
				String type = getContractType(purchaseType);
				
				String typeParentId = BusinessService.getshortYearcode()+type;				
				String supplierParentId = BusinessService.getshortYearcode() + shortName;				
				String typeSubId = getContractTypeCode(typeParentId);
				String suplierSubId = getContractSupplierCode(supplierParentId);

				//3位流水号格式化	
				//采购合同编号:16D081-WL002
				String contractId = BusinessService.getContractCode(type,typeSubId, suplierSubId,shortName);
				
				//新增采购合同

				B_PurchaseOrderData data = new B_PurchaseOrderData();
				
				data.setRecordid(guid);
				data.setYsid(YSId);
				data.setMaterialid(materialId);
				data.setContractid(contractId);
				data.setTypeparentid(typeParentId);
				data.setTypeserial(typeSubId);
				data.setSupplierparentid(supplierParentId);
				data.setSupplierserial(suplierSubId);
				data.setSupplierid(supplierId);
				data.setTotal(total);
				data.setPurchasedate(CalendarUtil.fmtYmdDate());
				data.setDeliverydate(CalendarUtil.dateAddToString(data.getPurchasedate(),20));
				data.setVersion(1);//默认为1
				
				insertPurchaseOrder(data);//新增合同头表

				//新增合同明细
				insertPurchaseOrderDetail(YSId,supplierId,contractId);
				
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
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
	
	/**
	 * 插入处理
	 */
	private void insertWorkshopRentun(
			B_WorkshopReturnData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"WorkshopReturnInsert",userInfo);
		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setReturnperson(userInfo.getUserId());
		
		new B_WorkshopReturnDao().Create(data);	
	}
	
	
	
	
	/**
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
	
	public void updateWorkshopRentunInit() throws Exception{

		String contractId = request.getParameter("contractId");
		String workshopReturnId = request.getParameter("workshopReturnId");
		
		getContractDetailList(contractId);		

		getWorkshopReturnList(workshopReturnId);
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
		/*
		for(B_PurchaseOrderData data:list){

			version = data.getVersion();
			
			//copyProperties(dbPlan,reqPlan);
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"purchasePlanUpdate",userInfo);			
			copyProperties(data,commData);
			data.setVersion(version+1);
			dao.Store(data);
		}
		try {
			dao.RemoveByWhere(astr_Where);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		*/
	}
	

	/**
	 * 合同明细删除处理
	 */
	private void deleteContractDetailByContractId(String contractId) {
		
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();

		String astr_Where = " contractId = '" + contractId +"'";	
		try {
			dao.RemoveByWhere(astr_Where);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
	}
	
	/**
	 * 合同删除处理
	 */
	@SuppressWarnings("unchecked")
	private int deleteContract(String YSId) {
		
		int version = 0;
		B_PurchaseOrderDao dao = new B_PurchaseOrderDao();
		String astr_Where = " YSId = '" + YSId +"'";	
		
		/*//确认数据是否存在		
		List<B_PurchaseOrderData> list = dao.Find(astr_Where);
		if(!(list == null || ("").equals(list))){	
			//update处理	
			for(B_PurchaseOrderData data:list){

				version = data.getVersion();
				
				//copyProperties(dbPlan,reqPlan);
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"purchasePlanUpdate",userInfo);			
				copyProperties(data,commData);
				data.setVersion(version+1);
				dao.Store(data);
			}

		}else{
			//insert处理
		}
		*/
		return version;	
		
	}
	

	/**
	 * 合同删除处理
	 */
	private void deleteContractByContractId(String contractId) {
		
		B_PurchaseOrderDao dao = new B_PurchaseOrderDao();

		String astr_Where = " contractId = '" + contractId +"'";	
		try {
			dao.RemoveByWhere(astr_Where);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
	}
	
	/*
	 * 更新处理
	 */
	private void updateWorkshopRentun(B_WorkshopReturnData data) 
			throws Exception{
		
		B_WorkshopReturnData dbDt = 
			(B_WorkshopReturnData) new B_WorkshopReturnDao().FindByPrimaryKey(data);
		
		if(null != dbDt){			
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"WorkshopReturnUPdate",userInfo);
			copyProperties(dbDt,commData);
			
			new B_WorkshopReturnDao().Store(dbDt);
		}
		
	}		
	
	private String getContractTypeCode(String parentId) throws Exception {

		dataModel.setQueryName("getContractTypeCode");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("typeParentId", parentId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);		
		String code =dataModel.getYsViewData().get(0).get("MaxSubId");		
		return code;		
	}	

	private String getContractSupplierCode(String parentId) throws Exception {

		dataModel.setQueryName("getContractTypeCode");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("supplierParentId", parentId);		
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
		
		getContractDetailList(contractId);
		
	}
	
	public void approveAndView() throws Exception {
		
		String YSId = request.getParameter("YSId");
		
		updateDetailToApprove(YSId);
		
		//getZZOrderDetail(YSId);	
		
	}
	

	public void createContractInit() throws Exception {
		
		String supplierId = request.getParameter("supplierId");
		String materialId = request.getParameter("materialId");
		String goBackFlag = request.getParameter("goBackFlag");
		String quantity = request.getParameter("quantity");
		String YSId = request.getParameter("YSId");
		
		getMaterialDetailList(supplierId,materialId);
		
		model.addAttribute("goBackFlag",goBackFlag);
		model.addAttribute("quantity",quantity);
		model.addAttribute("YSId",YSId);
	}
	

	public void createRoutineContract() throws Exception {
		
		String goBackFlag = request.getParameter("goBackFlag");
		String YSId = request.getParameter("YSId");
		String shortName = reqModel.getShortName();

		//常规采购的耀升编号:17YS00
		if(YSId == null || ("").equals(YSId)){
			YSId = BusinessService.getRoutinePurchaseYsid();
		}
		
		B_PurchaseOrderData contract = reqModel.getContract();
		//创建合同编号
		contract = geRoutinePurchaseContractId(contract,YSId,shortName);
		
		//合同数据做成
		insertRoutine(contract);
		//跳转到查看页面
		String contractId = contract.getContractid();
		getContractDetailList(contractId);
				
		model.addAttribute("goBackFlag",goBackFlag);
	}
	
	
	private B_PurchaseOrderData geRoutinePurchaseContractId(
			B_PurchaseOrderData dt,
			String YSId,String shortName) throws Exception{
		
		//取得供应商的合同流水号
		//父编号:年份+供应商简称
		
		//取得供应商的合同流水号
		//父编号:年份+供应商简称
		String typeParentId = BusinessService.getshortYearcode()+Constants.PURCHASE_TYPE_T;
		
		String supplierParentId = BusinessService.getshortYearcode() + shortName;
		
		String typeSubId = getContractTypeCode(typeParentId);
		String suplierSubId = getContractSupplierCode(supplierParentId);

		//3位流水号格式化	
		//采购合同编号:16D081-WL002
		String contractId = BusinessService.getContractCodeD(typeSubId, suplierSubId,shortName);

		String parentId = "";
		if(null != YSId && YSId.length() > 3 ){
			parentId = YSId.substring(0,2);//耀升编号前两位是年份
		}
		parentId = parentId + shortName;
		
		//String subId = getContractCode(parentId);
		
		//3位流水号格式化	
		//采购合同编号:16YS081-WL002
		//String contractId = BusinessService.getContractCode(YSId, shortName, subId);

		//dt.setYsid(YSId);
		//dt.setContractid(contractId);
		//dt.setParentid(parentId);
		//dt.setSubid(String.valueOf(Integer.parseInt(subId)+1));
		
		return dt;

	}
	
	private void insertRoutine(B_PurchaseOrderData contract){
		
		ts = new BaseTransaction();

		try {
			ts.begin();

			String contractId = contract.getContractid();
			String YSId = contract.getYsid();

			//删除既存合同信息
			deleteContractByContractId(contractId);
			
			//删除既存合同明细
			deleteContractDetailByContractId(contractId);
			
			//新增常规采购合同
			insertPurchaseOrder(contract);
			
			//合同明细
			List<B_PurchaseOrderDetailData> reqDetail = reqModel.getDetailList();
			for(B_PurchaseOrderDetailData d:reqDetail){
				d.setYsid(YSId);
				d.setContractid(contractId);				
				insertPurchaseOrderDetail(d);				
			}
			
			ts.commit();
		}
		catch(Exception e) {
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println(e.getMessage());
		}
	}
	
	private void insertPurchaseOrder(B_PurchaseOrderData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseOrderInsert",userInfo);
		copyProperties(data,commData);		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		orderDao.Create(data);	
	}
	
	
	private void insertPurchaseOrderDetail(B_PurchaseOrderDetailData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RoutinePurchaseOrderDetailInsert",userInfo);
		copyProperties(data,commData);		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		detailDao.Create(data);	
	}
	

	public void createAcssoryContractInit() throws Exception{

		String PIId = request.getParameter("PIId");
		String orderType = Constants.ORDERTYPE_2;//配件
		this.dataModel.setQueryName("createAcssoryContractInit");

		this.userDefinedSearchCase.put("PIId", PIId);
		this.userDefinedSearchCase.put("orderType", orderType);
		this.baseQuery = new BaseQuery(this.request, this.dataModel);	
		this.baseQuery.setUserDefinedSearchCase(this.userDefinedSearchCase);
		this.baseQuery.getYsFullData();
	
		this.model.addAttribute("order", this.dataModel.getYsViewData().get(0));
		this.model.addAttribute("detail", this.dataModel.getYsViewData());
		this.model.addAttribute("PIId", PIId);
	}
	
	public void createAcssoryContractAndView() throws Exception{

		//跳转到查看页面
		String contractId = insertPurchaseOrderAndDetail();
		getContractDetailList(contractId);
	}
	
	public String insertPurchaseOrderAndDetail() throws Exception{
		ts = new BaseTransaction();

		String YSId = request.getParameter("YSId");
		String contractId = "";
		try {
			ts.begin();
			
			if(!(YSId == null || ("").equals(YSId))){
				String[] tmp = YSId.split("-");
				if(tmp.length>1)
					YSId = tmp[0];//配件订单的耀升编号形式:17YS001-1
			}
			
			//B_PurchaseOrderData contract = reqModel.getContract();
			List<B_PurchaseOrderData> contractList = reqModel.getContractList();
			List<B_PurchaseOrderDetailData> detailList = reqModel.getDetailList();
						
			//删除既存合同信息
			deleteContract(YSId);
			
			//删除既存合同明细
			deleteContractDetail(YSId);			
			
			//供应商集计
			List<B_SupplierData> supplier = new ArrayList<B_SupplierData>();			
			for(int j=0;j<contractList.size();j++){
				
				String supplierId = contractList.get(j).getSupplierid();
				String shortName  = contractList.get(j).getTypeserial();//临时借用
				String totalPrice = detailList.get(j).getTotalprice();//合同跟明细件数相同
				float f1 = stringToFloat(totalPrice.replace(",", ""));
				
				if(supplierId == null || supplierId.equals("")){
					continue;
				}
				
				boolean repeatFlag = true;
				for(int i=0;i<supplier.size();i++){
					String id = supplier.get(i).getSupplierid();
					String price = supplier.get(i).getSuppliername();//临时借用
					
					if(supplierId.equals(id)){//计算重复的供应商
						float f2 = stringToFloat(price.replace(",", ""));
						supplier.get(i).setSuppliername(String.valueOf(f1+f2));//相同供应商的价格合并
						repeatFlag = false;
						break;
					}
				}
				
				if(repeatFlag){//保存不一致的供应商
					B_SupplierData s = new B_SupplierData();
					s.setSupplierid(supplierId);
					s.setShortname(shortName);
					s.setSuppliername(totalPrice);//临时借用
					supplier.add(s);
				}
				
			}//供应商集计
				
			for(B_SupplierData sup: supplier){
				
				String supplierId = sup.getSupplierid();
				String shortName  = sup.getShortname();
				String total  = sup.getSuppliername();//临时借用
				//String total  = sup.();//临时借用
				
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

				//String type = getContractType(purchaseType);
				
				//String subId = getContractCode(parentId);

				//3位流水号格式化	
				//采购合同编号:16YS081-WL002
				//contractId = BusinessService.getContractCode(YSId, shortName, subId);
				
				//新增采购合同:由于是配件订单,没有成品
				//insertPurchaseOrder(
						//YSId,null,supplierId,contractId,parentId,subId,String.valueOf(totalf));

				for(int i=0;i<detailList.size();i++){
					B_PurchaseOrderDetailData detail = detailList.get(i);
				
					String id = contractList.get(i).getSupplierid();
					if(supplierId.equals(id)){
						//新增合同明细
						detail.setYsid(YSId);
						detail.setContractid(contractId);
						insertPurchaseOrderDetail(detail);						
					}
				}
				
			}
			
			ts.commit();
			
		}catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return contractId;
	}

}
