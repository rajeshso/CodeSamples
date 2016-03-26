package com.db.tw.distribution.config;

import java.util.Properties;

/**
 * This is factory class responsible for loading the concrete implementation
 * class using the Configuration type property defined in the blocks.This
 * factory dynamically loads the concrete block configuration class based on its
 * type.
 * 
 * This class expects that the concrete implementation class should be part of
 * the package "com.db.tw.distribution.config" and the Implementation class name
 * should follow the naming conventions as {Configuration Type
 * Value}"Block".java
 * 
 * for e.g If the configuration type value is JMS then the concrete
 * implementation class name would be JMSBlock.java
 * 
 * @author Mukesh
 *
 */
public class BlockFactory extends AbstractBlockFactory {

	private static final String PACKAGE_NAME = "com.db.tw.distribution.config";
	private static final String IMPL_CLASS_SUFFIX = "Block";

	@Override
	public IBlock createConfig(String configType, String bNamePrefix,
			Properties prop) {

		IBlock configObject = null;
		String configFactoryClassName = configType + IMPL_CLASS_SUFFIX;
		String absoluteClassPath = PACKAGE_NAME + "." + configFactoryClassName;
		BlockClassLoader factoryLoader = new BlockClassLoader();
		configObject = factoryLoader.invokeClassMethod(absoluteClassPath);
		if (configObject != null) {
			configObject.setConfigType(configType);
			configObject.setBlockNameValue(bNamePrefix);
			configObject.setConfigProperty(prop);
		}
		return configObject;
	}

}
