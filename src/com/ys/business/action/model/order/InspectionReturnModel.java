package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_InspectionProcessData;
import com.ys.business.db.data.B_InspectionReturnData;
import com.ys.business.db.data.B_ReceiveInspectionData;
import com.ys.business.db.data.B_ReceiveInspectionDetailData;

public class InspectionReturnModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<B_InspectionReturnData> inspectReturnList;
	private B_InspectionReturnData inspectReturn;
	ArrayList<ListOption> resultList = new ArrayList<ListOption>();


	public ArrayList<ListOption> getResultList() {
		return this.resultList;
	}
	public void setResultList(ArrayList<ListOption> resultList) {
		this.resultList = resultList;
	}
		
	public B_InspectionReturnData getInspectReturn() {
		return this.inspectReturn;
	}
	public void setInspectReturn(B_InspectionReturnData inspect) {
		this.inspectReturn = inspect;
	}
	
	public List<B_InspectionReturnData> getInspectReturnList() {
		return this.inspectReturnList;
	}
	public void setInspectReturnList(List<B_InspectionReturnData> inspectList) {
		this.inspectReturnList = inspectList;
	}
	
}
