package biz.tlg.javatest.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Before;
import org.junit.Test;

import biz.tlg.javatest.JavaTest;

//import biz.tlg.javatest.utils.Utils;

/**
 * Some example Unit tests for the Utils class
 * 
 */
public class UtilsTest {

	/**
	 * Create a test document for using in the unit tests
	 */
	private Document testDoc1 = null;
	private Document testDocWithSimpleMessage = null;
	private Document testDocWithComplexMessage = null;
	private Document testDocWith3Tokens = null;

	@Before
	public void setup() {
		StringBuilder s = new StringBuilder("");
		s.append("<RLSOLVE_MSG>");
		s.append("	<TEST_ELEMENT testAttribute=\"testValue\" />");
		s.append("</RLSOLVE_MSG>");
		testDoc1 = createDocument(s.toString());

		s = new StringBuilder("");
		s.append("<RLSOLVE_MSG>");
		s.append("	<CARD>");
		s.append("		<PAN end=\"2016-12\">4444333322221111</PAN>");
		s.append("	</CARD>");
		s.append("	<TRANSACTION type=\"refund\" customer=\"present\" time=\"10:55:44\" >");
		s.append("		<AMOUNT>2000</AMOUNT>");
		s.append("	</TRANSACTION>");
		s.append("</RLSOLVE_MSG>   ");
		testDocWithSimpleMessage = createDocument(s.toString());

		s = new StringBuilder("");
		s.append("<RLSOLVE_MSG>");
		s.append("	<CARD>");
		s.append("    <PAN end=\"2016-12\">4111111111111111</PAN>");
		s.append("    <TRACK2>;476173******0010=*****************?4</TRACK2>");
		s.append(" 	  <TOKENS>");
		s.append("		<TOKEN origin=\"central\">476173bbbbbbbgf0010</TOKEN>");
		s.append("	  </TOKENS>");
		s.append("	</CARD>");
		s.append("	<TRANSACTION type=\"purchase\" customer=\"present\" time=\"10:53:30\" >");
		s.append(" 		<AMOUNT>15000</AMOUNT>");
		s.append("	</TRANSACTION>");
		s.append("</RLSOLVE_MSG>");
		testDocWithComplexMessage = createDocument(s.toString());

		s = new StringBuilder("");
		s.append("<RLSOLVE_MSG>");
		s.append("	<CARD>");
		s.append("    <PAN end=\"2016-12\">4111111111111111</PAN>");
		s.append("    <TRACK2>;476173******0010=*****************?4</TRACK2>");
		s.append(" 	  <TOKENS>");
		s.append("		<TOKEN origin=\"central\">476173bbbbbbbgf0010</TOKEN>");
		s.append("		<TOKEN origin=\"left\">576173bbbbbbbgf0010</TOKEN>");
		s.append("		<TOKEN origin=\"right\">676173bbbbbbbgf0010</TOKEN>");
		s.append("	  </TOKENS>");
		s.append("	</CARD>");
		s.append("	<TRANSACTION type=\"purchase\" customer=\"present\" time=\"10:53:30\" >");
		s.append(" 		<AMOUNT>15000</AMOUNT>");
		s.append("	</TRANSACTION>");
		s.append("</RLSOLVE_MSG>");
		testDocWith3Tokens = createDocument(s.toString());
	}

	/**
	 * Useful utility class used in test classes, converts a string
	 * representation into a JDOM document and calls JUnit fail if any
	 * exceptions are thrown
	 * 
	 */
	public static Document createDocument(String input) {

		try {
			return new SAXBuilder().build(new StringReader(input));
		} catch (JDOMException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return null;
	}

	/**
	 * Ensure an IllegalArgumentException is thrown when the input is null
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateDocNullString() throws JDOMException, IOException {
		Utils.createDocument(null);
		fail();
	}

	/**
	 * Test that an empty String results in a JDOMException being thrown
	 */
	@Test
	public void testCreateDocEmptyStringDoc() {
		try {
			Utils.createDocument("");
			fail("The JDOMException is expected");
		} catch (JDOMException e) {
			assertTrue(true);
		} catch (IOException e) {
			assertTrue(true);
		}
	}

	/**
	 * Test a simple String being converted to a Document
	 */
	@Test
	public void testCreateDocValidString() {
		/* Our test input */
		String input = "<RLSOLVE_MSG><TEST_ELEMENT testAttribute=\"testValue\" /></RLSOLVE_MSG>";

		Document actual;
		try {
			actual = Utils.createDocument(input);
			XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat()
					.setOmitDeclaration(true));

			/* Compare the expected to actual as Strings as it is easier */
			assertEquals(outputter.outputString(testDoc1),
					outputter.outputString(actual));
		} catch (JDOMException | IOException e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Make sure an IllegalArgumentException is thrown when a null Document is
	 * passed in
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetElementNullDoc() {
		try {
			Utils.getElement(null, null);
			fail();
		} catch (JDOMException e) {
			fail();
		}
	}

	/**
	 * Make sure an IllegalArgumentException is thrown when a null XPath is
	 * passed in
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetElementNullXPath() {
		try {
			Utils.getElement(testDoc1, null);
			fail();
		} catch (JDOMException e) {
			fail();
		}

	}

	/**
	 * Make sure an JDOMException is thrown when an empty XPath is passed in
	 */
	@Test
	public void testGetElementEmptyXPath() {

		try {
			Utils.getElement(testDoc1, "");
			fail();
		} catch (JDOMException e) {
			assertTrue(true);
		}

	}

	/**
	 * Make sure the TEST_ELEMENT Element is returned when it exists in the JDOM
	 * document
	 */
	@Test
	public void testGetElementEmptyXPathReturned() {
		Element testElem = testDoc1.getRootElement().getChild("TEST_ELEMENT");
		try {
			assertEquals(testElem,
					Utils.getElement(testDoc1, "//RLSOLVE_MSG/TEST_ELEMENT"));
		} catch (JDOMException e) {
			fail();
		}

	}

	/**
	 * Make sure an IllegalArgumentException is thrown when a null JDOM Document
	 * is passed in
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeNullDoc() {

		try {
			Utils.getAttribute(null, null);
			fail();
		} catch (JDOMException e) {
			fail();
		}

	}

	/**
	 * Make sure an IllegalArgumentException is thrown when a null XPath is
	 * passed in
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeNullXPath() {

		try {
			Utils.getAttribute(testDoc1, null);
			fail();
		} catch (JDOMException e) {
			fail();
		}

	}

	/**
	 * Make sure an JDOMException is thrown when an empty XPath is passed in
	 */
	@Test
	public void testGetAttributeNullReturned() {

		try {
			assertEquals(null, Utils.getAttribute(testDoc1, ""));
			fail();
		} catch (JDOMException e) {
			assertTrue(true);
		}

	}

	/**
	 * Make sure the testAttribute Attribute is returned when it exists in the
	 * JDOM document
	 */
	@Test
	public void testGetAttributeSuccess() {

		Attribute testElem = testDoc1.getRootElement().getChild("TEST_ELEMENT")
				.getAttribute("testAttribute");

		try {
			assertEquals(testElem, Utils.getAttribute(testDoc1,
					"//RLSOLVE_MSG/TEST_ELEMENT/@testAttribute"));
		} catch (JDOMException e) {
			fail();
		}
	}

	@Test
	public void testXPathExpressionsForSimpleMessage() {
		try {
			assertEquals("2016-12",
					Utils.getElement(testDocWithSimpleMessage, "//RLSOLVE_MSG/CARD/PAN")
							.getAttributeValue("end"));
			assertEquals("4444333322221111",
					Utils.getElement(testDocWithSimpleMessage, "//RLSOLVE_MSG/CARD/PAN")
							.getValue());
			assertEquals("refund",
					Utils.getElement(testDocWithSimpleMessage, "//RLSOLVE_MSG/TRANSACTION")
							.getAttributeValue("type"));
			assertEquals("present",
					Utils.getElement(testDocWithSimpleMessage, "//RLSOLVE_MSG/TRANSACTION")
							.getAttributeValue("customer"));
			assertEquals("10:55:44",
					Utils.getElement(testDocWithSimpleMessage, "//RLSOLVE_MSG/TRANSACTION")
							.getAttributeValue("time"));
			assertEquals(
					"2000",
					Utils.getElement(testDocWithSimpleMessage,
							"//RLSOLVE_MSG/TRANSACTION//AMOUNT").getValue());
		
		} catch (JDOMException e) {
			fail("The XPath expressions failed");
		}
	}

	@Test
	public void testXPathExpressionsForComplexMessage() {
		try {
			assertEquals("2016-12",
					Utils.getElement(testDocWithComplexMessage, "//RLSOLVE_MSG/CARD/PAN")
							.getAttributeValue("end"));
			assertEquals("4111111111111111",
					Utils.getElement(testDocWithComplexMessage, "//RLSOLVE_MSG/CARD/PAN")
							.getValue());
			assertEquals(";476173******0010=*****************?4", Utils
					.getElement(testDocWithComplexMessage, "//RLSOLVE_MSG/CARD/TRACK2")
					.getValue());
			assertEquals(1,
					Utils.getElement(testDocWithComplexMessage, "//RLSOLVE_MSG/CARD/TOKENS")
							.getChildren().size());
			List tokenList = Utils.getElement(testDocWithComplexMessage,
					"//RLSOLVE_MSG/CARD/TOKENS").getChildren();
			Iterator tokenListIterator = tokenList.iterator();
			while (tokenListIterator.hasNext()) {// tokenListIterator size is one
				Element tokenElement = (Element) tokenListIterator.next();
				assertEquals(
						"central",
						tokenElement
								.getAttributeValue("origin"));
				assertEquals(
						"476173bbbbbbbgf0010",
						tokenElement.getValue());
			}

			assertEquals("purchase",
					Utils.getElement(testDocWithComplexMessage, "//RLSOLVE_MSG/TRANSACTION")
							.getAttributeValue("type"));
			assertEquals("present",
					Utils.getElement(testDocWithComplexMessage, "//RLSOLVE_MSG/TRANSACTION")
							.getAttributeValue("customer"));
			assertEquals("10:53:30",
					Utils.getElement(testDocWithComplexMessage, "//RLSOLVE_MSG/TRANSACTION")
							.getAttributeValue("time"));
			assertEquals(
					"15000",
					Utils.getElement(testDocWithComplexMessage,
							"//RLSOLVE_MSG/TRANSACTION//AMOUNT").getValue());
		} catch (JDOMException e) {
			fail("The XPath expressions failed");
		}
	}

	@Test
	public void testXPathExpressionsForComplexMessageWithMultipleTokens() {
		try {
			assertEquals(3,
					Utils.getElement(testDocWith3Tokens, "//RLSOLVE_MSG/CARD/TOKENS")
							.getChildren().size());
			List tokenList = Utils.getElement(testDocWith3Tokens,
					"//RLSOLVE_MSG/CARD/TOKENS").getChildren();
			boolean central = false, left = false, right = false;
			for (int i = 0; i < tokenList.size(); i++) {
				Element tokenElement = (Element) tokenList.get(i);
				String origin = tokenElement.getAttributeValue(
						"origin");
				if (origin.equals("central")) {
					central = true;
					assertEquals(
							"476173bbbbbbbgf0010",
							tokenElement
									.getValue());
				}
				if (origin.equals("left")) {
					left = true;
					assertEquals(
							"576173bbbbbbbgf0010",
							tokenElement
									.getValue());
				}
				if (origin.equals("right")) {
					right = true;
					assertEquals(
							"676173bbbbbbbgf0010",
							tokenElement
									.getValue());
				}
			}
			assertTrue(central);
			assertTrue(left);
			assertTrue(right);
		} catch (JDOMException e) {
			fail("The XPath expressions failed");
		}
	}
	@Test
	public void testIncorrectFormatForSimpleMessage() {
			StringBuilder s = new StringBuilder("");
			s.append("<RLSOLVE_MSG>");
			s.append("	<CARD>");
			s.append("		<PAN4444333322221111</PAN>");//Note the injected error
			s.append("	</CARD>");
			s.append("	<TRANSACTION type=\"refund\" customer=\"present\" time=\"10:55:44\" >");
			s.append("		<AMOUNT>2000</AMOUNT>");
			s.append("	</TRANSACTION>");
			s.append("</RLSOLVE_MSG>   ");
		
			try {
				Document testDocWithIncorrectFormat = Utils.createDocument(s.toString());				
				fail("JDOMException is expected");
			} catch (Exception e) {
				assertTrue(true);
			}
		
	}
	@Test
	public void testMissingAttributeForSimpleMessage() {
		StringBuilder s = new StringBuilder("");
		s.append("<RLSOLVE_MSG>");
		s.append("	<CARD>");
		s.append("		<PAN>4444333322221111</PAN>"); //Note the injected error-missing end
		s.append("	</CARD>");
		s.append("	<TRANSACTION type=\"refund\" customer=\"present\" time=\"10:55:44\" >");
		s.append("		<AMOUNT>2000</AMOUNT>");
		s.append("	</TRANSACTION>");
		s.append("</RLSOLVE_MSG>   ");
		try {
			Document testDocWithMissingAttribute = Utils.createDocument(s.toString());
			assertEquals(null,
					Utils.getElement(testDocWithMissingAttribute, "//RLSOLVE_MSG/CARD/PAN")
							.getAttributeValue("end"));
		} catch (JDOMException | IOException e) {
			assertTrue(true);
		}		
	}
	


}
