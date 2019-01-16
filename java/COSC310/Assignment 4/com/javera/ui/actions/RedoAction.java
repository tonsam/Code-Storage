package com.javera.ui.actions;

import com.javera.ui.IconManager;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import javax.swing.Action;
import javax.swing.KeyStroke;

public class RedoAction extends ManagedStateAction {
    private static RedoAction redoAction = new RedoAction();

    private RedoAction() {
        super( "Redo", IconManager.getIcon( "Redo.gif" ) );
        putValue( Action.MNEMONIC_KEY, new Integer( 'R' ) );
        putValue( Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke( KeyEvent.VK_Z,
            Event.CTRL_MASK + Event.SHIFT_MASK ) );
        putValue( Action.SHORT_DESCRIPTION, "Redo" );
    }

    public static RedoAction getAction() {
        return redoAction;
    }

    public void actionPerformed( ActionEvent evt ) {
        Redoable redoable = ( Redoable )
            ActionUtilities.getCommandTarget( evt, Redoable.class );

        if ( redoable != null )
            redoable.redo( evt );
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Redoable redoable = ( Redoable )
            ActionUtilities.getCommandTarget( evt, Redoable.class );

        if ( redoable != null ) {
            return redoable.isRedoable( evt );
        } else {
            return false;
        }
    }
}
