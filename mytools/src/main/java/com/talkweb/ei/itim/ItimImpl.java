package com.talkweb.ei.itim;

import com.ibm.itim.apps.*;
import com.ibm.itim.apps.identity.PersonMO;
import com.ibm.itim.apps.provisioning.PasswordManager;
import com.ibm.itim.apps.search.SearchMO;
import com.ibm.itim.dataservices.model.CompoundDN;
import com.ibm.itim.dataservices.model.DistinguishedName;
import com.ibm.itim.dataservices.model.ObjectProfileCategory;
import com.ibm.itim.dataservices.model.domain.Person;
import java.util.*;
import org.apache.log4j.Logger;

public class ItimImpl {
	private ItimContext itimContext = new ItimContext();

	Logger log = Logger.getLogger(ItimImpl.class.getName());

	public Person getPerson(String uid) {
		Person owner = null;

		try {
			itimContext.login();

			String containerFilter = "(&((objectclass=erPersonItem))(uid=" + uid
					+ "))";

			SearchMO searchMO = new SearchMO(itimContext.getPlatform(),
					itimContext.getSubject());
			searchMO.setCategory(ObjectProfileCategory.PERSON);
			String TENANT_DN = "ou=unicom,dc=unicom";
			searchMO
					.setContext(new CompoundDN(new DistinguishedName(TENANT_DN)));
			searchMO.setProfileName("Person");
			searchMO.setFilter(containerFilter);
			Collection people = searchMO.execute().getResults();
			if (people.size() > 0) {
				owner = (Person) people.iterator().next();
			} else {
				log.info("TIM中查无此人!员工编码为：" + uid);
			}
			return owner;
		} catch (Exception e) {
			log.error("search person:" + e.getMessage());
			return owner;
		} finally {
			itimContext.logout();
		}
	}

	public boolean ChangePassword(String uid, String newPwd) {
		try {
			// System.out.println("changePassword===mobile--="+mobile);

			Person person = getPerson(uid);
			if (person == null) {
				return false;
			}

			itimContext.login();
			PersonMO ownerMO = new PersonMO(itimContext.getPlatform(),
					itimContext.getSubject(), person.getDistinguishedName());

			PasswordManager pManager = new PasswordManager(itimContext
					.getPlatform(), itimContext.getSubject());
			Request request = pManager.synchPasswords(ownerMO, newPwd, null);

			/*
			 *//**
			 * 为了解决TIM修改密码延时，以及TAMAgent有故障时新密码登陆问题， 在修改TIM的时候同时修改IDS密码
			 */
			/*
			 * LdapImpl search = new LdapImpl(); boolean chids=
			 * search.changePassword(mobile,newPwd); if(!chids){
			 * System.out.println("----------------修改IDS密码失败-------------"+mobile); }
			 */

			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		} finally {
			itimContext.logout();
		}
	}

	/**
	 * 生成随即密码
	 * 
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	public static void main(String args[]) throws Exception {
		ItimImpl itimImpl = new ItimImpl();
		boolean result = itimImpl.ChangePassword("000038670", "Sky651255");
		if(result==true){
			System.out.println("Successful!");
		}
		else{
			System.out.println("Failed!");
		}
		
	}
}
