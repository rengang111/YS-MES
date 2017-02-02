package com.ys.business.action.model.projecttaskrelationfile;

import com.ys.business.db.data.B_ESRelationFileData;
import com.ys.util.basequery.common.BaseModel;

public class ProjectTaskRelationFileModel extends BaseModel {
	private String keyBackup = "";
	private String esId = "";
	private String type = "";
	private String fileName = "";
	private String path = "";
	private String memo = "";
	private B_ESRelationFileData esRelationFileData = new B_ESRelationFileData();
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public String getEsId() {
		return this.esId;
	}
	public void setEsId(String esId) {
		this.esId = esId;
	}
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getPath() {
		return this.path;
	}
	public void setPath(String path) {
		this.path = path;
	}	
	
	public String getMemo() {
		return this.memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public B_ESRelationFileData getEsRelationFileData() {
		return this.esRelationFileData;
	}
	public void setEsRelationFileData(B_ESRelationFileData esRelationFileData) {
		this.esRelationFileData = esRelationFileData;
	}
	
}
