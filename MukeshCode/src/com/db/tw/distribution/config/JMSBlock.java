package com.db.tw.distribution.config;

import java.text.MessageFormat;
import java.util.Properties;

/**
 * This is a concrete implementation for loading the JMS Configuration specific
 * properties from the Block. This class is responsible for loading the specific
 * properties related to JMS type configuration.
 * 
 * @author Mukesh
 *
 */
public class JMSBlock extends AbstractBlock {
	
	//properties added based on input for the JMS config Type
	final private String BROKERURL_PROP_NAME = "brokerURL";
	final private String TOPICNAME_PROP_NAME = "topicName";
	final private String RETRYCOUNTER_PROP_NAME = "retryCounter";
	final private String MAXIDLE_PROP_NAME = "maxIdlePerKey";
	final private String MINIDLE_PROP_NAME = "minIdlePerKey";

	final private String[] propNames = { BROKERURL_PROP_NAME,
			TOPICNAME_PROP_NAME, RETRYCOUNTER_PROP_NAME, MAXIDLE_PROP_NAME,
			MINIDLE_PROP_NAME };

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
