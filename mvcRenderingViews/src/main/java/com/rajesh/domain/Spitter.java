package com.rajesh.domain;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Spitter {
	@NotNull
	@Size(min=2,max=10)
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	/**
	 * 
	 */
	public Spitter() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param firstname
	 * @param lastname
	 * @param username
	 * @param password
	 */
	public Spitter(String firstname, String lastname, String username,
			String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Spitter [firstname=" + firstname + ", lastname=" + lastname
				+ ", username=" + username + ", password=" + password + "]";
	}
}
