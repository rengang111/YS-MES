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
public class B_OrderReviewDao extends BaseAbstractDao
{
	public B_OrderReviewData beanData=new B_OrderReviewData();
	public B_OrderReviewDao()
	{
	}
	public B_OrderReviewDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_OrderReviewData)FindByPrimaryKey(beanData);
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
		B_OrderReviewData beanData = (B_OrderReviewData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_OrderReview( recordid,ysid,bomid,materialid,rmbprice,salestax,exchangerate,rebate,rebaterate,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,status,finishdate,reviewdate,reviewman,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getBomid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getRmbprice());			statement.setString( 6,beanData.getSalestax());			statement.setString( 7,beanData.getExchangerate());			statement.setString( 8,beanData.getRebate());			statement.setString( 9,beanData.getRebaterate());			statement.setString( 10,beanData.getSalesprofit());			statement.setString( 11,beanData.getAdjustprofit());			statement.setString( 12,beanData.getTotalsalesprofit());			statement.setString( 13,beanData.getTotaladjustprofit());			statement.setString( 14,beanData.getStatus());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 15, Types.DATE);			}			else			{				statement.setString( 15, beanData.getFinishdate());			}			if (beanData.getReviewdate()==null || beanData.getReviewdate().trim().equals(""))			{				statement.setNull( 16, Types.DATE);			}			else			{				statement.setString( 16, beanData.getReviewdate());			}			statement.setString( 17,beanData.getReviewman());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_OrderReview( recordid,ysid,bomid,materialid,rmbprice,salestax,exchangerate,rebate,rebaterate,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,status,finishdate,reviewdate,reviewman,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_OrderReview( recordid,ysid,bomid,materialid,rmbprice,salestax,exchangerate,rebate,rebaterate,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,status,finishdate,reviewdate,reviewman,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getBomid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getRmbprice()) + "',");			bufSQL.append("'" + nullString(beanData.getSalestax()) + "',");			bufSQL.append("'" + nullString(beanData.getExchangerate()) + "',");			bufSQL.append("'" + nullString(beanData.getRebate()) + "',");			bufSQL.append("'" + nullString(beanData.getRebaterate()) + "',");			bufSQL.append("'" + nullString(beanData.getSalesprofit()) + "',");			bufSQL.append("'" + nullString(beanData.getAdjustprofit()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalsalesprofit()) + "',");			bufSQL.append("'" + nullString(beanData.getTotaladjustprofit()) + "',");			bufSQL.append("'" + nullString(beanData.getStatus()) + "',");			bufSQL.append("'" + nullString(beanData.getFinishdate()) + "',");			bufSQL.append("'" + nullString(beanData.getReviewdate()) + "',");			bufSQL.append("'" + nullString(beanData.getReviewman()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_OrderReviewData beanData = (B_OrderReviewData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_OrderReview( recordid,ysid,bomid,materialid,rmbprice,salestax,exchangerate,rebate,rebaterate,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,status,finishdate,reviewdate,reviewman,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getBomid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getRmbprice());			statement.setString( 6,beanData.getSalestax());			statement.setString( 7,beanData.getExchangerate());			statement.setString( 8,beanData.getRebate());			statement.setString( 9,beanData.getRebaterate());			statement.setString( 10,beanData.getSalesprofit());			statement.setString( 11,beanData.getAdjustprofit());			statement.setString( 12,beanData.getTotalsalesprofit());			statement.setString( 13,beanData.getTotaladjustprofit());			statement.setString( 14,beanData.getStatus());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 15, Types.DATE);			}			else			{				statement.setString( 15, beanData.getFinishdate());			}			if (beanData.getReviewdate()==null || beanData.getReviewdate().trim().equals(""))			{				statement.setNull( 16, Types.DATE);			}			else			{				statement.setString( 16, beanData.getReviewdate());			}			statement.setString( 17,beanData.getReviewman());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_OrderReview( recordid,ysid,bomid,materialid,rmbprice,salestax,exchangerate,rebate,rebaterate,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,status,finishdate,reviewdate,reviewman,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_OrderReviewData beanData = (B_OrderReviewData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_OrderReview WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_OrderReview: "+ e.toString());
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
		B_OrderReviewData beanData = (B_OrderReviewData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_OrderReview WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_OrderReview"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_OrderReview: "+ e.toString());
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
		B_OrderReviewData beanData = (B_OrderReviewData) beanDataTmp; 
		B_OrderReviewData returnData=new B_OrderReviewData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,ysid,bomid,materialid,rmbprice,salestax,exchangerate,rebate,rebaterate,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,status,finishdate,reviewdate,reviewman,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_OrderReview WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setYsid( resultSet.getString( 2));			returnData.setBomid( resultSet.getString( 3));			returnData.setMaterialid( resultSet.getString( 4));			returnData.setRmbprice( resultSet.getString( 5));			returnData.setSalestax( resultSet.getString( 6));			returnData.setExchangerate( resultSet.getString( 7));			returnData.setRebate( resultSet.getString( 8));			returnData.setRebaterate( resultSet.getString( 9));			returnData.setSalesprofit( resultSet.getString( 10));			returnData.setAdjustprofit( resultSet.getString( 11));			returnData.setTotalsalesprofit( resultSet.getString( 12));			returnData.setTotaladjustprofit( resultSet.getString( 13));			returnData.setStatus( resultSet.getString( 14));			returnData.setFinishdate( resultSet.getString( 15));			returnData.setReviewdate( resultSet.getString( 16));			returnData.setReviewman( resultSet.getString( 17));			returnData.setDeptguid( resultSet.getString( 18));			returnData.setCreatetime( resultSet.getString( 19));			returnData.setCreateperson( resultSet.getString( 20));			returnData.setCreateunitid( resultSet.getString( 21));			returnData.setModifytime( resultSet.getString( 22));			returnData.setModifyperson( resultSet.getString( 23));			returnData.setDeleteflag( resultSet.getString( 24));			returnData.setFormid( resultSet.getString( 25));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,bomid,materialid,rmbprice,salestax,exchangerate,rebate,rebaterate,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,status,finishdate,reviewdate,reviewman,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_OrderReview  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,ysid,bomid,materialid,rmbprice,salestax,exchangerate,rebate,rebaterate,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,status,finishdate,reviewdate,reviewman,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_OrderReview"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_OrderReviewData returnData=new B_OrderReviewData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setYsid( resultSet.getString( 2));				returnData.setBomid( resultSet.getString( 3));				returnData.setMaterialid( resultSet.getString( 4));				returnData.setRmbprice( resultSet.getString( 5));				returnData.setSalestax( resultSet.getString( 6));				returnData.setExchangerate( resultSet.getString( 7));				returnData.setRebate( resultSet.getString( 8));				returnData.setRebaterate( resultSet.getString( 9));				returnData.setSalesprofit( resultSet.getString( 10));				returnData.setAdjustprofit( resultSet.getString( 11));				returnData.setTotalsalesprofit( resultSet.getString( 12));				returnData.setTotaladjustprofit( resultSet.getString( 13));				returnData.setStatus( resultSet.getString( 14));				returnData.setFinishdate( resultSet.getString( 15));				returnData.setReviewdate( resultSet.getString( 16));				returnData.setReviewman( resultSet.getString( 17));				returnData.setDeptguid( resultSet.getString( 18));				returnData.setCreatetime( resultSet.getString( 19));				returnData.setCreateperson( resultSet.getString( 20));				returnData.setCreateunitid( resultSet.getString( 21));				returnData.setModifytime( resultSet.getString( 22));				returnData.setModifyperson( resultSet.getString( 23));				returnData.setDeleteflag( resultSet.getString( 24));				returnData.setFormid( resultSet.getString( 25));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,bomid,materialid,rmbprice,salestax,exchangerate,rebate,rebaterate,salesprofit,adjustprofit,totalsalesprofit,totaladjustprofit,status,finishdate,reviewdate,reviewman,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_OrderReview" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_OrderReview SET recordid= ? , ysid= ? , bomid= ? , materialid= ? , rmbprice= ? , salestax= ? , exchangerate= ? , rebate= ? , rebaterate= ? , salesprofit= ? , adjustprofit= ? , totalsalesprofit= ? , totaladjustprofit= ? , status= ? , finishdate= ? , reviewdate= ? , reviewman= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getBomid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getRmbprice());			statement.setString( 6,beanData.getSalestax());			statement.setString( 7,beanData.getExchangerate());			statement.setString( 8,beanData.getRebate());			statement.setString( 9,beanData.getRebaterate());			statement.setString( 10,beanData.getSalesprofit());			statement.setString( 11,beanData.getAdjustprofit());			statement.setString( 12,beanData.getTotalsalesprofit());			statement.setString( 13,beanData.getTotaladjustprofit());			statement.setString( 14,beanData.getStatus());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 15, Types.DATE);			}			else			{				statement.setString( 15, beanData.getFinishdate());			}			if (beanData.getReviewdate()==null || beanData.getReviewdate().trim().equals(""))			{				statement.setNull( 16, Types.DATE);			}			else			{				statement.setString( 16, beanData.getReviewdate());			}			statement.setString( 17,beanData.getReviewman());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			statement.setString( 26,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_OrderReview SET recordid= ? , ysid= ? , bomid= ? , materialid= ? , rmbprice= ? , salestax= ? , exchangerate= ? , rebate= ? , rebaterate= ? , salesprofit= ? , adjustprofit= ? , totalsalesprofit= ? , totaladjustprofit= ? , status= ? , finishdate= ? , reviewdate= ? , reviewman= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_OrderReview SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Bomid = " + "'" + nullString(beanData.getBomid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Rmbprice = " + "'" + nullString(beanData.getRmbprice()) + "',");			bufSQL.append("Salestax = " + "'" + nullString(beanData.getSalestax()) + "',");			bufSQL.append("Exchangerate = " + "'" + nullString(beanData.getExchangerate()) + "',");			bufSQL.append("Rebate = " + "'" + nullString(beanData.getRebate()) + "',");			bufSQL.append("Rebaterate = " + "'" + nullString(beanData.getRebaterate()) + "',");			bufSQL.append("Salesprofit = " + "'" + nullString(beanData.getSalesprofit()) + "',");			bufSQL.append("Adjustprofit = " + "'" + nullString(beanData.getAdjustprofit()) + "',");			bufSQL.append("Totalsalesprofit = " + "'" + nullString(beanData.getTotalsalesprofit()) + "',");			bufSQL.append("Totaladjustprofit = " + "'" + nullString(beanData.getTotaladjustprofit()) + "',");			bufSQL.append("Status = " + "'" + nullString(beanData.getStatus()) + "',");			bufSQL.append("Finishdate = " + "'" + nullString(beanData.getFinishdate()) + "',");			bufSQL.append("Reviewdate = " + "'" + nullString(beanData.getReviewdate()) + "',");			bufSQL.append("Reviewman = " + "'" + nullString(beanData.getReviewman()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_OrderReviewData beanData = (B_OrderReviewData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_OrderReview SET recordid= ? , ysid= ? , bomid= ? , materialid= ? , rmbprice= ? , salestax= ? , exchangerate= ? , rebate= ? , rebaterate= ? , salesprofit= ? , adjustprofit= ? , totalsalesprofit= ? , totaladjustprofit= ? , status= ? , finishdate= ? , reviewdate= ? , reviewman= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getBomid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getRmbprice());			statement.setString( 6,beanData.getSalestax());			statement.setString( 7,beanData.getExchangerate());			statement.setString( 8,beanData.getRebate());			statement.setString( 9,beanData.getRebaterate());			statement.setString( 10,beanData.getSalesprofit());			statement.setString( 11,beanData.getAdjustprofit());			statement.setString( 12,beanData.getTotalsalesprofit());			statement.setString( 13,beanData.getTotaladjustprofit());			statement.setString( 14,beanData.getStatus());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 15, Types.DATE);			}			else			{				statement.setString( 15, beanData.getFinishdate());			}			if (beanData.getReviewdate()==null || beanData.getReviewdate().trim().equals(""))			{				statement.setNull( 16, Types.DATE);			}			else			{				statement.setString( 16, beanData.getReviewdate());			}			statement.setString( 17,beanData.getReviewman());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			statement.setString( 26,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_OrderReview SET recordid= ? , ysid= ? , bomid= ? , materialid= ? , rmbprice= ? , salestax= ? , exchangerate= ? , rebate= ? , rebaterate= ? , salesprofit= ? , adjustprofit= ? , totalsalesprofit= ? , totaladjustprofit= ? , status= ? , finishdate= ? , reviewdate= ? , reviewman= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_OrderReviewData beanData = (B_OrderReviewData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_OrderReview WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_OrderReview WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
