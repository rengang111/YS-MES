package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.util.basedao.BaseAbstractDao;
import com.ys.business.db.data.*;

/**
* <p>Title: </p>
* <p>Description: 数据表  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_MouldLendConfirmDao extends BaseAbstractDao
{
	public B_MouldLendConfirmData beanData=new B_MouldLendConfirmData();
	public B_MouldLendConfirmDao()
	{
	}
	public B_MouldLendConfirmDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MouldLendConfirmData)FindByPrimaryKey(beanData);
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
		B_MouldLendConfirmData beanData = (B_MouldLendConfirmData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldLendConfirm( lendid,proposer,brokerage,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getLendid());			statement.setString( 2,beanData.getProposer());			statement.setString( 3,beanData.getBrokerage());			statement.setString( 4,beanData.getConfirm());			statement.setString( 5,beanData.getDeptguid());			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldLendConfirm( lendid,proposer,brokerage,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
			bufSQL.append("INSERT INTO B_MouldLendConfirm( lendid,proposer,brokerage,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getLendid()) + "',");			bufSQL.append("'" + nullString(beanData.getProposer()) + "',");			bufSQL.append("'" + nullString(beanData.getBrokerage()) + "',");			bufSQL.append("'" + nullString(beanData.getConfirm()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_MouldLendConfirmData beanData = (B_MouldLendConfirmData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldLendConfirm( lendid,proposer,brokerage,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getLendid());			statement.setString( 2,beanData.getProposer());			statement.setString( 3,beanData.getBrokerage());			statement.setString( 4,beanData.getConfirm());			statement.setString( 5,beanData.getDeptguid());			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldLendConfirm( lendid,proposer,brokerage,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
		B_MouldLendConfirmData beanData = (B_MouldLendConfirmData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_MouldLendConfirm WHERE  lendid =?");
			statement.setString( 1,beanData.getLendid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldLendConfirm: "+ e.toString());
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
		B_MouldLendConfirmData beanData = (B_MouldLendConfirmData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_MouldLendConfirm WHERE ");
			bufSQL.append("Lendid = " + "'" + nullString(beanData.getLendid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM B_MouldLendConfirm"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldLendConfirm: "+ e.toString());
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
		B_MouldLendConfirmData beanData = (B_MouldLendConfirmData) beanDataTmp; 
		B_MouldLendConfirmData returnData=new B_MouldLendConfirmData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT lendid,proposer,brokerage,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldLendConfirm WHERE  lendid =?");
			statement.setString( 1,beanData.getLendid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setLendid( resultSet.getString( 1));			returnData.setProposer( resultSet.getString( 2));			returnData.setBrokerage( resultSet.getString( 3));			returnData.setConfirm( resultSet.getString( 4));			returnData.setDeptguid( resultSet.getString( 5));			returnData.setCreatetime( resultSet.getString( 6));			returnData.setCreateperson( resultSet.getString( 7));			returnData.setCreateunitid( resultSet.getString( 8));			returnData.setModifytime( resultSet.getString( 9));			returnData.setModifyperson( resultSet.getString( 10));			returnData.setDeleteflag( resultSet.getString( 11));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT lendid,proposer,brokerage,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldLendConfirm  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT lendid,proposer,brokerage,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldLendConfirm"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MouldLendConfirmData returnData=new B_MouldLendConfirmData();
				returnData.setLendid( resultSet.getString( 1));				returnData.setProposer( resultSet.getString( 2));				returnData.setBrokerage( resultSet.getString( 3));				returnData.setConfirm( resultSet.getString( 4));				returnData.setDeptguid( resultSet.getString( 5));				returnData.setCreatetime( resultSet.getString( 6));				returnData.setCreateperson( resultSet.getString( 7));				returnData.setCreateunitid( resultSet.getString( 8));				returnData.setModifytime( resultSet.getString( 9));				returnData.setModifyperson( resultSet.getString( 10));				returnData.setDeleteflag( resultSet.getString( 11));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT lendid,proposer,brokerage,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldLendConfirm" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_MouldLendConfirm SET lendid= ? , proposer= ? , brokerage= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  lendid  = ?");
			statement.setString( 1,beanData.getLendid());			statement.setString( 2,beanData.getProposer());			statement.setString( 3,beanData.getBrokerage());			statement.setString( 4,beanData.getConfirm());			statement.setString( 5,beanData.getDeptguid());			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());
			statement.setString( 12,beanData.getLendid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldLendConfirm SET lendid= ? , proposer= ? , brokerage= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  lendid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_MouldLendConfirm SET ");
			bufSQL.append("Lendid = " + "'" + nullString(beanData.getLendid()) + "',");			bufSQL.append("Proposer = " + "'" + nullString(beanData.getProposer()) + "',");			bufSQL.append("Brokerage = " + "'" + nullString(beanData.getBrokerage()) + "',");			bufSQL.append("Confirm = " + "'" + nullString(beanData.getConfirm()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Lendid = " + "'" + nullString(beanData.getLendid()) + "'");
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
		B_MouldLendConfirmData beanData = (B_MouldLendConfirmData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_MouldLendConfirm SET lendid= ? , proposer= ? , brokerage= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  lendid  = ?");
			statement.setString( 1,beanData.getLendid());			statement.setString( 2,beanData.getProposer());			statement.setString( 3,beanData.getBrokerage());			statement.setString( 4,beanData.getConfirm());			statement.setString( 5,beanData.getDeptguid());			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());
			statement.setString( 12,beanData.getLendid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldLendConfirm SET lendid= ? , proposer= ? , brokerage= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  lendid  = ?"+ e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param beanData:主键
	 */
	public void FindPrimaryKey(Object beanDataTmp) throws Exception
	{
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
		B_MouldLendConfirmData beanData = (B_MouldLendConfirmData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  lendid  FROM B_MouldLendConfirm WHERE  lendid =?");
			statement.setString( 1,beanData.getLendid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  lendid  FROM B_MouldLendConfirm WHERE  lendid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
