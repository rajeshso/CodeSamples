package hello;

import java.net.URL;

import hello.HelloApplication;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HelloApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class HelloApplicationTests {

	@Value(value = "${local.server.port}")
	private int port;
	
	private URL base;
	private RestTemplate restTemplate;
	
	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:"+port+"/rajesh");
		restTemplate = new RestTemplate();
	}
	
	@Test
	public void testGetHello() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(base.toString(), String.class);
		System.out.println(responseEntity.getBody());
		
		Assert.assertThat(responseEntity.getBody(), Matchers.equalTo("Hello World") );
	}
	
	@Test
	public void contextLoads() {
	}

}
