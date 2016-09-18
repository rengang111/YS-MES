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
public class YW_FTY_INSP_INFODao extends BaseAbstractDao
{
	public YW_FTY_INSP_INFOData beanData=new YW_FTY_INSP_INFOData();
	public YW_FTY_INSP_INFODao()
	{
	}
	public YW_FTY_INSP_INFODao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_FTY_INSP_INFOData)FindByPrimaryKey(beanData);
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
		YW_FTY_INSP_INFOData beanData = (YW_FTY_INSP_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_FTY_INSP_INFO( id,fact_name,inst_name,describes,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getFact_name());			statement.setString( 3,beanData.getInst_name());			statement.setString( 4,beanData.getDescribes());			statement.setString( 5,beanData.getCreatetime());			statement.setString( 6,beanData.getCreateperson());			statement.setString( 7,beanData.getCreateunitid());			statement.setString( 8,beanData.getModifytime());			statement.setString( 9,beanData.getModifyperson());			statement.setString( 10,beanData.getDeleteflag());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_FTY_INSP_INFO( id,fact_name,inst_name,describes,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_FTY_INSP_INFO( id,fact_name,inst_name,describes,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getFact_name()) + "',");			bufSQL.append("'" + nullString(beanData.getInst_name()) + "',");			bufSQL.append("'" + nullString(beanData.getDescribes()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_FTY_INSP_INFOData beanData = (YW_FTY_INSP_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_FTY_INSP_INFO( id,fact_name,inst_name,describes,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getFact_name());			statement.setString( 3,beanData.getInst_name());			statement.setString( 4,beanData.getDescribes());			statement.setString( 5,beanData.getCreatetime());			statement.setString( 6,beanData.getCreateperson());			statement.setString( 7,beanData.getCreateunitid());			statement.setString( 8,beanData.getModifytime());			statement.setString( 9,beanData.getModifyperson());			statement.setString( 10,beanData.getDeleteflag());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_FTY_INSP_INFO( id,fact_name,inst_name,describes,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_FTY_INSP_INFOData beanData = (YW_FTY_INSP_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_FTY_INSP_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_FTY_INSP_INFO: "+ e.toString());
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
		YW_FTY_INSP_INFOData beanData = (YW_FTY_INSP_INFOData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_FTY_INSP_INFO WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_FTY_INSP_INFO"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_FTY_INSP_INFO: "+ e.toString());
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
		YW_FTY_INSP_INFOData beanData = (YW_FTY_INSP_INFOData) beanDataTmp; 
		YW_FTY_INSP_INFOData returnData=new YW_FTY_INSP_INFOData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,fact_name,inst_name,describes,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_FTY_INSP_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setFact_name( resultSet.getString( 2));			returnData.setInst_name( resultSet.getString( 3));			returnData.setDescribes( resultSet.getString( 4));			returnData.setCreatetime( resultSet.getString( 5));			returnData.setCreateperson( resultSet.getString( 6));			returnData.setCreateunitid( resultSet.getString( 7));			returnData.setModifytime( resultSet.getString( 8));			returnData.setModifyperson( resultSet.getString( 9));			returnData.setDeleteflag( resultSet.getString( 10));			returnData.setDeptguid( resultSet.getString( 11));			returnData.setFormid( resultSet.getString( 12));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,fact_name,inst_name,describes,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_FTY_INSP_INFO  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,fact_name,inst_name,describes,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_FTY_INSP_INFO"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_FTY_INSP_INFOData returnData=new YW_FTY_INSP_INFOData();
				returnData.setId( resultSet.getString( 1));				returnData.setFact_name( resultSet.getString( 2));				returnData.setInst_name( resultSet.getString( 3));				returnData.setDescribes( resultSet.getString( 4));				returnData.setCreatetime( resultSet.getString( 5));				returnData.setCreateperson( resultSet.getString( 6));				returnData.setCreateunitid( resultSet.getString( 7));				returnData.setModifytime( resultSet.getString( 8));				returnData.setModifyperson( resultSet.getString( 9));				returnData.setDeleteflag( resultSet.getString( 10));				returnData.setDeptguid( resultSet.getString( 11));				returnData.setFormid( resultSet.getString( 12));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,fact_name,inst_name,describes,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_FTY_INSP_INFO" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_FTY_INSP_INFO SET id= ? , fact_name= ? , inst_name= ? , describes= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getFact_name());			statement.setString( 3,beanData.getInst_name());			statement.setString( 4,beanData.getDescribes());			statement.setString( 5,beanData.getCreatetime());			statement.setString( 6,beanData.getCreateperson());			statement.setString( 7,beanData.getCreateunitid());			statement.setString( 8,beanData.getModifytime());			statement.setString( 9,beanData.getModifyperson());			statement.setString( 10,beanData.getDeleteflag());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getFormid());
			statement.setString( 13,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_FTY_INSP_INFO SET id= ? , fact_name= ? , inst_name= ? , describes= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_FTY_INSP_INFO SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Fact_name = " + "'" + nullString(beanData.getFact_name()) + "',");			bufSQL.append("Inst_name = " + "'" + nullString(beanData.getInst_name()) + "',");			bufSQL.append("Describes = " + "'" + nullString(beanData.getDescribes()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_FTY_INSP_INFOData beanData = (YW_FTY_INSP_INFOData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_FTY_INSP_INFO SET id= ? , fact_name= ? , inst_name= ? , describes= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getFact_name());			statement.setString( 3,beanData.getInst_name());			statement.setString( 4,beanData.getDescribes());			statement.setString( 5,beanData.getCreatetime());			statement.setString( 6,beanData.getCreateperson());			statement.setString( 7,beanData.getCreateunitid());			statement.setString( 8,beanData.getModifytime());			statement.setString( 9,beanData.getModifyperson());			statement.setString( 10,beanData.getDeleteflag());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getFormid());
			statement.setString( 13,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_FTY_INSP_INFO SET id= ? , fact_name= ? , inst_name= ? , describes= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_FTY_INSP_INFOData beanData = (YW_FTY_INSP_INFOData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_FTY_INSP_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_FTY_INSP_INFO WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
