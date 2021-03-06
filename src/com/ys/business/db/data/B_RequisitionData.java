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
public class B_RequisitionData implements java.io.Serializable
{

	public B_RequisitionData()
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
	private String requisitionid;
	public String getRequisitionid()
	{
		return this.requisitionid;
	}
	public void setRequisitionid(String requisitionid)
	{
		this.requisitionid=requisitionid;
	}

	/**
	*
	*/
	private String parentid;
	public String getParentid()
	{
		return this.parentid;
	}
	public void setParentid(String parentid)
	{
		this.parentid=parentid;
	}

	/**
	*
	*/
	private String subid;
	public String getSubid()
	{
		return this.subid;
	}
	public void setSubid(String subid)
	{
		this.subid=subid;
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
	private String requisitiondate;
	public String getRequisitiondate()
	{
		return this.requisitiondate;
	}
	public void setRequisitiondate(String requisitiondate)
	{
		this.requisitiondate=requisitiondate;
	}

	/**
	*
	*/
	private String requisitionstoreid;
	public String getRequisitionstoreid()
	{
		return this.requisitionstoreid;
	}
	public void setRequisitionstoreid(String requisitionstoreid)
	{
		this.requisitionstoreid=requisitionstoreid;
	}

	/**
	*
	*/
	private String requisitionunitid;
	public String getRequisitionunitid()
	{
		return this.requisitionunitid;
	}
	public void setRequisitionunitid(String requisitionunitid)
	{
		this.requisitionunitid=requisitionunitid;
	}

	/**
	*
	*/
	private String requisitionuserid;
	public String getRequisitionuserid()
	{
		return this.requisitionuserid;
	}
	public void setRequisitionuserid(String requisitionuserid)
	{
		this.requisitionuserid=requisitionuserid;
	}

	/**
	*
	*/
	private String storeuserid;
	public String getStoreuserid()
	{
		return this.storeuserid;
	}
	public void setStoreuserid(String storeuserid)
	{
		this.storeuserid=storeuserid;
	}

	/**
	*
	*/
	private String storepmuserid;
	public String getStorepmuserid()
	{
		return this.storepmuserid;
	}
	public void setStorepmuserid(String storepmuserid)
	{
		this.storepmuserid=storepmuserid;
	}

	/**
	*
	*/
	private String unitpmuserid;
	public String getUnitpmuserid()
	{
		return this.unitpmuserid;
	}
	public void setUnitpmuserid(String unitpmuserid)
	{
		this.unitpmuserid=unitpmuserid;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("requisitionid = "+(requisitionid == null ? "null" : requisitionid)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("subid = "+(subid == null ? "null" : subid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("requisitiondate = "+(requisitiondate == null ? "null" : requisitiondate)+"\n");		sb.append("requisitionstoreid = "+(requisitionstoreid == null ? "null" : requisitionstoreid)+"\n");		sb.append("requisitionunitid = "+(requisitionunitid == null ? "null" : requisitionunitid)+"\n");		sb.append("requisitionuserid = "+(requisitionuserid == null ? "null" : requisitionuserid)+"\n");		sb.append("storeuserid = "+(storeuserid == null ? "null" : storeuserid)+"\n");		sb.append("storepmuserid = "+(storepmuserid == null ? "null" : storepmuserid)+"\n");		sb.append("unitpmuserid = "+(unitpmuserid == null ? "null" : unitpmuserid)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		requisitionid= (requisitionid == null ?null : requisitionid.trim());		parentid= (parentid == null ?null : parentid.trim());		subid= (subid == null ?null : subid.trim());		ysid= (ysid == null ?null : ysid.trim());		requisitiondate= (requisitiondate == null ?null : requisitiondate.trim());		requisitionstoreid= (requisitionstoreid == null ?null : requisitionstoreid.trim());		requisitionunitid= (requisitionunitid == null ?null : requisitionunitid.trim());		requisitionuserid= (requisitionuserid == null ?null : requisitionuserid.trim());		storeuserid= (storeuserid == null ?null : storeuserid.trim());		storepmuserid= (storepmuserid == null ?null : storepmuserid.trim());		unitpmuserid= (unitpmuserid == null ?null : unitpmuserid.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
