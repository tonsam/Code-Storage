package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;

import com.javera.ui.IconManager;

/**
 * Tool to initially construct a PtrDrawBox
 * 
 * @author David T. Smith
 */
public class PtrDrawBoxTool extends PtrDrawTool {
    /**
     * Constructor
     */
	public PtrDrawBoxTool() {
		super(IconManager.getIcon("box.gif"));
	}

	/**
	 * Process event that activates this tool
	 * 
	 * @param editor reference to the painter
	 * @param e the mouse event that initiated the activateTool.
	 */
	public void activateTool(PtrEditor editor, MouseEvent e) {
	    editor.clearSelection();

		PtrDrawBox box = new PtrDrawBox(editor, e.getX(), e.getY());
		box.setColor(editor.getColor());
		box.setLineColor(editor.getLineColor());
		box.setLineWidth(editor.getLineWidth());

		editor.addDrawObject(box);
		editor.setEditActionHandler(box.getBuildEditorAction()); 
		editor.selectDefaultTool();
		editor.repaint();
	}
}
