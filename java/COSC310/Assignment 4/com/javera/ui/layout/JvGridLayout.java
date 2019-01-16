//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

import com.javera.ui.LayoutPrint;
import com.javera.ui.LayoutPrintLayout;

/**
 * A Javera grid layout arranges components in grid where the rows and columns can be
 * assigned weights and alignments.  Each cell of the grid is sized according to their column
 * width and row height where the column width and row height is determined as follows:
 *
 * For columns without a weight assignment the column width is the widest preferred size of all
 * components in the column.
 *
 * For columns with assigned weights, the width of the column is set in proportion to their assigned
 * weights such that the total width of all columns fills the width of the container.
 *
 * For rows without a weight assignment the row height is the highest preferred size of all
 * components in the row.
 *
 * For rows with assigned weights, the height of the row is set in proportion to their assigned
 * weights such that the total height of all rows fills the height of the container.
 *
 * When a component is sized to its column width and row height, the row alignment and column
 * alignment goven the placement in of the component in the cell.  An alignment of FILL (the default)
 * specify that the compoent is to be resized to fill the cell size, otherwise the positioned within
 * the cell accordingly.
 *
 * A component's location is specified in the grid by passing a Point as the second argument on the
 * add method to the container.  The x and y of the point identify the column and row in the grid.
 * Alternatively a Rectangle can be used as the second argument in which case the width and height
 * of the rectangle specify that the component is to span a width number of columns and a height
 * number of rows.
 *
 * A Javera grid layout is similar in function a GridLayout except column sizes and row sizes are
 * individually used to determine the size of a cell.   Javera grid layout also provides gap separation and margins.
 *
 * @author David T. Smith
 */
public class JvGridLayout implements LayoutManager2, LayoutPrintLayout, java.io.Serializable {
	// Alignments

	/**
	 * Fill alignment, specifies the component is to be resized to the column width and or
	 * row height accordingly.
	 */
	public static final int FILL = 0;

	/**
	 * Center alignment, specifies the component is to be centered within the column or
	 * row accordingly.
	 */
	public static final int CENTER = 1;

	/**
	 * Left alignment, specifies the component is to be left aligned within the column.
	 */
	public static final int LEFT = 2;

	/**
	 * Top alignment, specifies the component is to be top aligned within the row
	 */
	public static final int TOP = 2;

	/**
	 * Right alignment, specifies the component is to be right aligned within the column.
	 */
	public static final int RIGHT = 3;

	/**
	 * Bottom alignment, specifies the component is to be bottom aligned with the row
	 */
	public static final int BOTTOM = 3;

	/**
	 * The number of rows in the grid
	 */
	private int nRows;

	/**
	 * The number of columns in the grid
	 */
	private int nColumns;

	/**
	 * The weighted grid layout manager allows a seperation of
	 * components with gaps.  The hgap will
	 * specify the horizontal space between components.
	 *
	 * @serial
	 * @see getGap
	 * @see setGap
	 */

	private int hGap;

	/**
	 * The weighted grid layout manager allows a seperation of
	 * components with gaps.  The vgap will
	 * specify the vertical space between components.
	 *
	 * @serial
	 * @see getGap
	 * @see setGap
	 */
	private int vGap;

	/**
	 * The weighted grid layout manager allows specification of
	 * a top margin to be reserved above laidout
	 * components.
	 *
	 * @serial
	 * @see getTopMargin
	 * @see setTopMargin
	 */
	private int topMargin;

	/**
	 * The weighted grid layout manager allows specification of
	 * a bottom margin to be reserved below laidout
	 * components.
	 *
	 * @serial
	 * @see getBottomMargin
	 * @see setBottomMargin
	 */
	private int bottomMargin;

	/**
	 * The weighted grid layout manager allows specification of
	 * a left margin to be reserved to the left of the leftmost
	 * component.
	 *
	 * @serial
	 * @see getLeftMargin
	 * @see setLeftMargin
	 */
	private int leftMargin;

	/**
	 * The weighted grid layout manager allows specification of
	 * a right margin to be reserved to the right of the rightmost
	 * component.
	 *
	 * @serial
	 * @see getRightMargin
	 * @see setRightMargin
	 */
	private int rightMargin;

	/**
	 * The row weights
	 */
	private double[] rowWeights;

	/**
	 * The column weights
	 */
	private double[] columnWeights;

	/**
	 * The row alignments
	 */
	private int[] rowAlignments;

	/**
	 * The column alignments
	 */
	private int[] columnAlignments;

	/**
	 * The row heights
	 */
	private int[] rowHeights;

	/**
	 * The column widths
	 */
	private int[] columnWidths;

	private boolean hasPreferredSizes = false;
	private boolean hasSetSizes = false;

	/**
	 * The row heights
	 */
	private int[] preferredRowHeights;

	/**
	 * The column widths
	 */
	private int[] preferredColumnWidths;

	/**
	 * The comonent map
	 */
	private Component[][] map;

	/**
	 * Constructs a new Weighted Grid Layout with a specified number of rows, a specified number
	 * or columns, a default 5 pixel gaps, and a 5 pixel margins.
	 *
	 * @param nRows number of rows
	 * @param nColumns number of columns
	 */
	public JvGridLayout(int nRows, int nColumns) {
		this(nRows, nColumns, 5, 5, 5, 5, 5, 5);
	}

	/**
	 * Constructs a new Weighted Layout a specified number of rows, columns, gaps,
	 * and margins.
	 *
	 * @param nRows          number of rows
	 * @param nColumns       number of columns
	 * @param hGap           the horizontal gap between components.
	 * @param vhGap          the vertical gap between components.
	 * @param topMargin      the top margin.
	 * @param leftMargin     the left margin.
	 * @param bottomMargin   the bottom margin.
	 * @param rightMargin    the right margin.
	 */
	public JvGridLayout(
		int nRows,
		int nColumns,
		int hGap,
		int vGap,
		int topMargin,
		int leftMargin,
		int bottomMargin,
		int rightMargin) {
		this.nRows = nRows;
		this.nColumns = nColumns;
		this.hGap = hGap;
		this.vGap = vGap;
		this.topMargin = topMargin;
		this.leftMargin = leftMargin;
		this.bottomMargin = bottomMargin;
		this.rightMargin = rightMargin;
		this.rowWeights = new double[nRows];
		this.rowAlignments = new int[nRows];
		this.rowHeights = new int[nRows];
		this.preferredRowHeights = new int[nRows];
		this.columnWeights = new double[nColumns];
		this.columnAlignments = new int[nColumns];
		this.columnWidths = new int[nColumns];
		this.preferredColumnWidths = new int[nColumns];
		this.map = new Component[nRows][nColumns];
	}

	/**
	 * Gets the horizontal gap between components.
	 * @return the horizontal gap between components.
	 */
	public int getHGap() {
		return hGap;
	}

	/**
	 * Sets the horizontal gap between components.
	 * @param hGap the horizontal gap between components
	 */
	public void setHGap(int hGap) {
		this.hGap = hGap;
	}

	/**
	 * Gets the vertical gap between components.
	 * @return the vertical gap between components.
	 */
	public int getVGap() {
		return vGap;
	}

	/**
	 * Sets the vertical gap between components.
	 * @param vGap the vertical gap between components
	 */
	public void setVGap(int vGap) {
		this.vGap = vGap;
	}

	/**
	 * Gets the top margin to be reserved above all components.
	 * @return the top margin.
	 */
	public int getTopMargin() {
		return topMargin;
	}

	/**
	 * Sets the top margin to be reserve above components.
	 * @param topMargin the top margin.
	 */
	public void setTopMargin(int topMargin) {
		this.topMargin = topMargin;
	}

	/**
	 * Gets the left margin to be reserved to the left of the leftmost component.
	 * @return the left margin.
	 */
	public int getLeftMargin() {
		return leftMargin;
	}

	/**
	 * Sets the left margin to be reserved to the left of the leftmost component.
	 * @param leftMargin the left margin.
	 */
	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
	}

	/**
	 * Gets the bottom margin to be reserved below all components.
	 * @return the bottom margin.
	 */
	public int getBottomMargin() {
		return bottomMargin;
	}

	/**
	 * Sets the bottom margin to be reserve below components.
	 * @param bottomMargin the bottom margin.
	 */
	public void setBottomMargin(int bottomMargin) {
		this.bottomMargin = bottomMargin;
	}

	/**
	 * Gets the right margin to be reserved to the right of the rightmost component.
	 * @return the right margin.
	 */
	public int getRightMargin() {
		return rightMargin;
	}

	/**
	 * Sets the right margin to be reserved to the right of the rightmost component.
	 * @param rightMargin the right margin.
	 */
	public void setRightMargin(int rightMargin) {
		this.rightMargin = rightMargin;
	}

	/**
	 * Set the weight of a given row
	 *
	 * @param row    row to be assigned a weight
	 * @param weight the row weight
	 */
	public void setRowWeight(int row, double weight) {
		rowWeights[row] = weight;
	}

	/**
	 * Set the weight of a given column
	 *
	 * @param column to be assigned a weight
	 * @param weight the column weight
	 */
	public void setColumnWeight(int column, double weight) {
		columnWeights[column] = weight;
	}

	/**
	 * Set the alignment of a given row
	 *
	 * @param row       row to be assigned an alignment
	 * @param alignment the row alignment FILL, CENTER, LEFT, RIGHT
	 */
	public void setRowAlignment(int row, int alignment) {
		rowAlignments[row] = alignment;
	}

	/**
	 * Set the alignment of a given column
	 *
	 * @param column    column to be assigned an alignment
	 * @param alignment the column alignment FILL, CENTER, TOP, BOTTOM
	 */
	public void setColumnAlignment(int column, int alignment) {
		columnAlignments[column] = alignment;
	}

	/**
	 * Adds the specified component to the layout, using the specified
	 * constraint object.
	 * @param comp the component to be added
	 * @param constraints  where/how the component is added to the layout.
	 */
	public void addLayoutComponent(Component comp, Object coord) {
		if (coord instanceof JvGridLocation) {
			JvGridLocation gl = (JvGridLocation) coord;
			
			for (int r = 0; r < gl.getNoRows(); r++) {
				for (int c = 0; c < gl.getNoColumns(); c++) {
					map[gl.row + r][gl.column + c] = comp;
				}
			}
		} else {
			throw new IllegalArgumentException("cannot add to layout: coordinate must be a JvGridLocation");
		}
	}

	/**
	 * Returns the maximum size of this component.
	 * @see java.awt.Component#getMinimumSize()
	 * @see java.awt.Component#getPreferredSize()
	 * @see LayoutManager
	 */
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Returns the alignment along the x axis.  This specifies how
	 * the component would like to be aligned relative to other
	 * components.  The value should be a number between 0 and 1
	 * where 0 represents alignment along the origin, 1 is aligned
	 * the furthest away from the origin, 0.5 is centered, etc.
	 */
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	/**
	 * Returns the alignment along the y axis.  This specifies how
	 * the component would like to be aligned relative to other
	 * components.  The value should be a number between 0 and 1
	 * where 0 represents alignment along the origin, 1 is aligned
	 * the furthest away from the origin, 0.5 is centered, etc.
	 */
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	/**
	 * Invalidates the layout, indicating that if the layout manager
	 * has cached information it should be discarded.
	 */
	public void invalidateLayout(Container target) {
		hasPreferredSizes = false;
		hasSetSizes = false;
	}

	/**
	 * Adds the specified component to the layout. Not used by this class.
	 * @param name the name of the component
	 * @param comp the component to be added
	 */
	public void addLayoutComponent(String name, Component comp) {
	}

	/**
	 * Removes the specified component from the layout.
	 * @param comp the component to remove
	 */
	public void removeLayoutComponent(Component comp) {
		for (int column = 0; column < nColumns; column++) {
			for (int row = 0; row < nRows; row++) {
				if (comp == map[row][column]) {
					map[row][column] = null;
				}
			}
		}
	}

	/**
	 * Get the preferred column and preferred row sizes.
	 */
	private void getPreferredSizes() {
		if (hasPreferredSizes) {
			return;
		}

		for (int column = 0; column < nColumns; column++) {
			for (int row = 0; row < nRows; row++) {
				if (map[row][column] != null) {

					Dimension size = map[row][column].getPreferredSize();

					if ((column == 0
						|| map[row][column] != map[row][column - 1])
						&& (column == nColumns - 1
							|| map[row][column] != map[row][column + 1])) {
						if (size.width > preferredColumnWidths[column]) {
							preferredColumnWidths[column] = size.width;
						}
					}

					if ((row == 0 || map[row][column] != map[row - 1][column])
						&& (row == nRows - 1
							|| map[row][column] != map[row + 1][column])) {
						if (size.height > preferredRowHeights[row]) {
							preferredRowHeights[row] = size.height;
						}
					}
				}
			}
		}

		hasPreferredSizes = true;
	}

	/**
	 * Returns the preferred dimensions for this layout given the components
	 * in the specified target container.
	 * @param target the component which needs to be laid out
	 * @return    the preferred dimensions to lay out the
	 *            subcomponents of the specified container.
	 */
	public Dimension minimumLayoutSize(Container target) {
		return preferredLayoutSize(target);
	}

	/**
	 * Returns the minimum dimensions needed to layout the components
	 * contained in the specified target container.
	 * @param target the component which needs to be laid out
	 * @return    the minimum dimensions to lay out the
	 *            subcomponents of the specified container.
	 */
	public Dimension preferredLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Insets insets = target.getInsets();
			int width = 0;
			int height = 0;
			double minColumnRatio = 0.0;
			int minColumnWidth = 0;
			double minColumnWeight = 0.0;
			double totalColumnWeight = 0.0;
			double minRowRatio = 0.0;
			int minRowHeight = 0;
			double minRowWeight = 0.0;
			double totalRowWeight = 0.0;

			getPreferredSizes();

			for (int column = 0; column < nColumns; column++) {
				if (columnWeights[column] > 0.0) {
					totalColumnWeight += columnWeights[column];

					double ratio =
						preferredColumnWidths[column] / columnWeights[column];

					if (ratio > minColumnRatio) {
						minColumnRatio = ratio;
						minColumnWidth = preferredColumnWidths[column];
						minColumnWeight = columnWeights[column];
					}
				} else {
					width += preferredColumnWidths[column];
				}
			}

			if (nColumns > 1) {
				width += hGap * (nColumns - 1);
			}

			if (totalColumnWeight > 0) {
				width += (minColumnWidth * totalColumnWeight / minColumnWeight);
			}

			for (int row = 0; row < nRows; row++) {
				if (rowWeights[row] > 0.0) {
					totalRowWeight += rowWeights[row];

					double ratio = preferredRowHeights[row] / rowWeights[row];

					if (ratio > minRowRatio) {
						minRowRatio = ratio;
						minRowHeight = preferredRowHeights[row];
						minRowWeight = rowWeights[row];
					}
				} else {
					height += preferredRowHeights[row];
				}
			}

			if (nRows > 1) {
				height += vGap * (nRows - 1);
			}

			if (totalRowWeight > 0) {
				height += (minRowHeight * totalRowWeight / minRowWeight);
			}

			return new Dimension(
				width + insets.left + insets.right + leftMargin + rightMargin,
				height + insets.top + insets.bottom + topMargin + bottomMargin);
		}
	}

	/**
	 * Set the row and column sizes according to their weights
	 */
	private void setSizes(Container target) {
		if (hasSetSizes) {
			return;
		}

		synchronized (target.getTreeLock()) {
			Insets insets = target.getInsets();
			int usedWidth = 0;
			int usedHeigth = 0;
			int availWidth = 0;
			int availHeight = 0;
			double totalRowWeight = 0.0;
			double totalColumnWeight = 0.0;

			availWidth =
				target.getSize().width
					- (insets.left + insets.right + leftMargin + rightMargin);

			availHeight =
				target.getSize().height
					- (insets.top + insets.bottom + topMargin + bottomMargin);

			for (int row = 0; row < nRows; row++) {
				if (rowWeights[row] == 0.0) {
					availHeight -= preferredRowHeights[row];
				} else {
					totalRowWeight += rowWeights[row];
				}
			}

			if (nRows > 1) {
				availHeight -= vGap * (nRows - 1);
			}

			for (int row = 0; row < nRows; row++) {
				if (rowWeights[row] == 0.0) {
					rowHeights[row] = preferredRowHeights[row];
				} else {
					rowHeights[row] =
						(int) ((availHeight * rowWeights[row] + 0.5)
							/ totalRowWeight);
				}
			}

			for (int column = 0; column < nColumns; column++) {
				if (columnWeights[column] == 0.0) {
					availWidth -= preferredColumnWidths[column];
				} else {
					totalColumnWeight += columnWeights[column];
				}
			}

			if (nColumns > 1) {
				availWidth -= vGap * (nColumns - 1);
			}

			for (int column = 0; column < nColumns; column++) {
				if (columnWeights[column] == 0.0) {
					columnWidths[column] = preferredColumnWidths[column];
				} else {
					columnWidths[column] =
						(int) ((availWidth * columnWeights[column] + 0.5)
							/ totalColumnWeight);
				}
			}
		}

		hasSetSizes = true;
	}

	/**
	 * Lays out the container. This method lets each component take
	 * its preferred size by reshaping the components in the
	 * target container in order to satisfy the constraints of
	 * this <code>FlowLayout</code> object.
	 * @param target the specified component being laid out.
	 * @see Container
	 * @see       java.awt.Container#doLayout
	 */
	public void layoutContainer(Container target) {
		synchronized (target.getTreeLock()) {
			Insets insets = target.getInsets();
			int x;
			int y = insets.top + topMargin;

			getPreferredSizes();
			setSizes(target);

			for (int row = 0; row < nRows; row++) {
				x = insets.left + leftMargin;

				for (int column = 0; column < nColumns; column++) {
					if (map[row][column] != null
						&& (row == 0 || map[row][column] != map[row - 1][column])
						&& (column == 0
							|| map[row][column] != map[row][column - 1])) {

						int cx = x;
						int cy = y;

						Dimension compSize =
							map[row][column].getPreferredSize();
						Dimension cellSize =
							new Dimension(
								columnWidths[column],
								rowHeights[row]);

						// adjust for components spaning cells
						for (int i = 1;
							column + i < nColumns
								&& map[row][column] == map[row][column + i];
							i++) {
							cellSize.width += columnWidths[column + i] + hGap;
						}

						for (int i = 1;
							row + i < nRows
								&& map[row][column] == map[row + i][column];
							i++) {
							cellSize.height += rowHeights[row + i] + vGap;
						}

						if (compSize.width > cellSize.width) {
							compSize.width = cellSize.width;
						}

						switch (columnAlignments[column]) {
							case FILL :
								compSize.width = cellSize.width;
								break;

							case LEFT :
								break;

							case CENTER :
								cx += (int) (cellSize.width - compSize.width)
									/ 2;
								break;

							case RIGHT :
								cx += cellSize.width - compSize.width;
						}

						if (compSize.height > cellSize.height) {
							compSize.height = cellSize.height;
						}

						switch (rowAlignments[row]) {
							case FILL :
								compSize.height = cellSize.height;
								break;

							case TOP :
								break;

							case CENTER :
								cy += (int) (cellSize.height - compSize.height)
									/ 2;
								break;

							case RIGHT :
								cy += cellSize.height - compSize.height;
						}

						map[row][column].setLocation(cx, cy);
						map[row][column].setSize(compSize);
					}

					x += columnWidths[column] + hGap;
				}

				y += rowHeights[row] + vGap;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.javera.ui.LayoutPrint#layoutPrint()
	 */
	public int layoutPrint(Container target, int pageHeight) {
		Insets insets = target.getInsets();
		int height = this.getTopMargin() + insets.top;
		
		int[] rowHeights = new int[nRows];
		
		for (int row = 0; row < nRows; row++) {
			rowHeights[row] = 0;
			
			for (int column = 0; column < nColumns; column++) {
				if (map[row][column] == null) {
					continue;
				}
				
				if (row + 1 < nRows && map[row][column] == map[row + 1][column]) {
					continue;
				}
				
				if (column + 1 < nColumns && map[row][column] == map[row][column + 1]) {
					continue;
				}
				
				int compHeight = 0;
				
				if (map[row][column] instanceof LayoutPrint) {
					compHeight = ((LayoutPrint) map[row][column]).layoutPrint(pageHeight);
				} else {
					compHeight = map[row][column].getPreferredSize().height;
				}
				
				for (int tRow = row - 1; tRow >= 0 && map[tRow][column] == map[tRow + 1][column]; tRow--) {
					compHeight -= rowHeights[tRow] + vGap;
				}
				
				if (compHeight > rowHeights[row]) {
					rowHeights[row] = compHeight;
				}
			}
			
			for (int column = 0; column < nColumns; column++) {
				if (map[row][column] == null) {
					continue;
				}
				
				if (row + 1 < nRows && map[row][column] == map[row + 1][column]) {
					continue;
				}
								
				if (column + 1 < nColumns && map[row][column] == map[row][column + 1]) {
					continue;
				}
				
				int compHeight = rowHeights[row];
				int compY = height;
				
				for (int tRow = row - 1; tRow >= 0 && map[tRow][column] == map[tRow + 1][column]; tRow--) {
					compHeight += rowHeights[tRow] + vGap;
					compY -= rowHeights[tRow] + vGap;
				}
				
				map[row][column].setLocation(map[row][column].getX(), compY);
				map[row][column].setSize(map[row][column].getWidth(), compHeight);				
			}
						
			height += rowHeights[row];
				
			if (row + 1 < nRows) {
				height += vGap;
			}
		}
		
		return height + getBottomMargin() + insets.bottom;
	}
}
