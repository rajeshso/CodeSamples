package com.db.tw.distribution.providers;

import java.io.Serializable;
import java.util.List;

import javax.jms.Destination;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
/**
 * A <code>JmsProvider</code> object encapsulates the <code>ConnectionFactory</code>
 * and <code>Destination</code> JMS objects the <code>JmsBolt</code> needs to manage
 * a topic/queue connection over the course of it's lifecycle.
 * 
 * @author Rajesh Somasundaram
 *
 */
public interface JmsProvider extends Serializable{
	
	/**
	 * Provides the JMS <code>ConnectionFactory</code>
	 * @param key TODO
	 * @return the QueueConnectionFactory - The Connection factory should be usable. 
	 * 			If authentication or any other requirements are present, they should be 
	 * 			performed in the implementation. 
	 * @throws Exception
	 */
	public QueueConnectionFactory getQueueConnectionFactory(String key) throws Exception;
	
	/**
	 * Provides the JMS <code>ConnectionFactory</code>
	 * @param key TODO
	 * @return the TopicConnectionFactory - The Connection factory should be usable. 
	 * 			If authentication or any other requirements are present, they should be 
	 * 			performed in the implementation. 
	 * @throws Exception
	 */
	public TopicConnectionFactory getTopicConnectionFactory(String key) throws Exception;

	/**
	 * Provides the Available JMS Servers configured in the system
	 * 
	 * @param key TODO
	 * @return List<String> - The Connection factory should be usable. 
	 * 			If authentication or any other requirements are present, they should be 
	 * 			performed in the implementation. 
	 * @throws Exception
	 */
	public List<String> getAvailableServerIDs() throws Exception;
	
	/**
	 * Provides the <code>Destination</code> (topic or queue) to which the
	 * <code>Bolt</code> will send the message. Check with Van before using this functionality.
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public Destination destination(String topicName) throws Exception;
}
