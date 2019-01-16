package edu.iup.cosc310.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LinkedItemListTest {

	private ItemList list = new LinkedItemList();

	private void addThree() {
		list.append("one");
		list.append("two");
		list.append("three");
	}

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

	/**
	 * Tests that a newly created list has the correct initial state. The number of
	 * items returned by size() should be zero, isEmpty() should return true, and
	 * trying to get() a node at any index should return an exception.
	 */
	@Test
	public void testNewBlankList() {
		list = new LinkedItemList();
		assertEquals(list.size(), 0);
		assertEquals(list.isEmpty(), true);
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(-1);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(0);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(1);
		}));
	}

	/**
	 * Test for adding a single element to a list. Verifies the functionality of the
	 * append() method (and by extension, the appendAfter() method upon which it is
	 * reliant), as well as whether the list size, exception-throwing, and get()
	 * method work as intended.
	 */
	@Test
	public void testAddingOne() {
		list.append("one");
		assertEquals(list.size(), 1);
		assertEquals(list.isEmpty(), false);
		assertEquals(list.get(0), "one");
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(-1);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(1);
		}));
	}

	/**
	 * Test for adding three elements to the list. Verifies the fact that, upon the
	 * addition of three elements to the list, that each element is in the correct
	 * order. Tests the functionality of the following methods and functions:
	 * append(), list.size(), get(), exception throwing
	 */
	@Test
	public void testAddingThree() {
		addThree();
		assertEquals(list.size(), 3);
		assertEquals(list.isEmpty(), false);
		assertEquals(list.get(0), "one");
		assertEquals(list.get(1), "two");
		assertEquals(list.get(2), "three");
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(-1);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(3);
		}));
	}

	/**
	 * Tests whether adding an element at the front of the list maintains proper
	 * list order. Verifies functionality of following methods: append(), insert(),
	 * isEmpty, get(), getNode()
	 */
	@Test
	public void testInsertAtFront() {
		addThree();
		list.insert("zero", 0);
		assertEquals(list.size(), 4);
		assertEquals(list.isEmpty(), false);
		assertEquals(list.get(0), "zero");
		assertEquals(list.get(1), "one");
		assertEquals(list.get(2), "two");
		assertEquals(list.get(3), "three");

	}

	/**
	 * Tests whether adding an element in the middle of the list maintains proper
	 * list order. Verifies functionality of following methods: append(), insert(),
	 * isEmpty, get(), getNode()
	 */
	@Test
	public void testInsertInMiddle() {
		addThree();
		list.insert("two&Half", 2);
		assertEquals(list.size(), 4);
		assertEquals(list.isEmpty(), false);
		assertEquals(list.get(0), "one");
		assertEquals(list.get(1), "two");
		assertEquals(list.get(2), "two&Half");
		assertEquals(list.get(3), "three");

	}

	/**
	 * Tests whether adding an element at the end of the list maintains proper list
	 * order. Verifies functionality of following methods: append(), insert(),
	 * isEmpty, get(), getNode()
	 */
	@Test
	public void testInsertAtEnd() {
		addThree();
		list.insert("four", 3);
		assertEquals(list.size(), 4);
		assertEquals(list.isEmpty(), false);
		assertEquals(list.get(0), "one");
		assertEquals(list.get(1), "two");
		assertEquals(list.get(2), "three");
		assertEquals(list.get(3), "four");
	}

	/**
	 * Tests whether removing an element from the front of the list maintains proper
	 * list order. Verifies functionality of following methods: append(),
	 * removeAtIndex() and by extension removeNode(), isEmpty, get(), getNode(),
	 * throwing exceptions
	 */
	@Test
	public void testRemoveAtFront() {
		addThree();
		list.removeAtIndex(0);
		assertEquals(list.size(), 2);
		assertEquals(list.isEmpty(), false);
		assertEquals(list.get(0), "two");
		assertEquals(list.get(1), "three");
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(-1);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(2);
		}));
	}

	/**
	 * Tests whether removing an element from the middle of the list maintains
	 * proper list order. Verifies functionality of following methods: append(),
	 * removeAtIndex() and by extension removeNode(), isEmpty, get() and by
	 * extension getNode(), throwing exceptions
	 */
	@Test
	public void testRemoveAtMiddle() {
		addThree();
		list.removeAtIndex(1);
		assertEquals(list.size(), 2);
		assertEquals(list.isEmpty(), false);
		assertEquals(list.get(0), "one");
		assertEquals(list.get(1), "three");
	}

	/**
	 * Tests functionality of clear() method. Upon executing clear(), list size
	 * should return zero and list .isEmpty() should return true. Trying to get an
	 * item at any index should throw an out-of-bounds exception
	 */
	@Test
	public void testClearingList() {
		addThree();
		list.clear();
		assertEquals(list.size(), 0);
		assertEquals(list.isEmpty(), true);
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(0);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(1);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(2);
		}));
		assertEquals(IndexOutOfBoundsException.class, catchException(() -> {
			list.get(3);
		}));
	}

}
