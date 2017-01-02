package com.hmrc.shop.offer;

import com.hmrc.shop.Item;
import com.hmrc.shop.Offer;

public class Offer3For2 implements Offer {

	private static final String ID = "com.hmrc.shop.offer.Offer3For2";
	
	@Override
	public void apply(Item i) {
		int newQuantityForCost = (i.getQuantity()/3)*2 + (i.getQuantity()%3);
		double discountCost = i.getUnitCost()*newQuantityForCost;
		i.updateOffer(ID, discountCost);
	}
}
