package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;

/**
 * An edit action handler for the purpose of moving an objects location.  
 * The edit handler takes into accout the x, y cooridates where the
 * drag was started and applys whatever delta from this position to
 * the draw objects location.
  * 
 * @author David T. Smith
 */
public class PtrDeltaDragEditHandler extends PtrDefaultEditActionHandler {
    private PtrDrawObject drawObject;  // The draw object to be updated
	private int deltaX;                // x delta where the drag started (i.e. mouse x - getX())
	private int deltaY;                // y delta where the drag started (i.e. mouse y - getY())

	/**
	 * Constructor 
	 * 
	 * @param drawObject draw object to be updated
	 * @param mouseX x coordinate of the mouse where the drag started
	 * @param deltaY y coordinate of the mouse where the drag started
	 */
	public PtrDeltaDragEditHandler(PtrDrawObject drawObject, int mouseX, int mouseY) {
	    this.drawObject = drawObject;
		this.deltaX = mouseX - drawObject.getX();
		this.deltaY = mouseY - drawObject.getY();
	}

    /**
     * Process a mouse drag event.  This will update the draw objects location taking into
     * account the calculated delta's
     * 
     * @param editor reference to the painter
     * @param e the mouse drag event
     */
	public void mouseDrag(PtrEditor editor, MouseEvent e) {
	    drawObject.setLocation(e.getX() - deltaX, e.getY() - deltaY);
	}
}