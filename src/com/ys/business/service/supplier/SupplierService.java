package com.ys.business.service.supplier;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.supplier.SupplierModel;
import com.ys.business.db.dao.B_SupplierDao;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.db.data.B_ZZMaterialPriceData;
import com.ys.business.db.data.B_ZZRawMaterialData;
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

@Service
public class SupplierService extends BaseService {
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
		
	}
	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		String key1 = "";
		String key2 = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key1 = getJsonData(data, "keyword1").toUpperCase();
		key2 = getJsonData(data, "keyword2").toUpperCase();
		
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
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
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

	public Model doAddInit() {


		try {			
			reqModel.setCountryList(getProvinceList());
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

	private ArrayList<HashMap<String, String>> arrangeUserList(ArrayList<HashMap<String, String>> data) {
		ArrayList<String> userList = new ArrayList<String>();
		HashMap<String, String>rowDataBackup = null;
		HashMap<String, String>rowData = null;
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		
		for(int i = 0; i < data.size(); i++) {
			rowData = data.get(i);
			String userName = rowData.get("userName");
			if (rowDataBackup == null) {
				rowDataBackup = rowData;
			}
			if (rowData.get("id").equals(rowDataBackup.get("id"))) {
				if (userName != null && !userName.equals("")) {
					userList.add(userName);
				}
			} else {
				rowDataBackup.put("userName", getUserList(userList));
				rtnData.add(rowDataBackup);
				rowDataBackup = rowData;
				userList = new ArrayList<String>();
				if (userName != null && !userName.equals("")) {
					userList.add(userName);
				}

			}
		}
		
		if (rowDataBackup != null) {
			rowData.put("userName", getUserList(userList));
			rtnData.add(rowDataBackup);
		}

		
		return rtnData;
		
	}
	
	private ArrayList<HashMap<String, String>> makeAddress(ArrayList<HashMap<String, String>> data) {

		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		
		for(HashMap<String, String>rowData:data) {
			rowData.put("fullAddress", rowData.get("countryName") + rowData.get("provinceName") + rowData.get("cityName") + rowData.get("address"));
			rtnData.add(rowData);
		}
		
		return rtnData;
	}
	
	private boolean isDataExist(B_SupplierData dbData) {
		boolean rtnValue = false;
		
		try {
			B_SupplierDao dao = new B_SupplierDao();
			dao.FindByPrimaryKey(dbData);
			rtnValue = true;
		}
		catch(Exception e) {
			
		}
		return rtnValue;
		
	}
	
	public Model getSupplierBaseInfo(String key) throws Exception {

		B_SupplierData dbData = new B_SupplierData();
		dbData.setRecordid(key);
		dbData = (B_SupplierData)dao.FindByPrimaryKey(dbData);
		reqModel.setSupplierBasicInfoData(dbData);
		reqModel.setSupplier(dbData);
	
		reqModel.setCountryList(getProvinceList());
		
		reqModel.setKeyBackup(dbData.getRecordid());
		
		model.addAttribute(reqModel);
		
		return model;
		
	}
	
	private HashMap<String, String> preCheckSupplierId(HttpServletRequest request, String key) throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/supplier/supplierquerydefine");
		dataModel.setQueryName("supplierquerydefine_preCheck");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		return baseQuery.getYsQueryData(0, 0).get(0);
					
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
		
		String subId = getSupplierSubId(parentId);
		
		modelMap.put("subId",subId);
		
		return modelMap;
	}
}
