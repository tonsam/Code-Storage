package edu.iup.cosc310.painter;

import java.awt.Color;

/**
 * An interface used to define that a PtrDrawObject is colorable. That
 * is the background color or color of a text can be set.
 * 
 * @author dtsmith
 *
 */
public interface Colorable {
	/**
	 * Set the color of a PtrDrawOject
	 * @param color the new Color
	 */
	public void setColor(Color color);
}
