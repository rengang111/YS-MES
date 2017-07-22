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
public class B_ProductDesignDao extends BaseAbstractDao
{
	public B_ProductDesignData beanData=new B_ProductDesignData();
	public B_ProductDesignDao()
	{
	}
	public B_ProductDesignDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_ProductDesignData)FindByPrimaryKey(beanData);
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
		B_ProductDesignData beanData = (B_ProductDesignData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ProductDesign( recordid,productdetailid,subid,ysid,productid,sealedsample,batterypack,chargertype,packagedescription,storagedescription,image_filename,image_filename2,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getProductdetailid());			statement.setString( 3,beanData.getSubid());			statement.setString( 4,beanData.getYsid());			statement.setString( 5,beanData.getProductid());			statement.setString( 6,beanData.getSealedsample());			statement.setString( 7,beanData.getBatterypack());			statement.setString( 8,beanData.getChargertype());			statement.setString( 9,beanData.getPackagedescription());			statement.setString( 10,beanData.getStoragedescription());			statement.setString( 11,beanData.getImage_filename());			statement.setString( 12,beanData.getImage_filename2());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ProductDesign( recordid,productdetailid,subid,ysid,productid,sealedsample,batterypack,chargertype,packagedescription,storagedescription,image_filename,image_filename2,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_ProductDesign( recordid,productdetailid,subid,ysid,productid,sealedsample,batterypack,chargertype,packagedescription,storagedescription,image_filename,image_filename2,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getProductdetailid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getProductid()) + "',");			bufSQL.append("'" + nullString(beanData.getSealedsample()) + "',");			bufSQL.append("'" + nullString(beanData.getBatterypack()) + "',");			bufSQL.append("'" + nullString(beanData.getChargertype()) + "',");			bufSQL.append("'" + nullString(beanData.getPackagedescription()) + "',");			bufSQL.append("'" + nullString(beanData.getStoragedescription()) + "',");			bufSQL.append("'" + nullString(beanData.getImage_filename()) + "',");			bufSQL.append("'" + nullString(beanData.getImage_filename2()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_ProductDesignData beanData = (B_ProductDesignData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ProductDesign( recordid,productdetailid,subid,ysid,productid,sealedsample,batterypack,chargertype,packagedescription,storagedescription,image_filename,image_filename2,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getProductdetailid());			statement.setString( 3,beanData.getSubid());			statement.setString( 4,beanData.getYsid());			statement.setString( 5,beanData.getProductid());			statement.setString( 6,beanData.getSealedsample());			statement.setString( 7,beanData.getBatterypack());			statement.setString( 8,beanData.getChargertype());			statement.setString( 9,beanData.getPackagedescription());			statement.setString( 10,beanData.getStoragedescription());			statement.setString( 11,beanData.getImage_filename());			statement.setString( 12,beanData.getImage_filename2());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ProductDesign( recordid,productdetailid,subid,ysid,productid,sealedsample,batterypack,chargertype,packagedescription,storagedescription,image_filename,image_filename2,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_ProductDesignData beanData = (B_ProductDesignData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_ProductDesign WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ProductDesign: "+ e.toString());
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
		B_ProductDesignData beanData = (B_ProductDesignData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_ProductDesign WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_ProductDesign"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ProductDesign: "+ e.toString());
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
		B_ProductDesignData beanData = (B_ProductDesignData) beanDataTmp; 
		B_ProductDesignData returnData=new B_ProductDesignData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,productdetailid,subid,ysid,productid,sealedsample,batterypack,chargertype,packagedescription,storagedescription,image_filename,image_filename2,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProductDesign WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setProductdetailid( resultSet.getString( 2));			returnData.setSubid( resultSet.getString( 3));			returnData.setYsid( resultSet.getString( 4));			returnData.setProductid( resultSet.getString( 5));			returnData.setSealedsample( resultSet.getString( 6));			returnData.setBatterypack( resultSet.getString( 7));			returnData.setChargertype( resultSet.getString( 8));			returnData.setPackagedescription( resultSet.getString( 9));			returnData.setStoragedescription( resultSet.getString( 10));			returnData.setImage_filename( resultSet.getString( 11));			returnData.setImage_filename2( resultSet.getString( 12));			returnData.setDeptguid( resultSet.getString( 13));			returnData.setCreatetime( resultSet.getString( 14));			returnData.setCreateperson( resultSet.getString( 15));			returnData.setCreateunitid( resultSet.getString( 16));			returnData.setModifytime( resultSet.getString( 17));			returnData.setModifyperson( resultSet.getString( 18));			returnData.setDeleteflag( resultSet.getString( 19));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,productdetailid,subid,ysid,productid,sealedsample,batterypack,chargertype,packagedescription,storagedescription,image_filename,image_filename2,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProductDesign  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,productdetailid,subid,ysid,productid,sealedsample,batterypack,chargertype,packagedescription,storagedescription,image_filename,image_filename2,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProductDesign"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_ProductDesignData returnData=new B_ProductDesignData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setProductdetailid( resultSet.getString( 2));				returnData.setSubid( resultSet.getString( 3));				returnData.setYsid( resultSet.getString( 4));				returnData.setProductid( resultSet.getString( 5));				returnData.setSealedsample( resultSet.getString( 6));				returnData.setBatterypack( resultSet.getString( 7));				returnData.setChargertype( resultSet.getString( 8));				returnData.setPackagedescription( resultSet.getString( 9));				returnData.setStoragedescription( resultSet.getString( 10));				returnData.setImage_filename( resultSet.getString( 11));				returnData.setImage_filename2( resultSet.getString( 12));				returnData.setDeptguid( resultSet.getString( 13));				returnData.setCreatetime( resultSet.getString( 14));				returnData.setCreateperson( resultSet.getString( 15));				returnData.setCreateunitid( resultSet.getString( 16));				returnData.setModifytime( resultSet.getString( 17));				returnData.setModifyperson( resultSet.getString( 18));				returnData.setDeleteflag( resultSet.getString( 19));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,productdetailid,subid,ysid,productid,sealedsample,batterypack,chargertype,packagedescription,storagedescription,image_filename,image_filename2,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProductDesign" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_ProductDesign SET recordid= ? , productdetailid= ? , subid= ? , ysid= ? , productid= ? , sealedsample= ? , batterypack= ? , chargertype= ? , packagedescription= ? , storagedescription= ? , image_filename= ? , image_filename2= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getProductdetailid());			statement.setString( 3,beanData.getSubid());			statement.setString( 4,beanData.getYsid());			statement.setString( 5,beanData.getProductid());			statement.setString( 6,beanData.getSealedsample());			statement.setString( 7,beanData.getBatterypack());			statement.setString( 8,beanData.getChargertype());			statement.setString( 9,beanData.getPackagedescription());			statement.setString( 10,beanData.getStoragedescription());			statement.setString( 11,beanData.getImage_filename());			statement.setString( 12,beanData.getImage_filename2());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ProductDesign SET recordid= ? , productdetailid= ? , subid= ? , ysid= ? , productid= ? , sealedsample= ? , batterypack= ? , chargertype= ? , packagedescription= ? , storagedescription= ? , image_filename= ? , image_filename2= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_ProductDesign SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Productdetailid = " + "'" + nullString(beanData.getProductdetailid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Productid = " + "'" + nullString(beanData.getProductid()) + "',");			bufSQL.append("Sealedsample = " + "'" + nullString(beanData.getSealedsample()) + "',");			bufSQL.append("Batterypack = " + "'" + nullString(beanData.getBatterypack()) + "',");			bufSQL.append("Chargertype = " + "'" + nullString(beanData.getChargertype()) + "',");			bufSQL.append("Packagedescription = " + "'" + nullString(beanData.getPackagedescription()) + "',");			bufSQL.append("Storagedescription = " + "'" + nullString(beanData.getStoragedescription()) + "',");			bufSQL.append("Image_filename = " + "'" + nullString(beanData.getImage_filename()) + "',");			bufSQL.append("Image_filename2 = " + "'" + nullString(beanData.getImage_filename2()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_ProductDesignData beanData = (B_ProductDesignData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_ProductDesign SET recordid= ? , productdetailid= ? , subid= ? , ysid= ? , productid= ? , sealedsample= ? , batterypack= ? , chargertype= ? , packagedescription= ? , storagedescription= ? , image_filename= ? , image_filename2= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getProductdetailid());			statement.setString( 3,beanData.getSubid());			statement.setString( 4,beanData.getYsid());			statement.setString( 5,beanData.getProductid());			statement.setString( 6,beanData.getSealedsample());			statement.setString( 7,beanData.getBatterypack());			statement.setString( 8,beanData.getChargertype());			statement.setString( 9,beanData.getPackagedescription());			statement.setString( 10,beanData.getStoragedescription());			statement.setString( 11,beanData.getImage_filename());			statement.setString( 12,beanData.getImage_filename2());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ProductDesign SET recordid= ? , productdetailid= ? , subid= ? , ysid= ? , productid= ? , sealedsample= ? , batterypack= ? , chargertype= ? , packagedescription= ? , storagedescription= ? , image_filename= ? , image_filename2= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  recordid  = ?"+ e.toString());
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
		B_ProductDesignData beanData = (B_ProductDesignData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_ProductDesign WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_ProductDesign WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
