package com.ys.system.action.model.power;

import com.ys.util.basequery.common.BaseModel;
import com.ys.system.db.data.S_POWERData;

public class PowerModel extends BaseModel {
	private String userIdName = "";	
	private String unitIdName = "";
	private String roleIdName = "";
	private String numCheck = "";
	private String id = "";
	private String userId = "";
	private String roleId = "";
	private String unitId = "";
	private String userName = "";
	private String roleName = "";
	private String unitName = "";
	private String powerType = "";

	private S_POWERData powerData = new S_POWERData();
	
	public String getUserIdName() {
		return this.userIdName;
	}
	public void setUserIdName(String userIdName) {
		this.userIdName = userIdName;
	}	
	
	public String getUnitIdName() {
		return this.unitIdName;
	}
	public void setUnitIdName(String unitIdName) {
		this.unitIdName = unitIdName;
	}
	
	public String getRoleIdName() {
		return this.roleIdName;
	}
	public void setRoleIdName(String roleIdName) {
		this.roleIdName = roleIdName;
	}
	
	public String getNumCheck() {
		return this.numCheck;
	}
	public void setNumCheck(String numCheck) {
		this.numCheck = numCheck;
	}
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}	

	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}		
	
	public String getRoleId() {
		return this.roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUnitId() {
		return this.unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getUserName	() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}		
	
	public String getRoleName() {
		return this.roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUnitName() {
		return this.unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public S_POWERData getpowerData() {
		return this.powerData;
	}
	public void setpowerData(S_POWERData powerData) {
		this.powerData = powerData;
	}
	
	public String getPowerType() {
		return this.powerType;
	}
	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}	
}
