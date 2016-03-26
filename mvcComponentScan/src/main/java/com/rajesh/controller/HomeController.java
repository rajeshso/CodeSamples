package com.rajesh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RequestMapping("/")
@RequestMapping({"/","/home"})
public class HomeController {
	//@RequestMapping(value="/", method=RequestMethod.GET)
	@RequestMapping(method=RequestMethod.GET)
	public String home() {
		return "home";
	}
}
