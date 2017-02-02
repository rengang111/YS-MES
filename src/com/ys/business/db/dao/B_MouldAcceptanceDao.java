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
public class B_MouldAcceptanceDao extends BaseAbstractDao
{
	public B_MouldAcceptanceData beanData=new B_MouldAcceptanceData();
	public B_MouldAcceptanceDao()
	{
	}
	public B_MouldAcceptanceDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MouldAcceptanceData)FindByPrimaryKey(beanData);
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
		B_MouldAcceptanceData beanData = (B_MouldAcceptanceData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldAcceptance( mouldbaseid,acceptancedate,result,memo,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getMouldbaseid());			statement.setString( 2,beanData.getAcceptancedate());			statement.setString( 3,beanData.getResult());			statement.setString( 4,beanData.getMemo());			statement.setString( 5,beanData.getConfirm());			statement.setString( 6,beanData.getDeptguid());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldAcceptance( mouldbaseid,acceptancedate,result,memo,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_MouldAcceptance( mouldbaseid,acceptancedate,result,memo,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getMouldbaseid()) + "',");			bufSQL.append("'" + nullString(beanData.getAcceptancedate()) + "',");			bufSQL.append("'" + nullString(beanData.getResult()) + "',");			bufSQL.append("'" + nullString(beanData.getMemo()) + "',");			bufSQL.append("'" + nullString(beanData.getConfirm()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_MouldAcceptanceData beanData = (B_MouldAcceptanceData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldAcceptance( mouldbaseid,acceptancedate,result,memo,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getMouldbaseid());			statement.setString( 2,beanData.getAcceptancedate());			statement.setString( 3,beanData.getResult());			statement.setString( 4,beanData.getMemo());			statement.setString( 5,beanData.getConfirm());			statement.setString( 6,beanData.getDeptguid());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldAcceptance( mouldbaseid,acceptancedate,result,memo,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_MouldAcceptanceData beanData = (B_MouldAcceptanceData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_MouldAcceptance WHERE  mouldbaseid =?");
			statement.setString( 1,beanData.getMouldbaseid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldAcceptance: "+ e.toString());
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
		B_MouldAcceptanceData beanData = (B_MouldAcceptanceData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_MouldAcceptance WHERE ");
			bufSQL.append("Mouldbaseid = " + "'" + nullString(beanData.getMouldbaseid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM B_MouldAcceptance"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldAcceptance: "+ e.toString());
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
		B_MouldAcceptanceData beanData = (B_MouldAcceptanceData) beanDataTmp; 
		B_MouldAcceptanceData returnData=new B_MouldAcceptanceData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT mouldbaseid,acceptancedate,result,memo,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldAcceptance WHERE  mouldbaseid =?");
			statement.setString( 1,beanData.getMouldbaseid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setMouldbaseid( resultSet.getString( 1));			returnData.setAcceptancedate( resultSet.getString( 2));			returnData.setResult( resultSet.getString( 3));			returnData.setMemo( resultSet.getString( 4));			returnData.setConfirm( resultSet.getString( 5));			returnData.setDeptguid( resultSet.getString( 6));			returnData.setCreatetime( resultSet.getString( 7));			returnData.setCreateperson( resultSet.getString( 8));			returnData.setCreateunitid( resultSet.getString( 9));			returnData.setModifytime( resultSet.getString( 10));			returnData.setModifyperson( resultSet.getString( 11));			returnData.setDeleteflag( resultSet.getString( 12));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT mouldbaseid,acceptancedate,result,memo,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldAcceptance  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT mouldbaseid,acceptancedate,result,memo,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldAcceptance"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MouldAcceptanceData returnData=new B_MouldAcceptanceData();
				returnData.setMouldbaseid( resultSet.getString( 1));				returnData.setAcceptancedate( resultSet.getString( 2));				returnData.setResult( resultSet.getString( 3));				returnData.setMemo( resultSet.getString( 4));				returnData.setConfirm( resultSet.getString( 5));				returnData.setDeptguid( resultSet.getString( 6));				returnData.setCreatetime( resultSet.getString( 7));				returnData.setCreateperson( resultSet.getString( 8));				returnData.setCreateunitid( resultSet.getString( 9));				returnData.setModifytime( resultSet.getString( 10));				returnData.setModifyperson( resultSet.getString( 11));				returnData.setDeleteflag( resultSet.getString( 12));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT mouldbaseid,acceptancedate,result,memo,confirm,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldAcceptance" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_MouldAcceptance SET mouldbaseid= ? , acceptancedate= ? , result= ? , memo= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  mouldbaseid  = ?");
			statement.setString( 1,beanData.getMouldbaseid());			statement.setString( 2,beanData.getAcceptancedate());			statement.setString( 3,beanData.getResult());			statement.setString( 4,beanData.getMemo());			statement.setString( 5,beanData.getConfirm());			statement.setString( 6,beanData.getDeptguid());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());
			statement.setString( 13,beanData.getMouldbaseid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldAcceptance SET mouldbaseid= ? , acceptancedate= ? , result= ? , memo= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  mouldbaseid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_MouldAcceptance SET ");
			bufSQL.append("Mouldbaseid = " + "'" + nullString(beanData.getMouldbaseid()) + "',");			bufSQL.append("Acceptancedate = " + "'" + nullString(beanData.getAcceptancedate()) + "',");			bufSQL.append("Result = " + "'" + nullString(beanData.getResult()) + "',");			bufSQL.append("Memo = " + "'" + nullString(beanData.getMemo()) + "',");			bufSQL.append("Confirm = " + "'" + nullString(beanData.getConfirm()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Mouldbaseid = " + "'" + nullString(beanData.getMouldbaseid()) + "'");
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
		B_MouldAcceptanceData beanData = (B_MouldAcceptanceData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_MouldAcceptance SET mouldbaseid= ? , acceptancedate= ? , result= ? , memo= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  mouldbaseid  = ?");
			statement.setString( 1,beanData.getMouldbaseid());			statement.setString( 2,beanData.getAcceptancedate());			statement.setString( 3,beanData.getResult());			statement.setString( 4,beanData.getMemo());			statement.setString( 5,beanData.getConfirm());			statement.setString( 6,beanData.getDeptguid());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());
			statement.setString( 13,beanData.getMouldbaseid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldAcceptance SET mouldbaseid= ? , acceptancedate= ? , result= ? , memo= ? , confirm= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  mouldbaseid  = ?"+ e.toString());
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
		B_MouldAcceptanceData beanData = (B_MouldAcceptanceData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  mouldbaseid  FROM B_MouldAcceptance WHERE  mouldbaseid =?");
			statement.setString( 1,beanData.getMouldbaseid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  mouldbaseid  FROM B_MouldAcceptance WHERE  mouldbaseid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
