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
public class S_DICTYPEDao extends BaseAbstractDao
{
	public S_DICTYPEData beanData=new S_DICTYPEData();
	public S_DICTYPEDao()
	{
	}
	public S_DICTYPEDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_DICTYPEData)FindByPrimaryKey(beanData);
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
		S_DICTYPEData beanData = (S_DICTYPEData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_DICTYPE( dictypeid,dictypename,dictypelevel,dicselectedflag,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getDictypeid());			statement.setString( 2,beanData.getDictypename());			statement.setString( 3,beanData.getDictypelevel());			statement.setString( 4,beanData.getDicselectedflag());			if (beanData.getSortno()== null)			{				statement.setNull( 5, Types.INTEGER);			}			else			{				statement.setInt( 5, beanData.getSortno().intValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getEnableflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_DICTYPE( dictypeid,dictypename,dictypelevel,dicselectedflag,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_DICTYPE( dictypeid,dictypename,dictypelevel,dicselectedflag,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getDictypeid()) + "',");			bufSQL.append("'" + nullString(beanData.getDictypename()) + "',");			bufSQL.append("'" + nullString(beanData.getDictypelevel()) + "',");			bufSQL.append("'" + nullString(beanData.getDicselectedflag()) + "',");			bufSQL.append(beanData.getSortno().intValue() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getEnableflag()) + "'");
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
		S_DICTYPEData beanData = (S_DICTYPEData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_DICTYPE( dictypeid,dictypename,dictypelevel,dicselectedflag,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getDictypeid());			statement.setString( 2,beanData.getDictypename());			statement.setString( 3,beanData.getDictypelevel());			statement.setString( 4,beanData.getDicselectedflag());			if (beanData.getSortno()== null)			{				statement.setNull( 5, Types.INTEGER);			}			else			{				statement.setInt( 5, beanData.getSortno().intValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getEnableflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_DICTYPE( dictypeid,dictypename,dictypelevel,dicselectedflag,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_DICTYPEData beanData = (S_DICTYPEData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_DICTYPE WHERE  dictypeid =?");
			statement.setString( 1,beanData.getDictypeid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_DICTYPE: "+ e.toString());
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
		S_DICTYPEData beanData = (S_DICTYPEData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_DICTYPE WHERE ");
			bufSQL.append("Dictypeid = " + "'" + nullString(beanData.getDictypeid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM S_DICTYPE"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_DICTYPE: "+ e.toString());
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
		S_DICTYPEData beanData = (S_DICTYPEData) beanDataTmp; 
		S_DICTYPEData returnData=new S_DICTYPEData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT dictypeid,dictypename,dictypelevel,dicselectedflag,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag FROM S_DICTYPE WHERE  dictypeid =?");
			statement.setString( 1,beanData.getDictypeid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setDictypeid( resultSet.getString( 1));			returnData.setDictypename( resultSet.getString( 2));			returnData.setDictypelevel( resultSet.getString( 3));			returnData.setDicselectedflag( resultSet.getString( 4));			if (resultSet.getString( 5)==null)				returnData.setSortno(null);			else				returnData.setSortno( new Integer(resultSet.getInt( 5)));			returnData.setCreatetime( resultSet.getString( 6));			returnData.setCreateperson( resultSet.getString( 7));			returnData.setCreateunitid( resultSet.getString( 8));			returnData.setModifytime( resultSet.getString( 9));			returnData.setModifyperson( resultSet.getString( 10));			returnData.setDeleteflag( resultSet.getString( 11));			returnData.setEnableflag( resultSet.getString( 12));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT dictypeid,dictypename,dictypelevel,dicselectedflag,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag FROM S_DICTYPE  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT dictypeid,dictypename,dictypelevel,dicselectedflag,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag FROM S_DICTYPE"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_DICTYPEData returnData=new S_DICTYPEData();
				returnData.setDictypeid( resultSet.getString( 1));				returnData.setDictypename( resultSet.getString( 2));				returnData.setDictypelevel( resultSet.getString( 3));				returnData.setDicselectedflag( resultSet.getString( 4));				if (resultSet.getString( 5)==null)					returnData.setSortno(null);				else					returnData.setSortno( new Integer(resultSet.getInt( 5)));				returnData.setCreatetime( resultSet.getString( 6));				returnData.setCreateperson( resultSet.getString( 7));				returnData.setCreateunitid( resultSet.getString( 8));				returnData.setModifytime( resultSet.getString( 9));				returnData.setModifyperson( resultSet.getString( 10));				returnData.setDeleteflag( resultSet.getString( 11));				returnData.setEnableflag( resultSet.getString( 12));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT dictypeid,dictypename,dictypelevel,dicselectedflag,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag FROM S_DICTYPE" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_DICTYPE SET dictypeid= ? , dictypename= ? , dictypelevel= ? , dicselectedflag= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , enableflag=? WHERE  dictypeid  = ?");
			statement.setString( 1,beanData.getDictypeid());			statement.setString( 2,beanData.getDictypename());			statement.setString( 3,beanData.getDictypelevel());			statement.setString( 4,beanData.getDicselectedflag());			if (beanData.getSortno()== null)			{				statement.setNull( 5, Types.INTEGER);			}			else			{				statement.setInt( 5, beanData.getSortno().intValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getEnableflag());
			statement.setString( 13,beanData.getDictypeid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_DICTYPE SET dictypeid= ? , dictypename= ? , dictypelevel= ? , dicselectedflag= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , enableflag=? WHERE  dictypeid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_DICTYPE SET ");
			bufSQL.append("Dictypeid = " + "'" + nullString(beanData.getDictypeid()) + "',");			bufSQL.append("Dictypename = " + "'" + nullString(beanData.getDictypename()) + "',");			bufSQL.append("Dictypelevel = " + "'" + nullString(beanData.getDictypelevel()) + "',");			bufSQL.append("Dicselectedflag = " + "'" + nullString(beanData.getDicselectedflag()) + "',");			bufSQL.append("Sortno = " + beanData.getSortno().intValue() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Enableflag = " + "'" + nullString(beanData.getEnableflag()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Dictypeid = " + "'" + nullString(beanData.getDictypeid()) + "'");
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
		S_DICTYPEData beanData = (S_DICTYPEData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_DICTYPE SET dictypeid= ? , dictypename= ? , dictypelevel= ? , dicselectedflag= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , enableflag=? WHERE  dictypeid  = ?");
			statement.setString( 1,beanData.getDictypeid());			statement.setString( 2,beanData.getDictypename());			statement.setString( 3,beanData.getDictypelevel());			statement.setString( 4,beanData.getDicselectedflag());			if (beanData.getSortno()== null)			{				statement.setNull( 5, Types.INTEGER);			}			else			{				statement.setInt( 5, beanData.getSortno().intValue());			}			statement.setString( 6,beanData.getCreatetime());			statement.setString( 7,beanData.getCreateperson());			statement.setString( 8,beanData.getCreateunitid());			statement.setString( 9,beanData.getModifytime());			statement.setString( 10,beanData.getModifyperson());			statement.setString( 11,beanData.getDeleteflag());			statement.setString( 12,beanData.getEnableflag());
			statement.setString( 13,beanData.getDictypeid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_DICTYPE SET dictypeid= ? , dictypename= ? , dictypelevel= ? , dicselectedflag= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , enableflag=? WHERE  dictypeid  = ?"+ e.toString());
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
		S_DICTYPEData beanData = (S_DICTYPEData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  dictypeid  FROM S_DICTYPE WHERE  dictypeid =?");
			statement.setString( 1,beanData.getDictypeid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  dictypeid  FROM S_DICTYPE WHERE  dictypeid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
