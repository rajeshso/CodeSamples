package com.db.tw.distribution.config;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This is the utility class for the doing the common operations.
 * 
 * @author Mukesh
 *
 */
public class BlockUtils {

	private static String ENV_START_L = BlockConstants.ENV_VAR_START_IDENTIFIER;
	private static String ENV_END_L = BlockConstants.ENV_VAR_END_IDENTIFIER;

	/**
	 * This Method will return the full key name starting with the Block name
	 * the separator and the property name.
	 * 
	 * @param prefixName
	 *            The Prefix to be appended
	 * @param propName
	 *            The Property name
	 * @return
	 */
	public static String getFullKeyName(String prefixName, String propName) {
		StringBuilder result = new StringBuilder(prefixName);
		result.append(BlockConstants.BLOCKKEY_SEPERATOR);
		result.append(propName);
		return result.toString();
	}

	/**
	 * This method will return the full key name for the configType key for e.g
	 * if the Block name key has a value as "JMS_WL" then this method will
	 * return full key name as "JMS_WL.CONFIG_TYPE"
	 * 
	 * 
	 * @param prefixName
	 * @param propertyName
	 * @return
	 */
	public static String getConfigTypeFullKeyName(String prefixName) {
		return getFullKeyName(prefixName, BlockConstants.CONFIGTYPE_PROP_NAME);
	}

	/**
	 * This method will parse the multiple values given for a property,
	 * separated by a separator and returns the List of the property values. for
	 * e.g if the propertyName = 1,2,3, This method will return the List
	 * containing 1, 2,3
	 * 
	 *
	 * @return
	 */
	public static List<String> getKeyValuesList(String keyValue) {
		List<String> result = new ArrayList<String>();
		if (keyValue != null) {
			String[] bKeys = keyValue.split(BlockConstants.LISTKEY_SEPERATOR);
			for (String key : bKeys) {
				result.add(key.trim());
			}
		}
		return result;
	}

	public static ArrayList<String> getUniqueMergeValueLists(
			List<String> list1, List<String> list2) {
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(BlockUtils.getUniqueValuesList(list1));
		// Copy the Non duplicate element from list 2
		if (list2 != null) {
			for (String data : list2) {
				if (!result.contains(data)) {
					result.add(data);
				}
			}
		}
		return result;
	}

	public static ArrayList<String> getUniqueValuesList(List<String> list) {
		ArrayList<String> result = new ArrayList<String>();
		// Copy the Non duplicate element from list 1
		if (list != null) {
			for (String data : list) {
				if (!result.contains(data)) {
					result.add(data);
				}
			}
		}
		return result;
	}

	public static String getRelativeBKey(String keyName) {
		String result = keyName;
		if (result.contains(BlockConstants.BLOCKKEY_SEPERATOR)) {
			result = result.substring(result
					.lastIndexOf(BlockConstants.BLOCKKEY_SEPERATOR) + 1);
		}
		return result;
	}

	/**
	 * This method will load the value from the property file. If the value is
	 * an environment variable e.g ${ENV_VAR_NAME} then it will try to get the
	 * value replaced with the actual value from the environment variables,
	 * otherwise it will return the value of the property.
	 * 
	 * @param key
	 *            The key name to read from the property file.
	 * @param prop
	 *            The property file from where the property needs to be read
	 * @return <code> String </code> if the property is an environment variable
	 *         then its value if exist otherwise null. If the value is not a
	 *         environment variable then the value itself will be returned.
	 */

	public static String getEnvVarPropValue(String key, Properties prop) {
		String result = null;
		if (prop != null) {
			String value = prop.getProperty(key);
			if (value != null) {
				if (value.startsWith(ENV_START_L) && value.endsWith(ENV_END_L)) {
					String envVarName = value.substring(
							value.indexOf(ENV_START_L) + ENV_START_L.length(),
							value.lastIndexOf(ENV_END_L));
					String envVarValue = System.getenv(envVarName);
					result = envVarValue;
				} else {
					result = value;
				}
			} else {
				String message = MessageFormat.format(
						Messages.getString("CONFIG_TYPE_KEY_NOT_DEFINED"), key); //$NON-NLS-1$
				System.out.println(message);
			}
		}
		return result;
	}

	/**
	 * This method will return the environment variable value for the specific
	 * environment variable name.
	 * 
	 * @param envVarName
	 *            The environment variable whose value is requested
	 * @return The system environment variable value or null if the environment
	 *         variable name is null or the the system environment do not have
	 *         any variable defined with the name.
	 */
	public static String getEnvValue(String envVarName) {
		String result = null;
		if (envVarName != null) {
			result = System.getenv(envVarName);
		}
		return result;
	}

	// //TODO Need to see if the replacement of the variable can be done using
	// // regex
	// public String getR(String value) {
	// String result = value;
	// // match ${ENV_VAR_NAME}
	// if (value.startsWith("${") && value.endsWith("}")) {
	// String envVarName = value.substring(value.indexOf("${"),
	// value.lastIndexOf("}"));
	// String envVarValue = System.getenv(envVarName);
	// return envVarValue;
	// }
	// Pattern p = Pattern.compile("\\$\\{(\\w+)\\}");
	// Matcher m = p.matcher(value); // get a matcher object
	//
	// StringBuffer sb = new StringBuffer();
	// while (m.find()) {
	// String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
	// String envVarValue = System.getenv(envVarName);
	// m.appendReplacement(sb, null == envVarValue ? "" : envVarValue);
	// }
	// m.appendTail(sb);
	// return sb.toString();
	// }

}
