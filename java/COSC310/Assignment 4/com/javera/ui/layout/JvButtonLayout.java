//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * A command button layout arranges components to be the same size and
 * aligns them on the right side.  Command button layouts are typically used
 * to arrange the command buttons buttons in a panel.
 *
 * @author David T. Smith
 */
public class JvButtonLayout
	implements LayoutManager, java.io.Serializable {
	/**
	 * The command button layout manager allows a seperation of
	 * components with gaps.  The horizontal gap will
	 * specify the space between components.
	 *
	 * @serial
	 * @see getGap
	 * @see setGap
	 */
	private int gap;

	/**
	 * The command button layout manager allows specification of
	 * a vertial margin to be reserved above and below all laidout
	 * components.
	 *
	 * @serial
	 * @see getVmargin
	 * @see setVmargin
	 */
	private int vmargin;

	/**
	 * The command button layout manager allows specification of
	 * a horizontal margin to be reserved to the left of the leftmost
	 * component and to the right of the right most component.
	 *
	 * @serial
	 * @see getHmargin
	 * @see setHmargin
	 */
	private int hmargin;

	/**
	 * Constructs a new Command Button Layout with a default 5 pixel gap,
	 * 5 pixel horizontal margin, and a 5 pixel vertical margin.
	 */
	public JvButtonLayout() {
		this(5, 5, 5);
	}

	/**
	 * Constructs a new Command Button Layout with a specified gap,
	 * default 5 pixel horizontal margin, and a default 5 pixel vertical margin.
	 *
	 * @param gap the specified gap
	 */
	public JvButtonLayout(int hgap) {
		this(hgap, 5, 5);
	}

	/**
	 * Constructs a new Command Button Layout with a specified gap,
	 * horizontal margin, and vertical margin.
	 *
	 * @param      gap     the gap between components.
	 * @param      hmargin the horizontal margin.
	 * @param      vmargin the vertical margin.
	 */
	public JvButtonLayout(int gap, int hmargin, int vmargin) {
		this.gap = gap;
		this.hmargin = hmargin;
		this.vmargin = vmargin;
	}

	/**
	 * Gets the gap between components.
	 * @return the gap between components.
	 */
	public int getGap() {
		return gap;
	}

	/**
	 * Sets the gap between components.
	 * @param hgap the gap between components
	 */
	public void setGap(int gap) {
		this.gap = gap;
	}

	/**
	 * Gets the horizontal margin to be reserved to the left of the leftmost component
	 * and to the right of the rightmost component.
	 * @return the horizontal margin.
	 */
	public int getHmargin() {
		return hmargin;
	}

	/**
	 * Sets the horizontal margin to be reserved to the left of the leftmost component
	 * and to the right of the rightmost component.
	 * @param hmargin the horizontal margin.
	 */
	public void setHmargin(int hmargin) {
		this.hmargin = hmargin;
	}

	/**
	 * Gets the vertical margin to be reserved below and above all components.
	 * @return the vertical margin.
	 */
	public int getVmargin() {
		return vmargin;
	}

	/**
	 * Sets the vertical margin to be reserve above and below components.
	 * @param vmargin the vertical margin.
	 */
	public void setVmargin(int vmargin) {
		this.vmargin = vmargin;
	}

	/**
	 * Adds the specified component to the layout. Not used by this class.
	 * @param name the name of the component
	 * @param comp the component to be added
	 */
	public void addLayoutComponent(String name, Component comp) {
	}

	/**
	 * Removes the specified component from the layout. Not used by
	 * this class.
	 * @param comp the component to remove
	 */
	public void removeLayoutComponent(Component comp) {
	}

	/**
	 * Returns the dimension that is the width of the widest component
	 * and the height of the tallest component.
	 * @param target the component which needs to be laid out
	 * @return   the dimension that is the width of the widest component
	 *           and the height of the tallest component.
	 */
	private Dimension componentLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Dimension rdim = new Dimension();

			int childCount = target.getComponentCount();

			for (int i = 0; i < childCount; i++) {
				Component child = target.getComponent(i);

				if (child.isVisible()) {
					Dimension cdim = child.getPreferredSize();

					rdim.width = Math.max(rdim.width, cdim.width);
					rdim.height = Math.max(rdim.height, cdim.height);
				}
			}

			return rdim;
		}
	}

	/**
	 * Returns the count of visible components.
	 * @param target the component which needs to be laid out
	 * @return   the count of visible components.
	 */
	private int getVisibleComponentCount(Container target) {
		synchronized (target.getTreeLock()) {

			int childCount = target.getComponentCount();
			int visibleCount = 0;

			for (int i = 0; i < childCount; i++) {
				Component child = target.getComponent(i);

				if (child.isVisible()) {
					visibleCount++;
				}
			}

			return visibleCount;
		}
	}

	/**
	 * Returns the preferred dimensions for this layout given the components
	 * in the specified target container.
	 * @param target the component which needs to be laid out
	 * @return    the preferred dimensions to lay out the
	 *            subcomponents of the specified container.
	 */
	public Dimension preferredLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {

			int visibleCount = getVisibleComponentCount(target);
			Dimension rdim = componentLayoutSize(target);

			rdim.width *= visibleCount;

			rdim.width += 2 * hmargin
				+ (visibleCount > 0 ? (visibleCount - 1) * gap : 0);
			rdim.height += 2 * vmargin;

			Insets insets = target.getInsets();

			rdim.width += insets.left + insets.right;
			rdim.height += insets.top + insets.bottom;

			return rdim;
		}
	}

	/**
	 * Returns the minimum dimensions needed to layout the components
	 * contained in the specified target container.
	 * @param target the component which needs to be laid out
	 * @return    the minimum dimensions to lay out the
	 *            subcomponents of the specified container.
	 */
	public Dimension minimumLayoutSize(Container target) {
		return preferredLayoutSize(target);
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
			Dimension cdim = componentLayoutSize(target);
			Insets insets = target.getInsets();
			int childCount = target.getComponentCount();
			int visibleCount = getVisibleComponentCount(target);
			int extraW =
				target.getSize().width
					- (insets.left + insets.right + hmargin * 2)
					- (cdim.width * visibleCount)
					- (visibleCount > 0 ? (visibleCount - 1) * gap : 0);
			int extraH =
				target.getSize().height
					- (insets.top + insets.right + vmargin * 2)
					- (cdim.height);
			int x = insets.left + hmargin + extraW / 2;
			int y = insets.top + vmargin + extraH / 2;

			for (int i = 0; i < childCount; i++) {
				Component child = target.getComponent(i);

				if (child.isVisible()) {
					child.setSize(cdim);
					child.setLocation(x, y);

					x += cdim.width + gap;
				}
			}
		}
	}
}
