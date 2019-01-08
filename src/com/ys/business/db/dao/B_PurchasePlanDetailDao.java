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
public class B_PurchasePlanDetailDao extends BaseAbstractDao
{
	public B_PurchasePlanDetailData beanData=new B_PurchasePlanDetailData();
	public B_PurchasePlanDetailDao()
	{
	}
	public B_PurchasePlanDetailDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_PurchasePlanDetailData)FindByPrimaryKey(beanData);
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
		B_PurchasePlanDetailData beanData = (B_PurchasePlanDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchasePlanDetail( recordid,purchaseid,ysid,subid,materialid,purchasetype,unitquantity,supplierid,suppliershortname,price,pricestatus,manufacturequantity,purchasequantity,totalrequisition,totalprice,subbomno,version,contractflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPurchaseid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getPurchasetype());			statement.setString( 7,beanData.getUnitquantity());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getSuppliershortname());			statement.setString( 10,beanData.getPrice());			statement.setString( 11,beanData.getPricestatus());			statement.setString( 12,beanData.getManufacturequantity());			statement.setString( 13,beanData.getPurchasequantity());			statement.setString( 14,beanData.getTotalrequisition());			statement.setString( 15,beanData.getTotalprice());			statement.setString( 16,beanData.getSubbomno());			statement.setInt( 17,beanData.getVersion());			statement.setInt( 18,beanData.getContractflag());			statement.setString( 19,beanData.getDeptguid());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());			statement.setString( 26,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchasePlanDetail( recordid,purchaseid,ysid,subid,materialid,purchasetype,unitquantity,supplierid,suppliershortname,price,pricestatus,manufacturequantity,purchasequantity,totalrequisition,totalprice,subbomno,version,contractflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_PurchasePlanDetail( recordid,purchaseid,ysid,subid,materialid,purchasetype,unitquantity,supplierid,suppliershortname,price,pricestatus,manufacturequantity,purchasequantity,totalrequisition,totalprice,subbomno,version,contractflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getPurchaseid()) + "',");			bufSQL.append("'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getPurchasetype()) + "',");			bufSQL.append("'" + nullString(beanData.getUnitquantity()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getSuppliershortname()) + "',");			bufSQL.append("'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("'" + nullString(beanData.getPricestatus()) + "',");			bufSQL.append("'" + nullString(beanData.getManufacturequantity()) + "',");			bufSQL.append("'" + nullString(beanData.getPurchasequantity()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalrequisition()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("'" + nullString(beanData.getSubbomno()) + "',");			bufSQL.append(beanData.getVersion() + ",");			bufSQL.append(beanData.getContractflag() + ",");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchasePlanDetailData beanData = (B_PurchasePlanDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_PurchasePlanDetail( recordid,purchaseid,ysid,subid,materialid,purchasetype,unitquantity,supplierid,suppliershortname,price,pricestatus,manufacturequantity,purchasequantity,totalrequisition,totalprice,subbomno,version,contractflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPurchaseid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getPurchasetype());			statement.setString( 7,beanData.getUnitquantity());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getSuppliershortname());			statement.setString( 10,beanData.getPrice());			statement.setString( 11,beanData.getPricestatus());			statement.setString( 12,beanData.getManufacturequantity());			statement.setString( 13,beanData.getPurchasequantity());			statement.setString( 14,beanData.getTotalrequisition());			statement.setString( 15,beanData.getTotalprice());			statement.setString( 16,beanData.getSubbomno());			statement.setInt( 17,beanData.getVersion());			statement.setInt( 18,beanData.getContractflag());			statement.setString( 19,beanData.getDeptguid());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());			statement.setString( 26,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_PurchasePlanDetail( recordid,purchaseid,ysid,subid,materialid,purchasetype,unitquantity,supplierid,suppliershortname,price,pricestatus,manufacturequantity,purchasequantity,totalrequisition,totalprice,subbomno,version,contractflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_PurchasePlanDetailData beanData = (B_PurchasePlanDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_PurchasePlanDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchasePlanDetail: "+ e.toString());
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
		B_PurchasePlanDetailData beanData = (B_PurchasePlanDetailData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_PurchasePlanDetail WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_PurchasePlanDetail"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_PurchasePlanDetail: "+ e.toString());
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
		B_PurchasePlanDetailData beanData = (B_PurchasePlanDetailData) beanDataTmp; 
		B_PurchasePlanDetailData returnData=new B_PurchasePlanDetailData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,purchaseid,ysid,subid,materialid,purchasetype,unitquantity,supplierid,suppliershortname,price,pricestatus,manufacturequantity,purchasequantity,totalrequisition,totalprice,subbomno,version,contractflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchasePlanDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setPurchaseid( resultSet.getString( 2));			returnData.setYsid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setMaterialid( resultSet.getString( 5));			returnData.setPurchasetype( resultSet.getString( 6));			returnData.setUnitquantity( resultSet.getString( 7));			returnData.setSupplierid( resultSet.getString( 8));			returnData.setSuppliershortname( resultSet.getString( 9));			returnData.setPrice( resultSet.getString( 10));			returnData.setPricestatus( resultSet.getString( 11));			returnData.setManufacturequantity( resultSet.getString( 12));			returnData.setPurchasequantity( resultSet.getString( 13));			returnData.setTotalrequisition( resultSet.getString( 14));			returnData.setTotalprice( resultSet.getString( 15));			returnData.setSubbomno( resultSet.getString( 16));			returnData.setVersion( resultSet.getInt( 17));			returnData.setContractflag( resultSet.getInt( 18));			returnData.setDeptguid( resultSet.getString( 19));			returnData.setCreatetime( resultSet.getString( 20));			returnData.setCreateperson( resultSet.getString( 21));			returnData.setCreateunitid( resultSet.getString( 22));			returnData.setModifytime( resultSet.getString( 23));			returnData.setModifyperson( resultSet.getString( 24));			returnData.setDeleteflag( resultSet.getString( 25));			returnData.setFormid( resultSet.getString( 26));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,purchaseid,ysid,subid,materialid,purchasetype,unitquantity,supplierid,suppliershortname,price,pricestatus,manufacturequantity,purchasequantity,totalrequisition,totalprice,subbomno,version,contractflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchasePlanDetail  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,purchaseid,ysid,subid,materialid,purchasetype,unitquantity,supplierid,suppliershortname,price,pricestatus,manufacturequantity,purchasequantity,totalrequisition,totalprice,subbomno,version,contractflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchasePlanDetail"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_PurchasePlanDetailData returnData=new B_PurchasePlanDetailData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setPurchaseid( resultSet.getString( 2));				returnData.setYsid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setMaterialid( resultSet.getString( 5));				returnData.setPurchasetype( resultSet.getString( 6));				returnData.setUnitquantity( resultSet.getString( 7));				returnData.setSupplierid( resultSet.getString( 8));				returnData.setSuppliershortname( resultSet.getString( 9));				returnData.setPrice( resultSet.getString( 10));				returnData.setPricestatus( resultSet.getString( 11));				returnData.setManufacturequantity( resultSet.getString( 12));				returnData.setPurchasequantity( resultSet.getString( 13));				returnData.setTotalrequisition( resultSet.getString( 14));				returnData.setTotalprice( resultSet.getString( 15));				returnData.setSubbomno( resultSet.getString( 16));				returnData.setVersion( resultSet.getInt( 17));				returnData.setContractflag( resultSet.getInt( 18));				returnData.setDeptguid( resultSet.getString( 19));				returnData.setCreatetime( resultSet.getString( 20));				returnData.setCreateperson( resultSet.getString( 21));				returnData.setCreateunitid( resultSet.getString( 22));				returnData.setModifytime( resultSet.getString( 23));				returnData.setModifyperson( resultSet.getString( 24));				returnData.setDeleteflag( resultSet.getString( 25));				returnData.setFormid( resultSet.getString( 26));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,purchaseid,ysid,subid,materialid,purchasetype,unitquantity,supplierid,suppliershortname,price,pricestatus,manufacturequantity,purchasequantity,totalrequisition,totalprice,subbomno,version,contractflag,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_PurchasePlanDetail" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_PurchasePlanDetail SET recordid= ? , purchaseid= ? , ysid= ? , subid= ? , materialid= ? , purchasetype= ? , unitquantity= ? , supplierid= ? , suppliershortname= ? , price= ? , pricestatus= ? , manufacturequantity= ? , purchasequantity= ? , totalrequisition= ? , totalprice= ? , subbomno= ? , version= ? , contractflag= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPurchaseid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getPurchasetype());			statement.setString( 7,beanData.getUnitquantity());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getSuppliershortname());			statement.setString( 10,beanData.getPrice());			statement.setString( 11,beanData.getPricestatus());			statement.setString( 12,beanData.getManufacturequantity());			statement.setString( 13,beanData.getPurchasequantity());			statement.setString( 14,beanData.getTotalrequisition());			statement.setString( 15,beanData.getTotalprice());			statement.setString( 16,beanData.getSubbomno());			statement.setInt( 17,beanData.getVersion());			statement.setInt( 18,beanData.getContractflag());			statement.setString( 19,beanData.getDeptguid());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());			statement.setString( 26,beanData.getFormid());
			statement.setString( 27,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchasePlanDetail SET recordid= ? , purchaseid= ? , ysid= ? , subid= ? , materialid= ? , purchasetype= ? , unitquantity= ? , supplierid= ? , suppliershortname= ? , price= ? , pricestatus= ? , manufacturequantity= ? , purchasequantity= ? , totalrequisition= ? , totalprice= ? , subbomno= ? , version= ? , contractflag= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_PurchasePlanDetail SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Purchaseid = " + "'" + nullString(beanData.getPurchaseid()) + "',");			bufSQL.append("Ysid = " + "'" + nullString(beanData.getYsid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Purchasetype = " + "'" + nullString(beanData.getPurchasetype()) + "',");			bufSQL.append("Unitquantity = " + "'" + nullString(beanData.getUnitquantity()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Suppliershortname = " + "'" + nullString(beanData.getSuppliershortname()) + "',");			bufSQL.append("Price = " + "'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("Pricestatus = " + "'" + nullString(beanData.getPricestatus()) + "',");			bufSQL.append("Manufacturequantity = " + "'" + nullString(beanData.getManufacturequantity()) + "',");			bufSQL.append("Purchasequantity = " + "'" + nullString(beanData.getPurchasequantity()) + "',");			bufSQL.append("Totalrequisition = " + "'" + nullString(beanData.getTotalrequisition()) + "',");			bufSQL.append("Totalprice = " + "'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("Subbomno = " + "'" + nullString(beanData.getSubbomno()) + "',");			bufSQL.append("Version = " + beanData.getVersion() + ",");			bufSQL.append("Contractflag = " + beanData.getContractflag() + ",");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_PurchasePlanDetailData beanData = (B_PurchasePlanDetailData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_PurchasePlanDetail SET recordid= ? , purchaseid= ? , ysid= ? , subid= ? , materialid= ? , purchasetype= ? , unitquantity= ? , supplierid= ? , suppliershortname= ? , price= ? , pricestatus= ? , manufacturequantity= ? , purchasequantity= ? , totalrequisition= ? , totalprice= ? , subbomno= ? , version= ? , contractflag= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPurchaseid());			statement.setString( 3,beanData.getYsid());			statement.setString( 4,beanData.getSubid());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getPurchasetype());			statement.setString( 7,beanData.getUnitquantity());			statement.setString( 8,beanData.getSupplierid());			statement.setString( 9,beanData.getSuppliershortname());			statement.setString( 10,beanData.getPrice());			statement.setString( 11,beanData.getPricestatus());			statement.setString( 12,beanData.getManufacturequantity());			statement.setString( 13,beanData.getPurchasequantity());			statement.setString( 14,beanData.getTotalrequisition());			statement.setString( 15,beanData.getTotalprice());			statement.setString( 16,beanData.getSubbomno());			statement.setInt( 17,beanData.getVersion());			statement.setInt( 18,beanData.getContractflag());			statement.setString( 19,beanData.getDeptguid());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());			statement.setString( 26,beanData.getFormid());
			statement.setString( 27,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_PurchasePlanDetail SET recordid= ? , purchaseid= ? , ysid= ? , subid= ? , materialid= ? , purchasetype= ? , unitquantity= ? , supplierid= ? , suppliershortname= ? , price= ? , pricestatus= ? , manufacturequantity= ? , purchasequantity= ? , totalrequisition= ? , totalprice= ? , subbomno= ? , version= ? , contractflag= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_PurchasePlanDetailData beanData = (B_PurchasePlanDetailData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_PurchasePlanDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_PurchasePlanDetail WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
