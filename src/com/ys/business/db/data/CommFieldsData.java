package com.ys.business.db.data;
import java.sql.*;import java.io.InputStream;

/**
* <p>Title: </p>
* <p>Description: </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class CommFieldsData implements java.io.Serializable
{

	public CommFieldsData()
	{
	}
	/**	*	*/	private String createtime;	public String getCreatetime()	{		return this.createtime;	}	public void setCreatetime(String createtime)	{		this.createtime=createtime;	}	/**	*	*/	private String createperson;	public String getCreateperson()	{		return this.createperson;	}	public void setCreateperson(String createperson)	{		this.createperson=createperson;	}	/**	*	*/	private String createunitid;	public String getCreateunitid()	{		return this.createunitid;	}	public void setCreateunitid(String createunitid)	{		this.createunitid=createunitid;	}	/**	*	*/	private String modifytime;	public String getModifytime()	{		return this.modifytime;	}	public void setModifytime(String modifytime)	{		this.modifytime=modifytime;	}	/**	*	*/	private String modifyperson;	public String getModifyperson()	{		return this.modifyperson;	}	public void setModifyperson(String modifyperson)	{		this.modifyperson=modifyperson;	}	/**	*����ֵ	*/	private String formid;	public String getFormid()	{		return this.formid;	}	public void setFormid(String formid)	{		this.formid=formid;	}	/**	*	*/	private String deleteflag;	public String getDeleteflag()	{		return this.deleteflag;	}	public void setDeleteflag(String deleteflag)	{		this.deleteflag=deleteflag;	}	/**	*����ֵ	*/	private String returnvalue;	public String getReturnvalue()	{		return this.returnvalue;	}	public void setReturnvalue(String returnvalue)	{		this.returnvalue=returnvalue;	}	/**	*����ֵ	*/	private String returnsql;	public String getReturnsql()	{		return this.returnsql;	}	public void setReturnsql(String returnsql)	{		this.returnsql=returnsql;	}	public String toString() {		StringBuffer sb = new StringBuffer("");		sb.append("***** DataObject list begin *****\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");		return sb.toString() ;	}	public void toTrim() {		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		formid= (formid == null ?null : formid.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());	}

}
