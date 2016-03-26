package com.db.tw.distribution.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.tw.distribution.adapters.TopicPublisherFactory;
import com.db.tw.distribution.config.IBlock;

/**
 * If you give me a IBlock, I will give you a Publisher
 * 
 * @author Rajesh
 *
 */
public abstract class PublisherFactory<T extends Publisher> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublisherFactory.class);

	public PublisherFactory() {
		LOGGER.debug("PublisherFactory commissioned");
	}

	public T getPublisher(IBlock iblock) throws Exception {
		if (iblock == null)
			throw new Exception("iblock is invalid");
		if (iblock.getBlockNameValue() == null
				|| iblock.getBlockNameValue().trim().isEmpty())
			throw new Exception("iblock is invalid");
		if (iblock.getConfigProperty() == null)
			throw new Exception("iblock's config property is missing");
		return createPublisher(iblock);
	}

	public abstract T createPublisher(IBlock iblock) throws Exception;

	public boolean testPublisher() throws Exception {
		return false;
	}

}
