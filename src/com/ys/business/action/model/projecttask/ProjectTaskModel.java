package com.ys.business.action.model.projecttask;

import com.ys.util.basequery.common.BaseModel;

import net.sf.json.JSONArray;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_ProjectTaskCostData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.system.db.data.S_DICData;

public class ProjectTaskModel extends BaseModel {
	private final String className = "com.ys.business.service.projecttask.ProjectTaskService";
	private String keyBackup = "";
	private String projectId = "";
	private String projectName = "";
	private String tempVersion = "";
	private String manager = "";
	private String referPrototype = "";
	private String designCapability = "";
	private String beginTime = "";
	private String endTime = "";	
	private String packing = "";
	private String estimateCost = "";
	private String salePrice = "";
	private String sales = "";
	private String recoveryNum = "";
	private String failMode = "";
	private String imageFileName = "";
	private String[][] filenames;
	private String imageKey = "";
	private String path = "";
	private String[] nowUseImageList;
	private ArrayList<ListOption> managerList = new ArrayList<ListOption>();
	private ArrayList<ListOption> currencyList = new ArrayList<ListOption>();
	private String currency = "";
	private String exchangeRate = "";
	private B_ProjectTaskData projectTaskData = new B_ProjectTaskData();
	private JSONArray costDataList = new JSONArray();
	private JSONArray costDataTypeCount = new JSONArray();
	
	public String getClassName() {
		return this.className;
	}
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public String getProjectId() {
		return this.projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getTempVersion() {
		return this.tempVersion;
	}
	public void setTempVersion(String tempVersion) {
		this.tempVersion = tempVersion;
	}	

	public String getManager() {
		return this.manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}	
	
	public String getReferPrototype() {
		return this.referPrototype;
	}
	public void setReferPrototype(String referPrototype) {
		this.referPrototype = referPrototype;
	}
	
	public String getDesignCapability() {
		return this.designCapability;
	}
	public void setDesignCapability(String designCapability) {
		this.designCapability = designCapability;
	}
	
	public String getBeginTime() {
		return this.beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}	
	
	public String getEndTime() {
		return this.endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}	

	public String getEstimateCost() {
		return this.estimateCost;
	}
	public void setEstimateCost(String estimateCost) {
		this.estimateCost = estimateCost;
	}
	
	public String getSalePrice() {
		return this.salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	
	public String getSales() {
		return this.sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	
	public String getPacking() {
		return this.packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	
	public String getRecoveryNum() {
		return this.recoveryNum;
	}
	public void setRecoveryNum(String recoveryNum) {
		this.recoveryNum = recoveryNum;
	}
	
	public String getFailMode() {
		return this.failMode;
	}
	public void setFailMode(String failMode) {
		this.failMode = failMode;
	}
	
	public String getImageFileName() {
		return this.imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	public String[][] getFilenames() {
		return this.filenames;
	}
	public void setFilenames(String[][] filenames) {
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
	
	public String[] getNowUseImageList() {
		return this.nowUseImageList;
	}
	public void setNowUseImageList(String[] nowUseImageList) {
		this.nowUseImageList = nowUseImageList;
	}
	
	public ArrayList<ListOption> getManagerList() {
		return this.managerList;
	}
	public void setManagerList(ArrayList<ListOption> managerList) {
		this.managerList = managerList;
	}	
	
	public ArrayList<ListOption> getCurrencyList() {
		return this.currencyList;
	}
	public void setCurrencyList(ArrayList<ListOption> currencyList) {
		this.currencyList = currencyList;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getExchangeRate() {
		return this.exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public B_ProjectTaskData getProjectTaskData() {
		return this.projectTaskData;
	}
	public void setProjectTaskData(B_ProjectTaskData projectTaskData) {
		this.projectTaskData = projectTaskData;
	}

	public JSONArray getCostDataList() {
		return this.costDataList;
	}
	public void setCostDataList(JSONArray costDataList) {
		this.costDataList = costDataList;
	}
	
	public JSONArray getCostDataTypeCount() {
		return this.costDataTypeCount;
	}
	public void setCostDataTypeCount(JSONArray costDataTypeCount) {
		this.costDataTypeCount = costDataTypeCount;
	}
	
}
