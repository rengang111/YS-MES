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
public class B_OrganBasicInfoDao extends BaseAbstractDao
{
	public B_OrganBasicInfoData beanData=new B_OrganBasicInfoData();
	public B_OrganBasicInfoDao()
	{
	}
	public B_OrganBasicInfoDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_OrganBasicInfoData)FindByPrimaryKey(beanData);
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
		B_OrganBasicInfoData beanData = (B_OrganBasicInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_OrganBasicInfo( id,category,name_short,name_full,address,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());
			statement.setString( 2,beanData.getCategory());
			statement.setString( 3,beanData.getName_short());
			statement.setString( 4,beanData.getName_full());
			statement.setString( 5,beanData.getAddress());
			statement.setString( 6,beanData.getDeptguid());
			statement.setString( 7,beanData.getCreatetime());
			statement.setString( 8,beanData.getCreateperson());
			statement.setString( 9,beanData.getCreateunitid());
			statement.setString( 10,beanData.getModifytime());
			statement.setString( 11,beanData.getModifyperson());
			statement.setString( 12,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_OrganBasicInfo( id,category,name_short,name_full,address,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
			bufSQL.append("INSERT INTO B_OrganBasicInfo( id,category,name_short,name_full,address,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");
			bufSQL.append("'" + nullString(beanData.getCategory()) + "',");
			bufSQL.append("'" + nullString(beanData.getName_short()) + "',");
			bufSQL.append("'" + nullString(beanData.getName_full()) + "',");
			bufSQL.append("'" + nullString(beanData.getAddress()) + "',");
			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");
			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");
			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");
			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");
			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");
			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");
			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_OrganBasicInfoData beanData = (B_OrganBasicInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_OrganBasicInfo( id,category,name_short,name_full,address,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());
			statement.setString( 2,beanData.getCategory());
			statement.setString( 3,beanData.getName_short());
			statement.setString( 4,beanData.getName_full());
			statement.setString( 5,beanData.getAddress());
			statement.setString( 6,beanData.getDeptguid());
			statement.setString( 7,beanData.getCreatetime());
			statement.setString( 8,beanData.getCreateperson());
			statement.setString( 9,beanData.getCreateunitid());
			statement.setString( 10,beanData.getModifytime());
			statement.setString( 11,beanData.getModifyperson());
			statement.setString( 12,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_OrganBasicInfo( id,category,name_short,name_full,address,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
		B_OrganBasicInfoData beanData = (B_OrganBasicInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_OrganBasicInfo WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_OrganBasicInfo: "+ e.toString());
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
		B_OrganBasicInfoData beanData = (B_OrganBasicInfoData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_OrganBasicInfo WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_OrganBasicInfo"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_OrganBasicInfo: "+ e.toString());
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
		B_OrganBasicInfoData beanData = (B_OrganBasicInfoData) beanDataTmp; 
		B_OrganBasicInfoData returnData=new B_OrganBasicInfoData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,category,name_short,name_full,address,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_OrganBasicInfo WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));
			returnData.setCategory( resultSet.getString( 2));
			returnData.setName_short( resultSet.getString( 3));
			returnData.setName_full( resultSet.getString( 4));
			returnData.setAddress( resultSet.getString( 5));
			returnData.setDeptguid( resultSet.getString( 6));
			returnData.setCreatetime( resultSet.getString( 7));
			returnData.setCreateperson( resultSet.getString( 8));
			returnData.setCreateunitid( resultSet.getString( 9));
			returnData.setModifytime( resultSet.getString( 10));
			returnData.setModifyperson( resultSet.getString( 11));
			returnData.setDeleteflag( resultSet.getString( 12));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,category,name_short,name_full,address,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_OrganBasicInfo  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,category,name_short,name_full,address,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_OrganBasicInfo"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_OrganBasicInfoData returnData=new B_OrganBasicInfoData();
				returnData.setId( resultSet.getString( 1));
				returnData.setCategory( resultSet.getString( 2));
				returnData.setName_short( resultSet.getString( 3));
				returnData.setName_full( resultSet.getString( 4));
				returnData.setAddress( resultSet.getString( 5));
				returnData.setDeptguid( resultSet.getString( 6));
				returnData.setCreatetime( resultSet.getString( 7));
				returnData.setCreateperson( resultSet.getString( 8));
				returnData.setCreateunitid( resultSet.getString( 9));
				returnData.setModifytime( resultSet.getString( 10));
				returnData.setModifyperson( resultSet.getString( 11));
				returnData.setDeleteflag( resultSet.getString( 12));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,category,name_short,name_full,address,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_OrganBasicInfo" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_OrganBasicInfo SET id= ? , category= ? , name_short= ? , name_full= ? , address= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());
			statement.setString( 2,beanData.getCategory());
			statement.setString( 3,beanData.getName_short());
			statement.setString( 4,beanData.getName_full());
			statement.setString( 5,beanData.getAddress());
			statement.setString( 6,beanData.getDeptguid());
			statement.setString( 7,beanData.getCreatetime());
			statement.setString( 8,beanData.getCreateperson());
			statement.setString( 9,beanData.getCreateunitid());
			statement.setString( 10,beanData.getModifytime());
			statement.setString( 11,beanData.getModifyperson());
			statement.setString( 12,beanData.getDeleteflag());
			statement.setString( 13,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_OrganBasicInfo SET id= ? , category= ? , name_short= ? , name_full= ? , address= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_OrganBasicInfo SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");
			bufSQL.append("Category = " + "'" + nullString(beanData.getCategory()) + "',");
			bufSQL.append("Name_short = " + "'" + nullString(beanData.getName_short()) + "',");
			bufSQL.append("Name_full = " + "'" + nullString(beanData.getName_full()) + "',");
			bufSQL.append("Address = " + "'" + nullString(beanData.getAddress()) + "',");
			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");
			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");
			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");
			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");
			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");
			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");
			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_OrganBasicInfoData beanData = (B_OrganBasicInfoData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_OrganBasicInfo SET id= ? , category= ? , name_short= ? , name_full= ? , address= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());
			statement.setString( 2,beanData.getCategory());
			statement.setString( 3,beanData.getName_short());
			statement.setString( 4,beanData.getName_full());
			statement.setString( 5,beanData.getAddress());
			statement.setString( 6,beanData.getDeptguid());
			statement.setString( 7,beanData.getCreatetime());
			statement.setString( 8,beanData.getCreateperson());
			statement.setString( 9,beanData.getCreateunitid());
			statement.setString( 10,beanData.getModifytime());
			statement.setString( 11,beanData.getModifyperson());
			statement.setString( 12,beanData.getDeleteflag());
			statement.setString( 13,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_OrganBasicInfo SET id= ? , category= ? , name_short= ? , name_full= ? , address= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
		B_OrganBasicInfoData beanData = (B_OrganBasicInfoData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM B_OrganBasicInfo WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_OrganBasicInfo WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
