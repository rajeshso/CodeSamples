package com.rajesh.endpoint.impl;

import org.springframework.stereotype.Component;

import com.rajesh.endpoint.Studio;

@Component
public class DevaStudio implements Studio {

	//@Override
	public String playMusic() {
		System.out.println("Deva Studio music "+ this.getClass().getName());
		return "Da da di dooo";
	}

}
