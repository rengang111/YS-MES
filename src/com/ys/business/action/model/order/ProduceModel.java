package com.ys.business.action.model.order;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_OrderCancelData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_OrderMergeData;
import com.ys.business.db.data.B_OrderMergeDetailData;
import com.ys.business.db.data.B_ProducePlanData;
import com.ys.business.db.data.B_ProductDesignDetailData;
import com.ys.business.db.data.S_ProduceLineCodeData;
import com.ys.business.db.data.S_ProductionTeamCodeData;
import com.ys.business.db.data.S_WarehouseCodeData;
import com.ys.util.basequery.common.BaseModel;

public class ProduceModel extends BaseModel {
	
	/**
	 * author:renang
	 * 生产任务
	 */
	private static final long serialVersionUID = 1L;

	private List<B_OrderMergeDetailData> mergeList = new ArrayList<B_OrderMergeDetailData>();
	private B_OrderMergeData merge = new B_OrderMergeData();
	private S_ProduceLineCodeData produceLine;
	private B_ProducePlanData plan;
	private S_ProductionTeamCodeData productionTeam;
	private String[] produceList;
	private String employeeSkills;

	public String getEmployeeSkills() {
        return employeeSkills;
    }
    public void setEmployeeSkills(String employeeSkills) {
        this.employeeSkills = employeeSkills;
    }
    
	public String[] getProduceList() {
        return produceList;
    }
    public void setProduceList(String[] produceList) {
        this.produceList = produceList;
    }
    
	public S_ProductionTeamCodeData getProductionTeam() {
		return this.productionTeam;
	}
	public void setProductionTeam(S_ProductionTeamCodeData team) {
		this.productionTeam = team;
	}
	
	public List<B_OrderMergeDetailData> getPackageList() {
		return this.mergeList;
	}
	public void setPackageList(List<B_OrderMergeDetailData> mergeList) {
		this.mergeList = mergeList;
	}
	public B_OrderMergeData getMerge() {
		return this.merge;
	}
	public void setMerge(B_OrderMergeData merge) {
		this.merge = merge;
	}
	
	public S_ProduceLineCodeData getProduceLine() {
		return this.produceLine;
	}
	public void setProduceLine(S_ProduceLineCodeData produceLine) {
		this.produceLine = produceLine;
	}

	public B_ProducePlanData getPlan() {
		return this.plan;
	}
	public void setPlan(B_ProducePlanData plan) {
		this.plan = plan;
	}
	
}
