package com.ys.system.service.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.role.RoleModel;
import com.ys.util.basequery.common.Constants;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_ROLEDao;
import com.ys.system.db.data.S_ROLEData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class RoleService {
 
	public RoleModel doSearch(HttpServletRequest request, RoleModel dataModel, UserInfo userInfo) throws Exception {

		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/role/rolequerydefine");
		dataModel.setQueryName("rolequerydefine_search");
		dataModel.setRoleTypeList(getRoleTypeList(userInfo));
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		
		if (!userInfo.getUserType().equals(Constants.USER_SA)) {
			userDefinedSearchCase.put("unitId", userInfo.getUnitId());
		} else {
			userDefinedSearchCase.put("unitId", "");
		}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getQueryData();
		
		baseQuery.getQueryData();
		
		return dataModel;
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
	
	public void doDelete(RoleModel roleModel, UserInfo userInfo) throws Exception {
		

		DbUpdateEjb bean = new DbUpdateEjb();
        
        bean.executeRoleDelete(roleModel, userInfo);

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
		String roleId = request.getParameter("roleId");
		
		userDefinedSearchCase.put("roleId", roleId);
		
		RoleModel dataModel = new RoleModel();
		dataModel.setQueryFileName("/role/rolequerydefine");
		dataModel.setQueryName("rolequerydefine_relationuser");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getQueryData();
		System.out.println(baseQuery.getSql());
		if (dataModel.getViewData().size() == 0) {
			dataModel.setMessage("无符合条件的数据");
		}
		S_ROLEDao dao = new S_ROLEDao();
		S_ROLEData data = new S_ROLEData();
		data.setRoleid(roleId);
		data = (S_ROLEData)dao.FindByPrimaryKey(data);
		
		dataModel.setRoleIdName(data.getRolename());
		
		return dataModel;
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
