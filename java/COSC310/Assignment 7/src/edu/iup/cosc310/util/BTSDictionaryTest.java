package edu.iup.cosc310.util;

import org.junit.Test; 

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BTSDictionaryTest {
	private BSTDictionary dict;

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
	 * test the isEmpty method
	 */
	@Test
	public void test1startsWith() {

		// a fresh list should be empty
		dict = new BSTDictionary();
		assertEquals(dict.isEmpty(), true);

		// adding items changes this
		dict.put("a key", 62);
		assertEquals(dict.isEmpty(), false);

		dict.put("another key", 63);
		dict.put("next key", 64);
		dict.put("last key", 65);
		assertEquals(dict.isEmpty(), false);

		// removing these keys reverts back to empty

	}

	/*
	 * test the noKeys method
	 */
	@Test
	public void test2noKeys() {
		dict = new BSTDictionary();

		// an empty Dictionary should return 0 for number of keys
		assertEquals(dict.isEmpty(), true);
		assertEquals(dict.noKeys(), 0);

		// adding key pairs to the Dictionary increments noKeys
		dict.put("key1", 1);
		assertEquals(dict.noKeys(), 1);

		dict.put("key2", 2);
		dict.put("key3", 3);
		assertEquals(dict.noKeys(), 3);
		
		// removing them again causes noKeys to decrement properly
		dict.remove("key2");
		assertEquals(dict.noKeys(), 2);
		
		dict.remove("key3");
		assertEquals(dict.noKeys(), 1);
		
		dict.remove("key1");
		assertEquals(dict.noKeys(), 0);
	}

	/*
	 * test the put and get method on a left-side only tree
	 */
	@Test
	public void test3putLeft() {
		dict = new BSTDictionary();
		dict.put("Frisbee", 1);

		// Dictionary should no longer be empty, noKeys should go up
		assertEquals(dict.isEmpty(), false);

		// test adding just to the left side
		// to do this, add new items in reverse alphabetical order from Frisbee
		dict.put("Egg", 2);
		dict.put("Dog", 3);
		dict.put("Cargo", 4);
		dict.put("Cat", 5);

		assertEquals(dict.get("Egg"), 2);
		assertEquals(dict.get("Dog"), 3);
		assertEquals(dict.get("Cargo"), 4);
		assertEquals(dict.get("Cat"), 5);

	}

	/*
	 * test the put and get method on a right-side only tree
	 */
	@Test
	public void test4putRight() {
		dict = new BSTDictionary();
		dict.put("Frisbee", 1);

		// Dictionary should no longer be empty, noKeys should go up
		assertEquals(dict.isEmpty(), false);

		// test adding just to the left side
		// to do this, add new items in alphabetical order from Frisbee
		dict.put("Hammer", 2);
		dict.put("Hamster", 3);
		dict.put("Igloo", 4);
		dict.put("Jogger", 5);

		assertEquals(dict.get("Hammer"), 2);
		assertEquals(dict.get("Hamster"), 3);
		assertEquals(dict.get("Igloo"), 4);
		assertEquals(dict.get("Jogger"), 5);

	}

	/*
	 * test the put and get method on a tree with objects going left and right
	 */
	@Test
	public void test5putLeftRight() {
		dict = new BSTDictionary();
		dict.put("Frisbee", 1);

		// Dictionary should no longer be empty, noKeys should go up
		assertEquals(dict.isEmpty(), false);

		// test adding just to the left side
		// to do this, alternate adding new items above and below Frisbee
		dict.put("Egg", 2);
		dict.put("Hamster", 3);
		dict.put("Dog", 4);
		dict.put("Jogger", 5);
		dict.put("Cat", 6);
		dict.put("Igloo", 7);

		// test that get still functions when alternating sides
		assertEquals(dict.get("Egg"), 2);
		assertEquals(dict.get("Hamster"), 3);
		assertEquals(dict.get("Dog"), 4);
		assertEquals(dict.get("Jogger"), 5);
		assertEquals(dict.get("Cat"), 6);
		assertEquals(dict.get("Igloo"), 7);

	}

	/*
	 * test the remove method
	 */
	@Test
	public void test6remove() {
		dict = new BSTDictionary();
		dict.put("Frisbee", 1);
		dict.put("Egg", 2);
		dict.put("Hamster", 3);
		dict.put("Dog", 4);
		dict.put("Jogger", 5);
		dict.put("Cat", 6);
		dict.put("Igloo", 7);

		assertEquals(dict.noKeys(), 7);

		// trying to remove a nonexistant key should return null and change nothing
		assertEquals(dict.remove("Fruit"), null);
		assertEquals(dict.noKeys(), 7);

		// remove an item preceding Frisbee doesn't break it
		assertEquals(dict.remove("Dog"), 4);
		assertEquals(dict.noKeys(), 6);

		// remove an item following Frisbee doesn't break it
		assertEquals(dict.remove("Jogger"), 5);
		assertEquals(dict.noKeys(), 5);

	}

	/*
	 * test the method to get the leftmost node. Originally, this method just
	 * returned the node's key, but I had to make it return the whole node to get
	 * the iterator to work (and the iterator still only kinda works).
	 */
	@Test
	public void test7getLeftmost() {
		dict = new BSTDictionary();

		dict.put("Frisbee", 1);
		dict.put("Egg", 2);
		dict.put("Hamster", 3);
		dict.put("Dog", 4);
		dict.put("Jogger", 5);
		dict.put("Cat", 6);
		dict.put("Igloo", 7);

		assertEquals(dict.noKeys(), 7);

		// getting leftmost should return Cat
		BSTDictionary leftMost = dict.getLeftmost();
		assertEquals(leftMost.getHighestKey(), "Cat");

		// removing Cat causes it to return Dog
		dict.remove("Cat");
		leftMost = dict.getLeftmost();
		assertEquals(leftMost.getHighestKey(), "Dog");

		// adding a new item further to the left doesn't break it
		dict.put("Abacus", 3);
		leftMost = dict.getLeftmost();
		assertEquals(leftMost.getHighestKey(), "Abacus");
	}

	/*
	 * test the iterator (good luck with that)
	 */
	@Test
	public void test8iteration() {
		dict = new BSTDictionary();
		BSTDictionary next = new BSTDictionary();

		dict.put("Frisbee", 1);
		dict.put("Egg", 2);
		dict.put("Hamster", 3);
		dict.put("Dog", 4);
		dict.put("Jogger", 5);
		dict.put("Cat", 6);
		dict.put("Igloo", 7);

		// iterating through the items returns them in the proper order
		Iterator iter = dict.keys();
		
		next = (BSTDictionary) iter.next();
		assertEquals(next.getHighestKey(), "Cat");
		
		next = (BSTDictionary) iter.next();
		assertEquals(next.getHighestKey(), "Dog");
		
		next = (BSTDictionary) iter.next();
		assertEquals(next.getHighestKey(), "Egg");
	}
}
