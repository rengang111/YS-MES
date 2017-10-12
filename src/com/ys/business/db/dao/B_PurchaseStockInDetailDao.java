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
public class B_PurchaseStockInDetailDao extends BaseAbstractDao
{
	public B_PurchaseStockInDetailData beanData=new B_PurchaseStockInDetailData();
	public B_PurchaseStockInDetailDao()
	{
	}
	public B_PurchaseStockInDetailDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_PurchaseStockInDetailData)FindByPrimaryKey(beanData);
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
		B_PurchaseStockInDetailData beanData = (B_PurchaseStockInDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchaseStockInDetail( recordid,receiptid,materialid,quantity,packaging,packagnumber,price,depotid,areanumber,shelfnumber,placenumber,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceiptid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getQuantity());			statement.setString( 5,beanData.getPackaging());			statement.setString( 6,beanData.getPackagnumber());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getDepotid());			statement.setString( 9,beanData.getAreanumber());			statement.setString( 10,beanData.getShelfnumber());			statement.setString( 11,beanData.getPlacenumber());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchaseStockInDetail( recordid,receiptid,materialid,quantity,packaging,packagnumber,price,depotid,areanumber,shelfnumber,placenumber,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_PurchaseStockInDetail( recordid,receiptid,materialid,quantity,packaging,packagnumber,price,depotid,areanumber,shelfnumber,placenumber,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getReceiptid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("'" + nullString(beanData.getPackaging()) + "',");			bufSQL.append("'" + nullString(beanData.getPackagnumber()) + "',");			bufSQL.append("'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("'" + nullString(beanData.getDepotid()) + "',");			bufSQL.append("'" + nullString(beanData.getAreanumber()) + "',");			bufSQL.append("'" + nullString(beanData.getShelfnumber()) + "',");			bufSQL.append("'" + nullString(beanData.getPlacenumber()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchaseStockInDetailData beanData = (B_PurchaseStockInDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchaseStockInDetail( recordid,receiptid,materialid,quantity,packaging,packagnumber,price,depotid,areanumber,shelfnumber,placenumber,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceiptid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getQuantity());			statement.setString( 5,beanData.getPackaging());			statement.setString( 6,beanData.getPackagnumber());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getDepotid());			statement.setString( 9,beanData.getAreanumber());			statement.setString( 10,beanData.getShelfnumber());			statement.setString( 11,beanData.getPlacenumber());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchaseStockInDetail( recordid,receiptid,materialid,quantity,packaging,packagnumber,price,depotid,areanumber,shelfnumber,placenumber,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_PurchaseStockInDetailData beanData = (B_PurchaseStockInDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_PurchaseStockInDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchaseStockInDetail: "+ e.toString());
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
		B_PurchaseStockInDetailData beanData = (B_PurchaseStockInDetailData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_PurchaseStockInDetail WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_PurchaseStockInDetail"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchaseStockInDetail: "+ e.toString());
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
		B_PurchaseStockInDetailData beanData = (B_PurchaseStockInDetailData) beanDataTmp; 
		B_PurchaseStockInDetailData returnData=new B_PurchaseStockInDetailData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,receiptid,materialid,quantity,packaging,packagnumber,price,depotid,areanumber,shelfnumber,placenumber,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseStockInDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setReceiptid( resultSet.getString( 2));			returnData.setMaterialid( resultSet.getString( 3));			returnData.setQuantity( resultSet.getString( 4));			returnData.setPackaging( resultSet.getString( 5));			returnData.setPackagnumber( resultSet.getString( 6));			returnData.setPrice( resultSet.getString( 7));			returnData.setDepotid( resultSet.getString( 8));			returnData.setAreanumber( resultSet.getString( 9));			returnData.setShelfnumber( resultSet.getString( 10));			returnData.setPlacenumber( resultSet.getString( 11));			returnData.setDeptguid( resultSet.getString( 12));			returnData.setCreatetime( resultSet.getString( 13));			returnData.setCreateperson( resultSet.getString( 14));			returnData.setCreateunitid( resultSet.getString( 15));			returnData.setModifytime( resultSet.getString( 16));			returnData.setModifyperson( resultSet.getString( 17));			returnData.setDeleteflag( resultSet.getString( 18));			returnData.setFormid( resultSet.getString( 19));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,receiptid,materialid,quantity,packaging,packagnumber,price,depotid,areanumber,shelfnumber,placenumber,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseStockInDetail  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,receiptid,materialid,quantity,packaging,packagnumber,price,depotid,areanumber,shelfnumber,placenumber,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseStockInDetail"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_PurchaseStockInDetailData returnData=new B_PurchaseStockInDetailData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setReceiptid( resultSet.getString( 2));				returnData.setMaterialid( resultSet.getString( 3));				returnData.setQuantity( resultSet.getString( 4));				returnData.setPackaging( resultSet.getString( 5));				returnData.setPackagnumber( resultSet.getString( 6));				returnData.setPrice( resultSet.getString( 7));				returnData.setDepotid( resultSet.getString( 8));				returnData.setAreanumber( resultSet.getString( 9));				returnData.setShelfnumber( resultSet.getString( 10));				returnData.setPlacenumber( resultSet.getString( 11));				returnData.setDeptguid( resultSet.getString( 12));				returnData.setCreatetime( resultSet.getString( 13));				returnData.setCreateperson( resultSet.getString( 14));				returnData.setCreateunitid( resultSet.getString( 15));				returnData.setModifytime( resultSet.getString( 16));				returnData.setModifyperson( resultSet.getString( 17));				returnData.setDeleteflag( resultSet.getString( 18));				returnData.setFormid( resultSet.getString( 19));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,receiptid,materialid,quantity,packaging,packagnumber,price,depotid,areanumber,shelfnumber,placenumber,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseStockInDetail" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_PurchaseStockInDetail SET recordid= ? , receiptid= ? , materialid= ? , quantity= ? , packaging= ? , packagnumber= ? , price= ? , depotid= ? , areanumber= ? , shelfnumber= ? , placenumber= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceiptid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getQuantity());			statement.setString( 5,beanData.getPackaging());			statement.setString( 6,beanData.getPackagnumber());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getDepotid());			statement.setString( 9,beanData.getAreanumber());			statement.setString( 10,beanData.getShelfnumber());			statement.setString( 11,beanData.getPlacenumber());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchaseStockInDetail SET recordid= ? , receiptid= ? , materialid= ? , quantity= ? , packaging= ? , packagnumber= ? , price= ? , depotid= ? , areanumber= ? , shelfnumber= ? , placenumber= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_PurchaseStockInDetail SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Receiptid = " + "'" + nullString(beanData.getReceiptid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Quantity = " + "'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("Packaging = " + "'" + nullString(beanData.getPackaging()) + "',");			bufSQL.append("Packagnumber = " + "'" + nullString(beanData.getPackagnumber()) + "',");			bufSQL.append("Price = " + "'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("Depotid = " + "'" + nullString(beanData.getDepotid()) + "',");			bufSQL.append("Areanumber = " + "'" + nullString(beanData.getAreanumber()) + "',");			bufSQL.append("Shelfnumber = " + "'" + nullString(beanData.getShelfnumber()) + "',");			bufSQL.append("Placenumber = " + "'" + nullString(beanData.getPlacenumber()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchaseStockInDetailData beanData = (B_PurchaseStockInDetailData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_PurchaseStockInDetail SET recordid= ? , receiptid= ? , materialid= ? , quantity= ? , packaging= ? , packagnumber= ? , price= ? , depotid= ? , areanumber= ? , shelfnumber= ? , placenumber= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReceiptid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getQuantity());			statement.setString( 5,beanData.getPackaging());			statement.setString( 6,beanData.getPackagnumber());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getDepotid());			statement.setString( 9,beanData.getAreanumber());			statement.setString( 10,beanData.getShelfnumber());			statement.setString( 11,beanData.getPlacenumber());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchaseStockInDetail SET recordid= ? , receiptid= ? , materialid= ? , quantity= ? , packaging= ? , packagnumber= ? , price= ? , depotid= ? , areanumber= ? , shelfnumber= ? , placenumber= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_PurchaseStockInDetailData beanData = (B_PurchaseStockInDetailData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_PurchaseStockInDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_PurchaseStockInDetail WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
