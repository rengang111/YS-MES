package com.ys.system.action.model.common;

import com.ys.util.basequery.common.BaseModel;

public class SelectMenuPopupInfo extends BaseModel {
	
	private final static long serialVersionUID = 66661L;
	
	private String menuId = "";
	private String menuName = "";
	private String menuControl = "";
	private String menuControlView = "";
	private String treeType = "";
	
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
	
	public String getMenuControl() {
		return this.menuControl;
	}
	public void setMenuControl(String menuControl) {
		this.menuControl = menuControl;
	}
	
	public String getMenuControlView() {
		return this.menuControlView;
	}
	public void setMenuControlView(String menuControlView) {
		this.menuControlView = menuControlView;
	}
	
	public String getTreeType() {
		return this.treeType;
	}
	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}
}
