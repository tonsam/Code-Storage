package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

public interface Openable {
    public void open(ActionEvent evt);
    public boolean        isOpenable(EventObject evt);
}