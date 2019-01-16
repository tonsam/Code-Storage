package com.javera.ui.actions;

import javax.swing.JInternalFrame;
import java.awt.event.ActionEvent;
import java.util.EventObject;

public interface Newable {
    public JInternalFrame makeNew(ActionEvent evt);
    public boolean        isNewable(EventObject evt);
}