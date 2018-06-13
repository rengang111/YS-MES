package com.ys.business.action.model.order;

import com.ys.business.db.data.B_InspectionReturnData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;
import com.ys.util.basequery.common.BaseModel;

public class DepotReturnModel extends BaseModel {
	
	/**
	 * author:renang
	 * 仓库退货管理:入库的对冲处理
	 */
	private static final long serialVersionUID = 1L;
	private B_PurchaseStockInData stockin = new B_PurchaseStockInData();
	private B_PurchaseStockInDetailData detail = new B_PurchaseStockInDetailData();
	
	public B_PurchaseStockInData getStockin() {
		return this.stockin;
	}
	public void setStockin(B_PurchaseStockInData stockin) {
		this.stockin = stockin;
	}	

	
	public B_PurchaseStockInDetailData getDetail() {
		return this.detail;
	}
	public void setDetail(B_PurchaseStockInDetailData detail) {
		this.detail = detail;
	}	
}
