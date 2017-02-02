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
public class B_OrderData implements java.io.Serializable
{

	public B_OrderData()
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
	private String customerid;
	public String getCustomerid()
	{
		return this.customerid;
	}
	public void setCustomerid(String customerid)
	{
		this.customerid=customerid;
	}

	/**
	*
	*/
	private String orderid;
	public String getOrderid()
	{
		return this.orderid;
	}
	public void setOrderid(String orderid)
	{
		this.orderid=orderid;
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
	private String shippingcase;
	public String getShippingcase()
	{
		return this.shippingcase;
	}
	public void setShippingcase(String shippingcase)
	{
		this.shippingcase=shippingcase;
	}

	/**
	*
	*/
	private String paymentterm;
	public String getPaymentterm()
	{
		return this.paymentterm;
	}
	public void setPaymentterm(String paymentterm)
	{
		this.paymentterm=paymentterm;
	}

	/**
	*
	*/
	private String loadingport;
	public String getLoadingport()
	{
		return this.loadingport;
	}
	public void setLoadingport(String loadingport)
	{
		this.loadingport=loadingport;
	}

	/**
	*
	*/
	private String deliveryport;
	public String getDeliveryport()
	{
		return this.deliveryport;
	}
	public void setDeliveryport(String deliveryport)
	{
		this.deliveryport=deliveryport;
	}

	/**
	*
	*/
	private String orderdate;
	public String getOrderdate()
	{
		return this.orderdate;
	}
	public void setOrderdate(String orderdate)
	{
		this.orderdate=orderdate;
	}

	/**
	*
	*/
	private String deliverydate;
	public String getDeliverydate()
	{
		return this.deliverydate;
	}
	public void setDeliverydate(String deliverydate)
	{
		this.deliverydate=deliverydate;
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
	private String yskordertarget;
	public String getYskordertarget()
	{
		return this.yskordertarget;
	}
	public void setYskordertarget(String yskordertarget)
	{
		this.yskordertarget=yskordertarget;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("piid = "+(piid == null ? "null" : piid)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("customerid = "+(customerid == null ? "null" : customerid)+"\n");		sb.append("orderid = "+(orderid == null ? "null" : orderid)+"\n");		sb.append("currency = "+(currency == null ? "null" : currency)+"\n");		sb.append("shippingcase = "+(shippingcase == null ? "null" : shippingcase)+"\n");		sb.append("paymentterm = "+(paymentterm == null ? "null" : paymentterm)+"\n");		sb.append("loadingport = "+(loadingport == null ? "null" : loadingport)+"\n");		sb.append("deliveryport = "+(deliveryport == null ? "null" : deliveryport)+"\n");		sb.append("orderdate = "+(orderdate == null ? "null" : orderdate)+"\n");		sb.append("deliverydate = "+(deliverydate == null ? "null" : deliverydate)+"\n");		sb.append("totalprice = "+(totalprice == null ? "null" : totalprice)+"\n");		sb.append("yskordertarget = "+(yskordertarget == null ? "null" : yskordertarget)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		piid= (piid == null ?null : piid.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		customerid= (customerid == null ?null : customerid.trim());		orderid= (orderid == null ?null : orderid.trim());		currency= (currency == null ?null : currency.trim());		shippingcase= (shippingcase == null ?null : shippingcase.trim());		paymentterm= (paymentterm == null ?null : paymentterm.trim());		loadingport= (loadingport == null ?null : loadingport.trim());		deliveryport= (deliveryport == null ?null : deliveryport.trim());		orderdate= (orderdate == null ?null : orderdate.trim());		deliverydate= (deliverydate == null ?null : deliverydate.trim());		totalprice= (totalprice == null ?null : totalprice.trim());		yskordertarget= (yskordertarget == null ?null : yskordertarget.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
