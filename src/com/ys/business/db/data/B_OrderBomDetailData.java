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
public class B_OrderBomDetailData implements java.io.Serializable
{

	public B_OrderBomDetailData()
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
	private String bomid;
	public String getBomid()
	{
		return this.bomid;
	}
	public void setBomid(String bomid)
	{
		this.bomid=bomid;
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
	private String productmodel;
	public String getProductmodel()
	{
		return this.productmodel;
	}
	public void setProductmodel(String productmodel)
	{
		this.productmodel=productmodel;
	}

	/**
	*
	*/
	private String quantity;
	public String getQuantity()
	{
		return this.quantity;
	}
	public void setQuantity(String quantity)
	{
		this.quantity=quantity;
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
	private String sourceprice;
	public String getSourceprice()
	{
		return this.sourceprice;
	}
	public void setSourceprice(String sourceprice)
	{
		this.sourceprice=sourceprice;
	}

	/**
	*
	*/
	private String subbomid;
	public String getSubbomid()
	{
		return this.subbomid;
	}
	public void setSubbomid(String subbomid)
	{
		this.subbomid=subbomid;
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
	private String subbomserial;
	public String getSubbomserial()
	{
		return this.subbomserial;
	}
	public void setSubbomserial(String subbomserial)
	{
		this.subbomserial=subbomserial;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("bomid = "+(bomid == null ? "null" : bomid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("supplierid = "+(supplierid == null ? "null" : supplierid)+"\n");		sb.append("productmodel = "+(productmodel == null ? "null" : productmodel)+"\n");		sb.append("quantity = "+(quantity == null ? "null" : quantity)+"\n");		sb.append("price = "+(price == null ? "null" : price)+"\n");		sb.append("totalprice = "+(totalprice == null ? "null" : totalprice)+"\n");		sb.append("sourceprice = "+(sourceprice == null ? "null" : sourceprice)+"\n");		sb.append("subbomid = "+(subbomid == null ? "null" : subbomid)+"\n");		sb.append("subbomno = "+(subbomno == null ? "null" : subbomno)+"\n");		sb.append("subbomserial = "+(subbomserial == null ? "null" : subbomserial)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		bomid= (bomid == null ?null : bomid.trim());		materialid= (materialid == null ?null : materialid.trim());		supplierid= (supplierid == null ?null : supplierid.trim());		productmodel= (productmodel == null ?null : productmodel.trim());		quantity= (quantity == null ?null : quantity.trim());		price= (price == null ?null : price.trim());		totalprice= (totalprice == null ?null : totalprice.trim());		sourceprice= (sourceprice == null ?null : sourceprice.trim());		subbomid= (subbomid == null ?null : subbomid.trim());		subbomno= (subbomno == null ?null : subbomno.trim());		subbomserial= (subbomserial == null ?null : subbomserial.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
