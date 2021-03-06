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
public class B_CustomerDao extends BaseAbstractDao
{
	public B_CustomerData beanData=new B_CustomerData();
	public B_CustomerDao()
	{
	}
	public B_CustomerDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_CustomerData)FindByPrimaryKey(beanData);
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
		B_CustomerData beanData = (B_CustomerData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Customer( recordid,customerid,shortname,customername,parentid,subid,paymentterm,country,currency,shippingcondition,shippiingport,destinationport,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCustomerid());			statement.setString( 3,beanData.getShortname());			statement.setString( 4,beanData.getCustomername());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getPaymentterm());			statement.setString( 8,beanData.getCountry());			statement.setString( 9,beanData.getCurrency());			statement.setString( 10,beanData.getShippingcondition());			statement.setString( 11,beanData.getShippiingport());			statement.setString( 12,beanData.getDestinationport());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());			statement.setString( 20,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Customer( recordid,customerid,shortname,customername,parentid,subid,paymentterm,country,currency,shippingcondition,shippiingport,destinationport,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_Customer( recordid,customerid,shortname,customername,parentid,subid,paymentterm,country,currency,shippingcondition,shippiingport,destinationport,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getCustomerid()) + "',");			bufSQL.append("'" + nullString(beanData.getShortname()) + "',");			bufSQL.append("'" + nullString(beanData.getCustomername()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentterm()) + "',");			bufSQL.append("'" + nullString(beanData.getCountry()) + "',");			bufSQL.append("'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getShippingcondition()) + "',");			bufSQL.append("'" + nullString(beanData.getShippiingport()) + "',");			bufSQL.append("'" + nullString(beanData.getDestinationport()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_CustomerData beanData = (B_CustomerData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Customer( recordid,customerid,shortname,customername,parentid,subid,paymentterm,country,currency,shippingcondition,shippiingport,destinationport,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCustomerid());			statement.setString( 3,beanData.getShortname());			statement.setString( 4,beanData.getCustomername());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getPaymentterm());			statement.setString( 8,beanData.getCountry());			statement.setString( 9,beanData.getCurrency());			statement.setString( 10,beanData.getShippingcondition());			statement.setString( 11,beanData.getShippiingport());			statement.setString( 12,beanData.getDestinationport());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());			statement.setString( 20,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Customer( recordid,customerid,shortname,customername,parentid,subid,paymentterm,country,currency,shippingcondition,shippiingport,destinationport,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_CustomerData beanData = (B_CustomerData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_Customer WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Customer: "+ e.toString());
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
		B_CustomerData beanData = (B_CustomerData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_Customer WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_Customer"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Customer: "+ e.toString());
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
		B_CustomerData beanData = (B_CustomerData) beanDataTmp; 
		B_CustomerData returnData=new B_CustomerData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,customerid,shortname,customername,parentid,subid,paymentterm,country,currency,shippingcondition,shippiingport,destinationport,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Customer WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setCustomerid( resultSet.getString( 2));			returnData.setShortname( resultSet.getString( 3));			returnData.setCustomername( resultSet.getString( 4));			returnData.setParentid( resultSet.getString( 5));			returnData.setSubid( resultSet.getString( 6));			returnData.setPaymentterm( resultSet.getString( 7));			returnData.setCountry( resultSet.getString( 8));			returnData.setCurrency( resultSet.getString( 9));			returnData.setShippingcondition( resultSet.getString( 10));			returnData.setShippiingport( resultSet.getString( 11));			returnData.setDestinationport( resultSet.getString( 12));			returnData.setDeptguid( resultSet.getString( 13));			returnData.setCreatetime( resultSet.getString( 14));			returnData.setCreateperson( resultSet.getString( 15));			returnData.setCreateunitid( resultSet.getString( 16));			returnData.setModifytime( resultSet.getString( 17));			returnData.setModifyperson( resultSet.getString( 18));			returnData.setDeleteflag( resultSet.getString( 19));			returnData.setFormid( resultSet.getString( 20));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,customerid,shortname,customername,parentid,subid,paymentterm,country,currency,shippingcondition,shippiingport,destinationport,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Customer  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,customerid,shortname,customername,parentid,subid,paymentterm,country,currency,shippingcondition,shippiingport,destinationport,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Customer"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_CustomerData returnData=new B_CustomerData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setCustomerid( resultSet.getString( 2));				returnData.setShortname( resultSet.getString( 3));				returnData.setCustomername( resultSet.getString( 4));				returnData.setParentid( resultSet.getString( 5));				returnData.setSubid( resultSet.getString( 6));				returnData.setPaymentterm( resultSet.getString( 7));				returnData.setCountry( resultSet.getString( 8));				returnData.setCurrency( resultSet.getString( 9));				returnData.setShippingcondition( resultSet.getString( 10));				returnData.setShippiingport( resultSet.getString( 11));				returnData.setDestinationport( resultSet.getString( 12));				returnData.setDeptguid( resultSet.getString( 13));				returnData.setCreatetime( resultSet.getString( 14));				returnData.setCreateperson( resultSet.getString( 15));				returnData.setCreateunitid( resultSet.getString( 16));				returnData.setModifytime( resultSet.getString( 17));				returnData.setModifyperson( resultSet.getString( 18));				returnData.setDeleteflag( resultSet.getString( 19));				returnData.setFormid( resultSet.getString( 20));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,customerid,shortname,customername,parentid,subid,paymentterm,country,currency,shippingcondition,shippiingport,destinationport,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Customer" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_Customer SET recordid= ? , customerid= ? , shortname= ? , customername= ? , parentid= ? , subid= ? , paymentterm= ? , country= ? , currency= ? , shippingcondition= ? , shippiingport= ? , destinationport= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCustomerid());			statement.setString( 3,beanData.getShortname());			statement.setString( 4,beanData.getCustomername());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getPaymentterm());			statement.setString( 8,beanData.getCountry());			statement.setString( 9,beanData.getCurrency());			statement.setString( 10,beanData.getShippingcondition());			statement.setString( 11,beanData.getShippiingport());			statement.setString( 12,beanData.getDestinationport());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());			statement.setString( 20,beanData.getFormid());
			statement.setString( 21,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Customer SET recordid= ? , customerid= ? , shortname= ? , customername= ? , parentid= ? , subid= ? , paymentterm= ? , country= ? , currency= ? , shippingcondition= ? , shippiingport= ? , destinationport= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_Customer SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Customerid = " + "'" + nullString(beanData.getCustomerid()) + "',");			bufSQL.append("Shortname = " + "'" + nullString(beanData.getShortname()) + "',");			bufSQL.append("Customername = " + "'" + nullString(beanData.getCustomername()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Paymentterm = " + "'" + nullString(beanData.getPaymentterm()) + "',");			bufSQL.append("Country = " + "'" + nullString(beanData.getCountry()) + "',");			bufSQL.append("Currency = " + "'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("Shippingcondition = " + "'" + nullString(beanData.getShippingcondition()) + "',");			bufSQL.append("Shippiingport = " + "'" + nullString(beanData.getShippiingport()) + "',");			bufSQL.append("Destinationport = " + "'" + nullString(beanData.getDestinationport()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_CustomerData beanData = (B_CustomerData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_Customer SET recordid= ? , customerid= ? , shortname= ? , customername= ? , parentid= ? , subid= ? , paymentterm= ? , country= ? , currency= ? , shippingcondition= ? , shippiingport= ? , destinationport= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCustomerid());			statement.setString( 3,beanData.getShortname());			statement.setString( 4,beanData.getCustomername());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getPaymentterm());			statement.setString( 8,beanData.getCountry());			statement.setString( 9,beanData.getCurrency());			statement.setString( 10,beanData.getShippingcondition());			statement.setString( 11,beanData.getShippiingport());			statement.setString( 12,beanData.getDestinationport());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());			statement.setString( 20,beanData.getFormid());
			statement.setString( 21,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Customer SET recordid= ? , customerid= ? , shortname= ? , customername= ? , parentid= ? , subid= ? , paymentterm= ? , country= ? , currency= ? , shippingcondition= ? , shippiingport= ? , destinationport= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_CustomerData beanData = (B_CustomerData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_Customer WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_Customer WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
