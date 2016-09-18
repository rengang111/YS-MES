package com.ys.system.action.model.dic;

import java.util.ArrayList;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.db.data.S_DICTYPEData;

public class DicTypeModel extends BaseModel {

	private String dicTypeIdName = "";
	private String dicTypeId = "";
	private String numCheck = "";

	private S_DICTYPEData dicTypeData = new S_DICTYPEData();
	private ArrayList<ArrayList<String>> dicTypeLevelList = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> dicSelectedFlagList = new ArrayList<ArrayList<String>>();
	
	public String getDicTypeIdName() {
		return this.dicTypeIdName;
	}
	public void setDicTypeIdName(String dicTypeIdName) {
		this.dicTypeIdName = dicTypeIdName;
	}
	
	public String getDicTypeId() {
		return this.dicTypeId;
	}
	public void setDicTypeId(String dicTypeId) {
		this.dicTypeId = dicTypeId;
	}	
	
	public String getNumCheck() {
		return this.numCheck;
	}
	public void setNumCheck(String numCheck) {
		this.numCheck = numCheck;
	}
	
	public S_DICTYPEData getdicTypeData() {
		return this.dicTypeData;
	}
	public void setdicTypeData(S_DICTYPEData dicTypeData) {
		this.dicTypeData = dicTypeData;
	}
	
	public ArrayList<ArrayList<String>> getDicTypeLevelList() {
		return this.dicTypeLevelList;
	}
	public void setDicTypeLevelList(ArrayList<ArrayList<String>> dicTypeLevelList) {
		this.dicTypeLevelList = dicTypeLevelList;
	}
	
	public ArrayList<ArrayList<String>> getDicSelectedFlagList() {
		return this.dicSelectedFlagList;
	}
	public void setDicSelectedFlagList(ArrayList<ArrayList<String>> dicSelectedFlagList) {
		this.dicSelectedFlagList = dicSelectedFlagList;
	}
	
}
