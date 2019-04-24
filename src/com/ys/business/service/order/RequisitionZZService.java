package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.RequisitionModel;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_RawRequirementDao;
import com.ys.business.db.dao.B_RequisitionDao;
import com.ys.business.db.dao.B_RequisitionDetailDao;
import com.ys.business.db.data.B_MaterialData;
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
		System.out.println("自制件领料申请："+sql);
		
		baseQuery.getYsQueryData(sql,having,iStart, iEnd);	 

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
	
		String ysid = request.getParameter("data");
		model.addAttribute("YSId",ysid);
		
		getOrderDetail(ysid);
		
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
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getRawMaterial(String makeType) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String ysid = request.getParameter("YSId");	

		
		//直接从原材料需求表中取值
		modelMap = getRawRequirement(ysid,makeType);
		
		
		ArrayList<HashMap<String, String>> dbData = 
				(ArrayList<HashMap<String, String>>)modelMap.get("data");
		if (dbData.size() > 0) {
			return modelMap;
		}
		
		addRawRequirement(ysid);
		
		//再次取得
		modelMap = getRawRequirement(ysid,makeType);
		
		return modelMap;
	}
	
	private void addRawRequirement(String YSId) throws Exception{
		
		ArrayList<HashMap<String, String>> list3 = getRawMaterialGroupList(YSId);	
		
		for(HashMap<String, String> map2:list3){

			 String parentMat = map2.get("materialId").substring(0, 3);
			 String type = "";
			 if( ("F02").equals(parentMat)){//吹塑:F02
				 type = Constants.REQUISITION_BLOW;
			 }else if( ("F01").equals(parentMat)){//吸塑:F01
				 type = Constants.REQUISITION_BLISTE;
			 }else{//以外
				 type = Constants.REQUISITION_INJECT;
			 }
			 
			String rawmater = map2.get("rawMaterialId");//二级物料名称(原材料)
			float purchase = 0;//采购量
			String unit = DicUtil.getCodeValue("换算单位" + map2.get("rawUnit"));
			float funit = stringToFloat(unit);
			float totalQuantity = stringToFloat(map2.get("purchaseQuantity"));//采购量
			float requirement = ( totalQuantity / funit);
			
			B_RawRequirementData raw = new B_RawRequirementData();
			raw.setYsid(YSId);
			raw.setMaterialid(rawmater);
			raw.setSupplierid(map2.get("supplierId"));
			raw.setQuantity(floatToString(requirement));
			raw.setRawtype(type);
			
			insertRawRequirement(raw);
			
			//更新虚拟库存
			updateMaterial("原材料领料时做成物料需求表",rawmater,purchase,requirement);						
		}
		
	}
	
	//更新虚拟库存:生成物料需求时增加“待出库”
	@SuppressWarnings("unchecked")
	private void updateMaterial(
			String action,
			String materialId,
			float purchaseIn,
			float requirementOut) throws Exception{
	
		B_MaterialData data = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();
		
		String where = "materialId ='"+ materialId + "' AND deleteFlag='0' ";
		
		List<B_MaterialData> list = 
				(List<B_MaterialData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		insertStorageHistory(data,action,String.valueOf(requirementOut));//保留更新前的数据
		
		//当前库存数量
		float iOnhand  = stringToFloat(data.getQuantityonhand());//实际库存
		float iWaitOut = stringToFloat(data.getWaitstockout());//待出库
		float iWaitIn  = stringToFloat(data.getWaitstockin());//待入库
		
		iWaitOut = iWaitOut + requirementOut;
		iWaitIn = iWaitIn + purchaseIn;
		
		//虚拟库存 = 当前库存 + 待入库 - 待出库
		float availabeltopromise = iOnhand + iWaitIn - iWaitOut;		
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchasePlanInsert",userInfo);
		copyProperties(data,commData);
		
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));//虚拟库存
		data.setWaitstockout(String.valueOf(iWaitOut));//待出库
		data.setWaitstockin(String.valueOf(iWaitIn));//待入库
		
		dao.Store(data);
		
	}
		
	@SuppressWarnings("unchecked")
	private void insertRawRequirement(B_RawRequirementData raw) throws Exception{
		
		String where = "YSId = '" + raw.getYsid() +"' AND materialId='" + raw.getMaterialid() +"' ";
		
		List<B_RawRequirementData> list = new B_RawRequirementDao().Find(where);
		
		if(list.size() > 0 ){
			//update
			B_RawRequirementData db = list.get(0);
	
			copyProperties(db,raw);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"更新采购方案物料",userInfo);
			copyProperties(db,commData);
			
			new B_RawRequirementDao().Store(db);	
			
		}else{
			//insert
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"新增采购方案物料",userInfo);
			copyProperties(raw,commData);
	
			guid = BaseDAO.getGuId();
			raw.setRecordid(guid);
			
			new B_RawRequirementDao().Create(raw);	
		}		
	}
	
	public ArrayList<HashMap<String, String>> getRawMaterialGroupList(
			String YSId ) throws Exception {		

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getRawMaterialFromPlan");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", YSId);
		return baseQuery.getYsFullData(sql);
		
	}
	


	private HashMap<String, Object> getRawRequirement(
			String ysid,String type) throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		dataModel.setQueryName("getRawRequirementById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", ysid);	
		userDefinedSearchCase.put("rawType", type);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", ysid);
		System.out.println("自制件领料："+sql);
		
		baseQuery.getYsFullData(sql);

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
		String sql = baseQuery.getSql();
		sql = sql.replace("#", YSId);
		System.out.println("订单查询："+sql);
		
		baseQuery.getYsFullData(sql,YSId);
		
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
