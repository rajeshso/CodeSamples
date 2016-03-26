/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lloydsbanking.interview.FuelType;

public class TestComponentNegative {

	private Component engine;

	@Rule
	public ExpectedException expectedEngineException = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		// Init engine
		engine = new Component();
		String engineID = "wMachineSteamEngine";
		Set<FuelType> supportedFuelTypes = new HashSet<>(2);
		supportedFuelTypes.add(FuelType.COAL);
		supportedFuelTypes.add(FuelType.WOOD);
		int maxFuelLevel = 999;
		int batchSize = 10;
		Map<FuelType, Double> costSheet = new HashMap<>(2);
		costSheet.put(FuelType.COAL, 11.11);
		costSheet.put(FuelType.WOOD, 22.22);
		engine.init(engineID, supportedFuelTypes, maxFuelLevel, batchSize, costSheet);
	}

	@After
	public void tearDown() throws Exception {
		this.engine = null;
	}

	@Test
	public final void testStartWithoutFill() {
		boolean started = this.engine.start();
		assertThat(started).isFalse();
	}

	/**
	 * this.validateNull(id, "Engine ID");
	 * this.validateFuelTypes(supportedFuelTypes, "supportedFuelTypes");
	 * this.validateNull(maxFuelLevel, "maxFuelLevel");
	 * this.validateNull(batchSize, "batchSize");
	 * this.validateCostSheet(costSheet, "costSheet");
	 * 
	 * @throws EngineException
	 */

	@Test
	public final void testInitWithIncorrectID() throws EngineException {
		expectedEngineException.expect(EngineException.class);
		expectedEngineException.expectMessage("supportedFuelTypes is a mandatory property");
		engine = new Component();
		String engineID = "wMachineSteamEngine";
		Set<FuelType> supportedFuelTypes = null;
		// supportedFuelTypes.add(FuelType.COAL);
		// supportedFuelTypes.add(FuelType.WOOD);
		int maxFuelLevel = 999;
		int batchSize = 10;
		Map<FuelType, Double> costSheet = new HashMap<>(2);
		costSheet.put(FuelType.COAL, 11.11);
		costSheet.put(FuelType.WOOD, 22.22);
		engine.init(engineID, supportedFuelTypes, maxFuelLevel, batchSize, costSheet);
	}

	@Test
	public final void testInitWithIncorrectSupportedFuelTypes() throws EngineException {
		expectedEngineException.expect(EngineException.class);
		expectedEngineException.expectMessage("Engine ID is a mandatory property");
		engine = new Component();
		String engineID = "";
		Set<FuelType> supportedFuelTypes = new HashSet<>(2);
		supportedFuelTypes.add(FuelType.COAL);
		supportedFuelTypes.add(FuelType.WOOD);
		int maxFuelLevel = 999;
		int batchSize = 10;
		Map<FuelType, Double> costSheet = new HashMap<>(2);
		costSheet.put(FuelType.COAL, 11.11);
		costSheet.put(FuelType.WOOD, 22.22);
		engine.init(engineID, supportedFuelTypes, maxFuelLevel, batchSize, costSheet);
	}

	@Test
	public final void testInitWithIncorrectMaxFuelLevel() throws EngineException {
		expectedEngineException.expect(EngineException.class);
		expectedEngineException.expectMessage("maxFuelLevel is a mandatory property");
		engine = new Component();
		String engineID = "wMachineSteamEngine";
		Set<FuelType> supportedFuelTypes = new HashSet<>(2);
		supportedFuelTypes.add(FuelType.COAL);
		supportedFuelTypes.add(FuelType.WOOD);
		int maxFuelLevel = -999;
		int batchSize = 10;
		Map<FuelType, Double> costSheet = new HashMap<>(2);
		costSheet.put(FuelType.COAL, 11.11);
		costSheet.put(FuelType.WOOD, 22.22);
		engine.init(engineID, supportedFuelTypes, maxFuelLevel, batchSize, costSheet);
	}

	@Test
	public final void testInitWithIncorrectBatchSize() throws EngineException {
		expectedEngineException.expect(EngineException.class);
		expectedEngineException.expectMessage("batchSize is a mandatory property");
		engine = new Component();
		String engineID = "wMachineSteamEngine";
		Set<FuelType> supportedFuelTypes = new HashSet<>(2);
		supportedFuelTypes.add(FuelType.COAL);
		supportedFuelTypes.add(FuelType.WOOD);
		int maxFuelLevel = 999;
		int batchSize = 0;
		Map<FuelType, Double> costSheet = new HashMap<>(2);
		costSheet.put(FuelType.COAL, 11.11);
		costSheet.put(FuelType.WOOD, 22.22);
		engine.init(engineID, supportedFuelTypes, maxFuelLevel, batchSize, costSheet);
	}

	@Test
	public final void testFillWithUnsupportedFuel() throws EngineException {
		expectedEngineException.expect(EngineException.class);
		expectedEngineException.expectMessage("Invalid fuel PETROL supplied for wMachineSteamEngine");
		this.engine.fill(FuelType.PETROL, 10);
	}

	@Test
	public final void testFillWithExcessFuel() {
		try {
			this.engine.fill(FuelType.COAL, 100000);
			int currentFuelLEvel = this.engine.getCurrentFuelLevel();
			assertThat(currentFuelLEvel).isEqualTo(999);
		} catch (EngineException e) {
			fail("EngineException is not expected");
		}
	}

	@Test
	public final void testFillWithNegativeFuel() throws EngineException {
		expectedEngineException.expect(EngineException.class);
		expectedEngineException.expectMessage("Invalid fuel level -10 supplied for wMachineSteamEngine");
		this.engine.fill(FuelType.WOOD, -10);
	}

	@Test
	public final void testGetCostForIncorrectFuelType() throws EngineException {
		expectedEngineException.expect(EngineException.class);
		expectedEngineException.expectMessage("Invalid fuel PETROL supplied for wMachineSteamEngine");
		this.engine.fill(FuelType.PETROL, -10);
	}

}
