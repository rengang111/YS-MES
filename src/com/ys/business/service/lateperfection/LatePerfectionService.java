
package com.ys.business.service.lateperfection;

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
import com.ys.business.action.model.lateperfection.LatePerfectionModel;
import com.ys.business.db.dao.B_LatePerfectionQuestionDao;
import com.ys.business.db.dao.B_LatePerfectionRelationFileDao;
import com.ys.business.db.dao.B_ProjectTaskDao;
import com.ys.business.db.data.B_LatePerfectionQuestionData;
import com.ys.business.db.data.B_LatePerfectionRelationFileData;
import com.ys.business.db.data.B_ProjectTaskData;
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
public class LatePerfectionService extends BaseService {
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

	public LatePerfectionModel getProjectBaseInfo(HttpServletRequest request, String key) throws Exception {
		BaseModel dataModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		ArrayList<ArrayList<String>> finishTimeList = new ArrayList<ArrayList<String>>();
		String lastestFinishDate = "";
		
		LatePerfectionModel model = new LatePerfectionModel();
		B_LatePerfectionQuestionDao dao = new B_LatePerfectionQuestionDao();
		B_LatePerfectionQuestionData dbData = new B_LatePerfectionQuestionData();
		
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
	public HashMap<String, Object> doGetTPFileList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String length = "";
		String projectId = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		projectId = getJsonData(data, "keyBackup");

		if (projectId.equals("")) {
			projectId = "-1";
		}
		

		dataModel.setQueryFileName("/business/lateperfection/lateperfectionquerydefine");
		dataModel.setQueryName("lateperfectionquerydefine_relationfilesearch");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("projectId", projectId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
				
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	
	public HashMap<String, Object> doGetQuestionList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

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

		
		dataModel.setQueryFileName("/business/lateperfection/lateperfectionquerydefine");
		dataModel.setQueryName("lateperfectionquerydefine_questionsearch");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("projectId", projectId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
				
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}	
	
	public LatePerfectionModel doUpdateTPFileInit(HttpServletRequest request, String projectId, String id) throws Exception {
		LatePerfectionModel model = new LatePerfectionModel();
		B_LatePerfectionRelationFileDao dao = new B_LatePerfectionRelationFileDao();
		B_LatePerfectionRelationFileData dbData = new B_LatePerfectionRelationFileData();

		if (id != null && !id.equals("")) {
			dbData.setId(id);
			dbData = (B_LatePerfectionRelationFileData)dao.FindByPrimaryKey(dbData);			
		}
		
		model.setRelationFileData(dbData);
		
		model.setKeyBackup(id);
		model.setProjectId(projectId);
		//model.setType(type);

		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}	
	
	public LatePerfectionModel doUpdateQuestionInit(HttpServletRequest request, String projectId, String id) throws Exception {
		LatePerfectionModel model = new LatePerfectionModel();
		B_LatePerfectionQuestionDao dao = new B_LatePerfectionQuestionDao();
		B_LatePerfectionQuestionData dbData = new B_LatePerfectionQuestionData();
		
		if (id != null && !id.equals("")) {
			dbData.setId(id);
			dbData = (B_LatePerfectionQuestionData)dao.FindByPrimaryKey(dbData);
			
			CalendarUtil calendarUtil;
			if (dbData.getCreatedate() != null) {
				calendarUtil = new CalendarUtil(dbData.getCreatedate());
				dbData.setCreatedate(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			}
			if (dbData.getExpectfinishdate() != null) {
				calendarUtil = new CalendarUtil(dbData.getExpectfinishdate());
				dbData.setExpectfinishdate(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			}
			if (dbData.getFinishtime() != null) {
				calendarUtil = new CalendarUtil(dbData.getFinishtime());
				dbData.setFinishtime(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			}
		}
				
		model.setQuestionData(dbData);
		
		model.setKeyBackup(id);
		model.setProjectId(projectId);

		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}	
	
	public LatePerfectionModel doUpdateTPFile(HttpServletRequest request, String data, UserInfo userInfo) {
		LatePerfectionModel model = new LatePerfectionModel();
    	B_LatePerfectionRelationFileData dbData = new B_LatePerfectionRelationFileData();											
    	B_LatePerfectionRelationFileDao dao = new B_LatePerfectionRelationFileDao();											

		String key = getJsonData(data, "keyBackup");
		String guid = "";
		
		try {
													
			if (key == null || key.equals("")) {
				guid = BaseDAO.getGuId();									
				dbData.setId(guid);									
				dbData.setProjectid(getJsonData(data, "projectId"));
				dbData.setTitle(getJsonData(data, "title"));
				dbData.setFilename(getJsonData(data, "fileName"));
				dbData.setPath(getJsonData(data, "path"));
				dbData.setMemo(getJsonData(data, "memo"));
				dbData = updateTPFileModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				key = guid;
			} else {
				dbData.setId(key);
				dbData = (B_LatePerfectionRelationFileData)dao.FindByPrimaryKey(dbData);
				dbData.setTitle(getJsonData(data, "title"));
				dbData.setFilename(getJsonData(data, "fileName"));
				dbData.setPath(getJsonData(data, "path"));
				dbData.setMemo(getJsonData(data, "memo"));
				dbData = updateTPFileModifyInfo(dbData, userInfo);
				dao.Store(dbData);
			}

			model.setEndInfoMap(NORMAL, "", key);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", key);
		}
		
		return model;
	}		
	
	public LatePerfectionModel doUpdateQuestion(HttpServletRequest request, String data, UserInfo userInfo) {
		LatePerfectionModel model = new LatePerfectionModel();
    	B_LatePerfectionQuestionData dbData = new B_LatePerfectionQuestionData();											
    	B_LatePerfectionQuestionDao dao = new B_LatePerfectionQuestionDao();											

		String key = getJsonData(data, "keyBackup");
		String guid = "";
		
		try {
													
			if (key == null || key.equals("")) {
				guid = BaseDAO.getGuId();									
				dbData.setId(guid);									
				dbData.setProjectid(getJsonData(data, "projectId"));
				dbData.setCreatedate(getJsonData(data, "createDate"));
				dbData.setDescription(getJsonData(data, "description"));
				dbData.setImprove(getJsonData(data, "improve"));
				dbData.setExpectfinishdate(getJsonData(data, "expectDate"));
				dbData.setFinishtime(getJsonData(data, "finishTime"));
				dbData = updateQuestionModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				key = guid;
			} else {
				dbData.setId(key);
				dbData = (B_LatePerfectionQuestionData)dao.FindByPrimaryKey(dbData);
				dbData.setProjectid(getJsonData(data, "projectId"));
				dbData.setCreatedate(getJsonData(data, "createDate"));
				dbData.setDescription(getJsonData(data, "description"));
				dbData.setImprove(getJsonData(data, "improve"));
				dbData.setExpectfinishdate(getJsonData(data, "expectDate"));
				dbData.setFinishtime(getJsonData(data, "finishTime"));
				dbData = updateQuestionModifyInfo(dbData, userInfo);
				dao.Store(dbData);
			}

			model.setEndInfoMap(NORMAL, "", key);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", key);
		}
		
		return model;
	}
	
	public LatePerfectionModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		LatePerfectionModel model = new LatePerfectionModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeLatePerfectionDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public LatePerfectionModel doDeleteTPFile(HttpServletRequest request, String data, UserInfo userInfo){
		
		LatePerfectionModel model = new LatePerfectionModel();
    	
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeLatePerfectionTPFileDelete(data, userInfo);					
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public LatePerfectionModel doDeleteQuestion(HttpServletRequest request, String data, UserInfo userInfo){
		
		LatePerfectionModel model = new LatePerfectionModel();
    	
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeLatePerfectionQuestionDelete(data, userInfo);					
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public static B_LatePerfectionRelationFileData updateTPFileModifyInfo(B_LatePerfectionRelationFileData data, UserInfo userInfo) {
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
	
	public static B_LatePerfectionQuestionData updateQuestionModifyInfo(B_LatePerfectionQuestionData data, UserInfo userInfo) {
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

	

	

	

	
}
