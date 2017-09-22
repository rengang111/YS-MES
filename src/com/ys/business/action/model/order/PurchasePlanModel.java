package com.ys.business.action.model.order;

import java.util.List;

import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchasePlanData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.util.basequery.common.BaseModel;

public class PurchasePlanModel extends BaseModel {
	
	/**
	 * author:renang
	 * 采购方案
	 */
	private static final long serialVersionUID = 1L;
	private B_PurchasePlanData purchasePlan;
	private List<B_PurchasePlanDetailData> planDetailList;
	
	private B_OrderDetailData orderDetail;
	
	
	public B_OrderDetailData getOrderDetail() {
		return this.orderDetail;
	}
	public void setOrderDetail(B_OrderDetailData orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	public B_PurchasePlanData getPurchasePlan() {
		return this.purchasePlan;
	}
	public void setPurchasePlan(B_PurchasePlanData purchasePlan) {
		this.purchasePlan = purchasePlan;
	}
	
	public List<B_PurchasePlanDetailData> getPlanDetailList() {
		return this.planDetailList;
	}
	public void setPlanDetailList(List<B_PurchasePlanDetailData> planDetailList) {
		this.planDetailList = planDetailList;
	}

	
	
}
