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
public class B_ZZMaterialPriceData implements java.io.Serializable
{

	public B_ZZMaterialPriceData()
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
	private String managementcostrate;
	public String getManagementcostrate()
	{
		return this.managementcostrate;
	}
	public void setManagementcostrate(String managementcostrate)
	{
		this.managementcostrate=managementcostrate;
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
	private String cavitiesnumber;
	public String getCavitiesnumber()
	{
		return this.cavitiesnumber;
	}
	public void setCavitiesnumber(String cavitiesnumber)
	{
		this.cavitiesnumber=cavitiesnumber;
	}

	/**
	*
	*/
	private String time;
	public String getTime()
	{
		return this.time;
	}
	public void setTime(String time)
	{
		this.time=time;
	}

	/**
	*
	*/
	private String houryield;
	public String getHouryield()
	{
		return this.houryield;
	}
	public void setHouryield(String houryield)
	{
		this.houryield=houryield;
	}

	/**
	*
	*/
	private String hourprice;
	public String getHourprice()
	{
		return this.hourprice;
	}
	public void setHourprice(String hourprice)
	{
		this.hourprice=hourprice;
	}

	/**
	*
	*/
	private String laborprice;
	public String getLaborprice()
	{
		return this.laborprice;
	}
	public void setLaborprice(String laborprice)
	{
		this.laborprice=laborprice;
	}

	/**
	*
	*/
	private String kilowatt;
	public String getKilowatt()
	{
		return this.kilowatt;
	}
	public void setKilowatt(String kilowatt)
	{
		this.kilowatt=kilowatt;
	}

	/**
	*
	*/
	private String hourpower;
	public String getHourpower()
	{
		return this.hourpower;
	}
	public void setHourpower(String hourpower)
	{
		this.hourpower=hourpower;
	}

	/**
	*
	*/
	private String kwprice;
	public String getKwprice()
	{
		return this.kwprice;
	}
	public void setKwprice(String kwprice)
	{
		this.kwprice=kwprice;
	}

	/**
	*
	*/
	private String powerprice;
	public String getPowerprice()
	{
		return this.powerprice;
	}
	public void setPowerprice(String powerprice)
	{
		this.powerprice=powerprice;
	}

	/**
	*
	*/
	private String totalprice;
	public String getTotalprice()
	{
		return this.totalprice;
	}
	public void setTotalprice(String totalprice)
	{
		this.totalprice=totalprice;
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