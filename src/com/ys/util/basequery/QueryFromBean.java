package com.ys.util.basequery;

public class QueryFromBean {
	private String caseInfo = "";

	public String getCaseInfo() {
		return caseInfo;
	}
	public void setCaseInfo(String caseInfo) {
		this.caseInfo = caseInfo;
	}
	
	public String getSql() {
		//TODO:
		String sql = "";
		if (!caseInfo.equals("")) {
			sql = " FROM " + caseInfo + " ";
		}
		return sql;
	}
}
