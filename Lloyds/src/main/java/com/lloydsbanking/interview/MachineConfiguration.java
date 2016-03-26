/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lloydsbanking.interview.engine.EngineException;

public class MachineConfiguration {
	private static final String STR_IS_A_MANDATORY_PROPERTY = " is a mandatory property";
	private static final Logger LOGGER = LoggerFactory.getLogger(MachineConfiguration.class);
	private static final String MACHINESKEY = "machines";
	private static final String ENGINESKEY = "engines";
	private static final String MACHINEKEY = "machine";
	private static final String ENGINEKEY = "engine";
	private static final String TYPEKEY = "type";
	private static final String MAXFUELLEVELKEY = "maxfuellevel";
	private static final String SUPPORTEDFUELTYPESKEY = "supportedfueltypes";
	private static final String COSTKEY = "cost";
	private static final String FUELTYPEKEY = "fueltype";
	private static final String BATCHSIZEKEY = "batchsize";
	private final Map<String, WidgetMachine> widgetMachinePool;

	public MachineConfiguration(String propertyFileName) throws WidgetMachineException {
		if (LOGGER.isInfoEnabled())
			LOGGER.info("MachineConfiguration for " + propertyFileName);
		try {
			CompositeConfiguration config = new CompositeConfiguration();
			config.addConfiguration(new PropertiesConfiguration(propertyFileName));
			widgetMachinePool = getWidgetMachines(config);
			if (LOGGER.isDebugEnabled())
				LOGGER.debug(widgetMachinePool.toString());
		} catch (WidgetMachineException e) {
			LOGGER.error("Unable to create a pool of widget machines. The configuration parameters are incorrect.",
					e.getMessage());
			throw e;
		} catch (NoSuchElementException | ConfigurationException ce) {
			LOGGER.error("Unable to create a pool of widget machines. The configuration parameters are incorrect.",
					ce.getMessage());
			WidgetMachineException wme = new WidgetMachineException(ce);
			throw wme;
		}
	}

	/**
	 * @param config
	 * @param widgetMachinePool
	 * @throws NoSuchElementException
	 * @throws IllegalArgumentException
	 * @throws WidgetMachineException
	 */
	private Map<String, WidgetMachine> getWidgetMachines(CompositeConfiguration config) throws WidgetMachineException {
		Map<String, WidgetMachine> widgetMachinePool = new HashMap<>();
		int noOfMachines;
		List<String> machineNames = null;
		try {
			noOfMachines = config.getInt(MACHINESKEY);
			this.validateNull(noOfMachines, MACHINESKEY);
			machineNames = getMachineNames(config, noOfMachines);
			LOGGER.debug(machineNames.toString());
			for (String machineName : machineNames) {
				WidgetMachine wm;
				wm = getWidgetMachine(config, machineName);
				widgetMachinePool.put(machineName, wm);
			}
		} catch (ConversionException | NoSuchElementException | IllegalArgumentException e) {
			WidgetMachineException wme = new WidgetMachineException("Unable to create a WidgeMachine." + e.getMessage(),
					e);
			throw wme;
		}
		return widgetMachinePool;
	}

	/**
	 * @param config
	 * @param machineName
	 * @return
	 * @throws WidgetMachineException
	 */
	private WidgetMachine getWidgetMachine(CompositeConfiguration config, String machineName)
			throws NoSuchElementException, WidgetMachineException {
		final int noOfEngines = config.getInt(machineName + "." + ENGINESKEY);
		this.validateNull(noOfEngines, machineName + "." + ENGINESKEY);
		final Map<String, Engine> enginePool = new HashMap<>();
		for (int engineIndex = 1; engineIndex <= noOfEngines; engineIndex++) {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("engineIndex is " + engineIndex);
			Engine engine = this.getEngine(config, machineName, engineIndex);
			enginePool.put(engine.getID(), engine);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Component Pool size is " + enginePool.size());
			LOGGER.debug("Component Pool ket set is " + enginePool.keySet());
		}
		return new WidgetMachine(machineName, enginePool);
	}

	/**
	 * @param config
	 * @param noOfMachines
	 * @return
	 */
	private List<String> getMachineNames(CompositeConfiguration config, int noOfMachines)
			throws IllegalArgumentException, NoSuchElementException, WidgetMachineException {
		List<String> machineNames = new ArrayList<>(noOfMachines);
		Iterator<String> machineNameKeyIterator = config.getKeys(MACHINEKEY);
		while (machineNameKeyIterator.hasNext()) {
			String mcNameKey = machineNameKeyIterator.next();
			String mcNameValue = config.getString(mcNameKey);
			this.validateNull(mcNameValue, mcNameKey);
			machineNames.add(mcNameValue);
		}
		return machineNames;
	}

	/**
	 * @param config
	 * @param machineName
	 * @param enginePool
	 * @param engineIndex
	 */
	private Engine getEngine(CompositeConfiguration config, String machineName, int engineIndex)
			throws ConversionException, IllegalArgumentException, NoSuchElementException, WidgetMachineException {
		final Set<FuelType> supportedFuelTypes = new HashSet<>();
		final String enginePrefixKey = machineName + "." + ENGINEKEY + "." + engineIndex;
		final String engineClassKey = enginePrefixKey;
		final String costPrefixKey = machineName + "." + ENGINEKEY + "." + engineIndex + "." + COSTKEY + "."
				+ FUELTYPEKEY + ".";
		final String typeValue = config.getString(enginePrefixKey + "." + TYPEKEY);
		validateNull(typeValue, enginePrefixKey + "." + TYPEKEY);
		final String engineClassValue = config.getString(engineClassKey);
		validateNull(engineClassValue, engineClassKey);
		final int maxFuelLevelValue = config.getInt(enginePrefixKey + "." + MAXFUELLEVELKEY);
		validateNull(maxFuelLevelValue, enginePrefixKey + "." + MAXFUELLEVELKEY);
		final List<Object> supportedfueltypesListValue = config.getList(enginePrefixKey + "." + SUPPORTEDFUELTYPESKEY);
		validateNull(supportedfueltypesListValue, enginePrefixKey + "." + SUPPORTEDFUELTYPESKEY);
		final int batchsizeValue = config.getInt(enginePrefixKey + "." + BATCHSIZEKEY);
		validateNull(batchsizeValue, enginePrefixKey + "." + BATCHSIZEKEY);
		Map<FuelType, Double> costSheet = new HashMap<>(supportedfueltypesListValue.size());
		try {
			for (int i = 0; i < supportedfueltypesListValue.size(); i++) {
				String fuelValue = (String) supportedfueltypesListValue.get(i);
				String fuelKey = costPrefixKey + fuelValue;
				Double costValue = config.getDouble(fuelKey);
				validateNull(costValue, fuelKey);
				if (LOGGER.isDebugEnabled())
					LOGGER.debug(fuelValue + " = " + costValue);
				FuelType fuelTypeEnum = FuelType.valueOf(fuelValue);
				supportedFuelTypes.add(fuelTypeEnum);
				costSheet.put(fuelTypeEnum, costValue);
			}
		} catch (IllegalArgumentException ie) {
			WidgetMachineException wme = new WidgetMachineException("Fuel Types in the config is not supported.", ie);
			throw wme;
		}
		Engine result = null;
		try {
			final Class engineClass = Class.forName(engineClassValue);
			result = (Engine) engineClass.newInstance();
			result.init(machineName + typeValue, supportedFuelTypes, maxFuelLevelValue, batchsizeValue, costSheet);
		} catch (ClassNotFoundException e) {
			WidgetMachineException wme = new WidgetMachineException("Engine configured is not in the classpath.", e);
			throw wme;
		} catch (ClassCastException e) {
			WidgetMachineException wme = new WidgetMachineException("Engine Class configured is not an Engine.", e);
			throw wme;
		} catch (InstantiationException e) {
			WidgetMachineException wme = new WidgetMachineException("Engine Class configured is not instatiable.", e);
			throw wme;
		} catch (IllegalAccessException e) {
			WidgetMachineException wme = new WidgetMachineException("Engine Class configured is Illegaly Accessed.", e);
			throw wme;
		} catch (EngineException e) {
			WidgetMachineException wme = new WidgetMachineException(e.getMessage(), e);
			throw wme;
		}
		return result;
	}

	public Map<String, WidgetMachine> getWidgetMachinePool() {
		return widgetMachinePool;
	}

	private void validateNull(String item, String itemName) throws WidgetMachineException {
		if (item == null || item.trim().isEmpty()) {
			WidgetMachineException wme = new WidgetMachineException(itemName + STR_IS_A_MANDATORY_PROPERTY);
			throw wme;
		}
	}

	private void validateNull(int item, String itemName) throws WidgetMachineException {
		if (item == 0) {
			WidgetMachineException wme = new WidgetMachineException(itemName + STR_IS_A_MANDATORY_PROPERTY);
			throw wme;
		}
	}

	private void validateNull(List<Object> item, String itemName) throws WidgetMachineException {
		if (item == null || item.isEmpty()) {
			WidgetMachineException wme = new WidgetMachineException(itemName + STR_IS_A_MANDATORY_PROPERTY);
			throw wme;
		}
	}

	private void validateNull(Double item, String itemName) throws WidgetMachineException {
		if (item == 0) {
			WidgetMachineException wme = new WidgetMachineException(itemName + STR_IS_A_MANDATORY_PROPERTY);
			throw wme;
		}
	}
}