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
public class B_ProducePlanData implements java.io.Serializable
{

	public B_ProducePlanData()
	{
	}

	/**
	*
	*/
	private String recordid;
	public String getRecordid()
	{
		return this.recordid;
	}
	public void setRecordid(String recordid)
	{
		this.recordid=recordid;
	}

	/**
	*
	*/
	private String ysid;
	public String getYsid()
	{
		return this.ysid;
	}
	public void setYsid(String ysid)
	{
		this.ysid=ysid;
	}

	/**
	*
	*/
	private String produceline;
	public String getProduceline()
	{
		return this.produceline;
	}
	public void setProduceline(String produceline)
	{
		this.produceline=produceline;
	}

	/**
	*
	*/
	private String sortno;
	public String getSortno()
	{
		return this.sortno;
	}
	public void setSortno(String sortno)
	{
		this.sortno=sortno;
	}

	/**
	*
	*/
	private String finishflag;
	public String getFinishflag()
	{
		return this.finishflag;
	}
	public void setFinishflag(String finishflag)
	{
		this.finishflag=finishflag;
	}

	/**
	*
	*/
	private String readydate;
	public String getReadydate()
	{
		return this.readydate;
	}
	public void setReadydate(String readydate)
	{
		this.readydate=readydate;
	}

	/**
	*
	*/
	private String wjdate;
	public String getWjdate()
	{
		return this.wjdate;
	}
	public void setWjdate(String wjdate)
	{
		this.wjdate=wjdate;
	}

	/**
	*
	*/
	private String dzdate;
	public String getDzdate()
	{
		return this.dzdate;
	}
	public void setDzdate(String dzdate)
	{
		this.dzdate=dzdate;
	}

	/**
	*
	*/
	private String zzdate;
	public String getZzdate()
	{
		return this.zzdate;
	}
	public void setZzdate(String zzdate)
	{
		this.zzdate=zzdate;
	}

	/**
	*
	*/
	private String bzdate;
	public String getBzdate()
	{
		return this.bzdate;
	}
	public void setBzdate(String bzdate)
	{
		this.bzdate=bzdate;
	}

	/**
	*
	*/
	private String remarks;
	public String getRemarks()
	{
		return this.remarks;
	}
	public void setRemarks(String remarks)
	{
		this.remarks=remarks;
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
		sb.append("***** DataObject list begin *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());
	}

}