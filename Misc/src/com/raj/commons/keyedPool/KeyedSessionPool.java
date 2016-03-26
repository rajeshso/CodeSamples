package com.raj.commons.keyedPool;

import javax.jms.TopicSession;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

//TODO: "idle object eviction" to be invoked
public class KeyedSessionPool extends
		GenericKeyedObjectPool<ServerDetails, TopicSession> {

	public KeyedSessionPool(
			JMSKeyedSessionPooledObjectFactory factory,
			GenericKeyedObjectPoolConfig config) {
		super(factory, config);
		// TODO Auto-generated constructor stub
	}

/*	@Override
	public void close() {
		//Map<String,List<DefaultPooledObjectInfo>> 
	}*/
}
