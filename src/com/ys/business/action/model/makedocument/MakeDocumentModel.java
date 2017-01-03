package com.ys.business.action.model.makedocument;

import com.ys.util.basequery.common.BaseModel;

import net.sf.json.JSONArray;

import java.util.ArrayList;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.processcontrol.ProcessControlModel;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_BaseTechDocData;
import com.ys.business.db.data.B_DocFileFolderData;
import com.ys.business.db.data.B_WorkingFilesData;
import com.ys.business.db.data.B_ProcessControlData;
import com.ys.business.db.data.B_ProjectTaskCostData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.system.db.data.S_DICData;

public class MakeDocumentModel extends ProcessControlModel {
	private final String className = "com.ys.business.service.makedocument.MakeDocumentService";
	private String keyBackup = "";
	private B_ProjectTaskData projectTaskData = new B_ProjectTaskData();
	private B_BaseTechDocData baseTechDocData = new B_BaseTechDocData();
	private B_DocFileFolderData docFileFolderData = new B_DocFileFolderData();
	private B_WorkingFilesData workingFilesData = new B_WorkingFilesData();
	private String exceedTime = "";
	private String projectId = "";
	private String type = "";
	private String folderName = "";
	private String oldFolderName = "";
	private String imageKey = "";
	private String path = "";
	private String nowUseImage = "";
	private ArrayList<String> filenames = new ArrayList<String>();
	
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

	public B_BaseTechDocData getBaseTechDocData() {
		return this.baseTechDocData;
	}
	public void setBaseTechDocData(B_BaseTechDocData baseTechDocData) {
		this.baseTechDocData = baseTechDocData;
	}	
	
	public B_DocFileFolderData getDocFileFolderData() {
		return this.docFileFolderData;
	}
	public void setDocFileFolderData(B_DocFileFolderData docFileFolderData) {
		this.docFileFolderData = docFileFolderData;
	}	
	
	public B_WorkingFilesData getWorkingFilesData() {
		return this.workingFilesData;
	}
	public void setWorkingFilesData(B_WorkingFilesData workingFilesData) {
		this.workingFilesData = workingFilesData;
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

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getFolderName() {
		return this.folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	public String getOldFolderName() {
		return this.oldFolderName;
	}
	public void setOldFolderName(String oldFolderName) {
		this.oldFolderName = oldFolderName;
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
	
	public String getNowUseImage() {
		return this.nowUseImage;
	}
	public void setNowUseImage(String nowUseImage) {
		this.nowUseImage = nowUseImage;
	}
	
	public ArrayList<String> getFilenames() {
		return this.filenames;
	}
	public void setFilenames(ArrayList<String> filenames) {
		this.filenames = filenames;
	}
}
