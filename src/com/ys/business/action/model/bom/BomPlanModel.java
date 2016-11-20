package com.ys.business.action.model.bom;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.util.basequery.common.BaseModel;

public class BomPlanModel extends BaseModel {
	
	/**
	 * author:renang
	 * 机构管理
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ListOption> manageRateList = new ArrayList<ListOption>();
	private B_OrderData order = new B_OrderData();
	private B_OrderDetailData orderDetail = new B_OrderDetailData();
	private B_BomDetailData bomDetail = new B_BomDetailData();
	private B_BomPlanData bomPlan = new B_BomPlanData();
	private List<B_BomDetailData> bomDetailLines;
	private String shortName;
	private String fullName;
	private int YSMaxId;
	private String YSParentId;
	private String attributeList1 = "";


	public ArrayList<ListOption> getManageRateList() {
		return this.manageRateList;
	}
	public void setManageRateList(ArrayList<ListOption> manageRateList) {
		this.manageRateList = manageRateList;
	}

	
	public List<B_BomDetailData> getBomDetailLines() {
		return this.bomDetailLines;
	}
	public void setBomDetailLines(List<B_BomDetailData> bomDetailLines) {
		this.bomDetailLines = bomDetailLines;
	}

	public String getAttributeList1() {
		return this.attributeList1;
	}
	public void setAttributeList1(String attributeList1) {
		this.attributeList1 = attributeList1;
	}
	
	public B_OrderData getOrder() {
		return this.order;
	}
	public void setOrderDetail(B_OrderData order) {
		this.order = order;
	}
	
	public B_OrderDetailData getOrderDetail() {
		return this.orderDetail;
	}
	
	public void setOrderDetail(B_OrderDetailData orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	public B_BomDetailData getBomDetail() {
		return this.bomDetail;
	}
	public void setBomDetail(B_BomDetailData bomDetail) {
		this.bomDetail = bomDetail;
	}	

	public B_BomPlanData getBomPlan() {
		return this.bomPlan;
	}
	public void setBomPlan(B_BomPlanData bomPlan) {
		this.bomPlan = bomPlan;
	}
	
	public String getShortName() {
		return this.shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public int getYSMaxId() {
		return this.YSMaxId;
	}
	
	public void setYSMaxId(int YSMaxId) {
		this.YSMaxId = YSMaxId;
	}
	public String getYSParentId() {
		return this.YSParentId;
	}
	
	public void setYSParentId(String YSParentId) {
		this.YSParentId = YSParentId;
	}
	
	
}
