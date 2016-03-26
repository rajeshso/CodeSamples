/**
 * 
 */
package com.sky.detector;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.configuration.CompositeConfiguration;

/** 
 * @author Rajesh
 *
 */
public class SimpleHackerDetector implements HackerDetector {

	static enum PROPERTY_KEYS {
		MIN_FAILED_LOGIN, MAX_TIME, SIGNIN_SUCCESS, SIGNIN_FAILURE
	}

	int minFailedLogin = 0;
	int maxTimeToRetain = 0;
	private String failureActionString = null;
	private SigninStore signinStore = null;

	public SimpleHackerDetector(String configFileName, SigninStore signinStore) throws InvalidInputException {
		CompositeConfiguration properties = InitializationUtil.getProperties(configFileName);
		minFailedLogin = properties.getInt(PROPERTY_KEYS.MIN_FAILED_LOGIN.toString());
		maxTimeToRetain = properties.getInt(PROPERTY_KEYS.MIN_FAILED_LOGIN.toString());
		//successActionString = properties.getString(PROPERTY_KEYS.SIGNIN_SUCCESS.toString());
		failureActionString = properties.getString(PROPERTY_KEYS.SIGNIN_FAILURE.toString());
		this.signinStore = signinStore;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.detector.HackerDetector#parseLine(java.lang.String)
	 */
	@Override
	public String parseLine(String line) {
		LogParser logParser = null;
		try {
			logParser = new SingleLineParser(line);
		} catch (InvalidInputException iie) {
			System.err.println("Line is ignored." + iie.getMessage());
			return null;
		}
		
		if (logParser.getAction().trim().equalsIgnoreCase(failureActionString.trim())) {
			housekeeping(logParser.getEventTime());
			signinStore.add(logParser.getEventTime(), logParser.getIP());
			if (countPastFailedEvents(logParser.getIP()) >= minFailedLogin) {
				return logParser.getIP();
			}
		}
		return null;
	}

	/**
	 * Remove obsolete entries
	 * @param recentTime the events before the recentTime are considered obsolete events/entries
	 */
	private void housekeeping(long recentTime) {
		Instant timeManipulator = Instant.ofEpochSecond(recentTime);
		timeManipulator = timeManipulator.minus(Duration.ofMinutes(maxTimeToRetain)); 
		long cutOffTime = timeManipulator.getEpochSecond();
		signinStore.removeObsolete(cutOffTime);
	}
	/**
	 * Counts past failed events for the ip Address 
	 * @param ipAddress
	 * @return
	 */
	private int countPastFailedEvents(String ipAddress) {
		return signinStore.countEvents(ipAddress);
	}
}
