package com.ys.system.service.common;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.interceptor.DicInfo;
import com.ys.util.basequery.BaseQuery;

@Service
public class SelectDicTypePopupService {
	public String doSearch(HttpServletRequest request) throws Exception {
		final String TYPE_DICTYPE = "0";
		
		ArrayList<ArrayList<String>> dicTreeDataList;
		String jsonData = "";
		BaseModel dataModel = new BaseModel();
		
		String type = request.getParameter("type");
		
		if (type.equals(TYPE_DICTYPE)) {
			dataModel.setQueryFileName("/common/selectdictypequery");
			dataModel.setQueryName("selectdictypequery_search");
		} else {
			dataModel.setQueryFileName("/common/selectdicquery");
			dataModel.setQueryName("selectdicquery_alldatasearch");	
		}
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		
		dicTreeDataList = baseQuery.getFullData();
		
		ArrayList<DicInfo> deptChain = new ArrayList<DicInfo>();
		for(ArrayList<String> rowData:dicTreeDataList) {
			DicInfo deptInfo = new DicInfo();
	
			deptInfo.setId(rowData.get(0));
			deptInfo.setName(rowData.get(1));
			if (rowData.size() > 2) {
				deptInfo.setParentId(rowData.get(2));
				deptInfo.setSortNo(rowData.get(3));
			}
			deptChain.add(deptInfo);
		}
		jsonData = MakeTreeStyleData.convertDeptChainToJson(deptChain);
			
		return jsonData;
	}
	
}
