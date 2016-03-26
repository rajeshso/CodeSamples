package demo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;

public class StandaloneSpringSubscriber {
	private JmsTemplate jmsTemplate;

	public StandaloneSpringSubscriber() {
		super();
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void recieveAlert() throws JMSException {
		if (this.jmsTemplate == null) {
			System.out.println("jmsOperation is null");
			return;
		}
		Message receivedMessage = jmsTemplate.receive();
		if (receivedMessage != null) {
			TextMessage msg = (TextMessage) receivedMessage;
			System.out.println("Message Received :" + msg.getText() + "\n\n\n\n\n");
		}
	}
}
