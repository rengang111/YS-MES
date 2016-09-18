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
public class S_MENUData implements java.io.Serializable
{

	public S_MENUData()
	{
	}

	/**
	*
	*/
	private String menuid;
	public String getMenuid()
	{
		return this.menuid;
	}
	public void setMenuid(String menuid)
	{
		this.menuid=menuid;
	}

	/**
	*
	*/
	private String menuname;
	public String getMenuname()
	{
		return this.menuname;
	}
	public void setMenuname(String menuname)
	{
		this.menuname=menuname;
	}

	/**
	*
	*/
	private String menudes;
	public String getMenudes()
	{
		return this.menudes;
	}
	public void setMenudes(String menudes)
	{
		this.menudes=menudes;
	}

	/**
	*
	*/
	private String menuywclass;
	public String getMenuywclass()
	{
		return this.menuywclass;
	}
	public void setMenuywclass(String menuywclass)
	{
		this.menuywclass=menuywclass;
	}

	/**
	*
	*/
	private String menuparentid;
	public String getMenuparentid()
	{
		return this.menuparentid;
	}
	public void setMenuparentid(String menuparentid)
	{
		this.menuparentid=menuparentid;
	}

	/**
	*
	*/
	private String menuurl;
	public String getMenuurl()
	{
		return this.menuurl;
	}
	public void setMenuurl(String menuurl)
	{
		this.menuurl=menuurl;
	}

	/**
	*
	*/
	private String menuicon1;
	public String getMenuicon1()
	{
		return this.menuicon1;
	}
	public void setMenuicon1(String menuicon1)
	{
		this.menuicon1=menuicon1;
	}

	/**
	*
	*/
	private String menuicon2;
	public String getMenuicon2()
	{
		return this.menuicon2;
	}
	public void setMenuicon2(String menuicon2)
	{
		this.menuicon2=menuicon2;
	}

	/**
	*
	*/
	private String menutype;
	public String getMenutype()
	{
		return this.menutype;
	}
	public void setMenutype(String menutype)
	{
		this.menutype=menutype;
	}

	/**
	*
	*/
	private String menuopenflag;
	public String getMenuopenflag()
	{
		return this.menuopenflag;
	}
	public void setMenuopenflag(String menuopenflag)
	{
		this.menuopenflag=menuopenflag;
	}

	/**
	*
	*/
	private String menuviewflag;
	public String getMenuviewflag()
	{
		return this.menuviewflag;
	}
	public void setMenuviewflag(String menuviewflag)
	{
		this.menuviewflag=menuviewflag;
	}

	/**
	*
	*/
	private String menuwfnode;
	public String getMenuwfnode()
	{
		return this.menuwfnode;
	}
	public void setMenuwfnode(String menuwfnode)
	{
		this.menuwfnode=menuwfnode;
	}

	/**
	*
	*/
	private String menunnableflag;
	public String getMenunnableflag()
	{
		return this.menunnableflag;
	}
	public void setMenunnableflag(String menunnableflag)
	{
		this.menunnableflag=menunnableflag;
	}

	/**
	*
	*/
	private String relationalmenuid;
	public String getRelationalmenuid()
	{
		return this.relationalmenuid;
	}
	public void setRelationalmenuid(String relationalmenuid)
	{
		this.relationalmenuid=relationalmenuid;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("menuid = "+(menuid == null ? "null" : menuid)+"\n");		sb.append("menuname = "+(menuname == null ? "null" : menuname)+"\n");		sb.append("menudes = "+(menudes == null ? "null" : menudes)+"\n");		sb.append("menuywclass = "+(menuywclass == null ? "null" : menuywclass)+"\n");		sb.append("menuparentid = "+(menuparentid == null ? "null" : menuparentid)+"\n");		sb.append("menuurl = "+(menuurl == null ? "null" : menuurl)+"\n");		sb.append("menuicon1 = "+(menuicon1 == null ? "null" : menuicon1)+"\n");		sb.append("menuicon2 = "+(menuicon2 == null ? "null" : menuicon2)+"\n");		sb.append("menutype = "+(menutype == null ? "null" : menutype)+"\n");		sb.append("menuopenflag = "+(menuopenflag == null ? "null" : menuopenflag)+"\n");		sb.append("menuviewflag = "+(menuviewflag == null ? "null" : menuviewflag)+"\n");		sb.append("menuwfnode = "+(menuwfnode == null ? "null" : menuwfnode)+"\n");		sb.append("menunnableflag = "+(menunnableflag == null ? "null" : menunnableflag)+"\n");		sb.append("relationalmenuid = "+(relationalmenuid == null ? "null" : relationalmenuid)+"\n");		sb.append("sortno = "+(sortno == null ? "null" : sortno.toString())+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		menuid= (menuid == null ?null : menuid.trim());		menuname= (menuname == null ?null : menuname.trim());		menudes= (menudes == null ?null : menudes.trim());		menuywclass= (menuywclass == null ?null : menuywclass.trim());		menuparentid= (menuparentid == null ?null : menuparentid.trim());		menuurl= (menuurl == null ?null : menuurl.trim());		menuicon1= (menuicon1 == null ?null : menuicon1.trim());		menuicon2= (menuicon2 == null ?null : menuicon2.trim());		menutype= (menutype == null ?null : menutype.trim());		menuopenflag= (menuopenflag == null ?null : menuopenflag.trim());		menuviewflag= (menuviewflag == null ?null : menuviewflag.trim());		menuwfnode= (menuwfnode == null ?null : menuwfnode.trim());		menunnableflag= (menunnableflag == null ?null : menunnableflag.trim());		relationalmenuid= (relationalmenuid == null ?null : relationalmenuid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
