package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	public GreetingController() {
		super();
	}

	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping(method= RequestMethod.POST, value= "/greetRequest", headers="Accept=application/json")
    public Greeting greetingResponse(Greeting greetRequest) {
    	greetRequest.setId(counter.incrementAndGet());
    	greetRequest.setContent("Hello 123");
    	return greetRequest;
    }
}
