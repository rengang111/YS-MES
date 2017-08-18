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
public class B_ProductDesignData implements java.io.Serializable
{

	public B_ProductDesignData()
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
	private String productdetailid;
	public String getProductdetailid()
	{
		return this.productdetailid;
	}
	public void setProductdetailid(String productdetailid)
	{
		this.productdetailid=productdetailid;
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
	private String productid;
	public String getProductid()
	{
		return this.productid;
	}
	public void setProductid(String productid)
	{
		this.productid=productid;
	}

	/**
	*
	*/
	private String sealedsample;
	public String getSealedsample()
	{
		return this.sealedsample;
	}
	public void setSealedsample(String sealedsample)
	{
		this.sealedsample=sealedsample;
	}

	/**
	*
	*/
	private String batterypack;
	public String getBatterypack()
	{
		return this.batterypack;
	}
	public void setBatterypack(String batterypack)
	{
		this.batterypack=batterypack;
	}

	/**
	*
	*/
	private String chargertype;
	public String getChargertype()
	{
		return this.chargertype;
	}
	public void setChargertype(String chargertype)
	{
		this.chargertype=chargertype;
	}

	/**
	*
	*/
	private String packagedescription;
	public String getPackagedescription()
	{
		return this.packagedescription;
	}
	public void setPackagedescription(String packagedescription)
	{
		this.packagedescription=packagedescription;
	}

	/**
	*
	*/
	private String storagedescription;
	public String getStoragedescription()
	{
		return this.storagedescription;
	}
	public void setStoragedescription(String storagedescription)
	{
		this.storagedescription=storagedescription;
	}

	/**
	*
	*/
	private String status;
	public String getStatus()
	{
		return this.status;
	}
	public void setStatus(String status)
	{
		this.status=status;
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