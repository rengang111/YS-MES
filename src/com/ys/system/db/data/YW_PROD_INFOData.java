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
public class YW_PROD_INFOData implements java.io.Serializable
{

	public YW_PROD_INFOData()
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
	private String code;
	public String getCode()
	{
		return this.code;
	}
	public void setCode(String code)
	{
		this.code=code;
	}

	/**
	*
	*/
	private String name_ch;
	public String getName_ch()
	{
		return this.name_ch;
	}
	public void setName_ch(String name_ch)
	{
		this.name_ch=name_ch;
	}

	/**
	*
	*/
	private String name_en;
	public String getName_en()
	{
		return this.name_en;
	}
	public void setName_en(String name_en)
	{
		this.name_en=name_en;
	}

	/**
	*
	*/
	private String pack_ch;
	public String getPack_ch()
	{
		return this.pack_ch;
	}
	public void setPack_ch(String pack_ch)
	{
		this.pack_ch=pack_ch;
	}

	/**
	*
	*/
	private String pack_en;
	public String getPack_en()
	{
		return this.pack_en;
	}
	public void setPack_en(String pack_en)
	{
		this.pack_en=pack_en;
	}

	/**
	*
	*/
	private String remark;
	public String getRemark()
	{
		return this.remark;
	}
	public void setRemark(String remark)
	{
		this.remark=remark;
	}

	/**
	*
	*/
	private String desc_ch;
	public String getDesc_ch()
	{
		return this.desc_ch;
	}
	public void setDesc_ch(String desc_ch)
	{
		this.desc_ch=desc_ch;
	}

	/**
	*
	*/
	private String desc_en;
	public String getDesc_en()
	{
		return this.desc_en;
	}
	public void setDesc_en(String desc_en)
	{
		this.desc_en=desc_en;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("code = "+(code == null ? "null" : code)+"\n");		sb.append("name_ch = "+(name_ch == null ? "null" : name_ch)+"\n");		sb.append("name_en = "+(name_en == null ? "null" : name_en)+"\n");		sb.append("pack_ch = "+(pack_ch == null ? "null" : pack_ch)+"\n");		sb.append("pack_en = "+(pack_en == null ? "null" : pack_en)+"\n");		sb.append("remark = "+(remark == null ? "null" : remark)+"\n");		sb.append("desc_ch = "+(desc_ch == null ? "null" : desc_ch)+"\n");		sb.append("desc_en = "+(desc_en == null ? "null" : desc_en)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		code= (code == null ?null : code.trim());		name_ch= (name_ch == null ?null : name_ch.trim());		name_en= (name_en == null ?null : name_en.trim());		pack_ch= (pack_ch == null ?null : pack_ch.trim());		pack_en= (pack_en == null ?null : pack_en.trim());		remark= (remark == null ?null : remark.trim());		desc_ch= (desc_ch == null ?null : desc_ch.trim());		desc_en= (desc_en == null ?null : desc_en.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
