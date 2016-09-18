package com.ys.system.db.data;

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
public class YW_AUT_PRJData implements java.io.Serializable
{

	public YW_AUT_PRJData()
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
	private int aut_id;
	public int getAut_id()
	{
		return this.aut_id;
	}
	public void setAut_id(int aut_id)
	{
		this.aut_id=aut_id;
	}

	/**
	*
	*/
	private String prj_name;
	public String getPrj_name()
	{
		return this.prj_name;
	}
	public void setPrj_name(String prj_name)
	{
		this.prj_name=prj_name;
	}

	/**
	*
	*/
	private Float cost;
	public Float getCost()
	{
		return this.cost;
	}
	public void setCost(Float cost)
	{
		this.cost=cost;
	}

	/**
	*
	*/
	private String apply_date;
	public String getApply_date()
	{
		return this.apply_date;
	}
	public void setApply_date(String apply_date)
	{
		this.apply_date=apply_date;
	}

	/**
	*
	*/
	private String proposer;
	public String getProposer()
	{
		return this.proposer;
	}
	public void setProposer(String proposer)
	{
		this.proposer=proposer;
	}

	/**
	*
	*/
	private String get_date;
	public String getGet_date()
	{
		return this.get_date;
	}
	public void setGet_date(String get_date)
	{
		this.get_date=get_date;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("aut_id = "+aut_id+"\n");		sb.append("prj_name = "+(prj_name == null ? "null" : prj_name)+"\n");		sb.append("cost = "+(cost == null ? "null" : cost.toString())+"\n");		sb.append("apply_date = "+(apply_date == null ? "null" : apply_date)+"\n");		sb.append("proposer = "+(proposer == null ? "null" : proposer)+"\n");		sb.append("get_date = "+(get_date == null ? "null" : get_date)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		prj_name= (prj_name == null ?null : prj_name.trim());		apply_date= (apply_date == null ?null : apply_date.trim());		proposer= (proposer == null ?null : proposer.trim());		get_date= (get_date == null ?null : get_date.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
