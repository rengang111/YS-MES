package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.util.basedao.BaseAbstractDao;
import com.ys.business.db.data.*;

/**
* <p>Title: </p>
* <p>Description: ���ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_MouldContractBaseInfoDao extends BaseAbstractDao
{
	public B_MouldContractBaseInfoData beanData=new B_MouldContractBaseInfoData();
	public B_MouldContractBaseInfoDao()
	{
	}
	public B_MouldContractBaseInfoDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_MouldContractBaseInfoData)FindByPrimaryKey(beanData);
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
		B_MouldContractBaseInfoData beanData = (B_MouldContractBaseInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldContractBaseInfo( id,contractid,contractyear,productmodelid,type,supplierid,contractdate,deliverdate,belong,oursidepay,providerpay,returncase,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getContractid());			statement.setString( 3,beanData.getContractyear());			statement.setString( 4,beanData.getProductmodelid());			statement.setString( 5,beanData.getType());			statement.setString( 6,beanData.getSupplierid());			statement.setString( 7,beanData.getContractdate());			statement.setString( 8,beanData.getDeliverdate());			statement.setString( 9,beanData.getBelong());			statement.setString( 10,beanData.getOursidepay());			statement.setString( 11,beanData.getProviderpay());			statement.setString( 12,beanData.getReturncase());			statement.setString( 13,beanData.getStatus());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldContractBaseInfo( id,contractid,contractyear,productmodelid,type,supplierid,contractdate,deliverdate,belong,oursidepay,providerpay,returncase,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_MouldContractBaseInfo( id,contractid,contractyear,productmodelid,type,supplierid,contractdate,deliverdate,belong,oursidepay,providerpay,returncase,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("'" + nullString(beanData.getContractyear()) + "',");			bufSQL.append("'" + nullString(beanData.getProductmodelid()) + "',");			bufSQL.append("'" + nullString(beanData.getType()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getContractdate()) + "',");			bufSQL.append("'" + nullString(beanData.getDeliverdate()) + "',");			bufSQL.append("'" + nullString(beanData.getBelong()) + "',");			bufSQL.append("'" + nullString(beanData.getOursidepay()) + "',");			bufSQL.append("'" + nullString(beanData.getProviderpay()) + "',");			bufSQL.append("'" + nullString(beanData.getReturncase()) + "',");			bufSQL.append("'" + nullString(beanData.getStatus()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_MouldContractBaseInfoData beanData = (B_MouldContractBaseInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_MouldContractBaseInfo( id,contractid,contractyear,productmodelid,type,supplierid,contractdate,deliverdate,belong,oursidepay,providerpay,returncase,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getContractid());			statement.setString( 3,beanData.getContractyear());			statement.setString( 4,beanData.getProductmodelid());			statement.setString( 5,beanData.getType());			statement.setString( 6,beanData.getSupplierid());			statement.setString( 7,beanData.getContractdate());			statement.setString( 8,beanData.getDeliverdate());			statement.setString( 9,beanData.getBelong());			statement.setString( 10,beanData.getOursidepay());			statement.setString( 11,beanData.getProviderpay());			statement.setString( 12,beanData.getReturncase());			statement.setString( 13,beanData.getStatus());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_MouldContractBaseInfo( id,contractid,contractyear,productmodelid,type,supplierid,contractdate,deliverdate,belong,oursidepay,providerpay,returncase,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_MouldContractBaseInfoData beanData = (B_MouldContractBaseInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_MouldContractBaseInfo WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldContractBaseInfo: "+ e.toString());
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
		B_MouldContractBaseInfoData beanData = (B_MouldContractBaseInfoData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_MouldContractBaseInfo WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_MouldContractBaseInfo"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_MouldContractBaseInfo: "+ e.toString());
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
		B_MouldContractBaseInfoData beanData = (B_MouldContractBaseInfoData) beanDataTmp; 
		B_MouldContractBaseInfoData returnData=new B_MouldContractBaseInfoData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,contractid,contractyear,productmodelid,type,supplierid,contractdate,deliverdate,belong,oursidepay,providerpay,returncase,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldContractBaseInfo WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setContractid( resultSet.getString( 2));			returnData.setContractyear( resultSet.getString( 3));			returnData.setProductmodelid( resultSet.getString( 4));			returnData.setType( resultSet.getString( 5));			returnData.setSupplierid( resultSet.getString( 6));			returnData.setContractdate( resultSet.getString( 7));			returnData.setDeliverdate( resultSet.getString( 8));			returnData.setBelong( resultSet.getString( 9));			returnData.setOursidepay( resultSet.getString( 10));			returnData.setProviderpay( resultSet.getString( 11));			returnData.setReturncase( resultSet.getString( 12));			returnData.setStatus( resultSet.getString( 13));			returnData.setDeptguid( resultSet.getString( 14));			returnData.setCreatetime( resultSet.getString( 15));			returnData.setCreateperson( resultSet.getString( 16));			returnData.setCreateunitid( resultSet.getString( 17));			returnData.setModifytime( resultSet.getString( 18));			returnData.setModifyperson( resultSet.getString( 19));			returnData.setDeleteflag( resultSet.getString( 20));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,contractid,contractyear,productmodelid,type,supplierid,contractdate,deliverdate,belong,oursidepay,providerpay,returncase,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldContractBaseInfo  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,contractid,contractyear,productmodelid,type,supplierid,contractdate,deliverdate,belong,oursidepay,providerpay,returncase,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldContractBaseInfo"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_MouldContractBaseInfoData returnData=new B_MouldContractBaseInfoData();
				returnData.setId( resultSet.getString( 1));				returnData.setContractid( resultSet.getString( 2));				returnData.setContractyear( resultSet.getString( 3));				returnData.setProductmodelid( resultSet.getString( 4));				returnData.setType( resultSet.getString( 5));				returnData.setSupplierid( resultSet.getString( 6));				returnData.setContractdate( resultSet.getString( 7));				returnData.setDeliverdate( resultSet.getString( 8));				returnData.setBelong( resultSet.getString( 9));				returnData.setOursidepay( resultSet.getString( 10));				returnData.setProviderpay( resultSet.getString( 11));				returnData.setReturncase( resultSet.getString( 12));				returnData.setStatus( resultSet.getString( 13));				returnData.setDeptguid( resultSet.getString( 14));				returnData.setCreatetime( resultSet.getString( 15));				returnData.setCreateperson( resultSet.getString( 16));				returnData.setCreateunitid( resultSet.getString( 17));				returnData.setModifytime( resultSet.getString( 18));				returnData.setModifyperson( resultSet.getString( 19));				returnData.setDeleteflag( resultSet.getString( 20));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,contractid,contractyear,productmodelid,type,supplierid,contractdate,deliverdate,belong,oursidepay,providerpay,returncase,status,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_MouldContractBaseInfo" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_MouldContractBaseInfo SET id= ? , contractid= ? , contractyear= ? , productmodelid= ? , type= ? , supplierid= ? , contractdate= ? , deliverdate= ? , belong= ? , oursidepay= ? , providerpay= ? , returncase= ? , status= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getContractid());			statement.setString( 3,beanData.getContractyear());			statement.setString( 4,beanData.getProductmodelid());			statement.setString( 5,beanData.getType());			statement.setString( 6,beanData.getSupplierid());			statement.setString( 7,beanData.getContractdate());			statement.setString( 8,beanData.getDeliverdate());			statement.setString( 9,beanData.getBelong());			statement.setString( 10,beanData.getOursidepay());			statement.setString( 11,beanData.getProviderpay());			statement.setString( 12,beanData.getReturncase());			statement.setString( 13,beanData.getStatus());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());
			statement.setString( 21,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldContractBaseInfo SET id= ? , contractid= ? , contractyear= ? , productmodelid= ? , type= ? , supplierid= ? , contractdate= ? , deliverdate= ? , belong= ? , oursidepay= ? , providerpay= ? , returncase= ? , status= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_MouldContractBaseInfo SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Contractid = " + "'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("Contractyear = " + "'" + nullString(beanData.getContractyear()) + "',");			bufSQL.append("Productmodelid = " + "'" + nullString(beanData.getProductmodelid()) + "',");			bufSQL.append("Type = " + "'" + nullString(beanData.getType()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Contractdate = " + "'" + nullString(beanData.getContractdate()) + "',");			bufSQL.append("Deliverdate = " + "'" + nullString(beanData.getDeliverdate()) + "',");			bufSQL.append("Belong = " + "'" + nullString(beanData.getBelong()) + "',");			bufSQL.append("Oursidepay = " + "'" + nullString(beanData.getOursidepay()) + "',");			bufSQL.append("Providerpay = " + "'" + nullString(beanData.getProviderpay()) + "',");			bufSQL.append("Returncase = " + "'" + nullString(beanData.getReturncase()) + "',");			bufSQL.append("Status = " + "'" + nullString(beanData.getStatus()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_MouldContractBaseInfoData beanData = (B_MouldContractBaseInfoData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_MouldContractBaseInfo SET id= ? , contractid= ? , contractyear= ? , productmodelid= ? , type= ? , supplierid= ? , contractdate= ? , deliverdate= ? , belong= ? , oursidepay= ? , providerpay= ? , returncase= ? , status= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getContractid());			statement.setString( 3,beanData.getContractyear());			statement.setString( 4,beanData.getProductmodelid());			statement.setString( 5,beanData.getType());			statement.setString( 6,beanData.getSupplierid());			statement.setString( 7,beanData.getContractdate());			statement.setString( 8,beanData.getDeliverdate());			statement.setString( 9,beanData.getBelong());			statement.setString( 10,beanData.getOursidepay());			statement.setString( 11,beanData.getProviderpay());			statement.setString( 12,beanData.getReturncase());			statement.setString( 13,beanData.getStatus());			statement.setString( 14,beanData.getDeptguid());			statement.setString( 15,beanData.getCreatetime());			statement.setString( 16,beanData.getCreateperson());			statement.setString( 17,beanData.getCreateunitid());			statement.setString( 18,beanData.getModifytime());			statement.setString( 19,beanData.getModifyperson());			statement.setString( 20,beanData.getDeleteflag());
			statement.setString( 21,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_MouldContractBaseInfo SET id= ? , contractid= ? , contractyear= ? , productmodelid= ? , type= ? , supplierid= ? , contractdate= ? , deliverdate= ? , belong= ? , oursidepay= ? , providerpay= ? , returncase= ? , status= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
		B_MouldContractBaseInfoData beanData = (B_MouldContractBaseInfoData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM B_MouldContractBaseInfo WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_MouldContractBaseInfo WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
