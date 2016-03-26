/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.lloydsbanking.interview.engine.EngineException;

/**
 * machines=2 machine.1=wmachine wmachine.engines=2
 * 
 * wmachine.engine.1=com.lloydsbanking.interview.engine.Component
 * wmachine.engine.1.type=InternalCombustionEngine
 * wmachine.engine.1.maxfuellevel=100
 * wmachine.engine.1.supportedfueltypes=PETROL, DIESEL
 * wmachine.engine.1.cost.fueltype.PETROL=9
 * wmachine.engine.1.cost.fueltype.DIESEL=12 wmachine.engine.1.batchsize=8
 * 
 * @author Rajesh
 *
 */
public class TestMachineConfigurationPostive {

	// One Configuration file
	// 1 Engine - DONE
	// Engine class - DONE
	// maxfuellevel - DONE
	// supportedfueltypes - DONE
	// fueltype 1,2,3 - DONE
	// batchsize - DONE

	// 2 Engines - Done
	// maxfuellevel different - DONE

	@Test
	public final void testMachineConfigurationForEngine() {
		try {
			MachineConfiguration machineConfiguration = new MachineConfiguration("machinePositive1.properties");
			Map<String, WidgetMachine> wMachinePool = machineConfiguration.getWidgetMachinePool();
			WidgetMachine wm = wMachinePool.get("wmachine");
			assertThat(wm.availableEngine("wmachineInternalCombustionEngine"));
		} catch (WidgetMachineException e) {
			fail("WidgetMachineException is not expected");
		}
	}

	@Test
	public final void testMachineConfigurationForEngineClass() {
		try {
			MachineConfiguration machineConfiguration = new MachineConfiguration("machinePositive1.properties");
			Map<String, WidgetMachine> wMachinePool = machineConfiguration.getWidgetMachinePool();
			WidgetMachine wm = wMachinePool.get("wmachine");
			assertThat(wm.getEngine("wmachineInternalCombustionEngine").getClass().getName())
					.hasToString("com.lloydsbanking.interview.engine.Component");
		} catch (WidgetMachineException e) {
			fail("WidgetMachineException is not expected");
		}
	}

	@Test
	public final void testMachineConfigurationForMaxfuellevel() {
		try {
			MachineConfiguration machineConfiguration = new MachineConfiguration("machinePositive1.properties");
			Map<String, WidgetMachine> wMachinePool = machineConfiguration.getWidgetMachinePool();
			WidgetMachine wm = wMachinePool.get("wmachine");
			assertThat(wm.getEngine("wmachineInternalCombustionEngine").getMaxFuelLevel()).isEqualTo(100);
		} catch (WidgetMachineException e) {
			fail("WidgetMachineException is not expected");
		}
	}

	@Test
	public final void testMachineConfigurationForSupportedfueltypes() {
		try {
			MachineConfiguration machineConfiguration = new MachineConfiguration("machinePositive1.properties");
			Map<String, WidgetMachine> wMachinePool = machineConfiguration.getWidgetMachinePool();
			WidgetMachine wm = wMachinePool.get("wmachine");
			Set<FuelType> supportedFuelTypes = wm.getEngine("wmachineInternalCombustionEngine").getSupportedFuelTypes();
			assertThat(supportedFuelTypes).containsOnly(FuelType.valueOf("PETROL"), FuelType.valueOf("DIESEL"));
		} catch (WidgetMachineException e) {
			fail("WidgetMachineException is not expected");
		}
	}

	// fueltype
	@Test
	public final void testMachineConfigurationForFueltypes() {
		try {
			MachineConfiguration machineConfiguration = new MachineConfiguration("machinePositive1.properties");
			Map<String, WidgetMachine> wMachinePool = machineConfiguration.getWidgetMachinePool();
			WidgetMachine wm = wMachinePool.get("wmachine");
			double petrolCost = wm.getEngine("wmachineInternalCombustionEngine").getCost(FuelType.valueOf("PETROL"));
			double dieselCost = wm.getEngine("wmachineInternalCombustionEngine").getCost(FuelType.valueOf("DIESEL"));
			assertThat(petrolCost).isEqualTo(9.99);
			assertThat(dieselCost).isEqualTo(12.12);
		} catch (WidgetMachineException | EngineException e) {
			fail("WidgetMachineException is not expected");
		}
	}

	// batchsize
	@Test
	public final void testMachineConfigurationForBatchsize() {
		try {
			MachineConfiguration machineConfiguration = new MachineConfiguration("machinePositive1.properties");
			Map<String, WidgetMachine> wMachinePool = machineConfiguration.getWidgetMachinePool();
			WidgetMachine wm = wMachinePool.get("wmachine");
			int batchSize = wm.getEngine("wmachineInternalCombustionEngine").getBatchSize();
			assertThat(batchSize).isEqualTo(10);
		} catch (WidgetMachineException e) {
			fail("WidgetMachineException is not expected");
		}
	}

	// Dual Engines
	@Test
	public final void testMachineConfigurationForDualEngines() {
		try {
			MachineConfiguration machineConfiguration = new MachineConfiguration("machinePositive2.properties");
			Map<String, WidgetMachine> wMachinePool = machineConfiguration.getWidgetMachinePool();
			WidgetMachine wm = wMachinePool.get("wmachine");
			assertThat(wm.getEngine("wmachineInternalCombustionEngine")).isNotNull();
			assertThat(wm.getEngine("wmachineSteamEngine")).isNotNull();
		} catch (WidgetMachineException e) {
			fail("WidgetMachineException is not expected");
		}
	}

	// Dual Engines - Different Properties-Fuel Levels
	@Test
	public final void testMachineConfigurationForDualEnginesDiffProps() {
		try {
			MachineConfiguration machineConfiguration = new MachineConfiguration("machinePositive2.properties");
			Map<String, WidgetMachine> wMachinePool = machineConfiguration.getWidgetMachinePool();
			WidgetMachine wm = wMachinePool.get("wmachine");
			int fuelLevelEngine1 = wm.getEngine("wmachineInternalCombustionEngine").getMaxFuelLevel();
			int fuelLevelEngine2 = wm.getEngine("wmachineSteamEngine").getMaxFuelLevel();
			assertThat(fuelLevelEngine1).isEqualTo(100);
			assertThat(fuelLevelEngine2).isEqualTo(999);
		} catch (WidgetMachineException e) {
			fail("WidgetMachineException is not expected");
		}
	}
}
