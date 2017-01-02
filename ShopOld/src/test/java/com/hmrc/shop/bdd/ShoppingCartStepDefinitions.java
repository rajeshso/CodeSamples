package com.hmrc.shop.bdd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import com.hmrc.shop.BillCalculator;
import com.hmrc.shop.bom.Calculator;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ShoppingCartStepDefinitions {
	
	private BillCalculator calculator = null;
	private double actualCost = 0.0;
	
	@Given("^the fruit cost in \"([^\"]*)\"$")
	public void initialiseFruitCost(String fruitPropertiesFile) throws Throwable {
		Map<String, String> initMap = new HashMap<>(1);
		initMap.put(Calculator.PROPERTY_KEYS.FRUIT_PROPERTYFILE.name(), fruitPropertiesFile);
		calculator = new Calculator(initMap);
	}

	@When("^the customer scans \"([^\"]*)\"$")
	public void scanItems(String items) throws Throwable {
		String[] itemArray = items.split(",");
		actualCost = calculator.generate(itemArray);
	}

	@Then("^The total cost should be (.+)$")
	public void generateTotalCostOfBill(double expectedCost) throws Throwable {
		assertThat(actualCost).isEqualTo(expectedCost);
	}
}
