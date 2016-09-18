package com.ys.system.interceptor;

public class MenuInfo {
	private String menuId = "";
	private String menuName = "";
	private String menuParentId = "";
	private String menuURL = "";
	private String sortNo = "";
	private String menuDes = "";
	private String menuIcon1 = "";
	private String menuIcon2 = "";
	
	public String getMenuId() {
		return this.menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public String getMenuParentId() {
		return this.menuParentId;
	}
	public void setMenuParentId(String menuParentId) {
		this.menuParentId = menuParentId;
	}
	
	public String getMenuURL() {
		return this.menuURL;
	}
	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}
	
	public String getSortNo() {
		return this.sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	
	public String getMenuDes() {
		return this.menuDes;
	}
	public void setMenuDes(String menuDes) {
		this.menuDes = menuDes;
	}
	
	public String getMenuIcon1() {
		return this.menuIcon1;
	}
	public void setMenuIcon1(String menuIcon1) {
		this.menuIcon1 = menuIcon1;
	}
	
	public String getMenuIcon2() {
		return this.menuIcon2;
	}
	public void setMenuIcon2(String menuIcon2) {
		this.menuIcon2 = menuIcon2;
	}
}
