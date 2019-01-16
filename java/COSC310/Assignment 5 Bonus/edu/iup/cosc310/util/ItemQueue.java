package edu.iup.cosc310.util;

/**
 * An abstract data type for a Queue.  Specifies the 5
 * main methods of a queue
 * 
 * @author dtsmith
 */
public interface ItemQueue<E> extends Iterable<E> {
	/**
	 * Enqueue an object to the end of the queue
	 * @param item the object to be enqueued
	 */
	public void enqueueItem(E item);
	
	/**
	 * Dequeue an object from the front of the queue
	 * @return the item removed from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public E dequeueItem();
	
	/**
	 * Examine the object at the head of the queue, but do
	 * not remove it.
	 * @return the item at the head of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public E peekItem();
	
	/**
	 * Test if the queue is empty
	 * @return true if the queue is empty, otherwise false
	 */
	public boolean isEmpty();
	
	/**
	 * Get the number of items in the queue
	 * 
	 * @return the number of items in the queue
	 */
	public int noItems();
}
