package one;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * In this example, we learnt how to wire beans, how to unit test beans, how to replace xml config with java based config
 * 
 * @author my pc
 *
 */
public class Exe {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"mybean.xml");

		// All about Calculators
		CalculatorImpl call = (CalculatorImpl) ctx.getBean("cal1");
		System.out.println(call.add(20, 33.059559));
		System.out.println(call);
		CalculatorImpl callo = ctx.getBean(CalculatorImpl.class);// child
																	// also....
		System.out.println(callo.add(20, 33));
		CalculatorImpl call2 = (CalculatorImpl) ctx.getBean("cal1");
		System.out.println(call2);

		// All about Customers
		Customer cust = (Customer) ctx.getBean("customer");
		System.out.println(cust.getCid());
		System.out.println(cust.getName());

		// All about Internal Customers
		InternalCustomer cust1 = (InternalCustomer) ctx
				.getBean("internalCustomer");
		System.out.printf("%n Customer ID = %s Name = %s", cust1.getCid(),
				cust1.getName());
		System.out.println("Address is " + cust1.getAddress());

		InternalCustomer custo = (InternalCustomer) ctx
				.getBean("internalCustomer1");
		System.out.println(custo.getCid());
		System.out.println(custo.getName());
		System.out.println("Address is " + custo.getAddress());

	}

}
