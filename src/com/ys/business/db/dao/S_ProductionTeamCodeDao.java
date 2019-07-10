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
public class S_ProductionTeamCodeDao extends BaseAbstractDao
{
	public S_ProductionTeamCodeData beanData=new S_ProductionTeamCodeData();
	public S_ProductionTeamCodeDao()
	{
	}
	public S_ProductionTeamCodeDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_ProductionTeamCodeData)FindByPrimaryKey(beanData);
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
		S_ProductionTeamCodeData beanData = (S_ProductionTeamCodeData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_ProductionTeamCode( recordid,codeid,productiontechnical,employeeskills,groupleader,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCodeid());			statement.setString( 3,beanData.getProductiontechnical());			statement.setString( 4,beanData.getEmployeeskills());			statement.setString( 5,beanData.getGroupleader());			statement.setString( 6,beanData.getParentid());			statement.setString( 7,beanData.getSubid());			statement.setString( 8,beanData.getMultilevel());			statement.setString( 9,beanData.getSortno());			statement.setString( 10,beanData.getEffectiveflag());			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_ProductionTeamCode( recordid,codeid,productiontechnical,employeeskills,groupleader,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_ProductionTeamCode( recordid,codeid,productiontechnical,employeeskills,groupleader,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getCodeid()) + "',");			bufSQL.append("'" + nullString(beanData.getProductiontechnical()) + "',");			bufSQL.append("'" + nullString(beanData.getEmployeeskills()) + "',");			bufSQL.append("'" + nullString(beanData.getGroupleader()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getMultilevel()) + "',");			bufSQL.append("'" + nullString(beanData.getSortno()) + "',");			bufSQL.append("'" + nullString(beanData.getEffectiveflag()) + "',");			bufSQL.append("'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		S_ProductionTeamCodeData beanData = (S_ProductionTeamCodeData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_ProductionTeamCode( recordid,codeid,productiontechnical,employeeskills,groupleader,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCodeid());			statement.setString( 3,beanData.getProductiontechnical());			statement.setString( 4,beanData.getEmployeeskills());			statement.setString( 5,beanData.getGroupleader());			statement.setString( 6,beanData.getParentid());			statement.setString( 7,beanData.getSubid());			statement.setString( 8,beanData.getMultilevel());			statement.setString( 9,beanData.getSortno());			statement.setString( 10,beanData.getEffectiveflag());			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_ProductionTeamCode( recordid,codeid,productiontechnical,employeeskills,groupleader,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_ProductionTeamCodeData beanData = (S_ProductionTeamCodeData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_ProductionTeamCode WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_ProductionTeamCode: "+ e.toString());
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
		S_ProductionTeamCodeData beanData = (S_ProductionTeamCodeData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_ProductionTeamCode WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM S_ProductionTeamCode"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_ProductionTeamCode: "+ e.toString());
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
		S_ProductionTeamCodeData beanData = (S_ProductionTeamCodeData) beanDataTmp; 
		S_ProductionTeamCodeData returnData=new S_ProductionTeamCodeData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,codeid,productiontechnical,employeeskills,groupleader,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_ProductionTeamCode WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setCodeid( resultSet.getString( 2));			returnData.setProductiontechnical( resultSet.getString( 3));			returnData.setEmployeeskills( resultSet.getString( 4));			returnData.setGroupleader( resultSet.getString( 5));			returnData.setParentid( resultSet.getString( 6));			returnData.setSubid( resultSet.getString( 7));			returnData.setMultilevel( resultSet.getString( 8));			returnData.setSortno( resultSet.getString( 9));			returnData.setEffectiveflag( resultSet.getString( 10));			returnData.setRemarks( resultSet.getString( 11));			returnData.setDeptguid( resultSet.getString( 12));			returnData.setCreatetime( resultSet.getString( 13));			returnData.setCreateperson( resultSet.getString( 14));			returnData.setCreateunitid( resultSet.getString( 15));			returnData.setModifytime( resultSet.getString( 16));			returnData.setModifyperson( resultSet.getString( 17));			returnData.setDeleteflag( resultSet.getString( 18));			returnData.setFormid( resultSet.getString( 19));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,codeid,productiontechnical,employeeskills,groupleader,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_ProductionTeamCode  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,codeid,productiontechnical,employeeskills,groupleader,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_ProductionTeamCode"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_ProductionTeamCodeData returnData=new S_ProductionTeamCodeData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setCodeid( resultSet.getString( 2));				returnData.setProductiontechnical( resultSet.getString( 3));				returnData.setEmployeeskills( resultSet.getString( 4));				returnData.setGroupleader( resultSet.getString( 5));				returnData.setParentid( resultSet.getString( 6));				returnData.setSubid( resultSet.getString( 7));				returnData.setMultilevel( resultSet.getString( 8));				returnData.setSortno( resultSet.getString( 9));				returnData.setEffectiveflag( resultSet.getString( 10));				returnData.setRemarks( resultSet.getString( 11));				returnData.setDeptguid( resultSet.getString( 12));				returnData.setCreatetime( resultSet.getString( 13));				returnData.setCreateperson( resultSet.getString( 14));				returnData.setCreateunitid( resultSet.getString( 15));				returnData.setModifytime( resultSet.getString( 16));				returnData.setModifyperson( resultSet.getString( 17));				returnData.setDeleteflag( resultSet.getString( 18));				returnData.setFormid( resultSet.getString( 19));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,codeid,productiontechnical,employeeskills,groupleader,parentid,subid,multilevel,sortno,effectiveflag,remarks,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM S_ProductionTeamCode" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_ProductionTeamCode SET recordid= ? , codeid= ? , productiontechnical= ? , employeeskills= ? , groupleader= ? , parentid= ? , subid= ? , multilevel= ? , sortno= ? , effectiveflag= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCodeid());			statement.setString( 3,beanData.getProductiontechnical());			statement.setString( 4,beanData.getEmployeeskills());			statement.setString( 5,beanData.getGroupleader());			statement.setString( 6,beanData.getParentid());			statement.setString( 7,beanData.getSubid());			statement.setString( 8,beanData.getMultilevel());			statement.setString( 9,beanData.getSortno());			statement.setString( 10,beanData.getEffectiveflag());			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_ProductionTeamCode SET recordid= ? , codeid= ? , productiontechnical= ? , employeeskills= ? , groupleader= ? , parentid= ? , subid= ? , multilevel= ? , sortno= ? , effectiveflag= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_ProductionTeamCode SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Codeid = " + "'" + nullString(beanData.getCodeid()) + "',");			bufSQL.append("Productiontechnical = " + "'" + nullString(beanData.getProductiontechnical()) + "',");			bufSQL.append("Employeeskills = " + "'" + nullString(beanData.getEmployeeskills()) + "',");			bufSQL.append("Groupleader = " + "'" + nullString(beanData.getGroupleader()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Multilevel = " + "'" + nullString(beanData.getMultilevel()) + "',");			bufSQL.append("Sortno = " + "'" + nullString(beanData.getSortno()) + "',");			bufSQL.append("Effectiveflag = " + "'" + nullString(beanData.getEffectiveflag()) + "',");			bufSQL.append("Remarks = " + "'" + nullString(beanData.getRemarks()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		S_ProductionTeamCodeData beanData = (S_ProductionTeamCodeData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_ProductionTeamCode SET recordid= ? , codeid= ? , productiontechnical= ? , employeeskills= ? , groupleader= ? , parentid= ? , subid= ? , multilevel= ? , sortno= ? , effectiveflag= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getCodeid());			statement.setString( 3,beanData.getProductiontechnical());			statement.setString( 4,beanData.getEmployeeskills());			statement.setString( 5,beanData.getGroupleader());			statement.setString( 6,beanData.getParentid());			statement.setString( 7,beanData.getSubid());			statement.setString( 8,beanData.getMultilevel());			statement.setString( 9,beanData.getSortno());			statement.setString( 10,beanData.getEffectiveflag());			statement.setString( 11,beanData.getRemarks());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_ProductionTeamCode SET recordid= ? , codeid= ? , productiontechnical= ? , employeeskills= ? , groupleader= ? , parentid= ? , subid= ? , multilevel= ? , sortno= ? , effectiveflag= ? , remarks= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		S_ProductionTeamCodeData beanData = (S_ProductionTeamCodeData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM S_ProductionTeamCode WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM S_ProductionTeamCode WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
