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
public class B_PriceReferenceData implements java.io.Serializable
{

	public B_PriceReferenceData()
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
	private String lastprice;
	public String getLastprice()
	{
		return this.lastprice;
	}
	public void setLastprice(String lastprice)
	{
		this.lastprice=lastprice;
	}

	/**
	*
	*/
	private String lastsupplierid;
	public String getLastsupplierid()
	{
		return this.lastsupplierid;
	}
	public void setLastsupplierid(String lastsupplierid)
	{
		this.lastsupplierid=lastsupplierid;
	}

	/**
	*
	*/
	private String lastdate;
	public String getLastdate()
	{
		return this.lastdate;
	}
	public void setLastdate(String lastdate)
	{
		this.lastdate=lastdate;
	}

	/**
	*
	*/
	private String minprice;
	public String getMinprice()
	{
		return this.minprice;
	}
	public void setMinprice(String minprice)
	{
		this.minprice=minprice;
	}

	/**
	*
	*/
	private String minsupplierid;
	public String getMinsupplierid()
	{
		return this.minsupplierid;
	}
	public void setMinsupplierid(String minsupplierid)
	{
		this.minsupplierid=minsupplierid;
	}

	/**
	*
	*/
	private String mindate;
	public String getMindate()
	{
		return this.mindate;
	}
	public void setMindate(String mindate)
	{
		this.mindate=mindate;
	}

	/**
	*
	*/
	private String usedflag;
	public String getUsedflag()
	{
		return this.usedflag;
	}
	public void setUsedflag(String usedflag)
	{
		this.usedflag=usedflag;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("lastprice = "+(lastprice == null ? "null" : lastprice)+"\n");		sb.append("lastsupplierid = "+(lastsupplierid == null ? "null" : lastsupplierid)+"\n");		sb.append("lastdate = "+(lastdate == null ? "null" : lastdate)+"\n");		sb.append("minprice = "+(minprice == null ? "null" : minprice)+"\n");		sb.append("minsupplierid = "+(minsupplierid == null ? "null" : minsupplierid)+"\n");		sb.append("mindate = "+(mindate == null ? "null" : mindate)+"\n");		sb.append("usedflag = "+(usedflag == null ? "null" : usedflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		materialid= (materialid == null ?null : materialid.trim());		lastprice= (lastprice == null ?null : lastprice.trim());		lastsupplierid= (lastsupplierid == null ?null : lastsupplierid.trim());		lastdate= (lastdate == null ?null : lastdate.trim());		minprice= (minprice == null ?null : minprice.trim());		minsupplierid= (minsupplierid == null ?null : minsupplierid.trim());		mindate= (mindate == null ?null : mindate.trim());		usedflag= (usedflag == null ?null : usedflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
