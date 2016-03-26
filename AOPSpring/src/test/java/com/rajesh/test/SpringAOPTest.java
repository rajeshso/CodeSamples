package com.rajesh.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rajesh.pointcuts.Work;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class SpringAOPTest {

	@Autowired
	ApplicationContext ctx;
	
	@Test
	public final void testWork() {
		Work work = (Work) ctx.getBean("work");
		work.doWork();
	}

}
