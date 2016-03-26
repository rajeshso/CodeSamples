package demo;
import javax.jms.JMSException;
import javax.jms.Topic;

import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext; 

public class SubscriberMain {
	public static void main(String[] args) throws BeansException, JMSException {
		//  "META-INF/spring/messaging.xml"
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("resources/beans.xml"); 
		String beanName = "subscriberService";
		String topicName = "dtccTopic";
		if (args.length!=0) {
			if (args[0].equals("1")) {
				beanName = "subscriberService";
				topicName = "dtccTopic";
			}else {
				beanName = "subscriberService1";
				topicName = "dtccTopic1";
			}
		}
		
		StandaloneSpringSubscriber alertService = (StandaloneSpringSubscriber) context.getBean(beanName); 
		System.out.println("Listening "+ ((Topic) context.getBean(topicName)).getTopicName() +" ......");
		try {
			while(true) {
				alertService.recieveAlert();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.out.println("Stopped Listening");
		context.close();
	}
}
