package edu.iup.cosc310.util;

import edu.iup.cosc310.util.LinkedItemList.Node;

/**
 * 
 * @author David Kornish
 *
 * @param <E>
 *            placeholder for some type of Object
 */
public class LinkedItemList<E> implements ItemList<E> {
	private Node head;
	private int numItems;

	/**
	 * constructor method for a LinkedItemList
	 */
	public LinkedItemList() {
		numItems = 0;
		this.head = new Node(null, null, null);
		head.previous = head;
		head.next = head;
	}

	/**
	 * Node class for a LinkedItemList
	 * 
	 * @author David Kornish
	 * 
	 */
	public class Node {
		E item;
		Node next;
		Node previous;

		public Node(E item, Node next, Node previous) {
			this.item = item;
			this.next = next;
			this.previous = previous;
		}

	}

	/**
	 * Constructor for a Node in a LinkedItemList
	 * 
	 * @param index
	 * @return
	 */
	private Node getNode(int index) {
		if (index < 0 || index >= this.numItems) {
			throw new IndexOutOfBoundsException();
		}
		Node pointer = this.head.next;
		for (int x = 0; x < index; x++) {
			pointer = pointer.next;
		}
		return pointer;
	}

	/**
	 * method to append a new node after a given node in the list
	 * 
	 * @param node
	 * @param item
	 */
	private void appendAfter(Node node, E item) {
		Node newNode = new Node(item, node.next, node);
		node.next.previous = newNode;
		node.next = newNode;
		this.numItems++;
	}

	/**
	 * method to remove a node from the list
	 * 
	 * @param node
	 */
	private void removeNode(Node node) {
		node.previous.next = node.next;
		node.next.previous = node.previous;
		this.numItems--;
	}

	/**
	 * method to append a node at the end of the list
	 */
	@Override
	public void append(E item) {
		appendAfter(this.head.previous, item);
	}

	/**
	 * method to insert a node into a given index
	 */
	@Override
	public void insert(E item, int index) {
		if (index == numItems) {
			appendAfter(head.previous, item);
		} else if (index == 0) {
			appendAfter(head, item);
		} else {
			appendAfter(getNode(index - 1), item);
		}
	}

	/**
	 * method to return the contents of a node at given index
	 */
	@Override
	public E get(int index) {
		return getNode(index).item;
	}

	/**
	 * method to remove a node at a given index
	 */
	@Override
	public E removeAtIndex(int index) {
		removeNode(getNode(index));
		return null;
	}

	/**
	 * method to return the number of items in the list
	 */
	@Override
	public int size() {
		return this.numItems;
	}

	/**
	 * boolean method to check if the list has any elements (besides the head node)
	 */
	@Override
	public boolean isEmpty() {
		if (this.numItems == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to clear all elements from the list
	 */
	@Override
	public void clear() {
		this.head.previous = head;
		this.head.next = head;
		this.numItems = 0;
	}


}
