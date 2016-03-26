package capitaAssignment.address;
/**
 * The Unit tests for the Address Validator
 */


import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rajesh
 *
 */
public class TestAddressValidator {

	private String fileName = null;
	
	@Before
	public void setup() {
        fileName = System.getProperty("fileName");
        System.out.println("System.getProperty(\"fileName\") is " + fileName);
	}
	
	@Test
	public void testLoadConstants() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.loadConstants(fileName);
			assertEquals("\\d{1,3}", instance.getBuildingNumPattern().pattern());
			assertEquals(4, instance.getAddressLenMin());
			assertEquals(30, instance.getAddressLenMax());
			assertEquals("[a-zA-Z]{2}\\d{2,3}[a-zA-Z]{2}", instance.getUkPostCodePattern()
					.pattern());
			assertEquals("\\d{5}", instance.getUsPostCodePattern().pattern());

		}


	@Test
	public void testValidateLengths() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("3");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean lengthMatched = instance.validateAddressLineLengths();
			assertEquals(true, lengthMatched);

	}

	@Test
	public void testValidateAddressLine1WithLessThanMinLen() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("3");
			instance.setAddressLine1Value("L");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean lengthMatched = instance.validateAddressLineLengths();
			assertEquals(false, lengthMatched);
	}

	@Test
	public void testValidateAddressLine2WithLessThanMinLen() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("3");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("L");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean lengthMatched = instance.validateAddressLineLengths();
			assertEquals(false, lengthMatched);
	}

	@Test
	public void testValidateAddressLinesWithLessThanMinLen() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("3");
			instance.setAddressLine1Value("L");
			instance.setAddressLine2Value("L");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean lengthMatched = instance.validateAddressLineLengths();
			assertEquals(false, lengthMatched);

	}

	@Test
	public void testValidateAddressLine1WithLessThanMaxLen() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("3");
			instance.setAddressLine1Value("1234567890123456789012345678901234567890");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean lengthMatched = instance.validateAddressLineLengths();
			assertEquals(false, lengthMatched);

	}

	@Test
	public void testValidateAddressLine2WithLessThanMaxLen() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("3");
			instance.setAddressLine1Value("London");
			instance.setAddressLine2Value("1234567890123456789012345678901234567890");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean lengthMatched = instance.validateAddressLineLengths();
			assertEquals(false, lengthMatched);

	}

	@Test
	public void testValidateAddressLinesWithLessThanMaxLen() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("3");
			instance.setAddressLine1Value("1234567890123456789012345678901234567890");
			instance.setAddressLine2Value("1234567890123456789012345678901234567890");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean lengthMatched = instance.validateAddressLineLengths();
			assertEquals(false, lengthMatched);
	}

	@Test
	public void testValidateBuildingNumber() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("3");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123DU");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(true, patternMatched);
	}

	@Test
	public void testValidateAlphabetBuildingNumber() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("A");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);

	}

	@Test
	public void testValidateLongLengthBuildingNumber() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("1234567890");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateTwoDigitLengthBuildingNumber() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("12");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE12DU");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(true, patternMatched);
	}

	@Test
	public void testValidateThreeDigitLengthBuildingNumber() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123qw");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(true, patternMatched);
	}

	@Test
	public void testValidateHexadecimalBuildingNumber() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("0x999");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateSpaceBuildingNumber() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("   ");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateSpecialCharBuildingNumber() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("$$%");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateUKPostCode() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123pL");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(true, patternMatched);
	}

	@Test
	public void testValidateIncorrectUKPostCode1() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("ZQONE");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateIncorrectUKPostCode2() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("12EWE");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateIncorrectUKPostCode3() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("aa 123");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateIncorrectUKPostCode4() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE1234556");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateIncorrectUKPostCode5() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EEE12");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateIncorrectUKPostCode6() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("KJHGDSKJDHSKDJHSD");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}
	@Test
	public void testValidateIncorrectUKPostCode7() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("Ee123iIi");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}
	@Test
	public void testValidateUSPostCode() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("White house");
			instance.setAddressLine2Value("NewYork");
			instance.setAddressLine3Value("United States");
			instance.setPostalCodeValue("12345");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(true, patternMatched);
	}

	@Test
	public void testValidateIncorrectUSPostCode1() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("White house");
			instance.setAddressLine2Value("NewYork");
			instance.setAddressLine3Value("United States");
			instance.setPostalCodeValue("1234567890");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateIncorrectUSPostCode2() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("White house");
			instance.setAddressLine2Value("NewYork");
			instance.setAddressLine3Value("United States");
			instance.setPostalCodeValue("1");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateIncorrectUSPostCode3() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("White house");
			instance.setAddressLine2Value("NewYork");
			instance.setAddressLine3Value("United States");
			instance.setPostalCodeValue("12 23");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateIncorrectUSPostCode4() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("White house");
			instance.setAddressLine2Value("NewYork");
			instance.setAddressLine3Value("United States");
			instance.setPostalCodeValue("1234");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateIncorrectUSPostCode5() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("White house");
			instance.setAddressLine2Value("NewYork");
			instance.setAddressLine3Value("United States");
			instance.setPostalCodeValue("0x1234");
			boolean patternMatched = instance.validatePatterns();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateMandatoryValues() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validateMandatoryValues();
			assertEquals(true, patternMatched);
	}

	@Test
	public void testValidateMandatoryValuesWithoutBuildingNum() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validateMandatoryValues();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateMandatoryValuesWithoutAddressLine1() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validateMandatoryValues();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateMandatoryValuesWithoutAddressLine2() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validateMandatoryValues();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateMandatoryValuesWithoutPostalCode() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			// instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validateMandatoryValues();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateMandatoryValuesWithNoMandatoryValues() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setAddressLine3Value("United Kingdom");
			boolean patternMatched = instance.validateMandatoryValues();
			assertEquals(false, patternMatched);
	}

	@Test
	public void testValidateMandatoryValuesWithNoOptionalValues() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			// instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean patternMatched = instance.validateMandatoryValues();
			assertEquals(true, patternMatched);
	}

	@Test
	public void testApplyBusinessRules() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123XY");
			boolean businessRulesMatched = instance.validateAddress();
			assertEquals(true, businessRulesMatched);
	}

	@Test
	public void testApplyBusinessRulesWithoutMandatoryValues() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			// instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean businessRulesMatched = instance.validateAddress();
			assertEquals(false, businessRulesMatched);
	}

	@Test
	public void testApplyBusinessRulesWithBuildingNumIncorrect() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("1234");
			instance.setAddressLine1Value("Lathom Road");
			instance.setAddressLine2Value("London");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean businessRulesMatched = instance.validateAddress();
			assertEquals(false, businessRulesMatched);
	}

	@Test
	public void testApplyBusinessRulesWithAddressLinesIncorrect() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("123");
			instance.setAddressLine1Value("L");
			instance.setAddressLine2Value("L");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("EE123");
			boolean businessRulesMatched = instance.validateAddress();
			assertEquals(false, businessRulesMatched);
	}

	@Test
	public void testApplyBusinessRulesWithIncorrectPatternsAndLengths() {
			AddressValidator instance = new AddressValidator(fileName);
			instance.setBuildingNumberValue("!@#");
			instance.setAddressLine1Value("L");
			instance.setAddressLine2Value("L");
			instance.setAddressLine3Value("United Kingdom");
			instance.setPostalCodeValue("@##@");
			boolean businessRulesMatched = instance.validateAddress();
			assertEquals(false, businessRulesMatched);
	}
}
