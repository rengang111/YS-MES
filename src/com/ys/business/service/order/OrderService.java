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
import com.ys.system.service.common.BaseService;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.OrderModel;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDao;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class OrderService extends BaseService {

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

	public OrderService(Model model,
			HttpServletRequest request,
			OrderModel reqModel,
			UserInfo userInfo,
			HttpSession session){
		
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
		
	}
	
	public HashMap<String, Object> getOrderList(String data) throws Exception {
		
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
		
		String[] keyArr = getSearchKey(Constants.FORM_ORDER,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		//String key1 = getJsonData(data, "keyword1").toUpperCase();
		//String key2 = getJsonData(data, "keyword2").toUpperCase();
		

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");
		
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
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		
		String key = request.getParameter("parentId");

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

			int YSMaxid = getYSIdByParentId(request);
			
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

					insertOrderDetail(data, YSMaxid,piId, userInfo);
					
					YSMaxid++;
								
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
			int YSMaxid ,
			String piId,
			UserInfo userInfo) throws Exception{

		B_OrderDetailDao dao = new B_OrderDetailDao();

		String parentid = BusinessService.getYSCommCode();
		
		String ysid = BusinessService.getYSFormatCode(YSMaxid,true);
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"OrderDetailInsert",userInfo);

		copyProperties(newData,commData);
		guid = BaseDAO.getGuId();
		newData.setRecordid(guid);
		newData.setParentid(newData.getYsid().substring(0, 4));//临时设置
		newData.setSubid(newData.getYsid().substring(4));//临时设置
		//newData.setParentid(parentid);
		//newData.setSubid(String.valueOf(YSMaxid+1));
		newData.setPiid(piId);
		//newData.setYsid(ysid);
		newData.setStatus(Constants.ORDER_STS_0);
		
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
			
			int YSMaxid = getYSIdByParentId(request);
				
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
					insertOrderDetail(newData, YSMaxid, piId, userInfo);						
					
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
			dbData.setCurrency(order.getCurrency());;
			dbData.setShippingcase(order.getShippingcase());
			dbData.setDeliverydate(order.getDeliverydate());
			dbData.setOrderdate(order.getOrderdate());
			dbData.setDeliveryport(order.getDeliveryport());
			dbData.setLoadingport(order.getLoadingport());
			dbData.setTotalprice(order.getTotalprice());
			dbData.setTeam(order.getTeam());
			
			dao.Store(dbData);
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
					purchaseplan.RemoveByWhere(purchaseStr);//采购订单
				} catch (Exception e1) {
					//
				}
				try {
					purOrder.RemoveByWhere(purchaseStr);//采购合同
				} catch (Exception e1) {
					//
				}
				
				try {
					purOrderDetail.RemoveByWhere(purchaseStr);//采购合同明细
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
	 * 新增物料初始处理
	 */
	public OrderModel createOrder(
			HttpServletRequest request,
			OrderModel reqModel) {

		try {	
			
      
			//耀升编号
	        String paternId = BusinessService.getYSCommCode();
	        
			int YSMaxId = getYSIdByParentId(request);

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
			
			
			reqModel.setEndInfoMap(NORMAL, "", "");
				

			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return reqModel;
	
	}

	/*
	 * 取得耀升编号的流水号
	 */
	public int getYSIdByParentId(HttpServletRequest request) 
			throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;
		
  
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getYSIdByParentId");
		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件
        String paternId = BusinessService.getYSCommCode();
        
		userDefinedSearchCase.put("keywords1", paternId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
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
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> piidExistCheck() throws Exception{

		String ExFlag = "";
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
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
	public HashMap<String, Object> ysidExistCheck() throws Exception{

		String ExFlag = "";
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String YSId = request.getParameter("YSId");

		String where = " ysid = '"+YSId +"' AND deleteFlag='0' " ;
		B_OrderDetailDao dao = new B_OrderDetailDao();
		List<B_OrderData> list; 
		list = (List<B_OrderData>)dao.Find(where);	
		if(list != null && list.size() > 0){
			ExFlag = "1";
		}	
		modelMap.put("ExFlag",ExFlag);
			
		return modelMap;		
	}
	
}
