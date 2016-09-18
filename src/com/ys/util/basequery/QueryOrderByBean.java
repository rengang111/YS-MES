package com.ys.util.basequery;

public class QueryOrderByBean {
	private String caseInfo = "";

	public String getCaseInfo() {
		return caseInfo;
	}
	public void setCaseInfo(String caseInfo) {
		this.caseInfo = caseInfo;
	}
	
	public String getSql() {

		StringBuffer sql = new StringBuffer("");
		if (!caseInfo.equals("")) {
			sql.append(" ORDER BY ");
			sql.append(caseInfo);
		}

		return sql.toString();

	}
}
