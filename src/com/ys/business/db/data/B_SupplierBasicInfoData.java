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
public class B_SupplierBasicInfoData implements java.io.Serializable
{

	public B_SupplierBasicInfoData()
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
	private String suppliersimpledes;
	public String getSuppliersimpledes()
	{
		return this.suppliersimpledes;
	}
	public void setSuppliersimpledes(String suppliersimpledes)
	{
		this.suppliersimpledes=suppliersimpledes;
	}

	/**
	*
	*/
	private String supplierdes;
	public String getSupplierdes()
	{
		return this.supplierdes;
	}
	public void setSupplierdes(String supplierdes)
	{
		this.supplierdes=supplierdes;
	}

	/**
	*
	*/
	private String twolevelid;
	public String getTwolevelid()
	{
		return this.twolevelid;
	}
	public void setTwolevelid(String twolevelid)
	{
		this.twolevelid=twolevelid;
	}

	/**
	*
	*/
	private String twoleveliddes;
	public String getTwoleveliddes()
	{
		return this.twoleveliddes;
	}
	public void setTwoleveliddes(String twoleveliddes)
	{
		this.twoleveliddes=twoleveliddes;
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
	*返回值
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
	*返回值
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("supplierid = "+(supplierid == null ? "null" : supplierid)+"\n");		sb.append("suppliersimpledes = "+(suppliersimpledes == null ? "null" : suppliersimpledes)+"\n");		sb.append("supplierdes = "+(supplierdes == null ? "null" : supplierdes)+"\n");		sb.append("twolevelid = "+(twolevelid == null ? "null" : twolevelid)+"\n");		sb.append("twoleveliddes = "+(twoleveliddes == null ? "null" : twoleveliddes)+"\n");		sb.append("paymentterm = "+(paymentterm == null ? "null" : paymentterm)+"\n");		sb.append("country = "+(country == null ? "null" : country)+"\n");		sb.append("province = "+(province == null ? "null" : province)+"\n");		sb.append("city = "+(city == null ? "null" : city)+"\n");		sb.append("address = "+(address == null ? "null" : address)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		supplierid= (supplierid == null ?null : supplierid.trim());		suppliersimpledes= (suppliersimpledes == null ?null : suppliersimpledes.trim());		supplierdes= (supplierdes == null ?null : supplierdes.trim());		twolevelid= (twolevelid == null ?null : twolevelid.trim());		twoleveliddes= (twoleveliddes == null ?null : twoleveliddes.trim());		paymentterm= (paymentterm == null ?null : paymentterm.trim());		country= (country == null ?null : country.trim());		province= (province == null ?null : province.trim());		city= (city == null ?null : city.trim());		address= (address == null ?null : address.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
