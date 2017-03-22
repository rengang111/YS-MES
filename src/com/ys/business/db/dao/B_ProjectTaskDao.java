package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.util.basedao.BaseAbstractDao;
import com.ys.business.db.data.*;

/**
* <p>Title: </p>
* <p>Description: 数据表  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_ProjectTaskDao extends BaseAbstractDao
{
	public B_ProjectTaskData beanData=new B_ProjectTaskData();
	public B_ProjectTaskDao()
	{
	}
	public B_ProjectTaskDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_ProjectTaskData)FindByPrimaryKey(beanData);
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
		B_ProjectTaskData beanData = (B_ProjectTaskData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ProjectTask( id,projectid,projectname,tempversion,manager,referprototype,designcapability,begintime,endtime,packing,estimatecost,currency,exchangerate,saleprice,sales,recoverynum,failmode,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProjectid());			statement.setString( 3,beanData.getProjectname());			statement.setString( 4,beanData.getTempversion());			statement.setString( 5,beanData.getManager());			statement.setString( 6,beanData.getReferprototype());			statement.setString( 7,beanData.getDesigncapability());			statement.setString( 8,beanData.getBegintime());			statement.setString( 9,beanData.getEndtime());			statement.setString( 10,beanData.getPacking());			statement.setString( 11,beanData.getEstimatecost());			statement.setString( 12,beanData.getCurrency());			statement.setString( 13,beanData.getExchangerate());			statement.setString( 14,beanData.getSaleprice());			statement.setString( 15,beanData.getSales());			statement.setString( 16,beanData.getRecoverynum());			statement.setString( 17,beanData.getFailmode());			statement.setString( 18,beanData.getImage_filename());			statement.setString( 19,beanData.getDeptguid());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ProjectTask( id,projectid,projectname,tempversion,manager,referprototype,designcapability,begintime,endtime,packing,estimatecost,currency,exchangerate,saleprice,sales,recoverynum,failmode,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
			bufSQL.append("INSERT INTO B_ProjectTask( id,projectid,projectname,tempversion,manager,referprototype,designcapability,begintime,endtime,packing,estimatecost,currency,exchangerate,saleprice,sales,recoverynum,failmode,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getProjectid()) + "',");			bufSQL.append("'" + nullString(beanData.getProjectname()) + "',");			bufSQL.append("'" + nullString(beanData.getTempversion()) + "',");			bufSQL.append("'" + nullString(beanData.getManager()) + "',");			bufSQL.append("'" + nullString(beanData.getReferprototype()) + "',");			bufSQL.append("'" + nullString(beanData.getDesigncapability()) + "',");			bufSQL.append("'" + nullString(beanData.getBegintime()) + "',");			bufSQL.append("'" + nullString(beanData.getEndtime()) + "',");			bufSQL.append("'" + nullString(beanData.getPacking()) + "',");			bufSQL.append("'" + nullString(beanData.getEstimatecost()) + "',");			bufSQL.append("'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getExchangerate()) + "',");			bufSQL.append("'" + nullString(beanData.getSaleprice()) + "',");			bufSQL.append("'" + nullString(beanData.getSales()) + "',");			bufSQL.append("'" + nullString(beanData.getRecoverynum()) + "',");			bufSQL.append("'" + nullString(beanData.getFailmode()) + "',");			bufSQL.append("'" + nullString(beanData.getImage_filename()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_ProjectTaskData beanData = (B_ProjectTaskData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ProjectTask( id,projectid,projectname,tempversion,manager,referprototype,designcapability,begintime,endtime,packing,estimatecost,currency,exchangerate,saleprice,sales,recoverynum,failmode,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProjectid());			statement.setString( 3,beanData.getProjectname());			statement.setString( 4,beanData.getTempversion());			statement.setString( 5,beanData.getManager());			statement.setString( 6,beanData.getReferprototype());			statement.setString( 7,beanData.getDesigncapability());			statement.setString( 8,beanData.getBegintime());			statement.setString( 9,beanData.getEndtime());			statement.setString( 10,beanData.getPacking());			statement.setString( 11,beanData.getEstimatecost());			statement.setString( 12,beanData.getCurrency());			statement.setString( 13,beanData.getExchangerate());			statement.setString( 14,beanData.getSaleprice());			statement.setString( 15,beanData.getSales());			statement.setString( 16,beanData.getRecoverynum());			statement.setString( 17,beanData.getFailmode());			statement.setString( 18,beanData.getImage_filename());			statement.setString( 19,beanData.getDeptguid());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ProjectTask( id,projectid,projectname,tempversion,manager,referprototype,designcapability,begintime,endtime,packing,estimatecost,currency,exchangerate,saleprice,sales,recoverynum,failmode,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)："+ e.toString());
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
		B_ProjectTaskData beanData = (B_ProjectTaskData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_ProjectTask WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ProjectTask: "+ e.toString());
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
		B_ProjectTaskData beanData = (B_ProjectTaskData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_ProjectTask WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_ProjectTask"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ProjectTask: "+ e.toString());
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
		B_ProjectTaskData beanData = (B_ProjectTaskData) beanDataTmp; 
		B_ProjectTaskData returnData=new B_ProjectTaskData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,projectid,projectname,tempversion,manager,referprototype,designcapability,begintime,endtime,packing,estimatecost,currency,exchangerate,saleprice,sales,recoverynum,failmode,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProjectTask WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProjectid( resultSet.getString( 2));			returnData.setProjectname( resultSet.getString( 3));			returnData.setTempversion( resultSet.getString( 4));			returnData.setManager( resultSet.getString( 5));			returnData.setReferprototype( resultSet.getString( 6));			returnData.setDesigncapability( resultSet.getString( 7));			returnData.setBegintime( resultSet.getString( 8));			returnData.setEndtime( resultSet.getString( 9));			returnData.setPacking( resultSet.getString( 10));			returnData.setEstimatecost( resultSet.getString( 11));			returnData.setCurrency( resultSet.getString( 12));			returnData.setExchangerate( resultSet.getString( 13));			returnData.setSaleprice( resultSet.getString( 14));			returnData.setSales( resultSet.getString( 15));			returnData.setRecoverynum( resultSet.getString( 16));			returnData.setFailmode( resultSet.getString( 17));			returnData.setImage_filename( resultSet.getString( 18));			returnData.setDeptguid( resultSet.getString( 19));			returnData.setCreatetime( resultSet.getString( 20));			returnData.setCreateperson( resultSet.getString( 21));			returnData.setCreateunitid( resultSet.getString( 22));			returnData.setModifytime( resultSet.getString( 23));			returnData.setModifyperson( resultSet.getString( 24));			returnData.setDeleteflag( resultSet.getString( 25));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,projectid,projectname,tempversion,manager,referprototype,designcapability,begintime,endtime,packing,estimatecost,currency,exchangerate,saleprice,sales,recoverynum,failmode,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProjectTask  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,projectid,projectname,tempversion,manager,referprototype,designcapability,begintime,endtime,packing,estimatecost,currency,exchangerate,saleprice,sales,recoverynum,failmode,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProjectTask"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_ProjectTaskData returnData=new B_ProjectTaskData();
				returnData.setId( resultSet.getString( 1));				returnData.setProjectid( resultSet.getString( 2));				returnData.setProjectname( resultSet.getString( 3));				returnData.setTempversion( resultSet.getString( 4));				returnData.setManager( resultSet.getString( 5));				returnData.setReferprototype( resultSet.getString( 6));				returnData.setDesigncapability( resultSet.getString( 7));				returnData.setBegintime( resultSet.getString( 8));				returnData.setEndtime( resultSet.getString( 9));				returnData.setPacking( resultSet.getString( 10));				returnData.setEstimatecost( resultSet.getString( 11));				returnData.setCurrency( resultSet.getString( 12));				returnData.setExchangerate( resultSet.getString( 13));				returnData.setSaleprice( resultSet.getString( 14));				returnData.setSales( resultSet.getString( 15));				returnData.setRecoverynum( resultSet.getString( 16));				returnData.setFailmode( resultSet.getString( 17));				returnData.setImage_filename( resultSet.getString( 18));				returnData.setDeptguid( resultSet.getString( 19));				returnData.setCreatetime( resultSet.getString( 20));				returnData.setCreateperson( resultSet.getString( 21));				returnData.setCreateunitid( resultSet.getString( 22));				returnData.setModifytime( resultSet.getString( 23));				returnData.setModifyperson( resultSet.getString( 24));				returnData.setDeleteflag( resultSet.getString( 25));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,projectid,projectname,tempversion,manager,referprototype,designcapability,begintime,endtime,packing,estimatecost,currency,exchangerate,saleprice,sales,recoverynum,failmode,image_filename,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProjectTask" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_ProjectTask SET id= ? , projectid= ? , projectname= ? , tempversion= ? , manager= ? , referprototype= ? , designcapability= ? , begintime= ? , endtime= ? , packing= ? , estimatecost= ? , currency= ? , exchangerate= ? , saleprice= ? , sales= ? , recoverynum= ? , failmode= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProjectid());			statement.setString( 3,beanData.getProjectname());			statement.setString( 4,beanData.getTempversion());			statement.setString( 5,beanData.getManager());			statement.setString( 6,beanData.getReferprototype());			statement.setString( 7,beanData.getDesigncapability());			statement.setString( 8,beanData.getBegintime());			statement.setString( 9,beanData.getEndtime());			statement.setString( 10,beanData.getPacking());			statement.setString( 11,beanData.getEstimatecost());			statement.setString( 12,beanData.getCurrency());			statement.setString( 13,beanData.getExchangerate());			statement.setString( 14,beanData.getSaleprice());			statement.setString( 15,beanData.getSales());			statement.setString( 16,beanData.getRecoverynum());			statement.setString( 17,beanData.getFailmode());			statement.setString( 18,beanData.getImage_filename());			statement.setString( 19,beanData.getDeptguid());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());
			statement.setString( 26,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ProjectTask SET id= ? , projectid= ? , projectname= ? , tempversion= ? , manager= ? , referprototype= ? , designcapability= ? , begintime= ? , endtime= ? , packing= ? , estimatecost= ? , currency= ? , exchangerate= ? , saleprice= ? , sales= ? , recoverynum= ? , failmode= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_ProjectTask SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Projectid = " + "'" + nullString(beanData.getProjectid()) + "',");			bufSQL.append("Projectname = " + "'" + nullString(beanData.getProjectname()) + "',");			bufSQL.append("Tempversion = " + "'" + nullString(beanData.getTempversion()) + "',");			bufSQL.append("Manager = " + "'" + nullString(beanData.getManager()) + "',");			bufSQL.append("Referprototype = " + "'" + nullString(beanData.getReferprototype()) + "',");			bufSQL.append("Designcapability = " + "'" + nullString(beanData.getDesigncapability()) + "',");			bufSQL.append("Begintime = " + "'" + nullString(beanData.getBegintime()) + "',");			bufSQL.append("Endtime = " + "'" + nullString(beanData.getEndtime()) + "',");			bufSQL.append("Packing = " + "'" + nullString(beanData.getPacking()) + "',");			bufSQL.append("Estimatecost = " + "'" + nullString(beanData.getEstimatecost()) + "',");			bufSQL.append("Currency = " + "'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("Exchangerate = " + "'" + nullString(beanData.getExchangerate()) + "',");			bufSQL.append("Saleprice = " + "'" + nullString(beanData.getSaleprice()) + "',");			bufSQL.append("Sales = " + "'" + nullString(beanData.getSales()) + "',");			bufSQL.append("Recoverynum = " + "'" + nullString(beanData.getRecoverynum()) + "',");			bufSQL.append("Failmode = " + "'" + nullString(beanData.getFailmode()) + "',");			bufSQL.append("Image_filename = " + "'" + nullString(beanData.getImage_filename()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_ProjectTaskData beanData = (B_ProjectTaskData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_ProjectTask SET id= ? , projectid= ? , projectname= ? , tempversion= ? , manager= ? , referprototype= ? , designcapability= ? , begintime= ? , endtime= ? , packing= ? , estimatecost= ? , currency= ? , exchangerate= ? , saleprice= ? , sales= ? , recoverynum= ? , failmode= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProjectid());			statement.setString( 3,beanData.getProjectname());			statement.setString( 4,beanData.getTempversion());			statement.setString( 5,beanData.getManager());			statement.setString( 6,beanData.getReferprototype());			statement.setString( 7,beanData.getDesigncapability());			statement.setString( 8,beanData.getBegintime());			statement.setString( 9,beanData.getEndtime());			statement.setString( 10,beanData.getPacking());			statement.setString( 11,beanData.getEstimatecost());			statement.setString( 12,beanData.getCurrency());			statement.setString( 13,beanData.getExchangerate());			statement.setString( 14,beanData.getSaleprice());			statement.setString( 15,beanData.getSales());			statement.setString( 16,beanData.getRecoverynum());			statement.setString( 17,beanData.getFailmode());			statement.setString( 18,beanData.getImage_filename());			statement.setString( 19,beanData.getDeptguid());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());
			statement.setString( 26,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ProjectTask SET id= ? , projectid= ? , projectname= ? , tempversion= ? , manager= ? , referprototype= ? , designcapability= ? , begintime= ? , endtime= ? , packing= ? , estimatecost= ? , currency= ? , exchangerate= ? , saleprice= ? , sales= ? , recoverynum= ? , failmode= ? , image_filename= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

	/**
	 *
	 * @param beanData:主键
	 */
	public void FindPrimaryKey(Object beanDataTmp) throws Exception
	{
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
		B_ProjectTaskData beanData = (B_ProjectTaskData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM B_ProjectTask WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_ProjectTask WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
