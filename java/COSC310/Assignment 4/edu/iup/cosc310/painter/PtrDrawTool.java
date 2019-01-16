package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JToggleButton;

/**
 * A draw tool is a toogle button that the painter can
 * add to its tool bar.  It provides a method to activate
 * the tool.  This is typically done when the painter 
 * performs a mouse pressed event and the tool is currently
 * selected.
 * 
 * @author David T. Smith
 */
public abstract class PtrDrawTool extends JToggleButton {
    /**
     * Constructor 
     * 
     * @param icon the icon to render on the button
     */
	public PtrDrawTool(Icon icon) {
		super(icon);
	}
	
	/**
	 * Process event that activates this tool
	 * 
	 * @param editor reference to the painter
	 * @param e the mouse event that initiated the activateTool.
	 */
	public abstract void activateTool(PtrEditor editor, MouseEvent e);
}
