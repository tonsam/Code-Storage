package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * This interface should be implemented by components that wish to work
 * with Find actions.
 */
public interface Findable {
    /** Performs the Find action. */
    public void find( ActionEvent evt );

    /**
     * Implementing object should use this method to specify whether a Find
     * is currently performable.
     */
    public boolean isFindable( EventObject evt );
}
