package com.db.tw.distribution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.db.tw.distribution.config.BlockManager;
import com.db.tw.distribution.config.IBlock;
import com.db.tw.distribution.publisher.Publisher;
import com.db.tw.distribution.publisher.PublisherFactory;
import com.db.tw.distribution.publisher.PublisherRegistry;

public class Router {
	private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);
	private static String BUSINESS_KEYS_DELIMITER = ".";
	private static String BUSINESS_KEY_VALUE_DELIMITER = "=";
	private static String BUSINESS_VALUE_DELIMITER = "/";
	private PublisherRegistry publisherRegistry;

	public Router() {
		// TODO Auto-generated constructor stub
	}

	// The PublisherRegistry in the constructor can be replaced with the
	// payload+businesskeys.
	// The Router can be initialised only once with Publisher Registry.
	public Router(PublisherRegistry publisherRegistry) {
		this.publisherRegistry = publisherRegistry;
	}

	/**
	 * The primary job of Router is encapsulated in this method. The
	 * destinations for the payload are decided using business keys and a
	 * specific algorithm The payload is sent to different destinations using
	 * parallel threads.
	 * 
	 * @param payLoad
	 *            is the message that is sent to the destination.
	 * @param businessKeys
	 *            - The business keys are expected to be in . separated string.
	 *            For example, the business keys JUR, ASSET and REPORT may be
	 *            represented as
	 *            JUR=CFTC/EMIR/HKMA/ASIC.ASSET=FX/RATES/EQUITY/COMMODITY
	 *            /DEBTS.REPORT=PET/DODDFRANK The key value may be represented
	 *            as KEY=VALUE.KEY=VALUE.KEY=VALUE The Values may be delimited
	 *            as /
	 */
	public void execute(String payLoad, String businessKeys) {
		// Use the input to find out the list of business keys
		// Use the business keys and destinationProvider to find out the
		// destinations
		// Thread the publishers and execute
/*		System.out
				.printf("Router execute called with payLoad %s and its business keys are %s %n",
						payLoad, businessKeys);*/
		LOGGER.debug("Router execute called with payLoad {} and its business keys are {} ",payLoad, businessKeys);
		try {
			List<Publisher> publisherList = getPublishers(businessKeys, payLoad);
			int size = publisherList.size();
			for (int i = 0; i < size; i++) {
				new Thread(publisherList.get(i)).start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e.getMessage());
			LOGGER.error(e.getMessage());
		}
/*		System.out.printf("Published from %s to all destinations %n", payLoad);*/
		LOGGER.info("Published from {} to all destinations",payLoad);
	}

	private List<Publisher> getPublishers(String businessKeys, String payLoad)
			throws Exception {
		List<Publisher> publisherList = new ArrayList<Publisher>();
		Iterator<IBlock> matchingIBlockIterator = getMatchingBlocks(businessKeys);
		while (matchingIBlockIterator.hasNext()) {
			IBlock iBlock = matchingIBlockIterator.next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			Publisher publisher = publisherFactory.createPublisher(iBlock);
			publisher.setPayLoad(payLoad);
			publisherList.add(publisher);
		}
		return publisherList;
	}

	private Iterator<IBlock> getMatchingBlocks(String businessKeys) {
		Map<String, List<String>> businessKeyCriteria = this
				.parse(businessKeys);
		Set<IBlock> matchingBlockSet = BlockManager
				.getMatchingConfigsForBKeys(businessKeyCriteria);
		return matchingBlockSet.iterator();
	}

	/**
	 * 
	 * @param input
	 *            input is like
	 *            JUR=CFTC/EMIR/HKMA/ASIC.ASSET=FX/RATES/EQUITY/COMMODITY
	 *            /DEBTS.REPORT=PET/DODDFRANK
	 * @return a Map like JUR = [CFTC, EMIT,HKMA, ASIC] ASSET = [FX, RATES,
	 *         EQUITY, COMMODITY,DEBTS] REPORT = [PET, DODDFRANK]
	 * 
	 */
	private Map<String, List<String>> parse(String input) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		StringTokenizer businessKeysTokenizer = new StringTokenizer(input,
				BUSINESS_KEYS_DELIMITER);
/*		System.out.printf("Number of Business Keys = %d %n",
				businessKeysTokenizer.countTokens());*/
		LOGGER.debug("Number of Business Keys = {}",
				businessKeysTokenizer.countTokens());
		while (businessKeysTokenizer.hasMoreTokens()) {
			StringTokenizer businessKeyValueTokenizer = new StringTokenizer(
					businessKeysTokenizer.nextToken(),
					BUSINESS_KEY_VALUE_DELIMITER);
			String businessKey = businessKeyValueTokenizer.nextToken();
			String businessValues = businessKeyValueTokenizer.nextToken();
			StringTokenizer businessValueTokenizer = new StringTokenizer(
					businessValues, BUSINESS_VALUE_DELIMITER);
			List<String> businessValueList = new ArrayList<String>(
					businessValueTokenizer.countTokens());
			while (businessValueTokenizer.hasMoreTokens()) {
				String businessValue = businessValueTokenizer.nextToken();
				businessValueList.add(businessValue);
			}
			result.put(businessKey, businessValueList);
			LOGGER.debug(businessKey);
			LOGGER.debug(businessValues);
		}
		System.out.println(result);
		LOGGER.debug(result.toString());
		return result;
	}
}
