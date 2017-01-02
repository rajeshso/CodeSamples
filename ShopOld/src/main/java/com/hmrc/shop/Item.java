package com.hmrc.shop;

import java.util.List;

/**
 * The representation of the Item
 * The Item may come under any classification, fruit, veg or juice or anything.
 * 
 * @author Rajesh
 *
 */
public interface Item {
	public String getName();
	public Double getUnitCost();
	public int getQuantity();
	public Double getItemCost();
	void updateQuantity(int quantity);
	void updateOffer(String offerId, double discount);
	List<String> getOffers();
}
