package com.ys.business.action.model.supplier;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_SupplierData;
import com.ys.system.db.data.S_DICData;

public class SupplierModel extends BaseModel {
	private String keyBackup = "";
	private String supplierId = "";
	private String shortName = "";
	private String supplierName = "";
	private String categoryId = "";
	private String categoryDes = "";
	private String paymentTerm = "";
	private String county = "";
	private String province = "";
	private String city = "";
	private String address = "";
	private String subId = "";
	ArrayList<ListOption> countryList = new ArrayList<ListOption>();
	ArrayList<ListOption> provinceList = new ArrayList<ListOption>();
	ArrayList<ListOption> cityList = new ArrayList<ListOption>();
	ArrayList<ListOption> unsureList = new ArrayList<ListOption>();
	private B_SupplierData supplierBasicInfoData = new B_SupplierData();
	private B_SupplierData supplier = new B_SupplierData();
	
	public String getSubId() {
		return this.subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	
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
	
	public String getshortName() {
		return this.shortName;
	}
	public void setshortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getsupplierName() {
		return this.supplierName;
	}
	public void setsupplierName(String supplierName) {
		this.supplierName = supplierName;
	}	
	
	public String getcategoryId() {
		return this.categoryId;
	}
	public void setcategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCounty() {
		return this.county;
	}
	public void setCounty(String country) {
		this.county = country;
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
		
	public String getcategoryDes() {
		return this.categoryDes;
	}
	public void setcategoryDes(String categoryDes) {
		this.categoryDes = categoryDes;
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
	
	public B_SupplierData getSupplierBasicInfoData() {
		return this.supplierBasicInfoData;
	}
	public void setSupplierBasicInfoData(B_SupplierData supplierBasicInfoData) {
		this.supplierBasicInfoData = supplierBasicInfoData;
	}
	
	public B_SupplierData getSupplier() {
		return this.supplier;
	}
	public void setSupplier(B_SupplierData supplier) {
		this.supplier = supplier;
	}
}
