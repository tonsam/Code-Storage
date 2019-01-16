package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;

import com.javera.ui.IconManager;

/**
 * Tool to initially construct a draw text object
 * 
 * @author David T. Smith
 */
public class PtrDrawTextTool extends PtrDrawTool {
    /**
     * Constructor
     */
	public PtrDrawTextTool() {
		super(IconManager.getIcon("text.gif"));
	}
	
	/**
	 * Process event that activates this tool
	 * 
	 * @param editor reference to the painter
	 * @param e the mouse event that initiated the activateTool.
	 */
	public void activateTool(PtrEditor editor, MouseEvent e) {
	    editor.clearSelection();

		PtrDrawText text = new PtrDrawText(editor, e.getX(), e.getY());

		editor.addDrawObject(text);
		editor.activateTextEditor(e.getX(), e.getY(), -1, -1, text);
		editor.selectDefaultTool();

		repaint();

		return;
	}

}
