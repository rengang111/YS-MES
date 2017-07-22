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
	private String image_filename2;
	public String getImage_filename2()
	{
		return this.image_filename2;
	}
	public void setImage_filename2(String image_filename2)
	{
		this.image_filename2=image_filename2;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("productdetailid = "+(productdetailid == null ? "null" : productdetailid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("productid = "+(productid == null ? "null" : productid)+"\n");		sb.append("sealedsample = "+(sealedsample == null ? "null" : sealedsample)+"\n");		sb.append("batterypack = "+(batterypack == null ? "null" : batterypack)+"\n");		sb.append("chargertype = "+(chargertype == null ? "null" : chargertype)+"\n");		sb.append("packagedescription = "+(packagedescription == null ? "null" : packagedescription)+"\n");		sb.append("storagedescription = "+(storagedescription == null ? "null" : storagedescription)+"\n");		sb.append("image_filename = "+(image_filename == null ? "null" : image_filename)+"\n");		sb.append("image_filename2 = "+(image_filename2 == null ? "null" : image_filename2)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		productdetailid= (productdetailid == null ?null : productdetailid.trim());		subid= (subid == null ?null : subid.trim());		ysid= (ysid == null ?null : ysid.trim());		productid= (productid == null ?null : productid.trim());		sealedsample= (sealedsample == null ?null : sealedsample.trim());		batterypack= (batterypack == null ?null : batterypack.trim());		chargertype= (chargertype == null ?null : chargertype.trim());		packagedescription= (packagedescription == null ?null : packagedescription.trim());		storagedescription= (storagedescription == null ?null : storagedescription.trim());		image_filename= (image_filename == null ?null : image_filename.trim());		image_filename2= (image_filename2 == null ?null : image_filename2.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
