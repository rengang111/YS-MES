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
public class YW_PROJ_TRIALDao extends BaseAbstractDao
{
	public YW_PROJ_TRIALData beanData=new YW_PROJ_TRIALData();
	public YW_PROJ_TRIALDao()
	{
	}
	public YW_PROJ_TRIALDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROJ_TRIALData)FindByPrimaryKey(beanData);
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
		YW_PROJ_TRIALData beanData = (YW_PROJ_TRIALData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROJ_TRIAL( id,proj_id,report_file,file_name,save_dir,file_type,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getReport_file());			statement.setString( 4,beanData.getFile_name());			statement.setString( 5,beanData.getSave_dir());			statement.setString( 6,beanData.getFile_type());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROJ_TRIAL( id,proj_id,report_file,file_name,save_dir,file_type,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROJ_TRIAL( id,proj_id,report_file,file_name,save_dir,file_type,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append(beanData.getProj_id() + ",");			bufSQL.append("'" + nullString(beanData.getReport_file()) + "',");			bufSQL.append("'" + nullString(beanData.getFile_name()) + "',");			bufSQL.append("'" + nullString(beanData.getSave_dir()) + "',");			bufSQL.append("'" + nullString(beanData.getFile_type()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROJ_TRIALData beanData = (YW_PROJ_TRIALData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROJ_TRIAL( id,proj_id,report_file,file_name,save_dir,file_type,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getReport_file());			statement.setString( 4,beanData.getFile_name());			statement.setString( 5,beanData.getSave_dir());			statement.setString( 6,beanData.getFile_type());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROJ_TRIAL( id,proj_id,report_file,file_name,save_dir,file_type,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROJ_TRIALData beanData = (YW_PROJ_TRIALData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROJ_TRIAL WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROJ_TRIAL: "+ e.toString());
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
		YW_PROJ_TRIALData beanData = (YW_PROJ_TRIALData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROJ_TRIAL WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROJ_TRIAL"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROJ_TRIAL: "+ e.toString());
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
		YW_PROJ_TRIALData beanData = (YW_PROJ_TRIALData) beanDataTmp; 
		YW_PROJ_TRIALData returnData=new YW_PROJ_TRIALData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,proj_id,report_file,file_name,save_dir,file_type,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_TRIAL WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProj_id( resultSet.getInt( 2));			returnData.setReport_file( resultSet.getString( 3));			returnData.setFile_name( resultSet.getString( 4));			returnData.setSave_dir( resultSet.getString( 5));			returnData.setFile_type( resultSet.getString( 6));			returnData.setCreatetime( resultSet.getString( 7));			returnData.setCreateperson( resultSet.getString( 8));			returnData.setCreateunitid( resultSet.getString( 9));			returnData.setModifytime( resultSet.getString( 10));			returnData.setModifyperson( resultSet.getString( 11));			returnData.setDeleteflag( resultSet.getString( 12));			returnData.setDeptguid( resultSet.getString( 13));			returnData.setFormid( resultSet.getString( 14));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,proj_id,report_file,file_name,save_dir,file_type,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_TRIAL  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,proj_id,report_file,file_name,save_dir,file_type,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_TRIAL"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROJ_TRIALData returnData=new YW_PROJ_TRIALData();
				returnData.setId( resultSet.getString( 1));				returnData.setProj_id( resultSet.getInt( 2));				returnData.setReport_file( resultSet.getString( 3));				returnData.setFile_name( resultSet.getString( 4));				returnData.setSave_dir( resultSet.getString( 5));				returnData.setFile_type( resultSet.getString( 6));				returnData.setCreatetime( resultSet.getString( 7));				returnData.setCreateperson( resultSet.getString( 8));				returnData.setCreateunitid( resultSet.getString( 9));				returnData.setModifytime( resultSet.getString( 10));				returnData.setModifyperson( resultSet.getString( 11));				returnData.setDeleteflag( resultSet.getString( 12));				returnData.setDeptguid( resultSet.getString( 13));				returnData.setFormid( resultSet.getString( 14));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,proj_id,report_file,file_name,save_dir,file_type,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_TRIAL" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROJ_TRIAL SET id= ? , proj_id= ? , report_file= ? , file_name= ? , save_dir= ? , file_type= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getReport_file());			statement.setString( 4,beanData.getFile_name());			statement.setString( 5,beanData.getSave_dir());			statement.setString( 6,beanData.getFile_type());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			statement.setString( 15,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROJ_TRIAL SET id= ? , proj_id= ? , report_file= ? , file_name= ? , save_dir= ? , file_type= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROJ_TRIAL SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Proj_id = " + beanData.getProj_id() + ",");			bufSQL.append("Report_file = " + "'" + nullString(beanData.getReport_file()) + "',");			bufSQL.append("File_name = " + "'" + nullString(beanData.getFile_name()) + "',");			bufSQL.append("Save_dir = " + "'" + nullString(beanData.getSave_dir()) + "',");			bufSQL.append("File_type = " + "'" + nullString(beanData.getFile_type()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROJ_TRIALData beanData = (YW_PROJ_TRIALData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROJ_TRIAL SET id= ? , proj_id= ? , report_file= ? , file_name= ? , save_dir= ? , file_type= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getReport_file());			statement.setString( 4,beanData.getFile_name());			statement.setString( 5,beanData.getSave_dir());			statement.setString( 6,beanData.getFile_type());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			statement.setString( 15,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROJ_TRIAL SET id= ? , proj_id= ? , report_file= ? , file_name= ? , save_dir= ? , file_type= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PROJ_TRIALData beanData = (YW_PROJ_TRIALData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PROJ_TRIAL WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PROJ_TRIAL WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
