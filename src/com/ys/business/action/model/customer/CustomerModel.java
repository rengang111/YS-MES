package com.ys.business.action.model.customer;

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
	private String denominationCurrency = "";
	private String shippingCase = "";
	private String loadingPort = "";	
	private String deliveryPort = "";
	private ArrayList<ListOption> countryList = new ArrayList<ListOption>();
	private ArrayList<ListOption> denominationCurrencyList = new ArrayList<ListOption>();
	private ArrayList<ListOption> shippingCaseList = new ArrayList<ListOption>();
	private ArrayList<ListOption> portList = new ArrayList<ListOption>();
	private ArrayList<ListOption> unsureList = new ArrayList<ListOption>();
	private B_CustomerData customerData = new B_CustomerData();
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
	
	public String getDenominationCurrency() {
		return this.denominationCurrency;
	}
	public void setDenominationCurrency(String denominationCurrency) {
		this.denominationCurrency = denominationCurrency;
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
	
	public ArrayList<ListOption> getCountryList() {
		return this.countryList;
	}
	public void setCountryList(ArrayList<ListOption> countryList) {
		this.countryList = countryList;
	}	
	
	public ArrayList<ListOption> getDenominationCurrencyList() {
		return this.denominationCurrencyList;
	}
	public void setDenominationCurrencyList(ArrayList<ListOption> denominationCurrencyList) {
		this.denominationCurrencyList = denominationCurrencyList;
	}
	
	public ArrayList<ListOption> getShippingCaseList() {
		return this.shippingCaseList;
	}
	public void setShippingCaseList(ArrayList<ListOption> shippingCaseList) {
		this.shippingCaseList = shippingCaseList;
	}
	
	public ArrayList<ListOption> getPortList() {
		return this.portList;
	}
	public void setPortList(ArrayList<ListOption> portList) {
		this.portList = portList;
	}
	
	
	public ArrayList<ListOption> getUnsureList() {
		return this.unsureList;
	}
	public void setUnsureList(ArrayList<ListOption> unsureList) {
		this.unsureList = unsureList;
	}
	
	public B_CustomerData getCustomerData() {
		return this.customerData;
	}
	public void setCustomerData(B_CustomerData customerData) {
		this.customerData = customerData;
	}
	
}
