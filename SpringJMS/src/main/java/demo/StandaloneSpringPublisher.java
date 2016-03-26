package demo;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class StandaloneSpringPublisher implements
		StandaloneSpringPublisherService, Runnable {
	private JmsTemplate jmsTemplate;
	private String message;

	public StandaloneSpringPublisher() {
		super();
	}

	/**
	 * @param jmsTemplate
	 * @param message
	 */
	public StandaloneSpringPublisher(JmsTemplate jmsTemplate, String message) {
		super();
		this.jmsTemplate = jmsTemplate;
		this.message = message;
	}


	@Override
	public void sendAlert(final String msg) {
		if (this.jmsTemplate == null) {
			System.out.println("jmsOperation is null");
			return;
		}
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
		System.out.println("message " + msg + " is published ");
		return;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void run() {
		sendAlert("Deutsche Bank");
	}
}
