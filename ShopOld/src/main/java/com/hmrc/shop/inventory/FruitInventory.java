/**
 * 
 */
package com.hmrc.shop.inventory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import com.hmrc.shop.Inventory;
import com.hmrc.shop.Item;
import com.hmrc.shop.TechnicalFailureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The fruit inventory maintains information related to Fruits.
 * 
 * @author Rajesh
 *
 */
public class FruitInventory implements Inventory {
	private static final Logger LOGGER = LoggerFactory.getLogger(FruitInventory.class);

	private List<Fruit> fruitStore;

	public FruitInventory(String fruitfile) throws Exception {
		Properties prop = new Properties();
		File f = new File(new File("").getAbsolutePath()+fruitfile);
		try (InputStream input = new FileInputStream(f) ) {
			prop.load(input);
			fruitStore = prop.keySet().stream().map(key->new Fruit((String)key, Double.parseDouble((String)prop.getProperty((String)key, "0.0")))).collect(Collectors.toList());
		} 
	}

	/**
	 * Beware that this method returns null if the given item is not found. The user is advised to use 
	 * the isAvailable before invoking this method.
	 */
	@Override
	public Item getItem(String name, int quantity) {
		Optional<Fruit> fruitOptional = fruitStore.stream().filter(f -> f.getName().equals(name)).findAny();
		if (fruitOptional.isPresent()) {
			Fruit fruit = fruitOptional.get();
			fruit.updateQuantity(quantity);
			return fruit;
		}
		LOGGER.debug("The item is not valid. A null is returned.");
		return null;
	}

	@Override
	public boolean isAvailable(String name) {
		return fruitStore.stream().anyMatch(f->f.getName().equals(name));
	}

}
