package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

public interface Exitable {
    public void exit(ActionEvent evt);
    public boolean isExitable(EventObject evt);
}