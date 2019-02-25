package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_PaymentData;
import com.ys.business.db.data.B_PaymentDetailData;
import com.ys.business.db.data.B_PaymentHistoryData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_ReceivableData;
import com.ys.business.db.data.B_ReceivableDetailData;
import com.ys.business.db.data.B_ReceivableOrderData;
import com.ys.business.db.data.B_StockOutData;
import com.ys.business.db.data.B_StockOutDetailData;

public class ReceivableModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private B_ReceivableData receivable;
	private B_ReceivableDetailData receivableDetail;
	private List<B_ReceivableDetailData> receivableList;
	private List<B_ReceivableOrderData> orderList;
	private ArrayList<ListOption> approvalOption = new ArrayList<ListOption>();
	private ArrayList<ListOption> invoiceTypeOption = new ArrayList<ListOption>();
		
	public List<B_ReceivableOrderData> getOrderList() {
		return this.orderList;
	}
	public void setOrderList(List<B_ReceivableOrderData> list) {
		this.orderList = list;
	}
	
	public ArrayList<ListOption> getInvoiceTypeOption() {
		return this.invoiceTypeOption;
	}
	
	public void setInvoiceTypeOption(ArrayList<ListOption> list) {
		this.invoiceTypeOption = list;
	}	
	
	public B_ReceivableDetailData getReceivableDetail() {
		return this.receivableDetail;
	}
	public void setReceivableDetail(B_ReceivableDetailData receivable) {
		this.receivableDetail = receivable;
	}

	
	public B_ReceivableData getReceivable() {
		return this.receivable;
	}
	public void setReceivable(B_ReceivableData receivable) {
		this.receivable = receivable;
	}

	
	public List<B_ReceivableDetailData> getReceivableList() {
		return this.receivableList;
	}
	public void setReceivableList(List<B_ReceivableDetailData> list) {
		this.receivableList = list;
	}
	public ArrayList<ListOption> getApprovalOption() {
		return this.approvalOption;
	}
	public void setApprovalOption(ArrayList<ListOption> list) {
		this.approvalOption = list;
	}

}
