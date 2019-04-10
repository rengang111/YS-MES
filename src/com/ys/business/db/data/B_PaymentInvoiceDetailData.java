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
public class B_PaymentInvoiceDetailData implements java.io.Serializable
{

	public B_PaymentInvoiceDetailData()
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
	private String paymentid;
	public String getPaymentid()
	{
		return this.paymentid;
	}
	public void setPaymentid(String paymentid)
	{
		this.paymentid=paymentid;
	}

	/**
	*
	*/
	private String invoiceamount;
	public String getInvoiceamount()
	{
		return this.invoiceamount;
	}
	public void setInvoiceamount(String invoiceamount)
	{
		this.invoiceamount=invoiceamount;
	}

	/**
	*
	*/
	private String invoiceuser;
	public String getInvoiceuser()
	{
		return this.invoiceuser;
	}
	public void setInvoiceuser(String invoiceuser)
	{
		this.invoiceuser=invoiceuser;
	}

	/**
	*
	*/
	private String invoicedate;
	public String getInvoicedate()
	{
		return this.invoicedate;
	}
	public void setInvoicedate(String invoicedate)
	{
		this.invoicedate=invoicedate;
	}

	/**
	*
	*/
	private String invoicetype;
	public String getInvoicetype()
	{
		return this.invoicetype;
	}
	public void setInvoicetype(String invoicetype)
	{
		this.invoicetype=invoicetype;
	}

	/**
	*
	*/
	private String invoicenumber;
	public String getInvoicenumber()
	{
		return this.invoicenumber;
	}
	public void setInvoicenumber(String invoicenumber)
	{
		this.invoicenumber=invoicenumber;
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
		sb.append("***** DataObject list begin *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());
	}

}