package com.talkweb.ei.util.db;

/**
 * db connect tag define
 * @author xuhong
 *
 */
public class DbConDef {

	public final static String dbJdbcDriver = "db.driverClassName";
	public final static String dbUrl = "db.url";
	public final static String dbUser = "db.username";
	public final static String dbPassword = "db.password";
	public final static String dbSchame = "db.dbSchame";
	public final static String dbTimeOut = "db.timeout";
	
	/***
	 * dbcp 最大连接数   db.maxactive
	 */
	public final static String dbMaxActive = "db.maxactive";
	
    /***
     * dbcp 初始连接数   db.initialsize
     */
	public final static String dbInitialSize = "db.initialsize";
	
	public final static String dbConnType = "db.dbConnType";
	public final static String dbType = "db.dbType";
	
	public final static String jndiName = "jndi.name";
}