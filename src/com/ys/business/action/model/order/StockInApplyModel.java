package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_StockInApplyData;
import com.ys.business.db.data.B_StockInApplyDetailData;

public class StockInApplyModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<B_StockInApplyDetailData> applyDetailList;
	private B_StockInApplyData stockinApply;

	public B_StockInApplyData getStockinApply() {
		return this.stockinApply;
	}
	public void setStockinApply(B_StockInApplyData apply) {
		this.stockinApply = apply;
	}
	
	public List<B_StockInApplyDetailData> getApplyDetailList() {
		return this.applyDetailList;
	}
	public void setApplyDetailList(List<B_StockInApplyDetailData> applyDetailList) {
		this.applyDetailList = applyDetailList;
	}
	
}
