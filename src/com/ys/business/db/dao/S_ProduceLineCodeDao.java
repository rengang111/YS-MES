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
public class S_ProduceLineCodeDao extends BaseAbstractDao
{
	public S_ProduceLineCodeData beanData=new S_ProduceLineCodeData();
	public S_ProduceLineCodeDao()
	{
	}
	public S_ProduceLineCodeDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_ProduceLineCodeData)FindByPrimaryKey(beanData);
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
		S_ProduceLineCodeData beanData = (S_ProduceLineCodeData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_ProduceLineCode( recordid,codeid,codetype,codename,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCodeid());			statement.setString( 3,beanData.getCodetype());			statement.setString( 4,beanData.getCodename());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getMultilevel());			statement.setString( 8,beanData.getSortno());			statement.setString( 9,beanData.getEffectiveflag());			statement.setString( 10,beanData.getRemarks());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_ProduceLineCode( recordid,codeid,codetype,codename,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_ProduceLineCode( recordid,codeid,codetype,codename,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getCodeid()) + "',");			bufSQL.append("'" + nullString(beanData.getCodetype()) + "',");			bufSQL.append("'" + nullString(beanData.getCodename()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getMultilevel()) + "',");			bufSQL.append("'" + nullString(beanData.getSortno()) + "',");			bufSQL.append("'" + nullString(beanData.getEffectiveflag()) + "',");			bufSQL.append("'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		S_ProduceLineCodeData beanData = (S_ProduceLineCodeData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_ProduceLineCode( recordid,codeid,codetype,codename,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCodeid());			statement.setString( 3,beanData.getCodetype());			statement.setString( 4,beanData.getCodename());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getMultilevel());			statement.setString( 8,beanData.getSortno());			statement.setString( 9,beanData.getEffectiveflag());			statement.setString( 10,beanData.getRemarks());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_ProduceLineCode( recordid,codeid,codetype,codename,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_ProduceLineCodeData beanData = (S_ProduceLineCodeData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_ProduceLineCode WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_ProduceLineCode: "+ e.toString());
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
		S_ProduceLineCodeData beanData = (S_ProduceLineCodeData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_ProduceLineCode WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM S_ProduceLineCode"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_ProduceLineCode: "+ e.toString());
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
		S_ProduceLineCodeData beanData = (S_ProduceLineCodeData) beanDataTmp; 
		S_ProduceLineCodeData returnData=new S_ProduceLineCodeData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,codeid,codetype,codename,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_ProduceLineCode WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setCodeid( resultSet.getString( 2));			returnData.setCodetype( resultSet.getString( 3));			returnData.setCodename( resultSet.getString( 4));			returnData.setParentid( resultSet.getString( 5));			returnData.setSubid( resultSet.getString( 6));			returnData.setMultilevel( resultSet.getString( 7));			returnData.setSortno( resultSet.getString( 8));			returnData.setEffectiveflag( resultSet.getString( 9));			returnData.setRemarks( resultSet.getString( 10));			returnData.setDeptguid( resultSet.getString( 11));			returnData.setCreatetime( resultSet.getString( 12));			returnData.setCreateperson( resultSet.getString( 13));			returnData.setCreateunitid( resultSet.getString( 14));			returnData.setModifytime( resultSet.getString( 15));			returnData.setModifyperson( resultSet.getString( 16));			returnData.setDeleteflag( resultSet.getString( 17));			returnData.setFormid( resultSet.getString( 18));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,codeid,codetype,codename,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_ProduceLineCode  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,codeid,codetype,codename,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_ProduceLineCode"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_ProduceLineCodeData returnData=new S_ProduceLineCodeData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setCodeid( resultSet.getString( 2));				returnData.setCodetype( resultSet.getString( 3));				returnData.setCodename( resultSet.getString( 4));				returnData.setParentid( resultSet.getString( 5));				returnData.setSubid( resultSet.getString( 6));				returnData.setMultilevel( resultSet.getString( 7));				returnData.setSortno( resultSet.getString( 8));				returnData.setEffectiveflag( resultSet.getString( 9));				returnData.setRemarks( resultSet.getString( 10));				returnData.setDeptguid( resultSet.getString( 11));				returnData.setCreatetime( resultSet.getString( 12));				returnData.setCreateperson( resultSet.getString( 13));				returnData.setCreateunitid( resultSet.getString( 14));				returnData.setModifytime( resultSet.getString( 15));				returnData.setModifyperson( resultSet.getString( 16));				returnData.setDeleteflag( resultSet.getString( 17));				returnData.setFormid( resultSet.getString( 18));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,codeid,codetype,codename,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_ProduceLineCode" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_ProduceLineCode SET recordid= ? , codeid= ? , codetype= ? , codename= ? , parentid= ? , subid= ? , multilevel= ? , sortno= ? , effectiveflag= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCodeid());			statement.setString( 3,beanData.getCodetype());			statement.setString( 4,beanData.getCodename());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getMultilevel());			statement.setString( 8,beanData.getSortno());			statement.setString( 9,beanData.getEffectiveflag());			statement.setString( 10,beanData.getRemarks());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			statement.setString( 19,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_ProduceLineCode SET recordid= ? , codeid= ? , codetype= ? , codename= ? , parentid= ? , subid= ? , multilevel= ? , sortno= ? , effectiveflag= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_ProduceLineCode SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Codeid = " + "'" + nullString(beanData.getCodeid()) + "',");			bufSQL.append("Codetype = " + "'" + nullString(beanData.getCodetype()) + "',");			bufSQL.append("Codename = " + "'" + nullString(beanData.getCodename()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Multilevel = " + "'" + nullString(beanData.getMultilevel()) + "',");			bufSQL.append("Sortno = " + "'" + nullString(beanData.getSortno()) + "',");			bufSQL.append("Effectiveflag = " + "'" + nullString(beanData.getEffectiveflag()) + "',");			bufSQL.append("Remarks = " + "'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		S_ProduceLineCodeData beanData = (S_ProduceLineCodeData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_ProduceLineCode SET recordid= ? , codeid= ? , codetype= ? , codename= ? , parentid= ? , subid= ? , multilevel= ? , sortno= ? , effectiveflag= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCodeid());			statement.setString( 3,beanData.getCodetype());			statement.setString( 4,beanData.getCodename());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getMultilevel());			statement.setString( 8,beanData.getSortno());			statement.setString( 9,beanData.getEffectiveflag());			statement.setString( 10,beanData.getRemarks());			statement.setString( 11,beanData.getDeptguid());			statement.setString( 12,beanData.getCreatetime());			statement.setString( 13,beanData.getCreateperson());			statement.setString( 14,beanData.getCreateunitid());			statement.setString( 15,beanData.getModifytime());			statement.setString( 16,beanData.getModifyperson());			statement.setString( 17,beanData.getDeleteflag());			statement.setString( 18,beanData.getFormid());
			statement.setString( 19,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_ProduceLineCode SET recordid= ? , codeid= ? , codetype= ? , codename= ? , parentid= ? , subid= ? , multilevel= ? , sortno= ? , effectiveflag= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		S_ProduceLineCodeData beanData = (S_ProduceLineCodeData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM S_ProduceLineCode WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM S_ProduceLineCode WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
