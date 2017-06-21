package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_InventoryData;
import com.ys.business.db.data.B_ReceiveInspectionData;

public class ReceiveInspectionModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private B_InventoryData inventory ;
	private List<B_ReceiveInspectionData> inspectList;
	private B_ReceiveInspectionData inspect;

	public B_InventoryData getInventory() {
		return this.inventory;
	}
	public void setInventory(B_InventoryData inventory) {
		this.inventory = inventory;
	}
	
	public B_ReceiveInspectionData getInspect() {
		return this.inspect;
	}
	public void setInspect(B_ReceiveInspectionData inspect) {
		this.inspect = inspect;
	}

	public List<B_ReceiveInspectionData> getInspectList() {
		return this.inspectList;
	}
	public void setInspectList(List<B_ReceiveInspectionData> inspectList) {
		this.inspectList = inspectList;
	}
	
}
