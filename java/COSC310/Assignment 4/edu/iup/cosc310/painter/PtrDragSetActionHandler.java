package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * An edit action handler for the purpose of moving a set of selected
 * draw objects. The edit handler takes into accout the x, y cooridates where the
 * drag was started and applys whatever delta from this position to each
 * the draw objects location.
  * 
 * @author David T. Smith
 */
public class PtrDragSetActionHandler extends PtrDefaultEditActionHandler {
	private ArrayList list = new ArrayList();  // Set of draw objects to be moved
	private int lastX;                         // last known x coordinate of the mouse
	private int lastY;                         // last known y coordinate of the mouse
	
	/**
	 * Constructor 
	 * 
	 * @param mouseX x coordinate of the mouse where the drag started
	 * @param deltaY y coordinate of the mouse where the drag started
	 */
	public PtrDragSetActionHandler(int firstX, int firstY) {
	    lastX = firstX;
	    lastY = firstY;
	}

	/**
	 * Add a draw object to the set of objects to be moved.  Note all objects should be
	 * added immediately following construction of a drag set editor and before processing
	 * any drag events.
	 * 
	 * @param drawObject the draw object to be added.
	 */
	public void add(PtrDrawObject drawObject) {
		list.add(drawObject);
	}
	
    /**
     * Process a mouse drag event.  This will update each of the draw objects location taking into
     * delta of the current mouse position and the last know mouse position.  
     * 
     * @param editor reference to the painter
     * @param e the mouse drag event
     */
	public void mouseDrag(PtrEditor editor, MouseEvent e) {
	    for (Iterator iter = list.iterator(); iter.hasNext(); ) {
	        PtrDrawObject drawObject = (PtrDrawObject) iter.next();
	        drawObject.setLocation(drawObject.getX() + e.getX() - lastX, drawObject.getY() + e.getY() - lastY);
	    }
	    
	    lastX = e.getX();
	    lastY = e.getY();
	}	
}