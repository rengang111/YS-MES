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
public class YW_MOLD_DTLDao extends BaseAbstractDao
{
	public YW_MOLD_DTLData beanData=new YW_MOLD_DTLData();
	public YW_MOLD_DTLDao()
	{
	}
	public YW_MOLD_DTLDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_MOLD_DTLData)FindByPrimaryKey(beanData);
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
		YW_MOLD_DTLData beanData = (YW_MOLD_DTLData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_MOLD_DTL( id,prod_code,mold_code,mold_name,mold_size,material,modulus,weight,price,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getMold_code());			statement.setString( 4,beanData.getMold_name());			statement.setString( 5,beanData.getMold_size());			statement.setString( 6,beanData.getMaterial());			statement.setString( 7,beanData.getModulus());			statement.setInt( 8,beanData.getWeight());			if (beanData.getPrice()== null)			{				statement.setNull( 9, Types.FLOAT);			}			else			{				statement.setFloat( 9, beanData.getPrice().floatValue());			}			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_MOLD_DTL( id,prod_code,mold_code,mold_name,mold_size,material,modulus,weight,price,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_MOLD_DTL( id,prod_code,mold_code,mold_name,mold_size,material,modulus,weight,price,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getProd_code()) + "',");			bufSQL.append("'" + nullString(beanData.getMold_code()) + "',");			bufSQL.append("'" + nullString(beanData.getMold_name()) + "',");			bufSQL.append("'" + nullString(beanData.getMold_size()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterial()) + "',");			bufSQL.append("'" + nullString(beanData.getModulus()) + "',");			bufSQL.append(beanData.getWeight() + ",");			bufSQL.append(beanData.getPrice() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_MOLD_DTLData beanData = (YW_MOLD_DTLData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_MOLD_DTL( id,prod_code,mold_code,mold_name,mold_size,material,modulus,weight,price,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getMold_code());			statement.setString( 4,beanData.getMold_name());			statement.setString( 5,beanData.getMold_size());			statement.setString( 6,beanData.getMaterial());			statement.setString( 7,beanData.getModulus());			statement.setInt( 8,beanData.getWeight());			if (beanData.getPrice()== null)			{				statement.setNull( 9, Types.FLOAT);			}			else			{				statement.setFloat( 9, beanData.getPrice().floatValue());			}			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_MOLD_DTL( id,prod_code,mold_code,mold_name,mold_size,material,modulus,weight,price,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_MOLD_DTLData beanData = (YW_MOLD_DTLData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_MOLD_DTL WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_MOLD_DTL: "+ e.toString());
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
		YW_MOLD_DTLData beanData = (YW_MOLD_DTLData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_MOLD_DTL WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_MOLD_DTL"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_MOLD_DTL: "+ e.toString());
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
		YW_MOLD_DTLData beanData = (YW_MOLD_DTLData) beanDataTmp; 
		YW_MOLD_DTLData returnData=new YW_MOLD_DTLData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,prod_code,mold_code,mold_name,mold_size,material,modulus,weight,price,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_MOLD_DTL WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProd_code( resultSet.getString( 2));			returnData.setMold_code( resultSet.getString( 3));			returnData.setMold_name( resultSet.getString( 4));			returnData.setMold_size( resultSet.getString( 5));			returnData.setMaterial( resultSet.getString( 6));			returnData.setModulus( resultSet.getString( 7));			returnData.setWeight( resultSet.getInt( 8));			if (resultSet.getString( 9)==null)				returnData.setPrice(null);			else				returnData.setPrice( new Float(resultSet.getFloat( 9)));			returnData.setCreatetime( resultSet.getString( 10));			returnData.setCreateperson( resultSet.getString( 11));			returnData.setCreateunitid( resultSet.getString( 12));			returnData.setModifytime( resultSet.getString( 13));			returnData.setModifyperson( resultSet.getString( 14));			returnData.setDeleteflag( resultSet.getString( 15));			returnData.setDeptguid( resultSet.getString( 16));			returnData.setFormid( resultSet.getString( 17));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,prod_code,mold_code,mold_name,mold_size,material,modulus,weight,price,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_MOLD_DTL  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,prod_code,mold_code,mold_name,mold_size,material,modulus,weight,price,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_MOLD_DTL"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_MOLD_DTLData returnData=new YW_MOLD_DTLData();
				returnData.setId( resultSet.getString( 1));				returnData.setProd_code( resultSet.getString( 2));				returnData.setMold_code( resultSet.getString( 3));				returnData.setMold_name( resultSet.getString( 4));				returnData.setMold_size( resultSet.getString( 5));				returnData.setMaterial( resultSet.getString( 6));				returnData.setModulus( resultSet.getString( 7));				returnData.setWeight( resultSet.getInt( 8));				if (resultSet.getString( 9)==null)					returnData.setPrice(null);				else					returnData.setPrice( new Float(resultSet.getFloat( 9)));				returnData.setCreatetime( resultSet.getString( 10));				returnData.setCreateperson( resultSet.getString( 11));				returnData.setCreateunitid( resultSet.getString( 12));				returnData.setModifytime( resultSet.getString( 13));				returnData.setModifyperson( resultSet.getString( 14));				returnData.setDeleteflag( resultSet.getString( 15));				returnData.setDeptguid( resultSet.getString( 16));				returnData.setFormid( resultSet.getString( 17));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,prod_code,mold_code,mold_name,mold_size,material,modulus,weight,price,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_MOLD_DTL" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_MOLD_DTL SET id= ? , prod_code= ? , mold_code= ? , mold_name= ? , mold_size= ? , material= ? , modulus= ? , weight= ? , price= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getMold_code());			statement.setString( 4,beanData.getMold_name());			statement.setString( 5,beanData.getMold_size());			statement.setString( 6,beanData.getMaterial());			statement.setString( 7,beanData.getModulus());			statement.setInt( 8,beanData.getWeight());			if (beanData.getPrice()== null)			{				statement.setNull( 9, Types.FLOAT);			}			else			{				statement.setFloat( 9, beanData.getPrice().floatValue());			}			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_MOLD_DTL SET id= ? , prod_code= ? , mold_code= ? , mold_name= ? , mold_size= ? , material= ? , modulus= ? , weight= ? , price= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_MOLD_DTL SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Prod_code = " + "'" + nullString(beanData.getProd_code()) + "',");			bufSQL.append("Mold_code = " + "'" + nullString(beanData.getMold_code()) + "',");			bufSQL.append("Mold_name = " + "'" + nullString(beanData.getMold_name()) + "',");			bufSQL.append("Mold_size = " + "'" + nullString(beanData.getMold_size()) + "',");			bufSQL.append("Material = " + "'" + nullString(beanData.getMaterial()) + "',");			bufSQL.append("Modulus = " + "'" + nullString(beanData.getModulus()) + "',");			bufSQL.append("Weight = " + beanData.getWeight() + ",");			bufSQL.append("Price = " + beanData.getPrice() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_MOLD_DTLData beanData = (YW_MOLD_DTLData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_MOLD_DTL SET id= ? , prod_code= ? , mold_code= ? , mold_name= ? , mold_size= ? , material= ? , modulus= ? , weight= ? , price= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProd_code());			statement.setString( 3,beanData.getMold_code());			statement.setString( 4,beanData.getMold_name());			statement.setString( 5,beanData.getMold_size());			statement.setString( 6,beanData.getMaterial());			statement.setString( 7,beanData.getModulus());			statement.setInt( 8,beanData.getWeight());			if (beanData.getPrice()== null)			{				statement.setNull( 9, Types.FLOAT);			}			else			{				statement.setFloat( 9, beanData.getPrice().floatValue());			}			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_MOLD_DTL SET id= ? , prod_code= ? , mold_code= ? , mold_name= ? , mold_size= ? , material= ? , modulus= ? , weight= ? , price= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_MOLD_DTLData beanData = (YW_MOLD_DTLData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_MOLD_DTL WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_MOLD_DTL WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
