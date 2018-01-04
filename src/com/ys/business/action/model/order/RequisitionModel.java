package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_ProductionTaskData;
import com.ys.business.db.data.B_RequisitionData;
import com.ys.business.db.data.B_RequisitionDetailData;

public class RequisitionModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyBackup = "";
	
	private List<B_RequisitionDetailData> requisitionList;
	private B_RequisitionData requisition;
	private B_RequisitionDetailData reqDetail;
	private B_ProductionTaskData task;
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
		
	public B_RequisitionDetailData getReqDetail() {
		return this.reqDetail;
	}
	public void setReqDetail(B_RequisitionDetailData reqDetail) {
		this.reqDetail = reqDetail;
	}
	
	public B_ProductionTaskData getTask() {
		return this.task;
	}
	public void setTask(B_ProductionTaskData task) {
		this.task = task;
	}
	
	public B_RequisitionData getRequisition() {
		return this.requisition;
	}
	public void setRequisition(B_RequisitionData requisition) {
		this.requisition = requisition;
	}

	public List<B_RequisitionDetailData> getRequisitionList() {
		return this.requisitionList;
	}
	public void setRequisitionList(List<B_RequisitionDetailData> list) {
		this.requisitionList = list;
	}
	
}
