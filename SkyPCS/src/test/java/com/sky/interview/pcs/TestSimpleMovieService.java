package com.sky.interview.pcs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestSimpleMovieService {
	private static final String MOVIE_META_DATA_FILE_NAME = "/src/main/resources/movieMetaData.properties";
	private static SimpleMovieService movieService;

	@BeforeClass
	public static void setUp() throws Exception {
		String rootPath = new File("").getAbsolutePath();
		String metadataAbsoluteFileName = rootPath + MOVIE_META_DATA_FILE_NAME;
		movieService = new SimpleMovieService(metadataAbsoluteFileName);
	}

	@Test
	public final void testSimpleMovieService() {
		String rootPath = new File("").getAbsolutePath();
		String metadataAbsoluteFileName = rootPath + MOVIE_META_DATA_FILE_NAME;
		try {
			SimpleMovieService sms = new SimpleMovieService(metadataAbsoluteFileName);
			assertNotNull(sms);
		} catch (TechnicalFailureException e) {
			fail("Exception is not expected in positive initialization");
		}
	}

	@Test
	public final void testSimpleMovieServiceWithIncorrectInput() {
		try {
			SimpleMovieService sms = new SimpleMovieService("ABC.XYZ");
			fail("Exception is expected in negative initialization");
		} catch (TechnicalFailureException e) {
			assertTrue(e.getMessage().contains("configuration is incorrect"));
		} catch (Exception e) {
			fail("Incorrect Exception thrown");
		}
	}

	@Test
	public final void testGetParentalControlLevel() {
		String pc;
		try {
			pc = movieService.getParentalControlLevel("Casino Royale");
			assertEquals("U", pc);
		} catch (TitleNotFoundException | TechnicalFailureException e) {
			fail("Exception is not expected in positive test case");
		}
	}

	@Test
	public final void testGetParentalControlLevelIncorrectTitle() {
		try {
			String pc = movieService.getParentalControlLevel("For your Eyes Only");
			fail("Exception is expected for incorrect title");
		} catch (TitleNotFoundException e) {
			assertTrue(e.getMessage().contains("For your Eyes Only not available"));
		} catch (TechnicalFailureException e) {
			fail("TechnicalFailureException is Not expected for incorrect title");
		}
	}

}
