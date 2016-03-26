package com.db.tw.distribution.publisher;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.tw.distribution.adapters.TopicPublisherFactory;
import com.db.tw.distribution.config.BlockManager;
import com.db.tw.distribution.config.IBlock;

/**
 * I maintain the registry of IBlockID vs
 * PublisherFactory Give me a set of IBlocks, I will create the Map. You can
 * then give an IBlock name and I can return you the equivalent PublisherFactory
 * Class
 * 
 * @author Rajesh
 *
 */
public class PublisherRegistry implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PublisherRegistry.class);

	private volatile static PublisherRegistry _instance;
	public static PublisherRegistry getInstance() {
		if (_instance == null) {
			synchronized (PublisherRegistry.class) {
				if (_instance == null) {
					_instance = new PublisherRegistry();
				}
			}
		}
		return _instance;
	}

	// Storm can serialise the following object
	private Map<String, Class<? extends PublisherFactory<? extends Publisher>>> publisherFactoryPoolMap = null;

	private PublisherRegistry() {
		publisherFactoryPoolMap = Collections
				.synchronizedMap(new HashMap<String, Class<? extends PublisherFactory<? extends Publisher>>>());
	}
	//TODO: Generic wildcard types should not be used in return parameters. Remove usage of generic wildcard type.
	public synchronized PublisherFactory<? extends Publisher> getPublisherFactory(
			IBlock iBlock) throws Exception {
		String iBlockName = iBlock.getBlockNameValue();
		if (publisherFactoryPoolMap.containsKey(iBlockName)) {
			PublisherFactory<? extends Publisher> publisherFactory = publisherFactoryPoolMap
					.get(iBlockName).newInstance();
			System.out.println("publisherFactory is " + publisherFactory);
			return publisherFactory;
		}
		return null;
	}

	public void init(Set<IBlock> iblockSet) throws Exception {
		// Iterate through iblockset
		// Get the factory
		// Store the iBlock id as the key and the Factory Class as the value
		String factoryStringConstant = "factory";
		if (iblockSet == null || iblockSet.isEmpty()) {
			iblockSet = BlockManager.getConfigList();
			int size = iblockSet.size();
			System.out.println("Size of set is " + size);
		}
		Iterator<IBlock> iblockSetIterator = iblockSet.iterator();
		while (iblockSetIterator.hasNext()) {
			IBlock iblock = iblockSetIterator.next();
			String key = iblock.getBlockNameValue() + "."
					+ factoryStringConstant;
			if (!iblock.getConfigProperty().containsKey(key)) {
				throw new Exception("Factory value not set for "
						+ iblock.getBlockNameValue());
			}
			String factoryStringValue = iblock.getConfigProperty().getProperty(
					key);
			try {
				Class<? extends PublisherFactory<? extends Publisher>> factoryClass = (Class<? extends PublisherFactory<? extends Publisher>>) Class
						.forName(factoryStringValue);
				publisherFactoryPoolMap.put(iblock.getBlockNameValue(),
						factoryClass);
			} catch (ClassNotFoundException e) {
				Exception e1 = new Exception("Factory " + factoryStringValue
						+ " not in class path for  "
						+ iblock.getBlockNameValue());
				e1.initCause(e);
				throw e1;
			}
		}
	}
}
