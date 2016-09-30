package com.ys.system.action.model.organ;

import java.util.ArrayList;

import com.ys.util.basequery.common.BaseModel;

public class OrganModel extends BaseModel {
	
	/**
	 * author:renang
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ArrayList<String>> categoryList = new ArrayList<ArrayList<String>>();
	private String compName;
	private String compFullname;
	private String address;
	private String category;

	public ArrayList<ArrayList<String>> getDutyList() {
		return this.categoryList;
	}
	public void setDutyList(ArrayList<ArrayList<String>> dutyList) {
		this.categoryList = dutyList;
	}
	
	
	public String getCompName() {
		return this.compName;
	}
	
	public void setFormDisp(String compName) {
		this.compName = compName;
	}
	
	public String getCompFullname() {
		return this.compFullname;
	}
	
	public void setCompFullname(String compFullname) {
		this.compFullname = compFullname;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public String Category() {
		return this.category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
}
