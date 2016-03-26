/**
 * 
 */
package com.pulselive.league;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

/**
 * @author Rajesh
 *
 */
public class TestInvalidInputException {

	/**
	 * Test method for {@link com.pulselive.league.InvalidInputException#InvalidInputException(java.lang.String)}.
	 */
	@Test
	public final void testInvalidInputException() {
		InvalidInputException iie = new InvalidInputException("Hello");
		assertThat(iie.getMessage()).isEqualTo("Hello");
	}

}
