package biz.tlg.javatest.messages;

import org.jdom.Document;

import biz.tlg.javatest.utils.Utils;

/**
 * Messages used in the JavaTest
 * 
 * 
 * @author paul.manning
 *
 */
public class Messages {

	private static final StringBuilder SIMPLE_MESSAGE_BUILDER = new StringBuilder();
	private static String SIMPLE_CARD_MESSAGE = null;
	private static final StringBuilder COMPLEX_MESSAGE_BUILDER = new StringBuilder();
	private static String COMPLEX_CARD_MESSAGE = null;	
    public static Document SIMPLE_CARD_MESSAGE_DOC;
    public static Document COMPLEX_CARD_MESSAGE_DOC;	
	static {
		SIMPLE_MESSAGE_BUILDER.append("<RLSOLVE_MSG>");
		SIMPLE_MESSAGE_BUILDER.append("	<CARD>");
		SIMPLE_MESSAGE_BUILDER.append("		<PAN end=\"2016-12\">4444333322221111</PAN>");
		SIMPLE_MESSAGE_BUILDER.append("	</CARD>");
		SIMPLE_MESSAGE_BUILDER.append("	<TRANSACTION type=\"refund\" customer=\"present\" time=\"10:55:44\" >");
		SIMPLE_MESSAGE_BUILDER.append("		<AMOUNT>2000</AMOUNT>");
		SIMPLE_MESSAGE_BUILDER.append("	</TRANSACTION>");
		SIMPLE_MESSAGE_BUILDER.append("</RLSOLVE_MSG>   ");
		SIMPLE_CARD_MESSAGE = SIMPLE_MESSAGE_BUILDER.toString();
		
		COMPLEX_MESSAGE_BUILDER.append("<RLSOLVE_MSG>");
		COMPLEX_MESSAGE_BUILDER.append("	<CARD>");
		COMPLEX_MESSAGE_BUILDER.append("    <PAN end=\"2016-12\">4111111111111111</PAN>");
		COMPLEX_MESSAGE_BUILDER.append("    <TRACK2>;476173******0010=*****************?4</TRACK2>");
		COMPLEX_MESSAGE_BUILDER.append(" 	  <TOKENS>");
		COMPLEX_MESSAGE_BUILDER.append("		<TOKEN origin=\"central\">476173bbbbbbbgf0010</TOKEN>");
		COMPLEX_MESSAGE_BUILDER.append("	  </TOKENS>");
		COMPLEX_MESSAGE_BUILDER.append("	</CARD>");
		COMPLEX_MESSAGE_BUILDER.append("	<TRANSACTION type=\"purchase\" customer=\"present\" time=\"10:53:30\" >");
		COMPLEX_MESSAGE_BUILDER.append(" 		<AMOUNT>15000</AMOUNT>");
		COMPLEX_MESSAGE_BUILDER.append("	</TRANSACTION>");
		COMPLEX_MESSAGE_BUILDER.append("</RLSOLVE_MSG>");
		COMPLEX_CARD_MESSAGE = COMPLEX_MESSAGE_BUILDER.toString();
	}

    public static void init() throws Exception {
    	try {
        	SIMPLE_CARD_MESSAGE_DOC = Utils.createDocument(SIMPLE_CARD_MESSAGE);
			COMPLEX_CARD_MESSAGE_DOC = Utils.createDocument(COMPLEX_CARD_MESSAGE);
		} catch (Exception e) {
			System.err.println("The card mesasge has an incorrect format. This program is not correctly initialized and will not execute as expected.");
			throw e;
		}
    }
   
}
