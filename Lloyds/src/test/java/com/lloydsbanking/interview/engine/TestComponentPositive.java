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
import org.junit.Test;

import com.lloydsbanking.interview.FuelType;

public class TestComponentPositive {

	private Component engine;

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
	public final void testComponent() {
		assertThat(engine.getState()).isEqualTo(EngineState.STOPPED);
	}

	@Test
	public final void testStart() {
		try {
			this.engine.fill(FuelType.COAL, 55);
			this.engine.start();
			assertThat(engine.getState()).isEqualTo(EngineState.STARTED);
		} catch (EngineException e) {
			fail("EngineException is not expected during filling fuel");
		}
	}

	@Test
	public final void testIsRunning() {
		try {
			this.engine.fill(FuelType.COAL, 55);
			this.engine.start();
			assertThat(engine.isRunning()).isTrue();
		} catch (EngineException e) {
			fail("EngineException is not expected during filling fuel");
		}
	}

	@Test
	public final void testIsSupportedFuelType() {
		assertThat(this.engine.isSupportedFuelType(FuelType.COAL)).isTrue();
	}

	@Test
	public final void testGetCost() {
		try {
			this.engine.fill(FuelType.WOOD, 155);
			this.engine.start();
			Double cost = this.engine.getCost(FuelType.WOOD);
			assertThat(cost).isEqualTo(22.22);
		} catch (EngineException e) {
			fail("EngineException is not expected.");
		}
	}

}
