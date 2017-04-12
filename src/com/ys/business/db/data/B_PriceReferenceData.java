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
		sb.append("***** DataObject list begin *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());
	}

}