package com.javera.ui.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.KeyStroke;


public class GroupAction extends ManagedStateAction {
    private static GroupAction groupAction = new GroupAction();

    private GroupAction() {
        super( "Group" );
        putValue( Action.MNEMONIC_KEY, new Integer( 'G' ) );
        putValue( Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke( KeyEvent.VK_G, Event.CTRL_MASK ) );
        putValue( Action.SHORT_DESCRIPTION, "Group" );
    }

    public static GroupAction getAction() { return groupAction; }

    public void actionPerformed( ActionEvent e ) {
        Groupable group = ( Groupable )
            ActionUtilities.getCommandTarget( e, Groupable.class );

        if ( group != null ) {
            group.group( e );
        }
    }

    public boolean isTargetEnabled( EventObject evt ) {
    	Groupable group = ( Groupable )
            ActionUtilities.getCommandTarget( evt, Groupable.class );

        if ( group != null ) {
            return group.isGroupable( evt );
        } else {
            return false;
        }
    }
}



