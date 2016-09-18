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
public class YW_MOLD_INFOData implements java.io.Serializable
{

	public YW_MOLD_INFOData()
	{
	}

	/**
	*
	*/
	private String mold_id;
	public String getMold_id()
	{
		return this.mold_id;
	}
	public void setMold_id(String mold_id)
	{
		this.mold_id=mold_id;
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
	private String prod_name;
	public String getProd_name()
	{
		return this.prod_name;
	}
	public void setProd_name(String prod_name)
	{
		this.prod_name=prod_name;
	}

	/**
	*
	*/
	private String supplier;
	public String getSupplier()
	{
		return this.supplier;
	}
	public void setSupplier(String supplier)
	{
		this.supplier=supplier;
	}

	/**
	*
	*/
	private String contract;
	public String getContract()
	{
		return this.contract;
	}
	public void setContract(String contract)
	{
		this.contract=contract;
	}

	/**
	*
	*/
	private String cont_sta_date;
	public String getCont_sta_date()
	{
		return this.cont_sta_date;
	}
	public void setCont_sta_date(String cont_sta_date)
	{
		this.cont_sta_date=cont_sta_date;
	}

	/**
	*
	*/
	private String cont_end_date;
	public String getCont_end_date()
	{
		return this.cont_end_date;
	}
	public void setCont_end_date(String cont_end_date)
	{
		this.cont_end_date=cont_end_date;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("mold_id = "+(mold_id == null ? "null" : mold_id)+"\n");		sb.append("prod_code = "+(prod_code == null ? "null" : prod_code)+"\n");		sb.append("prod_name = "+(prod_name == null ? "null" : prod_name)+"\n");		sb.append("supplier = "+(supplier == null ? "null" : supplier)+"\n");		sb.append("contract = "+(contract == null ? "null" : contract)+"\n");		sb.append("cont_sta_date = "+(cont_sta_date == null ? "null" : cont_sta_date)+"\n");		sb.append("cont_end_date = "+(cont_end_date == null ? "null" : cont_end_date)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		mold_id= (mold_id == null ?null : mold_id.trim());		prod_code= (prod_code == null ?null : prod_code.trim());		prod_name= (prod_name == null ?null : prod_name.trim());		supplier= (supplier == null ?null : supplier.trim());		contract= (contract == null ?null : contract.trim());		cont_sta_date= (cont_sta_date == null ?null : cont_sta_date.trim());		cont_end_date= (cont_end_date == null ?null : cont_end_date.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
