package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.order.SupplierModel;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_SupplierDao;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class SupplierService extends CommonService {
	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_SupplierDao dao;
	private SupplierModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;	

	public SupplierService(){
		
	}

	public SupplierService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			SupplierModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_SupplierDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/supplier/supplierquerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
				
	}
	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, HttpSession session) throws Exception {

		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		String type = request.getParameter("type");
		
		data = URLDecoder.decode(data, "UTF-8");

		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/business/supplier/supplierquerydefine");
		dataModel.setQueryName("supplierquerydefine_search");
		
		String[] keyArr = getSearchKey(Constants.FORM_SUPPLIER,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		String issuesFlag = request.getParameter("issuesFlag");
		if(notEmpty(issuesFlag))
				issuesFlag = URLDecoder.decode(issuesFlag,"utf-8");
		userDefinedSearchCase.put("issues", issuesFlag);
		
		if(type == null || type.equals("")){
			userDefinedSearchCase.put("keyword1", key1);
			userDefinedSearchCase.put("keyword2", key2);
		}else{
			userDefinedSearchCase.put("type", type);
			iStart = 0;
			iEnd = 0;			
		}

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

	public Model doAddInit() {

		try {			
			reqModel.setCountryList(getProvinceList());
			reqModel.setTypeList(util.getListOption(DicUtil.SUPPLIER_TYPE, ""));	
			model.addAttribute("issuesList",getListCheckBox(DicUtil.SUPPLIERISSUES, ""));

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return model;
	
	}

	public void insertAndView() {
		
		insertSupplier();
		
	}
	public void insertSupplier() {

		try {
			
			B_SupplierData reqData = reqModel.getSupplier();

			B_SupplierData dbData = preCheckId(reqData);
			
			if (dbData != null && dbData.getRecordid() != "") {
				//更新处理
				copyProperties(dbData,reqData);
				
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"SupeperUpdate",userInfo);
				copyProperties(dbData,commData);
				
				dao.Store(dbData);
				
			} else {
				
				//新增处理
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"SupeperInsert",userInfo);
				copyProperties(reqData,commData);

				guid = BaseDAO.getGuId();
				reqData.setRecordid(guid);
				
				dao.Create(reqData);
				
			}
			reqModel.setSupplier(reqData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}	

	
	public SupplierModel doOptionChange(String type, String parentCode) {
		DicUtil util = new DicUtil();
		SupplierModel model = new SupplierModel();
		
		try {
			ArrayList<ListOption> optionList = util.getListOption(type, parentCode);
			model.setUnsureList(optionList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
			model.setUnsureList(null);
		}
		
		return model;
	}
	
	public SupplierModel doDelete(String data, UserInfo userInfo){
		
		SupplierModel model = new SupplierModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeSupplierDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public static B_SupplierData updateModifyInfo(B_SupplierData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			//data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}

	
	
	private ArrayList<HashMap<String, String>> makeAddress(ArrayList<HashMap<String, String>> data) {

		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		
		for(HashMap<String, String>rowData:data) {
			rowData.put("fullAddress", rowData.get("countryName") + rowData.get("provinceName") + rowData.get("cityName") + rowData.get("address"));
			rtnData.add(rowData);
		}
		
		return rtnData;
	}
	
	
	public void supplierEditInit() throws Exception{

		String key = request.getParameter("key");
		getSupplierBaseInfo(key);

		
		model.addAttribute("issuesList",getListCheckBox(DicUtil.SUPPLIERISSUES, ""));
	}
	
	public Model getSupplierBaseInfo(String key) throws Exception {

		B_SupplierData dbData = new B_SupplierData();
		dbData.setRecordid(key);
		dbData = (B_SupplierData)dao.FindByPrimaryKey(dbData);
		reqModel.setSupplierBasicInfoData(dbData);
		reqModel.setSupplier(dbData);
	
		reqModel.setCountryList(getProvinceList());
		
		reqModel.setKeyBackup(dbData.getRecordid());
		reqModel.setTypeList(util.getListOption(DicUtil.SUPPLIER_TYPE, ""));	

		if(notEmpty(dbData.getIssues())){
			String[] issuesList = dbData.getIssues().split(",");
			reqModel.setIssuesList(issuesList);
		}
		
		model.addAttribute(reqModel);
		
		return model;
		
	}
	
	@SuppressWarnings("unchecked")
	public Model getSupplierById(String supplierId) throws Exception {
	
		B_SupplierData dbData = new B_SupplierData();

		String where = " supplierId='"+supplierId + "' AND deleteFlag='0' ";
		List<B_SupplierData> listData = dao.Find(where);

		if(listData != null && listData.size() > 0)
			dbData = listData.get(0);	
		
		reqModel.setSupplierBasicInfoData(dbData);
		reqModel.setSupplier(dbData);
	
		reqModel.setCountryList(getProvinceList());
		
		reqModel.setKeyBackup(dbData.getRecordid());
		
		model.addAttribute(reqModel);
		
		return  model;
					
	}
	
	
	private B_SupplierData preCheckId(B_SupplierData  reqData) throws Exception {
		B_SupplierData dbData = new B_SupplierData();
		
		try {
			dbData = (B_SupplierData)dao.FindByPrimaryKey(reqData);

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		return dbData;
	}
	
	private String getUserList(ArrayList<String>userList) {
		String viewUserList = "";
		
		for(String user:userList) {
			viewUserList += "," + user;
		}
		
		if (viewUserList.length() > 0) {
			viewUserList = viewUserList.substring(1, viewUserList.length());
		}
		
		return viewUserList;
	}
	
	public ArrayList<ListOption> getProvinceList() throws Exception{
		
		dataModel.setQueryName("getProvinceListOption");
		baseQuery = new BaseQuery(request, dataModel);
		
		return getProvinceOption(baseQuery.getFullData(),1,1);
	}
	
	public ArrayList<ListOption> getCityList(String province) throws Exception{
		
		dataModel.setQueryName("getCityListOption");
		userDefinedSearchCase.put("province", province);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		return getProvinceOption(baseQuery.getFullData(),3,2);
		/*
		 <f name="A.recordId" alias="" ctype="" />
	      <f name="A.province" alias="" ctype="" />
	      <f name="A.city" alias="" ctype="" />
	      <f name="A.cityCode" alias="" ctype="" />
	      <f name="A.county" alias="" ctype="" />
	      <f name="A.countyCode" alias="" ctype="" />
	      */
	}
	public ArrayList<ListOption> getCountyList(String city) throws Exception{

		dataModel.setQueryName("getCountyListOption");
		userDefinedSearchCase.put("cityCode", city);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		return getProvinceOption(baseQuery.getFullData(),5,4);
		
	}
	public ArrayList<ListOption> getProvinceOption(
			ArrayList<ArrayList<String>> list,
			int pos1,int pos2) throws Exception {

		ArrayList<ListOption> rtnData = new ArrayList<ListOption>();
		
		ListOption option = new ListOption("", "");
		rtnData.add(option);
		
		for(ArrayList<String>rowData:list) {
			option = new ListOption(rowData.get(pos1), rowData.get(pos2));
			rtnData.add(option);
		}
		
		return rtnData;
	}
	
	public String getSupplierSubId(String id) throws Exception{
		
		dataModel.setQueryName("getSupplierSubId");
		userDefinedSearchCase.put("parentId", id);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
				
		String subid = BusinessService.getFormat2Code(code, true);

		return subid;
	}
	
	public HashMap<String, Object> getSupplierId(String parentId) throws Exception{
		
		String shortName = request.getParameter("key");
		//check 简称是否存在
		String where = " shortName = '"+ shortName +"' AND deleteFlag='0'";
		boolean flag = CheckSupplierShortName(where);
		
		if(flag){
			String subId = getSupplierSubId(parentId);			
			modelMap.put("subId",subId);
			modelMap.put("returnCode","0");
		}else{
			modelMap.put("returnCode","1");
		}
		
		return modelMap;
	}
	

	public HashMap<String, Object> checkShortName() throws Exception{
		
		String shortName = request.getParameter("key");
		String supplierId = request.getParameter("supplierId");
		//check 简称是否存在
		String where = " supplierId != '"+ supplierId +"' AND shortName = '"+ shortName +"' AND deleteFlag='0'";
		boolean flag = CheckSupplierShortName(where);
		
		if(flag){
			modelMap.put("returnCode","0");
		}else{
			modelMap.put("returnCode","1");
		}
		
		return modelMap;
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean CheckSupplierShortName(
			String  where) throws Exception {
		
		boolean rtn = true;
		try {
			List<B_SupplierData> dbData = (List<B_SupplierData>)dao.Find(where);
			
			if(dbData.size() >0)
				rtn = false;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			
		}
		
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> updateContractDetail() throws Exception {
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		ts = new BaseTransaction();

		try {
			ts.begin();

			String deliveryDate = request.getParameter("deliveryDate");
			String materialId   = request.getParameter("materialId");
			String contractId   =request.getParameter("contractId");
			
			if(isNullOrEmpty(deliveryDate) && 
					isNullOrEmpty(materialId) && 
					isNullOrEmpty(contractId)){
				
				return dataMap;
			}else{

				String where = " contractId ='" + contractId + "' "+
						//" AND materialId ='" + materialId + "' " +
						" AND deleteFlag = '0' ";
				
				List<B_PurchaseOrderData> list = new B_PurchaseOrderDao().Find(where);
				
				if(list.size() > 0 ){

					//合同交期
					B_PurchaseOrderData contract = list.get(0);
					contract.setNewdeliverydate(deliveryDate);
					
					//更新处理					
					commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
							"更新合同交期",userInfo);
					copyProperties(contract,commData);
					
					new B_PurchaseOrderDao().Store(contract);
				}				
			}		
				
			dataMap.put("result", "success");
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			e.printStackTrace();
		}
		
		return dataMap;
	}	
	
	public void suppliserSearchInit() throws Exception{
		
		model.addAttribute("issuesBtList",util.getListOption(DicUtil.SUPPLIERISSUES, ""));
	}
	
	public ArrayList<HashMap<String, String>> getSupplierIssuesList() throws Exception{
		ArrayList<HashMap<String, String>> list = null;
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		dataModel.setQueryName("getPuchaserByMaterialId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("dicTypeId", "供应商问题");		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			list = dataModel.getYsViewData();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("rownum", String.valueOf(list.size()+1));
			map.put("dicName", "ALL");
			map.put("dicId", "999");
			map.put("SortNo", "999");
			list.add(0, map);
		
		}

		return list;
		
	}
}
