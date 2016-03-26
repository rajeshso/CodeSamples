package com.raj.commons.keyedPool;

import javax.jms.TopicConnection;
public class ServerDetails {

	private String brokerURL;
	private boolean FTP;
	private boolean JMS;
	private TopicConnection topicConnection;
	
	public String getBrokerURL() {
		return brokerURL;
	}

	public void setBrokerURL(String brokerURL) {
		this.brokerURL = brokerURL;
	}

	public boolean isFTP() {
		return FTP;
	}

	public void setFTP(boolean fTP) {
		FTP = fTP;
	}

	public boolean isJMS() {
		return JMS;
	}

	public void setJMS(boolean jMS) {
		JMS = jMS;
	}

	public TopicConnection getConnection() {
		return topicConnection;
	}

	public void setConnection(TopicConnection connection) {
		this.topicConnection = connection;
	}


	public ServerDetails() {
		// TODO Auto-generated constructor stub
	}

}
