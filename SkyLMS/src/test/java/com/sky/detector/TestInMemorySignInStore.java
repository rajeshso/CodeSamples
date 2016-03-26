package com.sky.detector;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;

import org.junit.Test;

public class TestInMemorySignInStore {

	@Test
	public final void testAdd() {
		InMemorySignInStore store = new InMemorySignInStore();
		store.add(1336129471L, "a");
		assertThat(store.get(1336129471L)).isEqualTo("a");
	}

	@Test
	public final void testRemoveObsoleteUniformElements() {
		InMemorySignInStore store = new InMemorySignInStore();
		Calendar c = Calendar.getInstance();
		c.set(2010, 1, 26, 10, 30, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 31, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 32, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 33, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 34, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 35, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 36, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 37, 0);
		store.add(c.getTime().getTime(), "a");
		assertThat(store.countEvents("a")).isEqualTo(8);

		try {
			c.set(2010, 1, 26, 10, 31);
			store.removeObsolete(c.getTime().getTime());
			assertThat(store.countEvents("a")).isEqualTo(6);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void testRemoveObsoleteNonUniformElements() {
		InMemorySignInStore store = new InMemorySignInStore();
		Calendar c = Calendar.getInstance();
		c.set(2010, 1, 26, 10, 28, 0);
		store.add(c.getTime().getTime(), "b");
		c.set(2010, 1, 26, 10, 29, 0);
		store.add(c.getTime().getTime(), "c");

		c.set(2010, 1, 26, 10, 30, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 31, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 32, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 33, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 34, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 35, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 36, 0);
		store.add(c.getTime().getTime(), "a");
		c.set(2010, 1, 26, 10, 37, 0);
		store.add(c.getTime().getTime(), "a");

		c.set(2010, 1, 26, 10, 38, 0);
		store.add(c.getTime().getTime(), "b");
		c.set(2010, 1, 26, 10, 39, 0);
		store.add(c.getTime().getTime(), "c");

		assertThat(store.countEvents("a")).isEqualTo(8);
		assertThat(store.countEvents("b")).isEqualTo(2);
		assertThat(store.countEvents("c")).isEqualTo(2);

		try {
			c.set(2010, 1, 26, 10, 31);
			store.removeObsolete(c.getTime().getTime());
			assertThat(store.countEvents("a")).isEqualTo(6);
			assertThat(store.countEvents("b")).isEqualTo(1);
			assertThat(store.countEvents("c")).isEqualTo(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void testCountEvents() {
		InMemorySignInStore store = new InMemorySignInStore();
		store.add(1336129471L, "a");
		store.add(1336129472L, "a");
		assertThat(store.countEvents("a")).isEqualTo(2);
	}
}
