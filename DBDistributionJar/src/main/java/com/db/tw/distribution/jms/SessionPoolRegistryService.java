package com.db.tw.distribution.jms;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.tw.distribution.adapters.TopicPublisherFactory;

/**
 * The Session Pool Registry Service is the repository for all the SessionPools
 * of the system. Both the SessionPool and the registry are thread safe. The
 * service has to be hosted and kept alive for the JMS Publishers to work.
 * 
 * @author Rajesh
 *
 */
public class SessionPoolRegistryService implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionPoolRegistryService.class);

	private Map<String, SessionPool> sessionPoolMap = null; // Storm can serialise this object
	private volatile static SessionPoolRegistryService _instance;

	private SessionPoolRegistryService() {
		sessionPoolMap = Collections
				.synchronizedMap(new HashMap<String, SessionPool>());
		LOGGER.info("SessionPoolRegistryService started");
	}

	public static SessionPoolRegistryService getInstance() {
		if (_instance == null) {
			synchronized (SessionPoolRegistryService.class) {
				if (_instance == null) {
					_instance = new SessionPoolRegistryService();
				}
			}
		}
		return _instance;
	}

	public synchronized boolean containsJMSSessionPooledObjectFactory(
			String factoryID) {
		return sessionPoolMap.containsKey(factoryID);
	}

	public synchronized void add(String factoryID, SessionPool sessionPool) {
		sessionPoolMap.put(factoryID, sessionPool);
	}

	public synchronized SessionPool get(String factoryID) {
		return sessionPoolMap.get(factoryID);
	}

	public synchronized void remove(String factoryID) {
		if (!containsJMSSessionPooledObjectFactory(factoryID)) {
			return;
		}
		SessionPool sessionPool = get(factoryID);
		sessionPool.clear();
		sessionPool.close();
		sessionPoolMap.remove(factoryID);
	}

	public void clear() {
		synchronized (sessionPoolMap) { // Synchronizing on sessionPoolMap, not
										// s!
			Set<String> s = sessionPoolMap.keySet();
			Iterator<String> i = s.iterator(); // Must be in synchronized block
			while (i.hasNext()) {
				String factoryID = (String) i.next();
				remove(factoryID);
			}
		LOGGER.info("SessionPoolRegistry shutdown");
		}
		sessionPoolMap.clear();
	}
}
