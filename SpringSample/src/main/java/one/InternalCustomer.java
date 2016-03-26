package one;

import org.springframework.beans.factory.annotation.Autowired;

public class InternalCustomer extends Customer {
	@Autowired
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public InternalCustomer() {
	}

	public InternalCustomer(int id, String name) {
		super(id, name);
	}

	public InternalCustomer(String name, int id, Address address) {
		super(id, name);
		this.address = address;
		System.out.println(this);

	}// this is overloaded....
}
