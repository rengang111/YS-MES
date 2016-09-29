package com.ys.business.service.contact;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.contact.ContactModel;
import com.ys.business.action.model.supplier.SupplierModel;
import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.dao.B_SupplierBasicInfoDao;
import com.ys.business.db.data.B_ContactData;
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
public class ContactService extends BaseService {
 
	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		String key = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key = getJsonData(data, "keyBackup");
		if (key.equals("")) {
			key = DUMMYKEY;
		}
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/business/contact/contactquerydefine");
		dataModel.setQueryName("contactquerydefine_search");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	
		//ArrayList<HashMap<String, String>> dbData1 = dataModel.getYsViewData()
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}

	public ContactModel doAddInit(HttpServletRequest request) {

		ContactModel model = new ContactModel();

		try {
			String key = request.getParameter("key");
			model.setSexList(doOptionChange(DicUtil.SEX, "").getSexList());
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
	}

	public ContactModel doAdd(HttpServletRequest request, String data, UserInfo userInfo) {

		ContactModel model = new ContactModel();
		try {
			B_ContactDao dao = new B_ContactDao();
			B_ContactData dbData = new B_ContactData();
			String companyCodeKey = getJsonData(data, "companyCode");
			String userName = getJsonData(data, "userName");
			
			ArrayList<ArrayList<String>> preCheckResult = preCheckUserName(request, companyCodeKey, userName);
			
			if (preCheckResult.size() > 0) {
				//已存在
				model.setEndInfoMap("001", "err005", "");
			} else {
				String guid = BaseDAO.getGuId();
				dbData.setId(guid);
				dbData.setCompanycode(companyCodeKey);
				dbData.setUsername(getJsonData(data, "userName"));
				dbData.setSex(getJsonData(data, "sex"));
				dbData.setPosition(getJsonData(data, "position"));
				dbData.setDepartment(getJsonData(data, "department"));
				dbData.setMobile(getJsonData(data, "mobile"));
				dbData.setPhone(getJsonData(data, "phone"));
				dbData.setFax(getJsonData(data, "fax"));
				dbData.setMail(getJsonData(data, "mail"));
				dbData.setSkype(getJsonData(data, "skype"));
				dbData.setMark(getJsonData(data, "mark"));
				dbData.setQq(getJsonData(data, "QQ"));
				
				dbData = updateModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				model.setEndInfoMap("000", "", guid + "|" + companyCodeKey);
			}
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}	
	
	public ContactModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) {
		ContactModel model = new ContactModel();
		String id = getJsonData(data, "keyBackup");
		
		try {
			B_ContactDao dao = new B_ContactDao();
			B_ContactData dbData = new B_ContactData();
			
			String userName = getJsonData(data, "userName");
			String companyCode = getJsonData(data, "companyCode");
			boolean isKeyExist = false;
			
			//要更新的记录是否存在
			isKeyExist = preCheckId(id);
			if (isKeyExist) {
				ArrayList<ArrayList<String>> preCheckResult = preCheckUserName(request, companyCode, userName);
				
				//要更新的供应商id是否存在
				if (preCheckResult.size() != 0 && !preCheckResult.get(0).get(1).equals(id)) {					
					//已存在
					model.setEndInfoMap("001", "err007", "");
				} else {
					dbData.setId(getJsonData(data, "keyBackup"));
					dbData.setCompanycode(getJsonData(data, "companyCode"));
					dbData.setUsername(getJsonData(data, "userName"));
					dbData.setSex(getJsonData(data, "sex"));
					dbData.setPosition(getJsonData(data, "position"));
					dbData.setDepartment(getJsonData(data, "department"));
					dbData.setMobile(getJsonData(data, "mobile"));
					dbData.setPhone(getJsonData(data, "phone"));
					dbData.setFax(getJsonData(data, "fax"));
					dbData.setMail(getJsonData(data, "mail"));
					dbData.setSkype(getJsonData(data, "skype"));
					dbData.setMark(getJsonData(data, "mark"));
					dbData.setQq(getJsonData(data, "QQ"));
					
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
			model.setEndInfoMap(SYSTEMERROR, "err001", id);
		}
		
		return model;
	}
	
	public ContactModel doDelete(String data, UserInfo userInfo){
		
		ContactModel model = new ContactModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeContactDelete(data, userInfo);
	        
	        model.setEndInfoMap("000", "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public static B_ContactData updateModifyInfo(B_ContactData data, UserInfo userInfo) {
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
	
	public ContactModel getContactDetailInfo(String key) throws Exception {
		ContactModel model = new ContactModel();
		B_ContactDao dao = new B_ContactDao();
		B_ContactData dbData = new B_ContactData();
		dbData.setId(key);
		dbData = (B_ContactData)dao.FindByPrimaryKey(dbData);
		model.setContactData(dbData);
		model.setCompanyCode(dbData.getCompanycode());
		
		model.setSexList(doOptionChange(DicUtil.SEX, "").getSexList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
	}
	
	private String getBaseInfo(String key) {
		String companyCode = "-1";

		B_SupplierBasicInfoDao dao = new B_SupplierBasicInfoDao();
		B_SupplierBasicInfoData dbData = new B_SupplierBasicInfoData();
		try {
			dbData.setId(key);
			dbData = (B_SupplierBasicInfoData)dao.FindByPrimaryKey(dbData);
			companyCode = dbData.getSupplierid();
		}
		catch(Exception e) {
			
		}
		
		return companyCode;
		
	}
	
	private ArrayList<ArrayList<String>> preCheckUserName(HttpServletRequest request, String companyCode, String userName) throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/contact/contactquerydefine");
		dataModel.setQueryName("contactquerydefine_preCheck");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("companyCode", companyCode);
		userDefinedSearchCase.put("userName", userName);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		return baseQuery.getQueryData();
					
	}
	
	private boolean preCheckId(String key) throws Exception {
		B_ContactDao dao = new B_ContactDao();
		B_ContactData dbData = new B_ContactData();
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
	
	public ContactModel doOptionChange(String type, String parentCode) {
		DicUtil util = new DicUtil();
		ContactModel model = new ContactModel();
		
		try {
			ArrayList<ListOption> optionList = util.getListOption(type, parentCode);
			model.setSexList(optionList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
			model.setSexList(null);
		}
		
		return model;
	}
}
