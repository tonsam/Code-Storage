package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;

import com.javera.ui.IconManager;

/**
 * Tool to initially construct a PtrDrawLine
 * 
 * @author David T. Smith
 */
public class PtrDrawLineTool extends PtrDrawTool {
    /**
     * Constructor
     */
	public PtrDrawLineTool() {
		super(IconManager.getIcon("line.gif"));
	}

	/**
	 * Process event that activates this tool
	 * 
	 * @param editor reference to the painter
	 * @param e the mouse event that initiated the activateTool.
	 */
	public void activateTool(PtrEditor editor, MouseEvent e) {
	    editor.clearSelection();

		PtrDrawLine line = new PtrDrawLine(editor, e.getX(), e.getY());
		line.setLineColor(editor.getLineColor());
		line.setLineWidth(editor.getLineWidth());

		editor.addDrawObject(line);
		editor.setEditActionHandler(line.getBuildEditorAction()); 
		editor.selectDefaultTool();
		editor.repaint();
	}
}
