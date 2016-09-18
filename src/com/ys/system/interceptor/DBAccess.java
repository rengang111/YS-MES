package com.ys.system.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.util.basequery.BaseQuery;

public class DBAccess {
	
	public static ArrayList<ArrayList<String>> getAllMenuPS(HttpServletRequest request, boolean isMenuManage) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		/*
		String sql = "select menuid, menuName, menuparentid, menuUrl, sortNo, menuDes, menuIcon1, menuIcon2 from s_Menu where ";
		if (!isMenuManage) {
			sql += "MenuNnableFlag = '" + Constants.ENABLEFLG_ENABLED + "' and ";
		}
		sql += "DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and MenuViewFlag = 'T' ";
		sql += "order by menuparentid, sortno, menuname";
		result = BaseDAO.execSQL(sql);
		*/
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		baseModel.setQueryFileName("/common/mainframequery");
		baseModel.setQueryName("mainframequery_getallmenups");
		
		BaseQuery baseQuery = new BaseQuery(request, baseModel);
		if (!isMenuManage) {
			userDefinedSearchCase.put("menunnableflag", BusinessConstants.ENABLEFLG_ENABLED);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		}
		result = baseQuery.getFullData();		
		
		return result;
	}
	
	public static ArrayList<ArrayList<String>> getRelationalMenuID(HttpServletRequest request, String menuId) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		/*
		String sql = "select RelationalMenuID from s_Menu where menuId = '" + menuId + "' and MenuNnableFlag = '" + Constants.ENABLEFLG_ENABLED + "' and DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and MenuViewFlag = 'T'";
		result = BaseDAO.execSQL(sql);
		*/
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		baseModel.setQueryFileName("/common/mainframequery");
		baseModel.setQueryName("mainframequery_getrelationalmenuid");
		
		BaseQuery baseQuery = new BaseQuery(request, baseModel);

		userDefinedSearchCase.put("menuid", menuId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		result = baseQuery.getFullData();		
		
		return result;
		
	}
	
	public static int getSysMenucount(HttpServletRequest request, String menuUrl) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		/*
		String sql = "select count(menuid) from s_Menu where MenuURL = '" + menuUrl + "' and menuType='1' and MenuNnableFlag = '" + Constants.ENABLEFLG_ENABLED + "' and DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and MenuViewFlag = 'T'";
		result = BaseDAO.execSQL(sql);
		*/
		menuUrl = menuUrl.replace(request.getContextPath(), "");
		
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		baseModel.setQueryFileName("/common/mainframequery");
		baseModel.setQueryName("mainframequery_getsysmenucount");
		
		BaseQuery baseQuery = new BaseQuery(request, baseModel);

		userDefinedSearchCase.put("menuurl", menuUrl);
		userDefinedSearchCase.put("menutype", BusinessConstants.MENUTYPE_ADMIN);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		result = baseQuery.getFullData();
		
		return Integer.parseInt(result.get(0).get(0));

	}
	
	public static int getRoleidCount(HttpServletRequest request, String userId, String menuId, String menuTypeLimit, boolean isFilter) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		/*
		StringBuffer sql = new StringBuffer("select count(a.roleid) ");
		sql.append("from s_rolemenu as a, s_Power as b, s_Menu as c ");
		sql.append(" where a.menuid = '" + menuId + "' and ");
		sql.append(" a.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and ");
		sql.append(" b.roleid = a.roleid and ");
		sql.append(" b.userId = '" + userId + "' and ");
		sql.append(" b.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and ");
		sql.append(" c.menuid = a.menuid and ");
		if (isFilter) {
			sql.append(" c.MenuType in (" + menuTypeLimit + ") and ");
		}
		sql.append(" c.MenuNnableFlag = '" + Constants.ENABLEFLG_ENABLED + "' and ");
		sql.append(" c.MenuViewFlag = 'T' and ");
		sql.append(" c.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "'");
		
		result = BaseDAO.execSQL(sql.toString());
		*/
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		baseModel.setQueryFileName("/common/mainframequery");
		baseModel.setQueryName("mainframequery_getsysmenucount");
		
		BaseQuery baseQuery = new BaseQuery(request, baseModel);
		userDefinedSearchCase.put("menuid", menuId);
		userDefinedSearchCase.put("userid", userId);
		if (isFilter) {
			userDefinedSearchCase.put("menutypelimit", menuTypeLimit);
		}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		result = baseQuery.getFullData();
		
		return Integer.parseInt(result.get(0).get(0));
	}
	
	public static int getRelationalRoleidCount(HttpServletRequest request, String userId, String menuId, String menuTypeLimit, boolean isFilter) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		/*
		StringBuffer sql = new StringBuffer("select count(a.roleid) ");
		sql.append(" from s_rolemenu as a, s_Power as b, s_menu as c ");
		sql.append(" where a.menuid in (" + menuId + ") and ");
		sql.append(" a.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and");
		sql.append(" b.roleid = a.roleid and");
		sql.append(" b.userId = '" + userId + "' and");
		sql.append(" b.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and ");
		sql.append(" c.menuid = a.menuid and ");
		if (isFilter) {
			sql.append(" c.MenuType in (" + menuTypeLimit + ") and ");
		}
		sql.append(" c.MenuNnableFlag = '" + Constants.ENABLEFLG_ENABLED + "' and ");
		sql.append(" c.MenuViewFlag = 'T' and ");
		sql.append(" c.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "'");
		
		result = BaseDAO.execSQL(sql.toString());
		*/
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		baseModel.setQueryFileName("/common/mainframequery");
		baseModel.setQueryName("mainframequery_getrelationalroleidcount");
		
		BaseQuery baseQuery = new BaseQuery(request, baseModel);
		userDefinedSearchCase.put("menuid", menuId);
		userDefinedSearchCase.put("userid", userId);
		if (isFilter) {
			userDefinedSearchCase.put("menutypelimit", menuTypeLimit);
		}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		result = baseQuery.getFullData();
		
		return Integer.parseInt(result.get(0).get(0));
	}
	
	public static ArrayList<ArrayList<String>> getUserMenu(HttpServletRequest request, String userId, String parentMenuId, String menuTypeLimit, boolean isFilter) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		/*
		StringBuffer sql = new StringBuffer("");
		
		sql.append("select distinct c.menuid, c.menuname, c.menuparentid, c.menuUrl, c.sortNo, c.menuDes, c.menuIcon1, c.menuIcon2 ");
		sql.append(" from s_power as a, s_rolemenu as b, s_menu as c ");
		sql.append(" where a.userid='" + userId + "' and ");
		sql.append(" a.deleteflag = '" + Constants.DELETEFLG_UNDELETE + "' and ");
		sql.append(" b.roleid = a.roleid and ");
 		sql.append(" b.deleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and");
		sql.append(" c.menuid = b.menuid and ");
		if (isFilter) {
			sql.append(" c.MenuType in (" + menuTypeLimit + ") and ");
		}
		sql.append(" c.menuparentid = '" + parentMenuId + "') and ");
		sql.append(" c.MenuNnableFlag = '" + Constants.ENABLEFLG_ENABLED + "' and ");
		sql.append(" c.MenuViewFlag = 'T' and ");
		sql.append(" c.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "'");
		sql.append(" order by c.menuid, c.menuparentid, c.sortNo");
   		
		result = BaseDAO.execSQL(sql.toString());
		*/
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		baseModel.setQueryFileName("/common/mainframequery");
		baseModel.setQueryName("mainframequery_getusermenu");
		baseModel.setUserDefinedWhere("c.menuparentid = '" + parentMenuId + "'");
		BaseQuery baseQuery = new BaseQuery(request, baseModel);
		
		userDefinedSearchCase.put("userid", userId);
		if (isFilter) {
			userDefinedSearchCase.put("menutypelimit", menuTypeLimit);
		}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		result = baseQuery.getFullData();
		
		return result;
		
	}

	public static ArrayList<ArrayList<String>> getUserMenuSA(HttpServletRequest request, String parentMenuId, String menuTypeLimit, boolean isFilter, boolean isMenuManage) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		/*
		StringBuffer sql = new StringBuffer("");
		
		sql.append("select distinct c.menuid, c.menuname, c.menuparentid, c.menuUrl, c.sortNo, c.menuDes, c.menuIcon1, c.menuIcon2 ");
		sql.append(" from s_menu as c ");
		sql.append(" where ");
		if (isFilter) {
			sql.append(" c.MenuType in (" + menuTypeLimit + ") and ");
		}
		if (!isMenuManage) {
			sql.append(" c.MenuNnableFlag = '" + Constants.ENABLEFLG_ENABLED + "' and ");
			sql.append(" c.MenuViewFlag = 'T' and ");
		}
		sql.append(" c.menuparentid = '" + parentMenuId + "' and ");
		sql.append(" c.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "'");
		sql.append(" order by c.menuid, c.menuparentid, c.sortNo");
   		
		result = BaseDAO.execSQL(sql.toString());
		*/
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		baseModel.setQueryFileName("/common/mainframequery");
		baseModel.setQueryName("mainframequery_getusermenusa");
		baseModel.setUserDefinedWhere("c.menuparentid = '" + parentMenuId + "'");
		BaseQuery baseQuery = new BaseQuery(request, baseModel);

		if (isFilter) {
			userDefinedSearchCase.put("menutypelimit", menuTypeLimit);
		}
		if (!isMenuManage) {
			userDefinedSearchCase.put("menunnableflag", BusinessConstants.ENABLEFLG_ENABLED);
			userDefinedSearchCase.put("menuviewflag", "T");
		}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		result = baseQuery.getFullData();
		
		return result;
		
	}
	
	public static ArrayList<ArrayList<String>> getLeafMenu(HttpServletRequest request, String menuId, String menuTypeLimit, boolean isFilter, boolean isMenuManage) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		/*
		StringBuffer sql = new StringBuffer("");
		
		sql.append("select distinct c.menuid, c.menuname, c.menuparentid, c.menuUrl, c.sortNo, c.menuDes, c.menuIcon1, c.menuIcon2 ");
		sql.append(" from s_menu as c ");
		sql.append(" where c.menuparentid = '" + menuId + "' and ");
		if (isFilter) {
			sql.append(" c.MenuType in (" + menuTypeLimit + ") and ");
		}
		if (!isMenuManage) {
			sql.append(" c.MenuNnableFlag = '" + Constants.ENABLEFLG_ENABLED + "' and ");
			sql.append(" c.MenuViewFlag = 'T' and ");
		}
		
		sql.append(" c.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "'");
		sql.append(" order by c.sortNo");
   		
		result = BaseDAO.execSQL(sql.toString());
		*/
		result = getUserMenuSA(request, menuId, menuTypeLimit, isFilter, isMenuManage);
		
		return result;
		
	}
	
	public static ArrayList<ArrayList<String>> getAllMenuInfo(HttpServletRequest request, String menuTypeLimit, UserInfo userInfo) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		/*
		String menuTypeList[] = menuType.split(",");
		
		StringBuffer sql = new StringBuffer("SELECT MenuId, MenuName, MenuIcon1, MenuParentId, SortNo ");
		sql.append("from s_menu where MenuViewFlag = 'T' and MenuNnableFlag = '" + Constants.ENABLEFLG_ENABLED + "' and DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and ");
		sql.append(" MenuType in (" + menuTypeLimit + ")");
		sql.append(" order by menuparentid, ifnull(sortno, 9999), menuid");

		result = BaseDAO.execSQL(sql.toString());
		*/
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		baseModel.setQueryFileName("/common/mainframequery");
		
		BaseQuery baseQuery = new BaseQuery(request, baseModel);
		if (userInfo.isSA()) {
			baseModel.setQueryName("mainframequery_getallmenuinfosa");
		} else {
			baseModel.setQueryName("mainframequery_getallmenuinfo");
			//userDefinedSearchCase.put("menutypelimit", menuTypeLimit);
			userDefinedSearchCase.put("userId", userInfo.getUserId());
			userDefinedSearchCase.put("userUnitId", userInfo.getUnitId());
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		}
		
		result = baseQuery.getFullData();
		
		return result;
	}
	
	public static ArrayList<ArrayList<String>> getAllDeptPS(HttpServletRequest request, String userId, String menuId, String unitId, String userType, String adminRole, boolean isFilter) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		BaseQuery baseQuery;
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		
		if (userType.equals(Constants.USER_BUSINESS)) {
			/*
			sql.append("select distinct c.UnitID, c.UnitName, c.UnitSimpleDes, c.jianpin, c.ParentID, c.DeptGuid, c.SortNo ");
			sql.append(" from s_Power as a, s_Role as b, s_dept as c ");
			if (!menuId.equals("")) {
				sql.append(", s_RoleMenu d ");
			}		
			sql.append(" where a.UserID = '" + userId + "' and ");
			sql.append(" a.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and " );
			sql.append(" b.roleid = a.roleid and ");
			sql.append(" b.deleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and ");
			if (!menuId.equals("")) {
				sql.append(" d.menuId = '" + menuId + "' and ");
				sql.append(" d.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and ");
				sql.append(" b.roleId = d.roleId and ");
				if (isFilter) {
					sql.append(" ( ");
					for(int i = 0; i < adminRoleList.length; i++) {
						if (i > 0) {
							sql.append(" or ");
						}
						sql.append(" b.roletype=" + adminRoleList[i].trim());
					}
					sql.append(") and ");
				}
			}
			sql.append(" c.unitid = a.unitid and ");
			sql.append(" c.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' ");
	
			sql.append(" order by c.unitid, c.parentid, c.sortno");
			*/

			baseModel.setQueryFileName("/common/mainframequery");
			baseModel.setQueryName("mainframequery_getalldeptps");
			baseQuery = new BaseQuery(request, baseModel);
			userDefinedSearchCase.put("userid", userId);
			userDefinedSearchCase.put("menuid", menuId);
			userDefinedSearchCase.put("parentid", "");
			if (isFilter) {
				userDefinedSearchCase.put("roletype", adminRole);
			}
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			
			
		} else {
			/*
			sql.append("SELECT distinct A.UnitID, A.UnitName, A.UnitSimpleDes, A.jianpin, A.ParentID, A.DeptGuid, A.SortNo ");
			sql.append("FROM s_dept as A ");
			sql.append("WHERE A.deleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' ");
			if (userType.equals(Constants.USER_ADMIN)) {
				sql.append(" AND A.UnitID LIKE '" + unitId + "%' ");
			}
			sql.append("ORDER BY A.unitid, A.parentid, A.sortno");
			*/
			baseModel.setQueryFileName("/common/mainframequery");
			baseModel.setQueryName("mainframequery_getalldeptps_admin");
			baseQuery = new BaseQuery(request, baseModel);
			if (userType.equals(Constants.USER_ADMIN)) {
				userDefinedSearchCase.put("unitId", unitId);
			}
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		}
		
		result = baseQuery.getFullData();
		
		return result;
	}
	
	public static ArrayList<ArrayList<String>> getLeafDept(HttpServletRequest request, String userId, String menuId, String unitId, String userType, String adminRole, boolean isFilter) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		BaseQuery baseQuery;
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		
		if (userType.equals(Constants.USER_BUSINESS)) {
			/*
			sql.append("select distinct c.UnitID, c.UnitName, c.UnitSimpleDes, c.jianpin, c.ParentID, c.DeptGuid, c.SortNo ");
			sql.append(" from s_Power as a, s_Role as b, s_dept as c ");
			if (!menuId.equals("")) {
				sql.append(", s_RoleMenu d ");
			}	
			sql.append(" where a.UserID = '" + userId + "' and ");
			sql.append(" a.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and " );
			sql.append(" b.roleid = a.roleid and ");
			sql.append(" b.deleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and ");
			if (!menuId.equals("")) {
				sql.append(" d.menuId = '" + menuId + "' and ");
				sql.append(" d.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' and ");		
				sql.append(" b.roleId = d.roleId and ");
				if (isFilter) {
					sql.append(" ( ");
					for(int i = 0; i < adminRoleList.length; i++) {
						if (i > 0) {
							sql.append(" or ");
						}
						sql.append(" b.roletype=" + adminRoleList[i].trim());
		
					}
					sql.append(") and ");
				}
			}		
			sql.append(" c.unitid = a.unitid and ");
			sql.append(" c.parentid = '" + unitId + "' and ");
			sql.append(" c.DeleteFlag = '" + Constants.DELETEFLG_UNDELETE + "'");
			sql.append(" order by c.sortno");
			*/
			baseModel.setQueryFileName("/common/mainframequery");
			baseModel.setQueryName("mainframequery_getalldeptps");
			baseQuery = new BaseQuery(request, baseModel);
			userDefinedSearchCase.put("userid", userId);
			userDefinedSearchCase.put("menuid", menuId);
			userDefinedSearchCase.put("parentid", unitId);
			if (isFilter) {
				userDefinedSearchCase.put("roletype", adminRole);
			}
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		} else {
			/*
			sql.append("SELECT distinct A.UnitID, A.UnitName, A.UnitSimpleDes, A.jianpin, A.ParentID, A.DeptGuid, A.SortNo ");
			sql.append("FROM s_dept as A ");
			sql.append("WHERE A.deleteFlag = '" + Constants.DELETEFLG_UNDELETE + "' ");
			sql.append("AND A.parentid = '" + unitId + "'");
			sql.append("ORDER BY A.unitid, A.parentid, A.sortno");			
			*/
			baseModel.setQueryFileName("/common/mainframequery");
			baseModel.setQueryName("mainframequery_getleafdept_admin");
			baseQuery = new BaseQuery(request, baseModel);
			userDefinedSearchCase.put("parentid", unitId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		}
   		
		result = baseQuery.getFullData();
		
		return result;
		
	}	
}
