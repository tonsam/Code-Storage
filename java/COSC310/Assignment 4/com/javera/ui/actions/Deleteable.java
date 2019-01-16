package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

public interface Deleteable {

    public void delete(ActionEvent evt);
    public boolean isDeleteable(EventObject evt);
}