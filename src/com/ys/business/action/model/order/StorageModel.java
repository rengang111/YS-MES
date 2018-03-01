package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_InventoryHistoryData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;

public class StorageModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String oldQuantity;
	private String oldPackagNumber;
	private B_MaterialData material ;
	private B_PurchaseStockInData stock;
	private B_PurchaseStockInDetailData stockDetail;
	private List<B_PurchaseStockInDetailData> stockList;
	private B_InventoryHistoryData invetoryHistory;
	
	public B_InventoryHistoryData getInvetoryHistory() {
		return this.invetoryHistory;
	}
	public void setInvetoryHistory(B_InventoryHistoryData invetoryHistory) {
		this.invetoryHistory = invetoryHistory;
	}	
	
	public B_PurchaseStockInDetailData getStockDetail() {
		return this.stockDetail;
	}
	public void setStockDetail(B_PurchaseStockInDetailData stockDetail) {
		this.stockDetail = stockDetail;
	}
	
	public String getOldPackagNumber(){
		return this.oldPackagNumber;
	}
	
	public void setOldPackagNumber(String number){
		this.oldPackagNumber = number;
	}
	public String getOldQuantity(){
		return this.oldQuantity;
	}
	
	public void setOldQuantity(String quantity){
		this.oldQuantity = quantity;
	}
	public B_MaterialData getMaterial() {
		return this.material;
	}
	public void setMaterial(B_MaterialData material) {
		this.material = material;
	}
	
	public B_PurchaseStockInData getStock() {
		return this.stock;
	}
	public void setStock(B_PurchaseStockInData stock) {
		this.stock = stock;
	}
	
	
	public List<B_PurchaseStockInDetailData> getStockList() {
		return this.stockList;
	}
	public void setStockList(List<B_PurchaseStockInDetailData> stockList) {
		this.stockList = stockList;
	}

}
