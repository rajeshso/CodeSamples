package com.raj.commons.keyedPool.pool;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class JMSSessionPooledObjectFactory extends BasePooledObjectFactory<javax.jms.TopicSession> {
	
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

	//Creates an object instance, to be wrapped in a PooledObject.
	@Override
	public TopicSession create() throws Exception {
		return topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	//Wrap the provided instance with an implementation of PooledObject.
	@Override
	public PooledObject<TopicSession> wrap(TopicSession arg0) {
		return new DefaultPooledObject<TopicSession>(arg0);
	}
	
	@Override
	public void destroyObject(PooledObject<TopicSession> p)   {
		System.out.println("JMSSessionPooledObjectFactory destroyObject is called");
		try {
			p.getObject().close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


/*	@Override 	 
	public PooledObject<TopicSession> makeObject() {
		//Create an instance that can be served by the pool and wrap it in a PooledObject to be managed by the pool.
		return null;
	}*/
	
/*	@Override 
	public void passivateObject(PooledObject<TopicSession> p) {
		//No-op.
	}*/
	
/*	@Override 
	public boolean validateObject(PooledObject<TopicSession> p) {
		//This implementation always returns true.
		return true;
	}*/

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
