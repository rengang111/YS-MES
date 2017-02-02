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
public class B_LatePerfectionQuestionDao extends BaseAbstractDao
{
	public B_LatePerfectionQuestionData beanData=new B_LatePerfectionQuestionData();
	public B_LatePerfectionQuestionDao()
	{
	}
	public B_LatePerfectionQuestionDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_LatePerfectionQuestionData)FindByPrimaryKey(beanData);
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
		B_LatePerfectionQuestionData beanData = (B_LatePerfectionQuestionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_LatePerfectionQuestion( id,projectid,createdate,description,improve,expectfinishdate,finishtime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProjectid());			statement.setString( 3,beanData.getCreatedate());			statement.setString( 4,beanData.getDescription());			statement.setString( 5,beanData.getImprove());			statement.setString( 6,beanData.getExpectfinishdate());			statement.setString( 7,beanData.getFinishtime());			statement.setString( 8,beanData.getDeptguid());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_LatePerfectionQuestion( id,projectid,createdate,description,improve,expectfinishdate,finishtime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_LatePerfectionQuestion( id,projectid,createdate,description,improve,expectfinishdate,finishtime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getProjectid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatedate()) + "',");			bufSQL.append("'" + nullString(beanData.getDescription()) + "',");			bufSQL.append("'" + nullString(beanData.getImprove()) + "',");			bufSQL.append("'" + nullString(beanData.getExpectfinishdate()) + "',");			bufSQL.append("'" + nullString(beanData.getFinishtime()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_LatePerfectionQuestionData beanData = (B_LatePerfectionQuestionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_LatePerfectionQuestion( id,projectid,createdate,description,improve,expectfinishdate,finishtime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProjectid());			statement.setString( 3,beanData.getCreatedate());			statement.setString( 4,beanData.getDescription());			statement.setString( 5,beanData.getImprove());			statement.setString( 6,beanData.getExpectfinishdate());			statement.setString( 7,beanData.getFinishtime());			statement.setString( 8,beanData.getDeptguid());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_LatePerfectionQuestion( id,projectid,createdate,description,improve,expectfinishdate,finishtime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_LatePerfectionQuestionData beanData = (B_LatePerfectionQuestionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_LatePerfectionQuestion WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_LatePerfectionQuestion: "+ e.toString());
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
		B_LatePerfectionQuestionData beanData = (B_LatePerfectionQuestionData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_LatePerfectionQuestion WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_LatePerfectionQuestion"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_LatePerfectionQuestion: "+ e.toString());
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
		B_LatePerfectionQuestionData beanData = (B_LatePerfectionQuestionData) beanDataTmp; 
		B_LatePerfectionQuestionData returnData=new B_LatePerfectionQuestionData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,projectid,createdate,description,improve,expectfinishdate,finishtime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_LatePerfectionQuestion WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProjectid( resultSet.getString( 2));			returnData.setCreatedate( resultSet.getString( 3));			returnData.setDescription( resultSet.getString( 4));			returnData.setImprove( resultSet.getString( 5));			returnData.setExpectfinishdate( resultSet.getString( 6));			returnData.setFinishtime( resultSet.getString( 7));			returnData.setDeptguid( resultSet.getString( 8));			returnData.setCreatetime( resultSet.getString( 9));			returnData.setCreateperson( resultSet.getString( 10));			returnData.setCreateunitid( resultSet.getString( 11));			returnData.setModifytime( resultSet.getString( 12));			returnData.setModifyperson( resultSet.getString( 13));			returnData.setDeleteflag( resultSet.getString( 14));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,projectid,createdate,description,improve,expectfinishdate,finishtime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_LatePerfectionQuestion  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,projectid,createdate,description,improve,expectfinishdate,finishtime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_LatePerfectionQuestion"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_LatePerfectionQuestionData returnData=new B_LatePerfectionQuestionData();
				returnData.setId( resultSet.getString( 1));				returnData.setProjectid( resultSet.getString( 2));				returnData.setCreatedate( resultSet.getString( 3));				returnData.setDescription( resultSet.getString( 4));				returnData.setImprove( resultSet.getString( 5));				returnData.setExpectfinishdate( resultSet.getString( 6));				returnData.setFinishtime( resultSet.getString( 7));				returnData.setDeptguid( resultSet.getString( 8));				returnData.setCreatetime( resultSet.getString( 9));				returnData.setCreateperson( resultSet.getString( 10));				returnData.setCreateunitid( resultSet.getString( 11));				returnData.setModifytime( resultSet.getString( 12));				returnData.setModifyperson( resultSet.getString( 13));				returnData.setDeleteflag( resultSet.getString( 14));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,projectid,createdate,description,improve,expectfinishdate,finishtime,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_LatePerfectionQuestion" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_LatePerfectionQuestion SET id= ? , projectid= ? , createdate= ? , description= ? , improve= ? , expectfinishdate= ? , finishtime= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProjectid());			statement.setString( 3,beanData.getCreatedate());			statement.setString( 4,beanData.getDescription());			statement.setString( 5,beanData.getImprove());			statement.setString( 6,beanData.getExpectfinishdate());			statement.setString( 7,beanData.getFinishtime());			statement.setString( 8,beanData.getDeptguid());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());
			statement.setString( 15,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_LatePerfectionQuestion SET id= ? , projectid= ? , createdate= ? , description= ? , improve= ? , expectfinishdate= ? , finishtime= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_LatePerfectionQuestion SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Projectid = " + "'" + nullString(beanData.getProjectid()) + "',");			bufSQL.append("Createdate = " + "'" + nullString(beanData.getCreatedate()) + "',");			bufSQL.append("Description = " + "'" + nullString(beanData.getDescription()) + "',");			bufSQL.append("Improve = " + "'" + nullString(beanData.getImprove()) + "',");			bufSQL.append("Expectfinishdate = " + "'" + nullString(beanData.getExpectfinishdate()) + "',");			bufSQL.append("Finishtime = " + "'" + nullString(beanData.getFinishtime()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_LatePerfectionQuestionData beanData = (B_LatePerfectionQuestionData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_LatePerfectionQuestion SET id= ? , projectid= ? , createdate= ? , description= ? , improve= ? , expectfinishdate= ? , finishtime= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProjectid());			statement.setString( 3,beanData.getCreatedate());			statement.setString( 4,beanData.getDescription());			statement.setString( 5,beanData.getImprove());			statement.setString( 6,beanData.getExpectfinishdate());			statement.setString( 7,beanData.getFinishtime());			statement.setString( 8,beanData.getDeptguid());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());
			statement.setString( 15,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_LatePerfectionQuestion SET id= ? , projectid= ? , createdate= ? , description= ? , improve= ? , expectfinishdate= ? , finishtime= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
		B_LatePerfectionQuestionData beanData = (B_LatePerfectionQuestionData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM B_LatePerfectionQuestion WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_LatePerfectionQuestion WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
