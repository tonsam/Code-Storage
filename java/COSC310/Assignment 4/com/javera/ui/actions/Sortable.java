package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

public interface Sortable {
    public void sort(ActionEvent e);
    public boolean isSortable(EventObject evt);
}