package com.rajesh.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FirstController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FirstController.class);

	@RequestMapping(value="/sayHi", method=RequestMethod.GET)
	public String sayHi(Model model) {
		model.addAttribute("name", "Rajesh");
		return "Hi";
	}
	
	@RequestMapping(value="/sayHi1", method= RequestMethod.GET)
	public ModelAndView sayHi1(Model model) {
		model.addAttribute("name", "Rajesh");
		return new ModelAndView("Hi");
	}
	
}
