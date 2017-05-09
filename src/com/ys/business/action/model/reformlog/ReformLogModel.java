package com.ys.business.action.model.reformlog;

import com.ys.util.basequery.common.BaseModel;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_LatePerfectionQuestionData;
import com.ys.business.db.data.B_LatePerfectionRelationFileData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_ProcessControlData;
import com.ys.business.db.data.B_ProjectTaskCostData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.business.db.data.B_ReformLogData;
import com.ys.system.db.data.S_DICData;

public class ReformLogModel extends BaseModel {
	private final String className = "com.ys.business.service.reformlog.ProcessControlService";
	private String keyBackup = "";
	private B_ProjectTaskData projectTaskData = new B_ProjectTaskData();
	private B_ReformLogData reformlogData = new B_ReformLogData();
	private String exceedTime = "";
	private String projectId = "";
	private List<B_ReformLogData> detailLines;
	
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

	public B_ReformLogData getReformlogData() {
		return this.reformlogData;
	}
	public void setReformlogData(B_ReformLogData reformlogData) {
		this.reformlogData = reformlogData;
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

	public List<B_ReformLogData> getDetailLines() {
		return this.detailLines;
	}
	public void setDetailLines(List<B_ReformLogData> detailLines) {
		this.detailLines = detailLines;
	}
}
