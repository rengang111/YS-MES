package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.ReceiveInspectionModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_InspectionProcessDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_ReceiveInspectionDao;
import com.ys.business.db.dao.B_ReceiveInspectionDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_InspectionProcessData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_ReceiveInspectionData;
import com.ys.business.db.data.B_ReceiveInspectionDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
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
public class ReceiveInspectionService extends CommonService  {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_ReceiveInspectionDao dao;
	private ReceiveInspectionModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public ReceiveInspectionService(){
		
	}

	public ReceiveInspectionService(Model model,
			HttpServletRequest request,
			HttpSession session,
			ReceiveInspectionModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_ReceiveInspectionDao();
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

		String[] keyArr = getSearchKey(Constants.FORM_RECEIVEINSPECTION,data,session);
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
		
		dataModel.setQueryName("getReceiveInspectionList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) ||  notEmpty(key2)){
			userDefinedSearchCase.put("checkResult", "");
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
	

	public String addInit() throws Exception {

		//取得进料检报告编号		
		String arrivalId = request.getParameter("arrivalId");
		
		String inspectArrivalId = getReceiveInspection(arrivalId);
		
		model.addAttribute("resultList",util.getListOption(DicUtil.RECEIVEINSPECTION, ""));

		return inspectArrivalId;
	
	}

	public void updateInit() throws Exception {

		//取得到货信息
		String arrivalId = reqModel.getInspect().getArrivalid() ;
		//String materialId = reqModel.getInspect().getMaterialid();
		getReceiveInspection(arrivalId);
		
		model.addAttribute("resultList",util.getListOption(DicUtil.RECEIVEINSPECTION, ""));
	
	}
	public void showArrivalDetail() {

		String arrivalId = request.getParameter("arrivalId");
		getArrivaRecord(arrivalId);
	}
	
	public void insertAndView() throws Exception {

		insert();

		String arrivalId = reqModel.getInspect().getArrivalid();
		//String materialId = reqModel.getInspect().getMaterialid();
		getReceiveInspection(arrivalId);
	}

	public void PMUpdateAndView() throws Exception {


		//String arrivalId = reqModel.getInspect().getArrivalid();
		//String materialId = reqModel.getInspect().getMaterialid();
		
		//updateProcess2(arrivalId,materialId,true);
		
		//getArriveId(arrivalId,materialId);
	}
/*
	public void GMUpdateAndView() throws Exception {

		String arrivalId = reqModel.getInspect().getArrivalid();
		String materialId = reqModel.getInspect().getMaterialid();
		
		updateProcess2(arrivalId,materialId,false);
		
		getArriveId(arrivalId,materialId);
	}
	*/
	
	private String insert(){
		String contractId = "";
		ts = new BaseTransaction();	
		try {
			ts.begin();
			
			B_ReceiveInspectionData reqData = reqModel.getInspect();
			List<B_ReceiveInspectionDetailData> reqList = reqModel.getInspectList();
			//B_InspectionProcessData process = reqModel.getProcess();
			//String result = process.getManagerresult();
			
			//取得进料检报告编号
			String recordId = reqData.getRecordid();
			if(isNullOrEmpty(recordId))				
				reqData = getReceiveInspectionId(reqData);
			
			//新增进料报检
			reqData.setInspecttime(CalendarUtil.fmtDate());
			reqData.setReport(replaceTextArea(reqData.getReport()));//字符转换
			insertReceivInspection(reqData);
			
			String inspectionid = reqData.getInspectionid();
			contractId = reqData.getContractid();
			
			//删除历史数据		
			deleteReceivInspectionDetail(contractId,inspectionid);
			
			//新增进料报检明细
			for(B_ReceiveInspectionDetailData data:reqList){
				
				data.setInspectionid(inspectionid);
				insertReceivInspectionDetail(data);

				float inspection = stringToFloat(data.getQuantityinspection());//报检数
				float qualified  = stringToFloat(data.getQuantityqualified());//合格数
				float newRtnQty = inspection - qualified;//本次退货
				
				//更新执行状态
				if( newRtnQty> 0 ){
					updateContractStatusById(
						contractId,
						data.getMaterialid(),
						data.getCheckresult(),
						newRtnQty);
				}
			
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
		return contractId;
	}
	
	//更新有效收货
	@SuppressWarnings("unchecked")
	private void updateContract(
			String contractId,
			String materialId,
			String newQuantity) throws Exception{
	
		B_PurchaseOrderDetailData data = new B_PurchaseOrderDetailData();
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();
		
		String where = "contractId ='"+ contractId + "' "+
				" AND materialId ='"+ materialId + "' AND deleteFlag='0' ";
		
		List<B_PurchaseOrderDetailData> list = 
				(List<B_PurchaseOrderDetailData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		float webQty = stringToFloat(newQuantity);

		float iaccumtd = stringToFloat(data.getAccumulated());//收货数
		
		//有效库存减少
		float iNewAccumtd = iaccumtd - webQty;
				
		data.setAccumulated(String.valueOf(iNewAccumtd));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"质检退货",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}
	
	@SuppressWarnings("unchecked")
	private void updateContractStatusById(
			String contractId,
			String materialId,
			String result,
			float returnQty) throws Exception{
	
		String where = "contractId ='"+contractId +
				"' AND materialId ='"+ materialId +
				"' AND deleteFlag='0' ";
		
		//更新到货数量
		B_PurchaseOrderDetailData data = new B_PurchaseOrderDetailData();
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();
		List<B_PurchaseOrderDetailData> list = 
				(List<B_PurchaseOrderDetailData>)dao.Find(where);
		
		if(list ==null || list.size() == 0)
			return ;
				
		//更新DB
		data = list.get(0);
		float iaccumtd = stringToFloat(data.getAccumulated());//有效收货数

		//有效收货数
		float iNewAccumtd = iaccumtd - returnQty;

		data.setAccumulated(String.valueOf(iNewAccumtd));
		
		updateContractDetailStatus(data);
		
	}
	
	@SuppressWarnings("rawtypes")
	private void updateArrivlStatus(
			String arrivalId,
			String materialId,
			String result) throws Exception{
		
		B_ArrivalData dt = new B_ArrivalData();
		String where = "arrivalId ='"+arrivalId +
				"' AND materialId ='"+ materialId+"' AND deleteFlag='0' ";
		
		List list = new B_ArrivalDao().Find(where);
		
		if(list == null || list.size() == 0)
			return;
		
		dt = (B_ArrivalData) list.get(0);		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"ArrivalUpdate",userInfo);
		copyProperties(dt,commData);
		dt.setStatus(Constants.ARRIVAL_STS_2);//已检验
		
		new B_ArrivalDao().Store(dt);
	}
	
	private void insertReceivInspection(
			B_ReceiveInspectionData data) throws Exception{
		B_ReceiveInspectionData db=null;
		try{
			db = new B_ReceiveInspectionDao(data).beanData;
		}catch(Exception e){
			
		}
		if(db == null || ("").equals(db)){

			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"ReceiveInspctionInsert",userInfo);
			copyProperties(data,commData);
			guid = BaseDAO.getGuId();
			data.setRecordid(guid);
			
			new B_ReceiveInspectionDao().Create(data);
		}else{

			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ReceiveInspctionUpdate",userInfo);
			copyProperties(db,commData);
			db.setReport(data.getReport());
			db.setCheckerid(data.getCheckerid());
			
			new B_ReceiveInspectionDao().Store(db);
		}		
		
	}
	
	@SuppressWarnings("unchecked")
	private float insertReceivInspectionDetail(
			B_ReceiveInspectionDetailData data) throws Exception{

		float oldQuantity = 0;
		
		String where = " inspectionid = '"+data.getInspectionid()
			+"' AND  materialId='" + data.getMaterialid() + "'";
			
		List<B_ReceiveInspectionDetailData> list = 
				new B_ReceiveInspectionDetailDao().Find(where);
		
		if(list.size() > 0 ){
			B_ReceiveInspectionDetailData db = list.get(0);
			oldQuantity = stringToFloat(db.getQuantityinspection()) - 
					stringToFloat(db.getQuantityqualified());//退货数
			
			copyProperties(db,data);
			
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ReceiveInspctionDetailUpdate",userInfo);
			copyProperties(db,commData);
			
			new B_ReceiveInspectionDetailDao().Store(db);
		}else{

			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"ReceiveInspctionDetailInsert",userInfo);
			copyProperties(data,commData);

			guid = BaseDAO.getGuId();
			data.setRecordid(guid);
			
			new B_ReceiveInspectionDetailDao().Create(data);
		}
		
		return oldQuantity;
	}
	

	@SuppressWarnings("unchecked")
	private void deleteReceivInspectionDetail(
			String contractId,
			String inspectionid) throws Exception{

		String where = " inspectionid = '"+inspectionid+"'";	
		List<B_ReceiveInspectionDetailData> list = 
				new B_ReceiveInspectionDetailDao().Find(where);
		
		if(list.size() > 0 ){
			for(B_ReceiveInspectionDetailData data:list){

				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"ReceiveInspctionDetailInsert",userInfo);
				copyProperties(data,commData);
				
				new B_ReceiveInspectionDetailDao().Store(data);
				
				//恢复有效收货
				float inspection = stringToFloat(data.getQuantityinspection());//报检数
				float qualified  = stringToFloat(data.getQuantityqualified());//合格数
				float newRtnQty = inspection - qualified;//本次退货				

				//更新执行状态
				if(newRtnQty > 0 ){
					newRtnQty = (-1) * newRtnQty;
					updateContractStatusById(
							contractId,
							data.getMaterialid(),
							data.getCheckresult(),
							newRtnQty);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void deleteReceivInspection(
			String inspectionid) throws Exception{

		String where = " inspectionid = '"+inspectionid+"'";	
		List<B_ReceiveInspectionData> list = 
				new B_ReceiveInspectionDao().Find(where);
		
		if(list.size() > 0 ){
			for(B_ReceiveInspectionData dt:list){

				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"ReceiveInspctionDetailInsert",userInfo);
				copyProperties(dt,commData);
				
				new B_ReceiveInspectionDao().Store(dt);
			}
		}
	}
	
	private void getArrivaRecord(String arrivalId){
		
		try {
			dataModel.setQueryName("getArrivaRecord");
			userDefinedSearchCase.put("arrivalId", arrivalId);
			baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsFullData();

			model.addAttribute("arrival",dataModel.getYsViewData().get(0));
			//String userId = dataModel.getYsViewData().get(0).get("userId");
			
			model.addAttribute("arrivalList",dataModel.getYsViewData());
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
		
	public void doDelete() throws Exception{
		
															
		try {
			
			ts = new BaseTransaction();										
			ts.begin();									

			String inspectionId = reqModel.getInspect().getInspectionid() ;
			String contractId = reqModel.getInspect().getContractid();
			
			//删除头部信息
			deleteReceivInspection(inspectionId);
			
			//删除明细	
			deleteReceivInspectionDetail(contractId,inspectionId);
				
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
	}
	
	
	
	
	public String getReceiveInspection(String arrivalId) throws Exception {

		dataModel.setQueryName("getReceiveInspectionById");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("arrivalId", arrivalId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		model.addAttribute("material",dataModel.getYsViewData());
		model.addAttribute("arrived",dataModel.getYsViewData().get(0));
		String	inspectArrivalId = dataModel.getYsViewData().get(0).get("inspectArrivalId");		
	
		return inspectArrivalId;
	}
	
	public void getContractDetail(String contractId) throws Exception {

		
		dataModel.setQueryName("getContractById");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());			
		}
		
	}
	
	public B_ReceiveInspectionData getReceiveInspectionId(B_ReceiveInspectionData data) throws Exception {
	
		String parentid = BusinessService.getInspectionParentId();
		dataModel.setQueryName("getMAXInspectionId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
		
		String inspectionId = 
				BusinessService.getInspectionRecordId(parentid,code,false);
		
		//B_ReceiveInspectionData data = new B_ReceiveInspectionData();
		data.setInspectionid(inspectionId);
		data.setParentid(parentid);
		data.setSubid(code);
		reqModel.setInspect(data);
		
		return data;		
	}
	
	public HashMap<String, Object> getArrivalHistory(
			String contractId) throws Exception {
		
		dataModel.setQueryName("getArrivaListByContractId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	
	
	public void getContractByArrivalId() throws Exception {

		String contractId = request.getParameter("contractId");
		String arrivalId = request.getParameter("arrivalId");
		
		dataModel.setQueryName("getContractByArrivalId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contractId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", arrivalId);
		baseQuery.getYsFullData(sql);

		if(dataModel.getRecordCount() >0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());
			model.addAttribute("arrivalId",arrivalId);
		}
		
	}

}
