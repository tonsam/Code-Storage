package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.JPopupMenu;

import com.javera.ui.IconManager;

/**
 * A Selection tool.
 * 
 * Most of the time this tool is processing clicks to select and deselect objects.  
 * When dragging over the canvas. This tool will create a selection object to render
 * a selection area.  The selection area object will perform the action of determining
 * if objects are within a selection area and if so mark them as selected.
 *  
 * @author David T. Smith
 */
public class PtrDrawSelectionTool extends PtrDrawTool {
    /**
     * Constructor
     */
	public PtrDrawSelectionTool() {
		super(IconManager.getIcon("pointer.gif"));
	}
	
	/**
	 * Process event that activates this tool
	 * 
	 * @param editor reference to the painter
	 * @param e the mouse event that initiated the activateTool.
	 */
	public void activateTool(PtrEditor editor, MouseEvent e) {
		PtrDrawObject overObject = editor.getOver(e.getX(), e.getY());

		if (overObject != null) {
			if (e.isControlDown()) {
				overObject.setSelected(!overObject.isSelected());
				repaint();
			} else {
				if (!overObject.isSelected()) {
				    editor.clearSelection();
					overObject.setSelected(true);
					editor.repaint();
				}
			}

			if (overObject.isSelected()) {
			    boolean setSelected = false;
				for (Iterator iter = editor.getDrawObjects().iterator(); iter.hasNext();) {
					PtrDrawObject object = (PtrDrawObject) iter.next();

					if (object != overObject && object.isSelected()) {
					    setSelected = true;
					    break;
					}
				}
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu popupMenu = overObject.getPopupMenu(
							e.getX(), e.getY());

					if (popupMenu != null) {
					    editor.showPopupMenu(popupMenu, e.getX(), e.getY());
					}
				} else {
					if (setSelected) {
					    PtrDragSetActionHandler dragSetEditor = new PtrDragSetActionHandler(e.getX(), e.getY());
						
						for (Iterator iter = editor.getDrawObjects().iterator(); iter.hasNext();) {
							PtrDrawObject object = (PtrDrawObject) iter.next();

							if (object.isSelected()) {
							    dragSetEditor.add(object);
							}
						}
						
						editor.setEditActionHandler(dragSetEditor);
					} else {
					    editor.setEditActionHandler(overObject.getEditActionHandler(e.getX(), e
							.getY()));
					}
				}
			}
		} else {
		    editor.clearSelection();
		    
			PtrDrawSelection box = new PtrDrawSelection(editor, e.getX(), e.getY());
			editor._addDrawObject(box);
			editor.setEditActionHandler(box.getBuildEditorAction()); 
		    editor.repaint();
		}

		return;
	}
}
