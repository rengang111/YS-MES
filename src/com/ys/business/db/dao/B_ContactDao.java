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
public class B_ContactDao extends BaseAbstractDao
{
	public B_ContactData beanData=new B_ContactData();
	public B_ContactDao()
	{
	}
	public B_ContactDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_ContactData)FindByPrimaryKey(beanData);
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
		B_ContactData beanData = (B_ContactData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Contact( id,companycode,username,sex,position,department,mobile,phone,fax,mail,skype,mark,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCompanycode());			statement.setString( 3,beanData.getUsername());			statement.setString( 4,beanData.getSex());			statement.setString( 5,beanData.getPosition());			statement.setString( 6,beanData.getDepartment());			statement.setString( 7,beanData.getMobile());			statement.setString( 8,beanData.getPhone());			statement.setString( 9,beanData.getFax());			statement.setString( 10,beanData.getMail());			statement.setString( 11,beanData.getSkype());			statement.setString( 12,beanData.getMark());			statement.setString( 13,beanData.getQq());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Contact( id,companycode,username,sex,position,department,mobile,phone,fax,mail,skype,mark,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_Contact( id,companycode,username,sex,position,department,mobile,phone,fax,mail,skype,mark,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getCompanycode()) + "',");			bufSQL.append("'" + nullString(beanData.getUsername()) + "',");			bufSQL.append("'" + nullString(beanData.getSex()) + "',");			bufSQL.append("'" + nullString(beanData.getPosition()) + "',");			bufSQL.append("'" + nullString(beanData.getDepartment()) + "',");			bufSQL.append("'" + nullString(beanData.getMobile()) + "',");			bufSQL.append("'" + nullString(beanData.getPhone()) + "',");			bufSQL.append("'" + nullString(beanData.getFax()) + "',");			bufSQL.append("'" + nullString(beanData.getMail()) + "',");			bufSQL.append("'" + nullString(beanData.getSkype()) + "',");			bufSQL.append("'" + nullString(beanData.getMark()) + "',");			bufSQL.append("'" + nullString(beanData.getQq()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_ContactData beanData = (B_ContactData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Contact( id,companycode,username,sex,position,department,mobile,phone,fax,mail,skype,mark,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCompanycode());			statement.setString( 3,beanData.getUsername());			statement.setString( 4,beanData.getSex());			statement.setString( 5,beanData.getPosition());			statement.setString( 6,beanData.getDepartment());			statement.setString( 7,beanData.getMobile());			statement.setString( 8,beanData.getPhone());			statement.setString( 9,beanData.getFax());			statement.setString( 10,beanData.getMail());			statement.setString( 11,beanData.getSkype());			statement.setString( 12,beanData.getMark());			statement.setString( 13,beanData.getQq());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Contact( id,companycode,username,sex,position,department,mobile,phone,fax,mail,skype,mark,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_ContactData beanData = (B_ContactData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_Contact WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Contact: "+ e.toString());
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
		B_ContactData beanData = (B_ContactData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_Contact WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_Contact"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Contact: "+ e.toString());
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
		B_ContactData beanData = (B_ContactData) beanDataTmp; 
		B_ContactData returnData=new B_ContactData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,companycode,username,sex,position,department,mobile,phone,fax,mail,skype,mark,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_Contact WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setCompanycode( resultSet.getString( 2));			returnData.setUsername( resultSet.getString( 3));			returnData.setSex( resultSet.getString( 4));			returnData.setPosition( resultSet.getString( 5));			returnData.setDepartment( resultSet.getString( 6));			returnData.setMobile( resultSet.getString( 7));			returnData.setPhone( resultSet.getString( 8));			returnData.setFax( resultSet.getString( 9));			returnData.setMail( resultSet.getString( 10));			returnData.setSkype( resultSet.getString( 11));			returnData.setMark( resultSet.getString( 12));			returnData.setQq( resultSet.getString( 13));			returnData.setCreatetime( resultSet.getString( 14));			returnData.setCreateperson( resultSet.getString( 15));			returnData.setCreateunitid( resultSet.getString( 16));			returnData.setModifytime( resultSet.getString( 17));			returnData.setModifyperson( resultSet.getString( 18));			returnData.setDeleteflag( resultSet.getString( 19));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,companycode,username,sex,position,department,mobile,phone,fax,mail,skype,mark,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_Contact  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,companycode,username,sex,position,department,mobile,phone,fax,mail,skype,mark,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_Contact"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_ContactData returnData=new B_ContactData();
				returnData.setId( resultSet.getString( 1));				returnData.setCompanycode( resultSet.getString( 2));				returnData.setUsername( resultSet.getString( 3));				returnData.setSex( resultSet.getString( 4));				returnData.setPosition( resultSet.getString( 5));				returnData.setDepartment( resultSet.getString( 6));				returnData.setMobile( resultSet.getString( 7));				returnData.setPhone( resultSet.getString( 8));				returnData.setFax( resultSet.getString( 9));				returnData.setMail( resultSet.getString( 10));				returnData.setSkype( resultSet.getString( 11));				returnData.setMark( resultSet.getString( 12));				returnData.setQq( resultSet.getString( 13));				returnData.setCreatetime( resultSet.getString( 14));				returnData.setCreateperson( resultSet.getString( 15));				returnData.setCreateunitid( resultSet.getString( 16));				returnData.setModifytime( resultSet.getString( 17));				returnData.setModifyperson( resultSet.getString( 18));				returnData.setDeleteflag( resultSet.getString( 19));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,companycode,username,sex,position,department,mobile,phone,fax,mail,skype,mark,qq,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_Contact" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_Contact SET id= ? , companycode= ? , username= ? , sex= ? , position= ? , department= ? , mobile= ? , phone= ? , fax= ? , mail= ? , skype= ? , mark= ? , qq= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCompanycode());			statement.setString( 3,beanData.getUsername());			statement.setString( 4,beanData.getSex());			statement.setString( 5,beanData.getPosition());			statement.setString( 6,beanData.getDepartment());			statement.setString( 7,beanData.getMobile());			statement.setString( 8,beanData.getPhone());			statement.setString( 9,beanData.getFax());			statement.setString( 10,beanData.getMail());			statement.setString( 11,beanData.getSkype());			statement.setString( 12,beanData.getMark());			statement.setString( 13,beanData.getQq());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());
			statement.setString( 20,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Contact SET id= ? , companycode= ? , username= ? , sex= ? , position= ? , department= ? , mobile= ? , phone= ? , fax= ? , mail= ? , skype= ? , mark= ? , qq= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_Contact SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Companycode = " + "'" + nullString(beanData.getCompanycode()) + "',");			bufSQL.append("Username = " + "'" + nullString(beanData.getUsername()) + "',");			bufSQL.append("Sex = " + "'" + nullString(beanData.getSex()) + "',");			bufSQL.append("Position = " + "'" + nullString(beanData.getPosition()) + "',");			bufSQL.append("Department = " + "'" + nullString(beanData.getDepartment()) + "',");			bufSQL.append("Mobile = " + "'" + nullString(beanData.getMobile()) + "',");			bufSQL.append("Phone = " + "'" + nullString(beanData.getPhone()) + "',");			bufSQL.append("Fax = " + "'" + nullString(beanData.getFax()) + "',");			bufSQL.append("Mail = " + "'" + nullString(beanData.getMail()) + "',");			bufSQL.append("Skype = " + "'" + nullString(beanData.getSkype()) + "',");			bufSQL.append("Mark = " + "'" + nullString(beanData.getMark()) + "',");			bufSQL.append("Qq = " + "'" + nullString(beanData.getQq()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_ContactData beanData = (B_ContactData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_Contact SET id= ? , companycode= ? , username= ? , sex= ? , position= ? , department= ? , mobile= ? , phone= ? , fax= ? , mail= ? , skype= ? , mark= ? , qq= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getCompanycode());			statement.setString( 3,beanData.getUsername());			statement.setString( 4,beanData.getSex());			statement.setString( 5,beanData.getPosition());			statement.setString( 6,beanData.getDepartment());			statement.setString( 7,beanData.getMobile());			statement.setString( 8,beanData.getPhone());			statement.setString( 9,beanData.getFax());			statement.setString( 10,beanData.getMail());			statement.setString( 11,beanData.getSkype());			statement.setString( 12,beanData.getMark());			statement.setString( 13,beanData.getQq());			statement.setString( 14,beanData.getCreatetime());			statement.setString( 15,beanData.getCreateperson());			statement.setString( 16,beanData.getCreateunitid());			statement.setString( 17,beanData.getModifytime());			statement.setString( 18,beanData.getModifyperson());			statement.setString( 19,beanData.getDeleteflag());
			statement.setString( 20,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Contact SET id= ? , companycode= ? , username= ? , sex= ? , position= ? , department= ? , mobile= ? , phone= ? , fax= ? , mail= ? , skype= ? , mark= ? , qq= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  id  = ?"+ e.toString());
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
		B_ContactData beanData = (B_ContactData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM B_Contact WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM B_Contact WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
