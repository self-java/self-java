package com.talkweb.ei.itim;

import java.util.Hashtable;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import org.apache.log4j.Logger;
import com.ibm.itim.apps.InitialPlatformContext;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.jaas.callback.PlatformCallbackHandler;

public class ItimContext {
	String contextFactory = "com.ibm.itim.apps.impl.websphere.WebSpherePlatformContextFactory";

	String appServerUrl = "iiop://10.192.60.13:2809/cell/clusters/tim_cluster";

	String ejbUser = "hstimbind";

	String ejbPswd = "lrw3n6hs";

	String itimUser = "hstimbind";// "itim.pswd"

	String itimPswd = "passw0rd";// "itim.user"

	String itimConfigFile = "c:/jaas_login_was.conf";

	com.ibm.itim.apps.PlatformContext platform = null;

	javax.security.auth.Subject subject = null;

	LoginContext lc = null;

	PlatformCallbackHandler handler = null;

	Logger log = Logger.getLogger(ItimContext.class.getName());

	public void init() throws Exception {
		System.setProperty("java.security.auth.login.config", itimConfigFile);
		
		itimUser = "hstimbind";
		itimPswd = "passw0rd";
		
		/*Properties itimPro = new Properties();
		itimPro.setProperty("apps.context.factory", contextFactory);
		itimPro.setProperty("apps.server.url", appServerUrl);
		itimPro.setProperty("apps.ejb.user", ejbUser);
		itimPro.setProperty("apps.ejb.pswd", ejbPswd);*/
		
		/*Hashtable env = new Hashtable(); 
		env.put("enrole.platform.contextFactory", contextFactory);
		env.put("enrole.appServer.url", appServerUrl);
		env.put("enrole.appServer.ejbuser.principal", ejbUser);
		env.put("enrole.appServer.ejbuser.credentials", ejbPswd);*/
		
		Hashtable env = new Hashtable();
		env.put(InitialPlatformContext.CONTEXT_FACTORY, contextFactory);
		env.put(PlatformContext.PLATFORM_URL, appServerUrl);
		env.put(PlatformContext.PLATFORM_PRINCIPAL, ejbUser);
		env.put(PlatformContext.PLATFORM_CREDENTIALS, ejbPswd);


		platform = new InitialPlatformContext(env);
		handler = new PlatformCallbackHandler(itimUser, itimPswd);
		handler.setPlatformContext(platform);
	}

	public Subject getSubject() {
		return lc.getSubject();
	}

	public PlatformContext getPlatform() {
		return platform;
	}

	/**
	 * ≥ı ºªØ
	 * 
	 * @throws Exception
	 */
	public void login() {
		try {
			if (handler == null) {
				init();
			}
			lc = new LoginContext("ITIM", handler);
			lc.login();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	public void logout() {
		if (lc != null) {
			try {
				lc.logout();
			} catch (Exception e) {
				log.error(e.getMessage());
			}finally{
				lc = null;
			}
		}
	}
}
