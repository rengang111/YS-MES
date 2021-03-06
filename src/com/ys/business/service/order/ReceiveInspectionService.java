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
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_InspectionProcessData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_ReceiveInspectionData;
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
		

		String result = request.getParameter("result");

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
		userDefinedSearchCase.put("result", result);
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
	

	public String addInit() throws Exception {

		//取得进料检报告编号
		getRecordId();
		
		String arrivalId = request.getParameter("arrivalId");
		String materialId = request.getParameter("materialId");
		String inspectArrivalId = getArriveId(arrivalId,materialId);
		
		model.addAttribute("resultList",util.getListOption(DicUtil.RECEIVEINSPECTION, ""));

		return inspectArrivalId;
	
	}

	public String updateInit() throws Exception {

		//取得到货信息
		String arrivalId = reqModel.getInspect().getArrivalid() ;
		String materialId = reqModel.getInspect().getMaterialid();
		String inspectArrivalId = getArriveId(arrivalId,materialId);
		
		model.addAttribute("resultList",util.getListOption(DicUtil.RECEIVEINSPECTION, ""));

		return inspectArrivalId;
	
	}
	public void showArrivalDetail() {

		String arrivalId = request.getParameter("arrivalId");
		getArrivaRecord(arrivalId);
	}
	
	public void insertAndView() throws Exception {

		insertArrival();

		String arrivalId = reqModel.getInspect().getArrivalid();
		String materialId = reqModel.getInspect().getMaterialid();
		getArriveId(arrivalId,materialId);
	}

	public void PMUpdateAndView() throws Exception {


		String arrivalId = reqModel.getInspect().getArrivalid();
		String materialId = reqModel.getInspect().getMaterialid();
		
		updateProcess2(arrivalId,materialId,true);
		
		getArriveId(arrivalId,materialId);
	}

	public void GMUpdateAndView() throws Exception {

		String arrivalId = reqModel.getInspect().getArrivalid();
		String materialId = reqModel.getInspect().getMaterialid();
		
		updateProcess2(arrivalId,materialId,false);
		
		getArriveId(arrivalId,materialId);
	}
	
	private String insertArrival(){
		String contractId = "";
		ts = new BaseTransaction();
		
		
		try {
			ts.begin();
			
			B_ReceiveInspectionData reqData = reqModel.getInspect();
			B_InspectionProcessData process = reqModel.getProcess();
			String result = process.getManagerresult();
			
			//删除旧数据
			String arrivalId = reqData.getArrivalid();
			String materialId = reqData.getMaterialid();
			deleteArrivalById(arrivalId,materialId);
			contractId = reqData.getContractid();			
				
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"ReceiveInspctionInsert",userInfo);
			copyProperties(reqData,commData);

			String guid = BaseDAO.getGuId();
			reqData.setRecordid(guid);
			reqData.setInspecttime(CalendarUtil.fmtDate());
			reqData.setMemo(replaceTextArea(reqData.getMemo()));//字符转换
			reqData.setReport(replaceTextArea(reqData.getReport()));//字符转换
			reqData.setLossandcisposal(replaceTextArea(reqData.getLossandcisposal()));//字符转换
			dao.Create(reqData);	
			
			//更新进料检确认流程
			updateProcess(reqData);
	
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

	
	private void updateProcess(
			B_ReceiveInspectionData inspect) throws Exception{
	
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
	
	
	
	
	public String getArriveId(String arrivalId,String materialId) {
		String inspectArrivalId = "";
		try {
			dataModel.setQueryName("getReceiveInspectionList");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("arrivalId", arrivalId);
			userDefinedSearchCase.put("materialId", materialId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
			baseQuery.getYsFullData();	
			
			model.addAttribute("arrived",dataModel.getYsViewData().get(0));
			inspectArrivalId = dataModel.getYsViewData().get(0).get("inspectArrivalId");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
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
	
	public void getRecordId() {

		try {
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
			
			B_ReceiveInspectionData data = new B_ReceiveInspectionData();
			data.setInspectionid(inspectionId);
			data.setParentid(parentid);
			data.setSubid(code);
			reqModel.setInspect(data);
			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
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
