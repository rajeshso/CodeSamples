package com.sky.detector.bdd;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import com.sky.detector.HackerDetector;
import com.sky.detector.InMemorySignInStore;
import com.sky.detector.SigninStore;
import com.sky.detector.SimpleHackerDetector;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LMSStepDefinitions {
	private HackerDetector hackerDetector;
	private String actualResult;

	@Given("^the Log Monitoring System configuration is \"([^\"]*)\"$")
	public void initHackDetector(String configFile) throws Throwable {
		String rootPath = new File("").getAbsolutePath();
		String metadataAbsoluteFileName = rootPath + configFile;
		SigninStore signinStore = new InMemorySignInStore();
		hackerDetector = new SimpleHackerDetector(configFile, signinStore);
	}

	@When("^the customer invokes parseline with a line of log value \"([^\"]*)\"$")
	public void parseLine(String logLine) throws Throwable {
		actualResult = hackerDetector.parseLine(logLine);
	}

	@Then("^The result is null$")
	public void checkResultForNull() throws Throwable {
		assertThat(actualResult).isNull();
	}

	@Then("^The result is \"([^\"]*)\"$")
	public void checkResult(String expectedResult) throws Throwable {
		assertThat(actualResult).isEqualTo(expectedResult);
	}
}
