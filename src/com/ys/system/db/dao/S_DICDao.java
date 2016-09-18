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
public class S_DICDao extends BaseAbstractDao
{
	public S_DICData beanData=new S_DICData();
	public S_DICDao()
	{
	}
	public S_DICDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_DICData)FindByPrimaryKey(beanData);
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
		S_DICData beanData = (S_DICData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_DIC( dicid,dicname,dicdes,dicprarentid,dictypeid,isleaf,jianpin,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getDicid());			statement.setString( 2,beanData.getDicname());			statement.setString( 3,beanData.getDicdes());			statement.setString( 4,beanData.getDicprarentid());			statement.setString( 5,beanData.getDictypeid());			statement.setString( 6,beanData.getIsleaf());			statement.setString( 7,beanData.getJianpin());			if (beanData.getSortno()== null)			{				statement.setNull( 8, Types.INTEGER);			}			else			{				statement.setInt( 8, beanData.getSortno().intValue());			}			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getEnableflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_DIC( dicid,dicname,dicdes,dicprarentid,dictypeid,isleaf,jianpin,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_DIC( dicid,dicname,dicdes,dicprarentid,dictypeid,isleaf,jianpin,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getDicid()) + "',");			bufSQL.append("'" + nullString(beanData.getDicname()) + "',");			bufSQL.append("'" + nullString(beanData.getDicdes()) + "',");			bufSQL.append("'" + nullString(beanData.getDicprarentid()) + "',");			bufSQL.append("'" + nullString(beanData.getDictypeid()) + "',");			bufSQL.append("'" + nullString(beanData.getIsleaf()) + "',");			bufSQL.append("'" + nullString(beanData.getJianpin()) + "',");			bufSQL.append(beanData.getSortno().intValue() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getEnableflag()) + "'");
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
		S_DICData beanData = (S_DICData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_DIC( dicid,dicname,dicdes,dicprarentid,dictypeid,isleaf,jianpin,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getDicid());			statement.setString( 2,beanData.getDicname());			statement.setString( 3,beanData.getDicdes());			statement.setString( 4,beanData.getDicprarentid());			statement.setString( 5,beanData.getDictypeid());			statement.setString( 6,beanData.getIsleaf());			statement.setString( 7,beanData.getJianpin());			if (beanData.getSortno()== null)			{				statement.setNull( 8, Types.INTEGER);			}			else			{				statement.setInt( 8, beanData.getSortno().intValue());			}			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getEnableflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_DIC( dicid,dicname,dicdes,dicprarentid,dictypeid,isleaf,jianpin,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_DICData beanData = (S_DICData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_DIC WHERE  dicid=? AND dictypeid =?");
			statement.setString( 1,beanData.getDicid());
			statement.setString( 2,beanData.getDictypeid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_DIC: "+ e.toString());
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
		S_DICData beanData = (S_DICData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_DIC WHERE ");
			bufSQL.append("Dicid = " + "'" + nullString(beanData.getDicid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM S_DIC"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_DIC: "+ e.toString());
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
		S_DICData beanData = (S_DICData) beanDataTmp; 
		S_DICData returnData=new S_DICData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT dicid,dicname,dicdes,dicprarentid,dictypeid,isleaf,jianpin,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag FROM S_DIC WHERE  dicid=? AND dictypeid =?");
			statement.setString( 1,beanData.getDicid());
			statement.setString( 2,beanData.getDictypeid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setDicid( resultSet.getString( 1));			returnData.setDicname( resultSet.getString( 2));			returnData.setDicdes( resultSet.getString( 3));			returnData.setDicprarentid( resultSet.getString( 4));			returnData.setDictypeid( resultSet.getString( 5));			returnData.setIsleaf( resultSet.getString( 6));			returnData.setJianpin( resultSet.getString( 7));			if (resultSet.getString( 8)==null)				returnData.setSortno(null);			else				returnData.setSortno( new Integer(resultSet.getInt( 8)));			returnData.setCreatetime( resultSet.getString( 9));			returnData.setCreateperson( resultSet.getString( 10));			returnData.setCreateunitid( resultSet.getString( 11));			returnData.setModifytime( resultSet.getString( 12));			returnData.setModifyperson( resultSet.getString( 13));			returnData.setDeleteflag( resultSet.getString( 14));			returnData.setEnableflag( resultSet.getString( 15));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT dicid,dicname,dicdes,dicprarentid,dictypeid,isleaf,jianpin,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag FROM S_DIC  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT dicid,dicname,dicdes,dicprarentid,dictypeid,isleaf,jianpin,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag FROM S_DIC"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_DICData returnData=new S_DICData();
				returnData.setDicid( resultSet.getString( 1));				returnData.setDicname( resultSet.getString( 2));				returnData.setDicdes( resultSet.getString( 3));				returnData.setDicprarentid( resultSet.getString( 4));				returnData.setDictypeid( resultSet.getString( 5));				returnData.setIsleaf( resultSet.getString( 6));				returnData.setJianpin( resultSet.getString( 7));				if (resultSet.getString( 8)==null)					returnData.setSortno(null);				else					returnData.setSortno( new Integer(resultSet.getInt( 8)));				returnData.setCreatetime( resultSet.getString( 9));				returnData.setCreateperson( resultSet.getString( 10));				returnData.setCreateunitid( resultSet.getString( 11));				returnData.setModifytime( resultSet.getString( 12));				returnData.setModifyperson( resultSet.getString( 13));				returnData.setDeleteflag( resultSet.getString( 14));				returnData.setEnableflag( resultSet.getString( 15));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT dicid,dicname,dicdes,dicprarentid,dictypeid,isleaf,jianpin,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,enableflag FROM S_DIC" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_DIC SET dicid= ? , dicname= ? , dicdes= ? , dicprarentid= ? , dictypeid= ? , isleaf= ? , jianpin= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , enableflag=? WHERE  dicid = ? AND dictypeid  = ?");
			statement.setString( 1,beanData.getDicid());			statement.setString( 2,beanData.getDicname());			statement.setString( 3,beanData.getDicdes());			statement.setString( 4,beanData.getDicprarentid());			statement.setString( 5,beanData.getDictypeid());			statement.setString( 6,beanData.getIsleaf());			statement.setString( 7,beanData.getJianpin());			if (beanData.getSortno()== null)			{				statement.setNull( 8, Types.INTEGER);			}			else			{				statement.setInt( 8, beanData.getSortno().intValue());			}			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getEnableflag());
			statement.setString( 16,beanData.getDicid());
			statement.setString( 17,beanData.getDictypeid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_DIC SET dicid= ? , dicname= ? , dicdes= ? , dicprarentid= ? , dictypeid= ? , isleaf= ? , jianpin= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , enableflag=? WHERE  dicid = ? AND dictypeid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_DIC SET ");
			bufSQL.append("Dicid = " + "'" + nullString(beanData.getDicid()) + "',");			bufSQL.append("Dicname = " + "'" + nullString(beanData.getDicname()) + "',");			bufSQL.append("Dicdes = " + "'" + nullString(beanData.getDicdes()) + "',");			bufSQL.append("Dicprarentid = " + "'" + nullString(beanData.getDicprarentid()) + "',");			bufSQL.append("Dictypeid = " + "'" + nullString(beanData.getDictypeid()) + "',");			bufSQL.append("Isleaf = " + "'" + nullString(beanData.getIsleaf()) + "',");			bufSQL.append("Jianpin = " + "'" + nullString(beanData.getJianpin()) + "',");			bufSQL.append("Sortno = " + beanData.getSortno().intValue() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Enableflag = " + "'" + nullString(beanData.getEnableflag()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Dicid = " + "'" + nullString(beanData.getDicid()) + "'");
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
		S_DICData beanData = (S_DICData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_DIC SET dicid= ? , dicname= ? , dicdes= ? , dicprarentid= ? , dictypeid= ? , isleaf= ? , jianpin= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , enableflag=? WHERE  dicid = ? AND dictypeid  = ?");
			statement.setString( 1,beanData.getDicid());			statement.setString( 2,beanData.getDicname());			statement.setString( 3,beanData.getDicdes());			statement.setString( 4,beanData.getDicprarentid());			statement.setString( 5,beanData.getDictypeid());			statement.setString( 6,beanData.getIsleaf());			statement.setString( 7,beanData.getJianpin());			if (beanData.getSortno()== null)			{				statement.setNull( 8, Types.INTEGER);			}			else			{				statement.setInt( 8, beanData.getSortno().intValue());			}			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getEnableflag());
			statement.setString( 16,beanData.getDicid());
			statement.setString( 17,beanData.getDictypeid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_DIC SET dicid= ? , dicname= ? , dicdes= ? , dicprarentid= ? , dictypeid= ? , isleaf= ? , jianpin= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , enableflag=? WHERE  dicid = ? AND dictypeid  = ?"+ e.toString());
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
		S_DICData beanData = (S_DICData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  dicid,dictypeid  FROM S_DIC WHERE  dicid=? AND dictypeid =?");
			statement.setString( 1,beanData.getDicid());
			statement.setString( 2,beanData.getDictypeid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  dicid,dictypeid  FROM S_DIC WHERE  dicid=? AND dictypeid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
