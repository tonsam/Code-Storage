//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui;

import javax.swing.Icon;

/**
 * GetIcon interface provides access to the getIcon() method used by
 * the various renderers to obtain an Icon for a given object
 * 
 * @author David T. Smith
 */
public interface GetIcon {
    /**
     * Get the icon to be displayed by the renderer
     * @ return the icon to be displayed
     */
    public Icon getIcon();
}