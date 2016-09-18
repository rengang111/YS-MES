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
public class YW_PROJ_DOCDao extends BaseAbstractDao
{
	public YW_PROJ_DOCData beanData=new YW_PROJ_DOCData();
	public YW_PROJ_DOCDao()
	{
	}
	public YW_PROJ_DOCDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROJ_DOCData)FindByPrimaryKey(beanData);
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
		YW_PROJ_DOCData beanData = (YW_PROJ_DOCData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROJ_DOC( id,proj_id,doc_code,version,parts_name,erp_code,mate_req,mach_req,save_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getDoc_code());			statement.setString( 4,beanData.getVersion());			statement.setString( 5,beanData.getParts_name());			statement.setString( 6,beanData.getErp_code());			statement.setString( 7,beanData.getMate_req());			statement.setString( 8,beanData.getMach_req());			statement.setString( 9,beanData.getSave_path());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROJ_DOC( id,proj_id,doc_code,version,parts_name,erp_code,mate_req,mach_req,save_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROJ_DOC( id,proj_id,doc_code,version,parts_name,erp_code,mate_req,mach_req,save_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append(beanData.getProj_id() + ",");			bufSQL.append("'" + nullString(beanData.getDoc_code()) + "',");			bufSQL.append("'" + nullString(beanData.getVersion()) + "',");			bufSQL.append("'" + nullString(beanData.getParts_name()) + "',");			bufSQL.append("'" + nullString(beanData.getErp_code()) + "',");			bufSQL.append("'" + nullString(beanData.getMate_req()) + "',");			bufSQL.append("'" + nullString(beanData.getMach_req()) + "',");			bufSQL.append("'" + nullString(beanData.getSave_path()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROJ_DOCData beanData = (YW_PROJ_DOCData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROJ_DOC( id,proj_id,doc_code,version,parts_name,erp_code,mate_req,mach_req,save_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getDoc_code());			statement.setString( 4,beanData.getVersion());			statement.setString( 5,beanData.getParts_name());			statement.setString( 6,beanData.getErp_code());			statement.setString( 7,beanData.getMate_req());			statement.setString( 8,beanData.getMach_req());			statement.setString( 9,beanData.getSave_path());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROJ_DOC( id,proj_id,doc_code,version,parts_name,erp_code,mate_req,mach_req,save_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROJ_DOCData beanData = (YW_PROJ_DOCData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROJ_DOC WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROJ_DOC: "+ e.toString());
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
		YW_PROJ_DOCData beanData = (YW_PROJ_DOCData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROJ_DOC WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROJ_DOC"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROJ_DOC: "+ e.toString());
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
		YW_PROJ_DOCData beanData = (YW_PROJ_DOCData) beanDataTmp; 
		YW_PROJ_DOCData returnData=new YW_PROJ_DOCData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,proj_id,doc_code,version,parts_name,erp_code,mate_req,mach_req,save_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_DOC WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProj_id( resultSet.getInt( 2));			returnData.setDoc_code( resultSet.getString( 3));			returnData.setVersion( resultSet.getString( 4));			returnData.setParts_name( resultSet.getString( 5));			returnData.setErp_code( resultSet.getString( 6));			returnData.setMate_req( resultSet.getString( 7));			returnData.setMach_req( resultSet.getString( 8));			returnData.setSave_path( resultSet.getString( 9));			returnData.setCreatetime( resultSet.getString( 10));			returnData.setCreateperson( resultSet.getString( 11));			returnData.setCreateunitid( resultSet.getString( 12));			returnData.setModifytime( resultSet.getString( 13));			returnData.setModifyperson( resultSet.getString( 14));			returnData.setDeleteflag( resultSet.getString( 15));			returnData.setDeptguid( resultSet.getString( 16));			returnData.setFormid( resultSet.getString( 17));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,proj_id,doc_code,version,parts_name,erp_code,mate_req,mach_req,save_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_DOC  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,proj_id,doc_code,version,parts_name,erp_code,mate_req,mach_req,save_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_DOC"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROJ_DOCData returnData=new YW_PROJ_DOCData();
				returnData.setId( resultSet.getString( 1));				returnData.setProj_id( resultSet.getInt( 2));				returnData.setDoc_code( resultSet.getString( 3));				returnData.setVersion( resultSet.getString( 4));				returnData.setParts_name( resultSet.getString( 5));				returnData.setErp_code( resultSet.getString( 6));				returnData.setMate_req( resultSet.getString( 7));				returnData.setMach_req( resultSet.getString( 8));				returnData.setSave_path( resultSet.getString( 9));				returnData.setCreatetime( resultSet.getString( 10));				returnData.setCreateperson( resultSet.getString( 11));				returnData.setCreateunitid( resultSet.getString( 12));				returnData.setModifytime( resultSet.getString( 13));				returnData.setModifyperson( resultSet.getString( 14));				returnData.setDeleteflag( resultSet.getString( 15));				returnData.setDeptguid( resultSet.getString( 16));				returnData.setFormid( resultSet.getString( 17));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,proj_id,doc_code,version,parts_name,erp_code,mate_req,mach_req,save_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_DOC" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROJ_DOC SET id= ? , proj_id= ? , doc_code= ? , version= ? , parts_name= ? , erp_code= ? , mate_req= ? , mach_req= ? , save_path= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getDoc_code());			statement.setString( 4,beanData.getVersion());			statement.setString( 5,beanData.getParts_name());			statement.setString( 6,beanData.getErp_code());			statement.setString( 7,beanData.getMate_req());			statement.setString( 8,beanData.getMach_req());			statement.setString( 9,beanData.getSave_path());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROJ_DOC SET id= ? , proj_id= ? , doc_code= ? , version= ? , parts_name= ? , erp_code= ? , mate_req= ? , mach_req= ? , save_path= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROJ_DOC SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Proj_id = " + beanData.getProj_id() + ",");			bufSQL.append("Doc_code = " + "'" + nullString(beanData.getDoc_code()) + "',");			bufSQL.append("Version = " + "'" + nullString(beanData.getVersion()) + "',");			bufSQL.append("Parts_name = " + "'" + nullString(beanData.getParts_name()) + "',");			bufSQL.append("Erp_code = " + "'" + nullString(beanData.getErp_code()) + "',");			bufSQL.append("Mate_req = " + "'" + nullString(beanData.getMate_req()) + "',");			bufSQL.append("Mach_req = " + "'" + nullString(beanData.getMach_req()) + "',");			bufSQL.append("Save_path = " + "'" + nullString(beanData.getSave_path()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROJ_DOCData beanData = (YW_PROJ_DOCData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROJ_DOC SET id= ? , proj_id= ? , doc_code= ? , version= ? , parts_name= ? , erp_code= ? , mate_req= ? , mach_req= ? , save_path= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			statement.setString( 3,beanData.getDoc_code());			statement.setString( 4,beanData.getVersion());			statement.setString( 5,beanData.getParts_name());			statement.setString( 6,beanData.getErp_code());			statement.setString( 7,beanData.getMate_req());			statement.setString( 8,beanData.getMach_req());			statement.setString( 9,beanData.getSave_path());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROJ_DOC SET id= ? , proj_id= ? , doc_code= ? , version= ? , parts_name= ? , erp_code= ? , mate_req= ? , mach_req= ? , save_path= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PROJ_DOCData beanData = (YW_PROJ_DOCData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PROJ_DOC WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PROJ_DOC WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
