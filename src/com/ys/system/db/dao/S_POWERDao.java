package com.ys.system.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.util.basedao.BaseAbstractDao;
import com.ys.system.db.data.*;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class S_POWERDao extends BaseAbstractDao
{
	public S_POWERData beanData=new S_POWERData();
	public S_POWERDao()
	{
	}
	public S_POWERDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_POWERData)FindByPrimaryKey(beanData);
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
		S_POWERData beanData = (S_POWERData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_POWER( id,userid,roleid,unitid,powertype,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getRoleid());			statement.setString( 4,beanData.getUnitid());			statement.setString( 5,beanData.getPowertype());			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_POWER( id,userid,roleid,unitid,powertype,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_POWER( id,userid,roleid,unitid,powertype,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getUserid()) + "',");			bufSQL.append("'" + nullString(beanData.getRoleid()) + "',");			bufSQL.append("'" + nullString(beanData.getUnitid()) + "',");			bufSQL.append("'" + nullString(beanData.getPowertype()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "'");
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
		S_POWERData beanData = (S_POWERData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_POWER( id,userid,roleid,unitid,powertype,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getRoleid());			statement.setString( 4,beanData.getUnitid());			statement.setString( 5,beanData.getPowertype());			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_POWER( id,userid,roleid,unitid,powertype,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_POWERData beanData = (S_POWERData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_POWER WHERE  userid=? AND roleid=? AND unitid =?");
			statement.setString( 1,beanData.getUserid());
			statement.setString( 2,beanData.getRoleid());
			statement.setString( 3,beanData.getUnitid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_POWER: "+ e.toString());
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
		S_POWERData beanData = (S_POWERData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_POWER WHERE ");
			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "'");
			bufSQL.append("Roleid = " + "'" + nullString(beanData.getRoleid()) + "'");
			bufSQL.append(" AND ");
			bufSQL.append("Unitid = " + "'" + nullString(beanData.getUnitid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM S_POWER"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_POWER: "+ e.toString());
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
		S_POWERData beanData = (S_POWERData) beanDataTmp; 
		S_POWERData returnData=new S_POWERData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,userid,roleid,unitid,powertype,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_POWER WHERE  userid=? AND roleid=? AND unitid =?");
			statement.setString( 1,beanData.getUserid());
			statement.setString( 2,beanData.getRoleid());
			statement.setString( 3,beanData.getUnitid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setUserid( resultSet.getString( 2));			returnData.setRoleid( resultSet.getString( 3));			returnData.setUnitid( resultSet.getString( 4));			returnData.setPowertype( resultSet.getString( 5));			returnData.setCreatetime( resultSet.getString( 6));			returnData.setCreateperson( resultSet.getString( 7));			returnData.setCreateunitid( resultSet.getString( 8));			returnData.setModifytime( resultSet.getString( 9));			returnData.setModifyperson( resultSet.getString( 10));			returnData.setDeleteflag( resultSet.getString( 11));			returnData.setDeptguid( resultSet.getString( 12));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,userid,roleid,unitid,powertype,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_POWER  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,userid,roleid,unitid,powertype,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_POWER"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_POWERData returnData=new S_POWERData();
				returnData.setId( resultSet.getString( 1));				returnData.setUserid( resultSet.getString( 2));				returnData.setRoleid( resultSet.getString( 3));				returnData.setUnitid( resultSet.getString( 4));				returnData.setPowertype( resultSet.getString( 5));				returnData.setCreatetime( resultSet.getString( 6));				returnData.setCreateperson( resultSet.getString( 7));				returnData.setCreateunitid( resultSet.getString( 8));				returnData.setModifytime( resultSet.getString( 9));				returnData.setModifyperson( resultSet.getString( 10));				returnData.setDeleteflag( resultSet.getString( 11));				returnData.setDeptguid( resultSet.getString( 12));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,userid,roleid,unitid,powertype,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_POWER" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_POWER SET id= ? , userid= ? , roleid= ? , unitid= ? , powertype= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  userid = ? AND roleid = ? AND unitid  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getRoleid());			statement.setString( 4,beanData.getUnitid());			statement.setString( 5,beanData.getPowertype());			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());
			statement.setString( 13,beanData.getUserid());
			statement.setString( 14,beanData.getRoleid());
			statement.setString( 15,beanData.getUnitid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_POWER SET id= ? , userid= ? , roleid= ? , unitid= ? , powertype= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  userid = ? AND roleid = ? AND unitid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_POWER SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "',");			bufSQL.append("Roleid = " + "'" + nullString(beanData.getRoleid()) + "',");			bufSQL.append("Unitid = " + "'" + nullString(beanData.getUnitid()) + "',");			bufSQL.append("Powertype = " + "'" + nullString(beanData.getPowertype()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "'");
			bufSQL.append("Roleid = " + "'" + nullString(beanData.getRoleid()) + "'");
			bufSQL.append(" AND ");
			bufSQL.append("Unitid = " + "'" + nullString(beanData.getUnitid()) + "'");
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
		S_POWERData beanData = (S_POWERData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_POWER SET id= ? , userid= ? , roleid= ? , unitid= ? , powertype= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  userid = ? AND roleid = ? AND unitid  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getRoleid());			statement.setString( 4,beanData.getUnitid());			statement.setString( 5,beanData.getPowertype());			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());
			statement.setString( 13,beanData.getUserid());
			statement.setString( 14,beanData.getRoleid());
			statement.setString( 15,beanData.getUnitid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_POWER SET id= ? , userid= ? , roleid= ? , unitid= ? , powertype= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  userid = ? AND roleid = ? AND unitid  = ?"+ e.toString());
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
		S_POWERData beanData = (S_POWERData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  userid,roleid,unitid  FROM S_POWER WHERE  userid=? AND roleid=? AND unitid =?");
			statement.setString( 1,beanData.getUserid());
			statement.setString( 2,beanData.getRoleid());
			statement.setString( 3,beanData.getUnitid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  userid,roleid,unitid  FROM S_POWER WHERE  userid=? AND roleid=? AND unitid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
