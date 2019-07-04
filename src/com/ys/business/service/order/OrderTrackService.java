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
	HttpSession session;

	public OrderTrackService(){
		
	}

	public OrderTrackService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,			
			OrderTrackModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_OrderReviewDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.session = session;
		this.userInfo = userInfo;
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		this.dataModel.setQueryFileName("/business/order/orderreviewquerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	
	public HashMap<String, Object> orderTrackingSearch( String data) throws Exception {

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
		String[] keyArr = getSearchKey(Constants.FORM_ORDERTRACKING,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];		

		dataModel.setQueryFileName("/business/order/ordertrackquerydefine");
		dataModel.setQueryName("orderTrackingFromPlan");
		String searchFlag = request.getParameter("searchFlag");
		String orderType = request.getParameter("orderType");//订单类型
		//String produceLine = URLDecoder.decode(request.getParameter("produceLine"),"UTF-8");
		String team     = request.getParameter("team");//业务组
		String mateType = request.getParameter("mateType");//三大件
		
		String where = " 1=1 ";
		//*** 关键字
		if(notEmpty(key1) && notEmpty(key2)){
			where = " full_field like '%"+key1+"%' AND full_field like '%"+key2+"%' ";
		}else{
			if(notEmpty(key1)){
				where = " full_field like '%"+key1+"%' ";
			}
			if(notEmpty(key2)){
				where = " full_field like '%"+key2+"%' ";
			}
		}
		
		//*** 快捷查询
		String having1 =" hideFlag='F' ";//false：不显示隐藏
		String having2 = " computeStockinQty+0 < orderQty+0 ";//成品未全部入库
		String orderby = " A.deliveryDate ";
		
		if(("U").equals(searchFlag)){
			//未安排
			having1 += " AND produceLineFlag = '0' AND filterFlag='0' ";//过滤掉当前任务
		}else if(("C").equals(searchFlag)){
			//当前任务
			having1 += " AND produceLineFlag = '1' AND finishFlag = '0' AND filterFlag='0' ";//只显示当前任务
			orderby = " e.produceLine,e.sortNo+0 ";
			
		}else if(("B").equals(searchFlag)){
			//料已备齐
			having1 += " AND produceLineFlag = '1' AND finishFlag = 'B' ";//中长期生产计划
		}else if(("E").equals(searchFlag)){
			//异常数据
			having1 += " AND filterFlag='1' ";
		}
		
		//*** 常规订单，配件单
		String where3 = " SUBSTRING_INDEX(SUBSTRING_INDEX(A.materialId,'.',2),'.',-1) NOT IN ('BTR','BNC','CGR','PJ') ";
		
		//***业务组
		if(("999").equals(team)){
			team = "";
		}
		
		//***五金，电子，自制件分类
		if(("A").equals(mateType)){//全部
			having1 += "";
		}else if(("W").equals(mateType)){
			having1 += " AND wjDate <'"+CalendarUtil.getToDay()+"'";
		}else if(("D").equals(mateType)){
			having1 += " AND dzDate <'"+CalendarUtil.getToDay()+"'";		
		}else if(("Z").equals(mateType)){
			having1 += " AND zzDate <'"+CalendarUtil.getToDay()+"'";	
		}
		
		userDefinedSearchCase.put("team", team);	
		userDefinedSearchCase.put("orderType", orderType);	
		baseQuery = new BaseQuery(request, dataModel);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		
		sql = sql.replace("#0",where );
		sql = sql.replace("#1",having1);
		sql = sql.replace("#2",having2);
		sql = sql.replace("#3",where3);
		sql = sql.replace("#4",orderby);

		List<String> list = new ArrayList<String>();
		list.add(where);
		list.add(having1);
		list.add(having2);
		list.add(where3);
		list.add(orderby);
		
		System.out.println("订单跟踪查询："+sql);
		baseQuery.getYsQueryData(sql,list,iStart, iEnd);	 
		
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
		dataModel.setQueryFileName("/business/order/ordertrackquerydefine");
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
		dataModel.setQueryFileName("/business/order/ordertrackquerydefine");
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
		    updatePurchaseOrderDetail(contractId,materialId,newDeliveryDate);
		    
		    ArrayList<HashMap<String, String>> deliverDate = getZPTimeFromContract(YSId);		    
		
		    updateProducePlan(YSId,deliverDate);		    		        

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
			String materialId,
			String newDeliveryDate) throws Exception{
		
		String where = " contractId='" + contractId +
				"' AND materialId='" + materialId +
				"' AND deleteFlag='0' ";
		
		List<B_PurchaseOrderDetailData> list = new B_PurchaseOrderDetailDao().Find(where);
		
		if(list.size() > 0){
			B_PurchaseOrderDetailData db = list.get(0);
			db.setNewdeliverydate(newDeliveryDate);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"newDeliveryDate",userInfo);
			copyProperties(db,commData);
			
			new B_PurchaseOrderDetailDao().Store(db);
			
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
			ArrayList<HashMap<String, String>> times) throws Exception{

		String zzTime = "";
		String dzTime = "";
		String wjTime = "";
		String zpTime = "";
		
		for(HashMap<String, String> map:times){
			String type = map.get("mateType");
			String date = map.get("deliveryDate");
			if(("Z").equals(type)){
				zzTime = date;
			}
			if(("D").equals(type)){
				dzTime = date;
			}
			if(("W").equals(type)){
				wjTime = date;
			}
			if(("A").equals(type)){
				zpTime = date;
			}			
		}
	
		String where = "YSId ='" + YSId +"' AND deleteFlag='0' ";
		List<B_ProducePlanData> list = new B_ProducePlanDao().Find(where);
		if(list.size() > 0){
			B_ProducePlanData plan = list.get(0); 
			plan.setFinishflag(finishFlag);
			plan.setReadydate(zpTime);
			plan.setZzdate(zzTime);
			plan.setDzdate(dzTime);
			plan.setWjdate(wjTime);
			
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"update",userInfo);
			copyProperties(plan,commData);	
			
			new B_ProducePlanDao().Store(plan);
			
		}else{
			B_ProducePlanData plan = new B_ProducePlanData();
			plan.setFinishflag(finishFlag);
			plan.setYsid(YSId);
			plan.setReadydate(zpTime);
			plan.setZzdate(zzTime);
			plan.setDzdate(dzTime);
			plan.setWjdate(wjTime);
			
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"insert",userInfo);
			copyProperties(plan,commData);	
			guid = BaseDAO.getGuId();				
			plan.setRecordid(guid);
			
			new B_ProducePlanDao().Create(plan);
		}
		
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
			ArrayList<HashMap<String, String>> times) throws Exception{
	
		String where = "YSId ='" + YSId +"' AND deleteFlag='0' ";
		List<B_ProducePlanData> list = new B_ProducePlanDao().Find(where);
		String zzTime = "";
		String dzTime = "";
		String wjTime = "";
		String zpTime = "";
		
		for(HashMap<String, String> map:times){
			String type = map.get("mateType");
			String date = map.get("deliveryDate");
			if(("Z").equals(type)){
				zzTime = date;
			}
			if(("D").equals(type)){
				dzTime = date;
			}
			if(("W").equals(type)){
				wjTime = date;
			}
			if(("A").equals(type)){
				zpTime = date;
			}
			
		}
		
		if(list.size() > 0){
			B_ProducePlanData plan = list.get(0); 
			plan.setReadydate(zpTime);
			plan.setZzdate(zzTime);
			plan.setDzdate(dzTime);
			plan.setWjdate(wjTime);
			
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"update",userInfo);
			copyProperties(plan,commData);	
			
			new B_ProducePlanDao().Store(plan);
			
		}		
	}
	
	public void orderTrackDetailInit() throws Exception{

		String YSId = request.getParameter("YSId");	
		
		ArrayList<HashMap<String, String>> zpTime = getZPTimeFromContract(YSId);
		
		if( zpTime.size() == 0 ){
			setMaterialFinished(YSId,"B","");//料已备齐
		}else{
			setMaterialFinished(YSId,"0",zpTime);//当前跟踪	
		}
		
		getOrderDetailByYSId(YSId);
	}
	
	
	private ArrayList<HashMap<String, String>> getZPTimeFromContract(String YSId) throws Exception{
		
		ArrayList<HashMap<String, String>> zptime = new ArrayList<HashMap<String, String>>();
		dataModel.setQueryFileName("/business/order/ordertrackquerydefine");
		dataModel.setQueryName("getMaxStokcinDate");		
		baseQuery = new BaseQuery(request, dataModel);
		//userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);	
		String sql = baseQuery.getSql();
		sql = sql.replace("#", YSId);
		
		baseQuery.getYsFullData(sql,YSId);
		
		boolean timeFlag = false;
		for(int i=0;i<dataModel.getYsViewData().size();i++){
			String deliveryDate = dataModel.getYsViewData().get(i).get("deliveryDate");
			if(notEmpty(deliveryDate)){
				timeFlag = true;
				break;
			}
		}
		if(timeFlag){
			zptime = dataModel.getYsViewData();
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
	
	public void orderTrackingSearchInit() throws Exception{

		ArrayList<HashMap<String, String>> list = getYewuzurById();

		model.addAttribute("yewuzu",list);
		model.addAttribute("year",util.getListOption(DicUtil.BUSINESSYEAR, ""));
	}
	
}
