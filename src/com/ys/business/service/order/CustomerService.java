package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.order.CustomerModel;
import com.ys.business.db.dao.B_CustomerDao;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
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
public class CustomerService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_CustomerDao dao;
	private CustomerModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;	

	public CustomerService(){
		
	}

	public CustomerService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			CustomerModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_CustomerDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/customer/customerquerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
		
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
		
		dataModel.setQueryFileName("/business/customer/customerquerydefine");
		dataModel.setQueryName("customerquerydefine_search");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		String[] keyArr = getSearchKey(Constants.FORM_CUSTOMER,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data, baseQuery);
		baseQuery.getYsQueryData(sql,iStart, iEnd);	
		
		dataModel.setYsViewData(makeAddress(dataModel.getYsViewData()));
		
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
			reqModel.setCountryList(util.getListOption(DicUtil.COUNTRY, ""));
			reqModel.setCurrencyList(util.getListOption(DicUtil.CURRENCY, ""));
			reqModel.setShippingConditionList(util.getListOption(DicUtil.SHIPPINGCASE, ""));
			reqModel.setShippiingPortList(util.getListOption(DicUtil.LOADINGPORT, ""));
			reqModel.setDestinationPortList(util.getListOption(DicUtil.DELIVERYPORT, ""));
			reqModel.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
	}
	

	@SuppressWarnings("unchecked")
	private B_CustomerData preCheckId(B_CustomerData  reqData) 
			throws Exception {
		List<B_CustomerData> dbData = null;
		B_CustomerData dt = null;
		String customerId = reqData.getCustomerid();
		String where = " customerId = '" + customerId +"' AND deleteFlag = '0' ";
		
		try {
			dbData = (List<B_CustomerData>)dao.Find(where);
			
			if(dbData.size() > 0)
				dt = dbData.get(0);

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dt = null;
		}
		
		return dt;
	}
	
	public CustomerModel doOptionChange(String type, String parentCode) {
		
		CustomerModel model = new CustomerModel();
		
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
	
	public CustomerModel doDelete(String data, UserInfo userInfo){
		
		CustomerModel model = new CustomerModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeCustomerDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
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
	
	public static B_CustomerData updateModifyInfo(B_CustomerData data, UserInfo userInfo) {
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
	
	private boolean isDataExist(B_CustomerData dbData) {
		boolean rtnValue = false;
		
		try {
			B_CustomerDao dao = new B_CustomerDao();
			dao.FindByPrimaryKey(dbData);
			rtnValue = true;
		}
		catch(Exception e) {
			
		}
		return rtnValue;
		
	}
	
	public Model getCustomerByRecordId(String key) throws Exception {

		dataModel.setQueryName("getCustomerByRecordId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("recordId", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsQueryData(0, 0);		

		model.addAttribute("customer",dataModel.getYsViewData().get(0));
		
		reqModel.setKeyBackup(key);
		
		return model;
		
	}
	
	public Model getCustomerDetail(String key) throws Exception {
		
		B_CustomerData dbData = new B_CustomerData();
		
		try {
			dbData.setRecordid(key);
			dbData = (B_CustomerData)dao.FindByPrimaryKey(dbData);

			reqModel.setCustomer(dbData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		reqModel.setKeyBackup(key);
		
		return model;
		
	}
	
	private ArrayList<ArrayList<String>> preCheckCustomerId(HttpServletRequest request, String key) throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/customer/customerquerydefine");
		dataModel.setQueryName("customerquerydefine_preCheck");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		return baseQuery.getQueryData();
					
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
	
	public HashMap<String, Object> getCustomerId() throws Exception{
		
		String parentId = request.getParameter("parentId");
		parentId = convertToUTF8(parentId);
		
		dataModel.setQueryName("getCustomerSubId");
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
				
		String subid = BusinessService.getFormatCode(code, true);
		
		modelMap.put("subId",subid);
		
		return modelMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> shortNameCheck() throws Exception{
		
		String ExFlag = "";
		String shortName = request.getParameter("shortName");
		shortName = convertToUTF8(shortName).toUpperCase();
		List<B_CustomerData> list = null;
		String astr_Where = "UPPER(shortName) = '"+ shortName + "' " +
							" AND deleteFlag = '0' ";
		list = (List<B_CustomerData>)dao.Find(astr_Where);
	
		if(list != null && list.size() > 0){
			ExFlag = "1";
		}		
		
		modelMap.put("ExFlag",ExFlag);
		
		return modelMap;
	}
	public void insertAndView(){
		
		String recordId = insertAndUpdate();
		try {
			getCustomerByRecordId(recordId);
			doAddInit();//加载下拉框
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String insertAndUpdate(){
		
		String recordId = "";
		try {
			
			B_CustomerData reqData = reqModel.getCustomer();

			B_CustomerData dbData = preCheckId(reqData);
			
			if (dbData != null && dbData.getRecordid() != "") {
				//更新处理
				copyProperties(dbData,reqData);
				
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"CustomerUpdate",userInfo);
				copyProperties(dbData,commData);
				
				dao.Store(dbData);
				
			} else {
				
				//新增处理
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"CustomerInsert",userInfo);
				copyProperties(reqData,commData);

				guid = BaseDAO.getGuId();
				reqData.setRecordid(guid);
				
				dao.Create(reqData);
				
			}
			reqModel.setCustomer(reqData);
			
			recordId = reqData.getRecordid();

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return recordId;
	}
	
	public Model editInit() throws Exception{

		String key = request.getParameter("key");
		
		getCustomerDetail(key);
		
		doAddInit();//加载下拉框
		
		return model;
	}
}
