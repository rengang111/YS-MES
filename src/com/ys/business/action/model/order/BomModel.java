package com.ys.business.action.model.order;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_BaseBomData;
import com.ys.business.db.data.B_BomData;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.util.basequery.common.BaseModel;

public class BomModel extends BaseModel {
	
	/**
	 * author:renang
	 * 机构管理
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ListOption> manageRateList = new ArrayList<ListOption>();
	private ArrayList<ListOption> currencyList = new ArrayList<ListOption>();
	private B_BomDetailData bomDetail = new B_BomDetailData();
	private B_BomData bom = new B_BomData();
	private B_BaseBomData baseBom = new B_BaseBomData();
	private B_BomPlanData bomPlan = new B_BomPlanData();
	private B_BomPlanData bomQuto = new B_BomPlanData();
	private List<B_BomDetailData> bomDetailLines;
	private String accessFlg;
	
	public String getAccessFlg(){
		return this.accessFlg;
	}

	public void setAccessFlg(String accessFlg){
		this.accessFlg = accessFlg;
	}

	public ArrayList<ListOption> getCurrencyList() {
		return this.currencyList;
	}
	public void setCurrencyList(ArrayList<ListOption> currencyList) {
		this.currencyList = currencyList;
	}


	public ArrayList<ListOption> getManageRateList() {
		return this.manageRateList;
	}
	public void setManageRateList(ArrayList<ListOption> manageRateList) {
		this.manageRateList = manageRateList;
	}

	public List<B_BomDetailData> getBomDetailLines() {
		return this.bomDetailLines;
	}
	public void setBomDetailLines(List<B_BomDetailData> bomDetailLines) {
		this.bomDetailLines = bomDetailLines;
	}

	public B_BomDetailData getBomDetail() {
		return this.bomDetail;
	}
	public void setBomDetail(B_BomDetailData bomDetail) {
		this.bomDetail = bomDetail;
	}	

	public B_BaseBomData getBaseBom() {
		return this.baseBom;
	}
	public void setBaseBom(B_BaseBomData baseBom) {
		this.baseBom = baseBom;
	}

	public B_BomData getBom() {
		return this.bom;
	}
	public void setBom(B_BomData bom) {
		this.bom = bom;
	}

	public B_BomPlanData getBomPlan() {
		return this.bomPlan;
	}
	public void setBomPlan(B_BomPlanData bomPlan) {
		this.bomPlan = bomPlan;
	}
	public B_BomPlanData getBomQuto() {
		return this.bomQuto;
	}
	public void setBomQuto(B_BomPlanData bomQuto) {
		this.bomQuto = bomQuto;
	}	
}
