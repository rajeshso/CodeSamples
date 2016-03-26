package com.raj.amq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.NamingException;

public class StandaloneSubscriber implements MessageListener {

	private PubSubUtils utils;
	private TopicConnection connection;
	private TopicSession session;
	private TopicSubscriber subscriber;

	public static void main(String[] args)
		throws NamingException, JMSException, IOException {
		StandaloneSubscriber subscriber = new StandaloneSubscriber();
		subscriber.connect();
		System.in.read();
		subscriber.disconnect();
	}

	private StandaloneSubscriber() {
		utils = new PubSubUtils();
	}

	private void connect() throws NamingException, JMSException {
		connection = utils.getConnection();
		session =
			connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = utils.getTopic(session);
		subscriber = session.createSubscriber(topic);
		subscriber.setMessageListener(this);
		connection.start();
		System.out.println("Subscriber started.");
	}

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage tMessage = (TextMessage) message;
			String text;
			text = tMessage.getText();
			System.out.println(
				"Received message <"
					+ text
					+ "> with ID <"
					+ message.getJMSMessageID()
					+ ">");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void disconnect() throws JMSException {
		subscriber.close();
		session.close();
		connection.stop();
		connection.close();
		System.out.println("Subscriber stopped.");
	}
}