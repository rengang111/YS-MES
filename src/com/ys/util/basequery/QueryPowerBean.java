package com.ys.util.basequery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;

public class QueryPowerBean {
	private String menuId = "";
	private String deptField = "";

	public String getMenuId() {
		return this.menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public String getDeptField() {
		return deptField;
	}
	public void setDeptField(String deptField) {
		this.deptField = deptField;
	}
	
	public String getSql(HttpServletRequest request, String menuId)  throws Exception {
		HttpSession session = request.getSession();
		
		//String userId = (String)session.getAttribute(BaseQuery.USERID);
		//String userType = (String)session.getAttribute(BaseQuery.USERTYPE);
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		String userId = userInfo.getUserId();
		String userType = userInfo.getUserType();
		
		userId = userId != null ? userId:"";
		userType = userType != null ? userType:"";
		
		StringBuffer sql = new StringBuffer("");
		if (this.deptField != null && !this.deptField.trim().equals("")) {
			sql.append(" AND ");
			sql.append(" exists (");
			if (menuId.equals("")) {
				menuId = this.menuId;
			}
			sql.append(getDeptFilterSubSql(request, userId, menuId, this.deptField, userType));
			sql.append(") ");
		}
		return sql.toString();
	}
	
	private String getDeptFilterSubSql(HttpServletRequest request, String userId, String menuId, String filterField, String userType) throws Exception {
		
	    String adminRoleList = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERADMIN);
	    boolean isFilter = Boolean.parseBoolean(BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERDEFINE));
    	int adminRoleidCount = BaseQuery.getAdminRoleidCount(request, userId, adminRoleList);
    	if (adminRoleidCount == 0) {
    		adminRoleList = Constants.BUSINESSROLE;
    	} 
	    
    	if(userType.equals(Constants.USER_SA)) {
    		isFilter = false;
    	}
    	
    	String adminRoleListDef[] = adminRoleList.split(",");
    	
		StringBuffer sql = new StringBuffer();
		sql.append("select y.UnitID ");
		sql.append("from s_RoleMenu as x, s_Power as y, s_Role as z ");
		sql.append("where ");
		if (menuId != null && !menuId.equals("")) {
			sql.append("x.MenuID = '" + menuId + "' and ");
		}
		sql.append("x.DeleteFlag = '0' and " );
		sql.append("y.UserID = '" + userId + "' and ");
		sql.append("y.roleid = x.roleid and ");
		sql.append("y.deleteFlag = '0' and ");
		sql.append("z.roleid = x.roleid and ");
		sql.append("y.UnitID = SUBSTR(" + filterField + ", 1, LENGTH(y.UnitID))");
    	if (isFilter) {
			sql.append(" and (");
			for(int i = 0; i < adminRoleListDef.length; i++) {
				if (i > 0) {
					sql.append(" or ");
				}
				sql.append(" z.roletype=" + adminRoleListDef[i].trim());
			}
			sql.append(") ");
    	}

		return sql.toString();
	}
}
