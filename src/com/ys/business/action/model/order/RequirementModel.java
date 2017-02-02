package com.ys.business.action.model.order;

import java.util.List;

import com.ys.business.db.data.B_MaterialRequirmentData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.util.basequery.common.BaseModel;

public class RequirementModel extends BaseModel {
	
	/**
	 * author:renang
	 * 机构管理
	 */
	private static final long serialVersionUID = 1L;
	private B_OrderDetailData orderDetail = new B_OrderDetailData();
	private B_MaterialRequirmentData requirment = new B_MaterialRequirmentData();
	private List<B_MaterialRequirmentData> requirmentList;

	
	public List<B_MaterialRequirmentData> getRequirmentList() {
		return this.requirmentList;
	}
	public void setRequirmentList(List<B_MaterialRequirmentData> requirmentList) {
		this.requirmentList = requirmentList;
	}
	
	public B_OrderDetailData getOrderDetail() {
		return this.orderDetail;
	}
	public void setOrderDetail(B_OrderDetailData orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	public B_MaterialRequirmentData getRequirment() {
		return this.requirment;
	}
	public void setRequirment(B_MaterialRequirmentData requirment) {
		this.requirment = requirment;
	}	
	
	
}
