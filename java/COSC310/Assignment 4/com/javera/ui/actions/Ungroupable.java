package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;


public interface Ungroupable {

    /**
     * Tells the component to do the ungroup.  
     */
    public void ungroup( ActionEvent evt );

    /** Returns true if a ungroup is currently possible. */
    public boolean isUngroupable( EventObject evt );
}

