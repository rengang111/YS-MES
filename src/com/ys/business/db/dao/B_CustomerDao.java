package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.util.basedao.BaseAbstractDao;
import com.ys.business.db.data.*;

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
			statement = connection.prepareStatement("INSERT INTO B_Customer( id,customerid,customersimpledes,customername,paymentterm,country,denominationcurrency,shippingcase,loadingport,deliveryport,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCustomerid());			statement.setString( 3,beanData.getCustomersimpledes());			statement.setString( 4,beanData.getCustomername());			statement.setString( 5,beanData.getPaymentterm());			statement.setString( 6,beanData.getCountry());			statement.setString( 7,beanData.getDenominationcurrency());			statement.setString( 8,beanData.getShippingcase());			statement.setString( 9,beanData.getLoadingport());			statement.setString( 10,beanData.getDeliveryport());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Customer( id,customerid,customersimpledes,customername,paymentterm,country,denominationcurrency,shippingcase,loadingport,deliveryport,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_Customer( id,customerid,customersimpledes,customername,paymentterm,country,denominationcurrency,shippingcase,loadingport,deliveryport,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getCustomerid()) + "',");			bufSQL.append("'" + nullString(beanData.getCustomersimpledes()) + "',");			bufSQL.append("'" + nullString(beanData.getCustomername()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentterm()) + "',");			bufSQL.append("'" + nullString(beanData.getCountry()) + "',");			bufSQL.append("'" + nullString(beanData.getDenominationcurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getShippingcase()) + "',");			bufSQL.append("'" + nullString(beanData.getLoadingport()) + "',");			bufSQL.append("'" + nullString(beanData.getDeliveryport()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
			statement = connection.prepareStatement("INSERT INTO B_Customer( id,customerid,customersimpledes,customername,paymentterm,country,denominationcurrency,shippingcase,loadingport,deliveryport,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCustomerid());			statement.setString( 3,beanData.getCustomersimpledes());			statement.setString( 4,beanData.getCustomername());			statement.setString( 5,beanData.getPaymentterm());			statement.setString( 6,beanData.getCountry());			statement.setString( 7,beanData.getDenominationcurrency());			statement.setString( 8,beanData.getShippingcase());			statement.setString( 9,beanData.getLoadingport());			statement.setString( 10,beanData.getDeliveryport());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Customer( id,customerid,customersimpledes,customername,paymentterm,country,denominationcurrency,shippingcase,loadingport,deliveryport,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			statement = connection.prepareStatement("DELETE FROM B_Customer WHERE  id =?");
			statement.setString( 1,beanData.getId());
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
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "'");
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
			statement = connection.prepareStatement("SELECT id,customerid,customersimpledes,customername,paymentterm,country,denominationcurrency,shippingcase,loadingport,deliveryport,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_Customer WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setCustomerid( resultSet.getString( 2));			returnData.setCustomersimpledes( resultSet.getString( 3));			returnData.setCustomername( resultSet.getString( 4));			returnData.setPaymentterm( resultSet.getString( 5));			returnData.setCountry( resultSet.getString( 6));			returnData.setDenominationcurrency( resultSet.getString( 7));			returnData.setShippingcase( resultSet.getString( 8));			returnData.setLoadingport( resultSet.getString( 9));			returnData.setDeliveryport( resultSet.getString( 10));			returnData.setCreatetime( resultSet.getString( 11));			returnData.setCreateperson( resultSet.getString( 12));			returnData.setCreateunitid( resultSet.getString( 13));			returnData.setModifytime( resultSet.getString( 14));			returnData.setModifyperson( resultSet.getString( 15));			returnData.setDeleteflag( resultSet.getString( 16));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,customerid,customersimpledes,customername,paymentterm,country,denominationcurrency,shippingcase,loadingport,deliveryport,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_Customer  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,customerid,customersimpledes,customername,paymentterm,country,denominationcurrency,shippingcase,loadingport,deliveryport,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_Customer"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_CustomerData returnData=new B_CustomerData();
				returnData.setId( resultSet.getString( 1));				returnData.setCustomerid( resultSet.getString( 2));				returnData.setCustomersimpledes( resultSet.getString( 3));				returnData.setCustomername( resultSet.getString( 4));				returnData.setPaymentterm( resultSet.getString( 5));				returnData.setCountry( resultSet.getString( 6));				returnData.setDenominationcurrency( resultSet.getString( 7));				returnData.setShippingcase( resultSet.getString( 8));				returnData.setLoadingport( resultSet.getString( 9));				returnData.setDeliveryport( resultSet.getString( 10));				returnData.setCreatetime( resultSet.getString( 11));				returnData.setCreateperson( resultSet.getString( 12));				returnData.setCreateunitid( resultSet.getString( 13));				returnData.setModifytime( resultSet.getString( 14));				returnData.setModifyperson( resultSet.getString( 15));				returnData.setDeleteflag( resultSet.getString( 16));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,customerid,customersimpledes,customername,paymentterm,country,denominationcurrency,shippingcase,loadingport,deliveryport,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_Customer" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_Customer SET id= ? , customerid= ? , customersimpledes= ? , customername= ? , paymentterm= ? , country= ? , denominationcurrency= ? , shippingcase= ? , loadingport= ? , deliveryport= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCustomerid());			statement.setString( 3,beanData.getCustomersimpledes());			statement.setString( 4,beanData.getCustomername());			statement.setString( 5,beanData.getPaymentterm());			statement.setString( 6,beanData.getCountry());			statement.setString( 7,beanData.getDenominationcurrency());			statement.setString( 8,beanData.getShippingcase());			statement.setString( 9,beanData.getLoadingport());			statement.setString( 10,beanData.getDeliveryport());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());
			statement.setString( 17,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Customer SET id= ? , customerid= ? , customersimpledes= ? , customername= ? , paymentterm= ? , country= ? , denominationcurrency= ? , shippingcase= ? , loadingport= ? , deliveryport= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Customerid = " + "'" + nullString(beanData.getCustomerid()) + "',");			bufSQL.append("Customersimpledes = " + "'" + nullString(beanData.getCustomersimpledes()) + "',");			bufSQL.append("Customername = " + "'" + nullString(beanData.getCustomername()) + "',");			bufSQL.append("Paymentterm = " + "'" + nullString(beanData.getPaymentterm()) + "',");			bufSQL.append("Country = " + "'" + nullString(beanData.getCountry()) + "',");			bufSQL.append("Denominationcurrency = " + "'" + nullString(beanData.getDenominationcurrency()) + "',");			bufSQL.append("Shippingcase = " + "'" + nullString(beanData.getShippingcase()) + "',");			bufSQL.append("Loadingport = " + "'" + nullString(beanData.getLoadingport()) + "',");			bufSQL.append("Deliveryport = " + "'" + nullString(beanData.getDeliveryport()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "'");
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
			statement = connection.prepareStatement("UPDATE B_Customer SET id= ? , customerid= ? , customersimpledes= ? , customername= ? , paymentterm= ? , country= ? , denominationcurrency= ? , shippingcase= ? , loadingport= ? , deliveryport= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCustomerid());			statement.setString( 3,beanData.getCustomersimpledes());			statement.setString( 4,beanData.getCustomername());			statement.setString( 5,beanData.getPaymentterm());			statement.setString( 6,beanData.getCountry());			statement.setString( 7,beanData.getDenominationcurrency());			statement.setString( 8,beanData.getShippingcase());			statement.setString( 9,beanData.getLoadingport());			statement.setString( 10,beanData.getDeliveryport());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());
			statement.setString( 17,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Customer SET id= ? , customerid= ? , customersimpledes= ? , customername= ? , paymentterm= ? , country= ? , denominationcurrency= ? , shippingcase= ? , loadingport= ? , deliveryport= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			statement = connection.prepareStatement("SELECT  id  FROM B_Customer WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_Customer WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
