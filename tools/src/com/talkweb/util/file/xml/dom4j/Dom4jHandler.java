package com.talkweb.util.file.xml.dom4j;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * @author bluesky
 * http://www.ibm.com/developerworks/cn/xml/x-dom4j.html?ca=drs-
 */
public class Dom4jHandler {
	
	public void otherOperate() throws DocumentException{
		SAXReader reader = new SAXReader();
		Document docu = reader.read(new File("/domain.xml"));

		// 基础属性
		// domain

		// 更改元素的属性值
		Element graphics = docu.getRootElement().element("devices")
				.element("graphics");
		Attribute attrPort = graphics.attribute("port");
		attrPort.setText("");
		// 更改元素值
		Element nameEle = docu.getRootElement().element("name");
		nameEle.setText("new name");
		// 以字符串形式返回xml
		String docXmlText = docu.asXML();
	}

	public void generateDocument() {

		//创建操作对象
		Document document = DocumentHelper.createDocument();

		//添加根
		Element catalogElement = document.addElement("catalog");
		catalogElement.addComment("An XML Catalog");

		catalogElement.addProcessingInstruction("target", "text");

		//添加子项
		Element journalElement = catalogElement.addElement("journal");
		//添加属性
		journalElement.addAttribute("title", "XML Zone");
		journalElement.addAttribute("publisher", "IBM developerWorks");

		Element articleElement = journalElement.addElement("article");
		articleElement.addAttribute("level", "Intermediate");
		articleElement.addAttribute("date", "December-2001");

		Element titleElement = articleElement.addElement("title");
		//设置值
		titleElement.setText("Java configuration with XML Schema");

		Element authorElement = articleElement.addElement("author");
		Element firstNameElement = authorElement.addElement("firstname");
		firstNameElement.setText("Marcello");
		Element lastNameElement = authorElement.addElement("lastname");
		lastNameElement.setText("Vitaletti");

		//document.addDocType("catalog", null, "file://c:/Dtds/catalog.dtd");

		try {
			//创建输出
			XMLWriter output = new XMLWriter(new FileWriter(new File(
					"/home/bluesky/test/catalog/catalog.xml")));
			output.write(document);
			output.flush();
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	//Attribute 属性
	//Element 元素
	public void modifyDocument(File inputXml) {
		try {
			//创建阅读器
			SAXReader saxReader = new SAXReader();
			
			saxReader.setEncoding("utf-8"); 
			
			//加载
			Document document = saxReader.read(inputXml);
			
			//获取 节点中的属性 @ 集合
			List<?> list = document.selectNodes("//article/@level");
			
			Iterator<?> iter = list.iterator();
			while (iter.hasNext()) {
				//遍历属性
				Attribute attribute = (Attribute) iter.next();
				//配对属性值
				if (attribute.getValue().equals("Intermediate"))
					attribute.setValue("Introductory");
			}

			list = document.selectNodes("//article/@date");
			iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attribute = (Attribute) iter.next();
				if (attribute.getValue().equals("December-2001"))
					attribute.setValue("October-2002");
			}
			
			//获取子节点 集合
			list = document.selectNodes("//article");
			iter = list.iterator();
			while (iter.hasNext()) {
				//遍历子节点
				Element element = (Element) iter.next();
				
				//获取子节点
				Iterator iterator = element.elementIterator("title");
				
				while (iterator.hasNext()) {
					Element titleElement = (Element) iterator.next();
					//比对值
					if (titleElement.getText().equals(
							"Java configuration with XMLSchema"))
						//设置值
						titleElement
								.setText("Create flexible and extensible XML schema");
				}
			}
			
			list = document.selectNodes("//article/author");
			iter = list.iterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Iterator iterator = element.elementIterator("firstname");
				while (iterator.hasNext()) {
					Element firstNameElement = (Element) iterator.next();
					if (firstNameElement.getText().equals("Marcello"))
						firstNameElement.setText("Ayesha");
				}
			}
			
			list = document.selectNodes("//article/author");
			iter = list.iterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Iterator iterator = element.elementIterator("lastname");
				while (iterator.hasNext()) {
					Element lastNameElement = (Element) iterator.next();
					if (lastNameElement.getText().equals("Vitaletti"))
						lastNameElement.setText("Malik");
				}
			}
			
			XMLWriter output = new XMLWriter(new FileWriter(new File(
					"/home/bluesky/test/catalog/catalog-modified.xml")));
			output.write(document);
			output.close();
		}

		catch (DocumentException e) {
			System.out.println(e.getMessage());
			
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] argv) {
		/*Dom4jHandler dom4j = new Dom4jHandler();
		dom4j.generateDocument();*/

		System.out.println("ok");

		Dom4jHandler dom4jParser = new Dom4jHandler();
		dom4jParser.modifyDocument(new File(
				"/home/bluesky/test/catalog/catalog.xml"));
	}
}
