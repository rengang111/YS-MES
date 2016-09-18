package com.ys.system.action.model.common;

import com.ys.util.basequery.common.BaseModel;

public class SelectRolePopupInfo extends BaseModel {
	
	private final static long serialVersionUID = 66661L;
	
	private String roleId = "";
	private String roleIdName = "";
	private String type = "";
	private String roleControl = "";
	private String roleControlView = "";
	
	public String getRoleId() {
		return this.roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleIdName() {
		return this.roleIdName;
	}
	public void setRoleIdName(String roleIdName) {
		this.roleIdName = roleIdName;
	}
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getRoleControl() {
		return this.roleControl;
	}
	public void setRoleControl(String roleControl) {
		this.roleControl = roleControl;
	}
	
	public String getRoleControlView() {
		return this.roleControlView;
	}
	public void setRoleControlView(String roleControlView) {
		this.roleControlView = roleControlView;
	}	
}
