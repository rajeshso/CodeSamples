package com.lloydsbanking.interview.bdd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Map;

import com.lloydsbanking.interview.MachineConfiguration;
import com.lloydsbanking.interview.WidgetMachine;
import com.lloydsbanking.interview.WidgetMachineException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class WidgetMachineStepDefinitions {

	private WidgetMachine widgetMachine;
	private Map<String, WidgetMachine> widgetMachinePool;
	private int totalCost;
	private String errorMessage;

	@Given("^the configuration is \"([^\"]*)\"$")
	public void configure(String propertyFileName) throws Throwable {
		MachineConfiguration machineConfiguration = null;
		try {
			machineConfiguration = new MachineConfiguration(propertyFileName);
			widgetMachinePool = machineConfiguration.getWidgetMachinePool();
			assertThat(widgetMachinePool).isNotEmpty();
		} catch (WidgetMachineException e) {
			fail("The " + propertyFileName + " or the business logic in code is incorrect");
		} catch (Exception e) {
			fail("The " + propertyFileName + " or the business logic in code is incorrect");
		}
	}

	@When("^machineName is \"([^\"]*)\" that has an Engine \"([^\"]*)\" filled with \"([^\"]*)\" litres of fuel \"([^\"]*)\"$")
	public void fillFuel(String machinName, String engineName, String fuelQuantity, String fuelType) throws Throwable {
		try {
			widgetMachine = widgetMachinePool.get(machinName);
			widgetMachine.init(engineName, fuelType, fuelQuantity);
		} catch (WidgetMachineException wme) {
			fail(wme.getMessage());
		}
	}

	@When("^I request the cost of \"([^\"]*)\" widgets$")
	public void requestQuantity(String widgetQty) throws Throwable {
		totalCost = widgetMachine.produceWidgets(Integer.parseInt(widgetQty));
	}

	@Then("^the cost should be \"([^\"]*)\"$")
	public void shouldBeCost(String cost) throws Throwable {
		assertThat(Integer.toString(totalCost)).isEqualTo(cost);
	}

	// Exception Scenarios
	@Given("^the incorrect configuration is \"([^\"]*)\"$")
	public void incorrectConfiguration(String incorrectConfigFile) throws Throwable {
		MachineConfiguration machineConfiguration = null;
		try {
			machineConfiguration = new MachineConfiguration(incorrectConfigFile);
			fail("Exception expected for incorrect configuration file");
		} catch (WidgetMachineException e) {
			errorMessage = e.getMessage();
		} catch (Exception e) {
			fail("A generic Exception is NOT expected for incorrect configuration file.");
		}
	}

	@When("^machineName is \"([^\"]*)\" that has an unconfigured Engine \"([^\"]*)\" filled with \"([^\"]*)\" watts of unconfigured fuel \"([^\"]*)\"$")
	public void fillUsingUnsupportedEngine(String machineName, String incorrectEngine, String fuelQty,
			String incorrectFuelType) throws Throwable {
		try {
			widgetMachine = widgetMachinePool.get(machineName);
			widgetMachine.init(incorrectEngine, incorrectFuelType, fuelQty);
			fail("Exception expected for unsupported engine and fuel type");
		} catch (WidgetMachineException wme) {
			this.errorMessage = wme.getMessage();
		}
	}

	@Then("^the error message should be \"([^\"]*)\"$")
	public void isErrorMessage(String errorMessage) throws Throwable {
		assertThat(this.errorMessage).isEqualTo(errorMessage);
	}
}
