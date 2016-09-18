package com.ys.system.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.system.db.data.*;
import com.ys.util.basedao.BaseAbstractDao;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class YW_PROJ_IMPRODao extends BaseAbstractDao
{
	public YW_PROJ_IMPROData beanData=new YW_PROJ_IMPROData();
	public YW_PROJ_IMPRODao()
	{
	}
	public YW_PROJ_IMPRODao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROJ_IMPROData)FindByPrimaryKey(beanData);
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
		YW_PROJ_IMPROData beanData = (YW_PROJ_IMPROData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROJ_IMPRO( id,proj_id,arose_date,solve_date,woring_desc,solve_prog,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getArose_date());			statement.setString( 4,beanData.getSolve_date());			statement.setString( 5,beanData.getWoring_desc());			statement.setString( 6,beanData.getSolve_prog());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROJ_IMPRO( id,proj_id,arose_date,solve_date,woring_desc,solve_prog,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROJ_IMPRO( id,proj_id,arose_date,solve_date,woring_desc,solve_prog,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append(beanData.getProj_id() + ",");			bufSQL.append("'" + nullString(beanData.getArose_date()) + "',");			bufSQL.append("'" + nullString(beanData.getSolve_date()) + "',");			bufSQL.append("'" + nullString(beanData.getWoring_desc()) + "',");			bufSQL.append("'" + nullString(beanData.getSolve_prog()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROJ_IMPROData beanData = (YW_PROJ_IMPROData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROJ_IMPRO( id,proj_id,arose_date,solve_date,woring_desc,solve_prog,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getArose_date());			statement.setString( 4,beanData.getSolve_date());			statement.setString( 5,beanData.getWoring_desc());			statement.setString( 6,beanData.getSolve_prog());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROJ_IMPRO( id,proj_id,arose_date,solve_date,woring_desc,solve_prog,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROJ_IMPROData beanData = (YW_PROJ_IMPROData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROJ_IMPRO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROJ_IMPRO: "+ e.toString());
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
		YW_PROJ_IMPROData beanData = (YW_PROJ_IMPROData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROJ_IMPRO WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROJ_IMPRO"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROJ_IMPRO: "+ e.toString());
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
		YW_PROJ_IMPROData beanData = (YW_PROJ_IMPROData) beanDataTmp; 
		YW_PROJ_IMPROData returnData=new YW_PROJ_IMPROData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,proj_id,arose_date,solve_date,woring_desc,solve_prog,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_IMPRO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProj_id( resultSet.getInt( 2));			returnData.setArose_date( resultSet.getString( 3));			returnData.setSolve_date( resultSet.getString( 4));			returnData.setWoring_desc( resultSet.getString( 5));			returnData.setSolve_prog( resultSet.getString( 6));			returnData.setCreatetime( resultSet.getString( 7));			returnData.setCreateperson( resultSet.getString( 8));			returnData.setCreateunitid( resultSet.getString( 9));			returnData.setModifytime( resultSet.getString( 10));			returnData.setModifyperson( resultSet.getString( 11));			returnData.setDeleteflag( resultSet.getString( 12));			returnData.setDeptguid( resultSet.getString( 13));			returnData.setFormid( resultSet.getString( 14));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,proj_id,arose_date,solve_date,woring_desc,solve_prog,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_IMPRO  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,proj_id,arose_date,solve_date,woring_desc,solve_prog,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_IMPRO"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROJ_IMPROData returnData=new YW_PROJ_IMPROData();
				returnData.setId( resultSet.getString( 1));				returnData.setProj_id( resultSet.getInt( 2));				returnData.setArose_date( resultSet.getString( 3));				returnData.setSolve_date( resultSet.getString( 4));				returnData.setWoring_desc( resultSet.getString( 5));				returnData.setSolve_prog( resultSet.getString( 6));				returnData.setCreatetime( resultSet.getString( 7));				returnData.setCreateperson( resultSet.getString( 8));				returnData.setCreateunitid( resultSet.getString( 9));				returnData.setModifytime( resultSet.getString( 10));				returnData.setModifyperson( resultSet.getString( 11));				returnData.setDeleteflag( resultSet.getString( 12));				returnData.setDeptguid( resultSet.getString( 13));				returnData.setFormid( resultSet.getString( 14));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,proj_id,arose_date,solve_date,woring_desc,solve_prog,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_IMPRO" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROJ_IMPRO SET id= ? , proj_id= ? , arose_date= ? , solve_date= ? , woring_desc= ? , solve_prog= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getArose_date());			statement.setString( 4,beanData.getSolve_date());			statement.setString( 5,beanData.getWoring_desc());			statement.setString( 6,beanData.getSolve_prog());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			statement.setString( 15,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROJ_IMPRO SET id= ? , proj_id= ? , arose_date= ? , solve_date= ? , woring_desc= ? , solve_prog= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROJ_IMPRO SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Proj_id = " + beanData.getProj_id() + ",");			bufSQL.append("Arose_date = " + "'" + nullString(beanData.getArose_date()) + "',");			bufSQL.append("Solve_date = " + "'" + nullString(beanData.getSolve_date()) + "',");			bufSQL.append("Woring_desc = " + "'" + nullString(beanData.getWoring_desc()) + "',");			bufSQL.append("Solve_prog = " + "'" + nullString(beanData.getSolve_prog()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROJ_IMPROData beanData = (YW_PROJ_IMPROData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROJ_IMPRO SET id= ? , proj_id= ? , arose_date= ? , solve_date= ? , woring_desc= ? , solve_prog= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getArose_date());			statement.setString( 4,beanData.getSolve_date());			statement.setString( 5,beanData.getWoring_desc());			statement.setString( 6,beanData.getSolve_prog());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			statement.setString( 15,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROJ_IMPRO SET id= ? , proj_id= ? , arose_date= ? , solve_date= ? , woring_desc= ? , solve_prog= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PROJ_IMPROData beanData = (YW_PROJ_IMPROData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PROJ_IMPRO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PROJ_IMPRO WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
