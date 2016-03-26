package one;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/mybean.xml" })
public class SampleTestCase {

	@Autowired
	private ApplicationContext ctx;

	@Test
	public final void testCalculator() {
		// All about Calculators
		CalculatorImpl call = (CalculatorImpl) ctx.getBean("cal1");
		System.out.println(call.add(20, 33.059559));
		System.out.println(call);
		CalculatorImpl callo = ctx.getBean(CalculatorImpl.class);// child
																	// also....
		System.out.println(callo.add(20, 33));
		CalculatorImpl call2 = (CalculatorImpl) ctx.getBean("cal1");
		System.out.println(call2);
	}

	@Test
	public final void testCustomer() {
		// All about Customers
		Customer cust = (Customer) ctx.getBean("customer");
		System.out.println(cust.getCid());
		System.out.println(cust.getName());
	}

	@Test
	public final void testInternalCustomer() {
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
