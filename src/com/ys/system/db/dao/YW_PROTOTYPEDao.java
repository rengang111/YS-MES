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
public class YW_PROTOTYPEDao extends BaseAbstractDao
{
	public YW_PROTOTYPEData beanData=new YW_PROTOTYPEData();
	public YW_PROTOTYPEDao()
	{
	}
	public YW_PROTOTYPEDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROTOTYPEData)FindByPrimaryKey(beanData);
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
		YW_PROTOTYPEData beanData = (YW_PROTOTYPEData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROTOTYPE( id,prot_code,prot_name,manufacturer,buy_date,buy_price,buy_location,prot_desc_ch,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProt_code());			statement.setString( 3,beanData.getProt_name());			statement.setString( 4,beanData.getManufacturer());			statement.setString( 5,beanData.getBuy_date());			if (beanData.getBuy_price()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getBuy_price().floatValue());			}			statement.setString( 7,beanData.getBuy_location());			statement.setString( 8,beanData.getProt_desc_ch());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getDeptguid());			statement.setString( 16,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROTOTYPE( id,prot_code,prot_name,manufacturer,buy_date,buy_price,buy_location,prot_desc_ch,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROTOTYPE( id,prot_code,prot_name,manufacturer,buy_date,buy_price,buy_location,prot_desc_ch,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getProt_code()) + "',");			bufSQL.append("'" + nullString(beanData.getProt_name()) + "',");			bufSQL.append("'" + nullString(beanData.getManufacturer()) + "',");			bufSQL.append("'" + nullString(beanData.getBuy_date()) + "',");			bufSQL.append(beanData.getBuy_price() + ",");			bufSQL.append("'" + nullString(beanData.getBuy_location()) + "',");			bufSQL.append("'" + nullString(beanData.getProt_desc_ch()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROTOTYPEData beanData = (YW_PROTOTYPEData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROTOTYPE( id,prot_code,prot_name,manufacturer,buy_date,buy_price,buy_location,prot_desc_ch,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProt_code());			statement.setString( 3,beanData.getProt_name());			statement.setString( 4,beanData.getManufacturer());			statement.setString( 5,beanData.getBuy_date());			if (beanData.getBuy_price()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getBuy_price().floatValue());			}			statement.setString( 7,beanData.getBuy_location());			statement.setString( 8,beanData.getProt_desc_ch());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getDeptguid());			statement.setString( 16,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROTOTYPE( id,prot_code,prot_name,manufacturer,buy_date,buy_price,buy_location,prot_desc_ch,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROTOTYPEData beanData = (YW_PROTOTYPEData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROTOTYPE WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROTOTYPE: "+ e.toString());
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
		YW_PROTOTYPEData beanData = (YW_PROTOTYPEData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROTOTYPE WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROTOTYPE"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROTOTYPE: "+ e.toString());
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
		YW_PROTOTYPEData beanData = (YW_PROTOTYPEData) beanDataTmp; 
		YW_PROTOTYPEData returnData=new YW_PROTOTYPEData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,prot_code,prot_name,manufacturer,buy_date,buy_price,buy_location,prot_desc_ch,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROTOTYPE WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProt_code( resultSet.getString( 2));			returnData.setProt_name( resultSet.getString( 3));			returnData.setManufacturer( resultSet.getString( 4));			returnData.setBuy_date( resultSet.getString( 5));			if (resultSet.getString( 6)==null)				returnData.setBuy_price(null);			else				returnData.setBuy_price( new Float(resultSet.getFloat( 6)));			returnData.setBuy_location( resultSet.getString( 7));			returnData.setProt_desc_ch( resultSet.getString( 8));			returnData.setCreatetime( resultSet.getString( 9));			returnData.setCreateperson( resultSet.getString( 10));			returnData.setCreateunitid( resultSet.getString( 11));			returnData.setModifytime( resultSet.getString( 12));			returnData.setModifyperson( resultSet.getString( 13));			returnData.setDeleteflag( resultSet.getString( 14));			returnData.setDeptguid( resultSet.getString( 15));			returnData.setFormid( resultSet.getString( 16));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,prot_code,prot_name,manufacturer,buy_date,buy_price,buy_location,prot_desc_ch,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROTOTYPE  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,prot_code,prot_name,manufacturer,buy_date,buy_price,buy_location,prot_desc_ch,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROTOTYPE"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROTOTYPEData returnData=new YW_PROTOTYPEData();
				returnData.setId( resultSet.getString( 1));				returnData.setProt_code( resultSet.getString( 2));				returnData.setProt_name( resultSet.getString( 3));				returnData.setManufacturer( resultSet.getString( 4));				returnData.setBuy_date( resultSet.getString( 5));				if (resultSet.getString( 6)==null)					returnData.setBuy_price(null);				else					returnData.setBuy_price( new Float(resultSet.getFloat( 6)));				returnData.setBuy_location( resultSet.getString( 7));				returnData.setProt_desc_ch( resultSet.getString( 8));				returnData.setCreatetime( resultSet.getString( 9));				returnData.setCreateperson( resultSet.getString( 10));				returnData.setCreateunitid( resultSet.getString( 11));				returnData.setModifytime( resultSet.getString( 12));				returnData.setModifyperson( resultSet.getString( 13));				returnData.setDeleteflag( resultSet.getString( 14));				returnData.setDeptguid( resultSet.getString( 15));				returnData.setFormid( resultSet.getString( 16));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,prot_code,prot_name,manufacturer,buy_date,buy_price,buy_location,prot_desc_ch,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROTOTYPE" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROTOTYPE SET id= ? , prot_code= ? , prot_name= ? , manufacturer= ? , buy_date= ? , buy_price= ? , buy_location= ? , prot_desc_ch= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProt_code());			statement.setString( 3,beanData.getProt_name());			statement.setString( 4,beanData.getManufacturer());			statement.setString( 5,beanData.getBuy_date());			if (beanData.getBuy_price()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getBuy_price().floatValue());			}			statement.setString( 7,beanData.getBuy_location());			statement.setString( 8,beanData.getProt_desc_ch());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getDeptguid());			statement.setString( 16,beanData.getFormid());
			statement.setString( 17,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROTOTYPE SET id= ? , prot_code= ? , prot_name= ? , manufacturer= ? , buy_date= ? , buy_price= ? , buy_location= ? , prot_desc_ch= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROTOTYPE SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Prot_code = " + "'" + nullString(beanData.getProt_code()) + "',");			bufSQL.append("Prot_name = " + "'" + nullString(beanData.getProt_name()) + "',");			bufSQL.append("Manufacturer = " + "'" + nullString(beanData.getManufacturer()) + "',");			bufSQL.append("Buy_date = " + "'" + nullString(beanData.getBuy_date()) + "',");			bufSQL.append("Buy_price = " + beanData.getBuy_price() + ",");			bufSQL.append("Buy_location = " + "'" + nullString(beanData.getBuy_location()) + "',");			bufSQL.append("Prot_desc_ch = " + "'" + nullString(beanData.getProt_desc_ch()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROTOTYPEData beanData = (YW_PROTOTYPEData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROTOTYPE SET id= ? , prot_code= ? , prot_name= ? , manufacturer= ? , buy_date= ? , buy_price= ? , buy_location= ? , prot_desc_ch= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProt_code());			statement.setString( 3,beanData.getProt_name());			statement.setString( 4,beanData.getManufacturer());			statement.setString( 5,beanData.getBuy_date());			if (beanData.getBuy_price()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getBuy_price().floatValue());			}			statement.setString( 7,beanData.getBuy_location());			statement.setString( 8,beanData.getProt_desc_ch());			statement.setString( 9,beanData.getCreatetime());			statement.setString( 10,beanData.getCreateperson());			statement.setString( 11,beanData.getCreateunitid());			statement.setString( 12,beanData.getModifytime());			statement.setString( 13,beanData.getModifyperson());			statement.setString( 14,beanData.getDeleteflag());			statement.setString( 15,beanData.getDeptguid());			statement.setString( 16,beanData.getFormid());
			statement.setString( 17,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROTOTYPE SET id= ? , prot_code= ? , prot_name= ? , manufacturer= ? , buy_date= ? , buy_price= ? , buy_location= ? , prot_desc_ch= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PROTOTYPEData beanData = (YW_PROTOTYPEData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PROTOTYPE WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PROTOTYPE WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
