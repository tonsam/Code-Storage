package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;


public interface Cutable {

    /**
     * Tells the component to do the Cut.  
     */
    public void cut( ActionEvent evt );

    /** Returns true if a Cut is currently possible. */
    public boolean isCutable( EventObject evt );
}

