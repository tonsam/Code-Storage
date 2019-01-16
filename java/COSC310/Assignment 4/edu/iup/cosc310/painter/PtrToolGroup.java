package edu.iup.cosc310.painter;

import java.util.Enumeration;

import javax.swing.ButtonGroup;

/**
 * PtrToolGroup provides exclusive selection of a PtrTool and a getSelection method to
 * identify the currently selected tool.
 * 
 * @author David T. Smith
 */
public class PtrToolGroup {
    private ButtonGroup buttonGroup = new ButtonGroup();

    /**
     * Add a tool to the exclusive set.
     * 
     * @param tool tool to be added
     * @return the added tool
     */
    public PtrDrawTool add(PtrDrawTool tool) {
        buttonGroup.add(tool);
        return tool;
    }

    /**
     * Get the currently selected tool.
     * 
     * @return the currently selected tool.
     */
    public PtrDrawTool getSelection() {
        for (Enumeration enumerator = buttonGroup.getElements(); enumerator.hasMoreElements();) {
            PtrDrawTool tool = (PtrDrawTool) enumerator.nextElement();

            if (tool.getModel() == buttonGroup.getSelection()) {
                return tool;
            }
        }
        return null;
    }
}