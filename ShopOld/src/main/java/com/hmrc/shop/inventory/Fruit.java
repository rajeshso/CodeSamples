package com.hmrc.shop.inventory;

import java.util.ArrayList;
import java.util.List;

import com.hmrc.shop.Item;
/**
 * A Classification of Item that represents fruit
 * This is not just a data holder; this is capable of calculating the individual cost
 * in its own business logic
 * 
 * @author Rajesh
 *
 */
public class Fruit implements Item {
	
	private String name;
	private double unitCost;
	private int quantity;
    private double itemCost;
    private List<String> appliedOfferList;
    
	public Fruit(String name, double unitCost) {
		this.name = name;
		this.unitCost = unitCost;
		appliedOfferList = new ArrayList<>();
	}
	
	@Override
	public void updateQuantity(int quantity) {
		if (quantity<0) 
			this.quantity = 0;
		else
			this.quantity = quantity;
		itemCost = unitCost * quantity;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Double getUnitCost() {
		return this.unitCost;
	}

	@Override
	public int getQuantity() {
		return this.quantity;
	}

	@Override
	public Double getItemCost() {
		return this.itemCost;
	}
	
	@Override
	public void updateOffer(String offerId,double discount) {
		this.itemCost = discount;
		this.appliedOfferList.add(offerId);
	}
	@Override
	public List<String> getOffers() {
		return this.appliedOfferList;
	}
	@Override
	public String toString() {
		return this.name +" "+ this.unitCost;
	}
}
