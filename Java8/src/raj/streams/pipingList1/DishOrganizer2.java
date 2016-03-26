package raj.streams.pipingList1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * The result should be as follows:
 * {FISH=[prawns, salmon],
	OTHER=[french fries, rice, season fruit, pizza],
	MEAT=[pork, beef, chicken]}
	
 * @author Rajesh
 *
 */
public class DishOrganizer2 {
	public static enum Type {
		MEAT, FISH, OTHER
	}

	public static void main(String[] args) {
		DishOrganizer2 d = new DishOrganizer2();
		List<Dish> menu = d.createMenu();
		///Group Dishes by Type
		Map<Type, List<Dish>> m = menu.stream().collect(Collectors.groupingBy(Dish::getType));
		System.out.println(m);
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
