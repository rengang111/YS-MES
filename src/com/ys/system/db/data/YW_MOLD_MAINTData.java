package com.ys.system.db.data;

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
public class YW_MOLD_MAINTData implements java.io.Serializable
{

	public YW_MOLD_MAINTData()
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
	private String prod_code;
	public String getProd_code()
	{
		return this.prod_code;
	}
	public void setProd_code(String prod_code)
	{
		this.prod_code=prod_code;
	}

	/**
	*
	*/
	private String mold_code;
	public String getMold_code()
	{
		return this.mold_code;
	}
	public void setMold_code(String mold_code)
	{
		this.mold_code=mold_code;
	}

	/**
	*
	*/
	private String happen_date;
	public String getHappen_date()
	{
		return this.happen_date;
	}
	public void setHappen_date(String happen_date)
	{
		this.happen_date=happen_date;
	}

	/**
	*
	*/
	private String finish_date;
	public String getFinish_date()
	{
		return this.finish_date;
	}
	public void setFinish_date(String finish_date)
	{
		this.finish_date=finish_date;
	}

	/**
	*
	*/
	private Float cost;
	public Float getCost()
	{
		return this.cost;
	}
	public void setCost(Float cost)
	{
		this.cost=cost;
	}

	/**
	*
	*/
	private String serviceman;
	public String getServiceman()
	{
		return this.serviceman;
	}
	public void setServiceman(String serviceman)
	{
		this.serviceman=serviceman;
	}

	/**
	*
	*/
	private String cause;
	public String getCause()
	{
		return this.cause;
	}
	public void setCause(String cause)
	{
		this.cause=cause;
	}

	/**
	*
	*/
	private String proposer;
	public String getProposer()
	{
		return this.proposer;
	}
	public void setProposer(String proposer)
	{
		this.proposer=proposer;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("prod_code = "+(prod_code == null ? "null" : prod_code)+"\n");		sb.append("mold_code = "+(mold_code == null ? "null" : mold_code)+"\n");		sb.append("happen_date = "+(happen_date == null ? "null" : happen_date)+"\n");		sb.append("finish_date = "+(finish_date == null ? "null" : finish_date)+"\n");		sb.append("cost = "+(cost == null ? "null" : cost.toString())+"\n");		sb.append("serviceman = "+(serviceman == null ? "null" : serviceman)+"\n");		sb.append("cause = "+(cause == null ? "null" : cause)+"\n");		sb.append("proposer = "+(proposer == null ? "null" : proposer)+"\n");		sb.append("status = "+(status == null ? "null" : status)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		prod_code= (prod_code == null ?null : prod_code.trim());		mold_code= (mold_code == null ?null : mold_code.trim());		happen_date= (happen_date == null ?null : happen_date.trim());		finish_date= (finish_date == null ?null : finish_date.trim());		serviceman= (serviceman == null ?null : serviceman.trim());		cause= (cause == null ?null : cause.trim());		proposer= (proposer == null ?null : proposer.trim());		status= (status == null ?null : status.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
