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
public class B_PaymentHistoryDao extends BaseAbstractDao
{
	public B_PaymentHistoryData beanData=new B_PaymentHistoryData();
	public B_PaymentHistoryDao()
	{
	}
	public B_PaymentHistoryDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_PaymentHistoryData)FindByPrimaryKey(beanData);
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
		B_PaymentHistoryData beanData = (B_PaymentHistoryData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PaymentHistory( recordid,paymenthistoryid,parentid,subid,paymentid,paymentmethod,currency,paymentamount,finishuser,finishdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPaymenthistoryid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getPaymentid());			statement.setString( 6,beanData.getPaymentmethod());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getPaymentamount());			statement.setString( 9,beanData.getFinishuser());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getFinishdate());			}			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PaymentHistory( recordid,paymenthistoryid,parentid,subid,paymentid,paymentmethod,currency,paymentamount,finishuser,finishdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_PaymentHistory( recordid,paymenthistoryid,parentid,subid,paymentid,paymentmethod,currency,paymentamount,finishuser,finishdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymenthistoryid()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentid()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentmethod()) + "',");			bufSQL.append("'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentamount()) + "',");			bufSQL.append("'" + nullString(beanData.getFinishuser()) + "',");			bufSQL.append("'" + nullString(beanData.getFinishdate()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_PaymentHistoryData beanData = (B_PaymentHistoryData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PaymentHistory( recordid,paymenthistoryid,parentid,subid,paymentid,paymentmethod,currency,paymentamount,finishuser,finishdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPaymenthistoryid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getPaymentid());			statement.setString( 6,beanData.getPaymentmethod());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getPaymentamount());			statement.setString( 9,beanData.getFinishuser());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getFinishdate());			}			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PaymentHistory( recordid,paymenthistoryid,parentid,subid,paymentid,paymentmethod,currency,paymentamount,finishuser,finishdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_PaymentHistoryData beanData = (B_PaymentHistoryData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_PaymentHistory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PaymentHistory: "+ e.toString());
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
		B_PaymentHistoryData beanData = (B_PaymentHistoryData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_PaymentHistory WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_PaymentHistory"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PaymentHistory: "+ e.toString());
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
		B_PaymentHistoryData beanData = (B_PaymentHistoryData) beanDataTmp; 
		B_PaymentHistoryData returnData=new B_PaymentHistoryData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,paymenthistoryid,parentid,subid,paymentid,paymentmethod,currency,paymentamount,finishuser,finishdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PaymentHistory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setPaymenthistoryid( resultSet.getString( 2));			returnData.setParentid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setPaymentid( resultSet.getString( 5));			returnData.setPaymentmethod( resultSet.getString( 6));			returnData.setCurrency( resultSet.getString( 7));			returnData.setPaymentamount( resultSet.getString( 8));			returnData.setFinishuser( resultSet.getString( 9));			returnData.setFinishdate( resultSet.getString( 10));			returnData.setDeptguid( resultSet.getString( 11));			returnData.setCreatetime( resultSet.getString( 12));			returnData.setCreateperson( resultSet.getString( 13));			returnData.setCreateunitid( resultSet.getString( 14));			returnData.setModifytime( resultSet.getString( 15));			returnData.setModifyperson( resultSet.getString( 16));			returnData.setDeleteflag( resultSet.getString( 17));			returnData.setFormid( resultSet.getString( 18));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,paymenthistoryid,parentid,subid,paymentid,paymentmethod,currency,paymentamount,finishuser,finishdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PaymentHistory  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,paymenthistoryid,parentid,subid,paymentid,paymentmethod,currency,paymentamount,finishuser,finishdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PaymentHistory"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_PaymentHistoryData returnData=new B_PaymentHistoryData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setPaymenthistoryid( resultSet.getString( 2));				returnData.setParentid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setPaymentid( resultSet.getString( 5));				returnData.setPaymentmethod( resultSet.getString( 6));				returnData.setCurrency( resultSet.getString( 7));				returnData.setPaymentamount( resultSet.getString( 8));				returnData.setFinishuser( resultSet.getString( 9));				returnData.setFinishdate( resultSet.getString( 10));				returnData.setDeptguid( resultSet.getString( 11));				returnData.setCreatetime( resultSet.getString( 12));				returnData.setCreateperson( resultSet.getString( 13));				returnData.setCreateunitid( resultSet.getString( 14));				returnData.setModifytime( resultSet.getString( 15));				returnData.setModifyperson( resultSet.getString( 16));				returnData.setDeleteflag( resultSet.getString( 17));				returnData.setFormid( resultSet.getString( 18));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,paymenthistoryid,parentid,subid,paymentid,paymentmethod,currency,paymentamount,finishuser,finishdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PaymentHistory" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_PaymentHistory SET recordid= ? , paymenthistoryid= ? , parentid= ? , subid= ? , paymentid= ? , paymentmethod= ? , currency= ? , paymentamount= ? , finishuser= ? , finishdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPaymenthistoryid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getPaymentid());			statement.setString( 6,beanData.getPaymentmethod());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getPaymentamount());			statement.setString( 9,beanData.getFinishuser());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getFinishdate());			}			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			statement.setString( 19,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PaymentHistory SET recordid= ? , paymenthistoryid= ? , parentid= ? , subid= ? , paymentid= ? , paymentmethod= ? , currency= ? , paymentamount= ? , finishuser= ? , finishdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_PaymentHistory SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Paymenthistoryid = " + "'" + nullString(beanData.getPaymenthistoryid()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Paymentid = " + "'" + nullString(beanData.getPaymentid()) + "',");			bufSQL.append("Paymentmethod = " + "'" + nullString(beanData.getPaymentmethod()) + "',");			bufSQL.append("Currency = " + "'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("Paymentamount = " + "'" + nullString(beanData.getPaymentamount()) + "',");			bufSQL.append("Finishuser = " + "'" + nullString(beanData.getFinishuser()) + "',");			bufSQL.append("Finishdate = " + "'" + nullString(beanData.getFinishdate()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_PaymentHistoryData beanData = (B_PaymentHistoryData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_PaymentHistory SET recordid= ? , paymenthistoryid= ? , parentid= ? , subid= ? , paymentid= ? , paymentmethod= ? , currency= ? , paymentamount= ? , finishuser= ? , finishdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPaymenthistoryid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getPaymentid());			statement.setString( 6,beanData.getPaymentmethod());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getPaymentamount());			statement.setString( 9,beanData.getFinishuser());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getFinishdate());			}			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			statement.setString( 19,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PaymentHistory SET recordid= ? , paymenthistoryid= ? , parentid= ? , subid= ? , paymentid= ? , paymentmethod= ? , currency= ? , paymentamount= ? , finishuser= ? , finishdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_PaymentHistoryData beanData = (B_PaymentHistoryData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_PaymentHistory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_PaymentHistory WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
