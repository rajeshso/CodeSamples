/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.db.tw.distribution.utils;

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
		this.destination = (Destination)context.getBean(destinationBean); //TODO : TRY Activate this usage. Try to replace the dynamic string topics with jms destination. This will help migrate TopicSession to Session
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
