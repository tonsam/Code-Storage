//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui;

import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

public interface GetPopupMenu {
    public JPopupMenu getPopupMenu(MouseEvent e);
}