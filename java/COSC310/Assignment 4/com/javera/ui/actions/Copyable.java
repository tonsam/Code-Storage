package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;


/**
 * Components interested in interacting with CopyAction (e.g. have UI-level
 * Copy support) should implement this.
 */
public interface Copyable {

    /**
     * Tells the Component to do the Copy.  
     */
    public void copy( ActionEvent evt );

    /** Return true if a Copy is current possible. */
    public boolean isCopyable( EventObject evt );
}

