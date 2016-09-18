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
public class S_ROLEDao extends BaseAbstractDao
{
	public S_ROLEData beanData=new S_ROLEData();
	public S_ROLEDao()
	{
	}
	public S_ROLEDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_ROLEData)FindByPrimaryKey(beanData);
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
		S_ROLEData beanData = (S_ROLEData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_ROLE( roleid,rolename,roletype,unitid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRoleid());			statement.setString( 2,beanData.getRolename());			statement.setString( 3,beanData.getRoletype());			statement.setString( 4,beanData.getUnitid());			if (beanData.getSortno()== null)			{				statement.setNull( 5, Types.INTEGER);			}			else			{				statement.setInt( 5, beanData.getSortno().intValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_ROLE( roleid,rolename,roletype,unitid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_ROLE( roleid,rolename,roletype,unitid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRoleid()) + "',");			bufSQL.append("'" + nullString(beanData.getRolename()) + "',");			bufSQL.append("'" + nullString(beanData.getRoletype()) + "',");			bufSQL.append("'" + nullString(beanData.getUnitid()) + "',");			bufSQL.append(beanData.getSortno().intValue() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "'");
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
		S_ROLEData beanData = (S_ROLEData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_ROLE( roleid,rolename,roletype,unitid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRoleid());			statement.setString( 2,beanData.getRolename());			statement.setString( 3,beanData.getRoletype());			statement.setString( 4,beanData.getUnitid());			if (beanData.getSortno()== null)			{				statement.setNull( 5, Types.INTEGER);			}			else			{				statement.setInt( 5, beanData.getSortno().intValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_ROLE( roleid,rolename,roletype,unitid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_ROLEData beanData = (S_ROLEData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_ROLE WHERE  roleid =?");
			statement.setString( 1,beanData.getRoleid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_ROLE: "+ e.toString());
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
		S_ROLEData beanData = (S_ROLEData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_ROLE WHERE ");
			bufSQL.append("Roleid = " + "'" + nullString(beanData.getRoleid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM S_ROLE"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_ROLE: "+ e.toString());
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
		S_ROLEData beanData = (S_ROLEData) beanDataTmp; 
		S_ROLEData returnData=new S_ROLEData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT roleid,rolename,roletype,unitid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_ROLE WHERE  roleid =?");
			statement.setString( 1,beanData.getRoleid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRoleid( resultSet.getString( 1));			returnData.setRolename( resultSet.getString( 2));			returnData.setRoletype( resultSet.getString( 3));			returnData.setUnitid( resultSet.getString( 4));			if (resultSet.getString( 5)==null)				returnData.setSortno(null);			else				returnData.setSortno( new Integer(resultSet.getInt( 5)));			returnData.setCreatetime( resultSet.getString( 6));			returnData.setCreateperson( resultSet.getString( 7));			returnData.setCreateunitid( resultSet.getString( 8));			returnData.setModifytime( resultSet.getString( 9));			returnData.setModifyperson( resultSet.getString( 10));			returnData.setDeleteflag( resultSet.getString( 11));			returnData.setDeptguid( resultSet.getString( 12));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT roleid,rolename,roletype,unitid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_ROLE  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT roleid,rolename,roletype,unitid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_ROLE"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_ROLEData returnData=new S_ROLEData();
				returnData.setRoleid( resultSet.getString( 1));				returnData.setRolename( resultSet.getString( 2));				returnData.setRoletype( resultSet.getString( 3));				returnData.setUnitid( resultSet.getString( 4));				if (resultSet.getString( 5)==null)					returnData.setSortno(null);				else					returnData.setSortno( new Integer(resultSet.getInt( 5)));				returnData.setCreatetime( resultSet.getString( 6));				returnData.setCreateperson( resultSet.getString( 7));				returnData.setCreateunitid( resultSet.getString( 8));				returnData.setModifytime( resultSet.getString( 9));				returnData.setModifyperson( resultSet.getString( 10));				returnData.setDeleteflag( resultSet.getString( 11));				returnData.setDeptguid( resultSet.getString( 12));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT roleid,rolename,roletype,unitid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_ROLE" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_ROLE SET roleid= ? , rolename= ? , roletype= ? , unitid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  roleid  = ?");
			statement.setString( 1,beanData.getRoleid());			statement.setString( 2,beanData.getRolename());			statement.setString( 3,beanData.getRoletype());			statement.setString( 4,beanData.getUnitid());			if (beanData.getSortno()== null)			{				statement.setNull( 5, Types.INTEGER);			}			else			{				statement.setInt( 5, beanData.getSortno().intValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());
			statement.setString( 13,beanData.getRoleid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_ROLE SET roleid= ? , rolename= ? , roletype= ? , unitid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  roleid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_ROLE SET ");
			bufSQL.append("Roleid = " + "'" + nullString(beanData.getRoleid()) + "',");			bufSQL.append("Rolename = " + "'" + nullString(beanData.getRolename()) + "',");			bufSQL.append("Roletype = " + "'" + nullString(beanData.getRoletype()) + "',");			bufSQL.append("Unitid = " + "'" + nullString(beanData.getUnitid()) + "',");			bufSQL.append("Sortno = " + beanData.getSortno().intValue() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Roleid = " + "'" + nullString(beanData.getRoleid()) + "'");
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
		S_ROLEData beanData = (S_ROLEData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_ROLE SET roleid= ? , rolename= ? , roletype= ? , unitid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  roleid  = ?");
			statement.setString( 1,beanData.getRoleid());			statement.setString( 2,beanData.getRolename());			statement.setString( 3,beanData.getRoletype());			statement.setString( 4,beanData.getUnitid());			if (beanData.getSortno()== null)			{				statement.setNull( 5, Types.INTEGER);			}			else			{				statement.setInt( 5, beanData.getSortno().intValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());
			statement.setString( 13,beanData.getRoleid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_ROLE SET roleid= ? , rolename= ? , roletype= ? , unitid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  roleid  = ?"+ e.toString());
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
		S_ROLEData beanData = (S_ROLEData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  roleid  FROM S_ROLE WHERE  roleid =?");
			statement.setString( 1,beanData.getRoleid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  roleid  FROM S_ROLE WHERE  roleid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
