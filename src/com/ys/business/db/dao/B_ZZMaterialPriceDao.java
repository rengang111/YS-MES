package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.business.db.data.*;
import com.ys.util.basedao.BaseAbstractDao;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_ZZMaterialPriceDao extends BaseAbstractDao
{
	public B_ZZMaterialPriceData beanData=new B_ZZMaterialPriceData();
	public B_ZZMaterialPriceDao()
	{
	}
	public B_ZZMaterialPriceDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_ZZMaterialPriceData)FindByPrimaryKey(beanData);
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
		B_ZZMaterialPriceData beanData = (B_ZZMaterialPriceData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ZZMateriaPrice( recordid,materialid,managementcostrate,materialprice,cavitiesnumber,unittime,yield,laborprice,kilowatt,unitpower,kwprice,powerprice,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getManagementcostrate());			statement.setString( 4,beanData.getMaterialprice());			statement.setString( 5,beanData.getCavitiesnumber());			statement.setString( 6,beanData.getUnittime());			statement.setString( 7,beanData.getYield());			statement.setString( 8,beanData.getLaborprice());			statement.setString( 9,beanData.getKilowatt());			statement.setString( 10,beanData.getUnitpower());			statement.setString( 11,beanData.getKwprice());			statement.setString( 12,beanData.getPowerprice());			statement.setString( 13,beanData.getTotalprice());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ZZMateriaPrice( recordid,materialid,managementcostrate,materialprice,cavitiesnumber,unittime,yield,laborprice,kilowatt,unitpower,kwprice,powerprice,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_ZZMateriaPrice( recordid,materialid,managementcostrate,materialprice,cavitiesnumber,unittime,yield,laborprice,kilowatt,unitpower,kwprice,powerprice,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getManagementcostrate()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialprice()) + "',");			bufSQL.append("'" + nullString(beanData.getCavitiesnumber()) + "',");			bufSQL.append("'" + nullString(beanData.getUnittime()) + "',");			bufSQL.append("'" + nullString(beanData.getYield()) + "',");			bufSQL.append("'" + nullString(beanData.getLaborprice()) + "',");			bufSQL.append("'" + nullString(beanData.getKilowatt()) + "',");			bufSQL.append("'" + nullString(beanData.getUnitpower()) + "',");			bufSQL.append("'" + nullString(beanData.getKwprice()) + "',");			bufSQL.append("'" + nullString(beanData.getPowerprice()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_ZZMaterialPriceData beanData = (B_ZZMaterialPriceData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ZZMateriaPrice( recordid,materialid,managementcostrate,materialprice,cavitiesnumber,unittime,yield,laborprice,kilowatt,unitpower,kwprice,powerprice,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getManagementcostrate());			statement.setString( 4,beanData.getMaterialprice());			statement.setString( 5,beanData.getCavitiesnumber());			statement.setString( 6,beanData.getUnittime());			statement.setString( 7,beanData.getYield());			statement.setString( 8,beanData.getLaborprice());			statement.setString( 9,beanData.getKilowatt());			statement.setString( 10,beanData.getUnitpower());			statement.setString( 11,beanData.getKwprice());			statement.setString( 12,beanData.getPowerprice());			statement.setString( 13,beanData.getTotalprice());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ZZMateriaPrice( recordid,materialid,managementcostrate,materialprice,cavitiesnumber,unittime,yield,laborprice,kilowatt,unitpower,kwprice,powerprice,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_ZZMaterialPriceData beanData = (B_ZZMaterialPriceData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_ZZMateriaPrice WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ZZMateriaPrice: "+ e.toString());
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
		B_ZZMaterialPriceData beanData = (B_ZZMaterialPriceData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_ZZMateriaPrice WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_ZZMateriaPrice"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ZZMateriaPrice: "+ e.toString());
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
		B_ZZMaterialPriceData beanData = (B_ZZMaterialPriceData) beanDataTmp; 
		B_ZZMaterialPriceData returnData=new B_ZZMaterialPriceData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,materialid,managementcostrate,materialprice,cavitiesnumber,unittime,yield,laborprice,kilowatt,unitpower,kwprice,powerprice,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ZZMateriaPrice WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setMaterialid( resultSet.getString( 2));			returnData.setManagementcostrate( resultSet.getString( 3));			returnData.setMaterialprice( resultSet.getString( 4));			returnData.setCavitiesnumber( resultSet.getString( 5));			returnData.setUnittime( resultSet.getString( 6));			returnData.setYield( resultSet.getString( 7));			returnData.setLaborprice( resultSet.getString( 8));			returnData.setKilowatt( resultSet.getString( 9));			returnData.setUnitpower( resultSet.getString( 10));			returnData.setKwprice( resultSet.getString( 11));			returnData.setPowerprice( resultSet.getString( 12));			returnData.setTotalprice( resultSet.getString( 13));			returnData.setDeptguid( resultSet.getString( 14));			returnData.setCreatetime( resultSet.getString( 15));			returnData.setCreateperson( resultSet.getString( 16));			returnData.setCreateunitid( resultSet.getString( 17));			returnData.setModifytime( resultSet.getString( 18));			returnData.setModifyperson( resultSet.getString( 19));			returnData.setDeleteflag( resultSet.getString( 20));			returnData.setFormid( resultSet.getString( 21));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,materialid,managementcostrate,materialprice,cavitiesnumber,unittime,yield,laborprice,kilowatt,unitpower,kwprice,powerprice,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ZZMateriaPrice  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,materialid,managementcostrate,materialprice,cavitiesnumber,unittime,yield,laborprice,kilowatt,unitpower,kwprice,powerprice,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ZZMateriaPrice"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_ZZMaterialPriceData returnData=new B_ZZMaterialPriceData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setMaterialid( resultSet.getString( 2));				returnData.setManagementcostrate( resultSet.getString( 3));				returnData.setMaterialprice( resultSet.getString( 4));				returnData.setCavitiesnumber( resultSet.getString( 5));				returnData.setUnittime( resultSet.getString( 6));				returnData.setYield( resultSet.getString( 7));				returnData.setLaborprice( resultSet.getString( 8));				returnData.setKilowatt( resultSet.getString( 9));				returnData.setUnitpower( resultSet.getString( 10));				returnData.setKwprice( resultSet.getString( 11));				returnData.setPowerprice( resultSet.getString( 12));				returnData.setTotalprice( resultSet.getString( 13));				returnData.setDeptguid( resultSet.getString( 14));				returnData.setCreatetime( resultSet.getString( 15));				returnData.setCreateperson( resultSet.getString( 16));				returnData.setCreateunitid( resultSet.getString( 17));				returnData.setModifytime( resultSet.getString( 18));				returnData.setModifyperson( resultSet.getString( 19));				returnData.setDeleteflag( resultSet.getString( 20));				returnData.setFormid( resultSet.getString( 21));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,materialid,managementcostrate,materialprice,cavitiesnumber,unittime,yield,laborprice,kilowatt,unitpower,kwprice,powerprice,totalprice,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ZZMateriaPrice" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_ZZMateriaPrice SET recordid= ? , materialid= ? , managementcostrate= ? , materialprice= ? , cavitiesnumber= ? , unittime= ? , yield= ? , laborprice= ? , kilowatt= ? , unitpower= ? , kwprice= ? , powerprice= ? , totalprice= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getManagementcostrate());			statement.setString( 4,beanData.getMaterialprice());			statement.setString( 5,beanData.getCavitiesnumber());			statement.setString( 6,beanData.getUnittime());			statement.setString( 7,beanData.getYield());			statement.setString( 8,beanData.getLaborprice());			statement.setString( 9,beanData.getKilowatt());			statement.setString( 10,beanData.getUnitpower());			statement.setString( 11,beanData.getKwprice());			statement.setString( 12,beanData.getPowerprice());			statement.setString( 13,beanData.getTotalprice());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			statement.setString( 22,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ZZMateriaPrice SET recordid= ? , materialid= ? , managementcostrate= ? , materialprice= ? , cavitiesnumber= ? , unittime= ? , yield= ? , laborprice= ? , kilowatt= ? , unitpower= ? , kwprice= ? , powerprice= ? , totalprice= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_ZZMateriaPrice SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Managementcostrate = " + "'" + nullString(beanData.getManagementcostrate()) + "',");			bufSQL.append("Materialprice = " + "'" + nullString(beanData.getMaterialprice()) + "',");			bufSQL.append("Cavitiesnumber = " + "'" + nullString(beanData.getCavitiesnumber()) + "',");			bufSQL.append("Unittime = " + "'" + nullString(beanData.getUnittime()) + "',");			bufSQL.append("Yield = " + "'" + nullString(beanData.getYield()) + "',");			bufSQL.append("Laborprice = " + "'" + nullString(beanData.getLaborprice()) + "',");			bufSQL.append("Kilowatt = " + "'" + nullString(beanData.getKilowatt()) + "',");			bufSQL.append("Unitpower = " + "'" + nullString(beanData.getUnitpower()) + "',");			bufSQL.append("Kwprice = " + "'" + nullString(beanData.getKwprice()) + "',");			bufSQL.append("Powerprice = " + "'" + nullString(beanData.getPowerprice()) + "',");			bufSQL.append("Totalprice = " + "'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_ZZMaterialPriceData beanData = (B_ZZMaterialPriceData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_ZZMateriaPrice SET recordid= ? , materialid= ? , managementcostrate= ? , materialprice= ? , cavitiesnumber= ? , unittime= ? , yield= ? , laborprice= ? , kilowatt= ? , unitpower= ? , kwprice= ? , powerprice= ? , totalprice= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getMaterialid());			statement.setString( 3,beanData.getManagementcostrate());			statement.setString( 4,beanData.getMaterialprice());			statement.setString( 5,beanData.getCavitiesnumber());			statement.setString( 6,beanData.getUnittime());			statement.setString( 7,beanData.getYield());			statement.setString( 8,beanData.getLaborprice());			statement.setString( 9,beanData.getKilowatt());			statement.setString( 10,beanData.getUnitpower());			statement.setString( 11,beanData.getKwprice());			statement.setString( 12,beanData.getPowerprice());			statement.setString( 13,beanData.getTotalprice());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			statement.setString( 22,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ZZMateriaPrice SET recordid= ? , materialid= ? , managementcostrate= ? , materialprice= ? , cavitiesnumber= ? , unittime= ? , yield= ? , laborprice= ? , kilowatt= ? , unitpower= ? , kwprice= ? , powerprice= ? , totalprice= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_ZZMaterialPriceData beanData = (B_ZZMaterialPriceData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_ZZMateriaPrice WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_ZZMateriaPrice WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
