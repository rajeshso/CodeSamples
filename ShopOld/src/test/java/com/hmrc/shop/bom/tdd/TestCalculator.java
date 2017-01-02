package com.hmrc.shop.bom.tdd;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.hmrc.shop.BillCalculator;
import com.hmrc.shop.bom.Calculator;

public class TestCalculator {
	private BillCalculator calculator = null;

	@Before
	public void setUp() {
		try {
			calculator = new Calculator(this.getInitializationFileMap());
		} catch (Exception e) {
			fail("An Exception is not expected here");
		}
	}

	@Test
	public final void setUpWithIncorrectFile() {
		try {
			Map<String, String> initMap = new HashMap<>(1);
			initMap.put(Calculator.PROPERTY_KEYS.FRUIT_PROPERTYFILE.name(), "abc.properties");
			new Calculator(initMap);
			fail("An Exception is expected here");
		} catch (Exception e) {
			assertThat(e.getMessage()).contains("The system cannot find the file specified");
		}
	}
	@Test
	public final void setUpWithIncorrectFileProperty() {
		try {
			Map<String, String> initMap = new HashMap<>(1);
			initMap.put("ABC", "abc.properties");
			new Calculator(initMap);
			fail("An Exception is expected here");
		} catch (Exception e) {
			assertThat(e.getMessage()).contains("Fruit File is mandatory");
		}
	}
	@Test
	public final void testGenerate() {
		String[] input = { "Apple" };
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(0.6);
	}

	@Test
	public final void testGenerateWithMoreItemsSameName() {
		String[] input = { "Apple", "Apple" };
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(1.2);
	}

	@Test
	public final void testGenerateWithDifferentItems() {
		String[] input = { "Apple", "Kiwi" };
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(1.6);
	}

	@Test
	public final void testGenerateWithNItemsNTypes() {
		String[] input = { "Apple", "Kiwi", "Apple", "Kiwi" };
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(3.2);
	}

	@Test
	public final void testGenerateWith1InvalidItem() {
		String[] input = { "Apple", "Kiwi", "Apple", "Tomato" };
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(2.2);
	}

	@Test
	public final void testGenerateWithAllInvalidItems() {
		String[] input = { "Egg", "Eggplant", "Potato", "Tomato" };
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(0.0);
	}

	@Test
	public final void testGenerateWithAllEmptyItems() {
		String[] input = { "", "", "", "" };
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(0.0);
	}

	@Test
	public final void testGenerateWithLargeVolume() {
		int volume = 100000;
		String[] input = new String[volume];
		for (int i = 0; i < volume; i++) {
			input[i] = "Apple";
		}
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(60000.0);
	}

	@Ignore
	public final void testGenerateWithLargeVolume1() {
		int volume = 100000000;
		String[] input = new String[volume];
		for (int i = 0; i < volume; i++) {
			input[i] = "Apple";
		}
		double cost = calculator.generate(input);
		assertThat(cost).isEqualTo(6.0E7);
	}

	@Test
	public final void testGenerateWithInvalidBlankFile1() {
		try {
			String[] input = { "Apple" };
			Map<String, String> initMap = new HashMap<>(1);
			initMap.put(Calculator.PROPERTY_KEYS.FRUIT_PROPERTYFILE.name(),
					"\\src\\test\\resources\\itemInvalid1.properties");
			BillCalculator c = new Calculator(initMap);
			double cost = c.generate(input);
			assertThat(cost).isEqualTo(0.0);
		} catch (Exception e) {
			fail("An Exception is NOT expected here");
		}
	}

	@Test
	public final void testGenerateWithInvalidCharFile2() {
		try {
			String[] input = { "Apple" };
			Map<String, String> initMap = new HashMap<>(1);
			initMap.put(Calculator.PROPERTY_KEYS.FRUIT_PROPERTYFILE.name(),
					"\\src\\test\\resources\\itemInvalid2-incorrectChar.properties");
			BillCalculator c = new Calculator(initMap);
			double cost = c.generate(input);
			fail("An Exception is expected here");
		} catch (Exception e) {
			assertThat(e.getMessage()).contains("empty");
		}
	}

	private Map<String, String> getInitializationFileMap() {
		Map<String, String> initMap = new HashMap<>(1);
		initMap.put(Calculator.PROPERTY_KEYS.FRUIT_PROPERTYFILE.name(), "\\src\\test\\resources\\item.properties");
		return initMap;
	}
}
