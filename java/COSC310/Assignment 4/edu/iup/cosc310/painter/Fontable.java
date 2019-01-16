package edu.iup.cosc310.painter;

import java.awt.Color;
import java.awt.Font;

/**
 * An interface used to define that a PtrDrawObject is fontable. That
 * is the font of a text can be set.
 * 
 * @author dtsmith
 *
 */
public interface Fontable {
	/**
	 * Set the font of a PtrDrawOject
	 * @param font the new Font
	 */
	public void setFont(Font font);
}
