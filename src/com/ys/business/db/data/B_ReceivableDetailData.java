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
public class B_ReceivableDetailData implements java.io.Serializable
{

	public B_ReceivableDetailData()
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
	private String receivableid;
	public String getReceivableid()
	{
		return this.receivableid;
	}
	public void setReceivableid(String receivableid)
	{
		this.receivableid=receivableid;
	}

	/**
	*
	*/
	private String receivableserialid;
	public String getReceivableserialid()
	{
		return this.receivableserialid;
	}
	public void setReceivableserialid(String receivableserialid)
	{
		this.receivableserialid=receivableserialid;
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
	private String bankcode;
	public String getBankcode()
	{
		return this.bankcode;
	}
	public void setBankcode(String bankcode)
	{
		this.bankcode=bankcode;
	}

	/**
	*
	*/
	private String actualamount;
	public String getActualamount()
	{
		return this.actualamount;
	}
	public void setActualamount(String actualamount)
	{
		this.actualamount=actualamount;
	}

	/**
	*
	*/
	private String bankdeduction;
	public String getBankdeduction()
	{
		return this.bankdeduction;
	}
	public void setBankdeduction(String bankdeduction)
	{
		this.bankdeduction=bankdeduction;
	}

	/**
	*
	*/
	private String customerdeduction;
	public String getCustomerdeduction()
	{
		return this.customerdeduction;
	}
	public void setCustomerdeduction(String customerdeduction)
	{
		this.customerdeduction=customerdeduction;
	}

	/**
	*
	*/
	private String payee;
	public String getPayee()
	{
		return this.payee;
	}
	public void setPayee(String payee)
	{
		this.payee=payee;
	}

	/**
	*
	*/
	private String collectiondate;
	public String getCollectiondate()
	{
		return this.collectiondate;
	}
	public void setCollectiondate(String collectiondate)
	{
		this.collectiondate=collectiondate;
	}

	/**
	*
	*/
	private String remarks;
	public String getRemarks()
	{
		return this.remarks;
	}
	public void setRemarks(String remarks)
	{
		this.remarks=remarks;
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