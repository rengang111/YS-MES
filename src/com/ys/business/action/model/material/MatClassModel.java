package com.ys.business.action.model.material;

import java.util.ArrayList;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.db.data.S_DEPTData;

public class MatClassModel extends BaseModel {
	private String parentUnitId = "";
	private String parentUnitName = "";
	private String unitIdName = "";
	private String userUnitId = "";
	private String numCheck = "";
	private String address = "";
	private S_DEPTData unitData = new S_DEPTData();
	private ArrayList<ArrayList<String>> unitPropertyList = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> unitTypeList = new ArrayList<ArrayList<String>>();
	
	public String getParentUnitId() {
		return this.parentUnitId;
	}
	public void setParentUnitId(String parentUnitId) {
		this.parentUnitId = parentUnitId;
	}
	
	public String getParentUnitName() {
		return this.parentUnitName;
	}
	public void setParentUnitName(String parentUnitName) {
		this.parentUnitName = parentUnitName;
	}
	
	public String getUnitIdName() {
		return this.unitIdName;
	}
	public void setUnitIdName(String unitIdName) {
		this.unitIdName = unitIdName;
	}	
	
	public String getUserUnitId() {
		return this.userUnitId;
	}
	public void setUserUnitId(String userUnitId) {
		this.userUnitId = userUnitId;
	}
	
	public String getNumCheck() {
		return this.numCheck;
	}
	public void setNumCheck(String numCheck) {
		this.numCheck = numCheck;
	}	
	
	public S_DEPTData getunitData() {
		return this.unitData;
	}
	public void setunitData(S_DEPTData unitData) {
		this.unitData = unitData;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<ArrayList<String>> getUnitPropertyList() {
		return this.unitPropertyList;
	}
	
	public void setUnitPropertyList(ArrayList<ArrayList<String>> unitPropertyList) {
		this.unitPropertyList = unitPropertyList;
	}
	
	public ArrayList<ArrayList<String>> getUnitTypeList() {
		return this.unitTypeList;
	}
	
	public void setUnitTypeList(ArrayList<ArrayList<String>> unitTypeList) {
		this.unitTypeList = unitTypeList;
	}	
}
