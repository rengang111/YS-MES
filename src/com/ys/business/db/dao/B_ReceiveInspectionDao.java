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
public class B_ReceiveInspectionDao extends BaseAbstractDao
{
	public B_ReceiveInspectionData beanData=new B_ReceiveInspectionData();
	public B_ReceiveInspectionDao()
	{
	}
	public B_ReceiveInspectionDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_ReceiveInspectionData)FindByPrimaryKey(beanData);
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
		B_ReceiveInspectionData beanData = (B_ReceiveInspectionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ReceiveInspection( recordid,inspectionid,parentid,subid,ysid,contractid,arrivalid,supplierid,checkerid,checkdate,report,memo,lossandcisposal,arrivedate,delaydays,inspecttime,waitdays,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getInspectionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivalid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getCheckerid());			statement.setString( 10,beanData.getCheckdate());			statement.setString( 11,beanData.getReport());			statement.setString( 12,beanData.getMemo());			statement.setString( 13,beanData.getLossandcisposal());			if (beanData.getArrivedate()==null || beanData.getArrivedate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getArrivedate());			}			statement.setString( 15,beanData.getDelaydays());			statement.setString( 16,beanData.getInspecttime());			statement.setString( 17,beanData.getWaitdays());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ReceiveInspection( recordid,inspectionid,parentid,subid,ysid,contractid,arrivalid,supplierid,checkerid,checkdate,report,memo,lossandcisposal,arrivedate,delaydays,inspecttime,waitdays,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_ReceiveInspection( recordid,inspectionid,parentid,subid,ysid,contractid,arrivalid,supplierid,checkerid,checkdate,report,memo,lossandcisposal,arrivedate,delaydays,inspecttime,waitdays,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getInspectionid()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("'" + nullString(beanData.getArrivalid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getCheckerid()) + "',");			bufSQL.append("'" + nullString(beanData.getCheckdate()) + "',");			bufSQL.append("'" + nullString(beanData.getReport()) + "',");			bufSQL.append("'" + nullString(beanData.getMemo()) + "',");			bufSQL.append("'" + nullString(beanData.getLossandcisposal()) + "',");			bufSQL.append("'" + nullString(beanData.getArrivedate()) + "',");			bufSQL.append("'" + nullString(beanData.getDelaydays()) + "',");			bufSQL.append("'" + nullString(beanData.getInspecttime()) + "',");			bufSQL.append("'" + nullString(beanData.getWaitdays()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_ReceiveInspectionData beanData = (B_ReceiveInspectionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ReceiveInspection( recordid,inspectionid,parentid,subid,ysid,contractid,arrivalid,supplierid,checkerid,checkdate,report,memo,lossandcisposal,arrivedate,delaydays,inspecttime,waitdays,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getInspectionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivalid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getCheckerid());			statement.setString( 10,beanData.getCheckdate());			statement.setString( 11,beanData.getReport());			statement.setString( 12,beanData.getMemo());			statement.setString( 13,beanData.getLossandcisposal());			if (beanData.getArrivedate()==null || beanData.getArrivedate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getArrivedate());			}			statement.setString( 15,beanData.getDelaydays());			statement.setString( 16,beanData.getInspecttime());			statement.setString( 17,beanData.getWaitdays());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ReceiveInspection( recordid,inspectionid,parentid,subid,ysid,contractid,arrivalid,supplierid,checkerid,checkdate,report,memo,lossandcisposal,arrivedate,delaydays,inspecttime,waitdays,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_ReceiveInspectionData beanData = (B_ReceiveInspectionData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_ReceiveInspection WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ReceiveInspection: "+ e.toString());
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
		B_ReceiveInspectionData beanData = (B_ReceiveInspectionData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_ReceiveInspection WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_ReceiveInspection"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ReceiveInspection: "+ e.toString());
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
		B_ReceiveInspectionData beanData = (B_ReceiveInspectionData) beanDataTmp; 
		B_ReceiveInspectionData returnData=new B_ReceiveInspectionData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,inspectionid,parentid,subid,ysid,contractid,arrivalid,supplierid,checkerid,checkdate,report,memo,lossandcisposal,arrivedate,delaydays,inspecttime,waitdays,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ReceiveInspection WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setInspectionid( resultSet.getString( 2));			returnData.setParentid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setYsid( resultSet.getString( 5));			returnData.setContractid( resultSet.getString( 6));			returnData.setArrivalid( resultSet.getString( 7));			returnData.setSupplierid( resultSet.getString( 8));			returnData.setCheckerid( resultSet.getString( 9));			returnData.setCheckdate( resultSet.getString( 10));			returnData.setReport( resultSet.getString( 11));			returnData.setMemo( resultSet.getString( 12));			returnData.setLossandcisposal( resultSet.getString( 13));			returnData.setArrivedate( resultSet.getString( 14));			returnData.setDelaydays( resultSet.getString( 15));			returnData.setInspecttime( resultSet.getString( 16));			returnData.setWaitdays( resultSet.getString( 17));			returnData.setDeptguid( resultSet.getString( 18));			returnData.setCreatetime( resultSet.getString( 19));			returnData.setCreateperson( resultSet.getString( 20));			returnData.setCreateunitid( resultSet.getString( 21));			returnData.setModifytime( resultSet.getString( 22));			returnData.setModifyperson( resultSet.getString( 23));			returnData.setDeleteflag( resultSet.getString( 24));			returnData.setFormid( resultSet.getString( 25));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,inspectionid,parentid,subid,ysid,contractid,arrivalid,supplierid,checkerid,checkdate,report,memo,lossandcisposal,arrivedate,delaydays,inspecttime,waitdays,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ReceiveInspection  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,inspectionid,parentid,subid,ysid,contractid,arrivalid,supplierid,checkerid,checkdate,report,memo,lossandcisposal,arrivedate,delaydays,inspecttime,waitdays,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ReceiveInspection"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_ReceiveInspectionData returnData=new B_ReceiveInspectionData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setInspectionid( resultSet.getString( 2));				returnData.setParentid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setYsid( resultSet.getString( 5));				returnData.setContractid( resultSet.getString( 6));				returnData.setArrivalid( resultSet.getString( 7));				returnData.setSupplierid( resultSet.getString( 8));				returnData.setCheckerid( resultSet.getString( 9));				returnData.setCheckdate( resultSet.getString( 10));				returnData.setReport( resultSet.getString( 11));				returnData.setMemo( resultSet.getString( 12));				returnData.setLossandcisposal( resultSet.getString( 13));				returnData.setArrivedate( resultSet.getString( 14));				returnData.setDelaydays( resultSet.getString( 15));				returnData.setInspecttime( resultSet.getString( 16));				returnData.setWaitdays( resultSet.getString( 17));				returnData.setDeptguid( resultSet.getString( 18));				returnData.setCreatetime( resultSet.getString( 19));				returnData.setCreateperson( resultSet.getString( 20));				returnData.setCreateunitid( resultSet.getString( 21));				returnData.setModifytime( resultSet.getString( 22));				returnData.setModifyperson( resultSet.getString( 23));				returnData.setDeleteflag( resultSet.getString( 24));				returnData.setFormid( resultSet.getString( 25));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,inspectionid,parentid,subid,ysid,contractid,arrivalid,supplierid,checkerid,checkdate,report,memo,lossandcisposal,arrivedate,delaydays,inspecttime,waitdays,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_ReceiveInspection" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_ReceiveInspection SET recordid= ? , inspectionid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivalid= ? , supplierid= ? , checkerid= ? , checkdate= ? , report= ? , memo= ? , lossandcisposal= ? , arrivedate= ? , delaydays= ? , inspecttime= ? , waitdays= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getInspectionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivalid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getCheckerid());			statement.setString( 10,beanData.getCheckdate());			statement.setString( 11,beanData.getReport());			statement.setString( 12,beanData.getMemo());			statement.setString( 13,beanData.getLossandcisposal());			if (beanData.getArrivedate()==null || beanData.getArrivedate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getArrivedate());			}			statement.setString( 15,beanData.getDelaydays());			statement.setString( 16,beanData.getInspecttime());			statement.setString( 17,beanData.getWaitdays());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			statement.setString( 26,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ReceiveInspection SET recordid= ? , inspectionid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivalid= ? , supplierid= ? , checkerid= ? , checkdate= ? , report= ? , memo= ? , lossandcisposal= ? , arrivedate= ? , delaydays= ? , inspecttime= ? , waitdays= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_ReceiveInspection SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Inspectionid = " + "'" + nullString(beanData.getInspectionid()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Contractid = " + "'" + nullString(beanData.getContractid()) + "',");			bufSQL.append("Arrivalid = " + "'" + nullString(beanData.getArrivalid()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Checkerid = " + "'" + nullString(beanData.getCheckerid()) + "',");			bufSQL.append("Checkdate = " + "'" + nullString(beanData.getCheckdate()) + "',");			bufSQL.append("Report = " + "'" + nullString(beanData.getReport()) + "',");			bufSQL.append("Memo = " + "'" + nullString(beanData.getMemo()) + "',");			bufSQL.append("Lossandcisposal = " + "'" + nullString(beanData.getLossandcisposal()) + "',");			bufSQL.append("Arrivedate = " + "'" + nullString(beanData.getArrivedate()) + "',");			bufSQL.append("Delaydays = " + "'" + nullString(beanData.getDelaydays()) + "',");			bufSQL.append("Inspecttime = " + "'" + nullString(beanData.getInspecttime()) + "',");			bufSQL.append("Waitdays = " + "'" + nullString(beanData.getWaitdays()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_ReceiveInspectionData beanData = (B_ReceiveInspectionData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_ReceiveInspection SET recordid= ? , inspectionid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivalid= ? , supplierid= ? , checkerid= ? , checkdate= ? , report= ? , memo= ? , lossandcisposal= ? , arrivedate= ? , delaydays= ? , inspecttime= ? , waitdays= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getInspectionid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getYsid());			statement.setString( 6,beanData.getContractid());			statement.setString( 7,beanData.getArrivalid());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getCheckerid());			statement.setString( 10,beanData.getCheckdate());			statement.setString( 11,beanData.getReport());			statement.setString( 12,beanData.getMemo());			statement.setString( 13,beanData.getLossandcisposal());			if (beanData.getArrivedate()==null || beanData.getArrivedate().trim().equals(""))			{				statement.setNull( 14, Types.DATE);			}			else			{				statement.setString( 14, beanData.getArrivedate());			}			statement.setString( 15,beanData.getDelaydays());			statement.setString( 16,beanData.getInspecttime());			statement.setString( 17,beanData.getWaitdays());			statement.setString( 18,beanData.getDeptguid());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getFormid());
			statement.setString( 26,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ReceiveInspection SET recordid= ? , inspectionid= ? , parentid= ? , subid= ? , ysid= ? , contractid= ? , arrivalid= ? , supplierid= ? , checkerid= ? , checkdate= ? , report= ? , memo= ? , lossandcisposal= ? , arrivedate= ? , delaydays= ? , inspecttime= ? , waitdays= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_ReceiveInspectionData beanData = (B_ReceiveInspectionData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_ReceiveInspection WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_ReceiveInspection WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
