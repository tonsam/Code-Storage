package edu.iup.cosc310.util;

import edu.iup.cosc310.util.CircularQueueIterationTest.Runner;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularQueueIterationTest {
	private CircularQueue<String> queue;

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
	 * Test iteration over three items in a queue
	 */
	@Test
	public void testIteratorThree() {
		queue = new CircularQueue();
		
		queue.enqueueItem("one");
		queue.enqueueItem("two");
		queue.enqueueItem("three");
		Iterator<String> iter = queue.iterator();
		assertEquals(iter.hasNext(), true);
		assertEquals(iter.next(), "one");
		assertEquals(iter.hasNext(), true);
		assertEquals(iter.next(), "two");
		assertEquals(iter.hasNext(), true);
		assertEquals(iter.next(), "three");
		assertEquals(iter.hasNext(), false);
		assertEquals(NoSuchElementException.class, catchException(() -> {
			iter.next();
		}));
	}

	/*
	 * Test iteration over three items in a queue, with modification to the queue
	 * being made during iteration.
	 */
	@Test
	public void testIteratorThreeWithMod() {
		queue = new CircularQueue();
		
		queue.enqueueItem("one");
		queue.enqueueItem("two");
		queue.enqueueItem("three");
		Iterator<String> iter = queue.iterator();
		assertEquals(iter.hasNext(), true);
		assertEquals(iter.next(), "one");
		assertEquals(iter.hasNext(), true);
		queue.enqueueItem("four");
		assertEquals(queue.dequeueItem(), "one");
		assertEquals(queue.dequeueItem(), "two");
		assertEquals(queue.dequeueItem(), "three");
		assertEquals(iter.next(), "two");
		assertEquals(queue.dequeueItem(), "four");
		assertEquals(queue.isEmpty(), true);
		assertEquals(queue.noItems(), 0);
		assertEquals(NoSuchElementException.class, catchException(() -> {
			queue.dequeueItem();
		}));
		assertEquals(NoSuchElementException.class, catchException(() -> {
			queue.peekItem();
		}));
		assertEquals(iter.hasNext(), true);
		assertEquals(iter.next(), "three");
		assertEquals(iter.hasNext(), false);
		assertEquals(NoSuchElementException.class, catchException(() -> {
			iter.next();
		}));

	}

	/*
	 * Test iteration over three items in a queue, using java's for each loop
	 */
	@Test
	public void testIteratorForEach() {
		queue = new CircularQueue<String>();
		
		queue.enqueueItem("one");
		queue.enqueueItem("two");
		queue.enqueueItem("three");

		for (String s : queue) {
			assertEquals(queue.dequeueItem(), s);
		}

		assertEquals(queue.isEmpty(), true);
		assertEquals(queue.noItems(), 0);
		assertEquals(NoSuchElementException.class, catchException(() -> {
			queue.dequeueItem();
		}));
		assertEquals(NoSuchElementException.class, catchException(() -> {
			queue.peekItem();
		}));
	}

	/*
	 * Test iteration over an empty queue
	 */
	@Test
	public void testEmptyIterator() {
		queue = new CircularQueue();
		
		Iterator<String> iter = queue.iterator();
		assertEquals(iter.hasNext(), false);
		assertEquals(NoSuchElementException.class, catchException(() -> {
			iter.next();
		}));
	}

	/*
	 * Test iteration over an empty queue that was modified after the iterator is
	 * created but before using the iterator methods.
	 */
	@Test
	public void testEmptyIteratorMod() {
		queue = new CircularQueue();
		
		Iterator<String> iter = queue.iterator();
		queue.enqueueItem("one");
		assertEquals(iter.hasNext(), false);
		assertEquals(NoSuchElementException.class, catchException(() -> {
			iter.next();
		}));
		assertEquals(queue.dequeueItem(), "one");
	}

}
