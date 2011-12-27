package com.talkweb.ei.util.ldap;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.*;
import javax.naming.directory.*;
import org.apache.log4j.Logger;


public class LdapImpl {
	private LdapConstant ldapConstant = null;
	private DirContext context = null;
	
	private Properties proLdap = null; 

	Logger log = Logger.getLogger(LdapImpl.class.getName());

	public LdapImpl(LdapConstant ldapConstant) {
		this.ldapConstant = ldapConstant;
		this.proLdap = ldapConstant.GetProperties();
	}

	/**此方法必须调用 closeDirContext() 释放 ldap连接
	 * @param tmpSearchBase
	 * @param myFilter
	 * @param strscope
	 * @return
	 * @throws NamingException
	 */
	public NamingEnumeration FromLdapgetresults(String tmpSearchBase,
			String myFilter, int strscope) throws NamingException {
		SearchControls constraints = new SearchControls();
		NamingEnumeration results = null;
		
		try {
			context = getDirContext();
			constraints.setSearchScope(strscope);
			results = (NamingEnumeration) context.search(tmpSearchBase,
					myFilter, constraints);

		} catch (Exception ex) {
			log.error(ex.getMessage());
			}
		
		return results;
	}

	public Attributes getLdapAttrFromDn(String strDn, String[] attrids)
			throws NamingException {
		SearchControls constraints = new SearchControls();
		Attributes attrs = null;
		
		try {
			context = getDirContext();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			// 属性返回到页面
			// 节点的DN
			attrs = context.getAttributes(strDn, attrids);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		} finally {
			closeDirContext();
			
			if (attrs != null && attrs.size() <= 0)
				return null;
		}
		return attrs;
	}

	public boolean ldapModifyNode(String strDn, BasicAttributes attrs)
			throws Exception {

		try {
			context = getDirContext();
			context
					.modifyAttributes(strDn, DirContext.REPLACE_ATTRIBUTE,
							attrs);

		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		} finally {
			closeDirContext();
		}
		return true;
	}

	public boolean AddLdapNode(String strDn, BasicAttributes attrs) {
		boolean br = true;
		
		try {
			context = getDirContext();
			context.createSubcontext(strDn, attrs);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			br = false;
		} finally {
			closeDirContext();
		}
		return br;
	}

	public boolean AddLdapNodeAttr(String strDn, BasicAttributes attrs) {
		boolean br = true;
		
		try {
			context = getDirContext();
			context.modifyAttributes(strDn, DirContext.ADD_ATTRIBUTE, attrs);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			br = false;
		} finally {
			closeDirContext();
		}
		return br;
	}

	public boolean DelLdapNodeAttr(String strDn, BasicAttributes attrs) {
		boolean br = true;
		
		try {
			context = getDirContext();
			context.modifyAttributes(strDn, DirContext.REMOVE_ATTRIBUTE, attrs);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		} finally {
			closeDirContext();
		}
		return br;
	}

	public boolean DeleteLdapNode(String strDn) {
		boolean br = true;
		
		try {
			context = getDirContext();
			context.destroySubcontext(strDn);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			br = false;
		} finally {
			closeDirContext();
		}
		return br;
	}

	/**
	 * 关闭LDAP连接
	 * 
	 * @param dirContext
	 *            DirContext 已连接的LDAP的Context实例
	 */
	void closeDirContext() {
		if (context != null) {
			try {
				context.close();
			} catch (NamingException e) {
				log.error(e.getMessage());
			}
			context = null;
		}
	}

	DirContext getDirContext() {
		try {
			if(context != null){
				closeDirContext();
			}
			
			DirContext ctx = new InitialDirContext(proLdap);
			return ctx;
		} catch (NamingException ex) {
			log.error(ex.getMessage());
		}
		return null;
	}
	
	/**
	 * 登录时，验证用户是否为有效用户
	 * @param userID 用户帐号
	 * @param password 用户密码
	 * @return true：存在此用户 false：不存在
	 * @throws Exception 异常
	 */
	public boolean ValidateUser(String userid, String password){
		boolean validate = false;
		DirContext ctx = null;

		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, this.ldapConstant.ldapJndiDriver);
		////PROVIDER_URL是活动目录服务器的DNS主机名（如：ldap://tmh.stamp.com）
		env.put(Context.PROVIDER_URL, this.ldapConstant.ldapUrl);
		//必须指定一个关于simple的SECURITY_AUTHENTICATION，否则活动目录将期望
		//Windows 2000验证，这是Java不支持的
		env.put(Context.SECURITY_AUTHENTICATION, "Simple");
		//SECURITY_PRINCIPAL可以是Windows管理员帐户（显式的）或者
		//是任何能访问目录的其他帐户
		//一般情况下,在有DOMAIN的环境里,用户名要全名:
		//domain\\username
		//例如你的domain是:upgradetest,用户名是administartor
		//那么在写程序的时候,用户就应该是:upgradetest\administrator.而不能只写成administrator.
		//LDAP://svn.talkweb.com/CN=lf,OU=临时帐号,OU=三一集团,DC=svn,DC=talkweb,DC=com
		//env.put(Context.SECURITY_PRINCIPAL,"CN="+userID+",OU=临时帐号,OU=三一集团,DC=svn,DC=talkweb,DC=com");
		env.put(Context.SECURITY_PRINCIPAL, "uid=" + userid + "," + this.ldapConstant.ldapUsersBase);
		//只包含于SECURITY_PRINCIPAL给定的帐户的口令
		env.put(Context.SECURITY_CREDENTIALS, password);

		try {
			ctx = new InitialDirContext(env);
			validate = true;

		} catch (javax.naming.AuthenticationException authe) {
			log.error(authe.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception Ignore) {
					log.error(Ignore.getMessage());
				}
				ctx = null;
			}
		}

		return validate;
	}
	
	// 根据uid更改IDS相应用户密码
	public boolean ChangePassword(String uid, String newpwd) {
		try {
			BasicAttributes attrs = new BasicAttributes("userPassword", newpwd);
			String UserSearchBase = this.ldapConstant.ldapUsersBase;
			ldapModifyNode("uid=" + uid + "," + UserSearchBase, attrs);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}
    
    /*public static void main(String args[]){
    	LdapConstant ldapInfo = new LdapConstant();
    	LdapImpl ldapImpl = new LdapImpl(ldapInfo);
    	boolean result = ldapImpl.ValidateUser("000038670", "Sky651255");
    	System.out.println("result:" + result);
		
	}*/
}