package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_StockOutData;
import com.ys.business.db.data.B_StockOutDetailData;

public class StockOutModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private B_StockOutData stockout;
	private List<B_StockOutDetailData> stockList;
		
	public B_StockOutData getStockout() {
		return this.stockout;
	}
	public void setStockout(B_StockOutData stock) {
		this.stockout = stock;
	}
	
	public List<B_StockOutDetailData> getStockList() {
		return this.stockList;
	}
	public void setStockList(List<B_StockOutDetailData> list) {
		this.stockList = list;
	}
	

}
