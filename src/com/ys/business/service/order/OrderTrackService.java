package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.sun.istack.internal.NotNull;
import com.ys.business.action.model.order.OrderTrackModel;
import com.ys.business.db.dao.B_BomPlanDao;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_OrderDivertDao;
import com.ys.business.db.dao.B_OrderReviewDao;
import com.ys.business.db.dao.B_ProducePlanDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDeliveryDateHistoryDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.S_systemConfigDao;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_OrderDivertData;
import com.ys.business.db.data.B_OrderReviewData;
import com.ys.business.db.data.B_ProducePlanData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDeliveryDateHistoryData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.db.data.S_systemConfigData;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

@Service
public class OrderTrackService extends CommonService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	
	private HttpServletRequest request;
	
	private B_OrderReviewDao dao;
	private CommFieldsData commData;
	private OrderTrackModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public OrderTrackService(){
		
	}

	public OrderTrackService(Model model,
			HttpServletRequest request,
			OrderTrackModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_OrderReviewDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/orderreviewquerydefine");
		
	}
	public HashMap<String, Object> getReviewList( 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		dataModel = new BaseModel();

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
		
		dataModel.setQueryName("getreviewlist");
		
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
		
		return modelMap;
	}
	
	

	@SuppressWarnings("unchecked")
	public B_OrderDetailData getOrderByYSId(
			B_OrderDetailDao dao,
			String YSId ) throws Exception {
		
		List<B_OrderDetailData> dbList = null;
		
		String where = " YSId = '"+YSId +"'" + " AND deleteFlag = '0' ";
						
		dbList = (List<B_OrderDetailData>)dao.Find(where);
		
		if(dbList != null & dbList.size() > 0)
			return dbList.get(0);		
		
		return null;
	}
	
	
	
	public HashMap<String, Object> getOrderTrackingForStorage() throws Exception {

		String YSId = request.getParameter("YSId");
		
		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("orderTrackingForStorage");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();
		
		HashMap.put("data", dataModel.getYsViewData());
		
		return HashMap;
	}
	
	
	public HashMap<String, Object> getContractByYsid() throws Exception {

		String YSId = request.getParameter("YSId");
		
		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("contractListByYsid");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		String sql = baseQuery.getSql();
		sql = sql.replace("#0", YSId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData(sql,YSId);
		
		HashMap.put("data", dataModel.getYsViewData());
		
		return HashMap;
	}
	

	public HashMap<String, Object> getUnStorageContractCount() throws Exception {

		String supplierId = request.getParameter("supplierId");
		String materialId = request.getParameter("materialId");
		
		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("UnStorageContractCount");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("supplierId", supplierId);
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();
		
		HashMap.put("data", dataModel.getYsViewData());
		
		return HashMap;
	}
	
	public HashMap<String, Object> insertNewDeliveryDate() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ts = new BaseTransaction();

		try {			
			ts.begin();
					
			//处理订单详情数据	
			B_PurchaseOrderDeliveryDateHistoryData date = reqModel.getDeliveryDate();

			//挪用订单
			String YSId 		= request.getParameter("YSId");
			String contractId 	= request.getParameter("contractId");
			String materialId 	= request.getParameter("materialId");
			String deliveryDate = request.getParameter("deliveryDate");
			String newDeliveryDate = request.getParameter("newDeliveryDate");
			
			date.setContractid(contractId);
			date.setMaterialid(materialId);
			date.setDeliverydate(deliveryDate);
			date.setNewdeliverydate(newDeliveryDate);
			date.setNewupdatedate(CalendarUtil.fmtYmdDate());//系统时间
					
			//合同交期记录
			insertNewDeliveryDate(date);
			
			//更新合同的最新交期
		    updatePurchaseOrderDetail(contractId,newDeliveryDate);
		    
		    String maxDeliverDate = getZPTimeFromContract(YSId);
		    
		    if(notEmpty(maxDeliverDate)){
		    	updateProducePlan(YSId,maxDeliverDate);
		    }		        

			ts.commit();
			modelMap.put("returnValue", "SUCCESS");
			
		}
		catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			modelMap.put("returnValue", "ERROR");
		}	
		
		return modelMap;
	}
	
	@SuppressWarnings("unchecked")
	private void updatePurchaseOrderDetail(
			String contractId,
			String newDeliveryDate) throws Exception{
		
		String where = " contractId='" + contractId +"'  AND deleteFlag='0' ";
		
		List<B_PurchaseOrderData> list = new B_PurchaseOrderDao().Find(where);
		
		if(list.size() > 0){
			B_PurchaseOrderData db = list.get(0);
			db.setNewdeliverydate(newDeliveryDate);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"newDeliveryDate",userInfo);
			copyProperties(db,commData);
			
			new B_PurchaseOrderDao().Store(db);
			
		}
	}
	
	private void insertNewDeliveryDate(
			B_PurchaseOrderDeliveryDateHistoryData db) throws Exception{
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"Insert",userInfo);
		copyProperties(db,commData);
		guid = BaseDAO.getGuId();
		db.setRecordid(guid);
		
		new B_PurchaseOrderDeliveryDateHistoryDao().Create(db);
		

	}	
	
	/**
	 * 将生产任务中的订单筛选出来：料已备齐
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void setMaterialFinished(
			String YSId,
			String finishFlag,
			String zpTime) throws Exception{

	
		String where = "YSId ='" + YSId +"' AND deleteFlag='0' ";
		List<B_ProducePlanData> list = new B_ProducePlanDao().Find(where);
		if(list.size() > 0){
			B_ProducePlanData plan = list.get(0); 
			plan.setFinishflag(finishFlag);//
			//plan.setReadydate(getReadydateFromContract(YSId));
			plan.setReadydate(zpTime);
			
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"update",userInfo);
			copyProperties(plan,commData);	
			
			new B_ProducePlanDao().Store(plan);
			
		}else{
			B_ProducePlanData plan = new B_ProducePlanData();
			plan.setFinishflag(finishFlag);//B:料已备齐
			plan.setYsid(YSId);
			//plan.setReadydate(getReadydateFromContract(YSId));
			plan.setReadydate(zpTime);
			
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"insert",userInfo);
			copyProperties(plan,commData);	
			guid = BaseDAO.getGuId();				
			plan.setRecordid(guid);
			
			new B_ProducePlanDao().Create(plan);
		}
		
	}
	@SuppressWarnings("unchecked")
	public void updateProducePlan(
			String YSId,
			String zpTime) throws Exception{
	
		String where = "YSId ='" + YSId +"' AND deleteFlag='0' ";
		List<B_ProducePlanData> list = new B_ProducePlanDao().Find(where);
		if(list.size() > 0){
			B_ProducePlanData plan = list.get(0); 
			//plan.setFinishflag(finishFlag);//
			//plan.setReadydate(getReadydateFromContract(YSId));
			plan.setReadydate(zpTime);
			
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"update",userInfo);
			copyProperties(plan,commData);	
			
			new B_ProducePlanDao().Store(plan);
			
		}		
	}
	
	public void orderTrackDetailInit() throws Exception{

		String YSId = request.getParameter("YSId");	
		
		String zpTime = getZPTimeFromContract(YSId);
		
		if( isNullOrEmpty(zpTime) ){
			setMaterialFinished(YSId,"B","");//料已备齐
		}else{
			setMaterialFinished(YSId,"0",zpTime);//当前跟踪	
		}
		
		getOrderDetailByYSId(YSId);
	}
	
	
	private String getZPTimeFromContract(String YSId) throws Exception{
		
		String zptime = "";
		dataModel.setQueryFileName("/business/order/ordertrackquerydefine");
		dataModel.setQueryName("getMaxStokcinDate");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		if(baseQuery.getRecodCount()> 0){
			zptime = dataModel.getYsViewData().get(0).get("deliveryDate");
		}
		
		return zptime;	
		
	}


	
	public void getOrderDetailByYSId(String YSId) throws Exception{
		
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		model.addAttribute("order", dataModel.getYsViewData().get(0));	

		
	}
	
}
