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
public class S_DICData implements java.io.Serializable
{

	public S_DICData()
	{
	}

	/**
	*
	*/
	private String dicid;
	public String getDicid()
	{
		return this.dicid;
	}
	public void setDicid(String dicid)
	{
		this.dicid=dicid;
	}

	/**
	*
	*/
	private String dicname;
	public String getDicname()
	{
		return this.dicname;
	}
	public void setDicname(String dicname)
	{
		this.dicname=dicname;
	}

	/**
	*
	*/
	private String dicdes;
	public String getDicdes()
	{
		return this.dicdes;
	}
	public void setDicdes(String dicdes)
	{
		this.dicdes=dicdes;
	}

	/**
	*
	*/
	private String dicprarentid;
	public String getDicprarentid()
	{
		return this.dicprarentid;
	}
	public void setDicprarentid(String dicprarentid)
	{
		this.dicprarentid=dicprarentid;
	}

	/**
	*
	*/
	private String dictypeid;
	public String getDictypeid()
	{
		return this.dictypeid;
	}
	public void setDictypeid(String dictypeid)
	{
		this.dictypeid=dictypeid;
	}

	/**
	*
	*/
	private String isleaf;
	public String getIsleaf()
	{
		return this.isleaf;
	}
	public void setIsleaf(String isleaf)
	{
		this.isleaf=isleaf;
	}

	/**
	*
	*/
	private String jianpin;
	public String getJianpin()
	{
		return this.jianpin;
	}
	public void setJianpin(String jianpin)
	{
		this.jianpin=jianpin;
	}

	/**
	*
	*/
	private Integer sortno;
	public Integer getSortno()
	{
		return this.sortno;
	}
	public void setSortno(Integer sortno)
	{
		this.sortno=sortno;
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
	private String enableflag;
	public String getEnableflag()
	{
		return this.enableflag;
	}
	public void setEnableflag(String enableflag)
	{
		this.enableflag=enableflag;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("dicid = "+(dicid == null ? "null" : dicid)+"\n");		sb.append("dicname = "+(dicname == null ? "null" : dicname)+"\n");		sb.append("dicdes = "+(dicdes == null ? "null" : dicdes)+"\n");		sb.append("dicprarentid = "+(dicprarentid == null ? "null" : dicprarentid)+"\n");		sb.append("dictypeid = "+(dictypeid == null ? "null" : dictypeid)+"\n");		sb.append("isleaf = "+(isleaf == null ? "null" : isleaf)+"\n");		sb.append("jianpin = "+(jianpin == null ? "null" : jianpin)+"\n");		sb.append("sortno = "+(sortno == null ? "null" : sortno.toString())+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("enableflag = "+(enableflag == null ? "null" : enableflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		dicid= (dicid == null ?null : dicid.trim());		dicname= (dicname == null ?null : dicname.trim());		dicdes= (dicdes == null ?null : dicdes.trim());		dicprarentid= (dicprarentid == null ?null : dicprarentid.trim());		dictypeid= (dictypeid == null ?null : dictypeid.trim());		isleaf= (isleaf == null ?null : isleaf.trim());		jianpin= (jianpin == null ?null : jianpin.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		enableflag= (enableflag == null ?null : enableflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
