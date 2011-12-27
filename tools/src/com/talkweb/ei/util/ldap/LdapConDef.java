package com.talkweb.ei.util.ldap;

public class LdapConDef {
	public final static String ldapJndiDriver = "java.naming.factory.initial";
	public final static String ldapUrl = "java.naming.provider.url";
	public final static String ldapUser = "java.naming.security.principal";
	public final static String ldapPassword = "java.naming.security.credentials";
	
	public final static String ldapConnPool = "com.sun.jndi.ldap.connect.pool";
	public final static String ldapConnPoolMaxSize = "com.sun.jndi.ldap.connect.pool.maxsize";
	public final static String ldapConnPoolInitSize = "com.sun.jndi.ldap.connect.pool.initsize";
	public final static String ldapConnPoolPrefSize = "com.sun.jndi.ldap.connect.pool.prefsize";
	public final static String ldapConnPoolTimeout = "com.sun.jndi.ldap.connect.pool.timeout";
	
	public final static String ldapUsersBase = "com.talkweb.jndi.ldap.usersbase";
	public final static String ldapOrgsBase = "com.talkweb.jndi.ldap.orgsbase";
	public final static String ldapGroupsBase = "com.talkweb.jndi.ldap.groupsbase";
}