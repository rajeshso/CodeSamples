package com.rajesh.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * Learn: 
 * Whether it’s a constructor, a setter method, or any other method, Spring will attempt
 *	to satisfy the dependency expressed in the method’s parameters.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StudioConfiguration.class)
public class SampleSpringTestcase {
	@Autowired
	private Studio studio;
	
	@Test
	public void testNotNull() {
		assertNotNull(studio);
	}
	
	@Test
	public void testString() {
		assertEquals("Looo la la la", studio.playMusic());
	}
}
