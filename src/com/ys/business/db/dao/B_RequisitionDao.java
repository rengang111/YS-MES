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
public class B_RequisitionDao extends BaseAbstractDao
{
	public B_RequisitionData beanData=new B_RequisitionData();
	public B_RequisitionDao()
	{
	}
	public B_RequisitionDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_RequisitionData)FindByPrimaryKey(beanData);
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Requisition( recordid,requisitionid,parentid,subid,ysid,requisitiondate,requisitionstoreid,requisitionunitid,requisitionuserid,storeuserid,storepmuserid,unitpmuserid,originalrequisitionid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getRequisitionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			if (beanData.getRequisitiondate()==null || beanData.getRequisitiondate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getRequisitiondate());			}			statement.setString( 7,beanData.getRequisitionstoreid());			statement.setString( 8,beanData.getRequisitionunitid());			statement.setString( 9,beanData.getRequisitionuserid());			statement.setString( 10,beanData.getStoreuserid());			statement.setString( 11,beanData.getStorepmuserid());			statement.setString( 12,beanData.getUnitpmuserid());			statement.setString( 13,beanData.getOriginalrequisitionid());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Requisition( recordid,requisitionid,parentid,subid,ysid,requisitiondate,requisitionstoreid,requisitionunitid,requisitionuserid,storeuserid,storepmuserid,unitpmuserid,originalrequisitionid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_Requisition( recordid,requisitionid,parentid,subid,ysid,requisitiondate,requisitionstoreid,requisitionunitid,requisitionuserid,storeuserid,storepmuserid,unitpmuserid,originalrequisitionid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getRequisitionid()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getRequisitiondate()) + "',");			bufSQL.append("'" + nullString(beanData.getRequisitionstoreid()) + "',");			bufSQL.append("'" + nullString(beanData.getRequisitionunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getRequisitionuserid()) + "',");			bufSQL.append("'" + nullString(beanData.getStoreuserid()) + "',");			bufSQL.append("'" + nullString(beanData.getStorepmuserid()) + "',");			bufSQL.append("'" + nullString(beanData.getUnitpmuserid()) + "',");			bufSQL.append("'" + nullString(beanData.getOriginalrequisitionid()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Requisition( recordid,requisitionid,parentid,subid,ysid,requisitiondate,requisitionstoreid,requisitionunitid,requisitionuserid,storeuserid,storepmuserid,unitpmuserid,originalrequisitionid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getRequisitionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			if (beanData.getRequisitiondate()==null || beanData.getRequisitiondate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getRequisitiondate());			}			statement.setString( 7,beanData.getRequisitionstoreid());			statement.setString( 8,beanData.getRequisitionunitid());			statement.setString( 9,beanData.getRequisitionuserid());			statement.setString( 10,beanData.getStoreuserid());			statement.setString( 11,beanData.getStorepmuserid());			statement.setString( 12,beanData.getUnitpmuserid());			statement.setString( 13,beanData.getOriginalrequisitionid());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Requisition( recordid,requisitionid,parentid,subid,ysid,requisitiondate,requisitionstoreid,requisitionunitid,requisitionuserid,storeuserid,storepmuserid,unitpmuserid,originalrequisitionid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_Requisition WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Requisition: "+ e.toString());
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_Requisition WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_Requisition"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Requisition: "+ e.toString());
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
		B_RequisitionData returnData=new B_RequisitionData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,requisitionid,parentid,subid,ysid,requisitiondate,requisitionstoreid,requisitionunitid,requisitionuserid,storeuserid,storepmuserid,unitpmuserid,originalrequisitionid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Requisition WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setRequisitionid( resultSet.getString( 2));			returnData.setParentid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setYsid( resultSet.getString( 5));			returnData.setRequisitiondate( resultSet.getString( 6));			returnData.setRequisitionstoreid( resultSet.getString( 7));			returnData.setRequisitionunitid( resultSet.getString( 8));			returnData.setRequisitionuserid( resultSet.getString( 9));			returnData.setStoreuserid( resultSet.getString( 10));			returnData.setStorepmuserid( resultSet.getString( 11));			returnData.setUnitpmuserid( resultSet.getString( 12));			returnData.setOriginalrequisitionid( resultSet.getString( 13));			returnData.setDeptguid( resultSet.getString( 14));			returnData.setCreatetime( resultSet.getString( 15));			returnData.setCreateperson( resultSet.getString( 16));			returnData.setCreateunitid( resultSet.getString( 17));			returnData.setModifytime( resultSet.getString( 18));			returnData.setModifyperson( resultSet.getString( 19));			returnData.setDeleteflag( resultSet.getString( 20));			returnData.setFormid( resultSet.getString( 21));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,requisitionid,parentid,subid,ysid,requisitiondate,requisitionstoreid,requisitionunitid,requisitionuserid,storeuserid,storepmuserid,unitpmuserid,originalrequisitionid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Requisition  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,requisitionid,parentid,subid,ysid,requisitiondate,requisitionstoreid,requisitionunitid,requisitionuserid,storeuserid,storepmuserid,unitpmuserid,originalrequisitionid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Requisition"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_RequisitionData returnData=new B_RequisitionData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setRequisitionid( resultSet.getString( 2));				returnData.setParentid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setYsid( resultSet.getString( 5));				returnData.setRequisitiondate( resultSet.getString( 6));				returnData.setRequisitionstoreid( resultSet.getString( 7));				returnData.setRequisitionunitid( resultSet.getString( 8));				returnData.setRequisitionuserid( resultSet.getString( 9));				returnData.setStoreuserid( resultSet.getString( 10));				returnData.setStorepmuserid( resultSet.getString( 11));				returnData.setUnitpmuserid( resultSet.getString( 12));				returnData.setOriginalrequisitionid( resultSet.getString( 13));				returnData.setDeptguid( resultSet.getString( 14));				returnData.setCreatetime( resultSet.getString( 15));				returnData.setCreateperson( resultSet.getString( 16));				returnData.setCreateunitid( resultSet.getString( 17));				returnData.setModifytime( resultSet.getString( 18));				returnData.setModifyperson( resultSet.getString( 19));				returnData.setDeleteflag( resultSet.getString( 20));				returnData.setFormid( resultSet.getString( 21));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,requisitionid,parentid,subid,ysid,requisitiondate,requisitionstoreid,requisitionunitid,requisitionuserid,storeuserid,storepmuserid,unitpmuserid,originalrequisitionid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Requisition" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_Requisition SET recordid= ? , requisitionid= ? , parentid= ? , subid= ? , ysid= ? , requisitiondate= ? , requisitionstoreid= ? , requisitionunitid= ? , requisitionuserid= ? , storeuserid= ? , storepmuserid= ? , unitpmuserid= ? , originalrequisitionid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getRequisitionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			if (beanData.getRequisitiondate()==null || beanData.getRequisitiondate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getRequisitiondate());			}			statement.setString( 7,beanData.getRequisitionstoreid());			statement.setString( 8,beanData.getRequisitionunitid());			statement.setString( 9,beanData.getRequisitionuserid());			statement.setString( 10,beanData.getStoreuserid());			statement.setString( 11,beanData.getStorepmuserid());			statement.setString( 12,beanData.getUnitpmuserid());			statement.setString( 13,beanData.getOriginalrequisitionid());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			statement.setString( 22,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Requisition SET recordid= ? , requisitionid= ? , parentid= ? , subid= ? , ysid= ? , requisitiondate= ? , requisitionstoreid= ? , requisitionunitid= ? , requisitionuserid= ? , storeuserid= ? , storepmuserid= ? , unitpmuserid= ? , originalrequisitionid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_Requisition SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Requisitionid = " + "'" + nullString(beanData.getRequisitionid()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Requisitiondate = " + "'" + nullString(beanData.getRequisitiondate()) + "',");			bufSQL.append("Requisitionstoreid = " + "'" + nullString(beanData.getRequisitionstoreid()) + "',");			bufSQL.append("Requisitionunitid = " + "'" + nullString(beanData.getRequisitionunitid()) + "',");			bufSQL.append("Requisitionuserid = " + "'" + nullString(beanData.getRequisitionuserid()) + "',");			bufSQL.append("Storeuserid = " + "'" + nullString(beanData.getStoreuserid()) + "',");			bufSQL.append("Storepmuserid = " + "'" + nullString(beanData.getStorepmuserid()) + "',");			bufSQL.append("Unitpmuserid = " + "'" + nullString(beanData.getUnitpmuserid()) + "',");			bufSQL.append("Originalrequisitionid = " + "'" + nullString(beanData.getOriginalrequisitionid()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_RequisitionData beanData = (B_RequisitionData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_Requisition SET recordid= ? , requisitionid= ? , parentid= ? , subid= ? , ysid= ? , requisitiondate= ? , requisitionstoreid= ? , requisitionunitid= ? , requisitionuserid= ? , storeuserid= ? , storepmuserid= ? , unitpmuserid= ? , originalrequisitionid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getRequisitionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			if (beanData.getRequisitiondate()==null || beanData.getRequisitiondate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getRequisitiondate());			}			statement.setString( 7,beanData.getRequisitionstoreid());			statement.setString( 8,beanData.getRequisitionunitid());			statement.setString( 9,beanData.getRequisitionuserid());			statement.setString( 10,beanData.getStoreuserid());			statement.setString( 11,beanData.getStorepmuserid());			statement.setString( 12,beanData.getUnitpmuserid());			statement.setString( 13,beanData.getOriginalrequisitionid());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			statement.setString( 22,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Requisition SET recordid= ? , requisitionid= ? , parentid= ? , subid= ? , ysid= ? , requisitiondate= ? , requisitionstoreid= ? , requisitionunitid= ? , requisitionuserid= ? , storeuserid= ? , storepmuserid= ? , unitpmuserid= ? , originalrequisitionid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_Requisition WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_Requisition WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
