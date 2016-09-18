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
public class YW_PROJ_DOCData implements java.io.Serializable
{

	public YW_PROJ_DOCData()
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
	private int proj_id;
	public int getProj_id()
	{
		return this.proj_id;
	}
	public void setProj_id(int proj_id)
	{
		this.proj_id=proj_id;
	}

	/**
	*
	*/
	private String doc_code;
	public String getDoc_code()
	{
		return this.doc_code;
	}
	public void setDoc_code(String doc_code)
	{
		this.doc_code=doc_code;
	}

	/**
	*
	*/
	private String version;
	public String getVersion()
	{
		return this.version;
	}
	public void setVersion(String version)
	{
		this.version=version;
	}

	/**
	*
	*/
	private String parts_name;
	public String getParts_name()
	{
		return this.parts_name;
	}
	public void setParts_name(String parts_name)
	{
		this.parts_name=parts_name;
	}

	/**
	*
	*/
	private String erp_code;
	public String getErp_code()
	{
		return this.erp_code;
	}
	public void setErp_code(String erp_code)
	{
		this.erp_code=erp_code;
	}

	/**
	*
	*/
	private String mate_req;
	public String getMate_req()
	{
		return this.mate_req;
	}
	public void setMate_req(String mate_req)
	{
		this.mate_req=mate_req;
	}

	/**
	*
	*/
	private String mach_req;
	public String getMach_req()
	{
		return this.mach_req;
	}
	public void setMach_req(String mach_req)
	{
		this.mach_req=mach_req;
	}

	/**
	*
	*/
	private String save_path;
	public String getSave_path()
	{
		return this.save_path;
	}
	public void setSave_path(String save_path)
	{
		this.save_path=save_path;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("proj_id = "+proj_id+"\n");		sb.append("doc_code = "+(doc_code == null ? "null" : doc_code)+"\n");		sb.append("version = "+(version == null ? "null" : version)+"\n");		sb.append("parts_name = "+(parts_name == null ? "null" : parts_name)+"\n");		sb.append("erp_code = "+(erp_code == null ? "null" : erp_code)+"\n");		sb.append("mate_req = "+(mate_req == null ? "null" : mate_req)+"\n");		sb.append("mach_req = "+(mach_req == null ? "null" : mach_req)+"\n");		sb.append("save_path = "+(save_path == null ? "null" : save_path)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		doc_code= (doc_code == null ?null : doc_code.trim());		version= (version == null ?null : version.trim());		parts_name= (parts_name == null ?null : parts_name.trim());		erp_code= (erp_code == null ?null : erp_code.trim());		mate_req= (mate_req == null ?null : mate_req.trim());		mach_req= (mach_req == null ?null : mach_req.trim());		save_path= (save_path == null ?null : save_path.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
