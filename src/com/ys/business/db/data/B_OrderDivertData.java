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
public class B_OrderDivertData implements java.io.Serializable
{

	public B_OrderDivertData()
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
	private String divertpiid;
	public String getDivertpiid()
	{
		return this.divertpiid;
	}
	public void setDivertpiid(String divertpiid)
	{
		this.divertpiid=divertpiid;
	}

	/**
	*
	*/
	private String divertfromysid;
	public String getDivertfromysid()
	{
		return this.divertfromysid;
	}
	public void setDivertfromysid(String divertfromysid)
	{
		this.divertfromysid=divertfromysid;
	}

	/**
	*
	*/
	private String divertoysid;
	public String getDivertoysid()
	{
		return this.divertoysid;
	}
	public void setDivertoysid(String divertoysid)
	{
		this.divertoysid=divertoysid;
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
	private String divertquantity;
	public String getDivertquantity()
	{
		return this.divertquantity;
	}
	public void setDivertquantity(String divertquantity)
	{
		this.divertquantity=divertquantity;
	}

	/**
	*
	*/
	private String diverflag;
	public String getDiverflag()
	{
		return this.diverflag;
	}
	public void setDiverflag(String diverflag)
	{
		this.diverflag=diverflag;
	}

	/**
	*
	*/
	private String thisreductionqty;
	public String getThisreductionqty()
	{
		return this.thisreductionqty;
	}
	public void setThisreductionqty(String thisreductionqty)
	{
		this.thisreductionqty=thisreductionqty;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("divertpiid = "+(divertpiid == null ? "null" : divertpiid)+"\n");		sb.append("divertfromysid = "+(divertfromysid == null ? "null" : divertfromysid)+"\n");		sb.append("divertoysid = "+(divertoysid == null ? "null" : divertoysid)+"\n");		sb.append("shortname = "+(shortname == null ? "null" : shortname)+"\n");		sb.append("divertquantity = "+(divertquantity == null ? "null" : divertquantity)+"\n");		sb.append("diverflag = "+(diverflag == null ? "null" : diverflag)+"\n");		sb.append("thisreductionqty = "+(thisreductionqty == null ? "null" : thisreductionqty)+"\n");		sb.append("remarks = "+(remarks == null ? "null" : remarks)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		divertpiid= (divertpiid == null ?null : divertpiid.trim());		divertfromysid= (divertfromysid == null ?null : divertfromysid.trim());		divertoysid= (divertoysid == null ?null : divertoysid.trim());		shortname= (shortname == null ?null : shortname.trim());		divertquantity= (divertquantity == null ?null : divertquantity.trim());		diverflag= (diverflag == null ?null : diverflag.trim());		thisreductionqty= (thisreductionqty == null ?null : thisreductionqty.trim());		remarks= (remarks == null ?null : remarks.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
