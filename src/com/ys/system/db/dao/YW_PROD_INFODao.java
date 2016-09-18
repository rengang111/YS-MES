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
public class YW_PROD_INFODao extends BaseAbstractDao
{
	public YW_PROD_INFOData beanData=new YW_PROD_INFOData();
	public YW_PROD_INFODao()
	{
	}
	public YW_PROD_INFODao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROD_INFOData)FindByPrimaryKey(beanData);
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
		YW_PROD_INFOData beanData = (YW_PROD_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROD_INFO( id,code,name_ch,name_en,pack_ch,pack_en,remark,desc_ch,desc_en,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCode());			statement.setString( 3,beanData.getName_ch());			statement.setString( 4,beanData.getName_en());			statement.setString( 5,beanData.getPack_ch());			statement.setString( 6,beanData.getPack_en());			statement.setString( 7,beanData.getRemark());			statement.setString( 8,beanData.getDesc_ch());			statement.setString( 9,beanData.getDesc_en());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROD_INFO( id,code,name_ch,name_en,pack_ch,pack_en,remark,desc_ch,desc_en,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROD_INFO( id,code,name_ch,name_en,pack_ch,pack_en,remark,desc_ch,desc_en,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getCode()) + "',");			bufSQL.append("'" + nullString(beanData.getName_ch()) + "',");			bufSQL.append("'" + nullString(beanData.getName_en()) + "',");			bufSQL.append("'" + nullString(beanData.getPack_ch()) + "',");			bufSQL.append("'" + nullString(beanData.getPack_en()) + "',");			bufSQL.append("'" + nullString(beanData.getRemark()) + "',");			bufSQL.append("'" + nullString(beanData.getDesc_ch()) + "',");			bufSQL.append("'" + nullString(beanData.getDesc_en()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROD_INFOData beanData = (YW_PROD_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROD_INFO( id,code,name_ch,name_en,pack_ch,pack_en,remark,desc_ch,desc_en,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCode());			statement.setString( 3,beanData.getName_ch());			statement.setString( 4,beanData.getName_en());			statement.setString( 5,beanData.getPack_ch());			statement.setString( 6,beanData.getPack_en());			statement.setString( 7,beanData.getRemark());			statement.setString( 8,beanData.getDesc_ch());			statement.setString( 9,beanData.getDesc_en());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROD_INFO( id,code,name_ch,name_en,pack_ch,pack_en,remark,desc_ch,desc_en,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROD_INFOData beanData = (YW_PROD_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROD_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROD_INFO: "+ e.toString());
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
		YW_PROD_INFOData beanData = (YW_PROD_INFOData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROD_INFO WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROD_INFO"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROD_INFO: "+ e.toString());
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
		YW_PROD_INFOData beanData = (YW_PROD_INFOData) beanDataTmp; 
		YW_PROD_INFOData returnData=new YW_PROD_INFOData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,code,name_ch,name_en,pack_ch,pack_en,remark,desc_ch,desc_en,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROD_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setCode( resultSet.getString( 2));			returnData.setName_ch( resultSet.getString( 3));			returnData.setName_en( resultSet.getString( 4));			returnData.setPack_ch( resultSet.getString( 5));			returnData.setPack_en( resultSet.getString( 6));			returnData.setRemark( resultSet.getString( 7));			returnData.setDesc_ch( resultSet.getString( 8));			returnData.setDesc_en( resultSet.getString( 9));			returnData.setCreatetime( resultSet.getString( 10));			returnData.setCreateperson( resultSet.getString( 11));			returnData.setCreateunitid( resultSet.getString( 12));			returnData.setModifytime( resultSet.getString( 13));			returnData.setModifyperson( resultSet.getString( 14));			returnData.setDeleteflag( resultSet.getString( 15));			returnData.setDeptguid( resultSet.getString( 16));			returnData.setFormid( resultSet.getString( 17));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,code,name_ch,name_en,pack_ch,pack_en,remark,desc_ch,desc_en,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROD_INFO  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,code,name_ch,name_en,pack_ch,pack_en,remark,desc_ch,desc_en,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROD_INFO"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROD_INFOData returnData=new YW_PROD_INFOData();
				returnData.setId( resultSet.getString( 1));				returnData.setCode( resultSet.getString( 2));				returnData.setName_ch( resultSet.getString( 3));				returnData.setName_en( resultSet.getString( 4));				returnData.setPack_ch( resultSet.getString( 5));				returnData.setPack_en( resultSet.getString( 6));				returnData.setRemark( resultSet.getString( 7));				returnData.setDesc_ch( resultSet.getString( 8));				returnData.setDesc_en( resultSet.getString( 9));				returnData.setCreatetime( resultSet.getString( 10));				returnData.setCreateperson( resultSet.getString( 11));				returnData.setCreateunitid( resultSet.getString( 12));				returnData.setModifytime( resultSet.getString( 13));				returnData.setModifyperson( resultSet.getString( 14));				returnData.setDeleteflag( resultSet.getString( 15));				returnData.setDeptguid( resultSet.getString( 16));				returnData.setFormid( resultSet.getString( 17));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,code,name_ch,name_en,pack_ch,pack_en,remark,desc_ch,desc_en,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROD_INFO" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROD_INFO SET id= ? , code= ? , name_ch= ? , name_en= ? , pack_ch= ? , pack_en= ? , remark= ? , desc_ch= ? , desc_en= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCode());			statement.setString( 3,beanData.getName_ch());			statement.setString( 4,beanData.getName_en());			statement.setString( 5,beanData.getPack_ch());			statement.setString( 6,beanData.getPack_en());			statement.setString( 7,beanData.getRemark());			statement.setString( 8,beanData.getDesc_ch());			statement.setString( 9,beanData.getDesc_en());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROD_INFO SET id= ? , code= ? , name_ch= ? , name_en= ? , pack_ch= ? , pack_en= ? , remark= ? , desc_ch= ? , desc_en= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROD_INFO SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Code = " + "'" + nullString(beanData.getCode()) + "',");			bufSQL.append("Name_ch = " + "'" + nullString(beanData.getName_ch()) + "',");			bufSQL.append("Name_en = " + "'" + nullString(beanData.getName_en()) + "',");			bufSQL.append("Pack_ch = " + "'" + nullString(beanData.getPack_ch()) + "',");			bufSQL.append("Pack_en = " + "'" + nullString(beanData.getPack_en()) + "',");			bufSQL.append("Remark = " + "'" + nullString(beanData.getRemark()) + "',");			bufSQL.append("Desc_ch = " + "'" + nullString(beanData.getDesc_ch()) + "',");			bufSQL.append("Desc_en = " + "'" + nullString(beanData.getDesc_en()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROD_INFOData beanData = (YW_PROD_INFOData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROD_INFO SET id= ? , code= ? , name_ch= ? , name_en= ? , pack_ch= ? , pack_en= ? , remark= ? , desc_ch= ? , desc_en= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCode());			statement.setString( 3,beanData.getName_ch());			statement.setString( 4,beanData.getName_en());			statement.setString( 5,beanData.getPack_ch());			statement.setString( 6,beanData.getPack_en());			statement.setString( 7,beanData.getRemark());			statement.setString( 8,beanData.getDesc_ch());			statement.setString( 9,beanData.getDesc_en());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());			statement.setString( 16,beanData.getDeptguid());			statement.setString( 17,beanData.getFormid());
			statement.setString( 18,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROD_INFO SET id= ? , code= ? , name_ch= ? , name_en= ? , pack_ch= ? , pack_en= ? , remark= ? , desc_ch= ? , desc_en= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PROD_INFOData beanData = (YW_PROD_INFOData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PROD_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PROD_INFO WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
