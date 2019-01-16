package edu.iup.cosc310.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * A draw object for a grab handle.  This draw object is also an edit action handler.
 * It is defined as abstract.  When used, an implementation for mouseDrag event must be
 * supplied.  Typically the implementation will update some attributes of the draw object
 * on which the grab handle is rendered.
 * 
 * @author dtsmith
 */
public abstract class PtrDrawGrabHandle extends PtrDrawObject implements Serializable, PtrEditActionHandler {

	private static int RADIUS = 3;
	
	public PtrDrawGrabHandle(PtrEditor edtitor) {
		super(edtitor);
	}
	
	/**
	 * Render the grab handle as a box of size 2 from the center coordinate.
	 */
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(getX() - RADIUS, getY() - RADIUS, RADIUS * 2, RADIUS * 2);
		g.setColor(Color.black);

		g.drawLine(getX() - RADIUS, getY() - RADIUS, getX() + RADIUS, getY() - RADIUS);
		g.drawLine(getX() - RADIUS, getY() + RADIUS, getX() + RADIUS, getY() + RADIUS);
		g.drawLine(getX() - RADIUS, getY() - RADIUS, getX() - RADIUS, getY() + RADIUS);
		g.drawLine(getX() + RADIUS, getY() - RADIUS, getX() + RADIUS, getY() + RADIUS);
	}

	/**
	 * Indicate if the coordinate is within the bounds of the object.
	 * 
	 * @param x the x coordinate to be tested
	 * @param y the y coordinate to be tested
	 * @return true if the coordinate is within the bounds of the object.
	 */
	public boolean isOver(int x, int y) {
		return x >= (getX() - RADIUS) && x < (getX() + RADIUS) && y >= (getY() - RADIUS) && y < (getY() + RADIUS);
	}

    /**
     * Process a mouse pressed event.
     * 
     * @param editor reference to the painter
     * @param e the mouse pressed event
     */
	public void mousePressed(PtrEditor editor, MouseEvent e) {
	}
	
    /**
     * Process a mouse drag event.
     * 
     * @param editor reference to the painter
     * @param e the mouse drag event
     */
	abstract public void mouseDrag(PtrEditor editor, MouseEvent e);
	
    /**
     * Process a mouse released event.
     * 
     * @param editor reference to the painter
     * @param e the mouse released event
     */
	public void mouseReleased(PtrEditor editor, MouseEvent e) {
		mouseDrag(editor, e);
		editor.setEditActionHandler(null);
	}
}