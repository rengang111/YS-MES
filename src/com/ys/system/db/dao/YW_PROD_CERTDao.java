package com.ys.system.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.system.db.data.*;
import com.ys.util.basedao.BaseAbstractDao;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class YW_PROD_CERTDao extends BaseAbstractDao
{
	public YW_PROD_CERTData beanData=new YW_PROD_CERTData();
	public YW_PROD_CERTDao()
	{
	}
	public YW_PROD_CERTDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROD_CERTData)FindByPrimaryKey(beanData);
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
		YW_PROD_CERTData beanData = (YW_PROD_CERTData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROD_CERT( id,prod_id,cert_mach,cert_char,cert_bat_pkg,cert_bat,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,describes,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProd_id());			statement.setString( 3,beanData.getCert_mach());			statement.setString( 4,beanData.getCert_char());			statement.setString( 5,beanData.getCert_bat_pkg());			statement.setString( 6,beanData.getCert_bat());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDescribes());			statement.setString( 14,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROD_CERT( id,prod_id,cert_mach,cert_char,cert_bat_pkg,cert_bat,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,describes,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROD_CERT( id,prod_id,cert_mach,cert_char,cert_bat_pkg,cert_bat,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,describes,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append(beanData.getProd_id() + ",");			bufSQL.append("'" + nullString(beanData.getCert_mach()) + "',");			bufSQL.append("'" + nullString(beanData.getCert_char()) + "',");			bufSQL.append("'" + nullString(beanData.getCert_bat_pkg()) + "',");			bufSQL.append("'" + nullString(beanData.getCert_bat()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDescribes()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROD_CERTData beanData = (YW_PROD_CERTData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROD_CERT( id,prod_id,cert_mach,cert_char,cert_bat_pkg,cert_bat,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,describes,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProd_id());			statement.setString( 3,beanData.getCert_mach());			statement.setString( 4,beanData.getCert_char());			statement.setString( 5,beanData.getCert_bat_pkg());			statement.setString( 6,beanData.getCert_bat());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDescribes());			statement.setString( 14,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROD_CERT( id,prod_id,cert_mach,cert_char,cert_bat_pkg,cert_bat,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,describes,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROD_CERTData beanData = (YW_PROD_CERTData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROD_CERT WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROD_CERT: "+ e.toString());
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
		YW_PROD_CERTData beanData = (YW_PROD_CERTData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROD_CERT WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROD_CERT"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROD_CERT: "+ e.toString());
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
		YW_PROD_CERTData beanData = (YW_PROD_CERTData) beanDataTmp; 
		YW_PROD_CERTData returnData=new YW_PROD_CERTData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,prod_id,cert_mach,cert_char,cert_bat_pkg,cert_bat,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,describes,formid FROM YW_PROD_CERT WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProd_id( resultSet.getInt( 2));			returnData.setCert_mach( resultSet.getString( 3));			returnData.setCert_char( resultSet.getString( 4));			returnData.setCert_bat_pkg( resultSet.getString( 5));			returnData.setCert_bat( resultSet.getString( 6));			returnData.setCreatetime( resultSet.getString( 7));			returnData.setCreateperson( resultSet.getString( 8));			returnData.setCreateunitid( resultSet.getString( 9));			returnData.setModifytime( resultSet.getString( 10));			returnData.setModifyperson( resultSet.getString( 11));			returnData.setDeleteflag( resultSet.getString( 12));			returnData.setDescribes( resultSet.getString( 13));			returnData.setFormid( resultSet.getString( 14));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,prod_id,cert_mach,cert_char,cert_bat_pkg,cert_bat,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,describes,formid FROM YW_PROD_CERT  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,prod_id,cert_mach,cert_char,cert_bat_pkg,cert_bat,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,describes,formid FROM YW_PROD_CERT"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROD_CERTData returnData=new YW_PROD_CERTData();
				returnData.setId( resultSet.getString( 1));				returnData.setProd_id( resultSet.getInt( 2));				returnData.setCert_mach( resultSet.getString( 3));				returnData.setCert_char( resultSet.getString( 4));				returnData.setCert_bat_pkg( resultSet.getString( 5));				returnData.setCert_bat( resultSet.getString( 6));				returnData.setCreatetime( resultSet.getString( 7));				returnData.setCreateperson( resultSet.getString( 8));				returnData.setCreateunitid( resultSet.getString( 9));				returnData.setModifytime( resultSet.getString( 10));				returnData.setModifyperson( resultSet.getString( 11));				returnData.setDeleteflag( resultSet.getString( 12));				returnData.setDescribes( resultSet.getString( 13));				returnData.setFormid( resultSet.getString( 14));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,prod_id,cert_mach,cert_char,cert_bat_pkg,cert_bat,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,describes,formid FROM YW_PROD_CERT" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROD_CERT SET id= ? , prod_id= ? , cert_mach= ? , cert_char= ? , cert_bat_pkg= ? , cert_bat= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , describes= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProd_id());			statement.setString( 3,beanData.getCert_mach());			statement.setString( 4,beanData.getCert_char());			statement.setString( 5,beanData.getCert_bat_pkg());			statement.setString( 6,beanData.getCert_bat());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDescribes());			statement.setString( 14,beanData.getFormid());
			statement.setString( 15,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROD_CERT SET id= ? , prod_id= ? , cert_mach= ? , cert_char= ? , cert_bat_pkg= ? , cert_bat= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , describes= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROD_CERT SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Prod_id = " + beanData.getProd_id() + ",");			bufSQL.append("Cert_mach = " + "'" + nullString(beanData.getCert_mach()) + "',");			bufSQL.append("Cert_char = " + "'" + nullString(beanData.getCert_char()) + "',");			bufSQL.append("Cert_bat_pkg = " + "'" + nullString(beanData.getCert_bat_pkg()) + "',");			bufSQL.append("Cert_bat = " + "'" + nullString(beanData.getCert_bat()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Describes = " + "'" + nullString(beanData.getDescribes()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROD_CERTData beanData = (YW_PROD_CERTData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROD_CERT SET id= ? , prod_id= ? , cert_mach= ? , cert_char= ? , cert_bat_pkg= ? , cert_bat= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , describes= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProd_id());			statement.setString( 3,beanData.getCert_mach());			statement.setString( 4,beanData.getCert_char());			statement.setString( 5,beanData.getCert_bat_pkg());			statement.setString( 6,beanData.getCert_bat());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDescribes());			statement.setString( 14,beanData.getFormid());
			statement.setString( 15,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROD_CERT SET id= ? , prod_id= ? , cert_mach= ? , cert_char= ? , cert_bat_pkg= ? , cert_bat= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , describes= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PROD_CERTData beanData = (YW_PROD_CERTData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PROD_CERT WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PROD_CERT WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
