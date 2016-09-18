package com.ys.system.action.model.role;


import java.util.ArrayList;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.db.data.S_ROLEData;
import com.ys.system.db.data.S_ROLEMENUData;

public class RoleModel  extends BaseModel {
	private String menuIdName = "";
	private String roleIdName = "";
	private String userIdName = "";
	private String numCheck = "";
	private String userName = "";
	private String unitName = "";
	private String roleId = "";
	private String deptGuid = "";

	private S_ROLEData roleData = new S_ROLEData();
	private S_ROLEMENUData roleMenuData = new S_ROLEMENUData();
	private ArrayList<ArrayList<String>> roleTypeList = new ArrayList<ArrayList<String>>();
	
	public String getMenuIdName() {
		return this.menuIdName;
	}
	public void setMenuIdName(String menuIdName) {
		this.menuIdName = menuIdName;
	}
	
	public String getRoleIdName() {
		return this.roleIdName;
	}
	public void setRoleIdName(String roleIdName) {
		this.roleIdName = roleIdName;
	}
	
	public String getUserIdName() {
		return this.userIdName;
	}
	public void setUserIdName(String userIdName) {
		this.userIdName = userIdName;
	}
	
	public String getNumCheck() {
		return this.numCheck;
	}
	public void setNumCheck(String numCheck) {
		this.numCheck = numCheck;
	}	
	
	public S_ROLEData getroleData() {
		return this.roleData;
	}
	public void setroleData(S_ROLEData roleData) {
		this.roleData = roleData;
	}
	
	public S_ROLEMENUData getroleMenuData() {
		return this.roleMenuData;
	}
	public void setroleMenuData(S_ROLEMENUData roleMenuData) {
		this.roleMenuData = roleMenuData;
	}	
	
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUnitName() {
		return this.unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}	
	
	public String getRoleId() {
		return this.roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getDeptGuid() {
		return this.deptGuid;
	}
	public void setDeptGuid(String deptGuid) {
		this.deptGuid = deptGuid;
	}	
	
	public ArrayList<ArrayList<String>> getRoleTypeList() {
		return this.roleTypeList;
	}
	public void setRoleTypeList(ArrayList<ArrayList<String>> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}
}
