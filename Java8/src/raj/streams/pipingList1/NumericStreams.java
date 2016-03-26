package raj.streams.pipingList1;

import java.util.List;

import raj.streams.pipingList1.Domain.Dish;

public class NumericStreams {
	public static void main(String[] dsfs) {
		Domain domain = new Domain();
		List<Dish> menu = domain.createMenu();

		// sum of calories
		int sum1 = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
		System.out.println(sum1);
		
		//IntStream, DoubleStream, and LongStream avoid hidden boxing costs
		int sum2 = menu.stream().mapToInt(Dish::getCalories).sum();
		System.out.println(sum2);
		
		
	}
}
