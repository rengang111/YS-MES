package com.ys.system.action.model.dic;

import com.ys.util.basequery.common.BaseModel;
import com.ys.system.db.data.S_DICData;

public class DicModel extends BaseModel {
	private String dicTypeIdName = "";
	private String dicIdName = "";
	private String dicId = "";
	private String dicTypeName = "";
	private String dicParentName = "";
	private String numCheck = "";
	private S_DICData dicData = new S_DICData();
	
	public String getDicTypeIdName() {
		return this.dicTypeIdName;
	}
	public void setDicTypeIdName(String dicTypeIdName) {
		this.dicTypeIdName = dicTypeIdName;
	}
	
	public String getDicIdName() {
		return this.dicIdName;
	}
	public void setDicIdName(String dicIdName) {
		this.dicIdName = dicIdName;
	}
	
	public String getDicId() {
		return this.dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}	
	
	public String getDicTypeName() {
		return this.dicTypeName;
	}
	public void setDicTypeName(String dicTypeName) {
		this.dicTypeName = dicTypeName;
	}
	
	public String getDicParentName() {
		return this.dicParentName;
	}
	public void setDicParentName(String dicParentName) {
		this.dicParentName = dicParentName;
	}
	
	public String getNumCheck() {
		return this.numCheck;
	}
	public void setNumCheck(String numCheck) {
		this.numCheck = numCheck;
	}
	public S_DICData getdicData() {
		return this.dicData;
	}
	public void setdicData(S_DICData dicData) {
		this.dicData = dicData;
	}
	
}
