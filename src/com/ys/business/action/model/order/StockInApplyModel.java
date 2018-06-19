package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;

public class StockInApplyModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<B_ArrivalData> applyDetailList;
	private B_ArrivalData stockinApply;
	
	private List<B_PurchaseStockInDetailData> detailList;
	private B_PurchaseStockInData stockin;

	public B_PurchaseStockInData getStockin() {
		return this.stockin;
	}
	public void setStockin(B_PurchaseStockInData stockin) {
		this.stockin = stockin;
	}
	
	public List<B_PurchaseStockInDetailData> getDetailList() {
		return this.detailList;
	}
	public void setDetailList(List<B_PurchaseStockInDetailData> detailList) {
		this.detailList = detailList;
	}
	
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
