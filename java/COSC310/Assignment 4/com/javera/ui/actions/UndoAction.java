package com.javera.ui.actions;

import com.javera.ui.IconManager;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import javax.swing.Action;
import javax.swing.KeyStroke;

public class UndoAction extends ManagedStateAction {
    private static UndoAction undoAction = new UndoAction();

    private UndoAction() {
        super( "Undo", IconManager.getIcon( "Undo.gif" ) );
        putValue( Action.MNEMONIC_KEY, new Integer( 'U' ) );
        putValue( Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke( KeyEvent.VK_Z, Event.CTRL_MASK ) );
        putValue( Action.SHORT_DESCRIPTION, "Undo" );
    }

    public static UndoAction getAction() {
        return undoAction;
    }

    public void actionPerformed( ActionEvent evt ) {
        Undoable undoable = ( Undoable )
            ActionUtilities.getCommandTarget( evt, Undoable.class );

        if ( undoable != null )
            undoable.undo( evt );
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Undoable undoable = ( Undoable )
            ActionUtilities.getCommandTarget( evt, Undoable.class );

        if ( undoable != null ) {
            return undoable.isUndoable( evt );
        } else {
            return false;
        }
    }
}
