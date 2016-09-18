package com.ys.system.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ys.system.db.data.YW_MOLD_INFOData;
import com.ys.util.basedao.BaseAbstractDao;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class YW_MOLD_INFODao extends BaseAbstractDao
{
	public YW_MOLD_INFOData beanData=new YW_MOLD_INFOData();
	public YW_MOLD_INFODao()
	{
	}
	public YW_MOLD_INFODao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_MOLD_INFOData)FindByPrimaryKey(beanData);
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
		YW_MOLD_INFOData beanData = (YW_MOLD_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_MOLD_INFO( mold_id,prod_code,prod_name,supplier,contract,cont_sta_date,cont_end_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getMold_id());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getProd_name());			statement.setString( 4,beanData.getSupplier());			statement.setString( 5,beanData.getContract());			statement.setString( 6,beanData.getCont_sta_date());			statement.setString( 7,beanData.getCont_end_date());			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_MOLD_INFO( mold_id,prod_code,prod_name,supplier,contract,cont_sta_date,cont_end_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_MOLD_INFO( mold_id,prod_code,prod_name,supplier,contract,cont_sta_date,cont_end_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getMold_id()) + "',");			bufSQL.append("'" + nullString(beanData.getProd_code()) + "',");			bufSQL.append("'" + nullString(beanData.getProd_name()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplier()) + "',");			bufSQL.append("'" + nullString(beanData.getContract()) + "',");			bufSQL.append("'" + nullString(beanData.getCont_sta_date()) + "',");			bufSQL.append("'" + nullString(beanData.getCont_end_date()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_MOLD_INFOData beanData = (YW_MOLD_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_MOLD_INFO( mold_id,prod_code,prod_name,supplier,contract,cont_sta_date,cont_end_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getMold_id());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getProd_name());			statement.setString( 4,beanData.getSupplier());			statement.setString( 5,beanData.getContract());			statement.setString( 6,beanData.getCont_sta_date());			statement.setString( 7,beanData.getCont_end_date());			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_MOLD_INFO( mold_id,prod_code,prod_name,supplier,contract,cont_sta_date,cont_end_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_MOLD_INFOData beanData = (YW_MOLD_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_MOLD_INFO WHERE  mold_id =?");
			statement.setString( 1,beanData.getMold_id());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_MOLD_INFO: "+ e.toString());
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
		YW_MOLD_INFOData beanData = (YW_MOLD_INFOData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_MOLD_INFO WHERE ");
			bufSQL.append("Mold_id = " + "'" + nullString(beanData.getMold_id()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM YW_MOLD_INFO"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_MOLD_INFO: "+ e.toString());
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
		YW_MOLD_INFOData beanData = (YW_MOLD_INFOData) beanDataTmp; 
		YW_MOLD_INFOData returnData=new YW_MOLD_INFOData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT mold_id,prod_code,prod_name,supplier,contract,cont_sta_date,cont_end_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_MOLD_INFO WHERE  mold_id =?");
			statement.setString( 1,beanData.getMold_id());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setMold_id( resultSet.getString( 1));			returnData.setProd_code( resultSet.getString( 2));			returnData.setProd_name( resultSet.getString( 3));			returnData.setSupplier( resultSet.getString( 4));			returnData.setContract( resultSet.getString( 5));			returnData.setCont_sta_date( resultSet.getString( 6));			returnData.setCont_end_date( resultSet.getString( 7));			returnData.setCreatetime( resultSet.getString( 8));			returnData.setCreateperson( resultSet.getString( 9));			returnData.setCreateunitid( resultSet.getString( 10));			returnData.setModifytime( resultSet.getString( 11));			returnData.setModifyperson( resultSet.getString( 12));			returnData.setDeleteflag( resultSet.getString( 13));			returnData.setDeptguid( resultSet.getString( 14));			returnData.setFormid( resultSet.getString( 15));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT mold_id,prod_code,prod_name,supplier,contract,cont_sta_date,cont_end_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_MOLD_INFO  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT mold_id,prod_code,prod_name,supplier,contract,cont_sta_date,cont_end_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_MOLD_INFO"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_MOLD_INFOData returnData=new YW_MOLD_INFOData();
				returnData.setMold_id( resultSet.getString( 1));				returnData.setProd_code( resultSet.getString( 2));				returnData.setProd_name( resultSet.getString( 3));				returnData.setSupplier( resultSet.getString( 4));				returnData.setContract( resultSet.getString( 5));				returnData.setCont_sta_date( resultSet.getString( 6));				returnData.setCont_end_date( resultSet.getString( 7));				returnData.setCreatetime( resultSet.getString( 8));				returnData.setCreateperson( resultSet.getString( 9));				returnData.setCreateunitid( resultSet.getString( 10));				returnData.setModifytime( resultSet.getString( 11));				returnData.setModifyperson( resultSet.getString( 12));				returnData.setDeleteflag( resultSet.getString( 13));				returnData.setDeptguid( resultSet.getString( 14));				returnData.setFormid( resultSet.getString( 15));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT mold_id,prod_code,prod_name,supplier,contract,cont_sta_date,cont_end_date,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_MOLD_INFO" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_MOLD_INFO SET mold_id= ? , prod_code= ? , prod_name= ? , supplier= ? , contract= ? , cont_sta_date= ? , cont_end_date= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  mold_id  = ?");
			statement.setString( 1,beanData.getMold_id());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getProd_name());			statement.setString( 4,beanData.getSupplier());			statement.setString( 5,beanData.getContract());			statement.setString( 6,beanData.getCont_sta_date());			statement.setString( 7,beanData.getCont_end_date());			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			statement.setString( 16,beanData.getMold_id());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_MOLD_INFO SET mold_id= ? , prod_code= ? , prod_name= ? , supplier= ? , contract= ? , cont_sta_date= ? , cont_end_date= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  mold_id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_MOLD_INFO SET ");
			bufSQL.append("Mold_id = " + "'" + nullString(beanData.getMold_id()) + "',");			bufSQL.append("Prod_code = " + "'" + nullString(beanData.getProd_code()) + "',");			bufSQL.append("Prod_name = " + "'" + nullString(beanData.getProd_name()) + "',");			bufSQL.append("Supplier = " + "'" + nullString(beanData.getSupplier()) + "',");			bufSQL.append("Contract = " + "'" + nullString(beanData.getContract()) + "',");			bufSQL.append("Cont_sta_date = " + "'" + nullString(beanData.getCont_sta_date()) + "',");			bufSQL.append("Cont_end_date = " + "'" + nullString(beanData.getCont_end_date()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Mold_id = " + "'" + nullString(beanData.getMold_id()) + "'");
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
		YW_MOLD_INFOData beanData = (YW_MOLD_INFOData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_MOLD_INFO SET mold_id= ? , prod_code= ? , prod_name= ? , supplier= ? , contract= ? , cont_sta_date= ? , cont_end_date= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  mold_id  = ?");
			statement.setString( 1,beanData.getMold_id());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getProd_name());			statement.setString( 4,beanData.getSupplier());			statement.setString( 5,beanData.getContract());			statement.setString( 6,beanData.getCont_sta_date());			statement.setString( 7,beanData.getCont_end_date());			statement.setString( 8,beanData.getCreatetime());			statement.setString( 9,beanData.getCreateperson());			statement.setString( 10,beanData.getCreateunitid());			statement.setString( 11,beanData.getModifytime());			statement.setString( 12,beanData.getModifyperson());			statement.setString( 13,beanData.getDeleteflag());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getFormid());
			statement.setString( 16,beanData.getMold_id());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_MOLD_INFO SET mold_id= ? , prod_code= ? , prod_name= ? , supplier= ? , contract= ? , cont_sta_date= ? , cont_end_date= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  mold_id  = ?"+ e.toString());
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
		YW_MOLD_INFOData beanData = (YW_MOLD_INFOData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  mold_id  FROM YW_MOLD_INFO WHERE  mold_id =?");
			statement.setString( 1,beanData.getMold_id());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  mold_id  FROM YW_MOLD_INFO WHERE  mold_id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
