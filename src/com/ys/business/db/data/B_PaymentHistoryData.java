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
public class B_PaymentHistoryData implements java.io.Serializable
{

	public B_PaymentHistoryData()
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
	private String paymenthistoryid;
	public String getPaymenthistoryid()
	{
		return this.paymenthistoryid;
	}
	public void setPaymenthistoryid(String paymenthistoryid)
	{
		this.paymenthistoryid=paymenthistoryid;
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
	private String paymentmethod;
	public String getPaymentmethod()
	{
		return this.paymentmethod;
	}
	public void setPaymentmethod(String paymentmethod)
	{
		this.paymentmethod=paymentmethod;
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
	private String paymentamount;
	public String getPaymentamount()
	{
		return this.paymentamount;
	}
	public void setPaymentamount(String paymentamount)
	{
		this.paymentamount=paymentamount;
	}

	/**
	*
	*/
	private String finishuser;
	public String getFinishuser()
	{
		return this.finishuser;
	}
	public void setFinishuser(String finishuser)
	{
		this.finishuser=finishuser;
	}

	/**
	*
	*/
	private String finishdate;
	public String getFinishdate()
	{
		return this.finishdate;
	}
	public void setFinishdate(String finishdate)
	{
		this.finishdate=finishdate;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("paymenthistoryid = "+(paymenthistoryid == null ? "null" : paymenthistoryid)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("paymentid = "+(paymentid == null ? "null" : paymentid)+"\n");		sb.append("paymentmethod = "+(paymentmethod == null ? "null" : paymentmethod)+"\n");		sb.append("currency = "+(currency == null ? "null" : currency)+"\n");		sb.append("paymentamount = "+(paymentamount == null ? "null" : paymentamount)+"\n");		sb.append("finishuser = "+(finishuser == null ? "null" : finishuser)+"\n");		sb.append("finishdate = "+(finishdate == null ? "null" : finishdate)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		paymenthistoryid= (paymenthistoryid == null ?null : paymenthistoryid.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		paymentid= (paymentid == null ?null : paymentid.trim());		paymentmethod= (paymentmethod == null ?null : paymentmethod.trim());		currency= (currency == null ?null : currency.trim());		paymentamount= (paymentamount == null ?null : paymentamount.trim());		finishuser= (finishuser == null ?null : finishuser.trim());		finishdate= (finishdate == null ?null : finishdate.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
