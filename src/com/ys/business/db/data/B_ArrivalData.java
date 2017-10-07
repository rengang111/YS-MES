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
public class B_ArrivalData implements java.io.Serializable
{

	public B_ArrivalData()
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
	private String arrivalid;
	public String getArrivalid()
	{
		return this.arrivalid;
	}
	public void setArrivalid(String arrivalid)
	{
		this.arrivalid=arrivalid;
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
	private String arrivedate;
	public String getArrivedate()
	{
		return this.arrivedate;
	}
	public void setArrivedate(String arrivedate)
	{
		this.arrivedate=arrivedate;
	}

	/**
	*
	*/
	private String userid;
	public String getUserid()
	{
		return this.userid;
	}
	public void setUserid(String userid)
	{
		this.userid=userid;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("arrivalid = "+(arrivalid == null ? "null" : arrivalid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("contractid = "+(contractid == null ? "null" : contractid)+"\n");		sb.append("supplierid = "+(supplierid == null ? "null" : supplierid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("status = "+(status == null ? "null" : status)+"\n");		sb.append("quantity = "+(quantity == null ? "null" : quantity)+"\n");		sb.append("arrivedate = "+(arrivedate == null ? "null" : arrivedate)+"\n");		sb.append("userid = "+(userid == null ? "null" : userid)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		arrivalid= (arrivalid == null ?null : arrivalid.trim());		ysid= (ysid == null ?null : ysid.trim());		contractid= (contractid == null ?null : contractid.trim());		supplierid= (supplierid == null ?null : supplierid.trim());		materialid= (materialid == null ?null : materialid.trim());		status= (status == null ?null : status.trim());		quantity= (quantity == null ?null : quantity.trim());		arrivedate= (arrivedate == null ?null : arrivedate.trim());		userid= (userid == null ?null : userid.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
