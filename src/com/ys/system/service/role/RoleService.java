package com.ys.system.service.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.role.RoleModel;
import com.ys.system.action.model.unit.UnitModel;
import com.ys.util.basequery.common.Constants;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DEPTDao;
import com.ys.system.db.dao.S_ROLEDao;
import com.ys.system.db.data.S_DEPTData;
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
public class RoleService extends BaseService {
 
	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		RoleModel dataModel = new RoleModel();
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/role/rolequerydefine");
		dataModel.setQueryName("rolequerydefine_search");
		
		String key1 = getJsonData(data, "roleIdName");
		String key2 = getJsonData(data, "menuIdName");
		String key3 = getJsonData(data, "userIdName");
		String key4 = getJsonData(data, "unitId");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("roleIdName", key1);
		userDefinedSearchCase.put("menuIdName", key2);
		userDefinedSearchCase.put("userIdName", key3);
		userDefinedSearchCase.put("unitId", key4);
		
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

	public RoleModel getDetail(HttpServletRequest request, UserInfo userInfo) throws Exception {
		String roleId = request.getParameter("roleId");
		RoleModel roleModel = new RoleModel();
		roleModel.setRoleTypeList(getRoleTypeList(userInfo));
		S_ROLEDao dao = new S_ROLEDao();
		S_ROLEData data = new S_ROLEData();
		data.setRoleid(roleId);
		data = (S_ROLEData)dao.FindByPrimaryKey(data);
		
		roleModel.setroleData(data);
		roleModel.setIsOnlyView("T");
		
		return roleModel;
	
	}

	public void doAdd(RoleModel roleModel, UserInfo userInfo) throws Exception {
		S_ROLEDao dao = new S_ROLEDao();
		
		S_ROLEData data = roleModel.getroleData();
		data = updateModifyInfo(roleModel.getroleData(), userInfo);
		data.setRoleid(BaseDAO.getGuId());
		data.setUnitid(userInfo.getUnitId());
		dao.Create(data);
	}	
	
	public void doUpdate(RoleModel roleModel, UserInfo userInfo) throws Exception {
		S_ROLEDao dao = new S_ROLEDao();
		S_ROLEData data = updateModifyInfo(roleModel.getroleData(), userInfo);
		
		//为确保角色权限不会被修改
		S_ROLEData dataSrc = new S_ROLEData();
		dataSrc.setRoleid(data.getRoleid());
		dataSrc = (S_ROLEData)dao.FindByPrimaryKey(dataSrc);
		data.setRoletype(dataSrc.getRoletype());
		
		dao.Store(data);
	}
	
	public RoleModel doUpdate(HttpServletRequest request, String formData, UserInfo userInfo, boolean isAdd) {
		S_ROLEDao dao = new S_ROLEDao();
		S_ROLEData data = new S_ROLEData();
		RoleModel model = new RoleModel();
		
		String roleid = getJsonData(formData, "roleid");
		String rolename = getJsonData(formData, "rolename");
		String roletype = getJsonData(formData, "roletype");
		
		try {

			if (isAdd) {
				roleid = BaseDAO.getGuId();
				data.setRoleid(roleid);
			} else {
				data.setRoleid(roleid);
				data = (S_ROLEData)dao.FindByPrimaryKey(data);
			}
			
			data.setRolename(rolename);
			data.setRoletype(roletype);
			data.setSortno(0);

			data = updateModifyInfo(data, userInfo);

			if (!isAdd) {
				dao.Store(data);
			} else {
				dao.Create(data);
			}
			
			model.setEndInfoMap(BaseService.NORMAL, "", roleid);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return model;
	}
	
	public RoleModel doDelete(HttpServletRequest request, String formData, UserInfo userInfo) {
		

		DbUpdateEjb bean = new DbUpdateEjb();
		RoleModel model = new RoleModel();
		
		try {
			bean.executeRoleDelete(formData, userInfo);
			model.setEndInfoMap(BaseService.NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return model;

	}
	
	public int checkRoleName(String roleId, String roleName) throws Exception {
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append(" ROLENAME = '" + roleName + "'");
		//是否是更新
		if (!roleId.equals("")) {
			sql.append(" AND ROLEID <> '" + roleId + "'");
		}
		
		S_ROLEDao dao = new S_ROLEDao();
		
		Vector<S_ROLEData> result = dao.Find(sql.toString());
        
        return result.size();
	}	
	
	public RoleModel getRoleRelationUser(HttpServletRequest request) throws Exception {

		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		RoleModel dataModel = new RoleModel();
		String roleId = request.getParameter("roleId");
		/*
		userDefinedSearchCase.put("roleId", roleId);
		
		
		dataModel.setQueryFileName("/role/rolequerydefine");
		dataModel.setQueryName("rolequerydefine_relationuser");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getQueryData();
		*/
		S_ROLEDao dao = new S_ROLEDao();
		S_ROLEData data = new S_ROLEData();
		data.setRoleid(roleId);
		data = (S_ROLEData)dao.FindByPrimaryKey(data);
		
		dataModel.setRoleIdName(data.getRolename());
		dataModel.setRoleId(roleId);
		return dataModel;
	}
	
	public HashMap<String, Object> getRoleRelationUserList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		RoleModel dataModel = new RoleModel();
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		String roleId = getJsonData(data, "roleId");
		
		userDefinedSearchCase.put("roleId", roleId);
		
		dataModel.setQueryFileName("/role/rolequerydefine");
		dataModel.setQueryName("rolequerydefine_relationuser");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
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
	
	public static S_ROLEData updateModifyInfo(S_ROLEData data, UserInfo userInfo) {
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
	
	public ArrayList<ArrayList<String>> getRoleTypeList(UserInfo userInfo) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		result = DicUtil.getGroupValue(DicUtil.ROLETYPE);
		
		if (!userInfo.isSA()) {
			for(ArrayList<String> rowData:result) {
				if (rowData.get(0).equals(BusinessConstants.MENUTYPE_ADMIN)) {
					result.remove(rowData);
					break;
				}
			}
		}
		
		return result;
		
		
	}
}
