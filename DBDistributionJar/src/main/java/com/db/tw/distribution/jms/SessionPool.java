package com.db.tw.distribution.jms;

import javax.jms.TopicSession;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.tw.distribution.adapters.TopicPublisherFactory;

//One PooledObjectFactory is expected to have only one Session Pool, so the factory ID/SERVERID is the unique ID
/**
 * This Session Pool represents the pool for one Message Oriented middleware.
 * The user is expected to borrow and return the Session after use. A number of
 * performance characteristics may be configured through the configuration
 * parameters.
 * 
 * The factory ID or SERVERID can be the key for this Session Pool.
 * 
 * @author Rajesh
 *
 */
public class SessionPool extends GenericObjectPool<TopicSession> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionPool.class);

	private static final long serialVersionUID = 1L;
	private String factoryID;

	public SessionPool(PooledObjectFactory<TopicSession> factory,
			GenericObjectPoolConfig config) {
		super(factory, config);
		LOGGER.debug("SessionPool created for {} with GenericObjectPoolConfig", factory.toString());
	}

	/**
	 * @param factory
	 */
	public SessionPool(PooledObjectFactory<TopicSession> factory) {
		super(factory);
		LOGGER.debug("SessionPool created for {}", factory.toString());
	}

	public String getFactoryID() {
		return factoryID;
	}

	public void setFactoryID(String factoryID) {
		this.factoryID = factoryID;
	}
}
