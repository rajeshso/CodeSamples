package com.trurating.util;

import static com.trurating.util.NumericWord.convert;
import static java.text.NumberFormat.getCurrencyInstance;
import static java.util.Locale.UK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.trurating.touchpoint.NumberToWordConverter;

/**
 * Test of print methods, of class NumericWord.
 *
 * @author Rajesh
 */
public class NumericWordTest {

	public NumericWordTest() {
	}

	@Test
	public void test1() {
		String nString = "1";
		try {
			int n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one", words);
		} catch (Exception e) {
			fail("Incorrect validation : " + e.getMessage());
		}
	}

	@Ignore
	void test9999To20000() {
		String nString = null;
		for (int n = 9999; n < 20000; n++) {
			try {
				String words = convert(n);
				print(n, words);
			} catch (Exception e) {
				fail("Incorrect Validation : " + e.getMessage());
			}

		}
	}

	
	

	@Test
	public void testIsNumeric() {
		try {
			String nString = "10000000000000000000000000000000000000000000000000000000000000000000000000000";
			NumericWord.isNumeric(nString);
			fail("The validation failed");
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void testEmpty() {
		try {
			String nString = " ";
			convertToInt(nString);
			fail("The validation failed");
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testDecimal() {
		try {
			String nString = "123.23";
			convertToInt(nString);
			fail("The validation failed");
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testHexaDecimal() {
		try {
			String nString = "0x123";
			convertToInt(nString);
			fail("The validation failed");
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testNegativeInteger() {
		try {
			String nString = "-123";
			convertToInt(nString);
			fail("The validation failed");
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testString() {
		try {
			String nString = "Rating";
			convertToInt(nString);
			fail("The validation failed");
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void test0() {
		String nString = "0";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" zero", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test000000100() {
		String nString = "000000100";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one hundred", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test16() {
		String nString = "16";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" sixteen", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}
	
	@Test
	public void test20() {
		String nString = "20";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" twenty", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test21() {
		String nString = "21";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" twenty-one", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test99() {
		String nString = "99";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" ninety-nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test100() {
		String nString = "100";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one hundred", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test101() {
		String nString = "101";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one hundred and one", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test120() {
		String nString = "120";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one hundred and twenty", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test999() {
		String nString = "999";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" nine hundred and ninety-nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test1000() {
		String nString = "1000";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one thousand", words);
		} catch (Exception e) {

			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test1001() {
		String nString = "1001";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one thousand and one", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test1099() {
		String nString = "1099";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one thousand and ninety-nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test1100() {
		String nString = "1100";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one thousand, one hundred", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test1999() {
		String nString = "1999";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one thousand, nine hundred and ninety-nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test2000() {
		String nString = "2000";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" two thousand", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test2001() {
		String nString = "2001";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" two thousand and one", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test2999() {
		String nString = "2999";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" two thousand, nine hundred and ninety-nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test2100() {
		String nString = "2100";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" two thousand, one hundred", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test9999() {
		String nString = "9999";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" nine thousand, nine hundred and ninety-nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test99999() {
		String nString = "99999";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" ninety-nine thousand, nine hundred and ninety-nine",
					words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test999000() {
		String nString = "999000";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" nine hundred and ninety-nine thousand", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test999998() {
		String nString = "999998";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(
					" nine hundred and ninety-nine thousand, nine hundred and ninety-eight",
					words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test999998WithComma() {
		String nString = "999,998";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(
					" nine hundred and ninety-nine thousand, nine hundred and ninety-eight",
					words);
		} catch (Exception e) {

			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test999999() {
		String nString = "999999";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(
					" nine hundred and ninety-nine thousand, nine hundred and ninety-nine",
					words);
		} catch (Exception e) {

			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test100000() {
		String nString = "100000";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" one hundred thousand", words);
		} catch (Exception e) {

			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test900000() {
		String nString = "900000";
		int n;
		try {
			n = convertToInt(nString);
			String words = NumericWord.convert(n);
			assertEquals(" nine hundred thousand", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	/**
	 * Test of main method, of class NumericWord.
	 */
	@Test
	public void testMain() {
		String[] args = { "100" };
		try {
			NumberToWordConverter.main(args);
		} catch (Exception e) {
			fail("The main method is incorrect");
		}
	}

	private void print(int n, String nWords) {
		if (nWords != null)
			System.out.printf("%s in words is%s %s %n", getCurrencyInstance(UK)
					.format(n), nWords, getCurrencyInstance(UK).getCurrency()
					.getDisplayName());
		else
			System.out.printf("%d in words is not supported %n", n);
	}

	private int convertToInt(String nString) throws Exception {
		nString = NumericWord.replaceComma(nString);
		int n = 0;
		if (!NumericWord.isNumeric(nString)) {
			throw new Exception(
					"Invalid Input : input is not a positive integer");
		} else {
			n = Integer.parseInt(nString);
		}
		return n;
	}

}
