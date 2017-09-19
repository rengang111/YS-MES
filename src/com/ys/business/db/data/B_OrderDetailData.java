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
public class B_OrderDetailData implements java.io.Serializable
{

	public B_OrderDetailData()
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
	private String piid;
	public String getPiid()
	{
		return this.piid;
	}
	public void setPiid(String piid)
	{
		this.piid=piid;
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
	private String totalquantity;
	public String getTotalquantity()
	{
		return this.totalquantity;
	}
	public void setTotalquantity(String totalquantity)
	{
		this.totalquantity=totalquantity;
	}

	/**
	*
	*/
	private String returnquantity;
	public String getReturnquantity()
	{
		return this.returnquantity;
	}
	public void setReturnquantity(String returnquantity)
	{
		this.returnquantity=returnquantity;
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
	private String productclassify;
	public String getProductclassify()
	{
		return this.productclassify;
	}
	public void setProductclassify(String productclassify)
	{
		this.productclassify=productclassify;
	}

	/**
	*
	*/
	private String ordertype;
	public String getOrdertype()
	{
		return this.ordertype;
	}
	public void setOrdertype(String ordertype)
	{
		this.ordertype=ordertype;
	}

	/**
	*
	*/
	private String payment;
	public String getPayment()
	{
		return this.payment;
	}
	public void setPayment(String payment)
	{
		this.payment=payment;
	}

	/**
	*
	*/
	private String receipt;
	public String getReceipt()
	{
		return this.receipt;
	}
	public void setReceipt(String receipt)
	{
		this.receipt=receipt;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("piid = "+(piid == null ? "null" : piid)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("quantity = "+(quantity == null ? "null" : quantity)+"\n");		sb.append("totalquantity = "+(totalquantity == null ? "null" : totalquantity)+"\n");		sb.append("returnquantity = "+(returnquantity == null ? "null" : returnquantity)+"\n");		sb.append("price = "+(price == null ? "null" : price)+"\n");		sb.append("totalprice = "+(totalprice == null ? "null" : totalprice)+"\n");		sb.append("status = "+(status == null ? "null" : status)+"\n");		sb.append("productclassify = "+(productclassify == null ? "null" : productclassify)+"\n");		sb.append("ordertype = "+(ordertype == null ? "null" : ordertype)+"\n");		sb.append("payment = "+(payment == null ? "null" : payment)+"\n");		sb.append("receipt = "+(receipt == null ? "null" : receipt)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		ysid= (ysid == null ?null : ysid.trim());		piid= (piid == null ?null : piid.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		materialid= (materialid == null ?null : materialid.trim());		quantity= (quantity == null ?null : quantity.trim());		totalquantity= (totalquantity == null ?null : totalquantity.trim());		returnquantity= (returnquantity == null ?null : returnquantity.trim());		price= (price == null ?null : price.trim());		totalprice= (totalprice == null ?null : totalprice.trim());		status= (status == null ?null : status.trim());		productclassify= (productclassify == null ?null : productclassify.trim());		ordertype= (ordertype == null ?null : ordertype.trim());		payment= (payment == null ?null : payment.trim());		receipt= (receipt == null ?null : receipt.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
