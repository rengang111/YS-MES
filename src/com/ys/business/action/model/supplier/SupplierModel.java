package com.ys.business.action.model.supplier;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_SupplierBasicInfoData;
import com.ys.system.db.data.S_DICData;

public class SupplierModel extends BaseModel {
	private String keyBackup = "";
	private String supplierId = "";
	private String supplierSimpleDes = "";
	private String supplierDes = "";
	private String twoLevelId = "";
	private String twoLevelIdDes = "";
	private String paymentTerm = "";
	private String country = "";
	private String province = "";
	private String city = "";
	private String address = "";
	ArrayList<ListOption> countryList = new ArrayList<ListOption>();
	ArrayList<ListOption> provinceList = new ArrayList<ListOption>();
	ArrayList<ListOption> cityList = new ArrayList<ListOption>();
	ArrayList<ListOption> unsureList = new ArrayList<ListOption>();
	private B_SupplierBasicInfoData supplierBasicInfoData = new B_SupplierBasicInfoData();
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public String getSupplierId() {
		return this.supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	public String getSupplierSimpleDes() {
		return this.supplierSimpleDes;
	}
	public void setSupplierSimpleDes(String supplierSimpleDes) {
		this.supplierSimpleDes = supplierSimpleDes;
	}
	
	public String getSupplierDes() {
		return this.supplierDes;
	}
	public void setSupplierDes(String supplierDes) {
		this.supplierDes = supplierDes;
	}	
	
	public String getTwoLevelId() {
		return this.twoLevelId;
	}
	public void setTwoLevelID(String twoLevelId) {
		this.twoLevelId = twoLevelId;
	}
	
	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getProvince() {
		return this.province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}	
	
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.city = address;
	}	
	
	public String getPaymentTerm() {
		return this.paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}	
		
	public String getTwoLevelIdDes() {
		return this.twoLevelIdDes;
	}
	public void setTwoLevelIdDes(String twoLevelIdDes) {
		this.twoLevelIdDes = twoLevelIdDes;
	}
	
	public ArrayList<ListOption> getProvinceList() {
		return this.provinceList;
	}
	public void setProvinceList(ArrayList<ListOption> provinceList) {
		this.provinceList = provinceList;
	}
	
	public ArrayList<ListOption> getCityList() {
		return this.cityList;
	}
	public void setCityList(ArrayList<ListOption> cityList) {
		this.cityList = cityList;
	}
	
	public ArrayList<ListOption> getCountryList() {
		return this.countryList;
	}
	public void setCountryList(ArrayList<ListOption> countryList) {
		this.countryList = countryList;
	}
	
	public ArrayList<ListOption> getUnsureList() {
		return this.unsureList;
	}
	public void setUnsureList(ArrayList<ListOption> unsureList) {
		this.unsureList = unsureList;
	}
	
	public B_SupplierBasicInfoData getSupplierBasicInfoData() {
		return this.supplierBasicInfoData;
	}
	public void setSupplierBasicInfoData(B_SupplierBasicInfoData supplierBasicInfoData) {
		this.supplierBasicInfoData = supplierBasicInfoData;
	}
	
}
