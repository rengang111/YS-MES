package com.ys.system.service.common;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.ys.system.action.model.common.SelectUserPopupInfo;
import com.ys.util.CalendarUtil;
import com.ys.util.basequery.BaseQuery;

@Service
public class SelectUserPopupService {
	
	public SelectUserPopupInfo doInit(SelectUserPopupInfo dataModel, HttpServletRequest request, String menuId, String userDeptId) throws Exception {
		dataModel.setQueryFileName("/common/selectuserquery");
		dataModel.setQueryName("selectuserquery_init");
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		
		userDefinedSearchCase.put("startTime", CalendarUtil.fmtDate());
		userDefinedSearchCase.put("endTime", CalendarUtil.fmtDate());
		userDefinedSearchCase.put("endTime", CalendarUtil.fmtDate());
		userDefinedSearchCase.put("userUnitId", userDeptId);
		dataModel.setMenuId(menuId);
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getQueryData();
		
		return dataModel;
	}
	
	public SelectUserPopupInfo doSearch(SelectUserPopupInfo dataModel, HttpServletRequest request, String menuId, String userDeptId) throws Exception {
		dataModel.setQueryFileName("/common/selectuserquery");
		dataModel.setQueryName("selectuserquery_search");
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		
		userDefinedSearchCase.put("startTime", CalendarUtil.fmtDate());
		userDefinedSearchCase.put("endTime", CalendarUtil.fmtDate());
		userDefinedSearchCase.put("userUnitId", userDeptId);
		
		dataModel.setMenuId(menuId);
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		baseQuery.getQueryData();
		
		return dataModel;
	}
	
}
