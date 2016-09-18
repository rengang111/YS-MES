package com.ys.system.action.model.menu;

import java.util.ArrayList;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.db.data.S_MENUData;

public class MenuModel extends BaseModel {
	private String parentMenuIdName = "";
	private String menuId = "";
	private String menuName = "";
	private String numCheck = "";

	private S_MENUData menuData = new S_MENUData();
	private ArrayList<ArrayList<String>> menuTypeList = new ArrayList<ArrayList<String>>();
	
	public String getParentMenuIdName() {
		return this.parentMenuIdName;
	}
	public void setParentMenuIdName(String parentMenuIdName) {
		this.parentMenuIdName = parentMenuIdName;
	}
	
	public String getMenuId() {
		return this.menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}	
	
	public String getMenuName() {
		return this.menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}	
	
	public String getNumCheck() {
		return this.numCheck;
	}
	public void setNumCheck(String numCheck) {
		this.numCheck = numCheck;
	}
	
	public S_MENUData getmenuData() {
		return this.menuData;
	}
	public void setmenuData(S_MENUData menuData) {
		this.menuData = menuData;
	}
	
	public ArrayList<ArrayList<String>> getMenuTypeList() {
		return this.menuTypeList;
	}
	public void setMenuTypeList(ArrayList<ArrayList<String>> menuTypeList) {
		this.menuTypeList = menuTypeList;
	}	

}
