package com.sky.detector;


public interface SigninStore { 
	public void add(Long timestamp, String ip);
	public void removeObsolete(Long cutOffTimeStamp);
	public int countEvents(String ip);
	public String get(Long timestamp);// Design for testablity method
}
