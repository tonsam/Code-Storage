package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * This interface should be implemented by components that wish to work
 * with Refresh actions.
 */
public interface Refreshable {
    /** Performs the Find action. */
    public void refresh( ActionEvent evt );

    /**
     * Implementing object should use this method to specify whether a Refresh
     * is currently performable.
     */
    public boolean isRefreshable( EventObject evt );
}
