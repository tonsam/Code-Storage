package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

public interface Undoable {

    public void undo(ActionEvent evt);
    public boolean isUndoable(EventObject evt);
}