package raj.streams.pipingList1;
import static java.util.stream.Collectors.toList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import raj.streams.pipingList1.Domain.Dish;

//map and flatMap methods
/**
 * Streams support the method map, which takes a function as argument. The
 * function is applied to each element, mapping it into a new element (the word
 * mapping is used because it has a meaning similar to transforming but with the
 * nuance of “creating a new version of” rather than “modifying”).
 * 
 * @author Rajesh
 *
 */
public class Mapping {

	public static void main(String[] args) {
		Domain domain = new Domain();
		List<Dish> menu = domain.createMenu();
		
		menu.stream().map(Dish::getName).forEach(System.out::print);
		System.out.println("-------------------------------");
		
		//Given a list of words, you’d like to return a list of the length of each string.
		List<String> wordList = Arrays.asList("Appa", "Amma", "Rajesh","Priya", "Nimalan", "Nithilan");
		wordList.stream().mapToInt(String::length).forEach(System.out::println);
		System.out.println("--------------------------------");
		
		//find out the length of the name of each dish - Chaining the maps
		menu.stream().map(Dish::getName).map(String::length).forEach(System.out::println);
		System.out.println("--------------------------------");
		
		//Flattening Streams
		//return a list of all the unique characters for a list of words? For example, given the list of words ["Hello", "World"] you’d like to return the list ["H", "e", "l", "o","W", "r", "d"].
		wordList = Arrays.asList("Hello","World");
		//List<String[]> resultList = wordList.stream().map(word->word.split("")).distinct().collect(Collectors.toList()); //Not easy because String[] stream is difficult to use
		
		//Another Solution, that continues to be incorrect
		//String[] arrayOfWords = {"Hello", "World"};
		//Stream<String> streamOfWords = Arrays.stream(arrayOfWords);
		//List<Stream<String>> resultList = wordList.stream().map(word->word.split("")).map(Arrays::stream).distinct().collect(Collectors.toList());
		//Stream<Stream<String>> is the result of Arrays::stream. So incorrect
		
		//Correct Solution - flatMap -> Flattens each generated stream into a single stream
		List<String> resultList = wordList.stream().map(word->word.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
		System.out.println(resultList);
		System.out.println("--------------------------------");
		
		//Given a list of numbers, how would you return a list of the square of each number? For example, given [1, 2, 3, 4, 5] you should return [1, 4, 9, 16, 25].
		/*int[] givenIntArray = {1, 2, 3, 4, 5};
	    IntStream.of(givenIntArray).map(i-> {return i^2;}).forEach(System.out::println);*/
		List<Integer> givenIntList = Arrays.asList(1,2,3,4,5);
		List<Integer> squareIntList = givenIntList.stream().map(i->{int a = i^2; return a;}).collect(Collectors.toList());
		squareIntList.stream().forEach(System.out::println);
	    System.out.println("-----------------------");
	    
		//Given two lists of numbers, how would you return all pairs of numbers? For example, given a
		//list [1, 2, 3] and a list [3, 4] you should return [(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]. For
		//simplicity, you can represent a pair as an array with two elements.
	    int a[] = {1,2,3};
	    int b[] = {3,4};
	    //List<int[]> resultPairList = IntStream.of(a).flatMap(i->IntStream.of(b).map(j-> new int[] {i,j})).collect(toList());
	    List<Integer> numbers1 = Arrays.asList(1,2,3);
	    List<Integer> numbers2 = Arrays.asList(3,4);
		List<int[]> pairs = numbers1.stream().flatMap(i-> numbers2.stream().map(j->new int[] {i,j})).collect(toList());
		for (int i=0;i<pairs.size();i++) {
			int[] a1 = pairs.get(i);
			System.out.println(a1[0] +" " + a1[1]);
		}
		System.out.println("-----------------------");
		//extend the previous example to return only pairs whose sum is divisible by 3? For example, (2, 4) and (3, 3) are valid
		List<int[]> pairsOdd = numbers1.stream().flatMap(i-> numbers2.stream().filter(j->((i+j)%3 ==0)).map(j->new int[] {i,j})).collect(toList());
		for (int i=0;i<pairsOdd.size();i++) {
			int[] a1 = pairsOdd.get(i);
			System.out.println(a1[0] +" " + a1[1]);
		}
		System.out.println("-----------------------"); //Stream<int[]>
	
	}

}
