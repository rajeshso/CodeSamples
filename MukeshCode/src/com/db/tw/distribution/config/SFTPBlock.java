package com.db.tw.distribution.config;

import java.text.MessageFormat;
import java.util.Properties;

/**
 * This is a concrete implementation for loading the SFTP Configuration specific
 * properties from the Block. This class is responsible for loading the specific
 * properties related to SFTP type configuration.
 * 
 * @author Mukesh
 *
 */
public class SFTPBlock extends AbstractBlock {

	private final String HOST_ADDRESS_PROP_NAME = "hostaddress"; //$NON-NLS-1$
	private final String HOST_USERNAME_PROP_NAME = "hostusername"; //$NON-NLS-1$
	private final String HOST_PWD_PROP_NAME = "hostpassword"; //$NON-NLS-1$
	private final String HOST_PORT_PROP_NAME = "hostport"; //$NON-NLS-1$

	private final String[] propNames = { HOST_ADDRESS_PROP_NAME,
			HOST_USERNAME_PROP_NAME, HOST_PWD_PROP_NAME, HOST_PORT_PROP_NAME };

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
			String message = MessageFormat.format(Messages.getString("PROPERTY_FILE_NOT_SET"), getBlockNameValue()); //$NON-NLS-1$
			System.out.println(message);
			
		}

	}
}
