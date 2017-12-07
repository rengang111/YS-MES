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
public class B_PurchaseStockInData implements java.io.Serializable
{

	public B_PurchaseStockInData()
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
	private String receiptid;
	public String getReceiptid()
	{
		return this.receiptid;
	}
	public void setReceiptid(String receiptid)
	{
		this.receiptid=receiptid;
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
	private String contractid;
	public String getContractid()
	{
		return this.contractid;
	}
	public void setContractid(String contractid)
	{
		this.contractid=contractid;
	}

	/**
	*
	*/
	private String arrivelid;
	public String getArrivelid()
	{
		return this.arrivelid;
	}
	public void setArrivelid(String arrivelid)
	{
		this.arrivelid=arrivelid;
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
	private String checkindate;
	public String getCheckindate()
	{
		return this.checkindate;
	}
	public void setCheckindate(String checkindate)
	{
		this.checkindate=checkindate;
	}

	/**
	*
	*/
	private String keepuser;
	public String getKeepuser()
	{
		return this.keepuser;
	}
	public void setKeepuser(String keepuser)
	{
		this.keepuser=keepuser;
	}

	/**
	*
	*/
	private String packagnumber;
	public String getPackagnumber()
	{
		return this.packagnumber;
	}
	public void setPackagnumber(String packagnumber)
	{
		this.packagnumber=packagnumber;
	}

	/**
	*
	*/
	private String originalreceiptid;
	public String getOriginalreceiptid()
	{
		return this.originalreceiptid;
	}
	public void setOriginalreceiptid(String originalreceiptid)
	{
		this.originalreceiptid=originalreceiptid;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("receiptid = "+(receiptid == null ? "null" : receiptid)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("contractid = "+(contractid == null ? "null" : contractid)+"\n");		sb.append("arrivelid = "+(arrivelid == null ? "null" : arrivelid)+"\n");		sb.append("supplierid = "+(supplierid == null ? "null" : supplierid)+"\n");		sb.append("checkindate = "+(checkindate == null ? "null" : checkindate)+"\n");		sb.append("keepuser = "+(keepuser == null ? "null" : keepuser)+"\n");		sb.append("packagnumber = "+(packagnumber == null ? "null" : packagnumber)+"\n");		sb.append("originalreceiptid = "+(originalreceiptid == null ? "null" : originalreceiptid)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		receiptid= (receiptid == null ?null : receiptid.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		ysid= (ysid == null ?null : ysid.trim());		contractid= (contractid == null ?null : contractid.trim());		arrivelid= (arrivelid == null ?null : arrivelid.trim());		supplierid= (supplierid == null ?null : supplierid.trim());		checkindate= (checkindate == null ?null : checkindate.trim());		keepuser= (keepuser == null ?null : keepuser.trim());		packagnumber= (packagnumber == null ?null : packagnumber.trim());		originalreceiptid= (originalreceiptid == null ?null : originalreceiptid.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
