package com.talkweb.ei.util.config;

public class ConfigConstant {
	/**
	 * 配置文件路径 （默认为应用程序而非web程序的路径形式）
	 */
	public String configFilePath = System.getProperty("user.dir")+ "/conf/config.xml";
	
	/**
	 * Log4j配置日志文件路径 （默认为应用程序而非web程序的路径形式）
	 */
	public String log4jFilePath = System.getProperty("user.dir")+ "/conf/Log4j.properties";
	
	/**
	 * 配置文件类型 1:properties文件 2:xml文件（默认）
	 */
	public String configFileType = "2";
}