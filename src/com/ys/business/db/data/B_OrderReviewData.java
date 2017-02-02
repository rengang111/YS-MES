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
public class B_OrderReviewData implements java.io.Serializable
{

	public B_OrderReviewData()
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
	private String rmbprice;
	public String getRmbprice()
	{
		return this.rmbprice;
	}
	public void setRmbprice(String rmbprice)
	{
		this.rmbprice=rmbprice;
	}

	/**
	*
	*/
	private String salestax;
	public String getSalestax()
	{
		return this.salestax;
	}
	public void setSalestax(String salestax)
	{
		this.salestax=salestax;
	}

	/**
	*
	*/
	private String exchangerate;
	public String getExchangerate()
	{
		return this.exchangerate;
	}
	public void setExchangerate(String exchangerate)
	{
		this.exchangerate=exchangerate;
	}

	/**
	*
	*/
	private String rebate;
	public String getRebate()
	{
		return this.rebate;
	}
	public void setRebate(String rebate)
	{
		this.rebate=rebate;
	}

	/**
	*
	*/
	private String rebaterate;
	public String getRebaterate()
	{
		return this.rebaterate;
	}
	public void setRebaterate(String rebaterate)
	{
		this.rebaterate=rebaterate;
	}

	/**
	*
	*/
	private String salesprofit;
	public String getSalesprofit()
	{
		return this.salesprofit;
	}
	public void setSalesprofit(String salesprofit)
	{
		this.salesprofit=salesprofit;
	}

	/**
	*
	*/
	private String adjustprofit;
	public String getAdjustprofit()
	{
		return this.adjustprofit;
	}
	public void setAdjustprofit(String adjustprofit)
	{
		this.adjustprofit=adjustprofit;
	}

	/**
	*
	*/
	private String totalsalesprofit;
	public String getTotalsalesprofit()
	{
		return this.totalsalesprofit;
	}
	public void setTotalsalesprofit(String totalsalesprofit)
	{
		this.totalsalesprofit=totalsalesprofit;
	}

	/**
	*
	*/
	private String totaladjustprofit;
	public String getTotaladjustprofit()
	{
		return this.totaladjustprofit;
	}
	public void setTotaladjustprofit(String totaladjustprofit)
	{
		this.totaladjustprofit=totaladjustprofit;
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
	private String finishdate;
	public String getFinishdate()
	{
		return this.finishdate;
	}
	public void setFinishdate(String finishdate)
	{
		this.finishdate=finishdate;
	}

	/**
	*
	*/
	private String reviewdate;
	public String getReviewdate()
	{
		return this.reviewdate;
	}
	public void setReviewdate(String reviewdate)
	{
		this.reviewdate=reviewdate;
	}

	/**
	*
	*/
	private String reviewman;
	public String getReviewman()
	{
		return this.reviewman;
	}
	public void setReviewman(String reviewman)
	{
		this.reviewman=reviewman;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("bomid = "+(bomid == null ? "null" : bomid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("rmbprice = "+(rmbprice == null ? "null" : rmbprice)+"\n");		sb.append("salestax = "+(salestax == null ? "null" : salestax)+"\n");		sb.append("exchangerate = "+(exchangerate == null ? "null" : exchangerate)+"\n");		sb.append("rebate = "+(rebate == null ? "null" : rebate)+"\n");		sb.append("rebaterate = "+(rebaterate == null ? "null" : rebaterate)+"\n");		sb.append("salesprofit = "+(salesprofit == null ? "null" : salesprofit)+"\n");		sb.append("adjustprofit = "+(adjustprofit == null ? "null" : adjustprofit)+"\n");		sb.append("totalsalesprofit = "+(totalsalesprofit == null ? "null" : totalsalesprofit)+"\n");		sb.append("totaladjustprofit = "+(totaladjustprofit == null ? "null" : totaladjustprofit)+"\n");		sb.append("status = "+(status == null ? "null" : status)+"\n");		sb.append("finishdate = "+(finishdate == null ? "null" : finishdate)+"\n");		sb.append("reviewdate = "+(reviewdate == null ? "null" : reviewdate)+"\n");		sb.append("reviewman = "+(reviewman == null ? "null" : reviewman)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		ysid= (ysid == null ?null : ysid.trim());		bomid= (bomid == null ?null : bomid.trim());		materialid= (materialid == null ?null : materialid.trim());		rmbprice= (rmbprice == null ?null : rmbprice.trim());		salestax= (salestax == null ?null : salestax.trim());		exchangerate= (exchangerate == null ?null : exchangerate.trim());		rebate= (rebate == null ?null : rebate.trim());		rebaterate= (rebaterate == null ?null : rebaterate.trim());		salesprofit= (salesprofit == null ?null : salesprofit.trim());		adjustprofit= (adjustprofit == null ?null : adjustprofit.trim());		totalsalesprofit= (totalsalesprofit == null ?null : totalsalesprofit.trim());		totaladjustprofit= (totaladjustprofit == null ?null : totaladjustprofit.trim());		status= (status == null ?null : status.trim());		finishdate= (finishdate == null ?null : finishdate.trim());		reviewdate= (reviewdate == null ?null : reviewdate.trim());		reviewman= (reviewman == null ?null : reviewman.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
