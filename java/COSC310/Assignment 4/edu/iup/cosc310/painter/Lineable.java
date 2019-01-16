package edu.iup.cosc310.painter;

import java.awt.Color;

/**
 * An interface used to define that a PtrDrawObject is lineable. That
 * is the color and width of a line can be set.
 * 
 * @author dtsmith
 *
 */
public interface Lineable {
	/**
	 * Set the line color of a PtrDrawOject
	 * @param color the new color
	 */
	public void setLineColor(Color color);
	
	/**
	 * Set the line width of a PtrDrawOject
	 * @param lineWidth the new lineWidth
	 */
	public void setLineWidth(int lineWidth);
}
