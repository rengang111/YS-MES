package com.ys.system.action.model.login;

import com.ys.system.db.data.S_USERData;
import com.ys.util.basequery.common.BaseModel;

public class LoginModel extends BaseModel {
	private String userId = "";
	private String pwd = "";
	private String verifyCode = "";
	private String loginId = "";
	
	private S_USERData data = new S_USERData();

	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getLoginId() {
		return this.loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getPwd() {
		return this.pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}	

	public String getVerifyCode() {
		return this.verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}	
	
	public void setData(S_USERData data) {
		this.data = data;
	}
	public S_USERData getData() {
		return this.data;
	}	
}
