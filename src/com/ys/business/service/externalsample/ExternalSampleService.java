package com.ys.business.service.externalsample;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.externalsample.ExternalSampleModel;
import com.ys.business.db.dao.B_ExternalSampleDao;
import com.ys.business.db.data.B_ExternalSampleData;
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
import com.ys.system.service.common.I_BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.UploadReceiver;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class ExternalSampleService extends BaseService implements I_BaseService {
 
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
		
		dataModel.setQueryFileName("/business/externalsample/externalsamplequerydefine");
		dataModel.setQueryName("externalsamplequerydefine_search");
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

	public ExternalSampleModel doAddInit(HttpServletRequest request) {

		ExternalSampleModel model = new ExternalSampleModel();

		try {			
			model.setCurrencyList(doOptionChange(DicUtil.CURRENCY, "").getCurrencyList());
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
	}

	public ExternalSampleModel doAdd(HttpServletRequest request, String data, UserInfo userInfo) {

		ExternalSampleModel model = new ExternalSampleModel();
		try {
			B_ExternalSampleDao dao = new B_ExternalSampleDao();
			B_ExternalSampleData dbData = new B_ExternalSampleData();
			String externalsampleid = getJsonData(data, "sampleId");
		
			ArrayList<ArrayList<String>> preCheckResult = preCheckExternalSampleId(request, externalsampleid);
			
			if (preCheckResult.size() > 0) {
				//已存在
				model.setEndInfoMap("001", "err005", "");
			} else {
				String guid = BaseDAO.getGuId();
				dbData.setId(guid);
				dbData.setSampleid(getJsonData(data, "sampleId"));
				dbData.setSampleversion(getJsonData(data, "sampleVersion"));
				dbData.setSamplename(getJsonData(data, "sampleName"));
				dbData.setBrand(getJsonData(data, "brand"));
				
				String buyTime = getJsonData(data, "buyTime");
				if (buyTime == null || buyTime.equals("")) {
					dbData.setBuytime(null);
				} else {
					dbData.setBuytime(buyTime);
				}
				
				dbData.setCurrency(getJsonData(data, "currency"));
				dbData.setPrice(getJsonData(data, "price"));
				dbData.setAddress(getJsonData(data, "address"));
				dbData.setMemo(getJsonData(data, "memo"));
				
				dbData = updateModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				model.setEndInfoMap(NORMAL, "", guid);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}	

	
	public ExternalSampleModel doOptionChange(String type, String parentCode) {
		DicUtil util = new DicUtil();
		ExternalSampleModel model = new ExternalSampleModel();
		
		try {
			ArrayList<ListOption> optionList = util.getListOption(type, parentCode);
			model.setCurrencyList(optionList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
			model.setCurrencyList(null);
		}
		
		return model;
	}
	
	public ExternalSampleModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) {
		ExternalSampleModel model = new ExternalSampleModel();
		String id = getJsonData(data, "keyBackup");
		
		try {
			B_ExternalSampleDao dao = new B_ExternalSampleDao();
			B_ExternalSampleData dbData = new B_ExternalSampleData();
			
			String externalsampleid = getJsonData(data, "sampleId");
			boolean isKeyExist = false;
			
			//要更新的记录是否存在
			isKeyExist = preCheckId(id);
			if (isKeyExist) {
				ArrayList<ArrayList<String>> preCheckResult = preCheckExternalSampleId(request, externalsampleid);
				
				//要更新的供应商id是否存在
				if (preCheckResult.size() != 0 && !preCheckResult.get(0).get(1).equals(id)) {					
					//已存在
					model.setEndInfoMap("001", "err007", "");
				} else {
					dbData.setId(getJsonData(data, "keyBackup"));
					dbData.setSampleid(getJsonData(data, "sampleId"));
					dbData.setSampleversion(getJsonData(data, "sampleVersion"));
					dbData.setSamplename(getJsonData(data, "sampleName"));
					dbData.setBrand(getJsonData(data, "brand"));
					String buyTime = getJsonData(data, "buyTime");
					if (buyTime == null || buyTime.equals("")) {
						dbData.setBuytime(null);
					} else {
						dbData.setBuytime(buyTime);
					}
					dbData.setCurrency(getJsonData(data, "currency"));
					dbData.setPrice(getJsonData(data, "price"));
					dbData.setAddress(getJsonData(data, "address"));
					dbData.setMemo(getJsonData(data, "memo"));
					
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
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", id);
		}
		
		return model;
	}
	
	public ExternalSampleModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		ExternalSampleModel model = new ExternalSampleModel();
		boolean isDBOperationSuccessed = false;
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeExternalSampleDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
	        isDBOperationSuccessed = true;
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		if (isDBOperationSuccessed) {
			UploadReceiver uploadReceiver = new UploadReceiver();
			String dir = request.getSession().getServletContext().getRealPath("/")
					+ BusinessConstants.BUSINESSPHOTOPATH + data; 			
			String dirSmall = dir + BusinessConstants.BUSINESSSMALLPHOTOPATH; 			
			
			String[] fileNames = uploadReceiver.getFileNameList(dirSmall);
			if (fileNames != null) {
				for(String fileName:fileNames) {
					uploadReceiver.deleteFile(request, data, fileName);
				}
			}
			uploadReceiver.deleteFolder(request, data);
			
			deleteUploadFiles(request, data);
		}
		
		return model;
	}
	
	public static B_ExternalSampleData updateModifyInfo(B_ExternalSampleData data, UserInfo userInfo) {
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
	
	private boolean isDataExist(B_ExternalSampleData dbData) {
		boolean rtnValue = false;
		
		try {
			B_ExternalSampleDao dao = new B_ExternalSampleDao();
			dao.FindByPrimaryKey(dbData);
			rtnValue = true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return rtnValue;
		
	}
	
	public ExternalSampleModel getExternalSampleBaseInfo(String key) throws Exception {
		ExternalSampleModel model = new ExternalSampleModel();
		B_ExternalSampleDao dao = new B_ExternalSampleDao();
		B_ExternalSampleData dbData = new B_ExternalSampleData();
		dbData.setId(key);
		dbData = (B_ExternalSampleData)dao.FindByPrimaryKey(dbData);
		
		CalendarUtil calendarUtil = new CalendarUtil(dbData.getBuytime());
		dbData.setBuytime(calendarUtil.fmtDate(calendarUtil.getDate(), "yyyy-MM-dd"));
		
		model.setExternalSampleData(dbData);

		model.setCurrencyList(doOptionChange(DicUtil.CURRENCY, "").getCurrencyList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
		
	}
	
	public ExternalSampleModel getFileList(HttpServletRequest request, ExternalSampleModel model) {
		UploadReceiver uploadReceiver = new UploadReceiver();
		
		String dir = request.getSession().getServletContext().getRealPath("/")
				+ BusinessConstants.BUSINESSPHOTOPATH + model.getExternalSampleData().getId() + BusinessConstants.BUSINESSSMALLPHOTOPATH; 
		
		String [] filenames = uploadReceiver.getFileNameList(dir);
		
		String nowUseImage = model.getExternalSampleData().getImage_filename();
		
		if (null != filenames && filenames.length > 0){
			
			//将当前图片放到最前
			if (!(null == nowUseImage||nowUseImage.equals(""))){	
				
				ArrayList<String> list_image = new ArrayList<>(Arrays.asList(filenames));
				
				for(String fileName:list_image) {
					if(fileName.equals(nowUseImage)) {
						list_image.remove(nowUseImage);
						break;
					}
				}
				
				list_image.add(0, nowUseImage);		
				
				filenames = new String[list_image.size()];
				int index = 0;
				for(Object fileName:list_image) {
					filenames[index++] = String.valueOf(fileName);
				}
			}			
		}
		
		model.setFilenames(filenames);
		model.setImageKey(model.getExternalSampleData().getId());
		model.setPath(BusinessConstants.BUSINESSPHOTOPATH);
		model.setNowUseImage(nowUseImage);
		
		return model;
	}
	
	public void setNowUseImage(String key, String src) throws Exception {
		B_ExternalSampleDao dao = new B_ExternalSampleDao();
		B_ExternalSampleData dbData = new B_ExternalSampleData();
		
		dbData.setId(key);
		dbData = (B_ExternalSampleData)dao.FindByPrimaryKey(dbData);
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute(BusinessConstants.SESSION_USERINFO);
		
		dbData = updateModifyInfo(dbData, userInfo);
		dbData.setImage_filename(src);
		dao.Store(dbData);
		
	}
	
	public String getNowUseImage(String key) {
		B_ExternalSampleDao dao = new B_ExternalSampleDao();
		B_ExternalSampleData dbData = new B_ExternalSampleData();
		
		String nowUseImage = "";
		
		try {
			dbData.setId(key);
			dbData = (B_ExternalSampleData)dao.FindByPrimaryKey(dbData);
			nowUseImage = dbData.getImage_filename();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return nowUseImage;
		
	}	
	
	
	private ArrayList<ArrayList<String>> preCheckExternalSampleId(HttpServletRequest request, String key) throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/externalsample/externalsamplequerydefine");
		dataModel.setQueryName("externalsamplequerydefine_preCheck");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		return baseQuery.getQueryData();
					
	}
	
	private boolean preCheckId(String key) throws Exception {
		B_ExternalSampleDao dao = new B_ExternalSampleDao();
		B_ExternalSampleData dbData = new B_ExternalSampleData();
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
