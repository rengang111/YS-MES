package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.business.db.data.*;
import com.ys.util.basedao.BaseAbstractDao;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_InspectionReturnDao extends BaseAbstractDao
{
	public B_InspectionReturnData beanData=new B_InspectionReturnData();
	public B_InspectionReturnDao()
	{
	}
	public B_InspectionReturnDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_InspectionReturnData)FindByPrimaryKey(beanData);
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
		B_InspectionReturnData beanData = (B_InspectionReturnData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_InspectionReturn( recordid,inspectionreturnid,parentid,subid,ysid,contractid,arrivalid,supplierid,materialid,returnquantity,checkerid,returndate,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getInspectionreturnid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivalid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getMaterialid());			statement.setString( 10,beanData.getReturnquantity());			statement.setString( 11,beanData.getCheckerid());			statement.setString( 12,beanData.getReturndate());			statement.setString( 13,beanData.getStatus());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_InspectionReturn( recordid,inspectionreturnid,parentid,subid,ysid,contractid,arrivalid,supplierid,materialid,returnquantity,checkerid,returndate,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_InspectionReturn( recordid,inspectionreturnid,parentid,subid,ysid,contractid,arrivalid,supplierid,materialid,returnquantity,checkerid,returndate,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getInspectionreturnid()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("'" + nullString(beanData.getArrivalid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getReturnquantity()) + "',");			bufSQL.append("'" + nullString(beanData.getCheckerid()) + "',");			bufSQL.append("'" + nullString(beanData.getReturndate()) + "',");			bufSQL.append("'" + nullString(beanData.getStatus()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_InspectionReturnData beanData = (B_InspectionReturnData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_InspectionReturn( recordid,inspectionreturnid,parentid,subid,ysid,contractid,arrivalid,supplierid,materialid,returnquantity,checkerid,returndate,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getInspectionreturnid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivalid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getMaterialid());			statement.setString( 10,beanData.getReturnquantity());			statement.setString( 11,beanData.getCheckerid());			statement.setString( 12,beanData.getReturndate());			statement.setString( 13,beanData.getStatus());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_InspectionReturn( recordid,inspectionreturnid,parentid,subid,ysid,contractid,arrivalid,supplierid,materialid,returnquantity,checkerid,returndate,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_InspectionReturnData beanData = (B_InspectionReturnData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_InspectionReturn WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_InspectionReturn: "+ e.toString());
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
		B_InspectionReturnData beanData = (B_InspectionReturnData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_InspectionReturn WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_InspectionReturn"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_InspectionReturn: "+ e.toString());
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
		B_InspectionReturnData beanData = (B_InspectionReturnData) beanDataTmp; 
		B_InspectionReturnData returnData=new B_InspectionReturnData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,inspectionreturnid,parentid,subid,ysid,contractid,arrivalid,supplierid,materialid,returnquantity,checkerid,returndate,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InspectionReturn WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setInspectionreturnid( resultSet.getString( 2));			returnData.setParentid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setYsid( resultSet.getString( 5));			returnData.setContractid( resultSet.getString( 6));			returnData.setArrivalid( resultSet.getString( 7));			returnData.setSupplierid( resultSet.getString( 8));			returnData.setMaterialid( resultSet.getString( 9));			returnData.setReturnquantity( resultSet.getString( 10));			returnData.setCheckerid( resultSet.getString( 11));			returnData.setReturndate( resultSet.getString( 12));			returnData.setStatus( resultSet.getString( 13));			returnData.setDeptguid( resultSet.getString( 14));			returnData.setCreatetime( resultSet.getString( 15));			returnData.setCreateperson( resultSet.getString( 16));			returnData.setCreateunitid( resultSet.getString( 17));			returnData.setModifytime( resultSet.getString( 18));			returnData.setModifyperson( resultSet.getString( 19));			returnData.setDeleteflag( resultSet.getString( 20));			returnData.setFormid( resultSet.getString( 21));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,inspectionreturnid,parentid,subid,ysid,contractid,arrivalid,supplierid,materialid,returnquantity,checkerid,returndate,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InspectionReturn  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,inspectionreturnid,parentid,subid,ysid,contractid,arrivalid,supplierid,materialid,returnquantity,checkerid,returndate,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InspectionReturn"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_InspectionReturnData returnData=new B_InspectionReturnData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setInspectionreturnid( resultSet.getString( 2));				returnData.setParentid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setYsid( resultSet.getString( 5));				returnData.setContractid( resultSet.getString( 6));				returnData.setArrivalid( resultSet.getString( 7));				returnData.setSupplierid( resultSet.getString( 8));				returnData.setMaterialid( resultSet.getString( 9));				returnData.setReturnquantity( resultSet.getString( 10));				returnData.setCheckerid( resultSet.getString( 11));				returnData.setReturndate( resultSet.getString( 12));				returnData.setStatus( resultSet.getString( 13));				returnData.setDeptguid( resultSet.getString( 14));				returnData.setCreatetime( resultSet.getString( 15));				returnData.setCreateperson( resultSet.getString( 16));				returnData.setCreateunitid( resultSet.getString( 17));				returnData.setModifytime( resultSet.getString( 18));				returnData.setModifyperson( resultSet.getString( 19));				returnData.setDeleteflag( resultSet.getString( 20));				returnData.setFormid( resultSet.getString( 21));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,inspectionreturnid,parentid,subid,ysid,contractid,arrivalid,supplierid,materialid,returnquantity,checkerid,returndate,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InspectionReturn" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_InspectionReturn SET recordid= ? , inspectionreturnid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivalid= ? , supplierid= ? , materialid= ? , returnquantity= ? , checkerid= ? , returndate= ? , status= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getInspectionreturnid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivalid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getMaterialid());			statement.setString( 10,beanData.getReturnquantity());			statement.setString( 11,beanData.getCheckerid());			statement.setString( 12,beanData.getReturndate());			statement.setString( 13,beanData.getStatus());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			statement.setString( 22,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_InspectionReturn SET recordid= ? , inspectionreturnid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivalid= ? , supplierid= ? , materialid= ? , returnquantity= ? , checkerid= ? , returndate= ? , status= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_InspectionReturn SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Inspectionreturnid = " + "'" + nullString(beanData.getInspectionreturnid()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Contractid = " + "'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("Arrivalid = " + "'" + nullString(beanData.getArrivalid()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Returnquantity = " + "'" + nullString(beanData.getReturnquantity()) + "',");			bufSQL.append("Checkerid = " + "'" + nullString(beanData.getCheckerid()) + "',");			bufSQL.append("Returndate = " + "'" + nullString(beanData.getReturndate()) + "',");			bufSQL.append("Status = " + "'" + nullString(beanData.getStatus()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_InspectionReturnData beanData = (B_InspectionReturnData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_InspectionReturn SET recordid= ? , inspectionreturnid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivalid= ? , supplierid= ? , materialid= ? , returnquantity= ? , checkerid= ? , returndate= ? , status= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getInspectionreturnid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivalid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getMaterialid());			statement.setString( 10,beanData.getReturnquantity());			statement.setString( 11,beanData.getCheckerid());			statement.setString( 12,beanData.getReturndate());			statement.setString( 13,beanData.getStatus());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			statement.setString( 22,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_InspectionReturn SET recordid= ? , inspectionreturnid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivalid= ? , supplierid= ? , materialid= ? , returnquantity= ? , checkerid= ? , returndate= ? , status= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_InspectionReturnData beanData = (B_InspectionReturnData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_InspectionReturn WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_InspectionReturn WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
