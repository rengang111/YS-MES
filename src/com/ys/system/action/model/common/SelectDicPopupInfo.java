package com.ys.system.action.model.common;

import com.ys.util.basequery.common.BaseModel;

public class SelectDicPopupInfo extends BaseModel {
	
	private final static long serialVersionUID = 66661L;
	
	private String dicCodeName = "";
	private String dicParentCodeName = "";
	private String dicId = "";
	private String dicName = "";
	private String dicPrarentID = "";	
	private String dicTypeID = "";
	private String sortNo = "";
	private String treeType = "";
	private String dicControl = "";
	private String dicControlView = "";

	public String getDicCodeName() {
		return this.dicCodeName;
	}
	public void setDicCodeName(String dicCodeName) {
		this.dicCodeName = dicCodeName;
	}	
	
	public String getDicParentCodeName() {
		return this.dicParentCodeName;
	}
	public void setDicParentCodeName(String dicParentCodeName) {
		this.dicParentCodeName = dicParentCodeName;
	}
	
	public String getDicId() {
		return this.dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	
	public String getDicName() {
		return this.dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}	
	public String getDicPrarentID() {
		return this.dicPrarentID;
	}
	public void setDicPrarentID(String dicPrarentID) {
		this.dicPrarentID = dicPrarentID;
	}
	
	public String getDicTypeID() {
		return this.dicTypeID;
	}
	public void setDicTypeID(String dicTypeID) {
		this.dicTypeID = dicTypeID;
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
