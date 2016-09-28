package com.ys.business.service.supplier;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.supplier.SupplierModel;
import com.ys.business.db.dao.B_SupplierBasicInfoDao;
import com.ys.business.db.data.B_SupplierBasicInfoData;
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
public class SupplierService extends BaseService {
 
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
		
		dataModel.setQueryFileName("/business/supplier/supplierquerydefine");
		dataModel.setQueryName("supplierquerydefine_search");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	
		
		dataModel.setYsViewData(makeAddress(arrangeUserList(dataModel.getYsViewData())));
		
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}

	public SupplierModel doAddInit(HttpServletRequest request) {

		SupplierModel model = new SupplierModel();

		try {			
			model.setCountryList(doOptionChange(DicUtil.ADDRESS, "").getUnsureList());
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap("-1", "err001", "");
		}
		
		return model;
	
	}

	public SupplierModel doAdd(HttpServletRequest request, String data, UserInfo userInfo) {

		SupplierModel model = new SupplierModel();
		try {
			B_SupplierBasicInfoDao dao = new B_SupplierBasicInfoDao();
			B_SupplierBasicInfoData dbData = new B_SupplierBasicInfoData();
			String supplierid = getJsonData(data, "supplierId");
		
			ArrayList<ArrayList<String>> preCheckResult = preCheckSupplierId(request, supplierid);
			
			if (preCheckResult.size() > 0) {
				//已存在
				model.setEndInfoMap("001", "err005", "");
			} else {
				String guid = BaseDAO.getGuId();
				dbData.setId(guid);
				dbData.setSupplierid(getJsonData(data, "supplierId"));
				dbData.setSuppliersimpledes(getJsonData(data, "supplierSimpleDes"));
				dbData.setSupplierdes(getJsonData(data, "supplierDes"));
				dbData.setTwolevelid(getJsonData(data, "twoLevelId"));
				dbData.setTwoleveliddes(getJsonData(data, "twoLevelIdDes"));
				dbData.setPaymentterm(getJsonData(data, "paymentTerm"));
				dbData.setCountry(getJsonData(data, "country"));
				dbData.setProvince(getJsonData(data, "province"));
				dbData.setCity(getJsonData(data, "city"));
				dbData.setAddress(getJsonData(data, "address"));
				
				dbData = updateModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				model.setEndInfoMap("000", "", guid);
			}
		}
		catch(Exception e) {
			model.setEndInfoMap("-1", "err001", "");
		}
		
		return model;
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
			model.setEndInfoMap("-1", "err001", "");
			model.setUnsureList(null);
		}
		
		return model;
	}
	
	public SupplierModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) {
		SupplierModel model = new SupplierModel();
		String id = getJsonData(data, "keyBackup");
		
		try {
			B_SupplierBasicInfoDao dao = new B_SupplierBasicInfoDao();
			B_SupplierBasicInfoData dbData = new B_SupplierBasicInfoData();
			
			String supplierid = getJsonData(data, "supplierId");
			boolean isKeyExist = false;
			
			//要更新的记录是否存在
			isKeyExist = preCheckId(id);
			if (isKeyExist) {
				ArrayList<ArrayList<String>> preCheckResult = preCheckSupplierId(request, supplierid);
				
				//要更新的供应商id是否存在
				if (preCheckResult.size() != 0 && !preCheckResult.get(0).get(1).equals(id)) {					
					//已存在
					model.setEndInfoMap("001", "err007", "");
				} else {
					dbData.setId(getJsonData(data, "keyBackup"));
					dbData.setSupplierid(getJsonData(data, "supplierId"));
					dbData.setSuppliersimpledes(getJsonData(data, "supplierSimpleDes"));
					dbData.setSupplierdes(getJsonData(data, "supplierDes"));
					dbData.setTwolevelid(getJsonData(data, "twoLevelId"));
					dbData.setTwoleveliddes(getJsonData(data, "twoLevelIdDes"));
					dbData.setPaymentterm(getJsonData(data, "paymentTerm"));
					dbData.setCountry(getJsonData(data, "country"));
					dbData.setProvince(getJsonData(data, "province"));
					dbData.setCity(getJsonData(data, "city"));
					dbData.setAddress(getJsonData(data, "address"));
					
					dbData = updateModifyInfo(dbData, userInfo);
					dao.Store(dbData);
					model.setEndInfoMap("000", "", id);
				}
			} else {
				//不存在
				model.setEndInfoMap("002", "err005", id);
			}
		}
		catch(Exception e) {
			model.setEndInfoMap("-1", "err001", id);
		}
		
		return model;
	}
	
	public SupplierModel doDelete(String data, UserInfo userInfo){
		
		SupplierModel model = new SupplierModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeSupplierDelete(data, userInfo);
	        
	        model.setEndInfoMap("000", "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap("-1", "err001", "");
		}
		
		return model;
	}
	
	public static B_SupplierBasicInfoData updateModifyInfo(B_SupplierBasicInfoData data, UserInfo userInfo) {
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
	
	private boolean isDataExist(B_SupplierBasicInfoData dbData) {
		boolean rtnValue = false;
		
		try {
			B_SupplierBasicInfoDao dao = new B_SupplierBasicInfoDao();
			dao.FindByPrimaryKey(dbData);
			rtnValue = true;
		}
		catch(Exception e) {
			
		}
		return rtnValue;
		
	}
	
	public SupplierModel getSupplierBaseInfo(String key) throws Exception {
		SupplierModel model = new SupplierModel();
		B_SupplierBasicInfoDao dao = new B_SupplierBasicInfoDao();
		B_SupplierBasicInfoData dbData = new B_SupplierBasicInfoData();
		dbData.setId(key);
		dbData = (B_SupplierBasicInfoData)dao.FindByPrimaryKey(dbData);
		model.setSupplierBasicInfoData(dbData);
		
		model.setCountryList(doOptionChange(DicUtil.ADDRESS, "").getUnsureList());
		model.setProvinceList(doOptionChange(DicUtil.ADDRESS, dbData.getCountry()).getUnsureList());
		model.setCityList(doOptionChange(DicUtil.ADDRESS, dbData.getProvince()).getUnsureList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
		
	}
	
	private ArrayList<ArrayList<String>> preCheckSupplierId(HttpServletRequest request, String key) throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/supplier/supplierquerydefine");
		dataModel.setQueryName("supplierquerydefine_preCheck");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		return baseQuery.getQueryData();
					
	}
	
	private boolean preCheckId(String key) throws Exception {
		B_SupplierBasicInfoDao dao = new B_SupplierBasicInfoDao();
		B_SupplierBasicInfoData dbData = new B_SupplierBasicInfoData();
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
