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
public class YW_PRODUCT_INFODao extends BaseAbstractDao
{
	public YW_PRODUCT_INFOData beanData=new YW_PRODUCT_INFOData();
	public YW_PRODUCT_INFODao()
	{
	}
	public YW_PRODUCT_INFODao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PRODUCT_INFOData)FindByPrimaryKey(beanData);
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
		YW_PRODUCT_INFOData beanData = (YW_PRODUCT_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PRODUCT_INFO( id,mode,name,name_short,cert_body,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMode());			statement.setString( 3,beanData.getName());			statement.setString( 4,beanData.getName_short());			if (beanData.getCert_body()== null)			{				statement.setNull( 5, Types.FLOAT);			}			else			{				statement.setFloat( 5, beanData.getCert_body().floatValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PRODUCT_INFO( id,mode,name,name_short,cert_body,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PRODUCT_INFO( id,mode,name,name_short,cert_body,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid) VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getMode()) + "',");			bufSQL.append("'" + nullString(beanData.getName()) + "',");			bufSQL.append("'" + nullString(beanData.getName_short()) + "',");			bufSQL.append(beanData.getCert_body() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PRODUCT_INFOData beanData = (YW_PRODUCT_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PRODUCT_INFO( id,mode,name,name_short,cert_body,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMode());			statement.setString( 3,beanData.getName());			statement.setString( 4,beanData.getName_short());			if (beanData.getCert_body()== null)			{				statement.setNull( 5, Types.FLOAT);			}			else			{				statement.setFloat( 5, beanData.getCert_body().floatValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PRODUCT_INFO( id,mode,name,name_short,cert_body,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PRODUCT_INFOData beanData = (YW_PRODUCT_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PRODUCT_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PRODUCT_INFO: "+ e.toString());
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
		YW_PRODUCT_INFOData beanData = (YW_PRODUCT_INFOData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PRODUCT_INFO WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_PRODUCT_INFO"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PRODUCT_INFO: "+ e.toString());
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
		YW_PRODUCT_INFOData beanData = (YW_PRODUCT_INFOData) beanDataTmp; 
		YW_PRODUCT_INFOData returnData=new YW_PRODUCT_INFOData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,mode,name,name_short,cert_body,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PRODUCT_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setMode( resultSet.getString( 2));			returnData.setName( resultSet.getString( 3));			returnData.setName_short( resultSet.getString( 4));			if (resultSet.getString( 5)==null)				returnData.setCert_body(null);			else				returnData.setCert_body( new Float(resultSet.getFloat( 5)));			returnData.setCreatetime( resultSet.getString( 6));			returnData.setCreateperson( resultSet.getString( 7));			returnData.setCreateunitid( resultSet.getString( 8));			returnData.setModifytime( resultSet.getString( 9));			returnData.setModifyperson( resultSet.getString( 10));			returnData.setDeleteflag( resultSet.getString( 11));			returnData.setDeptguid( resultSet.getString( 12));			returnData.setFormid( resultSet.getString( 13));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,mode,name,name_short,cert_body,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PRODUCT_INFO  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,mode,name,name_short,cert_body,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PRODUCT_INFO"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PRODUCT_INFOData returnData=new YW_PRODUCT_INFOData();
				returnData.setId( resultSet.getString( 1));				returnData.setMode( resultSet.getString( 2));				returnData.setName( resultSet.getString( 3));				returnData.setName_short( resultSet.getString( 4));				if (resultSet.getString( 5)==null)					returnData.setCert_body(null);				else					returnData.setCert_body( new Float(resultSet.getFloat( 5)));				returnData.setCreatetime( resultSet.getString( 6));				returnData.setCreateperson( resultSet.getString( 7));				returnData.setCreateunitid( resultSet.getString( 8));				returnData.setModifytime( resultSet.getString( 9));				returnData.setModifyperson( resultSet.getString( 10));				returnData.setDeleteflag( resultSet.getString( 11));				returnData.setDeptguid( resultSet.getString( 12));				returnData.setFormid( resultSet.getString( 13));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,mode,name,name_short,cert_body,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PRODUCT_INFO" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PRODUCT_INFO SET id= ? , mode= ? , name= ? , name_short= ? , cert_body= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMode());			statement.setString( 3,beanData.getName());			statement.setString( 4,beanData.getName_short());			if (beanData.getCert_body()== null)			{				statement.setNull( 5, Types.FLOAT);			}			else			{				statement.setFloat( 5, beanData.getCert_body().floatValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getFormid());
			statement.setString( 14,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PRODUCT_INFO SET id= ? , mode= ? , name= ? , name_short= ? , cert_body= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PRODUCT_INFO SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Mode = " + "'" + nullString(beanData.getMode()) + "',");			bufSQL.append("Name = " + "'" + nullString(beanData.getName()) + "',");			bufSQL.append("Name_short = " + "'" + nullString(beanData.getName_short()) + "',");			bufSQL.append("Cert_body = " + beanData.getCert_body() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_PRODUCT_INFOData beanData = (YW_PRODUCT_INFOData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PRODUCT_INFO SET id= ? , mode= ? , name= ? , name_short= ? , cert_body= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMode());			statement.setString( 3,beanData.getName());			statement.setString( 4,beanData.getName_short());			if (beanData.getCert_body()== null)			{				statement.setNull( 5, Types.FLOAT);			}			else			{				statement.setFloat( 5, beanData.getCert_body().floatValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getFormid());
			statement.setString( 14,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PRODUCT_INFO SET id= ? , mode= ? , name= ? , name_short= ? , cert_body= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PRODUCT_INFOData beanData = (YW_PRODUCT_INFOData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PRODUCT_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PRODUCT_INFO WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
