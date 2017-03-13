package com.ys.business.action.model.order;

import java.util.List;

import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_MaterialRequirmentData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchasePlanData;
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
	private List<B_PurchasePlanData> purchaseList;
	private B_PurchasePlanData purchase;
	private B_BomPlanData bomPlan;
	private List<B_BomDetailData> bomDetailList;


	public B_BomPlanData getBomPlan() {
		return this.bomPlan;
	}
	public void setBomPlan(B_BomPlanData bomPlan) {
		this.bomPlan = bomPlan;
	}

	public List<B_BomDetailData> getBomDetailList() {
		return this.bomDetailList;
	}
	public void setBomDetailList(List<B_BomDetailData> bomDetailList) {
		this.bomDetailList = bomDetailList;
	}
	
	public B_PurchasePlanData getPurchase() {
		return this.purchase;
	}
	public void setPurchase(B_PurchasePlanData purchase) {
		this.purchase = purchase;
	}

	public List<B_PurchasePlanData> getPurchaseList() {
		return this.purchaseList;
	}
	public void setPurchaseList(List<B_PurchasePlanData> purchaseList) {
		this.purchaseList = purchaseList;
	}
	
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
