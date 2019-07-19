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
public class B_SupplierData implements java.io.Serializable
{

	public B_SupplierData()
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
	private String supplierid;
	public String getSupplierid()
	{
		return this.supplierid;
	}
	public void setSupplierid(String supplierid)
	{
		this.supplierid=supplierid;
	}

	/**
	*
	*/
	private String shortname;
	public String getShortname()
	{
		return this.shortname;
	}
	public void setShortname(String shortname)
	{
		this.shortname=shortname;
	}

	/**
	*
	*/
	private String suppliername;
	public String getSuppliername()
	{
		return this.suppliername;
	}
	public void setSuppliername(String suppliername)
	{
		this.suppliername=suppliername;
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
	private String categorydes;
	public String getCategorydes()
	{
		return this.categorydes;
	}
	public void setCategorydes(String categorydes)
	{
		this.categorydes=categorydes;
	}

	/**
	*
	*/
	private String paymentterm;
	public String getPaymentterm()
	{
		return this.paymentterm;
	}
	public void setPaymentterm(String paymentterm)
	{
		this.paymentterm=paymentterm;
	}

	/**
	*
	*/
	private String country;
	public String getCountry()
	{
		return this.country;
	}
	public void setCountry(String country)
	{
		this.country=country;
	}

	/**
	*
	*/
	private String province;
	public String getProvince()
	{
		return this.province;
	}
	public void setProvince(String province)
	{
		this.province=province;
	}

	/**
	*
	*/
	private String city;
	public String getCity()
	{
		return this.city;
	}
	public void setCity(String city)
	{
		this.city=city;
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
	private String zipcode;
	public String getZipcode()
	{
		return this.zipcode;
	}
	public void setZipcode(String zipcode)
	{
		this.zipcode=zipcode;
	}

	/**
	*
	*/
	private String type;
	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type=type;
	}

	/**
	*
	*/
	private String normaldelivery;
	public String getNormaldelivery()
	{
		return this.normaldelivery;
	}
	public void setNormaldelivery(String normaldelivery)
	{
		this.normaldelivery=normaldelivery;
	}

	/**
	*
	*/
	private String maxdelivery;
	public String getMaxdelivery()
	{
		return this.maxdelivery;
	}
	public void setMaxdelivery(String maxdelivery)
	{
		this.maxdelivery=maxdelivery;
	}

	/**
	*
	*/
	private String issues;
	public String getIssues()
	{
		return this.issues;
	}
	public void setIssues(String issues)
	{
		this.issues=issues;
	}

	/**
	*
	*/
	private String purchasetype;
	public String getPurchasetype()
	{
		return this.purchasetype;
	}
	public void setPurchasetype(String purchasetype)
	{
		this.purchasetype=purchasetype;
	}

	/**
	*
	*/
	private String idusedbefore;
	public String getIdusedbefore()
	{
		return this.idusedbefore;
	}
	public void setIdusedbefore(String idusedbefore)
	{
		this.idusedbefore=idusedbefore;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("supplierid = "+(supplierid == null ? "null" : supplierid)+"\n");		sb.append("shortname = "+(shortname == null ? "null" : shortname)+"\n");		sb.append("suppliername = "+(suppliername == null ? "null" : suppliername)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("categoryid = "+(categoryid == null ? "null" : categoryid)+"\n");		sb.append("categorydes = "+(categorydes == null ? "null" : categorydes)+"\n");		sb.append("paymentterm = "+(paymentterm == null ? "null" : paymentterm)+"\n");		sb.append("country = "+(country == null ? "null" : country)+"\n");		sb.append("province = "+(province == null ? "null" : province)+"\n");		sb.append("city = "+(city == null ? "null" : city)+"\n");		sb.append("address = "+(address == null ? "null" : address)+"\n");		sb.append("zipcode = "+(zipcode == null ? "null" : zipcode)+"\n");		sb.append("type = "+(type == null ? "null" : type)+"\n");		sb.append("normaldelivery = "+(normaldelivery == null ? "null" : normaldelivery)+"\n");		sb.append("maxdelivery = "+(maxdelivery == null ? "null" : maxdelivery)+"\n");		sb.append("issues = "+(issues == null ? "null" : issues)+"\n");		sb.append("purchasetype = "+(purchasetype == null ? "null" : purchasetype)+"\n");		sb.append("idusedbefore = "+(idusedbefore == null ? "null" : idusedbefore)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		supplierid= (supplierid == null ?null : supplierid.trim());		shortname= (shortname == null ?null : shortname.trim());		suppliername= (suppliername == null ?null : suppliername.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		categoryid= (categoryid == null ?null : categoryid.trim());		categorydes= (categorydes == null ?null : categorydes.trim());		paymentterm= (paymentterm == null ?null : paymentterm.trim());		country= (country == null ?null : country.trim());		province= (province == null ?null : province.trim());		city= (city == null ?null : city.trim());		address= (address == null ?null : address.trim());		zipcode= (zipcode == null ?null : zipcode.trim());		type= (type == null ?null : type.trim());		normaldelivery= (normaldelivery == null ?null : normaldelivery.trim());		maxdelivery= (maxdelivery == null ?null : maxdelivery.trim());		issues= (issues == null ?null : issues.trim());		purchasetype= (purchasetype == null ?null : purchasetype.trim());		idusedbefore= (idusedbefore == null ?null : idusedbefore.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
