package com.talkweb.ei.util.db;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public class DbcpConnectionFactory implements IconnectionFactory {
	//private static DbcpConnectionFactory instance = null;
	private List freeConns;//空闲队列List<Connection>
	
	String driver = null;
	String url = null;
	String user = null;
	String password = null;
	int maxactive = 8;
	int initialsize = 0;
	
	DataSource dataSource = null;
	
	public static String overInfo = "数据库连接数已达最大值，请稍后再试！";
	
	Logger log = Logger.getLogger(DbcpConnectionFactory.class.getName());

	/**
	public static  DbcpConnectionFactory getInstance() {
		if (instance == null) {
			try {
				instance = new DbcpConnectionFactory();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	 */
	
	public DbcpConnectionFactory(DbConstant dbConstant){
		driver = dbConstant.dbJdbcDriver;
       	url = dbConstant.dbUrl;
		user = dbConstant.dbUser;
		password = dbConstant.dbPassword;
		
		try {
			maxactive = Integer.parseInt(dbConstant.dbMaxActive);
			initialsize = Integer.parseInt(dbConstant.dbInitialSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		
		 dataSource = setupDataSource();
	}

	/**
	 * 取得数据库连接
	 */
	public synchronized Connection getConn() {
		Connection conn = null;

		conn = newConn();

		return conn;
	}

	/**
	 * 断开数据库连接
	 */
	public void freeConn(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) 
			   conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
	}

	/**
	 * 新建立一个连接
	 * @return
	 */
	private Connection newConn(){
		Connection conn = null;
		try {
			 conn = dataSource.getConnection();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return conn;
	}

	protected void finalize() {
		// finalization code here
		try{
			if(dataSource!=null)
				shutdownDataSource(dataSource);
		}
		catch(Exception Err){
			log.error(Err.getMessage());
		}
		finally{
			dataSource = null;
		}
	} 



    private  DataSource setupDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName( driver );
        ds.setUsername( user );
        ds.setPassword( password );
        ds.setUrl( url );
        ds.setMaxActive( maxactive );
        ds.setInitialSize( initialsize );
        
        return ds;
    }

    private  void printDataSourceStats(DataSource ds) throws SQLException {
        BasicDataSource bds = (BasicDataSource) ds;
        System.out.println("NumActive: " + bds.getNumActive());
        System.out.println("NumIdle: " + bds.getNumIdle());
    }

    private  void shutdownDataSource(DataSource ds) throws SQLException {
        BasicDataSource bds = (BasicDataSource) ds;
        bds.close();
    }
	
	/**
	 * 释放所有的连接
	 */
	public void freeAllConn(){
		try {
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void displayPool(){
		System.out.println("DB dirver is : "+driver);
		System.out.println("DB url is : "+url);
		System.out.println("DB user is : "+user);
		System.out.println("DB password is : "+password);
		System.out.println("DB maxactive is : " + maxactive);
		System.out.println("DB initialsize is : "+ initialsize);
		System.out.println("DB current connpool size is : "+freeConns.size());
	}
	
	/**
	public static void main(String[] args) throws Exception {
		DbcpConnectionFactory ss = DbcpConnectionFactory.getInstance();
		String Id = null;
		Connection conn = null;
		try {
			conn = ss.getConn();
			Statement stat = conn.createStatement();
			String strSql = "select name from T_S_EI_WEB_Global";
			ResultSet rs = stat.executeQuery(strSql);
			while (rs.next()) {
				Id = rs.getString("name");
				System.out.println("The TS_EI_FL_WEB_TAG 's count is : "+Id);
			}
			
			ss.freeConn(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	*/
}
