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
public class S_OPERLOGData implements java.io.Serializable
{

	public S_OPERLOGData()
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
	private String userid;
	public String getUserid()
	{
		return this.userid;
	}
	public void setUserid(String userid)
	{
		this.userid=userid;
	}

	/**
	*
	*/
	private String username;
	public String getUsername()
	{
		return this.username;
	}
	public void setUsername(String username)
	{
		this.username=username;
	}

	/**
	*
	*/
	private String menuywclass;
	public String getMenuywclass()
	{
		return this.menuywclass;
	}
	public void setMenuywclass(String menuywclass)
	{
		this.menuywclass=menuywclass;
	}

	/**
	*
	*/
	private String menuid;
	public String getMenuid()
	{
		return this.menuid;
	}
	public void setMenuid(String menuid)
	{
		this.menuid=menuid;
	}

	/**
	*
	*/
	private String menuname;
	public String getMenuname()
	{
		return this.menuname;
	}
	public void setMenuname(String menuname)
	{
		this.menuname=menuname;
	}

	/**
	*
	*/
	private String menuurl;
	public String getMenuurl()
	{
		return this.menuurl;
	}
	public void setMenuurl(String menuurl)
	{
		this.menuurl=menuurl;
	}

	/**
	*
	*/
	private String ip;
	public String getIp()
	{
		return this.ip;
	}
	public void setIp(String ip)
	{
		this.ip=ip;
	}

	/**
	*
	*/
	private String browsename;
	public String getBrowsename()
	{
		return this.browsename;
	}
	public void setBrowsename(String browsename)
	{
		this.browsename=browsename;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("userid = "+(userid == null ? "null" : userid)+"\n");		sb.append("username = "+(username == null ? "null" : username)+"\n");		sb.append("menuywclass = "+(menuywclass == null ? "null" : menuywclass)+"\n");		sb.append("menuid = "+(menuid == null ? "null" : menuid)+"\n");		sb.append("menuname = "+(menuname == null ? "null" : menuname)+"\n");		sb.append("menuurl = "+(menuurl == null ? "null" : menuurl)+"\n");		sb.append("ip = "+(ip == null ? "null" : ip)+"\n");		sb.append("browsename = "+(browsename == null ? "null" : browsename)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		userid= (userid == null ?null : userid.trim());		username= (username == null ?null : username.trim());		menuywclass= (menuywclass == null ?null : menuywclass.trim());		menuid= (menuid == null ?null : menuid.trim());		menuname= (menuname == null ?null : menuname.trim());		menuurl= (menuurl == null ?null : menuurl.trim());		ip= (ip == null ?null : ip.trim());		browsename= (browsename == null ?null : browsename.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
