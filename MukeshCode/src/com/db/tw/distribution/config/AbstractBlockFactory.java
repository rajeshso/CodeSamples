package com.db.tw.distribution.config;

import java.util.Properties;

/**
 * This is the abstract factory, which works as the template for creating the
 * block instances.
 * 
 * @author Mukesh
 *
 */
public abstract class AbstractBlockFactory {

	public abstract IBlock createConfig(String configType, String bNamePrefix,
			Properties prop);
}
