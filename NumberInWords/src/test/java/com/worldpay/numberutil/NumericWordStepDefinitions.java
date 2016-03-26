package com.worldpay.numberutil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.worldpay.numberutil.InvalidNumberException;
import com.worldpay.numberutil.NumericWord;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class NumericWordStepDefinitions {
	
	private int numberInput;

	@Given("^the input is (\\d+)$")
	public void enterInput(int inp) throws Throwable {
		numberInput = inp;
	}

	@Then("^the output should be \"([^\"]*)\"$")
	public void checkEquivalentNumberInEnglish(String expectedEnglish) throws Throwable {
		String actualEnglishResponse = NumericWord.convert(numberInput);
		assertThat(actualEnglishResponse).isEqualTo(expectedEnglish);
	}

	@Then("^the exception should have a message \"([^\"]*)\"$")
	public void checkExceptionMessage(String expectedErrorMessage) throws Throwable {
		try {
			NumericWord.convert(numberInput);
			fail("Error message is expected here.");
		}catch(InvalidNumberException ine) {
			assertThat(ine.getMessage()).isEqualTo(expectedErrorMessage);
		}
	}

}
