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
public class YW_PROJ_INFOData implements java.io.Serializable
{

	public YW_PROJ_INFOData()
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
	private String proj_code;
	public String getProj_code()
	{
		return this.proj_code;
	}
	public void setProj_code(String proj_code)
	{
		this.proj_code=proj_code;
	}

	/**
	*
	*/
	private String proj_name;
	public String getProj_name()
	{
		return this.proj_name;
	}
	public void setProj_name(String proj_name)
	{
		this.proj_name=proj_name;
	}

	/**
	*
	*/
	private String proj_name_short;
	public String getProj_name_short()
	{
		return this.proj_name_short;
	}
	public void setProj_name_short(String proj_name_short)
	{
		this.proj_name_short=proj_name_short;
	}

	/**
	*
	*/
	private String prov_code;
	public String getProv_code()
	{
		return this.prov_code;
	}
	public void setProv_code(String prov_code)
	{
		this.prov_code=prov_code;
	}

	/**
	*
	*/
	private String proj_pm;
	public String getProj_pm()
	{
		return this.proj_pm;
	}
	public void setProj_pm(String proj_pm)
	{
		this.proj_pm=proj_pm;
	}

	/**
	*
	*/
	private String ref_prot;
	public String getRef_prot()
	{
		return this.ref_prot;
	}
	public void setRef_prot(String ref_prot)
	{
		this.ref_prot=ref_prot;
	}

	/**
	*
	*/
	private String sta_date;
	public String getSta_date()
	{
		return this.sta_date;
	}
	public void setSta_date(String sta_date)
	{
		this.sta_date=sta_date;
	}

	/**
	*
	*/
	private String end_date;
	public String getEnd_date()
	{
		return this.end_date;
	}
	public void setEnd_date(String end_date)
	{
		this.end_date=end_date;
	}

	/**
	*
	*/
	private String pack_info;
	public String getPack_info()
	{
		return this.pack_info;
	}
	public void setPack_info(String pack_info)
	{
		this.pack_info=pack_info;
	}

	/**
	*
	*/
	private String performs;
	public String getPerforms()
	{
		return this.performs;
	}
	public void setPerforms(String performs)
	{
		this.performs=performs;
	}

	/**
	*
	*/
	private Float est_cost;
	public Float getEst_cost()
	{
		return this.est_cost;
	}
	public void setEst_cost(Float est_cost)
	{
		this.est_cost=est_cost;
	}

	/**
	*
	*/
	private Float inv_total;
	public Float getInv_total()
	{
		return this.inv_total;
	}
	public void setInv_total(Float inv_total)
	{
		this.inv_total=inv_total;
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
	private String fail_note;
	public String getFail_note()
	{
		return this.fail_note;
	}
	public void setFail_note(String fail_note)
	{
		this.fail_note=fail_note;
	}

	/**
	*
	*/
	private Float sell_price;
	public Float getSell_price()
	{
		return this.sell_price;
	}
	public void setSell_price(Float sell_price)
	{
		this.sell_price=sell_price;
	}

	/**
	*
	*/
	private int expect_sales;
	public int getExpect_sales()
	{
		return this.expect_sales;
	}
	public void setExpect_sales(int expect_sales)
	{
		this.expect_sales=expect_sales;
	}

	/**
	*
	*/
	private int payback_qty;
	public int getPayback_qty()
	{
		return this.payback_qty;
	}
	public void setPayback_qty(int payback_qty)
	{
		this.payback_qty=payback_qty;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("proj_code = "+(proj_code == null ? "null" : proj_code)+"\n");		sb.append("proj_name = "+(proj_name == null ? "null" : proj_name)+"\n");		sb.append("proj_name_short = "+(proj_name_short == null ? "null" : proj_name_short)+"\n");		sb.append("prov_code = "+(prov_code == null ? "null" : prov_code)+"\n");		sb.append("proj_pm = "+(proj_pm == null ? "null" : proj_pm)+"\n");		sb.append("ref_prot = "+(ref_prot == null ? "null" : ref_prot)+"\n");		sb.append("sta_date = "+(sta_date == null ? "null" : sta_date)+"\n");		sb.append("end_date = "+(end_date == null ? "null" : end_date)+"\n");		sb.append("pack_info = "+(pack_info == null ? "null" : pack_info)+"\n");		sb.append("performs = "+(performs == null ? "null" : performs)+"\n");		sb.append("est_cost = "+(est_cost == null ? "null" : est_cost.toString())+"\n");		sb.append("inv_total = "+(inv_total == null ? "null" : inv_total.toString())+"\n");		sb.append("status = "+(status == null ? "null" : status)+"\n");		sb.append("fail_note = "+(fail_note == null ? "null" : fail_note)+"\n");		sb.append("sell_price = "+(sell_price == null ? "null" : sell_price.toString())+"\n");		sb.append("expect_sales = "+expect_sales+"\n");		sb.append("payback_qty = "+payback_qty+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		proj_code= (proj_code == null ?null : proj_code.trim());		proj_name= (proj_name == null ?null : proj_name.trim());		proj_name_short= (proj_name_short == null ?null : proj_name_short.trim());		prov_code= (prov_code == null ?null : prov_code.trim());		proj_pm= (proj_pm == null ?null : proj_pm.trim());		ref_prot= (ref_prot == null ?null : ref_prot.trim());		sta_date= (sta_date == null ?null : sta_date.trim());		end_date= (end_date == null ?null : end_date.trim());		pack_info= (pack_info == null ?null : pack_info.trim());		performs= (performs == null ?null : performs.trim());		status= (status == null ?null : status.trim());		fail_note= (fail_note == null ?null : fail_note.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
