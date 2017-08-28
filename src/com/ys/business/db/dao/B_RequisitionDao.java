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
public class B_RequisitionDao extends BaseAbstractDao
{
	public B_RequisitionData beanData=new B_RequisitionData();
	public B_RequisitionDao()
	{
	}
	public B_RequisitionDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_RequisitionData)FindByPrimaryKey(beanData);
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Requisition( recordid,requisitionid,parentid,subid,ysid,contractid,supplierid,materialid,quantity,requisitiondate,userid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getRequisitionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getSupplierid());			statement.setString( 8,beanData.getMaterialid());			statement.setString( 9,beanData.getQuantity());			if (beanData.getRequisitiondate()==null || beanData.getRequisitiondate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getRequisitiondate());			}			statement.setString( 11,beanData.getUserid());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Requisition( recordid,requisitionid,parentid,subid,ysid,contractid,supplierid,materialid,quantity,requisitiondate,userid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_Requisition( recordid,requisitionid,parentid,subid,ysid,contractid,supplierid,materialid,quantity,requisitiondate,userid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getRequisitionid()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("'" + nullString(beanData.getRequisitiondate()) + "',");			bufSQL.append("'" + nullString(beanData.getUserid()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Requisition( recordid,requisitionid,parentid,subid,ysid,contractid,supplierid,materialid,quantity,requisitiondate,userid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getRequisitionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getSupplierid());			statement.setString( 8,beanData.getMaterialid());			statement.setString( 9,beanData.getQuantity());			if (beanData.getRequisitiondate()==null || beanData.getRequisitiondate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getRequisitiondate());			}			statement.setString( 11,beanData.getUserid());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Requisition( recordid,requisitionid,parentid,subid,ysid,contractid,supplierid,materialid,quantity,requisitiondate,userid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_Requisition WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Requisition: "+ e.toString());
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_Requisition WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_Requisition"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Requisition: "+ e.toString());
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
		B_RequisitionData returnData=new B_RequisitionData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,requisitionid,parentid,subid,ysid,contractid,supplierid,materialid,quantity,requisitiondate,userid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Requisition WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setRequisitionid( resultSet.getString( 2));			returnData.setParentid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setYsid( resultSet.getString( 5));			returnData.setContractid( resultSet.getString( 6));			returnData.setSupplierid( resultSet.getString( 7));			returnData.setMaterialid( resultSet.getString( 8));			returnData.setQuantity( resultSet.getString( 9));			returnData.setRequisitiondate( resultSet.getString( 10));			returnData.setUserid( resultSet.getString( 11));			returnData.setDeptguid( resultSet.getString( 12));			returnData.setCreatetime( resultSet.getString( 13));			returnData.setCreateperson( resultSet.getString( 14));			returnData.setCreateunitid( resultSet.getString( 15));			returnData.setModifytime( resultSet.getString( 16));			returnData.setModifyperson( resultSet.getString( 17));			returnData.setDeleteflag( resultSet.getString( 18));			returnData.setFormid( resultSet.getString( 19));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,requisitionid,parentid,subid,ysid,contractid,supplierid,materialid,quantity,requisitiondate,userid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Requisition  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,requisitionid,parentid,subid,ysid,contractid,supplierid,materialid,quantity,requisitiondate,userid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Requisition"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_RequisitionData returnData=new B_RequisitionData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setRequisitionid( resultSet.getString( 2));				returnData.setParentid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setYsid( resultSet.getString( 5));				returnData.setContractid( resultSet.getString( 6));				returnData.setSupplierid( resultSet.getString( 7));				returnData.setMaterialid( resultSet.getString( 8));				returnData.setQuantity( resultSet.getString( 9));				returnData.setRequisitiondate( resultSet.getString( 10));				returnData.setUserid( resultSet.getString( 11));				returnData.setDeptguid( resultSet.getString( 12));				returnData.setCreatetime( resultSet.getString( 13));				returnData.setCreateperson( resultSet.getString( 14));				returnData.setCreateunitid( resultSet.getString( 15));				returnData.setModifytime( resultSet.getString( 16));				returnData.setModifyperson( resultSet.getString( 17));				returnData.setDeleteflag( resultSet.getString( 18));				returnData.setFormid( resultSet.getString( 19));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,requisitionid,parentid,subid,ysid,contractid,supplierid,materialid,quantity,requisitiondate,userid,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Requisition" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_Requisition SET recordid= ? , requisitionid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , supplierid= ? , materialid= ? , quantity= ? , requisitiondate= ? , userid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getRequisitionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getSupplierid());			statement.setString( 8,beanData.getMaterialid());			statement.setString( 9,beanData.getQuantity());			if (beanData.getRequisitiondate()==null || beanData.getRequisitiondate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getRequisitiondate());			}			statement.setString( 11,beanData.getUserid());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Requisition SET recordid= ? , requisitionid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , supplierid= ? , materialid= ? , quantity= ? , requisitiondate= ? , userid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_Requisition SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Requisitionid = " + "'" + nullString(beanData.getRequisitionid()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Contractid = " + "'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Quantity = " + "'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("Requisitiondate = " + "'" + nullString(beanData.getRequisitiondate()) + "',");			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_RequisitionData beanData = (B_RequisitionData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_Requisition SET recordid= ? , requisitionid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , supplierid= ? , materialid= ? , quantity= ? , requisitiondate= ? , userid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getRequisitionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getSupplierid());			statement.setString( 8,beanData.getMaterialid());			statement.setString( 9,beanData.getQuantity());			if (beanData.getRequisitiondate()==null || beanData.getRequisitiondate().trim().equals(""))			{				statement.setNull( 10, Types.DATE);			}			else			{				statement.setString( 10, beanData.getRequisitiondate());			}			statement.setString( 11,beanData.getUserid());			statement.setString( 12,beanData.getDeptguid());			statement.setString( 13,beanData.getCreatetime());			statement.setString( 14,beanData.getCreateperson());			statement.setString( 15,beanData.getCreateunitid());			statement.setString( 16,beanData.getModifytime());			statement.setString( 17,beanData.getModifyperson());			statement.setString( 18,beanData.getDeleteflag());			statement.setString( 19,beanData.getFormid());
			statement.setString( 20,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Requisition SET recordid= ? , requisitionid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , supplierid= ? , materialid= ? , quantity= ? , requisitiondate= ? , userid= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_RequisitionData beanData = (B_RequisitionData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_Requisition WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_Requisition WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
