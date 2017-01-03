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
public class B_MouldDetailData implements java.io.Serializable
{

	public B_MouldDetailData()
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
	private String mouldbaseid;
	public String getMouldbaseid()
	{
		return this.mouldbaseid;
	}
	public void setMouldbaseid(String mouldbaseid)
	{
		this.mouldbaseid=mouldbaseid;
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
	private String no;
	public String getNo()
	{
		return this.no;
	}
	public void setNo(String no)
	{
		this.no=no;
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
	private String mouldunloadingnum;
	public String getMouldunloadingnum()
	{
		return this.mouldunloadingnum;
	}
	public void setMouldunloadingnum(String mouldunloadingnum)
	{
		this.mouldunloadingnum=mouldunloadingnum;
	}

	/**
	*
	*/
	private String heavy;
	public String getHeavy()
	{
		return this.heavy;
	}
	public void setHeavy(String heavy)
	{
		this.heavy=heavy;
	}

	/**
	*
	*/
	private String price;
	public String getPrice()
	{
		return this.price;
	}
	public void setPrice(String price)
	{
		this.price=price;
	}

	/**
	*
	*/
	private String place;
	public String getPlace()
	{
		return this.place;
	}
	public void setPlace(String place)
	{
		this.place=place;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("mouldbaseid = "+(mouldbaseid == null ? "null" : mouldbaseid)+"\n");		sb.append("type = "+(type == null ? "null" : type)+"\n");		sb.append("no = "+(no == null ? "null" : no)+"\n");		sb.append("name = "+(name == null ? "null" : name)+"\n");		sb.append("size = "+(size == null ? "null" : size)+"\n");		sb.append("materialquality = "+(materialquality == null ? "null" : materialquality)+"\n");		sb.append("mouldunloadingnum = "+(mouldunloadingnum == null ? "null" : mouldunloadingnum)+"\n");		sb.append("heavy = "+(heavy == null ? "null" : heavy)+"\n");		sb.append("price = "+(price == null ? "null" : price)+"\n");		sb.append("place = "+(place == null ? "null" : place)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		mouldbaseid= (mouldbaseid == null ?null : mouldbaseid.trim());		type= (type == null ?null : type.trim());		no= (no == null ?null : no.trim());		name= (name == null ?null : name.trim());		size= (size == null ?null : size.trim());		materialquality= (materialquality == null ?null : materialquality.trim());		mouldunloadingnum= (mouldunloadingnum == null ?null : mouldunloadingnum.trim());		heavy= (heavy == null ?null : heavy.trim());		price= (price == null ?null : price.trim());		place= (place == null ?null : place.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
