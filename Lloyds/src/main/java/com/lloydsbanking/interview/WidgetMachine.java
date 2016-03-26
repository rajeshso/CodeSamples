/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lloydsbanking.interview.engine.EngineException;
import com.lloydsbanking.interview.engine.EngineState;

/**
 * @author Rajesh
 *
 */
public class WidgetMachine {
	private static final Logger LOGGER = LoggerFactory.getLogger(WidgetMachine.class);
	private final Map<String, Engine> availableEngines;
	private final Set<String> availablEngineIds;

	private final String machineId;
	private Engine currentEngine;
	private FuelType currentFuelType;
	private Double curUnitBatchCost;

	/**
	 * 
	 */
	public WidgetMachine(final String id, final Map<String, Engine> availableEngines) {
		this.machineId = id;
		this.availableEngines = availableEngines;
		this.availablEngineIds = availableEngines.keySet();
	}

	/**
	 * Initialise the Engine before production.
	 * 
	 * The initialisation fills the given fuel tank of the given engine.
	 * 
	 * @param engineId
	 *            - The engine ID
	 * @param fuelId
	 *            - The Fuel ID
	 * @param fuelQuantity
	 *            - The Fuel Quantity
	 * 
	 * @throws WidgetMachineException
	 *             if the supplied input is incorrect
	 */
	public void init(final String engineId, final String fuelId, final String fuelQuantity)
			throws WidgetMachineException {
		if (engineId == null || fuelId == null || fuelQuantity == null)
			throw new WidgetMachineException("Invalid input to initialize the WidgetMachine");
		FuelType fuelType = null;
		try {
			fuelType = FuelType.valueOf(fuelId);
		} catch (IllegalArgumentException e) {
			throw new WidgetMachineException("Invalid fuel to initialize the WidgetMachine : ", e);
		}
		int fuelQtyIntValue = 0;
		try {
			fuelQtyIntValue = Integer.parseInt(fuelQuantity);
		} catch (NumberFormatException nfe) {
			throw new WidgetMachineException("Invalid fuel quantity : ", nfe);
		}
		Engine engine = availableEngines.get(engineId);

		if (engine == null)
			throw new WidgetMachineException("Invalid engineID to initialize the WidgetMachine");
		if (!availablEngineIds.contains(engine.getID())) {
			throw new WidgetMachineException("Invalid engine " + engine.getID() + " supplied.");
		}
		if (!availableEngines.containsValue(engine)) {
			throw new WidgetMachineException(
					"Invalid engine " + engine.getID() + " supplied. The machine does not own this engine.");
		}
		if (!engine.isSupportedFuelType(fuelType)) {
			throw new WidgetMachineException(
					"Invalid fuel type " + fuelType.name() + " supplied for engine " + engine.getID() + ".");
		}
		this.currentEngine = engine;
		this.currentFuelType = fuelType;
		try {
			this.curUnitBatchCost = this.currentEngine.getCost(currentFuelType);
		} catch (EngineException ee) {
			throw new WidgetMachineException(ee);
		}
		try {
			this.currentEngine.fill(fuelType, fuelQtyIntValue);
		} catch (EngineException e) {
			throw new WidgetMachineException(e);
		}
	}
	/**
	 * Produces the widgets and Calculates the Cost 
	 * 
	 * @param quantity The requested quantity
	 * @return cost of production
	 * @throws WidgetMachineException
	 */
	public int produceWidgets(final int quantity) throws WidgetMachineException {
		if (currentEngine == null || currentFuelType == null) {
			throw new WidgetMachineException("The WidgetMachine " + machineId + " is not initialized for production.");
		}
		if (quantity <= 0) {
			throw new WidgetMachineException("The quantity " + quantity + " is invalid for production.");
		}
		this.currentEngine.start();
		int cost = 0;
		if (this.currentEngine.getState().equals(EngineState.STARTED)) {
			cost = produce(quantity);
		}
		this.currentEngine.stop();
		return cost;
	}

	private int produce(final int quantity) throws WidgetMachineException {
		int batchCost = 0;
		int batchSize = 0;
		try {
			batchSize = this.currentEngine.getBatchSize();
			if (quantity < batchSize) {
				LOGGER.info("Requested size of " + quantity + " is lesser than the batch size of " + batchSize
						+ ". So the required quantity cannot be produced by " + this.machineId);
			}
			final int requiredBatches = (int) Math.ceil(quantity / batchSize);
			batchCost = (int) Math.round(requiredBatches * this.curUnitBatchCost);
		} catch (ArithmeticException e) {
			throw new WidgetMachineException(
					"Batch size of " + batchSize + " and quantity of " + quantity + " is an invalid match.", e);
		}
		return batchCost;
	}

	public boolean availableEngine(String engineID) {
		return availablEngineIds.contains(engineID);
	}

	public boolean availableFuelType(final String engineID, final String fuelType) {
		return getEngine(engineID).isSupportedFuelType(FuelType.valueOf(fuelType));
	}

	public Engine getEngine(final String engineID) {
		return availableEngines.get(engineID);
	}
}
