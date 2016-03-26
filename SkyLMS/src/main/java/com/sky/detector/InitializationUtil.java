package com.sky.detector;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitializationUtil { 
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InitializationUtil.class);
	private InitializationUtil() {}
	
	/**
	 * @param metadataAbsoluteFileName
	 * @throws InvalidInputException
	 */
	public static CompositeConfiguration getProperties(String metadataAbsoluteFileName) throws InvalidInputException {
		LOGGER.info("Hello");
		CompositeConfiguration lmsProperties = new CompositeConfiguration();
		try {
			lmsProperties.addConfiguration(new PropertiesConfiguration(metadataAbsoluteFileName));
		} catch (ConfigurationException e) {
			InvalidInputException tfe = new InvalidInputException(
					"Property configuration is incorrect." + metadataAbsoluteFileName);
			tfe.initCause(e);
			throw tfe;
		}
		if (lmsProperties == null || lmsProperties.isEmpty())
			throw new InvalidInputException("Property configuration is incorrect");
		return lmsProperties;
	}
}
