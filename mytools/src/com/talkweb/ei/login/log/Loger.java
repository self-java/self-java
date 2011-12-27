package com.talkweb.ei.login.log;

/**
 * @author Administrator
 * 数据表结构对象
 */
public class Loger {
	private String logid = "";
	private String logtime = "";
	private String sourceip = "";
	private String userid = "";
	private String cmpid = "";
	private String deptid = "";
	private String logtype = "";
	
	public void setLogID(String logid){
		this.logid = logid;
	}
	
	public String getLogID(){
		return this.logid;
	}
	
	public void setLogTime(String logtime){
		this.logtime = logtime;
	}
	
	public String getLogTime(){
		return this.logtime;
	}
	
	public void setSourceIP(String sourceip){
		this.sourceip = sourceip;
	}
	
	public String getSourceIP(){
		return this.sourceip;
	}
	
	public void setUserID(String userid){
		this.userid = userid;
	}
	
	public String getUserID(){
		return this.userid;
	}
	
	public void setCmpID(String cmpid){
		this.cmpid = cmpid;
	}
	
	public String getCmpID(){
		return this.cmpid;
	}
	
	public void setDeptID(String deptid){
		this.deptid = deptid;
	}
	
	public String getDeptID(){
		return this.deptid;
	}
	
	public void setLogType(String logtype){
		this.logtype = logtype;
	}
	
	public String getLogType(){
		return this.logtype;
	}

}
