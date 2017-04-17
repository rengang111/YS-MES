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
public class B_MouldBaseInfoData 
{

	public B_MouldBaseInfoData()
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
	private String mouldid;
	public String getMouldid()
	{
		return this.mouldid;
	}
	public void setMouldid(String mouldid)
	{
		this.mouldid=mouldid;
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
	private String productmodelid;
	public String getProductmodelid()
	{
		return this.productmodelid;
	}
	public void setProductmodelid(String productmodelid)
	{
		this.productmodelid=productmodelid;
	}

	/**
	*
	*/
	private String productmodelname;
	public String getProductmodelname()
	{
		return this.productmodelname;
	}
	public void setProductmodelname(String productmodelname)
	{
		this.productmodelname=productmodelname;
	}

	/**
	*
	*/
	private String name;
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name=name;
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
	private String weight;
	public String getWeight()
	{
		return this.weight;
	}
	public void setWeight(String weight)
	{
		this.weight=weight;
	}

	/**
	*
	*/
	private String unloadingnum;
	public String getUnloadingnum()
	{
		return this.unloadingnum;
	}
	public void setUnloadingnum(String unloadingnum)
	{
		this.unloadingnum=unloadingnum;
	}

	/**
	*
	*/
	private String unit;
	public String getUnit()
	{
		return this.unit;
	}
	public void setUnit(String unit)
	{
		this.unit=unit;
	}

	/**
	*
	*/
	private String comment;
	public String getComment()
	{
		return this.comment;
	}
	public void setComment(String comment)
	{
		this.comment=comment;
	}

	/**
	*
	*/
	private String image_filename;
	public String getImage_filename()
	{
		return this.image_filename;
	}
	public void setImage_filename(String image_filename)
	{
		this.image_filename=image_filename;
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
	*返回值
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
	*返回值
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("mouldid = "+(mouldid == null ? "null" : mouldid)+"\n");		sb.append("type = "+(type == null ? "null" : type)+"\n");		sb.append("productmodelid = "+(productmodelid == null ? "null" : productmodelid)+"\n");		sb.append("productmodelname = "+(productmodelname == null ? "null" : productmodelname)+"\n");		sb.append("name = "+(name == null ? "null" : name)+"\n");		sb.append("materialquality = "+(materialquality == null ? "null" : materialquality)+"\n");		sb.append("size = "+(size == null ? "null" : size)+"\n");		sb.append("weight = "+(weight == null ? "null" : weight)+"\n");		sb.append("unloadingnum = "+(unloadingnum == null ? "null" : unloadingnum)+"\n");		sb.append("unit = "+(unit == null ? "null" : unit)+"\n");		sb.append("comment = "+(comment == null ? "null" : comment)+"\n");		sb.append("image_filename = "+(image_filename == null ? "null" : image_filename)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		mouldid= (mouldid == null ?null : mouldid.trim());		type= (type == null ?null : type.trim());		productmodelid= (productmodelid == null ?null : productmodelid.trim());		productmodelname= (productmodelname == null ?null : productmodelname.trim());		name= (name == null ?null : name.trim());		materialquality= (materialquality == null ?null : materialquality.trim());		size= (size == null ?null : size.trim());		weight= (weight == null ?null : weight.trim());		unloadingnum= (unloadingnum == null ?null : unloadingnum.trim());		unit= (unit == null ?null : unit.trim());		comment= (comment == null ?null : comment.trim());		image_filename= (image_filename == null ?null : image_filename.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
