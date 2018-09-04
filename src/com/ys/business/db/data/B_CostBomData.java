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
public class B_CostBomData implements java.io.Serializable
{

	public B_CostBomData()
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
	private String currency;
	public String getCurrency()
	{
		return this.currency;
	}
	public void setCurrency(String currency)
	{
		this.currency=currency;
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
	private String labolcost;
	public String getLabolcost()
	{
		return this.labolcost;
	}
	public void setLabolcost(String labolcost)
	{
		this.labolcost=labolcost;
	}

	/**
	*
	*/
	private String materialcost;
	public String getMaterialcost()
	{
		return this.materialcost;
	}
	public void setMaterialcost(String materialcost)
	{
		this.materialcost=materialcost;
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
	private String vat;
	public String getVat()
	{
		return this.vat;
	}
	public void setVat(String vat)
	{
		this.vat=vat;
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
	private String profitrate;
	public String getProfitrate()
	{
		return this.profitrate;
	}
	public void setProfitrate(String profitrate)
	{
		this.profitrate=profitrate;
	}

	/**
	*
	*/
	private String profit;
	public String getProfit()
	{
		return this.profit;
	}
	public void setProfit(String profit)
	{
		this.profit=profit;
	}

	/**
	*
	*/
	private String deduct;
	public String getDeduct()
	{
		return this.deduct;
	}
	public void setDeduct(String deduct)
	{
		this.deduct=deduct;
	}

	/**
	*
	*/
	private String gross;
	public String getGross()
	{
		return this.gross;
	}
	public void setGross(String gross)
	{
		this.gross=gross;
	}

	/**
	*
	*/
	private String accountingdate;
	public String getAccountingdate()
	{
		return this.accountingdate;
	}
	public void setAccountingdate(String accountingdate)
	{
		this.accountingdate=accountingdate;
	}

	/**
	*
	*/
	private String discount;
	public String getDiscount()
	{
		return this.discount;
	}
	public void setDiscount(String discount)
	{
		this.discount=discount;
	}

	/**
	*
	*/
	private String commission;
	public String getCommission()
	{
		return this.commission;
	}
	public void setCommission(String commission)
	{
		this.commission=commission;
	}

	/**
	*
	*/
	private String actualsales;
	public String getActualsales()
	{
		return this.actualsales;
	}
	public void setActualsales(String actualsales)
	{
		this.actualsales=actualsales;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("bomid = "+(bomid == null ? "null" : bomid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("currency = "+(currency == null ? "null" : currency)+"\n");		sb.append("exchangerate = "+(exchangerate == null ? "null" : exchangerate)+"\n");		sb.append("labolcost = "+(labolcost == null ? "null" : labolcost)+"\n");		sb.append("materialcost = "+(materialcost == null ? "null" : materialcost)+"\n");		sb.append("cost = "+(cost == null ? "null" : cost)+"\n");		sb.append("vat = "+(vat == null ? "null" : vat)+"\n");		sb.append("rebate = "+(rebate == null ? "null" : rebate)+"\n");		sb.append("rebaterate = "+(rebaterate == null ? "null" : rebaterate)+"\n");		sb.append("rmbprice = "+(rmbprice == null ? "null" : rmbprice)+"\n");		sb.append("profitrate = "+(profitrate == null ? "null" : profitrate)+"\n");		sb.append("profit = "+(profit == null ? "null" : profit)+"\n");		sb.append("deduct = "+(deduct == null ? "null" : deduct)+"\n");		sb.append("gross = "+(gross == null ? "null" : gross)+"\n");		sb.append("accountingdate = "+(accountingdate == null ? "null" : accountingdate)+"\n");		sb.append("discount = "+(discount == null ? "null" : discount)+"\n");		sb.append("commission = "+(commission == null ? "null" : commission)+"\n");		sb.append("actualsales = "+(actualsales == null ? "null" : actualsales)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		bomid= (bomid == null ?null : bomid.trim());		ysid= (ysid == null ?null : ysid.trim());		materialid= (materialid == null ?null : materialid.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		currency= (currency == null ?null : currency.trim());		exchangerate= (exchangerate == null ?null : exchangerate.trim());		labolcost= (labolcost == null ?null : labolcost.trim());		materialcost= (materialcost == null ?null : materialcost.trim());		cost= (cost == null ?null : cost.trim());		vat= (vat == null ?null : vat.trim());		rebate= (rebate == null ?null : rebate.trim());		rebaterate= (rebaterate == null ?null : rebaterate.trim());		rmbprice= (rmbprice == null ?null : rmbprice.trim());		profitrate= (profitrate == null ?null : profitrate.trim());		profit= (profit == null ?null : profit.trim());		deduct= (deduct == null ?null : deduct.trim());		gross= (gross == null ?null : gross.trim());		accountingdate= (accountingdate == null ?null : accountingdate.trim());		discount= (discount == null ?null : discount.trim());		commission= (commission == null ?null : commission.trim());		actualsales= (actualsales == null ?null : actualsales.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
