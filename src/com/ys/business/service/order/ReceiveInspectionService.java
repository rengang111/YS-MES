package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.ReceiveInspectionModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_InspectionProcessDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_ReceiveInspectionDao;
import com.ys.business.db.dao.B_ReceiveInspectionDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_InspectionProcessData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_ReceiveInspectionData;
import com.ys.business.db.data.B_ReceiveInspectionDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

import jxl.write.DateTime;

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
		//getRecordId();
		
		String arrivalId = request.getParameter("arrivalId");
		String materialId = request.getParameter("materialId");
		
		String inspectArrivalId = getReceiveInspection(arrivalId);
		
		model.addAttribute("resultList",util.getListOption(DicUtil.RECEIVEINSPECTION, ""));

		return inspectArrivalId;
	
	}

	public void updateInit() throws Exception {

		//取得到货信息
		String arrivalId = reqModel.getInspect().getArrivalid() ;
		//String materialId = reqModel.getInspect().getMaterialid();
		String inspectArrivalId = getReceiveInspection(arrivalId);
		
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


		String arrivalId = reqModel.getInspect().getArrivalid();
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
			String arrivalId = reqData.getArrivalid();
			contractId = reqData.getContractid();
			
			//删除历史数据
			String where = " inspectionid = '"+inspectionid+"'";
			try {
				new B_ReceiveInspectionDetailDao().RemoveByWhere(where);
			} catch (Exception e) {
				e.printStackTrace();
				// nothing
			}
			//新增进料报检明细
			for(B_ReceiveInspectionDetailData data:reqList){
				
				data.setInspectionid(inspectionid);
				insertReceivInspectionDetail(data);
				
				//更新收货状态
				updateArrivlStatus(
						arrivalId,
						data.getMaterialid(),
						data.getCheckresult());
				
				//更新执行状态
				updateContractStatusById(
						contractId,
						data.getMaterialid(),
						data.getCheckresult());
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
	
	@SuppressWarnings("unchecked")
	private void updateContractStatusById(
			String contractId,
			String materialId,
			String result) throws Exception{
	
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
		if(result != null && (Constants.ARRIVERECORD_3).equals(result)){

			data.setStatus(Constants.CONTRACT_PROCESS_4);//完成(退货)
		}else{
			data.setStatus(Constants.CONTRACT_PROCESS_2);//待入库
		}
		
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
	
	private void insertReceivInspectionDetail(
			B_ReceiveInspectionDetailData data) throws Exception{
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ReceiveInspctionDetailInsert",userInfo);
		copyProperties(data,commData);

		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		new B_ReceiveInspectionDetailDao().Create(data);
	}
	
	private void updateProcess(
			B_ReceiveInspectionData inspect) throws Exception{
	/*
		B_InspectionProcessDao dao = new B_InspectionProcessDao();

		B_InspectionProcessData process = reqModel.getProcess();
		
		String where = "arrivalId ='"+inspect.getArrivalid() +
				"' AND materialId ='"+ inspect.getMaterialid()+"'";
		try{
			dao.RemoveByWhere(where);
		}catch(Exception e){
			//
		}
		copyProperties(process,inspect);
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ArrivalInsert",userInfo);
		copyProperties(process,commData);

		String guid = BaseDAO.getGuId();
		process.setRecordid(guid);
		process.setCheckdate(CalendarUtil.fmtDate());
		process.setCheckerid(userInfo.getUserId());
		process.setResult(process.getCheckresult());//质检员的检验结果
		process.setManagerfeedback(replaceTextArea(process.getManagerfeedback()));//字符转换
		process.setGmfeedback(replaceTextArea(process.getGmfeedback()));//字符转换
		dao.Create(process);
		*/
	}
	
	@SuppressWarnings("unchecked")
	private void updateProcess2(
			String arrivalId,String materialId,
			boolean flg) throws Exception{
	
		B_InspectionProcessData data = new B_InspectionProcessData();
		B_InspectionProcessDao dao = new B_InspectionProcessDao();

		B_InspectionProcessData process = reqModel.getProcess();
		
		String where = "arrivalId ='"+arrivalId +
				"' AND materialId ='"+ materialId+"'";
		
		List<B_InspectionProcessData> list = 
				(List<B_InspectionProcessData>)dao.Find(where);
		
		if( list.size() > 0){			
			//更新DB
			data = list.get(0);
			process.setManagerfeedback(replaceTextArea(process.getManagerfeedback()));//字符转换
			process.setGmfeedback(replaceTextArea(process.getGmfeedback()));//字符转换
			copyProperties(data,process);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ArrivalUpdate",userInfo);
			copyProperties(data,commData);
			if(flg){
				//品质部经理确认
				data.setManagerdate(CalendarUtil.fmtDate());
				data.setResult(process.getManagerresult());//品质部经理检验结果
			}else{
				//总经理确认
				data.setGmdate(CalendarUtil.fmtDate());
			}
			
			dao.Store(data);
		}		
	}
	
	
	private void deleteArrivalById(String arrivalId,String materialId) {

		String where = " arrivalId = '"+arrivalId+"' AND materialId='"+materialId+"'";
		try {
			dao.RemoveByWhere(where);
		} catch (Exception e) {
			// nothing
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
	
	
	
	
	public String getReceiveInspection(String arrivalId) throws Exception {

		dataModel.setQueryName("getReceiveInspectionList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("arrivalId", arrivalId);
		//userDefinedSearchCase.put("materialId", materialId);
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
