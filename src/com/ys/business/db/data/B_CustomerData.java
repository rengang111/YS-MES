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
public class B_CustomerData implements java.io.Serializable
{

	public B_CustomerData()
	{
	}

	/**
	*
	*/
	private String id;
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id=id;
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
	private String customersimpledes;
	public String getCustomersimpledes()
	{
		return this.customersimpledes;
	}
	public void setCustomersimpledes(String customersimpledes)
	{
		this.customersimpledes=customersimpledes;
	}

	/**
	*
	*/
	private String customername;
	public String getCustomername()
	{
		return this.customername;
	}
	public void setCustomername(String customername)
	{
		this.customername=customername;
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
	private String country;
	public String getCountry()
	{
		return this.country;
	}
	public void setCountry(String country)
	{
		this.country=country;
	}

	/**
	*
	*/
	private String denominationcurrency;
	public String getDenominationcurrency()
	{
		return this.denominationcurrency;
	}
	public void setDenominationcurrency(String denominationcurrency)
	{
		this.denominationcurrency=denominationcurrency;
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
		sb.append("***** DataObject list begin *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());
	}

}