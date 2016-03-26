package com.worldpay.numberutil;

/**
 * This is a program that prints out a number supplied as an integer, in words.
 * 
 * Limit supported by the program is 0 to 999,999,999
 * 
 * 
 * @author Rajesh
 */
public class NumericWord {
	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 999999999;
	private static final String HYPHEN = "-";
	private static final String EMPTY_STRING = "";
	private static final String COMMA = ", ";
	private static final String MILLION = " Million ";
	private static final String THOUSAND = " Thousand ";
	private static final String HUNDRED = " Hundred";
	private static final String CONJUNCTION = " and ";
	private final static String[] UNITS = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
			"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
			"Nineteen" };
	private final static String[] TENS = { EMPTY_STRING, EMPTY_STRING, "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty",
			"Ninety" };
	private final static String ERROR = "E"; 

    /**
     * Given a number, the equivalent word is returned.
     * 
     * @param n The number should be within the supported range
     * 
     * @return The number in words is returned. 
     */
	public static String convert(int i) throws InvalidNumberException {
		checkIntegerRange(i);
		if (i < 20) {
			return UNITS[i];
		}else if (i < 100) { 
			return TENS[i / 10] + ((i % 10 > 0) ? HYPHEN + convert(i % 10) : EMPTY_STRING);
		}else if (i < 1000) { 
			return UNITS[i / 100] + HUNDRED + ((i % 100 > 0) ? CONJUNCTION + convert(i % 100) : EMPTY_STRING);
		}else if (i < 1000000) { 
			return convert(i / 1000) + THOUSAND + ((i % 1000 > 0) ? COMMA + convert(i % 1000) : EMPTY_STRING);
		}else if (i < 1000000000) { 
			return convert(i / 1000000) + MILLION + ((i % 1000000 > 0) ? COMMA + convert(i % 1000000) : EMPTY_STRING);
		}else {
			return ERROR;
		}
	}


    /**
     * This is a circuit breaker. A correct input ensures reliability of the 
     * program. 
     * The value is checked for integers and if the value is within the supported range
     * 
     * @param value The input value
     * 
     * @return true, if the value is supported by this program
     * 
     * @exception if the value is out of range
     */
    private static void checkIntegerRange(int n) throws InvalidNumberException {
   		if ( (n<MIN_VALUE) || (n>MAX_VALUE) ) {
    			throw new InvalidNumberException(n+ " out of range.");
    		}
    }
}
