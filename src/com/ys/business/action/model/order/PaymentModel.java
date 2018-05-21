package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_PaymentData;
import com.ys.business.db.data.B_PaymentDetailData;
import com.ys.business.db.data.B_PaymentHistoryData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_StockOutData;
import com.ys.business.db.data.B_StockOutDetailData;

public class PaymentModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private B_PaymentData payment;
	private B_PaymentHistoryData history;
	private List<B_PaymentDetailData> paymentList;
	private List<B_PaymentHistoryData> historyList;
	private List<B_PurchaseOrderData> contractList;//采购合同
	private ArrayList<ListOption> approvalOption = new ArrayList<ListOption>();
	private ArrayList<ListOption> invoiceTypeOption = new ArrayList<ListOption>();
		
	public List<B_PurchaseOrderData> getContractList() {
		return this.contractList;
	}
	public void setContractList(List<B_PurchaseOrderData> contractList) {
		this.contractList = contractList;
	}
	
	public ArrayList<ListOption> getInvoiceTypeOption() {
		return this.invoiceTypeOption;
	}
	
	public void setInvoiceTypeOption(ArrayList<ListOption> list) {
		this.invoiceTypeOption = list;
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
	public ArrayList<ListOption> getApprovalOption() {
		return this.approvalOption;
	}
	public void setApprovalOption(ArrayList<ListOption> list) {
		this.approvalOption = list;
	}	
	
	public List<B_PaymentHistoryData> getHistoryList() {
		return this.historyList;
	}
	public void setHistoryList(List<B_PaymentHistoryData> historyList) {
		this.historyList = historyList;
	}
	

}
