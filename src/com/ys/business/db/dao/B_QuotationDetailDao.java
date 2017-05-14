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
public class B_QuotationDetailDao extends BaseAbstractDao
{
	public B_QuotationDetailData beanData=new B_QuotationDetailData();
	public B_QuotationDetailDao()
	{
	}
	public B_QuotationDetailDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_QuotationDetailData)FindByPrimaryKey(beanData);
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
		B_QuotationDetailData beanData = (B_QuotationDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_QuotationDetail( recordid,bomid,materialid,supplierid,productmodel,quantity,price,totalprice,sourceprice,subbomid,subbomno,subbomserial,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getBomid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getProductmodel());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getTotalprice());			statement.setString( 9,beanData.getSourceprice());			statement.setString( 10,beanData.getSubbomid());			statement.setString( 11,beanData.getSubbomno());			statement.setString( 12,beanData.getSubbomserial());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());			statement.setString( 20,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_QuotationDetail( recordid,bomid,materialid,supplierid,productmodel,quantity,price,totalprice,sourceprice,subbomid,subbomno,subbomserial,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_QuotationDetail( recordid,bomid,materialid,supplierid,productmodel,quantity,price,totalprice,sourceprice,subbomid,subbomno,subbomserial,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getBomid()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getProductmodel()) + "',");			bufSQL.append("'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("'" + nullString(beanData.getSourceprice()) + "',");			bufSQL.append("'" + nullString(beanData.getSubbomid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubbomno()) + "',");			bufSQL.append("'" + nullString(beanData.getSubbomserial()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_QuotationDetailData beanData = (B_QuotationDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_QuotationDetail( recordid,bomid,materialid,supplierid,productmodel,quantity,price,totalprice,sourceprice,subbomid,subbomno,subbomserial,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getBomid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getProductmodel());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getTotalprice());			statement.setString( 9,beanData.getSourceprice());			statement.setString( 10,beanData.getSubbomid());			statement.setString( 11,beanData.getSubbomno());			statement.setString( 12,beanData.getSubbomserial());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());			statement.setString( 20,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_QuotationDetail( recordid,bomid,materialid,supplierid,productmodel,quantity,price,totalprice,sourceprice,subbomid,subbomno,subbomserial,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_QuotationDetailData beanData = (B_QuotationDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_QuotationDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_QuotationDetail: "+ e.toString());
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
		B_QuotationDetailData beanData = (B_QuotationDetailData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_QuotationDetail WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_QuotationDetail"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_QuotationDetail: "+ e.toString());
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
		B_QuotationDetailData beanData = (B_QuotationDetailData) beanDataTmp; 
		B_QuotationDetailData returnData=new B_QuotationDetailData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,bomid,materialid,supplierid,productmodel,quantity,price,totalprice,sourceprice,subbomid,subbomno,subbomserial,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_QuotationDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setBomid( resultSet.getString( 2));			returnData.setMaterialid( resultSet.getString( 3));			returnData.setSupplierid( resultSet.getString( 4));			returnData.setProductmodel( resultSet.getString( 5));			returnData.setQuantity( resultSet.getString( 6));			returnData.setPrice( resultSet.getString( 7));			returnData.setTotalprice( resultSet.getString( 8));			returnData.setSourceprice( resultSet.getString( 9));			returnData.setSubbomid( resultSet.getString( 10));			returnData.setSubbomno( resultSet.getString( 11));			returnData.setSubbomserial( resultSet.getString( 12));			returnData.setDeptguid( resultSet.getString( 13));			returnData.setCreatetime( resultSet.getString( 14));			returnData.setCreateperson( resultSet.getString( 15));			returnData.setCreateunitid( resultSet.getString( 16));			returnData.setModifytime( resultSet.getString( 17));			returnData.setModifyperson( resultSet.getString( 18));			returnData.setDeleteflag( resultSet.getString( 19));			returnData.setFormid( resultSet.getString( 20));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,bomid,materialid,supplierid,productmodel,quantity,price,totalprice,sourceprice,subbomid,subbomno,subbomserial,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_QuotationDetail  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,bomid,materialid,supplierid,productmodel,quantity,price,totalprice,sourceprice,subbomid,subbomno,subbomserial,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_QuotationDetail"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_QuotationDetailData returnData=new B_QuotationDetailData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setBomid( resultSet.getString( 2));				returnData.setMaterialid( resultSet.getString( 3));				returnData.setSupplierid( resultSet.getString( 4));				returnData.setProductmodel( resultSet.getString( 5));				returnData.setQuantity( resultSet.getString( 6));				returnData.setPrice( resultSet.getString( 7));				returnData.setTotalprice( resultSet.getString( 8));				returnData.setSourceprice( resultSet.getString( 9));				returnData.setSubbomid( resultSet.getString( 10));				returnData.setSubbomno( resultSet.getString( 11));				returnData.setSubbomserial( resultSet.getString( 12));				returnData.setDeptguid( resultSet.getString( 13));				returnData.setCreatetime( resultSet.getString( 14));				returnData.setCreateperson( resultSet.getString( 15));				returnData.setCreateunitid( resultSet.getString( 16));				returnData.setModifytime( resultSet.getString( 17));				returnData.setModifyperson( resultSet.getString( 18));				returnData.setDeleteflag( resultSet.getString( 19));				returnData.setFormid( resultSet.getString( 20));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,bomid,materialid,supplierid,productmodel,quantity,price,totalprice,sourceprice,subbomid,subbomno,subbomserial,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_QuotationDetail" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_QuotationDetail SET recordid= ? , bomid= ? , materialid= ? , supplierid= ? , productmodel= ? , quantity= ? , price= ? , totalprice= ? , sourceprice= ? , subbomid= ? , subbomno= ? , subbomserial= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getBomid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getProductmodel());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getTotalprice());			statement.setString( 9,beanData.getSourceprice());			statement.setString( 10,beanData.getSubbomid());			statement.setString( 11,beanData.getSubbomno());			statement.setString( 12,beanData.getSubbomserial());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());			statement.setString( 20,beanData.getFormid());
			statement.setString( 21,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_QuotationDetail SET recordid= ? , bomid= ? , materialid= ? , supplierid= ? , productmodel= ? , quantity= ? , price= ? , totalprice= ? , sourceprice= ? , subbomid= ? , subbomno= ? , subbomserial= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_QuotationDetail SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Bomid = " + "'" + nullString(beanData.getBomid()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Productmodel = " + "'" + nullString(beanData.getProductmodel()) + "',");			bufSQL.append("Quantity = " + "'" + nullString(beanData.getQuantity()) + "',");			bufSQL.append("Price = " + "'" + nullString(beanData.getPrice()) + "',");			bufSQL.append("Totalprice = " + "'" + nullString(beanData.getTotalprice()) + "',");			bufSQL.append("Sourceprice = " + "'" + nullString(beanData.getSourceprice()) + "',");			bufSQL.append("Subbomid = " + "'" + nullString(beanData.getSubbomid()) + "',");			bufSQL.append("Subbomno = " + "'" + nullString(beanData.getSubbomno()) + "',");			bufSQL.append("Subbomserial = " + "'" + nullString(beanData.getSubbomserial()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_QuotationDetailData beanData = (B_QuotationDetailData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_QuotationDetail SET recordid= ? , bomid= ? , materialid= ? , supplierid= ? , productmodel= ? , quantity= ? , price= ? , totalprice= ? , sourceprice= ? , subbomid= ? , subbomno= ? , subbomserial= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getBomid());			statement.setString( 3,beanData.getMaterialid());			statement.setString( 4,beanData.getSupplierid());			statement.setString( 5,beanData.getProductmodel());			statement.setString( 6,beanData.getQuantity());			statement.setString( 7,beanData.getPrice());			statement.setString( 8,beanData.getTotalprice());			statement.setString( 9,beanData.getSourceprice());			statement.setString( 10,beanData.getSubbomid());			statement.setString( 11,beanData.getSubbomno());			statement.setString( 12,beanData.getSubbomserial());			statement.setString( 13,beanData.getDeptguid());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());			statement.setString( 20,beanData.getFormid());
			statement.setString( 21,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_QuotationDetail SET recordid= ? , bomid= ? , materialid= ? , supplierid= ? , productmodel= ? , quantity= ? , price= ? , totalprice= ? , sourceprice= ? , subbomid= ? , subbomno= ? , subbomserial= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_QuotationDetailData beanData = (B_QuotationDetailData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_QuotationDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_QuotationDetail WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
