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
public class YW_PROCESSDao extends BaseAbstractDao
{
	public YW_PROCESSData beanData=new YW_PROCESSData();
	public YW_PROCESSDao()
	{
	}
	public YW_PROCESSDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROCESSData)FindByPrimaryKey(beanData);
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
		YW_PROCESSData beanData = (YW_PROCESSData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROCESS( id,prod_code,code,name,operations,yield,wages,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getCode());			statement.setString( 4,beanData.getName());			statement.setInt( 5,beanData.getOperations());			if (beanData.getYield()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getYield().floatValue());			}			if (beanData.getWages()== null)			{				statement.setNull( 7, Types.FLOAT);			}			else			{				statement.setFloat( 7, beanData.getWages().floatValue());			}			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROCESS( id,prod_code,code,name,operations,yield,wages,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROCESS( id,prod_code,code,name,operations,yield,wages,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getProd_code()) + "',");			bufSQL.append("'" + nullString(beanData.getCode()) + "',");			bufSQL.append("'" + nullString(beanData.getName()) + "',");			bufSQL.append(beanData.getOperations() + ",");			bufSQL.append(beanData.getYield() + ",");			bufSQL.append(beanData.getWages() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROCESSData beanData = (YW_PROCESSData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROCESS( id,prod_code,code,name,operations,yield,wages,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getCode());			statement.setString( 4,beanData.getName());			statement.setInt( 5,beanData.getOperations());			if (beanData.getYield()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getYield().floatValue());			}			if (beanData.getWages()== null)			{				statement.setNull( 7, Types.FLOAT);			}			else			{				statement.setFloat( 7, beanData.getWages().floatValue());			}			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROCESS( id,prod_code,code,name,operations,yield,wages,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROCESSData beanData = (YW_PROCESSData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROCESS WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROCESS: "+ e.toString());
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
		YW_PROCESSData beanData = (YW_PROCESSData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROCESS WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROCESS"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROCESS: "+ e.toString());
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
		YW_PROCESSData beanData = (YW_PROCESSData) beanDataTmp; 
		YW_PROCESSData returnData=new YW_PROCESSData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,prod_code,code,name,operations,yield,wages,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROCESS WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProd_code( resultSet.getString( 2));			returnData.setCode( resultSet.getString( 3));			returnData.setName( resultSet.getString( 4));			returnData.setOperations( resultSet.getInt( 5));			if (resultSet.getString( 6)==null)				returnData.setYield(null);			else				returnData.setYield( new Float(resultSet.getFloat( 6)));			if (resultSet.getString( 7)==null)				returnData.setWages(null);			else				returnData.setWages( new Float(resultSet.getFloat( 7)));			returnData.setCreatetime( resultSet.getString( 8));			returnData.setCreateperson( resultSet.getString( 9));			returnData.setCreateunitid( resultSet.getString( 10));			returnData.setModifytime( resultSet.getString( 11));			returnData.setModifyperson( resultSet.getString( 12));			returnData.setDeleteflag( resultSet.getString( 13));			returnData.setDeptguid( resultSet.getString( 14));			returnData.setFormid( resultSet.getString( 15));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,prod_code,code,name,operations,yield,wages,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROCESS  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,prod_code,code,name,operations,yield,wages,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROCESS"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROCESSData returnData=new YW_PROCESSData();
				returnData.setId( resultSet.getString( 1));				returnData.setProd_code( resultSet.getString( 2));				returnData.setCode( resultSet.getString( 3));				returnData.setName( resultSet.getString( 4));				returnData.setOperations( resultSet.getInt( 5));				if (resultSet.getString( 6)==null)					returnData.setYield(null);				else					returnData.setYield( new Float(resultSet.getFloat( 6)));				if (resultSet.getString( 7)==null)					returnData.setWages(null);				else					returnData.setWages( new Float(resultSet.getFloat( 7)));				returnData.setCreatetime( resultSet.getString( 8));				returnData.setCreateperson( resultSet.getString( 9));				returnData.setCreateunitid( resultSet.getString( 10));				returnData.setModifytime( resultSet.getString( 11));				returnData.setModifyperson( resultSet.getString( 12));				returnData.setDeleteflag( resultSet.getString( 13));				returnData.setDeptguid( resultSet.getString( 14));				returnData.setFormid( resultSet.getString( 15));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,prod_code,code,name,operations,yield,wages,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROCESS" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROCESS SET id= ? , prod_code= ? , code= ? , name= ? , operations= ? , yield= ? , wages= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getCode());			statement.setString( 4,beanData.getName());			statement.setInt( 5,beanData.getOperations());			if (beanData.getYield()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getYield().floatValue());			}			if (beanData.getWages()== null)			{				statement.setNull( 7, Types.FLOAT);			}			else			{				statement.setFloat( 7, beanData.getWages().floatValue());			}			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			statement.setString( 16,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROCESS SET id= ? , prod_code= ? , code= ? , name= ? , operations= ? , yield= ? , wages= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROCESS SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Prod_code = " + "'" + nullString(beanData.getProd_code()) + "',");			bufSQL.append("Code = " + "'" + nullString(beanData.getCode()) + "',");			bufSQL.append("Name = " + "'" + nullString(beanData.getName()) + "',");			bufSQL.append("Operations = " + beanData.getOperations() + ",");			bufSQL.append("Yield = " + beanData.getYield() + ",");			bufSQL.append("Wages = " + beanData.getWages() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROCESSData beanData = (YW_PROCESSData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROCESS SET id= ? , prod_code= ? , code= ? , name= ? , operations= ? , yield= ? , wages= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getCode());			statement.setString( 4,beanData.getName());			statement.setInt( 5,beanData.getOperations());			if (beanData.getYield()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getYield().floatValue());			}			if (beanData.getWages()== null)			{				statement.setNull( 7, Types.FLOAT);			}			else			{				statement.setFloat( 7, beanData.getWages().floatValue());			}			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			statement.setString( 16,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROCESS SET id= ? , prod_code= ? , code= ? , name= ? , operations= ? , yield= ? , wages= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PROCESSData beanData = (YW_PROCESSData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PROCESS WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PROCESS WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
