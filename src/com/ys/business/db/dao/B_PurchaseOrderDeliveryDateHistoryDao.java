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
public class B_PurchaseOrderDeliveryDateHistoryDao extends BaseAbstractDao
{
	public B_PurchaseOrderDeliveryDateHistoryData beanData=new B_PurchaseOrderDeliveryDateHistoryData();
	public B_PurchaseOrderDeliveryDateHistoryDao()
	{
	}
	public B_PurchaseOrderDeliveryDateHistoryDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_PurchaseOrderDeliveryDateHistoryData)FindByPrimaryKey(beanData);
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
		B_PurchaseOrderDeliveryDateHistoryData beanData = (B_PurchaseOrderDeliveryDateHistoryData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchaseOrderDeliveryDateHistory( recordid,contractid,materialid,deliverydate,newdeliverydate,newupdatedate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getContractid());			statement.setString( 3,beanData.getMaterialid());			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 4, Types.DATE);			}			else			{				statement.setString( 4, beanData.getDeliverydate());			}			if (beanData.getNewdeliverydate()==null || beanData.getNewdeliverydate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getNewdeliverydate());			}			if (beanData.getNewupdatedate()==null || beanData.getNewupdatedate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getNewupdatedate());			}			statement.setString( 7,beanData.getRemarks());			statement.setString( 8,beanData.getDeptguid());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchaseOrderDeliveryDateHistory( recordid,contractid,materialid,deliverydate,newdeliverydate,newupdatedate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_PurchaseOrderDeliveryDateHistory( recordid,contractid,materialid,deliverydate,newdeliverydate,newupdatedate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getDeliverydate()) + "',");			bufSQL.append("'" + nullString(beanData.getNewdeliverydate()) + "',");			bufSQL.append("'" + nullString(beanData.getNewupdatedate()) + "',");			bufSQL.append("'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchaseOrderDeliveryDateHistoryData beanData = (B_PurchaseOrderDeliveryDateHistoryData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchaseOrderDeliveryDateHistory( recordid,contractid,materialid,deliverydate,newdeliverydate,newupdatedate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getContractid());			statement.setString( 3,beanData.getMaterialid());			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 4, Types.DATE);			}			else			{				statement.setString( 4, beanData.getDeliverydate());			}			if (beanData.getNewdeliverydate()==null || beanData.getNewdeliverydate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getNewdeliverydate());			}			if (beanData.getNewupdatedate()==null || beanData.getNewupdatedate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getNewupdatedate());			}			statement.setString( 7,beanData.getRemarks());			statement.setString( 8,beanData.getDeptguid());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchaseOrderDeliveryDateHistory( recordid,contractid,materialid,deliverydate,newdeliverydate,newupdatedate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_PurchaseOrderDeliveryDateHistoryData beanData = (B_PurchaseOrderDeliveryDateHistoryData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_PurchaseOrderDeliveryDateHistory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchaseOrderDeliveryDateHistory: "+ e.toString());
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
		B_PurchaseOrderDeliveryDateHistoryData beanData = (B_PurchaseOrderDeliveryDateHistoryData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_PurchaseOrderDeliveryDateHistory WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_PurchaseOrderDeliveryDateHistory"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchaseOrderDeliveryDateHistory: "+ e.toString());
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
		B_PurchaseOrderDeliveryDateHistoryData beanData = (B_PurchaseOrderDeliveryDateHistoryData) beanDataTmp; 
		B_PurchaseOrderDeliveryDateHistoryData returnData=new B_PurchaseOrderDeliveryDateHistoryData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,contractid,materialid,deliverydate,newdeliverydate,newupdatedate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseOrderDeliveryDateHistory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setContractid( resultSet.getString( 2));			returnData.setMaterialid( resultSet.getString( 3));			returnData.setDeliverydate( resultSet.getString( 4));			returnData.setNewdeliverydate( resultSet.getString( 5));			returnData.setNewupdatedate( resultSet.getString( 6));			returnData.setRemarks( resultSet.getString( 7));			returnData.setDeptguid( resultSet.getString( 8));			returnData.setCreatetime( resultSet.getString( 9));			returnData.setCreateperson( resultSet.getString( 10));			returnData.setCreateunitid( resultSet.getString( 11));			returnData.setModifytime( resultSet.getString( 12));			returnData.setModifyperson( resultSet.getString( 13));			returnData.setDeleteflag( resultSet.getString( 14));			returnData.setFormid( resultSet.getString( 15));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,contractid,materialid,deliverydate,newdeliverydate,newupdatedate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseOrderDeliveryDateHistory  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,contractid,materialid,deliverydate,newdeliverydate,newupdatedate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseOrderDeliveryDateHistory"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_PurchaseOrderDeliveryDateHistoryData returnData=new B_PurchaseOrderDeliveryDateHistoryData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setContractid( resultSet.getString( 2));				returnData.setMaterialid( resultSet.getString( 3));				returnData.setDeliverydate( resultSet.getString( 4));				returnData.setNewdeliverydate( resultSet.getString( 5));				returnData.setNewupdatedate( resultSet.getString( 6));				returnData.setRemarks( resultSet.getString( 7));				returnData.setDeptguid( resultSet.getString( 8));				returnData.setCreatetime( resultSet.getString( 9));				returnData.setCreateperson( resultSet.getString( 10));				returnData.setCreateunitid( resultSet.getString( 11));				returnData.setModifytime( resultSet.getString( 12));				returnData.setModifyperson( resultSet.getString( 13));				returnData.setDeleteflag( resultSet.getString( 14));				returnData.setFormid( resultSet.getString( 15));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,contractid,materialid,deliverydate,newdeliverydate,newupdatedate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseOrderDeliveryDateHistory" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_PurchaseOrderDeliveryDateHistory SET recordid= ? , contractid= ? , materialid= ? , deliverydate= ? , newdeliverydate= ? , newupdatedate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getContractid());			statement.setString( 3,beanData.getMaterialid());			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 4, Types.DATE);			}			else			{				statement.setString( 4, beanData.getDeliverydate());			}			if (beanData.getNewdeliverydate()==null || beanData.getNewdeliverydate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getNewdeliverydate());			}			if (beanData.getNewupdatedate()==null || beanData.getNewupdatedate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getNewupdatedate());			}			statement.setString( 7,beanData.getRemarks());			statement.setString( 8,beanData.getDeptguid());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getFormid());
			statement.setString( 16,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchaseOrderDeliveryDateHistory SET recordid= ? , contractid= ? , materialid= ? , deliverydate= ? , newdeliverydate= ? , newupdatedate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_PurchaseOrderDeliveryDateHistory SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Contractid = " + "'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Deliverydate = " + "'" + nullString(beanData.getDeliverydate()) + "',");			bufSQL.append("Newdeliverydate = " + "'" + nullString(beanData.getNewdeliverydate()) + "',");			bufSQL.append("Newupdatedate = " + "'" + nullString(beanData.getNewupdatedate()) + "',");			bufSQL.append("Remarks = " + "'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchaseOrderDeliveryDateHistoryData beanData = (B_PurchaseOrderDeliveryDateHistoryData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_PurchaseOrderDeliveryDateHistory SET recordid= ? , contractid= ? , materialid= ? , deliverydate= ? , newdeliverydate= ? , newupdatedate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getContractid());			statement.setString( 3,beanData.getMaterialid());			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 4, Types.DATE);			}			else			{				statement.setString( 4, beanData.getDeliverydate());			}			if (beanData.getNewdeliverydate()==null || beanData.getNewdeliverydate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getNewdeliverydate());			}			if (beanData.getNewupdatedate()==null || beanData.getNewupdatedate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getNewupdatedate());			}			statement.setString( 7,beanData.getRemarks());			statement.setString( 8,beanData.getDeptguid());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getFormid());
			statement.setString( 16,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchaseOrderDeliveryDateHistory SET recordid= ? , contractid= ? , materialid= ? , deliverydate= ? , newdeliverydate= ? , newupdatedate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_PurchaseOrderDeliveryDateHistoryData beanData = (B_PurchaseOrderDeliveryDateHistoryData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_PurchaseOrderDeliveryDateHistory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_PurchaseOrderDeliveryDateHistory WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
