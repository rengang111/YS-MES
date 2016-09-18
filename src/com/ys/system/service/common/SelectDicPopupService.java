package com.ys.system.service.common;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import com.ys.system.action.model.common.SelectDicPopupInfo;
import com.ys.system.interceptor.DicInfo;
import com.ys.util.basequery.BaseQuery;

@Service
public class SelectDicPopupService {
	public String doSearch(HttpServletRequest request, SelectDicPopupInfo dataModel) throws Exception {

		ArrayList<ArrayList<String>> dicTreeDataList;
		String jsonData = "";
		/*
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT DicID, DicName, DicPrarentID, DicTypeID, SortNo FROM s_DIC WHERE ");


		if (!dataModel.getDicParentCodeName().equals("")) {
			sql.append("(DicPrarentID IN (");
			sql.append("SELECT DicID FROM s_dic where (DicID IN (SELECT DicPrarentID FROM s_Dic WHERE DicPrarentID <> '' AND DicPrarentID IS NOT NULL) AND DicName LIKE '%" + dataModel.getDicParentCodeName() + "%'))");
			sql.append(" OR DicPrarentID = '" + dataModel.getDicParentCodeName() + "')");
			isUnion = true;
		}
		if (!dataModel.getDicCodeName().equals("")) {
			if (isUnion) {
				sql.append(" AND ");
			} else {
				isUnion = true;
			}
			sql.append("(DicName LIKE '%" + dataModel.getDicCodeName() + "%' OR DicID = '" + dataModel.getDicCodeName() + "')");
		}
		if (isUnion) {
			sql.append(" AND ");
		}
		sql.append("DeleteFlag = '0' AND EnableFlag = '0' ");
		sql.append("ORDER BY DicPrarentID, SortNo, DicName");
		*/
		dataModel.setQueryFileName("/common/selectdicquery");
		dataModel.setQueryName("selectdicquery_search");
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		

		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		dicTreeDataList = baseQuery.getFullData();	
		
		ArrayList<DicInfo> deptChain = new ArrayList<DicInfo>();
		for(ArrayList<String> rowData:dicTreeDataList) {
			DicInfo deptInfo = new DicInfo();
	
			deptInfo.setId(rowData.get(0));
			deptInfo.setName(rowData.get(1));
			deptInfo.setParentId(rowData.get(2));
			deptInfo.setSortNo(rowData.get(5));
			deptChain.add(deptInfo);
		}
		jsonData = MakeTreeStyleData.convertDeptChainToJson(deptChain);
			
		return jsonData;
	}
	
}
