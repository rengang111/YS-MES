package com.ys.business.action.model.order;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.db.data.B_ProductDesignData;
import com.ys.business.db.data.B_ProductDesignDetailData;
import com.ys.util.basequery.common.BaseModel;

public class ProductDesignModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<B_ProductDesignDetailData> machineConfigList = new ArrayList<B_ProductDesignDetailData>();
	private List<B_ProductDesignDetailData> plasticList = new ArrayList<B_ProductDesignDetailData>();
	private List<B_ProductDesignDetailData> accessoryList = new ArrayList<B_ProductDesignDetailData>();
	private List<B_ProductDesignDetailData> labelList = new ArrayList<B_ProductDesignDetailData>();
	private List<B_ProductDesignDetailData> textPrintList = new ArrayList<B_ProductDesignDetailData>();
	private List<B_ProductDesignDetailData> packageList = new ArrayList<B_ProductDesignDetailData>();
	private B_ProductDesignData productDesign;
	
	public List<B_ProductDesignDetailData> getPackageList() {
		return this.packageList;
	}
	public void setPackageList(List<B_ProductDesignDetailData> packageList) {
		this.packageList = packageList;
	}
	public List<B_ProductDesignDetailData> getPlasticList() {
		return this.plasticList;
	}
	public void setPlasticList(List<B_ProductDesignDetailData> plasticList) {
		this.plasticList = plasticList;
	}
	
	public List<B_ProductDesignDetailData> getAccessoryList() {
		return this.accessoryList;
	}
	public void setAccessoryList(List<B_ProductDesignDetailData> accessoryList) {
		this.accessoryList = accessoryList;
	}
	
	public List<B_ProductDesignDetailData> getTextPrintList() {
		return this.textPrintList;
	}
	public void setTextPrintList(List<B_ProductDesignDetailData> textPrintList) {
		this.textPrintList = textPrintList;
	}
	

	public List<B_ProductDesignDetailData> getLabelList() {
		return this.labelList;
	}
	public void setLabelList(List<B_ProductDesignDetailData> labelList) {
		this.labelList = labelList;
	}
	
	public B_ProductDesignData getProductDesign() {
		return this.productDesign;
	}
	public void setProductDesign(B_ProductDesignData productDesign) {
		this.productDesign = productDesign;
	}

	public List<B_ProductDesignDetailData> getMachineConfigList() {
		return this.machineConfigList;
	}
	public void setMachineConfigList(List<B_ProductDesignDetailData> machineConfigList) {
		this.machineConfigList = machineConfigList;
	}
	
}
