package com.bdd.airportparking;

import static junit.framework.Assert.assertEquals;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CheckoutSteps {
	int bananaPrice;
	Checkout checkOut = new Checkout();;
	@Given("^the price of a \"(.*?)\" is (\\d+)p$")
	public void the_price_of_a_is_p(String itemName, int price) throws Throwable {
		this.bananaPrice = price; 
	}

	@When("^I checkout (\\d+) \"(.*?)\"$")
	public void i_checkout(int itemCount, String itemName) throws Throwable {
		checkOut.add(itemCount, bananaPrice);
	}

	@Then("^the total price should be (\\d+)p$")
	public void the_total_price_should_be_p(int totalExpected) throws Throwable {
		assertEquals(totalExpected, checkOut.getTotalPrice());
	}

	@SuppressWarnings("deprecation")
	@Then("^the runningtotal price should be (\\d+)p$")
	public void the_runningtotal_price_should_be_p(int runningTotal) throws Throwable {
		System.out.println("Checkout in runningTotal is "+ checkOut);
		assertEquals(runningTotal, checkOut.getRunningTotal());
	}
}
