package com.hmrc.shop.inventory.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.hmrc.shop.inventory.Fruit;

public class TestFruit {

	@Test
	public final void testFruitStringDouble() {
		Fruit f = new Fruit("Watermelon", 2.1);
		assertThat(f.getName()).isEqualTo("Watermelon");
	}

	@Test
	public final void testUpdateQuantityWithoutValue() {
		Fruit f = new Fruit("Watermelon", 2.1);
		assertThat(f.getQuantity()).isEqualTo(0);
	}
	@Test
	public final void testUpdateQuantity() {
		Fruit f = new Fruit("Watermelon", 2.1);
		f.updateQuantity(1);
		assertThat(f.getQuantity()).isEqualTo(1);
	}
	@Test
	public final void testGetName() {
		Fruit f = new Fruit("Watermelon", 2.1);
		f.updateQuantity(1);
		assertThat(f.getName()).isEqualTo("Watermelon");
	}

	@Test
	public final void testGetUnitCost() {
		Fruit f = new Fruit("Watermelon", 2.1);
		f.updateQuantity(1);
		assertThat(f.getUnitCost()).isEqualTo(2.1);
	}

	@Test
	public final void testGetQuantity() {
		Fruit f = new Fruit("Watermelon", 2.1);
		f.updateQuantity(1);
		assertThat(f.getQuantity()).isEqualTo(1);
	}

	@Test
	public final void testGetItemCost() {
		Fruit f = new Fruit("Watermelon", 2.0);
		f.updateQuantity(2);
		assertThat(f.getItemCost()).isEqualTo(4.0);
	}
	@Test
	public final void testGetItemCostHighValue() {
		Fruit f = new Fruit("Watermelon", 200000000.0001111111);
		f.updateQuantity(20000000);
		assertThat(f.getItemCost()).isEqualTo(4.000000000002222E15);
	}
	@Test
	public final void testGetItemCostDiscountValue() {
		Fruit f = new Fruit("Watermelon", -200000000.0001111111);
		f.updateQuantity(20000000);
		assertThat(f.getItemCost()).isEqualTo(-4.000000000002222E15);
	}

}
