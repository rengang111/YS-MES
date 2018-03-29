package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.business.db.data.*;
import com.ys.util.basedao.BaseAbstractDao;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_MaterialDao extends BaseAbstractDao
{
	public B_MaterialData beanData=new B_MaterialData();
	public B_MaterialDao()
	{
	}
	public B_MaterialDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MaterialData)FindByPrimaryKey(beanData);
		}
		catch(Exception e)
		{
			throw new Exception("Primary key not already exists");
		
		}
	}


	/**
	 *
	 * @param beanData
	 */
	public String Create(Object beanDataTmp) throws Exception
	{
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Material( recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getSerialnumber());			statement.setString( 4,beanData.getMaterialname());			statement.setString( 5,beanData.getCategoryid());			statement.setString( 6,beanData.getDescription());			statement.setString( 7,beanData.getSharemodel());			statement.setString( 8,beanData.getUnit());			statement.setString( 9,beanData.getParentid());			statement.setString( 10,beanData.getSubid());			statement.setString( 11,beanData.getSubiddes());			statement.setString( 12,beanData.getProductmodel());			statement.setString( 13,beanData.getCustomerid());			statement.setString( 14,beanData.getImage_filename());			statement.setString( 15,beanData.getPurchasetype());			statement.setString( 16,beanData.getOriginalid());			statement.setString( 17,beanData.getSafetyinventory());			statement.setString( 18,beanData.getWaitstockin());			statement.setString( 19,beanData.getWaitstockout());			statement.setString( 20,beanData.getQuantityonhand());			statement.setString( 21,beanData.getAvailabeltopromise());			statement.setString( 22,beanData.getAccountingquantity());			statement.setString( 23,beanData.getMaprice());			statement.setString( 24,beanData.getBeginningwaitin());			statement.setString( 25,beanData.getBeginningwaitout());			statement.setString( 26,beanData.getBeginningavailabel());			statement.setString( 27,beanData.getBeginninginventory());			statement.setString( 28,beanData.getBeginningprice());			statement.setString( 29,beanData.getQuantityeditflag());			statement.setString( 30,beanData.getStocktype());			statement.setString( 31,beanData.getDeptguid());			statement.setString( 32,beanData.getCreatetime());			statement.setString( 33,beanData.getCreateperson());			statement.setString( 34,beanData.getCreateunitid());			statement.setString( 35,beanData.getModifytime());			statement.setString( 36,beanData.getModifyperson());			statement.setString( 37,beanData.getDeleteflag());			statement.setString( 38,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Material( recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}


	/**
	 *
	 * @param beanData
	 */
	public String getInsertSQL()
	{
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("INSERT INTO B_Material( recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getSerialnumber()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialname()) + "',");			bufSQL.append("'" + nullString(beanData.getCategoryid()) + "',");			bufSQL.append("'" + nullString(beanData.getDescription()) + "',");			bufSQL.append("'" + nullString(beanData.getSharemodel()) + "',");			bufSQL.append("'" + nullString(beanData.getUnit()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubiddes()) + "',");			bufSQL.append("'" + nullString(beanData.getProductmodel()) + "',");			bufSQL.append("'" + nullString(beanData.getCustomerid()) + "',");			bufSQL.append("'" + nullString(beanData.getImage_filename()) + "',");			bufSQL.append("'" + nullString(beanData.getPurchasetype()) + "',");			bufSQL.append("'" + nullString(beanData.getOriginalid()) + "',");			bufSQL.append("'" + nullString(beanData.getSafetyinventory()) + "',");			bufSQL.append("'" + nullString(beanData.getWaitstockin()) + "',");			bufSQL.append("'" + nullString(beanData.getWaitstockout()) + "',");			bufSQL.append("'" + nullString(beanData.getQuantityonhand()) + "',");			bufSQL.append("'" + nullString(beanData.getAvailabeltopromise()) + "',");			bufSQL.append("'" + nullString(beanData.getAccountingquantity()) + "',");			bufSQL.append("'" + nullString(beanData.getMaprice()) + "',");			bufSQL.append("'" + nullString(beanData.getBeginningwaitin()) + "',");			bufSQL.append("'" + nullString(beanData.getBeginningwaitout()) + "',");			bufSQL.append("'" + nullString(beanData.getBeginningavailabel()) + "',");			bufSQL.append("'" + nullString(beanData.getBeginninginventory()) + "',");			bufSQL.append("'" + nullString(beanData.getBeginningprice()) + "',");			bufSQL.append("'" + nullString(beanData.getQuantityeditflag()) + "',");			bufSQL.append("'" + nullString(beanData.getStocktype()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
			bufSQL.append(")");

			beanData.setReturnsql(bufSQL.toString()); 
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			return bufSQL.toString();
		}
	}


	/**
	 *
	 * @param beanData
	 */
	public String Insert(Object beanDataTmp) throws Exception
	{
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Material( recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getSerialnumber());			statement.setString( 4,beanData.getMaterialname());			statement.setString( 5,beanData.getCategoryid());			statement.setString( 6,beanData.getDescription());			statement.setString( 7,beanData.getSharemodel());			statement.setString( 8,beanData.getUnit());			statement.setString( 9,beanData.getParentid());			statement.setString( 10,beanData.getSubid());			statement.setString( 11,beanData.getSubiddes());			statement.setString( 12,beanData.getProductmodel());			statement.setString( 13,beanData.getCustomerid());			statement.setString( 14,beanData.getImage_filename());			statement.setString( 15,beanData.getPurchasetype());			statement.setString( 16,beanData.getOriginalid());			statement.setString( 17,beanData.getSafetyinventory());			statement.setString( 18,beanData.getWaitstockin());			statement.setString( 19,beanData.getWaitstockout());			statement.setString( 20,beanData.getQuantityonhand());			statement.setString( 21,beanData.getAvailabeltopromise());			statement.setString( 22,beanData.getAccountingquantity());			statement.setString( 23,beanData.getMaprice());			statement.setString( 24,beanData.getBeginningwaitin());			statement.setString( 25,beanData.getBeginningwaitout());			statement.setString( 26,beanData.getBeginningavailabel());			statement.setString( 27,beanData.getBeginninginventory());			statement.setString( 28,beanData.getBeginningprice());			statement.setString( 29,beanData.getQuantityeditflag());			statement.setString( 30,beanData.getStocktype());			statement.setString( 31,beanData.getDeptguid());			statement.setString( 32,beanData.getCreatetime());			statement.setString( 33,beanData.getCreateperson());			statement.setString( 34,beanData.getCreateunitid());			statement.setString( 35,beanData.getModifytime());			statement.setString( 36,beanData.getModifyperson());			statement.setString( 37,beanData.getDeleteflag());			statement.setString( 38,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Material( recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param statement
	 */
	public void Remove(Object beanDataTmp)  throws Exception
	{
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_Material WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Material: "+ e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param statement
	 */
	public String getDeleteSQL(Object beanDataTmp) 
	{
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_Material WHERE ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "'");
			beanData.setReturnsql(bufSQL.toString()); 
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			return bufSQL.toString();
		}
	}

	/**
	 *
	 * @param statement
	 */
	public void RemoveByWhere(String astr_Where)  throws Exception
	{
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			String str_Where=astr_Where;
			if(str_Where==null)
				str_Where="";
			str_Where=str_Where.trim(); 
			if(!str_Where.equals(""))
				str_Where=" WHERE " + str_Where ; 
			statement = connection.prepareStatement("DELETE FROM B_Material"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Material: "+ e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param statement
	 */
	public Object FindByPrimaryKey(Object beanDataTmp)  throws Exception
	{
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
		B_MaterialData returnData=new B_MaterialData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Material WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setMaterialid( resultSet.getString( 2));			returnData.setSerialnumber( resultSet.getString( 3));			returnData.setMaterialname( resultSet.getString( 4));			returnData.setCategoryid( resultSet.getString( 5));			returnData.setDescription( resultSet.getString( 6));			returnData.setSharemodel( resultSet.getString( 7));			returnData.setUnit( resultSet.getString( 8));			returnData.setParentid( resultSet.getString( 9));			returnData.setSubid( resultSet.getString( 10));			returnData.setSubiddes( resultSet.getString( 11));			returnData.setProductmodel( resultSet.getString( 12));			returnData.setCustomerid( resultSet.getString( 13));			returnData.setImage_filename( resultSet.getString( 14));			returnData.setPurchasetype( resultSet.getString( 15));			returnData.setOriginalid( resultSet.getString( 16));			returnData.setSafetyinventory( resultSet.getString( 17));			returnData.setWaitstockin( resultSet.getString( 18));			returnData.setWaitstockout( resultSet.getString( 19));			returnData.setQuantityonhand( resultSet.getString( 20));			returnData.setAvailabeltopromise( resultSet.getString( 21));			returnData.setAccountingquantity( resultSet.getString( 22));			returnData.setMaprice( resultSet.getString( 23));			returnData.setBeginningwaitin( resultSet.getString( 24));			returnData.setBeginningwaitout( resultSet.getString( 25));			returnData.setBeginningavailabel( resultSet.getString( 26));			returnData.setBeginninginventory( resultSet.getString( 27));			returnData.setBeginningprice( resultSet.getString( 28));			returnData.setQuantityeditflag( resultSet.getString( 29));			returnData.setStocktype( resultSet.getString( 30));			returnData.setDeptguid( resultSet.getString( 31));			returnData.setCreatetime( resultSet.getString( 32));			returnData.setCreateperson( resultSet.getString( 33));			returnData.setCreateunitid( resultSet.getString( 34));			returnData.setModifytime( resultSet.getString( 35));			returnData.setModifyperson( resultSet.getString( 36));			returnData.setDeleteflag( resultSet.getString( 37));			returnData.setFormid( resultSet.getString( 38));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Material  WHERE  "+e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param statement
	 */
	public java.util.Vector Find(String astr_Where)  throws Exception
	{
		Connection connection = null;
		PreparedStatement statement = null;
		java.util.Vector v_1 = new java.util.Vector();
		try
		{
			connection = getConnection();
			String str_Where=astr_Where;
			if(str_Where==null)
				str_Where="";
			str_Where=str_Where.trim(); 
			if(!str_Where.equals(""))
				str_Where=" WHERE " + str_Where ; 
			statement = connection.prepareStatement("SELECT recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Material"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MaterialData returnData=new B_MaterialData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setMaterialid( resultSet.getString( 2));				returnData.setSerialnumber( resultSet.getString( 3));				returnData.setMaterialname( resultSet.getString( 4));				returnData.setCategoryid( resultSet.getString( 5));				returnData.setDescription( resultSet.getString( 6));				returnData.setSharemodel( resultSet.getString( 7));				returnData.setUnit( resultSet.getString( 8));				returnData.setParentid( resultSet.getString( 9));				returnData.setSubid( resultSet.getString( 10));				returnData.setSubiddes( resultSet.getString( 11));				returnData.setProductmodel( resultSet.getString( 12));				returnData.setCustomerid( resultSet.getString( 13));				returnData.setImage_filename( resultSet.getString( 14));				returnData.setPurchasetype( resultSet.getString( 15));				returnData.setOriginalid( resultSet.getString( 16));				returnData.setSafetyinventory( resultSet.getString( 17));				returnData.setWaitstockin( resultSet.getString( 18));				returnData.setWaitstockout( resultSet.getString( 19));				returnData.setQuantityonhand( resultSet.getString( 20));				returnData.setAvailabeltopromise( resultSet.getString( 21));				returnData.setAccountingquantity( resultSet.getString( 22));				returnData.setMaprice( resultSet.getString( 23));				returnData.setBeginningwaitin( resultSet.getString( 24));				returnData.setBeginningwaitout( resultSet.getString( 25));				returnData.setBeginningavailabel( resultSet.getString( 26));				returnData.setBeginninginventory( resultSet.getString( 27));				returnData.setBeginningprice( resultSet.getString( 28));				returnData.setQuantityeditflag( resultSet.getString( 29));				returnData.setStocktype( resultSet.getString( 30));				returnData.setDeptguid( resultSet.getString( 31));				returnData.setCreatetime( resultSet.getString( 32));				returnData.setCreateperson( resultSet.getString( 33));				returnData.setCreateunitid( resultSet.getString( 34));				returnData.setModifytime( resultSet.getString( 35));				returnData.setModifyperson( resultSet.getString( 36));				returnData.setDeleteflag( resultSet.getString( 37));				returnData.setFormid( resultSet.getString( 38));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Material" + astr_Where +e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param statement
	 */
	public void Store()  throws Exception
	{
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_Material SET recordid= ? , materialid= ? , serialnumber= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , productmodel= ? , customerid= ? , image_filename= ? , purchasetype= ? , originalid= ? , safetyinventory= ? , waitstockin= ? , waitstockout= ? , quantityonhand= ? , availabeltopromise= ? , accountingquantity= ? , maprice= ? , beginningwaitin= ? , beginningwaitout= ? , beginningavailabel= ? , beginninginventory= ? , beginningprice= ? , quantityeditflag= ? , stocktype= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getSerialnumber());			statement.setString( 4,beanData.getMaterialname());			statement.setString( 5,beanData.getCategoryid());			statement.setString( 6,beanData.getDescription());			statement.setString( 7,beanData.getSharemodel());			statement.setString( 8,beanData.getUnit());			statement.setString( 9,beanData.getParentid());			statement.setString( 10,beanData.getSubid());			statement.setString( 11,beanData.getSubiddes());			statement.setString( 12,beanData.getProductmodel());			statement.setString( 13,beanData.getCustomerid());			statement.setString( 14,beanData.getImage_filename());			statement.setString( 15,beanData.getPurchasetype());			statement.setString( 16,beanData.getOriginalid());			statement.setString( 17,beanData.getSafetyinventory());			statement.setString( 18,beanData.getWaitstockin());			statement.setString( 19,beanData.getWaitstockout());			statement.setString( 20,beanData.getQuantityonhand());			statement.setString( 21,beanData.getAvailabeltopromise());			statement.setString( 22,beanData.getAccountingquantity());			statement.setString( 23,beanData.getMaprice());			statement.setString( 24,beanData.getBeginningwaitin());			statement.setString( 25,beanData.getBeginningwaitout());			statement.setString( 26,beanData.getBeginningavailabel());			statement.setString( 27,beanData.getBeginninginventory());			statement.setString( 28,beanData.getBeginningprice());			statement.setString( 29,beanData.getQuantityeditflag());			statement.setString( 30,beanData.getStocktype());			statement.setString( 31,beanData.getDeptguid());			statement.setString( 32,beanData.getCreatetime());			statement.setString( 33,beanData.getCreateperson());			statement.setString( 34,beanData.getCreateunitid());			statement.setString( 35,beanData.getModifytime());			statement.setString( 36,beanData.getModifyperson());			statement.setString( 37,beanData.getDeleteflag());			statement.setString( 38,beanData.getFormid());
			statement.setString( 39,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Material SET recordid= ? , materialid= ? , serialnumber= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , productmodel= ? , customerid= ? , image_filename= ? , purchasetype= ? , originalid= ? , safetyinventory= ? , waitstockin= ? , waitstockout= ? , quantityonhand= ? , availabeltopromise= ? , accountingquantity= ? , maprice= ? , beginningwaitin= ? , beginningwaitout= ? , beginningavailabel= ? , beginninginventory= ? , beginningprice= ? , quantityeditflag= ? , stocktype= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param statement
	 */
	public String getUpdateSQL()
	{
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("UPDATE B_Material SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Serialnumber = " + "'" + nullString(beanData.getSerialnumber()) + "',");			bufSQL.append("Materialname = " + "'" + nullString(beanData.getMaterialname()) + "',");			bufSQL.append("Categoryid = " + "'" + nullString(beanData.getCategoryid()) + "',");			bufSQL.append("Description = " + "'" + nullString(beanData.getDescription()) + "',");			bufSQL.append("Sharemodel = " + "'" + nullString(beanData.getSharemodel()) + "',");			bufSQL.append("Unit = " + "'" + nullString(beanData.getUnit()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Subiddes = " + "'" + nullString(beanData.getSubiddes()) + "',");			bufSQL.append("Productmodel = " + "'" + nullString(beanData.getProductmodel()) + "',");			bufSQL.append("Customerid = " + "'" + nullString(beanData.getCustomerid()) + "',");			bufSQL.append("Image_filename = " + "'" + nullString(beanData.getImage_filename()) + "',");			bufSQL.append("Purchasetype = " + "'" + nullString(beanData.getPurchasetype()) + "',");			bufSQL.append("Originalid = " + "'" + nullString(beanData.getOriginalid()) + "',");			bufSQL.append("Safetyinventory = " + "'" + nullString(beanData.getSafetyinventory()) + "',");			bufSQL.append("Waitstockin = " + "'" + nullString(beanData.getWaitstockin()) + "',");			bufSQL.append("Waitstockout = " + "'" + nullString(beanData.getWaitstockout()) + "',");			bufSQL.append("Quantityonhand = " + "'" + nullString(beanData.getQuantityonhand()) + "',");			bufSQL.append("Availabeltopromise = " + "'" + nullString(beanData.getAvailabeltopromise()) + "',");			bufSQL.append("Accountingquantity = " + "'" + nullString(beanData.getAccountingquantity()) + "',");			bufSQL.append("Maprice = " + "'" + nullString(beanData.getMaprice()) + "',");			bufSQL.append("Beginningwaitin = " + "'" + nullString(beanData.getBeginningwaitin()) + "',");			bufSQL.append("Beginningwaitout = " + "'" + nullString(beanData.getBeginningwaitout()) + "',");			bufSQL.append("Beginningavailabel = " + "'" + nullString(beanData.getBeginningavailabel()) + "',");			bufSQL.append("Beginninginventory = " + "'" + nullString(beanData.getBeginninginventory()) + "',");			bufSQL.append("Beginningprice = " + "'" + nullString(beanData.getBeginningprice()) + "',");			bufSQL.append("Quantityeditflag = " + "'" + nullString(beanData.getQuantityeditflag()) + "',");			bufSQL.append("Stocktype = " + "'" + nullString(beanData.getStocktype()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "'");
			beanData.setReturnsql(bufSQL.toString()); 
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			return bufSQL.toString();
		}
	}

	/**
	 *
	 * @param statement
	 */
	public void Store(Object data)  throws Exception
	{
		B_MaterialData beanData = (B_MaterialData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_Material SET recordid= ? , materialid= ? , serialnumber= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , productmodel= ? , customerid= ? , image_filename= ? , purchasetype= ? , originalid= ? , safetyinventory= ? , waitstockin= ? , waitstockout= ? , quantityonhand= ? , availabeltopromise= ? , accountingquantity= ? , maprice= ? , beginningwaitin= ? , beginningwaitout= ? , beginningavailabel= ? , beginninginventory= ? , beginningprice= ? , quantityeditflag= ? , stocktype= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getSerialnumber());			statement.setString( 4,beanData.getMaterialname());			statement.setString( 5,beanData.getCategoryid());			statement.setString( 6,beanData.getDescription());			statement.setString( 7,beanData.getSharemodel());			statement.setString( 8,beanData.getUnit());			statement.setString( 9,beanData.getParentid());			statement.setString( 10,beanData.getSubid());			statement.setString( 11,beanData.getSubiddes());			statement.setString( 12,beanData.getProductmodel());			statement.setString( 13,beanData.getCustomerid());			statement.setString( 14,beanData.getImage_filename());			statement.setString( 15,beanData.getPurchasetype());			statement.setString( 16,beanData.getOriginalid());			statement.setString( 17,beanData.getSafetyinventory());			statement.setString( 18,beanData.getWaitstockin());			statement.setString( 19,beanData.getWaitstockout());			statement.setString( 20,beanData.getQuantityonhand());			statement.setString( 21,beanData.getAvailabeltopromise());			statement.setString( 22,beanData.getAccountingquantity());			statement.setString( 23,beanData.getMaprice());			statement.setString( 24,beanData.getBeginningwaitin());			statement.setString( 25,beanData.getBeginningwaitout());			statement.setString( 26,beanData.getBeginningavailabel());			statement.setString( 27,beanData.getBeginninginventory());			statement.setString( 28,beanData.getBeginningprice());			statement.setString( 29,beanData.getQuantityeditflag());			statement.setString( 30,beanData.getStocktype());			statement.setString( 31,beanData.getDeptguid());			statement.setString( 32,beanData.getCreatetime());			statement.setString( 33,beanData.getCreateperson());			statement.setString( 34,beanData.getCreateunitid());			statement.setString( 35,beanData.getModifytime());			statement.setString( 36,beanData.getModifyperson());			statement.setString( 37,beanData.getDeleteflag());			statement.setString( 38,beanData.getFormid());
			statement.setString( 39,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Material SET recordid= ? , materialid= ? , serialnumber= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , productmodel= ? , customerid= ? , image_filename= ? , purchasetype= ? , originalid= ? , safetyinventory= ? , waitstockin= ? , waitstockout= ? , quantityonhand= ? , availabeltopromise= ? , accountingquantity= ? , maprice= ? , beginningwaitin= ? , beginningwaitout= ? , beginningavailabel= ? , beginninginventory= ? , beginningprice= ? , quantityeditflag= ? , stocktype= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param beanData:����
	 */
	public void FindPrimaryKey(Object beanDataTmp) throws Exception
	{
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_Material WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_Material WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
