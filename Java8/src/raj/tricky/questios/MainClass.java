package raj.tricky.questios;

class A
{
	static {
		System.out.println("Hi");
	}
    static void staticMethod()
    {
        System.out.println("Static Method");
    }
    void print() {
    	System.out.println("hello");
    }
}
 
public class MainClass
{
    public static void main(String[] args)
    {
        A a = null;
        // Notice when hi is printed. It is printed only when a.staticMethod() is invoked. Hi is followed by static method 
        a.staticMethod();//Successfully prints "Static Method"
       // a.print();//results in null pointer error
    }
}
 