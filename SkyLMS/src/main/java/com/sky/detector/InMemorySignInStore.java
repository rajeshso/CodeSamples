package com.sky.detector;

import java.util.LinkedHashMap;

/**
 * An InMemory Data storage to store the failed logins.
 * 
 * @author Rajesh
 *
 */
public class InMemorySignInStore implements SigninStore {

	private final LinkedHashMap<Long, String> inMemoryStore = new LinkedHashMap<Long, String>();

	/**
	 * Add a failed attempt to the store
	 */
	@Override
	public void add(final Long timestamp, final String ipaddress) {
		inMemoryStore.put(timestamp, ipaddress);
	}

	/**
	 * The signin page can generate around 100,000 failed signins a day so
	 * memory consumption should be considered and managed. The Obsolete entries
	 * has to be periodically removed. Only the essential data is retained.
	 */
	@Override
	public void removeObsolete(final Long cutOffTimeStamp) {
		inMemoryStore.entrySet().removeIf(entry -> entry.getKey() <= cutOffTimeStamp);
	}

	@Override
	public int countEvents(String currentIP) {
		long count = inMemoryStore.values().stream().filter((pastIP) -> (pastIP.equals(currentIP))).count();
		return (int) count;
	}

	@Override
	public String get(Long timestamp) {
		return inMemoryStore.get(timestamp);
	}

}
