package edu.iup.cosc310.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * A draw object that renders a box.
 * 
 * @author David T. Smith
 */
public class PtrDrawOval extends PtrDrawRect {
    /**
     * Constructor
     * 
	 * @param editor the editor that may hold the PtrDrawObject
     * @param x initial x coordinate for the oval
     * @param y initial y coordinate for the oval
     */
	public PtrDrawOval(PtrEditor editor, int x, int y) {
		super(editor, x, y);
	}

	/**
	 * Draw (render) a oval onto the supplied graphics object.
	 * 
	 * @param g the graphics object
	 */
	public void draw(Graphics g) {
	    g.setColor(getColor());
	    g.fillOval(getX(), getY(), getWidth(), getHeight());
		g.setColor(getLineColor());
		
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setStroke(new BasicStroke(getLineWidth()));
		
	    g2.drawOval(getX(), getY(), getWidth(), getHeight());
	    
		g2.dispose();
		
		if (isSelected()) {
		    drawGrabHandles(g);
		}
	}

	/**
	 * Get the editor action to finish constructing the oval based upon mouse
	 * drag.  This editor action will set the dimesions of the box.
	 * 
	 * @return editor action to finish constructing the box
	 */
	public PtrEditActionHandler getBuildEditorAction() { 
		return new PtrDefaultEditActionHandler() {
			public void mouseDrag(PtrEditor editor, MouseEvent e) {
				setSize(e.getX() - PtrDrawOval.this.getX(), e.getY()
						- PtrDrawOval.this.getY());
			}
		};
	}
}