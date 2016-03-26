package raj.misc;

import java.util.Arrays;
import java.util.List;

public class Test {
	private static final int MAX_LIMIT = 10;

	public static void main(String[] as) {
		Test t2 = new Test();
		List<String> stringList = Arrays.asList("a", "b");
		t2.t1(stringList);
		int a[] = {1,2 ,3};
		for(int i=0;i<a.length;i++) {
			System.out.println(a[i]);
		}
	}

	void t1(List l) {
		try {
			validateList(l);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	private void validateList(List l) throws TechnicalFailureException {
		if (l == null)
			throw new TechnicalFailureException("The input is null.");
		if (l.size() > MAX_LIMIT)
			throw new TechnicalFailureException("The input has a higher limit than is expected.");
		if (l.isEmpty())
			throw new TechnicalFailureException("The input does not have valid set of elements.");
		try {
			List<Integer> l1 = (List<Integer>) l;
			for (Integer j : l1) {}
		} catch (ClassCastException cce) {
			throw new TechnicalFailureException("The List is not type safe");
		}
	}

	class TechnicalFailureException extends Exception {
		TechnicalFailureException(String message) {
			super(message);
		}
	}
}
