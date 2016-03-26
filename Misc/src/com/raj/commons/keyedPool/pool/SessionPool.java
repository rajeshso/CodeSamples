package com.raj.commons.keyedPool.pool;

import javax.jms.Session;
import javax.jms.TopicSession;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class SessionPool extends GenericObjectPool<TopicSession> {

	public SessionPool(PooledObjectFactory<TopicSession> factory,
			GenericObjectPoolConfig config, AbandonedConfig config1) {
		super(factory, config, config1);
		// TODO Auto-generated constructor stub
	}

}
