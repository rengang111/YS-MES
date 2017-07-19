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
public class B_ProductDesignDetailData implements java.io.Serializable
{

	public B_ProductDesignDetailData()
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
	private String productdetailid;
	public String getProductdetailid()
	{
		return this.productdetailid;
	}
	public void setProductdetailid(String productdetailid)
	{
		this.productdetailid=productdetailid;
	}

	/**
	*
	*/
	private String type;
	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type=type;
	}

	/**
	*
	*/
	private String componentname;
	public String getComponentname()
	{
		return this.componentname;
	}
	public void setComponentname(String componentname)
	{
		this.componentname=componentname;
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
	private String purchaser;
	public String getPurchaser()
	{
		return this.purchaser;
	}
	public void setPurchaser(String purchaser)
	{
		this.purchaser=purchaser;
	}

	/**
	*
	*/
	private String materialquality;
	public String getMaterialquality()
	{
		return this.materialquality;
	}
	public void setMaterialquality(String materialquality)
	{
		this.materialquality=materialquality;
	}

	/**
	*
	*/
	private String color;
	public String getColor()
	{
		return this.color;
	}
	public void setColor(String color)
	{
		this.color=color;
	}

	/**
	*
	*/
	private String specification;
	public String getSpecification()
	{
		return this.specification;
	}
	public void setSpecification(String specification)
	{
		this.specification=specification;
	}

	/**
	*
	*/
	private String process;
	public String getProcess()
	{
		return this.process;
	}
	public void setProcess(String process)
	{
		this.process=process;
	}

	/**
	*
	*/
	private String size;
	public String getSize()
	{
		return this.size;
	}
	public void setSize(String size)
	{
		this.size=size;
	}

	/**
	*
	*/
	private String packingqty;
	public String getPackingqty()
	{
		return this.packingqty;
	}
	public void setPackingqty(String packingqty)
	{
		this.packingqty=packingqty;
	}

	/**
	*
	*/
	private String filename;
	public String getFilename()
	{
		return this.filename;
	}
	public void setFilename(String filename)
	{
		this.filename=filename;
	}

	/**
	*
	*/
	private String remark;
	public String getRemark()
	{
		return this.remark;
	}
	public void setRemark(String remark)
	{
		this.remark=remark;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("productdetailid = "+(productdetailid == null ? "null" : productdetailid)+"\n");		sb.append("type = "+(type == null ? "null" : type)+"\n");		sb.append("componentname = "+(componentname == null ? "null" : componentname)+"\n");		sb.append("materialid = "+(materialid == null ? "null" : materialid)+"\n");		sb.append("purchaser = "+(purchaser == null ? "null" : purchaser)+"\n");		sb.append("materialquality = "+(materialquality == null ? "null" : materialquality)+"\n");		sb.append("color = "+(color == null ? "null" : color)+"\n");		sb.append("specification = "+(specification == null ? "null" : specification)+"\n");		sb.append("process = "+(process == null ? "null" : process)+"\n");		sb.append("size = "+(size == null ? "null" : size)+"\n");		sb.append("packingqty = "+(packingqty == null ? "null" : packingqty)+"\n");		sb.append("filename = "+(filename == null ? "null" : filename)+"\n");		sb.append("remark = "+(remark == null ? "null" : remark)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		productdetailid= (productdetailid == null ?null : productdetailid.trim());		type= (type == null ?null : type.trim());		componentname= (componentname == null ?null : componentname.trim());		materialid= (materialid == null ?null : materialid.trim());		purchaser= (purchaser == null ?null : purchaser.trim());		materialquality= (materialquality == null ?null : materialquality.trim());		color= (color == null ?null : color.trim());		specification= (specification == null ?null : specification.trim());		process= (process == null ?null : process.trim());		size= (size == null ?null : size.trim());		packingqty= (packingqty == null ?null : packingqty.trim());		filename= (filename == null ?null : filename.trim());		remark= (remark == null ?null : remark.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
