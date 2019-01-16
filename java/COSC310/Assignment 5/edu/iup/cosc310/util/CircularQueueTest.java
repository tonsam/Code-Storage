package edu.iup.cosc310.util;

import edu.iup.cosc310.util.CircularQueueTest.Runner;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

public class CircularQueueTest {
	private CircularQueue queue;

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
	 * test that a newly created queue has all of the traits one would expect an
	 * empty queue to have
	 */
	@Test
	public void testNewListIsEmpty() {
		queue = new CircularQueue();
		assertEquals(queue.isEmpty(), true);

		assertEquals(NoSuchElementException.class, catchException(() -> {
			queue.dequeueItem();
		}));
	}

	/*
	 * test that adding an item to a new queue affects parameters correctly
	 */
	@Test
	public void testAddingOne() {
		queue = new CircularQueue();
		assertEquals(queue.isEmpty(), true);
		queue.enqueueItem("one");
		assertEquals(queue.isEmpty(), false);
	}

	/*
	 * test that adding beyond default capacity will correctly double the array
	 * size,
	 */
	@Test
	public void testAddingBeyondOriginalCapacity() {
		queue = new CircularQueue();
		assertEquals(queue.isEmpty(), true);

		queue.enqueueItem("one");
		queue.enqueueItem("dos");
		queue.enqueueItem("3");
		queue.enqueueItem("cuatro");
		queue.enqueueItem("five");

		assertEquals(queue.isEmpty(), false);
		assertEquals(queue.getSize(), 5);
	}

	/*
	 * test that code to double array can function more than once
	 */
	@Test
	public void testAddingBeyondDoubledOriginalCapacity() {
		queue = new CircularQueue();
		assertEquals(queue.isEmpty(), true);
		for (int x = 0; x < 10; x++) {
			queue.enqueueItem("what an item");
		}
		assertEquals(queue.isEmpty(), false);
		assertEquals(queue.getSize(), 10);
	}

	/*
	 * test that queue properly circles when the an item in enqueued beyond the end
	 * of the array but capacity is not filled.
	 */
	@Test
	public void testCircularity() {
		queue = new CircularQueue();
		assertEquals(queue.isEmpty(), true);
		queue.enqueueItem("1");
		queue.enqueueItem("2");
		queue.enqueueItem("3");

		assertEquals(queue.isEmpty(), false);
		assertEquals(queue.getSize(), 3);

		queue.dequeueItem();
		queue.dequeueItem();

		assertEquals(queue.isEmpty(), false);
		assertEquals(queue.getSize(), 1);

		queue.enqueueItem("4");
		queue.enqueueItem("5");
		queue.enqueueItem("7");
		queue.enqueueItem("6");
	}

	/*
	 * test that removing items from an empty queue throws the proper exception
	 */
	@Test
	public void testRemovingItemsFromEmptyQueue() {
		queue = new CircularQueue();
		assertEquals(queue.isEmpty(), true);

		assertEquals(NoSuchElementException.class, catchException(() -> {
			queue.dequeueItem();
		}));
	}

	/*
	 * test that there are no anomalies in queue size when removing and adding items
	 * in sequence
	 */
	@Test
	public void testRemovingItemsFromSmallQueue() {
		queue = new CircularQueue();
		assertEquals(queue.isEmpty(), true);
		queue.enqueueItem("1");
		queue.enqueueItem("2");
		queue.enqueueItem("3");

		assertEquals(queue.isEmpty(), false);
		assertEquals(queue.getSize(), 3);

		queue.dequeueItem();
		queue.dequeueItem();

		assertEquals(queue.isEmpty(), false);
		assertEquals(queue.getSize(), 1);

		queue.dequeueItem();

		assertEquals(queue.isEmpty(), true);
		assertEquals(queue.getSize(), 0);

		assertEquals(NoSuchElementException.class, catchException(() -> {
			queue.dequeueItem();
		}));
	}

	/*
	 * test that queue continues to function when faced with large quantities of
	 * enqueue and dequeue calls
	 */
	@Test
	public void testMediumStressTest() {
		queue = new CircularQueue();
		assertEquals(queue.isEmpty(), true);

		// add a few items
		for (int x = 0; x < 100; x++) {
			queue.enqueueItem("what an item");
		}

		assertEquals(queue.isEmpty(), false);
		assertEquals(queue.getSize(), 100);

		// remove some of them items
		for (int x = 0; x < 50; x++) {
			queue.dequeueItem();
		}

		assertEquals(queue.getSize(), 50);

		// add a few more items
		for (int x = 0; x < 100; x++) {
			queue.enqueueItem("what an item");
		}
	}

	/*
	 * test that the queue continues to function under an extreme number of actions
	 */
	@Test
	public void testHeavyStressTest() {
		queue = new CircularQueue();
		assertEquals(queue.isEmpty(), true);

		// alternate between adding and removing items
		// if nothing breaks, circular functionality is working
		for (int x = 0; x < 1000000; x++) {
			for (int y = 0; y < 10; y++) {
				queue.enqueueItem("an item");
			}
			for (int y = 0; y < 5; y++) {
				queue.dequeueItem();
			}
		}

		assertEquals(queue.isEmpty(), false);

	}

}
