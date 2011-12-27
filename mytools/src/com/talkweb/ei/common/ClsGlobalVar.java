package com.talkweb.ei.common;

import org.apache.log4j.Logger;

import com.talkweb.ei.util.config.ConfigReaderFactory;
import com.talkweb.ei.util.config.IconfigReader;
import com.talkweb.ei.util.config.ConfigConstant;
import com.talkweb.ei.util.db.DbConDef;
import com.talkweb.ei.util.db.DbConstant;
import com.talkweb.ei.util.ldap.LdapConDef;
import com.talkweb.ei.util.ldap.LdapConstant;


/**
 * @author xuhong
 *
 */
public class ClsGlobalVar {
	
	//******************* 数据库 属性 **********************
	private static String dbJdbcDriver = null;
	private static String dbUrl = null;
	private static String dbUser = null;
	private static String dbPassword = null;
	private static String dbSchame = null;
	private static String dbTimeOut = null;
	private static String dbMaxActive = null;
	private static String dbInitialSize = null;
	
	private static String dbConnType = null;
	private static String dbType = null;
	
	private static String jndiName = null;
    //******************* 数据库 属性 **********************
	private static DbConstant dbConstant = null;
	
    //******************* LDAP 属性 **********************
	public static String ldapJndiDriver = null;
	public static String ldapUrl = null;
	public static String ldapUser = null;
	public static String ldapPassword = null;
	
	public static String ldapConnPool = null;
	public static String ldapConnPoolMaxSize = null;
	public static String ldapConnPoolInitSize = null;
	public static String ldapConnPoolPrefSize = null;
	public static String ldapConnPoolTimeout = null;
	
	public static String ldapUsersBase = null;
	public static String ldapOrgsBase = null;
	public static String ldapGroupsBase = null;
    //******************* LDAP 属性 **********************
	private static LdapConstant ldapConstant = null;
	
    //******************* 配置文件读写器 属性 **********************
	private static IconfigReader reader = null;
	private static String configfilepath = null;
    //******************* 配置文件读写器 属性 **********************
	
	private static String log4jfilepath = null;
	
	/***
	 * 读取项目程序的工作路径
	 * @return
	 */
	public static String GetApplicationPath(){
        //作为应用程序的路径
		//return System.getProperty("user.dir");
		
		//作为WEB程序的路径
		String userdir = ClsGlobalVar.class.getResource("/").getPath();
		
		//System.out.println("path3:" + userdir);
		
		int index = userdir.indexOf("class");
		if (index >= 0) {
			userdir = userdir.substring(0, index-1);
		}
		
		//System.out.println("path4:" + userdir);
		
		return userdir;
	}
	
	/***
	 * 读取项目程序的工作路径  默认为WEB程序
	 * @param appType 程序类型: 1 后台应用程序；2 WEB程序；
	 * @return
	 */
	public static String GetApplicationPath(int appType) {
		String userdir = "";

		switch (appType) {
		case 1:// 作为应用程序的路径
			userdir = System.getProperty("user.dir");
			break;
		default:// 作为WEB程序的路径
			userdir = ClsGlobalVar.class.getResource("/").getPath();
			int index = userdir.indexOf("class");
			if (index >= 0) {
				userdir = userdir.substring(0, index - 1);
			}
		}

		return userdir;
	}
	
	//初始化配置文件
	static{
		init();
	}
	
	/**
	 * 
	 */
	public static void init(){
		ConfigConstant configConstant = new ConfigConstant();
		configConstant.configFileType = "2";
		
        //初始化log4j日志配置
		ClsGlobalVar.log4jfilepath = GetApplicationPath(2)
		+ "/conf/Log4j.properties";
		configConstant.log4jFilePath = ClsGlobalVar.log4jfilepath;
		org.apache.log4j.PropertyConfigurator.configure(configConstant.log4jFilePath);
		
        //初始化其他配置文件
			ClsGlobalVar.configfilepath = GetApplicationPath(2)
					+ "/conf/config.xml";
			// System.out.println("ClsGlobalVar.configfilepath:" +
			// ClsGlobalVar.configfilepath);
			
			configConstant.configFilePath = ClsGlobalVar.configfilepath;
			reader = ConfigReaderFactory.getConfigReader(configConstant);
	}
	
	/**
	 *  获取配置文件指定节点
	 *   reader.GetNodeValue方法 获取配置文件中顶节点； 
	 *   reader.getNodeValue方法 获取配置文件中任意节点；
	 * @param nodename  节点名称
	 * @return   字符串
	 */
	public static String getNodeValue(String nodename) {
		//if (reader == null) {
		//	init();
		//}
		return reader.GetNodeValue(nodename);
	}
	
	/**
	 *  获取配置文件指定节点
	 *   reader.GetNodeValue方法 获取配置文件中顶节点； 
	 *   reader.getNodeValue方法 获取配置文件中任意节点；
	 * @param nodename 节点名称
	 * @param defaultvalue 节点默认值
	 * @return   字符串
	 */
	public static String getNodeValue(String nodename, String defaultvalue) {
		//if (reader == null) {
		//	init();
		//}
		return reader.GetNodeValue(nodename, defaultvalue);
	}
	
	public static DbConstant GetDBConstant() {
		if (ClsGlobalVar.dbConstant == null) {
			DbConstant dbConstant = new DbConstant();
			
			dbConstant.dbConnType = ClsGlobalVar.getDbConnType();
			dbConstant.dbJdbcDriver = ClsGlobalVar.getDbJdbcDriver();
			dbConstant.dbUser = ClsGlobalVar.getDbUser();
			dbConstant.dbPassword = ClsGlobalVar.getDbPassword();
			dbConstant.dbSchema = ClsGlobalVar.getDbSchame();
			dbConstant.dbTimeOut = ClsGlobalVar.getDbTimeOut();
			dbConstant.dbMaxActive = ClsGlobalVar.getDbMaxActive();
			dbConstant.dbInitialSize = ClsGlobalVar.getDbInitialSize();
			dbConstant.dbType = ClsGlobalVar.getDbType();
			dbConstant.dbUrl = ClsGlobalVar.getDbUrl();
			dbConstant.jndiName = ClsGlobalVar.getJndiName();

			ClsGlobalVar.dbConstant = dbConstant;
		}
		return ClsGlobalVar.dbConstant;
	}
	
	public static LdapConstant GetLdapConstant() {
		if (ClsGlobalVar.ldapConstant == null) {
			LdapConstant ldapConstant = new LdapConstant();
			
			ldapConstant.ldapJndiDriver = ClsGlobalVar.getLdapJndiDriver();
			ldapConstant.ldapUrl = ClsGlobalVar.getLdapUrl();
			ldapConstant.ldapUser = ClsGlobalVar.getLdapUser();
			ldapConstant.ldapPassword = ClsGlobalVar.getLdapPassword();
			ldapConstant.ldapConnPool = ClsGlobalVar.getLdapConnPool();
			ldapConstant.ldapConnPoolMaxSize = ClsGlobalVar.getLdapConnPoolMaxSize();
			ldapConstant.ldapConnPoolInitSize = ClsGlobalVar.getLdapConnPoolInitSize();
			ldapConstant.ldapConnPoolPrefSize = ClsGlobalVar.getLdapConnPoolPrefSize();
			ldapConstant.ldapConnPoolTimeout = ClsGlobalVar.getLdapConnPoolTimeout();
			ldapConstant.ldapUsersBase = ClsGlobalVar.getLdapUsersBase();
			ldapConstant.ldapOrgsBase = ClsGlobalVar.getLdapOrgsBase();
			ldapConstant.ldapGroupsBase = ClsGlobalVar.getLdapGroupsBase();

			ClsGlobalVar.ldapConstant = ldapConstant;
		}
		return ClsGlobalVar.ldapConstant;
	}
	
	public static String getDbConnType() {
		if(dbConnType == null){
			dbConnType = getNodeValue(DbConDef.dbConnType);
		}
		return dbConnType;
	}

	public static String getDbJdbcDriver() {
		if(dbJdbcDriver == null){
			dbJdbcDriver = getNodeValue(DbConDef.dbJdbcDriver);
		}
		return dbJdbcDriver;
	}

	public static String getDbPassword() {
		if(dbPassword == null){
			dbPassword = getNodeValue(DbConDef.dbPassword);
		}
		return dbPassword;
	}

	public static String getDbSchame() {
		if(dbSchame == null){
			dbSchame = getNodeValue(DbConDef.dbSchame);
		}
		return dbSchame;
	}

	public static String getDbTimeOut() {
		if(dbTimeOut == null){
			dbTimeOut = getNodeValue(DbConDef.dbTimeOut);
		}
		return dbTimeOut;
	}
	
	public static String getDbMaxActive() {
		if(dbMaxActive == null){
			dbMaxActive = getNodeValue(DbConDef.dbMaxActive);
		}
		return dbMaxActive;
	}
	
	public static String getDbInitialSize() {
		if(dbInitialSize == null){
			dbInitialSize = getNodeValue(DbConDef.dbInitialSize);
		}
		return dbInitialSize;
	}

	public static String getDbType() {
		if(dbType == null){
			dbType = getNodeValue(DbConDef.dbType);
		}
		return dbType;
	}

	public static String getDbUrl() {
		if(dbUrl == null){
			dbUrl = getNodeValue(DbConDef.dbUrl);
		}
		return dbUrl;
	}

	public static String getDbUser() {
		if(dbUser == null){
			dbUser = getNodeValue(DbConDef.dbUser);
		}
		return dbUser;
	}

	public static String getJndiName() {
		if(jndiName == null){
			jndiName = getNodeValue(DbConDef.jndiName);
		}
		return jndiName;
	}

	public static String getLdapConnPool() {
		if(ldapConnPool == null){
			ldapConnPool = getNodeValue(LdapConDef.ldapConnPool);
		}
		return ldapConnPool;
	}

	public static String getLdapConnPoolInitSize() {
		if(ldapConnPoolInitSize == null){
			ldapConnPoolInitSize = getNodeValue(LdapConDef.ldapConnPoolInitSize);
		}
		return ldapConnPoolInitSize;
	}

	public static String getLdapConnPoolMaxSize() {
		if(ldapConnPoolMaxSize == null){
			ldapConnPoolMaxSize = getNodeValue(LdapConDef.ldapConnPoolMaxSize);
		}
		return ldapConnPoolMaxSize;
	}

	public static String getLdapConnPoolPrefSize() {
		if(ldapConnPoolPrefSize == null){
			ldapConnPoolPrefSize = getNodeValue(LdapConDef.ldapConnPoolPrefSize);
		}
		return ldapConnPoolPrefSize;
	}

	public static String getLdapConnPoolTimeout() {
		if(ldapConnPoolTimeout == null){
			ldapConnPoolTimeout = getNodeValue(LdapConDef.ldapConnPoolTimeout);
		}
		return ldapConnPoolTimeout;
	}

	public static String getLdapGroupsBase() {
		if(ldapGroupsBase == null){
			ldapGroupsBase = getNodeValue(LdapConDef.ldapGroupsBase);
		}
		return ldapGroupsBase;
	}

	public static String getLdapJndiDriver() {
		if(ldapJndiDriver == null){
			ldapJndiDriver = getNodeValue(LdapConDef.ldapJndiDriver);
		}
		return ldapJndiDriver;
	}

	public static String getLdapOrgsBase() {
		if(ldapOrgsBase == null){
			ldapOrgsBase = getNodeValue(LdapConDef.ldapOrgsBase);
		}
		return ldapOrgsBase;
	}

	public static String getLdapPassword() {
		if(ldapPassword == null){
			ldapPassword = getNodeValue(LdapConDef.ldapPassword);
		}
		return ldapPassword;
	}

	public static String getLdapUrl() {
		if(ldapUrl == null){
			ldapUrl = getNodeValue(LdapConDef.ldapUrl);
		}
		return ldapUrl;
	}

	public static String getLdapUser() {
		if(ldapUser == null){
			ldapUser = getNodeValue(LdapConDef.ldapUser);
		}
		return ldapUser;
	}

	public static String getLdapUsersBase() {
		if(ldapUsersBase == null){
			ldapUsersBase = getNodeValue(LdapConDef.ldapUsersBase);
		}
		return ldapUsersBase;
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println("ok!");
		
		Logger log = Logger.getLogger(ClsGlobalVar.class.getName());
		log.info("my log file test");
	}
}
