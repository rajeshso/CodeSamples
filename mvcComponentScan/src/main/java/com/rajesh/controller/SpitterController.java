package com.rajesh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rajesh.domain.Spitter;
import com.rajesh.domain.SpitterRepository;
import com.rajesh.domain.SpitterRepositoryImpl;

@Controller
@RequestMapping("/spitter")
public class SpitterController {
	// Use the following for testing
	//private SpitterRepository spitterRepository;

	// Use the following for deployment
	 private SpitterRepository spitterRepository = new SpitterRepositoryImpl();

	public SpitterController(SpitterRepository spitterRepository) {
		this.spitterRepository = spitterRepository;
	}

	/**
	 * 
	 */
	public SpitterController() {
		super();
	}

	// URL is /spitter/register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationForm() {
		return "registerForm";
	}

	// The Spitter object is automatically created
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistration(Spitter spitter) {
		spitterRepository.save(spitter);
		return "redirect:/spitter/" + spitter.getUserName();
	}

	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String showSpitterProfile(@PathVariable String username, Model model) {
		Spitter spitter = spitterRepository.findByUsername(username);
		model.addAttribute(spitter);
		return "profile";
	}
}
