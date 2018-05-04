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
public class B_PaymentDao extends BaseAbstractDao
{
	public B_PaymentData beanData=new B_PaymentData();
	public B_PaymentDao()
	{
	}
	public B_PaymentDao(Object beanData) throws Exception
	{
		try
		{
			this.beanData = (B_PaymentData)FindByPrimaryKey(beanData);
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
		B_PaymentData beanData = (B_PaymentData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Payment( recordid,paymentid,parentid,subid,requestdate,applicant,deptid,paymentterms,paymentmethod,currency,totalpayable,approvaluser,approvaldate,approvalstatus,approvalfeedback,invoicetype,invoicenumber,finishuser,finishdate,finishstatus,supplierid,contractids,paymenttype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPaymentid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			if (beanData.getRequestdate()==null || beanData.getRequestdate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getRequestdate());			}			statement.setString( 6,beanData.getApplicant());			statement.setString( 7,beanData.getDeptid());			statement.setString( 8,beanData.getPaymentterms());			statement.setString( 9,beanData.getPaymentmethod());			statement.setString( 10,beanData.getCurrency());			statement.setString( 11,beanData.getTotalpayable());			statement.setString( 12,beanData.getApprovaluser());			if (beanData.getApprovaldate()==null || beanData.getApprovaldate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getApprovaldate());			}			statement.setString( 14,beanData.getApprovalstatus());			statement.setString( 15,beanData.getApprovalfeedback());			statement.setString( 16,beanData.getInvoicetype());			statement.setString( 17,beanData.getInvoicenumber());			statement.setString( 18,beanData.getFinishuser());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 19, Types.DATE);			}			else			{				statement.setString( 19, beanData.getFinishdate());			}			statement.setString( 20,beanData.getFinishstatus());			statement.setString( 21,beanData.getSupplierid());			statement.setString( 22,beanData.getContractids());			statement.setString( 23,beanData.getPaymenttype());			statement.setString( 24,beanData.getDeptguid());			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Payment( recordid,paymentid,parentid,subid,requestdate,applicant,deptid,paymentterms,paymentmethod,currency,totalpayable,approvaluser,approvaldate,approvalstatus,approvalfeedback,invoicetype,invoicenumber,finishuser,finishdate,finishstatus,supplierid,contractids,paymenttype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
			bufSQL.append("INSERT INTO B_Payment( recordid,paymentid,parentid,subid,requestdate,applicant,deptid,paymentterms,paymentmethod,currency,totalpayable,approvaluser,approvaldate,approvalstatus,approvalfeedback,invoicetype,invoicenumber,finishuser,finishdate,finishstatus,supplierid,contractids,paymenttype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(");
			bufSQL.append("'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentid()) + "',");			bufSQL.append("'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("'" + nullString(beanData.getRequestdate()) + "',");			bufSQL.append("'" + nullString(beanData.getApplicant()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptid()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentterms()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymentmethod()) + "',");			bufSQL.append("'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("'" + nullString(beanData.getTotalpayable()) + "',");			bufSQL.append("'" + nullString(beanData.getApprovaluser()) + "',");			bufSQL.append("'" + nullString(beanData.getApprovaldate()) + "',");			bufSQL.append("'" + nullString(beanData.getApprovalstatus()) + "',");			bufSQL.append("'" + nullString(beanData.getApprovalfeedback()) + "',");			bufSQL.append("'" + nullString(beanData.getInvoicetype()) + "',");			bufSQL.append("'" + nullString(beanData.getInvoicenumber()) + "',");			bufSQL.append("'" + nullString(beanData.getFinishuser()) + "',");			bufSQL.append("'" + nullString(beanData.getFinishdate()) + "',");			bufSQL.append("'" + nullString(beanData.getFinishstatus()) + "',");			bufSQL.append("'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("'" + nullString(beanData.getContractids()) + "',");			bufSQL.append("'" + nullString(beanData.getPaymenttype()) + "',");			bufSQL.append("'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("'" + nullString(beanData.getFormid()) + "'");
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
		B_PaymentData beanData = (B_PaymentData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("INSERT INTO B_Payment( recordid,paymentid,parentid,subid,requestdate,applicant,deptid,paymentterms,paymentmethod,currency,totalpayable,approvaluser,approvaldate,approvalstatus,approvalfeedback,invoicetype,invoicenumber,finishuser,finishdate,finishstatus,supplierid,contractids,paymenttype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPaymentid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			if (beanData.getRequestdate()==null || beanData.getRequestdate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getRequestdate());			}			statement.setString( 6,beanData.getApplicant());			statement.setString( 7,beanData.getDeptid());			statement.setString( 8,beanData.getPaymentterms());			statement.setString( 9,beanData.getPaymentmethod());			statement.setString( 10,beanData.getCurrency());			statement.setString( 11,beanData.getTotalpayable());			statement.setString( 12,beanData.getApprovaluser());			if (beanData.getApprovaldate()==null || beanData.getApprovaldate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getApprovaldate());			}			statement.setString( 14,beanData.getApprovalstatus());			statement.setString( 15,beanData.getApprovalfeedback());			statement.setString( 16,beanData.getInvoicetype());			statement.setString( 17,beanData.getInvoicenumber());			statement.setString( 18,beanData.getFinishuser());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 19, Types.DATE);			}			else			{				statement.setString( 19, beanData.getFinishdate());			}			statement.setString( 20,beanData.getFinishstatus());			statement.setString( 21,beanData.getSupplierid());			statement.setString( 22,beanData.getContractids());			statement.setString( 23,beanData.getPaymenttype());			statement.setString( 24,beanData.getDeptguid());			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getFormid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Can't Insert Row ");
			else
				return "Create success";
		}
		catch(Exception e)
		{
			throw new Exception("INSERT INTO B_Payment( recordid,paymentid,parentid,subid,requestdate,applicant,deptid,paymentterms,paymentmethod,currency,totalpayable,approvaluser,approvaldate,approvalstatus,approvalfeedback,invoicetype,invoicenumber,finishuser,finishdate,finishstatus,supplierid,contractids,paymenttype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)��"+ e.toString());
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
		B_PaymentData beanData = (B_PaymentData) beanDataTmp; 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("DELETE FROM B_Payment WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Payment: "+ e.toString());
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
		B_PaymentData beanData = (B_PaymentData) beanDataTmp; 
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("DELETE FROM B_Payment WHERE ");
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
			statement = connection.prepareStatement("DELETE FROM B_Payment"+ str_Where);
			if (statement.executeUpdate() < 1)
				throw new Exception("Error deleting row");
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL DELETE FROM B_Payment: "+ e.toString());
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
		B_PaymentData beanData = (B_PaymentData) beanDataTmp; 
		B_PaymentData returnData=new B_PaymentData();
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT recordid,paymentid,parentid,subid,requestdate,applicant,deptid,paymentterms,paymentmethod,currency,totalpayable,approvaluser,approvaldate,approvalstatus,approvalfeedback,invoicetype,invoicenumber,finishuser,finishdate,finishstatus,supplierid,contractids,paymenttype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Payment WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Row Not does;");
			}
			returnData.setRecordid( resultSet.getString( 1));			returnData.setPaymentid( resultSet.getString( 2));			returnData.setParentid( resultSet.getString( 3));			returnData.setSubid( resultSet.getString( 4));			returnData.setRequestdate( resultSet.getString( 5));			returnData.setApplicant( resultSet.getString( 6));			returnData.setDeptid( resultSet.getString( 7));			returnData.setPaymentterms( resultSet.getString( 8));			returnData.setPaymentmethod( resultSet.getString( 9));			returnData.setCurrency( resultSet.getString( 10));			returnData.setTotalpayable( resultSet.getString( 11));			returnData.setApprovaluser( resultSet.getString( 12));			returnData.setApprovaldate( resultSet.getString( 13));			returnData.setApprovalstatus( resultSet.getString( 14));			returnData.setApprovalfeedback( resultSet.getString( 15));			returnData.setInvoicetype( resultSet.getString( 16));			returnData.setInvoicenumber( resultSet.getString( 17));			returnData.setFinishuser( resultSet.getString( 18));			returnData.setFinishdate( resultSet.getString( 19));			returnData.setFinishstatus( resultSet.getString( 20));			returnData.setSupplierid( resultSet.getString( 21));			returnData.setContractids( resultSet.getString( 22));			returnData.setPaymenttype( resultSet.getString( 23));			returnData.setDeptguid( resultSet.getString( 24));			returnData.setCreatetime( resultSet.getString( 25));			returnData.setCreateperson( resultSet.getString( 26));			returnData.setCreateunitid( resultSet.getString( 27));			returnData.setModifytime( resultSet.getString( 28));			returnData.setModifyperson( resultSet.getString( 29));			returnData.setDeleteflag( resultSet.getString( 30));			returnData.setFormid( resultSet.getString( 31));
			return returnData;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,paymentid,parentid,subid,requestdate,applicant,deptid,paymentterms,paymentmethod,currency,totalpayable,approvaluser,approvaldate,approvalstatus,approvalfeedback,invoicetype,invoicenumber,finishuser,finishdate,finishstatus,supplierid,contractids,paymenttype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Payment  WHERE  "+e.toString());
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
			statement = connection.prepareStatement("SELECT recordid,paymentid,parentid,subid,requestdate,applicant,deptid,paymentterms,paymentmethod,currency,totalpayable,approvaluser,approvaldate,approvalstatus,approvalfeedback,invoicetype,invoicenumber,finishuser,finishdate,finishstatus,supplierid,contractids,paymenttype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Payment"+str_Where);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				B_PaymentData returnData=new B_PaymentData();
				returnData.setRecordid( resultSet.getString( 1));				returnData.setPaymentid( resultSet.getString( 2));				returnData.setParentid( resultSet.getString( 3));				returnData.setSubid( resultSet.getString( 4));				returnData.setRequestdate( resultSet.getString( 5));				returnData.setApplicant( resultSet.getString( 6));				returnData.setDeptid( resultSet.getString( 7));				returnData.setPaymentterms( resultSet.getString( 8));				returnData.setPaymentmethod( resultSet.getString( 9));				returnData.setCurrency( resultSet.getString( 10));				returnData.setTotalpayable( resultSet.getString( 11));				returnData.setApprovaluser( resultSet.getString( 12));				returnData.setApprovaldate( resultSet.getString( 13));				returnData.setApprovalstatus( resultSet.getString( 14));				returnData.setApprovalfeedback( resultSet.getString( 15));				returnData.setInvoicetype( resultSet.getString( 16));				returnData.setInvoicenumber( resultSet.getString( 17));				returnData.setFinishuser( resultSet.getString( 18));				returnData.setFinishdate( resultSet.getString( 19));				returnData.setFinishstatus( resultSet.getString( 20));				returnData.setSupplierid( resultSet.getString( 21));				returnData.setContractids( resultSet.getString( 22));				returnData.setPaymenttype( resultSet.getString( 23));				returnData.setDeptguid( resultSet.getString( 24));				returnData.setCreatetime( resultSet.getString( 25));				returnData.setCreateperson( resultSet.getString( 26));				returnData.setCreateunitid( resultSet.getString( 27));				returnData.setModifytime( resultSet.getString( 28));				returnData.setModifyperson( resultSet.getString( 29));				returnData.setDeleteflag( resultSet.getString( 30));				returnData.setFormid( resultSet.getString( 31));
				v_1.add(returnData);
			}
			return v_1;
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL SELECT recordid,paymentid,parentid,subid,requestdate,applicant,deptid,paymentterms,paymentmethod,currency,totalpayable,approvaluser,approvaldate,approvalstatus,approvalfeedback,invoicetype,invoicenumber,finishuser,finishdate,finishstatus,supplierid,contractids,paymenttype,deptguid,createtime,createperson,createunitid,modifytime,modifyperson,deleteflag,formid FROM B_Payment" + astr_Where +e.toString());
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
			statement = connection.prepareStatement("UPDATE B_Payment SET recordid= ? , paymentid= ? , parentid= ? , subid= ? , requestdate= ? , applicant= ? , deptid= ? , paymentterms= ? , paymentmethod= ? , currency= ? , totalpayable= ? , approvaluser= ? , approvaldate= ? , approvalstatus= ? , approvalfeedback= ? , invoicetype= ? , invoicenumber= ? , finishuser= ? , finishdate= ? , finishstatus= ? , supplierid= ? , contractids= ? , paymenttype= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPaymentid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			if (beanData.getRequestdate()==null || beanData.getRequestdate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getRequestdate());			}			statement.setString( 6,beanData.getApplicant());			statement.setString( 7,beanData.getDeptid());			statement.setString( 8,beanData.getPaymentterms());			statement.setString( 9,beanData.getPaymentmethod());			statement.setString( 10,beanData.getCurrency());			statement.setString( 11,beanData.getTotalpayable());			statement.setString( 12,beanData.getApprovaluser());			if (beanData.getApprovaldate()==null || beanData.getApprovaldate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getApprovaldate());			}			statement.setString( 14,beanData.getApprovalstatus());			statement.setString( 15,beanData.getApprovalfeedback());			statement.setString( 16,beanData.getInvoicetype());			statement.setString( 17,beanData.getInvoicenumber());			statement.setString( 18,beanData.getFinishuser());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 19, Types.DATE);			}			else			{				statement.setString( 19, beanData.getFinishdate());			}			statement.setString( 20,beanData.getFinishstatus());			statement.setString( 21,beanData.getSupplierid());			statement.setString( 22,beanData.getContractids());			statement.setString( 23,beanData.getPaymenttype());			statement.setString( 24,beanData.getDeptguid());			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getFormid());
			statement.setString( 32,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Payment SET recordid= ? , paymentid= ? , parentid= ? , subid= ? , requestdate= ? , applicant= ? , deptid= ? , paymentterms= ? , paymentmethod= ? , currency= ? , totalpayable= ? , approvaluser= ? , approvaldate= ? , approvalstatus= ? , approvalfeedback= ? , invoicetype= ? , invoicenumber= ? , finishuser= ? , finishdate= ? , finishstatus= ? , supplierid= ? , contractids= ? , paymenttype= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
			bufSQL.append("UPDATE B_Payment SET ");
			bufSQL.append("Recordid = " + "'" + nullString(beanData.getRecordid()) + "',");			bufSQL.append("Paymentid = " + "'" + nullString(beanData.getPaymentid()) + "',");			bufSQL.append("Parentid = " + "'" + nullString(beanData.getParentid()) + "',");			bufSQL.append("Subid = " + "'" + nullString(beanData.getSubid()) + "',");			bufSQL.append("Requestdate = " + "'" + nullString(beanData.getRequestdate()) + "',");			bufSQL.append("Applicant = " + "'" + nullString(beanData.getApplicant()) + "',");			bufSQL.append("Deptid = " + "'" + nullString(beanData.getDeptid()) + "',");			bufSQL.append("Paymentterms = " + "'" + nullString(beanData.getPaymentterms()) + "',");			bufSQL.append("Paymentmethod = " + "'" + nullString(beanData.getPaymentmethod()) + "',");			bufSQL.append("Currency = " + "'" + nullString(beanData.getCurrency()) + "',");			bufSQL.append("Totalpayable = " + "'" + nullString(beanData.getTotalpayable()) + "',");			bufSQL.append("Approvaluser = " + "'" + nullString(beanData.getApprovaluser()) + "',");			bufSQL.append("Approvaldate = " + "'" + nullString(beanData.getApprovaldate()) + "',");			bufSQL.append("Approvalstatus = " + "'" + nullString(beanData.getApprovalstatus()) + "',");			bufSQL.append("Approvalfeedback = " + "'" + nullString(beanData.getApprovalfeedback()) + "',");			bufSQL.append("Invoicetype = " + "'" + nullString(beanData.getInvoicetype()) + "',");			bufSQL.append("Invoicenumber = " + "'" + nullString(beanData.getInvoicenumber()) + "',");			bufSQL.append("Finishuser = " + "'" + nullString(beanData.getFinishuser()) + "',");			bufSQL.append("Finishdate = " + "'" + nullString(beanData.getFinishdate()) + "',");			bufSQL.append("Finishstatus = " + "'" + nullString(beanData.getFinishstatus()) + "',");			bufSQL.append("Supplierid = " + "'" + nullString(beanData.getSupplierid()) + "',");			bufSQL.append("Contractids = " + "'" + nullString(beanData.getContractids()) + "',");			bufSQL.append("Paymenttype = " + "'" + nullString(beanData.getPaymenttype()) + "',");			bufSQL.append("Deptguid = " + "'" + nullString(beanData.getDeptguid()) + "',");			bufSQL.append("Createtime = " + "'" + nullString(beanData.getCreatetime()) + "',");			bufSQL.append("Createperson = " + "'" + nullString(beanData.getCreateperson()) + "',");			bufSQL.append("Createunitid = " + "'" + nullString(beanData.getCreateunitid()) + "',");			bufSQL.append("Modifytime = " + "'" + nullString(beanData.getModifytime()) + "',");			bufSQL.append("Modifyperson = " + "'" + nullString(beanData.getModifyperson()) + "',");			bufSQL.append("Deleteflag = " + "'" + nullString(beanData.getDeleteflag()) + "',");			bufSQL.append("Formid = " + "'" + nullString(beanData.getFormid()) + "'");
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
		B_PaymentData beanData = (B_PaymentData)data;
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = getConnection();
			statement = connection.prepareStatement("UPDATE B_Payment SET recordid= ? , paymentid= ? , parentid= ? , subid= ? , requestdate= ? , applicant= ? , deptid= ? , paymentterms= ? , paymentmethod= ? , currency= ? , totalpayable= ? , approvaluser= ? , approvaldate= ? , approvalstatus= ? , approvalfeedback= ? , invoicetype= ? , invoicenumber= ? , finishuser= ? , finishdate= ? , finishstatus= ? , supplierid= ? , contractids= ? , paymenttype= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?");
			statement.setString( 1,beanData.getRecordid());			statement.setString( 2,beanData.getPaymentid());			statement.setString( 3,beanData.getParentid());			statement.setString( 4,beanData.getSubid());			if (beanData.getRequestdate()==null || beanData.getRequestdate().trim().equals(""))			{				statement.setNull( 5, Types.DATE);			}			else			{				statement.setString( 5, beanData.getRequestdate());			}			statement.setString( 6,beanData.getApplicant());			statement.setString( 7,beanData.getDeptid());			statement.setString( 8,beanData.getPaymentterms());			statement.setString( 9,beanData.getPaymentmethod());			statement.setString( 10,beanData.getCurrency());			statement.setString( 11,beanData.getTotalpayable());			statement.setString( 12,beanData.getApprovaluser());			if (beanData.getApprovaldate()==null || beanData.getApprovaldate().trim().equals(""))			{				statement.setNull( 13, Types.DATE);			}			else			{				statement.setString( 13, beanData.getApprovaldate());			}			statement.setString( 14,beanData.getApprovalstatus());			statement.setString( 15,beanData.getApprovalfeedback());			statement.setString( 16,beanData.getInvoicetype());			statement.setString( 17,beanData.getInvoicenumber());			statement.setString( 18,beanData.getFinishuser());			if (beanData.getFinishdate()==null || beanData.getFinishdate().trim().equals(""))			{				statement.setNull( 19, Types.DATE);			}			else			{				statement.setString( 19, beanData.getFinishdate());			}			statement.setString( 20,beanData.getFinishstatus());			statement.setString( 21,beanData.getSupplierid());			statement.setString( 22,beanData.getContractids());			statement.setString( 23,beanData.getPaymenttype());			statement.setString( 24,beanData.getDeptguid());			statement.setString( 25,beanData.getCreatetime());			statement.setString( 26,beanData.getCreateperson());			statement.setString( 27,beanData.getCreateunitid());			statement.setString( 28,beanData.getModifytime());			statement.setString( 29,beanData.getModifyperson());			statement.setString( 30,beanData.getDeleteflag());			statement.setString( 31,beanData.getFormid());
			statement.setString( 32,beanData.getRecordid());
			if (statement.executeUpdate() < 1)
				throw new Exception(" Row Not does; ");
		}
		catch(Exception e)
		{
			throw new Exception("UPDATE B_Payment SET recordid= ? , paymentid= ? , parentid= ? , subid= ? , requestdate= ? , applicant= ? , deptid= ? , paymentterms= ? , paymentmethod= ? , currency= ? , totalpayable= ? , approvaluser= ? , approvaldate= ? , approvalstatus= ? , approvalfeedback= ? , invoicetype= ? , invoicenumber= ? , finishuser= ? , finishdate= ? , finishstatus= ? , supplierid= ? , contractids= ? , paymenttype= ? , deptguid= ? , createtime= ? , createperson= ? , createunitid= ? , modifytime= ? , modifyperson= ? , deleteflag= ? , formid=? WHERE  recordid  = ?"+ e.toString());
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
		B_PaymentData beanData = (B_PaymentData) beanDataTmp; 
			connection = getConnection();
			statement = connection.prepareStatement("SELECT  recordid  FROM B_Payment WHERE  recordid =?");
			statement.setString( 1,beanData.getRecordid());
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
			{
				throw new Exception(" Primary Key Not does");
			}
		}
		catch(Exception e)
		{
			throw new Exception("Error executing SQL  SELECT  recordid  FROM B_Payment WHERE  recordid =?");
		}
		finally
		{
			closeConnection(connection, statement);
		}
	}

}
