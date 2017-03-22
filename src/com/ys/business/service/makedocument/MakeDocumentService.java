
package com.ys.business.service.makedocument;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.externalsample.ExternalSampleModel;
import com.ys.business.action.model.makedocument.MakeDocumentModel;
import com.ys.business.action.model.processcontrol.ProcessControlModel;
import com.ys.business.db.dao.B_BaseTechDocDao;
import com.ys.business.db.dao.B_DocFileFolderDao;
import com.ys.business.db.dao.B_ExternalSampleDao;
import com.ys.business.db.dao.B_FolderDao;
import com.ys.business.db.dao.B_WorkingFilesDao;
import com.ys.business.db.data.B_BaseTechDocData;
import com.ys.business.db.data.B_DocFileFolderData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_FolderData;
import com.ys.business.db.data.B_WorkingFilesData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.business.service.supplier.SupplierService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.role.RoleModel;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

import net.sf.json.JSONArray;

import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_ROLEDao;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_ROLEData;
import com.ys.system.db.data.S_USERData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.common.I_BaseService;
import com.ys.system.service.common.I_MultiAlbumService;
import com.ys.system.service.user.UserService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.UploadReceiver;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class MakeDocumentService extends BaseService implements I_BaseService {
	/*
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
		
		dataModel.setQueryFileName("/business/processcontrol/processcontrolquerydefine");
		dataModel.setQueryName("projecttaskquerydefine_search");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	
		
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}
		
		ArrayList<HashMap<String, String>> dbData = dataModel.getYsViewData();
		for(HashMap<String, String>rowData:dbData) {
			String endTime = rowData.get("endTime");
			String lastFinishTime = rowData.get("lastFinishTime");
			rowData.put("exceedTime", CalendarUtil.getDayBetween(endTime, lastFinishTime));
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}

	public MakeDocumentModel getProjectBaseInfo(HttpServletRequest request, String key) throws Exception {
		BaseModel dataModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		ArrayList<ArrayList<String>> finishTimeList = new ArrayList<ArrayList<String>>();
		String lastestFinishDate = "";
		
		MakeDocumentModel model = new MakeDocumentModel();
		B_WorkingFilesDao dao = new B_WorkingFilesDao();
		B_WorkingFilesData dbData = new B_WorkingFilesData();
		
		B_ProjectTaskDao taskDao = new B_ProjectTaskDao();
		B_ProjectTaskData taskData = new B_ProjectTaskData();
		taskData.setId(key);
		taskData = (B_ProjectTaskData)taskDao.FindByPrimaryKey(taskData);
		
		S_USERDao userDao = new S_USERDao();
		S_USERData userData = new S_USERData();
		userData.setUserid(taskData.getManager());
		userData = (S_USERData)userDao.FindByPrimaryKey(userData);
		taskData.setManager(userData.getLoginname());
		
		CalendarUtil calendarUtil = new CalendarUtil(taskData.getBegintime());

		taskData.setBegintime(calendarUtil.fmtDate(calendarUtil.getDate(), "yyyy-MM-dd"));
		calendarUtil = new CalendarUtil(taskData.getEndtime());
		taskData.setEndtime(calendarUtil.fmtDate(calendarUtil.getDate(), "yyyy-MM-dd"));
		
		model.setProjectTaskData(taskData);
		
		dataModel.setQueryFileName("/business/processcontrol/processcontrolquerydefine");
		dataModel.setQueryName("processcontrolquerydefine_isFinished");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("projectId", dbData.getProjectid());
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		finishTimeList = baseQuery.getFullData();
		if (finishTimeList.size() == 0) {
			model.setExceedTime(CalendarUtil.getDayBetween(taskData.getEndtime(), ""));
		} else {
			boolean unFinished = false;
			for(ArrayList<String> finishTime:finishTimeList) {
				if (finishTime.get(0).equals("")) {
					unFinished = true;
					break;
				} else {
					if (CalendarUtil.compareDate(lastestFinishDate, finishTime.get(0)) == 1) {
						lastestFinishDate = finishTime.get(0);
					}
				}
			}
			if (unFinished) {
				model.setExceedTime(CalendarUtil.getDayBetween(taskData.getEndtime(), ""));
			} else {
				model.setExceedTime(CalendarUtil.getDayBetween(taskData.getEndtime(), lastestFinishDate));
			}
		}
		
		model.setKeyBackup(key);
		
		model.setEndInfoMap("098", "0001", "");
		
		
		return model;
		
	}	
*/
	public HashMap<String, Object> doGetBaseTechDocList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String length = "";
		String projectId = "";
		String type = "";
		data = URLDecoder.decode(data, "UTF-8");

		projectId = getJsonData(data, "keyBackup");
		type = getJsonData(data, "type");
		
		if (projectId.equals("")) {
			projectId = "-1";
		}
		
		
		dataModel.setQueryFileName("/business/makedocument/makedocumentquerydefine");
		dataModel.setQueryName("makedocumentquerydefine_basetechdocsearch");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("projectId", projectId);
		userDefinedSearchCase.put("type", type);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
				
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	
	public HashMap<String, Object> doGetWorkingFileList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();

		String projectId = "";		
		data = URLDecoder.decode(data, "UTF-8");

		projectId = getJsonData(data, "keyBackup");
		if (projectId.equals("")) {
			projectId = "-1";
		}
		
		dataModel.setQueryFileName("/business/makedocument/makedocumentquerydefine");
		dataModel.setQueryName("makedocumentquerydefine_workingsearch");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("projectId", projectId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
				
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}	
	
	public MakeDocumentModel doUpdateBaseTechDocInit(HttpServletRequest request, String projectId, String id, String type) throws Exception {
		MakeDocumentModel model = new MakeDocumentModel();
		B_BaseTechDocDao dao = new B_BaseTechDocDao();
		B_BaseTechDocData dbData = new B_BaseTechDocData();

		if (id != null && !id.equals("")) {
			dbData.setId(id);
			dbData = (B_BaseTechDocData)dao.FindByPrimaryKey(dbData);			
		}
		
		model.setBaseTechDocData(dbData);
		
		model.setKeyBackup(id);
		model.setProjectId(projectId);
		model.setType(type);

		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}	
	
	public MakeDocumentModel doUpdateWorkingFileInit(HttpServletRequest request, String projectId, String id) throws Exception {
		MakeDocumentModel model = new MakeDocumentModel();
		B_WorkingFilesDao dao = new B_WorkingFilesDao();
		B_WorkingFilesData dbData = new B_WorkingFilesData();
		
		if (id != null && !id.equals("")) {
			dbData.setId(id);
			dbData = (B_WorkingFilesData)dao.FindByPrimaryKey(dbData);

		}
				
		model.setWorkingFilesData(dbData);
		
		model.setKeyBackup(id);
		model.setProjectId(projectId);

		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}	
	
	public MakeDocumentModel doUpdateBaseTechDoc(HttpServletRequest request, String data, UserInfo userInfo) {
		MakeDocumentModel model = new MakeDocumentModel();
    	B_BaseTechDocData dbData = new B_BaseTechDocData();											
    	B_BaseTechDocDao dao = new B_BaseTechDocDao();											

		String key = "";
		String guid = "";
		String type = "";
		
		try {
			data = URLDecoder.decode(data, "UTF-8");
			key = getJsonData(data, "keyBackup");
			type = getJsonData(data, "type");
			
			if (key == null || key.equals("")) {
				guid = BaseDAO.getGuId();									
				dbData.setId(guid);						
				dbData.setProjectid(getJsonData(data, "projectId"));
				dbData.setType(type);
				dbData.setTitle(getJsonData(data, "title"));
				dbData.setFilename(getJsonData(data, "fileName"));
				dbData.setPath(getJsonData(data, "path"));
				dbData.setMemo(getJsonData(data, "memo"));
				dbData = updateBaseTechDocModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				key = guid;
			} else {
				dbData.setId(key);
				dbData = (B_BaseTechDocData)dao.FindByPrimaryKey(dbData);
				dbData.setTitle(getJsonData(data, "title"));
				dbData.setFilename(getJsonData(data, "fileName"));
				dbData.setPath(getJsonData(data, "path"));
				dbData.setMemo(getJsonData(data, "memo"));
				dbData = updateBaseTechDocModifyInfo(dbData, userInfo);
				dao.Store(dbData);
			}

			model.setEndInfoMap(NORMAL, "", type + "|" + key);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", key);
		}
		
		return model;
	}		
	
	public MakeDocumentModel doUpdateWorkingFile(HttpServletRequest request, String data, UserInfo userInfo) {
		MakeDocumentModel model = new MakeDocumentModel();
    	B_WorkingFilesData dbData = new B_WorkingFilesData();											
    	B_WorkingFilesDao dao = new B_WorkingFilesDao();											

		String key = "";
		String guid = "";
		
		try {
			data = URLDecoder.decode(data, "UTF-8");
			key = getJsonData(data, "keyBackup");				
			if (key == null || key.equals("")) {
				guid = BaseDAO.getGuId();									
				dbData.setId(guid);									
				dbData.setProjectid(getJsonData(data, "projectId"));
				dbData.setFileno(getJsonData(data, "fileNo"));
				dbData.setPartsname(getJsonData(data, "partsName"));
				dbData.setErpno(getJsonData(data, "erpNo"));
				dbData.setMaterial(getJsonData(data, "material"));
				dbData.setWorking(getJsonData(data, "working"));
				dbData = updateWorkingFileModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				key = guid;
			} else {
				dbData.setId(key);
				dbData = (B_WorkingFilesData)dao.FindByPrimaryKey(dbData);
				dbData.setProjectid(getJsonData(data, "projectId"));
				dbData.setFileno(getJsonData(data, "fileNo"));
				dbData.setPartsname(getJsonData(data, "partsName"));
				dbData.setErpno(getJsonData(data, "erpNo"));
				dbData.setMaterial(getJsonData(data, "material"));
				dbData.setWorking(getJsonData(data, "working"));
				dbData = updateWorkingFileModifyInfo(dbData, userInfo);
				dao.Store(dbData);
			}

			model.setEndInfoMap(NORMAL, "", key);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", key);
		}
		
		return model;
	}
	
	public MakeDocumentModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		MakeDocumentModel model = new MakeDocumentModel();
		
		try {
			data = URLDecoder.decode(data, "UTF-8");
			
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeMakeDocumentDelete(data, userInfo);
	        
			String removeData[] = data.split(",");									
			for (String projectId:removeData) {
				removeProjectFiles(request, projectId);
			}
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public MakeDocumentModel doDeleteBaseTechDoc(HttpServletRequest request, String data, UserInfo userInfo){
		
		MakeDocumentModel model = new MakeDocumentModel();
		String type = getJsonData(data, "type");
		
		try {
			data = URLDecoder.decode(data, "UTF-8");
			
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeBaseTechDocDelete(data, userInfo);					
	        
	        model.setEndInfoMap(NORMAL, "", type);
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public MakeDocumentModel doDeleteWorkingFile(HttpServletRequest request, String data, UserInfo userInfo){
		
		MakeDocumentModel model = new MakeDocumentModel();
    	
		try {
			data = URLDecoder.decode(data, "UTF-8");
			
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeWorkingFileDelete(data, userInfo);					
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public ProcessControlModel getFolderNames(ProcessControlModel model, HttpServletRequest request, String projectId) {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<ArrayList<String>> dbData = new ArrayList<ArrayList<String>>();
		String folderNames = "";
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		try {
			dataModel.setQueryFileName("/business/makedocument/makedocumentquerydefine");
			dataModel.setQueryName("makedocumentquerydefine_getFolderNames");
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("projectId", projectId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			dbData = baseQuery.getFullData();
			
			for(ArrayList<String> rowData:dbData) {
				folderNames += rowData.get(0) + ",";
			}
			if (folderNames.length() > 0) {
				folderNames = folderNames.substring(0, folderNames.length() - 1);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

		model.setFolderNames(folderNames);
		
		return model;
	}
	
	public MakeDocumentModel getFileNames(HttpServletRequest request, MakeDocumentModel model, String projectId, String title) {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<ArrayList<String>> dbData = new ArrayList<ArrayList<String>>();
		ArrayList<String> fileNames = new ArrayList<String>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		try {
			dataModel.setQueryFileName("/business/makedocument/makedocumentquerydefine");
			dataModel.setQueryName("makedocumentquerydefine_getFileNames");
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("projectId", projectId);
			userDefinedSearchCase.put("folderName", title);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			dbData = baseQuery.getFullData();
			
			for(ArrayList<String> rowData:dbData) {
				fileNames.add(rowData.get(0));
			}

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

		model.setFilenames(fileNames);
		model.setImageKey(projectId);
		model.setPath(BusinessConstants.BUSINESSPHOTOPATH);
		model.setNowUseImage("");
		
		return model;
	}
	
	public ArrayList<String> getProjectFileNames(HttpServletRequest request, String projectId) {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<ArrayList<String>> dbData = new ArrayList<ArrayList<String>>();
		ArrayList<String> fileNames = new ArrayList<String>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		try {
			dataModel.setQueryFileName("/business/makedocument/makedocumentquerydefine");
			dataModel.setQueryName("makedocumentquerydefine_getProjectFileNames");
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("projectId", projectId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			dbData = baseQuery.getFullData();
			
			for(ArrayList<String> rowData:dbData) {
				fileNames.add(rowData.get(0));
			}

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return fileNames;
	}	
	
	public MakeDocumentModel doUpdateFolderEditInit(HttpServletRequest request, UserInfo userInfo) throws Exception {
		MakeDocumentModel model = new MakeDocumentModel();

		String projectId = request.getParameter("projectId");
		String name = request.getParameter("folderName");
		
		if (name != null && !name.equals("")) {
			String folderId = getFolderName(request, projectId, name);
			if (!folderId.equals("")) {
				model.setKeyBackup(folderId);
			}
		}
		
		model.setProjectId(projectId);
		model.setFolderName(name);
		model.setOldFolderName(name);

		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public MakeDocumentModel doUpdateFolder(HttpServletRequest request, String data, UserInfo userInfo) {
		MakeDocumentModel model = new MakeDocumentModel();										

		B_FolderDao dao = new B_FolderDao();
		B_FolderData dbData = new B_FolderData();
		
		String key = "";
		String projectId = "";
		String folderName = "";
		//String oldFolderName = getJsonData(data, "oldFolderName");
		String guid = "";
		
		try {
			data = URLDecoder.decode(data, "UTF-8");
			
			key = getJsonData(data, "keyBackup");
			projectId = getJsonData(data, "projectId");
			folderName = getJsonData(data, "title");
			
			String folderId = getFolderName(request, projectId, folderName);
			
			if (folderId.equals("")) {
			
				if (key == null || key.equals("")) {
					guid = BaseDAO.getGuId();									
					dbData.setId(guid);									
					dbData.setProjectid(projectId);
					dbData.setFoldername(folderName);
					dbData = updateFolderDataModifyInfo(dbData, userInfo);
					dao.Create(dbData);
					key = guid;
				} else {
					dbData.setId(key);
					dbData = (B_FolderData)dao.FindByPrimaryKey(dbData);
					dbData.setFoldername(folderName);
					dbData = updateFolderDataModifyInfo(dbData, userInfo);
					dao.Store(dbData);
				}
			} else {
				
			}
			model.setEndInfoMap(NORMAL, "", folderName);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", folderName);
		}
		
		return model;
	}
	
	public MakeDocumentModel doDeleteFolder(HttpServletRequest request, String data, UserInfo userInfo) {
		boolean isDBOperationSuccessed = false;
		MakeDocumentModel model = new MakeDocumentModel();
		
		try {
			data = URLDecoder.decode(data, "UTF-8");
			
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.doDeleteFolder(request, data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
	        isDBOperationSuccessed = true;
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
			
		if (isDBOperationSuccessed) {

			String projectId = getJsonData(data, "keyBackup");
			String folderName = getJsonData(data, "tabTitle");
			
			removeFiles(request, model, projectId, folderName);
			
			model.setFilenames(new ArrayList<String>());
			model.setEndInfoMap(NORMAL, "", folderName);
		
		}

		return model;
	}	
	
	public void addMultiAlbumData(String projectId, String folderName, String fileName) throws Exception {
		B_DocFileFolderDao dao = new B_DocFileFolderDao();
		B_DocFileFolderData dbData = new B_DocFileFolderData();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute(BusinessConstants.SESSION_USERINFO);
		String folderId = getFolderName(request, projectId, folderName);
		
		String guid = BaseDAO.getGuId();
		dbData.setId(guid);
		dbData.setProjectid(projectId);
		dbData.setFolderid(folderId);
		dbData.setImage_filename(fileName);
		dbData = updateDocFileFolderModifyInfo(dbData, userInfo);
		dao.Create(dbData);
	}
	
	public MakeDocumentModel getFileList(HttpServletRequest request, MakeDocumentModel model) {
		UploadReceiver uploadReceiver = new UploadReceiver();

/*		
		String dir = request.getSession().getServletContext().getRealPath("/")
				+ BusinessConstants.BUSINESSPHOTOPATH + model.getExternalSampleData().getId() + BusinessConstants.BUSINESSSMALLPHOTOPATH; 
		
		String [] filenames = uploadReceiver.getFileNameList(dir);
		
		if (null != filenames && filenames.length > 0){
			

				
				ArrayList<String> list_image = new ArrayList<>(Arrays.asList(filenames));
				
				
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
*/	
		return model;
	}
	
	public void setNowUseImage(String key, String src) throws Exception {
		/*
		B_ExternalSampleDao dao = new B_ExternalSampleDao();
		B_ExternalSampleData dbData = new B_ExternalSampleData();
		
		dbData.setId(key);
		dbData = (B_ExternalSampleData)dao.FindByPrimaryKey(dbData);
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute(BusinessConstants.SESSION_USERINFO);
		
		dbData = updateModifyInfo(dbData, userInfo);
		dbData.setImage_filename(src);
		dao.Store(dbData);
		*/
	}
	
	public String getNowUseImage(String key) {
		//B_ExternalSampleDao dao = new B_ExternalSampleDao();
		//B_ExternalSampleData dbData = new B_ExternalSampleData();
		
		String nowUseImage = "";
		
		try {
			//dbData.setId(key);
			//dbData = (B_ExternalSampleData)dao.FindByPrimaryKey(dbData);
			//nowUseImage = dbData.getImage_filename();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return nowUseImage;
		
	}	
	
	
	public static B_BaseTechDocData updateBaseTechDocModifyInfo(B_BaseTechDocData data, UserInfo userInfo) {
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
	
	public static B_WorkingFilesData updateWorkingFileModifyInfo(B_WorkingFilesData data, UserInfo userInfo) {
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

	public static B_FolderData updateFolderDataModifyInfo(B_FolderData data, UserInfo userInfo) {
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
	
	public static B_DocFileFolderData updateDocFileFolderModifyInfo(B_DocFileFolderData data, UserInfo userInfo) {
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
	
	public static String getFolderName(HttpServletRequest request, String projectId, String folderName) throws Exception {
		String folderId = "";
		BaseModel dataModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		
		dataModel.setQueryFileName("/business/makedocument/makedocumentquerydefine");
		dataModel.setQueryName("makedocumentquerydefine_checkFolderName");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("projectId", projectId);
		userDefinedSearchCase.put("folderName", folderName);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);
		
		if (baseQuery.getFullData().size() > 0) {
			folderId = baseQuery.getFullData().get(0).get(0);
		}
		
		return folderId;
	
	}
	
	private void removeFiles(HttpServletRequest request, MakeDocumentModel model, String projectId, String folderName) {
		UploadReceiver uploadReceiver = new UploadReceiver();
		
		
		model = getFileNames(request, model, projectId, folderName);
		String dir = request.getSession().getServletContext().getRealPath("/")
				+ BusinessConstants.BUSINESSPHOTOPATH + projectId;
		String dirSmall = dir + BusinessConstants.BUSINESSSMALLPHOTOPATH;
		for(String fileName:model.getFilenames()) {
			 uploadReceiver.deleteFile(request, projectId, fileName);
		}
		String [] fileNames = uploadReceiver.getFileNameList(dirSmall);
		if(fileNames!= null && fileNames.length == 0) {
			File f = new File(dirSmall);
	    	if(f.exists()) {
	    		f.delete(); 
	    	}
	    	
	    	f = new File(dir);
		    if(f.exists()) {
		    	f.delete(); 
		    }
		}
	}

	private void removeProjectFiles(HttpServletRequest request, String projectId) {
		UploadReceiver uploadReceiver = new UploadReceiver();
		ArrayList<String> fileNames = new ArrayList<String>();
		
		fileNames = getProjectFileNames(request, projectId);
		String dir = request.getSession().getServletContext().getRealPath("/")
				+ BusinessConstants.BUSINESSPHOTOPATH + projectId;
		String dirSmall = dir + BusinessConstants.BUSINESSSMALLPHOTOPATH;
		for(String fileName:fileNames) {
			 uploadReceiver.deleteFile(request, projectId, fileName);
		}
		String [] smallFileNames = uploadReceiver.getFileNameList(dirSmall);
		if(smallFileNames!= null && smallFileNames.length == 0) {
			File f = new File(dirSmall);
	    	if(f.exists()) {
	    		f.delete(); 
	    	}
	    	
	    	f = new File(dir);
		    if(f.exists()) {
		    	f.delete(); 
		    }
		}
	}
}
