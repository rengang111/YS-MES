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
public class B_ExternalSampleData implements java.io.Serializable
{

	public B_ExternalSampleData()
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
	private String sampleid;
	public String getSampleid()
	{
		return this.sampleid;
	}
	public void setSampleid(String sampleid)
	{
		this.sampleid=sampleid;
	}

	/**
	*
	*/
	private String sampleversion;
	public String getSampleversion()
	{
		return this.sampleversion;
	}
	public void setSampleversion(String sampleversion)
	{
		this.sampleversion=sampleversion;
	}

	/**
	*
	*/
	private String samplename;
	public String getSamplename()
	{
		return this.samplename;
	}
	public void setSamplename(String samplename)
	{
		this.samplename=samplename;
	}

	/**
	*
	*/
	private String brand;
	public String getBrand()
	{
		return this.brand;
	}
	public void setBrand(String brand)
	{
		this.brand=brand;
	}

	/**
	*
	*/
	private String buytime;
	public String getBuytime()
	{
		return this.buytime;
	}
	public void setBuytime(String buytime)
	{
		this.buytime=buytime;
	}

	/**
	*
	*/
	private String currency;
	public String getCurrency()
	{
		return this.currency;
	}
	public void setCurrency(String currency)
	{
		this.currency=currency;
	}

	/**
	*
	*/
	private String price;
	public String getPrice()
	{
		return this.price;
	}
	public void setPrice(String price)
	{
		this.price=price;
	}

	/**
	*
	*/
	private String address;
	public String getAddress()
	{
		return this.address;
	}
	public void setAddress(String address)
	{
		this.address=address;
	}

	/**
	*
	*/
	private String memo;
	public String getMemo()
	{
		return this.memo;
	}
	public void setMemo(String memo)
	{
		this.memo=memo;
	}

	/**
	*
	*/
	private String image_filename;
	public String getImage_filename()
	{
		return this.image_filename;
	}
	public void setImage_filename(String image_filename)
	{
		this.image_filename=image_filename;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("sampleid = "+(sampleid == null ? "null" : sampleid)+"\n");		sb.append("sampleversion = "+(sampleversion == null ? "null" : sampleversion)+"\n");		sb.append("samplename = "+(samplename == null ? "null" : samplename)+"\n");		sb.append("brand = "+(brand == null ? "null" : brand)+"\n");		sb.append("buytime = "+(buytime == null ? "null" : buytime)+"\n");		sb.append("currency = "+(currency == null ? "null" : currency)+"\n");		sb.append("price = "+(price == null ? "null" : price)+"\n");		sb.append("address = "+(address == null ? "null" : address)+"\n");		sb.append("memo = "+(memo == null ? "null" : memo)+"\n");		sb.append("image_filename = "+(image_filename == null ? "null" : image_filename)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		sampleid= (sampleid == null ?null : sampleid.trim());		sampleversion= (sampleversion == null ?null : sampleversion.trim());		samplename= (samplename == null ?null : samplename.trim());		brand= (brand == null ?null : brand.trim());		buytime= (buytime == null ?null : buytime.trim());		currency= (currency == null ?null : currency.trim());		price= (price == null ?null : price.trim());		address= (address == null ?null : address.trim());		memo= (memo == null ?null : memo.trim());		image_filename= (image_filename == null ?null : image_filename.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
