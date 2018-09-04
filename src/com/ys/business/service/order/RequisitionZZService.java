package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.RequisitionModel;
import com.ys.business.db.dao.B_ProductionTaskDao;
import com.ys.business.db.dao.B_RawRequirementDao;
import com.ys.business.db.dao.B_RequisitionDao;
import com.ys.business.db.dao.B_RequisitionDetailDao;
import com.ys.business.db.data.B_ProductionTaskData;
import com.ys.business.db.data.B_RawRequirementData;
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
	
	private HashMap<Object, Object> getRequisitionZZData(
			String makeType,String requisitionSts,int viewSize,
			ArrayList<HashMap<String, String>>  list) throws Exception{
		
		HashMap<Object, Object> hp = new HashMap<>();
		ArrayList<HashMap<String, String>> blow = new ArrayList<HashMap<String, String>>();
		
		//String makeType = request.getParameter("makeType");
		int size=0;
		for(HashMap<String, String>map:list){
			 
			//String subid = map.get("rawMaterialId").substring(0, 3);	
			String ysid = map.get("YSId");

			//确认领料状态
			B_RequisitionData task = new B_RequisitionData();
			String where = "collectYsid like '%" + ysid + "%'" +" AND requisitionType='"+makeType+"' AND deleteFlag='0' "; 
			task = checkRequisitionExsit(where);
			if(task == null){
				map.put("requisitionSts", Constants.STOCKOUT_1);//待申请
				map.put("requisitionId", "");//领料单编号
			}else{
				String sts = task.getRequisitionsts();
				map.put("requisitionSts", sts);//待出库/已出库
				map.put("requisitionId", task.getRequisitionid());//领料单编号
				map.put("taskId", task.getYsid());//任务编号
			}
			
			if(notEmpty(requisitionSts) && !requisitionSts.equals(map.get("requisitionSts")))
				continue;//过滤页面传来的状态值			
			
			if(size >= viewSize ){				
				blow.add(map);//截取页面允许显示的最大条数					
			}
			size++;
		}

		hp.put("list", blow);
		hp.put("recordCnt", size);
		return hp;
	}
	
	public HashMap<String, Object> doSearch(String makeType, String data,String formId) throws Exception {

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
		String requisitionSts = request.getParameter("requisitionSts");
		if(notEmpty(key1) || notEmpty(key2))
			requisitionSts = "";//有指定查询条件时,不再限定其业务状态
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		//userDefinedSearchCase.put("requisitionSts", requisitionSts);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String having ="";
		//替换数据源
		if(makeType.equals(Constants.REQUISITION_BLISTE)){
			
			dataModel.setQueryName("requisitionListForF01");//吸塑
		}else if(makeType.equals(Constants.REQUISITION_BLOW)){
			
			dataModel.setQueryName("requisitionListForF02");//吹塑		
		}else{
			
			dataModel.setQueryName("requisitionListForB01");//注塑
			
		}
		if(("010").equals(requisitionSts)){
			
			having = " requisitionQty = 0 ";//未申请
		}else if(("020").equals(requisitionSts)){
			
			having = " requisitionSts = '020' ";//待领料
		}else if(("030").equals(requisitionSts)){
			
			having = " requisitionSts = '030' ";//已出库
		}else if(("040").equals(requisitionSts)){
			
			having = " bstockinQty > 0 AND requisitionQty = 0 ";//成品已入库但未领料
		}else{
			having = " 1=1 ";
		}
		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#", having);
		
		baseQuery.getYsQueryData(sql,having,iStart, iEnd);	 

		if ( iEnd > dataModel.getYsViewData().size()){
			iEnd = dataModel.getYsViewData().size();
		}		
			
		/*	
		HashMap<Object, Object> hp = 
				getRequisitionZZData(
						makeType,
						requisitionSts,
						iEnd,
						dataModel.getYsViewData());
		
		ArrayList<HashMap<String, String>> list = 
				(ArrayList<HashMap<String, String>>) hp.get("list");
		int recordCnt = (int) hp.get("recordCnt");
		if ( iEnd > list.size()){			
			iEnd = list.size();
		}	
		*/	
		modelMap.put("sEcho", sEcho);
		modelMap.put("recordsTotal", dataModel.getRecordCount());
		modelMap.put("recordsFiltered", dataModel.getRecordCount());		
		modelMap.put("data", dataModel.getYsViewData());
		modelMap.put("keyword1",key1);
		modelMap.put("keyword2",key2);	
		
		return modelMap;		

	}
	

	public void addInit() throws Exception {
	
		String ysid = request.getParameter("data");
		model.addAttribute("YSId",ysid);
		
		getOrderDetail(ysid);
		
		/*
		B_ProductionTaskData task = new B_ProductionTaskData();
		String ysids = request.getParameter("data");
		String taskId = request.getParameter("taskId");	
		task = new B_ProductionTaskData();
		if(isNullOrEmpty(taskId)){
			task = getNewTaskId(task);			
		}else{
			String where = " taskId='" + taskId + "' AND deleteFlag='0' ";
			task = checkTaskIdExsit(where);
		}			
		task.setCollectysid(ysids);	
		reqModel.setTask(task);
		model.addAttribute("currentYsids",ysids);
		model.addAttribute("task",task);	
		*/
	}

	public void getRequisitionHistoryInit() throws Exception {
		
		String YSId  = request.getParameter("YSId");
		
		getOrderDetail(YSId);
	}
	

	public B_RequisitionData checkRequisition() throws Exception {
		
		String YSId  = request.getParameter("YSId");
		String makeType  = request.getParameter("makeType");
		String where = " YSId='"+YSId+"' "+" AND requisitionType='"+makeType+"' AND deleteFlag='0' ";
		return checkRequisitionExsit(where);
		//getTaskDetail(taskId);
	}
	
 

	public void updateInit() throws Exception {

		String YSId = request.getParameter("YSId");
		//String requisitionId = request.getParameter("requisitionId");
		
		//订单详情
		getOrderDetail(YSId);
		//领料单
		getRequisitionDetailForEdit();
	
	}
	
	public void getRequisitionZZDetail() throws Exception {

		String YSId = request.getParameter("YSId");
		//订单详情
		getOrderDetail(YSId);
	
	}
	
	public HashMap<String, Object> getRawMaterial(String makeType) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String ysid = request.getParameter("YSId");	

		/*
		//直接从原材料需求表中取值
		modelMap = getRawRequirement(ysid,makeType);
		
		ArrayList<HashMap<String, String>> dbData = 
				(ArrayList<HashMap<String, String>>)modelMap.get("data");
		if (dbData.size() > 0) {
			return modelMap;
		}
		*/

		//删除现有原材料需求表
		deleteRawRequirement(ysid);
		
		//原材料需求表未找到时，从采购方案重新组合，并插入原材料需求表
		ArrayList<HashMap<String, String>> list = getRawMaterialList(ysid);
		
		ArrayList<HashMap<String, String>> blow = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> blister = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> injection = new ArrayList<HashMap<String, String>>();
		
		for(HashMap<String, String>map:list){
			 	
			 String subMat = map.get("parentMaterialId").substring(0, 3);
			 String type = "";
			 if( ("F02").equals(subMat)){//吹塑:F02
				 blow.add(map);
				 type = Constants.REQUISITION_BLOW;
			 }else if( ("F01").equals(subMat)){//吸塑:F01
				 blister.add(map);
				 type = Constants.REQUISITION_BLISTE;
			 }else{//以外
				 injection.add(map);
				 type = Constants.REQUISITION_INJECT;
			 }

			insertRawRequirement(map,ysid,type);
		}
		/*
		if( Constants.REQUISITION_BLOW.equals(makeType) ){//吹塑:F02	
			modelMap.put("data", blow);
		}else if( Constants.REQUISITION_BLISTE.equals(makeType) ){//吸塑:F01
			modelMap.put("data", blister);			 
		}else{//以外
			modelMap.put("data", injection);			 
		}
		*/
		
		//再次取得
		modelMap = getRawRequirement(ysid,makeType);
		
		return modelMap;
	}
	
	private void insertRawRequirement(
			HashMap<String, String> map,
			String YSId,
			String type) throws Exception{
		
		B_RawRequirementData raw = new B_RawRequirementData();

		//String unit = map.get("unitId");
		String unit = DicUtil.getCodeValue("换算单位" + map.get("unitId"));
		float funit = stringToFloat(unit);
		float quantity = stringToFloat(map.get("purchaseQuantity"));
		String compute = floatToString( quantity / funit );
		//String zzunit = map.get("zzunitId");
		//float fzzunit = stringToFloat(DicUtil.getCodeValue(zzunit));

		raw.setQuantity(compute);
		raw.setYsid(YSId);
		raw.setMaterialid(map.get("materialId"));
		raw.setRawtype(type);
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RawRequirementInsert",userInfo);
		copyProperties(raw,commData);

		String guid = BaseDAO.getGuId();
		raw.setRecordid(guid);
		
		new B_RawRequirementDao().Create(raw);
		
	}
	
	private void deleteRawRequirement(String YSId) throws Exception{
		
		String where = " YSId ='" + YSId + "' ";
		try{
			new B_RawRequirementDao().RemoveByWhere(where);			
		}catch(Exception e){
			
		}
		
	}
	
	private HashMap<String, Object> getRawRequirement(
			String ysid,String type) throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		dataModel.setQueryName("getRawRequirementById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", ysid);	
		userDefinedSearchCase.put("rawType", type);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data",dataModel.getYsViewData());
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getMaterialZZ(String makeType) throws Exception {

		String ysid = request.getParameter("YSId");;			
			
		//自制品
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		ArrayList<HashMap<String, String>> list = getMaterialZZList(ysid);
		
		ArrayList<HashMap<String, String>> blow = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> blister = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> injection = new ArrayList<HashMap<String, String>>();
		
		for(HashMap<String, String>map:list){
			 String rawmaterialId = map.get("rawmaterialId");	
			 String materialId = map.get("materialId");		
			 String subMat = "";
			 if(isNullOrEmpty(rawmaterialId)){
				 continue;
			 }else{
				 subMat = materialId.substring(0, 3);
			 }
			 if( ("F02").equals(subMat)){//吹塑:F02
				 blow.add(map);
			 }else if( ("F01").equals(subMat) ){//吸塑:F01
				 blister.add(map);
			 }else{//以外
				 injection.add(map);
			 }
		}
		if( Constants.REQUISITION_BLOW.equals(makeType) ){//吹塑:F02	
			modelMap.put("data", blow);
		}else if( Constants.REQUISITION_BLISTE.equals(makeType) ){//吸塑:F01
			modelMap.put("data", blister);			 
		}else{//以外
			modelMap.put("data", injection);			 
		}
		 
		return modelMap;
	}
	
	public void updateAndView() throws Exception {

		String ysid = update();

		//任务详情
		getOrderDetail(ysid);
	}
	
	public void insertAndView() throws Exception {

		
		String ysid = insert();

		//任务详情
		getOrderDetail(ysid);
	}
	

	private String update(){
		
		String ysid = "";
		ts = new BaseTransaction();

		try {
			ts.begin();
			
			B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
			List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();
			//B_ProductionTaskData task = reqModel.getTask();

			ysid = reqData.getYsid();
			
			//领料单更新处理
			String requisitionid = reqData.getRequisitionid();
			reqData.setOriginalrequisitionid(reqData.getRequisitionid());
			updateRequisition(reqData);

			//旧的明细删除处理
			deleteRequisitionDetail(requisitionid);
			
			//新的领料单明细						
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				
				if(quantity <= 0)
					continue;
				
				data.setRequisitionid(requisitionid);
				insertRequisitionDetail(data);								
			
			}
			
			ts.commit();			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		return ysid;
	}
	
	private String insert(){
		
		String ysid = "";
		ts = new BaseTransaction();

		try {
			ts.begin();
			B_RequisitionData reqData  = reqModel.getRequisition();
			ysid = reqData.getYsid();
			List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();

			reqData = getRequisitionZZId(reqData);
			String requisitionId = reqData.getRequisitionid();
			reqData.setRequisitionsts(Constants.STOCKOUT_2);//待出库
			
			insertRequisition(reqData);//领料申请insert
			
			//领料明细
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				
				if(quantity <= 0)
					continue;
				
				data.setRequisitionid(requisitionId);
				insertRequisitionDetail(data);
			}
			
			ts.commit();			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		return ysid;
	}
	
	private void insertRequisition(
			B_RequisitionData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RequisitionInsert",userInfo);
		copyProperties(stock,commData);
		stock.setRequisitionuserid(userInfo.getUserId());//默认为登陆者

		String guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		stock.setRequisitiondate(CalendarUtil.fmtYmdDate());
		stock.setRequisitionuserid(userInfo.getUserId());
		
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
			
	private void updateRequisition(
			B_RequisitionData data) throws Exception{
		
		//
		B_RequisitionData db = new B_RequisitionDao(data).beanData;

		copyProperties(db,data);
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"RequisitionUpdate",userInfo);
		copyProperties(db,commData);
		db.setRequisitiondate(CalendarUtil.fmtYmdDate());	
		db.setRequisitionuserid(userInfo.getUserId());
		
		new B_RequisitionDao().Store(db);
	}
	

	@SuppressWarnings("unchecked")
	private void deleteRequisitionDetail(
			String requisitionId) throws Exception{
		
		B_RequisitionDetailDao dao = new B_RequisitionDetailDao();
		//
		String where = "requisitionId = '" + requisitionId  +"' AND deleteFlag = '0' ";
		List<B_RequisitionDetailData> list  = 
				new B_RequisitionDetailDao().Find(where);
		if(list ==null || list.size() == 0)
			return ;
		
		for(B_RequisitionDetailData dt:list){
			
			dao.Remove(dt);
			
		}
		
	}

		
	@SuppressWarnings("unchecked")
	public void doDelete(String recordId) throws Exception{
		
		try {
			
			ts = new BaseTransaction();					
			
			String recordid = request.getParameter("recordId");
			B_RequisitionData req = new B_RequisitionData();
			req.setRecordid(recordid);
			req = new B_RequisitionDao(req).beanData;
			if(req ==null || ("").equals(req))
				return;

			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"RequisitionDelete",userInfo);
			copyProperties(req,commData);
			new B_RequisitionDao().Store(req);
			
			String requisitionId = req.getRequisitionid();
			String astr_Where = " requisitionId='" +requisitionId+"' AND deleteFlag='0' ";
			List<B_RequisitionDetailData> list = new B_RequisitionDetailDao().Find(astr_Where);					
			
			ts.begin();	
			for(B_RequisitionDetailData dt:list){
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"RequisitionDelete",userInfo);
				copyProperties(dt,commData);
				new B_RequisitionDetailDao().Store(dt);				
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
	}

	public B_RequisitionData getRequisitionZZId(
			B_RequisitionData data) throws Exception {
		
		String parentId = BusinessService.getshortYearcode()+
				BusinessConstants.SHORTNAME_LL;
		dataModel.setQueryName("getMAXRequisitionId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();
		//sql里已经是MAX+1
		String subid = dataModel.getYsViewData().get(0).get("MaxSubId");
		String id =  BusinessService.getRequisitionId(parentId,subid);
		data.setRequisitionid(id);
		data.setParentid(parentId);
		data.setSubid(subid);
		
		return data;
	}
	

	public B_ProductionTaskData getNewTaskId(
			B_ProductionTaskData data) throws Exception {
		
		String parentId = BusinessService.getshortYearcode()
				+ BusinessConstants.SHORTNAME_RW;
		
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
	public B_RequisitionData checkRequisitionExsit(String where) throws Exception{

		B_RequisitionData rtnDt= null;
		List<B_RequisitionData> list = new B_RequisitionDao().Find(where);	
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
	
	public ArrayList<HashMap<String, String>> getRawMaterialList(
			String ysid) throws Exception {
				
		dataModel.setQueryName("getRawMaterialList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", ysid);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();

		return dataModel.getYsViewData();		
	}
	
	public ArrayList<HashMap<String, String>> getMaterialZZList(
			String ysids) throws Exception {
				
		dataModel.setQueryName("zzmaterialFromPlan");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", ysids);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		return dataModel.getYsViewData();		
	}
	
	public HashMap<String, Object> getRequisitionHistory(
			String YSId,String makeType) throws Exception {
		
		dataModel.setQueryName("getRequisitionById");		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("requisitionType", makeType);
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
	

	public void getRequisitionDetailForEdit() throws Exception {

		String requisitionId = request.getParameter("requisitionId");
		
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");		
		dataModel.setQueryName("requisitionEdit");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("requisitionId", requisitionId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		model.addAttribute("detail",dataModel.getYsViewData().get(0));
			
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
