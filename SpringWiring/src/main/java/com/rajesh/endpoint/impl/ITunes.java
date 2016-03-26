package com.rajesh.endpoint.impl;

import com.rajesh.endpoint.CD;
import com.rajesh.endpoint.MusicStore;

public class ITunes implements MusicStore {

	//@Override
	public void checkPrice() {
		System.out.println("Price checked");
	}

	//@Override
	public CD buyCD(int price) {
		return new CD(1,"Raaga");
	}

}
