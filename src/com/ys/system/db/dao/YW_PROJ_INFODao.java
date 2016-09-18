package com.ys.system.db.dao;

import java.sql.*;
import java.io.InputStream;
import com.ys.system.db.data.*;
import com.ys.util.basedao.BaseAbstractDao;

/**
* <p>Title: </p>
* <p>Description: ��ݱ�  </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class YW_PROJ_INFODao extends BaseAbstractDao
{
	public YW_PROJ_INFOData beanData=new YW_PROJ_INFOData();
	public YW_PROJ_INFODao()
	{
	}
	public YW_PROJ_INFODao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROJ_INFOData)FindByPrimaryKey(beanData);
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
		YW_PROJ_INFOData beanData = (YW_PROJ_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROJ_INFO( id,proj_code,proj_name,proj_name_short,prov_code,proj_pm,ref_prot,sta_date,end_date,pack_info,performs,est_cost,inv_total,status,fail_note,sell_price,expect_sales,payback_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formidz)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProj_code());			statement.setString( 3,beanData.getProj_name());			statement.setString( 4,beanData.getProj_name_short());			statement.setString( 5,beanData.getProv_code());			statement.setString( 6,beanData.getProj_pm());			statement.setString( 7,beanData.getRef_prot());			statement.setString( 8,beanData.getSta_date());			statement.setString( 9,beanData.getEnd_date());			statement.setString( 10,beanData.getPack_info());			statement.setString( 11,beanData.getPerforms());			if (beanData.getEst_cost()== null)			{				statement.setNull( 12, Types.FLOAT);			}			else			{				statement.setFloat( 12, beanData.getEst_cost().floatValue());			}			if (beanData.getInv_total()== null)			{				statement.setNull( 13, Types.FLOAT);			}			else			{				statement.setFloat( 13, beanData.getInv_total().floatValue());			}			statement.setString( 14,beanData.getStatus());			statement.setString( 15,beanData.getFail_note());			if (beanData.getSell_price()== null)			{				statement.setNull( 16, Types.FLOAT);			}			else			{				statement.setFloat( 16, beanData.getSell_price().floatValue());			}			statement.setInt( 17,beanData.getExpect_sales());			statement.setInt( 18,beanData.getPayback_qty());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getDeptguid());			statement.setString( 26,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROJ_INFO( id,proj_code,proj_name,proj_name_short,prov_code,proj_pm,ref_prot,sta_date,end_date,pack_info,performs,est_cost,inv_total,status,fail_note,sell_price,expect_sales,payback_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROJ_INFO( id,proj_code,proj_name,proj_name_short,prov_code,proj_pm,ref_prot,sta_date,end_date,pack_info,performs,est_cost,inv_total,status,fail_note,sell_price,expect_sales,payback_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append("'" + nullString(beanData.getProj_code()) + "',");			bufSQL.append("'" + nullString(beanData.getProj_name()) + "',");			bufSQL.append("'" + nullString(beanData.getProj_name_short()) + "',");			bufSQL.append("'" + nullString(beanData.getProv_code()) + "',");			bufSQL.append("'" + nullString(beanData.getProj_pm()) + "',");			bufSQL.append("'" + nullString(beanData.getRef_prot()) + "',");			bufSQL.append("'" + nullString(beanData.getSta_date()) + "',");			bufSQL.append("'" + nullString(beanData.getEnd_date()) + "',");			bufSQL.append("'" + nullString(beanData.getPack_info()) + "',");			bufSQL.append("'" + nullString(beanData.getPerforms()) + "',");			bufSQL.append(beanData.getEst_cost() + ",");			bufSQL.append(beanData.getInv_total() + ",");			bufSQL.append("'" + nullString(beanData.getStatus()) + "',");			bufSQL.append(beanData.getSell_price() + ",");			bufSQL.append(beanData.getExpect_sales() + ",");			bufSQL.append(beanData.getPayback_qty() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROJ_INFOData beanData = (YW_PROJ_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROJ_INFO( id,proj_code,proj_name,proj_name_short,prov_code,proj_pm,ref_prot,sta_date,end_date,pack_info,performs,est_cost,inv_total,status,fail_note,sell_price,expect_sales,payback_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProj_code());			statement.setString( 3,beanData.getProj_name());			statement.setString( 4,beanData.getProj_name_short());			statement.setString( 5,beanData.getProv_code());			statement.setString( 6,beanData.getProj_pm());			statement.setString( 7,beanData.getRef_prot());			statement.setString( 8,beanData.getSta_date());			statement.setString( 9,beanData.getEnd_date());			statement.setString( 10,beanData.getPack_info());			statement.setString( 11,beanData.getPerforms());			if (beanData.getEst_cost()== null)			{				statement.setNull( 12, Types.FLOAT);			}			else			{				statement.setFloat( 12, beanData.getEst_cost().floatValue());			}			if (beanData.getInv_total()== null)			{				statement.setNull( 13, Types.FLOAT);			}			else			{				statement.setFloat( 13, beanData.getInv_total().floatValue());			}			statement.setString( 14,beanData.getStatus());			statement.setString( 15,beanData.getFail_note());			if (beanData.getSell_price()== null)			{				statement.setNull( 16, Types.FLOAT);			}			else			{				statement.setFloat( 16, beanData.getSell_price().floatValue());			}			statement.setInt( 17,beanData.getExpect_sales());			statement.setInt( 18,beanData.getPayback_qty());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getDeptguid());			statement.setString( 26,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROJ_INFO( id,proj_code,proj_name,proj_name_short,prov_code,proj_pm,ref_prot,sta_date,end_date,pack_info,performs,est_cost,inv_total,status,fail_note,sell_price,expect_sales,payback_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROJ_INFOData beanData = (YW_PROJ_INFOData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROJ_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROJ_INFO: "+ e.toString());
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
		YW_PROJ_INFOData beanData = (YW_PROJ_INFOData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROJ_INFO WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROJ_INFO"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROJ_INFO: "+ e.toString());
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
		YW_PROJ_INFOData beanData = (YW_PROJ_INFOData) beanDataTmp; 
		YW_PROJ_INFOData returnData=new YW_PROJ_INFOData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,proj_code,proj_name,proj_name_short,prov_code,proj_pm,ref_prot,sta_date,end_date,pack_info,performs,est_cost,inv_total,status,fail_note,sell_price,expect_sales,payback_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProj_code( resultSet.getString( 2));			returnData.setProj_name( resultSet.getString( 3));			returnData.setProj_name_short( resultSet.getString( 4));			returnData.setProv_code( resultSet.getString( 5));			returnData.setProj_pm( resultSet.getString( 6));			returnData.setRef_prot( resultSet.getString( 7));			returnData.setSta_date( resultSet.getString( 8));			returnData.setEnd_date( resultSet.getString( 9));			returnData.setPack_info( resultSet.getString( 10));			returnData.setPerforms( resultSet.getString( 11));			if (resultSet.getString( 12)==null)				returnData.setEst_cost(null);			else				returnData.setEst_cost( new Float(resultSet.getFloat( 12)));			if (resultSet.getString( 13)==null)				returnData.setInv_total(null);			else				returnData.setInv_total( new Float(resultSet.getFloat( 13)));			returnData.setStatus( resultSet.getString( 14));			returnData.setFail_note( resultSet.getString( 15));			if (resultSet.getString( 16)==null)				returnData.setSell_price(null);			else				returnData.setSell_price( new Float(resultSet.getFloat( 16)));			returnData.setExpect_sales( resultSet.getInt( 17));			returnData.setPayback_qty( resultSet.getInt( 18));			returnData.setCreatetime( resultSet.getString( 19));			returnData.setCreateperson( resultSet.getString( 20));			returnData.setCreateunitid( resultSet.getString( 21));			returnData.setModifytime( resultSet.getString( 22));			returnData.setModifyperson( resultSet.getString( 23));			returnData.setDeleteflag( resultSet.getString( 24));			returnData.setDeptguid( resultSet.getString( 25));			returnData.setFormid( resultSet.getString( 26));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,proj_code,proj_name,proj_name_short,prov_code,proj_pm,ref_prot,sta_date,end_date,pack_info,performs,est_cost,inv_total,status,fail_note,sell_price,expect_sales,payback_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_INFO  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,proj_code,proj_name,proj_name_short,prov_code,proj_pm,ref_prot,sta_date,end_date,pack_info,performs,est_cost,inv_total,status,fail_note,sell_price,expect_sales,payback_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_INFO"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROJ_INFOData returnData=new YW_PROJ_INFOData();
				returnData.setId( resultSet.getString( 1));				returnData.setProj_code( resultSet.getString( 2));				returnData.setProj_name( resultSet.getString( 3));				returnData.setProj_name_short( resultSet.getString( 4));				returnData.setProv_code( resultSet.getString( 5));				returnData.setProj_pm( resultSet.getString( 6));				returnData.setRef_prot( resultSet.getString( 7));				returnData.setSta_date( resultSet.getString( 8));				returnData.setEnd_date( resultSet.getString( 9));				returnData.setPack_info( resultSet.getString( 10));				returnData.setPerforms( resultSet.getString( 11));				if (resultSet.getString( 12)==null)					returnData.setEst_cost(null);				else					returnData.setEst_cost( new Float(resultSet.getFloat( 12)));				if (resultSet.getString( 13)==null)					returnData.setInv_total(null);				else					returnData.setInv_total( new Float(resultSet.getFloat( 13)));				returnData.setStatus( resultSet.getString( 14));				returnData.setFail_note( resultSet.getString( 15));				if (resultSet.getString( 16)==null)					returnData.setSell_price(null);				else					returnData.setSell_price( new Float(resultSet.getFloat( 16)));				returnData.setExpect_sales( resultSet.getInt( 17));				returnData.setPayback_qty( resultSet.getInt( 18));				returnData.setCreatetime( resultSet.getString( 19));				returnData.setCreateperson( resultSet.getString( 20));				returnData.setCreateunitid( resultSet.getString( 21));				returnData.setModifytime( resultSet.getString( 22));				returnData.setModifyperson( resultSet.getString( 23));				returnData.setDeleteflag( resultSet.getString( 24));				returnData.setDeptguid( resultSet.getString( 25));				returnData.setFormid( resultSet.getString( 26));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,proj_code,proj_name,proj_name_short,prov_code,proj_pm,ref_prot,sta_date,end_date,pack_info,performs,est_cost,inv_total,status,fail_note,sell_price,expect_sales,payback_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_INFO" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROJ_INFO SET id= ? , proj_code= ? , proj_name= ? , proj_name_short= ? , prov_code= ? , proj_pm= ? , ref_prot= ? , sta_date= ? , end_date= ? , pack_info= ? , performs= ? , est_cost= ? , inv_total= ? , status= ? , fail_note= ? , sell_price= ? , expect_sales= ? , payback_qty= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProj_code());			statement.setString( 3,beanData.getProj_name());			statement.setString( 4,beanData.getProj_name_short());			statement.setString( 5,beanData.getProv_code());			statement.setString( 6,beanData.getProj_pm());			statement.setString( 7,beanData.getRef_prot());			statement.setString( 8,beanData.getSta_date());			statement.setString( 9,beanData.getEnd_date());			statement.setString( 10,beanData.getPack_info());			statement.setString( 11,beanData.getPerforms());			if (beanData.getEst_cost()== null)			{				statement.setNull( 12, Types.FLOAT);			}			else			{				statement.setFloat( 12, beanData.getEst_cost().floatValue());			}			if (beanData.getInv_total()== null)			{				statement.setNull( 13, Types.FLOAT);			}			else			{				statement.setFloat( 13, beanData.getInv_total().floatValue());			}			statement.setString( 14,beanData.getStatus());			statement.setString( 15,beanData.getFail_note());			if (beanData.getSell_price()== null)			{				statement.setNull( 16, Types.FLOAT);			}			else			{				statement.setFloat( 16, beanData.getSell_price().floatValue());			}			statement.setInt( 17,beanData.getExpect_sales());			statement.setInt( 18,beanData.getPayback_qty());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getDeptguid());			statement.setString( 26,beanData.getFormid());
			statement.setString( 27,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROJ_INFO SET id= ? , proj_code= ? , proj_name= ? , proj_name_short= ? , prov_code= ? , proj_pm= ? , ref_prot= ? , sta_date= ? , end_date= ? , pack_info= ? , performs= ? , est_cost= ? , inv_total= ? , status= ? , fail_note= ? , sell_price= ? , expect_sales= ? , payback_qty= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROJ_INFO SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Proj_code = " + "'" + nullString(beanData.getProj_code()) + "',");			bufSQL.append("Proj_name = " + "'" + nullString(beanData.getProj_name()) + "',");			bufSQL.append("Proj_name_short = " + "'" + nullString(beanData.getProj_name_short()) + "',");			bufSQL.append("Prov_code = " + "'" + nullString(beanData.getProv_code()) + "',");			bufSQL.append("Proj_pm = " + "'" + nullString(beanData.getProj_pm()) + "',");			bufSQL.append("Ref_prot = " + "'" + nullString(beanData.getRef_prot()) + "',");			bufSQL.append("Sta_date = " + "'" + nullString(beanData.getSta_date()) + "',");			bufSQL.append("End_date = " + "'" + nullString(beanData.getEnd_date()) + "',");			bufSQL.append("Pack_info = " + "'" + nullString(beanData.getPack_info()) + "',");			bufSQL.append("Performs = " + "'" + nullString(beanData.getPerforms()) + "',");			bufSQL.append("Est_cost = " + beanData.getEst_cost() + ",");			bufSQL.append("Inv_total = " + beanData.getInv_total() + ",");			bufSQL.append("Status = " + "'" + nullString(beanData.getStatus()) + "',");			bufSQL.append("Sell_price = " + beanData.getSell_price() + ",");			bufSQL.append("Expect_sales = " + beanData.getExpect_sales() + ",");			bufSQL.append("Payback_qty = " + beanData.getPayback_qty() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROJ_INFOData beanData = (YW_PROJ_INFOData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROJ_INFO SET id= ? , proj_code= ? , proj_name= ? , proj_name_short= ? , prov_code= ? , proj_pm= ? , ref_prot= ? , sta_date= ? , end_date= ? , pack_info= ? , performs= ? , est_cost= ? , inv_total= ? , status= ? , fail_note= ? , sell_price= ? , expect_sales= ? , payback_qty= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setString( 2,beanData.getProj_code());			statement.setString( 3,beanData.getProj_name());			statement.setString( 4,beanData.getProj_name_short());			statement.setString( 5,beanData.getProv_code());			statement.setString( 6,beanData.getProj_pm());			statement.setString( 7,beanData.getRef_prot());			statement.setString( 8,beanData.getSta_date());			statement.setString( 9,beanData.getEnd_date());			statement.setString( 10,beanData.getPack_info());			statement.setString( 11,beanData.getPerforms());			if (beanData.getEst_cost()== null)			{				statement.setNull( 12, Types.FLOAT);			}			else			{				statement.setFloat( 12, beanData.getEst_cost().floatValue());			}			if (beanData.getInv_total()== null)			{				statement.setNull( 13, Types.FLOAT);			}			else			{				statement.setFloat( 13, beanData.getInv_total().floatValue());			}			statement.setString( 14,beanData.getStatus());			statement.setString( 15,beanData.getFail_note());			if (beanData.getSell_price()== null)			{				statement.setNull( 16, Types.FLOAT);			}			else			{				statement.setFloat( 16, beanData.getSell_price().floatValue());			}			statement.setInt( 17,beanData.getExpect_sales());			statement.setInt( 18,beanData.getPayback_qty());			statement.setString( 19,beanData.getCreatetime());			statement.setString( 20,beanData.getCreateperson());			statement.setString( 21,beanData.getCreateunitid());			statement.setString( 22,beanData.getModifytime());			statement.setString( 23,beanData.getModifyperson());			statement.setString( 24,beanData.getDeleteflag());			statement.setString( 25,beanData.getDeptguid());			statement.setString( 26,beanData.getFormid());
			statement.setString( 27,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROJ_INFO SET id= ? , proj_code= ? , proj_name= ? , proj_name_short= ? , prov_code= ? , proj_pm= ? , ref_prot= ? , sta_date= ? , end_date= ? , pack_info= ? , performs= ? , est_cost= ? , inv_total= ? , status= ? , fail_note= ? , sell_price= ? , expect_sales= ? , payback_qty= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PROJ_INFOData beanData = (YW_PROJ_INFOData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PROJ_INFO WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PROJ_INFO WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
