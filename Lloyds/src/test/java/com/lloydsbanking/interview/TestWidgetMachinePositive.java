/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lloydsbanking.interview.engine.Component;
import com.lloydsbanking.interview.engine.EngineException;

public class TestWidgetMachinePositive {

	// init and use the getters
	// Produce with fuel 1
	// Produce with fuel 2

	private WidgetMachine widgetMachine;

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
	public final void testProduceWidgets() throws WidgetMachineException {
		widgetMachine.init("wMachineSteamEngine", "WOOD", "19");
		int cost = widgetMachine.produceWidgets(100);
		System.out.println("cost = " + cost);
		assertThat(cost).isEqualTo(222);
	}

	@Test
	public final void testProduceWidgetsWithCoal() throws WidgetMachineException {
		widgetMachine.init("wMachineSteamEngine", "COAL", "19");
		int cost = widgetMachine.produceWidgets(100);
		System.out.println("cost = " + cost);
		assertThat(cost).isEqualTo(111);
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
