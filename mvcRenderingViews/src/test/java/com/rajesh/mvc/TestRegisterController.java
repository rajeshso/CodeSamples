package com.rajesh.mvc;

import static org.junit.Assert.*;

import org.mockito.Mockito;


import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.servlet.view.InternalResourceView;

public class TestRegisterController {

	@Test
	public final void shouldShowRegistrationForm() throws Exception {
		// Mock Spring MVC
		MockMvc mockmvc = this.getMockMvc("/WEB-INF/views/registerForm.jsp");
		
		//Assert register from view
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/registerForm");
		ResultActions resultActions = mockmvc.perform(requestBuilder);
		resultActions.andExpect(MockMvcResultMatchers.view().name("registerForm"));
	}
	private MockMvc getMockMvc(String viewName) {
		RegisterController registerController = new RegisterController();
		InternalResourceView view = new InternalResourceView(
				viewName);
		StandaloneMockMvcBuilder standaloneMockBuilder = MockMvcBuilders
				.standaloneSetup(registerController);
		StandaloneMockMvcBuilder standaloneMockBuilderView = standaloneMockBuilder
				.setSingleView(view);
		MockMvc mockmvc = standaloneMockBuilderView.build();
		return mockmvc;
	}
}
