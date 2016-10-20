package com.ys.business.action.model.organ;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_OrganBasicInfoData;
import com.ys.util.basequery.common.BaseModel;

public class OrganModel extends BaseModel {
	
	/**
	 * author:renang
	 * 机构管理
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ListOption> categoryList = new ArrayList<ListOption>();
	private B_OrganBasicInfoData organData = new B_OrganBasicInfoData();
	private String name_short;
	private String name_full;
	private String address;
	private String category;
	private String keyBackup = "";
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	public ArrayList<ListOption> getCategoryList() {
		return this.categoryList;
	}
	public void setCategoryList(ArrayList<ListOption> categoryList) {
		this.categoryList = categoryList;
	}
	
	
	public B_OrganBasicInfoData getOrganData() {
		return this.organData;
	}
	public void setOrganData(B_OrganBasicInfoData organData) {
		this.organData = organData;
	}
	
	
	public String getName_short() {
		return this.name_short;
	}
	
	public void setName_short(String name_short) {
		this.name_short = name_short;
	}
	
	public String getname_full() {
		return this.name_full;
	}
	
	public void setname_full(String name_full) {
		this.name_full = name_full;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
}
