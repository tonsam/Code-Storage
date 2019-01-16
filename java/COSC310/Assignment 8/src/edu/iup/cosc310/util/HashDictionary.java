package edu.iup.cosc310.util;

import java.util.Iterator;

/**
 * A data type for a HashDictionary that maps a set of keys to values.
 * 
 * @author David Kornish
 *
 * @param <K>
 *            Data type for the keys
 * @param <V>
 *            Data type for the values
 */
public class HashDictionary<K, V> implements Dictionary {
	private Entry[] table;
	private int noKeys;

	// hard-coded set of primes, roughly doubling in size, for rehash
	private final int[] primes = { 51, 103, 211, 431, 863, 1733, 3469, 7001, 14009, 28019, 56039 };

	/**
	 * Constructor for a HashDictionary. Initializes table size and number of keys.
	 * Size is set to 51 when not specified with an argument
	 */
	public HashDictionary() {
		this.noKeys = 0;
		this.table = new Entry[51];
	}

	/**
	 * Inner class for key‐value pairs for a hash table.
	 */
	private static class Entry<K, V> {
		/** The key */
		private final K key;
		/** The value */
		private V value;

		/**
		 * Creates a new key‐value pair.
		 * 
		 * @param key
		 *            The key
		 * @param value
		 *            The value
		 */
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Retrieves the key.
		 * 
		 * @return The key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Retrieves the value.
		 * 
		 * @return The value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value.
		 * 
		 * @param val
		 *            The new value
		 * @return The old value
		 */
		public V setValue(V val) {
			V oldVal = value;
			value = val;
			return oldVal;
		}
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
		int index = Math.abs(key.hashCode()) % table.length;

		// linear probe
		while (table[index] != null) {
			// check if keys match
			if (table[index].getKey().equals(key)) {
				// key exists, update value and return old one
				Object oldValue = table[index].getValue();
				table[index].setValue(value);
				return oldValue;
			}

			// loop if necessary
			if (index + 1 == table.length) {
				index = 0;
			} else {
				index++;
			}
		}

		// index was null, put new entry
		table[index] = new Entry(key, value);
		this.noKeys++;
		this.rehashCheck();
		return null;
	}

	/**
	 * Get the current value associated with a given key.
	 * 
	 * @param key
	 *            the key
	 * @return the current value associated with the key in the dictionary if found,
	 *         otherwise null.
	 */
	@Override
	public Object get(Object key) {
		// generate hash code, make it positive and
		int index = Math.abs(key.hashCode()) % table.length;

		if (table[index] == null) {
			return null;
		} else if (table[index].getKey().equals(key)) {
			return table[index].getValue();
		} else {

			// linear probe, make sure it loops
			while (table[index] != null) {
				if (table[index].getKey().equals(key)) {
					return table[index].getValue();
				}

				if (index + 1 == table.length) {
					index = 0;
				} else {
					index++;
				}
			}
			return null;
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
		// key exists, continue
		int index = Math.abs(key.hashCode()) % table.length;
		// linear probe, make sure it loops
		while (table[index] != null) {
			// check if keys match
			if (table[index].getKey() == key) {
				Object oldValue = table[index].getValue();
				table[index] = null;
				this.noKeys--;
				return oldValue;
			}

			// keys didn't match, increment/loop
			if (index + 1 == table.length) {
				index = 0;
			} else {
				index++;
			}
		}

		// key isn't in dictionary, return null
		return null;
	}

	/**
	 * Create an Iterator to iterate over the keys of the dictionary.
	 * 
	 * @return an Iterator to iterator over the keys.
	 */
	@Override
	public Iterator keys() {
		return new Iter();
	}

	private class Iter implements Iterator<K> {
		private int index;
		private int size;
		private int keysUsed;
		private Entry[] temp;

		public Iter() {
			// create a copy of the dictionary
			temp = table;

			// keysUsed tracks iteration progress
			keysUsed = 0;

			// index tracks how far we have iterated
			int index = 0;
			size = temp.length;
		}

		/**
		 * Returns true if there are more elements in the dictionary to access.
		 */
		@Override
		public boolean hasNext() {
			return keysUsed < noKeys;
		}

		/**
		 * Returns the next key in the Dictionary.
		 */
		@Override
		public K next() {
			// check if current index is null, increment index if so
			while (temp[index] == null) {
				index++;
			}
			// return key from entry at current index, incrementing index and KeysUsed
			keysUsed++;
			return (K) temp[index++].getKey();
		}
	}

	/**
	 * Test if the dictionary is empty
	 * 
	 * @return true if the dictionary is empty, otherwise false
	 */
	@Override
	public boolean isEmpty() {
		return noKeys() == 0;
	}

	/**
	 * Get the number of keys in the dictionary
	 * 
	 * @return the number of keys in the dictionary
	 */
	@Override
	public int noKeys() {
		return this.noKeys;
	}

	/**
	 * BONUS: rehash table when load factor (filled cells / table size) reaches 50%
	 * 
	 * This method will check if the table needs rehashing, and does so if so.
	 * Should be called any time an element is added to the list.
	 */
	private void rehashCheck() {
		double loadFactor = (double) this.noKeys / (double) this.table.length;

		if (loadFactor > 0.5) {
			// factor is greater than 50%, rehash!
			// we will use the iterator to do this
			Iterator iter = this.keys();
			int newSize = 0;

			// figure out which prime is next
			for (int p : primes) {
				if (p > this.table.length) {
					newSize = p;
					break;
				}
			}

			// create a copy of table
			Entry[] tableClone = table.clone();

			// set current table to a new, empty table with new size
			table = new Entry[newSize];
			// be sure to reset noKeys since we will be using Put, or you will have more
			// keys than actual entries
			noKeys = 0;
			for (Entry e : tableClone) {
				if (e == null) {
					// skip null entries
					continue;
				}
				Object key = e.key;
				Object value = e.value;
				this.put(key, value);
			}
		}
	}

}
