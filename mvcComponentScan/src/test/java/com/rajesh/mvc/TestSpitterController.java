package com.rajesh.mvc;
import org.mockito.Mockito;


import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.rajesh.controller.SpitterController;
import com.rajesh.domain.Spitter;
import com.rajesh.domain.SpitterRepository;

public class TestSpitterController {

	@Test
	public void shouldShowRegistration() throws Exception {
		// Mock Spring MVC
		SpitterController spitterController = new SpitterController();
		StandaloneMockMvcBuilder standaloneMockBuilder = MockMvcBuilders
				.standaloneSetup(spitterController);
		MockMvc mockmvc = standaloneMockBuilder.build();		
		
		//Assert register from view
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spitter/register");
		ResultActions resultActions = mockmvc.perform(requestBuilder);
		resultActions.andExpect(MockMvcResultMatchers.view().name("registerForm"));
	}	

	@Test
	public void shouldProcessRegistration() throws Exception {
		TestSpittleRepository util = new TestSpittleRepository();
		//Create a Mock repository
		SpitterRepository mockRepository = Mockito.mock(SpitterRepository.class);
		Spitter unsaved = new Spitter("Raj", "Somasundaram", "rajsoma", "rajesh");
		Spitter saved = new Spitter("Raj", "Somasundaram", "rajsoma", "rajesh");
		Mockito.when(mockRepository.save(unsaved)).thenReturn(saved);
		
		//Create a mock SpitterController
		SpitterController spitterController = new SpitterController(mockRepository);
		StandaloneMockMvcBuilder standaloneMockBuilder = MockMvcBuilders
				.standaloneSetup(spitterController);
		MockMvc mockmvc = standaloneMockBuilder.build();	

		// POST spittles
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/spitter/register")
				.param("firstName","Raj")
				.param("lastName","Somasundaram")
				.param("userName","rajsoma")
				.param("password","rajesh");
		//Action
		ResultActions resultAction = mockmvc.perform(requestBuilder);
		//Assertion
		resultAction.andExpect(MockMvcResultMatchers.redirectedUrl("/spitter/rajesh"));
	}
}
