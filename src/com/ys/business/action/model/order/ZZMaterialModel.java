package com.ys.business.action.model.order;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_ZZMaterialPriceData;
import com.ys.business.db.data.B_ZZRawMaterialData;
import com.ys.util.basequery.common.BaseModel;

public class ZZMaterialModel extends BaseModel {
	
	/**
	 * author:renang
	 * 机构管理
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ListOption> manageRateList = new ArrayList<ListOption>();
	private ArrayList<ListOption> typeList = new ArrayList<ListOption>();
	private B_ZZMaterialPriceData price = new B_ZZMaterialPriceData();
	private B_ZZRawMaterialData rawMaterial = new B_ZZRawMaterialData();
	private B_MaterialData material;
	private List<B_MaterialData> materialLines;
	private List<B_ZZRawMaterialData> rawMaterials;

	
	public List<B_MaterialData> getMaterialLines() {
		return this.materialLines;
	}
	public void setMaterialLines(List<B_MaterialData> materialLines) {
		this.materialLines = materialLines;
	}
	
	public ArrayList<ListOption> getManageRateList() {
		return this.manageRateList;
	}
	public void setManageRateList(ArrayList<ListOption> manageRateList) {
		this.manageRateList = manageRateList;
	}

	public ArrayList<ListOption> getTypeList() {
		return this.typeList;
	}
	public void setTypeList(ArrayList<ListOption> typeList) {
		this.typeList = typeList;
	}
	
	public List<B_ZZRawMaterialData> getRawMaterials() {
		return this.rawMaterials;
	}
	public void setRawMaterials(List<B_ZZRawMaterialData> rawMaterials) {
		this.rawMaterials = rawMaterials;
	}

	public B_ZZMaterialPriceData getPrice() {
		return this.price;
	}
	public void setPrice(B_ZZMaterialPriceData price) {
		this.price = price;
	}	

	public B_ZZRawMaterialData getRawMaterial() {
		return this.rawMaterial;
	}
	public void setRawMaterial(B_ZZRawMaterialData rawMaterial) {
		this.rawMaterial = rawMaterial;
	}	

	public B_MaterialData getMaterial() {
		return this.material;
	}
	public void setMaterial(B_MaterialData material) {
		this.material = material;
	}	
	
}
