package raj.tricky.questios;

class A1 {
	static int i = 10;

	static {
		System.out.println("static A");
		i = i-- - --i;
		System.out.println(i);
	}

	{
		System.out.println("Non static A");
		i = i++ + ++i;
		System.out.println(i);
	}
}

class B1 extends A1 {
	static {
		System.out.println("static B");
		i = --i - i--;
		System.out.println(i);
	}

	{
		System.out.println("Non static B");
		i = ++i + i++;
		System.out.println(i);
	}
}

public class MainClass1 {
	public static void main(String[] args) {
		B1 b = new B1();
		System.out.println(b.i);
        Integer i1 = 127;
        Integer i2 = 127;
        System.out.println(i1 == i2);
        System.out.println("ONE"+1+2+"TWO"+"THREE"+3+4+"FOUR"+"FIVE"+5+6);
        String r = "Rajesh";
        String r1= "Rajesh";
        if (r==r1) System.out.println("true");	
        if (r.equals(r1)) System.out.println("true");	
	}
}
