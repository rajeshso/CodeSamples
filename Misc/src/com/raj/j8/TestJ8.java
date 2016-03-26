package com.raj.j8;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public class TestJ8 {
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
	
	public static String convert(int i) {
		if (i < 20) {
			return UNITS[i];
		}else if (i < 100) { //90 or 99
			return TENS[i / 10] + ((i % 10 > 0) ? HYPHEN + convert(i % 10) : EMPTY_STRING);
		}else if (i < 1000) { //900 or 999
			return UNITS[i / 100] + HUNDRED + ((i % 100 > 0) ? CONJUNCTION + convert(i % 100) : EMPTY_STRING);
		}else if (i < 1000000) { //9999, 90000 or 99,999 or 999,999
			return convert(i / 1000) + THOUSAND + ((i % 1000 > 0) ? COMMA + convert(i % 1000) : EMPTY_STRING);
		}else if (i < 1000000000) { //9,000,000 999,999,999
			return convert(i / 1000000) + MILLION + ((i % 1000000 > 0) ? COMMA + convert(i % 1000000) : EMPTY_STRING);
		}else {
			return ERROR;
		}
	}
	public static void main(String[] args) {
/*		File[] hiddenFiles = new File("/").listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isHidden();
			}
		});
		System.out.println(hiddenFiles.length);
		System.out.println(hiddenFiles[0] + " , " + hiddenFiles[1]);
		
		hiddenFiles = new File("/").listFiles(File::isHidden);
		System.out.println(hiddenFiles.length);
		System.out.println(hiddenFiles[0] + " , " + hiddenFiles[1]);
		
		testLambdaInForEach1();
		testLambdaInForEach2();
		testLambdaInForEach3();
		testLambdaInForEach4();*/
		System.out.println(999000%1000000);   
		System.out.println(convert(999999999));
	}
    
	static class MathUtils {
		public int write(int x) {
			return x+1;
		}
	}
	
	static void testLambdaInForEach1() {
		List<Integer> integers = Arrays.asList(1,2,3,4);
		integers.forEach(x -> System.out.println(x));
	}
	static void testLambdaInForEach2() {
		List<Integer> integers = Arrays.asList(1,2,3,4);
		integers.forEach(x -> {
			x = x*10;
			System.out.println(x);	
		});
	}
	static void testLambdaInForEach3() {
		List<Integer> integers = Arrays.asList(1,2,3,4);
		integers.forEach((Integer x) -> {
			x = x*10;
			System.out.println(x);	
		});
	}
	static void testLambdaInForEach4() {
		List<Integer> integers = Arrays.asList(1,2,3,4);
		integers.forEach((Integer x) -> System.out.println(x));
	}
}
