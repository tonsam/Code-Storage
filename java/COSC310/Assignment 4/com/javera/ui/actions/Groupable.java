package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;


public interface Groupable {

    /**
     * Tells the component to do the group.  
     */
    public void group( ActionEvent evt );

    /** Returns true if a group is currently possible. */
    public boolean isGroupable( EventObject evt );
}

