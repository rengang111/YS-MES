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
public class B_OrderDetailDao extends BaseAbstractDao
{
	public B_OrderDetailData beanData=new B_OrderDetailData();
	public B_OrderDetailDao()
	{
	}
	public B_OrderDetailDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_OrderDetailData)FindByPrimaryKey(beanData);
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
		B_OrderDetailData beanData = (B_OrderDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_OrderDetail( recordid,ysid,piid,parentid,subid,materialid,quantity,totalquantity,completedquantity,completednumber,stockoutqty,returnquantity,price,totalprice,currency,exchangerate,exchangeprice,rmbprice,salestax,rebate,rebaterate,profitrate,profit,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,ordercost,netprofit,productcost,storagedate,stockoutdate,status,productclassify,ordertype,payment,receipt,peiysid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getPiid());			statement.setString( 4,beanData.getParentid());			statement.setString( 5,beanData.getSubid());			statement.setString( 6,beanData.getMaterialid());			statement.setString( 7,beanData.getQuantity());			statement.setString( 8,beanData.getTotalquantity());			statement.setString( 9,beanData.getCompletedquantity());			statement.setString( 10,beanData.getCompletednumber());			statement.setString( 11,beanData.getStockoutqty());			statement.setString( 12,beanData.getReturnquantity());			statement.setString( 13,beanData.getPrice());			statement.setString( 14,beanData.getTotalprice());			statement.setString( 15,beanData.getCurrency());			statement.setString( 16,beanData.getExchangerate());			statement.setString( 17,beanData.getExchangeprice());			statement.setString( 18,beanData.getRmbprice());			statement.setString( 19,beanData.getSalestax());			statement.setString( 20,beanData.getRebate());			statement.setString( 21,beanData.getRebaterate());			statement.setString( 22,beanData.getProfitrate());			statement.setString( 23,beanData.getProfit());			statement.setString( 24,beanData.getSalesprofit());			statement.setString( 25,beanData.getAdjustprofit());			statement.setString( 26,beanData.getTotalsalesprofit());			statement.setString( 27,beanData.getTotaladjustprofit());			statement.setString( 28,beanData.getOrdercost());			statement.setString( 29,beanData.getNetprofit());			statement.setString( 30,beanData.getProductcost());			if (beanData.getStoragedate()==null || beanData.getStoragedate().trim().equals(""))			{				statement.setNull( 31, Types.DATE);			}			else			{				statement.setString( 31, beanData.getStoragedate());			}			if (beanData.getStockoutdate()==null || beanData.getStockoutdate().trim().equals(""))			{				statement.setNull( 32, Types.DATE);			}			else			{				statement.setString( 32, beanData.getStockoutdate());			}			statement.setString( 33,beanData.getStatus());			statement.setString( 34,beanData.getProductclassify());			statement.setString( 35,beanData.getOrdertype());			statement.setString( 36,beanData.getPayment());			statement.setString( 37,beanData.getReceipt());			statement.setString( 38,beanData.getPeiysid());			statement.setString( 39,beanData.getDeptguid());			statement.setString( 40,beanData.getCreatetime());			statement.setString( 41,beanData.getCreateperson());			statement.setString( 42,beanData.getCreateunitid());			statement.setString( 43,beanData.getModifytime());			statement.setString( 44,beanData.getModifyperson());			statement.setString( 45,beanData.getDeleteflag());			statement.setString( 46,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_OrderDetail( recordid,ysid,piid,parentid,subid,materialid,quantity,totalquantity,completedquantity,completednumber,stockoutqty,returnquantity,price,totalprice,currency,exchangerate,exchangeprice,rmbprice,salestax,rebate,rebaterate,profitrate,profit,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,ordercost,netprofit,productcost,storagedate,stockoutdate,status,productclassify,ordertype,payment,receipt,peiysid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_OrderDetail( recordid,ysid,piid,parentid,subid,materialid,quantity,totalquantity,completedquantity,completednumber,stockoutqty,returnquantity,price,totalprice,currency,exchangerate,exchangeprice,rmbprice,salestax,rebate,rebaterate,profitrate,profit,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,ordercost,netprofit,productcost,storagedate,stockoutdate,status,productclassify,ordertype,payment,receipt,peiysid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getPiid()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalquantity()) + "',");			bufSQL.append("'" + nullString(beanData.getCompletedquantity()) + "',");			bufSQL.append("'" + nullString(beanData.getCompletednumber()) + "',");			bufSQL.append("'" + nullString(beanData.getStockoutqty()) + "',");			bufSQL.append("'" + nullString(beanData.getReturnquantity()) + "',");			bufSQL.append("'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getExchangerate()) + "',");			bufSQL.append("'" + nullString(beanData.getExchangeprice()) + "',");			bufSQL.append("'" + nullString(beanData.getRmbprice()) + "',");			bufSQL.append("'" + nullString(beanData.getSalestax()) + "',");			bufSQL.append("'" + nullString(beanData.getRebate()) + "',");			bufSQL.append("'" + nullString(beanData.getRebaterate()) + "',");			bufSQL.append("'" + nullString(beanData.getProfitrate()) + "',");			bufSQL.append("'" + nullString(beanData.getProfit()) + "',");			bufSQL.append("'" + nullString(beanData.getSalesprofit()) + "',");			bufSQL.append("'" + nullString(beanData.getAdjustprofit()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalsalesprofit()) + "',");			bufSQL.append("'" + nullString(beanData.getTotaladjustprofit()) + "',");			bufSQL.append("'" + nullString(beanData.getOrdercost()) + "',");			bufSQL.append("'" + nullString(beanData.getNetprofit()) + "',");			bufSQL.append("'" + nullString(beanData.getProductcost()) + "',");			bufSQL.append("'" + nullString(beanData.getStoragedate()) + "',");			bufSQL.append("'" + nullString(beanData.getStockoutdate()) + "',");			bufSQL.append("'" + nullString(beanData.getStatus()) + "',");			bufSQL.append("'" + nullString(beanData.getProductclassify()) + "',");			bufSQL.append("'" + nullString(beanData.getOrdertype()) + "',");			bufSQL.append("'" + nullString(beanData.getPayment()) + "',");			bufSQL.append("'" + nullString(beanData.getReceipt()) + "',");			bufSQL.append("'" + nullString(beanData.getPeiysid()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_OrderDetailData beanData = (B_OrderDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_OrderDetail( recordid,ysid,piid,parentid,subid,materialid,quantity,totalquantity,completedquantity,completednumber,stockoutqty,returnquantity,price,totalprice,currency,exchangerate,exchangeprice,rmbprice,salestax,rebate,rebaterate,profitrate,profit,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,ordercost,netprofit,productcost,storagedate,stockoutdate,status,productclassify,ordertype,payment,receipt,peiysid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getPiid());			statement.setString( 4,beanData.getParentid());			statement.setString( 5,beanData.getSubid());			statement.setString( 6,beanData.getMaterialid());			statement.setString( 7,beanData.getQuantity());			statement.setString( 8,beanData.getTotalquantity());			statement.setString( 9,beanData.getCompletedquantity());			statement.setString( 10,beanData.getCompletednumber());			statement.setString( 11,beanData.getStockoutqty());			statement.setString( 12,beanData.getReturnquantity());			statement.setString( 13,beanData.getPrice());			statement.setString( 14,beanData.getTotalprice());			statement.setString( 15,beanData.getCurrency());			statement.setString( 16,beanData.getExchangerate());			statement.setString( 17,beanData.getExchangeprice());			statement.setString( 18,beanData.getRmbprice());			statement.setString( 19,beanData.getSalestax());			statement.setString( 20,beanData.getRebate());			statement.setString( 21,beanData.getRebaterate());			statement.setString( 22,beanData.getProfitrate());			statement.setString( 23,beanData.getProfit());			statement.setString( 24,beanData.getSalesprofit());			statement.setString( 25,beanData.getAdjustprofit());			statement.setString( 26,beanData.getTotalsalesprofit());			statement.setString( 27,beanData.getTotaladjustprofit());			statement.setString( 28,beanData.getOrdercost());			statement.setString( 29,beanData.getNetprofit());			statement.setString( 30,beanData.getProductcost());			if (beanData.getStoragedate()==null || beanData.getStoragedate().trim().equals(""))			{				statement.setNull( 31, Types.DATE);			}			else			{				statement.setString( 31, beanData.getStoragedate());			}			if (beanData.getStockoutdate()==null || beanData.getStockoutdate().trim().equals(""))			{				statement.setNull( 32, Types.DATE);			}			else			{				statement.setString( 32, beanData.getStockoutdate());			}			statement.setString( 33,beanData.getStatus());			statement.setString( 34,beanData.getProductclassify());			statement.setString( 35,beanData.getOrdertype());			statement.setString( 36,beanData.getPayment());			statement.setString( 37,beanData.getReceipt());			statement.setString( 38,beanData.getPeiysid());			statement.setString( 39,beanData.getDeptguid());			statement.setString( 40,beanData.getCreatetime());			statement.setString( 41,beanData.getCreateperson());			statement.setString( 42,beanData.getCreateunitid());			statement.setString( 43,beanData.getModifytime());			statement.setString( 44,beanData.getModifyperson());			statement.setString( 45,beanData.getDeleteflag());			statement.setString( 46,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_OrderDetail( recordid,ysid,piid,parentid,subid,materialid,quantity,totalquantity,completedquantity,completednumber,stockoutqty,returnquantity,price,totalprice,currency,exchangerate,exchangeprice,rmbprice,salestax,rebate,rebaterate,profitrate,profit,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,ordercost,netprofit,productcost,storagedate,stockoutdate,status,productclassify,ordertype,payment,receipt,peiysid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_OrderDetailData beanData = (B_OrderDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_OrderDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_OrderDetail: "+ e.toString());
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
		B_OrderDetailData beanData = (B_OrderDetailData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_OrderDetail WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_OrderDetail"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_OrderDetail: "+ e.toString());
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
		B_OrderDetailData beanData = (B_OrderDetailData) beanDataTmp; 
		B_OrderDetailData returnData=new B_OrderDetailData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,ysid,piid,parentid,subid,materialid,quantity,totalquantity,completedquantity,completednumber,stockoutqty,returnquantity,price,totalprice,currency,exchangerate,exchangeprice,rmbprice,salestax,rebate,rebaterate,profitrate,profit,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,ordercost,netprofit,productcost,storagedate,stockoutdate,status,productclassify,ordertype,payment,receipt,peiysid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_OrderDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setYsid( resultSet.getString( 2));			returnData.setPiid( resultSet.getString( 3));			returnData.setParentid( resultSet.getString( 4));			returnData.setSubid( resultSet.getString( 5));			returnData.setMaterialid( resultSet.getString( 6));			returnData.setQuantity( resultSet.getString( 7));			returnData.setTotalquantity( resultSet.getString( 8));			returnData.setCompletedquantity( resultSet.getString( 9));			returnData.setCompletednumber( resultSet.getString( 10));			returnData.setStockoutqty( resultSet.getString( 11));			returnData.setReturnquantity( resultSet.getString( 12));			returnData.setPrice( resultSet.getString( 13));			returnData.setTotalprice( resultSet.getString( 14));			returnData.setCurrency( resultSet.getString( 15));			returnData.setExchangerate( resultSet.getString( 16));			returnData.setExchangeprice( resultSet.getString( 17));			returnData.setRmbprice( resultSet.getString( 18));			returnData.setSalestax( resultSet.getString( 19));			returnData.setRebate( resultSet.getString( 20));			returnData.setRebaterate( resultSet.getString( 21));			returnData.setProfitrate( resultSet.getString( 22));			returnData.setProfit( resultSet.getString( 23));			returnData.setSalesprofit( resultSet.getString( 24));			returnData.setAdjustprofit( resultSet.getString( 25));			returnData.setTotalsalesprofit( resultSet.getString( 26));			returnData.setTotaladjustprofit( resultSet.getString( 27));			returnData.setOrdercost( resultSet.getString( 28));			returnData.setNetprofit( resultSet.getString( 29));			returnData.setProductcost( resultSet.getString( 30));			returnData.setStoragedate( resultSet.getString( 31));			returnData.setStockoutdate( resultSet.getString( 32));			returnData.setStatus( resultSet.getString( 33));			returnData.setProductclassify( resultSet.getString( 34));			returnData.setOrdertype( resultSet.getString( 35));			returnData.setPayment( resultSet.getString( 36));			returnData.setReceipt( resultSet.getString( 37));			returnData.setPeiysid( resultSet.getString( 38));			returnData.setDeptguid( resultSet.getString( 39));			returnData.setCreatetime( resultSet.getString( 40));			returnData.setCreateperson( resultSet.getString( 41));			returnData.setCreateunitid( resultSet.getString( 42));			returnData.setModifytime( resultSet.getString( 43));			returnData.setModifyperson( resultSet.getString( 44));			returnData.setDeleteflag( resultSet.getString( 45));			returnData.setFormid( resultSet.getString( 46));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,piid,parentid,subid,materialid,quantity,totalquantity,completedquantity,completednumber,stockoutqty,returnquantity,price,totalprice,currency,exchangerate,exchangeprice,rmbprice,salestax,rebate,rebaterate,profitrate,profit,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,ordercost,netprofit,productcost,storagedate,stockoutdate,status,productclassify,ordertype,payment,receipt,peiysid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_OrderDetail  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,ysid,piid,parentid,subid,materialid,quantity,totalquantity,completedquantity,completednumber,stockoutqty,returnquantity,price,totalprice,currency,exchangerate,exchangeprice,rmbprice,salestax,rebate,rebaterate,profitrate,profit,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,ordercost,netprofit,productcost,storagedate,stockoutdate,status,productclassify,ordertype,payment,receipt,peiysid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_OrderDetail"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_OrderDetailData returnData=new B_OrderDetailData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setYsid( resultSet.getString( 2));				returnData.setPiid( resultSet.getString( 3));				returnData.setParentid( resultSet.getString( 4));				returnData.setSubid( resultSet.getString( 5));				returnData.setMaterialid( resultSet.getString( 6));				returnData.setQuantity( resultSet.getString( 7));				returnData.setTotalquantity( resultSet.getString( 8));				returnData.setCompletedquantity( resultSet.getString( 9));				returnData.setCompletednumber( resultSet.getString( 10));				returnData.setStockoutqty( resultSet.getString( 11));				returnData.setReturnquantity( resultSet.getString( 12));				returnData.setPrice( resultSet.getString( 13));				returnData.setTotalprice( resultSet.getString( 14));				returnData.setCurrency( resultSet.getString( 15));				returnData.setExchangerate( resultSet.getString( 16));				returnData.setExchangeprice( resultSet.getString( 17));				returnData.setRmbprice( resultSet.getString( 18));				returnData.setSalestax( resultSet.getString( 19));				returnData.setRebate( resultSet.getString( 20));				returnData.setRebaterate( resultSet.getString( 21));				returnData.setProfitrate( resultSet.getString( 22));				returnData.setProfit( resultSet.getString( 23));				returnData.setSalesprofit( resultSet.getString( 24));				returnData.setAdjustprofit( resultSet.getString( 25));				returnData.setTotalsalesprofit( resultSet.getString( 26));				returnData.setTotaladjustprofit( resultSet.getString( 27));				returnData.setOrdercost( resultSet.getString( 28));				returnData.setNetprofit( resultSet.getString( 29));				returnData.setProductcost( resultSet.getString( 30));				returnData.setStoragedate( resultSet.getString( 31));				returnData.setStockoutdate( resultSet.getString( 32));				returnData.setStatus( resultSet.getString( 33));				returnData.setProductclassify( resultSet.getString( 34));				returnData.setOrdertype( resultSet.getString( 35));				returnData.setPayment( resultSet.getString( 36));				returnData.setReceipt( resultSet.getString( 37));				returnData.setPeiysid( resultSet.getString( 38));				returnData.setDeptguid( resultSet.getString( 39));				returnData.setCreatetime( resultSet.getString( 40));				returnData.setCreateperson( resultSet.getString( 41));				returnData.setCreateunitid( resultSet.getString( 42));				returnData.setModifytime( resultSet.getString( 43));				returnData.setModifyperson( resultSet.getString( 44));				returnData.setDeleteflag( resultSet.getString( 45));				returnData.setFormid( resultSet.getString( 46));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,piid,parentid,subid,materialid,quantity,totalquantity,completedquantity,completednumber,stockoutqty,returnquantity,price,totalprice,currency,exchangerate,exchangeprice,rmbprice,salestax,rebate,rebaterate,profitrate,profit,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,ordercost,netprofit,productcost,storagedate,stockoutdate,status,productclassify,ordertype,payment,receipt,peiysid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_OrderDetail" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_OrderDetail SET recordid= ? , ysid= ? , piid= ? , parentid= ? , subid= ? , materialid= ? , quantity= ? , totalquantity= ? , completedquantity= ? , completednumber= ? , stockoutqty= ? , returnquantity= ? , price= ? , totalprice= ? , currency= ? , exchangerate= ? , exchangeprice= ? , rmbprice= ? , salestax= ? , rebate= ? , rebaterate= ? , profitrate= ? , profit= ? , salesprofit= ? , adjustprofit= ? , totalsalesprofit= ? , totaladjustprofit= ? , ordercost= ? , netprofit= ? , productcost= ? , storagedate= ? , stockoutdate= ? , status= ? , productclassify= ? , ordertype= ? , payment= ? , receipt= ? , peiysid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getPiid());			statement.setString( 4,beanData.getParentid());			statement.setString( 5,beanData.getSubid());			statement.setString( 6,beanData.getMaterialid());			statement.setString( 7,beanData.getQuantity());			statement.setString( 8,beanData.getTotalquantity());			statement.setString( 9,beanData.getCompletedquantity());			statement.setString( 10,beanData.getCompletednumber());			statement.setString( 11,beanData.getStockoutqty());			statement.setString( 12,beanData.getReturnquantity());			statement.setString( 13,beanData.getPrice());			statement.setString( 14,beanData.getTotalprice());			statement.setString( 15,beanData.getCurrency());			statement.setString( 16,beanData.getExchangerate());			statement.setString( 17,beanData.getExchangeprice());			statement.setString( 18,beanData.getRmbprice());			statement.setString( 19,beanData.getSalestax());			statement.setString( 20,beanData.getRebate());			statement.setString( 21,beanData.getRebaterate());			statement.setString( 22,beanData.getProfitrate());			statement.setString( 23,beanData.getProfit());			statement.setString( 24,beanData.getSalesprofit());			statement.setString( 25,beanData.getAdjustprofit());			statement.setString( 26,beanData.getTotalsalesprofit());			statement.setString( 27,beanData.getTotaladjustprofit());			statement.setString( 28,beanData.getOrdercost());			statement.setString( 29,beanData.getNetprofit());			statement.setString( 30,beanData.getProductcost());			if (beanData.getStoragedate()==null || beanData.getStoragedate().trim().equals(""))			{				statement.setNull( 31, Types.DATE);			}			else			{				statement.setString( 31, beanData.getStoragedate());			}			if (beanData.getStockoutdate()==null || beanData.getStockoutdate().trim().equals(""))			{				statement.setNull( 32, Types.DATE);			}			else			{				statement.setString( 32, beanData.getStockoutdate());			}			statement.setString( 33,beanData.getStatus());			statement.setString( 34,beanData.getProductclassify());			statement.setString( 35,beanData.getOrdertype());			statement.setString( 36,beanData.getPayment());			statement.setString( 37,beanData.getReceipt());			statement.setString( 38,beanData.getPeiysid());			statement.setString( 39,beanData.getDeptguid());			statement.setString( 40,beanData.getCreatetime());			statement.setString( 41,beanData.getCreateperson());			statement.setString( 42,beanData.getCreateunitid());			statement.setString( 43,beanData.getModifytime());			statement.setString( 44,beanData.getModifyperson());			statement.setString( 45,beanData.getDeleteflag());			statement.setString( 46,beanData.getFormid());
			statement.setString( 47,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_OrderDetail SET recordid= ? , ysid= ? , piid= ? , parentid= ? , subid= ? , materialid= ? , quantity= ? , totalquantity= ? , completedquantity= ? , completednumber= ? , stockoutqty= ? , returnquantity= ? , price= ? , totalprice= ? , currency= ? , exchangerate= ? , exchangeprice= ? , rmbprice= ? , salestax= ? , rebate= ? , rebaterate= ? , profitrate= ? , profit= ? , salesprofit= ? , adjustprofit= ? , totalsalesprofit= ? , totaladjustprofit= ? , ordercost= ? , netprofit= ? , productcost= ? , storagedate= ? , stockoutdate= ? , status= ? , productclassify= ? , ordertype= ? , payment= ? , receipt= ? , peiysid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_OrderDetail SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Piid = " + "'" + nullString(beanData.getPiid()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Quantity = " + "'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("Totalquantity = " + "'" + nullString(beanData.getTotalquantity()) + "',");			bufSQL.append("Completedquantity = " + "'" + nullString(beanData.getCompletedquantity()) + "',");			bufSQL.append("Completednumber = " + "'" + nullString(beanData.getCompletednumber()) + "',");			bufSQL.append("Stockoutqty = " + "'" + nullString(beanData.getStockoutqty()) + "',");			bufSQL.append("Returnquantity = " + "'" + nullString(beanData.getReturnquantity()) + "',");			bufSQL.append("Price = " + "'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("Totalprice = " + "'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("Currency = " + "'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("Exchangerate = " + "'" + nullString(beanData.getExchangerate()) + "',");			bufSQL.append("Exchangeprice = " + "'" + nullString(beanData.getExchangeprice()) + "',");			bufSQL.append("Rmbprice = " + "'" + nullString(beanData.getRmbprice()) + "',");			bufSQL.append("Salestax = " + "'" + nullString(beanData.getSalestax()) + "',");			bufSQL.append("Rebate = " + "'" + nullString(beanData.getRebate()) + "',");			bufSQL.append("Rebaterate = " + "'" + nullString(beanData.getRebaterate()) + "',");			bufSQL.append("Profitrate = " + "'" + nullString(beanData.getProfitrate()) + "',");			bufSQL.append("Profit = " + "'" + nullString(beanData.getProfit()) + "',");			bufSQL.append("Salesprofit = " + "'" + nullString(beanData.getSalesprofit()) + "',");			bufSQL.append("Adjustprofit = " + "'" + nullString(beanData.getAdjustprofit()) + "',");			bufSQL.append("Totalsalesprofit = " + "'" + nullString(beanData.getTotalsalesprofit()) + "',");			bufSQL.append("Totaladjustprofit = " + "'" + nullString(beanData.getTotaladjustprofit()) + "',");			bufSQL.append("Ordercost = " + "'" + nullString(beanData.getOrdercost()) + "',");			bufSQL.append("Netprofit = " + "'" + nullString(beanData.getNetprofit()) + "',");			bufSQL.append("Productcost = " + "'" + nullString(beanData.getProductcost()) + "',");			bufSQL.append("Storagedate = " + "'" + nullString(beanData.getStoragedate()) + "',");			bufSQL.append("Stockoutdate = " + "'" + nullString(beanData.getStockoutdate()) + "',");			bufSQL.append("Status = " + "'" + nullString(beanData.getStatus()) + "',");			bufSQL.append("Productclassify = " + "'" + nullString(beanData.getProductclassify()) + "',");			bufSQL.append("Ordertype = " + "'" + nullString(beanData.getOrdertype()) + "',");			bufSQL.append("Payment = " + "'" + nullString(beanData.getPayment()) + "',");			bufSQL.append("Receipt = " + "'" + nullString(beanData.getReceipt()) + "',");			bufSQL.append("Peiysid = " + "'" + nullString(beanData.getPeiysid()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_OrderDetailData beanData = (B_OrderDetailData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_OrderDetail SET recordid= ? , ysid= ? , piid= ? , parentid= ? , subid= ? , materialid= ? , quantity= ? , totalquantity= ? , completedquantity= ? , completednumber= ? , stockoutqty= ? , returnquantity= ? , price= ? , totalprice= ? , currency= ? , exchangerate= ? , exchangeprice= ? , rmbprice= ? , salestax= ? , rebate= ? , rebaterate= ? , profitrate= ? , profit= ? , salesprofit= ? , adjustprofit= ? , totalsalesprofit= ? , totaladjustprofit= ? , ordercost= ? , netprofit= ? , productcost= ? , storagedate= ? , stockoutdate= ? , status= ? , productclassify= ? , ordertype= ? , payment= ? , receipt= ? , peiysid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getPiid());			statement.setString( 4,beanData.getParentid());			statement.setString( 5,beanData.getSubid());			statement.setString( 6,beanData.getMaterialid());			statement.setString( 7,beanData.getQuantity());			statement.setString( 8,beanData.getTotalquantity());			statement.setString( 9,beanData.getCompletedquantity());			statement.setString( 10,beanData.getCompletednumber());			statement.setString( 11,beanData.getStockoutqty());			statement.setString( 12,beanData.getReturnquantity());			statement.setString( 13,beanData.getPrice());			statement.setString( 14,beanData.getTotalprice());			statement.setString( 15,beanData.getCurrency());			statement.setString( 16,beanData.getExchangerate());			statement.setString( 17,beanData.getExchangeprice());			statement.setString( 18,beanData.getRmbprice());			statement.setString( 19,beanData.getSalestax());			statement.setString( 20,beanData.getRebate());			statement.setString( 21,beanData.getRebaterate());			statement.setString( 22,beanData.getProfitrate());			statement.setString( 23,beanData.getProfit());			statement.setString( 24,beanData.getSalesprofit());			statement.setString( 25,beanData.getAdjustprofit());			statement.setString( 26,beanData.getTotalsalesprofit());			statement.setString( 27,beanData.getTotaladjustprofit());			statement.setString( 28,beanData.getOrdercost());			statement.setString( 29,beanData.getNetprofit());			statement.setString( 30,beanData.getProductcost());			if (beanData.getStoragedate()==null || beanData.getStoragedate().trim().equals(""))			{				statement.setNull( 31, Types.DATE);			}			else			{				statement.setString( 31, beanData.getStoragedate());			}			if (beanData.getStockoutdate()==null || beanData.getStockoutdate().trim().equals(""))			{				statement.setNull( 32, Types.DATE);			}			else			{				statement.setString( 32, beanData.getStockoutdate());			}			statement.setString( 33,beanData.getStatus());			statement.setString( 34,beanData.getProductclassify());			statement.setString( 35,beanData.getOrdertype());			statement.setString( 36,beanData.getPayment());			statement.setString( 37,beanData.getReceipt());			statement.setString( 38,beanData.getPeiysid());			statement.setString( 39,beanData.getDeptguid());			statement.setString( 40,beanData.getCreatetime());			statement.setString( 41,beanData.getCreateperson());			statement.setString( 42,beanData.getCreateunitid());			statement.setString( 43,beanData.getModifytime());			statement.setString( 44,beanData.getModifyperson());			statement.setString( 45,beanData.getDeleteflag());			statement.setString( 46,beanData.getFormid());
			statement.setString( 47,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_OrderDetail SET recordid= ? , ysid= ? , piid= ? , parentid= ? , subid= ? , materialid= ? , quantity= ? , totalquantity= ? , completedquantity= ? , completednumber= ? , stockoutqty= ? , returnquantity= ? , price= ? , totalprice= ? , currency= ? , exchangerate= ? , exchangeprice= ? , rmbprice= ? , salestax= ? , rebate= ? , rebaterate= ? , profitrate= ? , profit= ? , salesprofit= ? , adjustprofit= ? , totalsalesprofit= ? , totaladjustprofit= ? , ordercost= ? , netprofit= ? , productcost= ? , storagedate= ? , stockoutdate= ? , status= ? , productclassify= ? , ordertype= ? , payment= ? , receipt= ? , peiysid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_OrderDetailData beanData = (B_OrderDetailData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_OrderDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_OrderDetail WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
