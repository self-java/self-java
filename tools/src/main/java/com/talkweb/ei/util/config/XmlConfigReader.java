package com.talkweb.ei.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.NodeList;


/**
 * @author xuhong
 *
 */
public class XmlConfigReader implements IconfigReader{
	private Document doc = null;
	private ConfigConstant configConstant = null;
    
    Logger log = Logger.getLogger(XmlConfigReader.class.getName());
        
    public XmlConfigReader(ConfigConstant configConstant) {
		this.configConstant = configConstant;
	}
    
    /**
     * 重置全局对象，只能通过类名静态调用。具体对象不要调用。
     */
    public void clear(){
    	if(doc!=null){
    		doc = null;
    	}
    	if(configConstant.configFilePath!=null || configConstant.configFilePath!=""){
    		configConstant.configFilePath = "";
    	}
    }
    
	public void Init(){
		SAXBuilder sb = null;
		FileInputStream input = null;
		File file = null;
		
		/*
		 * String configfilepath = "c:/tameai.xml"; if
		 * (System.getProperty("file.separator").equals("/")) {
		 * configfilepath = "/tameai.xml"; }
		 */

		try {
			if (configConstant.configFilePath == null || "".equals(configConstant.configFilePath))
				throw new Exception("XmlConigReaderInfo error filepath:"
						+ configConstant.configFilePath);

			file = new File(configConstant.configFilePath);
			if (!file.exists()) 
				throw new Exception("XmlConigReaderInfo error file not exist:"
						+ configConstant.configFilePath);
			
			sb = new SAXBuilder();
			input = new FileInputStream(configConstant.configFilePath);
			doc = sb.build(input);
			
		} catch (Exception e) {
			if(doc!=null){
				doc = null;
			}
			log.error("XmlConigReaderInfo error:" + e.getMessage());
		} finally {
			if (sb != null) {
				sb = null;
			}
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					log.error("XmlConigReaderInfo error:" + e.getMessage());
				} finally {
					input = null;
				}
			}
			if(file!=null){
				file = null;
			}
		}
	}
	

	/**
	 * 只能获取xml文件中顶层节点
	 * 
	 * @param strNodeName
	 * @return
	 */
	public String GetNodeValue(String strNodeName) {
		if (doc == null)
			Init();

		if (doc.hasRootElement()) {
			Element root = doc.getRootElement();
			return root.getChildText(strNodeName);
		}
		return null;
	}
   
   /**
	 * 可获取xml文件中任何一个节点值
	 * 
	 * @param nodeName
	 * @return
	 */
	public String getNodeValue(String nodeName) {
		String _result = null;

		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		org.w3c.dom.Document doc = null;

		NodeList _nodelist = null;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(configConstant.configFilePath);

			_nodelist = doc.getElementsByTagName(nodeName);
			if (_nodelist.getLength() == 0) {
				log.error("XmlConigReaderInfo error:" + " 节点 "
						+ nodeName + " 在指定的XML文件中没有找到！");
			}

			_result = _nodelist.item(0).getChildNodes().item(0).getNodeValue();
		} catch (Exception e) {
			log.error("XmlConigReaderInfo error:" + e.getMessage());
		} finally {
			if (_nodelist != null) {
				_nodelist = null;
			}
			if (doc != null) {
				doc = null;
			}
			if (builder != null) {
				builder = null;
			}
			if (factory != null) {
				factory = null;
			}
		}
		return _result;
	}
	
	public String GetNodeValue(String strNodeName, String defaultValue) {
		String keyValue = GetNodeValue(strNodeName);
		if (keyValue == null) {
			return defaultValue;
		} else {
			return keyValue;
		}
	}
   
   public String getNodeValue(String strNodeName, String defaultValue) {
		String keyValue = getNodeValue(strNodeName);
		if (keyValue == null) {
			return defaultValue;
		} else {
			return keyValue;
		}
	}
   
	public Properties getProperties(String strchild) {
		Properties retPro = new Properties();
		if (doc == null)
			Init();
		if (doc.hasRootElement()) {
			Element root = doc.getRootElement();
			Element el = null;
			el = root.getChild(strchild);
			if (el != null) {
				List list = el.getChildren();
				for (int i = 0; i < list.size(); i++) {
					Element item = (Element) list.get(i);
					retPro.setProperty(item.getName(), item.getText());
				}
			}
		}
		return retPro;
	}

	public String getConfigFilePath() {
		return configConstant.configFilePath;
	}

	public void setConfigFilePath(String configfilepath) {
		configConstant.configFilePath = configfilepath;
	}
	
	/*public static void main(String args[]){
		XmlConfigReader configReader = XmlConfigReader.getInstance("c:/tameai.xml");
		String result = configReader.getNodeValue("apps.server.url");
		System.out.println("result: " + result);
		
		Properties pro = configReader.getProperties("ldap");
		result = pro.getProperty("java.naming.provider.url");
		System.out.println("result: " + result);
		pro.clear();
		
		XmlConfigReader.clear();
	}*/
}
