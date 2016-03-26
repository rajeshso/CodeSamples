package raj.function;
//Use of @FunctionalInterface and Named Methods
class Function21 {
    static void useFunction2(Function2 f) {
    	System.out.println(f.print("Raj"));;
    }
	public static void main(String[] args) {
		oldWay();
		newWay();
	}
	
	//Old Way
	static void oldWay() {
		useFunction2(new Function2() {
			@Override
			public String print(String message) {
				return "Hello "+message;
			}
		});
	}
	static void newWay() {
		useFunction2(String::toString);
	}
}

@FunctionalInterface
public interface Function2 {
	public String print(String message);
}