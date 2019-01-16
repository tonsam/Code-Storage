package edu.iup.cosc310.util;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.Test;

import edu.iup.cosc310.util.LinkedStack;

public class LinkedStackTest {
	private LinkedStack stack = new LinkedStack();

	private void addThree() {
		stack.pushItem("one");
		stack.pushItem("two");
		stack.pushItem("three");
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
	 * Tests that a newly created stack has the correct initial state. A newly
	 * created stack should have no items in it, thus .isEmpty should return true.
	 * Also, when attempting to pop or peek, a noSuchElementException should be
	 * thrown.
	 */
	@Test
	public void testNewBlankList() {
		stack = new LinkedStack();
		assertEquals(stack.isEmpty(), true);
		assertEquals(NoSuchElementException.class, catchException(() -> {
			stack.peekItem();
		}));
		assertEquals(NoSuchElementException.class, catchException(() -> {
			stack.popItem();
		}));

	}

	/**
	 * Test for adding a single element to a stack. Verifies the functionality of
	 * the push() method as well as whether the pop, peek, isEmpty, and
	 * exception-throwing, work as intended.when dealing with one object
	 */
	@Test
	public void testAddingOne() {
		stack = new LinkedStack();
		stack.pushItem("one");

		assertEquals(stack.isEmpty(), false);

		assertEquals(stack.peekItem(), "one");
		assertEquals(stack.popItem(), "one");

		assertEquals(stack.isEmpty(), true);

		assertEquals(NoSuchElementException.class, catchException(() -> {
			stack.peekItem();
		}));
		assertEquals(NoSuchElementException.class, catchException(() -> {
			stack.popItem();
		}));
	}

	/**
	 * Tests that the pop and peek methods function properly. Adding three elements
	 * to the stack should change the result of .isEmpty from true to false, and
	 * peek should return the item of the last node to be added to the stack.
	 * Popping all the items will bring the list back to empty.
	 */
	@Test
	public void testAddingThree() {
		stack = new LinkedStack();
		assertEquals(stack.isEmpty(), true);

		addThree();
		assertEquals(stack.isEmpty(), false);

		assertEquals(stack.peekItem(), "three");
		assertEquals(stack.isEmpty(), false);

		assertEquals(stack.popItem(), "three");
		assertEquals(stack.isEmpty(), false);

		assertEquals(stack.popItem(), "two");
		assertEquals(stack.popItem(), "one");

		assertEquals(stack.isEmpty(), true);
	}

	/**
	 * Tests that popping all the items in a long list will bring the list back to
	 * empty.
	 */
	@Test
	public void testAddingMany() {
		stack = new LinkedStack();
		assertEquals(stack.isEmpty(), true);

		addThree();
		addThree();
		addThree();
		addThree();
		addThree();
		addThree();
		assertEquals(stack.isEmpty(), false);

		while (stack.isEmpty() != true) {
			stack.popItem();
		}

		assertEquals(stack.isEmpty(), true);
	}

	/**
	 * Tests that the clear method functions properly.
	 */
	@Test
	public void testClearingList() {
		stack = new LinkedStack();
		assertEquals(stack.isEmpty(), true);

		addThree();
		assertEquals(stack.isEmpty(), false);

		stack.clear();
		assertEquals(stack.isEmpty(), true);

		assertEquals(NoSuchElementException.class, catchException(() -> {
			stack.peekItem();
		}));
		assertEquals(NoSuchElementException.class, catchException(() -> {
			stack.popItem();
		}));
	}

}
