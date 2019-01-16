package edu.iup.cosc310.util;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * An implementation of an ItemQueue using a LinkedList as a delegate. This
 * implementation is thread safe.
 * 
 * @author dtsmith
 */
public class DelegateLinkedItemQueue<E> implements ItemQueue<E> {
	private LinkedList<E> list = new LinkedList<E>();

	public Iterator<E> iterator() {
	    throw new UnsupportedOperationException();
	}

	
	/**
	 * Enqueue an object to the end of the queue
	 * @param item the object to be enqueued
	 */
	public synchronized void enqueueItem(E item) {
		list.add(item);
	}

	/**
	 * Dequeue an object from the front of the queue
	 * @return the item removed from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public synchronized E dequeueItem() {
		return list.removeFirst();
	}

	/**
	 * Examine the object at the head of the queue, but do
	 * not remove it.
	 * @return the item at the head of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public synchronized E peekItem() {
		return list.getFirst();
	}

	/**
	 * Test if the queue is empty
	 * @return true if the queue is empty, otherwise false
	 */
	public synchronized boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Get the number of items in the queue
	 * 
	 * @return the number of items in the queue
	 */
	public synchronized int noItems() {
		return list.size();
	}
}
