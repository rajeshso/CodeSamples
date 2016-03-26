package com.raj.commons.keyedPool;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

public class JMSKeyedSessionPooledObjectFactory extends
		BaseKeyedPooledObjectFactory<ServerDetails, TopicSession> {

	@Override
	public TopicSession create(ServerDetails arg0) throws Exception {
		TopicConnection connection = arg0.getConnection();
		TopicSession topicSession = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
		System.out.println("TopicSession is "+ topicSession);
		return topicSession;
	}

	@Override
	public PooledObject<TopicSession> wrap(TopicSession arg0) {
		return new DefaultPooledObject<TopicSession>(arg0);
	}

	@Override
	public void destroyObject(ServerDetails key, PooledObject<TopicSession> p)   {
		try {
			System.out.println("destroy object for "+ p.toString() +" is called");
			p.getObject().close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
