package com.ys.business.action.model.order;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.util.basequery.common.BaseModel;

public class BomPlanModel extends BaseModel {
	
	/**
	 * author:renang
	 * 机构管理
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ListOption> manageRateList = new ArrayList<ListOption>();
	private B_BomDetailData bomDetail = new B_BomDetailData();
	private B_BomPlanData bomPlan = new B_BomPlanData();
	private List<B_BomDetailData> bomDetailLines;


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
	
}
