package com.ys.business.action.model.mouldcontract;

import com.ys.util.basequery.common.BaseModel;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_LatePerfectionQuestionData;
import com.ys.business.db.data.B_LatePerfectionRelationFileData;
import com.ys.business.db.data.B_MouldAcceptanceData;
import com.ys.business.db.data.B_MouldContractBaseInfoData;
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
	private B_MouldContractBaseInfoData mouldContractBaseInfoData = new B_MouldContractBaseInfoData();
	private B_MouldDetailData mouldDetailData = new B_MouldDetailData();
	private B_MouldAcceptanceData mouldAcceptanceData = new B_MouldAcceptanceData();
	private B_MouldPayInfoData mouldPayInfoData = new B_MouldPayInfoData();
	private B_MouldPayListData mouldPayListData = new B_MouldPayListData();

	private String mouldBaseId = "";
	private String contractId = "";
	private String contractYear = "";
	private String productModelId = "";
	private String productModelIdView = "";
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
	private String belong = "";
	private String mouldType = "";
	private String typeDesc = "";
	private String supplierIdView = "";
	private String supplierName = "";
	private String contractDate = "";
	private String deliverDate = "";
	
	private ArrayList<ListOption> productModelIdList = new ArrayList<ListOption>();
	private ArrayList<ListOption> mouldFactoryList = new ArrayList<ListOption>();
	private ArrayList<ListOption> resultList = new ArrayList<ListOption>();
	private ArrayList<ListOption> placeList = new ArrayList<ListOption>();
	private ArrayList<ListOption> typeList = new ArrayList<ListOption>();
	private ArrayList<ListOption> belongList = new ArrayList<ListOption>();
	private ArrayList<HashMap<String, String>> regulations = new ArrayList<HashMap<String, String>>(); 
	
	public String getClassName() {
		return this.className;
	}
	
	public String getKeyBackup() {
		
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public B_MouldContractBaseInfoData getMouldContractBaseInfoData() {
		return this.mouldContractBaseInfoData;
	}
	public void setMouldContractBaseInfoData(B_MouldContractBaseInfoData mouldContractBaseInfoData) {
		this.mouldContractBaseInfoData = mouldContractBaseInfoData;
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
	public String getContractYear() {
	    return this.contractId;
	}
	public void getContractYear(String contractId) {
	    this.contractId = contractId;
	}
	public String getProductModelId() {
	    return this.productModelId;
	}
	public void setProductModelId(String productModelId) {
	    this.productModelId = productModelId;
	}
	public String getProductModelIdView() {
	    return this.productModelIdView;
	}
	public void setProductModelIdView(String productModelIdView) {
	    this.productModelIdView = productModelIdView;
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
	public String getBelong() {
	    return this.belong;
	}
	public void setBelong(String belong) {
	    this.belong = belong;
	}
	public String getMouldType() {
	    return this.mouldType;
	}
	public void setMouldType(String mouldType) {
	    this.mouldType = mouldType;
	}
	public void setSupplierIdView(String supplierIdView) {
	    this.supplierIdView = supplierIdView;
	}
	public String getSupplierIdView() {
	    return this.supplierIdView;
	}
	public void setSupplierName(String supplierName) {
	    this.supplierName = supplierName;
	}
	public String getSupplierName() {
	    return this.supplierName;
	}
	public void setTypeDesc(String typeDesc) {
	    this.typeDesc = typeDesc;
	}
	public String getTypeDesc() {
	    return this.typeDesc;
	}
	public void setContractDate(String contractDate) {
	    this.contractDate = contractDate;
	}
	public String getContractDate() {
	    return this.contractDate;
	}
	
	public void setDeliverDate(String deliverDate) {
	    this.deliverDate = deliverDate;
	}
	public String getDeliverDate() {
	    return this.deliverDate;
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
	
	public ArrayList<ListOption> getBelongList() {
		return this.belongList;
	}
	public void setBelongList(ArrayList<ListOption> belongList) {
		this.belongList = belongList;
	}
	
	public ArrayList<HashMap<String, String>> getRegulations() {
		return this.regulations;
	}
	public void setRegulations(ArrayList<HashMap<String, String>> regulations) {
		this.regulations = regulations;
	}
	
}
