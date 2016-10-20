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
public class B_OrganBasicInfoData implements java.io.Serializable
{

	public B_OrganBasicInfoData()
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
	private String category;
	public String getCategory()
	{
		return this.category;
	}
	public void setCategory(String category)
	{
		this.category=category;
	}

	/**
	*
	*/
	private String name_short;
	public String getName_short()
	{
		return this.name_short;
	}
	public void setName_short(String name_short)
	{
		this.name_short=name_short;
	}

	/**
	*
	*/
	private String name_full;
	public String getName_full()
	{
		return this.name_full;
	}
	public void setName_full(String name_full)
	{
		this.name_full=name_full;
	}

	/**
	*
	*/
	private String address;
	public String getAddress()
	{
		return this.address;
	}
	public void setAddress(String address)
	{
		this.address=address;
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
		sb.append("***** DataObject list begin *****\n");
		sb.append("id = "+(id == null ? "null" : id)+"\n");
		sb.append("category = "+(category == null ? "null" : category)+"\n");
		sb.append("name_short = "+(name_short == null ? "null" : name_short)+"\n");
		sb.append("name_full = "+(name_full == null ? "null" : name_full)+"\n");
		sb.append("address = "+(address == null ? "null" : address)+"\n");
		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");
		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");
		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");
		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");
		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");
		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");
		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");
		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");
		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");
		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());
		category= (category == null ?null : category.trim());
		name_short= (name_short == null ?null : name_short.trim());
		name_full= (name_full == null ?null : name_full.trim());
		address= (address == null ?null : address.trim());
		deptguid= (deptguid == null ?null : deptguid.trim());
		createtime= (createtime == null ?null : createtime.trim());
		createperson= (createperson == null ?null : createperson.trim());
		createunitid= (createunitid == null ?null : createunitid.trim());
		modifytime= (modifytime == null ?null : modifytime.trim());
		modifyperson= (modifyperson == null ?null : modifyperson.trim());
		deleteflag= (deleteflag == null ?null : deleteflag.trim());
		returnvalue= (returnvalue == null ?null : returnvalue.trim());
		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
