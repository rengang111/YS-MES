package com.ys.business.action.model.lateperfection;

import com.ys.util.basequery.common.BaseModel;

import net.sf.json.JSONArray;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_LatePerfectionQuestionData;
import com.ys.business.db.data.B_LatePerfectionRelationFileData;
import com.ys.business.db.data.B_ProcessControlData;
import com.ys.business.db.data.B_ProjectTaskCostData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.system.db.data.S_DICData;

public class LatePerfectionModel extends BaseModel {
	private final String className = "com.ys.business.service.projecttask.ProcessControlService";
	private String keyBackup = "";
	private B_ProjectTaskData projectTaskData = new B_ProjectTaskData();
	private B_LatePerfectionQuestionData questionData = new B_LatePerfectionQuestionData();
	private B_LatePerfectionRelationFileData relationFileData = new B_LatePerfectionRelationFileData();
	private String exceedTime = "";
	private String projectId = "";
	
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

	public B_LatePerfectionQuestionData getQuestionData() {
		return this.questionData;
	}
	public void setQuestionData(B_LatePerfectionQuestionData questionData) {
		this.questionData = questionData;
	}	
	
	public B_LatePerfectionRelationFileData getRelationFileData() {
		return this.relationFileData;
	}
	public void setRelationFileData(B_LatePerfectionRelationFileData relationFileData) {
		this.relationFileData = relationFileData;
	}	
	
	public String getExceedTime() {
		return this.exceedTime;
	}
	public void setExceedTime(String exceedTime) {
		this.exceedTime = exceedTime;
	}
	
	public String getProjectId() {
		return this.projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
