package com.sky.detector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class TestSimpleHackerDetector {
	private static final String DETECTOR_CONFIG_FILE_NAME = "/src/main/resources/lms.properties";
	private static HackerDetector hackerDetector;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testSimpleHackerDetector() {
		try {
			String rootPath = new File("").getAbsolutePath();
			String metadataAbsoluteFileName = rootPath + DETECTOR_CONFIG_FILE_NAME;
			SigninStore signinStore = getPreFilledStore();
			hackerDetector = new SimpleHackerDetector(metadataAbsoluteFileName, signinStore);
			String result = hackerDetector.parseLine("80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(result).isNull();
		} catch (InvalidInputException e) {
			fail("Exception Not Expected");
		}
	}

	@Test
	public final void testParseLineWithConsecutiveSuccess() {
		try {
			String rootPath = new File("").getAbsolutePath();
			String metadataAbsoluteFileName = rootPath + DETECTOR_CONFIG_FILE_NAME;
			SigninStore signinStore = getPreFilledStore();
			hackerDetector = new SimpleHackerDetector(metadataAbsoluteFileName, signinStore);
			String result = hackerDetector.parseLine("80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612948,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(result).isNull();
			result = hackerDetector.parseLine("81.238.9.179,133612949,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(result).isNull();
			result = hackerDetector.parseLine("82.238.9.179,133612910,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(result).isNull();
			result = hackerDetector.parseLine("83.238.9.179,133612911,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(result).isNull();
			result = hackerDetector.parseLine("84.238.9.179,133612912,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(result).isNull();
			result = hackerDetector.parseLine("85.238.9.179,133612913,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(result).isNull();
		} catch (InvalidInputException e) {
			fail("Exception Not Expected");
		}
	}

	@Test
	public final void testParseLineWithConsecutiveFailure() {
		try {
			String rootPath = new File("").getAbsolutePath();
			String metadataAbsoluteFileName = rootPath + DETECTOR_CONFIG_FILE_NAME;
			SigninStore signinStore = new InMemorySignInStore();
			hackerDetector = new SimpleHackerDetector(metadataAbsoluteFileName, signinStore);
			String result = hackerDetector.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning");// 1
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning");// 2
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612949,SIGNIN_FAILURE,Dave.Branning");// 3
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612950,SIGNIN_FAILURE,Dave.Branning");// 4
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612951,SIGNIN_FAILURE,Dave.Branning");// 5
			assertThat(result).isEqualTo("80.238.9.179");
		} catch (InvalidInputException e) {
			fail("Exception Not Expected");
		}
	}

	@Test
	public final void testParseLineWithUpsAndDowns() {
		try {
			String rootPath = new File("").getAbsolutePath();
			String metadataAbsoluteFileName = rootPath + DETECTOR_CONFIG_FILE_NAME;
			SigninStore signinStore = new InMemorySignInStore();
			hackerDetector = new SimpleHackerDetector(metadataAbsoluteFileName, signinStore);
			String result = hackerDetector.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning");// 1
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning");// 2
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612949,SIGNIN_FAILURE,Dave.Branning");// 3
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612950,SIGNIN_FAILURE,Dave.Branning");// 4
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612951,SIGNIN_SUCCESS,Dave.Branning");// Success
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning");// 5
			assertThat(result).isEqualTo("80.238.9.179");
		} catch (InvalidInputException e) {
			fail("Exception Not Expected");
		}
	}

	@Test
	public final void testPermittedFailure() {
		try {
			String rootPath = new File("").getAbsolutePath();
			String metadataAbsoluteFileName = rootPath + DETECTOR_CONFIG_FILE_NAME;
			SigninStore signinStore = new InMemorySignInStore();
			hackerDetector = new SimpleHackerDetector(metadataAbsoluteFileName, signinStore);
			String result = hackerDetector.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning");// 1
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning");// 2
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612949,SIGNIN_FAILURE,Dave.Branning");// 3
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612950,SIGNIN_FAILURE,Dave.Branning");// 4
			assertThat(result).isNull();
			result = hackerDetector.parseLine("80.238.9.179,133612951,SIGNIN_SUCCESS,Dave.Branning");// Success
			assertThat(result).isNull();
			result = hackerDetector.parseLine("81.238.9.179,133612952,SIGNIN_SUCCESS,Dave.Branning");// Success
			assertThat(result).isNull();
			Calendar c = Calendar.getInstance();
			c.set(2015, 1, 26, 10, 30, 0);
			result = hackerDetector
					.parseLine("80.238.9.179," + c.getTime().getTime() + ",SIGNIN_FAILURE,Dave.Branning");// 5
			assertThat(result).isNull();
		} catch (InvalidInputException e) {
			fail("Exception Not Expected");
		}
	}

	private SigninStore getPreFilledStore() {
		InMemorySignInStore store = new InMemorySignInStore();
		Calendar c = Calendar.getInstance();
		c.set(2010, 1, 26, 10, 30, 0);
		store.add(c.getTime().getTime(), "80.238.9.179");
		c.set(2010, 1, 26, 10, 31, 0);
		store.add(c.getTime().getTime(), "80.238.9.179");
		c.set(2010, 1, 26, 10, 32, 0);
		store.add(c.getTime().getTime(), "80.238.9.179");
		c.set(2010, 1, 26, 10, 33, 0);
		store.add(c.getTime().getTime(), "80.238.9.179");
		c.set(2010, 1, 26, 10, 34, 0);
		store.add(c.getTime().getTime(), "80.238.9.179");
		c.set(2010, 1, 26, 10, 35, 0);
		store.add(c.getTime().getTime(), "80.238.9.179");
		c.set(2010, 1, 26, 10, 36, 0);
		store.add(c.getTime().getTime(), "80.238.9.179");
		c.set(2010, 1, 26, 10, 37, 0);
		store.add(c.getTime().getTime(), "80.238.9.179");
		c.set(2010, 1, 26, 10, 38, 0);
		store.add(c.getTime().getTime(), "99.999.9.999");
		return store;
	}
}
