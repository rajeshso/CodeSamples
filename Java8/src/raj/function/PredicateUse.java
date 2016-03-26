package raj.function;

import java.util.Arrays;

import java.util.List;
import java.util.function.Predicate;

/**
 * @FunctionalInterface 
 * public interface Predicate<T> 
 * Represents a predicate boolean-valued function) of one argument. 
 * This is a functional interface whose functional method is test(Object).
 * 
 * @author Rajesh
 *
 */
public class PredicateUse {
    private static final int MAX_INT = 5;
    
	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		printFilter(nums, (x) -> {
			System.out.print(" hi ");
			return x > MAX_INT;
		});
		System.out.println();
		printFilter(nums, (x) -> x > MAX_INT);
		
		System.out.println();
		printFilter(nums, (x) -> (x > MAX_INT));

		System.out.println();
		printFilter(nums, (Integer x) -> x > MAX_INT);
	}
	
	private static void printFilter(List<Integer> nums, Predicate<Integer> condition) {
		for (Integer num : nums) {
			if (condition.test(num))
				System.out.print(" "+num);
		}
	}
}
