package edu.iup.cosc310.util;

import java.util.NoSuchElementException; 

import java.util.Iterator;

/**
 * 
 * @author David Kornish
 *
 *         Binary Search Tree implementation for a dictionary. Each entry has a
 *         key and a value, as well as references to the left and right child
 *         nodes and number of keys
 *
 * @param <K>
 *            data type of the key
 * @param <V>
 *            data type of the value
 */
public class BSTDictionary<K, V> implements Dictionary {

	private K key;
	private V value;

	private int noKeys = noKeys();

	private BSTDictionary left;
	private BSTDictionary right;

	public BSTDictionary() {
		this.left = null;
		this.right = null;
		this.noKeys = 0;
	}

	/**
	 * Put a key together with its associated value into the dictionary. If the key
	 * already exists then the new value replaces the current value associated with
	 * the key. Values can be retrieved using the get method.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the new value
	 * @return the original value if the key already exists in the dictionary,
	 *         otherwise null.
	 */
	@Override
	public Object put(Object key, Object value) {
		if (this.isEmpty()) {
			// easiest case, this dict is empty so add key and item
			this.key = (K) key;
			this.value = (V) value;
			this.left = new BSTDictionary();
			this.right = new BSTDictionary();
			// this.noKeys++;
			return null;
		}

		String k1 = (String) this.key;
		String k2 = (String) key;

		if (k1.compareTo(k2) == 0) {
			// keys are the same, replace with new value and return old one
			V oldVal = this.value;
			this.value = (V) value;
			return oldVal;

		} else if (k1.compareTo(k2) > 0) {
			// key 1 precedes key 2 alphabetically, go left
			return this.left.put(key, value);

		} else {
			// key 1 follows key 2 alphabetically, go right
			return this.right.put(key, value);
		}

	}

	/**
	 * Get the current value associated with a given key. If the key is not yet
	 * mapped, return null.
	 * 
	 * @param key
	 *            the key
	 * @return the current value associated with the key in the dictionary if found,
	 *         otherwise null.
	 */
	@Override
	public Object get(Object key) {
		if (this.isEmpty()) {
			return null;
		}

		String k1 = (String) this.key;
		String k2 = (String) key;

		if (k1.compareTo(k2) == 0) {
			// keys are the same, return the key's value
			return this.value;

		} else if (k1.compareTo(k2) > 0) {
			// key 1 precedes key 2 alphabetically, go left
			return this.left.get(key);

		} else {
			// key 1 follows key 2 alphabetically, go right
			return this.right.get(key);
		}
	}

	/**
	 * Remove the key and its associated value associated from the dictionary. The
	 * value associated with the key is returned. If the key does not exist in the
	 * dictionary then null is returned.
	 * 
	 * @param key
	 *            the key
	 * @return the value associated with the removed key in the dictionary. If the
	 *         key did not exist then null.
	 */
	@Override
	public Object remove(Object key) {
		if (this.isEmpty()) {
			return null;
		}

		String k1 = (String) this.key;
		String k2 = (String) key;

		if (k1.compareTo(k2) == 0) {
			// keys are the same, remove the key
			V oldVal = this.value;

			if (left.isEmpty()) {
				// left tree is empty, copy up right tree
				this.key = (K) right.key;
				this.value = (V) right.value;

				this.noKeys = right.noKeys;
				this.left = right.left;
				this.right = right.right;
			} else if (right.isEmpty()) {
				// right tree is empty, copy up right tree
				this.key = (K) left.key;
				this.value = (V) left.value;

				this.noKeys = left.noKeys;
				this.right = left.right;
				this.left = left.left;
			}

			return oldVal;

		} else if (k1.compareTo(k2) > 0)

		{
			// key 1 precedes key 2 alphabetically, go left
			return this.left.remove(key);

		} else {
			// key 1 follows key 2 alphabetically, go right
			return this.right.remove(key);
		}
	}

	/**
	 * Create an Iterator to iterate over the keys of the dictionary. Accepts the
	 * BSTDictionary to iterate through.
	 * 
	 * @return an Iterator to iterator over the keys.
	 * 
	 */
	@Override
	public Iterator<Object> keys() {
		return new Iter(this);
	}

	/**
	 * Inner class to implement the Iterator interface. This is by far the sloppiest
	 * portion of my code. This class iterates through all of the the elements in
	 * the dictionary. By starting at the leftmost node, entries are alphabetized.
	 * 
	 * Iterating through a binary search tree with recursion is easy and can be
	 * coded in three or four lines, but I ran into several roadblocks. Since
	 * Iterator methods exist within an inner class, it is difficult to properly
	 * recurse through the tree while recursively keeping track of an index.
	 * 
	 * Luckily, the leftmost item is always the lowest in the tree, so my next
	 * thought was to create a copy of the dictionary. Each time next() is called,
	 * the leftmost item will be returned and then removed, revealing the next item
	 * in alphabetical order as the next leftmost item. However, trying to set a new
	 * dictionary object equal to the actual one creates a direct reference, meaning
	 * the removed items are permanently removed from the actual tree as well.
	 * 
	 * Implementing a proper clone method to properly enable the above functionality
	 * would require recursively copying the items in the list into a new tree
	 * anyway, which is against the rules set by the assignment prompt. So
	 * currently, my iterator deconstructs the dictionary as it runs, but it allows
	 * the wordcount program to give a proper output.
	 */
	private class Iter implements Iterator {
		private int index;
		private int size;
		private BSTDictionary<K, V> temp;

		public Iter(BSTDictionary<K, V> thisDictionary) {
			// create a copy of the dictionary
			temp = (BSTDictionary<K, V>) thisDictionary;

			// index tracks how far we have iterated
			int index = 0;
			size = temp.noKeys();

		}

		/**
		 * Returns true if there are more elements in the dictionary to access.
		 */
		@Override
		public boolean hasNext() {
			return index < size;
		}

		/**
		 * Returns the next node in the Dictionary snapshot.
		 */
		@Override
		public BSTDictionary next() {
			BSTDictionary next = new BSTDictionary();
			next.key = temp.getLeftmost().key;
			next.value = temp.getLeftmost().value;
			temp.remove(next.getHighestKey());
			index += 1;
			return next;
		}
	}

	/**
	 * Test if the dictionary is empty
	 * 
	 * @return true if the dictionary is empty, otherwise false
	 */
	@Override
	public boolean isEmpty() {
		if (this.key == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the number of keys in the dictionary
	 * 
	 * @return the number of keys in the dictionary
	 */
	@Override
	public int noKeys() {
		if (this.isEmpty()) {
			return 0;
		}

		// this isn't empty, add 1 to noKeys
		this.noKeys = 1;

		if (!this.left.isEmpty()) {
			// left isn't empty, get its items
			this.noKeys += this.left.noKeys();
		}

		if (!this.right.isEmpty()) {
			// right isn't empty, get its items
			this.noKeys += this.right.noKeys();
		}

		return this.noKeys;
	}

	/**
	 * Method to get the leftmost node in the dictionary
	 * 
	 * @return leftmost key
	 */
	public BSTDictionary getLeftmost() {
		BSTDictionary pointer = this;
		while (!pointer.left.isEmpty()) {
			pointer = pointer.left;
		}
		return pointer;
	}

	/**
	 * Method to get the hierarchically (not alphabetically) highest node in the
	 * dictionary. Needed to get the iterator working.
	 * 
	 * @return highest key
	 */
	public Object getHighestKey() {
		return this.key;
	}

}
