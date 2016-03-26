package mvcAnnotationOnly;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rajesh.controller.HomeController;

public class TestHomeController {

	@Test
	public final void testHomeString() {
		HomeController homeController = new HomeController();
		assertEquals("home", homeController.home());
	}
	
	@Test
	public final void testHomeView() throws Exception {
		HomeController homeController = new HomeController();
		MockMvc mockMVC = MockMvcBuilders.standaloneSetup(homeController).build();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/"); //Perform a simple get
		ResultMatcher resultMatcher =  MockMvcResultMatchers.view().name("home"); 
		ResultActions resultAction = mockMVC.perform(requestBuilder);
		resultAction.andExpect(resultMatcher);
	}

}
