package com.ys.system.action.model.user;

import java.util.ArrayList;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.db.data.S_USERData;

public class UserModel extends BaseModel {
	private String userIdName = "";	
	private String unitIdName = "";
	private String dutiesIdName = "";
	private String numCheck = "";
	private String userId = "";
	private String unitId = "";
	private String unitName = "";
	private String address = "";
	private String photo = "";
	private String photoChangeFlg = "";
	private String dispMode = "";
	private String nowPwd = "";
	private String wantPwd = "";
	
	private ArrayList<ArrayList<String>> dutyList = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> sexList = new ArrayList<ArrayList<String>>();

	private S_USERData userData = new S_USERData();
	
	public String getUnitIdName() {
		return this.unitIdName;
	}
	public void setUnitIdName(String unitIdName) {
		this.unitIdName = unitIdName;
	}
	
	public String getUserIdName() {
		return this.userIdName;
	}
	public void setUserIdName(String userIdName) {
		this.userIdName = userIdName;
	}	
	
	public String getDutiesIdName() {
		return this.dutiesIdName;
	}
	public void setDutiesIdName(String dutiesIdName) {
		this.dutiesIdName = dutiesIdName;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	
	public String getNumCheck() {
		return this.numCheck;
	}
	public void setNumCheck(String numCheck) {
		this.numCheck = numCheck;
	}	
	
	public S_USERData getuserData() {
		return this.userData;
	}
	public void setuserData(S_USERData userData) {
		this.userData = userData;
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
	
	public ArrayList<ArrayList<String>> getDutyList() {
		return this.dutyList;
	}
	public void setDutyList(ArrayList<ArrayList<String>> dutyList) {
		this.dutyList = dutyList;
	}
	
	public ArrayList<ArrayList<String>> getSexList() {
		return this.sexList;
	}
	public void setSexList(ArrayList<ArrayList<String>> sexList) {
		this.sexList = sexList;
	}	
	
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoto() {
		return this.photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getPhotoChangeFlg() {
		return this.photoChangeFlg;
	}
	public void setPhotoChangeFlg(String photoChangeFlg) {
		this.photoChangeFlg = photoChangeFlg;
	}
	
	public String getDispMode() {
		return this.dispMode;
	}
	public void setDispMode(String dispMode) {
		this.dispMode = dispMode;
	}	
	
	public String getNowPwd() {
		return this.nowPwd;
	}
	public void setNowPwd(String nowPwd) {
		this.nowPwd = nowPwd;
	}
	
	public String getWantPwd() {
		return this.wantPwd;
	}
	public void setWantPwd(String wantPwd) {
		this.wantPwd = wantPwd;
	}
}
