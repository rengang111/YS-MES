package com.ys.system.action.model.common;

import com.ys.util.basequery.common.BaseModel;

public class SelectUserPopupInfo extends BaseModel {
	
	private final static long serialVersionUID = 66661L;
	
	private String userId = "";
	private String userIdName = "";	
	private String type = "";
	private String userControl = "";
	private String userControlView = "";
	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserIdName() {
		return this.userIdName;
	}
	public void setUserIdName(String userIdName) {
		this.userIdName = userIdName;
	}
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUserControl() {
		return this.userControl;
	}
	public void setUserControl(String userControl) {
		this.userControl = userControl;
	}
	
	public String getUserControlView() {
		return this.userControlView;
	}
	public void setUserControlView(String userControlView) {
		this.userControlView = userControlView;
	}	
}
