package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * Things that support adding a new item (AddAction) should implement this interface. 
 */
public interface Addable {
    public void add(ActionEvent e);
    public boolean isAddable(EventObject evt);
}