package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;

/**
 * This interface defines the action events that the painter my delegate to a draw object, draw
 * tool, etc.
 * 
 * @author David T. Smith
 */
public interface PtrEditActionHandler {
    /**
     * Process a mouse pressed event.
     * 
     * @param editor reference to the painter
     * @param e the mouse pressed event
     */
    public void mousePressed(PtrEditor editor, MouseEvent e);
    
    /**
     * Process a mouse drag event.
     * 
     * @param editor reference to the painter
     * @param e the mouse drag event
     */
    public void mouseDrag(PtrEditor editor, MouseEvent e);  
    
    /**
     * Process a mouse released event.
     * 
     * @param editor reference to the painter
     * @param e the mouse released event
     */
    public void mouseReleased(PtrEditor editor, MouseEvent e);
}
