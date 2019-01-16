package edu.iup.cosc310.painter;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * Root class for all draw objects that are rectangular in nature, but need not be
 * fully rectangular. Processes the northwest and south east grab handles. This 
 * class does not provide any rendering.  Subclasses are still need to provide rendering.
 * 
 * Defines the size properties.  Also provides behavior for rendering and
 * behavior of nw and se grab handles.
 * 
 * @author dtsmith
 */
public class PtrDrawAbstractArea extends PtrDrawObject implements Serializable {
    private int width;
    private int height;

    private PtrDrawGrabHandle nwGrabHandle;
    private PtrDrawGrabHandle seGrabHandle;

	/**
	 * Base constructor - this will always get called.
	 * @param editor the editor that may hold the PtrDrawObject
	 */
    public PtrDrawAbstractArea(PtrEditor editor) {
    	super(editor);
    }

	/**
	 * Constructor setting initial location
	 * 
	 * @param editor the editor that may hold the PtrDrawObject
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
    public PtrDrawAbstractArea(PtrEditor editor, int x, int y) {
        super(editor, x, y);
    }
    
	/**
	 * Initialize - pre construct the grab handles.  This
	 * is needed since the methods to set location are 
	 * overridden to update the grab handle positions.
	 */
	public void init(PtrEditor editor) {
	    super.init(editor);
	    
	    nwGrabHandle = new PtrDrawGrabHandle(editor) {
	        public void mouseDrag(PtrEditor editor, MouseEvent e) {
	            PtrDrawAbstractArea.this.setSize(getWidth()
	                    - (e.getX() - PtrDrawAbstractArea.this.getX()), getHeight()
	                    - (e.getY() - PtrDrawAbstractArea.this.getY()));
	            PtrDrawAbstractArea.this.setLocation(e.getX(), e.getY());
	        }
	    };


	    seGrabHandle = new PtrDrawGrabHandle(editor) {
	        public void mouseDrag(PtrEditor editor, MouseEvent e) {
	            PtrDrawAbstractArea.this.setSize(e.getX() - PtrDrawAbstractArea.this.getX(), 
	                    e.getY() - PtrDrawAbstractArea.this.getY());
	        }
	    };

	}

	// Override setX, setY, and setLocation so that the grab handle
	// positions get updated.
	
	/**
	 * Set the x coordinate.
	 * 
	 * @param x the new x coodinate
	 */
    public void setX(int x) {
        super.setX(x);
        
        updateGrabHandles();
    }

	/**
	 * Set the y coordinate
	 * 
	 * @param y the new y coodinate
	 */
    public void setY(int y) {
        super.setY(y);
        
        updateGrabHandles();
    }


	/**
	 * Get the width
	 * 
	 * @return the width
	 */
    public int getWidth() {
        return width;
    }

	/**
	 * An edit action to redo/undo set width
	 * @author dtsmith
	 *
	 */
	private class SetWidthEditAction implements EditAction {
		int originalWidth;
		int newWidth;
		
		public SetWidthEditAction(int originalWidth, int newWidth) {
			super();
			this.originalWidth = originalWidth;
			this.newWidth = newWidth;
		}

		public void redo() {
			PtrDrawAbstractArea.this.width = newWidth;				
		}

		public void undo() {
			PtrDrawAbstractArea.this.width = originalWidth;				
		}
	}

	/**
	 * Set the width.
	 * 
	 * @param width the new width
	 */
	public void setWidth(final int width) {
		if (width != getWidth()) {
			editor.executeEditAction(new SetWidthEditAction(getWidth(), width));
		}
        
        updateGrabHandles();
    }

	/**
	 * Get the height
	 * 
	 * @return the height
	 */
    public int getHeight() {
        return height;
    }

	/**
	 * An edit action to redo/undo set height
	 * @author dtsmith
	 *
	 */
	private class SetHeightEditAction implements EditAction {
		int originalHeight;
		int newHeight;
		
		public SetHeightEditAction(int originalHeight, int newHeight) {
			super();
			this.originalHeight = originalHeight;
			this.newHeight = newHeight;
		}

		public void redo() {
			PtrDrawAbstractArea.this.height = newHeight;				
		}

		public void undo() {
			PtrDrawAbstractArea.this.height = originalHeight;				
		}
	}
	
	/**
	 * Set the height.
	 * 
	 * @param height the new height
	 */
	public void setHeight(final int height) {
		if (height != getHeight()) {
			editor.executeEditAction(new SetHeightEditAction(getHeight(), height));
		}
	        
	    updateGrabHandles();
	}
	
	/**
	 * Set the size (i.e. both width and height)
	 * 
	 * @param width the new width
	 * @param height the new height
	 */
    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    /**
     * Update the grab handle positions to reflect any changes to the
     * objects size any position.
     */
    protected void updateGrabHandles() {
        nwGrabHandle.setLocation(getX(), getY());
        seGrabHandle.setLocation(getX() + getWidth() - 1, getY() + getHeight()
                - 1);
    }

    /**
     * Draw the grab handles.  Subclasses should call this at the end of
     * their draw method so that the grab handles are rendered when the
     * object is selected and the rendering is on top of the objects rendering.
     * @param g
     */
    protected void drawGrabHandles(Graphics g) {
        if (isSelected()) {
	        nwGrabHandle.draw(g);
	        seGrabHandle.draw(g);
        }
    }
 
	/**
	 * Indicate the object lies within the bounds of a bounding box.
	 * 
	 * @param x the x coordinate of the bounding box
	 * @param y the y coordinate of the bounding box
	 * @param width of the bounding box
	 * @param height of the bounding box
	 * @return true if he object lies within the bounds of a bounding box.
	 */
	public boolean isInside(int x, int y, int width, int height) {
		return getX() >= x && getY() >= y && getX() + getWidth() <= x + width && getY() + getHeight() <= y + height;
	}

	/**
	 * Indicate if the coordinate is within the bounds of the object.
	 * 
	 * @param x the x coordinate to be tested
	 * @param y the y coordinate to be tested
	 * @return true if the coordinate is within the bounds of the object.
	 */
	public boolean isOver(int x, int y) {
	    if (isSelected()) {
	        if (nwGrabHandle.isOver(x, y)
                    || seGrabHandle.isOver(x, y)) {
	            return true;
	        }
	    } 

		return false;
	}

	/**
	 * Get the edit action handler for this object.  The edit action 
	 * handler is the object through which the painter will use to 
	 * edit the object.
	 * @param x the x coordinate of the mouse event used to activate
	 *          the action handler
	 * @param y the y coordinate of the mouse event used to activate
	 *          the action handler
	 * @return an edit action handler used to edit the object.
	 */
	public PtrEditActionHandler getEditActionHandler(int x, int y) {
	    if (isSelected()) {
	        if (nwGrabHandle.isOver(x, y)) {
	            return nwGrabHandle;
	        }
	        if (seGrabHandle.isOver(x, y)) {
	            return seGrabHandle;
	        }
	    }
	    
		return new PtrDeltaDragEditHandler(this, x, y);
	}
}