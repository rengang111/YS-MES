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
public class YW_AUT_PRJDao extends BaseAbstractDao
{
	public YW_AUT_PRJData beanData=new YW_AUT_PRJData();
	public YW_AUT_PRJDao()
	{
	}
	public YW_AUT_PRJDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_AUT_PRJData)FindByPrimaryKey(beanData);
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
		YW_AUT_PRJData beanData = (YW_AUT_PRJData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_AUT_PRJ( id,aut_id,prj_name,cost,apply_date,proposer,get_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getAut_id());			statement.setString( 3,beanData.getPrj_name());			if (beanData.getCost()== null)			{				statement.setNull( 4, Types.FLOAT);			}			else			{				statement.setFloat( 4, beanData.getCost().floatValue());			}			statement.setString( 5,beanData.getApply_date());			statement.setString( 6,beanData.getProposer());			statement.setString( 7,beanData.getGet_date());			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_AUT_PRJ( id,aut_id,prj_name,cost,apply_date,proposer,get_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_AUT_PRJ( id,aut_id,prj_name,cost,apply_date,proposer,get_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append(beanData.getAut_id() + ",");			bufSQL.append("'" + nullString(beanData.getPrj_name()) + "',");			bufSQL.append(beanData.getCost() + ",");			bufSQL.append("'" + nullString(beanData.getApply_date()) + "',");			bufSQL.append("'" + nullString(beanData.getProposer()) + "',");			bufSQL.append("'" + nullString(beanData.getGet_date()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_AUT_PRJData beanData = (YW_AUT_PRJData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_AUT_PRJ( id,aut_id,prj_name,cost,apply_date,proposer,get_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getAut_id());			statement.setString( 3,beanData.getPrj_name());			if (beanData.getCost()== null)			{				statement.setNull( 4, Types.FLOAT);			}			else			{				statement.setFloat( 4, beanData.getCost().floatValue());			}			statement.setString( 5,beanData.getApply_date());			statement.setString( 6,beanData.getProposer());			statement.setString( 7,beanData.getGet_date());			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_AUT_PRJ( id,aut_id,prj_name,cost,apply_date,proposer,get_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_AUT_PRJData beanData = (YW_AUT_PRJData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_AUT_PRJ WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_AUT_PRJ: "+ e.toString());
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
		YW_AUT_PRJData beanData = (YW_AUT_PRJData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_AUT_PRJ WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_AUT_PRJ"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_AUT_PRJ: "+ e.toString());
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
		YW_AUT_PRJData beanData = (YW_AUT_PRJData) beanDataTmp; 
		YW_AUT_PRJData returnData=new YW_AUT_PRJData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,aut_id,prj_name,cost,apply_date,proposer,get_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_AUT_PRJ WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setAut_id( resultSet.getInt( 2));			returnData.setPrj_name( resultSet.getString( 3));			if (resultSet.getString( 4)==null)				returnData.setCost(null);			else				returnData.setCost( new Float(resultSet.getFloat( 4)));			returnData.setApply_date( resultSet.getString( 5));			returnData.setProposer( resultSet.getString( 6));			returnData.setGet_date( resultSet.getString( 7));			returnData.setCreatetime( resultSet.getString( 8));			returnData.setCreateperson( resultSet.getString( 9));			returnData.setCreateunitid( resultSet.getString( 10));			returnData.setModifytime( resultSet.getString( 11));			returnData.setModifyperson( resultSet.getString( 12));			returnData.setDeleteflag( resultSet.getString( 13));			returnData.setDeptguid( resultSet.getString( 14));			returnData.setFormid( resultSet.getString( 15));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,aut_id,prj_name,cost,apply_date,proposer,get_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_AUT_PRJ  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,aut_id,prj_name,cost,apply_date,proposer,get_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_AUT_PRJ"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_AUT_PRJData returnData=new YW_AUT_PRJData();
				returnData.setId( resultSet.getString( 1));				returnData.setAut_id( resultSet.getInt( 2));				returnData.setPrj_name( resultSet.getString( 3));				if (resultSet.getString( 4)==null)					returnData.setCost(null);				else					returnData.setCost( new Float(resultSet.getFloat( 4)));				returnData.setApply_date( resultSet.getString( 5));				returnData.setProposer( resultSet.getString( 6));				returnData.setGet_date( resultSet.getString( 7));				returnData.setCreatetime( resultSet.getString( 8));				returnData.setCreateperson( resultSet.getString( 9));				returnData.setCreateunitid( resultSet.getString( 10));				returnData.setModifytime( resultSet.getString( 11));				returnData.setModifyperson( resultSet.getString( 12));				returnData.setDeleteflag( resultSet.getString( 13));				returnData.setDeptguid( resultSet.getString( 14));				returnData.setFormid( resultSet.getString( 15));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,aut_id,prj_name,cost,apply_date,proposer,get_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_AUT_PRJ" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_AUT_PRJ SET id= ? , aut_id= ? , prj_name= ? , cost= ? , apply_date= ? , proposer= ? , get_date= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getAut_id());			statement.setString( 3,beanData.getPrj_name());			if (beanData.getCost()== null)			{				statement.setNull( 4, Types.FLOAT);			}			else			{				statement.setFloat( 4, beanData.getCost().floatValue());			}			statement.setString( 6,beanData.getApply_date());			statement.setString( 7,beanData.getProposer());			statement.setString( 8,beanData.getGet_date());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getDeptguid());			statement.setString( 16,beanData.getFormid());
			statement.setString( 17,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_AUT_PRJ SET id= ? , aut_id= ? , prj_name= ? , cost= ? , apply_date= ? , proposer= ? , get_date= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_AUT_PRJ SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Aut_id = " + beanData.getAut_id() + ",");			bufSQL.append("Prj_name = " + "'" + nullString(beanData.getPrj_name()) + "',");			bufSQL.append("Cost = " + beanData.getCost() + ",");			bufSQL.append("Apply_date = " + "'" + nullString(beanData.getApply_date()) + "',");			bufSQL.append("Proposer = " + "'" + nullString(beanData.getProposer()) + "',");			bufSQL.append("Get_date = " + "'" + nullString(beanData.getGet_date()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_AUT_PRJData beanData = (YW_AUT_PRJData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_AUT_PRJ SET id= ? , aut_id= ? , prj_name= ? , cost= ? , apply_date= ? , proposer= ? , get_date= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getAut_id());			statement.setString( 3,beanData.getPrj_name());			if (beanData.getCost()== null)			{				statement.setNull( 4, Types.FLOAT);			}			else			{				statement.setFloat( 4, beanData.getCost().floatValue());			}			statement.setString( 5,beanData.getApply_date());			statement.setString( 6,beanData.getProposer());			statement.setString( 7,beanData.getGet_date());			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			statement.setString( 16,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_AUT_PRJ SET id= ? , aut_id= ? , prj_name= ? , cost= ? , apply_date= ? , proposer= ? , get_date= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_AUT_PRJData beanData = (YW_AUT_PRJData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_AUT_PRJ WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_AUT_PRJ WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
