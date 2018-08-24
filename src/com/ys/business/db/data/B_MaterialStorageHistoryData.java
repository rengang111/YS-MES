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
public class B_MaterialStorageHistoryData implements java.io.Serializable
{

	public B_MaterialStorageHistoryData()
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
	private String serialnumber;
	public String getSerialnumber()
	{
		return this.serialnumber;
	}
	public void setSerialnumber(String serialnumber)
	{
		this.serialnumber=serialnumber;
	}

	/**
	*
	*/
	private String materialname;
	public String getMaterialname()
	{
		return this.materialname;
	}
	public void setMaterialname(String materialname)
	{
		this.materialname=materialname;
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
	private String description;
	public String getDescription()
	{
		return this.description;
	}
	public void setDescription(String description)
	{
		this.description=description;
	}

	/**
	*
	*/
	private String sharemodel;
	public String getSharemodel()
	{
		return this.sharemodel;
	}
	public void setSharemodel(String sharemodel)
	{
		this.sharemodel=sharemodel;
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
	private String subiddes;
	public String getSubiddes()
	{
		return this.subiddes;
	}
	public void setSubiddes(String subiddes)
	{
		this.subiddes=subiddes;
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
	private String customerid;
	public String getCustomerid()
	{
		return this.customerid;
	}
	public void setCustomerid(String customerid)
	{
		this.customerid=customerid;
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
	private String originalid;
	public String getOriginalid()
	{
		return this.originalid;
	}
	public void setOriginalid(String originalid)
	{
		this.originalid=originalid;
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
	private String maprice;
	public String getMaprice()
	{
		return this.maprice;
	}
	public void setMaprice(String maprice)
	{
		this.maprice=maprice;
	}

	/**
	*
	*/
	private String beginningwaitin;
	public String getBeginningwaitin()
	{
		return this.beginningwaitin;
	}
	public void setBeginningwaitin(String beginningwaitin)
	{
		this.beginningwaitin=beginningwaitin;
	}

	/**
	*
	*/
	private String beginningwaitout;
	public String getBeginningwaitout()
	{
		return this.beginningwaitout;
	}
	public void setBeginningwaitout(String beginningwaitout)
	{
		this.beginningwaitout=beginningwaitout;
	}

	/**
	*
	*/
	private String beginningavailabel;
	public String getBeginningavailabel()
	{
		return this.beginningavailabel;
	}
	public void setBeginningavailabel(String beginningavailabel)
	{
		this.beginningavailabel=beginningavailabel;
	}

	/**
	*
	*/
	private String beginninginventory;
	public String getBeginninginventory()
	{
		return this.beginninginventory;
	}
	public void setBeginninginventory(String beginninginventory)
	{
		this.beginninginventory=beginninginventory;
	}

	/**
	*
	*/
	private String beginningprice;
	public String getBeginningprice()
	{
		return this.beginningprice;
	}
	public void setBeginningprice(String beginningprice)
	{
		this.beginningprice=beginningprice;
	}

	/**
	*
	*/
	private String quantityeditflag;
	public String getQuantityeditflag()
	{
		return this.quantityeditflag;
	}
	public void setQuantityeditflag(String quantityeditflag)
	{
		this.quantityeditflag=quantityeditflag;
	}

	/**
	*
	*/
	private String stocktype;
	public String getStocktype()
	{
		return this.stocktype;
	}
	public void setStocktype(String stocktype)
	{
		this.stocktype=stocktype;
	}

	/**
	*
	*/
	private String actioncontent;
	public String getActioncontent()
	{
		return this.actioncontent;
	}
	public void setActioncontent(String actioncontent)
	{
		this.actioncontent=actioncontent;
	}

	/**
	*
	*/
	private String changequantity;
	public String getChangequantity()
	{
		return this.changequantity;
	}
	public void setChangequantity(String changequantity)
	{
		this.changequantity=changequantity;
	}

	/**
	*
	*/
	private String copytime;
	public String getCopytime()
	{
		return this.copytime;
	}
	public void setCopytime(String copytime)
	{
		this.copytime=copytime;
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