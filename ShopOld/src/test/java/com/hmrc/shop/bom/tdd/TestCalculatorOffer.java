package com.hmrc.shop.bom.tdd;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.hmrc.shop.Offer;
import com.hmrc.shop.bom.Calculator;

public class TestCalculatorOffer {
	
	Calculator calculator = null;
	
	@Before
	public void setUp() throws Exception {
		Map<String, String> initMap = getInitializationFileMap();
		try {
			calculator = new Calculator(initMap);
		} catch (Exception e) {
			fail("An Exception is not expected here");
		}
	}

	@Test
	public final void testOfferBuy1Get1() {
		String[] input = { "Apple", "Apple", "Apple","Apple", "Orange"};
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(1.45);
	}
	
	@Test
	public final void testOffer3For2() {
		String[] input = { "Orange", "Orange", "Orange"};
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(0.5);
	}

	@Test
	public final void testOffer3For2For5Oranges() {
		String[] input = { "Orange", "Orange", "Orange","Orange", "Orange"};
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(1.0);
	}
	@Test
	public final void testOfferWithNonOffers() {
		String[] input = { "Kiwi", "Watermelon", "Orange","Orange", "Orange"};
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(3.5);
	}
	@Test
	public final void testOfferWithNoOffers() {
		String[] input = { "Kiwi", "Watermelon"};
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(3.0);
	}
	
	@Ignore
	public final void testGenerate() {
		fail("Not yet implemented"); // TODO
	}
	private Map<String, String> getInitializationFileMap() {
		Map<String, String> initMap = new HashMap<>(1);
		initMap.put(Calculator.PROPERTY_KEYS.FRUIT_PROPERTYFILE.name(), "\\src\\test\\resources\\item.properties");
		initMap.put(Calculator.PROPERTY_KEYS.OFFER_PROPERTYFILE.name(), "\\src\\test\\resources\\offer.properties");
		return initMap;
	}
}
