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
public class B_ReformLogDao extends BaseAbstractDao
{
	public B_ReformLogData beanData=new B_ReformLogData();
	public B_ReformLogDao()
	{
	}
	public B_ReformLogDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_ReformLogData)FindByPrimaryKey(beanData);
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
		B_ReformLogData beanData = (B_ReformLogData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ReformLog( id,projectid,createdate,oldfileno,newfileno,content,reason,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ReformLog( id,projectid,createdate,oldfileno,newfileno,content,reason,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_ReformLog( id,projectid,createdate,oldfileno,newfileno,content,reason,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");
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
		B_ReformLogData beanData = (B_ReformLogData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ReformLog( id,projectid,createdate,oldfileno,newfileno,content,reason,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ReformLog( id,projectid,createdate,oldfileno,newfileno,content,reason,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_ReformLogData beanData = (B_ReformLogData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_ReformLog WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ReformLog: "+ e.toString());
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
		B_ReformLogData beanData = (B_ReformLogData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_ReformLog WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_ReformLog"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ReformLog: "+ e.toString());
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
		B_ReformLogData beanData = (B_ReformLogData) beanDataTmp; 
		B_ReformLogData returnData=new B_ReformLogData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,projectid,createdate,oldfileno,newfileno,content,reason,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ReformLog WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,projectid,createdate,oldfileno,newfileno,content,reason,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ReformLog  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,projectid,createdate,oldfileno,newfileno,content,reason,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ReformLog"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_ReformLogData returnData=new B_ReformLogData();
				returnData.setId( resultSet.getString( 1));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,projectid,createdate,oldfileno,newfileno,content,reason,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ReformLog" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_ReformLog SET id= ? , projectid= ? , createdate= ? , oldfileno= ? , newfileno= ? , content= ? , reason= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());
			statement.setString( 15,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ReformLog SET id= ? , projectid= ? , createdate= ? , oldfileno= ? , newfileno= ? , content= ? , reason= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_ReformLog SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");
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
		B_ReformLogData beanData = (B_ReformLogData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_ReformLog SET id= ? , projectid= ? , createdate= ? , oldfileno= ? , newfileno= ? , content= ? , reason= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());
			statement.setString( 15,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ReformLog SET id= ? , projectid= ? , createdate= ? , oldfileno= ? , newfileno= ? , content= ? , reason= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
		B_ReformLogData beanData = (B_ReformLogData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM B_ReformLog WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_ReformLog WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}