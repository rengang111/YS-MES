
package com.ys.business.service.processcontrol;

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
import com.ys.business.action.model.processcontrol.ProcessControlModel;
import com.ys.business.db.dao.B_ProcessControlDao;
import com.ys.business.db.dao.B_ProjectTaskDao;
import com.ys.business.db.data.B_ProcessControlData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
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
import com.ys.util.basequery.BaseQuery;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class ProcessControlService extends BaseService {
 
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
			if (rowData.get("endTime").equals("0")) {
				rowData.put("exceedTime", getDayBetween(rowData.get("endTime"), rowData.get("lastFinishTime")));
			} else {
				rowData.put("exceedTime", getDayBetween(rowData.get("endTime"), ""));
			}
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	
	public HashMap<String, Object> doGetProcessCollect(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String length = "";
		String key = "";
		
		String[] titleList = {"3D完成", "3D手模", "3D工作样机", "模具确认", "模具完成", "模具调整",
				"委外加工", "试产", "文档整理"};
		
		data = URLDecoder.decode(data, "UTF-8");

		key = getJsonData(data, "keyBackup");
				
		dataModel.setQueryFileName("/business/processcontrol/processcontrolquerydefine");
		dataModel.setQueryName("processcontrolquerydefine_searchcollect");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
		
		ArrayList<HashMap<String, String>> dbData = dataModel.getYsViewData();
		HashMap<String, String>rowData = null;
		for(int i = 0; i < dbData.size(); i++) {
			HashMap<String, String>tempRowData = dbData.get(i);
			if (tempRowData.get("type").length() == 1) {
				rowData = dbData.get(i);
				if (rowData.get("finishTime").equals("")) {
					rowData.put("exceedTime", getDayBetween(rowData.get("expectDate"), ""));
				} else {
					rowData.put("exceedTime", getDayBetween(rowData.get("expectDate"), rowData.get("finishTime")));
				}
				rowData.put("lastestExpectDate", "");
			} else {
				rowData.put("lastestExpectDate", tempRowData.get("expectDate"));
				dbData.remove(i--);
			}
		}
		
		if (dbData.size() == 0) {
			for(int i = 0; i < 9; i++) {
				rowData = new HashMap<String, String>();
				dbData.add(rowData);
				rowData.put("type", String.valueOf(i));
				rowData.put("title", titleList[i]);
				rowData.put("id", "");
				rowData.put("expectDate", "");
				rowData.put("exceedTime", "");
				rowData.put("lastestExpectDate", "");
				rowData.put("finishTime", "");				
			}
		} else {
		
			for (int i = 0; i < 9; i++) {
				int rowIndex;
				rowData = null;
				if (i < dbData.size()) {
					rowData = dbData.get(i);
					if (rowData.get("type") == null) {
						rowIndex = i;
					} else {
						rowIndex = Integer.parseInt(rowData.get("type"));
						rowData.put("title", titleList[rowIndex]);
					}
					
					if (rowIndex == i) {
						rowData.put("type", String.valueOf(i));
						rowData.put("title", titleList[i]);
					} else {
						for(int j = i; j < rowIndex; j++) {
							rowData = new HashMap<String, String>();
							rowData.put("type", String.valueOf(j));
							rowData.put("title", titleList[j]);
							rowData.put("id", "");
							rowData.put("expectDate", "");
							rowData.put("exceedTime", "");
							rowData.put("lastestExpectDate", "");
							rowData.put("finishTime", "");
							dbData.add(j, rowData);
							i++;
						}
					}				
				} else {
					rowData = new HashMap<String, String>();
					dbData.add(rowData);
					rowData.put("type", String.valueOf(i));
					rowData.put("title", titleList[i]);
					rowData.put("id", "");
					rowData.put("expectDate", "");
					rowData.put("exceedTime", "");
					rowData.put("lastestExpectDate", "");
					rowData.put("finishTime", "");
				}
	
			}
		}
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	
	public HashMap<String, Object> doGetProcessDetail(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();

		String key1 = "";
		String key2 = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key1 = getJsonData(data, "keyBackup");
		key2 = getJsonData(data, "type");	
		
		dataModel.setQueryFileName("/business/processcontrol/processcontrolquerydefine");
		dataModel.setQueryName("processcontrolquerydefine_searchbytype");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
		
		ArrayList<HashMap<String, String>> dbData = dataModel.getYsViewData();
		int rowCount = 0;
		for(HashMap<String, String>rowData:dbData) {
			if (rowData.get("finishTime").equals("")) {
				rowData.put("exceedTime", getDayBetween(rowData.get("baseExpectDate"), ""));
			} else {
				rowData.put("exceedTime", getDayBetween(rowData.get("baseExpectDate"), rowData.get("finishTime")));
			}
			rowCount++;
			if (rowCount == dbData.size()) {
				if (rowData.get("finishTime").equals("")) {
					rowData.put("lastOne", "1");
				} else {
					rowData.put("lastOne", "");
				}
			} else {
				rowData.put("lastOne", "");
			}
		}
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}	
	
	
	public ProcessControlModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) {
		ProcessControlModel model = new ProcessControlModel();
		String id = getJsonData(data, "keyBackup");
		
		try {
			B_ProcessControlDao dao = new B_ProcessControlDao();
			B_ProcessControlData dbData = new B_ProcessControlData();
			
			String projectId = getJsonData(data, "keyBackup");
			
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
			String info = bean.executeProcessControlUpdate(data, userInfo);

			model.setEndInfoMap(NORMAL, "", info);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", id);
		}
		
		return model;
	}
	
	public ProcessControlModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		ProcessControlModel model = new ProcessControlModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeProcessControlDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public static B_ProcessControlData updateModifyInfo(B_ProcessControlData data, UserInfo userInfo) {
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
	
	
	private boolean isDataExist(B_ProcessControlData dbData) {
		boolean rtnValue = false;
		
		try {
			B_ProcessControlDao dao = new B_ProcessControlDao();
			dao.FindByPrimaryKey(dbData);
			rtnValue = true;
		}
		catch(Exception e) {
			
		}
		return rtnValue;
		
	}
	
	public ProcessControlModel getProcessControlBaseInfo(HttpServletRequest request, String key) throws Exception {
		BaseModel dataModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		ArrayList<ArrayList<String>> finishTimeList = new ArrayList<ArrayList<String>>();
		String lastestFinishDate = "";
		
		ProcessControlModel model = new ProcessControlModel();
		B_ProcessControlDao dao = new B_ProcessControlDao();
		B_ProcessControlData dbData = new B_ProcessControlData();
		
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
			model.setExceedTime(getDayBetween(taskData.getEndtime(), ""));
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
				model.setExceedTime(getDayBetween(taskData.getEndtime(), ""));
			} else {
				model.setExceedTime(getDayBetween(taskData.getEndtime(), lastestFinishDate));
			}
		}
		
		model.setKeyBackup(key);
		
		model.setEndInfoMap("098", "0001", "");
		
		
		return model;
		
	}
	
	public ProcessControlModel doUpdateProcessControlDetailInit(HttpServletRequest request, String projectId, String id, String type) throws Exception {
		ProcessControlModel model = new ProcessControlModel();
		B_ProcessControlDao dao = new B_ProcessControlDao();
		B_ProcessControlData dbData = new B_ProcessControlData();
		String subType = "";
		BaseModel dataModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		ArrayList<ArrayList<String>> expectDateList = new ArrayList<ArrayList<String>>();
		
		if (id != null && !id.equals("")) {
			dbData.setId(id);
			dbData = (B_ProcessControlData)dao.FindByPrimaryKey(dbData);
			
			CalendarUtil calendarUtil;
			if (dbData.getCreatedate() != null) {
				calendarUtil = new CalendarUtil(dbData.getCreatedate());
				dbData.setCreatedate(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			}
			if (dbData.getExpectdate() != null) {
				calendarUtil = new CalendarUtil(dbData.getExpectdate());
				dbData.setExpectdate(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			}
			if (dbData.getFinishtime() != null) {
				calendarUtil = new CalendarUtil(dbData.getFinishtime());
				dbData.setFinishtime(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			}
		}
		
		if (type.length() == 1) {
			dataModel.setQueryFileName("/business/processcontrol/processcontrolquerydefine");
			dataModel.setQueryName("processcontrolquerydefine_searchbytype");
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("keyword1", projectId);
			userDefinedSearchCase.put("keyword2", type + "1");
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsQueryData(0, -1);
			
			ArrayList<HashMap<String, String>> expectDataList = dataModel.getYsViewData();
			int rowCount = 0;
			for(HashMap<String, String>rowData:expectDataList) {
				++rowCount;
				if (rowCount == expectDataList.size()) {
					model.setLastestExpectDate(rowData.get("expectDate"));
				}
			}
			if (id != null && !id.equals("")) {
				if (dbData.getFinishtime() == null || dbData.getFinishtime().equals("")) {
					model.setExceedTime(getDayBetween(dbData.getExpectdate(), ""));
				} else {
					model.setExceedTime(getDayBetween(dbData.getExpectdate(), dbData.getFinishtime()));
				}
			}
		}
		
		if (type.length() == 2) {
			subType = type.substring(1, 2);
			if (subType.equals("1")) {

				
				dataModel.setQueryFileName("/business/processcontrol/processcontrolquerydefine");
				dataModel.setQueryName("processcontrolquerydefine_searchbytype");
				BaseQuery baseQuery = new BaseQuery(request, dataModel);
				userDefinedSearchCase.put("keyword1", projectId);
				userDefinedSearchCase.put("keyword2", type.substring(0, 1));
				baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
				expectDateList = baseQuery.getFullData();
				String baseExpectDate = expectDateList.get(0).get(3);
				String finishTime = expectDateList.get(0).get(6);
				if (finishTime.equals("")) {
					model.setExceedTime(getDayBetween(baseExpectDate, ""));
				} else {
					model.setExceedTime(getDayBetween(baseExpectDate, finishTime));
				}
			}
		}
		
		model.setProcessControlData(dbData);
		
		model.setKeyBackup(id);
		model.setProjectId(projectId);
		model.setType(type);

		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	private String getDayBetween(String date1, String date2 ) throws Exception {
	
		CalendarUtil calendarUtil = null;
		Date compareDate1 = null;
		Date compareDate2 = null;
		
		if (date1 == null || date1.equals("")) {
			compareDate1 = CalendarUtil.getSystemDate();
		} else {
			calendarUtil = new CalendarUtil(date1);
			compareDate1 = calendarUtil.getDate();
		}
		
		if (date2 == null || date2.equals("")) {
			compareDate2 = CalendarUtil.getSystemDate();
		} else {
			calendarUtil = new CalendarUtil(date2);
			compareDate2 = calendarUtil.getDate();
		}
		
		
		int dayBetween = 0;

		dayBetween = calendarUtil.daysBetween(compareDate1, compareDate2);
		if (dayBetween < 0) {
			dayBetween = 0;
		}
		
		return String.valueOf(dayBetween);
	}
	
}
