package com.ys.system.interceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.util.basequery.BaseQuery;


public class CommonDept {
	

	private static boolean isFilter = false;
	
	public static ArrayList<DicInfo> getInitDept(HttpServletRequest request, String userId, String menuId, String unitId, String userType) throws Exception {
		
		ArrayList<ArrayList<DicInfo>> deptChain = doInit(request, userId, menuId, unitId, userType);
		
		return sortDept(deptChain);
	}	
	
	public static ArrayList<DicInfo> launchDept(HttpServletRequest request, String userId, String menuId, String unitId, String userType) throws Exception {
		
		ArrayList<DicInfo> leafDept = new ArrayList<DicInfo>();
		ArrayList<ArrayList<String>> dbResult = new ArrayList<ArrayList<String>>();
	    String adminRoleList = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERADMIN);
    	//是否需要过滤admin
	    isFilter = Boolean.parseBoolean(BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERDEFINE));
    	int adminRoleidCount = BaseQuery.getAdminRoleidCount(request, userId, adminRoleList);
    	if (adminRoleidCount == 0) {
    		//普通用户
    		adminRoleList = Constants.BUSINESSROLE;
    	}		
	    
    	if (userType.equals(Constants.USER_SA)) {
    		isFilter = false;
    	}
    	
    	dbResult = DBAccess.getLeafDept(request, userId, menuId, unitId, userType, adminRoleList, isFilter);
	    
    	for(ArrayList<String> rowData:dbResult) {
    		DicInfo deptInfo = new DicInfo();
    		deptInfo.setId(rowData.get(0));
    		deptInfo.setName(rowData.get(1));
    		deptInfo.setDes(rowData.get(2));
    		deptInfo.setJianpin(rowData.get(3));
    		deptInfo.setParentId(rowData.get(4));
    		deptInfo.setDeptGuid(rowData.get(5));

    		leafDept.add(deptInfo);
    	}
		
		
		return leafDept;
	}
	

	
	private static ArrayList<ArrayList<DicInfo>> doInit(HttpServletRequest request, String userId, String menuId, String unitId, String userType) throws Exception {
		ArrayList<ArrayList<DicInfo>> deptChain = new ArrayList<ArrayList<DicInfo>>();
	    ArrayList<ArrayList<String>> dbResult = new ArrayList<ArrayList<String>>();
	    HashMap<String, DicInfo> deptMap = new HashMap<String, DicInfo>();

	    isFilter = Boolean.parseBoolean(BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERDEFINE));
	    String adminRoleList = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERADMIN);
	    
    	int adminRoleidCount = BaseQuery.getAdminRoleidCount(request, userId, adminRoleList);
    	if (adminRoleidCount == 0) {
    		adminRoleList = Constants.BUSINESSROLE;
    	}
	    
    	if (userType.equals(Constants.USER_SA)) {
    		isFilter = false;
    	}

    	dbResult = DBAccess.getAllDeptPS(request, userId, menuId, unitId, userType, adminRoleList, isFilter);
    	for (ArrayList<String> rowData:dbResult) {
    		DicInfo deptInfo = new DicInfo();
    		deptInfo.setId(rowData.get(0));
    		deptInfo.setName(rowData.get(1));
    		deptInfo.setDes(rowData.get(2));
    		deptInfo.setJianpin(rowData.get(3));
    		deptInfo.setParentId(rowData.get(4));
    		deptInfo.setDeptGuid(rowData.get(5));
    		deptInfo.setSortNo(rowData.get(6));
    		deptMap.put(deptInfo.getId(), deptInfo);
    	}
	    
    	for (ArrayList<String> rowData:dbResult) {
    		ArrayList<DicInfo> deptSubChain = new ArrayList<DicInfo>();
    		
    		DicInfo deptInfo = new DicInfo();
    		deptInfo.setId(rowData.get(0));
    		deptInfo.setName(rowData.get(1));
    		deptInfo.setDes(rowData.get(2));
    		deptInfo.setJianpin(rowData.get(3));
    		deptInfo.setParentId(rowData.get(4));
    		deptInfo.setDeptGuid(rowData.get(5));
    		deptInfo.setSortNo(rowData.get(6));
    		
    		//getDeptChain(deptInfo, deptSubChain, dbResult, deptMap);
    		deptSubChain.add(deptInfo);
    		deptChain.add(deptSubChain);
    	}
    	
    	return deptChain;
	}
	/*
	private static void getDeptChain(DicInfo deptInfo, ArrayList<DicInfo> deptChain, ArrayList<ArrayList<String>> deptList, HashMap<String, DicInfo> deptMap) {
		DicInfo subDicInfo = deptMap.get(deptInfo.getId());
		String parentUnitId = "";
		
		if (subDicInfo == null) {
			deptChain.add(0, subDicInfo);
		} else {
			parentUnitId = subDicInfo.getParentId();
			if (parentUnitId == null || parentUnitId.equals("")) {
				deptChain.add(0, subDicInfo);
			} else {
				getDeptChain(deptMap.get(parentUnitId), deptChain, deptList, deptMap);
			}
		}

	}
	*/
	
	private static ArrayList<DicInfo> sortDept(ArrayList<ArrayList<DicInfo>> deptChain) {
		
		HashMap<String, String> initDeptMap = new HashMap<String, String>();
		ArrayList<DicInfo> deptList = new ArrayList<DicInfo>();
		ArrayList<Integer> sortNoList = new ArrayList<Integer>();
		ArrayList<DicInfo> sortedDeptList = new ArrayList<DicInfo>();
		
		for(ArrayList<DicInfo> deptInfoList:deptChain) {
			if (deptInfoList.size() > 0) {
				DicInfo deptInfo = deptInfoList.get(0);
				if (!initDeptMap.containsKey(deptInfo.getId())) {
					initDeptMap.put(deptInfo.getId(), "");
					deptList.add(deptInfo);
					if (deptInfo.getSortNo() == null || deptInfo.getSortNo().equals("")) {
						sortNoList.add(new Integer("0"));
					} else {
						sortNoList.add(Integer.parseInt(deptInfo.getSortNo()));
					}
				}
			}
		}

		Collections.sort(sortNoList);
		
		
		for(Integer sortNo:sortNoList) {
			for(DicInfo deptInfo:deptList) {
				int subSortNo = 0;
				if (deptInfo.getSortNo() != null && !deptInfo.getSortNo().equals("")) {
					subSortNo = Integer.parseInt(deptInfo.getSortNo());
				}
				if (sortNo.intValue() == subSortNo) {
					sortedDeptList.add(deptInfo);
					deptList.remove(deptInfo);
					break;
				}
			}
		}
		
		return sortedDeptList;
		
	}
	
}
