package com.talkweb.ei.util.config;

import java.util.Properties;

public interface IconfigReader {
	public void Init();
	
	/** 只能获取xml文件中顶层节点
	 * @param strNodeName
	 * @return
	 */
	public String GetNodeValue(String strNodeName);
	public String GetNodeValue(String strNodeName, String defaultValue);
	
	/**可获取xml文件中任何一个节点值
	 * @param nodeName
	 * @return
	 */
	public String getNodeValue(String nodeName);
	public String getNodeValue(String strNodeName, String defaultValue);
	
	public Properties getProperties(String strchild);
	public String getConfigFilePath();
	public void setConfigFilePath(String configfilepath);
}
