/**
* 
*/
package com.sky.detector;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * The log lines will be in the following format: ip,date,action,username IP
 * looks like 80.238.9.179 Date is in the epoch format like 1336129471 Action is
 * one of the following: SIGNIN_SUCCESS or SIGNIN_FAILURE Username is a String
 * like Dave.Branning A log line will look like this:
 * 80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning The Parser would
 * 
 * @author Rajesh
 *
 */
public class SingleLineParser implements LogParser {

	private static final String DELIMITER = ",";
	private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
	private String ip;
	private Long eventTime;
	private String action;

	public SingleLineParser(String line) throws InvalidInputException {
		if (line == null || line.isEmpty())
			throw new InvalidInputException("The Log line is empty");
		StringTokenizer tokenizer = new StringTokenizer(line, DELIMITER);
		if (tokenizer.countTokens() != 4)
			throw new InvalidInputException("The Log format is incorrect");
		ip = tokenizer.nextToken();
		Matcher ipPatternmatcher = IP_PATTERN.matcher(ip);
		if (!ipPatternmatcher.matches()) {
			throw new InvalidInputException("The IP format is incorrect");
		}
		try {
			eventTime = Long.parseLong(tokenizer.nextToken().trim());
		} catch (NumberFormatException nfe) {
			InvalidInputException iie = new InvalidInputException("The Log format is incorrect");
			iie.initCause(nfe);
			throw iie;
		}
		action = tokenizer.nextToken();
		if (action.trim().length() == 0)
			throw new InvalidInputException("The Log format is incorrect");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.detector.LogParser#getIP()
	 */
	@Override
	public String getIP() {
		return ip;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.detector.LogParser#getEventTime()
	 */
	@Override
	public Long getEventTime() {
		return eventTime;
	}

	@Override
	public String getAction() {
		return action;
	}

}
