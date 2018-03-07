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
public class B_InventoryMonthlyReportData implements java.io.Serializable
{

	public B_InventoryMonthlyReportData()
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
	private String reportid;
	public String getReportid()
	{
		return this.reportid;
	}
	public void setReportid(String reportid)
	{
		this.reportid=reportid;
	}

	/**
	*
	*/
	private String monthly;
	public String getMonthly()
	{
		return this.monthly;
	}
	public void setMonthly(String monthly)
	{
		this.monthly=monthly;
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
	private String beginningqty;
	public String getBeginningqty()
	{
		return this.beginningqty;
	}
	public void setBeginningqty(String beginningqty)
	{
		this.beginningqty=beginningqty;
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
	private String stockinqty;
	public String getStockinqty()
	{
		return this.stockinqty;
	}
	public void setStockinqty(String stockinqty)
	{
		this.stockinqty=stockinqty;
	}

	/**
	*
	*/
	private String stockinprice;
	public String getStockinprice()
	{
		return this.stockinprice;
	}
	public void setStockinprice(String stockinprice)
	{
		this.stockinprice=stockinprice;
	}

	/**
	*
	*/
	private String stockoutqty;
	public String getStockoutqty()
	{
		return this.stockoutqty;
	}
	public void setStockoutqty(String stockoutqty)
	{
		this.stockoutqty=stockoutqty;
	}

	/**
	*
	*/
	private String stockoutprice;
	public String getStockoutprice()
	{
		return this.stockoutprice;
	}
	public void setStockoutprice(String stockoutprice)
	{
		this.stockoutprice=stockoutprice;
	}

	/**
	*
	*/
	private String balanceqty;
	public String getBalanceqty()
	{
		return this.balanceqty;
	}
	public void setBalanceqty(String balanceqty)
	{
		this.balanceqty=balanceqty;
	}

	/**
	*
	*/
	private String balanceprice;
	public String getBalanceprice()
	{
		return this.balanceprice;
	}
	public void setBalanceprice(String balanceprice)
	{
		this.balanceprice=balanceprice;
	}

	/**
	*
	*/
	private String startdate;
	public String getStartdate()
	{
		return this.startdate;
	}
	public void setStartdate(String startdate)
	{
		this.startdate=startdate;
	}

	/**
	*
	*/
	private String enddate;
	public String getEnddate()
	{
		return this.enddate;
	}
	public void setEnddate(String enddate)
	{
		this.enddate=enddate;
	}

	/**
	*
	*/
	private String reportdate;
	public String getReportdate()
	{
		return this.reportdate;
	}
	public void setReportdate(String reportdate)
	{
		this.reportdate=reportdate;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("reportid = "+(reportid == null ? "null" : reportid)+"\n");		sb.append("monthly = "+(monthly == null ? "null" : monthly)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("beginningqty = "+(beginningqty == null ? "null" : beginningqty)+"\n");		sb.append("beginningprice = "+(beginningprice == null ? "null" : beginningprice)+"\n");		sb.append("stockinqty = "+(stockinqty == null ? "null" : stockinqty)+"\n");		sb.append("stockinprice = "+(stockinprice == null ? "null" : stockinprice)+"\n");		sb.append("stockoutqty = "+(stockoutqty == null ? "null" : stockoutqty)+"\n");		sb.append("stockoutprice = "+(stockoutprice == null ? "null" : stockoutprice)+"\n");		sb.append("balanceqty = "+(balanceqty == null ? "null" : balanceqty)+"\n");		sb.append("balanceprice = "+(balanceprice == null ? "null" : balanceprice)+"\n");		sb.append("startdate = "+(startdate == null ? "null" : startdate)+"\n");		sb.append("enddate = "+(enddate == null ? "null" : enddate)+"\n");		sb.append("reportdate = "+(reportdate == null ? "null" : reportdate)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		reportid= (reportid == null ?null : reportid.trim());		monthly= (monthly == null ?null : monthly.trim());		materialid= (materialid == null ?null : materialid.trim());		beginningqty= (beginningqty == null ?null : beginningqty.trim());		beginningprice= (beginningprice == null ?null : beginningprice.trim());		stockinqty= (stockinqty == null ?null : stockinqty.trim());		stockinprice= (stockinprice == null ?null : stockinprice.trim());		stockoutqty= (stockoutqty == null ?null : stockoutqty.trim());		stockoutprice= (stockoutprice == null ?null : stockoutprice.trim());		balanceqty= (balanceqty == null ?null : balanceqty.trim());		balanceprice= (balanceprice == null ?null : balanceprice.trim());		startdate= (startdate == null ?null : startdate.trim());		enddate= (enddate == null ?null : enddate.trim());		reportdate= (reportdate == null ?null : reportdate.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
