package com.ys.system.service.mainframe;


import java.util.ArrayList;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import com.ys.business.service.order.MatCategoryService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.util.basequery.common.Constants;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_ROLEDao;
import com.ys.system.db.data.S_ROLEData;
import com.ys.system.interceptor.CommonDept;
import com.ys.system.interceptor.CommonMenu;
import com.ys.system.interceptor.DicInfo;
import com.ys.system.interceptor.MenuInfo;
import com.ys.system.service.common.MakeTreeStyleData;
import com.ys.system.service.role.RoleMenuService;

@Service
public class MainFrameService {
 
	public String doInitMenu(HttpServletRequest request, String userId, String userType, String menuId, boolean isMenuManage) {

		String json = "";

		try {
			ArrayList<MenuInfo> menuChain = CommonMenu.getInitMenu(request, userId, userType, isMenuManage);
			json = MakeTreeStyleData.convertMenuChainToJson(menuChain);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

		return json;
	}
	
    public String doLaunchMenu(HttpServletRequest request, String userId, String userType, String idJson, boolean isMenuManage) {
		String json = "";
		
		try {
			ArrayList<String> ownerMenuId = MakeTreeStyleData.getId(idJson);
			ArrayList<MenuInfo> menuChain = CommonMenu.launchMenu(request, userId, ownerMenuId.get(0), userType, isMenuManage);
			json = MakeTreeStyleData.convertMenuChainToJson(menuChain);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return json;
	}

	public String doGetAllMenu(HttpServletRequest request, UserInfo userInfo) {
		String json = "";
		
		ArrayList<MenuInfo> menuChain = null;
		
		String roleId = request.getParameter("roleId");
		
		String minusflg = request.getParameter("minusflg");
		
		String roleType = "";
		
		try {
			if (roleId != null && !roleId.equals("")) {
				S_ROLEDao sRoleDao = new S_ROLEDao();
				Vector<S_ROLEData> sRoleDataList = sRoleDao.Find(" ROLEID = '" + roleId + "' OR ROLENAME LIKE '%" + roleId + "%'");
				
				for(S_ROLEData sRoleData:sRoleDataList) {
					if (sRoleData.getRoletype().equals(Constants.BUSINESSROLE)) {
						roleType = BusinessConstants.MENUTYPE_BUSINESS;
					} else {
						roleType = BusinessConstants.MENUTYPE_ADMIN;
					}
					break;
				}
			} else {
				roleType = CommonMenu.getUserRoleType(request, userInfo.getUserId(), userInfo.getUserType());
			}
			
			menuChain = CommonMenu.getAllMenuInfo(request, roleType, userInfo);
			
			//去除已授权的菜单项
			if (minusflg != null && !minusflg.equals("")) {
				RoleMenuService roleMenuService = new RoleMenuService();
				ArrayList<MenuInfo> authoredRoleMenuList = roleMenuService.getRoleIdMenu(request, userInfo);
				ArrayList<MenuInfo> newMenuChain = new ArrayList<MenuInfo>();
				for(MenuInfo menuInfo:menuChain) {
					boolean isMinus = false;
					for (MenuInfo authoredMenuInfo:authoredRoleMenuList) {
						if(authoredMenuInfo.getMenuId().equals(menuInfo.getMenuId())) {
							isMinus = true;
							break;
						}
					}
					if (isMinus == false) {
						newMenuChain.add(menuInfo);
					}
				}
				/*
				ArrayList<MenuInfo> finalMenuChain = new ArrayList<MenuInfo>();
				HashMap<String, String> menuIdMap = new HashMap<String, String>();
				for(MenuInfo menuInfo:newMenuChain) {
					if (!menuIdMap.containsKey(menuInfo.getMenuId())) {
						menuIdMap.put(menuInfo.getMenuId(), "");
						finalMenuChain.add(menuInfo);
						//orderMenuChain(menuInfo, menuChain, finalMenuChain, menuIdMap);
					}
				}
				*/
				json = MakeTreeStyleData.convertMenuChainToJson(newMenuChain);
			} else {
				json = MakeTreeStyleData.convertMenuChainToJson(menuChain);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return json;
	}
	/*
	private void orderMenuChain(MenuInfo menuInfo, ArrayList<MenuInfo> menuChain, ArrayList<MenuInfo> finalMenuChain, HashMap<String, String> menuIdMap) {
		String menuParentId = menuInfo.getMenuParentId();
		
		if (menuParentId == null || menuParentId.equals("")) {
			//finalMenuChain.add(menuInfo);
		} else {
			for (MenuInfo parentMenuInfo:menuChain) {
				if (parentMenuInfo.getMenuId().equals(menuParentId)) {
					if (!menuIdMap.containsKey(parentMenuInfo.getMenuId())) {
						finalMenuChain.add(parentMenuInfo);
						menuIdMap.put(parentMenuInfo.getMenuId(), "");
						orderMenuChain(parentMenuInfo, menuChain, finalMenuChain, menuIdMap);
					}
					break;
				}
			}
		}
	}
	*/
	public String doInitDept(HttpServletRequest request, String userId, String menuId, String unitId, String userType) {
		String json = "";

		try {

			ArrayList<DicInfo> deptChain = CommonDept.getInitDept(request, userId, menuId, unitId, userType);
			json = MakeTreeStyleData.convertDeptChainToJson(deptChain);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return json;
	}		
	
    public String doLaunchDept(HttpServletRequest request, String userId, String userType, String idJson) {
		String json = "";

		try {
			ArrayList<String> ownerId = MakeTreeStyleData.getId(idJson);
			ArrayList<DicInfo> DeptChain = CommonDept.launchDept(request, userId, ownerId.get(1), ownerId.get(0), userType);
			json = MakeTreeStyleData.convertDeptChainToJson(DeptChain);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return json;
	}	
    
    public String doInitMaterial(HttpServletRequest request, String userId, String menuId, String unitId, String userType) {
		String json = "";
		MatCategoryService matClassServer = new MatCategoryService();

		try {

			ArrayList<DicInfo> deptChain = matClassServer.getInitMaterial(request, userId, menuId, unitId, userType);
			json = MakeTreeStyleData.convertDeptChainToJson(deptChain);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return json;
	}		
	
    public String doLaunchMaterial(HttpServletRequest request, String userId, String userType, String idJson) {
		String json = "";

		MatCategoryService matClassServer = new MatCategoryService();
		try {
			ArrayList<String> ownerId = MakeTreeStyleData.getId(idJson);
			ArrayList<DicInfo> DeptChain = matClassServer.launchMaterial(request, userId, ownerId.get(1), ownerId.get(0), userType);
			json = MakeTreeStyleData.convertDeptChainToJson(DeptChain);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return json;
	}	
	
}
