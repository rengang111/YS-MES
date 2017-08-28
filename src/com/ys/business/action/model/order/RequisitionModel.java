package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_RequisitionData;

public class RequisitionModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyBackup = "";
	
	private List<B_RequisitionData> requisitionList;
	private B_RequisitionData requisition;
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
		
	public B_RequisitionData getRequisition() {
		return this.requisition;
	}
	public void setRequisition(B_RequisitionData requisition) {
		this.requisition = requisition;
	}

	public List<B_RequisitionData> getRequisitionList() {
		return this.requisitionList;
	}
	public void setRequisitionList(List<B_RequisitionData> list) {
		this.requisitionList = list;
	}
	
}
