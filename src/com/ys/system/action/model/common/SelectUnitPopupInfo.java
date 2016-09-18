package com.ys.system.action.model.common;

import com.ys.util.basequery.common.BaseModel;

public class SelectUnitPopupInfo extends BaseModel {
	
	private final static long serialVersionUID = 66661L;
	
	private String unitId = "";
	private String unitName = "";
	private String menuId = "";
	private String unitControl = "";
	private String unitControlView = "";
	private String treeType = "";
	
	public String getUnitId() {
		return this.unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getUnitName() {
		return this.unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}	
	
	public String getMenuId() {
		return this.menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}	
	
	public String getUnitControl() {
		return this.unitControl;
	}
	public void setUnitControl(String unitControl) {
		this.unitControl = unitControl;
	}
	
	public String getUnitControlView() {
		return this.unitControlView;
	}
	public void setUnitControlView(String unitControlView) {
		this.unitControlView = unitControlView;
	}
	
	public String getTreeType() {
		return this.treeType;
	}
	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}
}
