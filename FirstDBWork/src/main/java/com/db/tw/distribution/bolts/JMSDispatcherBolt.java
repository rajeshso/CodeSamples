package com.db.tw.distribution.bolts;

import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.storm.jms.bolt.JmsBolt;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.db.tw.distribution.utils.jms.SpringJmsProvider;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Tuple;
/**
 * This Bolt uses jmsTemplate instead of the jms connections
 * @author Rajesh
 *
 */
public class JMSDispatcherBolt extends JmsBolt {

	private static final long serialVersionUID = 6251632879678292576L;
	private JmsTemplate jmsTemplate;
	private SpringJmsProvider springJmsProvider;
	
	/**
	 * Initialises JMS resources.
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		if(this.springJmsProvider == null || this.producer == null){
			throw new IllegalStateException("JMS Provider and MessageProducer not set.");
		}
		this.collector = collector;
		LOG.debug("Connecting JMS..");
		try {
			/**
			ConnectionFactory cf = this.jmsProvider.connectionFactory();
			Destination dest = this.jmsProvider.destination();
			this.connection = cf.createConnection();
			this.session = connection.createSession(this.jmsTransactional,
					this.jmsAcknowledgeMode);
			this.messageProducer = session.createProducer(dest);
			
			connection.start();
			**/
			jmsTemplate = (JmsTemplate) stormConf.get("jmsTemplate");
		} catch (Exception e) {
			LOG.warn("Error creating JMS connection.", e);
		}	
	}
	
	/**
	 * Consumes a tuple and sends a JMS message.
	 * <p/>
	 * If autoAck is true, the tuple will be acknowledged
	 * after the message is sent.
	 * <p/>
	 * If JMS sending fails, the tuple will be failed.
	 */
	@Override
	public void execute(Tuple input) {
		// write the tuple to a JMS destination...
		LOG.debug("Tuple received. Sending JMS message.");
		System.out.println("Tuple received from "+ input.getSourceComponent() +" . Sending JMS message through "+ this.hashCode());
		
		try {
			final Message msg = this.producer.toMessage(this.session, input);
			if(msg != null){
				if (msg.getJMSDestination() != null) {
					System.out.println("JMS message destination is "+ msg.getJMSDestination().toString());
					//this.messageProducer.send(msg.getJMSDestination(), msg);
		             jmsTemplate.send(new MessageCreator() { 
		                 public Message createMessage(Session session) throws JMSException { 
		                     return msg; 
		                 } 
		             });
				} else {
					System.out.println("No JMS Message destination is provided");
					this.messageProducer.send(msg);
				}
			}
			if(this.autoAck){
				LOG.debug("ACKing tuple: " + input);
				this.collector.ack(input);
			}		
			try {
				Thread.sleep(1000);
			}catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		} catch (JMSException e) {
			// failed to send the JMS message, fail the tuple fast
			LOG.warn("Failing tuple: " + input);
			LOG.warn("Exception: ", e);
			System.err.println(this.hashCode());
			e.printStackTrace();
			this.collector.fail(input);
		}
	}	
	
	@Override
	public void cleanup() {
		System.out.println("cleanup of the JMSDispatcherBolt");
//		try {
//			System.out.println("Closing JMS connection.");
//			this.session.close();
//			this.connection.close();
//		} catch (JMSException e) {
//			LOG.warn("Error closing JMS connection.", e);
//		}
	}
	
    public SpringJmsProvider getSpringJmsProvider() {
		return springJmsProvider;
	}

	public void setSpringJmsProvider(SpringJmsProvider springJmsProvider) {
		this.springJmsProvider = springJmsProvider;
	}	
}
