package com.talkweb.util.file.json.fastjson;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/** 使用例子 url： http://code.alibabatech.com/wiki/pages/viewpage.action?pageId=2424946
 * http://code.alibabatech.com/wiki/display/FastJSON/Examples
 * 
 * 文件名称:    DemoFastjson.java
 * 内容摘要: 
 * @author:   xuhong 
 * @version:  1.0  
 * @Date:     2011-12-27 下午10:59:02  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2011-12-27        xuhong     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2011
 * 公司:   拓维信息系统股份有限公司
 */
public class DemoFastjson {
	
	public static class User {
		private Long id;
		private String name;

		public Long getId() { return id; }
		public void setId(Long id) { this.id = id; }

		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
	}
	
	//must static
	public static class Group {
		private Long id;
		private String name;
		private List<User> users = new ArrayList<User>();

		public Long getId() { return id; }
		public void setId(Long id) { this.id = id; }

		public String getName() { return name; }
		public void setName(String name) { this.name = name; }

		public List<User> getUsers() { return users; }
		public void setUsers(List<User> users) { this.users = users; }
	}
	
	public String toEnJson() {
		// encode Object to json

		Group group = new Group();
		group.setId(0L);
		group.setName("admin");

		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");

		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");

		group.getUsers().add(guestUser);
		group.getUsers().add(rootUser);

		//编码
		String jsonString = JSON.toJSONString(group);
		
		/*输出结果 {"id":0,"name":"admin","users":[{"id":2,"name":"guest"},{"id":3,"name":"root"}]}
		 * {
		      "name":"admin",
		      "id":0,"users":[
		                             {"name":"guest","id":2},
		                             {"name":"root","id":3}
		                         ]
		}*/

		System.out.println(jsonString);
		
		//Group group2 = JSON.parseObject(jsonString, Group.class);
		
		return jsonString;
	}
	
	public void toDeJson(String jsonString){
		System.out.println("jsonString:" + jsonString);
		
		
		// decode Object from json
		//解码
		Group group2 = JSON.parseObject(jsonString, Group.class);
	}
	
	
	/** 
	 * @Title: main 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param args   
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DemoFastjson demo = new DemoFastjson();  
		String jsonString = demo.toEnJson();
		
	    //System.out.println("jsonString:" + jsonString);
		demo.toDeJson(jsonString);
		
	}

}
