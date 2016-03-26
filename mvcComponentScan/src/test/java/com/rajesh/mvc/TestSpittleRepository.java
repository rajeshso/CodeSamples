package com.rajesh.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.servlet.view.InternalResourceView;

import com.rajesh.controller.SpittleController;
import com.rajesh.domain.Spittle;
import com.rajesh.domain.SpittleRepository;

@WebAppConfiguration
public class TestSpittleRepository {

	@Test
	public final void showSpittles() throws Exception {
		// Mock Repository
		List<Spittle> expectedSpittles = createSpittlesList(10);
		SpittleRepository mockSpittleRepository = createMockSpittleRepository(Long.MAX_VALUE,expectedSpittles);

		//Mock Spring MVC
		MockMvc mockmvc = getMockMvc(mockSpittleRepository, "/WEB-INF/views/spittles.jsp");

		// GET spittles
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/spittles.jsp");
		ResultActions resultAction = mockmvc.perform(requestBuilder);

		// Assert expectations
		resultAction
				.andExpect(MockMvcResultMatchers.view().name("spittles"))
				.andExpect(
						MockMvcResultMatchers.model().attributeExists(
								"spittleList"))
				.andExpect(
						MockMvcResultMatchers.model().attribute("spittleList",
								Matchers.hasItems(expectedSpittles.toArray())));
	}

	@Test
	public void shouldShowPagedSpittles() throws Exception {
		// Mock Repository
		List<Spittle> expectedSpittles = createSpittlesList(50);
		SpittleRepository mockSpittleRepository = createMockSpittleRepository(238900L, expectedSpittles);
		
		// Mock Spring MVC
		MockMvc mockmvc = getMockMvc(mockSpittleRepository, "/WEB-INF/views/spittles.jsp");
		
		// GET spittles with Query Parameters
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/spittles.jsp?max=238900&count=50");
		ResultActions resultAction = mockmvc.perform(requestBuilder);
		
		// Assert expectations
		resultAction
				.andExpect(MockMvcResultMatchers.view().name("spittles"))
				.andExpect(
						MockMvcResultMatchers.model().attributeExists(
								"spittleList"))
				.andExpect(
						MockMvcResultMatchers.model().attribute("spittleList",
								Matchers.hasItems(expectedSpittles.toArray())));		
	}

	@Test
	public void shouldShowSpittle() throws Exception {
		// Mock Repository
		Spittle expectedSpittle = createSpittle();
		SpittleRepository mockSpittleRepository = createMockSpittleRepositoryForSingleSpittle(
				 expectedSpittle);

		// Mock Spring MVC
		MockMvc mockmvc = getMockMvc(mockSpittleRepository,
				"/WEB-INF/views/spittles.jsp");

		// GET spittles with Query Parameters
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/spittles/12345");
		ResultActions resultAction = mockmvc.perform(requestBuilder);

		// Assert expectations
		resultAction
				.andExpect(MockMvcResultMatchers.view().name("spittle"))
				.andExpect(
						MockMvcResultMatchers.model()
								.attributeExists("spittle"))
				.andExpect(
						MockMvcResultMatchers.model().attribute("spittle",
								expectedSpittle));
	}
	


	private SpittleRepository createMockSpittleRepositoryForSingleSpittle(
			Spittle expectedSpittle) {
		SpittleRepository mockSpittleRepository = Mockito
				.mock(SpittleRepository.class);
		Mockito.when(mockSpittleRepository.findOneSpittle(12345))
				.thenReturn(expectedSpittle);
		return mockSpittleRepository;
	}

	private SpittleRepository createMockSpittleRepository(
			Long maxID, List<Spittle> expectedSpittles) {
		SpittleRepository mockSpittleRepository = Mockito
				.mock(SpittleRepository.class);
		Mockito.when(mockSpittleRepository.findSpittles(maxID, expectedSpittles.size()))
				.thenReturn(expectedSpittles);
		return mockSpittleRepository;
	}
	
	private MockMvc getMockMvc(SpittleRepository mockSpittleRepository, String viewName) {
		SpittleController spittleController = new SpittleController(
				mockSpittleRepository);
		InternalResourceView view = new InternalResourceView(
				viewName);
		StandaloneMockMvcBuilder standaloneMockBuilder = MockMvcBuilders
				.standaloneSetup(spittleController);
		StandaloneMockMvcBuilder standaloneMockBuilderView = standaloneMockBuilder
				.setSingleView(view);
		MockMvc mockmvc = standaloneMockBuilderView.build();
		return mockmvc;
	}
	private MockMvc getMockMvc(String viewName) {
		SpittleController spittleController = new SpittleController();
		InternalResourceView view = new InternalResourceView(
				viewName);
		StandaloneMockMvcBuilder standaloneMockBuilder = MockMvcBuilders
				.standaloneSetup(spittleController);
		StandaloneMockMvcBuilder standaloneMockBuilderView = standaloneMockBuilder
				.setSingleView(view);
		MockMvc mockmvc = standaloneMockBuilderView.build();
		return mockmvc;
	}	

	public List<Spittle> createSpittlesList(int count) {
		List<Spittle> spittleList = new ArrayList<Spittle>();
		for (int i = 0; i < count; i++) {
			spittleList.add(new Spittle(Double.toString(Math.random()),
					new Date()));
		}
		return spittleList;
	}
	public Spittle createSpittle() {
		return new Spittle("12345", new Date());
	}	
}
