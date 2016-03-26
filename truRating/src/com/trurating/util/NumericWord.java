package com.trurating.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;

/**
 * This is a program that prints out a number supplied as an integer, in words.
 * An example of this task would be if you were writing a program to print out a
 * bank cheque. The amount has to appear in words on the left, and in digits on 
 * the right. For instance, if the amount were 123 the words printed would be 
 * one hundred and twenty three.
 * 
 * Limit supported by the program is 0 to 999,999
 * 
 * 
 * @author Rajesh
 */
public class NumericWord {
	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 999999;
    private static final Pattern INTEGER_PATTERN = compile("^[0-9]*$");
    private static final String[] UNIT0_9STRINGS = {" zero", " one", " two", " three", " four", " five", " six", " seven", " eight", " nine"};
    private static final String[] TENS10_19STRINGS = {" ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen"};

    private static final String[] TENS_STRINGS = {"", "ten", " twenty", " thirty", " forty", " fifty", " sixty", " seventy", " eighty", " ninety"};
    private static final String HUNDRED_STRING = " hundred";
    private static final String THOUSAND_STRING = " thousand";
    private static final String CONJUNCTION = " and";
    private static final String HYPHEN = "-";
    private static final String COMMA = ",";

    private enum Range {UNITS, TEENS, TENS, HUNDREDS, THOUSANDS, TEN_THOUSANDS, HUNDRED_THOUSANDS, OUT_OF_RANGE};
    /**
     * Given a number, the equivalent word is printed on the console.
     * 
     * @param n The number should be within the supported range
     * 
     * @return The number in words is returned. 
     */
    public static String convert(int n) {
        String nWords=null;
        Range range = getRange(n);
        switch (range) {
            case UNITS:
                nWords = getUnit(n);
                break;
            case TEENS:
                nWords = getTenTeen(n);
                break;
            case TENS:
                nWords = getTens(n);
                break;
            case HUNDREDS:
                nWords = getHundreds(n);
                break;
            case THOUSANDS:
                nWords = getThousands(n);
                break;
            case TEN_THOUSANDS:
                nWords = getTenThousands(n);
                break;
            case HUNDRED_THOUSANDS:
                nWords = getHundredThousands(n);
                break;
            case OUT_OF_RANGE:
                nWords = "";
                break;                
        }
        return nWords;
    }                 
    /**
     * This is a utility method to find the words for the ten thousands of the 
     * given number. For example, 100,123, is returned as one hundred 
     * thousands one hundred and twenty three. 
     * 
     * @param n The input number 
     * @return The hundred thousands of the number
     */
    private static String getHundredThousands(int n) {
        if (n < 100000) {
            return getTenThousands(n);
        }               
        String words;
        int hundred = (n % 1000);
        int hundredThousands = n / 1000;
        words = getHundreds(hundredThousands) + THOUSAND_STRING;
        if (hundred!=0)
            words = words + COMMA + getHundreds(hundred);
        return words;
    }

    /**
     * This is a utility method to find the words for the thousands of the 
     * given number. For example, 10,012, is returned as ten 
     * thousands and twelve. 
     * 
     * @param n The input number greater
     * 
     * @return The ten thousands of the number
     */    
    private static String getTenThousands(int n) {
        if (n < 10000) {
            return getThousands(n);
        }        
        String words;
        int hundred = (n % 1000);
        int tenthousands = n / 1000;
        words = getTens(tenthousands) + THOUSAND_STRING;
        if (hundred!=0) 
            words = words + COMMA+ getHundreds(hundred);
        return words;
    }

    /**
     * This is a utility method to find the words for the thousands of the 
     * given number. For example, 1,012, is returned as  
     * thousands and twelve. 
     * 
     * @param n The input number greater
     * 
     * @return The thousands of the number
     */ 
    private static String getThousands(int n) {
        if (n < 1000) {
            return getHundreds(n);
        }
        int hundreds;
        int thousands;
        String words;
        hundreds = (n % 1000);
        thousands = (n / 1000);
        words = getUnit(thousands) + THOUSAND_STRING;
        if ( (hundreds!=0) && (hundreds<100))
            words= words + CONJUNCTION + getHundreds(hundreds);
        else if ( (hundreds!=0)) {
        	words= words + COMMA+ getHundreds(hundreds);
        }
        return words;
    }
   
    /**
     * This is a utility method to find the words for the hundreds of the 
     * given number. For example, 112, is returned as one 
     * hundred and twelve. 
     * 
     * @param n The input number 
     * 
     * @return The hundreds of the number
     */ 
    private static String getHundreds(int n) {
        if (n < 100) {
            return getTens(n);
        }
        int units;
        int tens;
        String words;
        units = (n % 100);
        tens = (n / 100);
        words = getUnit(tens) + HUNDRED_STRING;
        if (units!=0)
            words = words + CONJUNCTION + getTens(units);
        return words;
    }
    /**
     * This is a utility method to find the words for the thousands of the 
     * given number. For example, 21, is returned as twenty one. This method focuses 
     * on the range 20-99.
     * 
     * @param n The input number greater than than 19 and lesser than 99
     * 
     * @return The tens of the number
     */ 
    private static String getTens(int n) {
        if (n < 20) {
            return getTenTeen(n);
        }
        int unit;
        int tens;
        String words;
        unit = n % 10;
        tens = n / 10;
        words = TENS_STRINGS[tens];
        if (unit!=0)
            words = words+ HYPHEN+ UNIT0_9STRINGS[unit].trim();
        return words;
    }
    /**
     * The numbers from 11 to 19 have a different suffix than the 20 to 99.
     * The teen range numbers are handled by this function.
     * 
     * @param n A number in the range of 11 - 19.
     * 
     * @return The teen words 
     */
    private static String getTenTeen(int n) {
        if (n < 10) {
            return getUnit(n);
        }
        int unit;
        String words;
        unit = n % 10;
        words = TENS10_19STRINGS[unit];
        return words;
    }

    /**
     * The unit of the number is returned. For example, 9 is returned as Nine.
     * 
     * @param n The number in the range 0-9
     * 
     * @return The equivalent word
     */
    private static String getUnit(int n) {
        int unit;
        String words;
        unit = n % 10;
        words = UNIT0_9STRINGS[unit];
        return words;
    }
    
    /**
     * This is an internal utility method to figure out the range of the number.
     * This is useful to switch which of the methods to invoke.
     * 
     * @param n input
     * @return Range
     */
    private static Range getRange(int n) {
        if (n < 10) {
            return Range.UNITS;
        } else if (n < 20) {
            return Range.TEENS;
        } else if (n < 100) {
            return Range.TENS;
        } else if (n < 1000) {
            return Range.HUNDREDS;
        } else if (n < 10000) {
            return Range.THOUSANDS;
        } else if (n < 100000) {
            return Range.TEN_THOUSANDS;
        } else if (n < 1000000) {
            return Range.HUNDRED_THOUSANDS;
        }
        return Range.OUT_OF_RANGE;
    }
    /**
     * This is a validation method for the input. An incorrect input can bring down the 
     * program. Hence, a stringent validation is performed by this method.
     * The value is checked for integers and if the value is withing the suppoerted range
     * 
     * @param value The input value
     * 
     * @return true, if the value is supported by this program
     * 
     * @exception if the value is out of range
     */
    public static boolean isNumeric(String value) throws Exception {
    	Matcher integerMatcher = INTEGER_PATTERN.matcher(value);
    	boolean isNumber = integerMatcher.matches();
    	if (isNumber) {
    		long n = Long.parseLong(value);
    		if ( (n<MIN_VALUE) || (n>MAX_VALUE) ) {
    			throw new Exception(value+ " out of range.");
    		}
    	}
    	return isNumber;
    }
    
    /**
     * Currency values sometimes are presented with comma. This function
     * removes the comma for numeric calculation.
     * 
     * @param value
     * 
     * @return value without comma
     */
    public static String replaceComma(String value) {
    	return value.replaceAll(COMMA, "");
    }
}
