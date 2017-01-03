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
public class B_MouldReturnRegisterData implements java.io.Serializable
{

	public B_MouldReturnRegisterData()
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
	private String lendid;
	public String getLendid()
	{
		return this.lendid;
	}
	public void setLendid(String lendid)
	{
		this.lendid=lendid;
	}

	/**
	*
	*/
	private String mouldreturnno;
	public String getMouldreturnno()
	{
		return this.mouldreturnno;
	}
	public void setMouldreturnno(String mouldreturnno)
	{
		this.mouldreturnno=mouldreturnno;
	}

	/**
	*
	*/
	private String factreturntime;
	public String getFactreturntime()
	{
		return this.factreturntime;
	}
	public void setFactreturntime(String factreturntime)
	{
		this.factreturntime=factreturntime;
	}

	/**
	*
	*/
	private String acceptresult;
	public String getAcceptresult()
	{
		return this.acceptresult;
	}
	public void setAcceptresult(String acceptresult)
	{
		this.acceptresult=acceptresult;
	}

	/**
	*
	*/
	private String memo;
	public String getMemo()
	{
		return this.memo;
	}
	public void setMemo(String memo)
	{
		this.memo=memo;
	}

	/**
	*
	*/
	private String acceptor;
	public String getAcceptor()
	{
		return this.acceptor;
	}
	public void setAcceptor(String acceptor)
	{
		this.acceptor=acceptor;
	}

	/**
	*
	*/
	private String confirm;
	public String getConfirm()
	{
		return this.confirm;
	}
	public void setConfirm(String confirm)
	{
		this.confirm=confirm;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("lendid = "+(lendid == null ? "null" : lendid)+"\n");		sb.append("mouldreturnno = "+(mouldreturnno == null ? "null" : mouldreturnno)+"\n");		sb.append("factreturntime = "+(factreturntime == null ? "null" : factreturntime)+"\n");		sb.append("acceptresult = "+(acceptresult == null ? "null" : acceptresult)+"\n");		sb.append("memo = "+(memo == null ? "null" : memo)+"\n");		sb.append("acceptor = "+(acceptor == null ? "null" : acceptor)+"\n");		sb.append("confirm = "+(confirm == null ? "null" : confirm)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		lendid= (lendid == null ?null : lendid.trim());		mouldreturnno= (mouldreturnno == null ?null : mouldreturnno.trim());		factreturntime= (factreturntime == null ?null : factreturntime.trim());		acceptresult= (acceptresult == null ?null : acceptresult.trim());		memo= (memo == null ?null : memo.trim());		acceptor= (acceptor == null ?null : acceptor.trim());		confirm= (confirm == null ?null : confirm.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
