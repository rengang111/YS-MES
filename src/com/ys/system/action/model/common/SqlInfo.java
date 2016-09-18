package com.ys.system.action.model.common;

import java.io.Serializable;

public class SqlInfo implements Serializable {
	
	private final static long serialVersionUID = 66661L;
	
	private String sql = "";
	private String sqlCount = "";
	
	public String getSql() {
		return this.sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String getSqlCount() {
		return this.sqlCount;
	}
	public void setSqlCount(String sqlCount) {
		this.sqlCount = sqlCount;
	}	

}
