package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.S_WarehouseCodeData;

public class WarehouseModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<S_WarehouseCodeData> warehouseList;
	private S_WarehouseCodeData warehouse;
	
	
	public S_WarehouseCodeData getWarehouse() {
		return this.warehouse;
	}
	public void setWarehouse(S_WarehouseCodeData warehouse) {
		this.warehouse = warehouse;
	}

	public List<S_WarehouseCodeData> getWarehouseList() {
		return this.warehouseList;
	}
	public void setWarehouseList(List<S_WarehouseCodeData> warehouseList) {
		this.warehouseList = warehouseList;
	}
	
}
