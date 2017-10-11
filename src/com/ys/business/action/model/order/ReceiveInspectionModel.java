package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_InspectionProcessData;
import com.ys.business.db.data.B_ReceiveInspectionData;
import com.ys.business.db.data.B_ReceiveInspectionDetailData;

public class ReceiveInspectionModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<B_ReceiveInspectionDetailData> inspectList;
	private B_ReceiveInspectionData inspect;
	private B_InspectionProcessData process;
	ArrayList<ListOption> resultList = new ArrayList<ListOption>();


	public ArrayList<ListOption> getResultList() {
		return this.resultList;
	}
	public void setResultList(ArrayList<ListOption> resultList) {
		this.resultList = resultList;
	}
		
	public B_ReceiveInspectionData getInspect() {
		return this.inspect;
	}
	public void setInspect(B_ReceiveInspectionData inspect) {
		this.inspect = inspect;
	}

	public B_InspectionProcessData getProcess() {
		return this.process;
	}
	public void setProcess(B_InspectionProcessData process) {
		this.process = process;
	}
	
	public List<B_ReceiveInspectionDetailData> getInspectList() {
		return this.inspectList;
	}
	public void setInspectList(List<B_ReceiveInspectionDetailData> inspectList) {
		this.inspectList = inspectList;
	}
	
}
