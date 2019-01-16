package edu.iup.cosc310.util;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * circular implementation of a queue for the Banner program.
 * 
 * @author David Kornish
 * 
 *
 */
public class CircularQueue<E> implements ItemQueue<E> {

	private int front;
	private int rear;
	private int size;
	private int capacity;
	private static final int DEFAULT_CAPACITY = 4;
	private E[] arrayQueue;

	/**
	 * constructor for a circular queue, initializes variables.
	 */
	public CircularQueue() {
		this.capacity = DEFAULT_CAPACITY;
		this.front = 0;
		this.rear = -1;
		this.size = 0;
		this.arrayQueue = (E[]) new Object[capacity];
	}

	/**
	 * Enqueue an object to the end of the queue
	 * 
	 * @param item
	 *            the object to be enqueued
	 */
	public synchronized void enqueueItem(E item) {
		if (size == capacity) {
			reallocate();
		}
		this.size++;
		rear = (rear + 1) % capacity;
		arrayQueue[rear] = item;
	}

	/**
	 * Dequeue an object from the front of the queue
	 * 
	 * @return the item removed from the front of the queue
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 */
	public synchronized E dequeueItem() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		E result = arrayQueue[front];
		front = (front + 1) % capacity;
		size--;
		return result;

	}

	/**
	 * Examine the object at the head of the queue, but do not remove it.
	 * 
	 * @return the item at the head of the queue
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 */
	public synchronized E peekItem() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		}
		return arrayQueue[front];
	}

	/**
	 * Test if the queue is empty
	 * 
	 * @return true if the queue is empty, otherwise false
	 */
	public synchronized boolean isEmpty() {
		if (this.size == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the number of items in the queue
	 * 
	 * @return the number of items in the queue
	 */
	public synchronized int noItems() {
		return this.size;
	}

	/**
	 * reallocate array when capacity is exceeded
	 */
	private void reallocate() {
		int newCapacity = 2 * capacity;
		E[] newData = (E[]) new Object[newCapacity];
		int j = front;
		for (int i = 0; i < size; i++) {
			newData[i] = arrayQueue[j];
			j = (j + 1) % capacity;
		}
		front = 0;
		rear = size - 1;
		capacity = newCapacity;
		arrayQueue = newData;
	}

	/**
	 * return the size of the queue, used for debugging.
	 * 
	 * @return
	 */
	public int getSize() {
		return this.size;
	}

}
