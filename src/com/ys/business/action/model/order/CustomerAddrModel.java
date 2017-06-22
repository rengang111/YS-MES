package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.system.db.data.S_DICData;

public class CustomerAddrModel extends BaseModel {
	private String keyBackup = "";
	private String customerId = "";
	private String title = "";
	private String address = "";
	private String memo = "";
	private B_CustomerAddrData customerAddrData = new B_CustomerAddrData();
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}	
	
	public String getMemo() {
		return this.memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public B_CustomerAddrData getCustomerAddrData() {
		return this.customerAddrData;
	}
	public void setCustomerAddrData(B_CustomerAddrData customerAddrData) {
		this.customerAddrData = customerAddrData;
	}
	
}
