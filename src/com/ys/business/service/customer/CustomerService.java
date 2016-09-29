package com.ys.business.service.customer;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.customer.CustomerModel;
import com.ys.business.db.dao.B_CustomerDao;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.role.RoleModel;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_ROLEDao;
import com.ys.system.db.data.S_ROLEData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class CustomerService extends BaseService {
 
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

		key1 = getJsonData(data, "keyword1");
		key2 = getJsonData(data, "keyword2");
		
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
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	
		
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

	public CustomerModel doAddInit(HttpServletRequest request) {

		CustomerModel model = new CustomerModel();

		try {			
			model.setCountryList(doOptionChange(DicUtil.ADDRESS, "").getUnsureList());
			model.setDenominationCurrencyList(doOptionChange(DicUtil.DENOMINATIONCURRENCY, "").getUnsureList());
			model.setShippingCaseList(doOptionChange(DicUtil.SHIPPINGCASE, "").getUnsureList());
			model.setPortList(doOptionChange(DicUtil.PORT, "").getUnsureList());
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
	}

	public CustomerModel doAdd(HttpServletRequest request, String data, UserInfo userInfo) {

		CustomerModel model = new CustomerModel();
		try {
			B_CustomerDao dao = new B_CustomerDao();
			B_CustomerData dbData = new B_CustomerData();
			String customerid = getJsonData(data, "customerId");
		
			ArrayList<ArrayList<String>> preCheckResult = preCheckCustomerId(request, customerid);
			
			if (preCheckResult.size() > 0) {
				//已存在
				model.setEndInfoMap("001", "err005", "");
			} else {
				String guid = BaseDAO.getGuId();
				dbData.setId(guid);
				dbData.setCustomerid(getJsonData(data, "customerId"));
				dbData.setCustomersimpledes(getJsonData(data, "customerSimpleDes"));
				dbData.setCustomername(getJsonData(data, "customerName"));
				dbData.setPaymentterm(getJsonData(data, "paymentTerm"));
				dbData.setCountry(getJsonData(data, "country"));
				dbData.setDenominationcurrency(getJsonData(data, "denominationCurrency"));
				dbData.setShippingcase(getJsonData(data, "shippingCase"));
				dbData.setLoadingport(getJsonData(data, "loadingPort"));
				dbData.setDeliveryport(getJsonData(data, "deliveryPort"));
				
				dbData = updateModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				model.setEndInfoMap(NORMAL, "", guid);
			}
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}	

	
	public CustomerModel doOptionChange(String type, String parentCode) {
		DicUtil util = new DicUtil();
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
	
	public CustomerModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) {
		CustomerModel model = new CustomerModel();
		String id = getJsonData(data, "keyBackup");
		
		try {
			B_CustomerDao dao = new B_CustomerDao();
			B_CustomerData dbData = new B_CustomerData();
			
			String customerid = getJsonData(data, "customerId");
			boolean isKeyExist = false;
			
			//要更新的记录是否存在
			isKeyExist = preCheckId(id);
			if (isKeyExist) {
				ArrayList<ArrayList<String>> preCheckResult = preCheckCustomerId(request, customerid);
				
				//要更新的供应商id是否存在
				if (preCheckResult.size() != 0 && !preCheckResult.get(0).get(1).equals(id)) {					
					//已存在
					model.setEndInfoMap("001", "err007", "");
				} else {
					dbData.setId(getJsonData(data, "keyBackup"));
					dbData.setCustomerid(getJsonData(data, "customerId"));
					dbData.setCustomersimpledes(getJsonData(data, "customerSimpleDes"));
					dbData.setCustomername(getJsonData(data, "customerName"));
					dbData.setPaymentterm(getJsonData(data, "paymentTerm"));
					dbData.setCountry(getJsonData(data, "country"));
					dbData.setDenominationcurrency(getJsonData(data, "denominationCurrency"));
					dbData.setShippingcase(getJsonData(data, "shippingCase"));
					dbData.setLoadingport(getJsonData(data, "loadingPort"));
					dbData.setDeliveryport(getJsonData(data, "deliveryPort"));
					
					dbData = updateModifyInfo(dbData, userInfo);
					dao.Store(dbData);
					model.setEndInfoMap(NORMAL, "", id);
				}
			} else {
				//不存在
				model.setEndInfoMap("002", "err005", id);
			}
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", id);
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
	
	public CustomerModel getCustomerBaseInfo(String key) throws Exception {
		CustomerModel model = new CustomerModel();
		B_CustomerDao dao = new B_CustomerDao();
		B_CustomerData dbData = new B_CustomerData();
		dbData.setId(key);
		dbData = (B_CustomerData)dao.FindByPrimaryKey(dbData);
		model.setCustomerData(dbData);
		
		model.setCountryList(doOptionChange(DicUtil.ADDRESS, "").getUnsureList());
		model.setDenominationCurrencyList(doOptionChange(DicUtil.DENOMINATIONCURRENCY, "").getUnsureList());
		model.setShippingCaseList(doOptionChange(DicUtil.SHIPPINGCASE, "").getUnsureList());
		model.setPortList(doOptionChange(DicUtil.PORT, "").getUnsureList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
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
	
	private boolean preCheckId(String key) throws Exception {
		B_CustomerDao dao = new B_CustomerDao();
		B_CustomerData dbData = new B_CustomerData();
		boolean rtnData = false;
		
		try {
			dbData.setId(key);
			dao.FindByPrimaryKey(dbData);
			rtnData = true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return rtnData;
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
}
