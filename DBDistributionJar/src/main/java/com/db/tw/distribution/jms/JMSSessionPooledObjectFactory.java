package com.db.tw.distribution.jms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.tw.distribution.adapters.TopicPublisherFactory;

/**
 * This is an internal Object pool implementation. One TopicConnectionFactory
 * for every JMS Server is entered in the repository
 * 
 * @author Rajesh
 *
 */
// This class is immutable, and therefore thread-safe
public class JMSSessionPooledObjectFactory extends
		BasePooledObjectFactory<TopicSession> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JMSSessionPooledObjectFactory.class);

	private String factoryID;
	private TopicConnection topicConnection;

	/**
	 * @param factoryID
	 * @param topicConnection
	 */
	public JMSSessionPooledObjectFactory(String factoryID,
			TopicConnection topicConnection) {
		super();
		this.factoryID = factoryID;
		this.topicConnection = topicConnection;
	}

	// Creates an object instance, to be wrapped in a PooledObject.
	@Override
	public TopicSession create() throws Exception {
		LOGGER.debug("create() topic session");
		return topicConnection.createTopicSession(false,
				Session.AUTO_ACKNOWLEDGE);
	}

	// Wrap the provided instance with an implementation of PooledObject.
	@Override
	public PooledObject<TopicSession> wrap(TopicSession arg0) {
		LOGGER.debug("wrap() topic session");
		return new DefaultPooledObject<TopicSession>(arg0);
	}

	@Override
	public void destroyObject(PooledObject<TopicSession> p) {
		LOGGER.debug("JMSSessionPooledObjectFactory destroyObject is called");
		try {
			p.getObject().close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			LOGGER.error("",e);
		}
	}

	public String getFactoryID() {
		return factoryID;
	}

	public void setFactoryID(String factoryID) {
		this.factoryID = factoryID;
	}

	public TopicConnection getTopicConnection() {
		return topicConnection;
	}

	public void setTopicConnection(TopicConnection topicConnection) {
		this.topicConnection = topicConnection;
	}
}
