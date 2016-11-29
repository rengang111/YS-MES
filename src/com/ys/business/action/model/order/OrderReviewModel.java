package com.ys.business.action.model.order;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_OrderReviewData;
import com.ys.util.basequery.common.BaseModel;

public class OrderReviewModel extends BaseModel {
	
	/**
	 * author:renang
	 * 订单评审
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ListOption> currencyList = new ArrayList<ListOption>();
	private B_OrderReviewData review = new B_OrderReviewData();
	private List<B_OrderReviewData> reviewLines;


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
