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
public class YW_PROTOTYPEData implements java.io.Serializable
{

	public YW_PROTOTYPEData()
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
	private String prot_code;
	public String getProt_code()
	{
		return this.prot_code;
	}
	public void setProt_code(String prot_code)
	{
		this.prot_code=prot_code;
	}

	/**
	*
	*/
	private String prot_name;
	public String getProt_name()
	{
		return this.prot_name;
	}
	public void setProt_name(String prot_name)
	{
		this.prot_name=prot_name;
	}

	/**
	*
	*/
	private String manufacturer;
	public String getManufacturer()
	{
		return this.manufacturer;
	}
	public void setManufacturer(String manufacturer)
	{
		this.manufacturer=manufacturer;
	}

	/**
	*
	*/
	private String buy_date;
	public String getBuy_date()
	{
		return this.buy_date;
	}
	public void setBuy_date(String buy_date)
	{
		this.buy_date=buy_date;
	}

	/**
	*
	*/
	private Float buy_price;
	public Float getBuy_price()
	{
		return this.buy_price;
	}
	public void setBuy_price(Float buy_price)
	{
		this.buy_price=buy_price;
	}

	/**
	*
	*/
	private String buy_location;
	public String getBuy_location()
	{
		return this.buy_location;
	}
	public void setBuy_location(String buy_location)
	{
		this.buy_location=buy_location;
	}

	/**
	*
	*/
	private String prot_desc_ch;
	public String getProt_desc_ch()
	{
		return this.prot_desc_ch;
	}
	public void setProt_desc_ch(String prot_desc_ch)
	{
		this.prot_desc_ch=prot_desc_ch;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("prot_code = "+(prot_code == null ? "null" : prot_code)+"\n");		sb.append("prot_name = "+(prot_name == null ? "null" : prot_name)+"\n");		sb.append("manufacturer = "+(manufacturer == null ? "null" : manufacturer)+"\n");		sb.append("buy_date = "+(buy_date == null ? "null" : buy_date)+"\n");		sb.append("buy_price = "+(buy_price == null ? "null" : buy_price.toString())+"\n");		sb.append("buy_location = "+(buy_location == null ? "null" : buy_location)+"\n");		sb.append("prot_desc_ch = "+(prot_desc_ch == null ? "null" : prot_desc_ch)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		prot_code= (prot_code == null ?null : prot_code.trim());		prot_name= (prot_name == null ?null : prot_name.trim());		manufacturer= (manufacturer == null ?null : manufacturer.trim());		buy_date= (buy_date == null ?null : buy_date.trim());		buy_location= (buy_location == null ?null : buy_location.trim());		prot_desc_ch= (prot_desc_ch == null ?null : prot_desc_ch.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
