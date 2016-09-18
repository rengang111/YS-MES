package com.ys.system.interceptor;

public class DicInfo {
	private String id = "";
	private String name = "";
	private String des = "";
	private String jianpin = "";
	private String parentId = "";
	private String deptGuid = "";
	private String url = "";
	private String sortNo = "";

	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDes() {
		return this.des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	public String getJianpin() {
		return this.jianpin;
	}
	public void setJianpin(String jianpin) {
		this.jianpin = jianpin;
	}
	
	public String getParentId() {
		return this.parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getDeptGuid() {
		return this.deptGuid;
	}
	public void setDeptGuid(String deptGuid) {
		this.deptGuid = deptGuid;
	}
	
	public String getSortNo() {
		return this.sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}	

	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
