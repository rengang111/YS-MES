package com.ys.business.action.model.processcontrol;

import com.ys.util.basequery.common.BaseModel;

import net.sf.json.JSONArray;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_ProcessControlData;
import com.ys.business.db.data.B_ProjectTaskCostData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.system.db.data.S_DICData;

public class ProcessControlModel extends BaseModel {
	private final String className = "com.ys.business.service.processcontrol.ProcessControlService";
	private String keyBackup = "";
	private B_ProjectTaskData projectTaskData = new B_ProjectTaskData();
	private B_ProcessControlData processControlData = new B_ProcessControlData();
	private String exceedTime = "";
	private String type = "";
	private String projectId = "";
	private String lastestExpectDate = "";
	private String folderNames;
	
	public String getClassName() {
		return this.className;
	}
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public B_ProjectTaskData getProjectTaskData() {
		return this.projectTaskData;
	}
	public void setProjectTaskData(B_ProjectTaskData projectTaskData) {
		this.projectTaskData = projectTaskData;
	}

	public B_ProcessControlData getProcessControlData() {
		return this.processControlData;
	}
	public void setProcessControlData(B_ProcessControlData processControlData) {
		this.processControlData = processControlData;
	}	
	
	public String getExceedTime() {
		return this.exceedTime;
	}
	public void setExceedTime(String exceedTime) {
		this.exceedTime = exceedTime;
	}
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getProjectId() {
		return this.projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getLastestExpectDate() {
		return this.lastestExpectDate;
	}
	public void setLastestExpectDate(String lastestExpectDate) {
		this.lastestExpectDate = lastestExpectDate;
	}
	
	public String getFolderNames() {
		return this.folderNames;
	}
	public void setFolderNames(String folderNames) {
		this.folderNames = folderNames;
	}
}
