package second;


import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Library {
private List<Book> mylist;
public List<Book> getMylist() {
	return mylist;
}
public void setMylist(List<Book> mylist) {
	this.mylist = mylist;
}
public Set<Book> getMyset() {
	return myset;
}
public void setMyset(Set<Book> myset) {
	this.myset = myset;
}
public Map<Integer,Book> getMymap() {
	return mymap;
}
public void setMymap(Map<Integer,Book> mymap) {
	this.mymap = mymap;
}
public Properties getMyprops() {
	return myprops;
}
public void setMyprops(Properties myprops) {
	this.myprops = myprops;
}
private Set<Book> myset;
private Map<Integer,Book> mymap;
private Properties myprops;
}
