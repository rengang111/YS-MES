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
public class B_PurchaseStockInDao extends BaseAbstractDao
{
	public B_PurchaseStockInData beanData=new B_PurchaseStockInData();
	public B_PurchaseStockInDao()
	{
	}
	public B_PurchaseStockInDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_PurchaseStockInData)FindByPrimaryKey(beanData);
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
		B_PurchaseStockInData beanData = (B_PurchaseStockInData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchaseStockIn( recordid,receiptid,parentid,subid,ysid,contractid,arrivelid,supplierid,checkindate,keepuser,packagnumber,originalreceiptid,stockintype,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceiptid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivelid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getCheckindate());			statement.setString( 10,beanData.getKeepuser());			statement.setString( 11,beanData.getPackagnumber());			statement.setString( 12,beanData.getOriginalreceiptid());			statement.setString( 13,beanData.getStockintype());			statement.setString( 14,beanData.getRemarks());			statement.setString( 15,beanData.getDeptguid());			statement.setString( 16,beanData.getCreatetime());			statement.setString( 17,beanData.getCreateperson());			statement.setString( 18,beanData.getCreateunitid());			statement.setString( 19,beanData.getModifytime());			statement.setString( 20,beanData.getModifyperson());			statement.setString( 21,beanData.getDeleteflag());			statement.setString( 22,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchaseStockIn( recordid,receiptid,parentid,subid,ysid,contractid,arrivelid,supplierid,checkindate,keepuser,packagnumber,originalreceiptid,stockintype,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_PurchaseStockIn( recordid,receiptid,parentid,subid,ysid,contractid,arrivelid,supplierid,checkindate,keepuser,packagnumber,originalreceiptid,stockintype,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getReceiptid()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("'" + nullString(beanData.getArrivelid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getCheckindate()) + "',");			bufSQL.append("'" + nullString(beanData.getKeepuser()) + "',");			bufSQL.append("'" + nullString(beanData.getPackagnumber()) + "',");			bufSQL.append("'" + nullString(beanData.getOriginalreceiptid()) + "',");			bufSQL.append("'" + nullString(beanData.getStockintype()) + "',");			bufSQL.append("'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchaseStockInData beanData = (B_PurchaseStockInData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchaseStockIn( recordid,receiptid,parentid,subid,ysid,contractid,arrivelid,supplierid,checkindate,keepuser,packagnumber,originalreceiptid,stockintype,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceiptid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivelid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getCheckindate());			statement.setString( 10,beanData.getKeepuser());			statement.setString( 11,beanData.getPackagnumber());			statement.setString( 12,beanData.getOriginalreceiptid());			statement.setString( 13,beanData.getStockintype());			statement.setString( 14,beanData.getRemarks());			statement.setString( 15,beanData.getDeptguid());			statement.setString( 16,beanData.getCreatetime());			statement.setString( 17,beanData.getCreateperson());			statement.setString( 18,beanData.getCreateunitid());			statement.setString( 19,beanData.getModifytime());			statement.setString( 20,beanData.getModifyperson());			statement.setString( 21,beanData.getDeleteflag());			statement.setString( 22,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchaseStockIn( recordid,receiptid,parentid,subid,ysid,contractid,arrivelid,supplierid,checkindate,keepuser,packagnumber,originalreceiptid,stockintype,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_PurchaseStockInData beanData = (B_PurchaseStockInData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_PurchaseStockIn WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchaseStockIn: "+ e.toString());
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
		B_PurchaseStockInData beanData = (B_PurchaseStockInData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_PurchaseStockIn WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_PurchaseStockIn"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchaseStockIn: "+ e.toString());
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
		B_PurchaseStockInData beanData = (B_PurchaseStockInData) beanDataTmp; 
		B_PurchaseStockInData returnData=new B_PurchaseStockInData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,receiptid,parentid,subid,ysid,contractid,arrivelid,supplierid,checkindate,keepuser,packagnumber,originalreceiptid,stockintype,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseStockIn WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setReceiptid( resultSet.getString( 2));			returnData.setParentid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setYsid( resultSet.getString( 5));			returnData.setContractid( resultSet.getString( 6));			returnData.setArrivelid( resultSet.getString( 7));			returnData.setSupplierid( resultSet.getString( 8));			returnData.setCheckindate( resultSet.getString( 9));			returnData.setKeepuser( resultSet.getString( 10));			returnData.setPackagnumber( resultSet.getString( 11));			returnData.setOriginalreceiptid( resultSet.getString( 12));			returnData.setStockintype( resultSet.getString( 13));			returnData.setRemarks( resultSet.getString( 14));			returnData.setDeptguid( resultSet.getString( 15));			returnData.setCreatetime( resultSet.getString( 16));			returnData.setCreateperson( resultSet.getString( 17));			returnData.setCreateunitid( resultSet.getString( 18));			returnData.setModifytime( resultSet.getString( 19));			returnData.setModifyperson( resultSet.getString( 20));			returnData.setDeleteflag( resultSet.getString( 21));			returnData.setFormid( resultSet.getString( 22));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,receiptid,parentid,subid,ysid,contractid,arrivelid,supplierid,checkindate,keepuser,packagnumber,originalreceiptid,stockintype,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseStockIn  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,receiptid,parentid,subid,ysid,contractid,arrivelid,supplierid,checkindate,keepuser,packagnumber,originalreceiptid,stockintype,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseStockIn"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_PurchaseStockInData returnData=new B_PurchaseStockInData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setReceiptid( resultSet.getString( 2));				returnData.setParentid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setYsid( resultSet.getString( 5));				returnData.setContractid( resultSet.getString( 6));				returnData.setArrivelid( resultSet.getString( 7));				returnData.setSupplierid( resultSet.getString( 8));				returnData.setCheckindate( resultSet.getString( 9));				returnData.setKeepuser( resultSet.getString( 10));				returnData.setPackagnumber( resultSet.getString( 11));				returnData.setOriginalreceiptid( resultSet.getString( 12));				returnData.setStockintype( resultSet.getString( 13));				returnData.setRemarks( resultSet.getString( 14));				returnData.setDeptguid( resultSet.getString( 15));				returnData.setCreatetime( resultSet.getString( 16));				returnData.setCreateperson( resultSet.getString( 17));				returnData.setCreateunitid( resultSet.getString( 18));				returnData.setModifytime( resultSet.getString( 19));				returnData.setModifyperson( resultSet.getString( 20));				returnData.setDeleteflag( resultSet.getString( 21));				returnData.setFormid( resultSet.getString( 22));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,receiptid,parentid,subid,ysid,contractid,arrivelid,supplierid,checkindate,keepuser,packagnumber,originalreceiptid,stockintype,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseStockIn" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_PurchaseStockIn SET recordid= ? , receiptid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivelid= ? , supplierid= ? , checkindate= ? , keepuser= ? , packagnumber= ? , originalreceiptid= ? , stockintype= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceiptid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivelid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getCheckindate());			statement.setString( 10,beanData.getKeepuser());			statement.setString( 11,beanData.getPackagnumber());			statement.setString( 12,beanData.getOriginalreceiptid());			statement.setString( 13,beanData.getStockintype());			statement.setString( 14,beanData.getRemarks());			statement.setString( 15,beanData.getDeptguid());			statement.setString( 16,beanData.getCreatetime());			statement.setString( 17,beanData.getCreateperson());			statement.setString( 18,beanData.getCreateunitid());			statement.setString( 19,beanData.getModifytime());			statement.setString( 20,beanData.getModifyperson());			statement.setString( 21,beanData.getDeleteflag());			statement.setString( 22,beanData.getFormid());
			statement.setString( 23,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchaseStockIn SET recordid= ? , receiptid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivelid= ? , supplierid= ? , checkindate= ? , keepuser= ? , packagnumber= ? , originalreceiptid= ? , stockintype= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_PurchaseStockIn SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Receiptid = " + "'" + nullString(beanData.getReceiptid()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Contractid = " + "'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("Arrivelid = " + "'" + nullString(beanData.getArrivelid()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Checkindate = " + "'" + nullString(beanData.getCheckindate()) + "',");			bufSQL.append("Keepuser = " + "'" + nullString(beanData.getKeepuser()) + "',");			bufSQL.append("Packagnumber = " + "'" + nullString(beanData.getPackagnumber()) + "',");			bufSQL.append("Originalreceiptid = " + "'" + nullString(beanData.getOriginalreceiptid()) + "',");			bufSQL.append("Stockintype = " + "'" + nullString(beanData.getStockintype()) + "',");			bufSQL.append("Remarks = " + "'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchaseStockInData beanData = (B_PurchaseStockInData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_PurchaseStockIn SET recordid= ? , receiptid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivelid= ? , supplierid= ? , checkindate= ? , keepuser= ? , packagnumber= ? , originalreceiptid= ? , stockintype= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceiptid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivelid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getCheckindate());			statement.setString( 10,beanData.getKeepuser());			statement.setString( 11,beanData.getPackagnumber());			statement.setString( 12,beanData.getOriginalreceiptid());			statement.setString( 13,beanData.getStockintype());			statement.setString( 14,beanData.getRemarks());			statement.setString( 15,beanData.getDeptguid());			statement.setString( 16,beanData.getCreatetime());			statement.setString( 17,beanData.getCreateperson());			statement.setString( 18,beanData.getCreateunitid());			statement.setString( 19,beanData.getModifytime());			statement.setString( 20,beanData.getModifyperson());			statement.setString( 21,beanData.getDeleteflag());			statement.setString( 22,beanData.getFormid());
			statement.setString( 23,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchaseStockIn SET recordid= ? , receiptid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivelid= ? , supplierid= ? , checkindate= ? , keepuser= ? , packagnumber= ? , originalreceiptid= ? , stockintype= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_PurchaseStockInData beanData = (B_PurchaseStockInData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_PurchaseStockIn WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_PurchaseStockIn WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
