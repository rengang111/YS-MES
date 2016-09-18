package com.ys.util.basequery;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class QueryWhereBean {
	ArrayList<QueryWhereSubBean> whereList = new ArrayList<QueryWhereSubBean>();
	HttpServletRequest request = null;

	public ArrayList<QueryWhereSubBean> getWhereList() {
		return this.whereList;
	}
	public void setWhereList(ArrayList<QueryWhereSubBean> whereList) {
		this.whereList = whereList;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void addSubWhere(QueryWhereSubBean subWhere) {
		whereList.add(subWhere);
	}
	
	public String getSql(HashMap<String, String> userDefinedSearchCase) {
		StringBuffer sql = new StringBuffer("");
		//if (whereList.size() > 0) {
		//	sql.append(" WHERE ");
		//}
		String lg = "";
		for (QueryWhereSubBean subWhere:whereList) {
			String value = getFormData(subWhere, userDefinedSearchCase);
			if (!value.trim().equals("")) {
				if (!sql.toString().equals("")) {
					sql.append(" ");
					sql.append(lg);
					sql.append(" ");
				}
				lg = subWhere.getLg();
				String bt = subWhere.getBt();
				String subWhereSuffix = "";
				if (bt.indexOf("(") >= 0) {
					sql.append(bt);
				}
				if (bt.indexOf(")") >= 0) {
					subWhereSuffix = bt;
				}
				sql.append(subWhere.getName() + " ");
	
				String dType = subWhere.getDType();
				String oper = subWhere.getOper().toLowerCase();
				switch(oper) {
				case ">":
					sql.append("> ");
					sql.append(BaseQuery.processDataType(value, dType, ""));
					break;
				case ">=":
					sql.append(">= ");
					sql.append(BaseQuery.processDataType(value, dType, ""));
					break;
				case "<":
					sql.append("< ");
					sql.append(BaseQuery.processDataType(value, dType, ""));
					break;
				case "<=":
					sql.append("<= ");
					sql.append(BaseQuery.processDataType(value, dType, ""));
					break;
				case "=":
					sql.append("= ");
					sql.append(BaseQuery.processDataType(value, dType, ""));
					break;
				case "like":
					sql.append(BaseQuery.processDataType(value, dType, "like"));
					break;
				case "llike":
					sql.append(BaseQuery.processDataType(value, dType, "llike"));
					break;
				case "rlike":
					sql.append(BaseQuery.processDataType(value, dType, "rlike"));
					break;
				case "in":
					sql.append(BaseQuery.processDataType(value, dType, "in"));
					break;
				}
				if (!sql.toString().equals("")) {
					sql.append(subWhereSuffix);

				}
			}
		}
		
		return sql.toString();
	}
	
	private String getFormData(QueryWhereSubBean subWhere, HashMap<String, String> userDefinedSearchCase) {
		String formData = "";
		try {
			if (userDefinedSearchCase.containsKey(subWhere.getReqName())) {
				formData = userDefinedSearchCase.get(subWhere.getReqName());
			} else {
				formData = request.getParameter(subWhere.getReqName());
				//Enumeration x = request.getParameterNames();
				//while(x.hasMoreElements()) {
				//	System.out.println(x.nextElement());
				//}
			}
		}
		catch(Exception e) {
			
		}
		if (formData == null) {
			formData = "";
		}
		return formData;
	}
}
