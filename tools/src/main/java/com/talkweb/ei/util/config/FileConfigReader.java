package com.talkweb.ei.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;


public class FileConfigReader implements IconfigReader {
	private Properties props = null;
    private ConfigConstant configConstant = null;
    
    Logger log = Logger.getLogger(FileConfigReader.class.getName());
    
    public FileConfigReader(ConfigConstant configConstant){
    	this.configConstant = configConstant;
    }
    
    /**
     * 重置全局对象，只能通过类名静态调用。具体对象不要调用。
     */
    public void clear(){
    	if(props!=null){
    		props.clear();
    		props = null;
    	}
    	if(configConstant.configFilePath!=null || configConstant.configFilePath!=""){
    		configConstant.configFilePath = "";
    	}
    }
    
	public void Init() {
		FileInputStream input = null;
		File file = null;

		/*
		 * if (System.getProperty("os.name").indexOf("Windows") != -1) {
		 * configfilepath = "C:\\Workflow.conf"; } else { configfilepath =
		 * "/Workflow.conf"; }
		 */

		try {

			if (configConstant.configFilePath == null || "".equals(configConstant.configFilePath))
				throw new Exception("FileConfigReader error filepath:"
						+ configConstant.configFilePath);

			file = new File(configConstant.configFilePath);
			if (!file.exists())
				throw new Exception("FileConfigReader error file not exist:"
						+ configConstant.configFilePath);

			input = new FileInputStream(file);

			props = new Properties();
			props.load(input);

		} catch (Exception e) {
			if(props!=null){
				props.clear();
				props = null;
			}
			log.error("FileConfigReader error:" + e.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					log.error("FileConfigReader error:" + e.getMessage());
				} finally {
					input = null;
				}
			}
			if(file!=null){
				file = null;
			}
		}
	}

   public String GetNodeValue(String strNodeName) {
		String keyValue = null;
		if (props == null)
			Init();
		try {
			keyValue = props.getProperty(strNodeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (keyValue != null) {
			keyValue = keyValue.replaceAll("\"", "");
		}
		return keyValue;
	}

	public String GetNodeValue(String strNodeName, String defaultValue) {
		String keyValue = GetNodeValue(strNodeName);
		if (keyValue == null) {
			return defaultValue;
		} else {
			return keyValue;
		}
	}
	
	/**只用于获取xml文件中任何一个节点值
	 * @param nodeName
	 * @return
	 */
	public String getNodeValue(String nodeName){
		return null;
	}
	
	/**只用于获取xml文件中任何一个节点值
	 * @param nodeName
	 * @return
	 */
	public String getNodeValue(String strNodeName, String defaultValue){
		return null;
	}
   
	/**
	 * @获取properties类型文件时，不建议使用此方法。获取的属性对象不要做释放处理。
	 * @param strchild 无效参数，可传入任意值。
	 */
	public Properties getProperties(String strchild) {
		if (props == null)
			Init();
		return props;
	}

	public String getConfigFilePath() {
		return configConstant.configFilePath;
	}

	public void setConfigFilePath(String configfilepath) {
		configConstant.configFilePath = configfilepath;
	}
	
	/*public static void main(String args[]){
		FileConfigReader configReader = FileConfigReader.getInstance("c:/Workflow.conf");
		String result = configReader.GetNodeValue("java.naming.provider.url");
		System.out.println("result: " + result);
		
		Properties pro = configReader.getProperties("ldap");
		result = pro.getProperty("java.naming.provider.url");
		System.out.println("result: " + result);
		pro.clear();
		
		FileConfigReader.clear();
	}*/
}
