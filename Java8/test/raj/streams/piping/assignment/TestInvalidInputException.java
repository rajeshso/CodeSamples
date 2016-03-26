/**
 * 
 */
package raj.streams.piping.assignment;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.Ignore;

/**
 * @author Rajesh
 *
 */
public class TestInvalidInputException {

	/**
	 * Test method for {@link raj.streams.piping.assignment.InvalidInputException#InvalidInputException(java.lang.String)}.
	 */
	@Test
	public final void testInvalidInputException() {
		InvalidInputException iie = new InvalidInputException("Hello");
		assertThat(iie.getMessage()).isEqualTo("Hello");
	}

}
