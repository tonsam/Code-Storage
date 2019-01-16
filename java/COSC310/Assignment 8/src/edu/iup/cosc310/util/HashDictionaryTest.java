package edu.iup.cosc310.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashDictionaryTest {
	private HashDictionary dict;

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
	public void test1isEmpty() {

		// a fresh list should be empty
		dict = new HashDictionary();
		assertEquals(dict.isEmpty(), true);

		// adding items changes this
		dict.put("key1", 1);
		assertEquals(dict.isEmpty(), false);

		dict.put("key2", 2);
		dict.put("key3", 3);
		assertEquals(dict.isEmpty(), false);

		// removing these keys reverts back to empty
		dict.remove("key1");
		dict.remove("key2");
		dict.remove("key3");
		assertEquals(dict.isEmpty(), true);
	}

	/*
	 * test the noKeys method
	 */
	@Test
	public void test2noKeys() {

		// a fresh list should be empty and have no keys
		dict = new HashDictionary();
		assertEquals(dict.isEmpty(), true);
		assertEquals(dict.noKeys(), 0);

		// adding items changes noKeys
		dict.put("key1", 1);
		assertEquals(dict.isEmpty(), false);

		dict.put("key2", 2);
		dict.put("key3", 3);
		dict.put("key4", 4);
		assertEquals(dict.noKeys(), 4);

		// removing these keys reduces noKeys
		dict.remove("key4");
		assertEquals(dict.noKeys(), 3);

		dict.remove("key3");
		dict.remove("key2");
		assertEquals(dict.noKeys(), 1);

		// updating a key that already exists does not affect noKeys
		dict.put("key1", 2);
		assertEquals(dict.noKeys(), 1);
	}

	/*
	 * test the get method
	 */
	@Test
	public void test3get() {

		dict = new HashDictionary();
		assertEquals(dict.isEmpty(), true);

		// add items
		dict.put("key1", 1);
		dict.put("key2", 2);
		dict.put("key3", 3);

		// getting the key returns the value
		assertEquals(dict.get("key1"), 1);
		assertEquals(dict.get("key2"), 2);
		assertEquals(dict.get("key3"), 3);

		// change an existing key
		dict.put("key1", 88);

		// get returns new value
		assertEquals(dict.get("key1"), 88);

		// trying to get a key that is not in the list returns null
		assertEquals(dict.get("key5"), null);

		// remove a key
		dict.remove("key3");

		// trying to get a key was removed from the list returns null
		assertEquals(dict.get("key3"), null);
	}

	/*
	 * test the remove method
	 */
	@Test
	public void test4remove() {

		dict = new HashDictionary();
		assertEquals(dict.isEmpty(), true);

		// add items
		dict.put("key1", 1);
		dict.put("key2", 2);
		dict.put("key3", 3);

		// removing the keys returns the value key's value and removes the key
		assertEquals(dict.remove("key1"), 1);
		assertEquals(dict.remove("key2"), 2);
		assertEquals(dict.remove("key3"), 3);

		// trying to remove a key that never existed or has been removed returns null
		assertEquals(dict.remove("key1"), null);
		assertEquals(dict.remove("key45"), null);

	}

	/*
	 * test the iterator and its methods
	 */
	@Test
	public void test5iterator() {

		dict = new HashDictionary();
		assertEquals(dict.isEmpty(), true);

		// add items
		dict.put("a", 1);
		dict.put("b", 2);
		dict.put("c", 3);
		dict.put("d", 4);
		dict.put("e", 5);

		Iterator iter = dict.keys();

		// hasNext() returns true on new iterator
		assertEquals(iter.hasNext(), true);

		// following loop should print each key and values
		while (iter.hasNext()) {
			String key = (String) iter.next();
			int value = (int) dict.get(key);
			System.out.printf(" %s %d %n", key, value);
		}

		// hasNext returns false on empty iterator
		assertEquals(iter.hasNext(), false);

		// adding duplicate keys does not cause it to print multiple times
		dict.put("d", 4);
		dict.put("e", 55);

		// Instantiate new iterator
		iter = dict.keys();

		// check hasNext using printed keys
		assertEquals(iter.hasNext(), true);

		assertEquals(iter.next(), "a");
		assertEquals(iter.next(), "b");
		assertEquals(iter.next(), "c");
		assertEquals(iter.next(), "d");
		assertEquals(iter.next(), "e");

		assertEquals(iter.hasNext(), false);

	}

	/*
	 * test the rehashing check and its methods
	 */
	@Test
	public void test6rehashCheck() {

		dict = new HashDictionary();
		assertEquals(dict.isEmpty(), true);

		/*
		 * a new dictionary has a size of 51, and the rehash checker makes it adding
		 * more than 25 items will double the size of the hash table. Though we can't
		 * explicitly call this private method, we can continuously add an arbitrary
		 * number of unique items and check whether they all exist
		 */
		int totalNewKeys = 555;

		for (int i = 0; i < totalNewKeys; i++) {
			String newKey = "key" + Integer.toString(i);
			dict.put(newKey, i);
		}

		// check that all keys are still in the list

		for (int i = 0; i < totalNewKeys; i++) {
			String newKey = "key" + Integer.toString(i);
			assertEquals(dict.get(newKey), i);
		}

	}

}
