package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import com.ys.business.db.data.B_StockOutDetailData;

public class StockOutModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private B_StockOutDetailData stockout;
		
	public B_StockOutDetailData getStockOut() {
		return this.stockout;
	}
	public void setStockOut(B_StockOutDetailData stock) {
		this.stockout = stock;
	}
	

}
