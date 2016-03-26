package com.trurating.touchpoint;
import static java.text.NumberFormat.getCurrencyInstance;
import static java.util.Locale.UK;

import com.trurating.util.NumericWord;

/**
 * The program is command prompt driven. The usage is java -jar NumericWord.jar <integer>
 * 
 * @author Rajesh
 *
 */
public class NumberToWordConverter {
    /**
     * This is the main entrance of the program. The user is expected to 
     * provide the input in the args[0] as a command line argument. 
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java -jar NumberToWordConverter.jar <integer>");
        }else {
        	print(args);        	
        }
    }
    /**
     * This program utilizes the NumericWord utility to convert the given value as British pound currency equivalent.
     * 
     * @param args the integer as a String
     */
	private static void print(String[] args) {
		String nString = NumericWord.replaceComma(args[0]);
		int n;
		boolean isValidNumber = false;
		try {
			isValidNumber = NumericWord.isNumeric(nString);
			if (!isValidNumber) 
				System.out.printf("%s is not a valid number for conversion. %n", nString);
		}catch(Exception e) {
			System.out.printf("%s is out of range %n", nString); 
		}
		if (isValidNumber) {
		    n = Integer.parseInt(nString);
			String nWords = NumericWord.convert(n);
		    if (nWords!=null) 
		        System.out.printf("%s in words is%s %s %n", getCurrencyInstance(UK).format(n), nWords, getCurrencyInstance(UK).getCurrency().getDisplayName()); 
		    else 
		        System.out.printf("%d in words is not supported %n", n);     
		}
	}
}
