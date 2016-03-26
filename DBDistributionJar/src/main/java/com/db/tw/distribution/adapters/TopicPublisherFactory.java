package com.db.tw.distribution.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.tw.distribution.Router;
import com.db.tw.distribution.config.IBlock;
import com.db.tw.distribution.jms.SessionPoolRegistryService;
import com.db.tw.distribution.publisher.PublisherFactory;

public class TopicPublisherFactory extends PublisherFactory<JMSTopicPublisher> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TopicPublisherFactory.class);
	public TopicPublisherFactory() {
	}

	@Override
	public JMSTopicPublisher createPublisher(IBlock iblock) throws Exception {
		// Take the values of iBlock and create a FilePublisher
		// Test the FilePublisher's connection. If the connection fails, throw
		// an exception
		// Get the values of the factory ID and the destination from iblock
		// SessionPoolRegistryService is initialised in DistributionBolt
		String factoryValue = iblock.getConfigProperty().getProperty(
				iblock.getBlockNameValue() + "." + "SERVERID");
		String topicValue = iblock.getConfigProperty().getProperty(
				iblock.getBlockNameValue() + "." + "topicName");
		LOGGER.debug("TopicPublisherFactory with factoryValue {} and topicValue {}", factoryValue, topicValue);
		return new JMSTopicPublisher(SessionPoolRegistryService.getInstance()
				.get(factoryValue), topicValue);
	}

}
