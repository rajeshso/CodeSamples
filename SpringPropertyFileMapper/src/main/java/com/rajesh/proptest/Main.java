package com.rajesh.proptest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		A a = context.getBean(A.class);
		System.out.printf("var1 = %s , var2 = %s %n", a.getVar1(), a.getVar2());
		
		System.out.println(System.getProperties().toString());
	}

}
