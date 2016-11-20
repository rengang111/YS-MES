package com.ys.util.basedao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.ys.util.CalendarUtil;


public abstract class BaseAbstractDao 
{

	public Timestamp getDateTimeFromString(String dateS)
	{
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//dateS = df.format(dateS);
		//Timestamp ts = Timestamp.valueOf(dateS);
		Date da = Date.valueOf(dateS);
		Timestamp ts = new Timestamp( da.getTime());
		
		return ts;
	}

	public String nullString(String s)
	{
		if(s == null)
			s = "";
		return s;
	}
	
	public static Connection getConnection() throws Exception 
	{
		Connection connection = null;
		try
		{
			connection = BaseDAO.getConnection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeConnection(Connection connection,Statement statement) throws Exception 
	{
		try
		{
			
			
			if(statement != null)
				statement.close();
			
			if(connection != null) {
				//connection.close();
				DataSource dataSource = BaseDAO.getDataSource(null);
				DataSourceUtils.releaseConnection(connection, dataSource);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}
