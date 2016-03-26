package capitaAssignment.address;

/**
 * This package holds the solution for Question 1 of the Assignment 
 *
 */

import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The Question1 is the solution for the Question1 of the Assignment. In order
 * to instantiate the Question1, the accompanying properties file has to be
 * placed in a convenient location and this class has to be invoked by java
 * RegexCapita C:\capita.properties
 * 
 * @author Rajesh
 *
 */
public final class AddressValidator {
	// Default pattern and length values
	private static final Pattern BUILDING_NUMBER_DEFAULT_PATTERN = Pattern
			.compile("\\d{1,3}");
	private static final int ADDRESS_LEN_DEFAULT_MIN = 4;
	// Not in Requirements. It is entered to match with MIN with MAX
	private static final int ADDRESS_LEN_DEFAULT_MAX = 30;
	private static final Pattern UK_POSTCODE_DEFAULT_PATTERN = Pattern
			.compile("[a-zA-Z]{2}\\d{2,3}[a-zA-Z]{2}");
	private static final Pattern US_POSTCODE_DEFAULT_PATTERN = Pattern
			.compile("\\d{5}");
	private static final String ADDRESS_RANGE_DELIMITER = "-";

	// Mapping keys in the properties file
	private static final String BUILDING_NUMBER_PATTERN_KEY = "BUILDING_NUMBER_PATTERN";
	private static final String ADDRESS_LEN_RANGE_KEY = "ADDRESS_LEN_RANGE";
	private static final String UK_POSTCODE_PATTERN_KEY = "UK_POSTCODE_PATTERN";
	private static final String US_POSTCODE_PATTERN_KEY = "US_POSTCODE_PATTERN";

	private Pattern buildingNumPattern, ukPostCodePattern,
			usPostCodePattern = null;
	private int addressLenMin, addressLenMax = 0;

	private String buildingNumberValue = null;
	private String addressLine1Value, addressLine2Value,
			addressLine3Value = null;
	private String postalCodeValue = null;

	AddressValidator() {
	}

	/**
	 * Instantiates the object with the properties file
	 * 
	 * @param file
	 *            : The properties file contains the patterns and length for the
	 *            validation
	 */
	AddressValidator(String file) {
		this.loadConstants(file);
	}

	/**
	 * This operation moves the state of this object to ready to take user
	 * input. The patterns and the rules are initialised.
	 * 
	 * The properties file has to conform to the following name value pair
	 * convention { BUILDING_NUMBER_PATTERN=\\d{1,3}, ADDRESS_LEN_RANGE=4-30,
	 * UK_POSTCODE_PATTERN=[a-zA-Z]{2}\\d{2,3}, US_POSTCODE_PATTERN=\\d{5} }
	 * 
	 * The patterns have to be regex. The length has to be in a range.
	 * 
	 * If the values do not conform to the specified format or incorrect, the
	 * default values are assigned.
	 * 
	 * @param file
	 *            The name and location of the properties file
	 */
	void loadConstants(String file) {
		Properties prop = new Properties();
		try {
			FileInputStream input = new FileInputStream(file);
			prop.load(input);
			String buildingNumberPatternStr = prop
					.getProperty(BUILDING_NUMBER_PATTERN_KEY);
			if (!isNull(BUILDING_NUMBER_PATTERN_KEY, buildingNumberPatternStr)
					&& isRegexInvalid(BUILDING_NUMBER_PATTERN_KEY,
							buildingNumberPatternStr)) {
				buildingNumPattern = Pattern.compile(buildingNumberPatternStr);
			}

			String addressLenRange = prop.getProperty(ADDRESS_LEN_RANGE_KEY);
			if (!isNull(ADDRESS_LEN_RANGE_KEY, addressLenRange)
					&& !isAddressRangeInvalid(ADDRESS_LEN_RANGE_KEY,
							addressLenRange)) {
				StringTokenizer tokenizer = new StringTokenizer(
						addressLenRange, ADDRESS_RANGE_DELIMITER);
				addressLenMin = Integer.parseInt(tokenizer.nextToken());
				addressLenMax = Integer.parseInt(tokenizer.nextToken());
			}

			String ukPostcodePatternStr = prop
					.getProperty(UK_POSTCODE_PATTERN_KEY);
			if (!isNull(UK_POSTCODE_PATTERN_KEY, ukPostcodePatternStr)
					&& isRegexInvalid(UK_POSTCODE_PATTERN_KEY,
							ukPostcodePatternStr)) {
				ukPostCodePattern = Pattern.compile(ukPostcodePatternStr);
			}
			String usPostcodePatternStr = prop
					.getProperty(US_POSTCODE_PATTERN_KEY);
			if (!isNull(US_POSTCODE_PATTERN_KEY, usPostcodePatternStr)
					&& isRegexInvalid(US_POSTCODE_PATTERN_KEY,
							usPostcodePatternStr)) {
				usPostCodePattern = Pattern.compile(usPostcodePatternStr);
			}
		} catch (IOException ioe) {
			System.out.println("Warning: " + file
					+ " is invalid. So default values are assigned");
		}

		// If incorrect input is provided in the file, assign default values
		if (buildingNumPattern == null) {
			buildingNumPattern = BUILDING_NUMBER_DEFAULT_PATTERN;
		}
		if (addressLenMin == 0 || addressLenMax == 0) {
			addressLenMin = ADDRESS_LEN_DEFAULT_MIN;
			addressLenMax = ADDRESS_LEN_DEFAULT_MAX;
		}
		if (ukPostCodePattern == null) {
			ukPostCodePattern = UK_POSTCODE_DEFAULT_PATTERN;
		}
		if (usPostCodePattern == null) {
			usPostCodePattern = US_POSTCODE_DEFAULT_PATTERN;
		}
	}

	private static final boolean isNull(String paramName, Object paramValue)

	{
		if (paramValue == null) {
			System.out.println("Warning: " + paramName
					+ " is null. So default value is assigned");
			return true;
		}
		return false;

	}

	private static final boolean isRegexInvalid(String paramName,
			Object paramValue) {
		try {
			Pattern.compile(paramName);
		} catch (PatternSyntaxException e) {
			System.out.println("Warning: The regex for the " + paramName
					+ " is invalid. So default value is assigned");
			return true;
		}
		return false;
	}

	private static final boolean isAddressRangeInvalid(String paramName,
			Object paramValue) {
		String addressLenRange = paramValue.toString();
		StringTokenizer tokenizer = new StringTokenizer(addressLenRange,
				ADDRESS_RANGE_DELIMITER);
		try {
			if (tokenizer.countTokens() != 2) {
				System.out.println("Warning: The Address Range " + paramName
						+ " format is invalid. So default values are assigned");
				return true;
			}
			Integer.parseInt(tokenizer.nextToken());
			Integer.parseInt(tokenizer.nextToken());
		} catch (Exception e) {
			System.out.println("Warning: The Address Range " + paramName
					+ " is invalid. So default values are assigned");
			return true;
		}
		return false;
	}

	/**
	 * The method helps to gather User input from the Console. It is recommended
	 * to run this method from a command prompt
	 */
	private void recieveInput() {
		Console console = System.console();
		if (console == null) {
			System.err
					.println("Cannot retrieve the console. Are you running the application from an IDE? Or does your OS permissions prohibit console input?  Exiting..");
			System.exit(-1);
		}
		console.printf("\nEnter the building number* : ");
		this.buildingNumberValue = console.readLine();
		console.printf("Enter the Address Line1* : ");
		this.addressLine1Value = console.readLine();
		console.printf("Enter the Address Line2* : ");
		this.addressLine2Value = console.readLine();
		console.printf("Enter the Address Line3 : ");
		this.addressLine3Value = console.readLine();
		console.printf("Enter the Post code* : ");
		this.postalCodeValue = console.readLine();
	}

	/**
	 * The validation mentioned in the Question 1 is carried out in this
	 * operation. The result is printed in the console.
	 * 
	 * @return true if the user input for the business rules are successful.
	 */
	boolean validateAddress() {
		System.out.println("The result of the Validation is :");
		boolean mandatoryCheckPassed = validateMandatoryValues();
		if (!mandatoryCheckPassed) {
			System.out.println("Mandatory Values validation failed.");
			return false;
		} else {
			System.out.println("Mandatory Values validation passed.");
		}
		boolean addressLenCheckPassed = validateAddressLineLengths();
		if (!addressLenCheckPassed) {
			System.out.println("Address Length validation failed.");
		} else {
			System.out.println("Address Length validation passed.");
		}
		boolean buildingNumberPostalCheckPassed = validatePatterns();
		if (!buildingNumberPostalCheckPassed) {
			System.out
					.println("Building number and postal code validation failed.");
		} else {
			System.out
					.println("Building number and postal code validation passed.");
		}
		if (!(mandatoryCheckPassed & addressLenCheckPassed & buildingNumberPostalCheckPassed)) {
			System.out
					.println("Please refer the capita.properties file for the format of the entry values");
		}
		return mandatoryCheckPassed & addressLenCheckPassed
				& buildingNumberPostalCheckPassed;
	}

	/**
	 * This method checks if all the mandatory values are provided. The
	 * mandatory values are specified in the requirements.
	 * 
	 * @return true if the mandatory values are provided
	 */
	boolean validateMandatoryValues() {
		boolean ok = true;
		if (buildingNumberValue == null
				|| buildingNumberValue.trim().length() == 0) {
			System.out
					.println("Building Number is mandatory. The value is missing");
			if (ok)
				ok = false;
		}
		if (addressLine1Value == null || addressLine1Value.trim().length() == 0) {
			System.out
					.println("Address Line1 is mandatory. The value is missing");
			if (ok)
				ok = false;
		}
		if (addressLine2Value == null || addressLine2Value.trim().length() == 0) {
			System.out
					.println("Address Line2 is mandatory. The value is missing");
			if (ok)
				ok = false;
		}
		if (postalCodeValue == null || postalCodeValue.trim().length() == 0) {
			System.out.println("Postal code mandatory. The value is missing");
			if (ok)
				ok = false;
		}
		return ok;
	}

	/**
	 * The requirement specified that building number and post codes have a
	 * fixed pattern. The pattern is entered in the capita.properties file. This
	 * method validates if the given values conform to the patterns. The
	 * building number and post codes are validated by this method
	 * 
	 * @return true if building number and post codes conform to pattern
	 */
	boolean validatePatterns() {
		boolean ok = true;
		Matcher matcher;
		Matcher ukPostalMatcher, usPostalMatcher;
		matcher = buildingNumPattern.matcher(buildingNumberValue);
		if (!matcher.matches()) {
			System.out.printf(
					"Building number %s does not conform to the pattern %s .",
					buildingNumberValue, buildingNumPattern.pattern());
			if (ok)
				ok = false;
		}
		usPostalMatcher = usPostCodePattern.matcher(postalCodeValue);
		ukPostalMatcher = ukPostCodePattern.matcher(postalCodeValue);
		if (!(usPostalMatcher.matches() || ukPostalMatcher.matches())) {
			System.out.printf(
					"Postal code %s does not match the pattern of US or UK.",
					postalCodeValue);
			if (ok)
				ok = false;
		}
		return ok;
	}

	/**
	 * The requirement specified that address lines have a variable length
	 * pattern. The length is entered in the capita.properties file. This method
	 * validates if the given values conform to the length. The address lines
	 * are validated by this method
	 * 
	 * @return true if address lines 1 and 2 conform to the length
	 */
	boolean validateAddressLineLengths() {
		boolean ok = true;
		int addressL1Len = this.addressLine1Value.length();
		int addressL2Len = this.addressLine2Value.length();
		if (addressL1Len < addressLenMin | addressL1Len > addressLenMax) {
			System.out
					.printf("Length of Address line1 %s does not conforms to the range %d - %d.",
							addressLine1Value, addressLenMin, addressLenMax);
			if (ok)
				ok = false;
		}
		if (addressL2Len < addressLenMin | addressL2Len > addressLenMax) {
			System.out
					.printf("Length of Address line2 %s does not conforms to the range %d - %d.",
							addressLine2Value, addressLenMin, addressLenMax);
			if (ok)
				ok = false;
		}
		return ok;
	}

	/**
	 * This method is invoked by the JVM. The args provide this program with the
	 * location of the capita.properties file. The properties file is expected
	 * to specify the patterns and length as provided in the requirement.
	 * 
	 * @param args
	 *            The file name and the location of the properties file. The
	 *            name is expected to be capita.properties
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out
					.println("Usage: java AddressValidator <capita properties file>");
			System.exit(-1);
		}
		AddressValidator addressValidator;
		addressValidator = new AddressValidator(args[0]);
		addressValidator.recieveInput();
		addressValidator.validateAddress();

	}

	// Design for Unit Testablity methods
	Pattern getBuildingNumPattern() {
		return buildingNumPattern;
	}

	Pattern getUkPostCodePattern() {
		return ukPostCodePattern;
	}

	Pattern getUsPostCodePattern() {
		return usPostCodePattern;
	}

	String getAddressLine1Value() {
		return addressLine1Value;
	}

	String getAddressLine2Value() {
		return addressLine2Value;
	}

	String getAddressLine3Value() {
		return addressLine3Value;
	}

	String getPostalCodeValue() {
		return postalCodeValue;
	}

	int getAddressLenMin() {
		return addressLenMin;
	}

	int getAddressLenMax() {
		return addressLenMax;
	}

	void setBuildingNumberValue(String buildingNumberValue) {
		this.buildingNumberValue = buildingNumberValue;
	}

	void setAddressLine1Value(String addressLine1Value) {
		this.addressLine1Value = addressLine1Value;
	}

	void setAddressLine2Value(String addressLine2Value) {
		this.addressLine2Value = addressLine2Value;
	}

	void setAddressLine3Value(String addressLine3Value) {
		this.addressLine3Value = addressLine3Value;
	}

	void setPostalCodeValue(String postalCodeValue) {
		this.postalCodeValue = postalCodeValue;
	}
}
