package com.ys.business.service.organ;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.service.common.BaseService;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.contact.ContactModel;
import com.ys.business.action.model.organ.OrganModel;
import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.dao.B_OrganizationDao;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_OrganizationData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.ejb.BusinessDbUpdateEjb;

@Service
public class OrganService extends BaseService {

	private CommFieldsData commData;

	public HashMap<String, Object> Init(HttpServletRequest request, String data) {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		try {
			data = URLDecoder.decode(data, "UTF-8");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
				
		BaseModel dataModel = new BaseModel();

		dataModel.setQueryFileName("/yssample/yssamplequerydefine");
		dataModel.setQueryName("yssamplequerydefine_search");
		try {
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			baseQuery.getYsQueryData(iStart, iEnd);	
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}		
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	public HashMap<String, Object> search(HttpServletRequest request, String data,UserInfo userInfo) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();
		

		dataModel.setQueryFileName("/business/organ/orgquerydefine");
		dataModel.setQueryName("orgquerydefine_search");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	 

		//dataModel.setYsViewData(makeAddress(arrangeUserList(dataModel.getYsViewData())));
		
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}		
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	public OrganModel insert(HttpServletRequest request, String data, UserInfo userInfo) {

		OrganModel model = new OrganModel();
		try {
			B_OrganizationDao dao = new B_OrganizationDao();
			B_OrganizationData dbData = new B_OrganizationData();

			String guid = BaseDAO.getGuId();
			dbData.setRecordid(guid);
			dbData.setType(getJsonData(data, "type"));
			dbData.setShortname(getJsonData(data, "shortName"));
			dbData.setFullname(getJsonData(data, "fullName"));
			dbData.setAddress(getJsonData(data, "address"));
	
			
			//插入机构信息表
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,"OrganInsert",userInfo);

			copyProperties(dbData,commData);
			
			dao.Create(dbData);
			
			model.setEndInfoMap(NORMAL, "suc001", "");
			
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}	
	
	public OrganModel createOrgan(HttpServletRequest request) {

		OrganModel model = new OrganModel();

		try {			
			model.setTypeList(doOptionChange(DicUtil.ORGANTYPE, "").getTypeList());
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
	}
	
	
	public OrganModel doOptionChange(String type, String parentCode) {
		DicUtil util = new DicUtil();
		OrganModel model = new OrganModel();
		
		try {
			ArrayList<ListOption> optionList = util.getListOption(type, parentCode);
			model.setTypeList(optionList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
			model.setTypeList(null);
		}
		
		return model;
	}

	
	
	public OrganModel doDelete(String data, UserInfo userInfo){
		
		OrganModel model = new OrganModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeOrganDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	
	public ContactModel getContactDetailInfo(String key) throws Exception {
		ContactModel model = new ContactModel();
		B_ContactDao dao = new B_ContactDao();
		B_ContactData dbData = new B_ContactData();
		dbData.setId(key);
		dbData = (B_ContactData)dao.FindByPrimaryKey(dbData);
		model.setContactData(dbData);
		model.setCompanyCode(dbData.getCompanycode());
		
		model.setSexList(doOptionChange(DicUtil.ORGANTYPE, "").getTypeList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
	}
	
	public OrganModel getOrganBaseInfo(String key) throws Exception {
		OrganModel model = new OrganModel();
		B_OrganizationDao dao = new B_OrganizationDao();
		B_OrganizationData dbData = new B_OrganizationData();
		dbData.setRecordid(key);
		dbData = (B_OrganizationData)dao.FindByPrimaryKey(dbData);
		model.setOrganData(dbData);
		
		model.setTypeList(doOptionChange(DicUtil.ORGANTYPE, "").getTypeList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getRecordid());
		
		return model;
		
	}
	

	public OrganModel update(HttpServletRequest request, String data, UserInfo userInfo) {
		OrganModel model = new OrganModel();
		String id = getJsonData(data, "keyBackup");
		
		try {
			B_OrganizationDao dao = new B_OrganizationDao();
			B_OrganizationData dbData = new B_OrganizationData();
			
			//要更新的记录是否存在
			dbData = preCheckId(id);
			
			dbData.setType(getJsonData(data, "type"));
			dbData.setShortname(getJsonData(data, "shortName"));
			dbData.setFullname(getJsonData(data, "fullName"));
			dbData.setAddress(getJsonData(data, "address"));
			
			//更新机构信息表

			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"OrganUpdate",userInfo);

			copyProperties(dbData,commData);
			
			dao.Store(dbData);
			model.setEndInfoMap(NORMAL, "suc001", id);
			
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", id);
		}
		
		return model;
	}
	
	private B_OrganizationData preCheckId(String key) throws Exception {
		B_OrganizationDao dao = new B_OrganizationDao();
		B_OrganizationData dbData = new B_OrganizationData();
		
		dbData.setRecordid(key);
		dbData = (B_OrganizationData)dao.FindByPrimaryKey(dbData);

		return dbData;
	}
	
}
