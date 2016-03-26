package raj.streams.pipingList1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PuttingItInPractice {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Transaction> trans= new PuttingItInPractice().getTransactions();
		//1. Find all transactions in the year 2011 and sort them by value (small to high).
		List<Transaction> answer1a = trans.stream().filter((t)->t.getYear()==2011).sorted((a,b)-> a.getValue()>b.getValue()?0:1).collect(Collectors.toList());
		List<Transaction> answer1b = trans.stream().filter((t)->t.getYear()==2011).sorted(new Comparator<Transaction>() {

			@Override
			public int compare(Transaction o1, Transaction o2) {
				if (o1.getValue()> o2.getValue()) return 0;
				return 1;
			}
		}).collect(Collectors.toList());
		List<Transaction> answer1c = trans.stream().filter((t)->t.getYear()==2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
		System.out.println(answer1a);
		System.out.println(answer1b);
		System.out.println(answer1c);//book answer
		//2. What are all the unique cities where the traders work?
		List<String> answer2a = trans.stream().map(t-> t.getTrader().getCity()).distinct().collect(Collectors.toList());
		Set<String> answer2b = trans.stream().map(t-> t.getTrader().getCity()).collect(Collectors.toSet());
		System.out.println(answer2a);
		System.out.println(answer2b);//book answer
		//3. Find all traders from Cambridge and sort them by name.
		List<String> answer3a = trans.stream().filter(t->t.getTrader().getCity()=="Cambridge").map(t-> t.getTrader().getName()).sorted().collect(Collectors.toList());
		List<String> answer3b = trans.stream().map(Transaction::getTrader).filter(trader-> trader.getCity().equals("Cambridge")).sorted(Comparator.comparing(Trader::getName)).map(t->t.getName()).collect(Collectors.toList());
		System.out.println(answer3a);
		System.out.println(answer3b);//book answer
		//4. Return a string of all traders’ names sorted alphabetically.
		List<String> answer4a = trans.stream().map(t-> t.getTrader().getName()).sorted().collect(Collectors.toList());
		String answer4b = trans.stream().map(t-> t.getTrader().getName()).sorted().reduce("", (n1, n2)-> n1 + " " + n2);
		String answer4c = trans.stream().map(t-> t.getTrader().getName()).sorted().collect(Collectors.joining());
		System.out.println(answer4a);
		System.out.println(answer4b);//book answer
		System.out.println(answer4c);//book answer - best
		//5. Are any traders based in Milan?
		boolean isMilanTraderPresent = trans.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan"));
		System.out.println(isMilanTraderPresent);//avoid == and use .equals()
		//6. Print all transactions’ values from the traders living in Cambridge.
		trans.stream().filter(t->t.getTrader().getCity()=="Cambridge").map(t->t.getValue()).forEach(System.out::println);
		//7. What’s the highest value of all the transactions?
		Optional<Integer> maxValue = trans.stream().map(t->t.getValue()).reduce(Integer::max);
		System.out.println("max = "+maxValue.get());
		//8. Find the transaction with the smallest value.
		Optional<Integer> minValue = trans.stream().map(t->t.getValue()).reduce(Integer::min);
		System.out.println("min = "+minValue.get());
	}

	class Trader {
		private final String name;
		private final String city;

		public Trader(String n, String c) {
			this.name = n;
			this.city = c;
		}

		public String getName() {
			return this.name;
		}

		public String getCity() {
			return this.city;
		}

		public String toString() {
			return "Trader:" + this.name + " in " + this.city;
		}
	}

	class Transaction {
		private final Trader trader;
		private final int year;
		private final int value;

		public Transaction(Trader trader, int year, int value) {
			this.trader = trader;
			this.year = year;
			this.value = value;
		}

		public Trader getTrader() {
			return this.trader;
		}

		public int getYear() {
			return this.year;
		}

		public int getValue() {
			return this.value;
		}

		public String toString() {
			return "{" + this.trader + ", " + "year: " + this.year + ", " + "value:" + this.value + "}";
		}
	}

	private List<Transaction> getTransactions() {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");
		List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950));
		return transactions;
	}
}
