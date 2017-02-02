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
public class S_systemConfigDao extends BaseAbstractDao
{
	public S_systemConfigData beanData=new S_systemConfigData();
	public S_systemConfigDao()
	{
	}
	public S_systemConfigDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_systemConfigData)FindByPrimaryKey(beanData);
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
		S_systemConfigData beanData = (S_systemConfigData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_systemConfig( syskey,sysvalue,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getSyskey());			statement.setString( 2,beanData.getSysvalue());			statement.setString( 3,beanData.getDeptguid());			statement.setString( 4,beanData.getCreatetime());			statement.setString( 5,beanData.getCreateperson());			statement.setString( 6,beanData.getCreateunitid());			statement.setString( 7,beanData.getModifytime());			statement.setString( 8,beanData.getModifyperson());			statement.setString( 9,beanData.getDeleteflag());			statement.setString( 10,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_systemConfig( syskey,sysvalue,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_systemConfig( syskey,sysvalue,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getSyskey()) + "',");			bufSQL.append("'" + nullString(beanData.getSysvalue()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		S_systemConfigData beanData = (S_systemConfigData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_systemConfig( syskey,sysvalue,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getSyskey());			statement.setString( 2,beanData.getSysvalue());			statement.setString( 3,beanData.getDeptguid());			statement.setString( 4,beanData.getCreatetime());			statement.setString( 5,beanData.getCreateperson());			statement.setString( 6,beanData.getCreateunitid());			statement.setString( 7,beanData.getModifytime());			statement.setString( 8,beanData.getModifyperson());			statement.setString( 9,beanData.getDeleteflag());			statement.setString( 10,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_systemConfig( syskey,sysvalue,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_systemConfigData beanData = (S_systemConfigData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_systemConfig WHERE  syskey =?");
			statement.setString( 1,beanData.getSyskey());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_systemConfig: "+ e.toString());
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
		S_systemConfigData beanData = (S_systemConfigData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_systemConfig WHERE ");
			bufSQL.append("Syskey = " + "'" + nullString(beanData.getSyskey()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM S_systemConfig"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_systemConfig: "+ e.toString());
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
		S_systemConfigData beanData = (S_systemConfigData) beanDataTmp; 
		S_systemConfigData returnData=new S_systemConfigData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT syskey,sysvalue,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_systemConfig WHERE  syskey =?");
			statement.setString( 1,beanData.getSyskey());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setSyskey( resultSet.getString( 1));			returnData.setSysvalue( resultSet.getString( 2));			returnData.setDeptguid( resultSet.getString( 3));			returnData.setCreatetime( resultSet.getString( 4));			returnData.setCreateperson( resultSet.getString( 5));			returnData.setCreateunitid( resultSet.getString( 6));			returnData.setModifytime( resultSet.getString( 7));			returnData.setModifyperson( resultSet.getString( 8));			returnData.setDeleteflag( resultSet.getString( 9));			returnData.setFormid( resultSet.getString( 10));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT syskey,sysvalue,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_systemConfig  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT syskey,sysvalue,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_systemConfig"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_systemConfigData returnData=new S_systemConfigData();
				returnData.setSyskey( resultSet.getString( 1));				returnData.setSysvalue( resultSet.getString( 2));				returnData.setDeptguid( resultSet.getString( 3));				returnData.setCreatetime( resultSet.getString( 4));				returnData.setCreateperson( resultSet.getString( 5));				returnData.setCreateunitid( resultSet.getString( 6));				returnData.setModifytime( resultSet.getString( 7));				returnData.setModifyperson( resultSet.getString( 8));				returnData.setDeleteflag( resultSet.getString( 9));				returnData.setFormid( resultSet.getString( 10));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT syskey,sysvalue,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_systemConfig" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_systemConfig SET syskey= ? , sysvalue= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  syskey  = ?");
			statement.setString( 1,beanData.getSyskey());			statement.setString( 2,beanData.getSysvalue());			statement.setString( 3,beanData.getDeptguid());			statement.setString( 4,beanData.getCreatetime());			statement.setString( 5,beanData.getCreateperson());			statement.setString( 6,beanData.getCreateunitid());			statement.setString( 7,beanData.getModifytime());			statement.setString( 8,beanData.getModifyperson());			statement.setString( 9,beanData.getDeleteflag());			statement.setString( 10,beanData.getFormid());
			statement.setString( 11,beanData.getSyskey());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_systemConfig SET syskey= ? , sysvalue= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  syskey  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_systemConfig SET ");
			bufSQL.append("Syskey = " + "'" + nullString(beanData.getSyskey()) + "',");			bufSQL.append("Sysvalue = " + "'" + nullString(beanData.getSysvalue()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Syskey = " + "'" + nullString(beanData.getSyskey()) + "'");
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
		S_systemConfigData beanData = (S_systemConfigData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_systemConfig SET syskey= ? , sysvalue= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  syskey  = ?");
			statement.setString( 1,beanData.getSyskey());			statement.setString( 2,beanData.getSysvalue());			statement.setString( 3,beanData.getDeptguid());			statement.setString( 4,beanData.getCreatetime());			statement.setString( 5,beanData.getCreateperson());			statement.setString( 6,beanData.getCreateunitid());			statement.setString( 7,beanData.getModifytime());			statement.setString( 8,beanData.getModifyperson());			statement.setString( 9,beanData.getDeleteflag());			statement.setString( 10,beanData.getFormid());
			statement.setString( 11,beanData.getSyskey());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_systemConfig SET syskey= ? , sysvalue= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  syskey  = ?"+ e.toString());
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
		S_systemConfigData beanData = (S_systemConfigData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  syskey  FROM S_systemConfig WHERE  syskey =?");
			statement.setString( 1,beanData.getSyskey());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  syskey  FROM S_systemConfig WHERE  syskey =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
