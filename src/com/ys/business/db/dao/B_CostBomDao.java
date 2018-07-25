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
public class B_CostBomDao extends BaseAbstractDao
{
	public B_CostBomData beanData=new B_CostBomData();
	public B_CostBomDao()
	{
	}
	public B_CostBomDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_CostBomData)FindByPrimaryKey(beanData);
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
		B_CostBomData beanData = (B_CostBomData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_CostBom( recordid,bomid,ysid,materialid,parentid,subid,currency,exchangerate,labolcost,materialcost,cost,rebate,rebaterate,rmbprice,profitrate,profit,accountingdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getBomid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getExchangerate());			statement.setString( 9,beanData.getLabolcost());			statement.setString( 10,beanData.getMaterialcost());			statement.setString( 11,beanData.getCost());			statement.setString( 12,beanData.getRebate());			statement.setString( 13,beanData.getRebaterate());			statement.setString( 14,beanData.getRmbprice());			statement.setString( 15,beanData.getProfitrate());			statement.setString( 16,beanData.getProfit());			if (beanData.getAccountingdate()==null || beanData.getAccountingdate().trim().equals(""))			{				statement.setNull( 17, Types.DATE);			}			else			{				statement.setString( 17, beanData.getAccountingdate());			}			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_CostBom( recordid,bomid,ysid,materialid,parentid,subid,currency,exchangerate,labolcost,materialcost,cost,rebate,rebaterate,rmbprice,profitrate,profit,accountingdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_CostBom( recordid,bomid,ysid,materialid,parentid,subid,currency,exchangerate,labolcost,materialcost,cost,rebate,rebaterate,rmbprice,profitrate,profit,accountingdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getBomid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getExchangerate()) + "',");			bufSQL.append("'" + nullString(beanData.getLabolcost()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialcost()) + "',");			bufSQL.append("'" + nullString(beanData.getCost()) + "',");			bufSQL.append("'" + nullString(beanData.getRebate()) + "',");			bufSQL.append("'" + nullString(beanData.getRebaterate()) + "',");			bufSQL.append("'" + nullString(beanData.getRmbprice()) + "',");			bufSQL.append("'" + nullString(beanData.getProfitrate()) + "',");			bufSQL.append("'" + nullString(beanData.getProfit()) + "',");			bufSQL.append("'" + nullString(beanData.getAccountingdate()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_CostBomData beanData = (B_CostBomData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_CostBom( recordid,bomid,ysid,materialid,parentid,subid,currency,exchangerate,labolcost,materialcost,cost,rebate,rebaterate,rmbprice,profitrate,profit,accountingdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getBomid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getExchangerate());			statement.setString( 9,beanData.getLabolcost());			statement.setString( 10,beanData.getMaterialcost());			statement.setString( 11,beanData.getCost());			statement.setString( 12,beanData.getRebate());			statement.setString( 13,beanData.getRebaterate());			statement.setString( 14,beanData.getRmbprice());			statement.setString( 15,beanData.getProfitrate());			statement.setString( 16,beanData.getProfit());			if (beanData.getAccountingdate()==null || beanData.getAccountingdate().trim().equals(""))			{				statement.setNull( 17, Types.DATE);			}			else			{				statement.setString( 17, beanData.getAccountingdate());			}			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_CostBom( recordid,bomid,ysid,materialid,parentid,subid,currency,exchangerate,labolcost,materialcost,cost,rebate,rebaterate,rmbprice,profitrate,profit,accountingdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_CostBomData beanData = (B_CostBomData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_CostBom WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_CostBom: "+ e.toString());
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
		B_CostBomData beanData = (B_CostBomData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_CostBom WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_CostBom"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_CostBom: "+ e.toString());
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
		B_CostBomData beanData = (B_CostBomData) beanDataTmp; 
		B_CostBomData returnData=new B_CostBomData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,bomid,ysid,materialid,parentid,subid,currency,exchangerate,labolcost,materialcost,cost,rebate,rebaterate,rmbprice,profitrate,profit,accountingdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_CostBom WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setBomid( resultSet.getString( 2));			returnData.setYsid( resultSet.getString( 3));			returnData.setMaterialid( resultSet.getString( 4));			returnData.setParentid( resultSet.getString( 5));			returnData.setSubid( resultSet.getString( 6));			returnData.setCurrency( resultSet.getString( 7));			returnData.setExchangerate( resultSet.getString( 8));			returnData.setLabolcost( resultSet.getString( 9));			returnData.setMaterialcost( resultSet.getString( 10));			returnData.setCost( resultSet.getString( 11));			returnData.setRebate( resultSet.getString( 12));			returnData.setRebaterate( resultSet.getString( 13));			returnData.setRmbprice( resultSet.getString( 14));			returnData.setProfitrate( resultSet.getString( 15));			returnData.setProfit( resultSet.getString( 16));			returnData.setAccountingdate( resultSet.getString( 17));			returnData.setDeptguid( resultSet.getString( 18));			returnData.setCreatetime( resultSet.getString( 19));			returnData.setCreateperson( resultSet.getString( 20));			returnData.setCreateunitid( resultSet.getString( 21));			returnData.setModifytime( resultSet.getString( 22));			returnData.setModifyperson( resultSet.getString( 23));			returnData.setDeleteflag( resultSet.getString( 24));			returnData.setFormid( resultSet.getString( 25));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,bomid,ysid,materialid,parentid,subid,currency,exchangerate,labolcost,materialcost,cost,rebate,rebaterate,rmbprice,profitrate,profit,accountingdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_CostBom  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,bomid,ysid,materialid,parentid,subid,currency,exchangerate,labolcost,materialcost,cost,rebate,rebaterate,rmbprice,profitrate,profit,accountingdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_CostBom"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_CostBomData returnData=new B_CostBomData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setBomid( resultSet.getString( 2));				returnData.setYsid( resultSet.getString( 3));				returnData.setMaterialid( resultSet.getString( 4));				returnData.setParentid( resultSet.getString( 5));				returnData.setSubid( resultSet.getString( 6));				returnData.setCurrency( resultSet.getString( 7));				returnData.setExchangerate( resultSet.getString( 8));				returnData.setLabolcost( resultSet.getString( 9));				returnData.setMaterialcost( resultSet.getString( 10));				returnData.setCost( resultSet.getString( 11));				returnData.setRebate( resultSet.getString( 12));				returnData.setRebaterate( resultSet.getString( 13));				returnData.setRmbprice( resultSet.getString( 14));				returnData.setProfitrate( resultSet.getString( 15));				returnData.setProfit( resultSet.getString( 16));				returnData.setAccountingdate( resultSet.getString( 17));				returnData.setDeptguid( resultSet.getString( 18));				returnData.setCreatetime( resultSet.getString( 19));				returnData.setCreateperson( resultSet.getString( 20));				returnData.setCreateunitid( resultSet.getString( 21));				returnData.setModifytime( resultSet.getString( 22));				returnData.setModifyperson( resultSet.getString( 23));				returnData.setDeleteflag( resultSet.getString( 24));				returnData.setFormid( resultSet.getString( 25));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,bomid,ysid,materialid,parentid,subid,currency,exchangerate,labolcost,materialcost,cost,rebate,rebaterate,rmbprice,profitrate,profit,accountingdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_CostBom" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_CostBom SET recordid= ? , bomid= ? , ysid= ? , materialid= ? , parentid= ? , subid= ? , currency= ? , exchangerate= ? , labolcost= ? , materialcost= ? , cost= ? , rebate= ? , rebaterate= ? , rmbprice= ? , profitrate= ? , profit= ? , accountingdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getBomid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getExchangerate());			statement.setString( 9,beanData.getLabolcost());			statement.setString( 10,beanData.getMaterialcost());			statement.setString( 11,beanData.getCost());			statement.setString( 12,beanData.getRebate());			statement.setString( 13,beanData.getRebaterate());			statement.setString( 14,beanData.getRmbprice());			statement.setString( 15,beanData.getProfitrate());			statement.setString( 16,beanData.getProfit());			if (beanData.getAccountingdate()==null || beanData.getAccountingdate().trim().equals(""))			{				statement.setNull( 17, Types.DATE);			}			else			{				statement.setString( 17, beanData.getAccountingdate());			}			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			statement.setString( 26,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_CostBom SET recordid= ? , bomid= ? , ysid= ? , materialid= ? , parentid= ? , subid= ? , currency= ? , exchangerate= ? , labolcost= ? , materialcost= ? , cost= ? , rebate= ? , rebaterate= ? , rmbprice= ? , profitrate= ? , profit= ? , accountingdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_CostBom SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Bomid = " + "'" + nullString(beanData.getBomid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Currency = " + "'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("Exchangerate = " + "'" + nullString(beanData.getExchangerate()) + "',");			bufSQL.append("Labolcost = " + "'" + nullString(beanData.getLabolcost()) + "',");			bufSQL.append("Materialcost = " + "'" + nullString(beanData.getMaterialcost()) + "',");			bufSQL.append("Cost = " + "'" + nullString(beanData.getCost()) + "',");			bufSQL.append("Rebate = " + "'" + nullString(beanData.getRebate()) + "',");			bufSQL.append("Rebaterate = " + "'" + nullString(beanData.getRebaterate()) + "',");			bufSQL.append("Rmbprice = " + "'" + nullString(beanData.getRmbprice()) + "',");			bufSQL.append("Profitrate = " + "'" + nullString(beanData.getProfitrate()) + "',");			bufSQL.append("Profit = " + "'" + nullString(beanData.getProfit()) + "',");			bufSQL.append("Accountingdate = " + "'" + nullString(beanData.getAccountingdate()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_CostBomData beanData = (B_CostBomData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_CostBom SET recordid= ? , bomid= ? , ysid= ? , materialid= ? , parentid= ? , subid= ? , currency= ? , exchangerate= ? , labolcost= ? , materialcost= ? , cost= ? , rebate= ? , rebaterate= ? , rmbprice= ? , profitrate= ? , profit= ? , accountingdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getBomid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getCurrency());			statement.setString( 8,beanData.getExchangerate());			statement.setString( 9,beanData.getLabolcost());			statement.setString( 10,beanData.getMaterialcost());			statement.setString( 11,beanData.getCost());			statement.setString( 12,beanData.getRebate());			statement.setString( 13,beanData.getRebaterate());			statement.setString( 14,beanData.getRmbprice());			statement.setString( 15,beanData.getProfitrate());			statement.setString( 16,beanData.getProfit());			if (beanData.getAccountingdate()==null || beanData.getAccountingdate().trim().equals(""))			{				statement.setNull( 17, Types.DATE);			}			else			{				statement.setString( 17, beanData.getAccountingdate());			}			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			statement.setString( 26,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_CostBom SET recordid= ? , bomid= ? , ysid= ? , materialid= ? , parentid= ? , subid= ? , currency= ? , exchangerate= ? , labolcost= ? , materialcost= ? , cost= ? , rebate= ? , rebaterate= ? , rmbprice= ? , profitrate= ? , profit= ? , accountingdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_CostBomData beanData = (B_CostBomData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_CostBom WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_CostBom WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
