package com.ys.system.service.common;

import java.util.ArrayList;

import com.ys.system.action.model.common.TreeInfo;
import com.ys.system.action.model.common.TreeInfoAttribute;
import com.ys.system.interceptor.DicInfo;
import com.ys.system.interceptor.MenuInfo;
import com.ys.util.JsonUtil;

public class MakeTreeStyleData {
	public static String convertMenuChainToJson(ArrayList<MenuInfo> menuChain) {
		ArrayList<TreeInfo> TreeInfoList = new ArrayList<TreeInfo>();
		
		for(MenuInfo menuInfo:menuChain) {
			TreeInfo treeInfo = new TreeInfo();
			treeInfo.setId(menuInfo.getMenuId());
			treeInfo.setParentId(menuInfo.getMenuParentId());
			treeInfo.setText(menuInfo.getMenuName());
			treeInfo.setIconCls("{background:url('" + menuInfo.getMenuIcon1() + "') no-repeat center center;}");
			TreeInfoAttribute attr = new TreeInfoAttribute();
			attr.setUrl(menuInfo.getMenuURL());
			attr.setSortNo(menuInfo.getSortNo());
			treeInfo.setAttributes(attr);
			TreeInfoList.add(treeInfo);
		}
	
		return JsonUtil.toJson(TreeInfoList);
	}
	
	public static String convertDeptChainToJson(ArrayList<DicInfo> deptChain) {
		ArrayList<TreeInfo> dicTreeInfoList = new ArrayList<TreeInfo>();
		
		for(DicInfo dicInfo:deptChain) {
			TreeInfo treeInfo = new TreeInfo();
			treeInfo.setId(dicInfo.getId());
			treeInfo.setParentId(dicInfo.getParentId());
			treeInfo.setText(dicInfo.getName());
			treeInfo.setIconCls("{no-repeat center center;}");
			
			TreeInfoAttribute attr = new TreeInfoAttribute();
			attr.setSortNo(dicInfo.getSortNo());
			treeInfo.setAttributes(attr);			
			
			//treeInfo.setIconCls("{no-repeat center center;}");
			dicTreeInfoList.add(treeInfo);
			
		}
	
		return JsonUtil.toJson(dicTreeInfoList);
	}
	
	public static ArrayList<String> getId(String idJson) {
		String id[] = idJson.split("&");
		ArrayList<String> idList = new ArrayList<String>();
		idList.add("");
		idList.add("");
		for(String subId:id) {
			String value = "";
			String[] subIdList = subId.split("=");
			if (subIdList.length > 1) {
				value = subIdList[1];
			}			
			if (subIdList[0].equals("menuid")) {
				idList.remove(1);
				idList.add(value);
			}
			if (subIdList[0].equals("id")) {
				idList.remove(0);
				idList.add(0, value);				
			}
		}
		return idList;
	}  

}
