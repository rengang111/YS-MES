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
public class B_RawRequirementDao extends BaseAbstractDao
{
	public B_RawRequirementData beanData=new B_RawRequirementData();
	public B_RawRequirementDao()
	{
	}
	public B_RawRequirementDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_RawRequirementData)FindByPrimaryKey(beanData);
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
		B_RawRequirementData beanData = (B_RawRequirementData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_RawRequirement( recordid,ysid,materialid,supplierid,quantity,price,currency,rawtype,stockoutqty,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getQuantity());			statement.setString( 6,beanData.getPrice());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getRawtype());			statement.setString( 9,beanData.getStockoutqty());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_RawRequirement( recordid,ysid,materialid,supplierid,quantity,price,currency,rawtype,stockoutqty,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_RawRequirement( recordid,ysid,materialid,supplierid,quantity,price,currency,rawtype,stockoutqty,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getRawtype()) + "',");			bufSQL.append("'" + nullString(beanData.getStockoutqty()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_RawRequirementData beanData = (B_RawRequirementData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_RawRequirement( recordid,ysid,materialid,supplierid,quantity,price,currency,rawtype,stockoutqty,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getQuantity());			statement.setString( 6,beanData.getPrice());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getRawtype());			statement.setString( 9,beanData.getStockoutqty());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_RawRequirement( recordid,ysid,materialid,supplierid,quantity,price,currency,rawtype,stockoutqty,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_RawRequirementData beanData = (B_RawRequirementData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_RawRequirement WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_RawRequirement: "+ e.toString());
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
		B_RawRequirementData beanData = (B_RawRequirementData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_RawRequirement WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_RawRequirement"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_RawRequirement: "+ e.toString());
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
		B_RawRequirementData beanData = (B_RawRequirementData) beanDataTmp; 
		B_RawRequirementData returnData=new B_RawRequirementData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,ysid,materialid,supplierid,quantity,price,currency,rawtype,stockoutqty,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_RawRequirement WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setYsid( resultSet.getString( 2));			returnData.setMaterialid( resultSet.getString( 3));			returnData.setSupplierid( resultSet.getString( 4));			returnData.setQuantity( resultSet.getString( 5));			returnData.setPrice( resultSet.getString( 6));			returnData.setCurrency( resultSet.getString( 7));			returnData.setRawtype( resultSet.getString( 8));			returnData.setStockoutqty( resultSet.getString( 9));			returnData.setDeptguid( resultSet.getString( 10));			returnData.setCreatetime( resultSet.getString( 11));			returnData.setCreateperson( resultSet.getString( 12));			returnData.setCreateunitid( resultSet.getString( 13));			returnData.setModifytime( resultSet.getString( 14));			returnData.setModifyperson( resultSet.getString( 15));			returnData.setDeleteflag( resultSet.getString( 16));			returnData.setFormid( resultSet.getString( 17));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,materialid,supplierid,quantity,price,currency,rawtype,stockoutqty,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_RawRequirement  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,ysid,materialid,supplierid,quantity,price,currency,rawtype,stockoutqty,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_RawRequirement"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_RawRequirementData returnData=new B_RawRequirementData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setYsid( resultSet.getString( 2));				returnData.setMaterialid( resultSet.getString( 3));				returnData.setSupplierid( resultSet.getString( 4));				returnData.setQuantity( resultSet.getString( 5));				returnData.setPrice( resultSet.getString( 6));				returnData.setCurrency( resultSet.getString( 7));				returnData.setRawtype( resultSet.getString( 8));				returnData.setStockoutqty( resultSet.getString( 9));				returnData.setDeptguid( resultSet.getString( 10));				returnData.setCreatetime( resultSet.getString( 11));				returnData.setCreateperson( resultSet.getString( 12));				returnData.setCreateunitid( resultSet.getString( 13));				returnData.setModifytime( resultSet.getString( 14));				returnData.setModifyperson( resultSet.getString( 15));				returnData.setDeleteflag( resultSet.getString( 16));				returnData.setFormid( resultSet.getString( 17));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,materialid,supplierid,quantity,price,currency,rawtype,stockoutqty,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_RawRequirement" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_RawRequirement SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , quantity= ? , price= ? , currency= ? , rawtype= ? , stockoutqty= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getQuantity());			statement.setString( 6,beanData.getPrice());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getRawtype());			statement.setString( 9,beanData.getStockoutqty());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_RawRequirement SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , quantity= ? , price= ? , currency= ? , rawtype= ? , stockoutqty= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_RawRequirement SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Quantity = " + "'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("Price = " + "'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("Currency = " + "'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("Rawtype = " + "'" + nullString(beanData.getRawtype()) + "',");			bufSQL.append("Stockoutqty = " + "'" + nullString(beanData.getStockoutqty()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_RawRequirementData beanData = (B_RawRequirementData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_RawRequirement SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , quantity= ? , price= ? , currency= ? , rawtype= ? , stockoutqty= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getQuantity());			statement.setString( 6,beanData.getPrice());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getRawtype());			statement.setString( 9,beanData.getStockoutqty());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_RawRequirement SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , quantity= ? , price= ? , currency= ? , rawtype= ? , stockoutqty= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_RawRequirementData beanData = (B_RawRequirementData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_RawRequirement WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_RawRequirement WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
