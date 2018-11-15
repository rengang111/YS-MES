package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CostBomDetailData;
import com.ys.business.db.data.B_CostBomData;
import com.ys.business.db.data.B_InventoryMonthlyReportData;
import com.ys.business.db.data.B_PaymentData;
import com.ys.business.db.data.B_PaymentDetailData;
import com.ys.business.db.data.B_PaymentHistoryData;

public class FinanceReportModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private B_PaymentData payment;
	private B_PaymentHistoryData history;
	private List<B_PaymentDetailData> paymentList;
	private List<B_PaymentHistoryData> historyList;
	private List<B_InventoryMonthlyReportData> monthlyList;
	private List<B_CostBomDetailData> costBomList;
	private B_CostBomData costBom;
	private ArrayList<ListOption> teamOption = new ArrayList<ListOption>();
	private String jsonData;
	private String team;
	
	public String getTeam() {
		return this.team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	public String getJsonData() {
		return this.jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
		
	public List<B_CostBomDetailData> getCostBomList() {
		return this.costBomList;
	}
	public void setCostBomList(List<B_CostBomDetailData> list) {
		this.costBomList = list;
	}
	

	public B_CostBomData getCostBom() {
		return this.costBom;
	}
	public void setCostBom(B_CostBomData costBom) {
		this.costBom = costBom;
	}
	
	public List<B_InventoryMonthlyReportData> getMonthlyList() {
		return this.monthlyList;
	}
	public void setMonthlyList(List<B_InventoryMonthlyReportData> list) {
		this.monthlyList = list;
	}
	
	public B_PaymentData getPayment() {
		return this.payment;
	}
	public void setPayment(B_PaymentData payment) {
		this.payment = payment;
	}
	public B_PaymentHistoryData getHistory() {
		return this.history;
	}
	public void setHistory(B_PaymentHistoryData history) {
		this.history = history;
	}
	
	public List<B_PaymentDetailData> getPaymentList() {
		return this.paymentList;
	}
	public void setPaymentList(List<B_PaymentDetailData> list) {
		this.paymentList = list;
	}
	public ArrayList<ListOption> getTeamOption() {
		return this.teamOption;
	}
	public void setTeamOption(ArrayList<ListOption> list) {
		this.teamOption = list;
	}	
	
	public List<B_PaymentHistoryData> getHistoryList() {
		return this.historyList;
	}
	public void setHistoryList(List<B_PaymentHistoryData> historyList) {
		this.historyList = historyList;
	}
	

}
