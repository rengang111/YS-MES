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
public class B_ProducePlanDao extends BaseAbstractDao
{
	public B_ProducePlanData beanData=new B_ProducePlanData();
	public B_ProducePlanDao()
	{
	}
	public B_ProducePlanDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_ProducePlanData)FindByPrimaryKey(beanData);
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
		B_ProducePlanData beanData = (B_ProducePlanData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ProducePlan( recordid,ysid,produceline,sortno,finishflag,readydate,wjdate,dzdate,zzdate,bzdate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getProduceline());			statement.setString( 4,beanData.getSortno());			statement.setString( 5,beanData.getFinishflag());			if (beanData.getReadydate()==null || beanData.getReadydate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getReadydate());			}			if (beanData.getWjdate()==null || beanData.getWjdate().trim().equals(""))			{				statement.setNull( 7, Types.DATE);			}			else			{				statement.setString( 7, beanData.getWjdate());			}			if (beanData.getDzdate()==null || beanData.getDzdate().trim().equals(""))			{				statement.setNull( 8, Types.DATE);			}			else			{				statement.setString( 8, beanData.getDzdate());			}			if (beanData.getZzdate()==null || beanData.getZzdate().trim().equals(""))			{				statement.setNull( 9, Types.DATE);			}			else			{				statement.setString( 9, beanData.getZzdate());			}			if (beanData.getBzdate()==null || beanData.getBzdate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getBzdate());			}			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ProducePlan( recordid,ysid,produceline,sortno,finishflag,readydate,wjdate,dzdate,zzdate,bzdate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_ProducePlan( recordid,ysid,produceline,sortno,finishflag,readydate,wjdate,dzdate,zzdate,bzdate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getProduceline()) + "',");			bufSQL.append("'" + nullString(beanData.getSortno()) + "',");			bufSQL.append("'" + nullString(beanData.getFinishflag()) + "',");			bufSQL.append("'" + nullString(beanData.getReadydate()) + "',");			bufSQL.append("'" + nullString(beanData.getWjdate()) + "',");			bufSQL.append("'" + nullString(beanData.getDzdate()) + "',");			bufSQL.append("'" + nullString(beanData.getZzdate()) + "',");			bufSQL.append("'" + nullString(beanData.getBzdate()) + "',");			bufSQL.append("'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_ProducePlanData beanData = (B_ProducePlanData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ProducePlan( recordid,ysid,produceline,sortno,finishflag,readydate,wjdate,dzdate,zzdate,bzdate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getProduceline());			statement.setString( 4,beanData.getSortno());			statement.setString( 5,beanData.getFinishflag());			if (beanData.getReadydate()==null || beanData.getReadydate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getReadydate());			}			if (beanData.getWjdate()==null || beanData.getWjdate().trim().equals(""))			{				statement.setNull( 7, Types.DATE);			}			else			{				statement.setString( 7, beanData.getWjdate());			}			if (beanData.getDzdate()==null || beanData.getDzdate().trim().equals(""))			{				statement.setNull( 8, Types.DATE);			}			else			{				statement.setString( 8, beanData.getDzdate());			}			if (beanData.getZzdate()==null || beanData.getZzdate().trim().equals(""))			{				statement.setNull( 9, Types.DATE);			}			else			{				statement.setString( 9, beanData.getZzdate());			}			if (beanData.getBzdate()==null || beanData.getBzdate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getBzdate());			}			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ProducePlan( recordid,ysid,produceline,sortno,finishflag,readydate,wjdate,dzdate,zzdate,bzdate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_ProducePlanData beanData = (B_ProducePlanData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_ProducePlan WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ProducePlan: "+ e.toString());
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
		B_ProducePlanData beanData = (B_ProducePlanData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_ProducePlan WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_ProducePlan"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ProducePlan: "+ e.toString());
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
		B_ProducePlanData beanData = (B_ProducePlanData) beanDataTmp; 
		B_ProducePlanData returnData=new B_ProducePlanData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,ysid,produceline,sortno,finishflag,readydate,wjdate,dzdate,zzdate,bzdate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ProducePlan WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setYsid( resultSet.getString( 2));			returnData.setProduceline( resultSet.getString( 3));			returnData.setSortno( resultSet.getString( 4));			returnData.setFinishflag( resultSet.getString( 5));			returnData.setReadydate( resultSet.getString( 6));			returnData.setWjdate( resultSet.getString( 7));			returnData.setDzdate( resultSet.getString( 8));			returnData.setZzdate( resultSet.getString( 9));			returnData.setBzdate( resultSet.getString( 10));			returnData.setRemarks( resultSet.getString( 11));			returnData.setDeptguid( resultSet.getString( 12));			returnData.setCreatetime( resultSet.getString( 13));			returnData.setCreateperson( resultSet.getString( 14));			returnData.setCreateunitid( resultSet.getString( 15));			returnData.setModifytime( resultSet.getString( 16));			returnData.setModifyperson( resultSet.getString( 17));			returnData.setDeleteflag( resultSet.getString( 18));			returnData.setFormid( resultSet.getString( 19));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,produceline,sortno,finishflag,readydate,wjdate,dzdate,zzdate,bzdate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ProducePlan  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,ysid,produceline,sortno,finishflag,readydate,wjdate,dzdate,zzdate,bzdate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ProducePlan"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_ProducePlanData returnData=new B_ProducePlanData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setYsid( resultSet.getString( 2));				returnData.setProduceline( resultSet.getString( 3));				returnData.setSortno( resultSet.getString( 4));				returnData.setFinishflag( resultSet.getString( 5));				returnData.setReadydate( resultSet.getString( 6));				returnData.setWjdate( resultSet.getString( 7));				returnData.setDzdate( resultSet.getString( 8));				returnData.setZzdate( resultSet.getString( 9));				returnData.setBzdate( resultSet.getString( 10));				returnData.setRemarks( resultSet.getString( 11));				returnData.setDeptguid( resultSet.getString( 12));				returnData.setCreatetime( resultSet.getString( 13));				returnData.setCreateperson( resultSet.getString( 14));				returnData.setCreateunitid( resultSet.getString( 15));				returnData.setModifytime( resultSet.getString( 16));				returnData.setModifyperson( resultSet.getString( 17));				returnData.setDeleteflag( resultSet.getString( 18));				returnData.setFormid( resultSet.getString( 19));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,produceline,sortno,finishflag,readydate,wjdate,dzdate,zzdate,bzdate,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ProducePlan" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_ProducePlan SET recordid= ? , ysid= ? , produceline= ? , sortno= ? , finishflag= ? , readydate= ? , wjdate= ? , dzdate= ? , zzdate= ? , bzdate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getProduceline());			statement.setString( 4,beanData.getSortno());			statement.setString( 5,beanData.getFinishflag());			if (beanData.getReadydate()==null || beanData.getReadydate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getReadydate());			}			if (beanData.getWjdate()==null || beanData.getWjdate().trim().equals(""))			{				statement.setNull( 7, Types.DATE);			}			else			{				statement.setString( 7, beanData.getWjdate());			}			if (beanData.getDzdate()==null || beanData.getDzdate().trim().equals(""))			{				statement.setNull( 8, Types.DATE);			}			else			{				statement.setString( 8, beanData.getDzdate());			}			if (beanData.getZzdate()==null || beanData.getZzdate().trim().equals(""))			{				statement.setNull( 9, Types.DATE);			}			else			{				statement.setString( 9, beanData.getZzdate());			}			if (beanData.getBzdate()==null || beanData.getBzdate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getBzdate());			}			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ProducePlan SET recordid= ? , ysid= ? , produceline= ? , sortno= ? , finishflag= ? , readydate= ? , wjdate= ? , dzdate= ? , zzdate= ? , bzdate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_ProducePlan SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Produceline = " + "'" + nullString(beanData.getProduceline()) + "',");			bufSQL.append("Sortno = " + "'" + nullString(beanData.getSortno()) + "',");			bufSQL.append("Finishflag = " + "'" + nullString(beanData.getFinishflag()) + "',");			bufSQL.append("Readydate = " + "'" + nullString(beanData.getReadydate()) + "',");			bufSQL.append("Wjdate = " + "'" + nullString(beanData.getWjdate()) + "',");			bufSQL.append("Dzdate = " + "'" + nullString(beanData.getDzdate()) + "',");			bufSQL.append("Zzdate = " + "'" + nullString(beanData.getZzdate()) + "',");			bufSQL.append("Bzdate = " + "'" + nullString(beanData.getBzdate()) + "',");			bufSQL.append("Remarks = " + "'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_ProducePlanData beanData = (B_ProducePlanData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_ProducePlan SET recordid= ? , ysid= ? , produceline= ? , sortno= ? , finishflag= ? , readydate= ? , wjdate= ? , dzdate= ? , zzdate= ? , bzdate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getProduceline());			statement.setString( 4,beanData.getSortno());			statement.setString( 5,beanData.getFinishflag());			if (beanData.getReadydate()==null || beanData.getReadydate().trim().equals(""))			{				statement.setNull( 6, Types.DATE);			}			else			{				statement.setString( 6, beanData.getReadydate());			}			if (beanData.getWjdate()==null || beanData.getWjdate().trim().equals(""))			{				statement.setNull( 7, Types.DATE);			}			else			{				statement.setString( 7, beanData.getWjdate());			}			if (beanData.getDzdate()==null || beanData.getDzdate().trim().equals(""))			{				statement.setNull( 8, Types.DATE);			}			else			{				statement.setString( 8, beanData.getDzdate());			}			if (beanData.getZzdate()==null || beanData.getZzdate().trim().equals(""))			{				statement.setNull( 9, Types.DATE);			}			else			{				statement.setString( 9, beanData.getZzdate());			}			if (beanData.getBzdate()==null || beanData.getBzdate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getBzdate());			}			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ProducePlan SET recordid= ? , ysid= ? , produceline= ? , sortno= ? , finishflag= ? , readydate= ? , wjdate= ? , dzdate= ? , zzdate= ? , bzdate= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_ProducePlanData beanData = (B_ProducePlanData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_ProducePlan WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_ProducePlan WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
