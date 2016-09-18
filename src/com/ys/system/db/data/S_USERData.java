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
public class S_USERData implements java.io.Serializable
{

	public S_USERData()
	{
	}

	/**
	*
	*/
	private String userid;
	public String getUserid()
	{
		return this.userid;
	}
	public void setUserid(String userid)
	{
		this.userid=userid;
	}

	/**
	*
	*/
	private String loginid;
	public String getLoginid()
	{
		return this.loginid;
	}
	public void setLoginid(String loginid)
	{
		this.loginid=loginid;
	}

	/**
	*
	*/
	private String loginname;
	public String getLoginname()
	{
		return this.loginname;
	}
	public void setLoginname(String loginname)
	{
		this.loginname=loginname;
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
	private String loginpwd;
	public String getLoginpwd()
	{
		return this.loginpwd;
	}
	public void setLoginpwd(String loginpwd)
	{
		this.loginpwd=loginpwd;
	}

	/**
	*
	*/
	private String proxyunitid;
	public String getProxyunitid()
	{
		return this.proxyunitid;
	}
	public void setProxyunitid(String proxyunitid)
	{
		this.proxyunitid=proxyunitid;
	}

	/**
	*
	*/
	private String sex;
	public String getSex()
	{
		return this.sex;
	}
	public void setSex(String sex)
	{
		this.sex=sex;
	}

	/**
	*
	*/
	private String duty;
	public String getDuty()
	{
		return this.duty;
	}
	public void setDuty(String duty)
	{
		this.duty=duty;
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
	private String handphone;
	public String getHandphone()
	{
		return this.handphone;
	}
	public void setHandphone(String handphone)
	{
		this.handphone=handphone;
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
	private String pwdquestion1;
	public String getPwdquestion1()
	{
		return this.pwdquestion1;
	}
	public void setPwdquestion1(String pwdquestion1)
	{
		this.pwdquestion1=pwdquestion1;
	}

	/**
	*
	*/
	private String pwdquestion2;
	public String getPwdquestion2()
	{
		return this.pwdquestion2;
	}
	public void setPwdquestion2(String pwdquestion2)
	{
		this.pwdquestion2=pwdquestion2;
	}

	/**
	*
	*/
	private String workid;
	public String getWorkid()
	{
		return this.workid;
	}
	public void setWorkid(String workid)
	{
		this.workid=workid;
	}

	/**
	*
	*/
	private Blob photo;
	public Blob getPhoto()
	{
		return this.photo;
	}
	public void setPhoto(Blob photo)
	{
		this.photo=photo;
	}
	private InputStream photoStream;
	public InputStream getPhotoStream()
	{
		return this.photoStream;
	}
	public void setPhotoStream(InputStream photoStream)
	{
		this.photoStream=photoStream;
	}

	/**
	*
	*/
	private String lockflag;
	public String getLockflag()
	{
		return this.lockflag;
	}
	public void setLockflag(String lockflag)
	{
		this.lockflag=lockflag;
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
	*
	*/
	private String enablestarttime;
	public String getEnablestarttime()
	{
		return this.enablestarttime;
	}
	public void setEnablestarttime(String enablestarttime)
	{
		this.enablestarttime=enablestarttime;
	}

	/**
	*
	*/
	private String enableendtime;
	public String getEnableendtime()
	{
		return this.enableendtime;
	}
	public void setEnableendtime(String enableendtime)
	{
		this.enableendtime=enableendtime;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("userid = "+(userid == null ? "null" : userid)+"\n");		sb.append("loginid = "+(loginid == null ? "null" : loginid)+"\n");		sb.append("loginname = "+(loginname == null ? "null" : loginname)+"\n");		sb.append("jianpin = "+(jianpin == null ? "null" : jianpin)+"\n");		sb.append("unitid = "+(unitid == null ? "null" : unitid)+"\n");		sb.append("loginpwd = "+(loginpwd == null ? "null" : loginpwd)+"\n");		sb.append("proxyunitid = "+(proxyunitid == null ? "null" : proxyunitid)+"\n");		sb.append("sex = "+(sex == null ? "null" : sex)+"\n");		sb.append("duty = "+(duty == null ? "null" : duty)+"\n");		sb.append("addresscode = "+(addresscode == null ? "null" : addresscode)+"\n");		sb.append("addressuser = "+(addressuser == null ? "null" : addressuser)+"\n");		sb.append("postcode = "+(postcode == null ? "null" : postcode)+"\n");		sb.append("telphone = "+(telphone == null ? "null" : telphone)+"\n");		sb.append("handphone = "+(handphone == null ? "null" : handphone)+"\n");		sb.append("email = "+(email == null ? "null" : email)+"\n");		sb.append("pwdquestion1 = "+(pwdquestion1 == null ? "null" : pwdquestion1)+"\n");		sb.append("pwdquestion2 = "+(pwdquestion2 == null ? "null" : pwdquestion2)+"\n");		sb.append("workid = "+(workid == null ? "null" : workid)+"\n");		sb.append("lockflag = "+(lockflag == null ? "null" : lockflag)+"\n");		sb.append("enableflag = "+(enableflag == null ? "null" : enableflag)+"\n");		sb.append("enablestarttime = "+(enablestarttime == null ? "null" : enablestarttime)+"\n");		sb.append("enableendtime = "+(enableendtime == null ? "null" : enableendtime)+"\n");		sb.append("sortno = "+(sortno == null ? "null" : sortno.toString())+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		userid= (userid == null ?null : userid.trim());		loginid= (loginid == null ?null : loginid.trim());		loginname= (loginname == null ?null : loginname.trim());		jianpin= (jianpin == null ?null : jianpin.trim());		unitid= (unitid == null ?null : unitid.trim());		loginpwd= (loginpwd == null ?null : loginpwd.trim());		proxyunitid= (proxyunitid == null ?null : proxyunitid.trim());		sex= (sex == null ?null : sex.trim());		duty= (duty == null ?null : duty.trim());		addresscode= (addresscode == null ?null : addresscode.trim());		addressuser= (addressuser == null ?null : addressuser.trim());		postcode= (postcode == null ?null : postcode.trim());		telphone= (telphone == null ?null : telphone.trim());		handphone= (handphone == null ?null : handphone.trim());		email= (email == null ?null : email.trim());		pwdquestion1= (pwdquestion1 == null ?null : pwdquestion1.trim());		pwdquestion2= (pwdquestion2 == null ?null : pwdquestion2.trim());		workid= (workid == null ?null : workid.trim());		lockflag= (lockflag == null ?null : lockflag.trim());		enableflag= (enableflag == null ?null : enableflag.trim());		enablestarttime= (enablestarttime == null ?null : enablestarttime.trim());		enableendtime= (enableendtime == null ?null : enableendtime.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
