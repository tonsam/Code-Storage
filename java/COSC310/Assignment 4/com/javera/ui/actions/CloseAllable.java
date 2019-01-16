package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * This interface should be implemented by UI classes that want to allow some data they handle to be saved.
 * @author David T. Smith
 */
public interface CloseAllable {

    public boolean closeAll(ActionEvent evt);
    public boolean isCloseAllable(EventObject evt);
}