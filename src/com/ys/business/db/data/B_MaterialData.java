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
public class B_MaterialData implements java.io.Serializable
{

	public B_MaterialData()
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
	private String materialid;
	public String getMaterialid()
	{
		return this.materialid;
	}
	public void setMaterialid(String materialid)
	{
		this.materialid=materialid;
	}

	/**
	*
	*/
	private String materialname;
	public String getMaterialname()
	{
		return this.materialname;
	}
	public void setMaterialname(String materialname)
	{
		this.materialname=materialname;
	}

	/**
	*
	*/
	private String categoryid;
	public String getCategoryid()
	{
		return this.categoryid;
	}
	public void setCategoryid(String categoryid)
	{
		this.categoryid=categoryid;
	}

	/**
	*
	*/
	private String description;
	public String getDescription()
	{
		return this.description;
	}
	public void setDescription(String description)
	{
		this.description=description;
	}

	/**
	*
	*/
	private String sharemodel;
	public String getSharemodel()
	{
		return this.sharemodel;
	}
	public void setSharemodel(String sharemodel)
	{
		this.sharemodel=sharemodel;
	}

	/**
	*
	*/
	private String unit;
	public String getUnit()
	{
		return this.unit;
	}
	public void setUnit(String unit)
	{
		this.unit=unit;
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
	private String subiddes;
	public String getSubiddes()
	{
		return this.subiddes;
	}
	public void setSubiddes(String subiddes)
	{
		this.subiddes=subiddes;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("materialname = "+(materialname == null ? "null" : materialname)+"\n");		sb.append("categoryid = "+(categoryid == null ? "null" : categoryid)+"\n");		sb.append("description = "+(description == null ? "null" : description)+"\n");		sb.append("sharemodel = "+(sharemodel == null ? "null" : sharemodel)+"\n");		sb.append("unit = "+(unit == null ? "null" : unit)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("subiddes = "+(subiddes == null ? "null" : subiddes)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		materialid= (materialid == null ?null : materialid.trim());		materialname= (materialname == null ?null : materialname.trim());		categoryid= (categoryid == null ?null : categoryid.trim());		description= (description == null ?null : description.trim());		sharemodel= (sharemodel == null ?null : sharemodel.trim());		unit= (unit == null ?null : unit.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		subiddes= (subiddes == null ?null : subiddes.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
