package com.db.tw.distribution.utils.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.storm.jms.JmsProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

//import com.db.tw.distribution.utils.JmsProvider;


/**
 * A <code>JmsProvider</code> that uses the spring framework
 * to obtain a JMS <code>ConnectionFactory</code> and 
 * <code>Desitnation</code> objects.
 * <p/>
 * The constructor takes three arguments:
 * <ol>
 * <li>A string pointing to the the spring application context file contining the JMS configuration
 * (must be on the classpath)
 * </li>
 * <li>The name of the connection factory bean</li>
 * <li>The name of the destination bean</li>
 * </ol>
 * 
 * 
 * @author Rajesh
 *
 */
//@SuppressWarnings("serial")
public class SpringJmsProvider implements JmsProvider {

	private static final long serialVersionUID = 2551664309779900327L;
	private ConnectionFactory connectionFactory;
	private Destination destination;
	//private JmsTemplate jmsTemplate;
	
	/**
	 * Constructs a <code>SpringJmsProvider</code> object given the name of a
	 * classpath resource (the spring application context file), and the bean
	 * names of a JMS connection factory and destination.
	 * 
	 * @param appContextClasspathResource - the spring configuration file (classpath resource)
	 * @param connectionFactoryBean - the JMS connection factory bean name
	 * @param destinationBean - the JMS destination bean name
	 */
	public SpringJmsProvider(String appContextClasspathResource, String connectionFactoryBean, String destinationBean, String jmsTemplateBean) {
		ApplicationContext context = new ClassPathXmlApplicationContext(appContextClasspathResource);
		this.connectionFactory = (ConnectionFactory)context.getBean(connectionFactoryBean);
		this.destination = (Destination)context.getBean(destinationBean);
		//this.jmsTemplate = (JmsTemplate) context.getBean(jmsTemplateBean);
	}

	public ConnectionFactory connectionFactory() throws Exception {
		return this.connectionFactory;
	}

	public Destination destination() throws Exception {
		return this.destination;
	}

	//@Override
/*	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}*/
}
