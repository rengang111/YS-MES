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
public class B_BomPlanData implements java.io.Serializable
{

	public B_BomPlanData()
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
	private String orderquantity;
	public String getOrderquantity()
	{
		return this.orderquantity;
	}
	public void setOrderquantity(String orderquantity)
	{
		this.orderquantity=orderquantity;
	}

	/**
	*
	*/
	private String managementcostrate;
	public String getManagementcostrate()
	{
		return this.managementcostrate;
	}
	public void setManagementcostrate(String managementcostrate)
	{
		this.managementcostrate=managementcostrate;
	}

	/**
	*
	*/
	private String managementcost;
	public String getManagementcost()
	{
		return this.managementcost;
	}
	public void setManagementcost(String managementcost)
	{
		this.managementcost=managementcost;
	}

	/**
	*
	*/
	private String bomcost;
	public String getBomcost()
	{
		return this.bomcost;
	}
	public void setBomcost(String bomcost)
	{
		this.bomcost=bomcost;
	}

	/**
	*
	*/
	private String productcost;
	public String getProductcost()
	{
		return this.productcost;
	}
	public void setProductcost(String productcost)
	{
		this.productcost=productcost;
	}

	/**
	*
	*/
	private String totalcost;
	public String getTotalcost()
	{
		return this.totalcost;
	}
	public void setTotalcost(String totalcost)
	{
		this.totalcost=totalcost;
	}

	/**
	*
	*/
	private String laborcost;
	public String getLaborcost()
	{
		return this.laborcost;
	}
	public void setLaborcost(String laborcost)
	{
		this.laborcost=laborcost;
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
	private String plandate;
	public String getPlandate()
	{
		return this.plandate;
	}
	public void setPlandate(String plandate)
	{
		this.plandate=plandate;
	}

	/**
	*
	*/
	private String sourcebomid;
	public String getSourcebomid()
	{
		return this.sourcebomid;
	}
	public void setSourcebomid(String sourcebomid)
	{
		this.sourcebomid=sourcebomid;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("bomid = "+(bomid == null ? "null" : bomid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("orderquantity = "+(orderquantity == null ? "null" : orderquantity)+"\n");		sb.append("managementcostrate = "+(managementcostrate == null ? "null" : managementcostrate)+"\n");		sb.append("managementcost = "+(managementcost == null ? "null" : managementcost)+"\n");		sb.append("bomcost = "+(bomcost == null ? "null" : bomcost)+"\n");		sb.append("productcost = "+(productcost == null ? "null" : productcost)+"\n");		sb.append("totalcost = "+(totalcost == null ? "null" : totalcost)+"\n");		sb.append("laborcost = "+(laborcost == null ? "null" : laborcost)+"\n");		sb.append("materialcost = "+(materialcost == null ? "null" : materialcost)+"\n");		sb.append("plandate = "+(plandate == null ? "null" : plandate)+"\n");		sb.append("sourcebomid = "+(sourcebomid == null ? "null" : sourcebomid)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		ysid= (ysid == null ?null : ysid.trim());		bomid= (bomid == null ?null : bomid.trim());		materialid= (materialid == null ?null : materialid.trim());		subid= (subid == null ?null : subid.trim());		orderquantity= (orderquantity == null ?null : orderquantity.trim());		managementcostrate= (managementcostrate == null ?null : managementcostrate.trim());		managementcost= (managementcost == null ?null : managementcost.trim());		bomcost= (bomcost == null ?null : bomcost.trim());		productcost= (productcost == null ?null : productcost.trim());		totalcost= (totalcost == null ?null : totalcost.trim());		laborcost= (laborcost == null ?null : laborcost.trim());		materialcost= (materialcost == null ?null : materialcost.trim());		plandate= (plandate == null ?null : plandate.trim());		sourcebomid= (sourcebomid == null ?null : sourcebomid.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
