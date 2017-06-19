package com.ys.util.basedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.dom4j.Element;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import com.ys.util.DicUtil;
import com.ys.util.XmlUtil;
import com.ys.util.basequery.QueryInfoBean;
import com.ys.util.basequery.QueryPageSumBean;
import com.ys.util.basequery.QuerySelectBean;
import com.ys.util.basequery.QuerySelectSubBean;

public class BaseDAO {
	
	private static final String DATASOURCEDEFINITION = "app-context.xml";
	private static final String SYSBASEDAOCONFIGFILE = "/setting/Sys_BaseDaoConfig.xml";
	private static final String DEFAULTDATADEFINE     = "default";
	
	private static HashMap<String, SysBaseDaoDefineBean> dataDefineMap = new HashMap<String, SysBaseDaoDefineBean>();
	
	private final static ThreadLocal dsLocal = new ThreadLocal();
	
	public static DataSource getDataSource() throws Exception {
		//default
		DataSource dataSource = null;
		
	    //DataSource dataSource = (DataSource)XmlUtil.getContent(DATASOURCEDEFINITION, DEFAULTDATASOURCE);
	    //connection = dataSource.getConnection(); 
		
		
		dataSource = getDataSource(DEFAULTDATADEFINE);
		
		return dataSource;
	}
	/*
	public static DataSource getDataSource(String name) throws Exception {
		//default
		DataSource dataSource = null;
		
		SysBaseDaoDefineBean defineBean = null;
		
		if (!dataDefineMap.containsKey(name)) {
			dataDefineMap = getPrivateDaoConfig(SYSBASEDAOCONFIGFILE, name);
		}
		if (dataDefineMap.containsKey(name)) {
			defineBean = (SysBaseDaoDefineBean)dataDefineMap.get(name);
		}
		if (defineBean != null) {
			if (defineBean.getIsPool().toUpperCase().equals("T")) {
			    dataSource = (DataSource)XmlUtil.getContent(DATASOURCEDEFINITION, defineBean.getName());
			}
		}
		
		return dataSource;
	}	
	*/
	public static Connection getConnection() throws Exception {
		//default
		Connection connection = null;
		
	    //DataSource dataSource = (DataSource)XmlUtil.getContent(DATASOURCEDEFINITION, DEFAULTDATASOURCE);
	    //connection = dataSource.getConnection(); 
		
		connection = getConnection(DEFAULTDATADEFINE);
		
		return connection;
	}
	
	public static DataSource getDataSource(String name) throws Exception {
		
		SysBaseDaoDefineBean defineBean = null;
		DataSource dataSource = null;
		
		if (name == null || name.equals("")) {
			name = DEFAULTDATADEFINE;
		}
		
		if (!dataDefineMap.containsKey(name)) {
			dataDefineMap = getPrivateDaoConfig(SYSBASEDAOCONFIGFILE, name);
		}
		if (dataDefineMap.containsKey(name)) {
			defineBean = (SysBaseDaoDefineBean)dataDefineMap.get(name);
		}
		if (defineBean != null) {
			if (defineBean.getIsPool().toUpperCase().equals("T")) {
				HashMap<String, DataSource> dsMap = (HashMap<String, DataSource>)dsLocal.get();
				
				if (dsMap != null && dsMap.containsKey(name)) {
					dataSource = dsMap.get(name);
				} else {
				    dataSource = (DataSource)XmlUtil.getContent(DATASOURCEDEFINITION, defineBean.getName());
				    if (dsMap == null) {
				    	dsMap = new HashMap<String, DataSource>();
				    }
				    dsMap.put(name, dataSource);
				    dsLocal.set(dsMap);
				}
			}
		}
		
		return dataSource;
	}
	
	public static Connection getConnection(String name)  {
		//System.out.println("connection name:"+name);
		ComboPooledDataSource ds = new ComboPooledDataSource();
		SysBaseDaoDefineBean defineBean = null;
		DataSource dataSource = null;
		Connection connection = null;
		try{
	        
			if (!dataDefineMap.containsKey(name)) {
				dataDefineMap = getPrivateDaoConfig(SYSBASEDAOCONFIGFILE, name);
			}
			if (dataDefineMap.containsKey(name)) {
				defineBean = (SysBaseDaoDefineBean)dataDefineMap.get(name);
			}
			if (defineBean != null) {
				if (defineBean.getIsPool().toUpperCase().equals("T")) {
					HashMap<String, DataSource> dsMap = (HashMap<String, DataSource>)dsLocal.get();
					
					if (dsMap != null && dsMap.containsKey(name)) {
						dataSource = dsMap.get(name);
					} else {
					    dataSource = (DataSource)XmlUtil.getContent(DATASOURCEDEFINITION, defineBean.getName());
					    if (dsMap == null) {
					    	dsMap = new HashMap<String, DataSource>();
					    }
					    dsMap.put(name, dataSource);
					    dsLocal.set(dsMap);
					}
/*
					Context initCtx = new InitialContext();
				    BasicDataSource bds = (BasicDataSource)initCtx.lookup(defineBean.getName());
				    initCtx.close();    
				    bds.getConnection();
				    System.out.println("当前连接数=" + bds.getNumActive());
				    
			       */
					connection = DataSourceUtils.getConnection(dataSource);
					 //DataSourceUtil util = new DatasourceUtil();

					showConnPoolInfo(dataSource);
				        
					//Context ctx = new InitialContext();
					//通过JNDI查找DataSource  
					//DataSource ds2 = (DataSource)ctx.lookup(defineBean.getName());
					//connection = ds.getConnection();
				    
				} else {
					Class.forName(defineBean.getDriver()).newInstance();
					connection = java.sql.DriverManager.getConnection(defineBean.getUrl(), defineBean.getUserName(), defineBean.getPwd());	    
				}
			} else {
				//throw new Exception("无效的数据库连接定义");
			}
		}catch(Exception e){
			System.out.println("DB getConnection error:"+e.getMessage()+"name:"+name);
		}
		
		return connection;
	}
	
	private static void showConnPoolInfo(DataSource pool){    
        PooledDataSource pds = (PooledDataSource) pool;    
        if(null != pds){    
            try {    
                System.out.println("------------c3p0连接池链接状态--------------");    
                System.out.println("c3p0连接池中 【 总共 】 连接数量："+pds.getNumConnectionsDefaultUser());    
                System.out.println("c3p0连接池中 【  忙  】 连接数量："+pds.getNumBusyConnectionsDefaultUser());    
                System.out.println("c3p0连接池中 【 空闲 】 连接数量："+pds.getNumIdleConnectionsDefaultUser());    
                System.out.println("c3p0连接池中 【未关闭】 连接数量："+pds.getNumUnclosedOrphanedConnectionsAllUsers());    
            } catch (Exception e) {    
                System.out.println("c3p0连接池异常！");    
            }    
        }    
    }    
	public static DataSourceTransactionManager getTransaction(String name) throws Exception {
		
		DataSourceTransactionManager transactionManager = null;
		DataSource dataSource = null;
		
		dataSource = getDataSource(name);
		transactionManager = new DataSourceTransactionManager(dataSource); 
		
		return transactionManager;
	}
	
	public static int execUpdate(String sql) throws Exception {
		return execUpdate(sql, "");
	}
	
	public static int execUpdate(String sql, String dataSourceName) throws Exception {
		Connection connection = null;
		int dataCount = 0;
		
		if(!(sql == null || sql.equals(""))) {
			if (dataSourceName.equals("")) {
				connection = getConnection();
			} else {
				connection = getConnection(dataSourceName);
			}
			
			Statement stm = connection.createStatement();
			dataCount = stm.executeUpdate(sql);
		}
		
		return dataCount;
	}
	
	public static ArrayList<ArrayList<String>> execSQL(String sql) throws Exception {
		return execSQL(sql, "", 0, 0);
	}
	
	public static ArrayList<ArrayList<String>> execSQL(String sql, String dataSourceName, int startIndex, int recordPerPage) throws Exception {
		ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
		int fieldCount = 0;
		int recordFetchCount = 0;
		Connection connection = null;
		Exception SysException = null;
		Statement stm = null;
		ResultSet rs = null;
		
		if(!(sql == null || sql.equals(""))) {
			try {
				if (dataSourceName.equals("")) {
					connection = getConnection();
				} else {
					connection = getConnection(dataSourceName);
				}
				
			    stm = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
			    rs = stm.executeQuery(sql);
			    fieldCount = rs.getMetaData().getColumnCount();
			    if (startIndex >= 0 && recordPerPage > 0) {
			    	if (startIndex > 1) {
			    		rs.absolute(startIndex);
			    	}
			    }
			    while(rs.next()) {
			    	ArrayList<String> rowData = new ArrayList<String>();
			    	for (int i = 1; i <= fieldCount; i++) {
			    		rowData.add(rs.getString(i));
			    	}
			    	resultList.add(rowData);
			    	if (recordPerPage > 0) {
			    		recordFetchCount++;
			    		if (recordFetchCount >= recordPerPage) {
			    			break;
			    		}
			    	}
			    }
			}
			catch(Exception e) {
				SysException = e;
			}
			finally {
				if (rs != null) {
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}
				//connection.close();
				if (connection != null) {
					DataSource dataSource = BaseDAO.getDataSource(null);
					DataSourceUtils.releaseConnection(connection, dataSource);
				}
				if (SysException != null) {
					throw SysException;
				}
			}
		}
		
	    return resultList;
	}
	
	//only for BaseQuery.getTurnPageData
	public static ArrayList<ArrayList<String>> execSQL(String sql, String dataSourceName, int startIndex, int recordPerPage, QueryInfoBean queryInfo, boolean appendNoFlg, int pageIndex) throws Exception {
		ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
		int fieldCount = 0;
		int recordFetchCount = 0;
		Connection connection = null;
		Exception SysException = null;
		Statement stm = null;
		ResultSet rs = null;
		
		QuerySelectBean querySelect = queryInfo==null?null:queryInfo.getDefinedSelect();
		QueryPageSumBean queryPageSum = queryInfo==null?null:queryInfo.getPageSum();
		
		ArrayList<QuerySelectSubBean> subSelect = null;
		int count = pageIndex * recordPerPage + 1;
		List<String> dataIndex = null;
		ArrayList<String> pageSumResult = null;
		
		if(!(sql == null || sql.equals(""))) {
			try {
				if (dataSourceName.equals("")) {
					connection = getConnection();
				} else {
					connection = getConnection(dataSourceName);
				}
				
			    stm = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
			    rs = stm.executeQuery(sql);
			    fieldCount = rs.getMetaData().getColumnCount();
			    if (startIndex >= 0 && recordPerPage > 0) {
			    	if (startIndex > 1) {
			    		rs.absolute(startIndex);
			    	}
			    }
		
			    
				if (querySelect != null) {
					subSelect = querySelect.getSelectList();
				}

				//取得页合计基础信息
				if (queryPageSum != null && queryPageSum.getIsView().equals("T")) {
					dataIndex = Arrays.asList(queryPageSum.getDataIndex().split(","));
					pageSumResult = new ArrayList<String>();
					
					//取得页合计初始化
					for(int i = 0; i < fieldCount; i++) {
						pageSumResult.add("");
					}
					if (appendNoFlg) {
						pageSumResult.add("");
					}
				}
				
			    while(rs.next()) {
			    	ArrayList<String> rowData = new ArrayList<String>();
			    	for (int i = 1; i <= fieldCount; i++) {
			    		//字典取得
						String cType = "";
						if (subSelect != null) {
							cType = subSelect.get(i - 1).getCType();
						}
						if (!cType.equals("")) {
							String codeValue = DicUtil.getCodeValue(cType + rs.getString(i));
							rowData.add(codeValue);
						} else {
							rowData.add(rs.getString(i));
						}

						//取得页合计
						if (pageSumResult != null) {
							if (dataIndex.contains(i)) {
								float pageSumColResult = parseFloat(pageSumResult.get(i)) + parseFloat(rs.getString(i));
								pageSumResult.set(i, String.valueOf(pageSumColResult));
							}
						}
			    	}
			    	
					if (appendNoFlg) {
						rowData.add(0, String.valueOf(count++));
					}
					
			    	resultList.add(rowData);
			    	if (recordPerPage > 0) {
			    		recordFetchCount++;
			    		if (recordFetchCount >= recordPerPage) {
			    			break;
			    		}
			    	}
			    }
			    if (pageSumResult != null) {
			    	resultList.add(pageSumResult);
			    }
			}
			catch(Exception e) {
				SysException = e;
			}
			finally {
				if (rs != null) {
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}
				//connection.close();
				if (connection != null) {
					DataSource dataSource = BaseDAO.getDataSource(null);
					DataSourceUtils.releaseConnection(connection, dataSource);
				}
				if (SysException != null) {
					throw SysException;
				}
			}
		}
		
	    return resultList;
	}	
	
	public static ArrayList<HashMap<String, String>> execYsSQL(String sql, String dataSourceName, int iStart, int iEnd, QueryInfoBean queryInfo, boolean appendNoFlg) throws Exception {
		ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		int fieldCount = 0;
		int recordFetchCount = 0;
		Connection connection = null;
		Exception SysException = null;
		Statement stm = null;
		ResultSet rs = null;
		int count = 0;
		
		QuerySelectBean querySelect = queryInfo==null?null:queryInfo.getDefinedSelect();
		QueryPageSumBean queryPageSum = queryInfo==null?null:queryInfo.getPageSum();
		
		ArrayList<QuerySelectSubBean> subSelect = null;
		List<String> dataIndex = null;
		ArrayList<String> pageSumResult = null;
		
		if(!(sql == null || sql.equals(""))) {
			try {
				if (dataSourceName.equals("")) {
					connection = getConnection();
				} else {
					connection = getConnection(dataSourceName);
				}
				
			    stm = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
			    rs = stm.executeQuery(sql);
			    fieldCount = rs.getMetaData().getColumnCount();
			    if (iStart >= 0 && iEnd > 0) {
			    	if (iStart > 1) {
			    		rs.absolute(iStart);
			    	}
			    	recordFetchCount = iStart;
			    	
			    }
			    if (iStart > 0) {
			    	count = iStart + 1;
			    } else {
			    	count = 1;
			    }
			    
				if (querySelect != null) {
					subSelect = querySelect.getSelectList();
				}

				//取得页合计基础信息
				if (queryPageSum != null && queryPageSum.getIsView().equals("T")) {
					dataIndex = Arrays.asList(queryPageSum.getDataIndex().split(","));
					pageSumResult = new ArrayList<String>();
					
					//取得页合计初始化
					for(int i = 0; i < fieldCount; i++) {
						pageSumResult.add("");
					}
					if (appendNoFlg) {
						pageSumResult.add("");
					}
				}
				
				ResultSetMetaData md = rs.getMetaData();
				
			    while(rs.next()) {
			    	HashMap<String, String> rowData = new HashMap<String, String>();
			    	for (int i = 1; i <= fieldCount; i++) {
			    		//字典取得
						String cType = "";
						if (subSelect != null) {
							cType = subSelect.get(i - 1).getCType();
						}
						if (!cType.equals("")) {
							String codeValue = DicUtil.getCodeValue(cType + rs.getString(i));
							rowData.put(md.getColumnLabel(i), codeValue);
						} else {
							rowData.put(md.getColumnLabel(i), rs.getString(i));
						}
						
						//取得页合计
						if (pageSumResult != null) {
							if (dataIndex.contains(i)) {
								float pageSumColResult = parseFloat(pageSumResult.get(i)) + parseFloat(rs.getString(i));
								pageSumResult.set(i, String.valueOf(pageSumColResult));
							}
						}
			    	}
			 
					if (appendNoFlg) {
						rowData.put("rownum", String.valueOf(count++));
					}
			
			    	resultList.add(rowData);
			    	if (iEnd > 0) {
			    		recordFetchCount++;
			    		if (recordFetchCount >= iEnd) {
			    			break;
			    		}
			    	}
			    }
			    /*
			    if (pageSumResult != null) {
			    	resultList.add(pageSumResult);
			    }
			    */
			}
			catch(Exception e) {
				SysException = e;
			}
			finally {
				if (rs != null) {
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}
				//connection.close();
				if (connection != null) {
					DataSource dataSource = BaseDAO.getDataSource(null);
					DataSourceUtils.releaseConnection(connection, dataSource);
				}
				if (SysException != null) {
					throw SysException;
				}
			}
		}
		
	    return resultList;
	}	
	
	public static HashMap<String, SysBaseDaoDefineBean> getPrivateDaoConfig(String xmlFileName, String dataBaseName) {
		try {
			Element element = XmlUtil.getRootElement(xmlFileName);
			if (element != null) {
				//�������ȡ��Ԫ���µ���Ԫ�����
				Iterator<Element> iter = element.elementIterator();
				while(iter.hasNext()){
				    Element el = (Element)iter.next();
				    SysBaseDaoDefineBean daoDefineBean = new SysBaseDaoDefineBean();
					//��ȡ������ơ�ֵ
					Iterator<Element> subIter = el.elementIterator();
					while(subIter.hasNext()){
						Element subEl = (Element)subIter.next();
					    String elName = subEl.getName().toLowerCase();
					    switch(elName) {
					    case "databasename":
					    	daoDefineBean.setDataBaseName(subEl.getText());
					    	break;
					    case "ispool":
					    	daoDefineBean.setIsPool(subEl.getText());
					    	break;
					    case "name":
					    	daoDefineBean.setName(subEl.getText());
					    	break;
					    case "driver":
					    	daoDefineBean.setDriver(subEl.getText());
					    	break;
					    case "url":
					    	daoDefineBean.setUrl(subEl.getText());
					    	break;
					    case "username":
					    	daoDefineBean.setUserName(subEl.getText());
					    	break;
					    case "pwd":
					    	daoDefineBean.setPwd(subEl.getText());
					    	break;
					    }
					}
					//������Ӷ����������ж�
					if (!daoDefineBean.getDataBaseName().equals("")) {
						dataDefineMap.put(daoDefineBean.getDataBaseName(), daoDefineBean);
					}
					if(daoDefineBean.getDataBaseName().equals(dataBaseName)) {
						break;
					}
				}
			}

		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		return dataDefineMap;
	}	
	
	public static void closeConnection(Connection connection, PreparedStatement statement) {
		try {
			System.out.println(connection.getTransactionIsolation());
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
			System.out.println(connection.getTransactionIsolation());
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void commit(Connection connection) {
		try {
			if (connection != null) {
				connection.commit();
				connection.close();
			}
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public static void rollback(Connection connection) {
		try {
			if (connection != null) {
				connection.rollback();
				connection.close();
			}
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static String getGuId() {
		UUID uuid = UUID.randomUUID();
		
		return uuid.toString();
		
	}
	
	private static float parseFloat(String data) {
		float rtnData = 0;
		if (!(data == null || data.equals(""))) {
			rtnData = Float.parseFloat(data);
		}
		
		return rtnData;
	}
}
