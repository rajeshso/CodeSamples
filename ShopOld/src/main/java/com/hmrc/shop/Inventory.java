package com.hmrc.shop;

/**
 * The Inventory is responsible to maintain the list of items and its information on cost.
 * When the customer asks for the cost of the item name, it returns the Item.
 * 
 * @author Rajesh
 *
 */
public interface Inventory {
	public Item getItem(String name, int quantity);
	public boolean isAvailable(String name);
}