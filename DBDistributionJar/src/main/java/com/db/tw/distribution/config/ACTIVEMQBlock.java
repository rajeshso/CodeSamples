package com.db.tw.distribution.config;

import java.text.MessageFormat;
import java.util.Properties;

/**
 * This is a concrete implementation for loading the ActiveMQ Configuration
 * specific properties from the Block. This class is responsible for loading the
 * specific properties related to ActiveMQ type configuration.
 * 
 * @author Mukesh
 *
 */
public class ACTIVEMQBlock extends AbstractBlock {

	// Define the property names to read from the block for this type of
	// configuration
	final private String[] propNames = {};

	@Override
	public void loadTypeConfig() {
		Properties prop = getConfigProperty();
		clearTypePropKeyMap();
		if (prop != null) {
			String prefixName = getBlockNameValue();
			for (String propName : propNames) {
				String propValue = BlockUtils.getEnvVarPropValue(
						BlockUtils.getFullKeyName(prefixName, propName), prop);
				addToTypePropKeyMap(propName, propValue);
			}
		} else {
			// Error Condition No property associated with the config to load
			String message = MessageFormat
					.format(Messages.getString("PROPERTY_FILE_NOT_SET"), getBlockNameValue()); //$NON-NLS-1$
			System.out.println(message);
		}

	}

}
