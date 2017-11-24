package com.ys.business.service.order;

import java.net.URLDecoder;
import java.text.ParseException;
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
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.OrderModel;
import com.ys.business.db.dao.B_CustomerDao;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDao;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class OrderService extends CommonService  {

	DicUtil util = new DicUtil();

	BaseTransaction ts;
	String guid ="";
	private CommFieldsData commData;

	private OrderModel reqModel;

	private HttpServletRequest request;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	HttpSession session;
	
	public OrderService(){
		
	}

	public OrderService(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			OrderModel reqModel,
			UserInfo userInfo){
		
		//this.bomPlanDao = new B_BomPlanDao();
		//this.bomPlanData = new B_BomPlanData();
		//this.bomDetailDao = new B_BomDetailDao();
		//this.bomDetailData = new B_BomDetailData();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	
	public HashMap<String, Object> getOrderList(String formId,String data) throws Exception {
		
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

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");	
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2))
			userDefinedSearchCase.put("status", "");
		
		baseQuery = new BaseQuery(request, dataModel);	
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
	
	public HashMap<String, Object> getOrderListDemand(String data) throws Exception {
		
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
		

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderListDemand");
		
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
		modelMap.put("unitList",util.getListOption(DicUtil.MEASURESTYPE, ""));
		
		modelMap.put("data", dataModel.getYsViewData());	
		modelMap.put("keyword1",key1);	
		modelMap.put("keyword2",key2);		
		
		return modelMap;
	}
	
	public ArrayList<HashMap<String, String>> getOrderViewByPIId(
			String PIId ) throws Exception {

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");
		
		baseQuery = new BaseQuery(request, dataModel);

		String YSId = request.getParameter("YSId");
		if(YSId != null && !YSId.equals(""))
			userDefinedSearchCase.put("YSId", YSId);
		
		if(PIId != null && !PIId.equals(""))
			userDefinedSearchCase.put("keywords1", PIId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		return modelMap;
	}
	

	public ArrayList<HashMap<String, String>> createOrderFromProduct(
			String materialId ) throws Exception {

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("createorderfromproduct");
		
		baseQuery = new BaseQuery(request, dataModel);

		
		userDefinedSearchCase.put("materialId", materialId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsFullData();
		
		return modelMap;
	}
	
	public ArrayList<HashMap<String, String>> getZZOrderDetail(
			String PIId ) throws Exception {

		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("getZZOrderDetail");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("orderId", PIId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		return modelMap;
	}
	public ArrayList<HashMap<String, String>> getZPOrderDetail(
			String PIId ) throws Exception {

		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("getZZOrderDetail");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("orderId", PIId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		return modelMap;
	}
	
	@SuppressWarnings("unchecked")
	private List<B_OrderDetailData> getOrderDetailByPIId(String where){
		B_OrderDetailDao dao = new B_OrderDetailDao();
		List<B_OrderDetailData> dbList = new ArrayList<B_OrderDetailData>();
				
		try {

			dbList = (List<B_OrderDetailData>)dao.Find(where);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbList = null;
		}
		
		return dbList;
	}
	
	public String  getNewYsid() throws Exception{
		
		String paternId = BusinessService.getYSCommCode();
		int YSMaxid = getYSIdByParentId(paternId);
		return BusinessService.getYSFormatCode(YSMaxid,true);
			
	}
	
	/*
	public ArrayList<HashMap<String, String>> getOrderDetailByPIId(
			HttpServletRequest request,
			String PIId ) throws Exception {
		
		ArrayList<HashMap<String, String>> modelMap = null;
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderDetailByPIId");
		
		baseQuery = new BaseQuery(request, dataModel);


		userDefinedSearchCase.put("keywords1", PIId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		return modelMap;
	}
	*/
	
	public HashMap<String, Object> getCustomer(HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		data = URLDecoder.decode(data, "UTF-8");
		
		String key = request.getParameter("key").toUpperCase();
		
				

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getCustomer");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 	
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());

		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getOrderSubIdByParentId(
			String key) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderSubIdByParentId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("retValue", "success");
		
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		int newSubid = code + 1;
		
		String codeFormat = String.format("%03d", newSubid); 
		
		modelMap.put("code",newSubid);	
		modelMap.put("codeFormat",codeFormat);			
		
		return modelMap;
	}
	
	/*
	 * 
	 */
	public HashMap<String, Object> getMaterialList(
			HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;


		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("materialList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
	
	
	/*
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	public Model insert(
			OrderModel reqFormBean,
			Model rtnModel,
			HttpServletRequest request,
			UserInfo userInfo) throws Exception  {

		ts = new BaseTransaction();

		try {

			
			ts.begin();
					
			B_OrderData reqData = (B_OrderData)reqFormBean.getOrder();
			
			//插入订单基本信息
			insertOrder(reqData,userInfo);
			
			//处理订单详情数据		
			List<B_OrderDetailData> reqDataList = reqFormBean.getOrderDetailLines();
			
			String piId = reqData.getPiid();
			
			for(B_OrderDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data != null && 
					data.getMaterialid() != null && 
					data.getMaterialid() != ""){

					insertOrderDetail(data,piId, userInfo);
					
								
				}	
			
			}
			
			//重新查询,回到查看页面
			//rtnModel = view(request,rtnModel,selectedRecord,parentId);
			
			reqFormBean.setEndInfoMap(NORMAL, "suc001", "");
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqFormBean.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return rtnModel;
	}	

	/*
	 * 订单详情插入处理
	 */
	public void insertOrder(
			B_OrderData data,
			UserInfo userInfo) throws Exception{

		B_OrderDao dao = new B_OrderDao();	
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"OrderInsert",userInfo);
		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		//重新编辑PI的流水号
		String PI = data.getPiid();
		String parentId = data.getParentid();
		String subId = PI.substring(parentId.length());
		data.setSubid(subId);
		
		dao.Create(data);
	}	
	
	/*
	 * 订单详情插入处理
	 */
	private void insertOrderDetail(
			B_OrderDetailData newData,
			String piId,
			UserInfo userInfo) throws Exception{

		B_OrderDetailDao dao = new B_OrderDetailDao();

		String ysid = newData.getYsid();
		//耀升编号重复check
		String existFlag = ysidExistCheck(ysid);
		
		//如果重复的话,重新设置
		if(("1").equals(existFlag)){
	        String paternId = BusinessService.getYSCommCode();
			int YSMaxid = getYSIdByParentId(paternId);
			ysid = BusinessService.getYSFormatCode(YSMaxid,true);

			newData.setYsid(ysid);
			newData.setParentid(paternId);
			newData.setSubid(String.valueOf(YSMaxid+1));
		}else{
			//newData.setYsid(ysid);
			newData.setParentid(ysid.substring(0, 4));
			String subid = newData.getYsid().substring(4);
			String split[] = ysid.split("-");
			if(split != null && split.length >1){
				subid = split[0].substring(4);
			}
			newData.setSubid(subid);
		}
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"OrderDetailInsert",userInfo);
		copyProperties(newData,commData);
		guid = BaseDAO.getGuId();
		newData.setRecordid(guid);
	
		newData.setPiid(piId);
		newData.setCurrency(reqModel.getCurrency());
		newData.setStatus(Constants.ORDER_STS_1);
		newData.setReturnquantity(Constants.ORDER_RETURNQUANTY);
		
		dao.Create(newData);

	}	
	
	/*
	 * 1.订单基本信息更新处理(1条数据)
	 * 2.订单详情 新增/更新/删除 处理(N条数据)
	 */
	public Model update(
			OrderModel reqForm,
			Model rtnModel, 
			HttpServletRequest request,
			UserInfo userInfo) throws Exception  {

		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
			
			//int YSMaxid = getYSIdByParentId(request);
				
			B_OrderData reqOrder = (B_OrderData)reqForm.getOrder();
			
			//订单基本信息更新处理
			updateOrder(reqOrder,userInfo);
			
			//订单详情 更新/删除/插入 处理			
			List<B_OrderDetailData> newDetailList = reqForm.getOrderDetailLines();
			
			//订单详情更新处理
			//首先删除DB中的订单详情
			String piId = reqOrder.getPiid();
			String oldPiId = reqModel.getKeyBackup();
			String where = " piid = '"+oldPiId +"'" ;
			deleteOrderDetail(where);
			
			//订单详情 
			for(B_OrderDetailData newData:newDetailList ){

				String mateId = newData.getMaterialid();	
					
				if(mateId != null && mateId.trim() != ""){
					
					//更新处理
					insertOrderDetail(newData, piId, userInfo);						
					
				}
			}
			
			ts.commit();
					
			reqForm.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			ts.rollback();
			reqForm.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
				
		return rtnModel;
	}
	
	/*
	 * 订单基本信息更新处理
	 */
	public void updateOrder(B_OrderData order,UserInfo userInfo) 
			throws Exception{

		String recordid = order.getRecordid();		
		B_OrderDao dao = new B_OrderDao();
		
		//取得更新前数据		
		B_OrderData dbData = getOrderByRecordId(recordid);					
		
		if(null != dbData){
			
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"OrderUpdate",userInfo);

			copyProperties(dbData,commData);
			//获取页面数据
			String piid = order.getPiid();
			dbData.setPiid(piid);
			dbData.setParentid(piid.substring(0,piid.length() -3));
			dbData.setSubid(piid.substring(piid.length() -3));
			dbData.setOrderid(order.getOrderid());
			dbData.setPaymentterm(order.getPaymentterm());
			//dbData.setCurrency(order.getCurrency());;
			dbData.setShippingcase(order.getShippingcase());
			dbData.setDeliverydate(order.getDeliverydate());
			dbData.setOrderdate(order.getOrderdate());
			dbData.setDeliveryport(order.getDeliveryport());
			dbData.setLoadingport(order.getLoadingport());
			dbData.setTotalprice(order.getTotalprice());
			dbData.setTeam(order.getTeam());
			dbData.setOrdercompany(order.getOrdercompany());
			
			dao.Store(dbData);
			
		}else{
			
			commData = commFiledEdit(
					Constants.ACCESSTYPE_INS,"OrderInsert",userInfo);
			copyProperties(order,commData);
			
			guid = BaseDAO.getGuId();
			order.setRecordid(guid);
			//重新编辑PI的流水号
			String PI = order.getPiid();
			String parentId = order.getParentid();
			String subId = PI.substring(parentId.length());
			order.setSubid(subId);
			
			dao.Create(order);	
		}
	}

	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail(
			B_OrderDetailData newData,
			B_OrderDetailData dbData,
			UserInfo userInfo) 
			throws Exception{
		
		B_OrderDetailDao dao = new B_OrderDetailDao();							
		
		//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"OrderDetailUpdate",userInfo);

		copyProperties(dbData,commData);
		//获取页面数据
		dbData.setQuantity(newData.getQuantity());
		dbData.setPrice(newData.getPrice());;
		dbData.setTotalprice(newData.getTotalprice());
		
		dao.Store(dbData);

	}
	
	/*
	 * 订单详情删除处理
	 */
	public void deleteOrderDetail(List<B_OrderDetailData> oldDetailList,UserInfo userInfo) 
			throws Exception{

		B_OrderDetailDao dao = new B_OrderDetailDao();
		
		for(B_OrderDetailData detail:oldDetailList){
			
			if(null != detail){
				
				//处理共通信息
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,"OrderDetailDelete",userInfo);

				copyProperties(detail,commData);
				
				dao.Store(detail);
			}
		}
	}
	
	/*
	 * BOM详情删除处理
	 */
	private void deleteOrderDetail(String where) {

		B_OrderDetailDao dao = new B_OrderDetailDao();
		try{
			dao.RemoveByWhere(where);
		}catch(Exception e){
			//nothing
		}		
	}
	@SuppressWarnings("unchecked")
	public Model delete(String delData){

		B_OrderDetailDao dao = new B_OrderDetailDao();	
		B_OrderDetailData data = new B_OrderDetailData();

		B_OrderDao odao = new B_OrderDao();	
		B_OrderData odata = new B_OrderData();
		List<B_OrderData> list = null;		
		
		B_PurchasePlanDao purchaseplan = new B_PurchasePlanDao();
		B_PurchaseOrderDao purOrder = new B_PurchaseOrderDao();
		B_PurchaseOrderDetailDao purOrderDetail = new B_PurchaseOrderDetailDao();
		
		try {	
			
			ts = new BaseTransaction();										
			ts.begin();									
			String removeData[] = delData.split(",");									
			for (String key:removeData) {									

				data.setRecordid(key);				
				data = (B_OrderDetailData)dao.FindByPrimaryKey(data);

				dao.Remove(data);
				
				
				String Ysid = data.getYsid();
				String purchaseStr = "Ysid = '" + Ysid +"'";
				
				try {
					//purchaseplan.RemoveByWhere(purchaseStr);//采购订单
				} catch (Exception e1) {
					//
				}
				try {
					//purOrder.RemoveByWhere(purchaseStr);//采购合同
				} catch (Exception e1) {
					//
				}
				
				try {
					//purOrderDetail.RemoveByWhere(purchaseStr);//采购合同明细
				} catch (Exception e1) {
					//
				}

				//判断是否要删除PI信息				
				String Piid = data.getPiid();
				String where = "PIId = '" + Piid +"' AND deleteFlag='0'";
				list = odao.Find(where);
				
				if(list !=null && list.size() == 1){
					//一个PI下只有一个产品时,删除YS时,同时删除PI信息
					odao.Remove(list.get(0));
				}
												
			}
			ts.commit();
		}
		catch(Exception e) {
			try {
				ts.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
		return model;
		
	}
	
	/*
	 * 
	 */
	public OrderModel createOrder() {

		try {			
      
			//耀升编号
	        String paternId = BusinessService.getYSCommCode();	        
			int YSMaxId = getYSIdByParentId(paternId);
			reqModel.setYSMaxId(YSMaxId);	
			reqModel.setYSParentId(paternId);
			
			reqModel.setDeliveryPortList(
					util.getListOption(DicUtil.DELIVERYPORT, ""));
			reqModel.setShippingCaseList(
					util.getListOption(DicUtil.SHIPPINGCASE, ""));
			reqModel.setLoadingPortList(
					util.getListOption(DicUtil.LOADINGPORT, ""));
			reqModel.setCurrencyList(
					util.getListOption(DicUtil.CURRENCY, ""));
			reqModel.setTeamList(
					util.getListOption(DicUtil.BUSINESSTEAM, ""));
			reqModel.setOrdercompanyList(
					util.getListOption(DicUtil.ORDERCOMPANY, ""));
			reqModel.setProductClassifyList(
					util.getListOption(DicUtil.PRODUCTCLASSIFY, ""));
			reqModel.setOrderNatureList(
					util.getListOption(DicUtil.ORDERNATURE,""));
			
			reqModel.setEndInfoMap(NORMAL, "", "");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return reqModel;
	
	}
	
	public void createOrderByMaterialId() throws Exception{

		String materialId = request.getParameter("materialId");
		
		B_OrderData order = getPiidByCustomer(materialId);
		//B_CustomerData cus = getCustomerInfo(order.getCustomerid());
		
		ArrayList<HashMap<String, String>> dbData = createOrderFromProduct(materialId);

		order.setCustomerid(dbData.get(0).get("customerId"));
		reqModel.setOrderData(order);
		//order.setCustomerid(cus.getCustomerid());
		//order.setCurrency(cus.getCurrency());
		//order.setDeliveryport(cus.getDestinationport());
		//order.setLoadingport(cus.getShippiingport());
		//order.setPaymentterm(cus.getPaymentterm());
		//order.setShippingcase(cus.getShippingcondition());

		createOrder();//下拉框
		String YSId = reqModel.getYSParentId()+String.valueOf(reqModel.getYSMaxId()+1);
		
		dbData.get(0).put("PIId",order.getPiid());
		dbData.get(0).put("orderDate",CalendarUtil.getToDay());
		dbData.get(0).put("deliveryDate",CalendarUtil.dateAddToString(CalendarUtil.getToDay(), 20));
		dbData.get(0).put("YSId",YSId);

		
		model.addAttribute("order",  dbData.get(0));
		model.addAttribute("detail", dbData);
		model.addAttribute("orderForm", reqModel);
	}

	@SuppressWarnings("unchecked")
	private B_CustomerData getCustomerInfo(String shortName) throws Exception{
		B_CustomerData data = null;
		B_CustomerDao dao = new B_CustomerDao();
		
		String where = " shortName ='" + shortName + "' AND deleteFlag='0' ";
		List<B_CustomerData> list = dao.Find(where);
		if(list != null && list.size() > 0)
				data = list.get(0);
		
		return data;
	}
	
	private B_OrderData getPiidByCustomer(String materialId) throws Exception{
		
		String shortName = "";
		
		if(!(materialId == null || ("").equals(materialId))){
			String[] splits = materialId.split("[.]");
			if(splits.length ==4){
				shortName = splits[2].substring(0, splits[2].length()-3);
			}else if(splits.length ==5){
				shortName = splits[3].substring(0, splits[3].length()-3);
				
			}
		}
		
		String yearCode = BusinessService.getshortYearcode();
		String parentId = yearCode+shortName;
		HashMap<String, Object> map = getOrderSubIdByParentId(parentId);
		String piid = parentId + map.get("codeFormat");
		
		B_OrderData order = new B_OrderData();		
		order.setSubid(map.get("code").toString());
		order.setParentid(parentId);
		order.setPiid(piid);
		order.setCustomerid(shortName);
		
		
		return order;
	}

	/*
	 * 取得耀升编号的流水号
	 */
	public int getYSIdByParentId(String paternId) 
			throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;		
  
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getYSIdByParentId");		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件        
		userDefinedSearchCase.put("keywords1", paternId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		return code;
	}
	

	/*
	 * 取得耀升编号的流水号
	 */
	@SuppressWarnings("unchecked")
	public int getYSIdByParentId() 
			throws Exception {

		String parentId = BusinessService.getYSCommCode();
		int code = 1;
		B_OrderDetailDao dao = new B_OrderDetailDao();
				
		try {
			String astr_Where = " parentId= '" +parentId +"' order by subId+0";
			List<B_OrderDetailData> list = dao.Find(astr_Where);
			
			if(list == null || ("").equals(list))
					return code;
			
			int max=BusinessConstants.ORDERNO_MAX;
			int subid =0;
			int size = list.size();
			for(int i=0;i<max;i++){
				
				String s="";
				try{
					s = list.get(i).getSubid();
				}catch(Exception e){
					code = i;
					break;
				}
				
				try{
					subid = Integer.parseInt(s);
				}catch(Exception e){
					continue;//防止有非数字混入到编码中
				}
				
				if(subid > BusinessConstants.ORDERBNO_START){
					
					if(i<size-1){
						
						String s2 = list.get(i+1).getSubid();
						int subid2=0;
						try{
							subid2 = Integer.parseInt(s2);
						}catch(Exception e){
							continue;//防止有非数字混入到编码中									
						}
						if(subid+1 >= subid2){
							continue;
						}else{
							code = subid;
							break;
						}
						
					}else{
						code = subid;
						break;
						
					}//i<size-1					
					
				}else{
					continue;
				}
					
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

  
		return code;
	}
	
	private B_OrderData getOrderByRecordId(String key) throws Exception {
		B_OrderDao dao = new B_OrderDao();
		B_OrderData dbData = new B_OrderData();
				
		try {
			dbData.setRecordid(key);
			dbData = (B_OrderData)dao.FindByPrimaryKey(dbData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		return dbData;
	}
	
	public HashMap<String, Object> getDocumenterayName() throws Exception{
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		 String key1 = this.request.getParameter("key").toUpperCase();
		
		 this.dataModel.setQueryFileName("/business/order/orderquerydefine");
		 this.dataModel.setQueryName("getDocumenterayName");
		
		 this.baseQuery = new BaseQuery(this.request, this.dataModel);
		
		 this.userDefinedSearchCase.put("costName", key1);
		
		 this.baseQuery.setUserDefinedSearchCase(this.userDefinedSearchCase);
		 this.baseQuery.getYsFullData();
		
		 modelMap.put("data", this.dataModel.getYsViewData());
		
		 modelMap.put("retValue", "success");
		
		 return modelMap;
	}

	public void documentaryEdit() throws Exception{
		
		String YSId = this.request.getParameter("YSId");
		getOrderDetail(YSId);
	}
	
	
	public void getPurchaseOrder() throws Exception{
		
		String YSId = this.request.getParameter("YSId");
		getOrderDetail(YSId);
	}
	
	public void getOrderDetail(String YSId) throws Exception{
		
		  this.dataModel.setQueryFileName("/business/order/orderquerydefine");
		  this.dataModel.setQueryName("getOrderList");
		
		  this.baseQuery = new BaseQuery(this.request, this.dataModel);
		
		  this.userDefinedSearchCase.put("keyword1", YSId);
		  this.baseQuery.setUserDefinedSearchCase(this.userDefinedSearchCase);
		  this.baseQuery.getYsFullData();
		
		  this.model.addAttribute("order", this.dataModel.getYsViewData().get(0));
	}
	
	public HashMap<String, Object> getYsidList() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		  this.dataModel.setQueryFileName("/business/order/orderquerydefine");
		  this.dataModel.setQueryName("getOrderList");
		
		  this.baseQuery = new BaseQuery(this.request, this.dataModel);
		  String key = request.getParameter("key");
		  if(notEmpty(key))
			  key = key.toUpperCase();
		  this.userDefinedSearchCase.put("keyword1", key);
		  this.baseQuery.setUserDefinedSearchCase(this.userDefinedSearchCase);
		  this.baseQuery.getYsFullData();
		
		  modelMap.put("data", dataModel.getYsViewData());
		  
		  return modelMap;		  
		}
		
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> piidExistCheck() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String ExFlag = "";
		String PIId = request.getParameter("PIId");

		String where = " piid = '"+PIId +"' AND deleteFlag='0' " ;
		B_OrderDao dao = new B_OrderDao();
		List<B_OrderData> list; 
		list = (List<B_OrderData>)dao.Find(where);	
		if(list != null && list.size() > 0){
			ExFlag = "1";
		}	
		modelMap.put("ExFlag",ExFlag);
			
		return modelMap;		
	}
	
	
	@SuppressWarnings("unchecked")
	public String ysidExistCheck(String YSId) throws Exception{

		String ExFlag = "";
		String where = " ysid = '"+YSId +"' AND deleteFlag='0' " ;
		B_OrderDetailDao dao = new B_OrderDetailDao();
		List<B_OrderData> list; 
		list = (List<B_OrderData>)dao.Find(where);	
		if(list != null && list.size() > 0){
			ExFlag = "1";
		}
			
		return ExFlag;		
	}
	
}
