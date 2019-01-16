package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

public interface Filterable {
    public void filter(ActionEvent evt);
    public boolean isFilterable(EventObject evt);
}