package com.sky.detector;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestSingleLineParser {
	@Rule 
	public ExpectedException exception = ExpectedException.none();

	@Test
	public final void testNullLine() throws InvalidInputException {
		exception.expect(InvalidInputException.class);
		exception.expectMessage("The Log line is empty");
		SingleLineParser parser = new SingleLineParser("");
		fail("Exception Expected");
	}

	@Test
	public final void testEmptyLine() throws InvalidInputException {
		exception.expect(InvalidInputException.class);
		exception.expectMessage("The Log format is incorrect");
		SingleLineParser parser = new SingleLineParser("              ");
		fail("Exception Expected");
	}

	@Test
	public final void testUnformattedLine() throws InvalidInputException {
		exception.expect(InvalidInputException.class);
		exception.expectMessage("The Log format is incorrect");
		SingleLineParser parser = new SingleLineParser("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		fail("Exception Expected");
	}

	@Test
	public final void testWithTooManyDelimiters() throws InvalidInputException {
		exception.expect(InvalidInputException.class);
		exception.expectMessage("The Log format is incorrect");
		SingleLineParser parser = new SingleLineParser("80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning, , , , , ");
		fail("Exception Expected");
	}

	@Test
	public final void testWithNoDelimiter() throws InvalidInputException {
		exception.expect(InvalidInputException.class);
		exception.expectMessage("The Log format is incorrect");
		SingleLineParser parser = new SingleLineParser("80.238.9.179 133612947 SIGNIN_SUCCESS Dave.Branning");
		fail("Exception Expected");
	}

	@Test
	public final void testWithIncorrectIP() throws InvalidInputException {
		exception.expect(InvalidInputException.class);
		exception.expectMessage("The IP format is incorrect");
		SingleLineParser parser = new SingleLineParser("a.b.c.d,133612947,SIGNIN_SUCCESS,Dave.Branning");
		fail("Exception Expected");
	}

	@Test
	public final void testWithIncorrectEventTime() throws InvalidInputException {
		exception.expect(InvalidInputException.class);
		exception.expectMessage("The Log format is incorrect");
		SingleLineParser parser = new SingleLineParser("80.238.9.179,12:30,SIGNIN_SUCCESS,Dave.Branning");
		fail("Exception Expected");
	}

	@Test
	public final void testWithTooLongEventTime() throws InvalidInputException {
		exception.expect(InvalidInputException.class);
		exception.expectMessage("The Log format is incorrect");
		SingleLineParser parser = new SingleLineParser(
				"80.238.9.179,133612947111111111111111111111111,SIGNIN_SUCCESS,Dave.Branning");
		fail("Exception Expected");
	}

	@Test
	public final void testWithNonNumberEventTime() throws InvalidInputException {
		exception.expect(InvalidInputException.class);
		exception.expectMessage("The Log format is incorrect");
		SingleLineParser parser = new SingleLineParser("80.238.9.179,a,SIGNIN_SUCCESS,Dave.Branning");
		fail("Exception Expected");
	}

	@Test
	public final void testWithEmptyAction() throws InvalidInputException {
		exception.expect(InvalidInputException.class);
		exception.expectMessage("The Log format is incorrect");
		SingleLineParser parser = new SingleLineParser("80.238.9.179,1336129471,  ,Dave.Branning");
		fail("Exception Expected");
	}

	@Test
	public final void testWithIncorrectFormatAction() {
		try {
			SingleLineParser parser = new SingleLineParser("80.238.9.179,133612947,SIGNIN_SUCCESS121212,Dave.Branning");
		} catch (InvalidInputException e) {
			fail("Exception not expected");
		}
	}

	@Test
	public final void testGetIP() {
		try {
			SingleLineParser parser = new SingleLineParser("80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(parser.getIP()).isEqualTo("80.238.9.179");
		} catch (InvalidInputException e) {
			fail("Exception not expected");
		}
	}

	@Test
	public final void testGetEventTime() {
		try {
			SingleLineParser parser = new SingleLineParser("80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(parser.getEventTime()).isEqualTo(133612947);
		} catch (InvalidInputException e) {
			fail("Exception not expected");
		}
	}

	@Test
	public final void testGetAction() {
		try {
			SingleLineParser parser = new SingleLineParser("80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning");
			assertThat(parser.getAction()).isEqualTo("SIGNIN_SUCCESS");
		} catch (InvalidInputException e) {
			fail("Exception not expected");
		}
	}
}
