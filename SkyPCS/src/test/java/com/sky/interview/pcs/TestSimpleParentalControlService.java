package com.sky.interview.pcs;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestSimpleParentalControlService {
	private static final String MOVIE_META_DATA_FILE_NAME = "/src/main/resources/movieMetaData.properties";
	private static final String PC_LEVEL_META_DATA_FILE_NAME = "/src/main/resources/parentalControlLevel.properties";
	private static SimpleParentalControlService pcs;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String rootPath = new File("").getAbsolutePath();
		String moviemetadataAbsoluteFileName = rootPath + MOVIE_META_DATA_FILE_NAME;
		String pcLevelmetadataAbsoluteFileName = rootPath + PC_LEVEL_META_DATA_FILE_NAME;
		pcs = new SimpleParentalControlService(pcLevelmetadataAbsoluteFileName, moviemetadataAbsoluteFileName);
	}

	@Test
	public final void testSimpleParentalControlServiceIncorrectInput() {
		try {
			pcs = new SimpleParentalControlService("ABC", "XYZ");
			fail("Exception expected for incorrect meta data file names");
		} catch (TechnicalFailureException e) {
			assertTrue(e.getMessage().contains("configuration is incorrect"));
		} catch (Exception e) {
			fail("Incorrect Exception is thrown");
		}
	}

	@Test
	public final void testIsViewPermittedForHigherPCLevel() {
		try {
			assertTrue(pcs.isViewPermitted("18", "Gold Finger"));
		} catch (IncorrectInputException | TitleNotFoundException | TechnicalFailureException e) {
			fail("Exception is not expected for a regular use case");
		}
	}

	@Test
	public final void testIsViewPermittedForEqualPCLevel() {
		try {
			assertTrue(pcs.isViewPermitted("15", "Thunder Ball"));
		} catch (IncorrectInputException | TitleNotFoundException | TechnicalFailureException e) {
			fail("Exception is not expected for a regular use case");
		}
	}

	@Test
	public final void testIsViewPermittedForLowerPCLevel() {
		try {
			assertFalse(pcs.isViewPermitted("12", "Thunder Ball"));
		} catch (IncorrectInputException | TitleNotFoundException | TechnicalFailureException e) {
			fail("Exception is not expected for a regular use case");
		}
	}
}
