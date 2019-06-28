package com.ys.business.action.model.order;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_OrderReviewData;
import com.ys.business.db.data.B_PurchaseOrderDeliveryDateHistoryData;
import com.ys.util.basequery.common.BaseModel;

public class OrderTrackModel extends BaseModel {
	
	/**
	 * author:renang
	 * 订单跟踪
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ListOption> currencyList = new ArrayList<ListOption>();
	private B_OrderReviewData review = new B_OrderReviewData();
	private B_PurchaseOrderDeliveryDateHistoryData deliveryDate = new B_PurchaseOrderDeliveryDateHistoryData();
	private List<B_OrderReviewData> reviewLines;

	public B_PurchaseOrderDeliveryDateHistoryData getDeliveryDate() {
		return this.deliveryDate;
	}
	public void setDeliveryDate(B_PurchaseOrderDeliveryDateHistoryData deliveryDate) {
		this.deliveryDate = deliveryDate;
	}	
	

	public ArrayList<ListOption> getCurrencyList() {
		return this.currencyList;
	}
	public void setCurrencyList(ArrayList<ListOption> currencyList) {
		this.currencyList = currencyList;
	}

	
	public List<B_OrderReviewData> getReviewLines() {
		return this.reviewLines;
	}
	public void setReviewLines(List<B_OrderReviewData> reviewLines) {
		this.reviewLines = reviewLines;
	}

	public B_OrderReviewData getReview() {
		return this.review;
	}
	public void setReview(B_OrderReviewData review) {
		this.review = review;
	}	
	
}
