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
public class B_PriceReferenceDao extends BaseAbstractDao
{
	public B_PriceReferenceData beanData=new B_PriceReferenceData();
	public B_PriceReferenceDao()
	{
	}
	public B_PriceReferenceDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_PriceReferenceData)FindByPrimaryKey(beanData);
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
		B_PriceReferenceData beanData = (B_PriceReferenceData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PriceReference( recordid,materialid,lastprice,lastsupplierid,lastdate,minprice,minsupplierid,mindate,usedflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getLastprice());			statement.setString( 4,beanData.getLastsupplierid());			if (beanData.getLastdate()==null || beanData.getLastdate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getLastdate());			}			statement.setString( 6,beanData.getMinprice());			statement.setString( 7,beanData.getMinsupplierid());			if (beanData.getMindate()==null || beanData.getMindate().trim().equals(""))			{				statement.setNull( 8, Types.DATE);			}			else			{				statement.setString( 8, beanData.getMindate());			}			statement.setString( 9,beanData.getUsedflag());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PriceReference( recordid,materialid,lastprice,lastsupplierid,lastdate,minprice,minsupplierid,mindate,usedflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_PriceReference( recordid,materialid,lastprice,lastsupplierid,lastdate,minprice,minsupplierid,mindate,usedflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getLastprice()) + "',");			bufSQL.append("'" + nullString(beanData.getLastsupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getLastdate()) + "',");			bufSQL.append("'" + nullString(beanData.getMinprice()) + "',");			bufSQL.append("'" + nullString(beanData.getMinsupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getMindate()) + "',");			bufSQL.append("'" + nullString(beanData.getUsedflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_PriceReferenceData beanData = (B_PriceReferenceData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PriceReference( recordid,materialid,lastprice,lastsupplierid,lastdate,minprice,minsupplierid,mindate,usedflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getLastprice());			statement.setString( 4,beanData.getLastsupplierid());			if (beanData.getLastdate()==null || beanData.getLastdate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getLastdate());			}			statement.setString( 6,beanData.getMinprice());			statement.setString( 7,beanData.getMinsupplierid());			if (beanData.getMindate()==null || beanData.getMindate().trim().equals(""))			{				statement.setNull( 8, Types.DATE);			}			else			{				statement.setString( 8, beanData.getMindate());			}			statement.setString( 9,beanData.getUsedflag());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PriceReference( recordid,materialid,lastprice,lastsupplierid,lastdate,minprice,minsupplierid,mindate,usedflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_PriceReferenceData beanData = (B_PriceReferenceData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_PriceReference WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PriceReference: "+ e.toString());
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
		B_PriceReferenceData beanData = (B_PriceReferenceData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_PriceReference WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_PriceReference"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PriceReference: "+ e.toString());
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
		B_PriceReferenceData beanData = (B_PriceReferenceData) beanDataTmp; 
		B_PriceReferenceData returnData=new B_PriceReferenceData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,materialid,lastprice,lastsupplierid,lastdate,minprice,minsupplierid,mindate,usedflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PriceReference WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setMaterialid( resultSet.getString( 2));			returnData.setLastprice( resultSet.getString( 3));			returnData.setLastsupplierid( resultSet.getString( 4));			returnData.setLastdate( resultSet.getString( 5));			returnData.setMinprice( resultSet.getString( 6));			returnData.setMinsupplierid( resultSet.getString( 7));			returnData.setMindate( resultSet.getString( 8));			returnData.setUsedflag( resultSet.getString( 9));			returnData.setDeptguid( resultSet.getString( 10));			returnData.setCreatetime( resultSet.getString( 11));			returnData.setCreateperson( resultSet.getString( 12));			returnData.setCreateunitid( resultSet.getString( 13));			returnData.setModifytime( resultSet.getString( 14));			returnData.setModifyperson( resultSet.getString( 15));			returnData.setDeleteflag( resultSet.getString( 16));			returnData.setFormid( resultSet.getString( 17));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,materialid,lastprice,lastsupplierid,lastdate,minprice,minsupplierid,mindate,usedflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PriceReference  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,materialid,lastprice,lastsupplierid,lastdate,minprice,minsupplierid,mindate,usedflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PriceReference"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_PriceReferenceData returnData=new B_PriceReferenceData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setMaterialid( resultSet.getString( 2));				returnData.setLastprice( resultSet.getString( 3));				returnData.setLastsupplierid( resultSet.getString( 4));				returnData.setLastdate( resultSet.getString( 5));				returnData.setMinprice( resultSet.getString( 6));				returnData.setMinsupplierid( resultSet.getString( 7));				returnData.setMindate( resultSet.getString( 8));				returnData.setUsedflag( resultSet.getString( 9));				returnData.setDeptguid( resultSet.getString( 10));				returnData.setCreatetime( resultSet.getString( 11));				returnData.setCreateperson( resultSet.getString( 12));				returnData.setCreateunitid( resultSet.getString( 13));				returnData.setModifytime( resultSet.getString( 14));				returnData.setModifyperson( resultSet.getString( 15));				returnData.setDeleteflag( resultSet.getString( 16));				returnData.setFormid( resultSet.getString( 17));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,materialid,lastprice,lastsupplierid,lastdate,minprice,minsupplierid,mindate,usedflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PriceReference" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_PriceReference SET recordid= ? , materialid= ? , lastprice= ? , lastsupplierid= ? , lastdate= ? , minprice= ? , minsupplierid= ? , mindate= ? , usedflag= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getLastprice());			statement.setString( 4,beanData.getLastsupplierid());			if (beanData.getLastdate()==null || beanData.getLastdate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getLastdate());			}			statement.setString( 6,beanData.getMinprice());			statement.setString( 7,beanData.getMinsupplierid());			if (beanData.getMindate()==null || beanData.getMindate().trim().equals(""))			{				statement.setNull( 8, Types.DATE);			}			else			{				statement.setString( 8, beanData.getMindate());			}			statement.setString( 9,beanData.getUsedflag());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PriceReference SET recordid= ? , materialid= ? , lastprice= ? , lastsupplierid= ? , lastdate= ? , minprice= ? , minsupplierid= ? , mindate= ? , usedflag= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_PriceReference SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Lastprice = " + "'" + nullString(beanData.getLastprice()) + "',");			bufSQL.append("Lastsupplierid = " + "'" + nullString(beanData.getLastsupplierid()) + "',");			bufSQL.append("Lastdate = " + "'" + nullString(beanData.getLastdate()) + "',");			bufSQL.append("Minprice = " + "'" + nullString(beanData.getMinprice()) + "',");			bufSQL.append("Minsupplierid = " + "'" + nullString(beanData.getMinsupplierid()) + "',");			bufSQL.append("Mindate = " + "'" + nullString(beanData.getMindate()) + "',");			bufSQL.append("Usedflag = " + "'" + nullString(beanData.getUsedflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_PriceReferenceData beanData = (B_PriceReferenceData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_PriceReference SET recordid= ? , materialid= ? , lastprice= ? , lastsupplierid= ? , lastdate= ? , minprice= ? , minsupplierid= ? , mindate= ? , usedflag= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getLastprice());			statement.setString( 4,beanData.getLastsupplierid());			if (beanData.getLastdate()==null || beanData.getLastdate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getLastdate());			}			statement.setString( 6,beanData.getMinprice());			statement.setString( 7,beanData.getMinsupplierid());			if (beanData.getMindate()==null || beanData.getMindate().trim().equals(""))			{				statement.setNull( 8, Types.DATE);			}			else			{				statement.setString( 8, beanData.getMindate());			}			statement.setString( 9,beanData.getUsedflag());			statement.setString( 10,beanData.getDeptguid());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PriceReference SET recordid= ? , materialid= ? , lastprice= ? , lastsupplierid= ? , lastdate= ? , minprice= ? , minsupplierid= ? , mindate= ? , usedflag= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_PriceReferenceData beanData = (B_PriceReferenceData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_PriceReference WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_PriceReference WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
