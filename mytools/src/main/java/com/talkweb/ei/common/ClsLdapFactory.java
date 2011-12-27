package com.talkweb.ei.common;

import com.talkweb.ei.util.ldap.*;

public class ClsLdapFactory {
	private static LdapImpl ldapImpl = null;
	
	public static LdapImpl getLdapExecutor(){
		if(ldapImpl==null){
			ldapImpl =  new LdapImpl(ClsGlobalVar.GetLdapConstant());
		}
		return ldapImpl;
	}
	
	public static void main(String args[]){
		LdapImpl lp = ClsLdapFactory.getLdapExecutor();
		boolean result = lp.ValidateUser("000038670", "passw0rd");
    	System.out.println("result:" + result);
	}
}
