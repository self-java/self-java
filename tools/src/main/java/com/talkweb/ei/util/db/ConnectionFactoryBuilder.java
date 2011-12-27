package com.talkweb.ei.util.db;

public class ConnectionFactoryBuilder {
	
	public static synchronized IconnectionFactory buildConnectionFactory(
			DbConstant dbConstant) {
		IconnectionFactory instance = null;
		String strtconntype = dbConstant.dbConnType;
		if (strtconntype.equals("jdbc")) {
			instance = new JdbcConnectionFactory(dbConstant);
		} else if (strtconntype.equals("jndi")) {
			instance = new JndiConnectionFactory(dbConstant);
		}else if (strtconntype.equals("dbcp")) {
			instance = new DbcpConnectionFactory(dbConstant);
		}

		return instance;
	}
}