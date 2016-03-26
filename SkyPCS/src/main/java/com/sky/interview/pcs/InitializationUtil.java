package com.sky.interview.pcs;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class InitializationUtil {
	/**
	 * @param metadataAbsoluteFileName
	 * @throws TechnicalFailureException
	 */
	public static CompositeConfiguration getRepository(String metadataAbsoluteFileName)
			throws TechnicalFailureException {
		CompositeConfiguration dataRepository = new CompositeConfiguration();
		try {
			dataRepository.addConfiguration(new PropertiesConfiguration(metadataAbsoluteFileName));
		} catch (ConfigurationException e) {
			TechnicalFailureException tfe = new TechnicalFailureException(
					"Property configuration is incorrect." + metadataAbsoluteFileName);
			tfe.initCause(e);
			throw tfe;
		}
		return dataRepository;
	}
}
