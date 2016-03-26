package raj.streams.pipingList1;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Reducing {

	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1,2,3,4,5);
		int sum = numbers.stream().reduce(0, (a,b)-> a+b);
		int sum1 = numbers.stream().reduce(0, Integer::sum);
		int sum2 = numbers.parallelStream().reduce(0, Integer::sum);
		int facto1 = numbers.stream().reduce(1, (a,b)->a*b);
		//Count the numbers in the list
		//the zero is the initial value for the operation
		System.out.println(sum);
		System.out.println(sum1);
		System.out.println(sum2);
		System.out.println(facto1);
		System.out.println("------------------------------------");
		//Find the max and min
		Optional<Integer> max = numbers.stream().reduce(Integer::max);
		Optional<Integer> min = numbers.stream().reduce(Integer::min);
		if (max.isPresent()) System.out.println(max.get());
		if (min.isPresent()) System.out.println(min.get());
	}

}
