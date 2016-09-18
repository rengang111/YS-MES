package com.ys.system.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.util.basedao.BaseAbstractDao;
import com.ys.system.db.data.*;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class S_MENUDao extends BaseAbstractDao
{
	public S_MENUData beanData=new S_MENUData();
	public S_MENUDao()
	{
	}
	public S_MENUDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_MENUData)FindByPrimaryKey(beanData);
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
		S_MENUData beanData = (S_MENUData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_MENU( menuid,menuname,menudes,menuywclass,menuparentid,menuurl,menuicon1,menuicon2,menutype,menuopenflag,menuviewflag,menuwfnode,menunnableflag,relationalmenuid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getMenuid());			statement.setString( 2,beanData.getMenuname());			statement.setString( 3,beanData.getMenudes());			statement.setString( 4,beanData.getMenuywclass());			statement.setString( 5,beanData.getMenuparentid());			statement.setString( 6,beanData.getMenuurl());			statement.setString( 7,beanData.getMenuicon1());			statement.setString( 8,beanData.getMenuicon2());			statement.setString( 9,beanData.getMenutype());			statement.setString( 10,beanData.getMenuopenflag());			statement.setString( 11,beanData.getMenuviewflag());			statement.setString( 12,beanData.getMenuwfnode());			statement.setString( 13,beanData.getMenunnableflag());			statement.setString( 14,beanData.getRelationalmenuid());			if (beanData.getSortno()== null)			{				statement.setNull( 15, Types.INTEGER);			}			else			{				statement.setInt( 15, beanData.getSortno().intValue());			}			statement.setString( 16,beanData.getCreatetime());			statement.setString( 17,beanData.getCreateperson());			statement.setString( 18,beanData.getCreateunitid());			statement.setString( 19,beanData.getModifytime());			statement.setString( 20,beanData.getModifyperson());			statement.setString( 21,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_MENU( menuid,menuname,menudes,menuywclass,menuparentid,menuurl,menuicon1,menuicon2,menutype,menuopenflag,menuviewflag,menuwfnode,menunnableflag,relationalmenuid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_MENU( menuid,menuname,menudes,menuywclass,menuparentid,menuurl,menuicon1,menuicon2,menutype,menuopenflag,menuviewflag,menuwfnode,menunnableflag,relationalmenuid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getMenuid()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuname()) + "',");			bufSQL.append("'" + nullString(beanData.getMenudes()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuywclass()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuparentid()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuurl()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuicon1()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuicon2()) + "',");			bufSQL.append("'" + nullString(beanData.getMenutype()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuopenflag()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuviewflag()) + "',");			bufSQL.append("'" + nullString(beanData.getMenuwfnode()) + "',");			bufSQL.append("'" + nullString(beanData.getMenunnableflag()) + "',");			bufSQL.append("'" + nullString(beanData.getRelationalmenuid()) + "',");			bufSQL.append(beanData.getSortno().intValue() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		S_MENUData beanData = (S_MENUData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_MENU( menuid,menuname,menudes,menuywclass,menuparentid,menuurl,menuicon1,menuicon2,menutype,menuopenflag,menuviewflag,menuwfnode,menunnableflag,relationalmenuid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getMenuid());			statement.setString( 2,beanData.getMenuname());			statement.setString( 3,beanData.getMenudes());			statement.setString( 4,beanData.getMenuywclass());			statement.setString( 5,beanData.getMenuparentid());			statement.setString( 6,beanData.getMenuurl());			statement.setString( 7,beanData.getMenuicon1());			statement.setString( 8,beanData.getMenuicon2());			statement.setString( 9,beanData.getMenutype());			statement.setString( 10,beanData.getMenuopenflag());			statement.setString( 11,beanData.getMenuviewflag());			statement.setString( 12,beanData.getMenuwfnode());			statement.setString( 13,beanData.getMenunnableflag());			statement.setString( 14,beanData.getRelationalmenuid());			if (beanData.getSortno()== null)			{				statement.setNull( 15, Types.INTEGER);			}			else			{				statement.setInt( 15, beanData.getSortno().intValue());			}			statement.setString( 16,beanData.getCreatetime());			statement.setString( 17,beanData.getCreateperson());			statement.setString( 18,beanData.getCreateunitid());			statement.setString( 19,beanData.getModifytime());			statement.setString( 20,beanData.getModifyperson());			statement.setString( 21,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_MENU( menuid,menuname,menudes,menuywclass,menuparentid,menuurl,menuicon1,menuicon2,menutype,menuopenflag,menuviewflag,menuwfnode,menunnableflag,relationalmenuid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_MENUData beanData = (S_MENUData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_MENU WHERE  menuid =?");
			statement.setString( 1,beanData.getMenuid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_MENU: "+ e.toString());
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
		S_MENUData beanData = (S_MENUData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_MENU WHERE ");
			bufSQL.append("Menuid = " + "'" + nullString(beanData.getMenuid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM S_MENU"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_MENU: "+ e.toString());
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
		S_MENUData beanData = (S_MENUData) beanDataTmp; 
		S_MENUData returnData=new S_MENUData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT menuid,menuname,menudes,menuywclass,menuparentid,menuurl,menuicon1,menuicon2,menutype,menuopenflag,menuviewflag,menuwfnode,menunnableflag,relationalmenuid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM S_MENU WHERE  menuid =?");
			statement.setString( 1,beanData.getMenuid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setMenuid( resultSet.getString( 1));			returnData.setMenuname( resultSet.getString( 2));			returnData.setMenudes( resultSet.getString( 3));			returnData.setMenuywclass( resultSet.getString( 4));			returnData.setMenuparentid( resultSet.getString( 5));			returnData.setMenuurl( resultSet.getString( 6));			returnData.setMenuicon1( resultSet.getString( 7));			returnData.setMenuicon2( resultSet.getString( 8));			returnData.setMenutype( resultSet.getString( 9));			returnData.setMenuopenflag( resultSet.getString( 10));			returnData.setMenuviewflag( resultSet.getString( 11));			returnData.setMenuwfnode( resultSet.getString( 12));			returnData.setMenunnableflag( resultSet.getString( 13));			returnData.setRelationalmenuid( resultSet.getString( 14));			if (resultSet.getString( 15)==null)				returnData.setSortno(null);			else				returnData.setSortno( new Integer(resultSet.getInt( 15)));			returnData.setCreatetime( resultSet.getString( 16));			returnData.setCreateperson( resultSet.getString( 17));			returnData.setCreateunitid( resultSet.getString( 18));			returnData.setModifytime( resultSet.getString( 19));			returnData.setModifyperson( resultSet.getString( 20));			returnData.setDeleteflag( resultSet.getString( 21));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT menuid,menuname,menudes,menuywclass,menuparentid,menuurl,menuicon1,menuicon2,menutype,menuopenflag,menuviewflag,menuwfnode,menunnableflag,relationalmenuid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM S_MENU  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT menuid,menuname,menudes,menuywclass,menuparentid,menuurl,menuicon1,menuicon2,menutype,menuopenflag,menuviewflag,menuwfnode,menunnableflag,relationalmenuid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM S_MENU"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_MENUData returnData=new S_MENUData();
				returnData.setMenuid( resultSet.getString( 1));				returnData.setMenuname( resultSet.getString( 2));				returnData.setMenudes( resultSet.getString( 3));				returnData.setMenuywclass( resultSet.getString( 4));				returnData.setMenuparentid( resultSet.getString( 5));				returnData.setMenuurl( resultSet.getString( 6));				returnData.setMenuicon1( resultSet.getString( 7));				returnData.setMenuicon2( resultSet.getString( 8));				returnData.setMenutype( resultSet.getString( 9));				returnData.setMenuopenflag( resultSet.getString( 10));				returnData.setMenuviewflag( resultSet.getString( 11));				returnData.setMenuwfnode( resultSet.getString( 12));				returnData.setMenunnableflag( resultSet.getString( 13));				returnData.setRelationalmenuid( resultSet.getString( 14));				if (resultSet.getString( 15)==null)					returnData.setSortno(null);				else					returnData.setSortno( new Integer(resultSet.getInt( 15)));				returnData.setCreatetime( resultSet.getString( 16));				returnData.setCreateperson( resultSet.getString( 17));				returnData.setCreateunitid( resultSet.getString( 18));				returnData.setModifytime( resultSet.getString( 19));				returnData.setModifyperson( resultSet.getString( 20));				returnData.setDeleteflag( resultSet.getString( 21));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT menuid,menuname,menudes,menuywclass,menuparentid,menuurl,menuicon1,menuicon2,menutype,menuopenflag,menuviewflag,menuwfnode,menunnableflag,relationalmenuid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM S_MENU" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_MENU SET menuid= ? , menuname= ? , menudes= ? , menuywclass= ? , menuparentid= ? , menuurl= ? , menuicon1= ? , menuicon2= ? , menutype= ? , menuopenflag= ? , menuviewflag= ? , menuwfnode= ? , menunnableflag= ? , relationalmenuid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  menuid  = ?");
			statement.setString( 1,beanData.getMenuid());			statement.setString( 2,beanData.getMenuname());			statement.setString( 3,beanData.getMenudes());			statement.setString( 4,beanData.getMenuywclass());			statement.setString( 5,beanData.getMenuparentid());			statement.setString( 6,beanData.getMenuurl());			statement.setString( 7,beanData.getMenuicon1());			statement.setString( 8,beanData.getMenuicon2());			statement.setString( 9,beanData.getMenutype());			statement.setString( 10,beanData.getMenuopenflag());			statement.setString( 11,beanData.getMenuviewflag());			statement.setString( 12,beanData.getMenuwfnode());			statement.setString( 13,beanData.getMenunnableflag());			statement.setString( 14,beanData.getRelationalmenuid());			if (beanData.getSortno()== null)			{				statement.setNull( 15, Types.INTEGER);			}			else			{				statement.setInt( 15, beanData.getSortno().intValue());			}			statement.setString( 16,beanData.getCreatetime());			statement.setString( 17,beanData.getCreateperson());			statement.setString( 18,beanData.getCreateunitid());			statement.setString( 19,beanData.getModifytime());			statement.setString( 20,beanData.getModifyperson());			statement.setString( 21,beanData.getDeleteflag());
			statement.setString( 22,beanData.getMenuid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_MENU SET menuid= ? , menuname= ? , menudes= ? , menuywclass= ? , menuparentid= ? , menuurl= ? , menuicon1= ? , menuicon2= ? , menutype= ? , menuopenflag= ? , menuviewflag= ? , menuwfnode= ? , menunnableflag= ? , relationalmenuid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  menuid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_MENU SET ");
			bufSQL.append("Menuid = " + "'" + nullString(beanData.getMenuid()) + "',");			bufSQL.append("Menuname = " + "'" + nullString(beanData.getMenuname()) + "',");			bufSQL.append("Menudes = " + "'" + nullString(beanData.getMenudes()) + "',");			bufSQL.append("Menuywclass = " + "'" + nullString(beanData.getMenuywclass()) + "',");			bufSQL.append("Menuparentid = " + "'" + nullString(beanData.getMenuparentid()) + "',");			bufSQL.append("Menuurl = " + "'" + nullString(beanData.getMenuurl()) + "',");			bufSQL.append("Menuicon1 = " + "'" + nullString(beanData.getMenuicon1()) + "',");			bufSQL.append("Menuicon2 = " + "'" + nullString(beanData.getMenuicon2()) + "',");			bufSQL.append("Menutype = " + "'" + nullString(beanData.getMenutype()) + "',");			bufSQL.append("Menuopenflag = " + "'" + nullString(beanData.getMenuopenflag()) + "',");			bufSQL.append("Menuviewflag = " + "'" + nullString(beanData.getMenuviewflag()) + "',");			bufSQL.append("Menuwfnode = " + "'" + nullString(beanData.getMenuwfnode()) + "',");			bufSQL.append("Menunnableflag = " + "'" + nullString(beanData.getMenunnableflag()) + "',");			bufSQL.append("Relationalmenuid = " + "'" + nullString(beanData.getRelationalmenuid()) + "',");			bufSQL.append("Sortno = " + beanData.getSortno().intValue() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Menuid = " + "'" + nullString(beanData.getMenuid()) + "'");
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
		S_MENUData beanData = (S_MENUData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_MENU SET menuid= ? , menuname= ? , menudes= ? , menuywclass= ? , menuparentid= ? , menuurl= ? , menuicon1= ? , menuicon2= ? , menutype= ? , menuopenflag= ? , menuviewflag= ? , menuwfnode= ? , menunnableflag= ? , relationalmenuid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  menuid  = ?");
			statement.setString( 1,beanData.getMenuid());			statement.setString( 2,beanData.getMenuname());			statement.setString( 3,beanData.getMenudes());			statement.setString( 4,beanData.getMenuywclass());			statement.setString( 5,beanData.getMenuparentid());			statement.setString( 6,beanData.getMenuurl());			statement.setString( 7,beanData.getMenuicon1());			statement.setString( 8,beanData.getMenuicon2());			statement.setString( 9,beanData.getMenutype());			statement.setString( 10,beanData.getMenuopenflag());			statement.setString( 11,beanData.getMenuviewflag());			statement.setString( 12,beanData.getMenuwfnode());			statement.setString( 13,beanData.getMenunnableflag());			statement.setString( 14,beanData.getRelationalmenuid());			if (beanData.getSortno()== null)			{				statement.setNull( 15, Types.INTEGER);			}			else			{				statement.setInt( 15, beanData.getSortno().intValue());			}			statement.setString( 16,beanData.getCreatetime());			statement.setString( 17,beanData.getCreateperson());			statement.setString( 18,beanData.getCreateunitid());			statement.setString( 19,beanData.getModifytime());			statement.setString( 20,beanData.getModifyperson());			statement.setString( 21,beanData.getDeleteflag());
			statement.setString( 22,beanData.getMenuid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_MENU SET menuid= ? , menuname= ? , menudes= ? , menuywclass= ? , menuparentid= ? , menuurl= ? , menuicon1= ? , menuicon2= ? , menutype= ? , menuopenflag= ? , menuviewflag= ? , menuwfnode= ? , menunnableflag= ? , relationalmenuid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  menuid  = ?"+ e.toString());
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
		S_MENUData beanData = (S_MENUData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  menuid  FROM S_MENU WHERE  menuid =?");
			statement.setString( 1,beanData.getMenuid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  menuid  FROM S_MENU WHERE  menuid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
