package com.raj.amq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.NamingException;

import org.apache.commons.pool2.impl.EvictionConfig;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.commons.pool2.impl.AbandonedConfig;

import com.raj.commons.keyedPool.JMSKeyedSessionPooledObjectFactory;
import com.raj.commons.keyedPool.KeyedSessionPool;
import com.raj.commons.keyedPool.ServerDetails;
import com.raj.commons.keyedPool.pool.JMSSessionPooledObjectFactory;
import com.raj.commons.keyedPool.pool.SessionPool;

public class EfficientPublisher {
	private PubSubUtils utils;
	private TopicConnection connection;
	private TopicSession session;
	private TopicPublisher publisher;
	
	public static void main(String[] args)
			throws Exception {
		EfficientPublisher publisher = new EfficientPublisher();
			publisher.connect();
			/**
			String message = "ignored";
			System.out.print("Hello");
			while (message.length() > 0) {
				byte[] input = new byte[40];
				System.out.print("Enter a message: ");
				System.in.read(input);
				message = (new String(input, 0, input.length)).trim();
				if (message.length() > 0)
					publisher.sendMessage(message);
			}
			publisher.disconnect();**/
		}

		private EfficientPublisher() {
			utils = new PubSubUtils();
		}

		private void connect() throws Exception {
			ServerDetails serverDetails1 = new ServerDetails();
			serverDetails1.setBrokerURL("tcp://localhost:61616");
			serverDetails1.setFTP(false);
			serverDetails1.setJMS(true);
			serverDetails1.setConnection(utils.getConnection());
			//connection = utils.getConnection();
			//session =
			//		serverDetails1.getConnection().createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//keyedConnectionTest(serverDetails1);
			connectionTest(serverDetails1);
		}

		private void keyedConnectionTest(ServerDetails serverDetails1)
				throws Exception, JMSException {
			JMSKeyedSessionPooledObjectFactory jmsPooledFactory = new JMSKeyedSessionPooledObjectFactory();
			GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
			KeyedSessionPool jmsPool = new KeyedSessionPool(jmsPooledFactory, config);
			TopicSession session = jmsPool.borrowObject(serverDetails1);
			//publisher = session.createPublisher(utils.getTopic(session));
			System.out.println("Borrowed Session is "+ session);
			jmsPool.returnObject(serverDetails1, session);
			System.out.println("Session returned.");
			session = jmsPool.borrowObject(serverDetails1);
			System.out.println("Borrowed Session is "+ session);
			jmsPool.returnObject(serverDetails1, session);
			jmsPool.clear();
			jmsPool.close();
			serverDetails1.getConnection().close();
		}

		private void connectionTest(ServerDetails serverDetails1)
				throws Exception, JMSException {
			JMSSessionPooledObjectFactory jmsPooledFactory = new JMSSessionPooledObjectFactory("1", serverDetails1.getConnection());
			GenericObjectPoolConfig config = new GenericObjectPoolConfig();
			config.setMaxTotal(10);//Sets the cap on the number of objects that can be allocated by the pool (checked out to clients, or idle awaiting checkout) at a given time
			config.setTestWhileIdle(true);//whether objects sitting idle in the pool will be validated by the idle object evictor
			config.setTestOnBorrow(false);
			config.setTestOnReturn(false);
			config.setTestOnCreate(false);
			config.setTimeBetweenEvictionRunsMillis(30000); //Sets the number of milliseconds to sleep between runs of the idle object evictor thread. When non-positive, no idle object evictor thread will be run
			config.setMinEvictableIdleTimeMillis(4000);//the minimum amount of time an object may sit idle in the pool before it is eligible for eviction by the idle object evictor 
			config.setMinIdle(1);//Sets the target for the minimum number of idle objects to maintain in the pool.
			config.setMaxIdle(2);//the cap on the number of "idle" instances in the pool.
			config.setBlockWhenExhausted(true);//Sets whether to block when the borrowObject() method is invoked when the pool is exhausted (the maximum number of "active" objects has been reached).
			config.setMaxWaitMillis(2000); //Sets the maximum amount of time (in milliseconds) the borrowObject() method should block before throwing an exception when the pool is exhausted and getBlockWhenExhausted() is true
			
			//TODO: Abandoned not working
			AbandonedConfig abandonedConfig = new AbandonedConfig();
			abandonedConfig.setRemoveAbandonedTimeout(3);
			abandonedConfig.setRemoveAbandonedOnBorrow(true);

			SessionPool jmsPool = new SessionPool(jmsPooledFactory, config, abandonedConfig);
			TopicSession session1 = jmsPool.borrowObject();
			//publisher = session.createPublisher(utils.getTopic(session));
			System.out.println("Borrowed Session is "+ session1);
			TopicSession session2 = jmsPool.borrowObject();
			System.out.println("Borrowed Session is "+ session2);
			TopicSession session3 = jmsPool.borrowObject();
			System.out.println("Borrowed Session is "+ session3);	
			jmsPool.returnObject(session1);
			System.out.println("Session "+ session1.toString() +" returned.");			
			jmsPool.returnObject(session2);
			System.out.println("Session "+ session2.toString() +" returned.");
			jmsPool.returnObject(session3);
			System.out.println("Session "+ session3.toString() +" returned.");
			System.out.println("getNumIdle() = "+ jmsPool.getNumIdle());
			System.out.println("getNumActive() = "+ jmsPool.getNumActive());
			System.out.println("getNumWaiters() = "+ jmsPool.getNumWaiters() );			
			try {
				System.out.println("Sleeping for 5 seconds...");
				Thread.currentThread().sleep(5000);
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("getNumIdle() = "+ jmsPool.getNumIdle());
			System.out.println("getNumActive() = "+ jmsPool.getNumActive());
			System.out.println("getNumWaiters() = "+ jmsPool.getNumWaiters() );
			try {
				System.out.println("Sleeping for 5 seconds...");
				Thread.currentThread().sleep(5000);
			}catch(Exception e) {
				e.printStackTrace();
			}	
			System.out.println("getNumIdle() = "+ jmsPool.getNumIdle());
			System.out.println("getNumActive() = "+ jmsPool.getNumActive());
			System.out.println("getNumWaiters() = "+ jmsPool.getNumWaiters() );			
			jmsPool.clear();
			jmsPool.close();
			serverDetails1.getConnection().close();
		}
		private void sendMessage(String text) throws JMSException {
			Message message = session.createTextMessage(text);
			publisher.publish(message);
			System.out.println(
				"Published message <"
					+ text
					+ "> with ID <"
					+ message.getJMSMessageID()
					+ ">");
		}

		private void disconnect() throws JMSException {
			publisher.close();
			session.close();
			connection.close();
			System.out.println("Publisher stopped.");
		}
}
