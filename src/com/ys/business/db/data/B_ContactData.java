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
public class B_ContactData implements java.io.Serializable
{

	public B_ContactData()
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
	private String companycode;
	public String getCompanycode()
	{
		return this.companycode;
	}
	public void setCompanycode(String companycode)
	{
		this.companycode=companycode;
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
	private String sex;
	public String getSex()
	{
		return this.sex;
	}
	public void setSex(String sex)
	{
		this.sex=sex;
	}

	/**
	*
	*/
	private String position;
	public String getPosition()
	{
		return this.position;
	}
	public void setPosition(String position)
	{
		this.position=position;
	}

	/**
	*
	*/
	private String department;
	public String getDepartment()
	{
		return this.department;
	}
	public void setDepartment(String department)
	{
		this.department=department;
	}

	/**
	*
	*/
	private String mobile;
	public String getMobile()
	{
		return this.mobile;
	}
	public void setMobile(String mobile)
	{
		this.mobile=mobile;
	}

	/**
	*
	*/
	private String phone;
	public String getPhone()
	{
		return this.phone;
	}
	public void setPhone(String phone)
	{
		this.phone=phone;
	}

	/**
	*
	*/
	private String fax;
	public String getFax()
	{
		return this.fax;
	}
	public void setFax(String fax)
	{
		this.fax=fax;
	}

	/**
	*
	*/
	private String mail;
	public String getMail()
	{
		return this.mail;
	}
	public void setMail(String mail)
	{
		this.mail=mail;
	}

	/**
	*
	*/
	private String skype;
	public String getSkype()
	{
		return this.skype;
	}
	public void setSkype(String skype)
	{
		this.skype=skype;
	}

	/**
	*
	*/
	private String mark;
	public String getMark()
	{
		return this.mark;
	}
	public void setMark(String mark)
	{
		this.mark=mark;
	}

	/**
	*
	*/
	private String qq;
	public String getQq()
	{
		return this.qq;
	}
	public void setQq(String qq)
	{
		this.qq=qq;
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
		sb.append("***** DataObject list begin *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());
	}

}