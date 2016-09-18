package com.ys.util.basequery.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseModel implements Serializable {
	
	private final static long serialVersionUID = 66661L;
	
	private String menuId = "";
	private int pageIndex = 0;
	private String flg = "";
	private int recordPerPage = 5;
	private int totalPages = 0;
	private String turnPageHtml = "";
	private ArrayList<ArrayList<String>> viewData;
	private ArrayList<HashMap<String, String>> ysViewData;
	private String queryName = "";
	private String queryFileName = "";
	private String userDefinedWhere = "";
	private String sortFieldList = "";
	private String turnPageFlg = "";
	private int startIndex = 0;
	private String message = "";
	private String isOnlyView = "";
	private int updatedRecordCount = 0;
	private int recordCount = 0;
	private boolean success;
	private String operType = "";
	
	public String getMenuId() {
		return this.menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public int getPageIndex() {
		return this.pageIndex;
	}
	
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}	
	
	public String getFlg() {
		return this.flg;
	}
	
	public void setFlg(String flg) {
		this.flg = flg;
	}	
	
	public int getRecordPerPage() {
		return this.recordPerPage;
	}
	
	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}	
	
	public int getTotalPages() {
		return this.totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public String getTurnPageHtml() {
		return this.turnPageHtml;
	}
	
	public void setTurnPageHtml(String turnPageHtml) {
		this.turnPageHtml = turnPageHtml;
	}
	
	public ArrayList<ArrayList<String>> getViewData() {
		return this.viewData;
	}
	
	public void setViewData(ArrayList<ArrayList<String>> viewData) {
		this.viewData = viewData;
	}
	
	public ArrayList<HashMap<String, String>> getYsViewData() {
		return this.ysViewData;
	}
	
	public void setYsViewData(ArrayList<HashMap<String, String>> ysViewData) {
		this.ysViewData = ysViewData;
	}
	
	
	public String getQueryName() {
		return this.queryName;
	}
	
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	
	public String getQueryFileName() {
		return this.queryFileName;
	}
	
	public void setQueryFileName(String queryFileName) {
		this.queryFileName = queryFileName;
	}	
	
	
	public String getUserDefinedWhere() {
		return this.userDefinedWhere;
	}
	
	public void setUserDefinedWhere(String userDefinedWhere) {
		this.userDefinedWhere = userDefinedWhere;
	}
	
	public String getSortFieldList() {
		return this.sortFieldList;
	}
	
	public void setSortFieldList(String sortFieldList) {
		this.sortFieldList = sortFieldList;
	}

	public String getTurnPageFlg() {
		return this.turnPageFlg;
	}
	
	public void setTurnPageFlg(String turnPageFlg) {
		this.turnPageFlg = turnPageFlg;
	}
	
	public int getStartIndex() {
		return this.startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		if (this.message.equals("")) {
			this.message = message;
		} else {
			this.message += "\\n" + message;
		}
	}
	
	public String getIsOnlyView() {
		return this.isOnlyView;
	}
	public void setIsOnlyView(String isOnlyView) {
		this.isOnlyView = isOnlyView;
	}
	
	public int getUpdatedRecordCount() {
		return this.updatedRecordCount;
	}
	public void setUpdatedRecordCount(int updatedRecordCount) {
		this.updatedRecordCount = updatedRecordCount;
	}

	public int getRecordCount() {
		return this.recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public boolean getSuccess() {
		return this.success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}	
	
	
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
}
