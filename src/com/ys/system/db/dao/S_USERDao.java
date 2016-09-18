package com.ys.system.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.util.basedao.BaseAbstractDao;
import com.ys.system.db.data.*;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class S_USERDao extends BaseAbstractDao
{
	public S_USERData beanData=new S_USERData();
	public S_USERDao()
	{
	}
	public S_USERDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_USERData)FindByPrimaryKey(beanData);
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
		S_USERData beanData = (S_USERData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_USER( userid,loginid,loginname,jianpin,unitid,loginpwd,proxyunitid,sex,duty,addresscode,addressuser,postcode,telphone,handphone,email,pwdquestion1,pwdquestion2,workid,photo,lockflag,enableflag,enablestarttime,enableendtime,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getUserid());			statement.setString( 2,beanData.getLoginid());			statement.setString( 3,beanData.getLoginname());			statement.setString( 4,beanData.getJianpin());			statement.setString( 5,beanData.getUnitid());			statement.setString( 6,beanData.getLoginpwd());			statement.setString( 7,beanData.getProxyunitid());			statement.setString( 8,beanData.getSex());			statement.setString( 9,beanData.getDuty());			statement.setString( 10,beanData.getAddresscode());			statement.setString( 11,beanData.getAddressuser());			statement.setString( 12,beanData.getPostcode());			statement.setString( 13,beanData.getTelphone());			statement.setString( 14,beanData.getHandphone());			statement.setString( 15,beanData.getEmail());			statement.setString( 16,beanData.getPwdquestion1());			statement.setString( 17,beanData.getPwdquestion2());			statement.setString( 18,beanData.getWorkid());			statement.setBinaryStream( 19,beanData.getPhotoStream());			statement.setString( 20,beanData.getLockflag());			statement.setString( 21,beanData.getEnableflag());			statement.setString( 22,beanData.getEnablestarttime());			statement.setString( 23,beanData.getEnableendtime());			if (beanData.getSortno()== null)			{				statement.setNull( 24, Types.INTEGER);			}			else			{				statement.setInt( 24, beanData.getSortno().intValue());			}			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getDeptguid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_USER( userid,loginid,loginname,jianpin,unitid,loginpwd,proxyunitid,sex,duty,addresscode,addressuser,postcode,telphone,handphone,email,pwdquestion1,pwdquestion2,workid,photo,lockflag,enableflag,enablestarttime,enableendtime,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_USER( userid,loginid,loginname,jianpin,unitid,loginpwd,proxyunitid,sex,duty,addresscode,addressuser,postcode,telphone,handphone,email,pwdquestion1,pwdquestion2,workid,photo,lockflag,enableflag,enablestarttime,enableendtime,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getUserid()) + "',");			bufSQL.append("'" + nullString(beanData.getLoginid()) + "',");			bufSQL.append("'" + nullString(beanData.getLoginname()) + "',");			bufSQL.append("'" + nullString(beanData.getJianpin()) + "',");			bufSQL.append("'" + nullString(beanData.getUnitid()) + "',");			bufSQL.append("'" + nullString(beanData.getLoginpwd()) + "',");			bufSQL.append("'" + nullString(beanData.getProxyunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getSex()) + "',");			bufSQL.append("'" + nullString(beanData.getDuty()) + "',");			bufSQL.append("'" + nullString(beanData.getAddresscode()) + "',");			bufSQL.append("'" + nullString(beanData.getAddressuser()) + "',");			bufSQL.append("'" + nullString(beanData.getPostcode()) + "',");			bufSQL.append("'" + nullString(beanData.getTelphone()) + "',");			bufSQL.append("'" + nullString(beanData.getHandphone()) + "',");			bufSQL.append("'" + nullString(beanData.getEmail()) + "',");			bufSQL.append("'" + nullString(beanData.getPwdquestion1()) + "',");			bufSQL.append("'" + nullString(beanData.getPwdquestion2()) + "',");			bufSQL.append("'" + nullString(beanData.getWorkid()) + "',");			bufSQL.append("NULL,");			bufSQL.append("'" + nullString(beanData.getLockflag()) + "',");			bufSQL.append("'" + nullString(beanData.getEnableflag()) + "',");			bufSQL.append("'" + nullString(beanData.getEnablestarttime()) + "',");			bufSQL.append("'" + nullString(beanData.getEnableendtime()) + "',");			bufSQL.append(beanData.getSortno().intValue() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "'");
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
		S_USERData beanData = (S_USERData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_USER( userid,loginid,loginname,jianpin,unitid,loginpwd,proxyunitid,sex,duty,addresscode,addressuser,postcode,telphone,handphone,email,pwdquestion1,pwdquestion2,workid,photo,lockflag,enableflag,enablestarttime,enableendtime,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getUserid());			statement.setString( 2,beanData.getLoginid());			statement.setString( 3,beanData.getLoginname());			statement.setString( 4,beanData.getJianpin());			statement.setString( 5,beanData.getUnitid());			statement.setString( 6,beanData.getLoginpwd());			statement.setString( 7,beanData.getProxyunitid());			statement.setString( 8,beanData.getSex());			statement.setString( 9,beanData.getDuty());			statement.setString( 10,beanData.getAddresscode());			statement.setString( 11,beanData.getAddressuser());			statement.setString( 12,beanData.getPostcode());			statement.setString( 13,beanData.getTelphone());			statement.setString( 14,beanData.getHandphone());			statement.setString( 15,beanData.getEmail());			statement.setString( 16,beanData.getPwdquestion1());			statement.setString( 17,beanData.getPwdquestion2());			statement.setString( 18,beanData.getWorkid());			statement.setBinaryStream( 19,beanData.getPhotoStream());			statement.setString( 20,beanData.getLockflag());			statement.setString( 21,beanData.getEnableflag());			statement.setString( 22,beanData.getEnablestarttime());			statement.setString( 23,beanData.getEnableendtime());			if (beanData.getSortno()== null)			{				statement.setNull( 24, Types.INTEGER);			}			else			{				statement.setInt( 24, beanData.getSortno().intValue());			}			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getDeptguid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_USER( userid,loginid,loginname,jianpin,unitid,loginpwd,proxyunitid,sex,duty,addresscode,addressuser,postcode,telphone,handphone,email,pwdquestion1,pwdquestion2,workid,photo,lockflag,enableflag,enablestarttime,enableendtime,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_USERData beanData = (S_USERData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_USER WHERE  userid =?");
			statement.setString( 1,beanData.getUserid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_USER: "+ e.toString());
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
		S_USERData beanData = (S_USERData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_USER WHERE ");
			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM S_USER"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_USER: "+ e.toString());
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
		S_USERData beanData = (S_USERData) beanDataTmp; 
		S_USERData returnData=new S_USERData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT userid,loginid,loginname,jianpin,unitid,loginpwd,proxyunitid,sex,duty,addresscode,addressuser,postcode,telphone,handphone,email,pwdquestion1,pwdquestion2,workid,photo,lockflag,enableflag,enablestarttime,enableendtime,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_USER WHERE  userid =?");
			statement.setString( 1,beanData.getUserid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setUserid( resultSet.getString( 1));			returnData.setLoginid( resultSet.getString( 2));			returnData.setLoginname( resultSet.getString( 3));			returnData.setJianpin( resultSet.getString( 4));			returnData.setUnitid( resultSet.getString( 5));			returnData.setLoginpwd( resultSet.getString( 6));			returnData.setProxyunitid( resultSet.getString( 7));			returnData.setSex( resultSet.getString( 8));			returnData.setDuty( resultSet.getString( 9));			returnData.setAddresscode( resultSet.getString( 10));			returnData.setAddressuser( resultSet.getString( 11));			returnData.setPostcode( resultSet.getString( 12));			returnData.setTelphone( resultSet.getString( 13));			returnData.setHandphone( resultSet.getString( 14));			returnData.setEmail( resultSet.getString( 15));			returnData.setPwdquestion1( resultSet.getString( 16));			returnData.setPwdquestion2( resultSet.getString( 17));			returnData.setWorkid( resultSet.getString( 18));			returnData.setPhoto( resultSet.getBlob( 19));			returnData.setLockflag( resultSet.getString( 20));			returnData.setEnableflag( resultSet.getString( 21));			returnData.setEnablestarttime( resultSet.getString( 22));			returnData.setEnableendtime( resultSet.getString( 23));			if (resultSet.getString( 24)==null)				returnData.setSortno(null);			else				returnData.setSortno( new Integer(resultSet.getInt( 24)));			returnData.setCreatetime( resultSet.getString( 25));			returnData.setCreateperson( resultSet.getString( 26));			returnData.setCreateunitid( resultSet.getString( 27));			returnData.setModifytime( resultSet.getString( 28));			returnData.setModifyperson( resultSet.getString( 29));			returnData.setDeleteflag( resultSet.getString( 30));			returnData.setDeptguid( resultSet.getString( 31));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT userid,loginid,loginname,jianpin,unitid,loginpwd,proxyunitid,sex,duty,addresscode,addressuser,postcode,telphone,handphone,email,pwdquestion1,pwdquestion2,workid,photo,lockflag,enableflag,enablestarttime,enableendtime,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_USER  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT userid,loginid,loginname,jianpin,unitid,loginpwd,proxyunitid,sex,duty,addresscode,addressuser,postcode,telphone,handphone,email,pwdquestion1,pwdquestion2,workid,photo,lockflag,enableflag,enablestarttime,enableendtime,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_USER"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_USERData returnData=new S_USERData();
				returnData.setUserid( resultSet.getString( 1));				returnData.setLoginid( resultSet.getString( 2));				returnData.setLoginname( resultSet.getString( 3));				returnData.setJianpin( resultSet.getString( 4));				returnData.setUnitid( resultSet.getString( 5));				returnData.setLoginpwd( resultSet.getString( 6));				returnData.setProxyunitid( resultSet.getString( 7));				returnData.setSex( resultSet.getString( 8));				returnData.setDuty( resultSet.getString( 9));				returnData.setAddresscode( resultSet.getString( 10));				returnData.setAddressuser( resultSet.getString( 11));				returnData.setPostcode( resultSet.getString( 12));				returnData.setTelphone( resultSet.getString( 13));				returnData.setHandphone( resultSet.getString( 14));				returnData.setEmail( resultSet.getString( 15));				returnData.setPwdquestion1( resultSet.getString( 16));				returnData.setPwdquestion2( resultSet.getString( 17));				returnData.setWorkid( resultSet.getString( 18));				returnData.setPhoto( resultSet.getBlob( 19));				returnData.setLockflag( resultSet.getString( 20));				returnData.setEnableflag( resultSet.getString( 21));				returnData.setEnablestarttime( resultSet.getString( 22));				returnData.setEnableendtime( resultSet.getString( 23));				if (resultSet.getString( 24)==null)					returnData.setSortno(null);				else					returnData.setSortno( new Integer(resultSet.getInt( 24)));				returnData.setCreatetime( resultSet.getString( 25));				returnData.setCreateperson( resultSet.getString( 26));				returnData.setCreateunitid( resultSet.getString( 27));				returnData.setModifytime( resultSet.getString( 28));				returnData.setModifyperson( resultSet.getString( 29));				returnData.setDeleteflag( resultSet.getString( 30));				returnData.setDeptguid( resultSet.getString( 31));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT userid,loginid,loginname,jianpin,unitid,loginpwd,proxyunitid,sex,duty,addresscode,addressuser,postcode,telphone,handphone,email,pwdquestion1,pwdquestion2,workid,photo,lockflag,enableflag,enablestarttime,enableendtime,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid FROM S_USER" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_USER SET userid= ? , loginid= ? , loginname= ? , jianpin= ? , unitid= ? , loginpwd= ? , proxyunitid= ? , sex= ? , duty= ? , addresscode= ? , addressuser= ? , postcode= ? , telphone= ? , handphone= ? , email= ? , pwdquestion1= ? , pwdquestion2= ? , workid= ? , photo= ? , lockflag= ? , enableflag= ? , enablestarttime= ? , enableendtime= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  userid  = ?");
			statement.setString( 1,beanData.getUserid());			statement.setString( 2,beanData.getLoginid());			statement.setString( 3,beanData.getLoginname());			statement.setString( 4,beanData.getJianpin());			statement.setString( 5,beanData.getUnitid());			statement.setString( 6,beanData.getLoginpwd());			statement.setString( 7,beanData.getProxyunitid());			statement.setString( 8,beanData.getSex());			statement.setString( 9,beanData.getDuty());			statement.setString( 10,beanData.getAddresscode());			statement.setString( 11,beanData.getAddressuser());			statement.setString( 12,beanData.getPostcode());			statement.setString( 13,beanData.getTelphone());			statement.setString( 14,beanData.getHandphone());			statement.setString( 15,beanData.getEmail());			statement.setString( 16,beanData.getPwdquestion1());			statement.setString( 17,beanData.getPwdquestion2());			statement.setString( 18,beanData.getWorkid());			statement.setBinaryStream( 19,beanData.getPhotoStream());			statement.setString( 20,beanData.getLockflag());			statement.setString( 21,beanData.getEnableflag());			statement.setString( 22,beanData.getEnablestarttime());			statement.setString( 23,beanData.getEnableendtime());			if (beanData.getSortno()== null)			{				statement.setNull( 24, Types.INTEGER);			}			else			{				statement.setInt( 24, beanData.getSortno().intValue());			}			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getDeptguid());
			statement.setString( 32,beanData.getUserid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_USER SET userid= ? , loginid= ? , loginname= ? , jianpin= ? , unitid= ? , loginpwd= ? , proxyunitid= ? , sex= ? , duty= ? , addresscode= ? , addressuser= ? , postcode= ? , telphone= ? , handphone= ? , email= ? , pwdquestion1= ? , pwdquestion2= ? , workid= ? , photo= ? , lockflag= ? , enableflag= ? , enablestarttime= ? , enableendtime= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  userid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_USER SET ");
			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "',");			bufSQL.append("Loginid = " + "'" + nullString(beanData.getLoginid()) + "',");			bufSQL.append("Loginname = " + "'" + nullString(beanData.getLoginname()) + "',");			bufSQL.append("Jianpin = " + "'" + nullString(beanData.getJianpin()) + "',");			bufSQL.append("Unitid = " + "'" + nullString(beanData.getUnitid()) + "',");			bufSQL.append("Loginpwd = " + "'" + nullString(beanData.getLoginpwd()) + "',");			bufSQL.append("Proxyunitid = " + "'" + nullString(beanData.getProxyunitid()) + "',");			bufSQL.append("Sex = " + "'" + nullString(beanData.getSex()) + "',");			bufSQL.append("Duty = " + "'" + nullString(beanData.getDuty()) + "',");			bufSQL.append("Addresscode = " + "'" + nullString(beanData.getAddresscode()) + "',");			bufSQL.append("Addressuser = " + "'" + nullString(beanData.getAddressuser()) + "',");			bufSQL.append("Postcode = " + "'" + nullString(beanData.getPostcode()) + "',");			bufSQL.append("Telphone = " + "'" + nullString(beanData.getTelphone()) + "',");			bufSQL.append("Handphone = " + "'" + nullString(beanData.getHandphone()) + "',");			bufSQL.append("Email = " + "'" + nullString(beanData.getEmail()) + "',");			bufSQL.append("Pwdquestion1 = " + "'" + nullString(beanData.getPwdquestion1()) + "',");			bufSQL.append("Pwdquestion2 = " + "'" + nullString(beanData.getPwdquestion2()) + "',");			bufSQL.append("Workid = " + "'" + nullString(beanData.getWorkid()) + "',");			bufSQL.append("Photo = NULL,");			bufSQL.append("Lockflag = " + "'" + nullString(beanData.getLockflag()) + "',");			bufSQL.append("Enableflag = " + "'" + nullString(beanData.getEnableflag()) + "',");			bufSQL.append("Enablestarttime = " + "'" + nullString(beanData.getEnablestarttime()) + "',");			bufSQL.append("Enableendtime = " + "'" + nullString(beanData.getEnableendtime()) + "',");			bufSQL.append("Sortno = " + beanData.getSortno().intValue() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Userid = " + "'" + nullString(beanData.getUserid()) + "'");
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
		S_USERData beanData = (S_USERData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_USER SET userid= ? , loginid= ? , loginname= ? , jianpin= ? , unitid= ? , loginpwd= ? , proxyunitid= ? , sex= ? , duty= ? , addresscode= ? , addressuser= ? , postcode= ? , telphone= ? , handphone= ? , email= ? , pwdquestion1= ? , pwdquestion2= ? , workid= ? , photo= ? , lockflag= ? , enableflag= ? , enablestarttime= ? , enableendtime= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  userid  = ?");
			statement.setString( 1,beanData.getUserid());			statement.setString( 2,beanData.getLoginid());			statement.setString( 3,beanData.getLoginname());			statement.setString( 4,beanData.getJianpin());			statement.setString( 5,beanData.getUnitid());			statement.setString( 6,beanData.getLoginpwd());			statement.setString( 7,beanData.getProxyunitid());			statement.setString( 8,beanData.getSex());			statement.setString( 9,beanData.getDuty());			statement.setString( 10,beanData.getAddresscode());			statement.setString( 11,beanData.getAddressuser());			statement.setString( 12,beanData.getPostcode());			statement.setString( 13,beanData.getTelphone());			statement.setString( 14,beanData.getHandphone());			statement.setString( 15,beanData.getEmail());			statement.setString( 16,beanData.getPwdquestion1());			statement.setString( 17,beanData.getPwdquestion2());			statement.setString( 18,beanData.getWorkid());			statement.setBinaryStream( 19,beanData.getPhotoStream());			statement.setString( 20,beanData.getLockflag());			statement.setString( 21,beanData.getEnableflag());			statement.setString( 22,beanData.getEnablestarttime());			statement.setString( 23,beanData.getEnableendtime());			if (beanData.getSortno()== null)			{				statement.setNull( 24, Types.INTEGER);			}			else			{				statement.setInt( 24, beanData.getSortno().intValue());			}			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getDeptguid());
			statement.setString( 32,beanData.getUserid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_USER SET userid= ? , loginid= ? , loginname= ? , jianpin= ? , unitid= ? , loginpwd= ? , proxyunitid= ? , sex= ? , duty= ? , addresscode= ? , addressuser= ? , postcode= ? , telphone= ? , handphone= ? , email= ? , pwdquestion1= ? , pwdquestion2= ? , workid= ? , photo= ? , lockflag= ? , enableflag= ? , enablestarttime= ? , enableendtime= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid=? WHERE  userid  = ?"+ e.toString());
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
		S_USERData beanData = (S_USERData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  userid  FROM S_USER WHERE  userid =?");
			statement.setString( 1,beanData.getUserid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  userid  FROM S_USER WHERE  userid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
