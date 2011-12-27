package com.talkweb.ei.util.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class JndiConnectionFactory implements IconnectionFactory{

	String strjndi="";
	//Connection conn=null;
	Logger log = Logger.getLogger(JndiConnectionFactory.class.getName());
	
	public JndiConnectionFactory(DbConstant dbConstant)
	{
		strjndi = dbConstant.jndiName;
	}
	public void freeAllConn() {
		// TODO Auto-generated method stub
		
	}

	public void freeConn(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed())
			    conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		
	}

	public Connection getConn() {
		Connection conn=null;
		try {
			//freeConn(conn);
			
            DataSource ds=null;
            InitialContext ctx=new InitialContext();
            
            //ds=(DataSource)ctx.lookup("java:comp/env/"+strjndi);
            //System.out.println("====开始查找jndi=====");
            //System.out.println(strjndi);
            ds = (DataSource)ctx.lookup(strjndi);
            //ds=(DataSource)ctx.lookup("java:comp/env/"+strjndi);
            conn=ds.getConnection();
            //System.out.println("====查找jndi成功=====");
    }catch (Exception e) {
		// TODO Auto-generated catch block
		log.error(e.getMessage());
	}
    return conn;
	}

}
