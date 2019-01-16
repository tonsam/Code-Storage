package edu.iup.cosc310.painter;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * Root class for all draw objects that are rectangular.  This class does
 * not provide any rendering.  Subclasses are still need to provide rendering.
 * 
 * The nw and se grab handles are inherited from PtrAbstractArea object.
 * 
 * Size properties are also inherited.
 * 
 * Also provides behavior for rendering and behavior of remaining grab handles 
 * at all corners and in the center of the sides.
 * 
 * @author dtsmith
 */
public class PtrDrawRect extends PtrDrawAbstractArea implements Serializable {

    private PtrDrawGrabHandle nGrabHandle;
    private PtrDrawGrabHandle neGrabHandle;
    private PtrDrawGrabHandle eGrabHandle;
    private PtrDrawGrabHandle sGrabHandle;
    private PtrDrawGrabHandle swGrabHandle;
    private PtrDrawGrabHandle wGrabHandle;

	/**
	 * Base constructor - this will always get called.
	 * @param editor the editor that may hold the PtrDrawObject
	 */
    public PtrDrawRect(PtrEditor editor) {
    	super(editor);
    }

	/**
	 * Constructor setting initial location
	 * 
	 * @param editor the editor that may hold the PtrDrawObject
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
    public PtrDrawRect(PtrEditor editor, int x, int y) {
        super(editor, x, y);
    }
    
	/**
	 * Initialize - pre construct the grab handles.  This
	 * is needed since the methods to set location are 
	 * overridden to update the grab handle positions.
	 */
	public void init(PtrEditor editor) {
	    super.init(editor);
	    
	    nGrabHandle = new PtrDrawGrabHandle(editor) {
	        public void mouseDrag(PtrEditor editor, MouseEvent e) {
	            PtrDrawRect.this.setHeight(getHeight()
	                    - (e.getY() - PtrDrawRect.this.getY()));
	            PtrDrawRect.this.setY(e.getY());
	        }
	    };

	    neGrabHandle = new PtrDrawGrabHandle(editor) {
	        public void mouseDrag(PtrEditor editor, MouseEvent e) {
	            PtrDrawRect.this.setSize(e.getX() - PtrDrawRect.this.getX(), 
	                    getHeight()
	                    - (e.getY() - PtrDrawRect.this.getY()));
	            PtrDrawRect.this.setY(e.getY());
	        }
	    };

	    eGrabHandle = new PtrDrawGrabHandle(editor) {
	        public void mouseDrag(PtrEditor editor, MouseEvent e) {
	            PtrDrawRect.this.setWidth(e.getX() - PtrDrawRect.this.getX());
	        }
	    };

	    sGrabHandle = new PtrDrawGrabHandle(editor) {
	        public void mouseDrag(PtrEditor editor, MouseEvent e) {
	            PtrDrawRect.this.setHeight(e.getY() - PtrDrawRect.this.getY());
	        }
	    };

	    swGrabHandle = new PtrDrawGrabHandle(editor) {
	        public void mouseDrag(PtrEditor editor, MouseEvent e) {
	            PtrDrawRect.this.setSize(getWidth()
	                    - (e.getX() - PtrDrawRect.this.getX()),
	                    e.getY() - PtrDrawRect.this.getY());
	            PtrDrawRect.this.setX(e.getX());
	        }
	    };

	    wGrabHandle = new PtrDrawGrabHandle(editor) {
	        public void mouseDrag(PtrEditor editor, MouseEvent e) {
	            PtrDrawRect.this.setWidth(getWidth()
	                    - (e.getX() - PtrDrawRect.this.getX()));
	            PtrDrawRect.this.setX(e.getX());
	        }
	    };
	}

	// Override setX, setY, and setLocation so that the grab handle
	// positions get updated.
	
	/**
	 * Set the x coodinate.
	 * 
	 * @param x the new x coodinate
	 */
    public void setX(int x) {
        super.setX(x);
        
        updateGrabHandles();
    }

	/**
	 * Set the y coodinate
	 * 
	 * @param y the new y coodinate
	 */
    public void setY(int y) {
        super.setY(y);
        
        updateGrabHandles();
    }

	/**
	 * Set the location (i.e. both x and y coodinates)
	 * 
	 * @param x the new x coodinate
	 * @param y the new y coodinate
	 */
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        
        updateGrabHandles();
    }


    /**
     * Update the grab handle positions to reflect any changes to the
     * objects size any position.
     */
    protected void updateGrabHandles() {
    	super.updateGrabHandles();
        nGrabHandle.setLocation(getX() + getWidth() / 2, getY());
        neGrabHandle.setLocation(getX() + getWidth() - 1, getY());
        eGrabHandle.setLocation(getX() + getWidth() - 1, getY() + getHeight()
                / 2);
        sGrabHandle.setLocation(getX() + getWidth() / 2, getY() + getHeight()
                - 1);
        swGrabHandle.setLocation(getX(), getY() + getHeight() - 1);
        wGrabHandle.setLocation(getX(), getY() + getHeight() / 2);
    }

    /**
     * Draw the grab handles.  Subclasses should call this at the end of
     * their draw method so that the grab handles are rendered when the
     * object is selected and the rendering is on top of the objects rendering.
     * @param g
     */
    protected void drawGrabHandles(Graphics g) {
    	super.drawGrabHandles(g);
        if (isSelected()) {
	        nGrabHandle.draw(g);
	        neGrabHandle.draw(g);
	        eGrabHandle.draw(g);
	        sGrabHandle.draw(g);
	        swGrabHandle.draw(g);
	        wGrabHandle.draw(g);
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
		
	    if (isSelected()) {
	        if (nGrabHandle.isOver(x, y)
	                || nGrabHandle.isOver(x, y) 
	                || neGrabHandle.isOver(x, y)
	                || eGrabHandle.isOver(x, y)
                    || sGrabHandle.isOver(x, y)
                    || swGrabHandle.isOver(x, y)
                    || wGrabHandle.isOver(x, y)) {
	            return true;
	        }
	    } 

		return x >= getX() 
			&& x < getX() + getWidth() 
			&& y >= getY()
			&& y < getY() + getHeight();
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
	        if (nGrabHandle.isOver(x, y)) {
	            return nGrabHandle;
	        }
	        if (neGrabHandle.isOver(x, y)) {
	            return neGrabHandle;
	        }
	        if (eGrabHandle.isOver(x, y)) {
	            return eGrabHandle;
	        }
	        if (sGrabHandle.isOver(x, y)) {
	            return sGrabHandle;
	        }
	        if (swGrabHandle.isOver(x, y)) {
	            return swGrabHandle;
	        }
	        if (wGrabHandle.isOver(x, y)) {
	            return wGrabHandle;
	        }
	    }
	    
	    return super.getEditActionHandler(x, y);
	}
}