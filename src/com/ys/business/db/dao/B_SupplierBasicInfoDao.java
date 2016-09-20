package com.ys.business.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.util.basedao.BaseAbstractDao;
import com.ys.business.db.data.*;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class B_SupplierBasicInfoDao extends BaseAbstractDao
{
	public B_SupplierBasicInfoData beanData=new B_SupplierBasicInfoData();
	public B_SupplierBasicInfoDao()
	{
	}
	public B_SupplierBasicInfoDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_SupplierBasicInfoData)FindByPrimaryKey(beanData);
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
		B_SupplierBasicInfoData beanData = (B_SupplierBasicInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_SupplierBasicInfo( supplierid,suppliersimpledes,supplierdes,twolevelid,twoleveliddes,paymentterm,country,province,city,address,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getSupplierid());			statement.setString( 2,beanData.getSuppliersimpledes());			statement.setString( 3,beanData.getSupplierdes());			statement.setString( 4,beanData.getTwolevelid());			statement.setString( 5,beanData.getTwoleveliddes());			statement.setString( 6,beanData.getPaymentterm());			statement.setString( 7,beanData.getCountry());			statement.setString( 8,beanData.getProvince());			statement.setString( 9,beanData.getCity());			statement.setString( 10,beanData.getAddress());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_SupplierBasicInfo( supplierid,suppliersimpledes,supplierdes,twolevelid,twoleveliddes,paymentterm,country,province,city,address,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_SupplierBasicInfo( supplierid,suppliersimpledes,supplierdes,twolevelid,twoleveliddes,paymentterm,country,province,city,address,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getSuppliersimpledes()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierdes()) + "',");			bufSQL.append("'" + nullString(beanData.getTwolevelid()) + "',");			bufSQL.append("'" + nullString(beanData.getTwoleveliddes()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentterm()) + "',");			bufSQL.append("'" + nullString(beanData.getCountry()) + "',");			bufSQL.append("'" + nullString(beanData.getProvince()) + "',");			bufSQL.append("'" + nullString(beanData.getCity()) + "',");			bufSQL.append("'" + nullString(beanData.getAddress()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_SupplierBasicInfoData beanData = (B_SupplierBasicInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_SupplierBasicInfo( supplierid,suppliersimpledes,supplierdes,twolevelid,twoleveliddes,paymentterm,country,province,city,address,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getSupplierid());			statement.setString( 2,beanData.getSuppliersimpledes());			statement.setString( 3,beanData.getSupplierdes());			statement.setString( 4,beanData.getTwolevelid());			statement.setString( 5,beanData.getTwoleveliddes());			statement.setString( 6,beanData.getPaymentterm());			statement.setString( 7,beanData.getCountry());			statement.setString( 8,beanData.getProvince());			statement.setString( 9,beanData.getCity());			statement.setString( 10,beanData.getAddress());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_SupplierBasicInfo( supplierid,suppliersimpledes,supplierdes,twolevelid,twoleveliddes,paymentterm,country,province,city,address,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_SupplierBasicInfoData beanData = (B_SupplierBasicInfoData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_SupplierBasicInfo WHERE  supplierid =?");
			statement.setString( 1,beanData.getSupplierid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_SupplierBasicInfo: "+ e.toString());
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
		B_SupplierBasicInfoData beanData = (B_SupplierBasicInfoData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_SupplierBasicInfo WHERE ");
			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM B_SupplierBasicInfo"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_SupplierBasicInfo: "+ e.toString());
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
		B_SupplierBasicInfoData beanData = (B_SupplierBasicInfoData) beanDataTmp; 
		B_SupplierBasicInfoData returnData=new B_SupplierBasicInfoData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT supplierid,suppliersimpledes,supplierdes,twolevelid,twoleveliddes,paymentterm,country,province,city,address,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_SupplierBasicInfo WHERE  supplierid =?");
			statement.setString( 1,beanData.getSupplierid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setSupplierid( resultSet.getString( 1));			returnData.setSuppliersimpledes( resultSet.getString( 2));			returnData.setSupplierdes( resultSet.getString( 3));			returnData.setTwolevelid( resultSet.getString( 4));			returnData.setTwoleveliddes( resultSet.getString( 5));			returnData.setPaymentterm( resultSet.getString( 6));			returnData.setCountry( resultSet.getString( 7));			returnData.setProvince( resultSet.getString( 8));			returnData.setCity( resultSet.getString( 9));			returnData.setAddress( resultSet.getString( 10));			returnData.setCreatetime( resultSet.getString( 11));			returnData.setCreateperson( resultSet.getString( 12));			returnData.setCreateunitid( resultSet.getString( 13));			returnData.setModifytime( resultSet.getString( 14));			returnData.setModifyperson( resultSet.getString( 15));			returnData.setDeleteflag( resultSet.getString( 16));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT supplierid,suppliersimpledes,supplierdes,twolevelid,twoleveliddes,paymentterm,country,province,city,address,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_SupplierBasicInfo  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT supplierid,suppliersimpledes,supplierdes,twolevelid,twoleveliddes,paymentterm,country,province,city,address,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_SupplierBasicInfo"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_SupplierBasicInfoData returnData=new B_SupplierBasicInfoData();
				returnData.setSupplierid( resultSet.getString( 1));				returnData.setSuppliersimpledes( resultSet.getString( 2));				returnData.setSupplierdes( resultSet.getString( 3));				returnData.setTwolevelid( resultSet.getString( 4));				returnData.setTwoleveliddes( resultSet.getString( 5));				returnData.setPaymentterm( resultSet.getString( 6));				returnData.setCountry( resultSet.getString( 7));				returnData.setProvince( resultSet.getString( 8));				returnData.setCity( resultSet.getString( 9));				returnData.setAddress( resultSet.getString( 10));				returnData.setCreatetime( resultSet.getString( 11));				returnData.setCreateperson( resultSet.getString( 12));				returnData.setCreateunitid( resultSet.getString( 13));				returnData.setModifytime( resultSet.getString( 14));				returnData.setModifyperson( resultSet.getString( 15));				returnData.setDeleteflag( resultSet.getString( 16));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT supplierid,suppliersimpledes,supplierdes,twolevelid,twoleveliddes,paymentterm,country,province,city,address,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_SupplierBasicInfo" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_SupplierBasicInfo SET supplierid= ? , suppliersimpledes= ? , supplierdes= ? , twolevelid= ? , twoleveliddes= ? , paymentterm= ? , country= ? , province= ? , city= ? , address= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  supplierid  = ?");
			statement.setString( 1,beanData.getSupplierid());			statement.setString( 2,beanData.getSuppliersimpledes());			statement.setString( 3,beanData.getSupplierdes());			statement.setString( 4,beanData.getTwolevelid());			statement.setString( 5,beanData.getTwoleveliddes());			statement.setString( 6,beanData.getPaymentterm());			statement.setString( 7,beanData.getCountry());			statement.setString( 8,beanData.getProvince());			statement.setString( 9,beanData.getCity());			statement.setString( 10,beanData.getAddress());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());
			statement.setString( 17,beanData.getSupplierid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_SupplierBasicInfo SET supplierid= ? , suppliersimpledes= ? , supplierdes= ? , twolevelid= ? , twoleveliddes= ? , paymentterm= ? , country= ? , province= ? , city= ? , address= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  supplierid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_SupplierBasicInfo SET ");
			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Suppliersimpledes = " + "'" + nullString(beanData.getSuppliersimpledes()) + "',");			bufSQL.append("Supplierdes = " + "'" + nullString(beanData.getSupplierdes()) + "',");			bufSQL.append("Twolevelid = " + "'" + nullString(beanData.getTwolevelid()) + "',");			bufSQL.append("Twoleveliddes = " + "'" + nullString(beanData.getTwoleveliddes()) + "',");			bufSQL.append("Paymentterm = " + "'" + nullString(beanData.getPaymentterm()) + "',");			bufSQL.append("Country = " + "'" + nullString(beanData.getCountry()) + "',");			bufSQL.append("Province = " + "'" + nullString(beanData.getProvince()) + "',");			bufSQL.append("City = " + "'" + nullString(beanData.getCity()) + "',");			bufSQL.append("Address = " + "'" + nullString(beanData.getAddress()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "'");
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
		B_SupplierBasicInfoData beanData = (B_SupplierBasicInfoData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_SupplierBasicInfo SET supplierid= ? , suppliersimpledes= ? , supplierdes= ? , twolevelid= ? , twoleveliddes= ? , paymentterm= ? , country= ? , province= ? , city= ? , address= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  supplierid  = ?");
			statement.setString( 1,beanData.getSupplierid());			statement.setString( 2,beanData.getSuppliersimpledes());			statement.setString( 3,beanData.getSupplierdes());			statement.setString( 4,beanData.getTwolevelid());			statement.setString( 5,beanData.getTwoleveliddes());			statement.setString( 6,beanData.getPaymentterm());			statement.setString( 7,beanData.getCountry());			statement.setString( 8,beanData.getProvince());			statement.setString( 9,beanData.getCity());			statement.setString( 10,beanData.getAddress());			statement.setString( 11,beanData.getCreatetime());			statement.setString( 12,beanData.getCreateperson());			statement.setString( 13,beanData.getCreateunitid());			statement.setString( 14,beanData.getModifytime());			statement.setString( 15,beanData.getModifyperson());			statement.setString( 16,beanData.getDeleteflag());
			statement.setString( 17,beanData.getSupplierid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_SupplierBasicInfo SET supplierid= ? , suppliersimpledes= ? , supplierdes= ? , twolevelid= ? , twoleveliddes= ? , paymentterm= ? , country= ? , province= ? , city= ? , address= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  supplierid  = ?"+ e.toString());
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
		B_SupplierBasicInfoData beanData = (B_SupplierBasicInfoData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  supplierid  FROM B_SupplierBasicInfo WHERE  supplierid =?");
			statement.setString( 1,beanData.getSupplierid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  supplierid  FROM B_SupplierBasicInfo WHERE  supplierid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
