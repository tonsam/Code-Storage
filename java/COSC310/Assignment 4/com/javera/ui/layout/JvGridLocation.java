//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui.layout;

/**
 * A grid location representing a location in a row, column coordinate space, specified
 * in integer precision.
 *
 * @author David T. Smith
 */
public class JvGridLocation implements java.io.Serializable {
	/**
	 * The <i>row</i> coordinate.
	 * If no <i>row</i> coordinate is set it will default to '0'.
	 */
	public int row;

	/**
	 * The <i>column</i> coordinate.
	 * If no <i>column</i> coordinate is set it will default to '0'.
	 */
	public int column;

	/**
	 * The <i>noRows</i> the element will occupy
	 * If <i>noRows</i> is not set it will default to '1'.
	 */
	public int noRows;

	/**
	 * The <i>noColumns</i> the element will occupy
	 * If <i>noRnoColumnsows</i> is not set it will default to '1'.
	 */
	public int noColumns;

	/**
	 * Constructs and initializes a grid location at the origin
	 * (0,&nbsp;0) of the coordinate space.
	 */
	public JvGridLocation() {
		this(0, 0);
	}

	/**
	 * Constructs and initializes a point with the same location as
	 * the specified <code>Point</code> object.
	 * @param       p a point.
	 * @since       JDK1.1
	 */
	public JvGridLocation(JvGridLocation p) {
		this(p.row, p.column, p.noRows, p.noColumns);
	}

	/**
	 * Constructs and initializes a grid location at the specified
	 * (<i>row</i>,&nbsp;<i>column</i>) location in the coordinate space.
	 * @param       row    the <i>row</i> coordinate.
	 * @param       column the <i>column</i> coordinate.
	 */
	public JvGridLocation(int row, int column) {
		this(row, column, 1, 1);
	}

	/**
	 * Constructs and initializes a grid location at the specified
	 * (<i>row</i>,&nbsp;<i>column</i>) location in the coordinate space.
	 * @param       row    the <i>row</i> coordinate.
	 * @param       column the <i>column</i> coordinate.
	 * @param       noRows the <i>noRows</i> the entry will occupy.
	 * @param       noColumn the <i>noColumns</i> the entry will occupy.
	 */
	public JvGridLocation(int row, int column, int noRows, int noColumns) {
		this.row = row;
		this.column = column;
		this.noRows = noRows;
		this.noColumns = noColumns;
	}

	/**
	 * Returns the row coordinate of the grid location.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column coordinate of the grid location.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Returns the number of rows the entry will occupy.
	 */
	public int getNoRows() {
		return noRows;
	}

	/**
	 * Returns the number of columns the entry will occupy.
	 */
	public int getNoColumns() {
		return noColumns;
	}

	/**
	 * Determines whether an instance of <code>GridLocation</code> is equal
	 * to this point.  Two instances of <code>GridLocation</code> are equal if
	 * the values of their <code>row</code> and <code>column</code> member
	 * fields, representing their position in the coordinate space, are
	 * the same.
	 * @param      obj   an object to be compared with this point.
	 * @return     <code>true</code> if the object to be compared is
	 *                     an instance of <code>GridLocation</code> and has
	 *                     the same values; <code>false</code> otherwise.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof JvGridLocation) {
			JvGridLocation gl = (JvGridLocation) obj;
			
			return (row == gl.row)
				&& (column == gl.column)
				&& (noRows == gl.noRows)
				&& (noColumns == gl.noColumns);
		}

		return super.equals(obj);
	}
}
