package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

public interface Redoable {

    public void redo(ActionEvent evt);
    public boolean isRedoable(EventObject evt);
}