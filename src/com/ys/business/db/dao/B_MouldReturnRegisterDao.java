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
public class B_MouldReturnRegisterDao extends BaseAbstractDao
{
	public B_MouldReturnRegisterData beanData=new B_MouldReturnRegisterData();
	public B_MouldReturnRegisterDao()
	{
	}
	public B_MouldReturnRegisterDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MouldReturnRegisterData)FindByPrimaryKey(beanData);
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
		B_MouldReturnRegisterData beanData = (B_MouldReturnRegisterData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldReturnRegister( id,lendid,mouldreturnno,factreturntime,acceptresult,memo,acceptor,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getLendid());			statement.setString( 3,beanData.getMouldreturnno());			statement.setString( 4,beanData.getFactreturntime());			statement.setString( 5,beanData.getAcceptresult());			statement.setString( 6,beanData.getMemo());			statement.setString( 7,beanData.getAcceptor());			statement.setString( 8,beanData.getConfirm());			statement.setString( 9,beanData.getDeptguid());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldReturnRegister( id,lendid,mouldreturnno,factreturntime,acceptresult,memo,acceptor,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
			bufSQL.append("INSERT INTO B_MouldReturnRegister( id,lendid,mouldreturnno,factreturntime,acceptresult,memo,acceptor,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getLendid()) + "',");			bufSQL.append("'" + nullString(beanData.getMouldreturnno()) + "',");			bufSQL.append("'" + nullString(beanData.getFactreturntime()) + "',");			bufSQL.append("'" + nullString(beanData.getAcceptresult()) + "',");			bufSQL.append("'" + nullString(beanData.getMemo()) + "',");			bufSQL.append("'" + nullString(beanData.getAcceptor()) + "',");			bufSQL.append("'" + nullString(beanData.getConfirm()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_MouldReturnRegisterData beanData = (B_MouldReturnRegisterData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldReturnRegister( id,lendid,mouldreturnno,factreturntime,acceptresult,memo,acceptor,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getLendid());			statement.setString( 3,beanData.getMouldreturnno());			statement.setString( 4,beanData.getFactreturntime());			statement.setString( 5,beanData.getAcceptresult());			statement.setString( 6,beanData.getMemo());			statement.setString( 7,beanData.getAcceptor());			statement.setString( 8,beanData.getConfirm());			statement.setString( 9,beanData.getDeptguid());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldReturnRegister( id,lendid,mouldreturnno,factreturntime,acceptresult,memo,acceptor,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
		B_MouldReturnRegisterData beanData = (B_MouldReturnRegisterData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_MouldReturnRegister WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldReturnRegister: "+ e.toString());
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
		B_MouldReturnRegisterData beanData = (B_MouldReturnRegisterData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_MouldReturnRegister WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_MouldReturnRegister"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldReturnRegister: "+ e.toString());
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
		B_MouldReturnRegisterData beanData = (B_MouldReturnRegisterData) beanDataTmp; 
		B_MouldReturnRegisterData returnData=new B_MouldReturnRegisterData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,lendid,mouldreturnno,factreturntime,acceptresult,memo,acceptor,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldReturnRegister WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setLendid( resultSet.getString( 2));			returnData.setMouldreturnno( resultSet.getString( 3));			returnData.setFactreturntime( resultSet.getString( 4));			returnData.setAcceptresult( resultSet.getString( 5));			returnData.setMemo( resultSet.getString( 6));			returnData.setAcceptor( resultSet.getString( 7));			returnData.setConfirm( resultSet.getString( 8));			returnData.setDeptguid( resultSet.getString( 9));			returnData.setCreatetime( resultSet.getString( 10));			returnData.setCreateperson( resultSet.getString( 11));			returnData.setCreateunitid( resultSet.getString( 12));			returnData.setModifytime( resultSet.getString( 13));			returnData.setModifyperson( resultSet.getString( 14));			returnData.setDeleteflag( resultSet.getString( 15));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,lendid,mouldreturnno,factreturntime,acceptresult,memo,acceptor,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldReturnRegister  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,lendid,mouldreturnno,factreturntime,acceptresult,memo,acceptor,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldReturnRegister"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MouldReturnRegisterData returnData=new B_MouldReturnRegisterData();
				returnData.setId( resultSet.getString( 1));				returnData.setLendid( resultSet.getString( 2));				returnData.setMouldreturnno( resultSet.getString( 3));				returnData.setFactreturntime( resultSet.getString( 4));				returnData.setAcceptresult( resultSet.getString( 5));				returnData.setMemo( resultSet.getString( 6));				returnData.setAcceptor( resultSet.getString( 7));				returnData.setConfirm( resultSet.getString( 8));				returnData.setDeptguid( resultSet.getString( 9));				returnData.setCreatetime( resultSet.getString( 10));				returnData.setCreateperson( resultSet.getString( 11));				returnData.setCreateunitid( resultSet.getString( 12));				returnData.setModifytime( resultSet.getString( 13));				returnData.setModifyperson( resultSet.getString( 14));				returnData.setDeleteflag( resultSet.getString( 15));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,lendid,mouldreturnno,factreturntime,acceptresult,memo,acceptor,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldReturnRegister" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_MouldReturnRegister SET id= ? , lendid= ? , mouldreturnno= ? , factreturntime= ? , acceptresult= ? , memo= ? , acceptor= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getLendid());			statement.setString( 3,beanData.getMouldreturnno());			statement.setString( 4,beanData.getFactreturntime());			statement.setString( 5,beanData.getAcceptresult());			statement.setString( 6,beanData.getMemo());			statement.setString( 7,beanData.getAcceptor());			statement.setString( 8,beanData.getConfirm());			statement.setString( 9,beanData.getDeptguid());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());
			statement.setString( 16,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldReturnRegister SET id= ? , lendid= ? , mouldreturnno= ? , factreturntime= ? , acceptresult= ? , memo= ? , acceptor= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_MouldReturnRegister SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Lendid = " + "'" + nullString(beanData.getLendid()) + "',");			bufSQL.append("Mouldreturnno = " + "'" + nullString(beanData.getMouldreturnno()) + "',");			bufSQL.append("Factreturntime = " + "'" + nullString(beanData.getFactreturntime()) + "',");			bufSQL.append("Acceptresult = " + "'" + nullString(beanData.getAcceptresult()) + "',");			bufSQL.append("Memo = " + "'" + nullString(beanData.getMemo()) + "',");			bufSQL.append("Acceptor = " + "'" + nullString(beanData.getAcceptor()) + "',");			bufSQL.append("Confirm = " + "'" + nullString(beanData.getConfirm()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_MouldReturnRegisterData beanData = (B_MouldReturnRegisterData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_MouldReturnRegister SET id= ? , lendid= ? , mouldreturnno= ? , factreturntime= ? , acceptresult= ? , memo= ? , acceptor= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getLendid());			statement.setString( 3,beanData.getMouldreturnno());			statement.setString( 4,beanData.getFactreturntime());			statement.setString( 5,beanData.getAcceptresult());			statement.setString( 6,beanData.getMemo());			statement.setString( 7,beanData.getAcceptor());			statement.setString( 8,beanData.getConfirm());			statement.setString( 9,beanData.getDeptguid());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());
			statement.setString( 16,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldReturnRegister SET id= ? , lendid= ? , mouldreturnno= ? , factreturntime= ? , acceptresult= ? , memo= ? , acceptor= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
		B_MouldReturnRegisterData beanData = (B_MouldReturnRegisterData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM B_MouldReturnRegister WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_MouldReturnRegister WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
