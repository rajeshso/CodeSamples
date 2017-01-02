package com.hmrc.shop.inventory.tdd;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.hmrc.shop.Inventory;
import com.hmrc.shop.Item;
import com.hmrc.shop.inventory.FruitInventory;

public class TestFruitInventory {
	Inventory fi = null;
	
	@Before
	public void setUp() throws Exception {
		try {
			fi = new FruitInventory("\\src\\test\\resources\\item.properties");
		} catch (Exception e) {
			fail("The file is not accessible");
		}
	}

	@Test
	public final void testFruitInventory() {
		try {
			Inventory fiInvalid = new FruitInventory("\\src\\test\\resources\\item.properties");
		} catch (Exception e) {
			fail("The file is not accessible");
		}
	}

	@Test
	public final void testGetItemForUnitCost() {
		Item i = fi.getItem("Apple", 2);
		assertThat(i.getUnitCost()).isEqualTo(0.6);
	}
	@Test
	public final void testGetItemForItemCost() {
		Item i = fi.getItem("Apple", 2);
		assertThat(i.getItemCost()).isEqualTo(1.2);
	}
	@Test
	public final void testGetItemForItemCostForNull() {
		Item i = fi.getItem("Tomato", 2);
		assertThat(i).isNull();
	}
	@Test
	public final void testIsAvailableFalse() {
		assertThat(fi.isAvailable("Tomato")).isEqualTo(false);
	}
	@Test
	public final void testIsAvailableTrue() {
		assertThat(fi.isAvailable("Apple")).isEqualTo(true);
	}
	@Test
	public final void tesGetItem() {
		Item i = fi.getItem("Kiwi", 2);
		assertThat(i.getQuantity()).isEqualTo(2);
	}
}
