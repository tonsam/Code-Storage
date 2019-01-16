package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;

import com.javera.ui.IconManager;

/**
 * Tool to initially construct a PtrDrawOval
 * 
 * @author David T. Smith
 */
public class PtrDrawOvalTool extends PtrDrawTool {
    /**
     * Constructor
     */
	public PtrDrawOvalTool() {
		super(IconManager.getIcon("oval.gif"));
	}

	/**
	 * Process event that activates this tool
	 * 
	 * @param editor reference to the painter
	 * @param e the mouse event that initiated the activateTool.
	 */
	public void activateTool(PtrEditor editor, MouseEvent e) {
	    editor.clearSelection();

		PtrDrawOval oval = new PtrDrawOval(editor, e.getX(), e.getY());
		oval.setColor(editor.getColor());
		oval.setLineColor(editor.getLineColor());
		oval.setLineWidth(editor.getLineWidth());

		editor.addDrawObject(oval);
		editor.setEditActionHandler(oval.getBuildEditorAction()); 
		editor.selectDefaultTool();
		editor.repaint();
	}
}
