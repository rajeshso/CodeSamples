/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview;

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

import com.lloydsbanking.interview.engine.Component;
import com.lloydsbanking.interview.engine.EngineException;

public class TestWidgetMachineNegative {

	private WidgetMachine widgetMachine;

	@Rule
	public ExpectedException wmExceptionExpected = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		String id = "wMachine";
		Map<String, Engine> availableEngines = getEngineMap();
		widgetMachine = new WidgetMachine(id, availableEngines);
	}

	@After
	public void tearDown() {
		widgetMachine = null;
	}

	@Test
	public final void testWithNullEngineId() throws WidgetMachineException {
		wmExceptionExpected.expect(WidgetMachineException.class);
		wmExceptionExpected.expectMessage("Invalid input to initialize the WidgetMachine");
		widgetMachine.init(null, "WOOD", "19");
	}

	@Test
	public final void testWithEmptyEngineId() throws WidgetMachineException {
		wmExceptionExpected.expect(WidgetMachineException.class);
		wmExceptionExpected.expectMessage("Invalid engineID to initialize the WidgetMachine");
		widgetMachine.init("   ", "WOOD", "19");
	}

	@Test
	public final void testWithEmptyFuelQuantity() throws WidgetMachineException {
		wmExceptionExpected.expect(WidgetMachineException.class);
		wmExceptionExpected.expectMessage("Invalid fuel quantity : ");
		widgetMachine.init("wMachineSteamEngine", "WOOD", " ");
	}

	@Test
	public final void testWithoutInit() throws WidgetMachineException {
		wmExceptionExpected.expect(WidgetMachineException.class);
		wmExceptionExpected.expectMessage("The WidgetMachine wMachine is not initialized for production.");
		widgetMachine.produceWidgets(100);
	}

	@Test
	public final void testProduceWidgetsWithNegativeQuantity() throws WidgetMachineException {
		wmExceptionExpected.expect(WidgetMachineException.class);
		wmExceptionExpected.expectMessage("The quantity -100 is invalid for production.");
		widgetMachine.init("wMachineSteamEngine", "WOOD", "19");
		widgetMachine.produceWidgets(-100);
	}

	@Test
	public final void testProduceWidgetsWithZeroQuantity() throws WidgetMachineException {
		wmExceptionExpected.expect(WidgetMachineException.class);
		wmExceptionExpected.expectMessage("The quantity 0 is invalid for production.");
		widgetMachine.init("wMachineSteamEngine", "WOOD", "19");
		widgetMachine.produceWidgets(0);
	}

	@Test
	public final void testProduceWidgetsWithQuantityLessThanBatch() {
		try {
			widgetMachine.init("wMachineSteamEngine", "WOOD", "19");
			int cost = widgetMachine.produceWidgets(1);
			assertThat(cost).isZero();
		} catch (WidgetMachineException e) {
			fail("WidgetMachineException is not expected here.");
		}
	}

	private Map<String, Engine> getEngineMap() throws EngineException {
		Component engine = new Component();
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
		Map<String, Engine> result = new HashMap<>(1);
		result.put(engineID, engine);
		return result;
	}
}
