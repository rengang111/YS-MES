package com.ys.business.action.model.order;

import java.util.List;

import com.ys.business.db.data.B_PurchasePlanData;
import com.ys.util.basequery.common.BaseModel;

public class PurchasePlanModel extends BaseModel {
	
	/**
	 * author:renang
	 * 采购方案
	 */
	private static final long serialVersionUID = 1L;
	private List<B_PurchasePlanData> detail;
	
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
	
	public List<B_PurchasePlanData> getDetail() {
		return this.detail;
	}
	public void setDetail(List<B_PurchasePlanData> detail) {
		this.detail = detail;
	}

	
	
}
