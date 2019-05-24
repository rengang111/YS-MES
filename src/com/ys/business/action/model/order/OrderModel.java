package com.ys.business.action.model.order;

import java.util.ArrayList;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_OrderCancelData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_OrderDivertData;
import com.ys.business.db.data.B_ProductReceiveData;
import com.ys.util.basequery.common.BaseModel;

public class OrderModel extends BaseModel {
	
	/**
	 * author:renang
	 * 机构管理
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ListOption> loadingPortList = new ArrayList<ListOption>();
	private ArrayList<ListOption> deliveryPortList = new ArrayList<ListOption>();
	private ArrayList<ListOption> shippingCaseList = new ArrayList<ListOption>();
	private ArrayList<ListOption> currencyList = new ArrayList<ListOption>();
	private ArrayList<ListOption> ordercompanyList = new ArrayList<ListOption>();
	private ArrayList<ListOption> productClassifyList = new ArrayList<ListOption>();
	private ArrayList<ListOption> teamList;
	private ArrayList<ListOption> orderNatureList;//订单性质
	private B_OrderData order = new B_OrderData();
	private B_OrderDetailData orderDetail = new B_OrderDetailData();
	private B_OrderDetailData orderTransfer = new B_OrderDetailData();
	private B_CustomerData customer = new B_CustomerData();
	private B_OrderCancelData orderCancel = new B_OrderCancelData();
	private B_ProductReceiveData receive = new B_ProductReceiveData();
	private B_OrderDivertData divert = new B_OrderDivertData();
	private List<B_OrderDetailData> orderDetailLines;
	private String shortName;
	private String fullName;
	private int YSMaxId;
	private String YSParentId;
	private String keyBackup = "";
	private String attribute1 = "";
	private String attribute2 = "";
	private String attribute3 = "";
	private String attributeList1 = "";
	private String currency;
	private String rebateRate;//退税率


	public B_OrderDivertData getDivert() {
		return this.divert;
	}
	public void setDivert(B_OrderDivertData divert) {
		this.divert = divert;
	}
	
	public B_ProductReceiveData getRecive() {
		return this.receive;
	}
	public void setRecive(B_ProductReceiveData recive) {
		this.receive = recive;
	}
	
	public String getRebateRate() {
		return this.rebateRate;
	}
	public void setRebateRate(String rebateRate) {
		this.rebateRate = rebateRate;
	}
	
	public ArrayList<ListOption> getOrderNatureList() {
		return this.orderNatureList;
	}
	public void setOrderNatureList(ArrayList<ListOption> orderNatureList) {
		this.orderNatureList = orderNatureList;
	}
	
	public ArrayList<ListOption> getProductClassifyList() {
		return this.productClassifyList;
	}
	public void setProductClassifyList(ArrayList<ListOption> productClassifyList) {
		this.productClassifyList = productClassifyList;
	}
	public ArrayList<ListOption> getCurrencyList() {
		return this.currencyList;
	}
	public void setCurrencyList(ArrayList<ListOption> currencyList) {
		this.currencyList = currencyList;
	}
	
	public ArrayList<ListOption> getOrdercompanyList() {
		return this.ordercompanyList;
	}
	
	public void setOrdercompanyList(ArrayList<ListOption> ordercompanyList) {
		this.ordercompanyList = ordercompanyList;
	}
	public ArrayList<ListOption> getTeamList() {
		return this.teamList;
	}
	public void setTeamList(ArrayList<ListOption> teamList) {
		this.teamList = teamList;
	}
	
	public List<B_OrderDetailData> getOrderDetailLines() {
		return this.orderDetailLines;
	}
	public void setOrderDetailLines(List<B_OrderDetailData> orderDetailLines) {
		this.orderDetailLines = orderDetailLines;
	}

	public String getAttributeList1() {
		return this.attributeList1;
	}
	public void setAttributeList1(String attributeList1) {
		this.attributeList1 = attributeList1;
	}
	
	public String getKeyBackup() {
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	public ArrayList<ListOption> getShippingCaseList() {
		return this.shippingCaseList;
	}
	public void setShippingCaseList(ArrayList<ListOption> shippingCaseList) {
		this.shippingCaseList = shippingCaseList;
	}

	public ArrayList<ListOption> getDeliveryPortList() {
		return this.deliveryPortList;
	}
	public void setDeliveryPortList(ArrayList<ListOption> deliveryPortList) {
		this.deliveryPortList = deliveryPortList;
	}

	public ArrayList<ListOption> getLoadingPortList() {
		return this.loadingPortList;
	}
	public void setLoadingPortList(ArrayList<ListOption> loadingPortList) {
		this.loadingPortList = loadingPortList;
	}

	public B_OrderData getOrder() {
		return this.order;
	}
	public void setOrderData(B_OrderData order) {
		this.order = order;
	}
	
	public B_OrderCancelData getOrderCancel() {
		return this.orderCancel;
	}
	public void setOrderCancel(B_OrderCancelData orderCancel) {
		this.orderCancel = orderCancel;
	}
	public B_OrderDetailData getOrderDetail() {
		return this.orderDetail;
	}
	public void setOrderDetail(B_OrderDetailData orderDetail) {
		this.orderDetail = orderDetail;
	}

	public B_OrderDetailData getOrderTransfer() {
		return this.orderTransfer;
	}
	public void setOrderTransfer(B_OrderDetailData orderTransfer) {
		this.orderTransfer = orderTransfer;
	}
	
	public B_CustomerData getCustomer() {
		return this.customer;
	}
	public void setCustomer(B_CustomerData customer) {
		this.customer = customer;
	}	
	
	public String getShortName() {
		return this.shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public int getYSMaxId() {
		return this.YSMaxId;
	}
	
	public void setYSMaxId(int YSMaxId) {
		this.YSMaxId = YSMaxId;
	}
	public String getYSParentId() {
		return this.YSParentId;
	}
	
	public void setYSParentId(String YSParentId) {
		this.YSParentId = YSParentId;
	}
	public String getAttribute1() {
		return this.attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}	
	
	public String getAttribute2() {
		return this.attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return this.attribute3;
	}
	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
