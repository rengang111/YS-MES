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
public class B_BomPlanDao extends BaseAbstractDao
{
	public B_BomPlanData beanData=new B_BomPlanData();
	public B_BomPlanDao()
	{
	}
	public B_BomPlanDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_BomPlanData)FindByPrimaryKey(beanData);
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
		B_BomPlanData beanData = (B_BomPlanData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_BomPlan( recordid,ysid,bomid,materialid,subid,quantity,managerate,managecost,productcost,accountcost,laborcost,materialcost,plandate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getBomid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getSubid());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getManagerate());			statement.setString( 8,beanData.getManagecost());			statement.setString( 9,beanData.getProductcost());			statement.setString( 10,beanData.getAccountcost());			statement.setString( 11,beanData.getLaborcost());			statement.setString( 12,beanData.getMaterialcost());			if (beanData.getPlandate()==null || beanData.getPlandate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setTimestamp( 13, getDateTimeFromString(beanData.getPlandate()));			}			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_BomPlan( recordid,ysid,bomid,materialid,subid,quantity,managerate,managecost,productcost,accountcost,laborcost,materialcost,plandate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_BomPlan( recordid,ysid,bomid,materialid,subid,quantity,managerate,managecost,productcost,accountcost,laborcost,materialcost,plandate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getBomid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("'" + nullString(beanData.getManagerate()) + "',");			bufSQL.append("'" + nullString(beanData.getManagecost()) + "',");			bufSQL.append("'" + nullString(beanData.getProductcost()) + "',");			bufSQL.append("'" + nullString(beanData.getAccountcost()) + "',");			bufSQL.append("'" + nullString(beanData.getLaborcost()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialcost()) + "',");			bufSQL.append("'" + nullString(beanData.getPlandate()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_BomPlanData beanData = (B_BomPlanData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_BomPlan( recordid,ysid,bomid,materialid,subid,quantity,managerate,managecost,productcost,accountcost,laborcost,materialcost,plandate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getBomid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getSubid());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getManagerate());			statement.setString( 8,beanData.getManagecost());			statement.setString( 9,beanData.getProductcost());			statement.setString( 10,beanData.getAccountcost());			statement.setString( 11,beanData.getLaborcost());			statement.setString( 12,beanData.getMaterialcost());			if (beanData.getPlandate()==null || beanData.getPlandate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setTimestamp( 13, getDateTimeFromString(beanData.getPlandate()));			}			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_BomPlan( recordid,ysid,bomid,materialid,subid,quantity,managerate,managecost,productcost,accountcost,laborcost,materialcost,plandate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_BomPlanData beanData = (B_BomPlanData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_BomPlan WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_BomPlan: "+ e.toString());
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
		B_BomPlanData beanData = (B_BomPlanData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_BomPlan WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_BomPlan"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_BomPlan: "+ e.toString());
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
		B_BomPlanData beanData = (B_BomPlanData) beanDataTmp; 
		B_BomPlanData returnData=new B_BomPlanData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,ysid,bomid,materialid,subid,quantity,managerate,managecost,productcost,accountcost,laborcost,materialcost,plandate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_BomPlan WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setYsid( resultSet.getString( 2));			returnData.setBomid( resultSet.getString( 3));			returnData.setMaterialid( resultSet.getString( 4));			returnData.setSubid( resultSet.getString( 5));			returnData.setQuantity( resultSet.getString( 6));			returnData.setManagerate( resultSet.getString( 7));			returnData.setManagecost( resultSet.getString( 8));			returnData.setProductcost( resultSet.getString( 9));			returnData.setAccountcost( resultSet.getString( 10));			returnData.setLaborcost( resultSet.getString( 11));			returnData.setMaterialcost( resultSet.getString( 12));			returnData.setPlandate( resultSet.getString( 13));			returnData.setDeptguid( resultSet.getString( 14));			returnData.setCreatetime( resultSet.getString( 15));			returnData.setCreateperson( resultSet.getString( 16));			returnData.setCreateunitid( resultSet.getString( 17));			returnData.setModifytime( resultSet.getString( 18));			returnData.setModifyperson( resultSet.getString( 19));			returnData.setDeleteflag( resultSet.getString( 20));			returnData.setFormid( resultSet.getString( 21));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,bomid,materialid,subid,quantity,managerate,managecost,productcost,accountcost,laborcost,materialcost,plandate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_BomPlan  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,ysid,bomid,materialid,subid,quantity,managerate,managecost,productcost,accountcost,laborcost,materialcost,plandate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_BomPlan"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_BomPlanData returnData=new B_BomPlanData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setYsid( resultSet.getString( 2));				returnData.setBomid( resultSet.getString( 3));				returnData.setMaterialid( resultSet.getString( 4));				returnData.setSubid( resultSet.getString( 5));				returnData.setQuantity( resultSet.getString( 6));				returnData.setManagerate( resultSet.getString( 7));				returnData.setManagecost( resultSet.getString( 8));				returnData.setProductcost( resultSet.getString( 9));				returnData.setAccountcost( resultSet.getString( 10));				returnData.setLaborcost( resultSet.getString( 11));				returnData.setMaterialcost( resultSet.getString( 12));				returnData.setPlandate( resultSet.getString( 13));				returnData.setDeptguid( resultSet.getString( 14));				returnData.setCreatetime( resultSet.getString( 15));				returnData.setCreateperson( resultSet.getString( 16));				returnData.setCreateunitid( resultSet.getString( 17));				returnData.setModifytime( resultSet.getString( 18));				returnData.setModifyperson( resultSet.getString( 19));				returnData.setDeleteflag( resultSet.getString( 20));				returnData.setFormid( resultSet.getString( 21));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,ysid,bomid,materialid,subid,quantity,managerate,managecost,productcost,accountcost,laborcost,materialcost,plandate,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_BomPlan" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_BomPlan SET recordid= ? , ysid= ? , bomid= ? , materialid= ? , subid= ? , quantity= ? , managerate= ? , managecost= ? , productcost= ? , accountcost= ? , laborcost= ? , materialcost= ? , plandate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getBomid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getSubid());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getManagerate());			statement.setString( 8,beanData.getManagecost());			statement.setString( 9,beanData.getProductcost());			statement.setString( 10,beanData.getAccountcost());			statement.setString( 11,beanData.getLaborcost());			statement.setString( 12,beanData.getMaterialcost());			if (beanData.getPlandate()==null || beanData.getPlandate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setTimestamp( 13, getDateTimeFromString(beanData.getPlandate()));			}			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			statement.setString( 22,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_BomPlan SET recordid= ? , ysid= ? , bomid= ? , materialid= ? , subid= ? , quantity= ? , managerate= ? , managecost= ? , productcost= ? , accountcost= ? , laborcost= ? , materialcost= ? , plandate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_BomPlan SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Bomid = " + "'" + nullString(beanData.getBomid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Quantity = " + "'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("Managerate = " + "'" + nullString(beanData.getManagerate()) + "',");			bufSQL.append("Managecost = " + "'" + nullString(beanData.getManagecost()) + "',");			bufSQL.append("Productcost = " + "'" + nullString(beanData.getProductcost()) + "',");			bufSQL.append("Accountcost = " + "'" + nullString(beanData.getAccountcost()) + "',");			bufSQL.append("Laborcost = " + "'" + nullString(beanData.getLaborcost()) + "',");			bufSQL.append("Materialcost = " + "'" + nullString(beanData.getMaterialcost()) + "',");			bufSQL.append("Plandate = " + "'" + nullString(beanData.getPlandate()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_BomPlanData beanData = (B_BomPlanData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_BomPlan SET recordid= ? , ysid= ? , bomid= ? , materialid= ? , subid= ? , quantity= ? , managerate= ? , managecost= ? , productcost= ? , accountcost= ? , laborcost= ? , materialcost= ? , plandate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getYsid());			statement.setString( 3,beanData.getBomid());			statement.setString( 4,beanData.getMaterialid());			statement.setString( 5,beanData.getSubid());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getManagerate());			statement.setString( 8,beanData.getManagecost());			statement.setString( 9,beanData.getProductcost());			statement.setString( 10,beanData.getAccountcost());			statement.setString( 11,beanData.getLaborcost());			statement.setString( 12,beanData.getMaterialcost());			if (beanData.getPlandate()==null || beanData.getPlandate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setTimestamp( 13, getDateTimeFromString(beanData.getPlandate()));			}			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());			statement.setString( 21,beanData.getFormid());
			statement.setString( 22,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_BomPlan SET recordid= ? , ysid= ? , bomid= ? , materialid= ? , subid= ? , quantity= ? , managerate= ? , managecost= ? , productcost= ? , accountcost= ? , laborcost= ? , materialcost= ? , plandate= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_BomPlanData beanData = (B_BomPlanData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_BomPlan WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_BomPlan WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
