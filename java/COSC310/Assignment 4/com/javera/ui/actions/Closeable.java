package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * This interface should be implemented by UI classes that want to allow some data they handle to be saved.
 * @author David T. Smith
 */
public interface Closeable {

    public void close(ActionEvent evt);
    public boolean isCloseable(EventObject evt);
}