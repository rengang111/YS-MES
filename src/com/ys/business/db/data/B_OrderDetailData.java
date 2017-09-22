package com.ys.business.db.data;

import java.sql.*;
import java.io.InputStream;

/**
* <p>Title: </p>
* <p>Description: </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_OrderDetailData implements java.io.Serializable
{

	public B_OrderDetailData()
	{
	}

	/**
	*
	*/
	private String recordid;
	public String getRecordid()
	{
		return this.recordid;
	}
	public void setRecordid(String recordid)
	{
		this.recordid=recordid;
	}

	/**
	*
	*/
	private String ysid;
	public String getYsid()
	{
		return this.ysid;
	}
	public void setYsid(String ysid)
	{
		this.ysid=ysid;
	}

	/**
	*
	*/
	private String piid;
	public String getPiid()
	{
		return this.piid;
	}
	public void setPiid(String piid)
	{
		this.piid=piid;
	}

	/**
	*
	*/
	private String parentid;
	public String getParentid()
	{
		return this.parentid;
	}
	public void setParentid(String parentid)
	{
		this.parentid=parentid;
	}

	/**
	*
	*/
	private String subid;
	public String getSubid()
	{
		return this.subid;
	}
	public void setSubid(String subid)
	{
		this.subid=subid;
	}

	/**
	*
	*/
	private String materialid;
	public String getMaterialid()
	{
		return this.materialid;
	}
	public void setMaterialid(String materialid)
	{
		this.materialid=materialid;
	}

	/**
	*
	*/
	private String quantity;
	public String getQuantity()
	{
		return this.quantity;
	}
	public void setQuantity(String quantity)
	{
		this.quantity=quantity;
	}

	/**
	*
	*/
	private String totalquantity;
	public String getTotalquantity()
	{
		return this.totalquantity;
	}
	public void setTotalquantity(String totalquantity)
	{
		this.totalquantity=totalquantity;
	}

	/**
	*
	*/
	private String returnquantity;
	public String getReturnquantity()
	{
		return this.returnquantity;
	}
	public void setReturnquantity(String returnquantity)
	{
		this.returnquantity=returnquantity;
	}

	/**
	*
	*/
	private String price;
	public String getPrice()
	{
		return this.price;
	}
	public void setPrice(String price)
	{
		this.price=price;
	}

	/**
	*
	*/
	private String totalprice;
	public String getTotalprice()
	{
		return this.totalprice;
	}
	public void setTotalprice(String totalprice)
	{
		this.totalprice=totalprice;
	}

	/**
	*
	*/
	private String currency;
	public String getCurrency()
	{
		return this.currency;
	}
	public void setCurrency(String currency)
	{
		this.currency=currency;
	}

	/**
	*
	*/
	private String exchangerate;
	public String getExchangerate()
	{
		return this.exchangerate;
	}
	public void setExchangerate(String exchangerate)
	{
		this.exchangerate=exchangerate;
	}

	/**
	*
	*/
	private String exchangeprice;
	public String getExchangeprice()
	{
		return this.exchangeprice;
	}
	public void setExchangeprice(String exchangeprice)
	{
		this.exchangeprice=exchangeprice;
	}

	/**
	*
	*/
	private String rmbprice;
	public String getRmbprice()
	{
		return this.rmbprice;
	}
	public void setRmbprice(String rmbprice)
	{
		this.rmbprice=rmbprice;
	}

	/**
	*
	*/
	private String salestax;
	public String getSalestax()
	{
		return this.salestax;
	}
	public void setSalestax(String salestax)
	{
		this.salestax=salestax;
	}

	/**
	*
	*/
	private String rebate;
	public String getRebate()
	{
		return this.rebate;
	}
	public void setRebate(String rebate)
	{
		this.rebate=rebate;
	}

	/**
	*
	*/
	private String rebaterate;
	public String getRebaterate()
	{
		return this.rebaterate;
	}
	public void setRebaterate(String rebaterate)
	{
		this.rebaterate=rebaterate;
	}

	/**
	*
	*/
	private String profitrate;
	public String getProfitrate()
	{
		return this.profitrate;
	}
	public void setProfitrate(String profitrate)
	{
		this.profitrate=profitrate;
	}

	/**
	*
	*/
	private String profit;
	public String getProfit()
	{
		return this.profit;
	}
	public void setProfit(String profit)
	{
		this.profit=profit;
	}

	/**
	*
	*/
	private String salesprofit;
	public String getSalesprofit()
	{
		return this.salesprofit;
	}
	public void setSalesprofit(String salesprofit)
	{
		this.salesprofit=salesprofit;
	}

	/**
	*
	*/
	private String adjustprofit;
	public String getAdjustprofit()
	{
		return this.adjustprofit;
	}
	public void setAdjustprofit(String adjustprofit)
	{
		this.adjustprofit=adjustprofit;
	}

	/**
	*
	*/
	private String totalsalesprofit;
	public String getTotalsalesprofit()
	{
		return this.totalsalesprofit;
	}
	public void setTotalsalesprofit(String totalsalesprofit)
	{
		this.totalsalesprofit=totalsalesprofit;
	}

	/**
	*
	*/
	private String totaladjustprofit;
	public String getTotaladjustprofit()
	{
		return this.totaladjustprofit;
	}
	public void setTotaladjustprofit(String totaladjustprofit)
	{
		this.totaladjustprofit=totaladjustprofit;
	}

	/**
	*
	*/
	private String ordercost;
	public String getOrdercost()
	{
		return this.ordercost;
	}
	public void setOrdercost(String ordercost)
	{
		this.ordercost=ordercost;
	}

	/**
	*
	*/
	private String netprofit;
	public String getNetprofit()
	{
		return this.netprofit;
	}
	public void setNetprofit(String netprofit)
	{
		this.netprofit=netprofit;
	}

	/**
	*
	*/
	private String productcost;
	public String getProductcost()
	{
		return this.productcost;
	}
	public void setProductcost(String productcost)
	{
		this.productcost=productcost;
	}

	/**
	*
	*/
	private String storagedate;
	public String getStoragedate()
	{
		return this.storagedate;
	}
	public void setStoragedate(String storagedate)
	{
		this.storagedate=storagedate;
	}

	/**
	*
	*/
	private String status;
	public String getStatus()
	{
		return this.status;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}

	/**
	*
	*/
	private String productclassify;
	public String getProductclassify()
	{
		return this.productclassify;
	}
	public void setProductclassify(String productclassify)
	{
		this.productclassify=productclassify;
	}

	/**
	*
	*/
	private String ordertype;
	public String getOrdertype()
	{
		return this.ordertype;
	}
	public void setOrdertype(String ordertype)
	{
		this.ordertype=ordertype;
	}

	/**
	*
	*/
	private String payment;
	public String getPayment()
	{
		return this.payment;
	}
	public void setPayment(String payment)
	{
		this.payment=payment;
	}

	/**
	*
	*/
	private String receipt;
	public String getReceipt()
	{
		return this.receipt;
	}
	public void setReceipt(String receipt)
	{
		this.receipt=receipt;
	}

	/**
	*
	*/
	private String deptguid;
	public String getDeptguid()
	{
		return this.deptguid;
	}
	public void setDeptguid(String deptguid)
	{
		this.deptguid=deptguid;
	}

	/**
	*
	*/
	private String createtime;
	public String getCreatetime()
	{
		return this.createtime;
	}
	public void setCreatetime(String createtime)
	{
		this.createtime=createtime;
	}

	/**
	*
	*/
	private String createperson;
	public String getCreateperson()
	{
		return this.createperson;
	}
	public void setCreateperson(String createperson)
	{
		this.createperson=createperson;
	}

	/**
	*
	*/
	private String createunitid;
	public String getCreateunitid()
	{
		return this.createunitid;
	}
	public void setCreateunitid(String createunitid)
	{
		this.createunitid=createunitid;
	}

	/**
	*
	*/
	private String modifytime;
	public String getModifytime()
	{
		return this.modifytime;
	}
	public void setModifytime(String modifytime)
	{
		this.modifytime=modifytime;
	}

	/**
	*
	*/
	private String modifyperson;
	public String getModifyperson()
	{
		return this.modifyperson;
	}
	public void setModifyperson(String modifyperson)
	{
		this.modifyperson=modifyperson;
	}

	/**
	*
	*/
	private String deleteflag;
	public String getDeleteflag()
	{
		return this.deleteflag;
	}
	public void setDeleteflag(String deleteflag)
	{
		this.deleteflag=deleteflag;
	}

	/**
	*
	*/
	private String formid;
	public String getFormid()
	{
		return this.formid;
	}
	public void setFormid(String formid)
	{
		this.formid=formid;
	}

	/**
	*����ֵ
	*/
	private String returnvalue;
	public String getReturnvalue()
	{
		return this.returnvalue;
	}
	public void setReturnvalue(String returnvalue)
	{
		this.returnvalue=returnvalue;
	}

	/**
	*����ֵ
	*/
	private String returnsql;
	public String getReturnsql()
	{
		return this.returnsql;
	}
	public void setReturnsql(String returnsql)
	{
		this.returnsql=returnsql;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("");
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("piid = "+(piid == null ? "null" : piid)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("quantity = "+(quantity == null ? "null" : quantity)+"\n");		sb.append("totalquantity = "+(totalquantity == null ? "null" : totalquantity)+"\n");		sb.append("returnquantity = "+(returnquantity == null ? "null" : returnquantity)+"\n");		sb.append("price = "+(price == null ? "null" : price)+"\n");		sb.append("totalprice = "+(totalprice == null ? "null" : totalprice)+"\n");		sb.append("currency = "+(currency == null ? "null" : currency)+"\n");		sb.append("exchangerate = "+(exchangerate == null ? "null" : exchangerate)+"\n");		sb.append("exchangeprice = "+(exchangeprice == null ? "null" : exchangeprice)+"\n");		sb.append("rmbprice = "+(rmbprice == null ? "null" : rmbprice)+"\n");		sb.append("salestax = "+(salestax == null ? "null" : salestax)+"\n");		sb.append("rebate = "+(rebate == null ? "null" : rebate)+"\n");		sb.append("rebaterate = "+(rebaterate == null ? "null" : rebaterate)+"\n");		sb.append("profitrate = "+(profitrate == null ? "null" : profitrate)+"\n");		sb.append("profit = "+(profit == null ? "null" : profit)+"\n");		sb.append("salesprofit = "+(salesprofit == null ? "null" : salesprofit)+"\n");		sb.append("adjustprofit = "+(adjustprofit == null ? "null" : adjustprofit)+"\n");		sb.append("totalsalesprofit = "+(totalsalesprofit == null ? "null" : totalsalesprofit)+"\n");		sb.append("totaladjustprofit = "+(totaladjustprofit == null ? "null" : totaladjustprofit)+"\n");		sb.append("ordercost = "+(ordercost == null ? "null" : ordercost)+"\n");		sb.append("netprofit = "+(netprofit == null ? "null" : netprofit)+"\n");		sb.append("productcost = "+(productcost == null ? "null" : productcost)+"\n");		sb.append("storagedate = "+(storagedate == null ? "null" : storagedate)+"\n");		sb.append("status = "+(status == null ? "null" : status)+"\n");		sb.append("productclassify = "+(productclassify == null ? "null" : productclassify)+"\n");		sb.append("ordertype = "+(ordertype == null ? "null" : ordertype)+"\n");		sb.append("payment = "+(payment == null ? "null" : payment)+"\n");		sb.append("receipt = "+(receipt == null ? "null" : receipt)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		ysid= (ysid == null ?null : ysid.trim());		piid= (piid == null ?null : piid.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		materialid= (materialid == null ?null : materialid.trim());		quantity= (quantity == null ?null : quantity.trim());		totalquantity= (totalquantity == null ?null : totalquantity.trim());		returnquantity= (returnquantity == null ?null : returnquantity.trim());		price= (price == null ?null : price.trim());		totalprice= (totalprice == null ?null : totalprice.trim());		currency= (currency == null ?null : currency.trim());		exchangerate= (exchangerate == null ?null : exchangerate.trim());		exchangeprice= (exchangeprice == null ?null : exchangeprice.trim());		rmbprice= (rmbprice == null ?null : rmbprice.trim());		salestax= (salestax == null ?null : salestax.trim());		rebate= (rebate == null ?null : rebate.trim());		rebaterate= (rebaterate == null ?null : rebaterate.trim());		profitrate= (profitrate == null ?null : profitrate.trim());		profit= (profit == null ?null : profit.trim());		salesprofit= (salesprofit == null ?null : salesprofit.trim());		adjustprofit= (adjustprofit == null ?null : adjustprofit.trim());		totalsalesprofit= (totalsalesprofit == null ?null : totalsalesprofit.trim());		totaladjustprofit= (totaladjustprofit == null ?null : totaladjustprofit.trim());		ordercost= (ordercost == null ?null : ordercost.trim());		netprofit= (netprofit == null ?null : netprofit.trim());		productcost= (productcost == null ?null : productcost.trim());		storagedate= (storagedate == null ?null : storagedate.trim());		status= (status == null ?null : status.trim());		productclassify= (productclassify == null ?null : productclassify.trim());		ordertype= (ordertype == null ?null : ordertype.trim());		payment= (payment == null ?null : payment.trim());		receipt= (receipt == null ?null : receipt.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
