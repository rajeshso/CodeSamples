package uk.rajesh;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	private static final String TEMPLATE = "Hello, %s !";
	private final AtomicLong atomicCounter = new AtomicLong();
	
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
	    Greeting g = new Greeting(atomicCounter.incrementAndGet(), String.format(TEMPLATE, name));
	    return g;
	}
	
}
