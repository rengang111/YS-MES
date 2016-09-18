package com.ys.system.action.model.common;

import com.ys.util.basequery.common.BaseModel;

public class SelectDicTypePopupInfo extends BaseModel {
	
	private final static long serialVersionUID = 66661L;
	
	private String dicTypeIdName = "";
	private String sortNo = "";
	private String treeType = "";
	private String type = "";
	private String dicTypeId = "";
	private String dicControl = "";
	private String dicControlView = "";

	public String getDicTypeIdName() {
		return this.dicTypeIdName;
	}
	public void setDicTypeIdName(String dicTypeIdName) {
		this.dicTypeIdName = dicTypeIdName;
	}	
	
	public String getSortNo() {
		return this.sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getTreeType() {
		return this.treeType;
	}
	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}	
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	
	public String getDicTypeId() {
		return this.dicTypeId;
	}
	public void setDicTypeId(String dicTypeId) {
		this.dicTypeId = dicTypeId;
	}		
	
	public String getDicControl() {
		return this.dicControl;
	}
	public void setDicControl(String dicControl) {
		this.dicControl = dicControl;
	}
	
	public String getDicControlView() {
		return this.dicControlView;
	}
	public void setDicControlView(String dicControlView) {
		this.dicControlView = dicControlView;
	}	
}
