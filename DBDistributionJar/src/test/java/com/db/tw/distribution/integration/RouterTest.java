package com.db.tw.distribution.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.TopicConnection;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.db.tw.distribution.Router;
import com.db.tw.distribution.config.BlockManager;
import com.db.tw.distribution.config.IBlock;
import com.db.tw.distribution.jms.JMSFactoryRegistry;
import com.db.tw.distribution.jms.SessionPool;
import com.db.tw.distribution.jms.SessionPoolRegistryService;
import com.db.tw.distribution.publisher.Publisher;
import com.db.tw.distribution.publisher.PublisherFactory;
import com.db.tw.distribution.publisher.PublisherRegistry;
import com.db.tw.distribution.utils.BlockUtils;
import com.db.tw.distribution.utils.JmsProvider;
import com.db.tw.distribution.utils.SpringJmsProvider;


public class RouterTest {
	private static String configFile = "src/main/resources/testConfig.properties";
	
	@BeforeClass
	public static void setUp() throws Exception {
		BlockUtils.createEnvValueCONFIG_FILE_LOC(configFile);
		Properties jmsProp = BlockUtils.getJMSBlock(1);
		System.out.println(jmsProp);
		BlockUtils.createPropertiesFile(jmsProp, configFile);
		BlockManager.init();
		PublisherRegistry.getInstance().init(BlockManager.getConfigList());
		Map<String, String> envProp = new HashMap<String, String>(1);
		envProp.put("amqBrokerURL", "tcp://localhost:61616");
		BlockUtils.setEnv(envProp);
		startService();
	} 
	private static void startService() throws Exception {
		// Data providers
		JmsProvider jmsTopicProvider = new SpringJmsProvider(
				"beans.xml", "jmsConnectionFactory",
				"notificationTopic");	
		try {
			initSessionPoolRegistry(jmsTopicProvider);
			Set<IBlock> iblockSet = BlockManager.getConfigList();
			PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
			publisherRegistry.init(iblockSet);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public final void testRouterPublisherRegistry() throws Exception {
		//TODO: Test one feature at a time. Multiply the test
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		Set<IBlock> iblockSet = BlockManager.getConfigList();
		IBlock iblock = iblockSet.iterator().next();
		PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry.getPublisherFactory(iblock);
		assertNotNull(publisherFactory);
	}

	@Test
	public final void testExecute() {
		try {
			Router router = new Router(PublisherRegistry.getInstance());
			router.execute("Hello Infosys", "JUR=ASIC/EMIR.ASSET=FX");
		} catch (Exception e) {
			fail("Exception not expected");
		}
	}
    

	private static void initSessionPoolRegistry(JmsProvider jmsProvider) throws JMSException, Exception {
		JMSFactoryRegistry jmsFactoryRegistry = JMSFactoryRegistry.getInstance();
		SessionPoolRegistryService sessionPoolRegistry = SessionPoolRegistryService.getInstance();
		List<String> serverIDList = jmsProvider.getAvailableServerIDs();
		for(int i=0; i<serverIDList.size();i++) {
			String serverID = serverIDList.get(i);
			TopicConnection topicConnection = jmsProvider.getTopicConnectionFactory(serverID).createTopicConnection();
			jmsFactoryRegistry.add(serverID, topicConnection);
			SessionPool sessionPool = new SessionPool(jmsFactoryRegistry.get(serverID), getGenericObjectPoolConfig());
			sessionPoolRegistry.add(serverID, sessionPool);
		}
	}
	@AfterClass
	public static void stopService() {
		try {
			SessionPoolRegistryService.getInstance().clear();
			JMSFactoryRegistry.getInstance().clear();
			BlockUtils.deletePropertiesFile(configFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//TODO: All the below values should come from the properties file
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
		config.setBlockWhenExhausted(false);//Sets whether to block when the borrowObject() method is invoked when the pool is exhausted (the maximum number of "active" objects has been reached).
		config.setMaxWaitMillis(2000); //Sets the maximum amount of time (in milliseconds) the borrowObject() method should block before throwing an exception when the pool is exhausted and getBlockWhenExhausted() is true
		return config;
	}
}
