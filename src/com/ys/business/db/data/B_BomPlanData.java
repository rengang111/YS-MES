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
public class B_BomPlanData  extends CommFieldsData
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
	private String managerate;
	public String getManagerate()
	{
		return this.managerate;
	}
	public void setManagerate(String managerate)
	{
		this.managerate=managerate;
	}

	/**
	*
	*/
	private String managecost;
	public String getManagecost()
	{
		return this.managecost;
	}
	public void setManagecost(String managecost)
	{
		this.managecost=managecost;
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
	private String accountcost;
	public String getAccountcost()
	{
		return this.accountcost;
	}
	public void setAccountcost(String accountcost)
	{
		this.accountcost=accountcost;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("bomid = "+(bomid == null ? "null" : bomid)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("quantity = "+(quantity == null ? "null" : quantity)+"\n");		sb.append("managerate = "+(managerate == null ? "null" : managerate)+"\n");		sb.append("managecost = "+(managecost == null ? "null" : managecost)+"\n");		sb.append("productcost = "+(productcost == null ? "null" : productcost)+"\n");		sb.append("accountcost = "+(accountcost == null ? "null" : accountcost)+"\n");		sb.append("laborcost = "+(laborcost == null ? "null" : laborcost)+"\n");		sb.append("materialcost = "+(materialcost == null ? "null" : materialcost)+"\n");		sb.append("plandate = "+(plandate == null ? "null" : plandate)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		ysid= (ysid == null ?null : ysid.trim());		bomid= (bomid == null ?null : bomid.trim());		materialid= (materialid == null ?null : materialid.trim());		subid= (subid == null ?null : subid.trim());		quantity= (quantity == null ?null : quantity.trim());		managerate= (managerate == null ?null : managerate.trim());		managecost= (managecost == null ?null : managecost.trim());		productcost= (productcost == null ?null : productcost.trim());		accountcost= (accountcost == null ?null : accountcost.trim());		laborcost= (laborcost == null ?null : laborcost.trim());		materialcost= (materialcost == null ?null : materialcost.trim());		plandate= (plandate == null ?null : plandate.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
