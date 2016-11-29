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
public class B_ZZRawMaterialData implements java.io.Serializable
{

	public B_ZZRawMaterialData()
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
	private String rawmaterialid;
	public String getRawmaterialid()
	{
		return this.rawmaterialid;
	}
	public void setRawmaterialid(String rawmaterialid)
	{
		this.rawmaterialid=rawmaterialid;
	}

	/**
	*
	*/
	private String netweight;
	public String getNetweight()
	{
		return this.netweight;
	}
	public void setNetweight(String netweight)
	{
		this.netweight=netweight;
	}

	/**
	*
	*/
	private String wastage;
	public String getWastage()
	{
		return this.wastage;
	}
	public void setWastage(String wastage)
	{
		this.wastage=wastage;
	}

	/**
	*
	*/
	private String weight;
	public String getWeight()
	{
		return this.weight;
	}
	public void setWeight(String weight)
	{
		this.weight=weight;
	}

	/**
	*
	*/
	private String kgprice;
	public String getKgprice()
	{
		return this.kgprice;
	}
	public void setKgprice(String kgprice)
	{
		this.kgprice=kgprice;
	}

	/**
	*
	*/
	private String materialprice;
	public String getMaterialprice()
	{
		return this.materialprice;
	}
	public void setMaterialprice(String materialprice)
	{
		this.materialprice=materialprice;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("rawmaterialid = "+(rawmaterialid == null ? "null" : rawmaterialid)+"\n");		sb.append("netweight = "+(netweight == null ? "null" : netweight)+"\n");		sb.append("wastage = "+(wastage == null ? "null" : wastage)+"\n");		sb.append("weight = "+(weight == null ? "null" : weight)+"\n");		sb.append("kgprice = "+(kgprice == null ? "null" : kgprice)+"\n");		sb.append("materialprice = "+(materialprice == null ? "null" : materialprice)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		materialid= (materialid == null ?null : materialid.trim());		rawmaterialid= (rawmaterialid == null ?null : rawmaterialid.trim());		netweight= (netweight == null ?null : netweight.trim());		wastage= (wastage == null ?null : wastage.trim());		weight= (weight == null ?null : weight.trim());		kgprice= (kgprice == null ?null : kgprice.trim());		materialprice= (materialprice == null ?null : materialprice.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
