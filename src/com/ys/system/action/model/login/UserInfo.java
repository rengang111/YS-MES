package com.ys.system.action.model.login;

import com.ys.util.basequery.common.Constants;
import com.ys.util.basequery.common.BaseModel;

public class UserInfo extends BaseModel {
	public static final String UNLOCKED = "0";
	public static final String LOCKED = "1";
	public static final String SANAME = "SAdmin";
	
	private String userId = "";
	private String loginId = "";
	private String userName = "";
	private String unitId = "";
	private String unitName = "";
	private String userType = "003";
	private String photo = "";
	private String deptGuid = "";

	//S_USERData data = new S_USERData();
	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
		this.photo = this.userId + "_HeadPhoto.jpg";
	}
	
	public String geLoginId() {
		return this.loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}	
	
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}	

	public String getUnitId() {
		return this.unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}	
	
	public String getUnitName() {
		return this.unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}	
	
	public String getUserType() {
		return this.userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}		

	public String getPhoto() {
		return this.photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getDeptGuid() {
		return this.deptGuid;
	}
	public void setDeptGuid(String deptGuid) {
		this.deptGuid = deptGuid;
	}
	/*
	public void setData(S_USERData data) {
		this.data = data;
	}
	public S_USERData getData() {
		return this.data;
	}
	*/
	public boolean isSA() {
		return this.userType.equals(Constants.USER_SA);
	}

}
