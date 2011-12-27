package com.talkweb.ei.util.db;

public class DbConstant {
	public String dbJdbcDriver = "oracle.jdbc.driver.OracleDriver";
	public String dbUrl = "jdbc:oracle:thin:@10.201.11.167:1521:jsunicom";
	public String dbUser = "appuser";
	public String dbPassword = "password";
	public String dbSchema = "shark";
	public String dbTimeOut = "10";
	public String dbMaxActive = "20";
	public String dbInitialSize = "5";
	
	/****
	 * 数据库连接类型： jdbc  自己的数据库连接； jndi WEB服务器提供的连接池； dbcp apache common pool dbcp连接池；
	 */
    public String dbConnType = "jdbc";
    
    /***
     * 数据库类型： oracle ； sqlserver ；
     */
    public String dbType = "oracle";
	
	public String jndiName = "shark";
}