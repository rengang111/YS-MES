package com.ys.business.service.projecttask;

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
import com.ys.business.action.model.projecttask.ProjectTaskModel;
import com.ys.business.db.dao.B_ProjectTaskDao;
import com.ys.business.db.data.B_ProjectTaskCostData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.role.RoleModel;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

import net.sf.json.JSONArray;

import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_ROLEDao;
import com.ys.system.db.data.S_ROLEData;
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
public class ProjectTaskService extends BaseService implements I_BaseService {
 
	public static String staticColName[][] = {
			{"机身模具", "齿轮箱模具", "电池包模具", "充电器模具", "压铸件模具", "五金件模具"},
			{"吹塑包装模具", "注塑包装模具",	"泡壳模具"},
			{"手模", "工作样机"},
			{"认证项目1", "认证项目2", "认证项目3"},
			{"外观", "实用性", "发明"},
			{"查询项目1", "查询项目2", "查询项目3"},
			{"设计项目1", "设计项目2"},
			{"试产数量", "试产费用"}
	};	
	
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
		
		dataModel.setQueryFileName("/business/projecttask/projecttaskquerydefine");
		dataModel.setQueryName("projecttaskquerydefine_search");
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

	public ProjectTaskModel doAddInit(HttpServletRequest request) {

		ProjectTaskModel model = new ProjectTaskModel();

		try {
			//TODO
			model.setManagerList(doOptionChange(request).getManagerList());
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
	}

	public ProjectTaskModel doAdd(HttpServletRequest request, String data, UserInfo userInfo) {

		ProjectTaskModel model = new ProjectTaskModel();
		try {

			String projecttaskid = getJsonData(data, "projectId");
		
			ArrayList<ArrayList<String>> preCheckResult = preCheckProjectTaskId(request, projecttaskid);
			
			if (preCheckResult.size() > 0) {
				//已存在
				model.setEndInfoMap("001", "err005", "");
			} else {
				BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
				String guid = bean.executeProjectTaskAdd(data, userInfo);
				model.setEndInfoMap(NORMAL, "", guid);
			}
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}	

	
	public ProjectTaskModel doOptionChange(HttpServletRequest request) {
		DicUtil util = new DicUtil();
		ProjectTaskModel model = new ProjectTaskModel();
		UserService userService = new UserService();
		try {
			//TODO
			ArrayList<ListOption> optionList = userService.getUserListByDuty(request, BusinessConstants.DUTY_PJMANAGER);
			model.setManagerList(optionList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
			model.setManagerList(null);
		}
		
		return model;
	}
	
	public ProjectTaskModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) {
		ProjectTaskModel model = new ProjectTaskModel();
		String id = getJsonData(data, "keyBackup");
		
		try {
			B_ProjectTaskDao dao = new B_ProjectTaskDao();
			B_ProjectTaskData dbData = new B_ProjectTaskData();
			
			String projecttaskid = getJsonData(data, "projectId");
			boolean isKeyExist = false;
			
			//要更新的记录是否存在
			isKeyExist = preCheckId(id);
			if (isKeyExist) {
				ArrayList<ArrayList<String>> preCheckResult = preCheckProjectTaskId(request, projecttaskid);
				
				//要更新的供应商id是否存在
				if (preCheckResult.size() != 0 && !preCheckResult.get(0).get(1).equals(id)) {					
					//已存在
					model.setEndInfoMap("001", "err007", "");
				} else {
					BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
			        
			        bean.executeProjectTaskUpdate(data, userInfo);
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
	
	public ProjectTaskModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		ProjectTaskModel model = new ProjectTaskModel();
		boolean isDBOperationSuccessed = false;
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeProjectTaskDelete(data, userInfo);
	        
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
		}
		
		return model;
	}
	
	public static B_ProjectTaskData updateModifyInfo(B_ProjectTaskData data, UserInfo userInfo) {
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
	
	public static B_ProjectTaskCostData updateCostDataModifyInfo(B_ProjectTaskCostData data, UserInfo userInfo) {
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
	
	private boolean isDataExist(B_ProjectTaskData dbData) {
		boolean rtnValue = false;
		
		try {
			B_ProjectTaskDao dao = new B_ProjectTaskDao();
			dao.FindByPrimaryKey(dbData);
			rtnValue = true;
		}
		catch(Exception e) {
			
		}
		return rtnValue;
		
	}
	
	public ProjectTaskModel getProjectTaskBaseInfo(HttpServletRequest request, String key) throws Exception {
		ProjectTaskModel model = new ProjectTaskModel();
		B_ProjectTaskDao dao = new B_ProjectTaskDao();
		B_ProjectTaskData dbData = new B_ProjectTaskData();
		dbData.setId(key);
		dbData = (B_ProjectTaskData)dao.FindByPrimaryKey(dbData);
		
		CalendarUtil calendarUtil = new CalendarUtil(dbData.getBegintime());
		dbData.setBegintime(calendarUtil.fmtDate(calendarUtil.getDate(), "yyyy-MM-dd"));
		calendarUtil = new CalendarUtil(dbData.getEndtime());
		dbData.setEndtime(calendarUtil.fmtDate(calendarUtil.getDate(), "yyyy-MM-dd"));
		
		model.setProjectTaskData(dbData);

		model.setManagerList(doOptionChange(request).getManagerList());

		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		model.setQueryFileName("/business/projecttask/projecttaskquerydefine");
		model.setQueryName("projecttaskquerydefine_searchcost");
		BaseQuery baseQuery = new BaseQuery(request, model);
		userDefinedSearchCase.put("keyword", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<ArrayList<String>> costDataList = baseQuery.getFullData();
		JSONArray jsonObject = JSONArray.fromObject(costDataList);
		model.setCostDataList(jsonObject);
		ArrayList<ArrayList<String>> costDataTypeCount = BaseDAO.execSQL("select count(*) from b_projecttaskcost group by type");
		ArrayList<String> costDataTypeCountList = new ArrayList<String>();
		for(ArrayList<String> data:costDataTypeCount) {
			costDataTypeCountList.add(data.get(0));
		}
		
		jsonObject = JSONArray.fromObject(costDataTypeCountList);
		model.setCostDataTypeCount(jsonObject);
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
		
	}
	
	public ProjectTaskModel getFileList(HttpServletRequest request, ProjectTaskModel model) {
		UploadReceiver uploadReceiver = new UploadReceiver();
		
		String dir = request.getSession().getServletContext().getRealPath("/")
				+ BusinessConstants.BUSINESSPHOTOPATH + model.getProjectTaskData().getId() + BusinessConstants.BUSINESSSMALLPHOTOPATH; 
		
		String [] filenames = uploadReceiver.getFileNameList(dir);
		
		String nowUseImage = model.getProjectTaskData().getImage_filename();
		
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
		model.setImageKey(model.getProjectTaskData().getId());
		model.setPath(BusinessConstants.BUSINESSPHOTOPATH);
		model.setNowUseImage(nowUseImage);
		
		return model;
	}
	
	public void setNowUseImage(String key, String src) throws Exception {
		B_ProjectTaskDao dao = new B_ProjectTaskDao();
		B_ProjectTaskData dbData = new B_ProjectTaskData();
		
		dbData.setId(key);
		dbData = (B_ProjectTaskData)dao.FindByPrimaryKey(dbData);
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute(BusinessConstants.SESSION_USERINFO);
		
		dbData = updateModifyInfo(dbData, userInfo);
		dbData.setImage_filename(src);
		dao.Store(dbData);
		
	}
	
	public String getNowUseImage(String key) {
		B_ProjectTaskDao dao = new B_ProjectTaskDao();
		B_ProjectTaskData dbData = new B_ProjectTaskData();
		
		String nowUseImage = "";
		
		try {
			dbData.setId(key);
			dbData = (B_ProjectTaskData)dao.FindByPrimaryKey(dbData);
			nowUseImage = dbData.getImage_filename();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return nowUseImage;
		
	}	
	
	
	private ArrayList<ArrayList<String>> preCheckProjectTaskId(HttpServletRequest request, String key) throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/projecttask/projecttaskquerydefine");
		dataModel.setQueryName("projecttaskquerydefine_preCheck");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		return baseQuery.getQueryData();
					
	}
	
	private boolean preCheckId(String key) throws Exception {
		B_ProjectTaskDao dao = new B_ProjectTaskDao();
		B_ProjectTaskData dbData = new B_ProjectTaskData();
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
