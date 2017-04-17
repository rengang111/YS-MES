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
public class B_InventoryData implements java.io.Serializable
{

	public B_InventoryData()
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
	private String safetyinventory;
	public String getSafetyinventory()
	{
		return this.safetyinventory;
	}
	public void setSafetyinventory(String safetyinventory)
	{
		this.safetyinventory=safetyinventory;
	}

	/**
	*
	*/
	private String waitstockin;
	public String getWaitstockin()
	{
		return this.waitstockin;
	}
	public void setWaitstockin(String waitstockin)
	{
		this.waitstockin=waitstockin;
	}

	/**
	*
	*/
	private String waitstockout;
	public String getWaitstockout()
	{
		return this.waitstockout;
	}
	public void setWaitstockout(String waitstockout)
	{
		this.waitstockout=waitstockout;
	}

	/**
	*
	*/
	private String quantityonhand;
	public String getQuantityonhand()
	{
		return this.quantityonhand;
	}
	public void setQuantityonhand(String quantityonhand)
	{
		this.quantityonhand=quantityonhand;
	}

	/**
	*
	*/
	private String availabeltopromise;
	public String getAvailabeltopromise()
	{
		return this.availabeltopromise;
	}
	public void setAvailabeltopromise(String availabeltopromise)
	{
		this.availabeltopromise=availabeltopromise;
	}

	/**
	*
	*/
	private String accountingquantity;
	public String getAccountingquantity()
	{
		return this.accountingquantity;
	}
	public void setAccountingquantity(String accountingquantity)
	{
		this.accountingquantity=accountingquantity;
	}

	/**
	*
	*/
	private String areanumber;
	public String getAreanumber()
	{
		return this.areanumber;
	}
	public void setAreanumber(String areanumber)
	{
		this.areanumber=areanumber;
	}

	/**
	*
	*/
	private String shelfnumber;
	public String getShelfnumber()
	{
		return this.shelfnumber;
	}
	public void setShelfnumber(String shelfnumber)
	{
		this.shelfnumber=shelfnumber;
	}

	/**
	*
	*/
	private String placenumber;
	public String getPlacenumber()
	{
		return this.placenumber;
	}
	public void setPlacenumber(String placenumber)
	{
		this.placenumber=placenumber;
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