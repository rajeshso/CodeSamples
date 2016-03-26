package raj.streams.pipingList1;

import java.util.List;
import java.util.Optional;

import raj.streams.pipingList1.Domain.Dish;

public class FindMatch {

	public static void main(String[] args) {
		Domain domain = new Domain();
		List<Dish> menu = domain.createMenu();
		
		//Checking to see if a predicate matches at least one element
		if (menu.stream().anyMatch(Dish::isVegetarian)) {
			System.out.println("The menu has atleast one veg dish");
		}
		
		//Checking to see if a predicate matches all elements
		if (menu.stream().allMatch(d->d.getCalories()<1000)) {
			System.out.println("The Menu is healthy");
		}
		
		//noneMatch
		if (menu.stream().noneMatch(d->d.getCalories()>1000)) {
			System.out.println("The menu is healthy");
		}
		
		//Finding an element
		Optional<Dish> dishOptional = menu.stream().filter(Dish::isVegetarian).findAny();
		if (dishOptional.isPresent()) 
			System.out.println("The menu has atleast one vegetarian dish.");
		
		//Finding and printing an element in one line
		menu.stream().filter(Dish::isVegetarian).findAny().ifPresent(d->System.out.println(d.getName()));
		
	}

}
