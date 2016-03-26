package raj.streams.pipingList1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import static java.util.Comparator.comparing;

public class DishOrganizer {
	
	public static void main(String[] args) {
		DishOrganizer dishOrganizer = new DishOrganizer();
		List<Dish> dishList = dishOrganizer.createDishes();
		List<String> lowCalorieDishes = dishOrganizer.getLowCalorieDishes(dishList, 400);
		System.out.println(lowCalorieDishes);
	}
	
	private List<String> getLowCalorieDishes(List<Dish> dishList, int cutoffcalorie) {
		//Filter the Dishes that are less than cut off value
		//Sort them by calorie value
		//Extract the name
		//Collect to a list
		List<String> result = null;
		result = dishList.stream().filter((dish) -> {
			return dish.getCalories() > cutoffcalorie;
		}).sorted(Comparator.comparing(Dish::getCalories)).map(Dish::getName).collect(Collectors.toList());
		
		//TO understand the sequence of operations
		result = dishList.stream().filter((dish) -> {
			System.out.println("filtering "+ dish.getName());
			return dish.getCalories() > cutoffcalorie;
		}).map(dish -> {
			System.out.println("mapping "+dish.getName());
			return dish.getName();
		}).limit(3).collect(Collectors.toList());
		System.out.println("To understand sequence "+ result);
		
		result = dishList.stream()
				.filter(dish -> dish.getCalories() < cutoffcalorie)
				.sorted(comparing(Dish::getCalories))
				.map(Dish::getName).collect(toList());
		return result;
	}
	
	private List<Dish> createDishes() {
		List<Dish> result = new ArrayList<>(10);
		result.add(new Dish("A", 3423));
		result.add(new Dish("B", 43));
		result.add(new Dish("C", 67));
		result.add(new Dish("D", 123));
		result.add(new Dish("E", 86));
		result.add(new Dish("F", 213));
		result.add(new Dish("G", 987));
		result.add(new Dish("H", 34));
		result.add(new Dish("I", 98));
		result.add(new Dish("J", 978));
/*		result.add(new Dish("K", 56));
		result.add(new Dish("L", 89));
		result.add(new Dish("M", 1278));
		result.add(new Dish("N", 5489));
		result.add(new Dish("O", 5690));
		result.add(new Dish("P", 128));
		result.add(new Dish("Q", 5423));
		result.add(new Dish("R", 98));
		result.add(new Dish("S", 125));
		result.add(new Dish("T", 983));
		result.add(new Dish("U", 22));
		result.add(new Dish("V", 87));
		result.add(new Dish("W", 121));
		result.add(new Dish("X", 87));
		result.add(new Dish("Y", 322));
		result.add(new Dish("Z", 872));*/
		return result;
	}
	
	class Dish {
		private String name;
		private int calories;
		/**
		 * @param name
		 * @param calories
		 */
		public Dish(String name, int calories) {
			super();
			this.name = name;
			this.calories = calories;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getCalories() {
			return calories;
		}
		public void setCalories(int calories) {
			this.calories = calories;
		}
		
	}

}
