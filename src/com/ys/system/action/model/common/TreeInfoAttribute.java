package com.ys.system.action.model.common;

import java.io.Serializable;

public class TreeInfoAttribute implements Serializable {
	
	private final static long serialVersionUID = 66661L;
	
	private String url = "";
	private String sortNo = "";
	
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSortNo() {
		return this.sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}	

}
