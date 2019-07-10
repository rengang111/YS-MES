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
public class S_ProductionTeamCodeData implements java.io.Serializable
{

	public S_ProductionTeamCodeData()
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
	private String codeid;
	public String getCodeid()
	{
		return this.codeid;
	}
	public void setCodeid(String codeid)
	{
		this.codeid=codeid;
	}

	/**
	*
	*/
	private String productiontechnical;
	public String getProductiontechnical()
	{
		return this.productiontechnical;
	}
	public void setProductiontechnical(String productiontechnical)
	{
		this.productiontechnical=productiontechnical;
	}

	/**
	*
	*/
	private String employeeskills;
	public String getEmployeeskills()
	{
		return this.employeeskills;
	}
	public void setEmployeeskills(String employeeskills)
	{
		this.employeeskills=employeeskills;
	}

	/**
	*
	*/
	private String groupleader;
	public String getGroupleader()
	{
		return this.groupleader;
	}
	public void setGroupleader(String groupleader)
	{
		this.groupleader=groupleader;
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
	private String multilevel;
	public String getMultilevel()
	{
		return this.multilevel;
	}
	public void setMultilevel(String multilevel)
	{
		this.multilevel=multilevel;
	}

	/**
	*
	*/
	private String sortno;
	public String getSortno()
	{
		return this.sortno;
	}
	public void setSortno(String sortno)
	{
		this.sortno=sortno;
	}

	/**
	*
	*/
	private String effectiveflag;
	public String getEffectiveflag()
	{
		return this.effectiveflag;
	}
	public void setEffectiveflag(String effectiveflag)
	{
		this.effectiveflag=effectiveflag;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("codeid = "+(codeid == null ? "null" : codeid)+"\n");		sb.append("productiontechnical = "+(productiontechnical == null ? "null" : productiontechnical)+"\n");		sb.append("employeeskills = "+(employeeskills == null ? "null" : employeeskills)+"\n");		sb.append("groupleader = "+(groupleader == null ? "null" : groupleader)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("multilevel = "+(multilevel == null ? "null" : multilevel)+"\n");		sb.append("sortno = "+(sortno == null ? "null" : sortno)+"\n");		sb.append("effectiveflag = "+(effectiveflag == null ? "null" : effectiveflag)+"\n");		sb.append("remarks = "+(remarks == null ? "null" : remarks)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		codeid= (codeid == null ?null : codeid.trim());		productiontechnical= (productiontechnical == null ?null : productiontechnical.trim());		employeeskills= (employeeskills == null ?null : employeeskills.trim());		groupleader= (groupleader == null ?null : groupleader.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		multilevel= (multilevel == null ?null : multilevel.trim());		sortno= (sortno == null ?null : sortno.trim());		effectiveflag= (effectiveflag == null ?null : effectiveflag.trim());		remarks= (remarks == null ?null : remarks.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
