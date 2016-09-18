package com.ys.util.basequery;

import java.util.ArrayList;

public class QuerySelectBean {
	ArrayList<QuerySelectSubBean> selectList = new ArrayList<QuerySelectSubBean>();

	public ArrayList<QuerySelectSubBean> getSelectList() {
		return this.selectList;
	}
	public void setSelectList(ArrayList<QuerySelectSubBean> selectList) {
		this.selectList = selectList;
	}
	
	public void addSubSelect(QuerySelectSubBean subSelect) {
		selectList.add(subSelect);
	}	
	
	public String getSql() {
		StringBuffer sql = new StringBuffer("SELECT ");
		boolean isFirst = true;
		for (QuerySelectSubBean subSelect:selectList) {
			if (isFirst) {
				isFirst = false;
			} else {
				sql.append(",");
			}
			sql.append(subSelect.getName());
			sql.append(" ");
			sql.append(subSelect.getAlias());
		}
		sql.append(" ");
		if (isFirst) {
			sql = new StringBuffer("");
		}
		return sql.toString();
	}
	public ArrayList<String> getCTypeList() {
		ArrayList<String> ctypeList = new ArrayList<String>();
		
		for (QuerySelectSubBean subSelect:selectList) {
			ctypeList.add(subSelect.getCType());
		}
		
		return ctypeList;
	}
}
