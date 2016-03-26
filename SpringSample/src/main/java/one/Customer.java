package one;

public class Customer {
private int cid;
private String name;
public int getCid() {
	return cid;
}
public Customer(int cid, String name) {
	super();
	this.cid = cid;
	this.name = name;
}
public void setCid(int cid) {
	this.cid = cid;
}
public Customer() {
	super();
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
}
