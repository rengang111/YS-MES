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
public class B_PurchasePlanDao extends BaseAbstractDao
{
	public B_PurchasePlanData beanData=new B_PurchasePlanData();
	public B_PurchasePlanDao()
	{
	}
	public B_PurchasePlanDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_PurchasePlanData)FindByPrimaryKey(beanData);
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
		B_PurchasePlanData beanData = (B_PurchasePlanData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchasePlan( recordid,purchaseid,ysid,subid,materialid,managementcostrate,managementcost,bomcost,productcost,totalcost,laborcost,materialcost,plandate,currency,exchangerate,exchangeprice,salestax,rebate,rebaterate,rmbprice,profitrate,profit,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPurchaseid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getManagementcostrate());			statement.setString( 7,beanData.getManagementcost());			statement.setString( 8,beanData.getBomcost());			statement.setString( 9,beanData.getProductcost());			statement.setString( 10,beanData.getTotalcost());			statement.setString( 11,beanData.getLaborcost());			statement.setString( 12,beanData.getMaterialcost());			if (beanData.getPlandate()==null || beanData.getPlandate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getPlandate());			}			statement.setString( 14,beanData.getCurrency());			statement.setString( 15,beanData.getExchangerate());			statement.setString( 16,beanData.getExchangeprice());			statement.setString( 17,beanData.getSalestax());			statement.setString( 18,beanData.getRebate());			statement.setString( 19,beanData.getRebaterate());			statement.setString( 20,beanData.getRmbprice());			statement.setString( 21,beanData.getProfitrate());			statement.setString( 22,beanData.getProfit());			statement.setInt( 23,beanData.getVersion());			statement.setString( 24,beanData.getDeptguid());			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchasePlan( recordid,purchaseid,ysid,subid,materialid,managementcostrate,managementcost,bomcost,productcost,totalcost,laborcost,materialcost,plandate,currency,exchangerate,exchangeprice,salestax,rebate,rebaterate,rmbprice,profitrate,profit,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_PurchasePlan( recordid,purchaseid,ysid,subid,materialid,managementcostrate,managementcost,bomcost,productcost,totalcost,laborcost,materialcost,plandate,currency,exchangerate,exchangeprice,salestax,rebate,rebaterate,rmbprice,profitrate,profit,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getPurchaseid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getManagementcostrate()) + "',");			bufSQL.append("'" + nullString(beanData.getManagementcost()) + "',");			bufSQL.append("'" + nullString(beanData.getBomcost()) + "',");			bufSQL.append("'" + nullString(beanData.getProductcost()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalcost()) + "',");			bufSQL.append("'" + nullString(beanData.getLaborcost()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialcost()) + "',");			bufSQL.append("'" + nullString(beanData.getPlandate()) + "',");			bufSQL.append("'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getExchangerate()) + "',");			bufSQL.append("'" + nullString(beanData.getExchangeprice()) + "',");			bufSQL.append("'" + nullString(beanData.getSalestax()) + "',");			bufSQL.append("'" + nullString(beanData.getRebate()) + "',");			bufSQL.append("'" + nullString(beanData.getRebaterate()) + "',");			bufSQL.append("'" + nullString(beanData.getRmbprice()) + "',");			bufSQL.append("'" + nullString(beanData.getProfitrate()) + "',");			bufSQL.append("'" + nullString(beanData.getProfit()) + "',");			bufSQL.append(beanData.getVersion() + ",");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchasePlanData beanData = (B_PurchasePlanData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchasePlan( recordid,purchaseid,ysid,subid,materialid,managementcostrate,managementcost,bomcost,productcost,totalcost,laborcost,materialcost,plandate,currency,exchangerate,exchangeprice,salestax,rebate,rebaterate,rmbprice,profitrate,profit,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPurchaseid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getManagementcostrate());			statement.setString( 7,beanData.getManagementcost());			statement.setString( 8,beanData.getBomcost());			statement.setString( 9,beanData.getProductcost());			statement.setString( 10,beanData.getTotalcost());			statement.setString( 11,beanData.getLaborcost());			statement.setString( 12,beanData.getMaterialcost());			if (beanData.getPlandate()==null || beanData.getPlandate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getPlandate());			}			statement.setString( 14,beanData.getCurrency());			statement.setString( 15,beanData.getExchangerate());			statement.setString( 16,beanData.getExchangeprice());			statement.setString( 17,beanData.getSalestax());			statement.setString( 18,beanData.getRebate());			statement.setString( 19,beanData.getRebaterate());			statement.setString( 20,beanData.getRmbprice());			statement.setString( 21,beanData.getProfitrate());			statement.setString( 22,beanData.getProfit());			statement.setInt( 23,beanData.getVersion());			statement.setString( 24,beanData.getDeptguid());			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchasePlan( recordid,purchaseid,ysid,subid,materialid,managementcostrate,managementcost,bomcost,productcost,totalcost,laborcost,materialcost,plandate,currency,exchangerate,exchangeprice,salestax,rebate,rebaterate,rmbprice,profitrate,profit,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_PurchasePlanData beanData = (B_PurchasePlanData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_PurchasePlan WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchasePlan: "+ e.toString());
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
		B_PurchasePlanData beanData = (B_PurchasePlanData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_PurchasePlan WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_PurchasePlan"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchasePlan: "+ e.toString());
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
		B_PurchasePlanData beanData = (B_PurchasePlanData) beanDataTmp; 
		B_PurchasePlanData returnData=new B_PurchasePlanData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,purchaseid,ysid,subid,materialid,managementcostrate,managementcost,bomcost,productcost,totalcost,laborcost,materialcost,plandate,currency,exchangerate,exchangeprice,salestax,rebate,rebaterate,rmbprice,profitrate,profit,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchasePlan WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setPurchaseid( resultSet.getString( 2));			returnData.setYsid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setMaterialid( resultSet.getString( 5));			returnData.setManagementcostrate( resultSet.getString( 6));			returnData.setManagementcost( resultSet.getString( 7));			returnData.setBomcost( resultSet.getString( 8));			returnData.setProductcost( resultSet.getString( 9));			returnData.setTotalcost( resultSet.getString( 10));			returnData.setLaborcost( resultSet.getString( 11));			returnData.setMaterialcost( resultSet.getString( 12));			returnData.setPlandate( resultSet.getString( 13));			returnData.setCurrency( resultSet.getString( 14));			returnData.setExchangerate( resultSet.getString( 15));			returnData.setExchangeprice( resultSet.getString( 16));			returnData.setSalestax( resultSet.getString( 17));			returnData.setRebate( resultSet.getString( 18));			returnData.setRebaterate( resultSet.getString( 19));			returnData.setRmbprice( resultSet.getString( 20));			returnData.setProfitrate( resultSet.getString( 21));			returnData.setProfit( resultSet.getString( 22));			returnData.setVersion( resultSet.getInt( 23));			returnData.setDeptguid( resultSet.getString( 24));			returnData.setCreatetime( resultSet.getString( 25));			returnData.setCreateperson( resultSet.getString( 26));			returnData.setCreateunitid( resultSet.getString( 27));			returnData.setModifytime( resultSet.getString( 28));			returnData.setModifyperson( resultSet.getString( 29));			returnData.setDeleteflag( resultSet.getString( 30));			returnData.setFormid( resultSet.getString( 31));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,purchaseid,ysid,subid,materialid,managementcostrate,managementcost,bomcost,productcost,totalcost,laborcost,materialcost,plandate,currency,exchangerate,exchangeprice,salestax,rebate,rebaterate,rmbprice,profitrate,profit,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchasePlan  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,purchaseid,ysid,subid,materialid,managementcostrate,managementcost,bomcost,productcost,totalcost,laborcost,materialcost,plandate,currency,exchangerate,exchangeprice,salestax,rebate,rebaterate,rmbprice,profitrate,profit,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchasePlan"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_PurchasePlanData returnData=new B_PurchasePlanData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setPurchaseid( resultSet.getString( 2));				returnData.setYsid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setMaterialid( resultSet.getString( 5));				returnData.setManagementcostrate( resultSet.getString( 6));				returnData.setManagementcost( resultSet.getString( 7));				returnData.setBomcost( resultSet.getString( 8));				returnData.setProductcost( resultSet.getString( 9));				returnData.setTotalcost( resultSet.getString( 10));				returnData.setLaborcost( resultSet.getString( 11));				returnData.setMaterialcost( resultSet.getString( 12));				returnData.setPlandate( resultSet.getString( 13));				returnData.setCurrency( resultSet.getString( 14));				returnData.setExchangerate( resultSet.getString( 15));				returnData.setExchangeprice( resultSet.getString( 16));				returnData.setSalestax( resultSet.getString( 17));				returnData.setRebate( resultSet.getString( 18));				returnData.setRebaterate( resultSet.getString( 19));				returnData.setRmbprice( resultSet.getString( 20));				returnData.setProfitrate( resultSet.getString( 21));				returnData.setProfit( resultSet.getString( 22));				returnData.setVersion( resultSet.getInt( 23));				returnData.setDeptguid( resultSet.getString( 24));				returnData.setCreatetime( resultSet.getString( 25));				returnData.setCreateperson( resultSet.getString( 26));				returnData.setCreateunitid( resultSet.getString( 27));				returnData.setModifytime( resultSet.getString( 28));				returnData.setModifyperson( resultSet.getString( 29));				returnData.setDeleteflag( resultSet.getString( 30));				returnData.setFormid( resultSet.getString( 31));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,purchaseid,ysid,subid,materialid,managementcostrate,managementcost,bomcost,productcost,totalcost,laborcost,materialcost,plandate,currency,exchangerate,exchangeprice,salestax,rebate,rebaterate,rmbprice,profitrate,profit,version,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchasePlan" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_PurchasePlan SET recordid= ? , purchaseid= ? , ysid= ? , subid= ? , materialid= ? , managementcostrate= ? , managementcost= ? , bomcost= ? , productcost= ? , totalcost= ? , laborcost= ? , materialcost= ? , plandate= ? , currency= ? , exchangerate= ? , exchangeprice= ? , salestax= ? , rebate= ? , rebaterate= ? , rmbprice= ? , profitrate= ? , profit= ? , version= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPurchaseid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getManagementcostrate());			statement.setString( 7,beanData.getManagementcost());			statement.setString( 8,beanData.getBomcost());			statement.setString( 9,beanData.getProductcost());			statement.setString( 10,beanData.getTotalcost());			statement.setString( 11,beanData.getLaborcost());			statement.setString( 12,beanData.getMaterialcost());			if (beanData.getPlandate()==null || beanData.getPlandate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getPlandate());			}			statement.setString( 14,beanData.getCurrency());			statement.setString( 15,beanData.getExchangerate());			statement.setString( 16,beanData.getExchangeprice());			statement.setString( 17,beanData.getSalestax());			statement.setString( 18,beanData.getRebate());			statement.setString( 19,beanData.getRebaterate());			statement.setString( 20,beanData.getRmbprice());			statement.setString( 21,beanData.getProfitrate());			statement.setString( 22,beanData.getProfit());			statement.setInt( 23,beanData.getVersion());			statement.setString( 24,beanData.getDeptguid());			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getFormid());
			statement.setString( 32,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchasePlan SET recordid= ? , purchaseid= ? , ysid= ? , subid= ? , materialid= ? , managementcostrate= ? , managementcost= ? , bomcost= ? , productcost= ? , totalcost= ? , laborcost= ? , materialcost= ? , plandate= ? , currency= ? , exchangerate= ? , exchangeprice= ? , salestax= ? , rebate= ? , rebaterate= ? , rmbprice= ? , profitrate= ? , profit= ? , version= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_PurchasePlan SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Purchaseid = " + "'" + nullString(beanData.getPurchaseid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Managementcostrate = " + "'" + nullString(beanData.getManagementcostrate()) + "',");			bufSQL.append("Managementcost = " + "'" + nullString(beanData.getManagementcost()) + "',");			bufSQL.append("Bomcost = " + "'" + nullString(beanData.getBomcost()) + "',");			bufSQL.append("Productcost = " + "'" + nullString(beanData.getProductcost()) + "',");			bufSQL.append("Totalcost = " + "'" + nullString(beanData.getTotalcost()) + "',");			bufSQL.append("Laborcost = " + "'" + nullString(beanData.getLaborcost()) + "',");			bufSQL.append("Materialcost = " + "'" + nullString(beanData.getMaterialcost()) + "',");			bufSQL.append("Plandate = " + "'" + nullString(beanData.getPlandate()) + "',");			bufSQL.append("Currency = " + "'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("Exchangerate = " + "'" + nullString(beanData.getExchangerate()) + "',");			bufSQL.append("Exchangeprice = " + "'" + nullString(beanData.getExchangeprice()) + "',");			bufSQL.append("Salestax = " + "'" + nullString(beanData.getSalestax()) + "',");			bufSQL.append("Rebate = " + "'" + nullString(beanData.getRebate()) + "',");			bufSQL.append("Rebaterate = " + "'" + nullString(beanData.getRebaterate()) + "',");			bufSQL.append("Rmbprice = " + "'" + nullString(beanData.getRmbprice()) + "',");			bufSQL.append("Profitrate = " + "'" + nullString(beanData.getProfitrate()) + "',");			bufSQL.append("Profit = " + "'" + nullString(beanData.getProfit()) + "',");			bufSQL.append("Version = " + beanData.getVersion() + ",");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchasePlanData beanData = (B_PurchasePlanData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_PurchasePlan SET recordid= ? , purchaseid= ? , ysid= ? , subid= ? , materialid= ? , managementcostrate= ? , managementcost= ? , bomcost= ? , productcost= ? , totalcost= ? , laborcost= ? , materialcost= ? , plandate= ? , currency= ? , exchangerate= ? , exchangeprice= ? , salestax= ? , rebate= ? , rebaterate= ? , rmbprice= ? , profitrate= ? , profit= ? , version= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPurchaseid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getManagementcostrate());			statement.setString( 7,beanData.getManagementcost());			statement.setString( 8,beanData.getBomcost());			statement.setString( 9,beanData.getProductcost());			statement.setString( 10,beanData.getTotalcost());			statement.setString( 11,beanData.getLaborcost());			statement.setString( 12,beanData.getMaterialcost());			if (beanData.getPlandate()==null || beanData.getPlandate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getPlandate());			}			statement.setString( 14,beanData.getCurrency());			statement.setString( 15,beanData.getExchangerate());			statement.setString( 16,beanData.getExchangeprice());			statement.setString( 17,beanData.getSalestax());			statement.setString( 18,beanData.getRebate());			statement.setString( 19,beanData.getRebaterate());			statement.setString( 20,beanData.getRmbprice());			statement.setString( 21,beanData.getProfitrate());			statement.setString( 22,beanData.getProfit());			statement.setInt( 23,beanData.getVersion());			statement.setString( 24,beanData.getDeptguid());			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getFormid());
			statement.setString( 32,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchasePlan SET recordid= ? , purchaseid= ? , ysid= ? , subid= ? , materialid= ? , managementcostrate= ? , managementcost= ? , bomcost= ? , productcost= ? , totalcost= ? , laborcost= ? , materialcost= ? , plandate= ? , currency= ? , exchangerate= ? , exchangeprice= ? , salestax= ? , rebate= ? , rebaterate= ? , rmbprice= ? , profitrate= ? , profit= ? , version= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_PurchasePlanData beanData = (B_PurchasePlanData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_PurchasePlan WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_PurchasePlan WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
