package com.talkweb.ei.util.db;

import java.util.List;

public interface IdbExecutor {
	public int executeNonQuery(String strSql);
	public List executeQuery(String strSql);
	public String queryForString(String strSql);
	public Object saveObject(Object obj);
	public boolean updateObject(Object obj);
	public boolean deleteObject(Object obj);
	public Object findObject(Object obj);
}
