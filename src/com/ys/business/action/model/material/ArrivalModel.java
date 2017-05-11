package com.ys.business.action.model.material;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_InventoryData;

public class ArrivalModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private B_InventoryData inventory ;
	private List<B_ArrivalData> arrivalList;
	private B_ArrivalData arrival;
	
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
	
	public B_InventoryData getInventory() {
		return this.inventory;
	}
	public void setInventory(B_InventoryData inventory) {
		this.inventory = inventory;
	}
	
	public B_ArrivalData getArrival() {
		return this.arrival;
	}
	public void setArrival(B_ArrivalData arrival) {
		this.arrival = arrival;
	}

	public List<B_ArrivalData> getArrivalList() {
		return this.arrivalList;
	}
	public void setArrivalList(List<B_ArrivalData> arrivalList) {
		this.arrivalList = arrivalList;
	}
	
}
