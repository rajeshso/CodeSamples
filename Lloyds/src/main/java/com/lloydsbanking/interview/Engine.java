/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview;

import java.util.Map;
import java.util.Set;

import com.lloydsbanking.interview.engine.EngineException;
import com.lloydsbanking.interview.engine.EngineState;

public interface Engine {
	/**
	 * The Unique ID for the Engine.
	 * 
	 * @return integer value is unique for this engine.
	 */
	public String getID();

	/**
	 * The Fuel Type supported by this Engine. The Fuel Type examples are Petrol,
	 * Diesel, Wood or Coal. The Engine is capable of combustion of the
	 * supported Fuel types only.
	 * 
	 * @return Set<FuelType> is a list of enums
	 */
	public Set<FuelType> getSupportedFuelTypes();

	/**
	 * Is the Fuel Type valid for this Engine ?
	 * @param fuelType 
	 * @return true if valid
	 */
	public boolean isSupportedFuelType(FuelType fuelType);

	/**
	 * The Fuel Type that is currently in the tank.
	 * 
	 * @return FuelType
	 */
	public FuelType getCurrentFuelType();

	/**
	 * The Driver may start the engine by invoking this method. The state of the
	 * engine will be altered on start. The Engine would perform a task, only
	 * if its started.
	 * 
	 * @return true if the start is successful. If the engine is already started
	 *         or in a fault, then, false will be returned.
	 */
	public boolean start();

	/**
	 * The Driver may stop the engine by invoking this operation. The state of
	 * the engine will be altered on stopped. The Component would not perform a
	 * task, only if its stopped.
	 * 
	 * @return true if the stop is successful.
	 * 
	 */
	public boolean stop();

	/**
	 * Returns the current state of the Component.
	 * 
	 * @return EngineState
	 */
	public EngineState getState();

	/**
	 * Fills the engine with the required amount of Fuel.
	 * 
	 * @param fuelLevel
	 *            the integer units of the fuel level.
	 * @throws EngineException if input is incorrect
	 *             
	 */
	public void fill(FuelType fuelType, int fuelLevel) throws EngineException;

	/**
	 * Returns the batch Size.
	 * 
	 * @return int
	 */
	public int getBatchSize();

	/**
	 * Present the unit cost for using a fuel type
	 * 
	 * @param fuelType
	 * @return Cost
	 * @throws EngineException if the cost calculation goes wrong because of the input
	 */
	public Double getCost(FuelType fuelType) throws EngineException;
	
	/**
	 * Initialize the Engine before Start.
	 * 
	 * @param id
	 * @param supportedFuelTypes
	 * @param maxFuelLevel
	 * @param batchSize
	 * @param costSheet
	 * @throws EngineException
	 */
	public void init(String id, Set<FuelType> supportedFuelTypes, int maxFuelLevel, int batchSize,
			Map<FuelType, Double> costSheet) throws EngineException;

	/**
	 * Returns the configured Max Fuel Level
	 * @return fuel level
	 */
	public int getMaxFuelLevel();
}
