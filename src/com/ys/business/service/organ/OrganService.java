package com.ys.business.service.organ;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.contact.ContactModel;
import com.ys.business.action.model.organ.OrganModel;
import com.ys.business.action.model.supplier.SupplierModel;
import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.dao.B_OrganBasicInfoDao;
import com.ys.business.db.dao.B_SupplierBasicInfoDao;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_OrganBasicInfoData;
import com.ys.business.db.data.B_SupplierBasicInfoData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.business.service.contact.ContactService;

@Service
public class OrganService extends BaseService {
 	

	public HashMap<String, Object> Init(HttpServletRequest request, String data) {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
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
		
		String key1 = getJsonData(data, "keyword1");
		String key2 = getJsonData(data, "keyword2");
		
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

	public HashMap<String, Object> Search(HttpServletRequest request, String data,UserInfo userInfo) throws Exception {
		
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
		
		String key1 = getJsonData(data, "keyword1");
		String key2 = getJsonData(data, "keyword2");
		

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
	
	public OrganModel Insert(HttpServletRequest request, String data, UserInfo userInfo) {

		OrganModel model = new OrganModel();
		try {
			B_OrganBasicInfoDao dao = new B_OrganBasicInfoDao();
			B_OrganBasicInfoData dbData = new B_OrganBasicInfoData();
			//String organid = getJsonData(data, "supplierId");
		
			//ArrayList<ArrayList<String>> preCheckResult = preCheckSupplierId(request, organid);
			
			//if (preCheckResult.size() > 0) {
				//已存在
			//	model.setEndInfoMap("001", "err005", "");
			//} else {
				String guid = BaseDAO.getGuId();
				dbData.setId(guid);
				dbData.setCategory(getJsonData(data, "category"));
				dbData.setName_short(getJsonData(data, "name_short"));
				dbData.setName_full(getJsonData(data, "name_full"));
				dbData.setAddress(getJsonData(data, "address"));
		
				
				dbData = updateModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				model.setEndInfoMap(NORMAL, "", guid);
			//}
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}	
	
	public OrganModel CreateOrgan(HttpServletRequest request) {

		OrganModel model = new OrganModel();

		try {			
			model.setCategoryList(doOptionChange(DicUtil.ORGANTYPE, "").getCategoryList());
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
	}
	
	public void insert(OrganModel model ,HttpServletRequest request) {

		//OrganModel model = new OrganModel();
		model.setAddress("北京市");
		
		//contactDao.saveOrUpdate(model);
		
		/*
		try {			
			model.setCategoryList(doOptionChange(DicUtil.ORGANTYPE, "").getCategoryList());
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		*/
		//return model;
	
	}
	
	public OrganModel doOptionChange(String type, String parentCode) {
		DicUtil util = new DicUtil();
		OrganModel model = new OrganModel();
		
		try {
			ArrayList<ListOption> optionList = util.getListOption(type, parentCode);
			model.setCategoryList(optionList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
			model.setCategoryList(null);
		}
		
		return model;
	}
	
	private ArrayList<ArrayList<String>> preCheckSupplierId(HttpServletRequest request, String key) throws Exception {
			
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseModel dataModel = new BaseModel();
			dataModel.setQueryFileName("/business/organ/orgquerydefine");
			dataModel.setQueryName("organquerydefine_preCheck");
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("keyword", key);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			
			return baseQuery.getQueryData();
						
	}
	
	public static B_OrganBasicInfoData updateModifyInfo(B_OrganBasicInfoData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
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
		
		model.setSexList(doOptionChange(DicUtil.ORGANTYPE, "").getCategoryList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
	}
	
	public OrganModel getOrganBaseInfo(String key) throws Exception {
		OrganModel model = new OrganModel();
		B_OrganBasicInfoDao dao = new B_OrganBasicInfoDao();
		B_OrganBasicInfoData dbData = new B_OrganBasicInfoData();
		dbData.setId(key);
		dbData = (B_OrganBasicInfoData)dao.FindByPrimaryKey(dbData);
		model.setOrganData(dbData);
		
		model.setCategoryList(doOptionChange(DicUtil.ORGANTYPE, "").getCategoryList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
		
	}
	

	public OrganModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) {
		OrganModel model = new OrganModel();
		String id = getJsonData(data, "keyBackup");
		
		try {
			B_OrganBasicInfoDao dao = new B_OrganBasicInfoDao();
			B_OrganBasicInfoData dbData = new B_OrganBasicInfoData();
			
			String supplierid = getJsonData(data, "supplierId");
			boolean isKeyExist = false;
			
			//要更新的记录是否存在
			isKeyExist = preCheckId(id);
			if (isKeyExist) {
				//ArrayList<ArrayList<String>> preCheckResult = preCheckSupplierId(request, supplierid);
				
				//要更新的供应商id是否存在
				//if (preCheckResult.size() != 0 && !preCheckResult.get(0).get(1).equals(id)) {					
					//已存在
					//model.setEndInfoMap("001", "err007", "");
				//} else {
					dbData.setId(getJsonData(data, "keyBackup"));
					dbData.setCategory(getJsonData(data, "category"));
					dbData.setName_short(getJsonData(data, "name_short"));
					dbData.setName_full(getJsonData(data, "name_full"));
					dbData.setAddress(getJsonData(data, "address"));
					
					dbData = updateModifyInfo(dbData, userInfo);
					dao.Store(dbData);
					model.setEndInfoMap(NORMAL, "", id);
				//}
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
	
	private boolean preCheckId(String key) throws Exception {
		B_OrganBasicInfoDao dao = new B_OrganBasicInfoDao();
		B_OrganBasicInfoData dbData = new B_OrganBasicInfoData();
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
	
}