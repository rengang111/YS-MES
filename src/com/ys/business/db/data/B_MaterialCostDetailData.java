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
public class B_MaterialCostDetailData implements java.io.Serializable
{

	public B_MaterialCostDetailData()
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
	private String sortno;
	public String getSortno()
	{
		return this.sortno;
	}
	public void setSortno(String sortno)
	{
		this.sortno=sortno;
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
	private String cost;
	public String getCost()
	{
		return this.cost;
	}
	public void setCost(String cost)
	{
		this.cost=cost;
	}

	/**
	*
	*/
	private String costname;
	public String getCostname()
	{
		return this.costname;
	}
	public void setCostname(String costname)
	{
		this.costname=costname;
	}

	/**
	*
	*/
	private String costtype;
	public String getCosttype()
	{
		return this.costtype;
	}
	public void setCosttype(String costtype)
	{
		this.costtype=costtype;
	}

	/**
	*
	*/
	private String remarks;
	public String getRemarks()
	{
		return this.remarks;
	}
	public void setRemarks(String remarks)
	{
		this.remarks=remarks;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("sortno = "+(sortno == null ? "null" : sortno)+"\n");		sb.append("quantity = "+(quantity == null ? "null" : quantity)+"\n");		sb.append("price = "+(price == null ? "null" : price)+"\n");		sb.append("cost = "+(cost == null ? "null" : cost)+"\n");		sb.append("costname = "+(costname == null ? "null" : costname)+"\n");		sb.append("costtype = "+(costtype == null ? "null" : costtype)+"\n");		sb.append("remarks = "+(remarks == null ? "null" : remarks)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		materialid= (materialid == null ?null : materialid.trim());		sortno= (sortno == null ?null : sortno.trim());		quantity= (quantity == null ?null : quantity.trim());		price= (price == null ?null : price.trim());		cost= (cost == null ?null : cost.trim());		costname= (costname == null ?null : costname.trim());		costtype= (costtype == null ?null : costtype.trim());		remarks= (remarks == null ?null : remarks.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
