package com.javera.ui.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.KeyStroke;


public class UngroupAction extends ManagedStateAction {
    private static UngroupAction groupAction = new UngroupAction();

    private UngroupAction() {
        super( "Ungroup" );
        putValue( Action.MNEMONIC_KEY, new Integer( 'U' ) );
        putValue( Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke( KeyEvent.VK_U, Event.CTRL_MASK ) );
        putValue( Action.SHORT_DESCRIPTION, "Ungroup" );
    }

    public static UngroupAction getAction() { return groupAction; }

    public void actionPerformed( ActionEvent e ) {
    	Ungroupable ungroup = ( Ungroupable )
            ActionUtilities.getCommandTarget( e, Ungroupable.class );

        if ( ungroup != null ) {
        	ungroup.ungroup( e );
        }
    }

    public boolean isTargetEnabled( EventObject evt ) {
    	Ungroupable ungroup = ( Ungroupable )
            ActionUtilities.getCommandTarget( evt, Ungroupable.class );

        if ( ungroup != null ) {
            return ungroup.isUngroupable( evt );
        } else {
            return false;
        }
    }
}



