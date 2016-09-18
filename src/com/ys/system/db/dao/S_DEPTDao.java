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
public class S_DEPTDao extends BaseAbstractDao
{
	public S_DEPTData beanData=new S_DEPTData();
	public S_DEPTDao()
	{
	}
	public S_DEPTDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (S_DEPTData)FindByPrimaryKey(beanData);
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
		S_DEPTData beanData = (S_DEPTData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_DEPT( unitid,unitname,unitsimpledes,jianpin,parentid,orgid,addresscode,addressuser,postcode,telphone,email,unitproperty,officeid,areaid,unittype,mgrperson,deptguid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getUnitid());			statement.setString( 2,beanData.getUnitname());			statement.setString( 3,beanData.getUnitsimpledes());			statement.setString( 4,beanData.getJianpin());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getOrgid());			statement.setString( 7,beanData.getAddresscode());			statement.setString( 8,beanData.getAddressuser());			statement.setString( 9,beanData.getPostcode());			statement.setString( 10,beanData.getTelphone());			statement.setString( 11,beanData.getEmail());			statement.setString( 12,beanData.getUnitproperty());			statement.setString( 13,beanData.getOfficeid());			statement.setString( 14,beanData.getAreaid());			statement.setString( 15,beanData.getUnittype());			statement.setString( 16,beanData.getMgrperson());			statement.setString( 17,beanData.getDeptguid());			if (beanData.getSortno()== null)			{				statement.setNull( 18, Types.INTEGER);			}			else			{				statement.setInt( 18, beanData.getSortno().intValue());			}			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_DEPT( unitid,unitname,unitsimpledes,jianpin,parentid,orgid,addresscode,addressuser,postcode,telphone,email,unitproperty,officeid,areaid,unittype,mgrperson,deptguid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO S_DEPT( unitid,unitname,unitsimpledes,jianpin,parentid,orgid,addresscode,addressuser,postcode,telphone,email,unitproperty,officeid,areaid,unittype,mgrperson,deptguid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getUnitid()) + "',");			bufSQL.append("'" + nullString(beanData.getUnitname()) + "',");			bufSQL.append("'" + nullString(beanData.getUnitsimpledes()) + "',");			bufSQL.append("'" + nullString(beanData.getJianpin()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getOrgid()) + "',");			bufSQL.append("'" + nullString(beanData.getAddresscode()) + "',");			bufSQL.append("'" + nullString(beanData.getAddressuser()) + "',");			bufSQL.append("'" + nullString(beanData.getPostcode()) + "',");			bufSQL.append("'" + nullString(beanData.getTelphone()) + "',");			bufSQL.append("'" + nullString(beanData.getEmail()) + "',");			bufSQL.append("'" + nullString(beanData.getUnitproperty()) + "',");			bufSQL.append("'" + nullString(beanData.getOfficeid()) + "',");			bufSQL.append("'" + nullString(beanData.getAreaid()) + "',");			bufSQL.append("'" + nullString(beanData.getUnittype()) + "',");			bufSQL.append("'" + nullString(beanData.getMgrperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append(beanData.getSortno().intValue() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		S_DEPTData beanData = (S_DEPTData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO S_DEPT( unitid,unitname,unitsimpledes,jianpin,parentid,orgid,addresscode,addressuser,postcode,telphone,email,unitproperty,officeid,areaid,unittype,mgrperson,deptguid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getUnitid());			statement.setString( 2,beanData.getUnitname());			statement.setString( 3,beanData.getUnitsimpledes());			statement.setString( 4,beanData.getJianpin());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getOrgid());			statement.setString( 7,beanData.getAddresscode());			statement.setString( 8,beanData.getAddressuser());			statement.setString( 9,beanData.getPostcode());			statement.setString( 10,beanData.getTelphone());			statement.setString( 11,beanData.getEmail());			statement.setString( 12,beanData.getUnitproperty());			statement.setString( 13,beanData.getOfficeid());			statement.setString( 14,beanData.getAreaid());			statement.setString( 15,beanData.getUnittype());			statement.setString( 16,beanData.getMgrperson());			statement.setString( 17,beanData.getDeptguid());			if (beanData.getSortno()== null)			{				statement.setNull( 18, Types.INTEGER);			}			else			{				statement.setInt( 18, beanData.getSortno().intValue());			}			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO S_DEPT( unitid,unitname,unitsimpledes,jianpin,parentid,orgid,addresscode,addressuser,postcode,telphone,email,unitproperty,officeid,areaid,unittype,mgrperson,deptguid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		S_DEPTData beanData = (S_DEPTData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM S_DEPT WHERE  unitid =?");
			statement.setString( 1,beanData.getUnitid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_DEPT: "+ e.toString());
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
		S_DEPTData beanData = (S_DEPTData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM S_DEPT WHERE ");
			bufSQL.append("Unitid = " + "'" + nullString(beanData.getUnitid()) + "'");
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
			statement = connection.prepareStatement("DELETE FROM S_DEPT"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM S_DEPT: "+ e.toString());
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
		S_DEPTData beanData = (S_DEPTData) beanDataTmp; 
		S_DEPTData returnData=new S_DEPTData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT unitid,unitname,unitsimpledes,jianpin,parentid,orgid,addresscode,addressuser,postcode,telphone,email,unitproperty,officeid,areaid,unittype,mgrperson,deptguid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM S_DEPT WHERE  unitid =?");
			statement.setString( 1,beanData.getUnitid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setUnitid( resultSet.getString( 1));			returnData.setUnitname( resultSet.getString( 2));			returnData.setUnitsimpledes( resultSet.getString( 3));			returnData.setJianpin( resultSet.getString( 4));			returnData.setParentid( resultSet.getString( 5));			returnData.setOrgid( resultSet.getString( 6));			returnData.setAddresscode( resultSet.getString( 7));			returnData.setAddressuser( resultSet.getString( 8));			returnData.setPostcode( resultSet.getString( 9));			returnData.setTelphone( resultSet.getString( 10));			returnData.setEmail( resultSet.getString( 11));			returnData.setUnitproperty( resultSet.getString( 12));			returnData.setOfficeid( resultSet.getString( 13));			returnData.setAreaid( resultSet.getString( 14));			returnData.setUnittype( resultSet.getString( 15));			returnData.setMgrperson( resultSet.getString( 16));			returnData.setDeptguid( resultSet.getString( 17));			if (resultSet.getString( 18)==null)				returnData.setSortno(null);			else				returnData.setSortno( new Integer(resultSet.getInt( 18)));			returnData.setCreatetime( resultSet.getString( 19));			returnData.setCreateperson( resultSet.getString( 20));			returnData.setCreateunitid( resultSet.getString( 21));			returnData.setModifytime( resultSet.getString( 22));			returnData.setModifyperson( resultSet.getString( 23));			returnData.setDeleteflag( resultSet.getString( 24));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT unitid,unitname,unitsimpledes,jianpin,parentid,orgid,addresscode,addressuser,postcode,telphone,email,unitproperty,officeid,areaid,unittype,mgrperson,deptguid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM S_DEPT  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT unitid,unitname,unitsimpledes,jianpin,parentid,orgid,addresscode,addressuser,postcode,telphone,email,unitproperty,officeid,areaid,unittype,mgrperson,deptguid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM S_DEPT"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				S_DEPTData returnData=new S_DEPTData();
				returnData.setUnitid( resultSet.getString( 1));				returnData.setUnitname( resultSet.getString( 2));				returnData.setUnitsimpledes( resultSet.getString( 3));				returnData.setJianpin( resultSet.getString( 4));				returnData.setParentid( resultSet.getString( 5));				returnData.setOrgid( resultSet.getString( 6));				returnData.setAddresscode( resultSet.getString( 7));				returnData.setAddressuser( resultSet.getString( 8));				returnData.setPostcode( resultSet.getString( 9));				returnData.setTelphone( resultSet.getString( 10));				returnData.setEmail( resultSet.getString( 11));				returnData.setUnitproperty( resultSet.getString( 12));				returnData.setOfficeid( resultSet.getString( 13));				returnData.setAreaid( resultSet.getString( 14));				returnData.setUnittype( resultSet.getString( 15));				returnData.setMgrperson( resultSet.getString( 16));				returnData.setDeptguid( resultSet.getString( 17));				if (resultSet.getString( 18)==null)					returnData.setSortno(null);				else					returnData.setSortno( new Integer(resultSet.getInt( 18)));				returnData.setCreatetime( resultSet.getString( 19));				returnData.setCreateperson( resultSet.getString( 20));				returnData.setCreateunitid( resultSet.getString( 21));				returnData.setModifytime( resultSet.getString( 22));				returnData.setModifyperson( resultSet.getString( 23));				returnData.setDeleteflag( resultSet.getString( 24));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT unitid,unitname,unitsimpledes,jianpin,parentid,orgid,addresscode,addressuser,postcode,telphone,email,unitproperty,officeid,areaid,unittype,mgrperson,deptguid,sortno,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM S_DEPT" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE S_DEPT SET unitid= ? , unitname= ? , unitsimpledes= ? , jianpin= ? , parentid= ? , orgid= ? , addresscode= ? , addressuser= ? , postcode= ? , telphone= ? , email= ? , unitproperty= ? , officeid= ? , areaid= ? , unittype= ? , mgrperson= ? , deptguid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  unitid  = ?");
			statement.setString( 1,beanData.getUnitid());			statement.setString( 2,beanData.getUnitname());			statement.setString( 3,beanData.getUnitsimpledes());			statement.setString( 4,beanData.getJianpin());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getOrgid());			statement.setString( 7,beanData.getAddresscode());			statement.setString( 8,beanData.getAddressuser());			statement.setString( 9,beanData.getPostcode());			statement.setString( 10,beanData.getTelphone());			statement.setString( 11,beanData.getEmail());			statement.setString( 12,beanData.getUnitproperty());			statement.setString( 13,beanData.getOfficeid());			statement.setString( 14,beanData.getAreaid());			statement.setString( 15,beanData.getUnittype());			statement.setString( 16,beanData.getMgrperson());			statement.setString( 17,beanData.getDeptguid());			if (beanData.getSortno()== null)			{				statement.setNull( 18, Types.INTEGER);			}			else			{				statement.setInt( 18, beanData.getSortno().intValue());			}			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());
			statement.setString( 25,beanData.getUnitid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_DEPT SET unitid= ? , unitname= ? , unitsimpledes= ? , jianpin= ? , parentid= ? , orgid= ? , addresscode= ? , addressuser= ? , postcode= ? , telphone= ? , email= ? , unitproperty= ? , officeid= ? , areaid= ? , unittype= ? , mgrperson= ? , deptguid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  unitid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE S_DEPT SET ");
			bufSQL.append("Unitid = " + "'" + nullString(beanData.getUnitid()) + "',");			bufSQL.append("Unitname = " + "'" + nullString(beanData.getUnitname()) + "',");			bufSQL.append("Unitsimpledes = " + "'" + nullString(beanData.getUnitsimpledes()) + "',");			bufSQL.append("Jianpin = " + "'" + nullString(beanData.getJianpin()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Orgid = " + "'" + nullString(beanData.getOrgid()) + "',");			bufSQL.append("Addresscode = " + "'" + nullString(beanData.getAddresscode()) + "',");			bufSQL.append("Addressuser = " + "'" + nullString(beanData.getAddressuser()) + "',");			bufSQL.append("Postcode = " + "'" + nullString(beanData.getPostcode()) + "',");			bufSQL.append("Telphone = " + "'" + nullString(beanData.getTelphone()) + "',");			bufSQL.append("Email = " + "'" + nullString(beanData.getEmail()) + "',");			bufSQL.append("Unitproperty = " + "'" + nullString(beanData.getUnitproperty()) + "',");			bufSQL.append("Officeid = " + "'" + nullString(beanData.getOfficeid()) + "',");			bufSQL.append("Areaid = " + "'" + nullString(beanData.getAreaid()) + "',");			bufSQL.append("Unittype = " + "'" + nullString(beanData.getUnittype()) + "',");			bufSQL.append("Mgrperson = " + "'" + nullString(beanData.getMgrperson()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Sortno = " + beanData.getSortno().intValue() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
			bufSQL.append(" WHERE ");
			bufSQL.append("Unitid = " + "'" + nullString(beanData.getUnitid()) + "'");
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
		S_DEPTData beanData = (S_DEPTData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE S_DEPT SET unitid= ? , unitname= ? , unitsimpledes= ? , jianpin= ? , parentid= ? , orgid= ? , addresscode= ? , addressuser= ? , postcode= ? , telphone= ? , email= ? , unitproperty= ? , officeid= ? , areaid= ? , unittype= ? , mgrperson= ? , deptguid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  unitid  = ?");
			statement.setString( 1,beanData.getUnitid());			statement.setString( 2,beanData.getUnitname());			statement.setString( 3,beanData.getUnitsimpledes());			statement.setString( 4,beanData.getJianpin());			statement.setString( 5,beanData.getParentid());			statement.setString( 6,beanData.getOrgid());			statement.setString( 7,beanData.getAddresscode());			statement.setString( 8,beanData.getAddressuser());			statement.setString( 9,beanData.getPostcode());			statement.setString( 10,beanData.getTelphone());			statement.setString( 11,beanData.getEmail());			statement.setString( 12,beanData.getUnitproperty());			statement.setString( 13,beanData.getOfficeid());			statement.setString( 14,beanData.getAreaid());			statement.setString( 15,beanData.getUnittype());			statement.setString( 16,beanData.getMgrperson());			statement.setString( 17,beanData.getDeptguid());			if (beanData.getSortno()== null)			{				statement.setNull( 18, Types.INTEGER);			}			else			{				statement.setInt( 18, beanData.getSortno().intValue());			}			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());
			statement.setString( 25,beanData.getUnitid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE S_DEPT SET unitid= ? , unitname= ? , unitsimpledes= ? , jianpin= ? , parentid= ? , orgid= ? , addresscode= ? , addressuser= ? , postcode= ? , telphone= ? , email= ? , unitproperty= ? , officeid= ? , areaid= ? , unittype= ? , mgrperson= ? , deptguid= ? , sortno= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  unitid  = ?"+ e.toString());
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
		S_DEPTData beanData = (S_DEPTData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  unitid  FROM S_DEPT WHERE  unitid =?");
			statement.setString( 1,beanData.getUnitid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  unitid  FROM S_DEPT WHERE  unitid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
