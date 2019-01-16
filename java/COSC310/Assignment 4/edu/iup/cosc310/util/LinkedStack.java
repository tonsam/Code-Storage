package edu.iup.cosc310.util;

import java.util.NoSuchElementException;

/**
 * Linked Stack class for use with undo and redo in the painter program
 * 
 * @author David Kornish
 *
 * @param <E>
 */
public class LinkedStack<E> implements ItemStack<E> {
	private Node topRef;

	/**
	 * Node class to support a linked implementation of stack
	 */
	private class Node {
		E item;
		Node next;

		/**
		 * constructor for initializing a node's item and next reference, and adjusting
		 * the stack's top reference accordingly.
		 * 
		 * @param item
		 */
		public Node(E item) {
			this.item = item;
			this.next = topRef;
			topRef = this;
		}

	}

	/**
	 * Push an item to the top of the stack. Since all reference adjesting is
	 * handled by the constructor, this method simply has to create a new node.
	 */
	@Override
	public void pushItem(E item) {
		Node newNode = new Node(item);
	}

	/**
	 * Pop an item from the list. This will simultaneously return the item on the
	 * top of the stack and remove it from the stack.
	 */
	@Override
	public E popItem() {
		if (this.topRef == null) {
			throw new NoSuchElementException();
		} else {
			Node top = this.topRef;
			topRef = top.next;
			return top.item;
		}
	}

	/**
	 * Return the item on top of the stack without removing it
	 */
	@Override
	public E peekItem() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.topRef.item;
		}
	}

	/**
	 * check if the stack has any items in it
	 */
	@Override
	public boolean isEmpty() {
		if (this.topRef == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * remove all items from the stack.
	 */
	@Override
	public void clear() {
		this.topRef = null;
	}

}
