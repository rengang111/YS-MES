package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.util.basedao.BaseAbstractDao;
import com.ys.business.db.data.*;

/**
* <p>Title: </p>
* <p>Description: 数据表  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_MouldBaseInfoDao extends BaseAbstractDao
{
	public B_MouldBaseInfoData beanData=new B_MouldBaseInfoData();
	public B_MouldBaseInfoDao()
	{
	}
	public B_MouldBaseInfoDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MouldBaseInfoData)FindByPrimaryKey(beanData);
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
		B_MouldBaseInfoData beanData = (B_MouldBaseInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldBaseInfo( id,mouldid,type,productmodelid,name,materialquality,size,weight,unloadingnum,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMouldid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getProductmodelid());			statement.setString( 5,beanData.getName());			statement.setString( 6,beanData.getMaterialquality());			statement.setString( 7,beanData.getSize());			statement.setString( 8,beanData.getWeight());			statement.setString( 9,beanData.getUnloadingnum());			statement.setString( 10,beanData.getImage_filename());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldBaseInfo( id,mouldid,type,productmodelid,name,materialquality,size,weight,unloadingnum,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
			bufSQL.append("INSERT INTO B_MouldBaseInfo( id,mouldid,type,productmodelid,name,materialquality,size,weight,unloadingnum,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getMouldid()) + "',");			bufSQL.append("'" + nullString(beanData.getType()) + "',");			bufSQL.append("'" + nullString(beanData.getProductmodelid()) + "',");			bufSQL.append("'" + nullString(beanData.getName()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialquality()) + "',");			bufSQL.append("'" + nullString(beanData.getSize()) + "',");			bufSQL.append("'" + nullString(beanData.getWeight()) + "',");			bufSQL.append("'" + nullString(beanData.getUnloadingnum()) + "',");			bufSQL.append("'" + nullString(beanData.getImage_filename()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_MouldBaseInfoData beanData = (B_MouldBaseInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldBaseInfo( id,mouldid,type,productmodelid,name,materialquality,size,weight,unloadingnum,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMouldid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getProductmodelid());			statement.setString( 5,beanData.getName());			statement.setString( 6,beanData.getMaterialquality());			statement.setString( 7,beanData.getSize());			statement.setString( 8,beanData.getWeight());			statement.setString( 9,beanData.getUnloadingnum());			statement.setString( 10,beanData.getImage_filename());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldBaseInfo( id,mouldid,type,productmodelid,name,materialquality,size,weight,unloadingnum,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
		B_MouldBaseInfoData beanData = (B_MouldBaseInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_MouldBaseInfo WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldBaseInfo: "+ e.toString());
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
		B_MouldBaseInfoData beanData = (B_MouldBaseInfoData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_MouldBaseInfo WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_MouldBaseInfo"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldBaseInfo: "+ e.toString());
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
		B_MouldBaseInfoData beanData = (B_MouldBaseInfoData) beanDataTmp; 
		B_MouldBaseInfoData returnData=new B_MouldBaseInfoData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,mouldid,type,productmodelid,name,materialquality,size,weight,unloadingnum,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldBaseInfo WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setMouldid( resultSet.getString( 2));			returnData.setType( resultSet.getString( 3));			returnData.setProductmodelid( resultSet.getString( 4));			returnData.setName( resultSet.getString( 5));			returnData.setMaterialquality( resultSet.getString( 6));			returnData.setSize( resultSet.getString( 7));			returnData.setWeight( resultSet.getString( 8));			returnData.setUnloadingnum( resultSet.getString( 9));			returnData.setImage_filename( resultSet.getString( 10));			returnData.setDeptguid( resultSet.getString( 11));			returnData.setCreatetime( resultSet.getString( 12));			returnData.setCreateperson( resultSet.getString( 13));			returnData.setCreateunitid( resultSet.getString( 14));			returnData.setModifytime( resultSet.getString( 15));			returnData.setModifyperson( resultSet.getString( 16));			returnData.setDeleteflag( resultSet.getString( 17));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,mouldid,type,productmodelid,name,materialquality,size,weight,unloadingnum,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldBaseInfo  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,mouldid,type,productmodelid,name,materialquality,size,weight,unloadingnum,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldBaseInfo"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MouldBaseInfoData returnData=new B_MouldBaseInfoData();
				returnData.setId( resultSet.getString( 1));				returnData.setMouldid( resultSet.getString( 2));				returnData.setType( resultSet.getString( 3));				returnData.setProductmodelid( resultSet.getString( 4));				returnData.setName( resultSet.getString( 5));				returnData.setMaterialquality( resultSet.getString( 6));				returnData.setSize( resultSet.getString( 7));				returnData.setWeight( resultSet.getString( 8));				returnData.setUnloadingnum( resultSet.getString( 9));				returnData.setImage_filename( resultSet.getString( 10));				returnData.setDeptguid( resultSet.getString( 11));				returnData.setCreatetime( resultSet.getString( 12));				returnData.setCreateperson( resultSet.getString( 13));				returnData.setCreateunitid( resultSet.getString( 14));				returnData.setModifytime( resultSet.getString( 15));				returnData.setModifyperson( resultSet.getString( 16));				returnData.setDeleteflag( resultSet.getString( 17));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,mouldid,type,productmodelid,name,materialquality,size,weight,unloadingnum,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldBaseInfo" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_MouldBaseInfo SET id= ? , mouldid= ? , type= ? , productmodelid= ? , name= ? , materialquality= ? , size= ? , weight= ? , unloadingnum= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMouldid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getProductmodelid());			statement.setString( 5,beanData.getName());			statement.setString( 6,beanData.getMaterialquality());			statement.setString( 7,beanData.getSize());			statement.setString( 8,beanData.getWeight());			statement.setString( 9,beanData.getUnloadingnum());			statement.setString( 10,beanData.getImage_filename());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());
			statement.setString( 18,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldBaseInfo SET id= ? , mouldid= ? , type= ? , productmodelid= ? , name= ? , materialquality= ? , size= ? , weight= ? , unloadingnum= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_MouldBaseInfo SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Mouldid = " + "'" + nullString(beanData.getMouldid()) + "',");			bufSQL.append("Type = " + "'" + nullString(beanData.getType()) + "',");			bufSQL.append("Productmodelid = " + "'" + nullString(beanData.getProductmodelid()) + "',");			bufSQL.append("Name = " + "'" + nullString(beanData.getName()) + "',");			bufSQL.append("Materialquality = " + "'" + nullString(beanData.getMaterialquality()) + "',");			bufSQL.append("Size = " + "'" + nullString(beanData.getSize()) + "',");			bufSQL.append("Weight = " + "'" + nullString(beanData.getWeight()) + "',");			bufSQL.append("Unloadingnum = " + "'" + nullString(beanData.getUnloadingnum()) + "',");			bufSQL.append("Image_filename = " + "'" + nullString(beanData.getImage_filename()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_MouldBaseInfoData beanData = (B_MouldBaseInfoData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_MouldBaseInfo SET id= ? , mouldid= ? , type= ? , productmodelid= ? , name= ? , materialquality= ? , size= ? , weight= ? , unloadingnum= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getMouldid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getProductmodelid());			statement.setString( 5,beanData.getName());			statement.setString( 6,beanData.getMaterialquality());			statement.setString( 7,beanData.getSize());			statement.setString( 8,beanData.getWeight());			statement.setString( 9,beanData.getUnloadingnum());			statement.setString( 10,beanData.getImage_filename());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());
			statement.setString( 18,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldBaseInfo SET id= ? , mouldid= ? , type= ? , productmodelid= ? , name= ? , materialquality= ? , size= ? , weight= ? , unloadingnum= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param beanData:主键
	 */
	public void FindPrimaryKey(Object beanDataTmp) throws Exception
	{
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
		B_MouldBaseInfoData beanData = (B_MouldBaseInfoData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM B_MouldBaseInfo WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_MouldBaseInfo WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
