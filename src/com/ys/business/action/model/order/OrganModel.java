package com.ys.business.action.model.order;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_OrganizationData;
import com.ys.util.basequery.common.BaseModel;

public class OrganModel extends BaseModel {
	
	/**
	 * author:renang
	 * 机构管理
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ListOption> typeList = new ArrayList<ListOption>();
	private B_OrganizationData organData = new B_OrganizationData();
	private String no;
	private String shortName;
	private String fullName;
	private String address;
	private String type;
	private String keyBackup = "";
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	public ArrayList<ListOption> getTypeList() {
		return this.typeList;
	}
	public void setTypeList(ArrayList<ListOption> typeList) {
		this.typeList = typeList;
	}
	
	
	public B_OrganizationData getOrganData() {
		return this.organData;
	}
	public void setOrganData(B_OrganizationData organData) {
		this.organData = organData;
	}
	
	public String getNo() {
		return this.no;
	}
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getShortName() {
		return this.shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
