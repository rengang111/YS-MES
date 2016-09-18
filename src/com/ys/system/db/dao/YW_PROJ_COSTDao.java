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
public class YW_PROJ_COSTDao extends BaseAbstractDao
{
	public YW_PROJ_COSTData beanData=new YW_PROJ_COSTData();
	public YW_PROJ_COSTDao()
	{
	}
	public YW_PROJ_COSTDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (YW_PROJ_COSTData)FindByPrimaryKey(beanData);
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
		YW_PROJ_COSTData beanData = (YW_PROJ_COSTData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROJ_COST( id,proj_id,cost_mach,cost_mach_gear,cost_mach_bat_pkg,cost_mach_char,cost_mach_diec,cost_mach_hard,cost_pack_blow,cost_pack_inj,cost_pack_bli,cost_3d_hand,describes,cost_pat_ext,cost_pat_prac,cost_pat_inv,cost_pat_query,cost_design,pilot_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			if (beanData.getCost_mach()== null)			{				statement.setNull( 3, Types.FLOAT);			}			else			{				statement.setFloat( 3, beanData.getCost_mach().floatValue());			}			if (beanData.getCost_mach_gear()== null)			{				statement.setNull( 4, Types.FLOAT);			}			else			{				statement.setFloat( 4, beanData.getCost_mach_gear().floatValue());			}			if (beanData.getCost_mach_bat_pkg()== null)			{				statement.setNull( 5, Types.FLOAT);			}			else			{				statement.setFloat( 5, beanData.getCost_mach_bat_pkg().floatValue());			}			if (beanData.getCost_mach_char()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getCost_mach_char().floatValue());			}			if (beanData.getCost_mach_diec()== null)			{				statement.setNull( 7, Types.FLOAT);			}			else			{				statement.setFloat( 7, beanData.getCost_mach_diec().floatValue());			}			if (beanData.getCost_mach_hard()== null)			{				statement.setNull( 8, Types.FLOAT);			}			else			{				statement.setFloat( 8, beanData.getCost_mach_hard().floatValue());			}			if (beanData.getCost_pack_blow()== null)			{				statement.setNull( 9, Types.FLOAT);			}			else			{				statement.setFloat( 9, beanData.getCost_pack_blow().floatValue());			}			if (beanData.getCost_pack_inj()== null)			{				statement.setNull( 10, Types.FLOAT);			}			else			{				statement.setFloat( 10, beanData.getCost_pack_inj().floatValue());			}			if (beanData.getCost_pack_bli()== null)			{				statement.setNull( 11, Types.FLOAT);			}			else			{				statement.setFloat( 11, beanData.getCost_pack_bli().floatValue());			}			if (beanData.getCost_3d_hand()== null)			{				statement.setNull( 12, Types.FLOAT);			}			else			{				statement.setFloat( 12, beanData.getCost_3d_hand().floatValue());			}			if (beanData.getDescribes()== null)			{				statement.setNull( 13, Types.FLOAT);			}			else			{				statement.setFloat( 13, beanData.getDescribes().floatValue());			}			if (beanData.getCost_pat_ext()== null)			{				statement.setNull( 14, Types.FLOAT);			}			else			{				statement.setFloat( 14, beanData.getCost_pat_ext().floatValue());			}			if (beanData.getCost_pat_prac()== null)			{				statement.setNull( 15, Types.FLOAT);			}			else			{				statement.setFloat( 15, beanData.getCost_pat_prac().floatValue());			}			if (beanData.getCost_pat_inv()== null)			{				statement.setNull( 16, Types.FLOAT);			}			else			{				statement.setFloat( 16, beanData.getCost_pat_inv().floatValue());			}			if (beanData.getCost_pat_query()== null)			{				statement.setNull( 17, Types.FLOAT);			}			else			{				statement.setFloat( 17, beanData.getCost_pat_query().floatValue());			}			if (beanData.getCost_design()== null)			{				statement.setNull( 18, Types.FLOAT);			}			else			{				statement.setFloat( 18, beanData.getCost_design().floatValue());			}			statement.setInt( 19,beanData.getPilot_qty());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());			statement.setString( 26,beanData.getDeptguid());			statement.setString( 27,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROJ_COST( id,proj_id,cost_mach,cost_mach_gear,cost_mach_bat_pkg,cost_mach_char,cost_mach_diec,cost_mach_hard,cost_pack_blow,cost_pack_inj,cost_pack_bli,cost_3d_hand,describes,cost_pat_ext,cost_pat_prac,cost_pat_inv,cost_pat_query,cost_design,pilot_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO YW_PROJ_COST( id,proj_id,cost_mach,cost_mach_gear,cost_mach_bat_pkg,cost_mach_char,cost_mach_diec,cost_mach_hard,cost_pack_blow,cost_pack_inj,cost_pack_bli,cost_3d_hand,describes,cost_pat_ext,cost_pat_prac,cost_pat_inv,cost_pat_query,cost_design,pilot_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getId()) + "',");			bufSQL.append(beanData.getProj_id() + ",");			bufSQL.append(beanData.getCost_mach() + ",");			bufSQL.append(beanData.getCost_mach_gear() + ",");			bufSQL.append(beanData.getCost_mach_bat_pkg() + ",");			bufSQL.append(beanData.getCost_mach_char() + ",");			bufSQL.append(beanData.getCost_mach_diec() + ",");			bufSQL.append(beanData.getCost_mach_hard() + ",");			bufSQL.append(beanData.getCost_pack_blow() + ",");			bufSQL.append(beanData.getCost_pack_inj() + ",");			bufSQL.append(beanData.getCost_pack_bli() + ",");			bufSQL.append(beanData.getCost_3d_hand() + ",");			bufSQL.append(beanData.getDescribes() + ",");			bufSQL.append(beanData.getCost_pat_ext() + ",");			bufSQL.append(beanData.getCost_pat_prac() + ",");			bufSQL.append(beanData.getCost_pat_inv() + ",");			bufSQL.append(beanData.getCost_pat_query() + ",");			bufSQL.append(beanData.getCost_design() + ",");			bufSQL.append(beanData.getPilot_qty() + ",");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROJ_COSTData beanData = (YW_PROJ_COSTData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO YW_PROJ_COST( id,proj_id,cost_mach,cost_mach_gear,cost_mach_bat_pkg,cost_mach_char,cost_mach_diec,cost_mach_hard,cost_pack_blow,cost_pack_inj,cost_pack_bli,cost_3d_hand,describes,cost_pat_ext,cost_pat_prac,cost_pat_inv,cost_pat_query,cost_design,pilot_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			if (beanData.getCost_mach()== null)			{				statement.setNull( 3, Types.FLOAT);			}			else			{				statement.setFloat( 3, beanData.getCost_mach().floatValue());			}			if (beanData.getCost_mach_gear()== null)			{				statement.setNull( 4, Types.FLOAT);			}			else			{				statement.setFloat( 4, beanData.getCost_mach_gear().floatValue());			}			if (beanData.getCost_mach_bat_pkg()== null)			{				statement.setNull( 5, Types.FLOAT);			}			else			{				statement.setFloat( 5, beanData.getCost_mach_bat_pkg().floatValue());			}			if (beanData.getCost_mach_char()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getCost_mach_char().floatValue());			}			if (beanData.getCost_mach_diec()== null)			{				statement.setNull( 7, Types.FLOAT);			}			else			{				statement.setFloat( 7, beanData.getCost_mach_diec().floatValue());			}			if (beanData.getCost_mach_hard()== null)			{				statement.setNull( 8, Types.FLOAT);			}			else			{				statement.setFloat( 8, beanData.getCost_mach_hard().floatValue());			}			if (beanData.getCost_pack_blow()== null)			{				statement.setNull( 9, Types.FLOAT);			}			else			{				statement.setFloat( 9, beanData.getCost_pack_blow().floatValue());			}			if (beanData.getCost_pack_inj()== null)			{				statement.setNull( 10, Types.FLOAT);			}			else			{				statement.setFloat( 10, beanData.getCost_pack_inj().floatValue());			}			if (beanData.getCost_pack_bli()== null)			{				statement.setNull( 11, Types.FLOAT);			}			else			{				statement.setFloat( 11, beanData.getCost_pack_bli().floatValue());			}			if (beanData.getCost_3d_hand()== null)			{				statement.setNull( 12, Types.FLOAT);			}			else			{				statement.setFloat( 12, beanData.getCost_3d_hand().floatValue());			}			if (beanData.getDescribes()== null)			{				statement.setNull( 13, Types.FLOAT);			}			else			{				statement.setFloat( 13, beanData.getDescribes().floatValue());			}			if (beanData.getCost_pat_ext()== null)			{				statement.setNull( 14, Types.FLOAT);			}			else			{				statement.setFloat( 14, beanData.getCost_pat_ext().floatValue());			}			if (beanData.getCost_pat_prac()== null)			{				statement.setNull( 15, Types.FLOAT);			}			else			{				statement.setFloat( 15, beanData.getCost_pat_prac().floatValue());			}			if (beanData.getCost_pat_inv()== null)			{				statement.setNull( 16, Types.FLOAT);			}			else			{				statement.setFloat( 16, beanData.getCost_pat_inv().floatValue());			}			if (beanData.getCost_pat_query()== null)			{				statement.setNull( 17, Types.FLOAT);			}			else			{				statement.setFloat( 17, beanData.getCost_pat_query().floatValue());			}			if (beanData.getCost_design()== null)			{				statement.setNull( 18, Types.FLOAT);			}			else			{				statement.setFloat( 18, beanData.getCost_design().floatValue());			}			statement.setInt( 19,beanData.getPilot_qty());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());			statement.setString( 26,beanData.getDeptguid());			statement.setString( 27,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO YW_PROJ_COST( id,proj_id,cost_mach,cost_mach_gear,cost_mach_bat_pkg,cost_mach_char,cost_mach_diec,cost_mach_hard,cost_pack_blow,cost_pack_inj,cost_pack_bli,cost_3d_hand,describes,cost_pat_ext,cost_pat_prac,cost_pat_inv,cost_pat_query,cost_design,pilot_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		YW_PROJ_COSTData beanData = (YW_PROJ_COSTData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM YW_PROJ_COST WHERE  id =?");
			statement.setString( 1,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROJ_COST: "+ e.toString());
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
		YW_PROJ_COSTData beanData = (YW_PROJ_COSTData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM YW_PROJ_COST WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM YW_PROJ_COST"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM YW_PROJ_COST: "+ e.toString());
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
		YW_PROJ_COSTData beanData = (YW_PROJ_COSTData) beanDataTmp; 
		YW_PROJ_COSTData returnData=new YW_PROJ_COSTData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT id,proj_id,cost_mach,cost_mach_gear,cost_mach_bat_pkg,cost_mach_char,cost_mach_diec,cost_mach_hard,cost_pack_blow,cost_pack_inj,cost_pack_bli,cost_3d_hand,describes,cost_pat_ext,cost_pat_prac,cost_pat_inv,cost_pat_query,cost_design,pilot_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_COST WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setId( resultSet.getString( 1));			returnData.setProj_id( resultSet.getInt( 2));			if (resultSet.getString( 3)==null)				returnData.setCost_mach(null);			else				returnData.setCost_mach( new Float(resultSet.getFloat( 3)));			if (resultSet.getString( 4)==null)				returnData.setCost_mach_gear(null);			else				returnData.setCost_mach_gear( new Float(resultSet.getFloat( 4)));			if (resultSet.getString( 5)==null)				returnData.setCost_mach_bat_pkg(null);			else				returnData.setCost_mach_bat_pkg( new Float(resultSet.getFloat( 5)));			if (resultSet.getString( 6)==null)				returnData.setCost_mach_char(null);			else				returnData.setCost_mach_char( new Float(resultSet.getFloat( 6)));			if (resultSet.getString( 7)==null)				returnData.setCost_mach_diec(null);			else				returnData.setCost_mach_diec( new Float(resultSet.getFloat( 7)));			if (resultSet.getString( 8)==null)				returnData.setCost_mach_hard(null);			else				returnData.setCost_mach_hard( new Float(resultSet.getFloat( 8)));			if (resultSet.getString(9)==null)				returnData.setCost_pack_blow(null);			else				returnData.setCost_pack_blow( new Float(resultSet.getFloat( 9)));			if (resultSet.getString( 10)==null)				returnData.setCost_pack_inj(null);			else				returnData.setCost_pack_inj( new Float(resultSet.getFloat( 10)));			if (resultSet.getString( 11)==null)				returnData.setCost_pack_bli(null);			else				returnData.setCost_pack_bli( new Float(resultSet.getFloat( 11)));			if (resultSet.getString( 12)==null)				returnData.setCost_3d_hand(null);			else				returnData.setCost_3d_hand( new Float(resultSet.getFloat( 12)));			if (resultSet.getString(13)==null)				returnData.setDescribes(null);			else				returnData.setDescribes( new Float(resultSet.getFloat( 13)));			if (resultSet.getString( 14)==null)				returnData.setCost_pat_ext(null);			else				returnData.setCost_pat_ext( new Float(resultSet.getFloat( 14)));			if (resultSet.getString( 15)==null)				returnData.setCost_pat_prac(null);			else				returnData.setCost_pat_prac( new Float(resultSet.getFloat( 15)));			if (resultSet.getString( 16)==null)				returnData.setCost_pat_inv(null);			else				returnData.setCost_pat_inv( new Float(resultSet.getFloat( 16)));			if (resultSet.getString( 17)==null)				returnData.setCost_pat_query(null);			else				returnData.setCost_pat_query( new Float(resultSet.getFloat( 17)));			if (resultSet.getString( 18)==null)				returnData.setCost_design(null);			else				returnData.setCost_design( new Float(resultSet.getFloat( 18)));			returnData.setPilot_qty( resultSet.getInt( 19));			returnData.setCreatetime( resultSet.getString( 20));			returnData.setCreateperson( resultSet.getString( 21));			returnData.setCreateunitid( resultSet.getString( 22));			returnData.setModifytime( resultSet.getString( 23));			returnData.setModifyperson( resultSet.getString( 24));			returnData.setDeleteflag( resultSet.getString( 25));			returnData.setDeptguid( resultSet.getString( 26));			returnData.setFormid( resultSet.getString( 27));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,proj_id,cost_mach,cost_mach_gear,cost_mach_bat_pkg,cost_mach_char,cost_mach_diec,cost_mach_hard,cost_pack_blow,cost_pack_inj,cost_pack_bli,cost_3d_hand,describes,cost_pat_ext,cost_pat_prac,cost_pat_inv,cost_pat_query,cost_design,pilot_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_COST  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT id,proj_id,cost_mach,cost_mach_gear,cost_mach_bat_pkg,cost_mach_char,cost_mach_diec,cost_mach_hard,cost_pack_blow,cost_pack_inj,cost_pack_bli,cost_3d_hand,describes,cost_pat_ext,cost_pat_prac,cost_pat_inv,cost_pat_query,cost_design,pilot_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_COST"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				YW_PROJ_COSTData returnData=new YW_PROJ_COSTData();
				returnData.setId( resultSet.getString( 1));				returnData.setProj_id( resultSet.getInt( 2));				if (resultSet.getString( 3)==null)					returnData.setCost_mach(null);				else					returnData.setCost_mach( new Float(resultSet.getFloat( 3)));				if (resultSet.getString( 4)==null)					returnData.setCost_mach_gear(null);				else					returnData.setCost_mach_gear( new Float(resultSet.getFloat( 4)));				if (resultSet.getString(5)==null)					returnData.setCost_mach_bat_pkg(null);				else					returnData.setCost_mach_bat_pkg( new Float(resultSet.getFloat( 5)));				if (resultSet.getString(6)==null)					returnData.setCost_mach_char(null);				else					returnData.setCost_mach_char( new Float(resultSet.getFloat( 6)));				if (resultSet.getString( 7)==null)					returnData.setCost_mach_diec(null);				else					returnData.setCost_mach_diec( new Float(resultSet.getFloat( 7)));				if (resultSet.getString( 8)==null)					returnData.setCost_mach_hard(null);				else					returnData.setCost_mach_hard( new Float(resultSet.getFloat( 8)));				if (resultSet.getString( 9)==null)					returnData.setCost_pack_blow(null);				else					returnData.setCost_pack_blow( new Float(resultSet.getFloat(9)));				if (resultSet.getString( 10)==null)					returnData.setCost_pack_inj(null);				else					returnData.setCost_pack_inj( new Float(resultSet.getFloat( 10)));				if (resultSet.getString( 11)==null)					returnData.setCost_pack_bli(null);				else					returnData.setCost_pack_bli( new Float(resultSet.getFloat( 11)));				if (resultSet.getString( 12)==null)					returnData.setCost_3d_hand(null);				else					returnData.setCost_3d_hand( new Float(resultSet.getFloat( 12)));				if (resultSet.getString( 13)==null)					returnData.setDescribes(null);				else					returnData.setDescribes( new Float(resultSet.getFloat( 13)));				if (resultSet.getString( 14)==null)					returnData.setCost_pat_ext(null);				else					returnData.setCost_pat_ext( new Float(resultSet.getFloat( 14)));				if (resultSet.getString( 15)==null)					returnData.setCost_pat_prac(null);				else					returnData.setCost_pat_prac( new Float(resultSet.getFloat( 15)));				if (resultSet.getString( 16)==null)					returnData.setCost_pat_inv(null);				else					returnData.setCost_pat_inv( new Float(resultSet.getFloat( 16)));				if (resultSet.getString( 17)==null)					returnData.setCost_pat_query(null);				else					returnData.setCost_pat_query( new Float(resultSet.getFloat( 17)));				if (resultSet.getString(18)==null)					returnData.setCost_design(null);				else					returnData.setCost_design( new Float(resultSet.getFloat( 18)));				returnData.setPilot_qty( resultSet.getInt( 19));				returnData.setCreatetime( resultSet.getString( 20));				returnData.setCreateperson( resultSet.getString( 21));				returnData.setCreateunitid( resultSet.getString( 22));				returnData.setModifytime( resultSet.getString( 23));				returnData.setModifyperson( resultSet.getString( 24));				returnData.setDeleteflag( resultSet.getString( 25));				returnData.setDeptguid( resultSet.getString( 26));				returnData.setFormid( resultSet.getString( 27));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT id,proj_id,cost_mach,cost_mach_gear,cost_mach_bat_pkg,cost_mach_char,cost_mach_diec,cost_mach_hard,cost_pack_blow,cost_pack_inj,cost_pack_bli,cost_3d_hand,describes,cost_pat_ext,cost_pat_prac,cost_pat_inv,cost_pat_query,cost_design,pilot_qty,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,deptguid,formid FROM YW_PROJ_COST" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE YW_PROJ_COST SET id= ? , proj_id= ? , cost_mach= ? , cost_mach_gear= ? , cost_mach_bat_pkg= ? , cost_mach_char= ? , cost_mach_diec= ? , cost_mach_hard= ? , cost_pack_blow= ? , cost_pack_inj= ? , cost_pack_bli= ? , cost_3d_hand= ? , describes= ? , cost_pat_ext= ? , cost_pat_prac= ? , cost_pat_inv= ? , cost_pat_query= ? , cost_design= ? , pilot_qty= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			if (beanData.getCost_mach()== null)			{				statement.setNull( 3, Types.FLOAT);			}			else			{				statement.setFloat( 3, beanData.getCost_mach().floatValue());			}			if (beanData.getCost_mach_gear()== null)			{				statement.setNull( 4, Types.FLOAT);			}			else			{				statement.setFloat( 4, beanData.getCost_mach_gear().floatValue());			}			if (beanData.getCost_mach_bat_pkg()== null)			{				statement.setNull( 5, Types.FLOAT);			}			else			{				statement.setFloat( 5, beanData.getCost_mach_bat_pkg().floatValue());			}			if (beanData.getCost_mach_char()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getCost_mach_char().floatValue());			}			if (beanData.getCost_mach_diec()== null)			{				statement.setNull( 7, Types.FLOAT);			}			else			{				statement.setFloat( 7, beanData.getCost_mach_diec().floatValue());			}			if (beanData.getCost_mach_hard()== null)			{				statement.setNull( 8, Types.FLOAT);			}			else			{				statement.setFloat( 8, beanData.getCost_mach_hard().floatValue());			}			if (beanData.getCost_pack_blow()== null)			{				statement.setNull( 9, Types.FLOAT);			}			else			{				statement.setFloat( 9, beanData.getCost_pack_blow().floatValue());			}			if (beanData.getCost_pack_inj()== null)			{				statement.setNull( 10, Types.FLOAT);			}			else			{				statement.setFloat( 10, beanData.getCost_pack_inj().floatValue());			}			if (beanData.getCost_pack_bli()== null)			{				statement.setNull( 11, Types.FLOAT);			}			else			{				statement.setFloat( 11, beanData.getCost_pack_bli().floatValue());			}			if (beanData.getCost_3d_hand()== null)			{				statement.setNull( 12, Types.FLOAT);			}			else			{				statement.setFloat( 12, beanData.getCost_3d_hand().floatValue());			}			if (beanData.getDescribes()== null)			{				statement.setNull( 13, Types.FLOAT);			}			else			{				statement.setFloat( 13, beanData.getDescribes().floatValue());			}			if (beanData.getCost_pat_ext()== null)			{				statement.setNull( 14, Types.FLOAT);			}			else			{				statement.setFloat( 14, beanData.getCost_pat_ext().floatValue());			}			if (beanData.getCost_pat_prac()== null)			{				statement.setNull( 15, Types.FLOAT);			}			else			{				statement.setFloat( 15, beanData.getCost_pat_prac().floatValue());			}			if (beanData.getCost_pat_inv()== null)			{				statement.setNull( 16, Types.FLOAT);			}			else			{				statement.setFloat( 16, beanData.getCost_pat_inv().floatValue());			}			if (beanData.getCost_pat_query()== null)			{				statement.setNull( 17, Types.FLOAT);			}			else			{				statement.setFloat( 17, beanData.getCost_pat_query().floatValue());			}			if (beanData.getCost_design()== null)			{				statement.setNull( 18, Types.FLOAT);			}			else			{				statement.setFloat( 18, beanData.getCost_design().floatValue());			}			statement.setInt( 19,beanData.getPilot_qty());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());			statement.setString( 26,beanData.getDeptguid());			statement.setString( 27,beanData.getFormid());
			statement.setString( 28,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROJ_COST SET id= ? , proj_id= ? , cost_mach= ? , cost_mach_gear= ? , cost_mach_bat_pkg= ? , cost_mach_char= ? , cost_mach_diec= ? , cost_mach_hard= ? , cost_pack_blow= ? , cost_pack_inj= ? , cost_pack_bli= ? , cost_3d_hand= ? , describes= ? , cost_pat_ext= ? , cost_pat_prac= ? , cost_pat_inv= ? , cost_pat_query= ? , cost_design= ? , pilot_qty= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
			bufSQL.append("UPDATE YW_PROJ_COST SET ");
			bufSQL.append("Id = " + "'" + nullString(beanData.getId()) + "',");			bufSQL.append("Proj_id = " + beanData.getProj_id() + ",");			bufSQL.append("Cost_mach = " + beanData.getCost_mach() + ",");			bufSQL.append("Cost_mach_gear = " + beanData.getCost_mach_gear() + ",");			bufSQL.append("Cost_mach_bat_pkg = " + beanData.getCost_mach_bat_pkg() + ",");			bufSQL.append("Cost_mach_char = " + beanData.getCost_mach_char() + ",");			bufSQL.append("Cost_mach_diec = " + beanData.getCost_mach_diec() + ",");			bufSQL.append("Cost_mach_hard = " + beanData.getCost_mach_hard() + ",");			bufSQL.append("Cost_pack_blow = " + beanData.getCost_pack_blow() + ",");			bufSQL.append("Cost_pack_inj = " + beanData.getCost_pack_inj() + ",");			bufSQL.append("Cost_pack_bli = " + beanData.getCost_pack_bli() + ",");			bufSQL.append("Cost_3d_hand = " + beanData.getCost_3d_hand() + ",");			bufSQL.append("Describes = " + beanData.getDescribes() + ",");			bufSQL.append("Cost_pat_ext = " + beanData.getCost_pat_ext() + ",");			bufSQL.append("Cost_pat_prac = " + beanData.getCost_pat_prac() + ",");			bufSQL.append("Cost_pat_inv = " + beanData.getCost_pat_inv() + ",");			bufSQL.append("Cost_pat_query = " + beanData.getCost_pat_query() + ",");			bufSQL.append("Cost_design = " + beanData.getCost_design() + ",");			bufSQL.append("Pilot_qty = " + beanData.getPilot_qty() + ",");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		YW_PROJ_COSTData beanData = (YW_PROJ_COSTData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE YW_PROJ_COST SET id= ? , proj_id= ? , cost_mach= ? , cost_mach_gear= ? , cost_mach_bat_pkg= ? , cost_mach_char= ? , cost_mach_diec= ? , cost_mach_hard= ? , cost_pack_blow= ? , cost_pack_inj= ? , cost_pack_bli= ? , cost_3d_hand= ? , describes= ? , cost_pat_ext= ? , cost_pat_prac= ? , cost_pat_inv= ? , cost_pat_query= ? , cost_design= ? , pilot_qty= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?");
			statement.setString( 1,beanData.getId());			statement.setInt( 2,beanData.getProj_id());			if (beanData.getCost_mach()== null)			{				statement.setNull( 3, Types.FLOAT);			}			else			{				statement.setFloat( 3, beanData.getCost_mach().floatValue());			}			if (beanData.getCost_mach_gear()== null)			{				statement.setNull( 4, Types.FLOAT);			}			else			{				statement.setFloat( 4, beanData.getCost_mach_gear().floatValue());			}			if (beanData.getCost_mach_bat_pkg()== null)			{				statement.setNull( 5, Types.FLOAT);			}			else			{				statement.setFloat( 5, beanData.getCost_mach_bat_pkg().floatValue());			}			if (beanData.getCost_mach_char()== null)			{				statement.setNull( 6, Types.FLOAT);			}			else			{				statement.setFloat( 6, beanData.getCost_mach_char().floatValue());			}			if (beanData.getCost_mach_diec()== null)			{				statement.setNull( 7, Types.FLOAT);			}			else			{				statement.setFloat( 7, beanData.getCost_mach_diec().floatValue());			}			if (beanData.getCost_mach_hard()== null)			{				statement.setNull( 8, Types.FLOAT);			}			else			{				statement.setFloat( 8, beanData.getCost_mach_hard().floatValue());			}			if (beanData.getCost_pack_blow()== null)			{				statement.setNull( 9, Types.FLOAT);			}			else			{				statement.setFloat( 9, beanData.getCost_pack_blow().floatValue());			}			if (beanData.getCost_pack_inj()== null)			{				statement.setNull( 10, Types.FLOAT);			}			else			{				statement.setFloat( 10, beanData.getCost_pack_inj().floatValue());			}			if (beanData.getCost_pack_bli()== null)			{				statement.setNull( 11, Types.FLOAT);			}			else			{				statement.setFloat( 11, beanData.getCost_pack_bli().floatValue());			}			if (beanData.getCost_3d_hand()== null)			{				statement.setNull( 12, Types.FLOAT);			}			else			{				statement.setFloat( 12, beanData.getCost_3d_hand().floatValue());			}			if (beanData.getDescribes()== null)			{				statement.setNull( 13, Types.FLOAT);			}			else			{				statement.setFloat( 13, beanData.getDescribes().floatValue());			}			if (beanData.getCost_pat_ext()== null)			{				statement.setNull( 14, Types.FLOAT);			}			else			{				statement.setFloat( 14, beanData.getCost_pat_ext().floatValue());			}			if (beanData.getCost_pat_prac()== null)			{				statement.setNull( 15, Types.FLOAT);			}			else			{				statement.setFloat( 15, beanData.getCost_pat_prac().floatValue());			}			if (beanData.getCost_pat_inv()== null)			{				statement.setNull( 16, Types.FLOAT);			}			else			{				statement.setFloat( 16, beanData.getCost_pat_inv().floatValue());			}			if (beanData.getCost_pat_query()== null)			{				statement.setNull( 17, Types.FLOAT);			}			else			{				statement.setFloat( 17, beanData.getCost_pat_query().floatValue());			}			if (beanData.getCost_design()== null)			{				statement.setNull( 18, Types.FLOAT);			}			else			{				statement.setFloat( 18, beanData.getCost_design().floatValue());			}			statement.setInt( 19,beanData.getPilot_qty());			statement.setString( 20,beanData.getCreatetime());			statement.setString( 21,beanData.getCreateperson());			statement.setString( 22,beanData.getCreateunitid());			statement.setString( 23,beanData.getModifytime());			statement.setString( 24,beanData.getModifyperson());			statement.setString( 25,beanData.getDeleteflag());			statement.setString( 26,beanData.getDeptguid());			statement.setString( 27,beanData.getFormid());
			statement.setString( 28,beanData.getId());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE YW_PROJ_COST SET id= ? , proj_id= ? , cost_mach= ? , cost_mach_gear= ? , cost_mach_bat_pkg= ? , cost_mach_char= ? , cost_mach_diec= ? , cost_mach_hard= ? , cost_pack_blow= ? , cost_pack_inj= ? , cost_pack_bli= ? , cost_3d_hand= ? , describes= ? , cost_pat_ext= ? , cost_pat_prac= ? , cost_pat_inv= ? , cost_pat_query= ? , cost_design= ? , pilot_qty= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , deptguid= ? , formid=? WHERE  id  = ?"+ e.toString());
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
		YW_PROJ_COSTData beanData = (YW_PROJ_COSTData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  id  FROM YW_PROJ_COST WHERE  id =?");
			statement.setString( 1,beanData.getId());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  id  FROM YW_PROJ_COST WHERE  id =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
