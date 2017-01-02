package com.hmrc.shop.bom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hmrc.shop.BillCalculator;
import com.hmrc.shop.Inventory;
import com.hmrc.shop.Item;
import com.hmrc.shop.Offer;
import com.hmrc.shop.TechnicalFailureException;
import com.hmrc.shop.inventory.FruitInventory;

/**
 * A simple calculator for the bill of materials.
 * 
 * @author Rajesh
 *
 */
public class Calculator implements BillCalculator {
	private List<Item> billableItemList;
	private double totalCost;
	private Inventory fruitInventory = null;
	private Map<String, Offer> offerMap = new HashMap<>();
	private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

	public enum PROPERTY_KEYS {
		FRUIT_PROPERTYFILE, OFFER_PROPERTYFILE
	}

	/**
	 * Initialises the calculator with the values from the given files.
	 * 
	 * @param initFiles
	 *            the property files
	 * 
	 * @throws TechnicalFailureException
	 *             if the file is not found or the file is in incorrect format
	 */
	public Calculator(Map<String, String> initFiles) throws TechnicalFailureException {
		if (!initFiles.containsKey(PROPERTY_KEYS.FRUIT_PROPERTYFILE.toString())) {
			LOGGER.debug("The property specified in the initFile is mandatory");
			throw new TechnicalFailureException("Fruit File is mandatory");
		}
		try {
			String fruitFile = initFiles.get(PROPERTY_KEYS.FRUIT_PROPERTYFILE.toString());
			fruitInventory = new FruitInventory(fruitFile);
			if (initFiles.containsKey(PROPERTY_KEYS.OFFER_PROPERTYFILE.toString())) {
				buildOfferMap(initFiles.get(PROPERTY_KEYS.OFFER_PROPERTYFILE.toString()));
			}
		} catch (Exception e) {
			LOGGER.error("The configuration has to be set right : "+e.getMessage());
			throw new TechnicalFailureException(e.getMessage());
		}
	}

	private void buildOfferMap(String offerFile)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Properties prop = new Properties();
		File f = new File(new File("").getAbsolutePath() + offerFile);
		try (InputStream input = new FileInputStream(f);) {
			prop.load(input);
			offerMap = prop.entrySet().stream().filter(entryset -> isOfferAvailable((String) entryset.getValue()))
					.collect(Collectors.toMap(t -> (String) t.getKey(), t -> getOffer((String) t.getValue())));
		}
	}

	/**
	 * Generate the Cost
	 */
	@Override
	public double generate(String[] items) {
		// Group the items as a Map
		Map<String, Long> itemMap = groupItemsMap(items);
		// Iterate through the map to collect the items in the billableItemList
		buildBillableItems(itemMap);
		// Calculate the total cost and return
		totalCost = getTotalCost();
		LOGGER.debug("Total cost without offers = " + totalCost);
		// if offer absent, return total cost
		if (offerMap.isEmpty())
			return totalCost;
		applyOffersifEligible();
		// recalculate the billableItemList
		totalCost = getTotalCost();
		LOGGER.debug("Total cost after offer recalculation = " + totalCost);
		return totalCost;
	}

	private void applyOffersifEligible() {
		List<Item> offerEligibleItems = billableItemList.stream().filter(item -> offerMap.containsKey(item.getName()))
				.collect(Collectors.toList());
		offerEligibleItems.stream().forEach(item -> offerMap.get(item.getName()).apply(item));
	}

	/**
	 * The items that are billable are built in a list. The items that are not
	 * available or incorrect items are skimmed
	 * 
	 * @param itemMap
	 *            item input
	 */
	private void buildBillableItems(Map<String, Long> itemMap) {
		billableItemList = itemMap.entrySet().stream()
				.filter(itemEntry -> fruitInventory.isAvailable(itemEntry.getKey()))
				.map(itemEntry -> fruitInventory.getItem(itemEntry.getKey(), itemEntry.getValue().intValue()))
				.collect(Collectors.toList());
	}

	/**
	 * The items are grouped into categories. For example, if the input is
	 * Apple, Apple, Orange, the output is Apple 2 and Orange 1.
	 * 
	 * @param items
	 *            item input
	 * @return grouped output
	 */
	private Map<String, Long> groupItemsMap(String[] items) {
		List<String> customerItemList = Arrays.asList(items);
		List<String> customerItemCleanedList =customerItemList.stream().map(string->string.trim()).collect(Collectors.toList());
		Map<String, Long> itemMap = customerItemCleanedList.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		return itemMap;
	}

	/**
	 * Calculate the total cost in a format that is useful for customer payments
	 * 
	 * @return cost with two decimal places
	 */
	private double getTotalCost() {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.valueOf(df.format(billableItemList.stream().mapToDouble(item -> item.getItemCost()).sum()));
	}

	/**
	 * Check if the offername is currently supported by the system.
	 * 
	 * @param offerName
	 * @return true if supported
	 */
	private boolean isOfferAvailable(String offerName) {
		try {
			Class.forName(offerName).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			return false;
		}
		return true;
	}

	/**
	 * Return the offer for the given offer ID. Beware of the exceptions and null if this
	 * method is invoked before checking in isOfferAvailable. Use isOfferAvailable before invoking getOffer
	 * 
	 * @param offerName
	 *            The offer name or the ID should be the full classname.
	 */
	private Offer getOffer(String offerName) {
		try {
			return (Offer) Class.forName(offerName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			LOGGER.debug("Use isOfferAvailable before invoking getOffer " + e.getMessage());
			LOGGER.error("The configuration values in the offers are incorrect.");
			return null;
		}
	}

}
