package demo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
/**
 * Listener Implement Spring SessionAwareMessageListener Interface
 *
 */
@Component("messageListenerExample")
public class JmsMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		// This is the received message
		if (message instanceof ObjectMessage) {
			ObjectMessage msg = (ObjectMessage) message;
			try {
				System.out.println("OnMessage Received  :"
						+ msg.getObject().toString());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			TextMessage textMessage = (TextMessage) message;
			try {
				System.out.println("OnMessage Received  :"
						+ textMessage.getText().toString());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
