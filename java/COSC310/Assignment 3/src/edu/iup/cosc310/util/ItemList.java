package edu.iup.cosc310.util;

/**
 * An ordered list of items.
 */
public interface ItemList<E> {

	/**
	 * Append an item to the end of the list
	 *
	 * @param item
	 *            – item to be appended
	 */
	public void append(E item);

	/**
	 * Insert an item at a specified index position
	 *
	 * @param item
	 *            – item to be inserted
	 * @param index
	 *            – index position where to insert the item
	 * @throw IndexOutOfBoundsException if index is < 0 or > no of items
	 */
	public void insert(E item, int index);

	/**
	 * Return an item at a specified index
	 *
	 * @param index
	 *            – index of the item to return
	 * @return the item at the specified index
	 * @throw IndexOutOfBoundsException if index is < 0 or >= no of items
	 */
	public E get(int index);

	/**
	 * Remove an item at a specified index
	 *
	 * @param index
	 *            – index of the item to be removed
	 * @return the removed item
	 * @throw IndexOutOfBoundsException if index is < 0 or >= no of items
	 */
	public E removeAtIndex(int index);

	/**
	 * Return the number of items currently in the list
	 *
	 * @return the number of items in the list
	 */
	public int size();

	/**
	 * Determine if the list is empty
	 *
	 * @return true if the list is empty, otherwise false
	 */
	public boolean isEmpty();

	/**
	 * Clear the list. The list becomes empty
	 *
	 */
	public void clear();
}
