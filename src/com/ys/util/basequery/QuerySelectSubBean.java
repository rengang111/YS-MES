package com.ys.util.basequery;

public class QuerySelectSubBean {
	private String name = "";
	private String alias = "";
	private String ctype = "";

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlias() {
		return this.alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getCType() {
		return this.ctype;
	}
	public void setCType(String ctype) {
		this.ctype = ctype;
	}
	
	public String getSql() {
		return "";
	}
}
