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
public class YW_PROD_PACKDao extends BaseAbstractDao
{
	public YW_PROD_PACKData beanData=new YW_PROD_PACKData();
	public YW_PROD_PACKDao()
	{
	}
	public YW_PROD_PACKDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROD_PACKData)FindByPrimaryKey(beanData);
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
		YW_PROD_PACKData beanData = (YW_PROD_PACKData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROD_PACK( id,prod_id,label_mat,label_ai,label_path,pack_mat,pack_ai,pack_path,instr_mat,instr_ai,instr_path,cart_mat,cart_ai,cart_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProd_id());			statement.setString( 3,beanData.getLabel_mat());			statement.setString( 4,beanData.getLabel_ai());			statement.setString( 5,beanData.getLabel_path());			statement.setString( 6,beanData.getPack_mat());			statement.setString( 7,beanData.getPack_ai());			statement.setString( 8,beanData.getPack_path());			statement.setString( 9,beanData.getInstr_mat());			statement.setString( 10,beanData.getInstr_ai());			statement.setString( 11,beanData.getInstr_path());			statement.setString( 12,beanData.getCart_mat());			statement.setString( 13,beanData.getCart_ai());			statement.setString( 14,beanData.getCart_path());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getDeptguid());			statement.setString( 22,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROD_PACK( id,prod_id,label_mat,label_ai,label_path,pack_mat,pack_ai,pack_path,instr_mat,instr_ai,instr_path,cart_mat,cart_ai,cart_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROD_PACK( id,prod_id,label_mat,label_ai,label_path,pack_mat,pack_ai,pack_path,instr_mat,instr_ai,instr_path,cart_mat,cart_ai,cart_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append(beanData.getProd_id() + ",");			bufSQL.append("'" + nullString(beanData.getLabel_mat()) + "',");			bufSQL.append("'" + nullString(beanData.getLabel_ai()) + "',");			bufSQL.append("'" + nullString(beanData.getLabel_path()) + "',");			bufSQL.append("'" + nullString(beanData.getPack_mat()) + "',");			bufSQL.append("'" + nullString(beanData.getPack_ai()) + "',");			bufSQL.append("'" + nullString(beanData.getPack_path()) + "',");			bufSQL.append("'" + nullString(beanData.getInstr_mat()) + "',");			bufSQL.append("'" + nullString(beanData.getInstr_ai()) + "',");			bufSQL.append("'" + nullString(beanData.getInstr_path()) + "',");			bufSQL.append("'" + nullString(beanData.getCart_mat()) + "',");			bufSQL.append("'" + nullString(beanData.getCart_ai()) + "',");			bufSQL.append("'" + nullString(beanData.getCart_path()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROD_PACKData beanData = (YW_PROD_PACKData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROD_PACK( id,prod_id,label_mat,label_ai,label_path,pack_mat,pack_ai,pack_path,instr_mat,instr_ai,instr_path,cart_mat,cart_ai,cart_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProd_id());			statement.setString( 3,beanData.getLabel_mat());			statement.setString( 4,beanData.getLabel_ai());			statement.setString( 5,beanData.getLabel_path());			statement.setString( 6,beanData.getPack_mat());			statement.setString( 7,beanData.getPack_ai());			statement.setString( 8,beanData.getPack_path());			statement.setString( 9,beanData.getInstr_mat());			statement.setString( 10,beanData.getInstr_ai());			statement.setString( 11,beanData.getInstr_path());			statement.setString( 12,beanData.getCart_mat());			statement.setString( 13,beanData.getCart_ai());			statement.setString( 14,beanData.getCart_path());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getDeptguid());			statement.setString( 22,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROD_PACK( id,prod_id,label_mat,label_ai,label_path,pack_mat,pack_ai,pack_path,instr_mat,instr_ai,instr_path,cart_mat,cart_ai,cart_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROD_PACKData beanData = (YW_PROD_PACKData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROD_PACK WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROD_PACK: "+ e.toString());
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
		YW_PROD_PACKData beanData = (YW_PROD_PACKData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROD_PACK WHERE ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROD_PACK"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROD_PACK: "+ e.toString());
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
		YW_PROD_PACKData beanData = (YW_PROD_PACKData) beanDataTmp; 
		YW_PROD_PACKData returnData=new YW_PROD_PACKData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,prod_id,label_mat,label_ai,label_path,pack_mat,pack_ai,pack_path,instr_mat,instr_ai,instr_path,cart_mat,cart_ai,cart_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROD_PACK WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProd_id( resultSet.getInt( 2));			returnData.setLabel_mat( resultSet.getString( 3));			returnData.setLabel_ai( resultSet.getString( 4));			returnData.setLabel_path( resultSet.getString( 5));			returnData.setPack_mat( resultSet.getString( 6));			returnData.setPack_ai( resultSet.getString( 7));			returnData.setPack_path( resultSet.getString( 8));			returnData.setInstr_mat( resultSet.getString( 9));			returnData.setInstr_ai( resultSet.getString( 10));			returnData.setInstr_path( resultSet.getString( 11));			returnData.setCart_mat( resultSet.getString( 12));			returnData.setCart_ai( resultSet.getString( 13));			returnData.setCart_path( resultSet.getString( 14));			returnData.setCreatetime( resultSet.getString( 15));			returnData.setCreateperson( resultSet.getString( 16));			returnData.setCreateunitid( resultSet.getString( 17));			returnData.setModifytime( resultSet.getString( 18));			returnData.setModifyperson( resultSet.getString( 19));			returnData.setDeleteflag( resultSet.getString( 20));			returnData.setDeptguid( resultSet.getString( 21));			returnData.setFormid( resultSet.getString( 22));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,prod_id,label_mat,label_ai,label_path,pack_mat,pack_ai,pack_path,instr_mat,instr_ai,instr_path,cart_mat,cart_ai,cart_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROD_PACK  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,prod_id,label_mat,label_ai,label_path,pack_mat,pack_ai,pack_path,instr_mat,instr_ai,instr_path,cart_mat,cart_ai,cart_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROD_PACK"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROD_PACKData returnData=new YW_PROD_PACKData();
				returnData.setId( resultSet.getString( 1));				returnData.setProd_id( resultSet.getInt( 2));				returnData.setLabel_mat( resultSet.getString( 3));				returnData.setLabel_ai( resultSet.getString( 4));				returnData.setLabel_path( resultSet.getString( 5));				returnData.setPack_mat( resultSet.getString( 6));				returnData.setPack_ai( resultSet.getString( 7));				returnData.setPack_path( resultSet.getString( 8));				returnData.setInstr_mat( resultSet.getString( 9));				returnData.setInstr_ai( resultSet.getString( 10));				returnData.setInstr_path( resultSet.getString( 11));				returnData.setCart_mat( resultSet.getString( 12));				returnData.setCart_ai( resultSet.getString( 13));				returnData.setCart_path( resultSet.getString( 14));				returnData.setCreatetime( resultSet.getString( 15));				returnData.setCreateperson( resultSet.getString( 16));				returnData.setCreateunitid( resultSet.getString( 17));				returnData.setModifytime( resultSet.getString( 18));				returnData.setModifyperson( resultSet.getString( 19));				returnData.setDeleteflag( resultSet.getString( 20));				returnData.setDeptguid( resultSet.getString( 21));				returnData.setFormid( resultSet.getString( 22));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,prod_id,label_mat,label_ai,label_path,pack_mat,pack_ai,pack_path,instr_mat,instr_ai,instr_path,cart_mat,cart_ai,cart_path,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROD_PACK" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROD_PACK SET id= ? , prod_id= ? , label_mat= ? , label_ai= ? , label_path= ? , pack_mat= ? , pack_ai= ? , pack_path= ? , instr_mat= ? , instr_ai= ? , instr_path= ? , cart_mat= ? , cart_ai= ? , cart_path= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProd_id());			statement.setString( 3,beanData.getLabel_mat());			statement.setString( 4,beanData.getLabel_ai());			statement.setString( 5,beanData.getLabel_path());			statement.setString( 6,beanData.getPack_mat());			statement.setString( 7,beanData.getPack_ai());			statement.setString( 8,beanData.getPack_path());			statement.setString( 9,beanData.getInstr_mat());			statement.setString( 10,beanData.getInstr_ai());			statement.setString( 11,beanData.getInstr_path());			statement.setString( 12,beanData.getCart_mat());			statement.setString( 13,beanData.getCart_ai());			statement.setString( 14,beanData.getCart_path());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getDeptguid());			statement.setString( 22,beanData.getFormid());
			statement.setString( 23,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROD_PACK SET id= ? , prod_id= ? , label_mat= ? , label_ai= ? , label_path= ? , pack_mat= ? , pack_ai= ? , pack_path= ? , instr_mat= ? , instr_ai= ? , instr_path= ? , cart_mat= ? , cart_ai= ? , cart_path= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROD_PACK SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Prod_id = " + beanData.getProd_id() + ",");			bufSQL.append("Label_mat = " + "'" + nullString(beanData.getLabel_mat()) + "',");			bufSQL.append("Label_ai = " + "'" + nullString(beanData.getLabel_ai()) + "',");			bufSQL.append("Label_path = " + "'" + nullString(beanData.getLabel_path()) + "',");			bufSQL.append("Pack_mat = " + "'" + nullString(beanData.getPack_mat()) + "',");			bufSQL.append("Pack_ai = " + "'" + nullString(beanData.getPack_ai()) + "',");			bufSQL.append("Pack_path = " + "'" + nullString(beanData.getPack_path()) + "',");			bufSQL.append("Instr_mat = " + "'" + nullString(beanData.getInstr_mat()) + "',");			bufSQL.append("Instr_ai = " + "'" + nullString(beanData.getInstr_ai()) + "',");			bufSQL.append("Instr_path = " + "'" + nullString(beanData.getInstr_path()) + "',");			bufSQL.append("Cart_mat = " + "'" + nullString(beanData.getCart_mat()) + "',");			bufSQL.append("Cart_ai = " + "'" + nullString(beanData.getCart_ai()) + "',");			bufSQL.append("Cart_path = " + "'" + nullString(beanData.getCart_path()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "'");
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
		YW_PROD_PACKData beanData = (YW_PROD_PACKData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROD_PACK SET id= ? , prod_id= ? , label_mat= ? , label_ai= ? , label_path= ? , pack_mat= ? , pack_ai= ? , pack_path= ? , instr_mat= ? , instr_ai= ? , instr_path= ? , cart_mat= ? , cart_ai= ? , cart_path= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProd_id());			statement.setString( 3,beanData.getLabel_mat());			statement.setString( 4,beanData.getLabel_ai());			statement.setString( 5,beanData.getLabel_path());			statement.setString( 6,beanData.getPack_mat());			statement.setString( 7,beanData.getPack_ai());			statement.setString( 8,beanData.getPack_path());			statement.setString( 9,beanData.getInstr_mat());			statement.setString( 10,beanData.getInstr_ai());			statement.setString( 11,beanData.getInstr_path());			statement.setString( 12,beanData.getCart_mat());			statement.setString( 13,beanData.getCart_ai());			statement.setString( 14,beanData.getCart_path());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getDeptguid());			statement.setString( 22,beanData.getFormid());
			statement.setString( 23,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROD_PACK SET id= ? , prod_id= ? , label_mat= ? , label_ai= ? , label_path= ? , pack_mat= ? , pack_ai= ? , pack_path= ? , instr_mat= ? , instr_ai= ? , instr_path= ? , cart_mat= ? , cart_ai= ? , cart_path= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PROD_PACKData beanData = (YW_PROD_PACKData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PROD_PACK WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PROD_PACK WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
