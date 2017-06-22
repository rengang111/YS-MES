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
public class B_InspectionProcessDao extends BaseAbstractDao
{
	public B_InspectionProcessData beanData=new B_InspectionProcessData();
	public B_InspectionProcessDao()
	{
	}
	public B_InspectionProcessDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_InspectionProcessData)FindByPrimaryKey(beanData);
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
		B_InspectionProcessData beanData = (B_InspectionProcessData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_InspectionProcess( recordid,ysid,arrivalid,materialid,checker,checkresult,checkdate,managerresult,managerdate,managerfeedback,gmdate,gmfeedback,gmresult,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getArrivalid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getChecker());			statement.setString( 6,beanData.getCheckresult());			statement.setString( 7,beanData.getCheckdate());			statement.setString( 8,beanData.getManagerresult());			statement.setString( 9,beanData.getManagerdate());			statement.setString( 10,beanData.getManagerfeedback());			statement.setString( 11,beanData.getGmdate());			statement.setString( 12,beanData.getGmfeedback());			statement.setString( 13,beanData.getGmresult());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_InspectionProcess( recordid,ysid,arrivalid,materialid,checker,checkresult,checkdate,managerresult,managerdate,managerfeedback,gmdate,gmfeedback,gmresult,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_InspectionProcess( recordid,ysid,arrivalid,materialid,checker,checkresult,checkdate,managerresult,managerdate,managerfeedback,gmdate,gmfeedback,gmresult,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getArrivalid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getChecker()) + "',");			bufSQL.append("'" + nullString(beanData.getCheckresult()) + "',");			bufSQL.append("'" + nullString(beanData.getCheckdate()) + "',");			bufSQL.append("'" + nullString(beanData.getManagerresult()) + "',");			bufSQL.append("'" + nullString(beanData.getManagerdate()) + "',");			bufSQL.append("'" + nullString(beanData.getManagerfeedback()) + "',");			bufSQL.append("'" + nullString(beanData.getGmdate()) + "',");			bufSQL.append("'" + nullString(beanData.getGmfeedback()) + "',");			bufSQL.append("'" + nullString(beanData.getGmresult()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_InspectionProcessData beanData = (B_InspectionProcessData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_InspectionProcess( recordid,ysid,arrivalid,materialid,checker,checkresult,checkdate,managerresult,managerdate,managerfeedback,gmdate,gmfeedback,gmresult,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getArrivalid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getChecker());			statement.setString( 6,beanData.getCheckresult());			statement.setString( 7,beanData.getCheckdate());			statement.setString( 8,beanData.getManagerresult());			statement.setString( 9,beanData.getManagerdate());			statement.setString( 10,beanData.getManagerfeedback());			statement.setString( 11,beanData.getGmdate());			statement.setString( 12,beanData.getGmfeedback());			statement.setString( 13,beanData.getGmresult());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_InspectionProcess( recordid,ysid,arrivalid,materialid,checker,checkresult,checkdate,managerresult,managerdate,managerfeedback,gmdate,gmfeedback,gmresult,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_InspectionProcessData beanData = (B_InspectionProcessData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_InspectionProcess WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_InspectionProcess: "+ e.toString());
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
		B_InspectionProcessData beanData = (B_InspectionProcessData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_InspectionProcess WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_InspectionProcess"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_InspectionProcess: "+ e.toString());
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
		B_InspectionProcessData beanData = (B_InspectionProcessData) beanDataTmp; 
		B_InspectionProcessData returnData=new B_InspectionProcessData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,ysid,arrivalid,materialid,checker,checkresult,checkdate,managerresult,managerdate,managerfeedback,gmdate,gmfeedback,gmresult,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InspectionProcess WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setYsid( resultSet.getString( 2));			returnData.setArrivalid( resultSet.getString( 3));			returnData.setMaterialid( resultSet.getString( 4));			returnData.setChecker( resultSet.getString( 5));			returnData.setCheckresult( resultSet.getString( 6));			returnData.setCheckdate( resultSet.getString( 7));			returnData.setManagerresult( resultSet.getString( 8));			returnData.setManagerdate( resultSet.getString( 9));			returnData.setManagerfeedback( resultSet.getString( 10));			returnData.setGmdate( resultSet.getString( 11));			returnData.setGmfeedback( resultSet.getString( 12));			returnData.setGmresult( resultSet.getString( 13));			returnData.setDeptguid( resultSet.getString( 14));			returnData.setCreatetime( resultSet.getString( 15));			returnData.setCreateperson( resultSet.getString( 16));			returnData.setCreateunitid( resultSet.getString( 17));			returnData.setModifytime( resultSet.getString( 18));			returnData.setModifyperson( resultSet.getString( 19));			returnData.setDeleteflag( resultSet.getString( 20));			returnData.setFormid( resultSet.getString( 21));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,arrivalid,materialid,checker,checkresult,checkdate,managerresult,managerdate,managerfeedback,gmdate,gmfeedback,gmresult,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InspectionProcess  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,ysid,arrivalid,materialid,checker,checkresult,checkdate,managerresult,managerdate,managerfeedback,gmdate,gmfeedback,gmresult,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InspectionProcess"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_InspectionProcessData returnData=new B_InspectionProcessData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setYsid( resultSet.getString( 2));				returnData.setArrivalid( resultSet.getString( 3));				returnData.setMaterialid( resultSet.getString( 4));				returnData.setChecker( resultSet.getString( 5));				returnData.setCheckresult( resultSet.getString( 6));				returnData.setCheckdate( resultSet.getString( 7));				returnData.setManagerresult( resultSet.getString( 8));				returnData.setManagerdate( resultSet.getString( 9));				returnData.setManagerfeedback( resultSet.getString( 10));				returnData.setGmdate( resultSet.getString( 11));				returnData.setGmfeedback( resultSet.getString( 12));				returnData.setGmresult( resultSet.getString( 13));				returnData.setDeptguid( resultSet.getString( 14));				returnData.setCreatetime( resultSet.getString( 15));				returnData.setCreateperson( resultSet.getString( 16));				returnData.setCreateunitid( resultSet.getString( 17));				returnData.setModifytime( resultSet.getString( 18));				returnData.setModifyperson( resultSet.getString( 19));				returnData.setDeleteflag( resultSet.getString( 20));				returnData.setFormid( resultSet.getString( 21));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,arrivalid,materialid,checker,checkresult,checkdate,managerresult,managerdate,managerfeedback,gmdate,gmfeedback,gmresult,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InspectionProcess" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_InspectionProcess SET recordid= ? , ysid= ? , arrivalid= ? , materialid= ? , checker= ? , checkresult= ? , checkdate= ? , managerresult= ? , managerdate= ? , managerfeedback= ? , gmdate= ? , gmfeedback= ? , gmresult= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getArrivalid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getChecker());			statement.setString( 6,beanData.getCheckresult());			statement.setString( 7,beanData.getCheckdate());			statement.setString( 8,beanData.getManagerresult());			statement.setString( 9,beanData.getManagerdate());			statement.setString( 10,beanData.getManagerfeedback());			statement.setString( 11,beanData.getGmdate());			statement.setString( 12,beanData.getGmfeedback());			statement.setString( 13,beanData.getGmresult());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			statement.setString( 22,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_InspectionProcess SET recordid= ? , ysid= ? , arrivalid= ? , materialid= ? , checker= ? , checkresult= ? , checkdate= ? , managerresult= ? , managerdate= ? , managerfeedback= ? , gmdate= ? , gmfeedback= ? , gmresult= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_InspectionProcess SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Arrivalid = " + "'" + nullString(beanData.getArrivalid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Checker = " + "'" + nullString(beanData.getChecker()) + "',");			bufSQL.append("Checkresult = " + "'" + nullString(beanData.getCheckresult()) + "',");			bufSQL.append("Checkdate = " + "'" + nullString(beanData.getCheckdate()) + "',");			bufSQL.append("Managerresult = " + "'" + nullString(beanData.getManagerresult()) + "',");			bufSQL.append("Managerdate = " + "'" + nullString(beanData.getManagerdate()) + "',");			bufSQL.append("Managerfeedback = " + "'" + nullString(beanData.getManagerfeedback()) + "',");			bufSQL.append("Gmdate = " + "'" + nullString(beanData.getGmdate()) + "',");			bufSQL.append("Gmfeedback = " + "'" + nullString(beanData.getGmfeedback()) + "',");			bufSQL.append("Gmresult = " + "'" + nullString(beanData.getGmresult()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_InspectionProcessData beanData = (B_InspectionProcessData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_InspectionProcess SET recordid= ? , ysid= ? , arrivalid= ? , materialid= ? , checker= ? , checkresult= ? , checkdate= ? , managerresult= ? , managerdate= ? , managerfeedback= ? , gmdate= ? , gmfeedback= ? , gmresult= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getArrivalid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getChecker());			statement.setString( 6,beanData.getCheckresult());			statement.setString( 7,beanData.getCheckdate());			statement.setString( 8,beanData.getManagerresult());			statement.setString( 9,beanData.getManagerdate());			statement.setString( 10,beanData.getManagerfeedback());			statement.setString( 11,beanData.getGmdate());			statement.setString( 12,beanData.getGmfeedback());			statement.setString( 13,beanData.getGmresult());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			statement.setString( 22,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_InspectionProcess SET recordid= ? , ysid= ? , arrivalid= ? , materialid= ? , checker= ? , checkresult= ? , checkdate= ? , managerresult= ? , managerdate= ? , managerfeedback= ? , gmdate= ? , gmfeedback= ? , gmresult= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_InspectionProcessData beanData = (B_InspectionProcessData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_InspectionProcess WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_InspectionProcess WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
