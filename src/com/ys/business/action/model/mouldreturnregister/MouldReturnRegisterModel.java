package com.ys.business.action.model.mouldreturnregister;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_MouldAcceptanceData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldDetailData;
import com.ys.business.db.data.B_MouldLendRegisterData;
import com.ys.business.db.data.B_MouldReturnRegisterData;
import com.ys.business.db.data.B_MouldPayInfoData;
import com.ys.business.db.data.B_MouldPayListData;
import com.ys.util.basequery.common.BaseModel;

public class MouldReturnRegisterModel extends BaseModel {
	private final String className = "com.ys.business.service.mouldreturnregister.MouldReturnRegisterService";
	private String keyBackup = "";
	private B_MouldReturnRegisterData mouldReturnRegisterData = new B_MouldReturnRegisterData();
	private B_MouldLendRegisterData mouldLendRegisterData = new B_MouldLendRegisterData(); 

	private String lendId = "";
	private String productModelNo = "";
	private String productModelName = "";
	private String mouldFactory = "";
	private String factReturnTime = "";
	private String acceptor = "";
	private String memo = "";
	private String confirm = "";
	private String acceptResult = "";
	
	private ArrayList<ListOption> acceptorList = new ArrayList<ListOption>();
	private ArrayList<ListOption> acceptResultList = new ArrayList<ListOption>();
	
	public String getClassName() {
		return this.className;
	}
	
	public String getKeyBackup() {
		
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public B_MouldReturnRegisterData getMouldReturnRegisterData() {
		return this.mouldReturnRegisterData;
	}
	public void setMouldReturnRegisterData(B_MouldReturnRegisterData mouldReturnRegisterData) {
		this.mouldReturnRegisterData = mouldReturnRegisterData;
	}

	public B_MouldLendRegisterData getMouldLendRegisterData() {
		return this.mouldLendRegisterData;
	}
	public void setMouldLendRegisterData(B_MouldLendRegisterData mouldLendRegisterData) {
		this.mouldLendRegisterData = mouldLendRegisterData;
	}
	
	public String getLendId() {
	    return this.lendId;
	}
	public void setLendId(String lendId) {
	    this.lendId = lendId;
	}

	public String getProductModelNo() {
	    return this.productModelNo;
	}
	public void setProductModelNo(String productModelNo) {
	    this.productModelNo = productModelNo;
	}
	
	public String getProductModelName() {
	    return this.productModelName;
	}
	public void setProductModelName(String productModelName) {
	    this.productModelName = productModelName;
	}
	
	public String getMouldFactory() {
	    return this.mouldFactory;
	}
	public void setMouldFactory(String mouldFactory) {
	    this.mouldFactory = mouldFactory;
	}
	
	public String getFactReturnTime() {
	    return this.factReturnTime;
	}
	public void setFactReturnTime(String factReturnTime) {
	    this.factReturnTime = factReturnTime;
	}

	public String getAcceptor() {
	    return this.acceptor;
	}
	public void setAcceptor(String acceptor) {
	    this.acceptor = acceptor;
	}
	
	public String getConfirm() {
	    return this.confirm;
	}
	public void setConfirm(String confirm) {
	    this.confirm = confirm;
	}
	
	public String getMemo() {
	    return this.memo;
	}
	public void setMemo(String memo) {
	    this.memo = memo;
	}
	
	public String getAcceptResult() {
	    return this.acceptResult;
	}
	public void setAcceptResult(String acceptResult) {
	    this.acceptResult = acceptResult;
	}
	
	public ArrayList<ListOption> getAcceptorList() {
		return this.acceptorList;
	}
	public void setAcceptorList(ArrayList<ListOption> acceptorList) {
		this.acceptorList = acceptorList;
	}

	public ArrayList<ListOption> getAcceptResultList() {
		return this.acceptResultList;
	}
	public void setAcceptResultList(ArrayList<ListOption> acceptResultList) {
		this.acceptResultList = acceptResultList;
	}
}
