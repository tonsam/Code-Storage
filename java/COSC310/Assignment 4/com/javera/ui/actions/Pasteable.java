package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * Components interested in interaction with PasteAction should
 * implement this.
 */
public interface Pasteable {

    /** Hands the component the object that's been Pasted. */
    public void paste( ActionEvent evt);

    /** Returns true if a Paste is currently possible. */
    public boolean isPasteable( EventObject evt );
}


