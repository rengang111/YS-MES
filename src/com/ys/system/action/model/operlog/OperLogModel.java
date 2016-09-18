package com.ys.system.action.model.operlog;

import java.io.Serializable;

import com.ys.system.db.data.S_OPERLOGData;
import com.ys.util.basequery.common.BaseModel;

public class OperLogModel extends BaseModel {
	
	private final static long serialVersionUID = 66661L;
	
	private String unitIdName = "";
	private String unitId = "";
	private String userIdName = "";
	private String ip = "";
	private String menuIdName = "";
	private String startTime = "";
	private String endTime = "";
	
	public String getUnitIdName() {
		return this.unitIdName;
	}
	public void setUnitIdName(String unitIdName) {
		this.unitIdName = unitIdName;
	}
	public String getUnitId() {
		return this.unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUserIdName() {
		return this.userIdName;
	}
	public void setUserIdName(String userIdName) {
		this.userIdName = userIdName;
	}	
	public String getIp() {
		return this.ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMenuIdName() {
		return this.menuIdName;
	}
	public void setMenuIdName(String menuIdName) {
		this.menuIdName = menuIdName;
	}	
	public String getStartTime() {
		return this.startTime;
	}
	public void setStartTime(String startTime) {
		//if (startTime != null && !startTime.equals("")) {
		//	startTime += " 00:00:00";
		//}
		this.startTime = startTime;
	}	
	public String getEndTime() {
		return this.endTime;
	}
	public void setEndTime(String endTime) {
		//if (endTime != null && !endTime.equals("")) {
		//	endTime += " 23:59:59";
		//}
		this.endTime = endTime;
	}

}
