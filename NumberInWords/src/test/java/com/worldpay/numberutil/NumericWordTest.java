package com.worldpay.numberutil;

import static java.text.NumberFormat.getCurrencyInstance;
import static java.util.Locale.UK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import com.worldpay.numberutil.NumericWord;

/**
 * Refer the British English spelling for numbers by checking your digits in this link
 * http://www.calculator.org/calculate-online/mathematics/text-number.aspx
 * 
 * @author Rajesh
 *
 */
public class NumericWordTest {

	@Test
	public void test1() {
		try {
			int n = 1;
			String words = NumericWord.convert(n);
			assertEquals("One", words);
		} catch (Exception e) {
			fail("Incorrect validation : " + e.getMessage());
		}
	}

	@Ignore
	void test9999To20000() {
		for (int n = 9999; n < 20000; n++) {
			try {
				String words = NumericWord.convert(n);
				print(n, words);
			} catch (Exception e) {
				fail("Incorrect Validation : " + e.getMessage());
			}

		}
	}

	@Test
	public void testIsNumeric() {
		try {
			NumericWord.convert(1000000000);
			fail("The validation failed");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testEmpty() {
		try {
			NumericWord.convert(-1);
			fail("The validation failed");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testHexaDecimal() {
		try {
			String actual = NumericWord.convert(0x123);
			assertEquals("Two Hundred and Ninety-One", actual);
		} catch (Exception e) {
			fail("Not expected");
		}
	}

	@Test
	public void testNegativeInteger() {
		try {
			int n = -123;
			NumericWord.convert(n);
			fail("The validation failed");
		} catch (Exception e) {
			assertEquals("-123 out of range.", e.getMessage());
		}
	}


	@Test
	public void test0() {
		int n=0;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Zero", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test000000100() {
		int n = 000000100;//Octal is 64
		try {
			String words = NumericWord.convert(n);
			assertEquals("Sixty-Four", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void testTeen() {
		int n = 16;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Sixteen", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}
	
	@Test
	public void test20() {
		int n = 20;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Twenty", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test21() {
		int n = 21;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Twenty-One", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test99() {
		int n = 99;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Ninety-Nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test100() {
		int n = 100;
		try {
			String words = NumericWord.convert(n);
			assertEquals("One Hundred", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test101() {
		int n = 101;
		try {
			String words = NumericWord.convert(n);
			assertEquals("One Hundred and One", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test120() {
		int n = 120;
		try {
			String words = NumericWord.convert(n);
			assertEquals("One Hundred and Twenty", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test999() {
		int n = 999;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Nine Hundred and Ninety-Nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test1000() {
		int n = 1000;
		try {
			String words = NumericWord.convert(n);
			assertEquals("One Thousand ", words);
		} catch (Exception e) {

			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test1001() {
		int n = 1001;
		try {
			String words = NumericWord.convert(n);
			assertEquals("One Thousand , One", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test1099() {
		int n = 1099;
		try {
			String words = NumericWord.convert(n);
			assertEquals("One Thousand , Ninety-Nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test1100() {
		int n = 1100;
		try {
			String words = NumericWord.convert(n);
			assertEquals("One Thousand , One Hundred", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test1999() {
		int n = 1999;
		try {
			String words = NumericWord.convert(n);
			assertEquals("One Thousand , Nine Hundred and Ninety-Nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test2000() {
		int n = 2000;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Two Thousand ", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test2001() {
		int n = 2001;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Two Thousand , One", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test2999() {
		int n = 2999;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Two Thousand , Nine Hundred and Ninety-Nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test2100() {
		int n = 2100;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Two Thousand , One Hundred", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test9999() {
		int n = 9999;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Nine Thousand , Nine Hundred and Ninety-Nine", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test99999() {
		int n = 99999;
		try {
			String words = NumericWord.convert(n);
			assertEquals("Ninety-Nine Thousand , Nine Hundred and Ninety-Nine",
					words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test999000() {
		try {
			String words = NumericWord.convert(999000);
			assertEquals("Nine Hundred and Ninety-Nine Thousand ", words);
		} catch (Exception e) {
			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test999998() {
		try {
			String words = NumericWord.convert(999998);
			assertEquals(
					"Nine Hundred and Ninety-Nine Thousand , Nine Hundred and Ninety-Eight",
					words);
		} catch (Exception e) {

			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void test999999() {
		try {
			String words = NumericWord.convert(999999);
			assertEquals(
					"Nine Hundred and Ninety-Nine Thousand , Nine Hundred and Ninety-Nine",
					words);
		} catch (Exception e) {

			fail("The validation failed with " + e.getMessage());
		}
	}
	
	//999,999,999
	@Test
	public void test999999999() {
		try {
			String words = NumericWord.convert(999999999);
			assertEquals(
					"Nine Hundred and Ninety-Nine Million , Nine Hundred and Ninety-Nine Thousand , Nine Hundred and Ninety-Nine",
					words);
		} catch (Exception e) {

			fail("The validation failed with " + e.getMessage());
		}
	}

	@Test
	public void test100000() {
		try {
			String words = NumericWord.convert(100000);
			assertEquals("One Hundred Thousand ", words);
		} catch (Exception e) {

			fail("The validation failed with " + e.getMessage());
		}

	}

	@Test
	public void testOutOfRangeHigh() {
		try {
			NumericWord.convert(1000000000);
			fail("1000000000 should be out of range");
		} catch (Exception e) {
			assertEquals("1000000000 out of range." , e.getMessage());
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

}
