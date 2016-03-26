/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview;

import java.util.Map;

/**
 * The command prompt console for the Widget Machine. 
 * 
 * @author Rajesh
 *
 */
public class WidgetMachineEmulator {

	public WidgetMachineEmulator() {
	}

	/**
	 * @param args
	 * @throws WidgetMachineException
	 */
	public static void main(String[] args) throws WidgetMachineException {
		String propertyFileName = "machine.properties";
		
		if (args.length != 5) {
			System.out.println(
					"Usage: java -jar WidgetMachineEmulator.jar wmachine wmachineInternalCombustionEngine PETROL 100 8");
			System.out.println(
					"Usage: java -jar WidgetMachineEmulator.jar <machine name> <engine name> <fuel Type> <fuel quantity> <quantity>");
			return;
		}
		String giveMachineName = args[0];
		String giveEngineName = args[1];
		String giveFuelType = args[2];
		String giveFuelQuantity = args[3];
		String givenQuantity = args[4];
		MachineConfiguration machineConfiguration = null;
		try {
			machineConfiguration = new MachineConfiguration(propertyFileName);
		} catch (WidgetMachineException e) {
			System.err.println(e.getMessage());
			return;
		}
		Map<String, WidgetMachine> widgetMachinePool = machineConfiguration.getWidgetMachinePool();
		boolean valid = validateInput(widgetMachinePool, giveMachineName, giveEngineName, giveFuelType,
				giveFuelQuantity, givenQuantity);
		if (valid)
			System.out.println("Validation Complete : Input data is valid. ");
		else
			System.out.println("Validation Complete : Input data is invalid. ");
		if (valid) {
			try {
				WidgetMachine widgetMachine = widgetMachinePool.get(giveMachineName);
				widgetMachine.init(giveEngineName, giveFuelType, giveFuelQuantity);
				int totalCost = widgetMachine.produceWidgets(Integer.parseInt(givenQuantity));
				System.out.println("Produced widgets for GBP " + totalCost);
			} catch (WidgetMachineException wme) {
				System.err.println("Unable to produce the widgets : " + wme.getMessage());
			}
		}
	}

	private static boolean validateInput(Map<String, WidgetMachine> widgetMachinePool, String giveMachineName,
			String giveEngineName, String giveFuelType, String givenFuelQuantity, String givenQuantity) {
		try {
			Integer.parseInt(givenQuantity);
		} catch (NumberFormatException nfe) {
			System.err.println(givenQuantity + " is not a valid integer for production quantity. ");
			return false;
		}
		try {
			Integer.parseInt(givenFuelQuantity);
		} catch (NumberFormatException nfe) {
			System.err.println(givenFuelQuantity + " is not a valid integer for fuel quantity. ");
			return false;
		}
		try {
			FuelType.valueOf(giveFuelType);
		} catch (IllegalArgumentException iae) {
			System.err.println(giveFuelType + " is not a valid Fuel Type. ");
			return false;
		}
		if (!widgetMachinePool.containsKey(giveMachineName)) {
			System.err.println(giveMachineName + " is not a valid widget machine. ");
			return false;
		}
		if (widgetMachinePool.containsKey(giveMachineName)) {
			WidgetMachine wm = widgetMachinePool.get(giveMachineName);
			boolean validEngine = wm.availableEngine(giveEngineName);
			if (!validEngine) {
				System.err
						.println(giveEngineName + " is not a valid engine in widget machine " + giveMachineName + ". ");
				return false;
			}
		}
		return true;
	}

}
