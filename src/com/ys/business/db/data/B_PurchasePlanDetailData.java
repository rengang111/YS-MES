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
public class B_PurchasePlanDetailData implements java.io.Serializable
{

	public B_PurchasePlanDetailData()
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
	private String purchaseid;
	public String getPurchaseid()
	{
		return this.purchaseid;
	}
	public void setPurchaseid(String purchaseid)
	{
		this.purchaseid=purchaseid;
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
	private String unitquantity;
	public String getUnitquantity()
	{
		return this.unitquantity;
	}
	public void setUnitquantity(String unitquantity)
	{
		this.unitquantity=unitquantity;
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
	private String suppliershortname;
	public String getSuppliershortname()
	{
		return this.suppliershortname;
	}
	public void setSuppliershortname(String suppliershortname)
	{
		this.suppliershortname=suppliershortname;
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
	private String pricestatus;
	public String getPricestatus()
	{
		return this.pricestatus;
	}
	public void setPricestatus(String pricestatus)
	{
		this.pricestatus=pricestatus;
	}

	/**
	*
	*/
	private String manufacturequantity;
	public String getManufacturequantity()
	{
		return this.manufacturequantity;
	}
	public void setManufacturequantity(String manufacturequantity)
	{
		this.manufacturequantity=manufacturequantity;
	}

	/**
	*
	*/
	private String purchasequantity;
	public String getPurchasequantity()
	{
		return this.purchasequantity;
	}
	public void setPurchasequantity(String purchasequantity)
	{
		this.purchasequantity=purchasequantity;
	}

	/**
	*
	*/
	private String totalrequisition;
	public String getTotalrequisition()
	{
		return this.totalrequisition;
	}
	public void setTotalrequisition(String totalrequisition)
	{
		this.totalrequisition=totalrequisition;
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
	private String subbomno;
	public String getSubbomno()
	{
		return this.subbomno;
	}
	public void setSubbomno(String subbomno)
	{
		this.subbomno=subbomno;
	}

	/**
	*
	*/
	private int version;
	public int getVersion()
	{
		return this.version;
	}
	public void setVersion(int version)
	{
		this.version=version;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("purchaseid = "+(purchaseid == null ? "null" : purchaseid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("purchasetype = "+(purchasetype == null ? "null" : purchasetype)+"\n");		sb.append("unitquantity = "+(unitquantity == null ? "null" : unitquantity)+"\n");		sb.append("supplierid = "+(supplierid == null ? "null" : supplierid)+"\n");		sb.append("suppliershortname = "+(suppliershortname == null ? "null" : suppliershortname)+"\n");		sb.append("price = "+(price == null ? "null" : price)+"\n");		sb.append("pricestatus = "+(pricestatus == null ? "null" : pricestatus)+"\n");		sb.append("manufacturequantity = "+(manufacturequantity == null ? "null" : manufacturequantity)+"\n");		sb.append("purchasequantity = "+(purchasequantity == null ? "null" : purchasequantity)+"\n");		sb.append("totalrequisition = "+(totalrequisition == null ? "null" : totalrequisition)+"\n");		sb.append("totalprice = "+(totalprice == null ? "null" : totalprice)+"\n");		sb.append("subbomno = "+(subbomno == null ? "null" : subbomno)+"\n");		sb.append("version = "+version+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		purchaseid= (purchaseid == null ?null : purchaseid.trim());		ysid= (ysid == null ?null : ysid.trim());		subid= (subid == null ?null : subid.trim());		materialid= (materialid == null ?null : materialid.trim());		purchasetype= (purchasetype == null ?null : purchasetype.trim());		unitquantity= (unitquantity == null ?null : unitquantity.trim());		supplierid= (supplierid == null ?null : supplierid.trim());		suppliershortname= (suppliershortname == null ?null : suppliershortname.trim());		price= (price == null ?null : price.trim());		pricestatus= (pricestatus == null ?null : pricestatus.trim());		manufacturequantity= (manufacturequantity == null ?null : manufacturequantity.trim());		purchasequantity= (purchasequantity == null ?null : purchasequantity.trim());		totalrequisition= (totalrequisition == null ?null : totalrequisition.trim());		totalprice= (totalprice == null ?null : totalprice.trim());		subbomno= (subbomno == null ?null : subbomno.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
