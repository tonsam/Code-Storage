package com.javera.ui.actions;

import java.util.EventObject;

import javax.swing.JInternalFrame;

public interface OpenableSpecial {
    public JInternalFrame openSpecial();
    public boolean        isOpenableSpecial(EventObject evt);
}