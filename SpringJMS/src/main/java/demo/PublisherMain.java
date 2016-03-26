package demo;
import org.springframework.context.support.ClassPathXmlApplicationContext; 
import org.springframework.jms.core.JmsTemplate;

public class PublisherMain {
	
	
	public PublisherMain() {
		super();
		//  "META-INF/spring/messaging.xml"
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("resources/beans.xml");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		StandaloneSpringPublisher alertService = (StandaloneSpringPublisher) context.getBean("publisherService"); 
		alertService.sendAlert("Hello");
		context.close();
		//JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
		
		// Learning: For some reason, if jmsTemplate is passed into a thread, the program does not end. The resources held are visible in debug mode
/*		for(int i=0;i<1;i++) {
			Thread t = new Thread(new StandaloneSpringPublisher(jmsTemplate, "Deutsche Bank"));
			//Thread t = new Thread(new SimpleRunnable());
			t.start();
		}*/
	}

	public static void main(String[] args) throws InterruptedException {
		new PublisherMain();
	}
}
