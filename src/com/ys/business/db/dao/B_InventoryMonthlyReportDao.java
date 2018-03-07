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
public class B_InventoryMonthlyReportDao extends BaseAbstractDao
{
	public B_InventoryMonthlyReportData beanData=new B_InventoryMonthlyReportData();
	public B_InventoryMonthlyReportDao()
	{
	}
	public B_InventoryMonthlyReportDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_InventoryMonthlyReportData)FindByPrimaryKey(beanData);
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
		B_InventoryMonthlyReportData beanData = (B_InventoryMonthlyReportData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_InventoryMonthlyReport( recordid,reportid,monthly,materialid,beginningqty,beginningprice,stockinqty,stockinprice,stockoutqty,stockoutprice,balanceqty,balanceprice,startdate,enddate,reportdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReportid());			statement.setString( 3,beanData.getMonthly());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getBeginningqty());			statement.setString( 6,beanData.getBeginningprice());			statement.setString( 7,beanData.getStockinqty());			statement.setString( 8,beanData.getStockinprice());			statement.setString( 9,beanData.getStockoutqty());			statement.setString( 10,beanData.getStockoutprice());			statement.setString( 11,beanData.getBalanceqty());			statement.setString( 12,beanData.getBalanceprice());			if (beanData.getStartdate()==null || beanData.getStartdate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getStartdate());			}			if (beanData.getEnddate()==null || beanData.getEnddate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getEnddate());			}			if (beanData.getReportdate()==null || beanData.getReportdate().trim().equals(""))			{				statement.setNull( 15, Types.DATE);			}			else			{				statement.setString( 15, beanData.getReportdate());			}			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getCreatetime());			statement.setString( 18,beanData.getCreateperson());			statement.setString( 19,beanData.getCreateunitid());			statement.setString( 20,beanData.getModifytime());			statement.setString( 21,beanData.getModifyperson());			statement.setString( 22,beanData.getDeleteflag());			statement.setString( 23,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_InventoryMonthlyReport( recordid,reportid,monthly,materialid,beginningqty,beginningprice,stockinqty,stockinprice,stockoutqty,stockoutprice,balanceqty,balanceprice,startdate,enddate,reportdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_InventoryMonthlyReport( recordid,reportid,monthly,materialid,beginningqty,beginningprice,stockinqty,stockinprice,stockoutqty,stockoutprice,balanceqty,balanceprice,startdate,enddate,reportdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getReportid()) + "',");			bufSQL.append("'" + nullString(beanData.getMonthly()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getBeginningqty()) + "',");			bufSQL.append("'" + nullString(beanData.getBeginningprice()) + "',");			bufSQL.append("'" + nullString(beanData.getStockinqty()) + "',");			bufSQL.append("'" + nullString(beanData.getStockinprice()) + "',");			bufSQL.append("'" + nullString(beanData.getStockoutqty()) + "',");			bufSQL.append("'" + nullString(beanData.getStockoutprice()) + "',");			bufSQL.append("'" + nullString(beanData.getBalanceqty()) + "',");			bufSQL.append("'" + nullString(beanData.getBalanceprice()) + "',");			bufSQL.append("'" + nullString(beanData.getStartdate()) + "',");			bufSQL.append("'" + nullString(beanData.getEnddate()) + "',");			bufSQL.append("'" + nullString(beanData.getReportdate()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_InventoryMonthlyReportData beanData = (B_InventoryMonthlyReportData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_InventoryMonthlyReport( recordid,reportid,monthly,materialid,beginningqty,beginningprice,stockinqty,stockinprice,stockoutqty,stockoutprice,balanceqty,balanceprice,startdate,enddate,reportdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReportid());			statement.setString( 3,beanData.getMonthly());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getBeginningqty());			statement.setString( 6,beanData.getBeginningprice());			statement.setString( 7,beanData.getStockinqty());			statement.setString( 8,beanData.getStockinprice());			statement.setString( 9,beanData.getStockoutqty());			statement.setString( 10,beanData.getStockoutprice());			statement.setString( 11,beanData.getBalanceqty());			statement.setString( 12,beanData.getBalanceprice());			if (beanData.getStartdate()==null || beanData.getStartdate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getStartdate());			}			if (beanData.getEnddate()==null || beanData.getEnddate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getEnddate());			}			if (beanData.getReportdate()==null || beanData.getReportdate().trim().equals(""))			{				statement.setNull( 15, Types.DATE);			}			else			{				statement.setString( 15, beanData.getReportdate());			}			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getCreatetime());			statement.setString( 18,beanData.getCreateperson());			statement.setString( 19,beanData.getCreateunitid());			statement.setString( 20,beanData.getModifytime());			statement.setString( 21,beanData.getModifyperson());			statement.setString( 22,beanData.getDeleteflag());			statement.setString( 23,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_InventoryMonthlyReport( recordid,reportid,monthly,materialid,beginningqty,beginningprice,stockinqty,stockinprice,stockoutqty,stockoutprice,balanceqty,balanceprice,startdate,enddate,reportdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_InventoryMonthlyReportData beanData = (B_InventoryMonthlyReportData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_InventoryMonthlyReport WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_InventoryMonthlyReport: "+ e.toString());
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
		B_InventoryMonthlyReportData beanData = (B_InventoryMonthlyReportData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_InventoryMonthlyReport WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_InventoryMonthlyReport"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_InventoryMonthlyReport: "+ e.toString());
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
		B_InventoryMonthlyReportData beanData = (B_InventoryMonthlyReportData) beanDataTmp; 
		B_InventoryMonthlyReportData returnData=new B_InventoryMonthlyReportData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,reportid,monthly,materialid,beginningqty,beginningprice,stockinqty,stockinprice,stockoutqty,stockoutprice,balanceqty,balanceprice,startdate,enddate,reportdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InventoryMonthlyReport WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setReportid( resultSet.getString( 2));			returnData.setMonthly( resultSet.getString( 3));			returnData.setMaterialid( resultSet.getString( 4));			returnData.setBeginningqty( resultSet.getString( 5));			returnData.setBeginningprice( resultSet.getString( 6));			returnData.setStockinqty( resultSet.getString( 7));			returnData.setStockinprice( resultSet.getString( 8));			returnData.setStockoutqty( resultSet.getString( 9));			returnData.setStockoutprice( resultSet.getString( 10));			returnData.setBalanceqty( resultSet.getString( 11));			returnData.setBalanceprice( resultSet.getString( 12));			returnData.setStartdate( resultSet.getString( 13));			returnData.setEnddate( resultSet.getString( 14));			returnData.setReportdate( resultSet.getString( 15));			returnData.setDeptguid( resultSet.getString( 16));			returnData.setCreatetime( resultSet.getString( 17));			returnData.setCreateperson( resultSet.getString( 18));			returnData.setCreateunitid( resultSet.getString( 19));			returnData.setModifytime( resultSet.getString( 20));			returnData.setModifyperson( resultSet.getString( 21));			returnData.setDeleteflag( resultSet.getString( 22));			returnData.setFormid( resultSet.getString( 23));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,reportid,monthly,materialid,beginningqty,beginningprice,stockinqty,stockinprice,stockoutqty,stockoutprice,balanceqty,balanceprice,startdate,enddate,reportdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InventoryMonthlyReport  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,reportid,monthly,materialid,beginningqty,beginningprice,stockinqty,stockinprice,stockoutqty,stockoutprice,balanceqty,balanceprice,startdate,enddate,reportdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InventoryMonthlyReport"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_InventoryMonthlyReportData returnData=new B_InventoryMonthlyReportData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setReportid( resultSet.getString( 2));				returnData.setMonthly( resultSet.getString( 3));				returnData.setMaterialid( resultSet.getString( 4));				returnData.setBeginningqty( resultSet.getString( 5));				returnData.setBeginningprice( resultSet.getString( 6));				returnData.setStockinqty( resultSet.getString( 7));				returnData.setStockinprice( resultSet.getString( 8));				returnData.setStockoutqty( resultSet.getString( 9));				returnData.setStockoutprice( resultSet.getString( 10));				returnData.setBalanceqty( resultSet.getString( 11));				returnData.setBalanceprice( resultSet.getString( 12));				returnData.setStartdate( resultSet.getString( 13));				returnData.setEnddate( resultSet.getString( 14));				returnData.setReportdate( resultSet.getString( 15));				returnData.setDeptguid( resultSet.getString( 16));				returnData.setCreatetime( resultSet.getString( 17));				returnData.setCreateperson( resultSet.getString( 18));				returnData.setCreateunitid( resultSet.getString( 19));				returnData.setModifytime( resultSet.getString( 20));				returnData.setModifyperson( resultSet.getString( 21));				returnData.setDeleteflag( resultSet.getString( 22));				returnData.setFormid( resultSet.getString( 23));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,reportid,monthly,materialid,beginningqty,beginningprice,stockinqty,stockinprice,stockoutqty,stockoutprice,balanceqty,balanceprice,startdate,enddate,reportdate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_InventoryMonthlyReport" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_InventoryMonthlyReport SET recordid= ? , reportid= ? , monthly= ? , materialid= ? , beginningqty= ? , beginningprice= ? , stockinqty= ? , stockinprice= ? , stockoutqty= ? , stockoutprice= ? , balanceqty= ? , balanceprice= ? , startdate= ? , enddate= ? , reportdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReportid());			statement.setString( 3,beanData.getMonthly());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getBeginningqty());			statement.setString( 6,beanData.getBeginningprice());			statement.setString( 7,beanData.getStockinqty());			statement.setString( 8,beanData.getStockinprice());			statement.setString( 9,beanData.getStockoutqty());			statement.setString( 10,beanData.getStockoutprice());			statement.setString( 11,beanData.getBalanceqty());			statement.setString( 12,beanData.getBalanceprice());			if (beanData.getStartdate()==null || beanData.getStartdate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getStartdate());			}			if (beanData.getEnddate()==null || beanData.getEnddate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getEnddate());			}			if (beanData.getReportdate()==null || beanData.getReportdate().trim().equals(""))			{				statement.setNull( 15, Types.DATE);			}			else			{				statement.setString( 15, beanData.getReportdate());			}			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getCreatetime());			statement.setString( 18,beanData.getCreateperson());			statement.setString( 19,beanData.getCreateunitid());			statement.setString( 20,beanData.getModifytime());			statement.setString( 21,beanData.getModifyperson());			statement.setString( 22,beanData.getDeleteflag());			statement.setString( 23,beanData.getFormid());
			statement.setString( 24,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_InventoryMonthlyReport SET recordid= ? , reportid= ? , monthly= ? , materialid= ? , beginningqty= ? , beginningprice= ? , stockinqty= ? , stockinprice= ? , stockoutqty= ? , stockoutprice= ? , balanceqty= ? , balanceprice= ? , startdate= ? , enddate= ? , reportdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_InventoryMonthlyReport SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Reportid = " + "'" + nullString(beanData.getReportid()) + "',");			bufSQL.append("Monthly = " + "'" + nullString(beanData.getMonthly()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Beginningqty = " + "'" + nullString(beanData.getBeginningqty()) + "',");			bufSQL.append("Beginningprice = " + "'" + nullString(beanData.getBeginningprice()) + "',");			bufSQL.append("Stockinqty = " + "'" + nullString(beanData.getStockinqty()) + "',");			bufSQL.append("Stockinprice = " + "'" + nullString(beanData.getStockinprice()) + "',");			bufSQL.append("Stockoutqty = " + "'" + nullString(beanData.getStockoutqty()) + "',");			bufSQL.append("Stockoutprice = " + "'" + nullString(beanData.getStockoutprice()) + "',");			bufSQL.append("Balanceqty = " + "'" + nullString(beanData.getBalanceqty()) + "',");			bufSQL.append("Balanceprice = " + "'" + nullString(beanData.getBalanceprice()) + "',");			bufSQL.append("Startdate = " + "'" + nullString(beanData.getStartdate()) + "',");			bufSQL.append("Enddate = " + "'" + nullString(beanData.getEnddate()) + "',");			bufSQL.append("Reportdate = " + "'" + nullString(beanData.getReportdate()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_InventoryMonthlyReportData beanData = (B_InventoryMonthlyReportData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_InventoryMonthlyReport SET recordid= ? , reportid= ? , monthly= ? , materialid= ? , beginningqty= ? , beginningprice= ? , stockinqty= ? , stockinprice= ? , stockoutqty= ? , stockoutprice= ? , balanceqty= ? , balanceprice= ? , startdate= ? , enddate= ? , reportdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getReportid());			statement.setString( 3,beanData.getMonthly());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getBeginningqty());			statement.setString( 6,beanData.getBeginningprice());			statement.setString( 7,beanData.getStockinqty());			statement.setString( 8,beanData.getStockinprice());			statement.setString( 9,beanData.getStockoutqty());			statement.setString( 10,beanData.getStockoutprice());			statement.setString( 11,beanData.getBalanceqty());			statement.setString( 12,beanData.getBalanceprice());			if (beanData.getStartdate()==null || beanData.getStartdate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getStartdate());			}			if (beanData.getEnddate()==null || beanData.getEnddate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getEnddate());			}			if (beanData.getReportdate()==null || beanData.getReportdate().trim().equals(""))			{				statement.setNull( 15, Types.DATE);			}			else			{				statement.setString( 15, beanData.getReportdate());			}			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getCreatetime());			statement.setString( 18,beanData.getCreateperson());			statement.setString( 19,beanData.getCreateunitid());			statement.setString( 20,beanData.getModifytime());			statement.setString( 21,beanData.getModifyperson());			statement.setString( 22,beanData.getDeleteflag());			statement.setString( 23,beanData.getFormid());
			statement.setString( 24,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_InventoryMonthlyReport SET recordid= ? , reportid= ? , monthly= ? , materialid= ? , beginningqty= ? , beginningprice= ? , stockinqty= ? , stockinprice= ? , stockoutqty= ? , stockoutprice= ? , balanceqty= ? , balanceprice= ? , startdate= ? , enddate= ? , reportdate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_InventoryMonthlyReportData beanData = (B_InventoryMonthlyReportData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_InventoryMonthlyReport WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_InventoryMonthlyReport WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
