package com.raj.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Parser {
 
	
	private static String BUSINESS_KEYS_DELIMITER = ".";
	private static String BUSINESS_KEY_VALUE_DELIMITER = "=";
	private static String BUSINESS_VALUE_DELIMITER = "/";
	
	public Parser() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String testString1 = "JUR=CFTC/EMIR/HKMA/ASIC.ASSET=FX/RATES/EQUITY/COMMODITY/DEBTS.REPORT=PET/DODDFRANK";
		String testString2 = "JUR=CFTC/EMIR/HKMA/ASIC.ASSET=FX/RATES/EQUITY/COMMODITY/DEBTS";
		String testString3 = "JUR=CFTC/EMIR/HKMA/ASIC.ASSET=FX/RATES/EQUITY/COMMODITY/DEBTS.REPORT=PET";
		Parser parser = new Parser();
		parser.parse(testString1);
		parser.parse(testString2);
		parser.parse(testString3);
	}

	public Map<String, List<String>> parse(String input) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		StringTokenizer businessKeysTokenizer = new StringTokenizer(input, BUSINESS_KEYS_DELIMITER ); 
		System.out.printf("Number of Business Keys = %d %n",businessKeysTokenizer.countTokens());
		while(businessKeysTokenizer.hasMoreTokens()) {
			StringTokenizer businessKeyValueTokenizer = new StringTokenizer(businessKeysTokenizer.nextToken(),BUSINESS_KEY_VALUE_DELIMITER);
			String businessKey = businessKeyValueTokenizer.nextToken();
			String businessValues = businessKeyValueTokenizer.nextToken();
			StringTokenizer businessValueTokenizer = new StringTokenizer(businessValues);
			List<String> businessValueList = new ArrayList<String>(businessValueTokenizer.countTokens());
			while(businessValueTokenizer.hasMoreTokens()) {
				String businessValue =  businessValueTokenizer.nextToken();
				businessValueList.add(businessValue);
			}
			result.put(businessKey, businessValueList);
			//System.out.println(businessKey);
			//System.out.println(businessValues);
		}
		System.out.println(result);
		return result;	
	}
}
