package com.talkweb.ei.util.db;

public class DbExecutorFactory {
	
	public static synchronized DbExecutor getDbExecutor(DbConstant dbConstant) {
		DbExecutor instance = null;
		String dbType = dbConstant.dbType;
		if (dbType.equals("oracle"))
			instance = new OraDbExecutor(dbConstant);
		else if (dbType.equals("msql"))
			instance = new MsSqlDbExecutor(dbConstant);
		else if (dbType.equals("db2"))
			instance = new DB2DbExecutor(dbConstant);

		return instance;
	}
}
