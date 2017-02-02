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
public class B_ProjectTaskData implements java.io.Serializable
{

	public B_ProjectTaskData()
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
	private String projectid;
	public String getProjectid()
	{
		return this.projectid;
	}
	public void setProjectid(String projectid)
	{
		this.projectid=projectid;
	}

	/**
	*
	*/
	private String projectname;
	public String getProjectname()
	{
		return this.projectname;
	}
	public void setProjectname(String projectname)
	{
		this.projectname=projectname;
	}

	/**
	*
	*/
	private String tempversion;
	public String getTempversion()
	{
		return this.tempversion;
	}
	public void setTempversion(String tempversion)
	{
		this.tempversion=tempversion;
	}

	/**
	*
	*/
	private String manager;
	public String getManager()
	{
		return this.manager;
	}
	public void setManager(String manager)
	{
		this.manager=manager;
	}

	/**
	*
	*/
	private String referprototype;
	public String getReferprototype()
	{
		return this.referprototype;
	}
	public void setReferprototype(String referprototype)
	{
		this.referprototype=referprototype;
	}

	/**
	*
	*/
	private String designcapability;
	public String getDesigncapability()
	{
		return this.designcapability;
	}
	public void setDesigncapability(String designcapability)
	{
		this.designcapability=designcapability;
	}

	/**
	*
	*/
	private String begintime;
	public String getBegintime()
	{
		return this.begintime;
	}
	public void setBegintime(String begintime)
	{
		this.begintime=begintime;
	}

	/**
	*
	*/
	private String endtime;
	public String getEndtime()
	{
		return this.endtime;
	}
	public void setEndtime(String endtime)
	{
		this.endtime=endtime;
	}

	/**
	*
	*/
	private String packing;
	public String getPacking()
	{
		return this.packing;
	}
	public void setPacking(String packing)
	{
		this.packing=packing;
	}

	/**
	*
	*/
	private String estimatecost;
	public String getEstimatecost()
	{
		return this.estimatecost;
	}
	public void setEstimatecost(String estimatecost)
	{
		this.estimatecost=estimatecost;
	}

	/**
	*
	*/
	private String saleprice;
	public String getSaleprice()
	{
		return this.saleprice;
	}
	public void setSaleprice(String saleprice)
	{
		this.saleprice=saleprice;
	}

	/**
	*
	*/
	private String sales;
	public String getSales()
	{
		return this.sales;
	}
	public void setSales(String sales)
	{
		this.sales=sales;
	}

	/**
	*
	*/
	private String recoverynum;
	public String getRecoverynum()
	{
		return this.recoverynum;
	}
	public void setRecoverynum(String recoverynum)
	{
		this.recoverynum=recoverynum;
	}

	/**
	*
	*/
	private String failmode;
	public String getFailmode()
	{
		return this.failmode;
	}
	public void setFailmode(String failmode)
	{
		this.failmode=failmode;
	}

	/**
	*
	*/
	private String image_filename;
	public String getImage_filename()
	{
		return this.image_filename;
	}
	public void setImage_filename(String image_filename)
	{
		this.image_filename=image_filename;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("projectid = "+(projectid == null ? "null" : projectid)+"\n");		sb.append("projectname = "+(projectname == null ? "null" : projectname)+"\n");		sb.append("tempversion = "+(tempversion == null ? "null" : tempversion)+"\n");		sb.append("manager = "+(manager == null ? "null" : manager)+"\n");		sb.append("referprototype = "+(referprototype == null ? "null" : referprototype)+"\n");		sb.append("designcapability = "+(designcapability == null ? "null" : designcapability)+"\n");		sb.append("begintime = "+(begintime == null ? "null" : begintime)+"\n");		sb.append("endtime = "+(endtime == null ? "null" : endtime)+"\n");		sb.append("packing = "+(packing == null ? "null" : packing)+"\n");		sb.append("estimatecost = "+(estimatecost == null ? "null" : estimatecost)+"\n");		sb.append("saleprice = "+(saleprice == null ? "null" : saleprice)+"\n");		sb.append("sales = "+(sales == null ? "null" : sales)+"\n");		sb.append("recoverynum = "+(recoverynum == null ? "null" : recoverynum)+"\n");		sb.append("failmode = "+(failmode == null ? "null" : failmode)+"\n");		sb.append("image_filename = "+(image_filename == null ? "null" : image_filename)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		projectid= (projectid == null ?null : projectid.trim());		projectname= (projectname == null ?null : projectname.trim());		tempversion= (tempversion == null ?null : tempversion.trim());		manager= (manager == null ?null : manager.trim());		referprototype= (referprototype == null ?null : referprototype.trim());		designcapability= (designcapability == null ?null : designcapability.trim());		begintime= (begintime == null ?null : begintime.trim());		endtime= (endtime == null ?null : endtime.trim());		packing= (packing == null ?null : packing.trim());		estimatecost= (estimatecost == null ?null : estimatecost.trim());		saleprice= (saleprice == null ?null : saleprice.trim());		sales= (sales == null ?null : sales.trim());		recoverynum= (recoverynum == null ?null : recoverynum.trim());		failmode= (failmode == null ?null : failmode.trim());		image_filename= (image_filename == null ?null : image_filename.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
