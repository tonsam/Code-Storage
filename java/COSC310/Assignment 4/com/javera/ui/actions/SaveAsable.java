//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * This interface should be implemented by UI classes that want to allow some data they handle to be saved
 * as another entity.
 * @author David T. Smith
 */
public interface SaveAsable {

    public void saveAs(ActionEvent evt);
    public boolean isSaveAsable(EventObject evt);
}