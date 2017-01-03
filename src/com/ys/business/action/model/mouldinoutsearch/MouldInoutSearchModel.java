package com.ys.business.action.model.mouldinoutsearch;

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

public class MouldInoutSearchModel extends BaseModel {
	private final String className = "com.ys.business.service.mouldinoutsearch.MouldInoutSearchService";
	private String keyBackup = "";

	private String productModelNo = "";
	private String productModelName = "";
	
	public String getClassName() {
		return this.className;
	}
	
	public String getKeyBackup() {
		
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
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
	


}
