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
	private String produceteam;
	public String getProduceteam()
	{
		return this.produceteam;
	}
	public void setProduceteam(String produceteam)
	{
		this.produceteam=produceteam;
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
	private String keypoint;
	public String getKeypoint()
	{
		return this.keypoint;
	}
	public void setKeypoint(String keypoint)
	{
		this.keypoint=keypoint;
	}

	/**
	*
	*/
	private String pointremarks;
	public String getPointremarks()
	{
		return this.pointremarks;
	}
	public void setPointremarks(String pointremarks)
	{
		this.pointremarks=pointremarks;
	}

	/**
	*
	*/
	private String pointdate;
	public String getPointdate()
	{
		return this.pointdate;
	}
	public void setPointdate(String pointdate)
	{
		this.pointdate=pointdate;
	}

	/**
	*
	*/
	private String startflag;
	public String getStartflag()
	{
		return this.startflag;
	}
	public void setStartflag(String startflag)
	{
		this.startflag=startflag;
	}

	/**
	*
	*/
	private String importantflag;
	public String getImportantflag()
	{
		return this.importantflag;
	}
	public void setImportantflag(String importantflag)
	{
		this.importantflag=importantflag;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("recordid = "+(recordid == null ? "null" : recordid)+"\n");		sb.append("ysid = "+(ysid == null ? "null" : ysid)+"\n");		sb.append("produceline = "+(produceline == null ? "null" : produceline)+"\n");		sb.append("produceteam = "+(produceteam == null ? "null" : produceteam)+"\n");		sb.append("sortno = "+(sortno == null ? "null" : sortno)+"\n");		sb.append("finishflag = "+(finishflag == null ? "null" : finishflag)+"\n");		sb.append("readydate = "+(readydate == null ? "null" : readydate)+"\n");		sb.append("wjdate = "+(wjdate == null ? "null" : wjdate)+"\n");		sb.append("dzdate = "+(dzdate == null ? "null" : dzdate)+"\n");		sb.append("zzdate = "+(zzdate == null ? "null" : zzdate)+"\n");		sb.append("bzdate = "+(bzdate == null ? "null" : bzdate)+"\n");		sb.append("remarks = "+(remarks == null ? "null" : remarks)+"\n");		sb.append("keypoint = "+(keypoint == null ? "null" : keypoint)+"\n");		sb.append("pointremarks = "+(pointremarks == null ? "null" : pointremarks)+"\n");		sb.append("pointdate = "+(pointdate == null ? "null" : pointdate)+"\n");		sb.append("startflag = "+(startflag == null ? "null" : startflag)+"\n");		sb.append("importantflag = "+(importantflag == null ? "null" : importantflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		recordid= (recordid == null ?null : recordid.trim());		ysid= (ysid == null ?null : ysid.trim());		produceline= (produceline == null ?null : produceline.trim());		produceteam= (produceteam == null ?null : produceteam.trim());		sortno= (sortno == null ?null : sortno.trim());		finishflag= (finishflag == null ?null : finishflag.trim());		readydate= (readydate == null ?null : readydate.trim());		wjdate= (wjdate == null ?null : wjdate.trim());		dzdate= (dzdate == null ?null : dzdate.trim());		zzdate= (zzdate == null ?null : zzdate.trim());		bzdate= (bzdate == null ?null : bzdate.trim());		remarks= (remarks == null ?null : remarks.trim());		keypoint= (keypoint == null ?null : keypoint.trim());		pointremarks= (pointremarks == null ?null : pointremarks.trim());		pointdate= (pointdate == null ?null : pointdate.trim());		startflag= (startflag == null ?null : startflag.trim());		importantflag= (importantflag == null ?null : importantflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
