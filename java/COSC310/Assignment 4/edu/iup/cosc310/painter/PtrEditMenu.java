package edu.iup.cosc310.painter;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.javera.ui.actions.CopyAction;
import com.javera.ui.actions.CutAction;
import com.javera.ui.actions.DeleteAction;
import com.javera.ui.actions.PasteAction;
import com.javera.ui.actions.RedoAction;
import com.javera.ui.actions.UndoAction;

/**
 * The edit menu for the painter
 * 
 * @author David T. Smith
  */
public class PtrEditMenu extends JMenu {
    /**
     * default constructor
     */
    public PtrEditMenu()
    {
        super("Edit");
        
        setMnemonic('E');

        JMenuItem m;
        JButton b;

        m = add(UndoAction.getAction());
        m.setMnemonic('U');
        m.setAccelerator(KeyStroke.getKeyStroke("control Z"));

        m = add(RedoAction.getAction());
        m.setMnemonic('R');

        addSeparator();

        m = add(CutAction.getAction());
        m.setMnemonic('t');

        m = add(CopyAction.getAction());
        m.setMnemonic('C');
        
        m = add(PasteAction.getAction());
        m.setMnemonic('P');

        m = add(DeleteAction.getAction());
        m.setMnemonic('D');
        m.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
    }    
}
