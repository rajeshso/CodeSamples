package com.db.tw.distribution.topology;
import com.db.tw.distribution.bolts.WordCounter;
import com.db.tw.distribution.bolts.WordNormalizer;
import com.db.tw.distribution.spouts.WordReader;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;

import org.apache.storm.jms.JmsMessageProducer;
import org.apache.storm.jms.JmsProvider;
import org.apache.storm.jms.JmsTupleProducer;
import org.apache.storm.jms.bolt.JmsBolt;
import org.apache.storm.jms.spout.JmsSpout;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.db.tw.distribution.utils.jms.SpringJmsProvider;

import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.utils.Utils;
//This file has been modified using jmsTemplate.
public class TopologyMain {
	private static final String WORD_NORMALIZER = "word-normalizer";
	private static final String WORD_COUNTER = "word-counter";
	private static final String WORD_READER = "word-reader";
	public static final String FINAL_BOLT = "FINAL_BOLT";
	public static final String JMS_TOPIC_BOLT = "JMS_TOPIC_BOLT";
		
	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
         
		// JMS Topic provider
		final JmsProvider jmsTopicProvider = new SpringJmsProvider(
				"jms-activemq.xml", "jmsConnectionFactory",
				"notificationTopic", "topicTemplate");
	
		//JMS Bolt definition
		// bolt that publishes to a JMS Topic		
		JmsBolt jmsBolt = new JmsBolt();
		//JMSDispatcherBolt jmsBolt = new JMSDispatcherBolt();
		jmsBolt.setJmsProvider(jmsTopicProvider);
		// anonymous message producer just calls toString() on the tuple to create a jms message
		jmsBolt.setJmsMessageProducer(new JmsMessageProducer() {
			@Override
			public Message toMessage(Session session, Tuple input) throws JMSException{
				System.out.println("Sending JMS Message:" + input.toString());
				TextMessage tm = session.createTextMessage(input.toString());
				try {
					tm.setJMSDestination(jmsTopicProvider.destination());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return tm;
			}
		});
		
        //Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(WORD_READER,new WordReader());
		builder.setBolt(WORD_NORMALIZER, new WordNormalizer())
			.shuffleGrouping(WORD_READER);
		builder.setBolt(WORD_COUNTER, new WordCounter(),1)
			.fieldsGrouping(WORD_NORMALIZER, new Fields("word"));
		builder.setBolt(JMS_TOPIC_BOLT, jmsBolt)
			.shuffleGrouping(WORD_COUNTER);
		System.out.println("The Topology Builder is "+ builder.toString());
		
        //Configuration
		Config conf = new Config();
		conf.put("wordsFile", args[0]);
		// conf.put("context", context); // Error is java.lang.IllegalArgumentException: Topology conf is not json-serializable
		conf.setDebug(true);
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		
        //Topology run
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
		//StormSubmitter.submitTopology("TopologyMain",conf,builder.createTopology());
		Thread.sleep(5000);
		cluster.shutdown();
	}
}
