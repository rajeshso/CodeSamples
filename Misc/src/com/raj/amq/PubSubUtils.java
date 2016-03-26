package com.raj.amq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class PubSubUtils extends JmsUtils {

	private static final String TCF_NAME = "SampleTCF";
	private static final String TOPIC_NAME = "dtcc.us.masterbroker";

	public TopicConnection getConnection()
		throws NamingException, JMSException {
		Context context = getInitialContext();
		TopicConnectionFactory tcf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		//TODO: Use JNDI lookup 
		/**
		TopicConnectionFactory qcf =
			(TopicConnectionFactory) context.lookup(TCF_NAME);**/
		return tcf.createTopicConnection();
	}

	public Topic getTopicProposed() throws NamingException {
		Context context = getInitialContext();
		return (Topic) context.lookup(TOPIC_NAME);
	}
	public Topic getTopic(Session session) throws JMSException {
		Destination destination = session.createTopic(TOPIC_NAME);
		return (Topic) destination;
	}	
}
