package com.ys.business.action.model.order;

import com.ys.business.db.data.B_InspectionReturnData;
import com.ys.util.basequery.common.BaseModel;

public class DepotReturnModel extends BaseModel {
	
	/**
	 * author:renang
	 * 仓库退货管理
	 */
	private static final long serialVersionUID = 1L;
	private B_InspectionReturnData depotReturn = new B_InspectionReturnData();
	
	public B_InspectionReturnData getDepotReturn() {
		return this.depotReturn;
	}
	public void setDepotReturn(B_InspectionReturnData depotReturn) {
		this.depotReturn = depotReturn;
	}	

}
