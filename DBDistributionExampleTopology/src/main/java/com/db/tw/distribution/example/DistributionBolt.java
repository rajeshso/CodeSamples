package com.db.tw.distribution.example;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import com.db.tw.distribution.Router;
import com.db.tw.distribution.config.BlockManager;
import com.db.tw.distribution.config.IBlock;
import com.db.tw.distribution.jms.JMSFactoryRegistry;
import com.db.tw.distribution.jms.SessionPool;
import com.db.tw.distribution.jms.SessionPoolRegistryService;
import com.db.tw.distribution.providers.JmsProvider;
import com.db.tw.distribution.publisher.PublisherRegistry;

public class DistributionBolt extends BaseRichBolt {

	private static final long serialVersionUID = 7470911325502148367L;
	private JmsProvider jmsProvider;
	private SessionPoolRegistryService sessionPoolRegistry;
	private PublisherRegistry publisherRegistry;


	public DistributionBolt() {
		System.out.println("DistributionBolt constructor called");
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		BlockManager.init();
		try {
			initSessionPoolRegistry(jmsProvider);
			Set<IBlock> iblockSet = BlockManager.getConfigList();
			publisherRegistry = PublisherRegistry.getInstance();
			publisherRegistry.init(iblockSet);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void execute(Tuple input) {
		System.out.println("DistributionBolt execute called");
		String payLoad = input.getString(0);
		String businessKeys = input.getString(1);
		System.out.println("Payload is " + payLoad + " and its business Keys are "+ businessKeys);
		try {
			Router router = new Router(publisherRegistry);
			router.execute(payLoad, businessKeys);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Published "+ payLoad);
	}

	@Override
	public void cleanup() {
		try {
			if (sessionPoolRegistry!=null)
				sessionPoolRegistry.clear();
			JMSFactoryRegistry.getInstance().clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		System.out.println("DistributionBolt declareOutputFields called");
	}

	public void setJmsProvider(JmsProvider jmsProvider) {
		this.jmsProvider = jmsProvider;
	}

	public SessionPoolRegistryService getSessionPoolRegistry() throws JMSException, Exception {
		if (sessionPoolRegistry==null) initSessionPoolRegistry(jmsProvider);
		return sessionPoolRegistry;
	}
	
	public void initSessionPoolRegistry(JmsProvider jmsProvider) throws JMSException, Exception {
		if (this.sessionPoolRegistry!=null) return;
		JMSFactoryRegistry jmsFactoryRegistry = JMSFactoryRegistry.getInstance();
		this.sessionPoolRegistry = SessionPoolRegistryService.getInstance();
		List<String> serverIDList = jmsProvider.getAvailableServerIDs();
		for(int i=0; i<serverIDList.size();i++) {
			String serverID = serverIDList.get(i);
			jmsFactoryRegistry.add(serverID, jmsProvider.getTopicConnectionFactory(serverID).createTopicConnection());
			SessionPool sessionPool = new SessionPool(jmsFactoryRegistry.get(serverID), getGenericObjectPoolConfig());
			sessionPoolRegistry.add(serverID, sessionPool);
		}
	}
	
	//TODO: All the below values should come from the properties file
	public GenericObjectPoolConfig getGenericObjectPoolConfig() {
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
	}	
}
