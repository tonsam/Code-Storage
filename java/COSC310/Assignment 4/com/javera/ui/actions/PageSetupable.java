package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * This interface should be implemented by components that wish to work
 * with Print actions.
 */
public interface PageSetupable {
    /** Performs the Print action. */
    public void pageSetup( ActionEvent evt );

    /**
     * Implementing object should use this method to specify whether a Print
     * is currently performable.
     */
    public boolean isPageSetupable( EventObject evt );
}
