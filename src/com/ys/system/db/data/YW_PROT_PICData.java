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
public class YW_PROT_PICData implements java.io.Serializable
{

	public YW_PROT_PICData()
	{
	}

	/**
	*
	*/
	private String prot_pic_id;
	public String getProt_pic_id()
	{
		return this.prot_pic_id;
	}
	public void setProt_pic_id(String prot_pic_id)
	{
		this.prot_pic_id=prot_pic_id;
	}

	/**
	*
	*/
	private int prot_id;
	public int getProt_id()
	{
		return this.prot_id;
	}
	public void setProt_id(int prot_id)
	{
		this.prot_id=prot_id;
	}

	/**
	*
	*/
	private String pic_type;
	public String getPic_type()
	{
		return this.pic_type;
	}
	public void setPic_type(String pic_type)
	{
		this.pic_type=pic_type;
	}

	/**
	*
	*/
	private String pic_name;
	public String getPic_name()
	{
		return this.pic_name;
	}
	public void setPic_name(String pic_name)
	{
		this.pic_name=pic_name;
	}

	/**
	*
	*/
	private String save_dir;
	public String getSave_dir()
	{
		return this.save_dir;
	}
	public void setSave_dir(String save_dir)
	{
		this.save_dir=save_dir;
	}

	/**
	*
	*/
	private String view_sts;
	public String getView_sts()
	{
		return this.view_sts;
	}
	public void setView_sts(String view_sts)
	{
		this.view_sts=view_sts;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("prot_pic_id = "+(prot_pic_id == null ? "null" : prot_pic_id)+"\n");		sb.append("prot_id = "+prot_id+"\n");		sb.append("pic_type = "+(pic_type == null ? "null" : pic_type)+"\n");		sb.append("pic_name = "+(pic_name == null ? "null" : pic_name)+"\n");		sb.append("save_dir = "+(save_dir == null ? "null" : save_dir)+"\n");		sb.append("view_sts = "+(view_sts == null ? "null" : view_sts)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		prot_pic_id= (prot_pic_id == null ?null : prot_pic_id.trim());		pic_type= (pic_type == null ?null : pic_type.trim());		pic_name= (pic_name == null ?null : pic_name.trim());		save_dir= (save_dir == null ?null : save_dir.trim());		view_sts= (view_sts == null ?null : view_sts.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
