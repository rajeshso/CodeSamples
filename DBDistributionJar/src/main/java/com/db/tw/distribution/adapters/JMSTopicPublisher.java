package com.db.tw.distribution.adapters;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.tw.distribution.Router;
import com.db.tw.distribution.jms.SessionPool;
import com.db.tw.distribution.publisher.Publisher;

/**
 * A JMSTopicPublisher receives <code>java.lang.String</code> objects from a
 * Storm topology and publishes JMS Messages to a destination (topic or queue).
 * <p/>
 * To use a JMSTopicPublisher in a topology, the following must be supplied:
 * <ol>
 * <li>A <code>JmsProvider</code> implementation.</li>
 * <li>A <code>JmsMessageProducer</code> implementation.</li>
 * </ol>
 * The <code>JmsProvider</code> provides the JMS
 * <code>javax.jms.ConnectionFactory</code> and
 * <code>javax.jms.Destination</code> objects required to publish JMS messages.
 * <p/>
 * The JMSTopicPublisher uses a <code>JmsMessageProducer</code> to translate
 * <code>java.lang.String</code> objects into <code>javax.jms.Message</code>
 * objects for publishing.
 * <p/>
 * Both JmsProvider and JmsMessageProducer must be set, or the bolt will fail
 * upon deployment to a cluster.
 * <p/>
 * The JMSTopicPublisher is typically an endpoint in a topology -- in other
 * words it does not emit any tuples.
 * 
 * @author Rajesh Somasundaram
 *
 */
public class JMSTopicPublisher implements Publisher {
	private static final Logger LOGGER = LoggerFactory.getLogger(JMSTopicPublisher.class);

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 9128465901995616539L;

	// JMS options
	public final static boolean JMS_TRANSACTIONAL = false;
	public final static int JMS_ACK_MODE = Session.AUTO_ACKNOWLEDGE;

	// Session objects
	private SessionPool sessionPool;
	private TopicSession topicSession;
	private Topic topic;
	private TopicPublisher topicPublisher;
	// Publisher objects
	private String payLoad;
	private String destination;

	public JMSTopicPublisher() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param sessionPool
	 * @param payLoad
	 */
	public JMSTopicPublisher(SessionPool sessionPool, String destination) {
		super();
		this.sessionPool = sessionPool;
		this.destination = destination;
	}

	/**
	 * The last edge of the program to publish the message to the destination.
	 * <p/>
	 * <p/>
	 * If JMS sending fails, the retry mechanism will be invoked TODO: Define
	 * and throw a dedicated exception instead of using a generic one
	 */
	// @Override
	public void execute() throws Exception {
		topicSession = sessionPool.borrowObject();
		topic = topicSession.createTopic(this.destination.trim());
		topicPublisher = topicSession.createPublisher(topic);
		LOGGER.info("Sending {} to {} ..", this.payLoad,
				topic.getTopicName());
		try {
			TextMessage message = topicSession.createTextMessage(this.payLoad);
			topicPublisher.publish(message);
		} catch (JMSException jmsexe) {
			// TODO: Retry mechanism and then audit the reason for exception
			// TODO: Be warned that the connection is alive during the wait
			// period.
			LOGGER.error(jmsexe.getMessage());
			throw jmsexe;
		} finally {
			LOGGER.debug("Say thank you and return the Session.");
			sessionPool.returnObject(topicSession);
		}
	}

	/**
	 * Releases JMS resources. Never close the session.
	 */
	// @Override
	public void cleanup() {
		try {
			if (topicPublisher != null)
				topicPublisher.close();
			topicPublisher = null;
			topic = null;
		} catch (JMSException e) {
			LOGGER.warn("Error cleaning up JMS resources.", e);
		}
	}

	@Override
	public void run() {
		LOGGER.debug("JMSTopicPublisher run called");
		try {
			prepare(null);
			execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("",e);
			// There is a chance that the task has not completed.
			// TODO: Do not retry. Log the error with the error message.
		} finally {
			cleanup();
		}
	}

	@Override
	public void prepare(Map<String, Object> context) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean testConnection() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setPayLoad(String payLoad) {
		this.payLoad = payLoad;
	}
}
