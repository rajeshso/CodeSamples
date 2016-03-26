package com.db.tw.distribution.jms;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.TopicConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.tw.distribution.adapters.TopicPublisherFactory;

/**
 * This is the central repository for the JMSSessionPooledObjectFactories. This
 * is an internal Object pool implementation. One TopicConnectionFactory for
 * every JMS Server is entered in the repository
 * 
 * @author Rajesh
 *
 */
// TODO: Is synchronisation necessary? Check
public class JMSFactoryRegistry {
	private static final Logger LOGGER = LoggerFactory.getLogger(JMSFactoryRegistry.class);

	private Map<String, JMSSessionPooledObjectFactory> jmsFactoryMap = null;
	private volatile static JMSFactoryRegistry _instance;

	private JMSFactoryRegistry() {
		jmsFactoryMap = Collections
				.synchronizedMap(new HashMap<String, JMSSessionPooledObjectFactory>());
	}

	public static JMSFactoryRegistry getInstance() {
		if (_instance == null) {
			synchronized (JMSFactoryRegistry.class) {
				if (_instance == null) {
					_instance = new JMSFactoryRegistry();
				}
			}
		}
		return _instance;
	}

	public synchronized boolean containsJMSSessionPooledObjectFactory(
			String factoryID) {
		return jmsFactoryMap.containsKey(factoryID);
	}

	public synchronized void add(String factoryID,
			TopicConnection topicConnection) throws JMSException {
		LOGGER.debug("Adding {} to the JMSFactoryRegistry", factoryID);
		JMSSessionPooledObjectFactory jmsPooledFactory = new JMSSessionPooledObjectFactory(
				factoryID, topicConnection);
		topicConnection.start();
		jmsFactoryMap.put(factoryID, jmsPooledFactory);
	}

	public synchronized JMSSessionPooledObjectFactory get(String factoryID) {
		return jmsFactoryMap.get(factoryID);
	}

	public synchronized void remove(String factoryID) {
		if (!containsJMSSessionPooledObjectFactory(factoryID)) {
			return;
		}
		TopicConnection topicConnection = get(factoryID).getTopicConnection();
		try {
			topicConnection.stop();
			topicConnection.close();
		} catch (JMSException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		jmsFactoryMap.remove(factoryID);
	}

	public void clear() {
		synchronized (jmsFactoryMap) { // Synchronising on map, not set!
			Set<String> s = jmsFactoryMap.keySet();
			Iterator<String> i = s.iterator(); // Must be in synchronised block
			while (i.hasNext()) {
				String factoryID = i.next();
				remove(factoryID);
			}
		}
		jmsFactoryMap.clear();
	}
}
