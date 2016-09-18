package com.ys.system.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.system.db.data.*;
import com.ys.util.basedao.BaseAbstractDao;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class YW_PROT_PICDao extends BaseAbstractDao
{
	public YW_PROT_PICData beanData=new YW_PROT_PICData();
	public YW_PROT_PICDao()
	{
	}
	public YW_PROT_PICDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROT_PICData)FindByPrimaryKey(beanData);
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
		YW_PROT_PICData beanData = (YW_PROT_PICData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROT_PIC( prot_pic_id,prot_id,pic_type,pic_name,save_dir,view_sts,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getProt_pic_id());			statement.setInt( 2,beanData.getProt_id());			statement.setString( 3,beanData.getPic_type());			statement.setString( 4,beanData.getPic_name());			statement.setString( 5,beanData.getSave_dir());			statement.setString( 6,beanData.getView_sts());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROT_PIC( prot_pic_id,prot_id,pic_type,pic_name,save_dir,view_sts,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROT_PIC( prot_pic_id,prot_id,pic_type,pic_name,save_dir,view_sts,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getProt_pic_id()) + "',");			bufSQL.append(beanData.getProt_id() + ",");			bufSQL.append("'" + nullString(beanData.getPic_type()) + "',");			bufSQL.append("'" + nullString(beanData.getPic_name()) + "',");			bufSQL.append("'" + nullString(beanData.getSave_dir()) + "',");			bufSQL.append("'" + nullString(beanData.getView_sts()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROT_PICData beanData = (YW_PROT_PICData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROT_PIC( prot_pic_id,prot_id,pic_type,pic_name,save_dir,view_sts,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getProt_pic_id());			statement.setInt( 2,beanData.getProt_id());			statement.setString( 3,beanData.getPic_type());			statement.setString( 4,beanData.getPic_name());			statement.setString( 5,beanData.getSave_dir());			statement.setString( 6,beanData.getView_sts());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROT_PIC( prot_pic_id,prot_id,pic_type,pic_name,save_dir,view_sts,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROT_PICData beanData = (YW_PROT_PICData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROT_PIC WHERE  prot_pic_id =?");
			statement.setString( 1,beanData.getProt_pic_id());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROT_PIC: "+ e.toString());
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
		YW_PROT_PICData beanData = (YW_PROT_PICData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROT_PIC WHERE ");
			bufSQL.append("Prot_pic_id = " + "'" + nullString(beanData.getProt_pic_id()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROT_PIC"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROT_PIC: "+ e.toString());
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
		YW_PROT_PICData beanData = (YW_PROT_PICData) beanDataTmp; 
		YW_PROT_PICData returnData=new YW_PROT_PICData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT prot_pic_id,prot_id,pic_type,pic_name,save_dir,view_sts,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROT_PIC WHERE  prot_pic_id =?");
			statement.setString( 1,beanData.getProt_pic_id());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setProt_pic_id( resultSet.getString( 1));			returnData.setProt_id( resultSet.getInt( 2));			returnData.setPic_type( resultSet.getString( 3));			returnData.setPic_name( resultSet.getString( 4));			returnData.setSave_dir( resultSet.getString( 5));			returnData.setView_sts( resultSet.getString( 6));			returnData.setCreatetime( resultSet.getString( 7));			returnData.setCreateperson( resultSet.getString( 8));			returnData.setCreateunitid( resultSet.getString( 9));			returnData.setModifytime( resultSet.getString( 10));			returnData.setModifyperson( resultSet.getString( 11));			returnData.setDeleteflag( resultSet.getString( 12));			returnData.setDeptguid( resultSet.getString( 13));			returnData.setFormid( resultSet.getString( 14));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT prot_pic_id,prot_id,pic_type,pic_name,save_dir,view_sts,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROT_PIC  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT prot_pic_id,prot_id,pic_type,pic_name,save_dir,view_sts,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROT_PIC"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROT_PICData returnData=new YW_PROT_PICData();
				returnData.setProt_pic_id( resultSet.getString( 1));				returnData.setProt_id( resultSet.getInt( 2));				returnData.setPic_type( resultSet.getString( 3));				returnData.setPic_name( resultSet.getString( 4));				returnData.setSave_dir( resultSet.getString( 5));				returnData.setView_sts( resultSet.getString( 6));				returnData.setCreatetime( resultSet.getString( 7));				returnData.setCreateperson( resultSet.getString( 8));				returnData.setCreateunitid( resultSet.getString( 9));				returnData.setModifytime( resultSet.getString( 10));				returnData.setModifyperson( resultSet.getString( 11));				returnData.setDeleteflag( resultSet.getString( 12));				returnData.setDeptguid( resultSet.getString( 13));				returnData.setFormid( resultSet.getString( 14));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT prot_pic_id,prot_id,pic_type,pic_name,save_dir,view_sts,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROT_PIC" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROT_PIC SET prot_pic_id= ? , prot_id= ? , pic_type= ? , pic_name= ? , save_dir= ? , view_sts= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  prot_pic_id  = ?");
			statement.setString( 1,beanData.getProt_pic_id());			statement.setInt( 2,beanData.getProt_id());			statement.setString( 3,beanData.getPic_type());			statement.setString( 4,beanData.getPic_name());			statement.setString( 5,beanData.getSave_dir());			statement.setString( 6,beanData.getView_sts());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			statement.setString( 15,beanData.getProt_pic_id());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROT_PIC SET prot_pic_id= ? , prot_id= ? , pic_type= ? , pic_name= ? , save_dir= ? , view_sts= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  prot_pic_id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROT_PIC SET ");
			bufSQL.append("Prot_pic_id = " + "'" + nullString(beanData.getProt_pic_id()) + "',");			bufSQL.append("Prot_id = " + beanData.getProt_id() + ",");			bufSQL.append("Pic_type = " + "'" + nullString(beanData.getPic_type()) + "',");			bufSQL.append("Pic_name = " + "'" + nullString(beanData.getPic_name()) + "',");			bufSQL.append("Save_dir = " + "'" + nullString(beanData.getSave_dir()) + "',");			bufSQL.append("View_sts = " + "'" + nullString(beanData.getView_sts()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Prot_pic_id = " + "'" + nullString(beanData.getProt_pic_id()) + "'");
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
		YW_PROT_PICData beanData = (YW_PROT_PICData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROT_PIC SET prot_pic_id= ? , prot_id= ? , pic_type= ? , pic_name= ? , save_dir= ? , view_sts= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  prot_pic_id  = ?");
			statement.setString( 1,beanData.getProt_pic_id());			statement.setInt( 2,beanData.getProt_id());			statement.setString( 3,beanData.getPic_type());			statement.setString( 4,beanData.getPic_name());			statement.setString( 5,beanData.getSave_dir());			statement.setString( 6,beanData.getView_sts());			statement.setString( 7,beanData.getCreatetime());			statement.setString( 8,beanData.getCreateperson());			statement.setString( 9,beanData.getCreateunitid());			statement.setString( 10,beanData.getModifytime());			statement.setString( 11,beanData.getModifyperson());			statement.setString( 12,beanData.getDeleteflag());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getFormid());
			statement.setString( 15,beanData.getProt_pic_id());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROT_PIC SET prot_pic_id= ? , prot_id= ? , pic_type= ? , pic_name= ? , save_dir= ? , view_sts= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  prot_pic_id  = ?"+ e.toString());
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
		YW_PROT_PICData beanData = (YW_PROT_PICData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  prot_pic_id  FROM YW_PROT_PIC WHERE  prot_pic_id =?");
			statement.setString( 1,beanData.getProt_pic_id());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  prot_pic_id  FROM YW_PROT_PIC WHERE  prot_pic_id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
