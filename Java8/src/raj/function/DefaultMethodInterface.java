package raj.function;

//Remember the difference between default method interface vs abstract class.
//Abstract class can have private variables that can maintain state. In Interface, the
//default methods are stateless.
class DefaultMethodInterfaceImpl implements DefaultMethodInterface {

	public static void main(String[] args) {
		DefaultMethodInterface dmi = new DefaultMethodInterfaceImpl();
		dmi.sayHello();
		dmi.sayHi();
	}

	@Override
	public void sayHello() {
		System.out.println("Hello");
	}

}
public interface DefaultMethodInterface {
	public void sayHello();
	//Default method
	public default void sayHi() {
		System.out.println("Hi");
	}
}
