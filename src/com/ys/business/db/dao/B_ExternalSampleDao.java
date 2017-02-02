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
public class B_ExternalSampleDao extends BaseAbstractDao
{
	public B_ExternalSampleData beanData=new B_ExternalSampleData();
	public B_ExternalSampleDao()
	{
	}
	public B_ExternalSampleDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_ExternalSampleData)FindByPrimaryKey(beanData);
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
		B_ExternalSampleData beanData = (B_ExternalSampleData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ExternalSample( id,sampleid,sampleversion,samplename,brand,buytime,currency,price,address,memo,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getSampleid());			statement.setString( 3,beanData.getSampleversion());			statement.setString( 4,beanData.getSamplename());			statement.setString( 5,beanData.getBrand());			statement.setString( 6,beanData.getBuytime());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getPrice());			statement.setString( 9,beanData.getAddress());			statement.setString( 10,beanData.getMemo());			statement.setString( 11,beanData.getImage_filename());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ExternalSample( id,sampleid,sampleversion,samplename,brand,buytime,currency,price,address,memo,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_ExternalSample( id,sampleid,sampleversion,samplename,brand,buytime,currency,price,address,memo,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getSampleid()) + "',");			bufSQL.append("'" + nullString(beanData.getSampleversion()) + "',");			bufSQL.append("'" + nullString(beanData.getSamplename()) + "',");			bufSQL.append("'" + nullString(beanData.getBrand()) + "',");			bufSQL.append("'" + nullString(beanData.getBuytime()) + "',");			bufSQL.append("'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("'" + nullString(beanData.getAddress()) + "',");			bufSQL.append("'" + nullString(beanData.getMemo()) + "',");			bufSQL.append("'" + nullString(beanData.getImage_filename()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_ExternalSampleData beanData = (B_ExternalSampleData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ExternalSample( id,sampleid,sampleversion,samplename,brand,buytime,currency,price,address,memo,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getSampleid());			statement.setString( 3,beanData.getSampleversion());			statement.setString( 4,beanData.getSamplename());			statement.setString( 5,beanData.getBrand());			statement.setString( 6,beanData.getBuytime());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getPrice());			statement.setString( 9,beanData.getAddress());			statement.setString( 10,beanData.getMemo());			statement.setString( 11,beanData.getImage_filename());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ExternalSample( id,sampleid,sampleversion,samplename,brand,buytime,currency,price,address,memo,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_ExternalSampleData beanData = (B_ExternalSampleData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_ExternalSample WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ExternalSample: "+ e.toString());
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
		B_ExternalSampleData beanData = (B_ExternalSampleData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_ExternalSample WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_ExternalSample"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ExternalSample: "+ e.toString());
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
		B_ExternalSampleData beanData = (B_ExternalSampleData) beanDataTmp; 
		B_ExternalSampleData returnData=new B_ExternalSampleData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,sampleid,sampleversion,samplename,brand,buytime,currency,price,address,memo,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ExternalSample WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setSampleid( resultSet.getString( 2));			returnData.setSampleversion( resultSet.getString( 3));			returnData.setSamplename( resultSet.getString( 4));			returnData.setBrand( resultSet.getString( 5));			returnData.setBuytime( resultSet.getString( 6));			returnData.setCurrency( resultSet.getString( 7));			returnData.setPrice( resultSet.getString( 8));			returnData.setAddress( resultSet.getString( 9));			returnData.setMemo( resultSet.getString( 10));			returnData.setImage_filename( resultSet.getString( 11));			returnData.setDeptguid( resultSet.getString( 12));			returnData.setCreatetime( resultSet.getString( 13));			returnData.setCreateperson( resultSet.getString( 14));			returnData.setCreateunitid( resultSet.getString( 15));			returnData.setModifytime( resultSet.getString( 16));			returnData.setModifyperson( resultSet.getString( 17));			returnData.setDeleteflag( resultSet.getString( 18));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,sampleid,sampleversion,samplename,brand,buytime,currency,price,address,memo,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ExternalSample  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,sampleid,sampleversion,samplename,brand,buytime,currency,price,address,memo,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ExternalSample"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_ExternalSampleData returnData=new B_ExternalSampleData();
				returnData.setId( resultSet.getString( 1));				returnData.setSampleid( resultSet.getString( 2));				returnData.setSampleversion( resultSet.getString( 3));				returnData.setSamplename( resultSet.getString( 4));				returnData.setBrand( resultSet.getString( 5));				returnData.setBuytime( resultSet.getString( 6));				returnData.setCurrency( resultSet.getString( 7));				returnData.setPrice( resultSet.getString( 8));				returnData.setAddress( resultSet.getString( 9));				returnData.setMemo( resultSet.getString( 10));				returnData.setImage_filename( resultSet.getString( 11));				returnData.setDeptguid( resultSet.getString( 12));				returnData.setCreatetime( resultSet.getString( 13));				returnData.setCreateperson( resultSet.getString( 14));				returnData.setCreateunitid( resultSet.getString( 15));				returnData.setModifytime( resultSet.getString( 16));				returnData.setModifyperson( resultSet.getString( 17));				returnData.setDeleteflag( resultSet.getString( 18));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,sampleid,sampleversion,samplename,brand,buytime,currency,price,address,memo,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ExternalSample" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_ExternalSample SET id= ? , sampleid= ? , sampleversion= ? , samplename= ? , brand= ? , buytime= ? , currency= ? , price= ? , address= ? , memo= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getSampleid());			statement.setString( 3,beanData.getSampleversion());			statement.setString( 4,beanData.getSamplename());			statement.setString( 5,beanData.getBrand());			statement.setString( 6,beanData.getBuytime());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getPrice());			statement.setString( 9,beanData.getAddress());			statement.setString( 10,beanData.getMemo());			statement.setString( 11,beanData.getImage_filename());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());
			statement.setString( 19,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ExternalSample SET id= ? , sampleid= ? , sampleversion= ? , samplename= ? , brand= ? , buytime= ? , currency= ? , price= ? , address= ? , memo= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_ExternalSample SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Sampleid = " + "'" + nullString(beanData.getSampleid()) + "',");			bufSQL.append("Sampleversion = " + "'" + nullString(beanData.getSampleversion()) + "',");			bufSQL.append("Samplename = " + "'" + nullString(beanData.getSamplename()) + "',");			bufSQL.append("Brand = " + "'" + nullString(beanData.getBrand()) + "',");			bufSQL.append("Buytime = " + "'" + nullString(beanData.getBuytime()) + "',");			bufSQL.append("Currency = " + "'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("Price = " + "'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("Address = " + "'" + nullString(beanData.getAddress()) + "',");			bufSQL.append("Memo = " + "'" + nullString(beanData.getMemo()) + "',");			bufSQL.append("Image_filename = " + "'" + nullString(beanData.getImage_filename()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_ExternalSampleData beanData = (B_ExternalSampleData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_ExternalSample SET id= ? , sampleid= ? , sampleversion= ? , samplename= ? , brand= ? , buytime= ? , currency= ? , price= ? , address= ? , memo= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getSampleid());			statement.setString( 3,beanData.getSampleversion());			statement.setString( 4,beanData.getSamplename());			statement.setString( 5,beanData.getBrand());			statement.setString( 6,beanData.getBuytime());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getPrice());			statement.setString( 9,beanData.getAddress());			statement.setString( 10,beanData.getMemo());			statement.setString( 11,beanData.getImage_filename());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());
			statement.setString( 19,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ExternalSample SET id= ? , sampleid= ? , sampleversion= ? , samplename= ? , brand= ? , buytime= ? , currency= ? , price= ? , address= ? , memo= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
		B_ExternalSampleData beanData = (B_ExternalSampleData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM B_ExternalSample WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_ExternalSample WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
