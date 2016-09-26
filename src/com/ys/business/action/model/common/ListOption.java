package com.ys.business.action.model.common;

public class ListOption {
	
	private String key;

	private String value;
	
	public ListOption(String key,String value){
		this.key = key;
		this.value = value;		
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
}
