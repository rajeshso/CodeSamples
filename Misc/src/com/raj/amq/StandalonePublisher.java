package com.raj.amq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.NamingException;

public class StandalonePublisher {

	private PubSubUtils utils;
	private TopicConnection connection;
	private TopicSession session;
	private TopicPublisher publisher;

	public static void main(String[] args)
		throws NamingException, JMSException, IOException {
		StandalonePublisher publisher = new StandalonePublisher();
		publisher.connect();
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
		publisher.disconnect();
	}

	private StandalonePublisher() {
		utils = new PubSubUtils();
	}

	private void connect() throws NamingException, JMSException {
		connection = utils.getConnection();
		session =
			connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		publisher = session.createPublisher(utils.getTopic(session));
		System.out.println("Publisher started.");
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
