package com.ys.business.action.model.contact;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_ContactData;
import com.ys.system.db.data.S_DICData;

public class ContactModel extends BaseModel {
	private String keyBackup = "";
	private String companyCode = "";
	private String userName = "";
	private String sex = "";
	private String position = "";
	private String department = "";
	private String mobile = "";
	private String phone = "";
	private String fax = "";
	private String mail = "";
	private String skype = "";
	private String mark = "";
	private String QQ = "";
	private ArrayList<ListOption> sexList = new ArrayList<ListOption>();
	private B_ContactData contactData = new B_ContactData();
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public String getCompanyCode() {
		return this.companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getSex() {
		return this.sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}	
	
	public String getPosition() {
		return this.position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}	
	
	public String getFax() {
		return this.fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}	
	
	public String getMail() {
		return this.mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}	
		
	public String getSkype() {
		return this.skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	
	public String getMark() {
		return this.mark;
	}
	public void setMark(String mark) {
		this.skype = mark;
	}
	
	public String getQQ() {
		return this.QQ;
	}
	public void setQQ(String QQ) {
		this.QQ = QQ;
	}	
	
	public ArrayList<ListOption> getSexList() {
		return this.sexList;
	}
	public void setSexList(ArrayList<ListOption> sexList) {
		this.sexList = sexList;
	}	
	
	public B_ContactData getContactData() {
		return this.contactData;
	}
	public void setContactData(B_ContactData contactData) {
		this.contactData = contactData;
	}
	
}
