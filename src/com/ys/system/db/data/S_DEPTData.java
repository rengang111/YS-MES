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
public class S_DEPTData implements java.io.Serializable
{

	public S_DEPTData()
	{
	}

	/**
	*
	*/
	private String unitid;
	public String getUnitid()
	{
		return this.unitid;
	}
	public void setUnitid(String unitid)
	{
		this.unitid=unitid;
	}

	/**
	*
	*/
	private String unitname;
	public String getUnitname()
	{
		return this.unitname;
	}
	public void setUnitname(String unitname)
	{
		this.unitname=unitname;
	}

	/**
	*
	*/
	private String unitsimpledes;
	public String getUnitsimpledes()
	{
		return this.unitsimpledes;
	}
	public void setUnitsimpledes(String unitsimpledes)
	{
		this.unitsimpledes=unitsimpledes;
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
	private String orgid;
	public String getOrgid()
	{
		return this.orgid;
	}
	public void setOrgid(String orgid)
	{
		this.orgid=orgid;
	}

	/**
	*
	*/
	private String addresscode;
	public String getAddresscode()
	{
		return this.addresscode;
	}
	public void setAddresscode(String addresscode)
	{
		this.addresscode=addresscode;
	}

	/**
	*
	*/
	private String addressuser;
	public String getAddressuser()
	{
		return this.addressuser;
	}
	public void setAddressuser(String addressuser)
	{
		this.addressuser=addressuser;
	}

	/**
	*
	*/
	private String postcode;
	public String getPostcode()
	{
		return this.postcode;
	}
	public void setPostcode(String postcode)
	{
		this.postcode=postcode;
	}

	/**
	*
	*/
	private String telphone;
	public String getTelphone()
	{
		return this.telphone;
	}
	public void setTelphone(String telphone)
	{
		this.telphone=telphone;
	}

	/**
	*
	*/
	private String email;
	public String getEmail()
	{
		return this.email;
	}
	public void setEmail(String email)
	{
		this.email=email;
	}

	/**
	*
	*/
	private String unitproperty;
	public String getUnitproperty()
	{
		return this.unitproperty;
	}
	public void setUnitproperty(String unitproperty)
	{
		this.unitproperty=unitproperty;
	}

	/**
	*
	*/
	private String officeid;
	public String getOfficeid()
	{
		return this.officeid;
	}
	public void setOfficeid(String officeid)
	{
		this.officeid=officeid;
	}

	/**
	*
	*/
	private String areaid;
	public String getAreaid()
	{
		return this.areaid;
	}
	public void setAreaid(String areaid)
	{
		this.areaid=areaid;
	}

	/**
	*
	*/
	private String unittype;
	public String getUnittype()
	{
		return this.unittype;
	}
	public void setUnittype(String unittype)
	{
		this.unittype=unittype;
	}

	/**
	*
	*/
	private String mgrperson;
	public String getMgrperson()
	{
		return this.mgrperson;
	}
	public void setMgrperson(String mgrperson)
	{
		this.mgrperson=mgrperson;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("unitid = "+(unitid == null ? "null" : unitid)+"\n");		sb.append("unitname = "+(unitname == null ? "null" : unitname)+"\n");		sb.append("unitsimpledes = "+(unitsimpledes == null ? "null" : unitsimpledes)+"\n");		sb.append("jianpin = "+(jianpin == null ? "null" : jianpin)+"\n");		sb.append("parentid = "+(parentid == null ? "null" : parentid)+"\n");		sb.append("orgid = "+(orgid == null ? "null" : orgid)+"\n");		sb.append("addresscode = "+(addresscode == null ? "null" : addresscode)+"\n");		sb.append("addressuser = "+(addressuser == null ? "null" : addressuser)+"\n");		sb.append("postcode = "+(postcode == null ? "null" : postcode)+"\n");		sb.append("telphone = "+(telphone == null ? "null" : telphone)+"\n");		sb.append("email = "+(email == null ? "null" : email)+"\n");		sb.append("unitproperty = "+(unitproperty == null ? "null" : unitproperty)+"\n");		sb.append("officeid = "+(officeid == null ? "null" : officeid)+"\n");		sb.append("areaid = "+(areaid == null ? "null" : areaid)+"\n");		sb.append("unittype = "+(unittype == null ? "null" : unittype)+"\n");		sb.append("mgrperson = "+(mgrperson == null ? "null" : mgrperson)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("sortno = "+(sortno == null ? "null" : sortno.toString())+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		unitid= (unitid == null ?null : unitid.trim());		unitname= (unitname == null ?null : unitname.trim());		unitsimpledes= (unitsimpledes == null ?null : unitsimpledes.trim());		jianpin= (jianpin == null ?null : jianpin.trim());		parentid= (parentid == null ?null : parentid.trim());		orgid= (orgid == null ?null : orgid.trim());		addresscode= (addresscode == null ?null : addresscode.trim());		addressuser= (addressuser == null ?null : addressuser.trim());		postcode= (postcode == null ?null : postcode.trim());		telphone= (telphone == null ?null : telphone.trim());		email= (email == null ?null : email.trim());		unitproperty= (unitproperty == null ?null : unitproperty.trim());		officeid= (officeid == null ?null : officeid.trim());		areaid= (areaid == null ?null : areaid.trim());		unittype= (unittype == null ?null : unittype.trim());		mgrperson= (mgrperson == null ?null : mgrperson.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
