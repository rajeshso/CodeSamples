package com.db.tw.distribution.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.Destination;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * A <code>JmsProvider</code> that uses the spring framework
 * to obtain a JMS <code>ConnectionFactory</code> and 
 * <code>Destination</code> objects.
 * <p/>
 * The constructor takes three arguments:
 * <ol>
 * <li>A string pointing to the the spring application context file containing the JMS configuration
 * (must be on the classpath)
 * </li>
 * <li>The name of the connection factory bean</li>
 * <li>The name of the destination bean</li>
 * </ol>
 * 
 * 
 * @author Rajesh Somasundaram
 *
 */

public class SpringJmsProvider implements JmsProvider {
	private static final long serialVersionUID = 1L;
	private QueueConnectionFactory qConnectionFactory;
	private TopicConnectionFactory topicConnectionFactory;
	private Destination destination;
	private HashMap<String, TopicConnectionFactory> connectionFactoryMap = new HashMap<String, TopicConnectionFactory>();
	
	/**
	 * Constructs a <code>SpringJmsProvider</code> object given the name of a
	 * classpath resource (the spring application context file), and the bean
	 * names of a JMS connection factory and destination.
	 * 
	 * @param appContextClasspathResource - the spring configuration file (classpath resource)
	 * @param connectionFactoryBean - the JMS connection factory bean name
	 * @param destinationBean - the JMS destination bean name
	 */
	public SpringJmsProvider(String appContextClasspathResource, String connectionFactoryBean, String destinationBean){
		ApplicationContext context = new ClassPathXmlApplicationContext(appContextClasspathResource);
		this.topicConnectionFactory = (TopicConnectionFactory) context.getBean(connectionFactoryBean);
		connectionFactoryMap.put(connectionFactoryBean, topicConnectionFactory);
		this.destination = (Destination)context.getBean(destinationBean); //TODO : Remove this field. We are now using dynamic topics
	}

	//Note that the following methods are thread-safe because of atomicity. Remember this before you make any changes

	//Van suggested not to use this destination, but had some reservations. Check with Van before using this destination
	@Override
	public Destination destination(String topicName) throws Exception {
		return this.destination;
	}

	@Override
	public QueueConnectionFactory getQueueConnectionFactory(String key) throws Exception {
		return qConnectionFactory;
	}

	@Override
	public TopicConnectionFactory getTopicConnectionFactory(String key) throws Exception {
		return connectionFactoryMap.get(key);
	}

	@Override
	public List<String> getAvailableServerIDs() throws Exception {
		List<String> availableServerList = new ArrayList<String>();
		availableServerList.add("jmsConnectionFactory"); //TODO: Hardcoded here. This value should come from constructor
		return availableServerList;
	}

}
