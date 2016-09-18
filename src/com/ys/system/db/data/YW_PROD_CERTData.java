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
public class YW_PROD_CERTData implements java.io.Serializable
{

	public YW_PROD_CERTData()
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
	private int prod_id;
	public int getProd_id()
	{
		return this.prod_id;
	}
	public void setProd_id(int prod_id)
	{
		this.prod_id=prod_id;
	}

	/**
	*
	*/
	private String cert_mach;
	public String getCert_mach()
	{
		return this.cert_mach;
	}
	public void setCert_mach(String cert_mach)
	{
		this.cert_mach=cert_mach;
	}

	/**
	*
	*/
	private String cert_char;
	public String getCert_char()
	{
		return this.cert_char;
	}
	public void setCert_char(String cert_char)
	{
		this.cert_char=cert_char;
	}

	/**
	*
	*/
	private String cert_bat_pkg;
	public String getCert_bat_pkg()
	{
		return this.cert_bat_pkg;
	}
	public void setCert_bat_pkg(String cert_bat_pkg)
	{
		this.cert_bat_pkg=cert_bat_pkg;
	}

	/**
	*
	*/
	private String cert_bat;
	public String getCert_bat()
	{
		return this.cert_bat;
	}
	public void setCert_bat(String cert_bat)
	{
		this.cert_bat=cert_bat;
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
	private String describes;
	public String getDescribes()
	{
		return this.describes;
	}
	public void setDescribes(String describes)
	{
		this.describes=describes;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("prod_id = "+prod_id+"\n");		sb.append("cert_mach = "+(cert_mach == null ? "null" : cert_mach)+"\n");		sb.append("cert_char = "+(cert_char == null ? "null" : cert_char)+"\n");		sb.append("cert_bat_pkg = "+(cert_bat_pkg == null ? "null" : cert_bat_pkg)+"\n");		sb.append("cert_bat = "+(cert_bat == null ? "null" : cert_bat)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("describes = "+(describes == null ? "null" : describes)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		cert_mach= (cert_mach == null ?null : cert_mach.trim());		cert_char= (cert_char == null ?null : cert_char.trim());		cert_bat_pkg= (cert_bat_pkg == null ?null : cert_bat_pkg.trim());		cert_bat= (cert_bat == null ?null : cert_bat.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		describes= (describes == null ?null : describes.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
