package com.ys.business.action.model.order;

import java.util.List;

import com.ys.business.db.data.B_WorkshopReturnData;
import com.ys.util.basequery.common.BaseModel;

public class WorkshopReturnModel extends BaseModel {
	
	/**
	 * author:renang
	 * 采购方案
	 */
	private static final long serialVersionUID = 1L;

	private B_WorkshopReturnData workshopReturn;
	private List<B_WorkshopReturnData> workshopRetunList;

	
	public List<B_WorkshopReturnData> getWorkshopRetunList() {
		return workshopRetunList;
	}
	public void setWorkshopRetunList(List<B_WorkshopReturnData> workshopRetunList) {
		this.workshopRetunList = workshopRetunList;
	}
	
	public B_WorkshopReturnData getWorkshopReturn() {
		return this.workshopReturn;
	}
	public void setWorkshopReturn(B_WorkshopReturnData workshopReturn) {
		this.workshopReturn = workshopReturn;
	}

	
}
