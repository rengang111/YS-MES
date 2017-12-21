package com.ys.business.action.model.order;

import com.ys.util.basequery.common.BaseModel;

import java.util.List;

import com.ys.business.db.data.B_PaymentData;
import com.ys.business.db.data.B_PaymentDetailData;
import com.ys.business.db.data.B_StockOutData;
import com.ys.business.db.data.B_StockOutDetailData;

public class PaymentModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private B_PaymentData payment;
	private List<B_PaymentDetailData> paymentList;
		
	public B_PaymentData getPayment() {
		return this.payment;
	}
	public void setPayment(B_PaymentData payment) {
		this.payment = payment;
	}
	
	public List<B_PaymentDetailData> getPaymentList() {
		return this.paymentList;
	}
	public void setPaymentList(List<B_PaymentDetailData> list) {
		this.paymentList = list;
	}
	

}
