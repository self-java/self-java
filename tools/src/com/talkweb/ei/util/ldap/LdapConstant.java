package com.talkweb.ei.util.ldap;

import java.util.Properties;

public class LdapConstant {
	public String ldapJndiDriver = "com.sun.jndi.ldap.LdapCtxFactory";
	public String ldapUrl = "ldap://10.192.60.20:389";
	public String ldapUser = "cn=root";
	public String ldapPassword = "passw0rd";
	
	public String ldapConnPool = "true";
	public String ldapConnPoolMaxSize = "50";
	public String ldapConnPoolInitSize = "5";
	public String ldapConnPoolPrefSize = "5";
	public String ldapConnPoolTimeout = "10000";
	
	public String ldapUsersBase = "cn=users,dc=hs,dc=unicom";
	public String ldapOrgsBase = "cn=org,dc=hs,dc=unicom";
	public String ldapGroupsBase = "cn=workflowgroups,dc=hs,dc=unicom";
	
	public Properties GetProperties() {
		Properties retPro = new Properties();

		retPro.setProperty(LdapConDef.ldapJndiDriver, this.ldapJndiDriver);
		retPro.setProperty(LdapConDef.ldapUrl, this.ldapUrl);
		retPro.setProperty(LdapConDef.ldapUser, this.ldapUser);
		retPro.setProperty(LdapConDef.ldapPassword, this.ldapPassword);
		retPro.setProperty(LdapConDef.ldapConnPool, this.ldapConnPool);
		retPro.setProperty(LdapConDef.ldapConnPoolMaxSize, this.ldapConnPoolMaxSize);
		retPro.setProperty(LdapConDef.ldapConnPoolInitSize, this.ldapConnPoolInitSize);
		retPro.setProperty(LdapConDef.ldapConnPoolPrefSize, this.ldapConnPoolPrefSize);
		retPro.setProperty(LdapConDef.ldapConnPoolTimeout, this.ldapConnPoolTimeout);
		
		return retPro;
	}
}