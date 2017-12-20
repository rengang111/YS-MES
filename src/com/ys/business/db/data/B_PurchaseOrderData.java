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
public class B_PurchaseOrderData implements java.io.Serializable
{

	public B_PurchaseOrderData()
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
	private String contractid;
	public String getContractid()
	{
		return this.contractid;
	}
	public void setContractid(String contractid)
	{
		this.contractid=contractid;
	}

	/**
	*
	*/
	private String typeparentid;
	public String getTypeparentid()
	{
		return this.typeparentid;
	}
	public void setTypeparentid(String typeparentid)
	{
		this.typeparentid=typeparentid;
	}

	/**
	*
	*/
	private String typeserial;
	public String getTypeserial()
	{
		return this.typeserial;
	}
	public void setTypeserial(String typeserial)
	{
		this.typeserial=typeserial;
	}

	/**
	*
	*/
	private String supplierparentid;
	public String getSupplierparentid()
	{
		return this.supplierparentid;
	}
	public void setSupplierparentid(String supplierparentid)
	{
		this.supplierparentid=supplierparentid;
	}

	/**
	*
	*/
	private String supplierserial;
	public String getSupplierserial()
	{
		return this.supplierserial;
	}
	public void setSupplierserial(String supplierserial)
	{
		this.supplierserial=supplierserial;
	}

	/**
	*
	*/
	private String total;
	public String getTotal()
	{
		return this.total;
	}
	public void setTotal(String total)
	{
		this.total=total;
	}

	/**
	*
	*/
	private String taxexcluded;
	public String getTaxexcluded()
	{
		return this.taxexcluded;
	}
	public void setTaxexcluded(String taxexcluded)
	{
		this.taxexcluded=taxexcluded;
	}

	/**
	*
	*/
	private String taxes;
	public String getTaxes()
	{
		return this.taxes;
	}
	public void setTaxes(String taxes)
	{
		this.taxes=taxes;
	}

	/**
	*
	*/
	private String taxrate;
	public String getTaxrate()
	{
		return this.taxrate;
	}
	public void setTaxrate(String taxrate)
	{
		this.taxrate=taxrate;
	}

	/**
	*
	*/
	private String purchasedate;
	public String getPurchasedate()
	{
		return this.purchasedate;
	}
	public void setPurchasedate(String purchasedate)
	{
		this.purchasedate=purchasedate;
	}

	/**
	*
	*/
	private String deliverydate;
	public String getDeliverydate()
	{
		return this.deliverydate;
	}
	public void setDeliverydate(String deliverydate)
	{
		this.deliverydate=deliverydate;
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
	private String stockindate;
	public String getStockindate()
	{
		return this.stockindate;
	}
	public void setStockindate(String stockindate)
	{
		this.stockindate=stockindate;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("supplierid = "+(supplierid == null ? "null" : supplierid)+"\n");		sb.append("contractid = "+(contractid == null ? "null" : contractid)+"\n");		sb.append("typeparentid = "+(typeparentid == null ? "null" : typeparentid)+"\n");		sb.append("typeserial = "+(typeserial == null ? "null" : typeserial)+"\n");		sb.append("supplierparentid = "+(supplierparentid == null ? "null" : supplierparentid)+"\n");		sb.append("supplierserial = "+(supplierserial == null ? "null" : supplierserial)+"\n");		sb.append("total = "+(total == null ? "null" : total)+"\n");		sb.append("taxexcluded = "+(taxexcluded == null ? "null" : taxexcluded)+"\n");		sb.append("taxes = "+(taxes == null ? "null" : taxes)+"\n");		sb.append("taxrate = "+(taxrate == null ? "null" : taxrate)+"\n");		sb.append("purchasedate = "+(purchasedate == null ? "null" : purchasedate)+"\n");		sb.append("deliverydate = "+(deliverydate == null ? "null" : deliverydate)+"\n");		sb.append("memo = "+(memo == null ? "null" : memo)+"\n");		sb.append("stockindate = "+(stockindate == null ? "null" : stockindate)+"\n");		sb.append("status = "+(status == null ? "null" : status)+"\n");		sb.append("version = "+version+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		ysid= (ysid == null ?null : ysid.trim());		materialid= (materialid == null ?null : materialid.trim());		supplierid= (supplierid == null ?null : supplierid.trim());		contractid= (contractid == null ?null : contractid.trim());		typeparentid= (typeparentid == null ?null : typeparentid.trim());		typeserial= (typeserial == null ?null : typeserial.trim());		supplierparentid= (supplierparentid == null ?null : supplierparentid.trim());		supplierserial= (supplierserial == null ?null : supplierserial.trim());		total= (total == null ?null : total.trim());		taxexcluded= (taxexcluded == null ?null : taxexcluded.trim());		taxes= (taxes == null ?null : taxes.trim());		taxrate= (taxrate == null ?null : taxrate.trim());		purchasedate= (purchasedate == null ?null : purchasedate.trim());		deliverydate= (deliverydate == null ?null : deliverydate.trim());		memo= (memo == null ?null : memo.trim());		stockindate= (stockindate == null ?null : stockindate.trim());		status= (status == null ?null : status.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
