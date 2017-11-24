package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.ArrivalModel;
import com.ys.business.action.model.order.RequisitionModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_ProductionTaskDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDao;
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.dao.B_RequisitionDao;
import com.ys.business.db.dao.B_RequisitionDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_ProductionTaskData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchasePlanData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;
import com.ys.business.db.data.B_RequisitionData;
import com.ys.business.db.data.B_RequisitionDetailData;
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
public class RequisitionZZService extends CommonService {

	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_RequisitionDao dao;
	private B_RequisitionDetailDao detailDao;
	private RequisitionModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public RequisitionZZService(){
		
	}

	public RequisitionZZService(Model model,
			HttpServletRequest request,
			HttpSession session,
			RequisitionModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_RequisitionDao();
		this.detailDao = new B_RequisitionDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
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

		String[] keyArr = getSearchKey(Constants.FORM_REQUISITION,data,session);
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

		//dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderListForZZ");	
		baseQuery = new BaseQuery(request, dataModel);
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
	

	public void addInit() throws Exception {
	
		B_ProductionTaskData task = new B_ProductionTaskData();
		String ysids = request.getParameter("data");
		String newYsid = "";
		String[] list = ysids.split(",");
		boolean taskIdFlag = true;
		for(String ysid:list){
			String where = "collectYsid like '%" + ysid + "%'"; 
			task = checkTaskIdExsit(where);
			if(task != null){
				taskIdFlag = false;				
				break;
			}				
		}
		if(taskIdFlag){
			task = new B_ProductionTaskData();
			task = getTaskId(task);
			newYsid = ysids;
		}else{
			newYsid = ysids + task.getCollectysid();
			String[] newList = newYsid.split(",");
			List<String> listTmp =  new ArrayList<String>();
			for(String str:newList){
				listTmp.add(str.trim());
			}
            //去掉重复项
			removeDuplicate((ArrayList<String>) listTmp);
			//去掉首位的括号和中间的空格
			newYsid = StringUtils.strip(listTmp.toString().replaceAll(" +", ""),"[]");
		}
		
		task.setCollectysid(newYsid);	
		reqModel.setTask(task);
		model.addAttribute("currentYsids",ysids);
		model.addAttribute("task",task);	
	}

	public void getRequisitionHistoryInit() throws Exception {
		String taskId  = request.getParameter("taskId");
		getTaskDetail(taskId);
	}
	
	public void removeDuplicate(ArrayList arlList)      
	{      
		HashSet h = new HashSet(arlList);      
		arlList.clear();      
		arlList.addAll(h);      
	}  

	public void updateInit() throws Exception {

		String YSId = request.getParameter("YSId");
		//String requisitionId = request.getParameter("requisitionId");
		
		//订单详情
		getOrderDetail(YSId);
		//领料单
		getRequisitionDetail();
	
	}
	public HashMap<String, Object> getRawMaterial() throws Exception {

		String taskId = request.getParameter("taskId");	
		String ysids = request.getParameter("YSId");			
		model.addAttribute("ysids",ysids);//选中的耀升编号
	
		//物料需求表
		return getRawMaterialList(ysids,taskId);
	}
	public void updateAndView() throws Exception {

		String YSId = update();

		//订单详情
		getOrderDetail(YSId);
	}
	
	public void insertAndView() throws Exception {

		
		String taskId = insert();

		//任务详情
		getTaskDetail(taskId);
	}
	

	private String update(){
		
		String YSId = "";
		ts = new BaseTransaction();

		try {
			ts.begin();
			
			B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
			List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();

			YSId = reqData.getYsid();
			//旧的领料单号
			String oldId = reqData.getRequisitionid();
			
			//领料单更新处理
			reqData = getRequisitionZZId(reqData,"");
			String requisitionid = reqData.getRequisitionid();//新的单号
			reqData.setOriginalrequisitionid(oldId);
			updateRequisition(reqData);

			//旧的明细删除处理
			deleteRequisitionDetail(YSId,oldId);
			
			//新的领料单明细						
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				float overQuty = stringToFloat(data.getOverquantity());//超领
				
				if(quantity <= 0)
					continue;
				
				data.setRequisitionid(requisitionid);
				insertRequisitionDetail(data);
								
				//更新累计领料数量
				//updatePurchasePlan(YSId,data.getMaterialid(),quantity);
				
				//更新库存
				updateMaterialStock(data.getMaterialid(),quantity,overQuty);
			
			}
			
			//更新订单状态:待交货
			updateOrderDetail(YSId);
			
			
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
		
		return YSId;
	}
	
	private String insert(){
		
		String taskId = "";
		ts = new BaseTransaction();

		try {
			ts.begin();
			B_RequisitionData reqData  = reqModel.getRequisition();
			B_ProductionTaskData task = reqModel.getTask();
			//B_RequisitionData reqData = new B_RequisitionData();
			List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();

			//生产任务处理开始*******************
			//取得任务编号
			String recordId = task.getRecordid();
			if(isNullOrEmpty(recordId)){	
				insertProductionTask(task);				
			}else{
				updateProductionTask(task);
			}				
			//生产任务处理结束*******************
			
			//String currentYsids = reqDt.getCollectysid();
			taskId = task.getTaskid();
			reqData = getRequisitionZZId(reqData,taskId);
			String requisitionId = reqData.getRequisitionid();
			reqData.setYsid(taskId);
			//reqData.setCollectysid(currentYsids);			
			
			insertRequisition(reqData);//领料申请insert
			
			//领料明细
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				float overQuty = stringToFloat(data.getOverquantity());//超领
				
				if(quantity <= 0)
					continue;
				
				data.setRequisitionid(requisitionId);
				insertRequisitionDetail(data);
				
				//更新库存
				updateMaterialStock(data.getMaterialid(),quantity,overQuty);
			
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
		
		return taskId;
	}
	
	private void insertRequisition(
			B_RequisitionData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RequisitionInsert",userInfo);
		copyProperties(stock,commData);
		stock.setStoreuserid(userInfo.getUserId());//默认为登陆者

		String guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		stock.setRequisitiondate(CalendarUtil.fmtYmdDate());
		
		dao.Create(stock);
	}
	
	private void insertRequisitionDetail(
			B_RequisitionDetailData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RequisitionInsert",userInfo);
		copyProperties(stock,commData);

		String guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		
		detailDao.Create(stock);
	}
	

	private void insertProductionTask(
			B_ProductionTaskData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"B_ProductionTaskInsert",userInfo);
		copyProperties(stock,commData);
		String guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		
		new B_ProductionTaskDao().Create(stock);
	}
	
	private void updateProductionTask(
			B_ProductionTaskData stock) throws Exception{
		//插入新数据
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"B_ProductionTaskUpdate",userInfo);
				copyProperties(stock,commData);
		new B_ProductionTaskDao().Store(stock);		
	}
	
	//更新当前库存:领料时，减少“当前库存”,减少“待出库”,
	@SuppressWarnings("unchecked")
	private void updateMaterialStock(
			String materialId,
			float reqQuantity,
			float overQuantity) throws Exception{
	
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
		//float ireqQuantity = stringToFloat(reqQuantity);				
		float iNewQuantiy = iQuantity - reqQuantity;		
		
		//待入库数量
		float istockin = stringToFloat(data.getWaitstockin());		
		//float iNewStockIn = istockin - reqQuantity;
		
		//待出库
		float waitstockout = stringToFloat(data.getWaitstockout());
		waitstockout = waitstockout - reqQuantity + overQuantity;//超领部分不计入
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float availabeltopromise = iNewQuantiy + istockin - waitstockout;
		
		data.setQuantityonhand(String.valueOf(iNewQuantiy));
		data.setWaitstockout(String.valueOf(waitstockout));
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}
		
	@SuppressWarnings("unchecked")
	private void updateOrderDetail(
			String ysid) throws Exception{
		String where = "YSId = '" + ysid  +"' AND deleteFlag = '0' ";
		List<B_OrderDetailData> list  = new B_OrderDetailDao().Find(where);
		if(list ==null || list.size() == 0)
			return ;	
		
		//更新DB
		B_OrderDetailData data = list.get(0);
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		data.setStatus(Constants.ORDER_STS_3);//待交货	
		
		new B_OrderDetailDao().Store(data);
	}
	
	
	private void updateRequisition(
			B_RequisitionData data) throws Exception{
		
		//
		B_RequisitionData db = new B_RequisitionDao(data).beanData;

		copyProperties(db,data);
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"RequisitionUpdate",userInfo);
		copyProperties(db,commData);
		db.setRequisitiondate(CalendarUtil.fmtYmdDate());		
		
		new B_RequisitionDao().Store(db);
	}
	

	@SuppressWarnings("unchecked")
	private void deleteRequisitionDetail(
			String YSId,
			String requisitionId) throws Exception{
		
		B_RequisitionDetailDao dao = new B_RequisitionDetailDao();
		//
		String where = "requisitionId = '" + requisitionId  +"' AND deleteFlag = '0' ";
		List<B_RequisitionDetailData> list  = 
				new B_RequisitionDetailDao().Find(where);
		if(list ==null || list.size() == 0)
			return ;
		
		for(B_RequisitionDetailData dt:list){
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"RequisitionDelete",userInfo);
			copyProperties(dt,commData);
			
			dao.Store(dt);
			
			//更新累计领料数量(恢复)
			String mateId = dt.getMaterialid();
			float quantity = (-1) * stringToFloat(dt.getQuantity());
			//updatePurchasePlan(YSId,mateId,quantity);
			
			//更新库存(恢复)
			float overQuty = (-1) * stringToFloat(dt.getOverquantity());
			updateMaterialStock(mateId,quantity,overQuty);
		}
		
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

	public B_RequisitionData getRequisitionZZId(
			B_RequisitionData data,
			String parentId) throws Exception {
				
		dataModel.setQueryName("getMAXRequisitionId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();
		//sql里已经是MAX+1
		String subid = dataModel.getYsViewData().get(0).get("MaxSubId");
		String id =  BusinessService.getRequisitionZZId(parentId,subid);
		data.setRequisitionid(id);
		data.setParentid(parentId);
		data.setSubid(subid);
		
		return data;
	}
	

	public B_ProductionTaskData getTaskId(
			B_ProductionTaskData data) throws Exception {
		
		String parentId = BusinessService.getshortYearcode()
				+ BusinessConstants.SHORTNAME_LLZZ;
		
		dataModel.setQueryName("getMAXTaskId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();
		//sql里已经是MAX+1
		String subid = dataModel.getYsViewData().get(0).get("MaxSubId");
		String id =  BusinessService.getProductionTaskId(parentId,subid);
		data.setTaskid(id);
		data.setParentid(parentId);
		data.setSubid(subid);
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private void getTaskDetail(String taskId) throws Exception{
		String where = " taskid = '" + taskId + "' AND deleteFlag='0' ";
		List<B_ProductionTaskData> list = new B_ProductionTaskDao().Find(where);
		B_ProductionTaskData dt = null;
		if(list.size() > 0)
			dt = list.get(0);
		
		reqModel.setTask(dt);	
	}

	@SuppressWarnings("unchecked")
	public B_ProductionTaskData checkTaskIdExsit(String where) throws Exception{

		B_ProductionTaskData rtnDt=null;
		List<B_ProductionTaskData> list = new B_ProductionTaskDao().Find(where);	
		if(list != null && list.size() > 0){
			rtnDt = list.get(0);
		}
			
		return rtnDt;		
	}
	
	public HashMap<String, Object> getPurchasePlan(String YSId) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryName("getPurchasePlanByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());
			modelMap.put("data", dataModel.getYsViewData());
		}
		
		return modelMap;		
	}
	
	public HashMap<String, Object> getRawMaterialList(
			String ysids,
			String taskId) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();		
		dataModel.setQueryName("getRawMaterialList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", ysids);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql().replace("#", taskId);
		
		baseQuery.getYsFullData(sql);

		if(dataModel.getRecordCount() >0){
			modelMap.put("data", dataModel.getYsViewData());
		}
		
		return modelMap;		
	}
	
	public HashMap<String, Object> getRequisitionHistory(
			String taskId) throws Exception {
		
		dataModel.setQueryName("getRequisitionZZById");		
		baseQuery = new BaseQuery(request, dataModel);
		//自制品一次关联多个耀升编号,所以,领料单里的耀升编号实际存放的是任务编号
		userDefinedSearchCase.put("YSId", taskId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	
	public HashMap<String, Object> getRequisitionDetail() throws Exception {

		String requisitionId = request.getParameter("requisitionId");
		
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");		
		dataModel.setQueryName("updateRequisition");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("requisitionId", requisitionId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("detail",dataModel.getYsViewData().get(0));
		
		return modelMap;		
	}
	
	public HashMap<String, Object> getOrderDetail(
			String YSId) throws Exception {
		
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		model.addAttribute("order",dataModel.getYsViewData().get(0));
		
		return modelMap;		
	}
	
	
	public HashMap<String, Object> getRequisitionById(
			String YSId) throws Exception {
		
		dataModel.setQueryName("getRequisitionById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		
	}

}
