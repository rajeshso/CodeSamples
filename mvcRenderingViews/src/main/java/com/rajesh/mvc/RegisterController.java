package com.rajesh.mvc;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rajesh.domain.Spitter;

@Controller
public class RegisterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
	@RequestMapping(value="/registerForm", method= RequestMethod.GET)
	public String showRegistrationForm(Model model) {
		System.out.println("Hello");
		model.addAttribute("spitter", new Spitter());
		return "registerForm";
	}
	
	@RequestMapping(value="/registerForm", method=RequestMethod.POST)
	public String register(@Valid Spitter spitter, Errors errors) {
		if (errors.hasErrors()) 
			return "registerForm";
		System.out.println("register Spitter is "+ spitter);
		return "home";
	}
}
