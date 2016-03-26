package com.rajesh.endpoint.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.rajesh.endpoint.Studio;

@Component("rahmanStudioChennai")
@Primary
//@Component
public class RahmanStudio implements Studio {

	//@Override
	public String playMusic() {
		System.out.println("Play Music from "+ this.getClass());
		return "Looo la la la";
	}

}
