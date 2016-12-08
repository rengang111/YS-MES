package com.ys.business.action.model.order;

import java.util.List;

import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.util.basequery.common.BaseModel;

public class PurchaseOrderModel extends BaseModel {
	
	/**
	 * author:renang
	 * 采购方案
	 */
	private static final long serialVersionUID = 1L;
	private List<B_PurchaseOrderData> detailList;
	private B_PurchaseOrderData contract;
	
	private String YSId;
	private String bomId;

	public String getBomId() {
		return this.bomId;
	}
	public void BomId(String bomId) {
		this.bomId = bomId;
	}
	public String getYSId() {
		return this.YSId;
	}
	public void setYSId(String YSId) {
		this.YSId = YSId;
	}
	
	public List<B_PurchaseOrderData> getDetailList() {
		return this.detailList;
	}
	public void setDetailList(List<B_PurchaseOrderData> detailList) {
		this.detailList = detailList;
	}
	
	public B_PurchaseOrderData getContract() {
		return this.contract;
	}
	public void setContract(B_PurchaseOrderData contract) {
		this.contract = contract;
	}

	
	
}
