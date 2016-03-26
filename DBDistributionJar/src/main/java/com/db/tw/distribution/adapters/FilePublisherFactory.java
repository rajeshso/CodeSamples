package com.db.tw.distribution.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.tw.distribution.Router;
import com.db.tw.distribution.config.IBlock;
import com.db.tw.distribution.publisher.PublisherFactory;

public class FilePublisherFactory extends PublisherFactory<FilePublisher> {
	private static final Logger LOGGER = LoggerFactory.getLogger(FilePublisherFactory.class);
	public FilePublisherFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FilePublisher createPublisher(IBlock iblock) {
		// Take the values of iBlock and create a FilePublisher
		// Test the FilePublisher's connection. If the connection fails, throw
		// an exception
		String filePathValue = iblock.getConfigProperty().getProperty(
				iblock.getBlockNameValue() + "." + "filepath");
		LOGGER.debug("creating file publisher for {}",filePathValue);
		return new FilePublisher(filePathValue);
	}

}
