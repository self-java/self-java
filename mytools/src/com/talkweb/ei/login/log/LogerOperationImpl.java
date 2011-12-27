package com.talkweb.ei.login.log;

import java.io.File;

public class LogerOperationImpl { 
	public static void test(){
		String sep = System.getProperty("file.separator");
		String userdir =  System.getProperty("user.dir");
		
		userdir = userdir + "/";
		
		
		
		System.out.println("sep:" + sep);
		System.out.println("userdir:" + userdir);
		try{
		File establishFile = new File(".");
		System.out.println("userdir2:" + establishFile.getAbsolutePath());
		}catch(Exception e){
		   System.out.println(e.getMessage());
		}
		
		//userdir = application.getRealPath("");
		//System.out.println("application path" + userdir);

		//userdir = this.getClass().getResource("/").getPath();
		userdir = LogerOperationImpl.class.getResource("/").getPath();
		System.out.println("path3:" + userdir);
		
		int index = userdir.indexOf("class");
		if (index >= 0) {
			userdir = userdir.substring(0, index-1);
		}
		System.out.println("path4:" + userdir);
	}

	public void DbImpl(){
		
	}
	
	public static void main(String[] args) throws Exception{
		String sep = System.getProperty("file.separator");
		String userdir =  System.getProperty("user.dir");
		
		userdir = userdir + "/";
		
		System.out.println("sep:" + sep);
		System.out.println("userdir:" + userdir);
		
		LogerOperationImpl.test();
	}
}