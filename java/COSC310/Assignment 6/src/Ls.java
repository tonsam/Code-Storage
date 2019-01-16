import java.util.NoSuchElementException;

/**
 * A class that has similar semantics to Scheme/Lisp lists, except it is object
 * oriented instead of functional. Base methods are car, cdr, and cons:
 * 
 * car returns the value of the first element in the list, cdr returns the
 * sublist past the first element, and cons returns a new list composed of new
 * car inserted at the beginning of a list
 * 
 * Ls lists are considered to be non-mutable. That is a given instance must not
 * change its contained set of elements. Any modifications, must result in
 * creation of new Ls lists.
 * 
 * Ls lists can be instantiated yet be considered to be empty. A method isEmpty
 * returns true for an empty Ls list, otherwise false.
 * 
 * @author David Smith, modified by David Kornish
 */
public class Ls<E> implements Comparable<Ls> {
	private E car;
	private Ls<E> cdr;

	/**
	 * Constructor to build a scheme list.
	 * 
	 * @param car
	 *            the car of the list
	 * @param cdr
	 *            the cdr for the list, must not be null
	 * @throws RuntimeException
	 *             on attempt to create a Ls list with a null cdr
	 */
	private Ls(E car, Ls<E> cdr) {
		this.car = car;
		this.cdr = cdr;
	}

	/**
	 * Private constructor to recursively construct a scheme list over a variable
	 * list of parameters.
	 * 
	 * @param i
	 *            the current position when constructing the list
	 * @param parms
	 *            the variable list of parameters
	 */
	private Ls(int i, E... parms) {
		if (i < parms.length) {
			car = parms[i];
			cdr = new Ls<E>(i + 1, parms);
		} else {
			car = null;
			cdr = null; // Ls with an empty cdr is the end marker node
		}
	}

	/**
	 * Constructor to create a scheme list from its parameters.
	 * 
	 * @param parms
	 *            the variable list of parameters
	 */
	public Ls(E... parms) {
		this(0, parms);
	}

	/**
	 * Check if the list begins with the same elements as another list
	 * 
	 * @param newLs
	 * @return
	 */
	public boolean startsWith(Ls<E> newLs) {
		if (newLs.isEmpty()) {
			return true;
		} else if (this.isEmpty() && !newLs.isEmpty()) {
			return false;
		} else if (newLs.car() == this.car()) {
			return this.cdr().startsWith(newLs.cdr());
		} else {
			return false;
		}
	}

	/**
	 * return the index of an item in the list, or -1 if there is no occurrence of
	 * that item
	 * 
	 * @param item
	 * @return
	 */
	public int indexOf(Ls<E> item) {
		if (!this.isEmpty()) {
			if (this.startsWith(item)) {
				return 0;
			} else {
				int index = this.cdr().indexOf(item);
				if (index == -1) {
					return -1;
				} else {
					return index + 1;
				}
			}
		}
		return -1;
	}

	/**
	 * concatenate one list onto this list.
	 * 
	 * @param toConcat
	 * @return
	 */
	public Ls<E> concat(Ls<E> toConcat) {
		if (this.isEmpty()) {
			this.car = toConcat.car();
			this.cdr = toConcat.cdr();
			return this;
		}
		this.cdr().concat(toConcat);
		return this;
	}

	/**
	 * a sublist method with one argument, functions similar to substring by
	 * returning a list containing only elements starting at the specified index
	 * 
	 * @param start
	 * @return
	 */
	public Ls<E> sublist(int start) {
		// check for exceptions
		if (start > this.size() + 1 || start < 0) {
			throw new IndexOutOfBoundsException();
		}

		if (start == 0) {
			return this;
		}
		return this.cdr().sublist(start - 1);
	}

	/**
	 * a sublist method with two argument, functions similar to substring, returning
	 * a new list of java objects between the specified start and end indices
	 * 
	 * @param start
	 * @return
	 */
	public Ls<E> sublist(int start, int end) {
		// check for exceptions
		if (start > end) {
			throw new IndexOutOfBoundsException();
		} else if (start > this.size() + 1 || start < 0) {
			throw new IndexOutOfBoundsException();
		} else if (end > this.size() + 1 || end < 0) {
			throw new IndexOutOfBoundsException();
		}

		// Trim the front of the list. These two lines will do nothing during recursive
		// steps, since start will be set to 0.
		Ls frontTrimmed = this.sublist(start);
		end -= start;

		// end number is used to track recursion.
		if (end == 0) {
			// since end is zero, the current element is the original break index. set the
			// car and cdr to null to cut off the rest of the list.
			this.car = null;
			this.cdr = null;
			return this;
		}
		return frontTrimmed.cdr().sublist(0, end - 1).cons(frontTrimmed.car());

	}

	/**
	 * Flatten method, which checks if any of the elements in a list are themselves
	 * lists, and if so, adds the nested list's elements into the parent list as
	 * normal elements.
	 * 
	 * @return
	 */
	public Ls<E> flatten() {
		if (this.cdr().isEmpty()) {
			// if cdr is empty, you are at the last element of the list.
			if (this.car() instanceof Ls) {
				// the last element in the list was another list... we aren't done yet
				Ls first = (Ls) this.car();
				return new Ls().concat(first).flatten();
			} else {
				// last element is not an Ls object, we are good.
				return new Ls().cons(this.car());
			}
		}

		// check if a non-last element is a list.
		if (this.car() instanceof Ls) {
			Ls first = (Ls) this.car();
			Ls second = this.cdr();
			return new Ls().concat(first).concat(second).flatten();
		}

		// current non-last element is not a list, therefore return a new list
		// containing this element plus whatever comes next, but flattened.
		return new Ls().cons(this.car()).concat(this.cdr.flatten());
	}

	public Ls<E> replaceAll(E target, E replacement) {
		if (this.cdr().isEmpty()) {
			// we are on the last element.
			// check whether the last element's car contains the target element
			if (this.car().equals(target)) {
				// the car is the target, replace it.
				return new Ls().cons(replacement);
			} else if (this.car instanceof Ls) {
				// the last car is a list, which may contain the target item.
				// run replaceAll on the list.
				Ls<E> listElement = (Ls) this.car();
				return new Ls().cons((E) listElement.replaceAll(target, replacement));
			}

			// there are no more elements and we didn't replace anything, so return
			// a list containing the final car.
			return new Ls().cons(this.car());
		}

		// we are not on the last element.
		// check whether current element's car contains the target element
		if (this.car().equals(target)) {
			// the car is the target, replace it.
			this.car = replacement;
		} else if (this.car instanceof Ls) {
			// the car is a list, which may contain the target item.
			// run replaceAll on the list.
			Ls<E> listElement = (Ls) this.car();
			this.car = (E) listElement.replaceAll(target, replacement);
		}

		// there are more elements and we didn't replace anything, so return
		// a list containing the current car, calling replaceAll on the cdr.
		return this.cdr().replaceAll(target, replacement).cons(this.car());
	}

	/**
	 * Compare method for two lists. Assumes that both input lists consist of
	 * integers only.
	 */
	@Override
	public int compareTo(Ls compareTo) {
		// first, check if either of the lists wins outright
		if ((int) this.car() > (int) compareTo.car()) {
			return 1;
		} else if ((int) this.car() < (int) compareTo.car()) {
			return -1;
		}

		// no winner so far, check if either cdr is empty
		if (this.cdr().isEmpty() && compareTo.cdr().isEmpty()) {
			// there is a tie and both are empty, return 0
			return 0;
		} else if (this.cdr().isEmpty()) {
			// just "this" is empty, compareTo list wins
			return -1;
		} else if (compareTo.cdr().isEmpty()) {
			// compareTo list is empty, "this" wins
			return 1;
		} else {
			// neither list is empty and score is tied, recurse.
			return this.cdr().compareTo(compareTo.cdr());
		}
	}

	/**
	 * Get the car
	 * 
	 * @return the car - the first element in the list
	 * @throws NoSuchElementException
	 *             on an empty list
	 */
	public E car() {
		if (cdr == null) {
			throw new NoSuchElementException();
		}
		return car;
	}

	/**
	 * Get the cdr
	 * 
	 * @return the cdr - Ls list past the first element
	 * @throws NoSuchElementException
	 *             on an empty list
	 */
	public Ls<E> cdr() {
		if (cdr == null) {
			throw new NoSuchElementException();
		}
		return cdr;
	}

	/**
	 * Cons a new car onto a list.
	 * 
	 * @param car
	 *            the new car.
	 * @return a new scheme list with the car at the front of the list
	 */
	public Ls<E> cons(E car) {
		return new Ls<E>(car, this);
	}

	/**
	 * Identify if a scheme list is empty
	 * 
	 * @return true if the scheme list is empty, otherwise false.
	 */
	public boolean isEmpty() {
		return cdr == null;
	}

	/**
	 * Get the string representation of the list. The string representation is a
	 * comma separated list of elements (as strings) enclosed in parentheses.
	 * 
	 * @return string representation of a list.
	 */
	public String toString() {
		return "(" + toRemainingString();
	}

	/**
	 * Private method to print the string representation of a list past the leading
	 * square bracket. If empty, prints the closing parenthesis, otherwise prints
	 * the car and then uses recursion to print the remaining elements.
	 * 
	 * @return string representation of a list past the leading parenthesis.
	 */
	private String toRemainingString() {
		return isEmpty() ? ")" : car.toString() + (cdr.isEmpty() ? "" : ",") + cdr.toRemainingString();
	}

	/**
	 * Get the size of the list
	 * 
	 * @return number of elements in the list
	 */
	public int size() {
		if (isEmpty()) {
			return 0;
		} else {
			return 1 + cdr.size();
		}
	}

	/**
	 * Compares the specified object with this Ls for equality. Returns true only if
	 * the specified object is also an Ls, both lists have the same size, and all
	 * corresponding pairs of elements in the two lists are equal. In other words,
	 * two Ls lists are defined to be equal if they contain the same elements in the
	 * same order.
	 * 
	 * @param other
	 *            the other object
	 * @return true if equals, otherwise false
	 */
	public boolean equals(Object other) {
		if (!(other instanceof Ls)) {
			return false;
		}

		Ls<?> otherLs = (Ls<?>) other;

		if (isEmpty() && otherLs.isEmpty()) {
			return true;
		}

		if (isEmpty() || otherLs.isEmpty()) {
			return false;
		}

		if (car.equals(otherLs.car())) {
			return cdr.equals(otherLs.cdr);
		} else {
			return false;
		}
	}

}
