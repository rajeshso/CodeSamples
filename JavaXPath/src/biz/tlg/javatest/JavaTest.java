package biz.tlg.javatest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import biz.tlg.javatest.dto.CardDTO;
import biz.tlg.javatest.dto.CustomerPresent;
import biz.tlg.javatest.dto.PaymentDTO;
import biz.tlg.javatest.dto.TokenDTO;
import biz.tlg.javatest.dto.TransactionDTO;
import biz.tlg.javatest.dto.TransactionType;
import biz.tlg.javatest.messages.Messages;
import biz.tlg.javatest.utils.Utils;

/**
 * 
 * @author paul.manning
 *
 */
public class JavaTest {

	private static final String LOG4J_PROPERTIES = "/log4j.properties";

	/** log4j logger */
	private static final Logger LOG = Logger.getLogger(JavaTest.class);

	/**
	 * This method is the facade of the primary function of this test
	 * 
	 * @throws JDOMException if the XML messages are incorrect
	 */
	private void run() throws JDOMException {

		LOG.debug("Starting run method...");

		// TransactionDTO transactionDTO = new TransactionDTO();
		// write common parsing code to deal with both of theses XML messages
		// into the DTO objects in the DTO package (PaymentDTO sits at the
		// root)

		// Messages.SIMPLE_CARD_MESSAGE_DOC;
		// Messages.COMPLEX_CARD_MESSAGE_DOC;
		PaymentDTO simplePaymentDTO = getCardMessage(Messages.SIMPLE_CARD_MESSAGE_DOC);
		PaymentDTO complexPaymentDTO = getCardMessage(Messages.COMPLEX_CARD_MESSAGE_DOC);
		// Feel free to add new classes & methods as you wish, there are
		// getElement() and getAttribute() methods in the Utils class
		// for you to use. Usage of these methods is demonstrated via unit tests
		// in the UtilsTest class

		// Add unit test coverage for some of your code, prioritising areas as
		// you feel appropriate.

		/* Print the content of the DTO objects */
		LOG.info("Output of DTOs...");
		LOG.info("Output of SIMPLE_CARD_MESSAGE_DOC DTO...");
		LOG.info(simplePaymentDTO.toString());
		LOG.info("Output of COMPLEX_CARD_MESSAGE_DOC DTO...");
		LOG.info(complexPaymentDTO.toString());
	}
	
	/**
	 * PaymentDTO is the root Data object. It contains the Card and Transaction Data.
	 * This method parses the given document and returns a DTO form
	 * @param cardMessage The XML as a DOM document
	 * @return PaymentDTO The cardMessage XML in the DTO avatar
	 * @throws JDOMException in case if the message is incorrect
	 */
	public PaymentDTO getCardMessage(Document cardMessage)
			throws JDOMException {
		CardDTO cardDTO = getCardDTO(cardMessage);
		TransactionDTO transactionDTO = this
				.getTransactionDTO(cardMessage);
		PaymentDTO paymentDTO = new PaymentDTO(cardDTO, transactionDTO);
		return paymentDTO;
	}
	
	/**
	 * The Card is the Child. This method parses the Card and returns a DTO
	 * @param cardMessage The XML document can either be Simple or Complex
	 * @return CardDTO The DTO avatar of the Card element
	 * @throws JDOMException if something goes wrong in the XML
	 */
	private CardDTO getCardDTO(Document cardMessage)
			throws JDOMException {
		String pan = Utils.getElement(cardMessage,
				"//RLSOLVE_MSG/CARD/PAN").getValue();
		String panExpiryDate = Utils.getElement(cardMessage,
				"//RLSOLVE_MSG/CARD/PAN").getAttributeValue("end");
		Element track2Element = Utils.getElement(cardMessage,
				"//RLSOLVE_MSG/CARD/TRACK2");
		CardDTO cardDTO = new CardDTO(pan, panExpiryDate);
		if (track2Element!=null) {
			String track2 = Utils.getElement(cardMessage,
					"//RLSOLVE_MSG/CARD/TRACK2").getValue();
			cardDTO.setTrack2(track2);		
		}
		Element tokenElements = Utils.getElement(cardMessage,
				"//RLSOLVE_MSG/CARD/TOKENS");
		if (tokenElements != null) {
			List<TokenDTO> tokenList = this
					.getTokenDTOList(cardMessage);
			cardDTO.setTokens(tokenList);
		}
		return cardDTO;
	}
	
	/**
	 * Transaction values of the XML are converted to the DTO
	 * @param cardMessage The XML can either be Simple or Complex
	 * @return The DTO form of the Transaction child element
	 * @throws JDOMException if the XML is non-parsable
	 * @throws IllegalStateException if the values of the XML are incorrect
	 */
	private TransactionDTO getTransactionDTO(Document cardMessage)
			throws JDOMException, IllegalStateException {
		String transactionType = Utils.getElement(cardMessage,
				"//RLSOLVE_MSG/TRANSACTION").getAttributeValue("type");
		String customerPresent = Utils.getElement(cardMessage,
				"//RLSOLVE_MSG/TRANSACTION").getAttributeValue("customer");
		String transactionTime = Utils.getElement(cardMessage,
				"//RLSOLVE_MSG/TRANSACTION").getAttributeValue("time");
		String amount = Utils.getElement(cardMessage,
				"//RLSOLVE_MSG/TRANSACTION//AMOUNT").getValue();
		TransactionDTO txDTO = new TransactionDTO();
		txDTO.setTransactionType(TransactionType
				.getTransactionType(transactionType));
		txDTO.setCustomerPresent(CustomerPresent
				.getCustomerPresent(customerPresent));
		txDTO.parseAndSetTransactionTime(transactionTime);
		txDTO.setTransactionAmount(amount);
		return txDTO;
	}

	/**
	 * Tokens are parsed and returned as a Collection of objects
	 * @param cardMessage the XML can either be Simple or Complex
	 * @return The Collection of TokenDTOs
	 * @throws JDOMException if the XML is incorrect
	 * @throws IllegalArgumentException The values that does not conform to rules are thrown as IllegalArguments
	 */
	private List<TokenDTO> getTokenDTOList(Document cardMessage)
			throws JDOMException, IllegalArgumentException {
		List tokenList = Utils.getElement(cardMessage,
				"//RLSOLVE_MSG/CARD/TOKENS").getChildren();
		List<TokenDTO> tokenDTOList = new ArrayList<TokenDTO>(tokenList.size());
		for (int i = 0; i < tokenList.size(); i++) {
			Element tokenElement = (Element) tokenList.get(i);
			String origin = tokenElement.getAttributeValue("origin");
			TokenDTO tokenDTO = new TokenDTO(tokenElement.getValue(), origin);
			tokenDTOList.add(tokenDTO);
		}
		return tokenDTOList;
	}

	/**
	 * Run the Java Test
	 * This program is executed through the command line. This method is the entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		/* Initialise log4j */
		String currentdir = System.getProperty("user.dir");
		System.out.println("Reading log4j properties file: " + LOG4J_PROPERTIES
				+ " ...");
		if (new File(currentdir + LOG4J_PROPERTIES).exists()) {
			PropertyConfigurator.configureAndWatch(currentdir
					+ LOG4J_PROPERTIES);
		} else {
			System.out.println("FAILED - " + currentdir + " Not Found.");
		}
		
		try {
			Messages.init();
			new JavaTest().run();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} 
	}

}
