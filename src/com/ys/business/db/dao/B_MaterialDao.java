package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.business.db.data.*;
import com.ys.util.basedao.BaseAbstractDao;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_MaterialDao extends BaseAbstractDao
{
	public B_MaterialData beanData=new B_MaterialData();
	public B_MaterialDao()
	{
	}
	public B_MaterialDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MaterialData)FindByPrimaryKey(beanData);
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
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Material( recordid,materialid,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getMaterialname());			statement.setString( 4,beanData.getCategoryid());			statement.setString( 5,beanData.getDescription());			statement.setString( 6,beanData.getSharemodel());			statement.setString( 7,beanData.getUnit());			statement.setString( 8,beanData.getParentid());			statement.setString( 9,beanData.getSubid());			statement.setString( 10,beanData.getSubiddes());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Material( recordid,materialid,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_Material( recordid,materialid,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialname()) + "',");			bufSQL.append("'" + nullString(beanData.getCategoryid()) + "',");			bufSQL.append("'" + nullString(beanData.getDescription()) + "',");			bufSQL.append("'" + nullString(beanData.getSharemodel()) + "',");			bufSQL.append("'" + nullString(beanData.getUnit()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubiddes()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Material( recordid,materialid,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getMaterialname());			statement.setString( 4,beanData.getCategoryid());			statement.setString( 5,beanData.getDescription());			statement.setString( 6,beanData.getSharemodel());			statement.setString( 7,beanData.getUnit());			statement.setString( 8,beanData.getParentid());			statement.setString( 9,beanData.getSubid());			statement.setString( 10,beanData.getSubiddes());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Material( recordid,materialid,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_Material WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Material: "+ e.toString());
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
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_Material WHERE ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM B_Material"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Material: "+ e.toString());
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
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
		B_MaterialData returnData=new B_MaterialData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,materialid,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Material WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setMaterialid( resultSet.getString( 2));			returnData.setMaterialname( resultSet.getString( 3));			returnData.setCategoryid( resultSet.getString( 4));			returnData.setDescription( resultSet.getString( 5));			returnData.setSharemodel( resultSet.getString( 6));			returnData.setUnit( resultSet.getString( 7));			returnData.setParentid( resultSet.getString( 8));			returnData.setSubid( resultSet.getString( 9));			returnData.setSubiddes( resultSet.getString( 10));			returnData.setDeptguid( resultSet.getString( 11));			returnData.setCreatetime( resultSet.getString( 12));			returnData.setCreateperson( resultSet.getString( 13));			returnData.setCreateunitid( resultSet.getString( 14));			returnData.setModifytime( resultSet.getString( 15));			returnData.setModifyperson( resultSet.getString( 16));			returnData.setDeleteflag( resultSet.getString( 17));			returnData.setFormid( resultSet.getString( 18));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,materialid,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Material  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,materialid,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Material"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MaterialData returnData=new B_MaterialData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setMaterialid( resultSet.getString( 2));				returnData.setMaterialname( resultSet.getString( 3));				returnData.setCategoryid( resultSet.getString( 4));				returnData.setDescription( resultSet.getString( 5));				returnData.setSharemodel( resultSet.getString( 6));				returnData.setUnit( resultSet.getString( 7));				returnData.setParentid( resultSet.getString( 8));				returnData.setSubid( resultSet.getString( 9));				returnData.setSubiddes( resultSet.getString( 10));				returnData.setDeptguid( resultSet.getString( 11));				returnData.setCreatetime( resultSet.getString( 12));				returnData.setCreateperson( resultSet.getString( 13));				returnData.setCreateunitid( resultSet.getString( 14));				returnData.setModifytime( resultSet.getString( 15));				returnData.setModifyperson( resultSet.getString( 16));				returnData.setDeleteflag( resultSet.getString( 17));				returnData.setFormid( resultSet.getString( 18));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,materialid,materialname,categoryid,description,sharemodel,unit,parentid,subid,subiddes,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Material" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_Material SET recordid= ? , materialid= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getMaterialname());			statement.setString( 4,beanData.getCategoryid());			statement.setString( 5,beanData.getDescription());			statement.setString( 6,beanData.getSharemodel());			statement.setString( 7,beanData.getUnit());			statement.setString( 8,beanData.getParentid());			statement.setString( 9,beanData.getSubid());			statement.setString( 10,beanData.getSubiddes());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			statement.setString( 19,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Material SET recordid= ? , materialid= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_Material SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Materialname = " + "'" + nullString(beanData.getMaterialname()) + "',");			bufSQL.append("Categoryid = " + "'" + nullString(beanData.getCategoryid()) + "',");			bufSQL.append("Description = " + "'" + nullString(beanData.getDescription()) + "',");			bufSQL.append("Sharemodel = " + "'" + nullString(beanData.getSharemodel()) + "',");			bufSQL.append("Unit = " + "'" + nullString(beanData.getUnit()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Subiddes = " + "'" + nullString(beanData.getSubiddes()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "'");
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
		B_MaterialData beanData = (B_MaterialData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_Material SET recordid= ? , materialid= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getMaterialname());			statement.setString( 4,beanData.getCategoryid());			statement.setString( 5,beanData.getDescription());			statement.setString( 6,beanData.getSharemodel());			statement.setString( 7,beanData.getUnit());			statement.setString( 8,beanData.getParentid());			statement.setString( 9,beanData.getSubid());			statement.setString( 10,beanData.getSubiddes());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			statement.setString( 19,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Material SET recordid= ? , materialid= ? , materialname= ? , categoryid= ? , description= ? , sharemodel= ? , unit= ? , parentid= ? , subid= ? , subiddes= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_MaterialData beanData = (B_MaterialData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_Material WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_Material WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
