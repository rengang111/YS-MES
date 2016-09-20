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
public class B_SupplierContractsData implements java.io.Serializable
{

	public B_SupplierContractsData()
	{
	}

	/**
	*
	*/
	private String supplierid;
	public String getSupplierid()
	{
		return this.supplierid;
	}
	public void setSupplierid(String supplierid)
	{
		this.supplierid=supplierid;
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
	private String duties;
	public String getDuties()
	{
		return this.duties;
	}
	public void setDuties(String duties)
	{
		this.duties=duties;
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
	private String telephone;
	public String getTelephone()
	{
		return this.telephone;
	}
	public void setTelephone(String telephone)
	{
		this.telephone=telephone;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("supplierid = "+(supplierid == null ? "null" : supplierid)+"\n");		sb.append("name = "+(name == null ? "null" : name)+"\n");		sb.append("sex = "+(sex == null ? "null" : sex)+"\n");		sb.append("duties = "+(duties == null ? "null" : duties)+"\n");		sb.append("mobile = "+(mobile == null ? "null" : mobile)+"\n");		sb.append("telephone = "+(telephone == null ? "null" : telephone)+"\n");		sb.append("fax = "+(fax == null ? "null" : fax)+"\n");		sb.append("mail = "+(mail == null ? "null" : mail)+"\n");		sb.append("qq = "+(qq == null ? "null" : qq)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		supplierid= (supplierid == null ?null : supplierid.trim());		name= (name == null ?null : name.trim());		sex= (sex == null ?null : sex.trim());		duties= (duties == null ?null : duties.trim());		mobile= (mobile == null ?null : mobile.trim());		telephone= (telephone == null ?null : telephone.trim());		fax= (fax == null ?null : fax.trim());		mail= (mail == null ?null : mail.trim());		qq= (qq == null ?null : qq.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
