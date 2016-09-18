package com.ys.system.service.common;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import com.ys.system.action.model.common.SelectRolePopupInfo;
import com.ys.util.basequery.BaseQuery;

@Service
public class SelectRolePopupService {
	
	public SelectRolePopupInfo doInit(SelectRolePopupInfo dataModel, HttpServletRequest request, String menuId, String userDeptId) throws Exception {
		dataModel.setQueryFileName("/common/selectrolequery");
		dataModel.setQueryName("selectrolequery_init");
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("userUnitId", userDeptId);
		dataModel.setMenuId(menuId);
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getQueryData();
		
		return dataModel;
	}
	
	public SelectRolePopupInfo doSearch(SelectRolePopupInfo dataModel, HttpServletRequest request, String menuId, String userDeptId) {
		dataModel.setQueryFileName("/common/selectrolequery");
		dataModel.setQueryName("selectrolequery_search");
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		try {
			userDefinedSearchCase.put("userUnitId", userDeptId);
			dataModel.setMenuId(menuId);
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getQueryData();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return dataModel;
	}

}
