package com.ys.business.action.model.material;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.system.db.data.S_DICData;

public class CustomerModel extends BaseModel {
	private String keyBackup = "";
	private String customerId = "";
	private String customerSimpleDes = "";
	private String customerName = "";
	private String paymentTerm = "";
	private String country = "";
	private String currency = "";
	private String shippingCase = "";
	private String loadingPort = "";	
	private String deliveryPort = "";
	private B_CustomerData customer = new B_CustomerData();
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
	
	public String getCustomerSimpleDes() {
		return this.customerSimpleDes;
	}
	public void setCustomerSimpleDes(String customerSimpleDes) {
		this.customerSimpleDes = customerSimpleDes;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}	

	public String getPaymentTerm() {
		return this.paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}	
	
	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getShippingCase() {
		return this.shippingCase;
	}
	public void setShippingCase(String shippingCase) {
		this.shippingCase = shippingCase;
	}	
	
	public String getLoadingPort() {
		return this.loadingPort;
	}
	public void setLoadingPort(String loadingPort) {
		this.loadingPort = loadingPort;
	}	

	public String getDeliveryPort() {
		return this.deliveryPort;
	}
	public void setDeliveryPort(String deliveryPort) {
		this.deliveryPort = deliveryPort;
	}
	

	
	public B_CustomerData getCustomer() {
		return this.customer;
	}
	public void setCustomer(B_CustomerData customerData) {
		this.customer = customerData;
	}
	
}
