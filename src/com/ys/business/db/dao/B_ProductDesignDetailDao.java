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
public class B_ProductDesignDetailDao extends BaseAbstractDao
{
	public B_ProductDesignDetailData beanData=new B_ProductDesignDetailData();
	public B_ProductDesignDetailDao()
	{
	}
	public B_ProductDesignDetailDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_ProductDesignDetailData)FindByPrimaryKey(beanData);
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
		B_ProductDesignDetailData beanData = (B_ProductDesignDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ProductDesignDetail( recordid,productdetailid,type,componentname,materialid,supplierid,purchaser,materialquality,color,specification,process,size,packingqty,filename,remark,sortno,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getProductdetailid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getComponentname());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getSupplierid());			statement.setString( 7,beanData.getPurchaser());			statement.setString( 8,beanData.getMaterialquality());			statement.setString( 9,beanData.getColor());			statement.setString( 10,beanData.getSpecification());			statement.setString( 11,beanData.getProcess());			statement.setString( 12,beanData.getSize());			statement.setString( 13,beanData.getPackingqty());			statement.setString( 14,beanData.getFilename());			statement.setString( 15,beanData.getRemark());			statement.setString( 16,beanData.getSortno());			statement.setString( 17,beanData.getDeptguid());			statement.setString( 18,beanData.getCreatetime());			statement.setString( 19,beanData.getCreateperson());			statement.setString( 20,beanData.getCreateunitid());			statement.setString( 21,beanData.getModifytime());			statement.setString( 22,beanData.getModifyperson());			statement.setString( 23,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ProductDesignDetail( recordid,productdetailid,type,componentname,materialid,supplierid,purchaser,materialquality,color,specification,process,size,packingqty,filename,remark,sortno,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_ProductDesignDetail( recordid,productdetailid,type,componentname,materialid,supplierid,purchaser,materialquality,color,specification,process,size,packingqty,filename,remark,sortno,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getProductdetailid()) + "',");			bufSQL.append("'" + nullString(beanData.getType()) + "',");			bufSQL.append("'" + nullString(beanData.getComponentname()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getPurchaser()) + "',");			bufSQL.append("'" + nullString(beanData.getMaterialquality()) + "',");			bufSQL.append("'" + nullString(beanData.getColor()) + "',");			bufSQL.append("'" + nullString(beanData.getSpecification()) + "',");			bufSQL.append("'" + nullString(beanData.getProcess()) + "',");			bufSQL.append("'" + nullString(beanData.getSize()) + "',");			bufSQL.append("'" + nullString(beanData.getPackingqty()) + "',");			bufSQL.append("'" + nullString(beanData.getFilename()) + "',");			bufSQL.append("'" + nullString(beanData.getRemark()) + "',");			bufSQL.append("'" + nullString(beanData.getSortno()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_ProductDesignDetailData beanData = (B_ProductDesignDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_ProductDesignDetail( recordid,productdetailid,type,componentname,materialid,supplierid,purchaser,materialquality,color,specification,process,size,packingqty,filename,remark,sortno,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getProductdetailid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getComponentname());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getSupplierid());			statement.setString( 7,beanData.getPurchaser());			statement.setString( 8,beanData.getMaterialquality());			statement.setString( 9,beanData.getColor());			statement.setString( 10,beanData.getSpecification());			statement.setString( 11,beanData.getProcess());			statement.setString( 12,beanData.getSize());			statement.setString( 13,beanData.getPackingqty());			statement.setString( 14,beanData.getFilename());			statement.setString( 15,beanData.getRemark());			statement.setString( 16,beanData.getSortno());			statement.setString( 17,beanData.getDeptguid());			statement.setString( 18,beanData.getCreatetime());			statement.setString( 19,beanData.getCreateperson());			statement.setString( 20,beanData.getCreateunitid());			statement.setString( 21,beanData.getModifytime());			statement.setString( 22,beanData.getModifyperson());			statement.setString( 23,beanData.getDeleteflag());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_ProductDesignDetail( recordid,productdetailid,type,componentname,materialid,supplierid,purchaser,materialquality,color,specification,process,size,packingqty,filename,remark,sortno,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_ProductDesignDetailData beanData = (B_ProductDesignDetailData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_ProductDesignDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ProductDesignDetail: "+ e.toString());
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
		B_ProductDesignDetailData beanData = (B_ProductDesignDetailData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_ProductDesignDetail WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_ProductDesignDetail"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_ProductDesignDetail: "+ e.toString());
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
		B_ProductDesignDetailData beanData = (B_ProductDesignDetailData) beanDataTmp; 
		B_ProductDesignDetailData returnData=new B_ProductDesignDetailData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,productdetailid,type,componentname,materialid,supplierid,purchaser,materialquality,color,specification,process,size,packingqty,filename,remark,sortno,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProductDesignDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setProductdetailid( resultSet.getString( 2));			returnData.setType( resultSet.getString( 3));			returnData.setComponentname( resultSet.getString( 4));			returnData.setMaterialid( resultSet.getString( 5));			returnData.setSupplierid( resultSet.getString( 6));			returnData.setPurchaser( resultSet.getString( 7));			returnData.setMaterialquality( resultSet.getString( 8));			returnData.setColor( resultSet.getString( 9));			returnData.setSpecification( resultSet.getString( 10));			returnData.setProcess( resultSet.getString( 11));			returnData.setSize( resultSet.getString( 12));			returnData.setPackingqty( resultSet.getString( 13));			returnData.setFilename( resultSet.getString( 14));			returnData.setRemark( resultSet.getString( 15));			returnData.setSortno( resultSet.getString( 16));			returnData.setDeptguid( resultSet.getString( 17));			returnData.setCreatetime( resultSet.getString( 18));			returnData.setCreateperson( resultSet.getString( 19));			returnData.setCreateunitid( resultSet.getString( 20));			returnData.setModifytime( resultSet.getString( 21));			returnData.setModifyperson( resultSet.getString( 22));			returnData.setDeleteflag( resultSet.getString( 23));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,productdetailid,type,componentname,materialid,supplierid,purchaser,materialquality,color,specification,process,size,packingqty,filename,remark,sortno,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProductDesignDetail  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,productdetailid,type,componentname,materialid,supplierid,purchaser,materialquality,color,specification,process,size,packingqty,filename,remark,sortno,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProductDesignDetail"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_ProductDesignDetailData returnData=new B_ProductDesignDetailData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setProductdetailid( resultSet.getString( 2));				returnData.setType( resultSet.getString( 3));				returnData.setComponentname( resultSet.getString( 4));				returnData.setMaterialid( resultSet.getString( 5));				returnData.setSupplierid( resultSet.getString( 6));				returnData.setPurchaser( resultSet.getString( 7));				returnData.setMaterialquality( resultSet.getString( 8));				returnData.setColor( resultSet.getString( 9));				returnData.setSpecification( resultSet.getString( 10));				returnData.setProcess( resultSet.getString( 11));				returnData.setSize( resultSet.getString( 12));				returnData.setPackingqty( resultSet.getString( 13));				returnData.setFilename( resultSet.getString( 14));				returnData.setRemark( resultSet.getString( 15));				returnData.setSortno( resultSet.getString( 16));				returnData.setDeptguid( resultSet.getString( 17));				returnData.setCreatetime( resultSet.getString( 18));				returnData.setCreateperson( resultSet.getString( 19));				returnData.setCreateunitid( resultSet.getString( 20));				returnData.setModifytime( resultSet.getString( 21));				returnData.setModifyperson( resultSet.getString( 22));				returnData.setDeleteflag( resultSet.getString( 23));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,productdetailid,type,componentname,materialid,supplierid,purchaser,materialquality,color,specification,process,size,packingqty,filename,remark,sortno,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag FROM B_ProductDesignDetail" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_ProductDesignDetail SET recordid= ? , productdetailid= ? , type= ? , componentname= ? , materialid= ? , supplierid= ? , purchaser= ? , materialquality= ? , color= ? , specification= ? , process= ? , size= ? , packingqty= ? , filename= ? , remark= ? , sortno= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getProductdetailid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getComponentname());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getSupplierid());			statement.setString( 7,beanData.getPurchaser());			statement.setString( 8,beanData.getMaterialquality());			statement.setString( 9,beanData.getColor());			statement.setString( 10,beanData.getSpecification());			statement.setString( 11,beanData.getProcess());			statement.setString( 12,beanData.getSize());			statement.setString( 13,beanData.getPackingqty());			statement.setString( 14,beanData.getFilename());			statement.setString( 15,beanData.getRemark());			statement.setString( 16,beanData.getSortno());			statement.setString( 17,beanData.getDeptguid());			statement.setString( 18,beanData.getCreatetime());			statement.setString( 19,beanData.getCreateperson());			statement.setString( 20,beanData.getCreateunitid());			statement.setString( 21,beanData.getModifytime());			statement.setString( 22,beanData.getModifyperson());			statement.setString( 23,beanData.getDeleteflag());
			statement.setString( 24,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ProductDesignDetail SET recordid= ? , productdetailid= ? , type= ? , componentname= ? , materialid= ? , supplierid= ? , purchaser= ? , materialquality= ? , color= ? , specification= ? , process= ? , size= ? , packingqty= ? , filename= ? , remark= ? , sortno= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_ProductDesignDetail SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Productdetailid = " + "'" + nullString(beanData.getProductdetailid()) + "',");			bufSQL.append("Type = " + "'" + nullString(beanData.getType()) + "',");			bufSQL.append("Componentname = " + "'" + nullString(beanData.getComponentname()) + "',");			bufSQL.append("Materialid = " + "'" + nullString(beanData.getMaterialid()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Purchaser = " + "'" + nullString(beanData.getPurchaser()) + "',");			bufSQL.append("Materialquality = " + "'" + nullString(beanData.getMaterialquality()) + "',");			bufSQL.append("Color = " + "'" + nullString(beanData.getColor()) + "',");			bufSQL.append("Specification = " + "'" + nullString(beanData.getSpecification()) + "',");			bufSQL.append("Process = " + "'" + nullString(beanData.getProcess()) + "',");			bufSQL.append("Size = " + "'" + nullString(beanData.getSize()) + "',");			bufSQL.append("Packingqty = " + "'" + nullString(beanData.getPackingqty()) + "',");			bufSQL.append("Filename = " + "'" + nullString(beanData.getFilename()) + "',");			bufSQL.append("Remark = " + "'" + nullString(beanData.getRemark()) + "',");			bufSQL.append("Sortno = " + "'" + nullString(beanData.getSortno()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "'");
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
		B_ProductDesignDetailData beanData = (B_ProductDesignDetailData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_ProductDesignDetail SET recordid= ? , productdetailid= ? , type= ? , componentname= ? , materialid= ? , supplierid= ? , purchaser= ? , materialquality= ? , color= ? , specification= ? , process= ? , size= ? , packingqty= ? , filename= ? , remark= ? , sortno= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getProductdetailid());			statement.setString( 3,beanData.getType());			statement.setString( 4,beanData.getComponentname());			statement.setString( 5,beanData.getMaterialid());			statement.setString( 6,beanData.getSupplierid());			statement.setString( 7,beanData.getPurchaser());			statement.setString( 8,beanData.getMaterialquality());			statement.setString( 9,beanData.getColor());			statement.setString( 10,beanData.getSpecification());			statement.setString( 11,beanData.getProcess());			statement.setString( 12,beanData.getSize());			statement.setString( 13,beanData.getPackingqty());			statement.setString( 14,beanData.getFilename());			statement.setString( 15,beanData.getRemark());			statement.setString( 16,beanData.getSortno());			statement.setString( 17,beanData.getDeptguid());			statement.setString( 18,beanData.getCreatetime());			statement.setString( 19,beanData.getCreateperson());			statement.setString( 20,beanData.getCreateunitid());			statement.setString( 21,beanData.getModifytime());			statement.setString( 22,beanData.getModifyperson());			statement.setString( 23,beanData.getDeleteflag());
			statement.setString( 24,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_ProductDesignDetail SET recordid= ? , productdetailid= ? , type= ? , componentname= ? , materialid= ? , supplierid= ? , purchaser= ? , materialquality= ? , color= ? , specification= ? , process= ? , size= ? , packingqty= ? , filename= ? , remark= ? , sortno= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag=? WHERE  recordid  = ?"+ e.toString());
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
		B_ProductDesignDetailData beanData = (B_ProductDesignDetailData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_ProductDesignDetail WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_ProductDesignDetail WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
