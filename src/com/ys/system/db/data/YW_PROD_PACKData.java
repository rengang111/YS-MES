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
public class YW_PROD_PACKData implements java.io.Serializable
{

	public YW_PROD_PACKData()
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
	private int prod_id;
	public int getProd_id()
	{
		return this.prod_id;
	}
	public void setProd_id(int prod_id)
	{
		this.prod_id=prod_id;
	}

	/**
	*
	*/
	private String label_mat;
	public String getLabel_mat()
	{
		return this.label_mat;
	}
	public void setLabel_mat(String label_mat)
	{
		this.label_mat=label_mat;
	}

	/**
	*
	*/
	private String label_ai;
	public String getLabel_ai()
	{
		return this.label_ai;
	}
	public void setLabel_ai(String label_ai)
	{
		this.label_ai=label_ai;
	}

	/**
	*
	*/
	private String label_path;
	public String getLabel_path()
	{
		return this.label_path;
	}
	public void setLabel_path(String label_path)
	{
		this.label_path=label_path;
	}

	/**
	*
	*/
	private String pack_mat;
	public String getPack_mat()
	{
		return this.pack_mat;
	}
	public void setPack_mat(String pack_mat)
	{
		this.pack_mat=pack_mat;
	}

	/**
	*
	*/
	private String pack_ai;
	public String getPack_ai()
	{
		return this.pack_ai;
	}
	public void setPack_ai(String pack_ai)
	{
		this.pack_ai=pack_ai;
	}

	/**
	*
	*/
	private String pack_path;
	public String getPack_path()
	{
		return this.pack_path;
	}
	public void setPack_path(String pack_path)
	{
		this.pack_path=pack_path;
	}

	/**
	*
	*/
	private String instr_mat;
	public String getInstr_mat()
	{
		return this.instr_mat;
	}
	public void setInstr_mat(String instr_mat)
	{
		this.instr_mat=instr_mat;
	}

	/**
	*
	*/
	private String instr_ai;
	public String getInstr_ai()
	{
		return this.instr_ai;
	}
	public void setInstr_ai(String instr_ai)
	{
		this.instr_ai=instr_ai;
	}

	/**
	*
	*/
	private String instr_path;
	public String getInstr_path()
	{
		return this.instr_path;
	}
	public void setInstr_path(String instr_path)
	{
		this.instr_path=instr_path;
	}

	/**
	*
	*/
	private String cart_mat;
	public String getCart_mat()
	{
		return this.cart_mat;
	}
	public void setCart_mat(String cart_mat)
	{
		this.cart_mat=cart_mat;
	}

	/**
	*
	*/
	private String cart_ai;
	public String getCart_ai()
	{
		return this.cart_ai;
	}
	public void setCart_ai(String cart_ai)
	{
		this.cart_ai=cart_ai;
	}

	/**
	*
	*/
	private String cart_path;
	public String getCart_path()
	{
		return this.cart_path;
	}
	public void setCart_path(String cart_path)
	{
		this.cart_path=cart_path;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("prod_id = "+prod_id+"\n");		sb.append("label_mat = "+(label_mat == null ? "null" : label_mat)+"\n");		sb.append("label_ai = "+(label_ai == null ? "null" : label_ai)+"\n");		sb.append("label_path = "+(label_path == null ? "null" : label_path)+"\n");		sb.append("pack_mat = "+(pack_mat == null ? "null" : pack_mat)+"\n");		sb.append("pack_ai = "+(pack_ai == null ? "null" : pack_ai)+"\n");		sb.append("pack_path = "+(pack_path == null ? "null" : pack_path)+"\n");		sb.append("instr_mat = "+(instr_mat == null ? "null" : instr_mat)+"\n");		sb.append("instr_ai = "+(instr_ai == null ? "null" : instr_ai)+"\n");		sb.append("instr_path = "+(instr_path == null ? "null" : instr_path)+"\n");		sb.append("cart_mat = "+(cart_mat == null ? "null" : cart_mat)+"\n");		sb.append("cart_ai = "+(cart_ai == null ? "null" : cart_ai)+"\n");		sb.append("cart_path = "+(cart_path == null ? "null" : cart_path)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		label_mat= (label_mat == null ?null : label_mat.trim());		label_ai= (label_ai == null ?null : label_ai.trim());		label_path= (label_path == null ?null : label_path.trim());		pack_mat= (pack_mat == null ?null : pack_mat.trim());		pack_ai= (pack_ai == null ?null : pack_ai.trim());		pack_path= (pack_path == null ?null : pack_path.trim());		instr_mat= (instr_mat == null ?null : instr_mat.trim());		instr_ai= (instr_ai == null ?null : instr_ai.trim());		instr_path= (instr_path == null ?null : instr_path.trim());		cart_mat= (cart_mat == null ?null : cart_mat.trim());		cart_ai= (cart_ai == null ?null : cart_ai.trim());		cart_path= (cart_path == null ?null : cart_path.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
