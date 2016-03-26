package com.raj.amq;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JmsUtils {
	private static final String CF_CLASS_NAME =
		"org.apache.activemq.jndi.ActiveMQInitialContextFactory";
	//http://activemq.apache.org/uri-protocols.html
	private static final String WMQ_URL = "vm://localhost?async=false";
	
	//TODO: Use JNDI lookup 
	protected Context getInitialContext() throws NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, CF_CLASS_NAME);
		props.put(Context.PROVIDER_URL, WMQ_URL);
		return new InitialContext(props);
	}
}
