package com.ys.business.action.model.externalsample;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.system.db.data.S_DICData;

public class ExternalSampleModel extends BaseModel {
	private final String className = "com.ys.business.service.externalsample.ExternalSampleService";
	private String keyBackup = "";
	private String sampleId = "";
	private String sampleVersion = "";
	private String sampleName = "";
	private String brand = "";
	private String buyTime = "";
	private String currency = "";
	private String price = "";
	private String address = "";	
	private String memo = "";
	private String imageFileName = "";
	private String[] filenames;
	private String imageKey = "";
	private String path = "";
	private String nowUseImage = "";
	private ArrayList<ListOption> currencyList = new ArrayList<ListOption>();
	private B_ExternalSampleData externalsampleData = new B_ExternalSampleData();
	
	public String getClassName() {
		return this.className;
	}
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public String getSampleId() {
		return this.sampleId;
	}
	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}
	
	public String getSampleVersion() {
		return this.sampleVersion;
	}
	public void setSampleVersion(String sampleVersion) {
		this.sampleVersion = sampleVersion;
	}
	
	public String getSampleName() {
		return this.sampleName;
	}
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}	

	public String getBrand() {
		return this.brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}	
	
	public String getBuyTime() {
		return this.buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getPrice() {
		return this.price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	
	public String getImageFileName() {
		return this.imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.memo = imageFileName;
	}
	
	public String[] getFilenames() {
		return this.filenames;
	}
	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}
	
	public String getImageKey() {
		return this.imageKey;
	}
	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}
	
	public String getPath() {
		return this.path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getNowUseImage() {
		return this.nowUseImage;
	}
	public void setNowUseImage(String nowUseImage) {
		this.nowUseImage = nowUseImage;
	}
	
	public ArrayList<ListOption> getCurrencyList() {
		return this.currencyList;
	}
	public void setCurrencyList(ArrayList<ListOption> currencyList) {
		this.currencyList = currencyList;
	}	
	
	public B_ExternalSampleData getExternalSampleData() {
		return this.externalsampleData;
	}
	public void setExternalSampleData(B_ExternalSampleData externalsampleData) {
		this.externalsampleData = externalsampleData;
	}
	
}
