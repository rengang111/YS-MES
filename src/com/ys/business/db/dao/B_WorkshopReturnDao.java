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
public class B_WorkshopReturnDao extends BaseAbstractDao
{
	public B_WorkshopReturnData beanData=new B_WorkshopReturnData();
	public B_WorkshopReturnDao()
	{
	}
	public B_WorkshopReturnDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_WorkshopReturnData)FindByPrimaryKey(beanData);
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
		B_WorkshopReturnData beanData = (B_WorkshopReturnData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_WorkshopReturn( recordid,workshopreturnid,ysid,taskid,materialid,quantity,returndate,remark,returnperson,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getWorkshopreturnid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getTaskid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getQuantity());			if (beanData.getReturndate()==null || beanData.getReturndate().trim().equals(""))			{				statement.setNull( 7, Types.DATE);			}			else			{				statement.setString( 7, beanData.getReturndate());			}			statement.setString( 8,beanData.getRemark());			statement.setString( 9,beanData.getReturnperson());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_WorkshopReturn( recordid,workshopreturnid,ysid,taskid,materialid,quantity,returndate,remark,returnperson,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_WorkshopReturn( recordid,workshopreturnid,ysid,taskid,materialid,quantity,returndate,remark,returnperson,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getWorkshopreturnid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getTaskid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("'" + nullString(beanData.getReturndate()) + "',");			bufSQL.append("'" + nullString(beanData.getRemark()) + "',");			bufSQL.append("'" + nullString(beanData.getReturnperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_WorkshopReturnData beanData = (B_WorkshopReturnData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_WorkshopReturn( recordid,workshopreturnid,ysid,taskid,materialid,quantity,returndate,remark,returnperson,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getWorkshopreturnid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getTaskid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getQuantity());			if (beanData.getReturndate()==null || beanData.getReturndate().trim().equals(""))			{				statement.setNull( 7, Types.DATE);			}			else			{				statement.setString( 7, beanData.getReturndate());			}			statement.setString( 8,beanData.getRemark());			statement.setString( 9,beanData.getReturnperson());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_WorkshopReturn( recordid,workshopreturnid,ysid,taskid,materialid,quantity,returndate,remark,returnperson,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_WorkshopReturnData beanData = (B_WorkshopReturnData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_WorkshopReturn WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_WorkshopReturn: "+ e.toString());
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
		B_WorkshopReturnData beanData = (B_WorkshopReturnData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_WorkshopReturn WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_WorkshopReturn"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_WorkshopReturn: "+ e.toString());
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
		B_WorkshopReturnData beanData = (B_WorkshopReturnData) beanDataTmp; 
		B_WorkshopReturnData returnData=new B_WorkshopReturnData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,workshopreturnid,ysid,taskid,materialid,quantity,returndate,remark,returnperson,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_WorkshopReturn WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setWorkshopreturnid( resultSet.getString( 2));			returnData.setYsid( resultSet.getString( 3));			returnData.setTaskid( resultSet.getString( 4));			returnData.setMaterialid( resultSet.getString( 5));			returnData.setQuantity( resultSet.getString( 6));			returnData.setReturndate( resultSet.getString( 7));			returnData.setRemark( resultSet.getString( 8));			returnData.setReturnperson( resultSet.getString( 9));			returnData.setDeptguid( resultSet.getString( 10));			returnData.setCreatetime( resultSet.getString( 11));			returnData.setCreateperson( resultSet.getString( 12));			returnData.setCreateunitid( resultSet.getString( 13));			returnData.setModifytime( resultSet.getString( 14));			returnData.setModifyperson( resultSet.getString( 15));			returnData.setDeleteflag( resultSet.getString( 16));			returnData.setFormid( resultSet.getString( 17));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,workshopreturnid,ysid,taskid,materialid,quantity,returndate,remark,returnperson,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_WorkshopReturn  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,workshopreturnid,ysid,taskid,materialid,quantity,returndate,remark,returnperson,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_WorkshopReturn"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_WorkshopReturnData returnData=new B_WorkshopReturnData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setWorkshopreturnid( resultSet.getString( 2));				returnData.setYsid( resultSet.getString( 3));				returnData.setTaskid( resultSet.getString( 4));				returnData.setMaterialid( resultSet.getString( 5));				returnData.setQuantity( resultSet.getString( 6));				returnData.setReturndate( resultSet.getString( 7));				returnData.setRemark( resultSet.getString( 8));				returnData.setReturnperson( resultSet.getString( 9));				returnData.setDeptguid( resultSet.getString( 10));				returnData.setCreatetime( resultSet.getString( 11));				returnData.setCreateperson( resultSet.getString( 12));				returnData.setCreateunitid( resultSet.getString( 13));				returnData.setModifytime( resultSet.getString( 14));				returnData.setModifyperson( resultSet.getString( 15));				returnData.setDeleteflag( resultSet.getString( 16));				returnData.setFormid( resultSet.getString( 17));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,workshopreturnid,ysid,taskid,materialid,quantity,returndate,remark,returnperson,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_WorkshopReturn" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_WorkshopReturn SET recordid= ? , workshopreturnid= ? , ysid= ? , taskid= ? , materialid= ? , quantity= ? , returndate= ? , remark= ? , returnperson= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getWorkshopreturnid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getTaskid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getQuantity());			if (beanData.getReturndate()==null || beanData.getReturndate().trim().equals(""))			{				statement.setNull( 7, Types.DATE);			}			else			{				statement.setString( 7, beanData.getReturndate());			}			statement.setString( 8,beanData.getRemark());			statement.setString( 9,beanData.getReturnperson());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_WorkshopReturn SET recordid= ? , workshopreturnid= ? , ysid= ? , taskid= ? , materialid= ? , quantity= ? , returndate= ? , remark= ? , returnperson= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_WorkshopReturn SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Workshopreturnid = " + "'" + nullString(beanData.getWorkshopreturnid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Taskid = " + "'" + nullString(beanData.getTaskid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Quantity = " + "'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("Returndate = " + "'" + nullString(beanData.getReturndate()) + "',");			bufSQL.append("Remark = " + "'" + nullString(beanData.getRemark()) + "',");			bufSQL.append("Returnperson = " + "'" + nullString(beanData.getReturnperson()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_WorkshopReturnData beanData = (B_WorkshopReturnData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_WorkshopReturn SET recordid= ? , workshopreturnid= ? , ysid= ? , taskid= ? , materialid= ? , quantity= ? , returndate= ? , remark= ? , returnperson= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getWorkshopreturnid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getTaskid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getQuantity());			if (beanData.getReturndate()==null || beanData.getReturndate().trim().equals(""))			{				statement.setNull( 7, Types.DATE);			}			else			{				statement.setString( 7, beanData.getReturndate());			}			statement.setString( 8,beanData.getRemark());			statement.setString( 9,beanData.getReturnperson());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_WorkshopReturn SET recordid= ? , workshopreturnid= ? , ysid= ? , taskid= ? , materialid= ? , quantity= ? , returndate= ? , remark= ? , returnperson= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_WorkshopReturnData beanData = (B_WorkshopReturnData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_WorkshopReturn WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_WorkshopReturn WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
