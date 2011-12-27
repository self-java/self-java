package com.talkweb.ei.util.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;
import oracle.sql.CLOB;

public class OraDbExecutor extends DbExecutor {

	Logger log = Logger.getLogger(OraDbExecutor.class.getName());
	
	public OraDbExecutor(DbConstant dbConstant) {
		super(dbConstant);
	}

	public String queryForString(String strSql, int pos) {
		CLOB clob = null;
		String strreturn = "";
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			conn = this.IconnFactory.getConn();
			stat = conn.createStatement();
			rs = stat.executeQuery(strSql);
			
			if (rs.next()) {
				{
					clob = (CLOB) rs.getClob(pos);
					if (clob != null) {
						int len = (int) clob.length();
						char[] buffer = new char[len];
						java.io.Reader reader = clob.getCharacterStream();
						reader.read(buffer);
						reader.close();
						strreturn = new String(buffer);
					}
				}

			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		} finally {
			if(clob != null){
				clob = null;
			}
			
			if(rs != null){
				rs = null;
			}
			
			if(stat != null){
				stat = null;
			}
			
			if(conn != null){
				IconnFactory.freeConn(conn);
				conn = null;
			}
		}
		return strreturn;
	}
}
