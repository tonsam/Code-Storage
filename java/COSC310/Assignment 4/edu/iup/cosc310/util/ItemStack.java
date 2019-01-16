package edu.iup.cosc310.util;

/**
 * A stack of items.
 */
public interface ItemStack<E> {

      /**
       * Push an item onto the stack
       *
       * @param item – item to be pushed
       */
public void pushItem(E item);

/**
       * Pop an item of the stack
       *
       * @return the popped item
       * @throw NoSuchElementException if stack is empty
       */
public E popItem();

/**
       * Return the item on the top of the stack without removing it
       *
       * @return the item on the top of the stack
       * @throw NoSuchElementException if stack is empty
       */
public E peekItem();

/**
       * Test if the stack is empty
       *
       * @return true if the stack is empty, otherwise false
       */
public boolean isEmpty();

/**
       * Clear the stack, removes all items.
       */
public void clear();
}

