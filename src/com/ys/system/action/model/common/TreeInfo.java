package com.ys.system.action.model.common;

import java.io.Serializable;

public class TreeInfo implements Serializable {
	
	private final static long serialVersionUID = 66661L;
	
	private String id = "";
	private String parentId = "";
	private String text = "";
	private String state = "";
	private String iconCls = "";
	private TreeInfoAttribute attributes = new TreeInfoAttribute();
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getParentId() {
		return this.parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}	
	
	public String getText() {
		return this.text;
	}
	public void setText(String text) {
		this.text = text;
	}	
	
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}	
	
	public String getIconCls() {
		return this.iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public TreeInfoAttribute getAttributes() {
		return this.attributes;
	}
	
	public void setAttributes(TreeInfoAttribute attributes) {
		this.attributes = attributes;
	}

}
