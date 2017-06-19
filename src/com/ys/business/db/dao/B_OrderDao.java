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
public class B_OrderDao extends BaseAbstractDao
{
	public B_OrderData beanData=new B_OrderData();
	public B_OrderDao()
	{
	}
	public B_OrderDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_OrderData)FindByPrimaryKey(beanData);
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
		B_OrderData beanData = (B_OrderData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Order( recordid,piid,parentid,subid,customerid,orderid,currency,shippingcase,paymentterm,loadingport,deliveryport,ordercompany,orderdate,deliverydate,totalprice,yskordertarget,team,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPiid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getCustomerid());			statement.setString( 6,beanData.getOrderid());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getShippingcase());			statement.setString( 9,beanData.getPaymentterm());			statement.setString( 10,beanData.getLoadingport());			statement.setString( 11,beanData.getDeliveryport());			statement.setString( 12,beanData.getOrdercompany());			if (beanData.getOrderdate()==null || beanData.getOrderdate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getOrderdate());			}			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getDeliverydate());			}			statement.setString( 15,beanData.getTotalprice());			statement.setString( 16,beanData.getYskordertarget());			statement.setString( 17,beanData.getTeam());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Order( recordid,piid,parentid,subid,customerid,orderid,currency,shippingcase,paymentterm,loadingport,deliveryport,ordercompany,orderdate,deliverydate,totalprice,yskordertarget,team,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_Order( recordid,piid,parentid,subid,customerid,orderid,currency,shippingcase,paymentterm,loadingport,deliveryport,ordercompany,orderdate,deliverydate,totalprice,yskordertarget,team,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getPiid()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getCustomerid()) + "',");			bufSQL.append("'" + nullString(beanData.getOrderid()) + "',");			bufSQL.append("'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getShippingcase()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentterm()) + "',");			bufSQL.append("'" + nullString(beanData.getLoadingport()) + "',");			bufSQL.append("'" + nullString(beanData.getDeliveryport()) + "',");			bufSQL.append("'" + nullString(beanData.getOrdercompany()) + "',");			bufSQL.append("'" + nullString(beanData.getOrderdate()) + "',");			bufSQL.append("'" + nullString(beanData.getDeliverydate()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("'" + nullString(beanData.getYskordertarget()) + "',");			bufSQL.append("'" + nullString(beanData.getTeam()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_OrderData beanData = (B_OrderData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Order( recordid,piid,parentid,subid,customerid,orderid,currency,shippingcase,paymentterm,loadingport,deliveryport,ordercompany,orderdate,deliverydate,totalprice,yskordertarget,team,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPiid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getCustomerid());			statement.setString( 6,beanData.getOrderid());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getShippingcase());			statement.setString( 9,beanData.getPaymentterm());			statement.setString( 10,beanData.getLoadingport());			statement.setString( 11,beanData.getDeliveryport());			statement.setString( 12,beanData.getOrdercompany());			if (beanData.getOrderdate()==null || beanData.getOrderdate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getOrderdate());			}			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getDeliverydate());			}			statement.setString( 15,beanData.getTotalprice());			statement.setString( 16,beanData.getYskordertarget());			statement.setString( 17,beanData.getTeam());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Order( recordid,piid,parentid,subid,customerid,orderid,currency,shippingcase,paymentterm,loadingport,deliveryport,ordercompany,orderdate,deliverydate,totalprice,yskordertarget,team,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_OrderData beanData = (B_OrderData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_Order WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Order: "+ e.toString());
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
		B_OrderData beanData = (B_OrderData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_Order WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_Order"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Order: "+ e.toString());
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
		B_OrderData beanData = (B_OrderData) beanDataTmp; 
		B_OrderData returnData=new B_OrderData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,piid,parentid,subid,customerid,orderid,currency,shippingcase,paymentterm,loadingport,deliveryport,ordercompany,orderdate,deliverydate,totalprice,yskordertarget,team,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Order WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setPiid( resultSet.getString( 2));			returnData.setParentid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setCustomerid( resultSet.getString( 5));			returnData.setOrderid( resultSet.getString( 6));			returnData.setCurrency( resultSet.getString( 7));			returnData.setShippingcase( resultSet.getString( 8));			returnData.setPaymentterm( resultSet.getString( 9));			returnData.setLoadingport( resultSet.getString( 10));			returnData.setDeliveryport( resultSet.getString( 11));			returnData.setOrdercompany( resultSet.getString( 12));			returnData.setOrderdate( resultSet.getString( 13));			returnData.setDeliverydate( resultSet.getString( 14));			returnData.setTotalprice( resultSet.getString( 15));			returnData.setYskordertarget( resultSet.getString( 16));			returnData.setTeam( resultSet.getString( 17));			returnData.setDeptguid( resultSet.getString( 18));			returnData.setCreatetime( resultSet.getString( 19));			returnData.setCreateperson( resultSet.getString( 20));			returnData.setCreateunitid( resultSet.getString( 21));			returnData.setModifytime( resultSet.getString( 22));			returnData.setModifyperson( resultSet.getString( 23));			returnData.setDeleteflag( resultSet.getString( 24));			returnData.setFormid( resultSet.getString( 25));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,piid,parentid,subid,customerid,orderid,currency,shippingcase,paymentterm,loadingport,deliveryport,ordercompany,orderdate,deliverydate,totalprice,yskordertarget,team,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Order  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,piid,parentid,subid,customerid,orderid,currency,shippingcase,paymentterm,loadingport,deliveryport,ordercompany,orderdate,deliverydate,totalprice,yskordertarget,team,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Order"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_OrderData returnData=new B_OrderData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setPiid( resultSet.getString( 2));				returnData.setParentid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setCustomerid( resultSet.getString( 5));				returnData.setOrderid( resultSet.getString( 6));				returnData.setCurrency( resultSet.getString( 7));				returnData.setShippingcase( resultSet.getString( 8));				returnData.setPaymentterm( resultSet.getString( 9));				returnData.setLoadingport( resultSet.getString( 10));				returnData.setDeliveryport( resultSet.getString( 11));				returnData.setOrdercompany( resultSet.getString( 12));				returnData.setOrderdate( resultSet.getString( 13));				returnData.setDeliverydate( resultSet.getString( 14));				returnData.setTotalprice( resultSet.getString( 15));				returnData.setYskordertarget( resultSet.getString( 16));				returnData.setTeam( resultSet.getString( 17));				returnData.setDeptguid( resultSet.getString( 18));				returnData.setCreatetime( resultSet.getString( 19));				returnData.setCreateperson( resultSet.getString( 20));				returnData.setCreateunitid( resultSet.getString( 21));				returnData.setModifytime( resultSet.getString( 22));				returnData.setModifyperson( resultSet.getString( 23));				returnData.setDeleteflag( resultSet.getString( 24));				returnData.setFormid( resultSet.getString( 25));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,piid,parentid,subid,customerid,orderid,currency,shippingcase,paymentterm,loadingport,deliveryport,ordercompany,orderdate,deliverydate,totalprice,yskordertarget,team,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Order" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_Order SET recordid= ? , piid= ? , parentid= ? , subid= ? , customerid= ? , orderid= ? , currency= ? , shippingcase= ? , paymentterm= ? , loadingport= ? , deliveryport= ? , ordercompany= ? , orderdate= ? , deliverydate= ? , totalprice= ? , yskordertarget= ? , team= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPiid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getCustomerid());			statement.setString( 6,beanData.getOrderid());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getShippingcase());			statement.setString( 9,beanData.getPaymentterm());			statement.setString( 10,beanData.getLoadingport());			statement.setString( 11,beanData.getDeliveryport());			statement.setString( 12,beanData.getOrdercompany());			if (beanData.getOrderdate()==null || beanData.getOrderdate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getOrderdate());			}			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getDeliverydate());			}			statement.setString( 15,beanData.getTotalprice());			statement.setString( 16,beanData.getYskordertarget());			statement.setString( 17,beanData.getTeam());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			statement.setString( 26,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Order SET recordid= ? , piid= ? , parentid= ? , subid= ? , customerid= ? , orderid= ? , currency= ? , shippingcase= ? , paymentterm= ? , loadingport= ? , deliveryport= ? , ordercompany= ? , orderdate= ? , deliverydate= ? , totalprice= ? , yskordertarget= ? , team= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_Order SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Piid = " + "'" + nullString(beanData.getPiid()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Customerid = " + "'" + nullString(beanData.getCustomerid()) + "',");			bufSQL.append("Orderid = " + "'" + nullString(beanData.getOrderid()) + "',");			bufSQL.append("Currency = " + "'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("Shippingcase = " + "'" + nullString(beanData.getShippingcase()) + "',");			bufSQL.append("Paymentterm = " + "'" + nullString(beanData.getPaymentterm()) + "',");			bufSQL.append("Loadingport = " + "'" + nullString(beanData.getLoadingport()) + "',");			bufSQL.append("Deliveryport = " + "'" + nullString(beanData.getDeliveryport()) + "',");			bufSQL.append("Ordercompany = " + "'" + nullString(beanData.getOrdercompany()) + "',");			bufSQL.append("Orderdate = " + "'" + nullString(beanData.getOrderdate()) + "',");			bufSQL.append("Deliverydate = " + "'" + nullString(beanData.getDeliverydate()) + "',");			bufSQL.append("Totalprice = " + "'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("Yskordertarget = " + "'" + nullString(beanData.getYskordertarget()) + "',");			bufSQL.append("Team = " + "'" + nullString(beanData.getTeam()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_OrderData beanData = (B_OrderData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_Order SET recordid= ? , piid= ? , parentid= ? , subid= ? , customerid= ? , orderid= ? , currency= ? , shippingcase= ? , paymentterm= ? , loadingport= ? , deliveryport= ? , ordercompany= ? , orderdate= ? , deliverydate= ? , totalprice= ? , yskordertarget= ? , team= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPiid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getCustomerid());			statement.setString( 6,beanData.getOrderid());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getShippingcase());			statement.setString( 9,beanData.getPaymentterm());			statement.setString( 10,beanData.getLoadingport());			statement.setString( 11,beanData.getDeliveryport());			statement.setString( 12,beanData.getOrdercompany());			if (beanData.getOrderdate()==null || beanData.getOrderdate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getOrderdate());			}			if (beanData.getDeliverydate()==null || beanData.getDeliverydate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getDeliverydate());			}			statement.setString( 15,beanData.getTotalprice());			statement.setString( 16,beanData.getYskordertarget());			statement.setString( 17,beanData.getTeam());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			statement.setString( 26,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Order SET recordid= ? , piid= ? , parentid= ? , subid= ? , customerid= ? , orderid= ? , currency= ? , shippingcase= ? , paymentterm= ? , loadingport= ? , deliveryport= ? , ordercompany= ? , orderdate= ? , deliverydate= ? , totalprice= ? , yskordertarget= ? , team= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_OrderData beanData = (B_OrderData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_Order WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_Order WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
