package com.talkweb.ei.util.db;

import java.sql.Connection;

/**
 * jdbc 连接数据接口
 */
public interface IconnectionFactory{
	public Connection getConn();//取得一个连接
	public void freeConn(Connection conn);//释放一个连接
	public void freeAllConn();//释放所有的连接
}
