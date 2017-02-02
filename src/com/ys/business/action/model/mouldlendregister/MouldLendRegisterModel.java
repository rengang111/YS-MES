package com.ys.business.action.model.mouldlendregister;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_MouldAcceptanceData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldDetailData;
import com.ys.business.db.data.B_MouldLendConfirmData;
import com.ys.business.db.data.B_MouldLendDetailData;
import com.ys.business.db.data.B_MouldLendRegisterData;
import com.ys.business.db.data.B_MouldPayInfoData;
import com.ys.business.db.data.B_MouldPayListData;
import com.ys.util.basequery.common.BaseModel;

public class MouldLendRegisterModel extends BaseModel {
	private final String className = "com.ys.business.service.mouldlendregister.MouldLendRegisterService";
	private String keyBackup = "";
	private B_MouldLendRegisterData mouldLendRegisterData = new B_MouldLendRegisterData();
	private B_MouldLendDetailData mouldLendDetailData = new B_MouldLendDetailData();
	private B_MouldLendConfirmData mouldLendConfirmData = new B_MouldLendConfirmData();

	private String mouldLendNo = "";
	private String productModelId = "";
	private String productModelName = "";
	private String mouldFactoryId = "";
	private String mouldFactory = "";
	private String lendTime = "";
	private String returnTime = "";
	private String proposer = "";
	private String brokerage = "";
	private String no = "";
	private String name = "";
	private String mouldDetailId = "";
	
	private ArrayList<ListOption> productModelIdList = new ArrayList<ListOption>();
	private ArrayList<ListOption> mouldFactoryList = new ArrayList<ListOption>();
	private ArrayList<ListOption> proposerList = new ArrayList<ListOption>();
	private ArrayList<ListOption> noList = new ArrayList<ListOption>();
	
	
	public String getClassName() {
		return this.className;
	}
	
	public String getKeyBackup() {
		
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public B_MouldLendRegisterData getMouldLendRegisterData() {
		return this.mouldLendRegisterData;
	}
	public void setMouldLendRegisterData(B_MouldLendRegisterData mouldLendRegisterData) {
		this.mouldLendRegisterData = mouldLendRegisterData;
	}

	public B_MouldLendDetailData getMouldLendDetailData() {
		return this.mouldLendDetailData;
	}
	public void setMouldLendDetailData(B_MouldLendDetailData mouldLendDetailData) {
		this.mouldLendDetailData = mouldLendDetailData;
	}	
	
	public B_MouldLendConfirmData getMouldLendConfirmData() {
		return this.mouldLendConfirmData;
	}
	public void setMouldLendConfirmData(B_MouldLendConfirmData mouldLendConfirmData) {
		this.mouldLendConfirmData = mouldLendConfirmData;
	}	

	public String getMouldLendNo() {
	    return this.mouldLendNo;
	}
	public void setMouldLendNo(String mouldLendNo) {
	    this.mouldLendNo = mouldLendNo;
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

	public String getLendTime() {
	    return this.lendTime;
	}
	public void setLendTime(String lendTime) {
	    this.lendTime = lendTime;
	}

	public String getReturnTime() {
	    return this.returnTime;
	}
	public void setReturnTime(String returnTime) {
	    this.returnTime = returnTime;
	}

	public String getProposer() {
	    return this.proposer;
	}
	public void setProposer(String proposer) {
	    this.proposer = proposer;
	}

	public String getBrokerage() {
	    return this.brokerage;
	}
	public void setBrokerage(String brokerage) {
	    this.brokerage = brokerage;
	}
	
	public String getNo() {
	    return this.no;
	}
	public void setNo(String no) {
	    this.no = no;
	}
	
	public String getName() {
	    return this.name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	
	public String getMouldDetailId() {
	    return this.mouldDetailId;
	}
	public void setMouldDetailId(String mouldDetailId) {
	    this.mouldDetailId = mouldDetailId;
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
	
	public ArrayList<ListOption> getProposerList() {
		return this.proposerList;
	}
	public void setProposerList(ArrayList<ListOption> proposerList) {
		this.proposerList = proposerList;
	}
	
	public ArrayList<ListOption> getNoList() {
		return this.noList;
	}
	public void setNoList(ArrayList<ListOption> noList) {
		this.noList = noList;
	}
	
}
