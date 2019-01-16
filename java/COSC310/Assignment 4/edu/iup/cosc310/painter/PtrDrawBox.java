package edu.iup.cosc310.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * A draw object that renders a box.
 * 
 * @author David T. Smith
 */
public class PtrDrawBox extends PtrDrawRect implements Serializable {
    /**
     * Constructor
     * 
	 * @param editor the editor that may hold the PtrDrawObject
     * @param x initial x coordinate for the box
     * @param y initial y coordinate for the box
     */
	public PtrDrawBox(PtrEditor editor, int x, int y) {
		super(editor, x, y);
	}

	/**
	 * Draw (render) a box onto the supplied graphics object.
	 * 
	 * @param g the graphics object
	 */
	public void draw(Graphics g) {
	    g.setColor(getColor());
	    g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(getLineColor());
		
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setStroke(new BasicStroke(getLineWidth()));
		
		g2.drawLine(getX(), getY(), getX() + getWidth() - 1, getY());
		g2.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth() - 1,
				getY() + getHeight() - 1);
		g2.drawLine(getX(), getY(), getX(), getY() + getHeight() - 1);
		g2.drawLine(getX() + getWidth() - 1, getY(), getX() + getWidth() - 1,
				getY() + getHeight() - 1);
		g2.dispose();
		
		if (isSelected()) {
		    drawGrabHandles(g);
		}
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
				setSize(e.getX() - PtrDrawBox.this.getX(), e.getY()
						- PtrDrawBox.this.getY());
			}
		};
	}
}