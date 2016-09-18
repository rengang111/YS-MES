package com.ys.util.basedao; 

import javax.persistence.Column;

import org.hibernate.mapping.List;

/**
 * Title: OSJ BSC001
 * 
 * @author Z.X
 * @since JDK1.6 Model
 * @see createdata:2012-3-12
 * @version 1.0
 **/

public class ListOption {
	
	@Column(name = "key",  nullable = false)
	private String key;

	@Column(name = "value", nullable = false)
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
