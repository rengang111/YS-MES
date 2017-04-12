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
public class B_MouldContractBaseInfoData 
{

	public B_MouldContractBaseInfoData()
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
	private String contractid;
	public String getContractid()
	{
		return this.contractid;
	}
	public void setContractid(String contractid)
	{
		this.contractid=contractid;
	}

	/**
	*
	*/
	private String productmodelid;
	public String getProductmodelid()
	{
		return this.productmodelid;
	}
	public void setProductmodelid(String productmodelid)
	{
		this.productmodelid=productmodelid;
	}

	/**
	*
	*/
	private String type;
	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type=type;
	}

	/**
	*
	*/
	private String supplierid;
	public String getSupplierid()
	{
		return this.supplierid;
	}
	public void setSupplierid(String supplierid)
	{
		this.supplierid=supplierid;
	}

	/**
	*
	*/
	private String contractdate;
	public String getContractdate()
	{
		return this.contractdate;
	}
	public void setContractdate(String contractdate)
	{
		this.contractdate=contractdate;
	}

	/**
	*
	*/
	private String deliverdate;
	public String getDeliverdate()
	{
		return this.deliverdate;
	}
	public void setDeliverdate(String deliverdate)
	{
		this.deliverdate=deliverdate;
	}

	/**
	*
	*/
	private String belong;
	public String getBelong()
	{
		return this.belong;
	}
	public void setBelong(String belong)
	{
		this.belong=belong;
	}

	/**
	*
	*/
	private String oursidepay;
	public String getOursidepay()
	{
		return this.oursidepay;
	}
	public void setOursidepay(String oursidepay)
	{
		this.oursidepay=oursidepay;
	}

	/**
	*
	*/
	private String providerpay;
	public String getProviderpay()
	{
		return this.providerpay;
	}
	public void setProviderpay(String providerpay)
	{
		this.providerpay=providerpay;
	}

	/**
	*
	*/
	private String returncase;
	public String getReturncase()
	{
		return this.returncase;
	}
	public void setReturncase(String returncase)
	{
		this.returncase=returncase;
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
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("contractid = "+(contractid == null ? "null" : contractid)+"\n");		sb.append("productmodelid = "+(productmodelid == null ? "null" : productmodelid)+"\n");		sb.append("type = "+(type == null ? "null" : type)+"\n");		sb.append("supplierid = "+(supplierid == null ? "null" : supplierid)+"\n");		sb.append("contractdate = "+(contractdate == null ? "null" : contractdate)+"\n");		sb.append("deliverdate = "+(deliverdate == null ? "null" : deliverdate)+"\n");		sb.append("belong = "+(belong == null ? "null" : belong)+"\n");		sb.append("oursidepay = "+(oursidepay == null ? "null" : oursidepay)+"\n");		sb.append("providerpay = "+(providerpay == null ? "null" : providerpay)+"\n");		sb.append("returncase = "+(returncase == null ? "null" : returncase)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		contractid= (contractid == null ?null : contractid.trim());		productmodelid= (productmodelid == null ?null : productmodelid.trim());		type= (type == null ?null : type.trim());		supplierid= (supplierid == null ?null : supplierid.trim());		contractdate= (contractdate == null ?null : contractdate.trim());		deliverdate= (deliverdate == null ?null : deliverdate.trim());		belong= (belong == null ?null : belong.trim());		oursidepay= (oursidepay == null ?null : oursidepay.trim());		providerpay= (providerpay == null ?null : providerpay.trim());		returncase= (returncase == null ?null : returncase.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
