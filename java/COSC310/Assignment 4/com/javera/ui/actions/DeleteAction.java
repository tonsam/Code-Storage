package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.KeyStroke;


import com.javera.ui.IconManager;

public class DeleteAction extends ManagedStateAction {
    private static DeleteAction deleteAction = new DeleteAction();

    private DeleteAction() {
        super( "Delete", IconManager.getIcon( "Delete.gif" ) );
        putValue( Action.MNEMONIC_KEY, new Integer( 'D' ) );
        putValue( Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, 0 ) );
        putValue( Action.SHORT_DESCRIPTION, "Delete" );
    }

    public static DeleteAction getAction() { return deleteAction; }

    public void actionPerformed( ActionEvent evt ) {
        Deleteable deletable = ( Deleteable )
            ActionUtilities.getCommandTarget( evt, Deleteable.class ); 

        if ( deletable != null ) {
            deletable.delete( evt );
        }
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Deleteable deletable = ( Deleteable )
            ActionUtilities.getCommandTarget( evt, Deleteable.class );

        if ( deletable != null ) {
            return deletable.isDeleteable( evt );
        } else {
            return false;
        }
    }
}


