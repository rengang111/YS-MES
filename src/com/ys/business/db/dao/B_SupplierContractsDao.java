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
public class B_SupplierContractsDao extends BaseAbstractDao
{
	public B_SupplierContractsData beanData=new B_SupplierContractsData();
	public B_SupplierContractsDao()
	{
	}
	public B_SupplierContractsDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_SupplierContractsData)FindByPrimaryKey(beanData);
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
		B_SupplierContractsData beanData = (B_SupplierContractsData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_SupplierContracts( supplierid,name,sex,duties,mobile,telephone,fax,mail,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getSupplierid());			statement.setString( 2,beanData.getName());			statement.setString( 3,beanData.getSex());			statement.setString( 4,beanData.getDuties());			statement.setString( 5,beanData.getMobile());			statement.setString( 6,beanData.getTelephone());			statement.setString( 7,beanData.getFax());			statement.setString( 8,beanData.getMail());			statement.setString( 9,beanData.getQq());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_SupplierContracts( supplierid,name,sex,duties,mobile,telephone,fax,mail,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_SupplierContracts( supplierid,name,sex,duties,mobile,telephone,fax,mail,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getName()) + "',");			bufSQL.append("'" + nullString(beanData.getSex()) + "',");			bufSQL.append("'" + nullString(beanData.getDuties()) + "',");			bufSQL.append("'" + nullString(beanData.getMobile()) + "',");			bufSQL.append("'" + nullString(beanData.getTelephone()) + "',");			bufSQL.append("'" + nullString(beanData.getFax()) + "',");			bufSQL.append("'" + nullString(beanData.getMail()) + "',");			bufSQL.append("'" + nullString(beanData.getQq()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_SupplierContractsData beanData = (B_SupplierContractsData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_SupplierContracts( supplierid,name,sex,duties,mobile,telephone,fax,mail,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getSupplierid());			statement.setString( 2,beanData.getName());			statement.setString( 3,beanData.getSex());			statement.setString( 4,beanData.getDuties());			statement.setString( 5,beanData.getMobile());			statement.setString( 6,beanData.getTelephone());			statement.setString( 7,beanData.getFax());			statement.setString( 8,beanData.getMail());			statement.setString( 9,beanData.getQq());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_SupplierContracts( supplierid,name,sex,duties,mobile,telephone,fax,mail,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_SupplierContractsData beanData = (B_SupplierContractsData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_SupplierContracts WHERE  supplierid=? AND name =?");
			statement.setString( 1,beanData.getSupplierid());
			statement.setString( 2,beanData.getName());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_SupplierContracts: "+ e.toString());
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
		B_SupplierContractsData beanData = (B_SupplierContractsData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_SupplierContracts WHERE ");
			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "'");
			bufSQL.append("Name = " + "'" + nullString(beanData.getName()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM B_SupplierContracts"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_SupplierContracts: "+ e.toString());
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
		B_SupplierContractsData beanData = (B_SupplierContractsData) beanDataTmp; 
		B_SupplierContractsData returnData=new B_SupplierContractsData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT supplierid,name,sex,duties,mobile,telephone,fax,mail,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_SupplierContracts WHERE  supplierid=? AND name =?");
			statement.setString( 1,beanData.getSupplierid());
			statement.setString( 2,beanData.getName());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setSupplierid( resultSet.getString( 1));			returnData.setName( resultSet.getString( 2));			returnData.setSex( resultSet.getString( 3));			returnData.setDuties( resultSet.getString( 4));			returnData.setMobile( resultSet.getString( 5));			returnData.setTelephone( resultSet.getString( 6));			returnData.setFax( resultSet.getString( 7));			returnData.setMail( resultSet.getString( 8));			returnData.setQq( resultSet.getString( 9));			returnData.setCreatetime( resultSet.getString( 10));			returnData.setCreateperson( resultSet.getString( 11));			returnData.setCreateunitid( resultSet.getString( 12));			returnData.setModifytime( resultSet.getString( 13));			returnData.setModifyperson( resultSet.getString( 14));			returnData.setDeleteflag( resultSet.getString( 15));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT supplierid,name,sex,duties,mobile,telephone,fax,mail,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_SupplierContracts  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT supplierid,name,sex,duties,mobile,telephone,fax,mail,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_SupplierContracts"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_SupplierContractsData returnData=new B_SupplierContractsData();
				returnData.setSupplierid( resultSet.getString( 1));				returnData.setName( resultSet.getString( 2));				returnData.setSex( resultSet.getString( 3));				returnData.setDuties( resultSet.getString( 4));				returnData.setMobile( resultSet.getString( 5));				returnData.setTelephone( resultSet.getString( 6));				returnData.setFax( resultSet.getString( 7));				returnData.setMail( resultSet.getString( 8));				returnData.setQq( resultSet.getString( 9));				returnData.setCreatetime( resultSet.getString( 10));				returnData.setCreateperson( resultSet.getString( 11));				returnData.setCreateunitid( resultSet.getString( 12));				returnData.setModifytime( resultSet.getString( 13));				returnData.setModifyperson( resultSet.getString( 14));				returnData.setDeleteflag( resultSet.getString( 15));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT supplierid,name,sex,duties,mobile,telephone,fax,mail,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_SupplierContracts" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_SupplierContracts SET supplierid= ? , name= ? , sex= ? , duties= ? , mobile= ? , telephone= ? , fax= ? , mail= ? , qq= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  supplierid = ? AND name  = ?");
			statement.setString( 1,beanData.getSupplierid());			statement.setString( 2,beanData.getName());			statement.setString( 3,beanData.getSex());			statement.setString( 4,beanData.getDuties());			statement.setString( 5,beanData.getMobile());			statement.setString( 6,beanData.getTelephone());			statement.setString( 7,beanData.getFax());			statement.setString( 8,beanData.getMail());			statement.setString( 9,beanData.getQq());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());
			statement.setString( 16,beanData.getSupplierid());
			statement.setString( 17,beanData.getName());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_SupplierContracts SET supplierid= ? , name= ? , sex= ? , duties= ? , mobile= ? , telephone= ? , fax= ? , mail= ? , qq= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  supplierid = ? AND name  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_SupplierContracts SET ");
			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Name = " + "'" + nullString(beanData.getName()) + "',");			bufSQL.append("Sex = " + "'" + nullString(beanData.getSex()) + "',");			bufSQL.append("Duties = " + "'" + nullString(beanData.getDuties()) + "',");			bufSQL.append("Mobile = " + "'" + nullString(beanData.getMobile()) + "',");			bufSQL.append("Telephone = " + "'" + nullString(beanData.getTelephone()) + "',");			bufSQL.append("Fax = " + "'" + nullString(beanData.getFax()) + "',");			bufSQL.append("Mail = " + "'" + nullString(beanData.getMail()) + "',");			bufSQL.append("Qq = " + "'" + nullString(beanData.getQq()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "'");
			bufSQL.append("Name = " + "'" + nullString(beanData.getName()) + "'");
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
		B_SupplierContractsData beanData = (B_SupplierContractsData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_SupplierContracts SET supplierid= ? , name= ? , sex= ? , duties= ? , mobile= ? , telephone= ? , fax= ? , mail= ? , qq= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  supplierid = ? AND name  = ?");
			statement.setString( 1,beanData.getSupplierid());			statement.setString( 2,beanData.getName());			statement.setString( 3,beanData.getSex());			statement.setString( 4,beanData.getDuties());			statement.setString( 5,beanData.getMobile());			statement.setString( 6,beanData.getTelephone());			statement.setString( 7,beanData.getFax());			statement.setString( 8,beanData.getMail());			statement.setString( 9,beanData.getQq());			statement.setString( 10,beanData.getCreatetime());			statement.setString( 11,beanData.getCreateperson());			statement.setString( 12,beanData.getCreateunitid());			statement.setString( 13,beanData.getModifytime());			statement.setString( 14,beanData.getModifyperson());			statement.setString( 15,beanData.getDeleteflag());
			statement.setString( 16,beanData.getSupplierid());
			statement.setString( 17,beanData.getName());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_SupplierContracts SET supplierid= ? , name= ? , sex= ? , duties= ? , mobile= ? , telephone= ? , fax= ? , mail= ? , qq= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  supplierid = ? AND name  = ?"+ e.toString());
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
		B_SupplierContractsData beanData = (B_SupplierContractsData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  supplierid,name  FROM B_SupplierContracts WHERE  supplierid=? AND name =?");
			statement.setString( 1,beanData.getSupplierid());
			statement.setString( 2,beanData.getName());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  supplierid,name  FROM B_SupplierContracts WHERE  supplierid=? AND name =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
