package com.db.tw.distribution.config;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is abstract class for the configuration blocks. This class is
 * responsible for loading the generic properties like business keys, system
 * type etc which are part of every block. The concrete implementation of
 * specific supported type should extend this and provide the specialisation
 * related to that configuration type.
 * 
 * @author Mukesh
 *
 */

public abstract class AbstractBlock implements IBlock {

	private String configType = null;

	// For holding the Property file reference
	private Properties configProperty;

	// For holding the block name property value
	private String blockNameValue;

	// Map to hold business key name as key and and object as a List of
	// values for the business key
	private Map<String, ArrayList<String>> bKeyValuesMap = new ConcurrentHashMap<String, ArrayList<String>>();

	// Map to hold publishing specific keys like server name, port etc.
	// specific to the defined configuration type
	private Map<String, String> typeKeyValueMap = new ConcurrentHashMap<String, String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.db.tw.distribution.config.IBlock#getConfigType()
	 */
	@Override
	public String getConfigType() {
		return configType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.db.tw.distribution.config.IBlock#setBlockNameValue(java.lang.String)
	 */
	@Override
	public void setBlockNameValue(String blockNameValue) {
		this.blockNameValue = blockNameValue;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.db.tw.distribution.config.IBlock#getBlockNameValue()
	 */
	@Override
	public String getBlockNameValue() {
		return blockNameValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.db.tw.distribution.config.IBlock#setConfigType(java.lang.String)
	 */
	public void setConfigType(String configType) {
		this.configType = configType;

	}

	/**
	 * The method should be implemented by the concrete implementation for
	 * supported configuration types . This method will get called after loading
	 * the generic properties for the block like block name, configuration type,
	 * business keys etc. to provide a chance to load the configuration type
	 * specific properties.
	 */
	public abstract void loadTypeConfig();

	/**
	 * This method will load the generic properties common to every block and
	 * delegate the call to the specific type implementation to load the
	 * specific properties required for that type.
	 */
	public final void loadConfig() {
		String bListPropName = BlockUtils.getFullKeyName(blockNameValue,
				BlockConstants.BKEYLIST_PROP_NAME);
		String value = configProperty.getProperty(bListPropName);
		if (value != null) {
			List<String> bKeyNames = BlockUtils.getKeyValuesList(value);
			for (String key : bKeyNames) {
				String bKeyName = BlockUtils
						.getFullKeyName(blockNameValue, key);
				String bKeyValue = configProperty.getProperty(bKeyName);
				if (bKeyValue != null) {
					List<String> valuesList = BlockUtils
							.getKeyValuesList(bKeyValue);
					if (!valuesList.isEmpty()) {
						if (bKeyValuesMap.containsKey(bKeyName)) {
							List<String> existingValues = bKeyValuesMap
									.get(bKeyName);
							bKeyValuesMap.put(bKeyName, BlockUtils
									.getUniqueMergeValueLists(existingValues,
											valuesList));
						} else {
							bKeyValuesMap.put(bKeyName,
									BlockUtils.getUniqueValuesList(valuesList));
						}
					}
				} else {
					String message = MessageFormat.format(
							Messages.getString("BKEY_VALUE_NOT_DEFINED"),
							bKeyName);
					System.out.println(message); //$NON-NLS-1$
				}
			}
			// Delegate to load the supported type specific property
			loadTypeConfig();
		} else {
			String message = MessageFormat.format(
					Messages.getString("BKEY_LIST_PROP_NOT_DEFINED"),
					getBlockNameValue());
			System.out.println(message); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.db.tw.distribution.config.IBlock#containsBusinessKey(java.lang.String
	 * )
	 */
	@Override
	public boolean containsBusinessKey(String bKeyName) {
		boolean result = false;
		if (bKeyValuesMap.containsKey(bKeyName)) {
			result = true;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.db.tw.distribution.config.IBlock#setConfigProperty(java.util.Properties
	 * )
	 */
	@Override
	public void setConfigProperty(Properties configProp) {
		this.configProperty = configProp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.db.tw.distribution.config.IBlock#getConfigProperty()
	 */
	@Override
	public Properties getConfigProperty() {
		return configProperty;
	}

	/**
	 * This method will return the value list associated with passed business
	 * key from the block business key properties map.
	 * 
	 * @return <code> List<String> </code> The list of values for the Business
	 *         key if it exist in the map otherwise null.
	 * 
	 */
	public List<String> getBusinessKeyValues(String bKeyName) {
		List<String> result = new ArrayList<String>();
		result = bKeyValuesMap.get(bKeyName);
		return Collections.unmodifiableList(result);
	}

	/**
	 * This method will return the key set associated with business keys for the
	 * configuration block.
	 * 
	 * @return <code> Set<String> </code> The business key name properties set
	 *         for the block.
	 */
	public Set<String> getBusinessKeySet() {
		Set<String> keySet = bKeyValuesMap.keySet();
		return Collections.unmodifiableSet(keySet);
	}

	public Map<String, ArrayList<String>> getBusinessKeyValueMap() {
		return bKeyValuesMap;
	}

	/**
	 * This method will return the value associated with passed key from the
	 * type specific configuration properties map.
	 * 
	 * @return <code> String </code> The value associated with the property from
	 *         the type specific properties map if the properties exist in the
	 *         map, else null.
	 */
	public String getTypePropKeyValue(String typeKeyName) {
		String result = typeKeyValueMap.get(typeKeyName);
		return result;
	}

	/**
	 * This method will return the key set associated with the all the
	 * configuration type specific keys for the configuration block.
	 * 
	 * @return <code> Set<String> </code> The type specific properties key set
	 *         for the block.
	 */

	public Set<String> getTypePropKeySet() {
		Set<String> typeKeySet = typeKeyValueMap.keySet();
		return Collections.unmodifiableSet(typeKeySet);
	}

	/**
	 * This method will add the configuration type specific property with its
	 * value to the internal block configuration properties map. If the property
	 * already exist this method will override with the new value.
	 *
	 * @param typeKeyName
	 *            <code> String</code> The key name.
	 * @param value
	 *            <code> String</code> The value for the key.
	 */
	public void addToTypePropKeyMap(String typeKeyName, String value) {
		if (typeKeyName != null && value != null) {
			typeKeyValueMap.put(typeKeyName, value);
		}
	}

	/**
	 * This method will clear the configuration type specific properties map.
	 */
	public void clearTypePropKeyMap() {
		typeKeyValueMap.clear();
	}

	@Override
	public String toString() {
		return blockNameValue;
		// return super.toString();
	}

}
