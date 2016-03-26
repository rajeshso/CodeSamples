package com.db.tw.distribution.config;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * This is generic Interface for the Configuration and contains some of the
 * common methods for accessing any configuration specific properties like
 * getting a type(for e.g SFTP,JMS etc.), block name, business keys,
 * configuration type specific keys and values.
 * 
 * @author Mukesh
 *
 */
public interface IBlock {

	/**
	 * This method will be used to load the specific configuration for a target
	 * system type. The specific implementation of different types need to
	 * provide there own implementation specific to the properties that are
	 * required by that type.
	 */
	public void loadConfig();

	/**
	 * This method will provide the type of configuration for e.g SFTP,JMS etc.
	 * 
	 * @return <code>String</code> The type of the configuration.
	 */
	public String getConfigType();

	/**
	 * This setter method to setting the configuration type e.g SFTP,JMS etc.
	 * 
	 * @param configType
	 *            <code>String</code> The configuration type property value.
	 */

	public void setConfigType(String configType);

	/**
	 * This method will set the block name value from the properties file.
	 * 
	 * @param blockNameValue
	 *            <code>String</code> The value for the block name property.
	 */
	public void setBlockNameValue(String blockNameValue);

	/**
	 * This method will provide the block name value for the configuration block
	 * 
	 * @return <code>String</code> The name of the configuration block.
	 */
	public String getBlockNameValue();

	/**
	 * This method will set the configuration properties file associated with
	 * the block.
	 * 
	 * @param configProp
	 *            <code>Properties</code> The properties file associated with
	 *            the block.
	 */
	public void setConfigProperty(Properties configProp);

	/**
	 * This method will returns the configuration properties file associated
	 * with the block.
	 * 
	 * @return <code>Properties</code> The properties file associated with the
	 *         block
	 */
	public Properties getConfigProperty();

	/**
	 * This method will return all the Business key values as a list for the key
	 * name defined in the configuration block.
	 *
	 * @return <code>List<String></code> The list of values for the specific
	 *         business key name.
	 */
	public List<String> getBusinessKeyValues(String bKeyName);

	/**
	 * This method will return all the Business keys as a Set of the key names
	 * defined in the configuration without the block name prefix.
	 *
	 * @return <code>Set<String></code> The set of string values of all the
	 *         Business key names.
	 */
	public Set<String> getBusinessKeySet();

	/**
	 * This method will check whether the business key is already contains in
	 * the business key map.
	 * 
	 *
	 * @return <code>boolean</code> true if the business key contains in the map
	 *         otherwise false.
	 */
	public boolean containsBusinessKey(String bKeyName);

	/**
	 * This method will return all the configuration type specific keys like
	 * server name, port etc or any other key, specific to that configuration
	 * type as a set of String, for the key name defined in the configuration.
	 *
	 * @return <code>Set<String></code> The set of string values of all the
	 *         Configuration specific key names.
	 */
	public Set<String> getTypePropKeySet();

	/**
	 * This method will return the value associated with the configuration
	 * specific key defined in the configuration.
	 *
	 * @return <code>String</code> The property value of the configuration type
	 *         key if the value exist or null.
	 */
	public String getTypePropKeyValue(String typeKeyName);

	/**
	 * This method will add the specific configuration type key name and value
	 * to property configuration map. The specific type configuration
	 * implementation classes has to call this method to add their configuration
	 * type specific property to the map.
	 */
	public void addToTypePropKeyMap(String typeKeyName, String value);

	/**
	 * This method will clear the type specific property configuration map.
	 */
	public void clearTypePropKeyMap();

}
