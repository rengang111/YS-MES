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
public class B_MaterialStorageHistoryDao extends BaseAbstractDao
{
	public B_MaterialStorageHistoryData beanData=new B_MaterialStorageHistoryData();
	public B_MaterialStorageHistoryDao()
	{
	}
	public B_MaterialStorageHistoryDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MaterialStorageHistoryData)FindByPrimaryKey(beanData);
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
		B_MaterialStorageHistoryData beanData = (B_MaterialStorageHistoryData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MaterialStorageHistory( recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,actioncontent,changequantity,copytime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MaterialStorageHistory( recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,actioncontent,changequantity,copytime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_MaterialStorageHistory( recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,actioncontent,changequantity,copytime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");
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
		B_MaterialStorageHistoryData beanData = (B_MaterialStorageHistoryData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MaterialStorageHistory( recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,actioncontent,changequantity,copytime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MaterialStorageHistory( recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,actioncontent,changequantity,copytime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_MaterialStorageHistoryData beanData = (B_MaterialStorageHistoryData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_MaterialStorageHistory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MaterialStorageHistory: "+ e.toString());
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
		B_MaterialStorageHistoryData beanData = (B_MaterialStorageHistoryData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_MaterialStorageHistory WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_MaterialStorageHistory"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MaterialStorageHistory: "+ e.toString());
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
		B_MaterialStorageHistoryData beanData = (B_MaterialStorageHistoryData) beanDataTmp; 
		B_MaterialStorageHistoryData returnData=new B_MaterialStorageHistoryData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,actioncontent,changequantity,copytime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialStorageHistory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,actioncontent,changequantity,copytime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialStorageHistory  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,actioncontent,changequantity,copytime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialStorageHistory"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MaterialStorageHistoryData returnData=new B_MaterialStorageHistoryData();
				returnData.setRecordid( resultSet.getString( 1));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,materialid,serialnumber,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,productmodel,customerid,image_filename,purchasetype,originalid,safetyinventory,waitstockin,waitstockout,quantityonhand,availabeltopromise,accountingquantity,maprice,beginningwaitin,beginningwaitout,beginningavailabel,beginninginventory,beginningprice,quantityeditflag,stocktype,actioncontent,changequantity,copytime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialStorageHistory" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_MaterialStorageHistory SET recordid= ? , materialid= ? , serialnumber= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , productmodel= ? , customerid= ? , image_filename= ? , purchasetype= ? , originalid= ? , safetyinventory= ? , waitstockin= ? , waitstockout= ? , quantityonhand= ? , availabeltopromise= ? , accountingquantity= ? , maprice= ? , beginningwaitin= ? , beginningwaitout= ? , beginningavailabel= ? , beginninginventory= ? , beginningprice= ? , quantityeditflag= ? , stocktype= ? , actioncontent= ? , changequantity= ? , copytime= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());
			statement.setString( 42,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MaterialStorageHistory SET recordid= ? , materialid= ? , serialnumber= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , productmodel= ? , customerid= ? , image_filename= ? , purchasetype= ? , originalid= ? , safetyinventory= ? , waitstockin= ? , waitstockout= ? , quantityonhand= ? , availabeltopromise= ? , accountingquantity= ? , maprice= ? , beginningwaitin= ? , beginningwaitout= ? , beginningavailabel= ? , beginninginventory= ? , beginningprice= ? , quantityeditflag= ? , stocktype= ? , actioncontent= ? , changequantity= ? , copytime= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_MaterialStorageHistory SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");
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
		B_MaterialStorageHistoryData beanData = (B_MaterialStorageHistoryData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_MaterialStorageHistory SET recordid= ? , materialid= ? , serialnumber= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , productmodel= ? , customerid= ? , image_filename= ? , purchasetype= ? , originalid= ? , safetyinventory= ? , waitstockin= ? , waitstockout= ? , quantityonhand= ? , availabeltopromise= ? , accountingquantity= ? , maprice= ? , beginningwaitin= ? , beginningwaitout= ? , beginningavailabel= ? , beginninginventory= ? , beginningprice= ? , quantityeditflag= ? , stocktype= ? , actioncontent= ? , changequantity= ? , copytime= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());
			statement.setString( 42,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MaterialStorageHistory SET recordid= ? , materialid= ? , serialnumber= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , productmodel= ? , customerid= ? , image_filename= ? , purchasetype= ? , originalid= ? , safetyinventory= ? , waitstockin= ? , waitstockout= ? , quantityonhand= ? , availabeltopromise= ? , accountingquantity= ? , maprice= ? , beginningwaitin= ? , beginningwaitout= ? , beginningavailabel= ? , beginninginventory= ? , beginningprice= ? , quantityeditflag= ? , stocktype= ? , actioncontent= ? , changequantity= ? , copytime= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_MaterialStorageHistoryData beanData = (B_MaterialStorageHistoryData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_MaterialStorageHistory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_MaterialStorageHistory WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}