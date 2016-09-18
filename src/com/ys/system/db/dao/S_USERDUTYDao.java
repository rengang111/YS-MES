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
public class S_USERDUTYDao extends BaseAbstractDao
{
	public S_USERDUTYData beanData=new S_USERDUTYData();
	public S_USERDUTYDao()
	{
	}
	public S_USERDUTYDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_USERDUTYData)FindByPrimaryKey(beanData);
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
		S_USERDUTYData beanData = (S_USERDUTYData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_USERDUTY( id,userid,unitid,post,duty,eanblestartdate,eanbleenddate,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getUnitid());			statement.setString( 4,beanData.getPost());			statement.setString( 5,beanData.getDuty());			statement.setString( 6,beanData.getEanblestartdate());			statement.setString( 7,beanData.getEanbleenddate());			if (beanData.getSortno()== null)			{				statement.setNull( 8, Types.INTEGER);			}			else			{				statement.setInt( 8, beanData.getSortno().intValue());			}			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getDeptguid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_USERDUTY( id,userid,unitid,post,duty,eanblestartdate,eanbleenddate,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_USERDUTY( id,userid,unitid,post,duty,eanblestartdate,eanbleenddate,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getUserid()) + "',");			bufSQL.append("'" + nullString(beanData.getUnitid()) + "',");			bufSQL.append("'" + nullString(beanData.getPost()) + "',");			bufSQL.append("'" + nullString(beanData.getDuty()) + "',");			bufSQL.append("'" + nullString(beanData.getEanblestartdate()) + "',");			bufSQL.append("'" + nullString(beanData.getEanbleenddate()) + "',");			bufSQL.append(beanData.getSortno().intValue() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "'");
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
		S_USERDUTYData beanData = (S_USERDUTYData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_USERDUTY( id,userid,unitid,post,duty,eanblestartdate,eanbleenddate,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getUnitid());			statement.setString( 4,beanData.getPost());			statement.setString( 5,beanData.getDuty());			statement.setString( 6,beanData.getEanblestartdate());			statement.setString( 7,beanData.getEanbleenddate());			if (beanData.getSortno()== null)			{				statement.setNull( 8, Types.INTEGER);			}			else			{				statement.setInt( 8, beanData.getSortno().intValue());			}			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getDeptguid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_USERDUTY( id,userid,unitid,post,duty,eanblestartdate,eanbleenddate,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_USERDUTYData beanData = (S_USERDUTYData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_USERDUTY WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_USERDUTY: "+ e.toString());
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
		S_USERDUTYData beanData = (S_USERDUTYData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_USERDUTY WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM S_USERDUTY"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_USERDUTY: "+ e.toString());
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
		S_USERDUTYData beanData = (S_USERDUTYData) beanDataTmp; 
		S_USERDUTYData returnData=new S_USERDUTYData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,userid,unitid,post,duty,eanblestartdate,eanbleenddate,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_USERDUTY WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setUserid( resultSet.getString( 2));			returnData.setUnitid( resultSet.getString( 3));			returnData.setPost( resultSet.getString( 4));			returnData.setDuty( resultSet.getString( 5));			returnData.setEanblestartdate( resultSet.getString( 6));			returnData.setEanbleenddate( resultSet.getString( 7));			if (resultSet.getString( 8)==null)				returnData.setSortno(null);			else				returnData.setSortno( new Integer(resultSet.getInt( 8)));			returnData.setCreatetime( resultSet.getString( 9));			returnData.setCreateperson( resultSet.getString( 10));			returnData.setCreateunitid( resultSet.getString( 11));			returnData.setModifytime( resultSet.getString( 12));			returnData.setModifyperson( resultSet.getString( 13));			returnData.setDeleteflag( resultSet.getString( 14));			returnData.setDeptguid( resultSet.getString( 15));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,userid,unitid,post,duty,eanblestartdate,eanbleenddate,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_USERDUTY  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,userid,unitid,post,duty,eanblestartdate,eanbleenddate,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_USERDUTY"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_USERDUTYData returnData=new S_USERDUTYData();
				returnData.setId( resultSet.getString( 1));				returnData.setUserid( resultSet.getString( 2));				returnData.setUnitid( resultSet.getString( 3));				returnData.setPost( resultSet.getString( 4));				returnData.setDuty( resultSet.getString( 5));				returnData.setEanblestartdate( resultSet.getString( 6));				returnData.setEanbleenddate( resultSet.getString( 7));				if (resultSet.getString( 8)==null)					returnData.setSortno(null);				else					returnData.setSortno( new Integer(resultSet.getInt( 8)));				returnData.setCreatetime( resultSet.getString( 9));				returnData.setCreateperson( resultSet.getString( 10));				returnData.setCreateunitid( resultSet.getString( 11));				returnData.setModifytime( resultSet.getString( 12));				returnData.setModifyperson( resultSet.getString( 13));				returnData.setDeleteflag( resultSet.getString( 14));				returnData.setDeptguid( resultSet.getString( 15));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,userid,unitid,post,duty,eanblestartdate,eanbleenddate,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_USERDUTY" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_USERDUTY SET id= ? , userid= ? , unitid= ? , post= ? , duty= ? , eanblestartdate= ? , eanbleenddate= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getUnitid());			statement.setString( 4,beanData.getPost());			statement.setString( 5,beanData.getDuty());			statement.setString( 6,beanData.getEanblestartdate());			statement.setString( 7,beanData.getEanbleenddate());			if (beanData.getSortno()== null)			{				statement.setNull( 8, Types.INTEGER);			}			else			{				statement.setInt( 8, beanData.getSortno().intValue());			}			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getDeptguid());
			statement.setString( 16,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_USERDUTY SET id= ? , userid= ? , unitid= ? , post= ? , duty= ? , eanblestartdate= ? , eanbleenddate= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_USERDUTY SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "',");			bufSQL.append("Unitid = " + "'" + nullString(beanData.getUnitid()) + "',");			bufSQL.append("Post = " + "'" + nullString(beanData.getPost()) + "',");			bufSQL.append("Duty = " + "'" + nullString(beanData.getDuty()) + "',");			bufSQL.append("Eanblestartdate = " + "'" + nullString(beanData.getEanblestartdate()) + "',");			bufSQL.append("Eanbleenddate = " + "'" + nullString(beanData.getEanbleenddate()) + "',");			bufSQL.append("Sortno = " + beanData.getSortno().intValue() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "'");
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
		S_USERDUTYData beanData = (S_USERDUTYData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_USERDUTY SET id= ? , userid= ? , unitid= ? , post= ? , duty= ? , eanblestartdate= ? , eanbleenddate= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getUserid());			statement.setString( 3,beanData.getUnitid());			statement.setString( 4,beanData.getPost());			statement.setString( 5,beanData.getDuty());			statement.setString( 6,beanData.getEanblestartdate());			statement.setString( 7,beanData.getEanbleenddate());			if (beanData.getSortno()== null)			{				statement.setNull( 8, Types.INTEGER);			}			else			{				statement.setInt( 8, beanData.getSortno().intValue());			}			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getDeptguid());
			statement.setString( 16,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_USERDUTY SET id= ? , userid= ? , unitid= ? , post= ? , duty= ? , eanblestartdate= ? , eanbleenddate= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  id  = ?"+ e.toString());
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
		S_USERDUTYData beanData = (S_USERDUTYData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM S_USERDUTY WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM S_USERDUTY WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
