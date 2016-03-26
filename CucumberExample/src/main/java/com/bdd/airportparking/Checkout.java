package com.bdd.airportparking;

public class Checkout {
	int price;
	int itemCount;
	int runningTotal;
	public void add(int itemCount, int bananaPrice) {
		this.price = bananaPrice;
		this.itemCount = itemCount;
		runningTotal=runningTotal+ (bananaPrice*itemCount);
		System.out.println("Running Total is "+ runningTotal);
	}

	public int getTotalPrice() {
		return price*itemCount;
	}
	public int getRunningTotal() {
		return runningTotal;
	}
}
