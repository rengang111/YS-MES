package com.ys.system.service.operlog;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.operlog.OperLogModel;
import com.ys.system.action.model.unit.UnitModel;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;

@Service
public class OperLogService {
 
	public OperLogModel doSearch(HttpServletRequest request, OperLogModel dataModel, UserInfo userInfo) throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/operlog/operlogquerydefine");
		dataModel.setQueryName("operlogquerydefine_search");
		
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		//userDefinedSearchCase.put("startTime", dataModel.getStartTime());
		//userDefinedSearchCase.put("endTime", dataModel.getEndTime());
		//baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getQueryData();
		
		//dataModel.setUserUnitId(userInfo.getUnitId());
		
		return dataModel;
	}


}
