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
public class S_OPERLOGDao extends BaseAbstractDao
{
	public S_OPERLOGData beanData=new S_OPERLOGData();
	public S_OPERLOGDao()
	{
	}
	public S_OPERLOGDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_OPERLOGData)FindByPrimaryKey(beanData);
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
		S_OPERLOGData beanData = (S_OPERLOGData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_OPERLOG( id,userid,username,menuywclass,menuid,menuname,menuurl,ip,browsename,createtime,createperson,createunitid,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getUsername());			statement.setString( 4,beanData.getMenuywclass());			statement.setString( 5,beanData.getMenuid());			statement.setString( 6,beanData.getMenuname());			statement.setString( 7,beanData.getMenuurl());			statement.setString( 8,beanData.getIp());			statement.setString( 9,beanData.getBrowsename());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_OPERLOG( id,userid,username,menuywclass,menuid,menuname,menuurl,ip,browsename,createtime,createperson,createunitid,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_OPERLOG( id,userid,username,menuywclass,menuid,menuname,menuurl,ip,browsename,createtime,createperson,createunitid,deleteflag,deptguid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getUserid()) + "',");			bufSQL.append("'" + nullString(beanData.getUsername()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuywclass()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuid()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuname()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuurl()) + "',");			bufSQL.append("'" + nullString(beanData.getIp()) + "',");			bufSQL.append("'" + nullString(beanData.getBrowsename()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "'");
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
		S_OPERLOGData beanData = (S_OPERLOGData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_OPERLOG( id,userid,username,menuywclass,menuid,menuname,menuurl,ip,browsename,createtime,createperson,createunitid,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getUsername());			statement.setString( 4,beanData.getMenuywclass());			statement.setString( 5,beanData.getMenuid());			statement.setString( 6,beanData.getMenuname());			statement.setString( 7,beanData.getMenuurl());			statement.setString( 8,beanData.getIp());			statement.setString( 9,beanData.getBrowsename());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_OPERLOG( id,userid,username,menuywclass,menuid,menuname,menuurl,ip,browsename,createtime,createperson,createunitid,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_OPERLOGData beanData = (S_OPERLOGData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_OPERLOG WHERE  id=? AND userid =?");
			statement.setString( 1,beanData.getId());
			statement.setString( 2,beanData.getUserid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_OPERLOG: "+ e.toString());
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
		S_OPERLOGData beanData = (S_OPERLOGData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_OPERLOG WHERE ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "'");
			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM S_OPERLOG"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_OPERLOG: "+ e.toString());
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
		S_OPERLOGData beanData = (S_OPERLOGData) beanDataTmp; 
		S_OPERLOGData returnData=new S_OPERLOGData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,userid,username,menuywclass,menuid,menuname,menuurl,ip,browsename,createtime,createperson,createunitid,deleteflag,deptguid FROM S_OPERLOG WHERE  id=? AND userid =?");
			statement.setString( 1,beanData.getId());
			statement.setString( 2,beanData.getUserid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setUserid( resultSet.getString( 2));			returnData.setUsername( resultSet.getString( 3));			returnData.setMenuywclass( resultSet.getString( 4));			returnData.setMenuid( resultSet.getString( 5));			returnData.setMenuname( resultSet.getString( 6));			returnData.setMenuurl( resultSet.getString( 7));			returnData.setIp( resultSet.getString( 8));			returnData.setBrowsename( resultSet.getString( 9));			returnData.setCreatetime( resultSet.getString( 10));			returnData.setCreateperson( resultSet.getString( 11));			returnData.setCreateunitid( resultSet.getString( 12));			returnData.setDeleteflag( resultSet.getString( 13));			returnData.setDeptguid( resultSet.getString( 14));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,userid,username,menuywclass,menuid,menuname,menuurl,ip,browsename,createtime,createperson,createunitid,deleteflag,deptguid FROM S_OPERLOG  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,userid,username,menuywclass,menuid,menuname,menuurl,ip,browsename,createtime,createperson,createunitid,deleteflag,deptguid FROM S_OPERLOG"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_OPERLOGData returnData=new S_OPERLOGData();
				returnData.setId( resultSet.getString( 1));				returnData.setUserid( resultSet.getString( 2));				returnData.setUsername( resultSet.getString( 3));				returnData.setMenuywclass( resultSet.getString( 4));				returnData.setMenuid( resultSet.getString( 5));				returnData.setMenuname( resultSet.getString( 6));				returnData.setMenuurl( resultSet.getString( 7));				returnData.setIp( resultSet.getString( 8));				returnData.setBrowsename( resultSet.getString( 9));				returnData.setCreatetime( resultSet.getString( 10));				returnData.setCreateperson( resultSet.getString( 11));				returnData.setCreateunitid( resultSet.getString( 12));				returnData.setDeleteflag( resultSet.getString( 13));				returnData.setDeptguid( resultSet.getString( 14));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,userid,username,menuywclass,menuid,menuname,menuurl,ip,browsename,createtime,createperson,createunitid,deleteflag,deptguid FROM S_OPERLOG" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_OPERLOG SET id= ? , userid= ? , username= ? , menuywclass= ? , menuid= ? , menuname= ? , menuurl= ? , ip= ? , browsename= ? , createtime= ? , createperson= ? , createunitid= ? , deleteflag= ? , deptguid=? WHERE  id = ? AND userid  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getUsername());			statement.setString( 4,beanData.getMenuywclass());			statement.setString( 5,beanData.getMenuid());			statement.setString( 6,beanData.getMenuname());			statement.setString( 7,beanData.getMenuurl());			statement.setString( 8,beanData.getIp());			statement.setString( 9,beanData.getBrowsename());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());
			statement.setString( 15,beanData.getId());
			statement.setString( 16,beanData.getUserid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_OPERLOG SET id= ? , userid= ? , username= ? , menuywclass= ? , menuid= ? , menuname= ? , menuurl= ? , ip= ? , browsename= ? , createtime= ? , createperson= ? , createunitid= ? , deleteflag= ? , deptguid=? WHERE  id = ? AND userid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_OPERLOG SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "',");			bufSQL.append("Username = " + "'" + nullString(beanData.getUsername()) + "',");			bufSQL.append("Menuywclass = " + "'" + nullString(beanData.getMenuywclass()) + "',");			bufSQL.append("Menuid = " + "'" + nullString(beanData.getMenuid()) + "',");			bufSQL.append("Menuname = " + "'" + nullString(beanData.getMenuname()) + "',");			bufSQL.append("Menuurl = " + "'" + nullString(beanData.getMenuurl()) + "',");			bufSQL.append("Ip = " + "'" + nullString(beanData.getIp()) + "',");			bufSQL.append("Browsename = " + "'" + nullString(beanData.getBrowsename()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "'");
			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "'");
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
		S_OPERLOGData beanData = (S_OPERLOGData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_OPERLOG SET id= ? , userid= ? , username= ? , menuywclass= ? , menuid= ? , menuname= ? , menuurl= ? , ip= ? , browsename= ? , createtime= ? , createperson= ? , createunitid= ? , deleteflag= ? , deptguid=? WHERE  id = ? AND userid  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getUsername());			statement.setString( 4,beanData.getMenuywclass());			statement.setString( 5,beanData.getMenuid());			statement.setString( 6,beanData.getMenuname());			statement.setString( 7,beanData.getMenuurl());			statement.setString( 8,beanData.getIp());			statement.setString( 9,beanData.getBrowsename());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());
			statement.setString( 15,beanData.getId());
			statement.setString( 16,beanData.getUserid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_OPERLOG SET id= ? , userid= ? , username= ? , menuywclass= ? , menuid= ? , menuname= ? , menuurl= ? , ip= ? , browsename= ? , createtime= ? , createperson= ? , createunitid= ? , deleteflag= ? , deptguid=? WHERE  id = ? AND userid  = ?"+ e.toString());
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
		S_OPERLOGData beanData = (S_OPERLOGData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id,userid  FROM S_OPERLOG WHERE  id=? AND userid =?");
			statement.setString( 1,beanData.getId());
			statement.setString( 2,beanData.getUserid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id,userid  FROM S_OPERLOG WHERE  id=? AND userid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
