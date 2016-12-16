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
public class B_MaterialRequirmentDao extends BaseAbstractDao
{
	public B_MaterialRequirmentData beanData=new B_MaterialRequirmentData();
	public B_MaterialRequirmentDao()
	{
	}
	public B_MaterialRequirmentDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MaterialRequirmentData)FindByPrimaryKey(beanData);
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
		B_MaterialRequirmentData beanData = (B_MaterialRequirmentData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MaterialRequirment( recordid,ysid,materialid,supplierid,total,quantity,price,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getTotal());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getTotalprice());			statement.setString( 9,beanData.getDeptguid());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MaterialRequirment( recordid,ysid,materialid,supplierid,total,quantity,price,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_MaterialRequirment( recordid,ysid,materialid,supplierid,total,quantity,price,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getTotal()) + "',");			bufSQL.append("'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_MaterialRequirmentData beanData = (B_MaterialRequirmentData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MaterialRequirment( recordid,ysid,materialid,supplierid,total,quantity,price,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getTotal());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getTotalprice());			statement.setString( 9,beanData.getDeptguid());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MaterialRequirment( recordid,ysid,materialid,supplierid,total,quantity,price,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_MaterialRequirmentData beanData = (B_MaterialRequirmentData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_MaterialRequirment WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MaterialRequirment: "+ e.toString());
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
		B_MaterialRequirmentData beanData = (B_MaterialRequirmentData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_MaterialRequirment WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_MaterialRequirment"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MaterialRequirment: "+ e.toString());
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
		B_MaterialRequirmentData beanData = (B_MaterialRequirmentData) beanDataTmp; 
		B_MaterialRequirmentData returnData=new B_MaterialRequirmentData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,ysid,materialid,supplierid,total,quantity,price,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialRequirment WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setYsid( resultSet.getString( 2));			returnData.setMaterialid( resultSet.getString( 3));			returnData.setSupplierid( resultSet.getString( 4));			returnData.setTotal( resultSet.getString( 5));			returnData.setQuantity( resultSet.getString( 6));			returnData.setPrice( resultSet.getString( 7));			returnData.setTotalprice( resultSet.getString( 8));			returnData.setDeptguid( resultSet.getString( 9));			returnData.setCreatetime( resultSet.getString( 10));			returnData.setCreateperson( resultSet.getString( 11));			returnData.setCreateunitid( resultSet.getString( 12));			returnData.setModifytime( resultSet.getString( 13));			returnData.setModifyperson( resultSet.getString( 14));			returnData.setDeleteflag( resultSet.getString( 15));			returnData.setFormid( resultSet.getString( 16));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,materialid,supplierid,total,quantity,price,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialRequirment  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,ysid,materialid,supplierid,total,quantity,price,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialRequirment"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MaterialRequirmentData returnData=new B_MaterialRequirmentData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setYsid( resultSet.getString( 2));				returnData.setMaterialid( resultSet.getString( 3));				returnData.setSupplierid( resultSet.getString( 4));				returnData.setTotal( resultSet.getString( 5));				returnData.setQuantity( resultSet.getString( 6));				returnData.setPrice( resultSet.getString( 7));				returnData.setTotalprice( resultSet.getString( 8));				returnData.setDeptguid( resultSet.getString( 9));				returnData.setCreatetime( resultSet.getString( 10));				returnData.setCreateperson( resultSet.getString( 11));				returnData.setCreateunitid( resultSet.getString( 12));				returnData.setModifytime( resultSet.getString( 13));				returnData.setModifyperson( resultSet.getString( 14));				returnData.setDeleteflag( resultSet.getString( 15));				returnData.setFormid( resultSet.getString( 16));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,materialid,supplierid,total,quantity,price,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialRequirment" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_MaterialRequirment SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , total= ? , quantity= ? , price= ? , totalprice= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getTotal());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getTotalprice());			statement.setString( 9,beanData.getDeptguid());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getFormid());
			statement.setString( 17,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MaterialRequirment SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , total= ? , quantity= ? , price= ? , totalprice= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_MaterialRequirment SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Total = " + "'" + nullString(beanData.getTotal()) + "',");			bufSQL.append("Quantity = " + "'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("Price = " + "'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("Totalprice = " + "'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_MaterialRequirmentData beanData = (B_MaterialRequirmentData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_MaterialRequirment SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , total= ? , quantity= ? , price= ? , totalprice= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getTotal());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getTotalprice());			statement.setString( 9,beanData.getDeptguid());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getFormid());
			statement.setString( 17,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MaterialRequirment SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , total= ? , quantity= ? , price= ? , totalprice= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_MaterialRequirmentData beanData = (B_MaterialRequirmentData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_MaterialRequirment WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_MaterialRequirment WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
