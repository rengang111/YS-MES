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
public class B_PurchaseOrderDao extends BaseAbstractDao
{
	public B_PurchaseOrderData beanData=new B_PurchaseOrderData();
	public B_PurchaseOrderDao()
	{
	}
	public B_PurchaseOrderDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_PurchaseOrderData)FindByPrimaryKey(beanData);
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
		B_PurchaseOrderData beanData = (B_PurchaseOrderData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchaseOrder( recordid,ysid,materialid,supplierid,contractid,typeparentid,typeserial,supplierparentid,supplierserial,total,purchasedate,deliverydate,memo,status,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getContractid());			statement.setString( 6,beanData.getTypeparentid());			statement.setString( 7,beanData.getTypeserial());			statement.setString( 8,beanData.getSupplierparentid());			statement.setString( 9,beanData.getSupplierserial());			statement.setString( 10,beanData.getTotal());			if (beanData.getPurchasedate()==null || beanData.getPurchasedate().trim().equals(""))			{				statement.setNull( 11, Types.DATE);			}			else			{				statement.setTimestamp( 11, getDateTimeFromString(beanData.getPurchasedate()));			}			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 12, Types.DATE);			}			else			{				statement.setTimestamp( 12, getDateTimeFromString(beanData.getDeliverydate()));			}			statement.setString( 13,beanData.getMemo());			statement.setString( 14,beanData.getStatus());			statement.setInt( 15,beanData.getVersion());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getCreatetime());			statement.setString( 18,beanData.getCreateperson());			statement.setString( 19,beanData.getCreateunitid());			statement.setString( 20,beanData.getModifytime());			statement.setString( 21,beanData.getModifyperson());			statement.setString( 22,beanData.getDeleteflag());			statement.setString( 23,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchaseOrder( recordid,ysid,materialid,supplierid,contractid,typeparentid,typeserial,supplierparentid,supplierserial,total,purchasedate,deliverydate,memo,status,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_PurchaseOrder( recordid,ysid,materialid,supplierid,contractid,typeparentid,typeserial,supplierparentid,supplierserial,total,purchasedate,deliverydate,memo,status,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("'" + nullString(beanData.getTypeparentid()) + "',");			bufSQL.append("'" + nullString(beanData.getTypeserial()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierparentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierserial()) + "',");			bufSQL.append("'" + nullString(beanData.getTotal()) + "',");			bufSQL.append("'" + nullString(beanData.getPurchasedate()) + "',");			bufSQL.append("'" + nullString(beanData.getDeliverydate()) + "',");			bufSQL.append("'" + nullString(beanData.getMemo()) + "',");			bufSQL.append("'" + nullString(beanData.getStatus()) + "',");			bufSQL.append(beanData.getVersion() + ",");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchaseOrderData beanData = (B_PurchaseOrderData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchaseOrder( recordid,ysid,materialid,supplierid,contractid,typeparentid,typeserial,supplierparentid,supplierserial,total,purchasedate,deliverydate,memo,status,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getContractid());			statement.setString( 6,beanData.getTypeparentid());			statement.setString( 7,beanData.getTypeserial());			statement.setString( 8,beanData.getSupplierparentid());			statement.setString( 9,beanData.getSupplierserial());			statement.setString( 10,beanData.getTotal());			if (beanData.getPurchasedate()==null || beanData.getPurchasedate().trim().equals(""))			{				statement.setNull( 11, Types.DATE);			}			else			{				statement.setTimestamp( 11, getDateTimeFromString(beanData.getPurchasedate()));			}			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 12, Types.DATE);			}			else			{				statement.setTimestamp( 12, getDateTimeFromString(beanData.getDeliverydate()));			}			statement.setString( 13,beanData.getMemo());			statement.setString( 14,beanData.getStatus());			statement.setInt( 15,beanData.getVersion());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getCreatetime());			statement.setString( 18,beanData.getCreateperson());			statement.setString( 19,beanData.getCreateunitid());			statement.setString( 20,beanData.getModifytime());			statement.setString( 21,beanData.getModifyperson());			statement.setString( 22,beanData.getDeleteflag());			statement.setString( 23,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchaseOrder( recordid,ysid,materialid,supplierid,contractid,typeparentid,typeserial,supplierparentid,supplierserial,total,purchasedate,deliverydate,memo,status,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_PurchaseOrderData beanData = (B_PurchaseOrderData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_PurchaseOrder WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchaseOrder: "+ e.toString());
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
		B_PurchaseOrderData beanData = (B_PurchaseOrderData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_PurchaseOrder WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_PurchaseOrder"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchaseOrder: "+ e.toString());
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
		B_PurchaseOrderData beanData = (B_PurchaseOrderData) beanDataTmp; 
		B_PurchaseOrderData returnData=new B_PurchaseOrderData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,ysid,materialid,supplierid,contractid,typeparentid,typeserial,supplierparentid,supplierserial,total,purchasedate,deliverydate,memo,status,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseOrder WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setYsid( resultSet.getString( 2));			returnData.setMaterialid( resultSet.getString( 3));			returnData.setSupplierid( resultSet.getString( 4));			returnData.setContractid( resultSet.getString( 5));			returnData.setTypeparentid( resultSet.getString( 6));			returnData.setTypeserial( resultSet.getString( 7));			returnData.setSupplierparentid( resultSet.getString( 8));			returnData.setSupplierserial( resultSet.getString( 9));			returnData.setTotal( resultSet.getString( 10));			returnData.setPurchasedate( resultSet.getString( 11));			returnData.setDeliverydate( resultSet.getString( 12));			returnData.setMemo( resultSet.getString( 13));			returnData.setStatus( resultSet.getString( 14));			returnData.setVersion( resultSet.getInt( 15));			returnData.setDeptguid( resultSet.getString( 16));			returnData.setCreatetime( resultSet.getString( 17));			returnData.setCreateperson( resultSet.getString( 18));			returnData.setCreateunitid( resultSet.getString( 19));			returnData.setModifytime( resultSet.getString( 20));			returnData.setModifyperson( resultSet.getString( 21));			returnData.setDeleteflag( resultSet.getString( 22));			returnData.setFormid( resultSet.getString( 23));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,materialid,supplierid,contractid,typeparentid,typeserial,supplierparentid,supplierserial,total,purchasedate,deliverydate,memo,status,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseOrder  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,ysid,materialid,supplierid,contractid,typeparentid,typeserial,supplierparentid,supplierserial,total,purchasedate,deliverydate,memo,status,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseOrder"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_PurchaseOrderData returnData=new B_PurchaseOrderData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setYsid( resultSet.getString( 2));				returnData.setMaterialid( resultSet.getString( 3));				returnData.setSupplierid( resultSet.getString( 4));				returnData.setContractid( resultSet.getString( 5));				returnData.setTypeparentid( resultSet.getString( 6));				returnData.setTypeserial( resultSet.getString( 7));				returnData.setSupplierparentid( resultSet.getString( 8));				returnData.setSupplierserial( resultSet.getString( 9));				returnData.setTotal( resultSet.getString( 10));				returnData.setPurchasedate( resultSet.getString( 11));				returnData.setDeliverydate( resultSet.getString( 12));				returnData.setMemo( resultSet.getString( 13));				returnData.setStatus( resultSet.getString( 14));				returnData.setVersion( resultSet.getInt( 15));				returnData.setDeptguid( resultSet.getString( 16));				returnData.setCreatetime( resultSet.getString( 17));				returnData.setCreateperson( resultSet.getString( 18));				returnData.setCreateunitid( resultSet.getString( 19));				returnData.setModifytime( resultSet.getString( 20));				returnData.setModifyperson( resultSet.getString( 21));				returnData.setDeleteflag( resultSet.getString( 22));				returnData.setFormid( resultSet.getString( 23));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,materialid,supplierid,contractid,typeparentid,typeserial,supplierparentid,supplierserial,total,purchasedate,deliverydate,memo,status,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchaseOrder" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_PurchaseOrder SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , contractid= ? , typeparentid= ? , typeserial= ? , supplierparentid= ? , supplierserial= ? , total= ? , purchasedate= ? , deliverydate= ? , memo= ? , status= ? , version= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getContractid());			statement.setString( 6,beanData.getTypeparentid());			statement.setString( 7,beanData.getTypeserial());			statement.setString( 8,beanData.getSupplierparentid());			statement.setString( 9,beanData.getSupplierserial());			statement.setString( 10,beanData.getTotal());			if (beanData.getPurchasedate()==null || beanData.getPurchasedate().trim().equals(""))			{				statement.setNull( 11, Types.DATE);			}			else			{				statement.setTimestamp( 11, getDateTimeFromString(beanData.getPurchasedate()));			}			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 12, Types.DATE);			}			else			{				statement.setTimestamp( 12, getDateTimeFromString(beanData.getDeliverydate()));			}			statement.setString( 13,beanData.getMemo());			statement.setString( 14,beanData.getStatus());			statement.setInt( 15,beanData.getVersion());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getCreatetime());			statement.setString( 18,beanData.getCreateperson());			statement.setString( 19,beanData.getCreateunitid());			statement.setString( 20,beanData.getModifytime());			statement.setString( 21,beanData.getModifyperson());			statement.setString( 22,beanData.getDeleteflag());			statement.setString( 23,beanData.getFormid());
			statement.setString( 24,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchaseOrder SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , contractid= ? , typeparentid= ? , typeserial= ? , supplierparentid= ? , supplierserial= ? , total= ? , purchasedate= ? , deliverydate= ? , memo= ? , status= ? , version= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_PurchaseOrder SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Contractid = " + "'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("Typeparentid = " + "'" + nullString(beanData.getTypeparentid()) + "',");			bufSQL.append("Typeserial = " + "'" + nullString(beanData.getTypeserial()) + "',");			bufSQL.append("Supplierparentid = " + "'" + nullString(beanData.getSupplierparentid()) + "',");			bufSQL.append("Supplierserial = " + "'" + nullString(beanData.getSupplierserial()) + "',");			bufSQL.append("Total = " + "'" + nullString(beanData.getTotal()) + "',");			bufSQL.append("Purchasedate = " + "'" + nullString(beanData.getPurchasedate()) + "',");			bufSQL.append("Deliverydate = " + "'" + nullString(beanData.getDeliverydate()) + "',");			bufSQL.append("Memo = " + "'" + nullString(beanData.getMemo()) + "',");			bufSQL.append("Status = " + "'" + nullString(beanData.getStatus()) + "',");			bufSQL.append("Version = " + beanData.getVersion() + ",");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchaseOrderData beanData = (B_PurchaseOrderData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_PurchaseOrder SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , contractid= ? , typeparentid= ? , typeserial= ? , supplierparentid= ? , supplierserial= ? , total= ? , purchasedate= ? , deliverydate= ? , memo= ? , status= ? , version= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getContractid());			statement.setString( 6,beanData.getTypeparentid());			statement.setString( 7,beanData.getTypeserial());			statement.setString( 8,beanData.getSupplierparentid());			statement.setString( 9,beanData.getSupplierserial());			statement.setString( 10,beanData.getTotal());			if (beanData.getPurchasedate()==null || beanData.getPurchasedate().trim().equals(""))			{				statement.setNull( 11, Types.DATE);			}			else			{				statement.setTimestamp( 11, getDateTimeFromString(beanData.getPurchasedate()));			}			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 12, Types.DATE);			}			else			{				statement.setTimestamp( 12, getDateTimeFromString(beanData.getDeliverydate()));			}			statement.setString( 13,beanData.getMemo());			statement.setString( 14,beanData.getStatus());			statement.setInt( 15,beanData.getVersion());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getCreatetime());			statement.setString( 18,beanData.getCreateperson());			statement.setString( 19,beanData.getCreateunitid());			statement.setString( 20,beanData.getModifytime());			statement.setString( 21,beanData.getModifyperson());			statement.setString( 22,beanData.getDeleteflag());			statement.setString( 23,beanData.getFormid());
			statement.setString( 24,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchaseOrder SET recordid= ? , ysid= ? , materialid= ? , supplierid= ? , contractid= ? , typeparentid= ? , typeserial= ? , supplierparentid= ? , supplierserial= ? , total= ? , purchasedate= ? , deliverydate= ? , memo= ? , status= ? , version= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_PurchaseOrderData beanData = (B_PurchaseOrderData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_PurchaseOrder WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_PurchaseOrder WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
