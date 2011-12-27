package com.talkweb.ei.common;

import java.util.List;
import com.talkweb.ei.util.db.*;

public class ClsDbFactory {
	private static DbExecutor db_excutor = null;
	
	/**
	 * 使用唯一对象实例  在dbcp连接方式下，只要初始化一次数据库配置，否则会存在多次初始连接池的问题
	 * 如果连接多个数据库，可以扩展此类生成多个数据库操作对象和方法
	 * @return 数据库操作对象
	 */
	public static DbExecutor getDbExcutor(){
		if(db_excutor == null){
			db_excutor = DbExecutorFactory.getDbExecutor(ClsGlobalVar.GetDBConstant());
		}
		return db_excutor;
	}
	
	public static void main(String args[]){
		//String result = ClsDbFactory.queryForString("select LDAPCHANGETYPE,count(ldapchangetype) from DB2INST1.T_PUB_SYLDAPLOG where   Date(changetime)   between   '2007-06-01'   and   '2008-11-01' group by ldapchangetype");
		//String result = ClsDbFactory.queryForString("select LDAPCHANGETYPE,count(ldapchangetype) from DB2INST1.T_PUB_SYLDAPLOG where   Date(changetime)   between   '2007-06-01'   and   '2008-11-01' group by ldapchangetype");
		
		for (int q = 0; q < 20; q++) {
			DbExecutor dbEx = ClsDbFactory.getDbExcutor();
			// List result = ClsDbFactory.executeQuery("select * from
			// DB2INST1.T_PUB_SYLDAPLOG where Date(changetime) between
			// '2007-06-01' and '2008-11-01' group by ldapchangetype");
			List result = dbEx
					.executeQuery("select * from t_workflow_activity");

			for (int i = 0; i < result.size(); i++) {
				String[] tmpStrs = (String[]) result.get(i);
				String record = "";
				for (int j = 0; j < tmpStrs.length; j++) {
					record += tmpStrs[j] + ";";
				}
				System.out.println(record);
			}
		}
	}
}
