package one;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySpringConfiguration {

	/**
	 * 	<bean id="cal1" class="one.CalculatorImpl" scope="singleton">
	 *	</bean>
	 * @return
	 */
	@Bean(name={"cal1"})
	public CalculatorImpl getCalculatorImpl() {
		return new CalculatorImpl();
	}
	
	/**
	 *
	<bean id="customer" class="one.Customer">
		<property name="cid" value="1"></property>
		<property name="name" value="Rajesh"></property>
	</bean>	  
	 * @return
	 */
	@Bean(name={"customer"}) 
	public Customer getCustomer() {
		Customer c = new Customer();
		c.setCid(0);
		c.setName("Rajesh");
		return c;
	}
	
	/**
	 * 	<bean id="internalCustomer" class="one.InternalCustomer">
		<constructor-arg value="3"></constructor-arg>
		<constructor-arg value="Raj1"></constructor-arg>
	</bean>
	 */
	@Bean(name={"internalCustomer"})
	public InternalCustomer getInternalCustomer() {
		InternalCustomer ic = new InternalCustomer(3, "Raj1");
		return ic;
	}

/**
 * 	<!-- int doorno, String street, String city, String country, int zip -->
	<bean id="address" class="one.Address">
		<constructor-arg index="0" value="3"></constructor-arg>
		<constructor-arg index="1" value="Lathom Road">
		</constructor-arg>
		<constructor-arg index="2" value="LON">
		</constructor-arg>
		<constructor-arg index="3" value="UK">
		</constructor-arg>
		<constructor-arg index="4" value="1">
		</constructor-arg>
	</bean>
 * @return
 */
	@Bean(name={"address"})
	public Address getAddress() {
		Address a = new Address(3, "Lathom Road", "LON", "UK", 1);
		return a;
	}
	
/**	
	<bean id="internalCustomer1" class="one.InternalCustomer">
		<property name="address" ref="address"></property>
	</bean>
**/	
	@Bean(name={"internalCustomer1"})
	public InternalCustomer getInternalCustomer1() {
		InternalCustomer ic = new InternalCustomer(3, "Raj1");
		ic.setAddress(getAddress());
		return ic;		
	}
}
