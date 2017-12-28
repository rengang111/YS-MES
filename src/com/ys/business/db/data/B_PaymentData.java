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
public class B_PaymentData implements java.io.Serializable
{

	public B_PaymentData()
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
	private String requestdate;
	public String getRequestdate()
	{
		return this.requestdate;
	}
	public void setRequestdate(String requestdate)
	{
		this.requestdate=requestdate;
	}

	/**
	*
	*/
	private String applicant;
	public String getApplicant()
	{
		return this.applicant;
	}
	public void setApplicant(String applicant)
	{
		this.applicant=applicant;
	}

	/**
	*
	*/
	private String deptid;
	public String getDeptid()
	{
		return this.deptid;
	}
	public void setDeptid(String deptid)
	{
		this.deptid=deptid;
	}

	/**
	*
	*/
	private String paymentterms;
	public String getPaymentterms()
	{
		return this.paymentterms;
	}
	public void setPaymentterms(String paymentterms)
	{
		this.paymentterms=paymentterms;
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
	private String totalpayable;
	public String getTotalpayable()
	{
		return this.totalpayable;
	}
	public void setTotalpayable(String totalpayable)
	{
		this.totalpayable=totalpayable;
	}

	/**
	*
	*/
	private String approvaluser;
	public String getApprovaluser()
	{
		return this.approvaluser;
	}
	public void setApprovaluser(String approvaluser)
	{
		this.approvaluser=approvaluser;
	}

	/**
	*
	*/
	private String approvaldate;
	public String getApprovaldate()
	{
		return this.approvaldate;
	}
	public void setApprovaldate(String approvaldate)
	{
		this.approvaldate=approvaldate;
	}

	/**
	*
	*/
	private String approvalstatus;
	public String getApprovalstatus()
	{
		return this.approvalstatus;
	}
	public void setApprovalstatus(String approvalstatus)
	{
		this.approvalstatus=approvalstatus;
	}

	/**
	*
	*/
	private String approvalfeedback;
	public String getApprovalfeedback()
	{
		return this.approvalfeedback;
	}
	public void setApprovalfeedback(String approvalfeedback)
	{
		this.approvalfeedback=approvalfeedback;
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
	private String finishstatus;
	public String getFinishstatus()
	{
		return this.finishstatus;
	}
	public void setFinishstatus(String finishstatus)
	{
		this.finishstatus=finishstatus;
	}

	/**
	*
	*/
	private String supplierid;
	public String getSupplierid()
	{
		return this.supplierid;
	}
	public void setSupplierid(String supplierid)
	{
		this.supplierid=supplierid;
	}

	/**
	*
	*/
	private String contractids;
	public String getContractids()
	{
		return this.contractids;
	}
	public void setContractids(String contractids)
	{
		this.contractids=contractids;
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