package com.ys.business.action.model.mouldcontract;

import com.ys.util.basequery.common.BaseModel;

import net.sf.json.JSONArray;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_LatePerfectionQuestionData;
import com.ys.business.db.data.B_LatePerfectionRelationFileData;
import com.ys.business.db.data.B_MouldAcceptanceData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldDetailData;
import com.ys.business.db.data.B_MouldPayInfoData;
import com.ys.business.db.data.B_MouldPayListData;
import com.ys.business.db.data.B_ProcessControlData;
import com.ys.business.db.data.B_ProjectTaskCostData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.system.db.data.S_DICData;

public class MouldContractModel extends BaseModel {
	private final String className = "com.ys.business.service.mouldcontract.MouldContractService";
	private String keyBackup = "";
	private B_MouldBaseInfoData mouldBaseInfoData = new B_MouldBaseInfoData();
	private B_MouldDetailData mouldDetailData = new B_MouldDetailData();
	private B_MouldAcceptanceData mouldAcceptanceData = new B_MouldAcceptanceData();
	private B_MouldPayInfoData mouldPayInfoData = new B_MouldPayInfoData();
	private B_MouldPayListData mouldPayListData = new B_MouldPayListData();

	private String mouldBaseId = "";
	private String contractId = "";
	private String productModelId = "";
	private String productModelName = "";
	private String mouldFactoryId = "";
	private String mouldFactory = "";
	private String payCase = "";
	private String finishTime = "";
	private String acceptanceDate = "";
	private String result = "";
	private String withhold = "";
	private String paid = "";
	private String memo = "";
	private String place = "";
	private String type = "";
	private String sumPrice = "";
	
	private ArrayList<ListOption> productModelIdList = new ArrayList<ListOption>();
	private ArrayList<ListOption> mouldFactoryList = new ArrayList<ListOption>();
	private ArrayList<ListOption> resultList = new ArrayList<ListOption>();
	private ArrayList<ListOption> placeList = new ArrayList<ListOption>();
	private ArrayList<ListOption> typeList = new ArrayList<ListOption>();
	
	
	
	public String getClassName() {
		return this.className;
	}
	
	public String getKeyBackup() {
		
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public B_MouldBaseInfoData getMouldBaseInfoData() {
		return this.mouldBaseInfoData;
	}
	public void setMouldBaseInfoData(B_MouldBaseInfoData mouldBaseInfoData) {
		this.mouldBaseInfoData = mouldBaseInfoData;
	}

	public B_MouldDetailData getMouldDetailData() {
		return this.mouldDetailData;
	}
	public void setMouldDetailData(B_MouldDetailData mouldDetailData) {
		this.mouldDetailData = mouldDetailData;
	}	
	
	public B_MouldPayInfoData getMouldPayInfoData() {
		return this.mouldPayInfoData;
	}
	public void setMouldPayInfoData(B_MouldPayInfoData mouldPayInfoData) {
		this.mouldPayInfoData = mouldPayInfoData;
	}	
	
	public B_MouldAcceptanceData getMouldAcceptanceData() {
		return this.mouldAcceptanceData;
	}
	public void setMouldAcceptanceData(B_MouldAcceptanceData mouldAcceptanceData) {
		this.mouldAcceptanceData = mouldAcceptanceData;
	}	
	
	public B_MouldPayListData getMouldPayListData() {
		return this.mouldPayListData;
	}
	public void setMouldPayListData(B_MouldPayListData mouldPayListData) {
		this.mouldPayListData = mouldPayListData;
	}

	public String getMouldBaseId() {
		return this.mouldBaseId;
	}
	public void setMouldBaseId(String mouldBaseId) {
	    this.mouldBaseId = mouldBaseId;
	}
	public String getContractId() {
	    return this.contractId;
	}
	public void setContractId(String contractId) {
	    this.contractId = contractId;
	}
	public String getProductModelId() {
	    return this.productModelId;
	}
	public void setProductModelId(String productModelId) {
	    this.productModelId = productModelId;
	}
	public String getProductModelName() {
	    return this.productModelName;
	}
	public void setProductModelName(String productModelName) {
	    this.productModelName = productModelName;
	}
	public String getMouldFactoryId() {
	    return this.mouldFactoryId;
	}
	public void setMouldFactoryId(String mouldFactoryId) {
	    this.mouldFactoryId = mouldFactoryId;
	}
	public String getMouldFactory() {
	    return this.mouldFactory;
	}
	public void setMouldFactory(String mouldFactory) {
	    this.mouldFactory = mouldFactory;
	}
	public String getPayCase() {
	    return this.payCase;
	}
	public void setPayCase(String payCase) {
	    this.payCase = payCase;
	}
	public String getFinishTime() {
	    return this.finishTime;
	}
	public void setFinishTime(String finishTime) {
	    this.finishTime = finishTime;
	}
	public String getAcceptanceDate() {
	    return this.acceptanceDate;
	}
	public void setAcceptanceDate(String acceptanceDate) {
	    this.acceptanceDate = acceptanceDate;
	}
	public String getResult() {
	    return this.result;
	}
	public void setResult(String result) {
	    this.result = result;
	}
	public String getWithhold() {
	    return this.withhold;
	}
	public void setWithhold(String withhold) {
	    this.withhold = withhold;
	}
	public String getPaid() {
	    return this.paid;
	}
	public void setPaid(String paid) {
	    this.paid = paid;
	}
	public String getMemo() {
	    return this.memo;
	}
	public void setMemo(String memo) {
	    this.memo = memo;
	}
	public String getPlace() {
	    return this.place;
	}
	public void setPlace(String place) {
	    this.place = place;
	}
	public String getType() {
	    return this.type;
	}
	public void setType(String type) {
	    this.type = type;
	}
	public String getSumPrice() {
	    return this.sumPrice;
	}
	public void setSumPrice(String sumPrice) {
	    this.sumPrice = sumPrice;
	}
	
	public ArrayList<ListOption> getProductModelIdList() {
		return this.productModelIdList;
	}
	public void setProductModelIdList(ArrayList<ListOption> productModelIdList) {
		this.productModelIdList = productModelIdList;
	}

	public ArrayList<ListOption> getMouldFactoryList() {
		return this.mouldFactoryList;
	}
	public void setMouldFactoryList(ArrayList<ListOption> mouldFactoryList) {
		this.mouldFactoryList = mouldFactoryList;
	}
	
	public ArrayList<ListOption> getResultList() {
		return this.resultList;
	}
	public void setResultList(ArrayList<ListOption> resultList) {
		this.resultList = resultList;
	}
	
	public ArrayList<ListOption> getPlaceList() {
		return this.placeList;
	}
	public void setPlaceList(ArrayList<ListOption> placeList) {
		this.placeList = placeList;
	}
	
	public ArrayList<ListOption> getTypeList() {
		return this.typeList;
	}
	public void setTypeList(ArrayList<ListOption> typeList) {
		this.typeList = typeList;
	}
}
