package com.ys.business.db.dao;


import java.sql.*;
import java.io.InputStream;
import com.ys.business.db.data.*;
import com.ys.util.basedao.BaseAbstractDao;


/**
* <p>Title: </p>
* <p>Description: 数据表  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_MaterialClassDao extends BaseAbstractDao
{
	public B_MaterialClassData beanData=new B_MaterialClassData();
	public B_MaterialClassDao()
	{
	}
	public B_MaterialClassDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MaterialClassData)FindByPrimaryKey(beanData);
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
		B_MaterialClassData beanData = (B_MaterialClassData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MaterialCategory( recordid,categoryid,categoryname,parentid,formatdes,memo,ChildId,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());
			statement.setString( 2,beanData.getCategoryid());
			statement.setString( 3,beanData.getCategoryname());
			statement.setString( 4,beanData.getParentid());
			statement.setString( 5,beanData.getFormatdes());
			statement.setString( 6,beanData.getMemo());
			statement.setString( 7,beanData.getChildid());
			statement.setString( 8,beanData.getCreatetime());
			statement.setString( 9,beanData.getCreateperson());
			statement.setString( 10,beanData.getCreateunitid());
			statement.setString( 11,beanData.getModifytime());
			statement.setString( 12,beanData.getModifyperson());
			statement.setString( 13,beanData.getDeleteflag());
			statement.setString( 14,beanData.getFormid());

			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MaterialCategory( recordid,categoryid,categoryname,parentid,formatdes,memo,ChildId,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
			bufSQL.append("INSERT INTO B_MaterialCategory( recordid,categoryid,categoryname,parentid,formatdes,memo,ChildId,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");
			bufSQL.append("'" + nullString(beanData.getCategoryid()) + "',");
			bufSQL.append("'" + nullString(beanData.getCategoryname()) + "',");
			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");
			bufSQL.append("'" + nullString(beanData.getFormatdes()) + "',");
			bufSQL.append("'" + nullString(beanData.getMemo()) + "',");
			bufSQL.append("'" + nullString(beanData.getChildid()) + "',");
			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");
			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");
			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");
			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");
			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");
			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");
			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");

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
		B_MaterialClassData beanData = (B_MaterialClassData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MaterialCategory( recordid,categoryid,categoryname,parentid,formatdes,memo,ChildId,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());
			statement.setString( 2,beanData.getCategoryid());
			statement.setString( 3,beanData.getCategoryname());
			statement.setString( 4,beanData.getParentid());
			statement.setString( 5,beanData.getFormatdes());
			statement.setString( 6,beanData.getMemo());
			statement.setString( 7,beanData.getChildid());
			statement.setString( 8,beanData.getCreatetime());
			statement.setString( 9,beanData.getCreateperson());
			statement.setString( 10,beanData.getCreateunitid());
			statement.setString( 11,beanData.getModifytime());
			statement.setString( 12,beanData.getModifyperson());
			statement.setString( 13,beanData.getDeleteflag());
			statement.setString( 14,beanData.getFormid());

			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MaterialCategory( recordid,categoryid,categoryname,parentid,formatdes,memo,ChildId,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
		B_MaterialClassData beanData = (B_MaterialClassData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_MaterialCategory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MaterialCategory: "+ e.toString());
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
		B_MaterialClassData beanData = (B_MaterialClassData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_MaterialCategory WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_MaterialCategory"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MaterialCategory: "+ e.toString());
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
		B_MaterialClassData beanData = (B_MaterialClassData) beanDataTmp; 
		B_MaterialClassData returnData=new B_MaterialClassData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,categoryid,categoryname,parentid,formatdes,memo,ChildId,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialCategory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));
			returnData.setCategoryid( resultSet.getString( 2));
			returnData.setCategoryname( resultSet.getString( 3));
			returnData.setParentid( resultSet.getString( 4));
			returnData.setFormatdes( resultSet.getString( 5));
			returnData.setMemo( resultSet.getString( 6));
			returnData.setChildid( resultSet.getString( 7));
			returnData.setCreatetime( resultSet.getString( 8));
			returnData.setCreateperson( resultSet.getString( 9));
			returnData.setCreateunitid( resultSet.getString( 10));
			returnData.setModifytime( resultSet.getString( 11));
			returnData.setModifyperson( resultSet.getString( 12));
			returnData.setDeleteflag( resultSet.getString( 13));
			returnData.setFormid( resultSet.getString( 14));

			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,categoryid,categoryname,parentid,formatdes,memo,ChildId,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialCategory  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,categoryid,categoryname,parentid,formatdes,memo,ChildId,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialCategory"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MaterialClassData returnData=new B_MaterialClassData();
				returnData.setRecordid( resultSet.getString( 1));
				returnData.setCategoryid( resultSet.getString( 2));
				returnData.setCategoryname( resultSet.getString( 3));
				returnData.setParentid( resultSet.getString( 4));
				returnData.setFormatdes( resultSet.getString( 5));
				returnData.setMemo( resultSet.getString( 6));
				returnData.setChildid( resultSet.getString( 7));
				returnData.setCreatetime( resultSet.getString( 8));
				returnData.setCreateperson( resultSet.getString( 9));
				returnData.setCreateunitid( resultSet.getString( 10));
				returnData.setModifytime( resultSet.getString( 11));
				returnData.setModifyperson( resultSet.getString( 12));
				returnData.setDeleteflag( resultSet.getString( 13));
				returnData.setFormid( resultSet.getString( 14));

				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,categoryid,categoryname,parentid,formatdes,memo,ChildId,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_MaterialCategory" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_MaterialCategory SET recordid= ? , categoryid= ? , categoryname= ? , parentid= ? , formatdes= ? , memo= ? , childId= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());
			statement.setString( 2,beanData.getCategoryid());
			statement.setString( 3,beanData.getCategoryname());
			statement.setString( 4,beanData.getParentid());
			statement.setString( 5,beanData.getFormatdes());
			statement.setString( 6,beanData.getMemo());
			statement.setString( 7,beanData.getChildid());
			statement.setString( 8,beanData.getCreatetime());
			statement.setString( 9,beanData.getCreateperson());
			statement.setString( 10,beanData.getCreateunitid());
			statement.setString( 11,beanData.getModifytime());
			statement.setString( 12,beanData.getModifyperson());
			statement.setString( 13,beanData.getDeleteflag());
			statement.setString( 14,beanData.getFormid());

			statement.setString( 15,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MaterialCategory SET recordid= ? , categoryid= ? , categoryname= ? , parentid= ? , formatdes= ? , memo= ? , childId= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_MaterialCategory SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");
			bufSQL.append("Categoryid = " + "'" + nullString(beanData.getCategoryid()) + "',");
			bufSQL.append("Categoryname = " + "'" + nullString(beanData.getCategoryname()) + "',");
			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");
			bufSQL.append("Formatdes = " + "'" + nullString(beanData.getFormatdes()) + "',");
			bufSQL.append("Memo = " + "'" + nullString(beanData.getMemo()) + "',");
			bufSQL.append("ChildId = " + "'" + nullString(beanData.getChildid()) + "',");
			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");
			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");
			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");
			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");
			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");
			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");
			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");

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
		B_MaterialClassData beanData = (B_MaterialClassData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_MaterialCategory SET recordid= ? , categoryid= ? , categoryname= ? , parentid= ? , formatdes= ? , memo= ? , childId= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());
			statement.setString( 2,beanData.getCategoryid());
			statement.setString( 3,beanData.getCategoryname());
			statement.setString( 4,beanData.getParentid());
			statement.setString( 5,beanData.getFormatdes());
			statement.setString( 6,beanData.getMemo());
			statement.setString( 7,beanData.getChildid());
			statement.setString( 8,beanData.getCreatetime());
			statement.setString( 9,beanData.getCreateperson());
			statement.setString( 10,beanData.getCreateunitid());
			statement.setString( 11,beanData.getModifytime());
			statement.setString( 12,beanData.getModifyperson());
			statement.setString( 13,beanData.getDeleteflag());
			statement.setString( 14,beanData.getFormid());

			statement.setString( 15,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MaterialCategory SET recordid= ? , categoryid= ? , categoryname= ? , parentid= ? , formatdes= ? , memo= ? , childId= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param beanData:主键
	 */
	public void FindPrimaryKey(Object beanDataTmp) throws Exception
	{
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
		B_MaterialClassData beanData = (B_MaterialClassData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_MaterialCategory WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_MaterialCategory WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
