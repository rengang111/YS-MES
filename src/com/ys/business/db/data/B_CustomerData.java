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
	private String shortname;
	public String getShortname()
	{
		return this.shortname;
	}
	public void setShortname(String shortname)
	{
		this.shortname=shortname;
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
	private String shippingcondition;
	public String getShippingcondition()
	{
		return this.shippingcondition;
	}
	public void setShippingcondition(String shippingcondition)
	{
		this.shippingcondition=shippingcondition;
	}

	/**
	*
	*/
	private String shippiingport;
	public String getShippiingport()
	{
		return this.shippiingport;
	}
	public void setShippiingport(String shippiingport)
	{
		this.shippiingport=shippiingport;
	}

	/**
	*
	*/
	private String destinationport;
	public String getDestinationport()
	{
		return this.destinationport;
	}
	public void setDestinationport(String destinationport)
	{
		this.destinationport=destinationport;
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
	private String commission;
	public String getCommission()
	{
		return this.commission;
	}
	public void setCommission(String commission)
	{
		this.commission=commission;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("customerid = "+(customerid == null ? "null" : customerid)+"\n");		sb.append("shortname = "+(shortname == null ? "null" : shortname)+"\n");		sb.append("customername = "+(customername == null ? "null" : customername)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("paymentterm = "+(paymentterm == null ? "null" : paymentterm)+"\n");		sb.append("country = "+(country == null ? "null" : country)+"\n");		sb.append("currency = "+(currency == null ? "null" : currency)+"\n");		sb.append("shippingcondition = "+(shippingcondition == null ? "null" : shippingcondition)+"\n");		sb.append("shippiingport = "+(shippiingport == null ? "null" : shippiingport)+"\n");		sb.append("destinationport = "+(destinationport == null ? "null" : destinationport)+"\n");		sb.append("rebate = "+(rebate == null ? "null" : rebate)+"\n");		sb.append("commission = "+(commission == null ? "null" : commission)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		customerid= (customerid == null ?null : customerid.trim());		shortname= (shortname == null ?null : shortname.trim());		customername= (customername == null ?null : customername.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		paymentterm= (paymentterm == null ?null : paymentterm.trim());		country= (country == null ?null : country.trim());		currency= (currency == null ?null : currency.trim());		shippingcondition= (shippingcondition == null ?null : shippingcondition.trim());		shippiingport= (shippiingport == null ?null : shippiingport.trim());		destinationport= (destinationport == null ?null : destinationport.trim());		rebate= (rebate == null ?null : rebate.trim());		commission= (commission == null ?null : commission.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
