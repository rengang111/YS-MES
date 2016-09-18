package com.ys.system.interceptor;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.Constants;

public class CommonMenu {
	
	private static boolean isFilter = false;
	
	public static ArrayList<MenuInfo> getInitMenu(HttpServletRequest request, String userId, String userType, boolean isMenuManage) throws Exception {
			
		ArrayList<MenuInfo> menuChain = doInit(request, userId, userType, isMenuManage);
		
		return menuChain;
	}
	
	private static ArrayList<MenuInfo> doInit(HttpServletRequest request, String userId, String userType, boolean isMenuManage) throws Exception {

		ArrayList<MenuInfo> menuChain = new ArrayList<MenuInfo>();
	    ArrayList<ArrayList<String>> dbResult = new ArrayList<ArrayList<String>>();
	    
	    //Admin权限
	    //String adminRoleList = PropertyUtil.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERADMIN);
	    isFilter = Boolean.parseBoolean(BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERDEFINE));
	    String menuTypeLimit = "";

    	//判断该用户是否有admin权限
    	menuTypeLimit = getUserRoleType(request, userId, userType);

    	//获得用户拥有权限的menu
    	if (userType.equals(Constants.USER_SA)) {
    		//如果要放开SA权限则应将isFilter置为false
    		isFilter = false;
    		dbResult = DBAccess.getUserMenuSA(request, "", menuTypeLimit, isFilter, isMenuManage);
    	} else {
    		//如果要放开Admin权限则应在此处判断是否为admin，如是则将isFilter置为false
    		dbResult = DBAccess.getUserMenu(request, userId, "", menuTypeLimit, isFilter);
    	}
    	
    	for(ArrayList<String> rowData:dbResult) {

    		MenuInfo menuInfo = new MenuInfo();
    		menuInfo.setMenuId(rowData.get(0));
    		menuInfo.setMenuName(rowData.get(1));
    		menuInfo.setMenuParentId(rowData.get(2));
    		menuInfo.setMenuURL(rowData.get(3));
    		menuInfo.setSortNo(rowData.get(4));
    		menuInfo.setMenuDes(rowData.get(5));
    		menuInfo.setMenuIcon1(rowData.get(6));
    		menuInfo.setMenuIcon2(rowData.get(7));
    		//getMenuChain(menuInfo, menuSubChain, dbResult, menuMap);

    		menuChain.add(menuInfo);
    	}
    	
    	return menuChain;
	}
	
	public static String getUserRoleType(HttpServletRequest request, String userId, String userType) throws Exception {
		String roleType = "";
		String adminRoleList = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERADMIN);
    	if (userType.equals(Constants.USER_SA)) {
    		roleType = BusinessConstants.MENUTYPE_ADMIN;
    	} else {
	    	int adminRoleidCount = BaseQuery.getAdminRoleidCount(request, userId, adminRoleList);
	    	if (adminRoleidCount > 0) {
	    		roleType = BusinessConstants.MENUTYPE_ADMIN;
	    	} else {
	    		roleType = BusinessConstants.MENUTYPE_BUSINESS;
	    	}
    	}
    	
    	return roleType;
	}
	
	public static ArrayList<MenuInfo> launchMenu(HttpServletRequest request, String userId, String menuId, String userType, boolean isMenuManage) throws Exception {
		ArrayList<MenuInfo> menuChain = new ArrayList<MenuInfo>();
	    ArrayList<ArrayList<String>> dbResult = new ArrayList<ArrayList<String>>();
	    
	    //Admin权限
	    //String adminRoleList = PropertyUtil.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERADMIN);
	    isFilter = Boolean.parseBoolean(BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERDEFINE));
	    String menuTypeLimit = "";
    	//获得用户拥有权限的menu
    	if (userType.equals(Constants.USER_SA)) {
    		//如果要放开SA权限则应将isFilter置为false
    		isFilter = false;
    		dbResult = DBAccess.getUserMenuSA(request, menuId, menuTypeLimit, isFilter, isMenuManage);
    	} else {
    		//如果要放开Admin权限则应在此处判断是否为admin，如是则将isFilter置为false
    		dbResult = DBAccess.getUserMenu(request, userId, menuId, menuTypeLimit, isFilter);
    	}
    	
    	for(ArrayList<String> rowData:dbResult) {

    		MenuInfo menuInfo = new MenuInfo();
    		menuInfo.setMenuId(rowData.get(0));
    		menuInfo.setMenuName(rowData.get(1));
    		menuInfo.setMenuParentId(rowData.get(2));
    		menuInfo.setMenuURL(rowData.get(3));
    		menuInfo.setSortNo(rowData.get(4));
    		menuInfo.setMenuDes(rowData.get(5));
    		menuInfo.setMenuIcon1(rowData.get(6));
    		menuInfo.setMenuIcon2(rowData.get(7));
    		//getMenuChain(menuInfo, menuSubChain, dbResult, menuMap);

    		menuChain.add(menuInfo);
    	}
		
		return menuChain;
	}
	
	public static ArrayList<MenuInfo> getAllMenuInfo(HttpServletRequest request, String menuType, UserInfo userInfo) throws Exception {
		ArrayList<MenuInfo> allMenuInfoList = new ArrayList<MenuInfo>();
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		result = DBAccess.getAllMenuInfo(request, menuType, userInfo);
		for(ArrayList<String> rowData:result) {
			MenuInfo menuData = new MenuInfo();
			menuData.setMenuId(rowData.get(0));
			menuData.setMenuName(rowData.get(1));
			menuData.setMenuIcon1(rowData.get(2));
			menuData.setMenuParentId(rowData.get(3));
			menuData.setSortNo(rowData.get(4));
			allMenuInfoList.add(menuData);
		}
		
		return allMenuInfoList;
	}	
	

}
