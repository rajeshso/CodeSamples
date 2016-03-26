package one;

public class Address {
	private int doorno;
	private String street;
	private String city;
	private String country;

	public Address() {
		super();
	}

	private int zip;

	public int getDoorno() {
		return doorno;
	}

	public void setDoorno(int doorno) {
		this.doorno = doorno;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param doorno
	 * @param street
	 * @param city
	 * @param country
	 * @param zip
	 */
	public Address(int doorno, String street, String city, String country,
			int zip) {
		super();
		this.doorno = doorno;
		this.street = street;
		this.city = city;
		this.country = country;
		this.zip = zip;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}
}
