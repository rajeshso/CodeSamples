package com.db.tw.distribution.publisher;

import java.util.Map;

public interface Publisher extends Runnable {

	public abstract void prepare(Map<String, Object> context);

	public abstract boolean testConnection();

	public abstract void setPayLoad(String payLoad);

}
