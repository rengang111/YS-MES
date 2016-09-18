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
public class S_USERDUTYData implements java.io.Serializable
{

	public S_USERDUTYData()
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
	private String unitid;
	public String getUnitid()
	{
		return this.unitid;
	}
	public void setUnitid(String unitid)
	{
		this.unitid=unitid;
	}

	/**
	*
	*/
	private String post;
	public String getPost()
	{
		return this.post;
	}
	public void setPost(String post)
	{
		this.post=post;
	}

	/**
	*
	*/
	private String duty;
	public String getDuty()
	{
		return this.duty;
	}
	public void setDuty(String duty)
	{
		this.duty=duty;
	}

	/**
	*
	*/
	private String eanblestartdate;
	public String getEanblestartdate()
	{
		return this.eanblestartdate;
	}
	public void setEanblestartdate(String eanblestartdate)
	{
		this.eanblestartdate=eanblestartdate;
	}

	/**
	*
	*/
	private String eanbleenddate;
	public String getEanbleenddate()
	{
		return this.eanbleenddate;
	}
	public void setEanbleenddate(String eanbleenddate)
	{
		this.eanbleenddate=eanbleenddate;
	}

	/**
	*
	*/
	private Integer sortno;
	public Integer getSortno()
	{
		return this.sortno;
	}
	public void setSortno(Integer sortno)
	{
		this.sortno=sortno;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("userid = "+(userid == null ? "null" : userid)+"\n");		sb.append("unitid = "+(unitid == null ? "null" : unitid)+"\n");		sb.append("post = "+(post == null ? "null" : post)+"\n");		sb.append("duty = "+(duty == null ? "null" : duty)+"\n");		sb.append("eanblestartdate = "+(eanblestartdate == null ? "null" : eanblestartdate)+"\n");		sb.append("eanbleenddate = "+(eanbleenddate == null ? "null" : eanbleenddate)+"\n");		sb.append("sortno = "+(sortno == null ? "null" : sortno.toString())+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		userid= (userid == null ?null : userid.trim());		unitid= (unitid == null ?null : unitid.trim());		post= (post == null ?null : post.trim());		duty= (duty == null ?null : duty.trim());		eanblestartdate= (eanblestartdate == null ?null : eanblestartdate.trim());		eanbleenddate= (eanbleenddate == null ?null : eanbleenddate.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
