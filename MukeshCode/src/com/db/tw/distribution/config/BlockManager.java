package com.db.tw.distribution.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This Configuration manager class contains the utility method
 * <ol>
 * configuration loading, getting configurations matching the Business keys, etc
 * </ol>
 * 
 * @author Mukesh
 *
 */
public class BlockManager {

	// List of all Configuration types
	private static Set<IBlock> configList = new HashSet<IBlock>();

	private static Map<String, Map<String, Set<IBlock>>> bKeyToConfigMap = new HashMap<String, Map<String, Set<IBlock>>>();

	private static transient boolean isConfigLoaded = false;

	/**
	 * 
	 */
	public static synchronized void init() {
		loadConfig();
		isConfigLoaded = true;
	}

	/**
	 * This method is responsible for loading the configuration properties
	 * specified by the CONFIG_FILE_LOC system environment variable.
	 */
	private static void loadConfig() {

		String configFilePath = BlockUtils.getEnvValue("CONFIG_FILE_LOC");
		if (configFilePath != null) {
			Properties prop = new Properties();
			InputStream input = null;
			File configFile = new File(configFilePath);
			if (configFile.exists() && configFile.isFile()) {
				System.out.println("Loading config file:"
						+ configFile.getAbsolutePath());
				try {
					input = new FileInputStream(configFile);
					prop.load(input);
					boolean isMoreBlock = true;
					int blockCount = 1;
					while (isMoreBlock) {
						String blockKey = BlockConstants.BLOCK_NAME_PREFIX
								+ blockCount;
						String blockName = prop.getProperty(blockKey);
						if (blockName == null) {
							// ERROR No more block is defined
							isMoreBlock = false;
						} else {
							String configType = prop.getProperty(BlockUtils
									.getConfigTypeFullKeyName(blockName));

							if (configType != null) {

								AbstractBlockFactory factory = new BlockFactory();
								if (factory != null) {
									IBlock conf = factory.createConfig(
											configType, blockName, prop);
									conf.loadConfig();
									configList.add(conf);
									populateBKeyMap(conf);
								}
							} else {
								System.out
										.println("Block with name {0} do not specify the  System Type"
												+ blockName);
							}
							blockCount++;
							System.out.println("Block found with Key "
									+ blockName);
						}
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					if (input != null) {
						try {
							input.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			} else {
				System.out
						.println("Unable to Load the Configuration Enviroment Variable with name {0} not found");
			}
		}

	}

	private static void populateBKeyMap(IBlock config) {
		Set<String> keySet = config.getBusinessKeySet();
		for (String key : keySet) {
			// If the business key is present in the root level map
			String relativeKey = BlockUtils.getRelativeBKey(key);
			if (bKeyToConfigMap.containsKey(relativeKey)) {
				Map<String, Set<IBlock>> internalMap = bKeyToConfigMap
						.get(relativeKey);
				List<String> keyValues = config.getBusinessKeyValues(key);
				for (String keyValue : keyValues) {
					// If the Internal Map contains the key
					if (internalMap.containsKey(keyValue)) {
						Set<IBlock> configSet = internalMap.get(keyValue);
						// If the set does not contain this configuration then
						// only update the map
						if (configSet.add(config)) {
							internalMap.put(keyValue, configSet);
						}
					} else {
						Set<IBlock> configSet = new HashSet<IBlock>();
						configSet.add(config);
						internalMap.put(keyValue, configSet);
					}
				}
			} else {
				// When the Business Key is not present in the root level map
				Map<String, Set<IBlock>> internalMap = new HashMap<String, Set<IBlock>>();
				List<String> keyValues = config.getBusinessKeyValues(key);
				for (String keyValue : keyValues) {
					Set<IBlock> configSet = new HashSet<IBlock>();
					configSet.add(config);
					internalMap.put(keyValue, configSet);
				}
				bKeyToConfigMap.put(relativeKey, internalMap);
			}
		}

	}

	// TODO Caching logic needs to be introduced to further reduce the time for
	// finding the matching configurations based on the business key
	public static Set<IBlock> getMatchingConfigsForBKeys(
			Map<String, List<String>> businessKeyMap) {

		if (!IsConfigLoaded()) {
			init();
		}
		Set<IBlock> matchingConfig = new HashSet<IBlock>();
		Set<String> keyNames = businessKeyMap.keySet();

		// Lets iterate through the Business keys come as part of the data and
		// try to find the matching configuration
		for (String key : keyNames) {
			List<String> valuesToMatch = businessKeyMap.get(key);
			if (bKeyToConfigMap.containsKey(key)) {
				Map<String, Set<IBlock>> valuesToConfigMap = bKeyToConfigMap
						.get(key);
				// Get the unionised List of all the configuration types
				// matching for the key
				Set<IBlock> configSet = getMatchingConfigForValue(
						valuesToMatch, valuesToConfigMap);
				// For the Fist key add all the configurations
				if (matchingConfig.isEmpty()) {
					matchingConfig.addAll(configSet);
				} else {
					// Only retain those configuration which are common for both
					// keys
					matchingConfig.retainAll(configSet);
				}
			} else {
				// The passed Business key has not been defined in anywhere in
				// the configuration file
				System.out
						.println("The Passed Business key {0} is not used in any of the defined blocks in configuration");
				break;
			}
		}

		return matchingConfig;
	}

	private static boolean IsConfigLoaded() {
		return isConfigLoaded;
	}

	private static Set<IBlock> getMatchingConfigForValue(
			List<String> valuesToMatch,
			Map<String, Set<IBlock>> valuesToConfigMap) {

		Set<IBlock> configSet = new HashSet<IBlock>();
		for (String value : valuesToMatch) {
			if (valuesToConfigMap.containsKey(value)) {
				configSet.addAll(valuesToConfigMap.get(value));
			} else {
				System.out
						.println("The value {0} is no where used for the Business key {1}");
			}
		}

		return configSet;
	}

	public static void printConfigMap() {
		System.out.println("Config Data:");
		for (IBlock config : configList) {
			System.out.println("Config Type :" + config.getConfigType());
			Set<String> bKeys = config.getBusinessKeySet();
			System.out.println("Business Keys:");
			for (String key : bKeys) {
				System.out.println("\t key name:" + key + " values:"
						+ config.getBusinessKeyValues(key));
			}

			// Print the Configuration type specific keys
			Set<String> typePropKeySet = config.getTypePropKeySet();
			if (!typePropKeySet.isEmpty()) {
				System.out.println("Config Keys:");
				for (String key : typePropKeySet) {
					System.out.println("\t key name:" + key + " values:"
							+ config.getTypePropKeyValue(key));
				}
			}
		}
	}

	public static void printBKeytoConfigMap() {
		System.out.println("Bkey to Config Map:");
		Set<String> rootKeys = bKeyToConfigMap.keySet();
		for (String rootKey : rootKeys) {
			System.out.println("Business Key : " + rootKey);
			Map<String, Set<IBlock>> internalMap = bKeyToConfigMap.get(rootKey);
			Set<String> internalKeys = internalMap.keySet();
			for (String internalKey : internalKeys) {
				System.out.println("\t First Level Values : " + internalKey);
				Set<IBlock> valueSet = internalMap.get(internalKey);
				System.out.println("\t\t Config Types : " + valueSet);
			}
		}
	}
	
	public static Set<IBlock> getConfigList() {
		return (Set) ((HashSet) configList).clone();
	}
	
	public static Set<String> getConfigTypes() {
		Set<String> availableConfigTypes = new HashSet<String>();
		Iterator<IBlock> configListIterator = configList.iterator();
		while(configListIterator.hasNext()) {
			IBlock iBlock = configListIterator.next();
			availableConfigTypes.add(iBlock.getConfigType());
		}
		return availableConfigTypes;
	}

	public static Set<IBlock> getIBlocks(String configType) {
		Set<IBlock> availableIBlockForConfigType = new HashSet<IBlock>();
		Iterator<IBlock> configListIterator = configList.iterator();
		while(configListIterator.hasNext()) {
			IBlock iBlock = configListIterator.next();
			if (iBlock.getConfigType().equals(configType)) {
				availableIBlockForConfigType.add(iBlock);
				System.out.println("ABCDEF = " + iBlock.getBlockNameValue());
			}
		}
		return availableIBlockForConfigType;
	}

}
