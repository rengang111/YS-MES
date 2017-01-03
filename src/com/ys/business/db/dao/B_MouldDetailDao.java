package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.util.basedao.BaseAbstractDao;
import com.ys.business.db.data.*;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_MouldDetailDao extends BaseAbstractDao
{
	public B_MouldDetailData beanData=new B_MouldDetailData();
	public B_MouldDetailDao()
	{
	}
	public B_MouldDetailDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MouldDetailData)FindByPrimaryKey(beanData);
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
		B_MouldDetailData beanData = (B_MouldDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldDetail( id,mouldbaseid,type,no,name,size,materialquality,mouldunloadingnum,heavy,price,place,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMouldbaseid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getNo());			statement.setString( 5,beanData.getName());			statement.setString( 6,beanData.getSize());			statement.setString( 7,beanData.getMaterialquality());			statement.setString( 8,beanData.getMouldunloadingnum());			statement.setString( 9,beanData.getHeavy());			statement.setString( 10,beanData.getPrice());			statement.setString( 11,beanData.getPlace());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldDetail( id,mouldbaseid,type,no,name,size,materialquality,mouldunloadingnum,heavy,price,place,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_MouldDetail( id,mouldbaseid,type,no,name,size,materialquality,mouldunloadingnum,heavy,price,place,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getMouldbaseid()) + "',");			bufSQL.append("'" + nullString(beanData.getType()) + "',");			bufSQL.append("'" + nullString(beanData.getNo()) + "',");			bufSQL.append("'" + nullString(beanData.getName()) + "',");			bufSQL.append("'" + nullString(beanData.getSize()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialquality()) + "',");			bufSQL.append("'" + nullString(beanData.getMouldunloadingnum()) + "',");			bufSQL.append("'" + nullString(beanData.getHeavy()) + "',");			bufSQL.append("'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("'" + nullString(beanData.getPlace()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_MouldDetailData beanData = (B_MouldDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldDetail( id,mouldbaseid,type,no,name,size,materialquality,mouldunloadingnum,heavy,price,place,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMouldbaseid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getNo());			statement.setString( 5,beanData.getName());			statement.setString( 6,beanData.getSize());			statement.setString( 7,beanData.getMaterialquality());			statement.setString( 8,beanData.getMouldunloadingnum());			statement.setString( 9,beanData.getHeavy());			statement.setString( 10,beanData.getPrice());			statement.setString( 11,beanData.getPlace());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldDetail( id,mouldbaseid,type,no,name,size,materialquality,mouldunloadingnum,heavy,price,place,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_MouldDetailData beanData = (B_MouldDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_MouldDetail WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldDetail: "+ e.toString());
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
		B_MouldDetailData beanData = (B_MouldDetailData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_MouldDetail WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_MouldDetail"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldDetail: "+ e.toString());
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
		B_MouldDetailData beanData = (B_MouldDetailData) beanDataTmp; 
		B_MouldDetailData returnData=new B_MouldDetailData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,mouldbaseid,type,no,name,size,materialquality,mouldunloadingnum,heavy,price,place,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldDetail WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setMouldbaseid( resultSet.getString( 2));			returnData.setType( resultSet.getString( 3));			returnData.setNo( resultSet.getString( 4));			returnData.setName( resultSet.getString( 5));			returnData.setSize( resultSet.getString( 6));			returnData.setMaterialquality( resultSet.getString( 7));			returnData.setMouldunloadingnum( resultSet.getString( 8));			returnData.setHeavy( resultSet.getString( 9));			returnData.setPrice( resultSet.getString( 10));			returnData.setPlace( resultSet.getString( 11));			returnData.setDeptguid( resultSet.getString( 12));			returnData.setCreatetime( resultSet.getString( 13));			returnData.setCreateperson( resultSet.getString( 14));			returnData.setCreateunitid( resultSet.getString( 15));			returnData.setModifytime( resultSet.getString( 16));			returnData.setModifyperson( resultSet.getString( 17));			returnData.setDeleteflag( resultSet.getString( 18));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,mouldbaseid,type,no,name,size,materialquality,mouldunloadingnum,heavy,price,place,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldDetail  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,mouldbaseid,type,no,name,size,materialquality,mouldunloadingnum,heavy,price,place,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldDetail"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MouldDetailData returnData=new B_MouldDetailData();
				returnData.setId( resultSet.getString( 1));				returnData.setMouldbaseid( resultSet.getString( 2));				returnData.setType( resultSet.getString( 3));				returnData.setNo( resultSet.getString( 4));				returnData.setName( resultSet.getString( 5));				returnData.setSize( resultSet.getString( 6));				returnData.setMaterialquality( resultSet.getString( 7));				returnData.setMouldunloadingnum( resultSet.getString( 8));				returnData.setHeavy( resultSet.getString( 9));				returnData.setPrice( resultSet.getString( 10));				returnData.setPlace( resultSet.getString( 11));				returnData.setDeptguid( resultSet.getString( 12));				returnData.setCreatetime( resultSet.getString( 13));				returnData.setCreateperson( resultSet.getString( 14));				returnData.setCreateunitid( resultSet.getString( 15));				returnData.setModifytime( resultSet.getString( 16));				returnData.setModifyperson( resultSet.getString( 17));				returnData.setDeleteflag( resultSet.getString( 18));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,mouldbaseid,type,no,name,size,materialquality,mouldunloadingnum,heavy,price,place,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldDetail" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_MouldDetail SET id= ? , mouldbaseid= ? , type= ? , no= ? , name= ? , size= ? , materialquality= ? , mouldunloadingnum= ? , heavy= ? , price= ? , place= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMouldbaseid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getNo());			statement.setString( 5,beanData.getName());			statement.setString( 6,beanData.getSize());			statement.setString( 7,beanData.getMaterialquality());			statement.setString( 8,beanData.getMouldunloadingnum());			statement.setString( 9,beanData.getHeavy());			statement.setString( 10,beanData.getPrice());			statement.setString( 11,beanData.getPlace());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());
			statement.setString( 19,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldDetail SET id= ? , mouldbaseid= ? , type= ? , no= ? , name= ? , size= ? , materialquality= ? , mouldunloadingnum= ? , heavy= ? , price= ? , place= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_MouldDetail SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Mouldbaseid = " + "'" + nullString(beanData.getMouldbaseid()) + "',");			bufSQL.append("Type = " + "'" + nullString(beanData.getType()) + "',");			bufSQL.append("No = " + "'" + nullString(beanData.getNo()) + "',");			bufSQL.append("Name = " + "'" + nullString(beanData.getName()) + "',");			bufSQL.append("Size = " + "'" + nullString(beanData.getSize()) + "',");			bufSQL.append("Materialquality = " + "'" + nullString(beanData.getMaterialquality()) + "',");			bufSQL.append("Mouldunloadingnum = " + "'" + nullString(beanData.getMouldunloadingnum()) + "',");			bufSQL.append("Heavy = " + "'" + nullString(beanData.getHeavy()) + "',");			bufSQL.append("Price = " + "'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("Place = " + "'" + nullString(beanData.getPlace()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_MouldDetailData beanData = (B_MouldDetailData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_MouldDetail SET id= ? , mouldbaseid= ? , type= ? , no= ? , name= ? , size= ? , materialquality= ? , mouldunloadingnum= ? , heavy= ? , price= ? , place= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMouldbaseid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getNo());			statement.setString( 5,beanData.getName());			statement.setString( 6,beanData.getSize());			statement.setString( 7,beanData.getMaterialquality());			statement.setString( 8,beanData.getMouldunloadingnum());			statement.setString( 9,beanData.getHeavy());			statement.setString( 10,beanData.getPrice());			statement.setString( 11,beanData.getPlace());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());
			statement.setString( 19,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldDetail SET id= ? , mouldbaseid= ? , type= ? , no= ? , name= ? , size= ? , materialquality= ? , mouldunloadingnum= ? , heavy= ? , price= ? , place= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
		B_MouldDetailData beanData = (B_MouldDetailData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM B_MouldDetail WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_MouldDetail WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
