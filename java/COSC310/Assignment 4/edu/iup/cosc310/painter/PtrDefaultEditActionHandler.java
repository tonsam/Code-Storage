package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;

/**
 * A default implementation of a edit action handler.  mouseDrag is abstract and
 * must still be implemented by a subclass.
 * 
 * mousePressed is defined to do nothing.  
 * 
 * mouseReleased is defined to call mouseDrag and then remove the edit action handler
 * from the painter.
 * 
 * @author David T. Smith
 */
public abstract class PtrDefaultEditActionHandler implements PtrEditActionHandler {
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
    public abstract void mouseDrag(PtrEditor editor, MouseEvent e);
    
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
