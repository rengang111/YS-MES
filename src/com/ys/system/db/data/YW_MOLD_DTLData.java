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
public class YW_MOLD_DTLData implements java.io.Serializable
{

	public YW_MOLD_DTLData()
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
	private String prod_code;
	public String getProd_code()
	{
		return this.prod_code;
	}
	public void setProd_code(String prod_code)
	{
		this.prod_code=prod_code;
	}

	/**
	*
	*/
	private String mold_code;
	public String getMold_code()
	{
		return this.mold_code;
	}
	public void setMold_code(String mold_code)
	{
		this.mold_code=mold_code;
	}

	/**
	*
	*/
	private String mold_name;
	public String getMold_name()
	{
		return this.mold_name;
	}
	public void setMold_name(String mold_name)
	{
		this.mold_name=mold_name;
	}

	/**
	*
	*/
	private String mold_size;
	public String getMold_size()
	{
		return this.mold_size;
	}
	public void setMold_size(String mold_size)
	{
		this.mold_size=mold_size;
	}

	/**
	*
	*/
	private String material;
	public String getMaterial()
	{
		return this.material;
	}
	public void setMaterial(String material)
	{
		this.material=material;
	}

	/**
	*
	*/
	private String modulus;
	public String getModulus()
	{
		return this.modulus;
	}
	public void setModulus(String modulus)
	{
		this.modulus=modulus;
	}

	/**
	*
	*/
	private int weight;
	public int getWeight()
	{
		return this.weight;
	}
	public void setWeight(int weight)
	{
		this.weight=weight;
	}

	/**
	*
	*/
	private Float price;
	public Float getPrice()
	{
		return this.price;
	}
	public void setPrice(Float price)
	{
		this.price=price;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("prod_code = "+(prod_code == null ? "null" : prod_code)+"\n");		sb.append("mold_code = "+(mold_code == null ? "null" : mold_code)+"\n");		sb.append("mold_name = "+(mold_name == null ? "null" : mold_name)+"\n");		sb.append("mold_size = "+(mold_size == null ? "null" : mold_size)+"\n");		sb.append("material = "+(material == null ? "null" : material)+"\n");		sb.append("modulus = "+(modulus == null ? "null" : modulus)+"\n");		sb.append("weight = "+weight+"\n");		sb.append("price = "+(price == null ? "null" : price.toString())+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		prod_code= (prod_code == null ?null : prod_code.trim());		mold_code= (mold_code == null ?null : mold_code.trim());		mold_name= (mold_name == null ?null : mold_name.trim());		mold_size= (mold_size == null ?null : mold_size.trim());		material= (material == null ?null : material.trim());		modulus= (modulus == null ?null : modulus.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
