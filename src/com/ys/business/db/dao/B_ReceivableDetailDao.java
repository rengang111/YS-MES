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
public class B_ReceivableDetailDao extends BaseAbstractDao
{
	public B_ReceivableDetailData beanData=new B_ReceivableDetailData();
	public B_ReceivableDetailDao()
	{
	}
	public B_ReceivableDetailDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_ReceivableDetailData)FindByPrimaryKey(beanData);
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
		B_ReceivableDetailData beanData = (B_ReceivableDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ReceivableDetail( recordid,receivableid,receivableserialid,subid,bankcode,actualamount,bankdeduction,customerdeduction,payee,collectiondate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceivableid());			statement.setString( 3,beanData.getReceivableserialid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getBankcode());			statement.setString( 6,beanData.getActualamount());			statement.setString( 7,beanData.getBankdeduction());			statement.setString( 8,beanData.getCustomerdeduction());			statement.setString( 9,beanData.getPayee());			if (beanData.getCollectiondate()==null || beanData.getCollectiondate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getCollectiondate());			}			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ReceivableDetail( recordid,receivableid,receivableserialid,subid,bankcode,actualamount,bankdeduction,customerdeduction,payee,collectiondate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_ReceivableDetail( recordid,receivableid,receivableserialid,subid,bankcode,actualamount,bankdeduction,customerdeduction,payee,collectiondate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getReceivableid()) + "',");			bufSQL.append("'" + nullString(beanData.getReceivableserialid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getBankcode()) + "',");			bufSQL.append("'" + nullString(beanData.getActualamount()) + "',");			bufSQL.append("'" + nullString(beanData.getBankdeduction()) + "',");			bufSQL.append("'" + nullString(beanData.getCustomerdeduction()) + "',");			bufSQL.append("'" + nullString(beanData.getPayee()) + "',");			bufSQL.append("'" + nullString(beanData.getCollectiondate()) + "',");			bufSQL.append("'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_ReceivableDetailData beanData = (B_ReceivableDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ReceivableDetail( recordid,receivableid,receivableserialid,subid,bankcode,actualamount,bankdeduction,customerdeduction,payee,collectiondate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceivableid());			statement.setString( 3,beanData.getReceivableserialid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getBankcode());			statement.setString( 6,beanData.getActualamount());			statement.setString( 7,beanData.getBankdeduction());			statement.setString( 8,beanData.getCustomerdeduction());			statement.setString( 9,beanData.getPayee());			if (beanData.getCollectiondate()==null || beanData.getCollectiondate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getCollectiondate());			}			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ReceivableDetail( recordid,receivableid,receivableserialid,subid,bankcode,actualamount,bankdeduction,customerdeduction,payee,collectiondate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_ReceivableDetailData beanData = (B_ReceivableDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_ReceivableDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ReceivableDetail: "+ e.toString());
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
		B_ReceivableDetailData beanData = (B_ReceivableDetailData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_ReceivableDetail WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_ReceivableDetail"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ReceivableDetail: "+ e.toString());
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
		B_ReceivableDetailData beanData = (B_ReceivableDetailData) beanDataTmp; 
		B_ReceivableDetailData returnData=new B_ReceivableDetailData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,receivableid,receivableserialid,subid,bankcode,actualamount,bankdeduction,customerdeduction,payee,collectiondate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ReceivableDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setReceivableid( resultSet.getString( 2));			returnData.setReceivableserialid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setBankcode( resultSet.getString( 5));			returnData.setActualamount( resultSet.getString( 6));			returnData.setBankdeduction( resultSet.getString( 7));			returnData.setCustomerdeduction( resultSet.getString( 8));			returnData.setPayee( resultSet.getString( 9));			returnData.setCollectiondate( resultSet.getString( 10));			returnData.setRemarks( resultSet.getString( 11));			returnData.setDeptguid( resultSet.getString( 12));			returnData.setCreatetime( resultSet.getString( 13));			returnData.setCreateperson( resultSet.getString( 14));			returnData.setCreateunitid( resultSet.getString( 15));			returnData.setModifytime( resultSet.getString( 16));			returnData.setModifyperson( resultSet.getString( 17));			returnData.setDeleteflag( resultSet.getString( 18));			returnData.setFormid( resultSet.getString( 19));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,receivableid,receivableserialid,subid,bankcode,actualamount,bankdeduction,customerdeduction,payee,collectiondate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ReceivableDetail  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,receivableid,receivableserialid,subid,bankcode,actualamount,bankdeduction,customerdeduction,payee,collectiondate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ReceivableDetail"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_ReceivableDetailData returnData=new B_ReceivableDetailData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setReceivableid( resultSet.getString( 2));				returnData.setReceivableserialid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setBankcode( resultSet.getString( 5));				returnData.setActualamount( resultSet.getString( 6));				returnData.setBankdeduction( resultSet.getString( 7));				returnData.setCustomerdeduction( resultSet.getString( 8));				returnData.setPayee( resultSet.getString( 9));				returnData.setCollectiondate( resultSet.getString( 10));				returnData.setRemarks( resultSet.getString( 11));				returnData.setDeptguid( resultSet.getString( 12));				returnData.setCreatetime( resultSet.getString( 13));				returnData.setCreateperson( resultSet.getString( 14));				returnData.setCreateunitid( resultSet.getString( 15));				returnData.setModifytime( resultSet.getString( 16));				returnData.setModifyperson( resultSet.getString( 17));				returnData.setDeleteflag( resultSet.getString( 18));				returnData.setFormid( resultSet.getString( 19));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,receivableid,receivableserialid,subid,bankcode,actualamount,bankdeduction,customerdeduction,payee,collectiondate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ReceivableDetail" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_ReceivableDetail SET recordid= ? , receivableid= ? , receivableserialid= ? , subid= ? , bankcode= ? , actualamount= ? , bankdeduction= ? , customerdeduction= ? , payee= ? , collectiondate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceivableid());			statement.setString( 3,beanData.getReceivableserialid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getBankcode());			statement.setString( 6,beanData.getActualamount());			statement.setString( 7,beanData.getBankdeduction());			statement.setString( 8,beanData.getCustomerdeduction());			statement.setString( 9,beanData.getPayee());			if (beanData.getCollectiondate()==null || beanData.getCollectiondate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getCollectiondate());			}			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ReceivableDetail SET recordid= ? , receivableid= ? , receivableserialid= ? , subid= ? , bankcode= ? , actualamount= ? , bankdeduction= ? , customerdeduction= ? , payee= ? , collectiondate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_ReceivableDetail SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Receivableid = " + "'" + nullString(beanData.getReceivableid()) + "',");			bufSQL.append("Receivableserialid = " + "'" + nullString(beanData.getReceivableserialid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Bankcode = " + "'" + nullString(beanData.getBankcode()) + "',");			bufSQL.append("Actualamount = " + "'" + nullString(beanData.getActualamount()) + "',");			bufSQL.append("Bankdeduction = " + "'" + nullString(beanData.getBankdeduction()) + "',");			bufSQL.append("Customerdeduction = " + "'" + nullString(beanData.getCustomerdeduction()) + "',");			bufSQL.append("Payee = " + "'" + nullString(beanData.getPayee()) + "',");			bufSQL.append("Collectiondate = " + "'" + nullString(beanData.getCollectiondate()) + "',");			bufSQL.append("Remarks = " + "'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_ReceivableDetailData beanData = (B_ReceivableDetailData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_ReceivableDetail SET recordid= ? , receivableid= ? , receivableserialid= ? , subid= ? , bankcode= ? , actualamount= ? , bankdeduction= ? , customerdeduction= ? , payee= ? , collectiondate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceivableid());			statement.setString( 3,beanData.getReceivableserialid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getBankcode());			statement.setString( 6,beanData.getActualamount());			statement.setString( 7,beanData.getBankdeduction());			statement.setString( 8,beanData.getCustomerdeduction());			statement.setString( 9,beanData.getPayee());			if (beanData.getCollectiondate()==null || beanData.getCollectiondate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getCollectiondate());			}			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ReceivableDetail SET recordid= ? , receivableid= ? , receivableserialid= ? , subid= ? , bankcode= ? , actualamount= ? , bankdeduction= ? , customerdeduction= ? , payee= ? , collectiondate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_ReceivableDetailData beanData = (B_ReceivableDetailData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_ReceivableDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_ReceivableDetail WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
