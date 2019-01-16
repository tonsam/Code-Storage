
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

public class LsJUnitTest {
	private Ls list;

	@FunctionalInterface
	interface Runner {
		public void run();
	}

	public static Class catchException(Runner runner) {
		try {
			runner.run();
			return null;
		} catch (Exception e) {
			return e.getClass();
		}
	}

	/*
	 * test problem 1 and the startsWith method
	 */
	@Test
	public void test1startsWith() {
		list = new Ls(1, 2, 3, 4, 5);
		assertEquals(list.isEmpty(), false);

		// from assignment page
		assertEquals(list.startsWith(new Ls(1, 2, 3)), true);
		assertEquals(list.startsWith(new Ls(1, 3, 2)), false);
		assertEquals(new Ls(1, 2, 3).startsWith(new Ls()), true);

		// a blank list should start with a blank list
		assertEquals(new Ls().startsWith(new Ls()), true);

		// a blank list should NOT start with a non-blank list
		assertEquals(new Ls().startsWith(new Ls(7, 8, 9)), false);

		// works for non-numeric objects
		assertEquals(new Ls('h', 'o', 't', 'd', 'o', 'g').startsWith(new Ls('h', 'o', 't')), true);
	}

	/*
	 * test problem 2 and the indexOf method
	 */
	@Test
	public void test2indexOf() {
		list = new Ls(1, 2, 3, 4, 5);

		// from assignment page
		assertEquals(list.indexOf(new Ls(3, 4)), 2);

		// first element and last index are valid
		assertEquals(list.indexOf(new Ls(1)), 0);
		assertEquals(list.indexOf(new Ls(5)), 4);

		// non-existant elements return -1
		assertEquals(list.indexOf(new Ls(6)), -1);
		assertEquals(list.indexOf(new Ls(86)), -1);
		assertEquals(list.indexOf(new Ls(-12)), -1);

		// larger lists return -1, even if a portion of it exists
		assertEquals(list.indexOf(new Ls(0, 1, 2, 3)), -1);
		assertEquals(list.indexOf(new Ls(2, 3, 4, 5, 6, 7)), -1);
		assertEquals(list.indexOf(new Ls(0, 1, 2, 3, 4, 5, 6, 7)), -1);

		// works for non-numeric objects
		assertEquals(new Ls("My", "Name", "Is", "David").indexOf(new Ls("Name")), 1);
		assertEquals(new Ls("you", "and", "I", "like", "food").indexOf(new Ls("I", "like", "food")), 2);
	}

	/*
	 * test problem 3 and the concat method
	 */
	@Test
	public void test3concat() {
		list = new Ls(1, 2, 3, 4);

		// from assignment page
		assertEquals(list.concat(new Ls(5, 6, 7)), new Ls(1, 2, 3, 4, 5, 6, 7));

		// calling several concats in succession functions as intended
		assertEquals(new Ls(2, 4).concat(new Ls(6, 8)).concat(new Ls(10, 12)), new Ls(2, 4, 6, 8, 10, 12));
		assertEquals(new Ls('S').concat(new Ls('t')).concat(new Ls('e').concat(new Ls('v')).concat(new Ls('e'))),
				new Ls('S', 't', 'e', 'v', 'e'));

		// works for non-integer objects
		assertEquals(new Ls('r', 'o').concat(new Ls('b', 'o', 't')), new Ls('r', 'o', 'b', 'o', 't'));

	}

	/*
	 * test problem 4 and the single-argument sublist method
	 */
	@Test
	public void test4sublistOneArg() {
		list = new Ls(1, 2, 3, 4);

		// from assignment page
		assertEquals(list.sublist(2), new Ls(3, 4));

		// out-of-bounds starting points throw proper exceptions
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.sublist(-1);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.sublist(12);
		}));

		// first and last indexes function properly
		assertEquals(list.sublist(0), new Ls(1, 2, 3, 4));
		assertEquals(list.sublist(3), new Ls(4));
	}

	/*
	 * test problem 5 and the two-argument sublist method
	 */
	@Test
	public void test5sublistTwoArgs() {
		list = new Ls(1, 2, 3, 4);

		// from assignment page
		assertEquals(list.sublist(1, 3), new Ls(2, 3));

		// test that invalid starting and ending points throw exceptions
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.sublist(-1, 6);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.sublist(1, 5);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.sublist(-2, 3);
		}));

		// further tests
		assertEquals(new Ls(0, 1, 2, 3, 4, 5).sublist(1, 4), new Ls(1, 2, 3));
		assertEquals(new Ls(3, 6, 9, 12, 15, 18, 21).sublist(2, 6), new Ls(9, 12, 15, 18));
	}

	/*
	 * test problem 6 and the flatten method
	 */
	@Test
	public void test6flatten() {
		list = new Ls(1, 2, new Ls(3, 4), 5, new Ls(new Ls(6, 7)));

		// from assignment page
		assertEquals(list.flatten(), new Ls(1, 2, 3, 4, 5, 6, 7));

		// flattening on a list with no nested lists returns original list
		assertEquals(new Ls(0, 1, 2, 3).flatten(), new Ls(0, 1, 2, 3));

		// flattening a double-nested list flattens properly
		assertEquals(new Ls(1, 2, new Ls(3, 4, new Ls(5, 6)), 7, 8).flatten(), new Ls(1, 2, 3, 4, 5, 6, 7, 8));

		// test nested lists at beginning of list, end of list, and both
		assertEquals(new Ls(new Ls(1, 2), 3, 4, 5, 6).flatten(), new Ls(1, 2, 3, 4, 5, 6));
		assertEquals(new Ls(1, 2, 3, 4, new Ls(5, 6)).flatten(), new Ls(1, 2, 3, 4, 5, 6));
		assertEquals(new Ls(new Ls(1, 2), 3, 4, new Ls(5, 6)).flatten(), new Ls(1, 2, 3, 4, 5, 6));

		// test if flattening a list of only lists works out
		assertEquals(new Ls(new Ls(1, 2), new Ls(3, 4), new Ls(5, 6)).flatten(), new Ls(1, 2, 3, 4, 5, 6));

	}

	/*
	 * test problem 7 and the replaceAll method.
	 */
	@Test
	public void test7replaceAll() {
		list = new Ls(1, 2, new Ls(1, 2, 3), 2, 1);

		// from assignment page
		assertEquals(list.replaceAll(1, 99), new Ls(99, 2, new Ls(99, 2, 3), 2, 99));

		// test replacing entire list and none of a list
		assertEquals(new Ls(3, 3, new Ls(3, 3), 3).replaceAll(3, 4), new Ls(4, 4, new Ls(4, 4), 4));
		assertEquals(new Ls(3, 3, new Ls(3, 3), 3).replaceAll(2, 4), new Ls(3, 3, new Ls(3, 3), 3));

		// test replacing from deeply nested lists
		assertEquals(new Ls(1, 2, new Ls(3, new Ls(4, 5, new Ls(6))), 7).replaceAll(6, 66),
				new Ls(1, 2, new Ls(3, new Ls(4, 5, new Ls(66))), 7));
	}

	/*
	 * test problem 8 and the compareTo method.
	 */
	@Test
	public void test8compareTo() {
		list = new Ls(1, 2, 3, 4);

		// from assignment page
		assertEquals(list.compareTo(new Ls(1, 2, 4)), -1);
		assertEquals(list.compareTo(new Ls(1, 2, 3)), 1);

		// check if two equal lists return 0
		assertEquals(new Ls(1, 2, 3, 4).compareTo(new Ls(1, 2, 3, 4)), 0);

		// check that comparisons work both ways
		assertEquals(new Ls(1, 2, 3, 4).compareTo(new Ls(1, 2, 5, 4)), -1);
		assertEquals(new Ls(1, 2).compareTo(new Ls(1, 2, 5, 4)), -1);
		assertEquals(new Ls(1, 2, 5, 4).compareTo(new Ls(1, 2, 3, 4)), 1);
		assertEquals(new Ls(1, 2, 5, 4).compareTo(new Ls(1, 2)), 1);
	}

}
