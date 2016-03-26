/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview.engine;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lloydsbanking.interview.Engine;
import com.lloydsbanking.interview.FuelType;

/**
 * @author Rajesh
 *
 */
public class Component implements Engine {
	private static final Logger LOGGER = LoggerFactory.getLogger(Component.class);
	private static final String STR_IS_A_MANDATORY_PROPERTY = " is a mandatory property";
	private Set<FuelType> supportedFuelTypes = null;
	private Map<FuelType, Double> costSheet;
	private int maxFuelLevel;
	private int batchSize;

	private int currentFuelLevel;
	private FuelType currentFuelType;
	private String engineID;
	private EngineState currentEngineState = EngineState.STOPPED;

	public Component() {
	}

	@Override
	public void init(String id, Set<FuelType> supportedFuelTypes, int maxFuelLevel, int batchSize,
			Map<FuelType, Double> costSheet) throws EngineException {
		this.validateNull(id, "Engine ID");
		this.validateFuelTypes(supportedFuelTypes, "supportedFuelTypes");
		this.validateNull(maxFuelLevel, "maxFuelLevel");
		this.validateNull(batchSize, "batchSize");
		this.validateCostSheet(costSheet, "costSheet");

		this.supportedFuelTypes = supportedFuelTypes;
		this.maxFuelLevel = maxFuelLevel;
		this.engineID = id;
		this.batchSize = batchSize;
		this.costSheet = costSheet;
		LOGGER.info(this.engineID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lloydsbanking.interview.Engine#getID()
	 */
	@Override
	public String getID() {
		return engineID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lloydsbanking.interview.Engine#getFuelType()
	 */
	@Override
	public Set<FuelType> getSupportedFuelTypes() {
		return supportedFuelTypes;
	}

	/**
	 * The Fuel Type that is currently in the tank.
	 * 
	 * @return FuelType
	 */
	@Override
	public FuelType getCurrentFuelType() {
		return currentFuelType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lloydsbanking.interview.Engine#start()
	 */
	@Override
	public boolean start() {
		if (getCurrentFuelLevel() > 0) {
			this.currentEngineState = EngineState.STARTED;
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lloydsbanking.interview.Engine#stop()
	 */
	@Override
	public boolean stop() {
		this.currentEngineState = EngineState.STOPPED;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lloydsbanking.interview.Engine#fill(int)
	 */
	@Override
	public void fill(FuelType fuelType, int fuelLevel) throws EngineException {
		if (!isSupportedFuelType(fuelType))
			throw new EngineException("Invalid fuel " + fuelType.name() + " supplied for " + getID());
		if ((fuelLevel >= 0 && fuelLevel <= getMaxFuelLevel()))
			setCurrentFuelLevel(fuelLevel);
		else if (fuelLevel > getMaxFuelLevel())
			setCurrentFuelLevel(getMaxFuelLevel());
		else
			throw new EngineException("Invalid fuel level " + fuelLevel + " supplied for " + getID());
		this.currentFuelType = fuelType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lloydsbanking.interview.Engine#getState()
	 */
	@Override
	public EngineState getState() {
		return currentEngineState;
	}

	@Override
	public int getMaxFuelLevel() {
		return maxFuelLevel;
	}

	public boolean isRunning() {
		return getState().equals(EngineState.STARTED) ? true : false;
	}

	public int getCurrentFuelLevel() {
		return currentFuelLevel;
	}

	private void setCurrentFuelLevel(int currentFuelLevel) {
		this.currentFuelLevel = currentFuelLevel;
	}

	@Override
	public boolean isSupportedFuelType(final FuelType fuelType) {
		return getSupportedFuelTypes().contains(fuelType);
	}

	@Override
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @return the price for FuelType
	 */
	@Override
	public Double getCost(FuelType fuelType) throws EngineException {
		if (!costSheet.keySet().contains(fuelType))
			throw new EngineException("FuelType " + fuelType.name() + " is not supported in engine " + engineID);
		return costSheet.get(fuelType);
	}

	private void validateNull(String item, String itemName) throws EngineException {
		if (item == null || item.trim().isEmpty()) {
			EngineException wme = new EngineException(itemName + STR_IS_A_MANDATORY_PROPERTY);
			throw wme;
		}
	}

	private void validateNull(int item, String itemName) throws EngineException {
		if (item <= 0) {
			EngineException wme = new EngineException(itemName + STR_IS_A_MANDATORY_PROPERTY);
			throw wme;
		}
	}

	private void validateCostSheet(Map<FuelType, Double> costSheet2, String itemName) throws EngineException {
		if (costSheet2 == null || costSheet2.isEmpty()) {
			EngineException wme = new EngineException(itemName + STR_IS_A_MANDATORY_PROPERTY);
			throw wme;
		} else if ((costSheet2 != null && supportedFuelTypes != null)
				&& (costSheet2.keySet().containsAll(supportedFuelTypes))) {
			EngineException wme = new EngineException(itemName + " should support fuel types " + supportedFuelTypes);
			throw wme;
		}
	}

	private void validateFuelTypes(Set<FuelType> supportedFuelTypes2, String itemName) throws EngineException {
		if (supportedFuelTypes2 == null || supportedFuelTypes2.isEmpty()) {
			EngineException wme = new EngineException(itemName + STR_IS_A_MANDATORY_PROPERTY);
			throw wme;
		}
	}
}
