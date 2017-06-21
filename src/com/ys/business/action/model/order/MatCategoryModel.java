package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;
import com.ys.business.db.data.B_MaterialCategoryData;

public class MatCategoryModel extends BaseModel {
	private String parentCategoryId = "";
	private String parentCategoryName = "";
	private String unitIdName = "";
	private String userCategoryId = "";
	private String recordId = "";
	private String numCheck = "";
	private String numCheckNode = "";
	private String address = "";
	private String categoryName = "";
	private String categoryOldName="";
	private String categoryId = "";
	private String categoryIdChid = "";
	private String memo = "";
	private String formatDes = "";
	private B_MaterialCategoryData unitData = new B_MaterialCategoryData();

	public String getCategoryIdChid() {
		return this.categoryIdChid;
	}
	public void setCategoryIdChid(String categoryIdChid) {
		this.categoryIdChid = categoryIdChid;
	}
	
	public String getNumCheckNode() {
		return this.numCheckNode;
	}
	public void setNumCheckNode(String numCheckNode) {
		this.numCheckNode = numCheckNode;
	}
	
	public String getRecordId() {
		return this.recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public String getCategoryOldName() {
		return this.categoryOldName;
	}
	public void setCategoryOldName(String categoryOldName) {
		this.categoryOldName = categoryOldName;
	}
	
	public String getFormatDes() {
		return this.formatDes;
	}
	public void setFormatDes(String formatDes) {
		this.formatDes = formatDes;
	}
	
	public String getMemo() {
		return this.memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCategoryName() {
		return this.categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getParentCategoryId() {
		return this.parentCategoryId;
	}
	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	
	public String getParentCategoryName() {
		return this.parentCategoryName;
	}
	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}
	
	public String getUnitIdName() {
		return this.unitIdName;
	}
	public void setUnitIdName(String unitIdName) {
		this.unitIdName = unitIdName;
	}	
	
	public String getUserCategoryId() {
		return this.userCategoryId;
	}
	public void setUserCategoryId(String userCategoryId) {
		this.userCategoryId = userCategoryId;
	}
	
	public String getNumCheck() {
		return this.numCheck;
	}
	public void setNumCheck(String numCheck) {
		this.numCheck = numCheck;
	}	
	
	public B_MaterialCategoryData getunitData() {
		return this.unitData;
	}
	public void setunitData(B_MaterialCategoryData unitData) {
		this.unitData = unitData;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

}
