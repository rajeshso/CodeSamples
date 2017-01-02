package com.hmrc.shop.offer;


import com.hmrc.shop.Item;
import com.hmrc.shop.Offer;

public class OfferBuy1Get1 implements Offer {
	private static final String ID = "com.hmrc.shop.offer.OfferBuy1Get1";
	
	@Override
	public void apply(Item i) {
		int discountQuantity = i.getQuantity()/2;
		double discountCost = i.getUnitCost()*discountQuantity;
		double newCost = i.getItemCost()-discountCost;
		i.updateOffer(ID, newCost);
	}
}
