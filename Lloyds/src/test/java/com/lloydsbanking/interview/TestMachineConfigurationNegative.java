/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * machines=1 machine.1=wmachine wmachine.engines=2
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
public class TestMachineConfigurationNegative {

	// incorrect key names - DONE
	// missing values - DONE
	// invalid format for values - DONE
	// mismatch of engines and the number of mentioned engines - DONE
	// mismatch of supportedfueltypes and the number of fueltypes - DONE
	// invalid engine.1 value - incorrect classname - DONE

	@Rule
	public ExpectedException wmExceptionExpectedException = ExpectedException.none();

	// machineNegative1.properties
	@Test
	public final void testWithIncorrectMachineKey() throws WidgetMachineException {
		wmExceptionExpectedException.expect(WidgetMachineException.class);
		wmExceptionExpectedException
				.expectMessage("Unable to create a WidgeMachine.'machines' doesn't map to an existing object");
		new MachineConfiguration("machineNegative1.properties");
	}

	// machineNegative2.properties
	@Test
	public final void testWithIncorrectEngineKeys() throws WidgetMachineException {
		wmExceptionExpectedException.expect(WidgetMachineException.class);
		wmExceptionExpectedException.expectMessage("wmachine.engine.1.type is a mandatory property");
		new MachineConfiguration("machineNegative2.properties");
	}

	// machineNegative3.properties
	@Test
	public final void testWithIncorrectEngineTypeKeys() throws WidgetMachineException {
		wmExceptionExpectedException.expect(WidgetMachineException.class);
		wmExceptionExpectedException.expectMessage("wmachine.engine.1.type is a mandatory property");
		new MachineConfiguration("machineNegative3.properties");
	}

	// machineNegative4.properties - missing key and value
	@Test
	public final void testWithMissingEngineTypeKeys() throws WidgetMachineException {
		wmExceptionExpectedException.expect(WidgetMachineException.class);
		wmExceptionExpectedException.expectMessage(
				"Unable to create a WidgeMachine.'wmachine.engine.1.maxfuellevel' doesn't map to an existing object");
		new MachineConfiguration("machineNegative4.properties");
	}

	// machineNegative5.properties - missing value
	@Test
	public final void testWithMissingFuelTypeValue() throws WidgetMachineException {
		wmExceptionExpectedException.expect(WidgetMachineException.class);
		wmExceptionExpectedException.expectMessage(
				"Unable to create a WidgeMachine.'wmachine.engine.2.cost.fueltype.COAL' doesn't map to a Double object");
		new MachineConfiguration("machineNegative5.properties");
	}

	// machineNegative6.properties - Incorrect format for maxfuellevel
	@Test
	public final void testWithIncorrectFormatFuelLevelValue() throws WidgetMachineException {
		wmExceptionExpectedException.expect(WidgetMachineException.class);
		wmExceptionExpectedException.expectMessage(
				"Unable to create a WidgeMachine.'wmachine.engine.2.maxfuellevel' doesn't map to an Integer object");
		new MachineConfiguration("machineNegative6.properties");
	}

	// machineNegative7.properties mismatch of configured engines and the number
	// of mentioned engines
	@Test
	public final void testWithIncorrectNumberOfEngines() throws WidgetMachineException {
		wmExceptionExpectedException.expect(WidgetMachineException.class);
		wmExceptionExpectedException.expectMessage("wmachine.engine.3.type is a mandatory property");
		new MachineConfiguration("machineNegative7.properties");
	}

	// machineNegative8.properties mismatch of supportedfueltypes and the number
	// of fueltypes
	@Test
	public final void testWithIncorrectNumberOfSupportedfueltypes() throws WidgetMachineException {
		wmExceptionExpectedException.expect(WidgetMachineException.class);
		wmExceptionExpectedException.expectMessage(
				"Unable to create a WidgeMachine.'wmachine.engine.1.cost.fueltype.DIESEL' doesn't map to an existing object");
		new MachineConfiguration("machineNegative8.properties");
	}

	// machineNegative9.properties invalid engine.1 value - incorrect classname
	@Test
	public final void testWithIncorrectEngineClass() throws WidgetMachineException {
		wmExceptionExpectedException.expect(WidgetMachineException.class);
		wmExceptionExpectedException.expectMessage("Engine Class configured is not an Engine.");
		new MachineConfiguration("machineNegative9.properties");
	}

	// machineNegative10.properties invalid engine.1 value - incorrect classname
	@Test
	public final void testWithInvalidEngineStringValue() throws WidgetMachineException {
		wmExceptionExpectedException.expect(WidgetMachineException.class);
		wmExceptionExpectedException.expectMessage("Engine configured is not in the classpath.");
		new MachineConfiguration("machineNegative10.properties");
	}
}
