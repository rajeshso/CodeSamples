package com.db.tw.distribution.example;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

import com.db.tw.distribution.jms.JMSFactoryRegistry;
import com.db.tw.distribution.jms.SessionPoolRegistryService;
import com.db.tw.distribution.providers.JmsProvider;
import com.db.tw.distribution.providers.SpringJmsProvider;

public class ExampleJmsTopology {
	public static final String DISTRIBUTION_BOLT = "DISTRIBUTION_BOLT";
	private static final String WORD_READER_SPOUT = "WORD-READER";

	public static void main(String[] args) throws Exception {

		// Data providers
		JmsProvider jmsTopicProvider = new SpringJmsProvider(
				"beans.xml", "jmsConnectionFactory",
				"notificationTopic");

		TopologyBuilder builder = new TopologyBuilder();
		Config conf = new Config();
		conf.setDebug(false);
		if (args.length==1)
			conf.put("wordsFile",args[0]);
		else
			conf.put("wordsFile", "src/main/resources/words.txt");
		
		// spout with one instance
		builder.setSpout(WORD_READER_SPOUT,new WordReaderSpout(),1);
		// Distribution Bolt with one instance
		DistributionBolt distributionBolt = new DistributionBolt();
		distributionBolt.setJmsProvider(jmsTopicProvider);
		builder.setBolt(DISTRIBUTION_BOLT, (IRichBolt) distributionBolt,1).shuffleGrouping(WORD_READER_SPOUT);
		
		LocalCluster cluster = new LocalCluster();
		try {
			cluster.submitTopology("storm-distribution-db_example", conf,
					builder.createTopology());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Utils.sleep(25000);
		SessionPoolRegistryService.getInstance().clear();
		JMSFactoryRegistry.getInstance().clear();;
		cluster.killTopology("storm-distribution-db_example");
		cluster.shutdown();
	}

/*	public static SessionPoolRegistryService getSessionPoolRegistry(JmsProvider jmsProvider) throws JMSException, Exception {
		JMSFactoryRegistry jmsFactoryRegistry = JMSFactoryRegistry.getInstance();
		jmsFactoryRegistry.add("1", null);
		SessionPoolRegistryService sessionPoolRegistry = SessionPoolRegistryService.getInstance();
		JMSSessionPooledObjectFactory jmsPooledFactory = new JMSSessionPooledObjectFactory(
				"1", null);
		SessionPool sessionPool = new SessionPool(jmsPooledFactory, getGenericObjectPoolConfig());
		sessionPoolRegistry.add("1", sessionPool);
		return sessionPoolRegistry;
	}
	
	public static GenericObjectPoolConfig getGenericObjectPoolConfig() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(10);//Sets the cap on the number of objects that can be allocated by the pool (checked out to clients, or idle awaiting checkout) at a given time
		config.setTestWhileIdle(true);//whether objects sitting idle in the pool will be validated by the idle object evictor
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		config.setTestOnCreate(false);
		config.setTimeBetweenEvictionRunsMillis(30000); //Sets the number of milliseconds to sleep between runs of the idle object evictor thread. When non-positive, no idle object evictor thread will be run
		config.setMinEvictableIdleTimeMillis(4000);//the minimum amount of time an object may sit idle in the pool before it is eligible for eviction by the idle object evictor 
		config.setMinIdle(1);//Sets the target for the minimum number of idle objects to maintain in the pool.
		config.setMaxIdle(2);//the cap on the number of "idle" instances in the pool.
		config.setBlockWhenExhausted(true);//Sets whether to block when the borrowObject() method is invoked when the pool is exhausted (the maximum number of "active" objects has been reached).
		config.setMaxWaitMillis(2000); //Sets the maximum amount of time (in milliseconds) the borrowObject() method should block before throwing an exception when the pool is exhausted and getBlockWhenExhausted() is true
		return config;
	}*/
}
