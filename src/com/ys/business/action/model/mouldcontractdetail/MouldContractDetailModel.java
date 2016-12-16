package com.ys.business.action.model.mouldcontractdetail;

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

public class MouldContractDetailModel extends BaseModel {
	private final String className = "com.ys.business.service.mouldcontractdetail.MouldContractDetailService";
	private String keyBackup = "";
	private ArrayList<ListOption> tabNameList = new ArrayList<ListOption>(); 
	private JSONArray jsonObject = new JSONArray();
	
	public String getClassName() {
		return this.className;
	}
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public ArrayList<ListOption> getTabNameList() {	
		return this.tabNameList;
	}
	public void setTabNameList(ArrayList<ListOption> tabNameList) {
		this.tabNameList = tabNameList;
	}
	
	public JSONArray getJsonObject() {
		this.jsonObject = JSONArray.fromObject(this.tabNameList);
		return this.jsonObject;
	}
	public void setJsonObject(JSONArray jsonObject) {
		this.jsonObject = jsonObject;
	}	
	
}
