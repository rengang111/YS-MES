package com.ys.business.action.model.order;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_BaseBomData;
import com.ys.business.db.data.B_BomData;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_OrderExpenseData;
import com.ys.business.db.data.B_OrderExpenseDetailData;
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
	private B_OrderDetailData orderDetail = new B_OrderDetailData();
	private B_BomData bom = new B_BomData();
	private B_BaseBomData baseBom = new B_BaseBomData();
	private B_BomPlanData bomPlan = new B_BomPlanData();
	private B_BomPlanData bomQuto = new B_BomPlanData();
	private List<B_BomDetailData> bomDetailLines;
	private String accessFlg;
	
	private String counter;
	private String counter1;
	private String counter2;
	private String counter3;
	private String counter4;
	private String counter5;
	private List<B_OrderExpenseData> documentaryLines1;
	private List<B_OrderExpenseData> documentaryLines2;
	private List<B_OrderExpenseData> documentaryLines3;
	private List<B_OrderExpenseData> documentaryLines4;
	private B_OrderExpenseDetailData expense;

	
	public void setExpense(B_OrderExpenseDetailData expense) {
		this.expense = expense;
	}
	
	public B_OrderExpenseDetailData getExpense() {
		return this.expense;
	}

	
	public void seOrderDetail(B_OrderDetailData orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	public B_OrderDetailData getOrderDetail() {
		return this.orderDetail;
	}

	public List<B_OrderExpenseData> getDocumentaryLines1()
	{
	  return this.documentaryLines1; }

	public void setDocumentaryLines1(List<B_OrderExpenseData> documentaryLines) {
	  this.documentaryLines1 = documentaryLines; }

	public List<B_OrderExpenseData> getDocumentaryLines2() {
	  return this.documentaryLines2; }

	public void setDocumentaryLines2(List<B_OrderExpenseData> documentaryLines) {
	  this.documentaryLines2 = documentaryLines; }

	public List<B_OrderExpenseData> getDocumentaryLines3() {
	  return this.documentaryLines3; }

	public void setDocumentaryLines3(List<B_OrderExpenseData> documentaryLines) {
	  this.documentaryLines3 = documentaryLines; }

	public List<B_OrderExpenseData> getDocumentaryLines4() {
	  return this.documentaryLines4; }

	public void setDocumentaryLines4(List<B_OrderExpenseData> documentaryLines) {
	  this.documentaryLines4 = documentaryLines;
	}

	public String getAccessFlg() {
	  return this.accessFlg; }

	public void setAccessFlg(String accessFlg) {
	  this.accessFlg = accessFlg; }

	public String getCounter() {
	  return this.counter;
	}

	public void setCounter(String counter) {
	  this.counter = counter;
	}

	public String getCounter1() {
	  return this.counter1;
	}

	public void setCounter1(String counter1) {
	  this.counter1 = counter1;
	}

	public String getCounter2() {
	  return this.counter2;
	}

	public void setCounter2(String counter2) {
	  this.counter2 = counter2; }

	public String getCounter3() {
	  return this.counter3;
	}

	public void setCounter3(String counter3) {
	  this.counter3 = counter3; }

	public String getCounter4() {
	  return this.counter4;
	}

	public void setCounter4(String counter4) {
	  this.counter4 = counter4;
	}

	public String getCounter5() {
		  return this.counter5;
		}

		public void setCounter5(String counter5) {
		  this.counter5 = counter5;
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
