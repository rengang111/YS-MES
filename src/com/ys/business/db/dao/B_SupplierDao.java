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
public class B_SupplierDao extends BaseAbstractDao
{
	public B_SupplierData beanData=new B_SupplierData();
	public B_SupplierDao()
	{
	}
	public B_SupplierDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_SupplierData)FindByPrimaryKey(beanData);
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
		B_SupplierData beanData = (B_SupplierData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Supplier( recordid,supplierid,shortname,suppliername,parentid,subid,categoryid,categorydes,paymentterm,country,province,city,address,zipcode,type,normaldelivery,maxdelivery,issues,purchasetype,idusedbefore,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getSupplierid());			statement.setString( 3,beanData.getShortname());			statement.setString( 4,beanData.getSuppliername());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getCategoryid());			statement.setString( 8,beanData.getCategorydes());			statement.setString( 9,beanData.getPaymentterm());			statement.setString( 10,beanData.getCountry());			statement.setString( 11,beanData.getProvince());			statement.setString( 12,beanData.getCity());			statement.setString( 13,beanData.getAddress());			statement.setString( 14,beanData.getZipcode());			statement.setString( 15,beanData.getType());			statement.setString( 16,beanData.getNormaldelivery());			statement.setString( 17,beanData.getMaxdelivery());			statement.setString( 18,beanData.getIssues());			statement.setString( 19,beanData.getPurchasetype());			statement.setString( 20,beanData.getIdusedbefore());			statement.setString( 21,beanData.getDeptguid());			statement.setString( 22,beanData.getCreatetime());			statement.setString( 23,beanData.getCreateperson());			statement.setString( 24,beanData.getCreateunitid());			statement.setString( 25,beanData.getModifytime());			statement.setString( 26,beanData.getModifyperson());			statement.setString( 27,beanData.getDeleteflag());			statement.setString( 28,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Supplier( recordid,supplierid,shortname,suppliername,parentid,subid,categoryid,categorydes,paymentterm,country,province,city,address,zipcode,type,normaldelivery,maxdelivery,issues,purchasetype,idusedbefore,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_Supplier( recordid,supplierid,shortname,suppliername,parentid,subid,categoryid,categorydes,paymentterm,country,province,city,address,zipcode,type,normaldelivery,maxdelivery,issues,purchasetype,idusedbefore,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getShortname()) + "',");			bufSQL.append("'" + nullString(beanData.getSuppliername()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getCategoryid()) + "',");			bufSQL.append("'" + nullString(beanData.getCategorydes()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentterm()) + "',");			bufSQL.append("'" + nullString(beanData.getCountry()) + "',");			bufSQL.append("'" + nullString(beanData.getProvince()) + "',");			bufSQL.append("'" + nullString(beanData.getCity()) + "',");			bufSQL.append("'" + nullString(beanData.getAddress()) + "',");			bufSQL.append("'" + nullString(beanData.getZipcode()) + "',");			bufSQL.append("'" + nullString(beanData.getType()) + "',");			bufSQL.append("'" + nullString(beanData.getNormaldelivery()) + "',");			bufSQL.append("'" + nullString(beanData.getMaxdelivery()) + "',");			bufSQL.append("'" + nullString(beanData.getIssues()) + "',");			bufSQL.append("'" + nullString(beanData.getPurchasetype()) + "',");			bufSQL.append("'" + nullString(beanData.getIdusedbefore()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_SupplierData beanData = (B_SupplierData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Supplier( recordid,supplierid,shortname,suppliername,parentid,subid,categoryid,categorydes,paymentterm,country,province,city,address,zipcode,type,normaldelivery,maxdelivery,issues,purchasetype,idusedbefore,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getSupplierid());			statement.setString( 3,beanData.getShortname());			statement.setString( 4,beanData.getSuppliername());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getCategoryid());			statement.setString( 8,beanData.getCategorydes());			statement.setString( 9,beanData.getPaymentterm());			statement.setString( 10,beanData.getCountry());			statement.setString( 11,beanData.getProvince());			statement.setString( 12,beanData.getCity());			statement.setString( 13,beanData.getAddress());			statement.setString( 14,beanData.getZipcode());			statement.setString( 15,beanData.getType());			statement.setString( 16,beanData.getNormaldelivery());			statement.setString( 17,beanData.getMaxdelivery());			statement.setString( 18,beanData.getIssues());			statement.setString( 19,beanData.getPurchasetype());			statement.setString( 20,beanData.getIdusedbefore());			statement.setString( 21,beanData.getDeptguid());			statement.setString( 22,beanData.getCreatetime());			statement.setString( 23,beanData.getCreateperson());			statement.setString( 24,beanData.getCreateunitid());			statement.setString( 25,beanData.getModifytime());			statement.setString( 26,beanData.getModifyperson());			statement.setString( 27,beanData.getDeleteflag());			statement.setString( 28,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Supplier( recordid,supplierid,shortname,suppliername,parentid,subid,categoryid,categorydes,paymentterm,country,province,city,address,zipcode,type,normaldelivery,maxdelivery,issues,purchasetype,idusedbefore,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_SupplierData beanData = (B_SupplierData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_Supplier WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Supplier: "+ e.toString());
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
		B_SupplierData beanData = (B_SupplierData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_Supplier WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_Supplier"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Supplier: "+ e.toString());
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
		B_SupplierData beanData = (B_SupplierData) beanDataTmp; 
		B_SupplierData returnData=new B_SupplierData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,supplierid,shortname,suppliername,parentid,subid,categoryid,categorydes,paymentterm,country,province,city,address,zipcode,type,normaldelivery,maxdelivery,issues,purchasetype,idusedbefore,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Supplier WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setSupplierid( resultSet.getString( 2));			returnData.setShortname( resultSet.getString( 3));			returnData.setSuppliername( resultSet.getString( 4));			returnData.setParentid( resultSet.getString( 5));			returnData.setSubid( resultSet.getString( 6));			returnData.setCategoryid( resultSet.getString( 7));			returnData.setCategorydes( resultSet.getString( 8));			returnData.setPaymentterm( resultSet.getString( 9));			returnData.setCountry( resultSet.getString( 10));			returnData.setProvince( resultSet.getString( 11));			returnData.setCity( resultSet.getString( 12));			returnData.setAddress( resultSet.getString( 13));			returnData.setZipcode( resultSet.getString( 14));			returnData.setType( resultSet.getString( 15));			returnData.setNormaldelivery( resultSet.getString( 16));			returnData.setMaxdelivery( resultSet.getString( 17));			returnData.setIssues( resultSet.getString( 18));			returnData.setPurchasetype( resultSet.getString( 19));			returnData.setIdusedbefore( resultSet.getString( 20));			returnData.setDeptguid( resultSet.getString( 21));			returnData.setCreatetime( resultSet.getString( 22));			returnData.setCreateperson( resultSet.getString( 23));			returnData.setCreateunitid( resultSet.getString( 24));			returnData.setModifytime( resultSet.getString( 25));			returnData.setModifyperson( resultSet.getString( 26));			returnData.setDeleteflag( resultSet.getString( 27));			returnData.setFormid( resultSet.getString( 28));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,supplierid,shortname,suppliername,parentid,subid,categoryid,categorydes,paymentterm,country,province,city,address,zipcode,type,normaldelivery,maxdelivery,issues,purchasetype,idusedbefore,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Supplier  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,supplierid,shortname,suppliername,parentid,subid,categoryid,categorydes,paymentterm,country,province,city,address,zipcode,type,normaldelivery,maxdelivery,issues,purchasetype,idusedbefore,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Supplier"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_SupplierData returnData=new B_SupplierData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setSupplierid( resultSet.getString( 2));				returnData.setShortname( resultSet.getString( 3));				returnData.setSuppliername( resultSet.getString( 4));				returnData.setParentid( resultSet.getString( 5));				returnData.setSubid( resultSet.getString( 6));				returnData.setCategoryid( resultSet.getString( 7));				returnData.setCategorydes( resultSet.getString( 8));				returnData.setPaymentterm( resultSet.getString( 9));				returnData.setCountry( resultSet.getString( 10));				returnData.setProvince( resultSet.getString( 11));				returnData.setCity( resultSet.getString( 12));				returnData.setAddress( resultSet.getString( 13));				returnData.setZipcode( resultSet.getString( 14));				returnData.setType( resultSet.getString( 15));				returnData.setNormaldelivery( resultSet.getString( 16));				returnData.setMaxdelivery( resultSet.getString( 17));				returnData.setIssues( resultSet.getString( 18));				returnData.setPurchasetype( resultSet.getString( 19));				returnData.setIdusedbefore( resultSet.getString( 20));				returnData.setDeptguid( resultSet.getString( 21));				returnData.setCreatetime( resultSet.getString( 22));				returnData.setCreateperson( resultSet.getString( 23));				returnData.setCreateunitid( resultSet.getString( 24));				returnData.setModifytime( resultSet.getString( 25));				returnData.setModifyperson( resultSet.getString( 26));				returnData.setDeleteflag( resultSet.getString( 27));				returnData.setFormid( resultSet.getString( 28));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,supplierid,shortname,suppliername,parentid,subid,categoryid,categorydes,paymentterm,country,province,city,address,zipcode,type,normaldelivery,maxdelivery,issues,purchasetype,idusedbefore,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Supplier" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_Supplier SET recordid= ? , supplierid= ? , shortname= ? , suppliername= ? , parentid= ? , subid= ? , categoryid= ? , categorydes= ? , paymentterm= ? , country= ? , province= ? , city= ? , address= ? , zipcode= ? , type= ? , normaldelivery= ? , maxdelivery= ? , issues= ? , purchasetype= ? , idusedbefore= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getSupplierid());			statement.setString( 3,beanData.getShortname());			statement.setString( 4,beanData.getSuppliername());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getCategoryid());			statement.setString( 8,beanData.getCategorydes());			statement.setString( 9,beanData.getPaymentterm());			statement.setString( 10,beanData.getCountry());			statement.setString( 11,beanData.getProvince());			statement.setString( 12,beanData.getCity());			statement.setString( 13,beanData.getAddress());			statement.setString( 14,beanData.getZipcode());			statement.setString( 15,beanData.getType());			statement.setString( 16,beanData.getNormaldelivery());			statement.setString( 17,beanData.getMaxdelivery());			statement.setString( 18,beanData.getIssues());			statement.setString( 19,beanData.getPurchasetype());			statement.setString( 20,beanData.getIdusedbefore());			statement.setString( 21,beanData.getDeptguid());			statement.setString( 22,beanData.getCreatetime());			statement.setString( 23,beanData.getCreateperson());			statement.setString( 24,beanData.getCreateunitid());			statement.setString( 25,beanData.getModifytime());			statement.setString( 26,beanData.getModifyperson());			statement.setString( 27,beanData.getDeleteflag());			statement.setString( 28,beanData.getFormid());
			statement.setString( 29,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Supplier SET recordid= ? , supplierid= ? , shortname= ? , suppliername= ? , parentid= ? , subid= ? , categoryid= ? , categorydes= ? , paymentterm= ? , country= ? , province= ? , city= ? , address= ? , zipcode= ? , type= ? , normaldelivery= ? , maxdelivery= ? , issues= ? , purchasetype= ? , idusedbefore= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_Supplier SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Shortname = " + "'" + nullString(beanData.getShortname()) + "',");			bufSQL.append("Suppliername = " + "'" + nullString(beanData.getSuppliername()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Categoryid = " + "'" + nullString(beanData.getCategoryid()) + "',");			bufSQL.append("Categorydes = " + "'" + nullString(beanData.getCategorydes()) + "',");			bufSQL.append("Paymentterm = " + "'" + nullString(beanData.getPaymentterm()) + "',");			bufSQL.append("Country = " + "'" + nullString(beanData.getCountry()) + "',");			bufSQL.append("Province = " + "'" + nullString(beanData.getProvince()) + "',");			bufSQL.append("City = " + "'" + nullString(beanData.getCity()) + "',");			bufSQL.append("Address = " + "'" + nullString(beanData.getAddress()) + "',");			bufSQL.append("Zipcode = " + "'" + nullString(beanData.getZipcode()) + "',");			bufSQL.append("Type = " + "'" + nullString(beanData.getType()) + "',");			bufSQL.append("Normaldelivery = " + "'" + nullString(beanData.getNormaldelivery()) + "',");			bufSQL.append("Maxdelivery = " + "'" + nullString(beanData.getMaxdelivery()) + "',");			bufSQL.append("Issues = " + "'" + nullString(beanData.getIssues()) + "',");			bufSQL.append("Purchasetype = " + "'" + nullString(beanData.getPurchasetype()) + "',");			bufSQL.append("Idusedbefore = " + "'" + nullString(beanData.getIdusedbefore()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_SupplierData beanData = (B_SupplierData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_Supplier SET recordid= ? , supplierid= ? , shortname= ? , suppliername= ? , parentid= ? , subid= ? , categoryid= ? , categorydes= ? , paymentterm= ? , country= ? , province= ? , city= ? , address= ? , zipcode= ? , type= ? , normaldelivery= ? , maxdelivery= ? , issues= ? , purchasetype= ? , idusedbefore= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getSupplierid());			statement.setString( 3,beanData.getShortname());			statement.setString( 4,beanData.getSuppliername());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getSubid());			statement.setString( 7,beanData.getCategoryid());			statement.setString( 8,beanData.getCategorydes());			statement.setString( 9,beanData.getPaymentterm());			statement.setString( 10,beanData.getCountry());			statement.setString( 11,beanData.getProvince());			statement.setString( 12,beanData.getCity());			statement.setString( 13,beanData.getAddress());			statement.setString( 14,beanData.getZipcode());			statement.setString( 15,beanData.getType());			statement.setString( 16,beanData.getNormaldelivery());			statement.setString( 17,beanData.getMaxdelivery());			statement.setString( 18,beanData.getIssues());			statement.setString( 19,beanData.getPurchasetype());			statement.setString( 20,beanData.getIdusedbefore());			statement.setString( 21,beanData.getDeptguid());			statement.setString( 22,beanData.getCreatetime());			statement.setString( 23,beanData.getCreateperson());			statement.setString( 24,beanData.getCreateunitid());			statement.setString( 25,beanData.getModifytime());			statement.setString( 26,beanData.getModifyperson());			statement.setString( 27,beanData.getDeleteflag());			statement.setString( 28,beanData.getFormid());
			statement.setString( 29,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Supplier SET recordid= ? , supplierid= ? , shortname= ? , suppliername= ? , parentid= ? , subid= ? , categoryid= ? , categorydes= ? , paymentterm= ? , country= ? , province= ? , city= ? , address= ? , zipcode= ? , type= ? , normaldelivery= ? , maxdelivery= ? , issues= ? , purchasetype= ? , idusedbefore= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_SupplierData beanData = (B_SupplierData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_Supplier WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_Supplier WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
