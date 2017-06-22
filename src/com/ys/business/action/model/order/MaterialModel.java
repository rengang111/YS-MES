package com.ys.business.action.model.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ys.util.basequery.common.BaseModel;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_PriceSupplierData;

@Repository
public class MaterialModel extends BaseModel {
	private final String className = "com.ys.business.service.material.MaterialService";
	private String recordId = "";
	private String attribute1 = "";
	private String attribute2 = "";
	private String attribute3 = "";
	private String materialname = "";
	private String materialid="";
	private String categoryid="";
	private String parentid="";
	private String categoryname="";
	private String purchaseTypeName="";
	private String unit="";
	private String purchaseType="";
	private String unitname="";
	private String currency="";
	private ArrayList<ListOption> unitList = new ArrayList<ListOption>();
	private ArrayList<ListOption> currencyList = new ArrayList<ListOption>();
	private ArrayList<ListOption> purchaseTypeList = new ArrayList<ListOption>();
	private B_MaterialData material;
	private String[] shareModelList;
	private String shareModel;
	private String subid;
	private String subidDes;
	private ArrayList<ArrayList<String>> subidList;
	private List<B_MaterialData> materialLines;
	private String paymentTerm="";
	private B_PriceSupplierData price;

	public String getClassName() {
		return this.className;
	}
	
	public B_PriceSupplierData getPrice() {
		return this.price;
	}
	public void setPrice(B_PriceSupplierData price) {
		this.price = price;
	}

	public ArrayList<ListOption> getCurrencyList() {
		return this.currencyList;
	}
	public void setCurrencyList(ArrayList<ListOption> currencyList) {
		this.currencyList = currencyList;
	}
		
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}	

	public String getPaymentTerm() {
		return this.paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}	
	public ArrayList<ListOption> getUnitList() {
		return this.unitList;
	}
	public void setUnitList(ArrayList<ListOption> unitList) {
		this.unitList = unitList;
	}
	
	public ArrayList<ListOption> getPurchaseTypeList() {
		return this.purchaseTypeList;
	}
	public void setPurchaseTypeList(ArrayList<ListOption> purchaseTypeList) {
		this.purchaseTypeList = purchaseTypeList;
	}
	
	public String getUnitname() {
		return this.unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	
	public String getPurchaseTypeName() {
		return this.purchaseTypeName;
	}
	public void setPurchaseTypeName(String purchaseTypeName) {
		this.purchaseTypeName = purchaseTypeName;
	}
	
	public String getParentid() {
		return this.parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
		
	public String getCategoryname() {
		return this.categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	public String getCategoryid() {
		return this.categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
		
	public String getUnit() {
		return this.unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public List<B_MaterialData> getMaterialLines() {
		return this.materialLines;
	}
	public void setMaterialLines(List<B_MaterialData> materialLines) {
		this.materialLines = materialLines;
	}	
	
	public ArrayList<ArrayList<String>> getSubidList() {
		return this.subidList;
	}
	public void setSubidList(ArrayList<ArrayList<String>> subidList) {
		this.subidList = subidList;
	}	
	
	public String getSubid() {
		return this.subid;
	}
	public void setSubid(String subid) {
		this.subid = subid;
	}
	public String getPurchaseType() {
		return this.purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	
	public String getSubidDes() {
		return this.subidDes;
	}
	public void setSubidDes(String subidDes) {
		this.subidDes = subidDes;
	}
	
	public String[] getShareModelList() {
		return this.shareModelList;
	}
	public void setShareModelList(String[] shareModelList) {
		this.shareModelList = shareModelList;
	}
	
	public String getShareModel() {
		return this.shareModel;
	}
	public void setShareModel(String shareModel) {
		this.shareModel = shareModel;
	}
	
	
	public String getRecordId() {
		return this.recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public String getMaterialid() {
		return this.materialid;
	}
	public void setMaterialid(String materialid) {
		this.materialid = materialid;
	}
	
	public String getMaterialname() {
		return this.materialname;
	}
	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}
	
	public String getAttribute1() {
		return this.attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}	
	
	public String getAttribute2() {
		return this.attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return this.attribute3;
	}
	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}
	
	public B_MaterialData getMaterial() {
		return this.material;
	}
	public void setMaterial(B_MaterialData material) {
		this.material = material;
	}

}
