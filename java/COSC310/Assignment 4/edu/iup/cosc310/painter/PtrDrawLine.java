package edu.iup.cosc310.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * A draw object that renders a line.
 * 
 * @author David T. Smith
 */
public class PtrDrawLine extends PtrDrawAbstractArea {
    /**
     * Constructor
     * 
	 * @param editor the editor that may hold the PtrDrawObject
     * @param x initial x coordinate for the line
     * @param y initial y coordinate for the line
     */
	public PtrDrawLine(PtrEditor editor, int x, int y) {
		super(editor, x, y);
	}

	/**
	 * Draw (render) a line onto the supplied graphics object.
	 * 
	 * @param g the graphics object
	 */
	public void draw(Graphics g) {
		g.setColor(getLineColor());
		
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setStroke(new BasicStroke(getLineWidth()));
		
		g2.drawLine(getX(), getY(), getX() + getWidth() - 1, getY() + getHeight() - 1);

		g2.dispose();
		
		if (isSelected()) {
		    drawGrabHandles(g);
		}
	}

	/**
	 * Indicate if the coordinate is within the bounds of the object.
	 * 
	 * @param x the x coordinate to be tested
	 * @param y the y coordinate to be tested
	 * @return true if the coordinate is within the bounds of the object.
	 */
	public boolean isOver(int x, int y) {
		if (super.isOver(x, y)) {
			return true;
		}

		if (x < getX() 
				|| x > getX() + getWidth() 
				|| y < getY()
				|| y > getY() + getHeight() ) {
			return false;
		}

		double ratio = (double) getHeight() / (double) getWidth();
			    
		if (ratio < 1 && Math.abs((getY() + (x - getX()) * ratio) - y) <= 2) {
			return true;
		}
		
		if (ratio >= 1 && Math.abs((getX() + (y - getY()) / ratio) - x) <= 2) {
			return true;
		}	
		
	    return false;
	}

	/**
	 * Get the editor action to finish constructing the box based upon mouse
	 * drag.  This editor action will set the dimesions of the box.
	 * 
	 * @return editor action to finish constructing the box
	 */
	public PtrEditActionHandler getBuildEditorAction() { 
		return new PtrDefaultEditActionHandler() {
			public void mouseDrag(PtrEditor editor, MouseEvent e) {
				setSize(e.getX() - PtrDrawLine.this.getX(), e.getY()
						- PtrDrawLine.this.getY());
			}
		};
	}
}