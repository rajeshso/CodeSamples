package com.sky.interview.pcs.bdd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.File;

import com.sky.interview.pcs.ParentalControlService;
import com.sky.interview.pcs.SimpleParentalControlService;
import com.sky.interview.pcs.TitleNotFoundException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PCSStepDefinitions {
	private ParentalControlService pcs;
	private boolean isPermittedWatchMovie;
	private String exception;

	@Given("^the parent level configuration is \"([^\"]*)\" and the movie meta data configuration is \"([^\"]*)\"$")
	public void the_parent_level_configuration_is_and_the_movie_meta_data_configuration_is(String parentLevelConfig,
			String movieMetaDataConfig) throws Throwable {
		pcs = new SimpleParentalControlService(parentLevelConfig, movieMetaDataConfig);
	}

	@When("^the customer’s parental control level is \"([^\"]*)\" and the movie is \"([^\"]*)\"$")
	public void the_customer_s_parental_control_level_is_and_the_movie_is(String parentLevelPref, String movie)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		try {
			isPermittedWatchMovie = pcs.isViewPermitted(parentLevelPref, movie);
		} catch (TitleNotFoundException tnfe) {
			exception = tnfe.getClass().getName();
		}
	}

	@Then("^The customer is able to watch the movie$")
	public void the_customer_is_able_to_watch_the_movie() throws Throwable {
		assertThat(isPermittedWatchMovie).isTrue();
	}

	@Then("^The customer is NOT able to watch the movie$")
	public void the_customer_is_NOT_able_to_watch_the_movie() throws Throwable {
		assertThat(isPermittedWatchMovie).isFalse();
	}

	@Then("^the exception should be \"([^\"]*)\"$")
	public void the_exception_should_be(String expectedException) throws Throwable {
		assertThat("com.sky.interview.pcs." + expectedException).isEqualTo(exception);
	}

}
