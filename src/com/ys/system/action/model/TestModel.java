package com.ys.system.action.model;

import com.ys.util.basequery.common.BaseModel;

public class TestModel extends BaseModel {
	private TestModelSub tSub;
	private String formDisp;
	
	public TestModelSub gettSub() {
		return this.tSub;
	}
	
	public void settSub(TestModelSub tSub) {
		this.tSub = tSub;
	}
	
	public String getFormDisp() {
		return this.formDisp;
	}
	
	public void setFormDisp(String formDisp) {
		this.formDisp = formDisp;
	}
}
