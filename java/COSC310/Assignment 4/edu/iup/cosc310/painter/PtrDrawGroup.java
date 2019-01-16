package edu.iup.cosc310.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * A draw object that renders a group.
 * 
 * @author David T. Smith
 */
public class PtrDrawGroup extends PtrDrawRect {
	List<PtrDrawObject> groupedObjects;
	private double originalWidth;
	private double originalHeight;
	
    /**
     * Constructor
     * 
	 * @param editor the editor that may hold the PtrDrawObject
     * @param x initial x coordinate for the group
     * @param y initial y coordinate for the box
     */
	public PtrDrawGroup(PtrEditor editor, int x, int y, int width, int height, List<PtrDrawObject> groupedObjects) {
		super(editor, x, y);
		setSize(width, height);
		originalWidth = width;
		originalHeight = height;
		this.groupedObjects = groupedObjects;
	}

	/**
	 * Draw (render) the group onto the supplied graphics object.
	 * 
	 * @param g the graphics object
	 */
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create(getX(), getY(), getWidth(),                   
                getHeight());

		g2.scale(getXScale(), getYScale());
		
		for (PtrDrawObject drawObject : groupedObjects) {
			drawObject.draw(g2);
		}

		g2.dispose();
		
		if (isSelected()) {
		    drawGrabHandles(g);
		}
	}
	
	/**
	 * get the horizontal scale by which the grouped objects are to be scaled
	 * @return the horizontal scale by which the grouped objects are to be scaled
	 */
	public double getXScale() {
		return getWidth() / originalWidth;
	}
	
	/**
	 * get the vertical scale by which the grouped objects are to be scaled
	 * @return the vertical scale by which the grouped objects are to be scaled
	 */
	public double getYScale() {
		return getHeight() / originalHeight;
	}

	
	/**
	 * get the list of objects composing the group
	 * @return the list of objects composing the group
	 */
	public List<PtrDrawObject> getGroupedObjects() {
		return groupedObjects;
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
				setSize(e.getX() - PtrDrawGroup.this.getX(), e.getY()
						- PtrDrawGroup.this.getY());
			}
		};
	}
}