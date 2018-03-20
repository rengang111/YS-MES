package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_ArrivalData;

public class StockInApplyModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<B_ArrivalData> applyDetailList;
	private B_ArrivalData stockinApply;

	public B_ArrivalData getStockinApply() {
		return this.stockinApply;
	}
	public void setStockinApply(B_ArrivalData apply) {
		this.stockinApply = apply;
	}
	
	public List<B_ArrivalData> getApplyDetailList() {
		return this.applyDetailList;
	}
	public void setApplyDetailList(List<B_ArrivalData> applyDetailList) {
		this.applyDetailList = applyDetailList;
	}
	
}
