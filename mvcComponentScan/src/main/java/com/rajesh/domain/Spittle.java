package com.rajesh.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Spittle {

	private final Long id;
	private final String message;
	private final Date time;
	private Double latitude;
	private Double longitude;
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Long getId() {
		return id;
	}
	public String getMessage() {
		return message;
	}
	public Date getTime() {
		return time;
	}
	/**
	 * @param id
	 * @param message
	 * @param time
	 * @param latitude
	 * @param longitude
	 */
	public Spittle(Long id, String message, Date time, Double latitude,
			Double longitude) {
		super();
		this.id = id;
		this.message = message;
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	/**
	 * @param message
	 * @param time
	 */
	public Spittle(String message, Date time) {
		this(null,message, time, null, null);
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id", "time");
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "id","time");
	}
	@Override
	public String toString() {
		return "Spittle [id=" + id + ", message=" + message + ", time=" + time
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
}
