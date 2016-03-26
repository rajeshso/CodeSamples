package biz.tlg.javatest.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.junit.Before;
import org.junit.Test;

import biz.tlg.javatest.JavaTest;
import biz.tlg.javatest.dto.TokenDTO;

public class JavaTestTest {

	@Test
	public final void testGetCardMessage() {
		StringBuilder s = new StringBuilder("");
		s.append("<RLSOLVE_MSG>");
		s.append("	<CARD>");
		s.append("    <PAN end=\"2016-11\">4111111111111111</PAN>");//Note the point of error
		s.append("    <TRACK2>;476173******0010=*****************?4</TRACK2>");
		s.append(" 	  <TOKENS>");
		s.append("		<TOKEN origin=\"central\">476173bbbbbbbgf0010</TOKEN>");
		s.append("	  </TOKENS>");
		s.append("	</CARD>");
		s.append("	<TRANSACTION type=\"purchase\" customer=\"present\" time=\"10:53:30\" >");
		s.append(" 		<AMOUNT>15000</AMOUNT>");
		s.append("	</TRANSACTION>");
		s.append("</RLSOLVE_MSG>");
		try {
			Document docWithAllCorrectValues = Utils.createDocument(s.toString());
			
			JavaTest javaTest = new JavaTest();
			javaTest.getCardMessage(docWithAllCorrectValues);
			assertEquals("2016-11", javaTest.getCardMessage(docWithAllCorrectValues).getCardDTO().getExpiryDate());
			assertEquals("4111111111111111", javaTest.getCardMessage(docWithAllCorrectValues).getCardDTO().getPan());
			assertEquals(";476173******0010=*****************?4", javaTest.getCardMessage(docWithAllCorrectValues).getCardDTO().getTrack2());
			assertEquals("central", (javaTest.getCardMessage(docWithAllCorrectValues).getCardDTO().getTokens().iterator().next()).getOrigin());
			assertEquals("476173bbbbbbbgf0010", (javaTest.getCardMessage(docWithAllCorrectValues).getCardDTO().getTokens().iterator().next()).getToken());
			assertEquals("purchase", javaTest.getCardMessage(docWithAllCorrectValues).getTransactionDTO().getTransactionType().getType());
			assertEquals("present", javaTest.getCardMessage(docWithAllCorrectValues).getTransactionDTO().getCustomerPresent().getPresence());
			assertEquals(10, javaTest.getCardMessage(docWithAllCorrectValues).getTransactionDTO().getTransactionTime().getHours());
			assertEquals(53, javaTest.getCardMessage(docWithAllCorrectValues).getTransactionDTO().getTransactionTime().getMinutes());
			assertEquals(30, javaTest.getCardMessage(docWithAllCorrectValues).getTransactionDTO().getTransactionTime().getSeconds());
		} catch (JDOMException | IOException e) {
			fail("PAN expiry date Failure is undetected");
		}catch(IllegalArgumentException e) {
			fail("PAN expiry date Failure is undetected");
		}
	}
	@Test
	public final void testGetCardMessageWithTwoTokens() {
		StringBuilder s = new StringBuilder("");
		s.append("<RLSOLVE_MSG>");
		s.append("	<CARD>");
		s.append("    <PAN end=\"2016-11\">4111111111111111</PAN>");//Note the point of error
		s.append("    <TRACK2>;476173******0010=*****************?4</TRACK2>");
		s.append(" 	  <TOKENS>");
		s.append("		<TOKEN origin=\"central\">476173bbbbbbbgf0010</TOKEN>");
		s.append("		<TOKEN origin=\"central\">576173bbbbbbbgf0010</TOKEN>");		
		s.append("	  </TOKENS>");
		s.append("	</CARD>");
		s.append("	<TRANSACTION type=\"purchase\" customer=\"present\" time=\"10:53:30\" >");
		s.append(" 		<AMOUNT>15000</AMOUNT>");
		s.append("	</TRANSACTION>");
		s.append("</RLSOLVE_MSG>");
		try {
			Document docWithAllCorrectValues = Utils.createDocument(s.toString());
			JavaTest javaTest = new JavaTest();
			javaTest.getCardMessage(docWithAllCorrectValues);
			Collection<TokenDTO> tokenDTOList = javaTest.getCardMessage(docWithAllCorrectValues).getCardDTO().getTokens();
			assertEquals(2, tokenDTOList.size());
		} catch (JDOMException | IOException e) {
			fail("PAN expiry date Failure is undetected");
		}catch(IllegalArgumentException e) {
			fail("PAN expiry date Failure is undetected");
		}
	}	
	@Test
	public void testInvalidPANExpiryDate1() {
		StringBuilder s = new StringBuilder("");
		s.append("<RLSOLVE_MSG>");
		s.append("	<CARD>");
		s.append("    <PAN end=\"2016-33\">4111111111111111</PAN>");//Note the point of error
		s.append("    <TRACK2>;476173******0010=*****************?4</TRACK2>");
		s.append(" 	  <TOKENS>");
		s.append("		<TOKEN origin=\"central\">476173bbbbbbbgf0010</TOKEN>");
		s.append("	  </TOKENS>");
		s.append("	</CARD>");
		s.append("	<TRANSACTION type=\"purchase\" customer=\"present\" time=\"10:53:30\" >");
		s.append(" 		<AMOUNT>15000</AMOUNT>");
		s.append("	</TRANSACTION>");
		s.append("</RLSOLVE_MSG>");
		try {
			Document docWithIncorrectExpDate = Utils.createDocument(s.toString());
			JavaTest javaTest = new JavaTest();
			javaTest.getCardMessage(docWithIncorrectExpDate);
			fail("PAN expiry date Failure is undetected");
		} catch (JDOMException | IOException e) {
			fail("PAN expiry date Failure is undetected");
		}catch(IllegalArgumentException e) {
			System.err.println(e.getMessage());
			assertEquals("Expiry date must match YYYY-MM and have valid values", e.getMessage());
		}
	}
	
	@Test
	public void testInvalidPANExpiryDate2() {
		StringBuilder s = new StringBuilder("");
		s.append("<RLSOLVE_MSG>");
		s.append("	<CARD>");
		s.append("    <PAN end=\"20aa-12\">4111111111111111</PAN>");//Note the point of error
		s.append("    <TRACK2>;476173******0010=*****************?4</TRACK2>");
		s.append(" 	  <TOKENS>");
		s.append("		<TOKEN origin=\"central\">476173bbbbbbbgf0010</TOKEN>");
		s.append("	  </TOKENS>");
		s.append("	</CARD>");
		s.append("	<TRANSACTION type=\"purchase\" customer=\"present\" time=\"10:53:30\" >");
		s.append(" 		<AMOUNT>15000</AMOUNT>");
		s.append("	</TRANSACTION>");
		s.append("</RLSOLVE_MSG>");
		try {
			Document docWithIncorrectExpDate = Utils.createDocument(s.toString());
			JavaTest javaTest = new JavaTest();
			javaTest.getCardMessage(docWithIncorrectExpDate);
			fail("PAN expiry date Failure is undetected");
		} catch (JDOMException | IOException e) {
			fail("PAN expiry date Failure is undetected");
		}catch(IllegalArgumentException e) {
			System.err.println(e.getMessage());
			assertEquals("Expiry date must match YYYY-MM and have valid values", e.getMessage());
		}
	}
	@Test
	public void testPANWithInvalidLen() {
		StringBuilder s = new StringBuilder("");
		s.append("<RLSOLVE_MSG>");
		s.append("	<CARD>");
		s.append("    <PAN end=\"2000-12\">41111111111111113333333333</PAN>");//Note the point of error
		s.append("    <TRACK2>;476173******0010=*****************?4</TRACK2>");
		s.append(" 	  <TOKENS>");
		s.append("		<TOKEN origin=\"central\">476173bbbbbbbgf0010</TOKEN>");
		s.append("	  </TOKENS>");
		s.append("	</CARD>");
		s.append("	<TRANSACTION type=\"purchase\" customer=\"present\" time=\"10:53:30\" >");
		s.append(" 		<AMOUNT>15000</AMOUNT>");
		s.append("	</TRANSACTION>");
		s.append("</RLSOLVE_MSG>");
		try {
			Document docWithIncorrectPAN = Utils.createDocument(s.toString());
			JavaTest javaTest = new JavaTest();
			javaTest.getCardMessage(docWithIncorrectPAN);
			fail("PAN len Failure is undetected");
		} catch (JDOMException | IOException e) {
			fail("PAN len Failure is undetected");
		}catch(IllegalArgumentException e) {
			System.err.println(e.getMessage());
			assertEquals("PAN must be 13 to 16 numerical digits", e.getMessage());
		}
	}	
}
