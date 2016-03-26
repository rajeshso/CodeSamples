package raj.streams.pipingList1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class FilterAndSlice {

	public static void main(String[] args) {
		//Terminal operations - forEach
		List<String> a = Arrays.asList("a", "b", "c");
		a.stream().forEach(System.out::println);
		System.out.println("--------------------------");
		
		//Filtering with Predicates
		FilterAndSlice filterAndSlice = new FilterAndSlice();
		List<Dish> menu = filterAndSlice.createMenu();
		List<Dish> vegetarianMenu = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
		vegetarianMenu = menu.stream().filter(dish -> dish.isVegetarian()).collect(Collectors.toList());
		System.out.println("--------------------------");
		
		//Filtering for unique elements
		List<Integer> intList = Arrays.asList(1,2,3,4,4,3,2,1);
		intList.stream().distinct().forEach(System.out::println);
		System.out.println("--------------------------");
		
		//Truncating a stream
		//menu.stream().filter(Dish::getCalories > 300) - Incorrect predicate
		menu.stream().filter(d -> d.getCalories()>300).limit(3).forEach(System.out::println);
		System.out.println("--------------------------");
		
		//Truncating a stream on a Set - order would not be maintained
		Set<Dish> s = new HashSet<>();
		s.addAll(menu);
		s.stream().filter(d-> d.getCalories()>300).limit(3).forEach(System.out::println);
		System.out.println("--------------------------");
		
		//Skipping elements - skip(n) method to return a stream that discards the first n elements
		menu.stream().filter(d -> d.getCalories()>300).skip(2).forEach(System.out::println);
		System.out.println("--------------------------");
		
		//Filter the first 2 meat dishes
		menu.stream().filter(d -> d.getType()==Type.MEAT).limit(2).forEach(System.out::println);
		System.out.println("--------------------------");
	}
	public static enum Type {
		MEAT, FISH, OTHER
	}

	private List<Dish> createMenu() {
		List<Dish> menu = Arrays.asList(
				new Dish("pork", false, 800, Type.MEAT),
				new Dish("beef", false, 700, Type.MEAT),
				new Dish("chicken", false, 400, Type.MEAT),
				new Dish("french fries", true, 530, Type.OTHER),
				new Dish("rice", true, 350, Type.OTHER),
				new Dish("season fruit", true, 120, Type.OTHER),
				new Dish("pizza", true, 550, Type.OTHER),
				new Dish("prawns", false, 300, Type.FISH),
				new Dish("salmon", false, 450, Type.FISH) );
		return menu;
	}

	private class Dish {
		private String name;
		private boolean vegetarian;
		private int calories;
		public Type type;



		/**
		 * @param name
		 * @param vegetarian
		 * @param calories
		 * @param type
		 */
		public Dish(String name, boolean vegetarian, int calories, Type type) {
			super();
			this.name = name;
			this.vegetarian = vegetarian;
			this.calories = calories;
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isVegetarian() {
			return vegetarian;
		}

		public void setVegetarian(boolean vegetarian) {
			this.vegetarian = vegetarian;
		}

		public int getCalories() {
			return calories;
		}

		public void setCalories(int calories) {
			this.calories = calories;
		}
		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}
		public String toString() {
			return name;
		}
	}
}
