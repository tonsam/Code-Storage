package edu.iup.cosc310.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Iterator;

/**
 * A draw the selection box
 * 
 * @author David T. Smith
 */
public class PtrDrawSelection extends PtrDrawRect {
    /**
     * Constructor
     * 
     * @param x initial x coordinate for the box
     * @param y initial y coordinate for the box
     */
	public PtrDrawSelection(PtrEditor editor, int x, int y) {
		super(editor, x, y);
	}

	/**
	 * Draw (render) a box onto the supplied graphics object.
	 * 
	 * @param g the graphics object
	 */
	public void draw(Graphics g) {
		g.setColor(Color.gray);
		
		g.drawLine(getX(), getY(), getX() + getWidth() - 1, getY());
		g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth() - 1,
				getY() + getHeight() - 1);
		g.drawLine(getX(), getY(), getX(), getY() + getHeight() - 1);
		g.drawLine(getX() + getWidth() - 1, getY(), getX() + getWidth() - 1,
				getY() + getHeight() - 1);
	}

	/**
	 * Get the editor action to finish constructing the box based upon mouse
	 * drag.  This editor action will set the dimensions of the box.
	 * 
	 * @return editor action to finish constructing the box
	 */
	public PtrEditActionHandler getBuildEditorAction() { 
		return new PtrDefaultEditActionHandler() {
			public void mouseDrag(PtrEditor editor, MouseEvent e) {
				setSize(e.getX() - PtrDrawSelection.this.getX(), e.getY()
						- PtrDrawSelection.this.getY());
			}
		    public void mouseReleased(PtrEditor editor, MouseEvent e) {  
		    	mouseDrag(editor, e);
		    	editor.selectObjects(PtrDrawSelection.this.getX(), PtrDrawSelection.this.getY(), PtrDrawSelection.this.getWidth(), PtrDrawSelection.this.getHeight());
		    	editor.setEditActionHandler(null);
		    	editor._removeDrawObject(PtrDrawSelection.this);
		    	editor.selectObjects(getX(), getY(), getWidth(), getHeight());
		    }
		};
	}
}